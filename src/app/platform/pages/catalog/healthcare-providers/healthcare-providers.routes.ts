import { Routes } from '@angular/router';
import { HealthcareProvidersListComponent } from './healthcare-providers-list.component';
import { HealthcareProvidersAddComponent } from './healthcare-providers-add.component';
import { HealthcareProvidersEditComponent } from './healthcare-providers-edit.component';

export const routes: Routes = [
    { path: 'list', component: HealthcareProvidersListComponent },
    { path: 'add', component: HealthcareProvidersAddComponent },
    { path: 'edit', component: HealthcareProvidersEditComponent },
    { path: '', redirectTo: 'list', pathMatch: 'full' }
];
