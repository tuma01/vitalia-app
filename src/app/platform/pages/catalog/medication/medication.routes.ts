import { Routes } from '@angular/router';
import { MedicationListComponent } from './medication-list.component';
import { MedicationAddComponent } from './medication-add.component';
import { MedicationEditComponent } from './medication-edit.component';

export const routes: Routes = [
    { path: 'list', component: MedicationListComponent },
    { path: 'add', component: MedicationAddComponent },
    { path: 'edit', component: MedicationEditComponent },
    { path: '', redirectTo: 'list', pathMatch: 'full' }
];
