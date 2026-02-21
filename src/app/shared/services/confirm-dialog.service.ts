import { inject, Injectable } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import {
    ConfirmDialogComponent,
    ConfirmDialogData
} from '../components/confirm-dialog/confirm-dialog.component';

@Injectable({ providedIn: 'root' })
export class ConfirmDialogService {
    private dialog = inject(MatDialog);

    /**
     * Opens the modern confirm dialog.
     * Returns an Observable<boolean> — true if confirmed, false if cancelled.
     */
    confirm(data: ConfirmDialogData): Observable<boolean> {
        const ref = this.dialog.open(ConfirmDialogComponent, {
            data,
            width: 'auto',
            maxWidth: '96vw',
            panelClass: 'confirm-dialog-panel',
            disableClose: true,
            autoFocus: false
        });
        return ref.afterClosed().pipe(map(result => result === true));
    }
}
