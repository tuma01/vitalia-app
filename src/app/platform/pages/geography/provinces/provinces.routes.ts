import { Routes } from '@angular/router';
import { ProvincesListComponent } from './provinces-list.component';
import { ProvincesAddComponent } from './provinces-add.component';
import { ProvincesEditComponent } from './provinces-edit.component';

export const routes: Routes = [
    { path: 'list', component: ProvincesListComponent },
    { path: 'add', component: ProvincesAddComponent },
    { path: 'edit', component: ProvincesEditComponent },
    { path: '', redirectTo: 'list', pathMatch: 'full' }
];
