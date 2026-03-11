import { Routes } from '@angular/router';
import { MunicipalitiesListComponent } from './municipalities-list.component';
import { MunicipalitiesAddComponent } from './municipalities-add.component';
import { MunicipalitiesEditComponent } from './municipalities-edit.component';

export const routes: Routes = [
    { path: 'list', component: MunicipalitiesListComponent },
    { path: 'add', component: MunicipalitiesAddComponent },
    { path: 'edit', component: MunicipalitiesEditComponent },
    { path: '', redirectTo: 'list', pathMatch: 'full' }
];
