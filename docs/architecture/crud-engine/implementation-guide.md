# Guía de Implementación (Master Template)

El módulo de **Países** (`app/platform/pages/geography/pais`) es el **Master Template** oficial. Úsalo como base para cualquier nuevo CRUD.

## Paso 1: Configuración (`*-crud.config.ts`)
Define el esquema, columnas y campos del formulario.

```typescript
export const MI_ENTIDAD_CONFIG = (): CrudConfig<MiEntidad> => ({
  entityName: 'entity.singular',
  apiService: new OpenApiCrudAdapter<MiEntidad>(inject(MiServicio), { ... }),
  columns: [ ... ],
  form: {
    layout: { columns: 2 },
    fields: [
      { name: 'name', label: 'entity.fields.name', type: 'text', required: true }
    ]
  }
});
```

## Paso 2: Componente de Lista (`*.component.ts`)
Simple inyección del template y configuración.

```typescript
@Component({
  imports: [CrudTemplateComponent],
  template: `<app-crud-template #crud [config]="config" (create)="navToAdd()"></app-crud-template>`
})
export class MiListaComponent {
  config = MI_ENTIDAD_CONFIG();
  // ... añadir botones de operación vía getOperationColumn() en constructor
}
```

## Paso 3: Add/Edit con `buildFormFromConfig`
Para evitar duplicar validaciones, usa el helper estático en la clase base.

```typescript
export class MiAddEditComponent extends CrudBaseAddEditComponent<MiEntidad> {
  public readonly config = MI_ENTIDAD_CONFIG();
  protected override form = CrudBaseAddEditComponent.buildFormFromConfig(inject(FormBuilder), this.config);
  // El resto (guardado, errores) lo maneja la clase base automáticamente.
}
```

## Ventajas del Master Template
1. **DRY (Don't Repeat Yourself)**: Las validaciones se definen en un solo lugar (Config).
2. **Consistencia UI**: Todos los formularios e hilos de éxito/error se ven y comportan igual.
3. **Internacionalización**: Usa bloques `crud.*` y `validation.*` globales.
