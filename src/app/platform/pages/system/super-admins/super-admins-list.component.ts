import { Component, inject, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { CrudTemplateComponent } from '@shared/components/crud-template/crud-template.component';
import { SUPER_ADMINS_CRUD_CONFIG } from './super-admins-crud.config';
import { SuperAdmin } from 'app/api/models/super-admin';
import { getOperationColumn } from '@shared/gridcolumn-config';
import { TranslateService, TranslateModule } from '@ngx-translate/core';
import { ConfirmDialogService } from '@shared/services/confirm-dialog.service';

@Component({
    selector: 'app-super-admins-list',
    standalone: true,
    imports: [CrudTemplateComponent, TranslateModule],
    template: `
        <app-crud-template #crud
            [config]="config"
            (create)="createNew()"
        ></app-crud-template>
    `
})
export class SuperAdminsListComponent {
    @ViewChild('crud') private crud!: CrudTemplateComponent<SuperAdmin>;

    private router = inject(Router);
    private translate = inject(TranslateService);
    private confirmDialog = inject(ConfirmDialogService);
    private snackBar = inject(MatSnackBar);

    public readonly config = SUPER_ADMINS_CRUD_CONFIG('list');

    constructor() {
        // Añadir columna de operaciones profesional
        this.config.columns.push(
            (getOperationColumn(
                this.translate,
                {
                    editHandler: (record: SuperAdmin) => this.edit(record),
                    deleteHandler: (record: SuperAdmin) => this.deleteSuperAdmin(record),
                    entityType: 'platform_governance.super_admins.singular',
                    fieldForMessage: 'nombre'
                },
                this.confirmDialog
            ) as any)
        );
    }

    createNew(): void {
        this.router.navigate(['/platform/system/iam/add']);
    }

    edit(record: SuperAdmin): void {
        this.router.navigate(['/platform/system/iam/edit'], {
            queryParams: { id: record.id },
        });
    }

    private deleteSuperAdmin(record: SuperAdmin): void {
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
