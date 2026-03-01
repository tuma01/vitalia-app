import { Routes } from '@angular/router';
import { KinshipsListComponent } from './kinships-list.component';
import { KinshipsAddComponent } from './kinships-add.component';
import { KinshipsEditComponent } from './kinships-edit.component';

export const routes: Routes = [
    { path: 'list', component: KinshipsListComponent },
    { path: 'add', component: KinshipsAddComponent },
    { path: 'edit', component: KinshipsEditComponent },
    { path: '', redirectTo: 'list', pathMatch: 'full' }
];
