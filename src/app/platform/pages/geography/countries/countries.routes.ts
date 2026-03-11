import { Routes } from '@angular/router';
import { CountriesListComponent } from './countries-list.component';
import { CountriesAddComponent } from './countries-add.component';
import { CountriesEditComponent } from './countries-edit.component';

export const routes: Routes = [
    { path: 'list', component: CountriesListComponent },
    { path: 'add', component: CountriesAddComponent },
    { path: 'edit', component: CountriesEditComponent },
    { path: '', redirectTo: 'list', pathMatch: 'full' }
];
