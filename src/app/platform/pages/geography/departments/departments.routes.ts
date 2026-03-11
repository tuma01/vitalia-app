import { Routes } from '@angular/router';
import { DepartmentsListComponent } from './departments-list.component';
import { DepartmentsAddComponent } from './departments-add.component';
import { DepartmentsEditComponent } from './departments-edit.component';

export const routes: Routes = [
    { path: 'list', component: DepartmentsListComponent },
    { path: 'add', component: DepartmentsAddComponent },
    { path: 'edit', component: DepartmentsEditComponent },
    { path: '', redirectTo: 'list', pathMatch: 'full' }
];
