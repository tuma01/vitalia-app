import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { inject } from '@angular/core';
import { Observable } from 'rxjs';
import { TranslateModule } from '@ngx-translate/core';
import { CrudBaseAddEditComponent } from '@shared/components/crud-template/crud-base-add-edit.component';
import { CrudTemplateComponent } from '@shared/components/crud-template/crud-template.component';
import { MUNICIPIO_CRUD_CONFIG } from '../municipio-crud.config';
import { Municipio } from 'app/api/models/municipio';
import { ProvinciaService } from 'app/api/services/provincia.service';

@Component({
    selector: 'app-add-municipio',
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
export class AddMunicipioComponent extends CrudBaseAddEditComponent<Municipio> implements OnInit {
    protected override entityNameKey = 'entity.municipality';
    public readonly config = MUNICIPIO_CRUD_CONFIG();
    private provinciaService = inject(ProvinciaService);
    private cdr = inject(ChangeDetectorRef);

    protected override form: FormGroup = CrudBaseAddEditComponent.buildFormFromConfig(
        inject(FormBuilder), this.config
    );

    ngOnInit(): void {
        this.loadProvincias();
    }

    private loadProvincias(): void {
        this.provinciaService.getAllProvincias().subscribe({
            next: (provincias) => {
                const provField = this.config.form?.fields.find(f => f.name === 'provinciaId');
                if (provField) {
                    provField.options = provincias.map(p => ({
                        label: p.nombre || p.id?.toString() || '',
                        value: p.id
                    }));
                    this.cdr.detectChanges();
                }
            }
        });
    }

    protected override getSuccessRoute(): any[] {
        return ['/platform/geography/municipio/municipios'];
    }

    protected override saveEntity(formData: Municipio): Observable<Municipio> {
        return this.config.apiService.create(formData);
    }

    onCancel(): void {
        this.router.navigate(this.getSuccessRoute());
    }
}
