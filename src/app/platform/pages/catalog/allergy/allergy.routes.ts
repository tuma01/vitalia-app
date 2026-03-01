import { Routes } from '@angular/router';
import { AllergyListComponent } from './allergy-list.component';
import { AllergyAddComponent } from './allergy-add.component';
import { AllergyEditComponent } from './allergy-edit.component';

export const routes: Routes = [
    { path: 'list', component: AllergyListComponent },
    { path: 'add', component: AllergyAddComponent },
    { path: 'edit', component: AllergyEditComponent },
    { path: '', redirectTo: 'list', pathMatch: 'full' }
];
