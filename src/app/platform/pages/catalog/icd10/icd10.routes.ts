import { Routes } from '@angular/router';
import { Icd10ListComponent } from './icd10-list.component';
import { Icd10AddComponent } from './icd10-add.component';
import { Icd10EditComponent } from './icd10-edit.component';

export const routes: Routes = [
    { path: 'list', component: Icd10ListComponent },
    { path: 'add', component: Icd10AddComponent },
    { path: 'edit', component: Icd10EditComponent },
    { path: '', redirectTo: 'list', pathMatch: 'full' }
];
