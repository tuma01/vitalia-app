import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { Observable } from 'rxjs';
import { TranslateModule } from '@ngx-translate/core';
import { CrudBaseAddEditComponent } from '@shared/components/crud-template/crud-base-add-edit.component';
import { CrudTemplateComponent } from '@shared/components/crud-template/crud-template.component';
import { AddressSelectorComponent } from '@shared/components/address-selector/address-selector.component';
import { ORGANIZATIONS_CRUD_CONFIG } from '../organizations-crud.config';
import { Tenant } from 'app/api/models/tenant';
import { ThemeService } from 'app/api/services/theme.service';

@Component({
    selector: 'app-organizations-add',
    standalone: true,
    imports: [CrudTemplateComponent, TranslateModule, ReactiveFormsModule, AddressSelectorComponent],
    template: `
        <app-crud-template
            mode="add"
            [config]="config"
            [formGroup]="form"
            (save)="onSubmit()"
            (cancel)="onCancel()">
            <!-- Address section rendered outside the CRUD grid, in the manual [form] slot -->
            <div form>
                <app-address-selector [addressForm]="addressForm"></app-address-selector>
            </div>
        </app-crud-template>
    `
})
export class OrganizationsAddComponent extends CrudBaseAddEditComponent<Tenant> implements OnInit {
    protected override entityNameKey = 'menu.tenant_governance.organizations.singular';
    public readonly config = ORGANIZATIONS_CRUD_CONFIG();
    private themeService = inject(ThemeService);
    private readonly _fb = inject(FormBuilder);

    /** Address sub-FormGroup — managed by AddressSelectorComponent */
    addressForm: FormGroup = AddressSelectorComponent.buildAddressFormGroup(this._fb);

    protected override form: FormGroup = CrudBaseAddEditComponent.buildFormFromConfig(
        inject(FormBuilder), this.config
    );

    ngOnInit(): void {
        this.loadThemes();
    }

    private loadThemes(): void {
        this.themeService.getAllThemes().subscribe(themes => {
            const themeField = this.config.form?.fields.find(f => f.name === 'themeId');
            if (themeField) {
                themeField.options = themes.map(t => ({ label: t.name!, value: t.id! }));
            }
        });
    }

    protected override getSuccessRoute(): any[] {
        return ['/platform/tenants/organizations/list'];
    }

    protected override saveEntity(formData: any): Observable<Tenant> {
        const addressValues = this.addressForm.value;
        const payload: Tenant = {
            ...formData,
            address: {
                direccion: addressValues.direccion,
                ciudad: addressValues.ciudad || undefined,
                numero: addressValues.numero || undefined,
                piso: addressValues.piso || undefined,
                casillaPostal: addressValues.casillaPostal || undefined,
                countryId: addressValues.countryId || undefined,
                departamentoId: addressValues.departamentoId || undefined,
                provinciaId: addressValues.provinciaId || undefined,
                municipioId: addressValues.municipioId || undefined,
            }
        };
        return this.config.apiService.create(payload);
    }

    onCancel(): void {
        this.router.navigate(this.getSuccessRoute());
    }
}
