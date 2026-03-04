import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { inject } from '@angular/core';
import { Observable } from 'rxjs';
import { TranslateModule } from '@ngx-translate/core';
import { CrudBaseAddEditComponent } from '@shared/components/crud-template/crud-base-add-edit.component';
import { CrudTemplateComponent } from '@shared/components/crud-template/crud-template.component';
import { ORGANIZATIONS_CRUD_CONFIG } from '../organizations-crud.config';
import { Tenant } from 'app/api/models/tenant';
import { ThemeService } from 'app/api/services/theme.service';

@Component({
    selector: 'app-organizations-add',
    standalone: true,
    imports: [CrudTemplateComponent, TranslateModule],
    template: `
        <app-crud-template
            mode="add"
            [config]="config"
            [formGroup]="form"
            (save)="onSubmit()"
            (cancel)="onCancel()">
        </app-crud-template>
    `
})
export class OrganizationsAddComponent extends CrudBaseAddEditComponent<Tenant> implements OnInit {
    protected override entityNameKey = 'menu.tenant_governance.organizations.singular';
    public readonly config = ORGANIZATIONS_CRUD_CONFIG();
    private themeService = inject(ThemeService);

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

    protected override saveEntity(formData: Tenant): Observable<Tenant> {
        return this.config.apiService.create(formData);
    }

    onCancel(): void {
        this.router.navigate(this.getSuccessRoute());
    }
}
