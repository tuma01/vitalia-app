import { Routes } from '@angular/router';
import { OrganizationsListComponent } from './organizations-list/organizations-list.component';
import { OrganizationsAddComponent } from './organizations-add/organizations-add.component';
import { OrganizationsEditComponent } from './organizations-edit/organizations-edit.component';

export const ORGANIZATIONS_ROUTES: Routes = [
    { path: 'list', component: OrganizationsListComponent },
    { path: 'add', component: OrganizationsAddComponent },
    { path: 'edit', component: OrganizationsEditComponent },
    { path: '', redirectTo: 'list', pathMatch: 'full' }
];
