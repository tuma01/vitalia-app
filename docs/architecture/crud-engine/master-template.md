# Master Template Reference: Allergies (Alergias)

El módulo de **Alergias** (`src/app/platform/pages/catalog/allergies/*`) es el **Master Template** oficial del proyecto Vitalia. Al desarrollar nuevos módulos CRUD, se debe seguir esta estructura y patrones exactos.

> [!IMPORTANT]
> La convención anterior usaba `pais/` como referencia. Ese módulo tiene un naming antiguo no homogéneo. **Usar `allergies/` como referencia a partir de ahora.**

---

## Estructura de Archivos (Template)

**5 archivos planos dentro de una sola carpeta en plural:**

| Archivo | Propósito |
|---|---|
| `allergies.routes.ts` | Define las rutas `list`, `add`, `edit` |
| `allergies-crud.config.ts` | **Configuración Central**. Columnas del GRID, campos del FORM, adaptador API |
| `allergies-list.component.ts` | **Vista de Lista**. Inyecta `CrudTemplateComponent`, botones de operación |
| `allergies-add.component.ts` | **Vista Crear**. Usa `buildFormFromConfig`, extiende `CrudBaseAddEditComponent` |
| `allergies-edit.component.ts` | **Vista Editar**. Igual que Add, con carga por ID |

> ❌ **No se usan subcarpetas** como `add-pais/`, `edit-pais/` — todo va en la misma carpeta del módulo.

---

## Convención de Nombres (OBLIGATORIO)

| Elemento | Patrón | Ejemplo |
|---|---|---|
| Carpeta | kebab-case plural | `blood-types/` |
| Archivos | `{modules}-*.ts` | `blood-types-list.component.ts` |
| Clases | PascalCase plural | `BloodTypesListComponent` |
| Constante config | SCREAMING plural | `BLOOD_TYPES_CRUD_CONFIG` |
| Selector | `app-{modules}-*` | `app-blood-types-list` |

---

## Patrones Clave

### 1. Claves i18n — prefijo `menu.catalog.`

Todos los módulos de catálogo usan el prefijo `menu.catalog.{module}.*` en sus configuraciones:

```typescript
// ✅ CORRECTO
entityName: 'menu.catalog.allergy.singular',
{ field: 'name', header: 'menu.catalog.allergy.fields.name', ... }

// ❌ INCORRECTO (convención antigua)
entityName: 'entity.allergy',
{ field: 'name', header: 'catalog.allergy.fields.name', ... }
```

### 2. Formulario Declarativo

Las validaciones se definen en `*-crud.config.ts`, no en el componente:
```typescript
{ name: 'code', label: 'menu.catalog.allergy.fields.code', type: 'text', required: true, maxLength: 20 }
```
Y se construye en el componente:
```typescript
protected override form = CrudBaseAddEditComponent.buildFormFromConfig(inject(FormBuilder), this.config);
```

### 3. Mensajes de Feedback Globales
- `common.delete_success` / `common.delete_error`: Para notificaciones de eliminación.
- `common.active` / `common.inactive`: Para tags de estado.
- `crud.*`: Para mensajes de éxito/error al guardar o cargar.
- `validation.*`: Para errores de validación en campos del formulario.

### 4. Adaptador OpenAPI
```typescript
apiService: new OpenApiCrudAdapter<Allergy>(service, {
    getAll: 'getAllAllergies',
    getById: 'getAllergyById',
    create: 'createAllergy',
    update: 'updateAllergy',
    delete: 'deleteAllergy'
})
```

---

## Cómo usar este Template

1. Copia la carpeta `allergies/` a tu nuevo destino en plural (ej: `blood-types/`).
2. Renombra todos los archivos a la forma plural del nuevo módulo.
3. Actualiza las clases (ej: `AllergiesListComponent` → `BloodTypesListComponent`).
4. Actualiza las claves i18n (ej: `menu.catalog.allergy.*` → `menu.catalog.blood_type.*`).
5. Registra la ruta en `catalog.routes.ts`.
6. Agrega las claves i18n en los **3 archivos** (`es-ES.json`, `en-US.json`, `fr-FR.json`).

> Ver guía completa: [`module-implementation-guide.md`](../module-implementation-guide.md)
