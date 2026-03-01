import { Routes } from '@angular/router';
import { VaccineListComponent } from './vaccine-list.component';
import { VaccineAddComponent } from './vaccine-add.component';
import { VaccineEditComponent } from './vaccine-edit.component';

export const routes: Routes = [
    { path: 'list', component: VaccineListComponent },
    { path: 'add', component: VaccineAddComponent },
    { path: 'edit', component: VaccineEditComponent },
    { path: '', redirectTo: 'list', pathMatch: 'full' }
];
