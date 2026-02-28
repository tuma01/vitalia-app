import { Routes } from '@angular/router';
import { PlatformShellComponent } from '../../shared/components/platform-shell/platform-shell.component';

export const routes: Routes = [
    {
        path: '',
        children: [
            {
                path: 'audit',
                component: PlatformShellComponent,
                data: { title: 'menu.audit_logs', icon: 'policy' }
            },
            {
                path: 'security',
                component: PlatformShellComponent,
                data: { title: 'menu.security_policies', icon: 'admin_panel_settings' }
            },
            {
                path: 'iam',
                component: PlatformShellComponent,
                data: { title: 'menu.iam_superadmins', icon: 'manage_accounts' }
            },
            {
                path: 'consent',
                component: PlatformShellComponent,
                data: { title: 'menu.consent_registry', icon: 'gavel' }
            }
        ]
    }
];
