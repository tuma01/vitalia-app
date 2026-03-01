import { Routes } from '@angular/router';
import { GendersListComponent } from './genders-list.component';
import { GendersAddComponent } from './genders-add.component';
import { GendersEditComponent } from './genders-edit.component';

export const routes: Routes = [
    { path: 'list', component: GendersListComponent },
    { path: 'add', component: GendersAddComponent },
    { path: 'edit', component: GendersEditComponent },
    { path: '', redirectTo: 'list', pathMatch: 'full' }
];
