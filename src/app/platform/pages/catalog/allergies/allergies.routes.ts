import { Routes } from '@angular/router';
import { AllergiesListComponent } from './allergies-list.component';
import { AllergiesAddComponent } from './allergies-add.component';
import { AllergiesEditComponent } from './allergies-edit.component';

export const routes: Routes = [
    { path: 'list', component: AllergiesListComponent },
    { path: 'add', component: AllergiesAddComponent },
    { path: 'edit', component: AllergiesEditComponent },
    { path: '', redirectTo: 'list', pathMatch: 'full' }
];
