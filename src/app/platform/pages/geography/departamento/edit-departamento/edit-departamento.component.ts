import { Component, inject, OnInit } from '@angular/core';
import { FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { TranslateModule } from '@ngx-translate/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { CrudBaseAddEditComponent } from '@shared/components/crud-template/crud-base-add-edit.component';
import { Departamento } from 'app/api/models/departamento';
import { DEPARTAMENTO_CRUD_CONFIG } from '../departamento-crud.config';
import { CrudTemplateComponent } from '@shared/components/crud-template/crud-template.component';
import { Observable } from 'rxjs';

@Component({
    selector: 'app-edit-departamento',
    standalone: true,
    imports: [
        CommonModule,
        TranslateModule,
        FormsModule,
        ReactiveFormsModule,
        MatButtonModule,
        MatCardModule,
        MatFormFieldModule,
        MatInputModule,
        CrudTemplateComponent
    ],
    templateUrl: './edit-departamento.component.html',
    styleUrls: ['./edit-departamento.component.scss']
})
export class EditDepartamentoComponent extends CrudBaseAddEditComponent<Departamento> implements OnInit {
    protected override entityNameKey = 'entity.departamento';

    protected override form = this.fb.nonNullable.group({
        id: [null as number | null],
        nombre: ['', [Validators.required]],
        poblacion: [0],
        superficie: [0],
    });

    public readonly config = DEPARTAMENTO_CRUD_CONFIG();

    ngOnInit(): void {
        const id = this.activatedRoute.snapshot.queryParamMap.get('id');
        if (id) {
            this.entityId = Number(id);
            this.loadEntityData(this.entityId);
        } else {
            this.router.navigate(this.getSuccessRoute());
        }
    }

    protected override getSuccessRoute(): any[] {
        return ['/platform/geography/departamento/departamentos'];
    }

    protected override saveEntity(formData: Departamento): Observable<Departamento> {
        return this.config.apiService.update(this.entityId!, formData);
    }

    protected override loadEntityData(id: number): void {
        this.config.apiService.getById(id).subscribe({
            next: (departamento: Departamento) => {
                this.form.patchValue(departamento as any);
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
