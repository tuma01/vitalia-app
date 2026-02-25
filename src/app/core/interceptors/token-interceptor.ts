import {
  HttpInterceptorFn,
  HttpRequest,
  HttpHandlerFn,
  HttpErrorResponse
} from '@angular/common/http';

import { inject } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { BehaviorSubject, catchError, filter, switchMap, take, throwError } from 'rxjs';
import { TokenService } from '../token/token.service';

let isRefreshing = false;
const refreshTokenSubject = new BehaviorSubject<string | null>(null);

export const tokenInterceptor: HttpInterceptorFn = (req, next) => {
  const tokenService = inject(TokenService);
  const authService = inject(AuthService);

  // 🛡️ [PROFESSIONAL] Skip auth endpoints to prevent "expired token" loops
  // Refresh and Login should NEVER carry an Authorization header from previous sessions
  const isAuthRequest = req.url.includes('/auth/');

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
      // If it's not a 401 or it IS an auth request (which failed on its own merits), throw immediately
      if (error.status !== 401 || isAuthRequest) {
        return throwError(() => error);
      }

      const refreshToken = tokenService.refreshToken;
      if (!refreshToken || !tokenService.isRefreshTokenValid()) {
        console.warn('[TokenInterceptor] No valid refresh token found. Logging out.');
        tokenService.clearTokens();
        return throwError(() => error);
      }

      if (!isRefreshing) {
        isRefreshing = true;
        refreshTokenSubject.next(null);

        console.log('[TokenInterceptor] 🔄 Access token expired. Attempting refresh...');

        return authService.refreshToken().pipe(
          switchMap((response: any) => {
            const newAccessToken = response?.tokens?.accessToken;

            if (!newAccessToken) {
              console.error('[TokenInterceptor] ❌ Refresh failed: No new access token received.');
              tokenService.clearTokens();
              return throwError(() => error);
            }

            console.log('[TokenInterceptor] ✅ Refresh successful. Retrying original request.');
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
            console.error('[TokenInterceptor] ❌ Refresh failed critical error:', refreshErr);
            tokenService.clearTokens();
            isRefreshing = false;
            return throwError(() => refreshErr);
          })
        );
      } else {
        // [PROFESSIONAL] Queue concurrent requests while refresh is in progress
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
