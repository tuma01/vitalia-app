import { Injectable } from '@angular/core';
import { Observable, of, Subject, take, switchMap, tap, catchError } from 'rxjs';
import { ThemeDto } from '../../api/models/theme-dto';
import { AppContextService } from '../services/app-context.service';
import { ContextStorageService } from '../services/context-storage.service';
import { ThemeApiService } from './theme-api.service';
import { PLATFORM_DEFAULT_THEME, TENANT_DEFAULT_THEME } from './theme.service';

/**
 * 💾 Cached Theme Structure (with versioning)
 */
export interface CachedTheme {
    version?: number;
    theme: ThemeDto;
    cachedAt: number;
}

/**
 * 🎯 Settings Resolver
 * 
 * Orchestrates theme sources with proper priority:
 * 1. localStorage (context-scoped) → instant UI
 * 2. Backend API → source of truth
 * 3. Default theme → fallback
 * 
 * Features:
 * - Cache-first for instant UI
 * - Background refresh for freshness
 * - Version-based cache invalidation
 * - Context-safe backend fetching
 */
@Injectable({ providedIn: 'root' })
export class SettingsResolver {
    private themeUpdateSubject = new Subject<ThemeDto>();

    /**
     * Observable that emits when theme is updated from backend
     * (e.g., admin changed branding while user is logged in)
     */
    public readonly themeUpdates$ = this.themeUpdateSubject.asObservable();

    constructor(
        private storage: ContextStorageService,
        private themeApi: ThemeApiService,
        private appContext: AppContextService
    ) { }

    /**
     * 🔄 Resolve initial theme with version-based cache invalidation
     * 
     * Flow:
     * 1. Check localStorage → if found, return immediately + refresh in background
     * 2. If not found → fetch from backend → cache → return
     * 3. If backend fails → use default → cache → return
     */
    resolveInitialTheme(): Observable<ThemeDto> {
        const savedJson = this.storage.getItem('theme-config');

        if (savedJson) {
            try {
                const cached: CachedTheme = JSON.parse(savedJson);
                console.log('[SettingsResolver] ✅ Theme from cache (v' + (cached.version ?? 0) + ')');

                // 🔥 Return cached immediately (instant UI)
                const cachedTheme$ = of(cached.theme);

                // 🔥 Refresh in background with version check
                this.refreshInBackground(cached.version ?? 0);

                return cachedTheme$;
            } catch (error) {
                console.warn('[SettingsResolver] Invalid cached theme, fetching from backend');
                // Invalid JSON, fall through to backend
            }
        }

        // No cache → fetch from backend
        console.log('[SettingsResolver] 🌐 No cache, fetching from backend...');
        return this.fetchAndCacheTheme();
    }

    /**
     * 🔄 Background refresh with version comparison
     * 
     * Fetches theme from backend and compares version.
     * If version changed → updates cache and notifies ThemeService.
     */
    private refreshInBackground(currentVersion: number): void {
        this.fetchFromBackend().subscribe({
            next: serverTheme => {
                if (serverTheme.version !== currentVersion) {
                    console.log('[SettingsResolver] 🔄 Theme version changed:', currentVersion, '→', serverTheme.version);

                    // Update cache with new version
                    const cached: CachedTheme = {
                        version: serverTheme.version,
                        theme: serverTheme,
                        cachedAt: Date.now()
                    };
                    this.storage.setItem('theme-config', JSON.stringify(cached));

                    // Notify ThemeService to apply new theme
                    this.themeUpdateSubject.next(serverTheme);
                } else {
                    console.log('[SettingsResolver] ✅ Theme version unchanged (v' + currentVersion + ')');
                }
            },
            error: err => console.warn('[SettingsResolver] Background refresh failed:', err)
        });
    }

    /**
     * 💾 Fetch and cache theme (first load)
     */
    private fetchAndCacheTheme(): Observable<ThemeDto> {
        return this.fetchFromBackend().pipe(
            tap(theme => {
                const cached: CachedTheme = {
                    version: theme.version ?? 0,
                    theme: theme,
                    cachedAt: Date.now()
                };
                this.storage.setItem('theme-config', JSON.stringify(cached));
                console.log('[SettingsResolver] 💾 Cached theme v' + cached.version);
            }),
            catchError(error => {
                console.warn('[SettingsResolver] ⚠️ Backend failed, using default theme');
                const defaultTheme = this.getDefaultTheme();

                // Cache default with special version
                const cached: CachedTheme = {
                    version: -1,
                    theme: defaultTheme,
                    cachedAt: Date.now()
                };
                this.storage.setItem('theme-config', JSON.stringify(cached));

                return of(defaultTheme);
            })
        );
    }

    /**
     * 🌐 Fetch from backend (context-safe)
     *
     * For 'platform' context: returns PLATFORM_DEFAULT_THEME directly (no backend endpoint).
     * For 'app' context: fetches from /themes/tenant/{tenantCode}.
     */
    private fetchFromBackend(): Observable<ThemeDto> {
        return this.appContext.contextChanges$.pipe(
            take(1), // Get current context reactively
            switchMap(context => {
                if (context === 'platform') {
                    // 🏢 No backend theme endpoint for platform — use default directly
                    console.log('[SettingsResolver] Platform context → using PLATFORM_DEFAULT_THEME (no API call)');
                    return of(PLATFORM_DEFAULT_THEME);
                } else {
                    const tenantInfo = this.appContext.tenant();
                    const tenantCode = tenantInfo?.code || 'default';
                    console.log('[SettingsResolver] App context → fetching theme for tenant:', tenantCode);
                    return this.themeApi.getThemeForTenant(tenantCode);
                }
            })
        );
    }

    /**
     * 🎨 Get default theme based on current context
     */
    private getDefaultTheme(): ThemeDto {
        const context = this.appContext.getContextSnapshot();
        return context === 'platform' ? PLATFORM_DEFAULT_THEME : TENANT_DEFAULT_THEME;
    }
}
