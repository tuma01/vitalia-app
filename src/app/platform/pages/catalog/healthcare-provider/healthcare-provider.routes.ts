import { Routes } from '@angular/router';
import { HealthcareProviderListComponent } from './healthcare-provider-list.component';
import { HealthcareProviderAddComponent } from './healthcare-provider-add.component';
import { HealthcareProviderEditComponent } from './healthcare-provider-edit.component';

export const routes: Routes = [
    { path: 'list', component: HealthcareProviderListComponent },
    { path: 'add', component: HealthcareProviderAddComponent },
    { path: 'edit', component: HealthcareProviderEditComponent },
    { path: '', redirectTo: 'list', pathMatch: 'full' }
];
