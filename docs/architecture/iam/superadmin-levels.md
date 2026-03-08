# Niveles de Acceso SuperAdmin (RBAC)

Este documento detalla la jerarquía y permisos de los administradores del Tenant Global (SuperAdmins) en la plataforma Vitalia.

## Jerarquía de Niveles

La plataforma define tres niveles de acceso para asegurar la integridad de la configuración global y la infraestructura.

### LEVEL_1: Maestro (Master Admin)
*   **Propósito**: Control total sobre la infraestructura y gobernanza de la plataforma.
*   **Permisos**:
    *   **Lectura**: Ver todos los SuperAdmins y configuraciones.
    *   **Creación/Edición**: Puede agregar nuevos SuperAdmins o modificar existentes.
    *   **Eliminación**: Es el **único** nivel con autoridad para eliminar otros SuperAdmins.
    *   **Restricción**: El sistema protege al "último administrador" impidiendo su eliminación accidental.

### LEVEL_2: Operador (System Operator)
*   **Propósito**: Gestión operativa y soporte técnico de nivel medio.
*   **Permisos**:
    *   **Lectura**: Ver la lista de SuperAdmins.
    *   **Creación/Edición**: Puede agregar o editar perfiles de SuperAdmin (para tareas de onboarding).
    *   **Eliminación**: **PROHIBIDO**. Este nivel no tiene permisos para destruir cuentas de administradores de infraestructura.

### LEVEL_3: Consulta (Audit / Read-Only)
*   **Propósito**: Auditoría, soporte de primer nivel o visualización de reportes.
*   **Permisos**:
    *   **Lectura**: Ver la lista de SuperAdmins y detalles.
    *   **Escritura (Crear/Editar/Borrar)**: **PROHIBIDO**. Todas las acciones que modifiquen el estado del sistema están bloqueadas para este nivel.

---

## Tabla de Matriz de Permisos (RBAC)

| Acción | LEVEL_1 (Maestro) | LEVEL_2 (Operador) | LEVEL_3 (Consulta) |
| :--- | :---: | :---: | :---: |
| Listar Administradores | ✅ | ✅ | ✅ |
| Crear Administrador | ✅ | ✅ | ❌ |
| Editar Administrador | ✅ | ✅ | ❌ |
| Eliminar Administrador | ✅ | ❌ | ❌ |

## Implementación Técnica

Las restricciones se validan en dos capas:
1.  **Frontend**: Los botones de acción (Agregar, Editar, Borrar) se ocultan o deshabilitan según el nivel del usuario logueado.
2.  **Backend**: El servicio `SuperAdminServiceImpl` valida el nivel del `User` autenticado antes de persistir cualquier cambio, lanzando `AccessDeniedException` si se viola la matriz de permisos.
