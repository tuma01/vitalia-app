import { Component, inject, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { CrudTemplateComponent } from '@shared/components/crud-template/crud-template.component';
import { MEDICAL_SPECIALTY_CRUD_CONFIG } from './medical-specialty-crud.config';
import { MedicalSpecialty } from 'app/api/models/medical-specialty';
import { getOperationColumn } from '@shared/gridcolumn-config';
import { TranslateService } from '@ngx-translate/core';
import { ConfirmDialogService } from '@shared/services/confirm-dialog.service';

@Component({
    selector: 'app-medical-specialty-list',
    standalone: true,
    imports: [CrudTemplateComponent],
    template: `
    <app-crud-template #crud
      [config]="config"
      (create)="createNew()"
    ></app-crud-template>
  `
})
export class MedicalSpecialtyListComponent {
    @ViewChild('crud') private crud!: CrudTemplateComponent<MedicalSpecialty>;

    private router = inject(Router);
    private translate = inject(TranslateService);
    private confirmDialog = inject(ConfirmDialogService);
    private snackBar = inject(MatSnackBar);

    config = MEDICAL_SPECIALTY_CRUD_CONFIG();

    constructor() {
        this.config.columns.push(
            (getOperationColumn(
                this.translate,
                {
                    editHandler: (record: MedicalSpecialty) => this.edit(record),
                    deleteHandler: (record: MedicalSpecialty) => this.deleteSpecialty(record),
                    entityType: 'menu.catalog.specialty.singular',
                    fieldForMessage: 'name'
                },
                this.confirmDialog
            ) as any)
        );
    }

    createNew(): void {
        this.router.navigate(['/platform/catalog/specialty/add']);
    }

    edit(record: MedicalSpecialty): void {
        this.router.navigate(['/platform/catalog/specialty/edit'], {
            queryParams: { id: record.id },
        });
    }

    private deleteSpecialty(record: MedicalSpecialty): void {
        this.config.apiService.delete(record.id!).subscribe({
            next: () => {
                this.snackBar.open(
                    this.translate.instant('common.delete_success'),
                    undefined, { duration: 3000, panelClass: 'success-snackbar' }
                );
                this.crud.loadData();
            },
            error: () => this.snackBar.open(
                this.translate.instant('common.delete_error'),
                this.translate.instant('common.close'),
                { duration: 5000, panelClass: 'error-snackbar' }
            )
        });
    }
}
