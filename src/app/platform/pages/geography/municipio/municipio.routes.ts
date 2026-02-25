import { Routes } from '@angular/router';
import { MunicipiosComponent } from './municipios/municipios.component';
import { AddMunicipioComponent } from './add-municipio/add-municipio.component';
import { EditMunicipioComponent } from './edit-municipio/edit-municipio.component';

export const routes: Routes = [
    { path: 'municipios', component: MunicipiosComponent },
    { path: 'addMunicipio', component: AddMunicipioComponent },
    { path: 'editMunicipio', component: EditMunicipioComponent },
    { path: '', redirectTo: 'municipios', pathMatch: 'full' }
];
