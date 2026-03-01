import { Routes } from '@angular/router';
import { CivilStatusesListComponent } from './civil-statuses-list.component';
import { CivilStatusesAddComponent } from './civil-statuses-add.component';
import { CivilStatusesEditComponent } from './civil-statuses-edit.component';

export const routes: Routes = [
    { path: 'list', component: CivilStatusesListComponent },
    { path: 'add', component: CivilStatusesAddComponent },
    { path: 'edit', component: CivilStatusesEditComponent },
    { path: '', redirectTo: 'list', pathMatch: 'full' }
];
