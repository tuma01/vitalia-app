import { Routes } from '@angular/router';
import { BloodTypeListComponent } from './blood-type-list.component';
import { BloodTypeAddComponent } from './blood-type-add.component';
import { BloodTypeEditComponent } from './blood-type-edit.component';

export const routes: Routes = [
    { path: 'list', component: BloodTypeListComponent },
    { path: 'add', component: BloodTypeAddComponent },
    { path: 'edit', component: BloodTypeEditComponent },
    { path: '', redirectTo: 'list', pathMatch: 'full' }
];
