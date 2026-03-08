import { Injectable, inject, signal, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser, DOCUMENT } from '@angular/common';
import { ThemeService } from '../theme/theme.service';
import { ContextStorageService } from './context-storage.service';
import { AppContextService } from './app-context.service';
import { merge, debounceTime, skip } from 'rxjs';

export type LayoutMode = 'light' | 'dark';
export type SidenavColor = 'light' | 'dark';
export type Density = 'compact' | 'default' | 'expanded';

export interface ThemeSettings {
    layoutMode?: LayoutMode;
    sidenavColor?: SidenavColor;
    density?: Density;
    headerColor?: string;
}

/**
 * SettingsService - Context-Aware UI Settings
 * 
 * 🔥 CRITICAL: Uses ContextStorageService for all persistence
 * Settings are automatically scoped by context (platform vs tenant)
 */
@Injectable({
    providedIn: 'root'
})
export class SettingsService {
    private themeService = inject(ThemeService);
    private platformId = inject(PLATFORM_ID);
    private document = inject(DOCUMENT);
    private storage = inject(ContextStorageService); // 🔥 Use context-scoped storage

    // Signals para el estado visual de la app
    layoutMode = signal<LayoutMode>('light');
    sidenavColor = signal<SidenavColor>('light');
    density = signal<Density>('default');
    headerColor = signal<string>('#ffffff');

    private appContext = inject(AppContextService);

    constructor() {
        this.loadSettings();

        // 🔄 Subscribe to theme application events to synchronize panel signals
        this.themeService.themeApplied$.subscribe(theme => {
            if (theme) {
                this.extractAndApplyThemeDefaults(theme);
            }
        });

        // 🔄 Subscribe to context/tenant changes to reload settings from the correct storage scope
        merge(
            this.appContext.contextChanges$,
            this.appContext.tenantChanges$
        ).pipe(
            debounceTime(50)
        ).subscribe(() => {
            console.log('[SettingsService] 🔄 Refreshing settings due to context/tenant change');
            this.loadSettings();
        });
    }

    setHeaderColor(color: string): void {
        this.headerColor.set(color);
        this.storage.setItem('header-color', color); // 🔥 Context-scoped

        // Determinar el color de marca (Indigo si el header es blanco/default)
        const brandColor = (color.toLowerCase() === '#ffffff') ? '#3f51b5' : color;

        // ✅ Persistir Override de Marca si no es blanco
        if (color.toLowerCase() !== '#ffffff') {
            this.storage.setItem('brand-primary', brandColor); // 🔥 Context-scoped
            this.storage.setItem('brand-accent', brandColor);  // 🔥 Context-scoped
        } else {
            // Si es blanco, eliminamos overrides para permitir que mande el branding del hospital
            this.storage.removeItem('brand-primary');
            this.storage.removeItem('brand-accent');
        }

        this.applyHeaderStyles(color);

        // ✅ Sincronización Global
        const current = this.themeService.getCurrentTheme();
        if (current) {
            this.themeService.applyTheme({
                ...current,
                primaryColor: brandColor,
                accentColor: brandColor
            });
        }
    }

    private applyHeaderStyles(color: string): void {
        if (!isPlatformBrowser(this.platformId)) return;
        const root = this.document.documentElement;
        const body = this.document.body;
        root.style.setProperty('--app-header-bg', color);

        // Calcular contraste (blanco o negro)
        const contrastColor = this.getContrastYIQ(color);
        root.style.setProperty('--app-header-text', contrastColor);

        // ✅ Aplicar clases estructurales al body para CSS robusto
        if (contrastColor === '#ffffff') {
            body.classList.add('header-dark'); // Fondo oscuro -> Header "Dark mode" (texto blanco)
            body.classList.remove('header-light');
        } else {
            body.classList.add('header-light'); // Fondo claro -> Header "Light mode" (texto negro)
            body.classList.remove('header-dark');
        }
    }

    private getContrastYIQ(hexcolor: string): string {
        hexcolor = hexcolor.replace("#", "");
        if (hexcolor.length === 3) {
            hexcolor = hexcolor.split('').map(hex => hex + hex).join('');
        }
        const r = parseInt(hexcolor.substr(0, 2), 16);
        const g = parseInt(hexcolor.substr(2, 2), 16);
        const b = parseInt(hexcolor.substr(4, 2), 16);
        const yiq = ((r * 299) + (g * 587) + (b * 114)) / 1000;
        return (yiq >= 170) ? 'rgba(0,0,0,0.87)' : '#ffffff';
    }

    setLayoutMode(mode: LayoutMode): void {
        this.layoutMode.set(mode);
        this.storage.setItem('layout', mode); // 🔥 Context-scoped

        // ✅ Sincronizar Sidebar Color con el Layout
        this.setSidenavColor(mode);

        // ✅ Sincronizar Color de Header automáticamente con el Layout
        const autoColor = mode === 'dark' ? '#111421' : '#ffffff';
        this.setHeaderColor(autoColor);

        // Sincronizar con el ThemeService
        const current = this.themeService.getCurrentTheme();
        if (current) {
            this.themeService.applyTheme({
                ...current,
                themeMode: mode.toUpperCase() as 'LIGHT' | 'DARK'
            });
        }
    }

