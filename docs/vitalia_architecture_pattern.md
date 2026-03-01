# Vitalia Web - Architecture Pattern & Implementation Guide

This document defines the professional and homogeneous architecture model that all modules and sections in the Vitalia platform must strictly follow.

## 1. Directory & File Naming (Pluralization)

All module folders and their primary files (routes, components, services) must use **plural** names for consistency.

- **Bad**: `src/app/platform/pages/catalog/identification-type/`
- **Good**: `src/app/platform/pages/catalog/identification-types/`
- **File Examples**:
  - `identification-types.routes.ts`
  - `identification-types-list.component.ts`
  - `identification-types-crud.config.ts`

## 2. Internationalization (i18n) Structure

Translation keys for functional sections must be at the **root level** of the i18n JSON files to ensure accessibility and synchronization between menus and configurations.

- **Structure**:
  ```json
  {
    "catalog": {
      "identification_type": {
        "singular": "Tipo de Identificación",
        "plural": "Tipos de Identificación",
        "fields": { ... }
      }
    }
  }
  ```
- **Usage in Code**: Always use `catalog.entity_name.key` (e.g., `catalog.identification_type.singular`). Do **not** nest under `menu.catalog` or use `clinical.*`.

## 3. Routing Configuration

Parent routes must lazily load the pluralized route files.

- **Example (`catalog.routes.ts`)**:
  ```typescript
  {
      path: 'identification-types',
      loadChildren: () => import('./identification-types/identification-types.routes').then(m => m.routes)
  }
  ```

## 4. CRUD Components & Configurations

### CRUD Config (`*-crud.config.ts`)
Must use the root i18n keys and consistent naming.

```typescript
export const IDENTIFICATION_TYPES_CRUD_CONFIG = (): CrudConfig<IdentificationType> => {
    return {
        entityName: 'catalog.identification_type.singular',
        entityNamePlural: 'catalog.identification_type.plural',
        // ... columns and fields
    };
};
```

### Components
All CRUD components (Add, Edit, List) must inherit from `CrudBaseAddEditComponent` or similar, using the `entityNameKey` from the root i18n.

- **Example**: `protected override entityNameKey = 'catalog.identification_type.singular';`

## 5. Menu Configuration (`super-admin-menu.json`)

Menu IDs and routes must be synchronized with the pluralized paths.

- **Bad**: `"id": "identification-type-list"`, `"route": "/platform/catalog/identification-type"`
- **Good**: `"id": "identification-types-list"`, `"route": "/platform/catalog/identification-types"`

---

> [!IMPORTANT]
> Any new section or modification to existing sections must strictly adhere to this model. No custom or ad-hoc implementations are allowed.
