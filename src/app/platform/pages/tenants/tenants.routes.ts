import { Routes } from '@angular/router';
import { PlatformShellComponent } from '../../shared/components/platform-shell/platform-shell.component';

export const routes: Routes = [
    {
        path: '',
        children: [
            {
                path: 'list',
                component: PlatformShellComponent,
                data: { title: 'menu.hospitals_clinics', icon: 'domain' }
            },
            {
                path: 'onboarding',
                component: PlatformShellComponent,
                data: { title: 'menu.tenant_onboarding', icon: 'add_business' }
            },
            {
                path: 'billing',
                component: PlatformShellComponent,
                data: { title: 'menu.billing_licensing', icon: 'payments' }
            },
            {
                path: 'config',
                component: PlatformShellComponent,
                data: { title: 'menu.tenant_config', icon: 'settings_applications' }
            }
        ]
    }
];
