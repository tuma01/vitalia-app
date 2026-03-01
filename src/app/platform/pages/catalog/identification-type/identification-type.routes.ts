import { Routes } from '@angular/router';
import { IdentificationTypeListComponent } from './identification-type-list.component';
import { IdentificationTypeAddComponent } from './identification-type-add.component';
import { IdentificationTypeEditComponent } from './identification-type-edit.component';

export const routes: Routes = [
    { path: 'list', component: IdentificationTypeListComponent },
    { path: 'add', component: IdentificationTypeAddComponent },
    { path: 'edit', component: IdentificationTypeEditComponent },
    { path: '', redirectTo: 'list', pathMatch: 'full' }
];
