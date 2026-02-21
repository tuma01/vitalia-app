import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import { CrudTemplateComponent } from '@shared/components/crud-template/crud-template.component';
import { DEPARTAMENTO_CRUD_CONFIG } from '../departamento-crud.config';
import { Departamento } from 'app/api/models/departamento';
import { getOperationColumn } from '@shared/gridcolumn-config';
import { TranslateService } from '@ngx-translate/core';

@Component({
    selector: 'app-departamentos',
    standalone: true,
    imports: [CrudTemplateComponent],
    template: `
    <app-crud-template #crud
      [config]="config"
      (create)="createNew()"
      (edit)="edit($event)"
    ></app-crud-template>
  `
})
export class DepartamentosComponent {
    private router = inject(Router);
    private translate = inject(TranslateService);

    config = DEPARTAMENTO_CRUD_CONFIG();

    constructor() {
        // Add operation column and link it to the engine's logic
        this.config.columns.push(
            (getOperationColumn(this.translate, {
                editHandler: (record: Departamento) => this.edit(record),
                deleteHandler: (record: Departamento) => {
                    // Logic handled within the grid buttons if needed,
                    // or by the engine's onDelete if called via event.
                },
                entityType: 'entity.departamento',
                fieldForMessage: 'nombre'
            }) as any)
        );
    }

    createNew(): void {
        this.router.navigate(['/platform/geography/departamento/addDepartamento']);
    }

    edit(record: Departamento): void {
        this.router.navigate(['/platform/geography/departamento/editDepartamento'], {
            queryParams: { id: record.id },
        });
    }
}
