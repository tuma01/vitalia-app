import { inject } from '@angular/core';
import { Country } from 'app/api/models/country';
import { CountryService } from 'app/api/services/country.service';
import { OpenApiCrudAdapter } from '@shared/services/crud-api-adapter.service';
import { CrudConfig } from '@shared/components/crud-template/crud-config';

export const PAIS_CRUD_CONFIG = (): CrudConfig<Country> => {
    const service = inject(CountryService);

    return {
        entityName: 'entity.country',
        entityNamePlural: 'entity.countries',

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
            { field: 'iso', header: 'geography.country.fields.code', sortable: true, width: '100px' },
            { field: 'iso3', header: 'geography.country.fields.code3', sortable: true, width: '100px' },
            { field: 'name', header: 'geography.country.fields.name', sortable: true },
            { field: 'niceName', header: 'geography.country.fields.niceName', sortable: true },
            { field: 'phoneCode', header: 'geography.country.fields.phoneCode', sortable: true },
            { field: 'currency', header: 'geography.country.fields.currency', sortable: true },
        ],

        form: {
            layout: { columns: 2 },
            fields: [
                { name: 'iso', label: 'geography.country.fields.code', type: 'text', required: true, colSpan: 1, minLength: 2, maxLength: 2 },
                { name: 'iso3', label: 'geography.country.fields.code3', type: 'text', colSpan: 1, minLength: 3, maxLength: 3 },
                { name: 'name', label: 'geography.country.fields.name', type: 'text', required: true, colSpan: 1 },
                { name: 'niceName', label: 'geography.country.fields.niceName', type: 'text', required: true, colSpan: 1 },
                { name: 'numCode', label: 'geography.country.fields.numCode', type: 'number', colSpan: 1 },
                { name: 'phoneCode', label: 'geography.country.fields.phoneCode', type: 'number', required: true, colSpan: 1 },
                { name: 'currency', label: 'geography.country.fields.currency', type: 'text', colSpan: 1 },
            ]
        },

        enableAdd: true,
        enableEdit: true,
        enableDelete: true,

        table: {
            pageSize: 10,
            rowStriped: true,
            showToolbar: true,
            multiSelectable: true,
            rowSelectable: true,
            hideRowSelectionCheckbox: false
        }
    };
};
