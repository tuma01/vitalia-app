import { Routes } from '@angular/router';

// import { PlatformLayout } from './../../../platform/layout/platform-layout/platform-layout';
import { authGuard } from './../../../core/guards/auth.guard';
import { platformGuard } from './../../../core/guards/platform.guard';

export const routes: Routes = [
  {
    path: '',
    canActivate: [authGuard, platformGuard],  // <- Doble protección (opcional)
    // data: {roles: ['ROLE_ADMIN', 'SUPER_ADMIN']},
    children: [
      {
        path: 'countries',
        loadChildren: () => import('./countries/countries.routes').then(m => m.routes),
      },
      {
        path: 'departments',
        loadChildren: () => import('./departments/departments.routes').then(m => m.routes),
      },
      {
        path: 'provinces',
        loadChildren: () => import('./provinces/provinces.routes').then(m => m.routes),
      },
      {
        path: 'municipalities',
        loadChildren: () => import('./municipalities/municipalities.routes').then(m => m.routes),
      },
      { path: '', redirectTo: 'countries', pathMatch: 'full' }
    ]

  }
]
