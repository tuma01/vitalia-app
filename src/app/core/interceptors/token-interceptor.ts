import {
  HttpInterceptorFn,
  HttpRequest,
  HttpHandlerFn,
  HttpErrorResponse
} from '@angular/common/http';

import { inject } from '@angular/core';
import { BehaviorSubject, catchError, filter, switchMap, take, throwError } from 'rxjs';
import { TokenService } from '../token/token.service';
import { RefreshTokenService } from '../token/refresh-token.service';
import { SessionService } from '../services/session.service';

let isRefreshing = false;
const refreshTokenSubject = new BehaviorSubject<string | null>(null);

export const tokenInterceptor: HttpInterceptorFn = (req, next) => {
  const tokenService = inject(TokenService);
  const refreshTokenService = inject(RefreshTokenService);
  const sessionService = inject(SessionService);

  // 🛡️ SKIP auth endpoints to prevent loops
  const isAuthRequest = req.url.includes('/auth/login') || req.url.includes('/auth/refresh');

  const accessToken = tokenService.accessToken;

  let authReq = req;
  if (accessToken && !isAuthRequest) {
    authReq = req.clone({
      setHeaders: {
        Authorization: `Bearer ${accessToken}`
      }
    });
  }

  return next(authReq).pipe(
    catchError((error: HttpErrorResponse) => {
      // 1. Handle non-401 or auth requests normally
      if (error.status !== 401 || isAuthRequest) {
        if (error.status === 401 && req.url.includes('/auth/refresh')) {
          console.error('[TokenInterceptor] Refresh failed. Logging out.');
          sessionService.logout();
        }
        return throwError(() => error);
      }

      // 2. Start refresh if not in progress
      if (!isRefreshing) {
        isRefreshing = true;
        refreshTokenSubject.next(null);

        console.log('[TokenInterceptor] 🔄 Access token expired. Refreshing...');

        return refreshTokenService.refreshAccessToken().pipe(
          switchMap((response: any) => {
            const newAccessToken = response?.tokens?.accessToken || response?.accessToken;

            if (!newAccessToken) {
              console.error('[TokenInterceptor] ❌ Refresh failed: No token.');
              sessionService.logout();
              return throwError(() => error);
            }

            console.log('[TokenInterceptor] ✅ Refresh successful.');
            refreshTokenSubject.next(newAccessToken);
            isRefreshing = false;

            return next(
              req.clone({
                setHeaders: {
                  Authorization: `Bearer ${newAccessToken}`
                }
              })
            );
          }),
          catchError(refreshErr => {
            isRefreshing = false;
            sessionService.logout();
            return throwError(() => refreshErr);
          })
        );
      } else {
        // 3. Queue subsequent requests
        return refreshTokenSubject.pipe(
          filter(t => t !== null),
          take(1),
          switchMap(t =>
            next(
              req.clone({
                setHeaders: { Authorization: `Bearer ${t}` }
              })
            )
          )
        );
      }
    })
  );
};