    setSidenavColor(color: SidenavColor): void {
        this.sidenavColor.set(color);
        this.storage.setItem('sidenav', color); // 🔥 Context-scoped

        if (!isPlatformBrowser(this.platformId)) return;
        const body = this.document.body;
        // La clase sidebar-* es independiente de theme-*, permitiendo sidebar oscuro en theme claro
        body.classList.remove('sidebar-light', 'sidebar-dark');
        body.classList.add(`sidebar-${color}`);
    }

    setDensity(density: Density): void {
        if (!isPlatformBrowser(this.platformId)) return;

        this.density.set(density);
        this.storage.setItem('density', density); // 🔥 Context-scoped

        // Aplicar clase al body
        const body = this.document.body;
        body.classList.remove('density-compact', 'density-default', 'density-expanded');
        body.classList.add(`density-${density}`);
    }

    private loadSettings(): void {
        if (!isPlatformBrowser(this.platformId)) return;

        const layout = this.storage.getItem('layout') as LayoutMode; // 🔥 Context-scoped
        const sidenav = this.storage.getItem('sidenav') as SidenavColor; // 🔥 Context-scoped
        const density = this.storage.getItem('density') as Density; // 🔥 Context-scoped
        const header = this.storage.getItem('header-color'); // 🔥 Context-scoped

        // 🛡️ RESET TO ABSOLUTE DEFAULTS BEFORE LOADING
        // This prevents leakage from previous tenant if current tenant has no settings
        this.layoutMode.set('light');
        this.sidenavColor.set('light');
        this.density.set('default');
        this.headerColor.set('#ffffff');

        // 🛡️ Apply values from storage if they exist
        if (layout) this.setLayoutMode(layout);
        if (sidenav) this.setSidenavColor(sidenav);
        if (density) this.setDensity(density);

        if (header) {
            this.setHeaderColor(header);
        } else {
            // Apply default header for current mode (resetting to white/dark)
            const autoColor = this.layoutMode() === 'dark' ? '#111421' : '#ffffff';
            this.applyHeaderStyles(autoColor);
        }
    }

    /**
     * 🎯 Sincroniza las señales del Settings Panel con los valores sugeridos por el tema.
     * Solo aplica el cambio si el usuario NO tiene una preferencia manual guardada.
     */
    applyThemeDefaults(defaults: ThemeSettings): void {
        const userLayout = this.storage.getItem('layout');
        const userSidenav = this.storage.getItem('sidenav');
        const userDensity = this.storage.getItem('density');
        const userHeader = this.storage.getItem('header-color');

        if (!userLayout && defaults.layoutMode) {
            this.layoutMode.set(defaults.layoutMode);
        }
        if (!userSidenav && defaults.sidenavColor) {
            this.sidenavColor.set(defaults.sidenavColor);
        }
        if (!userDensity && defaults.density) {
            this.density.set(defaults.density);
        }
        if (!userHeader && defaults.headerColor) {
            this.headerColor.set(defaults.headerColor);
            this.applyHeaderStyles(defaults.headerColor);
        }
    }

    private extractAndApplyThemeDefaults(theme: any): void {
        let themeDefaults: ThemeSettings = {};
        if (theme.propertiesJson) {
            try {
                const props = JSON.parse(theme.propertiesJson);
                themeDefaults = {
                    layoutMode: props.layoutMode || (theme.themeMode?.toLowerCase() as any),
                    sidenavColor: props.sidenavColor,
                    density: props.density,
                    headerColor: props.headerColor || (theme.themeMode === 'DARK' ? '#111421' : '#ffffff')
                };
            } catch (e) {
                // Fallback simplest defaults from themeMode
                const mode = theme.themeMode === 'DARK' ? 'dark' : 'light';
                themeDefaults = {
                    layoutMode: mode as any,
                    headerColor: mode === 'dark' ? '#111421' : '#ffffff'
                };
            }
        } else {
            const mode = theme.themeMode === 'DARK' ? 'dark' : 'light';
            themeDefaults = {
                layoutMode: mode as any,
                headerColor: mode === 'dark' ? '#111421' : '#ffffff'
            };
        }
        this.applyThemeDefaults(themeDefaults);
    }

    /**
     * 🧹 Resetea todas las personalizaciones del usuario y vuelve a los defaults del tema.
     */
    resetToThemeDefaults(): void {
        this.storage.removeItem('layout');
        this.storage.removeItem('sidenav');
        this.storage.removeItem('density');
        this.storage.removeItem('header-color');
        this.storage.removeItem('brand-primary');
        this.storage.removeItem('brand-accent');

        // Re-cargar settings (volverá a los absolutos primero, y luego el themeService notificará los defaults del tema)
        this.loadSettings();

        // Forzar reaplicación del tema actual para disparar el evento de synchronization
        const current = this.themeService.getCurrentTheme();
        if (current) {
            this.themeService.applyTheme(current);
        }
    }
}
