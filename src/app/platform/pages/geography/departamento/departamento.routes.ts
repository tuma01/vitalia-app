import { Routes } from '@angular/router';
import { DepartamentosComponent } from './departamentos/departamentos.component';
// import { AddDepartamentoComponent } from './add-departemento/add-departemento.component';
import { EditDepartamentoComponent } from './edit-departamento/edit-departamento.component';

export const routes: Routes = [
    { path: 'departamentos', component: DepartamentosComponent },
    //{ path: 'addDepatamento', component: AddDepartementoComponent },
    { path: 'editDepartamento', component: EditDepartamentoComponent },
    { path: '', redirectTo: 'departamentos', pathMatch: 'full' }
];
