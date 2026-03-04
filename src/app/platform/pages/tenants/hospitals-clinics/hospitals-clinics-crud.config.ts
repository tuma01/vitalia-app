import { inject } from '@angular/core';
import { Tenant } from 'app/api/models/tenant';
import { TenantService } from 'app/api/services/tenant.service';
import { OpenApiCrudAdapter } from '@shared/services/crud-api-adapter.service';
import { CrudConfig } from '@shared/components/crud-template/crud-config';

export const HOSPITALS_CLINICS_CRUD_CONFIG = (): CrudConfig<Tenant> => {
    const service = inject(TenantService);

    return {
        entityName: 'menu.tenant_governance.hospitals_clinics.singular',
        entityNamePlural: 'menu.tenant_governance.hospitals_clinics.plural',

        getId: (entity: Tenant) => entity.id!,

        apiService: new OpenApiCrudAdapter<Tenant>(service, {
            getAll: 'getAllTenants',
            getById: 'getTenantById',
            create: 'createTenant',
            update: 'updateTenant',
            delete: 'deleteTenant'
        }),

        columns: [
            {
                field: 'id',
                header: 'menu.tenant_governance.hospitals_clinics.fields.id',
                sortable: true,
                width: '70px'
            },
            {
                field: 'code',
                header: 'menu.tenant_governance.hospitals_clinics.fields.code',
                sortable: true,
                width: '120px'
            },
            {
                field: 'name',
                header: 'menu.tenant_governance.hospitals_clinics.fields.name',
                sortable: true
            },
            {
                field: 'type',
                header: 'menu.tenant_governance.hospitals_clinics.fields.type',
                sortable: true,
                width: '130px',
                type: 'tag',
                tag: {
                    'HOSPITAL': { text: 'menu.tenant_governance.hospitals_clinics.fields.type_options.HOSPITAL', color: '#1565c0' },
                    'CLINIC': { text: 'menu.tenant_governance.hospitals_clinics.fields.type_options.CLINIC', color: '#2e7d32' },
                    'LABORATORY': { text: 'menu.tenant_governance.hospitals_clinics.fields.type_options.LABORATORY', color: '#6a1b9a' },
                    'PHARMACY': { text: 'menu.tenant_governance.hospitals_clinics.fields.type_options.PHARMACY', color: '#e65100' },
                    'GLOBAL': { text: 'menu.tenant_governance.hospitals_clinics.fields.type_options.GLOBAL', color: '#37474f' }
                }
            },
            {
                field: 'isActive',
                header: 'menu.tenant_governance.hospitals_clinics.fields.isActive',
                sortable: true,
                width: '110px',
                type: 'tag',
                tag: {
                    'true': { text: 'common.active', color: '#4caf50' },
                    'false': { text: 'common.inactive', color: '#f44336' }
                }
            }
        ],

        form: {
            layout: { columns: 2 },
            fields: [
                {
                    name: 'code',
                    label: 'menu.tenant_governance.hospitals_clinics.fields.code',
                    type: 'text',
                    required: true,
                    colSpan: 1,
                    maxLength: 100
                },
                {
                    name: 'name',
                    label: 'menu.tenant_governance.hospitals_clinics.fields.name',
                    type: 'text',
                    required: true,
                    colSpan: 1,
                    maxLength: 100
                },
                {
                    name: 'type',
                    label: 'menu.tenant_governance.hospitals_clinics.fields.type',
                    type: 'select',
                    required: true,
                    colSpan: 1,
                    options: [
                        { label: 'menu.tenant_governance.hospitals_clinics.fields.type_options.HOSPITAL', value: 'HOSPITAL' },
                        { label: 'menu.tenant_governance.hospitals_clinics.fields.type_options.CLINIC', value: 'CLINIC' },
                        { label: 'menu.tenant_governance.hospitals_clinics.fields.type_options.LABORATORY', value: 'LABORATORY' },
                        { label: 'menu.tenant_governance.hospitals_clinics.fields.type_options.PHARMACY', value: 'PHARMACY' },
                        { label: 'menu.tenant_governance.hospitals_clinics.fields.type_options.GLOBAL', value: 'GLOBAL' }
                    ]
                },
                {
                    name: 'isActive',
                    label: 'menu.tenant_governance.hospitals_clinics.fields.isActive',
                    type: 'radio',
                    colSpan: 1,
                    options: [
                        { label: 'common.active', value: true },
                        { label: 'common.inactive', value: false }
                    ]
                },
                {
                    name: 'description',
                    label: 'menu.tenant_governance.hospitals_clinics.fields.description',
                    type: 'text',
                    required: false,
                    colSpan: 2,
                    maxLength: 500
                }
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
