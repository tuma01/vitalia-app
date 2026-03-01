import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { inject } from '@angular/core';
import { Observable } from 'rxjs';
import { TranslateModule } from '@ngx-translate/core';
import { CrudBaseAddEditComponent } from '@shared/components/crud-template/crud-base-add-edit.component';
import { CrudTemplateComponent } from '@shared/components/crud-template/crud-template.component';
import { KINSHIP_CRUD_CONFIG } from './kinship-crud.config';
import { Kinship } from 'app/api/models/kinship';

@Component({
    selector: 'app-kinship-add',
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
export class KinshipAddComponent extends CrudBaseAddEditComponent<Kinship> implements OnInit {
    protected override entityNameKey = 'menu.catalog.kinship.singular';
    public readonly config = KINSHIP_CRUD_CONFIG();

    protected override form: FormGroup = CrudBaseAddEditComponent.buildFormFromConfig(
        inject(FormBuilder), this.config
    );

    ngOnInit(): void { }

    protected override getSuccessRoute(): any[] {
        return ['/platform/catalog/kinship/list'];
    }

    protected override saveEntity(formData: Kinship): Observable<Kinship> {
        return this.config.apiService.create(formData);
    }

    onCancel(): void {
        this.router.navigate(this.getSuccessRoute());
    }
}
