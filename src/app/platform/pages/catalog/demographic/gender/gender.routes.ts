import { Routes } from '@angular/router';
import { GenderListComponent } from './gender-list.component';
import { GenderAddComponent } from './gender-add.component';
import { GenderEditComponent } from './gender-edit.component';

export const routes: Routes = [
    { path: 'list', component: GenderListComponent },
    { path: 'add', component: GenderAddComponent },
    { path: 'edit', component: GenderEditComponent },
    { path: '', redirectTo: 'list', pathMatch: 'full' }
];
