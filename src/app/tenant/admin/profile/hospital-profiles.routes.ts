import { Routes } from '@angular/router';

export const HOSPITAL_PROFILES_ROUTES: Routes = [
    {
        path: '',
        loadComponent: () => import('./hospital-profiles-edit.component').then(m => m.HospitalProfilesEditComponent),
        data: { title: 'menu.tenant_admin.admin.profile.title' }
    }
];
