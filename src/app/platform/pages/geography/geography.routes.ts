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
        path: 'pais',
        loadChildren: () => import('./pais/pais.routes').then(m => m.routes),
      },
      {
        path: 'departamento',
        loadChildren: () => import('./departamento/departamento.routes').then(m => m.routes),
      },
    ]

  }
]
