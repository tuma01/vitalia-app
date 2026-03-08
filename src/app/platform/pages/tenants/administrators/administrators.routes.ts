import { Routes } from '@angular/router';
import { AdministratorsListComponent } from './administrators-list.component';
import { AdministratorsAddComponent } from './administrators-add.component';
import { AdministratorsEditComponent } from './administrators-edit.component';

export const routes: Routes = [
    { path: 'list', component: AdministratorsListComponent },
    { path: 'add', component: AdministratorsAddComponent },
    { path: 'edit', component: AdministratorsEditComponent },
    { path: '', redirectTo: 'list', pathMatch: 'full' }
];
