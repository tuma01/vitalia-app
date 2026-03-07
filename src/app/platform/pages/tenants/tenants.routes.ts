import { Routes } from '@angular/router';
import { PlatformShellComponent } from '../../shared/components/platform-shell/platform-shell.component';

export const routes: Routes = [
    {
        path: '',
        children: [
            {
                path: 'organizations',
                loadChildren: () => import('./organizations/organizations.routes').then(m => m.ORGANIZATIONS_ROUTES)
            },
            {
                path: 'list',
                component: PlatformShellComponent,
                data: { title: 'menu.tenant_governance.organizations.title', icon: 'domain' }
            },
            {
                path: 'administrators',
                loadChildren: () => import('./onboardings/onboardings.routes').then(m => m.routes)
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
