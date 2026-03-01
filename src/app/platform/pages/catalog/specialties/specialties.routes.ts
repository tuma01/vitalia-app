import { Routes } from '@angular/router';
import { SpecialtiesListComponent } from './specialties-list.component';
import { SpecialtiesAddComponent } from './specialties-add.component';
import { SpecialtiesEditComponent } from './specialties-edit.component';

export const routes: Routes = [
    { path: 'list', component: SpecialtiesListComponent },
    { path: 'add', component: SpecialtiesAddComponent },
    { path: 'edit', component: SpecialtiesEditComponent },
    { path: '', redirectTo: 'list', pathMatch: 'full' }
];
