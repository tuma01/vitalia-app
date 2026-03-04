import { inject } from '@angular/core';
import { Tenant } from 'app/api/models/tenant';
import { TenantService } from 'app/api/services/tenant.service';
import { OpenApiCrudAdapter } from '@shared/services/crud-api-adapter.service';
import { CrudConfig } from '@shared/components/crud-template/crud-config';
import { of } from 'rxjs';

export const ORGANIZATIONS_CRUD_CONFIG = (): CrudConfig<Tenant> => {
    const service = inject(TenantService);

    return {
        entityName: 'menu.tenant_governance.organizations.singular',
        entityNamePlural: 'menu.tenant_governance.organizations.plural',

        getId: (entity: Tenant) => entity.id!,

        apiService: new OpenApiCrudAdapter<Tenant>(service, {
            getAll: 'getPaginatedTenants',
            getById: 'getTenantById',
            create: 'createTenant',
            update: 'updateTenant',
            delete: 'deleteTenant'
        }),

        columns: [
            { field: 'id', header: 'menu.tenant_governance.organizations.fields.id', sortable: true, width: '100px' },
            { field: 'code', header: 'menu.tenant_governance.organizations.fields.code', sortable: true, width: '150px' },
            { field: 'name', header: 'menu.tenant_governance.organizations.fields.name', sortable: true },
            { field: 'type', header: 'menu.tenant_governance.organizations.fields.type', sortable: true, width: '120px' },
            {
                field: 'isActive',
                header: 'menu.tenant_governance.organizations.fields.isActive',
                width: '130px',
                type: 'tag',
                tag: {
                    'true': { text: 'common.active', color: '#2e7d32' },
                    'false': { text: 'common.inactive', color: '#c62828' }
                }
            },
            {
                field: 'themeName',
                header: 'menu.tenant_governance.organizations.fields.themeId',
                width: '160px'
            },
        ],

        form: {
            layout: { columns: 2 },
            fields: [
                { name: 'code', label: 'menu.tenant_governance.organizations.fields.code', type: 'text', required: true, colSpan: 1 },
                { name: 'name', label: 'menu.tenant_governance.organizations.fields.name', type: 'text', required: true, colSpan: 1 },
                {
                    name: 'type',
                    label: 'menu.tenant_governance.organizations.fields.type',
                    type: 'select',
                    required: true,
                    colSpan: 1,
                    options: [
                        { label: 'menu.tenant_governance.organizations.types.hospital', value: 'HOSPITAL' },
                        { label: 'menu.tenant_governance.organizations.types.clinic', value: 'CLINIC' },
                        { label: 'menu.tenant_governance.organizations.types.pharmacy', value: 'PHARMACY' },
                        { label: 'menu.tenant_governance.organizations.types.laboratory', value: 'LABORATORY' },
                    ]
                },
                {
                    name: 'themeId',
                    label: 'menu.tenant_governance.organizations.fields.themeId',
                    type: 'select',
                    required: true,
                    colSpan: 1,
                    // We will populate this from ThemeService in the component or via a dynamic option loader
                    options: []
                },
                { name: 'isActive', label: 'menu.tenant_governance.organizations.fields.isActive', type: 'checkbox', colSpan: 1 },
                { name: 'description', label: 'menu.tenant_governance.organizations.fields.description', type: 'textarea', colSpan: 2 },
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
