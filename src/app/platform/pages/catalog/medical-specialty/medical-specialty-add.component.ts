import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { inject } from '@angular/core';
import { Observable } from 'rxjs';
import { TranslateModule } from '@ngx-translate/core';
import { CrudBaseAddEditComponent } from '@shared/components/crud-template/crud-base-add-edit.component';
import { CrudTemplateComponent } from '@shared/components/crud-template/crud-template.component';
import { MEDICAL_SPECIALTY_CRUD_CONFIG } from './medical-specialty-crud.config';
import { MedicalSpecialty } from 'app/api/models/medical-specialty';

@Component({
    selector: 'app-medical-specialty-add',
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
export class MedicalSpecialtyAddComponent extends CrudBaseAddEditComponent<MedicalSpecialty> implements OnInit {
    protected override entityNameKey = 'menu.catalog.specialty.singular';
    public readonly config = MEDICAL_SPECIALTY_CRUD_CONFIG();

    protected override form: FormGroup = CrudBaseAddEditComponent.buildFormFromConfig(
        inject(FormBuilder), this.config
    );

    ngOnInit(): void { }

    protected override getSuccessRoute(): any[] {
        return ['/platform/catalog/specialty/list'];
    }

    protected override saveEntity(formData: MedicalSpecialty): Observable<MedicalSpecialty> {
        return this.config.apiService.create(formData);
    }

    onCancel(): void {
        this.router.navigate(this.getSuccessRoute());
    }
}
