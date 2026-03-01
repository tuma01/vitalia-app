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
                loadChildren: () => import('./medication/medication.routes').then(m => m.routes)
            },
            {
                path: 'procedures',
                loadChildren: () => import('./medical-procedure/medical-procedure.routes').then(m => m.routes)
            },
            {
                path: 'vaccines',
                loadChildren: () => import('./vaccine/vaccine.routes').then(m => m.routes)
            },
            {
                path: 'specialty',
                loadChildren: () => import('./medical-specialty/medical-specialty.routes').then(m => m.routes)
            },
            {
                path: 'allergy',
                loadChildren: () => import('./allergy/allergy.routes').then(m => m.routes)
            },
            {
                path: 'blood-type',
                loadChildren: () => import('./blood-type/blood-type.routes').then(m => m.routes)
            },
            {
                path: 'healthcare-provider',
                loadChildren: () => import('./healthcare-provider/healthcare-provider.routes').then(m => m.routes)
            },
            {
                path: 'identification-type',
                loadChildren: () => import('./identification-type/identification-type.routes').then(m => m.routes)
            },
            {
                path: 'kinship',
                loadChildren: () => import('./kinship/kinship.routes').then(m => m.routes)
            },
            {
                path: 'demographics',
                loadChildren: () => import('./demographic/demographic.routes').then(m => m.routes)
            }
        ]
    }
];
