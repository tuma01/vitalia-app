import { Routes } from '@angular/router';
import { PlatformShellComponent } from '../../shared/components/platform-shell/platform-shell.component';

export const routes: Routes = [
    {
        path: '',
        children: [
            {
                path: 'bridge',
                component: PlatformShellComponent,
                data: { title: 'menu.bridge_connectors', icon: 'settings_input_component' }
            },
            {
                path: 'profiles',
                component: PlatformShellComponent,
                data: { title: 'menu.fhir_hl7_profiles', icon: 'schema' }
            },
            {
                path: 'empi',
                component: PlatformShellComponent,
                data: { title: 'menu.empi_matching', icon: 'fingerprint' }
            }
        ]
    }
];
