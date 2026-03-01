import { Component, Input, Output, EventEmitter, TemplateRef, ViewChild, inject, AfterViewInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatOptionModule } from '@angular/material/core';
import { MatSelectModule } from '@angular/material/select';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MtxGridModule, MtxGrid } from '@ng-matero/extensions/grid';
import { TranslateModule, TranslateService } from '@ngx-translate/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatRadioModule } from '@angular/material/radio';
import { CrudBaseComponent } from './crud-base.component';
import { CrudConfig, CrudMode } from './crud-config';

@Component({
    selector: 'app-crud-template',
    standalone: true,
    imports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        MatButtonModule,
        MatFormFieldModule,
        MtxGridModule,
        MatInputModule,
        MatOptionModule,
        MatSelectModule,
        MatCardModule,
        TranslateModule,
        MatCheckboxModule,
        MatIconModule,
        MatToolbarModule,
        MatTooltipModule,
        MatSnackBarModule,
        MatDatepickerModule,
        MatRadioModule
    ],
    templateUrl: './crud-template.component.html',
    styleUrls: ['./crud-template.component.scss']
})
export class CrudTemplateComponent<T> extends CrudBaseComponent<T> implements AfterViewInit {
    @Input() override mode: CrudMode = 'list';
    @ViewChild('tagCellTemplate', { static: true }) tagCellTemplate!: TemplateRef<any>;

    @Output() create = new EventEmitter<void>();
    @Output() edit = new EventEmitter<T>();
    @Output() delete = new EventEmitter<T>();
    @Output() bulkDelete = new EventEmitter<T[]>();
    @Output() save = new EventEmitter<void>();
    @Output() cancel = new EventEmitter<void>();

    @Input() cellTemplates: { [key: string]: TemplateRef<any> } = {};
    @Input() formGroup?: any; // The ReactiveForm from the implementation

    @ViewChild('grid') grid!: MtxGrid;

    protected override translate = inject(TranslateService);
    protected override cdr = inject(ChangeDetectorRef);

    constructor() {
        super();
    }

    ngAfterViewInit(): void {
        this.assignCustomTemplates();
    }

    private assignCustomTemplates(): void {
        // Only target columns that explicitly have a tag configuration
        const needsTemplate = this.columns.filter(col => (col as any).tag && !col.cellTemplate);

        if (needsTemplate.length > 0) {
            needsTemplate.forEach(col => {
                col.cellTemplate = this.tagCellTemplate;
            });
            this.columns = [...this.columns]; // Force grid to re-render
            this.cdr.detectChanges();
        }
    }

    getErrorMessage(controlName: string): string {
        const control = this.formGroup?.get(controlName);
        if (!control || !control.errors) {
            return '';
        }

        const errors = control.errors;

        if (errors['required']) {
            return this.translate.instant('validation.required');
        }
        if (errors['minlength']) {
            return this.translate.instant('validation.minlength', { requiredLength: errors['minlength'].requiredLength });
        }
        if (errors['maxlength']) {
            return this.translate.instant('validation.maxlength', { requiredLength: errors['maxlength'].requiredLength });
        }
        if (errors['email']) {
            return this.translate.instant('validation.email');
        }
        if (errors['pattern']) {
            return this.translate.instant('validation.pattern');
        }
        return '';
    }

    clearSelection(): void {
        this.grid.rowSelection.clear();
        this.selectedRows = [];
    }

    /**
     * Row click → toggle row selection (modern UX: clicking anywhere on a row
     * selects/deselects it, not just the checkbox).
     */
    onRowClick(event: { rowData: T; index: number }): void {
        if (!this.config.table?.multiSelectable && !this.config.table?.rowSelectable) {
            return; // selection not enabled for this config
        }
        const row = event.rowData;
        const alreadySelected = this.selectedRows.some(r => r === row);
        if (alreadySelected) {
            this.grid.rowSelection.deselect(row);
            this.selectedRows = this.selectedRows.filter(r => r !== row);
        } else {
            this.grid.rowSelection.select(row);
            this.selectedRows = [...this.selectedRows, row];
        }
    }
}
