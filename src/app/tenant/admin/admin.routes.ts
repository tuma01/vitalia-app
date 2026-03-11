import { Routes } from '@angular/router';

export const ADMIN_ROUTES: Routes = [
    {
        path: 'dashboard',
        loadComponent: () => import('./dashboard/role-dashboard.component').then(m => m.RoleDashboardComponent),
        data: { title: 'dashboard.title' }
    },
    {
        path: 'admin',
        children: [
            {
                path: 'profile',
                loadChildren: () => import('./profile/hospital-profiles.routes').then(m => m.HOSPITAL_PROFILES_ROUTES)
            }
        ]
    },
    { path: '', redirectTo: 'dashboard', pathMatch: 'full' }
];
