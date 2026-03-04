import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { SessionService } from '../services/session.service';
import { AppContextService } from '../services/app-context.service';

/**
 * Platform Guard
 * 
 * Ensures ONLY ROLE_SUPER_ADMIN can access platform routes.
 * Activates 'platform' context.
 */
export const platformGuard: CanActivateFn = (route, state) => {
    const sessionService = inject(SessionService);
    const context = inject(AppContextService);
    const router = inject(Router);

    const user = sessionService.user();
    const isSuperAdmin = user?.roles?.includes('ROLE_SUPER_ADMIN');

    if (!isSuperAdmin) {
        console.warn('[PlatformGuard] Access denied: User is not SuperAdmin');
        // Redirigir al login de plataforma como fallback
        router.navigate(['/platform/login']);
        return false;
    }

    // 🔥 Activar contexto PLATFORM
    context.setPlatformContext();

    return true;
};
