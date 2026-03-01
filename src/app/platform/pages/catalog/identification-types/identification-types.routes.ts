import { Routes } from '@angular/router';
import { IdentificationTypesListComponent } from './identification-types-list.component';
import { IdentificationTypesAddComponent } from './identification-types-add.component';
import { IdentificationTypesEditComponent } from './identification-types-edit.component';

export const routes: Routes = [
    { path: 'list', component: IdentificationTypesListComponent },
    { path: 'add', component: IdentificationTypesAddComponent },
    { path: 'edit', component: IdentificationTypesEditComponent },
    { path: '', redirectTo: 'list', pathMatch: 'full' }
];
