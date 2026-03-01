import { Routes } from '@angular/router';
import { KinshipListComponent } from './kinship-list.component';
import { KinshipAddComponent } from './kinship-add.component';
import { KinshipEditComponent } from './kinship-edit.component';

export const routes: Routes = [
    { path: 'list', component: KinshipListComponent },
    { path: 'add', component: KinshipAddComponent },
    { path: 'edit', component: KinshipEditComponent },
    { path: '', redirectTo: 'list', pathMatch: 'full' }
];
