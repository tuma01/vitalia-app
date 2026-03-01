import { Routes } from '@angular/router';
import { MedicalSpecialtyListComponent } from './medical-specialty-list.component';
import { MedicalSpecialtyAddComponent } from './medical-specialty-add.component';
import { MedicalSpecialtyEditComponent } from './medical-specialty-edit.component';

export const routes: Routes = [
    { path: 'list', component: MedicalSpecialtyListComponent },
    { path: 'add', component: MedicalSpecialtyAddComponent },
    { path: 'edit', component: MedicalSpecialtyEditComponent },
    { path: '', redirectTo: 'list', pathMatch: 'full' }
];
