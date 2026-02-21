import { TranslateService } from '@ngx-translate/core';
import { MtxGridColumn, MtxGridColumnButton } from '@ng-matero/extensions/grid';
import { ConfirmDialogService } from '@shared/services/confirm-dialog.service';

interface CustomButtonOptions {
    icon: string;
    tooltipKey: string;
    handler: (record: any) => void;
    color?: string;
}

interface OperationColumnOptions {
    editHandler?: (record: any) => void;
    deleteHandler?: (record: any) => void;
    customButtons?: CustomButtonOptions[];
    entityType?: string;
    /** Row field name used to extract the item label for the delete dialog */
    fieldForMessage?: string;
}

export function getOperationColumn(
    translate: TranslateService,
    options: OperationColumnOptions,
    confirmDialog?: ConfirmDialogService
): MtxGridColumn {
    const buttons: MtxGridColumnButton[] = [];

    if (options.editHandler) {
        const editBtn: MtxGridColumnButton = {
            type: 'icon',
            icon: 'edit',
            text: '',         // explicit empty — prevents MtxGrid from showing row data
            tooltip: { message: translate.instant('common.edit'), position: 'below' },
            click: options.editHandler,
        };
        buttons.push(editBtn);
    }

    if (options.deleteHandler) {
        const deleteBtn: MtxGridColumnButton = {
            type: 'icon',
            icon: 'delete',
            text: '',         // explicit empty — prevents MtxGrid from showing row data
            class: 'btn-row-delete',
            tooltip: { message: translate.instant('common.delete'), position: 'below' },
            click: (record) => {
                const itemName = options.fieldForMessage
                    ? record[options.fieldForMessage]
                    : undefined;
                const entityLabel = options.entityType
                    ? translate.instant(options.entityType)
                    : undefined;

                if (confirmDialog) {
                    confirmDialog.confirm({
                        titleKey: 'common.confirm_delete_title',
                        messageKey: 'common.confirm_delete_message',
                        itemName,
                        entityLabel,
                        icon: 'delete_forever',
                        confirmColor: 'warn',
                    }).subscribe(confirmed => {
                        if (confirmed) options.deleteHandler!(record);
                    });
                } else {
                    const message = translate.instant('common.confirm_delete_generic');
                    if (confirm(message)) options.deleteHandler!(record);
                }
            },
        };
        buttons.push(deleteBtn);
    }

    if (options.customButtons) {
        buttons.push(...options.customButtons.map(btn => {
            const customBtn: MtxGridColumnButton = {
                type: 'icon',
                icon: btn.icon,
                text: '',
                tooltip: { message: translate.instant(btn.tooltipKey), position: 'below' },
                color: btn.color as any,
                click: btn.handler,
            };
            return customBtn;
        }));
    }

    return {
        header: translate.stream('common.operations'),
        field: 'operation',
        minWidth: 120,
        width: '120px',
        pinned: 'right',
        type: 'button',
        buttons,
    };
}
