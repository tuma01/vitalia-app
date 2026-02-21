# Master Template Reference: País (Country)

El módulo de **País** (`src/app/platform/pages/geography/pais/*`) se ha establecido como el **Master Template** oficial del proyecto Vitalia. Al desarrollar nuevos módulos CRUD, se debe seguir esta estructura y patrones exactos.

## Estructura de Archivos (Template)

| Archivo | Propósito |
|---|---|
| `pais-crud.config.ts` | **Configuración Central**. Define columnas del GRID, campos del FORM y adaptador API. |
| `paises/paises.component.ts` | **Vista de Lista**. Inyecta `CrudTemplateComponent` y configura botones de operación. |
| `add-pais/add-pais.component.ts`| **Vista Crear**. Usa `buildFormFromConfig` para generar el formulario desde la config. |
| `edit-pais/edit-pais.component.ts`| **Vista Editar**. Idem a Add, con lógica de carga por ID. |

## Patrones Clave

### 1. Formulario Declarativo
En lugar de definir el `FormGroup` manualmente, se definen las validaciones en `pais-crud.config.ts`:
```typescript
{ name: 'iso', label: '...', type: 'text', required: true, minLength: 2, maxLength: 2 }
```
Y se construye en el componente usando:
```typescript
protected override form = CrudBaseAddEditComponent.buildFormFromConfig(inject(FormBuilder), this.config);
```

### 2. Mensajes de Feedback Globales
El sistema utiliza bloques de traducción estandarizados:
- `crud.*`: Para mensajes de éxito/error al guardar o cargar.
- `validation.*`: Para mensajes de error en campos del formulario.
- `paginator.*`: Para las etiquetas de paginación de Angular Material.

### 3. Adaptador OpenAPI
Usa `OpenApiCrudAdapter` para conectar el motor con los servicios generados por Swagger, manteniendo el motor agnóstico a los nombres de métodos del backend.

---

## Cómo usar este Template
1. Copia la carpeta `pais` a tu nuevo destino (ej: `department`).
2. Renombra los archivos y clases (ej: `Pais` -> `Department`).
3. Actualiza las claves de traducción en la configuración (`entity.country` -> `entity.department`).
4. Mapea los métodos de la API en el adaptador.
