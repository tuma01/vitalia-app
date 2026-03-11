import { inject } from '@angular/core';
import { Country } from 'app/api/models/country';
import { CountryService } from 'app/api/services/country.service';
import { OpenApiCrudAdapter } from '@shared/services/crud-api-adapter.service';
import { CrudConfig } from '@shared/components/crud-template/crud-config';

export const COUNTRIES_CRUD_CONFIG = (): CrudConfig<Country> => {
    const service = inject(CountryService);

    return {
        // Aligned with menu.catalog.geography.country structure
        entityName: 'menu.catalog.geography.country.singular',
        entityNamePlural: 'menu.catalog.geography.country.plural',

        getId: (entity: Country) => entity.id!,

        apiService: new OpenApiCrudAdapter<Country>(service, {
            getAll: 'getAllCountries',
            getById: 'getCountryById',
            create: 'createCountry',
            update: 'updateCountry',
            delete: 'deleteCountry'
        }),

        columns: [
            { field: 'id', header: 'common.id', sortable: true, width: '80px' },
            { field: 'iso', header: 'menu.catalog.geography.country.fields.code', sortable: true, width: '100px' },
            { field: 'niceName', header: 'menu.catalog.geography.country.fields.niceName', sortable: true },
        ],

        form: {
            layout: { columns: 2 },
            fields: [
                { name: 'iso', label: 'menu.catalog.geography.country.fields.code', type: 'text', required: true, colSpan: 1, minLength: 2, maxLength: 2 },
                { name: 'iso3', label: 'menu.catalog.geography.country.fields.code3', type: 'text', colSpan: 1, minLength: 3, maxLength: 3 },
                { name: 'name', label: 'menu.catalog.geography.country.fields.name', type: 'text', required: true, colSpan: 1 },
                { name: 'niceName', label: 'menu.catalog.geography.country.fields.niceName', type: 'text', required: true, colSpan: 1 },
                { name: 'numCode', label: 'menu.catalog.geography.country.fields.numCode', type: 'number', colSpan: 1 },
                { name: 'phoneCode', label: 'menu.catalog.geography.country.fields.phoneCode', type: 'number', required: true, colSpan: 1 },
                { name: 'currency', label: 'menu.catalog.geography.country.fields.currency', type: 'text', colSpan: 1 },
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
