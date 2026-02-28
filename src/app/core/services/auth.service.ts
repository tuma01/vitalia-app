import { Injectable, Inject, PLATFORM_ID, inject } from '@angular/core';
import { catchError, map, Observable, of, tap, throwError } from 'rxjs';
import { isPlatformBrowser } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { AuthenticationService } from '../../api/services/authentication.service';
import { AuthenticationResponse } from '../../api/models/authentication-response';
import { UserSummary } from '../../api/models/user-summary';
import { Router } from '@angular/router';
import { TokenService } from '../token/token.service';
import { SessionService } from './session.service';
import { RefreshTokenService } from '../token/refresh-token.service';

/**
 * Enhanced authentication service wrapper
 * Provides additional functionality on top of the generated AuthenticationService
 */
@Injectable({ providedIn: 'root' })
export class AuthService {

  constructor(
    private authApiService: AuthenticationService,
    private router: Router,
    // @Inject(PLATFORM_ID) private platformId: Object,
    private tokenService: TokenService,
    private sessionService: SessionService
  ) { }

  /**
   * Perform login and store tokens
   */
  login(email: string, password: string, tenantCode?: string): Observable<AuthenticationResponse> {
    // 🧪 [DEV MOCK] Bypassear el login real para el usuario de médico
    if (email === 'doctor-dev@test.com') {
      console.warn('🧪 [DEV MOCK] Validando credenciales para doctor-dev@test.com');

      // ✅ Validar contraseña (realismo)
      if (password === 'dev-password') {
        return this.mockLoginResponse('ROLE_DOCTOR', 'Dr. Gregory House (Mock)', tenantCode);
      } else {
        // ❌ Simular error de credenciales incorrectas (401)
        const errorResponse = new HttpErrorResponse({
          error: { message: 'Correo o contraseña incorrectos.' },
          status: 401,
          statusText: 'Unauthorized',
          url: 'mock-api/login'
        });
        return throwError(() => errorResponse);
      }
    }

    // Only include tenantCode if it's provided (not undefined)
    const body: any = {
      email,
      password
    };

    // Add tenantCode only if it's defined
    if (tenantCode !== undefined) {
      body.tenantCode = tenantCode;
    }

    console.log('[AuthService] Logging in with:', body);

    /* --- SIMULACIÓN DE ROLES (DESHABILITADA PARA USAR API REAL) ---
    // const normalizedEmail = email.trim().toLowerCase();

    // if (normalizedEmail === 'doctor@test.com') {
    //   return this.mockLoginResponse('ROLE_DOCTOR', 'Dr. Gregory House', tenantCode);
    // }
    // if (normalizedEmail === 'admin@test.com') {
    //   return this.mockLoginResponse('ROLE_SUPER_ADMIN', 'Super Admin User', tenantCode);
    // }
    // if (normalizedEmail === 'nurse@test.com') {
    //   console.log('[AuthService Debug] Intercepting Nurse Login');
    //   return this.mockLoginResponse('ROLE_NURSE', 'Enf. Florence Nightingale', tenantCode);
    // }
    // if (normalizedEmail === 'patient@test.com') {
    //   return this.mockLoginResponse('ROLE_PATIENT', 'Patient John Doe', tenantCode);
    // }
    // if (normalizedEmail === 'employee@test.com') {
    //   return this.mockLoginResponse('ROLE_EMPLOYEE', 'Employee Jane Smith', tenantCode);
    // }
    */

    return this.authApiService.login({
      body
    }).pipe(
      tap(response => {
        if (response.tokens && response.user) {
          this.sessionService.login({
            accessToken: response.tokens.accessToken!,
            refreshToken: response.tokens.refreshToken,
            user: response.user
          });
          console.log('[AuthService] Real login successful. Session stored.');
        } else {
          console.error('[AuthService] Login response missing tokens or user data', response);
        }
      })
    );
  }

