import { Routes } from '@angular/router';
import { TenantConfigsListComponent } from './tenant-configs-list.component';
import { TenantConfigsAddComponent } from './tenant-configs-add.component';
import { TenantConfigsEditComponent } from './tenant-configs-edit.component';

export const routes: Routes = [
    { path: 'list', component: TenantConfigsListComponent },
    { path: 'add', component: TenantConfigsAddComponent },
    { path: 'edit', component: TenantConfigsEditComponent },
    { path: '', redirectTo: 'list', pathMatch: 'full' }
];
