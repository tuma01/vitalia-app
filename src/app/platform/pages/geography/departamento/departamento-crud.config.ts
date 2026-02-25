import { inject } from '@angular/core';
import { Departamento } from 'app/api/models/departamento';
import { DepartamentoService } from 'app/api/services/departamento.service';
import { OpenApiCrudAdapter } from '@shared/services/crud-api-adapter.service';
import { CrudConfig } from '@shared/components/crud-template/crud-config';

export const DEPARTAMENTO_CRUD_CONFIG = (): CrudConfig<Departamento> => {
    const service = inject(DepartamentoService);

    return {
        entityName: 'entity.department',
        entityNamePlural: 'entity.departments',

        getId: (entity: Departamento) => entity.id!,

        apiService: new OpenApiCrudAdapter<Departamento>(service, {
            getAll: 'getAllDepartamentos',
            getById: 'getDepartamentoById',
            create: 'createDepartamento',
            update: 'updateDepartamento',
            delete: 'deleteDepartamento'
        }),

        columns: [
            { field: 'id', header: 'common.id', sortable: true, width: '80px' },
            { field: 'nombre', header: 'geography.department.fields.name', sortable: true }, // [FLEXIBLE]
            { field: 'countryId', header: 'geography.department.fields.country', sortable: false, width: '250px' }
        ],

        form: {
            layout: { columns: 2 },
            fields: [
                { name: 'nombre', label: 'geography.department.fields.name', type: 'text', required: true, colSpan: 1 },
                { name: 'poblacion', label: 'geography.department.fields.population', type: 'number', colSpan: 1 },
                { name: 'superficie', label: 'geography.department.fields.surface', type: 'number', colSpan: 1 },
                {
                    name: 'countryId',
                    label: 'geography.department.fields.country',
                    type: 'select',
                    required: true,
                    colSpan: 1,
                    options: [] // To be populated dynamically
                },
            ]
        },

        enableAdd: true,
        enableEdit: true,
        enableDelete: true,

        table: {
            pageSize: 10,
            rowStriped: true,
            showToolbar: true,
            columnResizable: true,
            multiSelectable: true,
            rowSelectable: true,
            hideRowSelectionCheckbox: false
        }
    };
};
