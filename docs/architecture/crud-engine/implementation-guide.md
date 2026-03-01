# Guía de Implementación del CRUD Engine

El módulo de **Alergias** (`allergies/`) es el **Master Template** oficial. Úsalo como base para cualquier nuevo CRUD.

> [!IMPORTANT]
> Las claves i18n en el CRUD Engine usan el prefijo **`menu.catalog.{module}.*`** — nunca `entity.*`, `catalog.*` (sin `menu.`) ni cualquier otra variante.

---

## Paso 1: Configuración (`*-crud.config.ts`)

Define el esquema, columnas y campos del formulario con las claves i18n correctas:

```typescript
export const BLOOD_TYPES_CRUD_CONFIG = (): CrudConfig<BloodType> => {
  const service = inject(BloodTypeService);
  return {
    // ⚠️ Siempre con prefijo menu.catalog.{module}
    entityName:       'menu.catalog.blood_type.singular',
    entityNamePlural: 'menu.catalog.blood_type.plural',

    getId: (entity: BloodType) => entity.id!,

    apiService: new OpenApiCrudAdapter<BloodType>(service, {
      getAll:   'getAllBloodTypes',
      getById:  'getBloodTypeById',
      create:   'createBloodType',
      update:   'updateBloodType',
      delete:   'deleteBloodType'
    }),

    columns: [
      { field: 'id',   header: 'menu.catalog.blood_type.fields.id',   sortable: true, width: '80px' },
      { field: 'code', header: 'menu.catalog.blood_type.fields.code', sortable: true, width: '120px' },
      { field: 'name', header: 'menu.catalog.blood_type.fields.name', sortable: true },
    ],

    form: {
      layout: { columns: 2 },
      fields: [
        { name: 'code', label: 'menu.catalog.blood_type.fields.code', type: 'text', required: true, maxLength: 20, colSpan: 1 },
        { name: 'name', label: 'menu.catalog.blood_type.fields.name', type: 'text', required: true, maxLength: 100, colSpan: 1 },
      ]
    },

    enableAdd: true, enableEdit: true, enableDelete: true,
    table: { pageSize: 10, rowStriped: true, showToolbar: true, columnResizable: true }
  };
};
```

---

## Paso 2: Componente de Lista

```typescript
@Component({
  selector: 'app-blood-types-list',
  standalone: true,
  imports: [CrudTemplateComponent],
  template: `<app-crud-template #crud [config]="config" (create)="createNew()"></app-crud-template>`
})
export class BloodTypesListComponent {
  @ViewChild('crud') private crud!: CrudTemplateComponent<BloodType>;
  config = BLOOD_TYPES_CRUD_CONFIG();

  constructor() {
    this.config.columns.push(
      getOperationColumn(inject(TranslateService), {
        editHandler:   (r) => this.edit(r),
        deleteHandler: (r) => this.delete(r),
        entityType:    'menu.catalog.blood_type.singular',
        fieldForMessage: 'name'
      }, inject(ConfirmDialogService)) as any
    );
  }

  createNew() { inject(Router).navigate(['/platform/catalog/blood-types/add']); }
  edit(r: BloodType) { inject(Router).navigate(['/platform/catalog/blood-types/edit'], { queryParams: { id: r.id } }); }
}
```

---

## Paso 3: Add/Edit con `buildFormFromConfig`

```typescript
export class BloodTypesAddComponent extends CrudBaseAddEditComponent<BloodType> implements OnInit {
  protected override entityNameKey = 'menu.catalog.blood_type.singular';
  public readonly config = BLOOD_TYPES_CRUD_CONFIG();
  protected override form = CrudBaseAddEditComponent.buildFormFromConfig(inject(FormBuilder), this.config);

  ngOnInit(): void { }

  protected override getSuccessRoute() { return ['/platform/catalog/blood-types/list']; }
  protected override saveEntity(data: BloodType) { return this.config.apiService.create(data); }
  onCancel() { inject(Router).navigate(this.getSuccessRoute()); }
}
```

---

## Paso 4: Rutas

```typescript
// blood-types.routes.ts
export const routes: Routes = [
  { path: 'list', component: BloodTypesListComponent },
  { path: 'add',  component: BloodTypesAddComponent },
  { path: 'edit', component: BloodTypesEditComponent },
  { path: '', redirectTo: 'list', pathMatch: 'full' }
];
```

---

## Ventajas del Master Template

1. **DRY**: Las validaciones se definen una sola vez en el CRUD Config.
2. **Consistencia UI**: Todos los formularios y mensajes de éxito/error se comportan igual.
3. **i18n centralizado**: Claves bajo `menu.catalog.*` sincronizadas en los 3 archivos JSON.
