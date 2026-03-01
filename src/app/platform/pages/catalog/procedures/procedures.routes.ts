import { Routes } from '@angular/router';
import { ProceduresListComponent } from './procedures-list.component';
import { ProceduresAddComponent } from './procedures-add.component';
import { ProceduresEditComponent } from './procedures-edit.component';

export const routes: Routes = [
    { path: 'list', component: ProceduresListComponent },
    { path: 'add', component: ProceduresAddComponent },
    { path: 'edit', component: ProceduresEditComponent },
    { path: '', redirectTo: 'list', pathMatch: 'full' }
];
