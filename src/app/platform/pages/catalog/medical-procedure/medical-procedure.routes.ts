import { Routes } from '@angular/router';
import { MedicalProcedureListComponent } from './medical-procedure-list.component';
import { MedicalProcedureAddComponent } from './medical-procedure-add.component';
import { MedicalProcedureEditComponent } from './medical-procedure-edit.component';

export const routes: Routes = [
    { path: 'list', component: MedicalProcedureListComponent },
    { path: 'add', component: MedicalProcedureAddComponent },
    { path: 'edit', component: MedicalProcedureEditComponent },
    { path: '', redirectTo: 'list', pathMatch: 'full' }
];
