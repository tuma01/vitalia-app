import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { inject } from '@angular/core';
import { Observable, forkJoin } from 'rxjs';
import { TranslateModule } from '@ngx-translate/core';
import { CrudBaseAddEditComponent } from '@shared/components/crud-template/crud-base-add-edit.component';
import { CrudTemplateComponent } from '@shared/components/crud-template/crud-template.component';
import { PROVINCIA_CRUD_CONFIG } from '../provincia-crud.config';
import { Provincia } from 'app/api/models/provincia';
import { DepartamentoService } from 'app/api/services/departamento.service';

@Component({
    selector: 'app-edit-provincia',
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
export class EditProvinciaComponent extends CrudBaseAddEditComponent<Provincia> implements OnInit {
    protected override entityNameKey = 'entity.province';
    public readonly config = PROVINCIA_CRUD_CONFIG();
    private departamentoService = inject(DepartamentoService);
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
            departamentos: this.departamentoService.getAllDepartamentos(),
            provincia: this.config.apiService.getById(id)
        }).subscribe({
            next: ({ departamentos, provincia }) => {
                // 1. Populate options
                const deptField = this.config.form?.fields.find(f => f.name === 'departamentoId');
                if (deptField) {
                    deptField.options = departamentos.map(d => ({
                        label: d.nombre || d.id?.toString() || '',
                        value: d.id
                    }));
                }

                // 2. Patch form
                this.form.patchValue(provincia as any);

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
        return ['/platform/geography/provincia/provincias'];
    }

    protected override saveEntity(formData: Provincia): Observable<Provincia> {
        return this.config.apiService.update(this.entityId!, formData);
    }

    onCancel(): void {
        this.router.navigate(this.getSuccessRoute());
    }
}
