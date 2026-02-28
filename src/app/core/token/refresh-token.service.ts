import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, throwError } from 'rxjs';
import { catchError, tap, finalize } from 'rxjs/operators';
import { TokenService } from './token.service';
import { SessionService } from '../services/session.service';

@Injectable({
  providedIn: 'root'
})
export class RefreshTokenService {
  private isRefreshing = false;
  private refreshSubject = new BehaviorSubject<any>(null);

  private http = inject(HttpClient);
  private tokenService = inject(TokenService);
  private sessionService = inject(SessionService);

  constructor() { }

  refreshAccessToken(): Observable<any> {
    const refreshToken = this.tokenService.refreshToken;

    if (!refreshToken) {
      return throwError(() => new Error('No refresh token available locally'));
    }

    if (this.isRefreshing) {
      return this.refreshSubject.asObservable();
    }

    this.isRefreshing = true;

    return this.http.post<any>(`${this.tokenService.getApiRoot()}/auth/refresh`, null, {
      params: { refreshToken: refreshToken }
    }).pipe(
      tap((response: any) => {
        const accessToken = response.tokens?.accessToken || response.accessToken;
        const newRefreshToken = response.tokens?.refreshToken || response.refreshToken || refreshToken;

        if (accessToken) {
          this.tokenService.setTokens(accessToken, newRefreshToken);
          this.sessionService.handleTokenRefresh();
          console.log('[RefreshTokenService] 🔄 Token successfully refreshed');
          this.refreshSubject.next(response);
        } else {
          console.warn('[RefreshTokenService] Refresh response missing accessToken');
        }
      }),
      finalize(() => {
        this.isRefreshing = false;
      }),
      catchError(error => {
        console.error('[RefreshTokenService] Refresh failed:', error);
        return throwError(() => error);
      })
    );
  }

  shouldRefreshToken(): boolean {
    return this.tokenService.isTokenAboutToExpire() &&
      this.tokenService.isRefreshTokenValid();
  }
}
