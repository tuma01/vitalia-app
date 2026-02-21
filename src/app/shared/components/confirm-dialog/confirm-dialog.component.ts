import { Component, Inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogModule, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { TranslateModule } from '@ngx-translate/core';

export interface ConfirmDialogData {
    /** i18n key for the dialog title */
    titleKey?: string;
    /** i18n key for the message body. Supports {{name}} and {{entity}} interpolation */
    messageKey?: string;
    /** Plain string name of the item being deleted */
    itemName?: string;
    /** Translated entity label */
    entityLabel?: string;
    /** i18n key for confirm button, defaults to 'common.delete' */
    confirmKey?: string;
    /** Color of the confirm button: 'warn' | 'primary', defaults to 'warn' */
    confirmColor?: 'warn' | 'primary';
    /** Material icon name, defaults to 'delete_forever' */
    icon?: string;
}

@Component({
    selector: 'app-confirm-dialog',
    standalone: true,
    imports: [
        CommonModule,
        MatDialogModule,
        MatButtonModule,
        MatIconModule,
        TranslateModule
    ],
    template: `
        <div class="dialog-wrapper">

            <!-- Danger header band -->
            <div class="dialog-header">
                <div class="header-icon-wrap">
                    <mat-icon>{{ data.icon || 'delete_forever' }}</mat-icon>
                </div>
                <div class="header-text">
                    <h2 class="dialog-title">
                        {{ (data.titleKey || 'common.confirm_delete_title') | translate }}
                    </h2>
                    <p class="dialog-subtitle">
                        {{ (data.messageKey || 'common.confirm_delete_message') | translate : {
                            name: data.itemName || '',
                            entity: data.entityLabel || ''
                        } }}
                    </p>
                </div>
            </div>

            <!-- Divider -->
            <div class="dialog-divider"></div>

            <!-- Item preview -->
            @if (data.itemName) {
                <div class="dialog-body">
                    <div class="item-preview">
                        <span class="item-preview-label">
                            {{ data.entityLabel || 'Item' }}
                        </span>
                        <div class="item-preview-value">
                            <mat-icon class="item-icon">label_outline</mat-icon>
                            <span>{{ data.itemName }}</span>
                        </div>
                    </div>
                </div>
            }

            <!-- Footer — buttons right-aligned -->
            <div class="dialog-footer">
                <button mat-stroked-button class="btn-cancel" (click)="cancel()">
                    {{ 'common.cancel' | translate }}
                </button>
                <button mat-flat-button [color]="data.confirmColor || 'warn'" class="btn-confirm" (click)="confirm()">
                    <mat-icon>{{ data.icon || 'delete' }}</mat-icon>
                    {{ (data.confirmKey || 'common.delete') | translate }}
                </button>
            </div>

        </div>
    `,
    styles: [`
        :host {
            display: block;
        }

        .dialog-wrapper {
            width: 460px;
            max-width: 96vw;
            overflow: hidden;
            border-radius: inherit;
            display: flex;
            flex-direction: column;
        }

        /* ── Header ─────────────────────────────── */
        .dialog-header {
            display: flex;
            align-items: flex-start;
            gap: 16px;
            padding: 24px 24px 20px;
            background: color-mix(in srgb, var(--mat-sys-error) 6%, var(--mat-sys-surface));
        }

        .header-icon-wrap {
            flex-shrink: 0;
            width: 48px;
            height: 48px;
            border-radius: 12px;
            background: color-mix(in srgb, var(--mat-sys-error) 15%, transparent);
            display: flex;
            align-items: center;
            justify-content: center;

            mat-icon {
                font-size: 26px;
                width: 26px;
                height: 26px;
                color: var(--mat-sys-error);
            }
        }

        .header-text {
            flex: 1;
            min-width: 0;
        }

        .dialog-title {
            font-size: 1.05rem;
            font-weight: 600;
            color: var(--mat-sys-on-surface);
            margin: 0 0 6px;
            line-height: 1.3;
        }

        .dialog-subtitle {
            font-size: 0.875rem;
            color: var(--mat-sys-on-surface-variant);
            margin: 0;
            line-height: 1.5;
        }

        /* ── Divider ─────────────────────────────── */
        .dialog-divider {
            height: 1px;
            background: var(--mat-sys-outline-variant);
            opacity: 0.6;
        }

        /* ── Body ────────────────────────────────── */
        .dialog-body {
            padding: 16px 24px;
            background: var(--mat-sys-surface);
        }

        .item-preview {
            display: flex;
            align-items: center;
            justify-content: space-between;
            background: var(--mat-sys-surface-container-low);
            border: 1px solid var(--mat-sys-outline-variant);
            border-radius: 10px;
            padding: 10px 14px;
        }

        .item-preview-label {
            font-size: 0.75rem;
            font-weight: 500;
            color: var(--mat-sys-on-surface-variant);
            letter-spacing: 0.5px;
            text-transform: uppercase;
        }

        .item-preview-value {
            display: flex;
            align-items: center;
            gap: 6px;
            font-size: 0.9rem;
            font-weight: 600;
            color: var(--mat-sys-on-surface);

            .item-icon {
                font-size: 17px;
                width: 17px;
                height: 17px;
                color: var(--mat-sys-on-surface-variant);
            }
        }

        /* ── Footer ─────────────────────────────── */
        .dialog-footer {
            display: flex;
            align-items: center;
            justify-content: flex-end;
            gap: 10px;
            padding: 14px 24px;
            background: var(--mat-sys-surface-container-low);
            border-top: 1px solid var(--mat-sys-outline-variant);
        }

        .btn-cancel {
            min-width: 100px;
        }

        .btn-confirm {
            min-width: 120px;
            display: inline-flex;
            align-items: center;
            gap: 6px;

            mat-icon {
                font-size: 18px;
                width: 18px;
                height: 18px;
            }
        }
    `]
})
export class ConfirmDialogComponent {
    constructor(
        private dialogRef: MatDialogRef<ConfirmDialogComponent>,
        @Inject(MAT_DIALOG_DATA) public data: ConfirmDialogData
    ) { }

    confirm(): void {
        this.dialogRef.close(true);
    }

    cancel(): void {
        this.dialogRef.close(false);
    }
}
