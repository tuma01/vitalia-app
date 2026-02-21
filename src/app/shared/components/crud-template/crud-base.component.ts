import { OnInit, Directive, Input, inject } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { finalize, forkJoin } from 'rxjs';
import { MatSnackBar } from '@angular/material/snack-bar';
import { CrudConfig, CrudMode } from './crud-config';
import { MtxGridColumn, MtxGridColumnType } from '@ng-matero/extensions/grid';
import { ConfirmDialogService } from '@shared/services/confirm-dialog.service';

@Directive()
export abstract class CrudBaseComponent<T> implements OnInit {
    @Input() config!: CrudConfig<T>;

    dataList: T[] = [];
    filteredData: T[] = [];
    isLoading = false;
    columns: MtxGridColumn[] = [];
    selectedRows: T[] = [];
    @Input() mode: CrudMode = 'list';

    /** Tracks the current page to constrain select-all to visible page only */
    protected currentPageIndex = 0;
    protected currentPageSize = 10;

    protected translate = inject(TranslateService);
    protected confirmDialog = inject(ConfirmDialogService);
    protected snackBar = inject(MatSnackBar);

    ngOnInit(): void {
        if (!this.config) {
            throw new Error('CrudConfig is required for CrudBaseComponent');
        }
        this.currentPageSize = this.config.table?.pageSize ?? 10;
        this.initColumns();
        this.loadData();
    }

    protected initColumns(): void {
        this.columns = this.config.columns.map(col => {
            const mtxCol: MtxGridColumn = {
                ...col,
                field: col.field as string,
                type: (col.type || 'text') as MtxGridColumnType
            };

            if (typeof col.header === 'string') {
                mtxCol.header = this.translate.stream(col.header);
            }

            return mtxCol;
        });
        console.log('[CrudBase] Columns initialized:', this.columns);
    }

    loadData(): void {
        const entityName = this.config.entityNamePlural || 'entities';
        console.log(`[CrudBase] 🔍 Loading data for ${entityName}...`);
        this.isLoading = true;

        this.config.apiService.getAll()
            .pipe(finalize(() => {
                this.isLoading = false;
                console.log(`[CrudBase] 🏁 Loading complete for ${entityName}.`);
            }))
            .subscribe({
                next: (data) => {
                    console.log(`[CrudBase] ✅ Data received for ${entityName}:`, data);
                    if (Array.isArray(data)) {
                        this.dataList = data;
                        this.filteredData = [...this.dataList];
                    } else if ((data as any)?.content) {
                        this.dataList = (data as any).content;
                        this.filteredData = [...this.dataList];
                    } else {
                        console.error(`[CrudBase] ❌ Expected array but received:`, data);
                    }
                },
                error: (err) => {
                    console.error(`[CrudBase] ❌ Error loading ${entityName}:`, err);
                }
            });
    }

    onSearch(searchTerm: string): void {
        if (!searchTerm?.trim()) {
            this.filteredData = [...this.dataList];
            return;
        }
        const term = searchTerm.toLowerCase().trim();
        this.filteredData = this.dataList.filter(item => this.searchInObject(item, term));
    }

    protected searchInObject(obj: any, term: string): boolean {
        return Object.keys(obj).some(key => {
            const value = obj[key];
            if (key.startsWith('$') || value == null) return false;

            if (typeof value === 'object' && !Array.isArray(value)) {
                return this.searchInObject(value, term);
            }

            if (Array.isArray(value)) {
                return value.some(arrayItem => {
                    if (typeof arrayItem === 'object' && arrayItem !== null) {
                        return this.searchInObject(arrayItem, term);
                    }
                    return String(arrayItem).toLowerCase().includes(term);
                });
            }

            return String(value).toLowerCase().includes(term);
        });
    }

    /** Single row delete — opens the modern dialog */
    onDelete(entity: T): void {
        if (!this.config.enableDelete) return;

        const id = this.config.getId(entity);
        const itemName = (entity as any).name || (entity as any).niceName || String(id);
        const entityLabel = this.translate.instant(this.config.entityName);

        this.confirmDialog.confirm({
            titleKey: 'common.confirm_delete_title',
            messageKey: 'common.confirm_delete_message',
            itemName,
            entityLabel,
            icon: 'delete_forever',
            confirmColor: 'warn',
        }).subscribe(confirmed => {
            if (!confirmed) return;
            this.isLoading = true;
            this.config.apiService.delete(id)
                .pipe(finalize(() => this.isLoading = false))
                .subscribe({
                    next: () => {
                        this.snackBar.open(
                            this.translate.instant('common.delete_success'),
                            undefined, { duration: 3000, panelClass: 'success-snackbar' }
                        );
                        this.loadData();
                    },
                    error: () => this.snackBar.open(
                        this.translate.instant('common.delete_error'),
                        this.translate.instant('common.close'),
                        { duration: 5000, panelClass: 'error-snackbar' }
                    )
                });
        });
    }

    /** Bulk delete — opens a summarized dialog then deletes all selected rows in parallel */
    onBulkDelete(rows: T[]): void {
        if (!this.config.enableDelete || rows.length === 0) return;

        const entityLabel = this.translate.instant(this.config.entityName);
        const count = rows.length;

        this.confirmDialog.confirm({
            titleKey: 'common.confirm_delete_title',
            messageKey: 'common.confirm_delete_bulk_message',
            itemName: String(count),
            entityLabel,
            confirmKey: 'common.delete',
            icon: 'delete_sweep',
            confirmColor: 'warn',
        }).subscribe(confirmed => {
            if (!confirmed) return;
            this.isLoading = true;
            const deletes$ = rows.map(row =>
                this.config.apiService.delete(this.config.getId(row))
            );
            forkJoin(deletes$)
                .pipe(finalize(() => this.isLoading = false))
                .subscribe({
                    next: () => {
                        this.snackBar.open(
                            this.translate.instant('common.delete_success'),
                            undefined, { duration: 3000, panelClass: 'success-snackbar' }
                        );
                        this.selectedRows = [];
                        this.loadData();
                    },
                    error: () => this.snackBar.open(
                        this.translate.instant('common.delete_error'),
                        this.translate.instant('common.close'),
                        { duration: 5000, panelClass: 'error-snackbar' }
                    )
                });
        });
    }

    onRefresh(): void {
        this.loadData();
    }

    clearSearch(input: HTMLInputElement): void {
        input.value = '';
        this.filteredData = [...this.dataList];
    }

    /**
     * Called by MtxGrid on every checkbox change.
     * If the event contains ALL filteredData items → header "Select All" was clicked
     * → constrain to the current page only.
     */
    onRowSelectionChange(rows: T[]): void {
        const isSelectAll = rows.length === this.filteredData.length && this.filteredData.length > 0;
        if (isSelectAll) {
            // Limit to current page slice
            const start = this.currentPageIndex * this.currentPageSize;
            const end = start + this.currentPageSize;
            this.selectedRows = this.filteredData.slice(start, end);
        } else {
            this.selectedRows = rows;
        }
    }

    /** Tracks pagination changes so we always know the current page slice */
    onPageChange(event: { pageIndex: number; pageSize: number }): void {
        this.currentPageIndex = event.pageIndex;
        this.currentPageSize = event.pageSize;
        // Clear selection on page change — avoids stale cross-page selections
        this.selectedRows = [];
    }
}
