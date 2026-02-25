import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { inject } from '@angular/core';
import { Observable, forkJoin } from 'rxjs';
import { TranslateModule } from '@ngx-translate/core';
import { CrudBaseAddEditComponent } from '@shared/components/crud-template/crud-base-add-edit.component';
import { CrudTemplateComponent } from '@shared/components/crud-template/crud-template.component';
import { MUNICIPIO_CRUD_CONFIG } from '../municipio-crud.config';
import { Municipio } from 'app/api/models/municipio';
import { ProvinciaService } from 'app/api/services/provincia.service';

@Component({
    selector: 'app-edit-municipio',
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
export class EditMunicipioComponent extends CrudBaseAddEditComponent<Municipio> implements OnInit {
    protected override entityNameKey = 'entity.municipality';
    public readonly config = MUNICIPIO_CRUD_CONFIG();
    private provinciaService = inject(ProvinciaService);
    private cdr = inject(ChangeDetectorRef);

    protected override form: FormGroup = CrudBaseAddEditComponent.buildFormFromConfig(
        inject(FormBuilder), this.config
    );

    ngOnInit(): void {
        const id = this.activatedRoute.snapshot.queryParamMap.get('id');
        if (id) {
            this.entityId = Number(id);
            this.loadInitialData(this.entityId);
        } else {
            this.router.navigate(this.getSuccessRoute());
        }
    }

    private loadInitialData(id: number): void {
        forkJoin({
            provincias: this.provinciaService.getAllProvincias(),
            municipio: this.config.apiService.getById(id)
        }).subscribe({
            next: ({ provincias, municipio }) => {
                // 1. Populate options
                const provField = this.config.form?.fields.find(f => f.name === 'provinciaId');
                if (provField) {
                    provField.options = provincias.map(p => ({
                        label: p.nombre || p.id?.toString() || '',
                        value: p.id
                    }));
                }

                // 2. Patch form
                this.form.patchValue(municipio as any);

                // 3. Force change detection
                this.cdr.detectChanges();
            },
            error: (err: any) => {
                this.handleError(err, 'crud.load_error');
                this.router.navigate(this.getSuccessRoute());
            }
        });
    }

    protected override getSuccessRoute(): any[] {
        return ['/platform/geography/municipio/municipios'];
    }

    protected override saveEntity(formData: Municipio): Observable<Municipio> {
        return this.config.apiService.update(this.entityId!, formData);
    }

    onCancel(): void {
        this.router.navigate(this.getSuccessRoute());
    }
}