  /**
   * Perform logout
   */
  logout(): void {
    const refreshToken = this.tokenService.refreshToken;

    if (refreshToken) {
      this.authApiService.logout({ refreshToken }).subscribe({
        next: () => this.performLogout(),
        complete: () => this.performLogout(),
        error: () => this.performLogout()
      });
    } else {
      this.performLogout();
    }
  }

  private performLogout(): void {
    // ✅ Delegar a SessionService
    this.sessionService.logout();
    this.router.navigate(['/auth/login']);
  }

  /**
   * Refresh access token
   * Delegates to specialized RefreshTokenService to ensure unified logic and session sync
   */
  refreshToken(): Observable<AuthenticationResponse> {
    const refreshTokenService = inject(RefreshTokenService);
    return refreshTokenService.refreshAccessToken().pipe(
      map(response => response as AuthenticationResponse)
    );
  }

  /**
   * Check if user is authenticated (with server validation)
   */
  isAuthenticated(): Observable<boolean> {
    // 1. Quick local validation (Standard for SPAs)
    // We trust the token is valid if it exists and hasn't expired according to its claims.
    // If the server rejects it later (401), the HttpInterceptor should handle the logout.
    const isValid = this.tokenService.isAccessTokenValid();
    return of(isValid);
  }

  /**
   * Quick local authentication check (no HTTP call)
   */
  isAuthenticatedSync(): boolean {
    return this.tokenService.isAccessTokenValid();
  }

  getCurrentUser(): UserSummary | null {
    return this.sessionService.getCurrentUser();
  }

  currentUserRole(): string {
    // 🏆 PRIORIDAD 1: Respetar el rol activo establecido en la sesión
    const active = this.sessionService.activeRole();
    if (active) {
      console.log(`[AuthService] currentUserRole using active: ${active}`);
      return active;
    }

    const user = this.getCurrentUser();
    if (!user) return '';

    // Fallback: primer rol disponible
    if (Array.isArray(user.roles) && user.roles.length > 0) {
      return user.roles[0];
    }

    return (user as any).role ?? '';
  }

  /**
   * Store user data in localStorage
   */
  private storeUser(user: UserSummary): void {
    localStorage.setItem('user', JSON.stringify(user));
  }

  /**
   * Clear user data
   */
  private clearUser(): void {
    localStorage.removeItem('user');
    localStorage.removeItem('tenantTheme');
  }

  /**
   * Get access token
   */
  getAccessToken(): string | null {
    return this.tokenService.accessToken;
  }

  /**
   * Get refresh token
   */
  getRefreshToken(): string | null {
    // if (!isPlatformBrowser(this.platformId)) return null;
    return this.tokenService.refreshToken;
  }

