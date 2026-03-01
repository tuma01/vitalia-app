import { Routes } from '@angular/router';
import { MedicationsListComponent } from './medications-list.component';
import { MedicationsAddComponent } from './medications-add.component';
import { MedicationsEditComponent } from './medications-edit.component';

export const routes: Routes = [
    { path: 'list', component: MedicationsListComponent },
    { path: 'add', component: MedicationsAddComponent },
    { path: 'edit', component: MedicationsEditComponent },
    { path: '', redirectTo: 'list', pathMatch: 'full' }
];
