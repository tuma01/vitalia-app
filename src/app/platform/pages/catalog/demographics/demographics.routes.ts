import { Routes } from '@angular/router';

export const routes: Routes = [
    {
        path: 'civil-statuses',
        loadChildren: () => import('./civil-statuses/civil-statuses.routes').then(m => m.routes)
    },
    {
        path: 'genders',
        loadChildren: () => import('./genders/genders.routes').then(m => m.routes)
    },
    { path: '', redirectTo: 'civil-statuses', pathMatch: 'full' }
];
