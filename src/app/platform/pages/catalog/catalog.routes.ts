import { Routes } from '@angular/router';

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
                loadChildren: () => import('./medications/medications.routes').then(m => m.routes)
            },
            {
                path: 'procedures',
                loadChildren: () => import('./procedures/procedures.routes').then(m => m.routes)
            },
            {
                path: 'vaccines',
                loadChildren: () => import('./vaccines/vaccines.routes').then(m => m.routes)
            },
            {
                path: 'specialties',
                loadChildren: () => import('./specialties/specialties.routes').then(m => m.routes)
            },
            {
                path: 'allergies',
                loadChildren: () => import('./allergies/allergies.routes').then(m => m.routes)
            },
            {
                path: 'blood-types',
                loadChildren: () => import('./blood-types/blood-types.routes').then(m => m.routes)
            },
            {
                path: 'healthcare-providers',
                loadChildren: () => import('./healthcare-providers/healthcare-providers.routes').then(m => m.routes)
            },
            {
                path: 'identification-types',
                loadChildren: () => import('./identification-types/identification-types.routes').then(m => m.routes)
            },
            {
                path: 'kinships',
                loadChildren: () => import('./kinships/kinships.routes').then(m => m.routes)
            },
            {
                path: 'demographics',
                loadChildren: () => import('./demographics/demographics.routes').then(m => m.routes)
            }
        ]
    }
];