  /**
   * Navigate based on user roles
   * @param preferredRole Optional role selected by the user in the login UI
   */
  navigateBasedOnRole(preferredRole?: string): void {
    const user = this.sessionService.getCurrentUser();

    if (!user) {
      console.warn('[AuthService] No user found for navigation. Redirecting to login.');
      this.router.navigate(['/auth/login']);
      return;
    }

    // 🧪 [DEV MOCK] Bypassear ROLE_DOCTOR para pruebas si se selecciona y no se tiene
    if (preferredRole === 'ROLE_DOCTOR' && !this.sessionService.hasRole('ROLE_DOCTOR')) {
      console.warn('🧪 [DEV MOCK] Inyectando ROLE_DOCTOR e identidad de médico para fines de depuración');
      const currentRoles = Array.isArray(user.roles) ? [...user.roles] : [];
      if (!currentRoles.includes('ROLE_DOCTOR')) {
        currentRoles.push('ROLE_DOCTOR');
        this.sessionService.updateUser({
          roles: currentRoles,
          personName: 'Dr. Gregory House (Mock)' // 🏥 Nombre realista para el mock
        });
      }
    }

    // 🏆 Determine the final target role to use for navigation
    // 1. If preferredRole is passed and the user has it, use it.
    // 2. If not, try to use the persistent activeRole from session.
    // 3. If still nothing, use the discovery fallback.
    let targetRole: string | null = null;

    if (preferredRole && this.sessionService.hasRole(preferredRole)) {
      targetRole = preferredRole;
      console.log(`[AuthService] Using PREFERRED role: ${targetRole}`);
    } else {
      const activeRole = this.sessionService.getActiveRoleSync();
      if (activeRole && this.sessionService.hasRole(activeRole)) {
        targetRole = activeRole;
        console.log(`[AuthService] Using PERSISTENT activeRole: ${targetRole}`);
      }
    }

    if (targetRole) {
      this.sessionService.setActiveRole(targetRole);
      this.navToDashboard(targetRole);
      return;
    }

    // 🏆 Discovery Fallback (if no preferred or active role)
    console.log('[AuthService] No active role found. Falling back to discovery logic.');
    if (this.sessionService.hasRole('ROLE_SUPER_ADMIN')) {
      targetRole = 'ROLE_SUPER_ADMIN';
    } else if (this.sessionService.hasAnyRole(['ROLE_ADMIN', 'ROLE_TENANT_ADMIN'])) {
      targetRole = 'ROLE_ADMIN';
    } else if (this.sessionService.hasRole('ROLE_DOCTOR')) {
      targetRole = 'ROLE_DOCTOR';
    } else if (this.sessionService.hasRole('ROLE_NURSE')) {
      targetRole = 'ROLE_NURSE';
    } else if (this.sessionService.hasRole('ROLE_EMPLOYEE')) {
      targetRole = 'ROLE_EMPLOYEE';
    } else if (this.sessionService.hasRole('ROLE_PATIENT')) {
      targetRole = 'ROLE_PATIENT';
    }

    if (targetRole) {
      this.sessionService.setActiveRole(targetRole);
      this.navToDashboard(targetRole);
    } else {
      console.error('[AuthService] User has no recognized roles:', user.roles);
      this.router.navigate(['/login']);
    }
  }

  /**
   * Internal helper to perform actual navigation to the dashboard
   */
  private navToDashboard(role: string): void {
    console.log(`[AuthService] Navigating to dashboard for role: ${role}`);

    // 🛡️ REGLA: Redirección por dominio absoluto
    if (role === 'ROLE_SUPER_ADMIN') {
      this.router.navigate(['/platform/dashboard']);
    } else if (role === 'ROLE_ADMIN' || role === 'ROLE_TENANT_ADMIN') {
      this.router.navigate(['/admin/dashboard']);
    } else if (role === 'ROLE_DOCTOR') {
      this.router.navigate(['/doctor/dashboard']);
    } else if (role === 'ROLE_NURSE') {
      this.router.navigate(['/nurse/dashboard']);
    } else if (role === 'ROLE_EMPLOYEE') {
      this.router.navigate(['/employee/dashboard']);
    } else if (role === 'ROLE_PATIENT') {
      this.router.navigate(['/patient/dashboard']);
    } else {
      console.warn(`[AuthService] Unmapped dashboard for role: ${role}. Falling back to default.`);
      this.router.navigate(['/dashboard']);
    }
  }

  /**
   * Genera una respuesta simulada para pruebas
   */
  private mockLoginResponse(role: string, name: string, tenant?: string): Observable<AuthenticationResponse> {
    const mockUser: UserSummary = {
      id: 999,
      email: role === 'ROLE_DOCTOR' ? 'doctor@test.com' : 'nurse@test.com',
      personName: name,
      roles: [role],
      tenantCode: tenant || 'HOSPITAL_CENTRAL', // Default tenant
      personType: role.replace('ROLE_', '') as any
    };

    const mockResponse: AuthenticationResponse = {
      tokens: {
        accessToken: 'mock-access-token-' + Date.now(),
        refreshToken: 'mock-refresh-token-' + Date.now(),
        // expiresIn: 3600 // Eliminar si da error en tipos
      },
      user: mockUser
    };

    return of(mockResponse).pipe(
      tap(() => {
        this.sessionService.login({
          accessToken: mockResponse.tokens!.accessToken!,
          refreshToken: mockResponse.tokens!.refreshToken,
          user: mockUser
        });
        console.log(`[AuthService] MOCKED login successful for ${role}`);
      })
    );
  }
}
