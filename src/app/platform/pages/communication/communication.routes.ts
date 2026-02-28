import { Routes } from '@angular/router';
import { PlatformShellComponent } from '../../shared/components/platform-shell/platform-shell.component';

export const routes: Routes = [
    {
        path: '',
        children: [
            {
                path: 'broadcast',
                component: PlatformShellComponent,
                data: { title: 'menu.broadcast_center', icon: 'campaign' }
            }
        ]
    }
];
