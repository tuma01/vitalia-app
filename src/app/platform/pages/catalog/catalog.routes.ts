import { Routes } from '@angular/router';
import { PlatformShellComponent } from '../../shared/components/platform-shell/platform-shell.component';

export const routes: Routes = [
    {
        path: '',
        children: [
            {
                path: 'icd10',
                loadChildren: () => import('./icd10/icd10.routes').then(m => m.routes)
            },
            {
                path: 'medications',
                component: PlatformShellComponent,
                data: { title: 'menu.medications', icon: 'medication' }
            },
            {
                path: 'procedures',
                component: PlatformShellComponent,
                data: { title: 'menu.procedures', icon: 'medical_services' }
            },
            {
                path: 'vaccines',
                component: PlatformShellComponent,
                data: { title: 'menu.vaccines', icon: 'vaccines' }
            },
            {
                path: 'specialties',
                component: PlatformShellComponent,
                data: { title: 'menu.specialties_allergies', icon: 'assignment_ind' }
            }
        ]
    }
];
