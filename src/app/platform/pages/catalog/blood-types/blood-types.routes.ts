import { Routes } from '@angular/router';
import { BloodTypesListComponent } from './blood-types-list.component';
import { BloodTypesAddComponent } from './blood-types-add.component';
import { BloodTypesEditComponent } from './blood-types-edit.component';

export const routes: Routes = [
    { path: 'list', component: BloodTypesListComponent },
    { path: 'add', component: BloodTypesAddComponent },
    { path: 'edit', component: BloodTypesEditComponent },
    { path: '', redirectTo: 'list', pathMatch: 'full' }
];
