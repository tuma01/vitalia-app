import { Routes } from '@angular/router';

export const routes: Routes = [
    {
        path: 'civil-status',
        loadChildren: () => import('./civil-status/civil-status.routes').then(m => m.routes)
    },
    {
        path: 'gender',
        loadChildren: () => import('./gender/gender.routes').then(m => m.routes)
    },
    { path: '', redirectTo: 'civil-status', pathMatch: 'full' }
];
