import { Routes } from '@angular/router';
import { CivilStatusListComponent } from './civil-status-list.component';
import { CivilStatusAddComponent } from './civil-status-add.component';
import { CivilStatusEditComponent } from './civil-status-edit.component';

export const routes: Routes = [
    { path: 'list', component: CivilStatusListComponent },
    { path: 'add', component: CivilStatusAddComponent },
    { path: 'edit', component: CivilStatusEditComponent },
    { path: '', redirectTo: 'list', pathMatch: 'full' }
];
