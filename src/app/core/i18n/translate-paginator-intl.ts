import { Injectable, OnDestroy } from '@angular/core';
import { MatPaginatorIntl } from '@angular/material/paginator';
import { TranslateService } from '@ngx-translate/core';
import { Subject, takeUntil } from 'rxjs';

/**
 * Custom MatPaginatorIntl that uses ngx-translate so all paginator labels
 * update automatically when the active language changes.
 */
@Injectable()
export class TranslatePaginatorIntl extends MatPaginatorIntl implements OnDestroy {
    private destroy$ = new Subject<void>();

    constructor(private translate: TranslateService) {
        super();
        this.updateLabels();

        // Re-translate whenever language changes
        this.translate.onLangChange
            .pipe(takeUntil(this.destroy$))
            .subscribe(() => this.updateLabels());
    }

    private updateLabels(): void {
        this.itemsPerPageLabel = this.translate.instant('paginator.itemsPerPage');
        this.nextPageLabel = this.translate.instant('paginator.nextPage');
        this.previousPageLabel = this.translate.instant('paginator.previousPage');
        this.firstPageLabel = this.translate.instant('paginator.firstPage');
        this.lastPageLabel = this.translate.instant('paginator.lastPage');
        this.changes.next(); // notify paginator to re-render
    }

    override getRangeLabel = (page: number, pageSize: number, length: number): string => {
        if (length === 0 || pageSize === 0) {
            return this.translate.instant('paginator.rangeEmpty', { length });
        }
        const totalPages = Math.ceil(length / pageSize);
        const start = page * pageSize + 1;
        const end = Math.min(start + pageSize - 1, length);
        return this.translate.instant('paginator.range', { start, end, length, totalPages });
    };

    ngOnDestroy(): void {
        this.destroy$.next();
        this.destroy$.complete();
    }
}
