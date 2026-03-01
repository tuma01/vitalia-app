import { Routes } from '@angular/router';
import { VaccinesListComponent } from './vaccines-list.component';
import { VaccinesAddComponent } from './vaccines-add.component';
import { VaccinesEditComponent } from './vaccines-edit.component';

export const routes: Routes = [
    { path: 'list', component: VaccinesListComponent },
    { path: 'add', component: VaccinesAddComponent },
    { path: 'edit', component: VaccinesEditComponent },
    { path: '', redirectTo: 'list', pathMatch: 'full' }
];
