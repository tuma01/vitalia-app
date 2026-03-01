# Configuración del Motor (CrudConfig)

La interfaz `CrudConfig<T>` es el corazón del motor. Define qué datos mostrar, cómo pedirlos a la API y cómo capturarlos en un formulario.

## Interfaz `CrudConfig<T>`

```typescript
export interface CrudConfig<T> {
  entityName: string;              // Clave i18n singular (ej: 'menu.catalog.blood_type.singular')
  entityNamePlural: string;        // Clave i18n plural (ej: 'menu.catalog.blood_type.plural')

  getId: (entity: T) => number | string; // Resolvedor de ID

  columns: CrudColumnConfig<T>[];  // Columnas del Grid

  form?: {                         // Configuración del Formulario (Declarativo)
    layout: { columns: number };   // Layout (ej: 2 columnas)
    fields: CrudFormFieldConfig<T>[];
  };

  apiService: CrudApiService<T>;   // Adaptador API

  enableDelete?: boolean;
  enableEdit?: boolean;
  enableAdd?: boolean;

  table?: TableConfig;             // Paginación, hover, etc.
}
```

## Propiedades Clave

### `getId`
Es fundamental para que el motor sea agnóstico al nombre de la propiedad ID del backend (algunos usan `id`, otros `_id`, otros `pk`).
```typescript
getId: (user) => user.id
```

### `columns` (`CrudColumnConfig`)
Define cómo se visualiza la data en el Grid.
- `field`: Nombre de la propiedad en el objeto.
- `header`: Clave de traducción para el encabezado.
- `type`: 'text', 'number', 'date', 'boolean', 'custom'.

### `form` (`CrudFormConfig`)
Define la estructura del formulario reactivo. El motor usa esto para:
1. **Generar campos**: `mat-form-field` con inputs según el `type` ('text', 'number', etc.).
2. **Validar**: Aplica `Validators.required`, `minLength`, `maxLength`, `min`, `max`, y `pattern` automáticamente.
3. **Mapear Errores**: Muestra mensajes i18n desde `validation.*` según el error detectado.

Ejemplo de campo:
```typescript
{ 
  name: 'name', 
  label: 'menu.catalog.blood_type.fields.name',  // ⚠️ siempre con prefijo menu.catalog.
  type: 'text', 
  required: true, 
  minLength: 3 
}
```

### `apiService` (`CrudApiService`)
Interfaz para el adaptador. Se recomienda usar `OpenApiCrudAdapter` para mapear servicios generados por Swagger/OpenAPI.
```typescript
apiService: new OpenApiCrudAdapter<BloodType>(bloodTypeService, {
    getAll:   'getAllBloodTypes',
    getById:  'getBloodTypeById',
    create:   'createBloodType',
    update:   'updateBloodType',
    delete:   'deleteBloodType'
})
```

> [!IMPORTANT]
> El prefijo i18n correcto para todos los módulos del catálogo es **`menu.catalog.{module}.*`** — nunca `entity.*` ni `catalog.*` (sin `menu.`). Ver [`module-implementation-guide.md`](../module-implementation-guide.md) para la referencia completa.
