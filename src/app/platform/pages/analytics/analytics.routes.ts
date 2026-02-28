import { Routes } from '@angular/router';
import { PlatformShellComponent } from '../../shared/components/platform-shell/platform-shell.component';

export const routes: Routes = [
    {
        path: '',
        children: [
            {
                path: 'stats',
                component: PlatformShellComponent,
                data: { title: 'menu.operational_stats', icon: 'insert_chart' }
            }
        ]
    }
];
