import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { inject } from '@angular/core';
import { Observable } from 'rxjs';
import { TranslateModule } from '@ngx-translate/core';
import { CrudBaseAddEditComponent } from '@shared/components/crud-template/crud-base-add-edit.component';
import { CrudTemplateComponent } from '@shared/components/crud-template/crud-template.component';
import { ORGANIZATIONS_CRUD_CONFIG } from '../organizations-crud.config';
import { Tenant } from 'app/api/models/tenant';
import { ThemeService } from 'app/api/services/theme.service';

@Component({
    selector: 'app-organizations-edit',
    standalone: true,
    imports: [CrudTemplateComponent, TranslateModule],
    template: `
        <app-crud-template
            mode="edit"
            [config]="config"
            [formGroup]="form"
            (save)="onSubmit()"
            (cancel)="onCancel()">
        </app-crud-template>
    `
})
export class OrganizationsEditComponent extends CrudBaseAddEditComponent<Tenant> implements OnInit {
    protected override entityNameKey = 'menu.tenant_governance.organizations.singular';
    public readonly config = ORGANIZATIONS_CRUD_CONFIG();
    private themeService = inject(ThemeService);

    protected override form: FormGroup = CrudBaseAddEditComponent.buildFormFromConfig(
        inject(FormBuilder), this.config
    );

    ngOnInit(): void {
        const id = this.activatedRoute.snapshot.queryParamMap.get('id');
        if (id) {
            this.entityId = Number(id);
            this.loadThemes();
            this.loadEntityData(this.entityId);
        } else {
            this.router.navigate(this.getSuccessRoute());
        }
    }

    private loadThemes(): void {
        this.themeService.getAllThemes().subscribe(themes => {
            const themeField = this.config.form?.fields.find(f => f.name === 'themeId');
            if (themeField) {
                // Map templates
                const options = themes.map(t => ({ label: t.name!, value: t.id! }));

                // Check if the current value is NOT in the templates (lazy cloning case)
                const currentValue = this.form.get('themeId')?.value;
                if (currentValue && !options.find(o => o.value === currentValue)) {
                    // Try to get the name of the current theme if it's a clone
                    this.themeService.getThemeById({ id: currentValue }).subscribe(t => {
                        options.push({ label: `${t.name!} (Personalizado)`, value: t.id! });
                        themeField.options = [...options];
                    });
                } else {
                    themeField.options = options;
                }
            }
        });
    }

    protected override getSuccessRoute(): any[] {
        return ['/platform/tenants/organizations/list'];
    }

    protected override saveEntity(formData: Tenant): Observable<Tenant> {
        return this.config.apiService.update(this.entityId!, formData);
    }

    protected override loadEntityData(id: number): void {
        this.config.apiService.getById(id).subscribe({
            next: (tenant: Tenant) => {
                this.form.patchValue(tenant as any);
            },
            error: (err: any) => {
                this.handleError(err, 'crud.load_error');
                this.router.navigate(this.getSuccessRoute());
            }
        });
    }

    onCancel(): void {
        this.router.navigate(this.getSuccessRoute());
    }
}
