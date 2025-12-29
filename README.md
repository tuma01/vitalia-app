# Vitalia Backend Architecture

## 🛡️ Security & Multi-Tenancy

### Zero Trust Data Isolation
The application implements a strict **Zero Trust** data isolation strategy to prevent cross-tenant data leakage.

#### 1. Database-Level Enforcement (Hibernate Filters)
*   Entities like `Person` (which can be shared across tenants) are protected by a Hibernate `@Filter`.
*   **The Rule:** A Person is only visible if a record exists in the `PERSON_TENANT` table linking them to the *Current Tenant*.
*   **Default Behavior:** If no Tenant is selected (e.g. valid global login but no context), the filter defaults to `tenantId = -1`, ensuring **NO DATA** is returned.

#### 2. Automatic Enforcement (AOP)
*   **`TenantFilterAspect`**: Automatically intercepts every `@Transactional` method or Service execution using an `@Around` advice to ensure cleanup.
*   **Lifecycle:** Enables filter -> Executes Method -> Disables filter (finally block).
*   **Zero Trust:** If Context is empty using `tenantId = -1`.
*   **Security Guarantee:** Developers do NOT need to manually add `WHERE tenant_id = ?`. The isolation is transparent.
*   **🚨 ARCHITECTURE RULE:** Developers are **STRICTLY FORBIDDEN** from calling `session.disableFilter("tenantFilter")` in Services or Repositories. Filter lifecycle is exclusive to the Infrastructure Layer.

#### 3. Role Protection (Last Man Standing)
*   **SuperAdmin**: Cannot delete the last remaining SuperAdmin.
*   **TenantAdmin**: Cannot delete the last remaining Administrator of a Tenant (prevents lockout).

### Authentication
*   **JWT & Refresh Token:** Standard secure flow.
*   **Tenant Scoped:** Login is always contextual to a Tenant (or Global for SuperAdmin).
*   **Tenant Soft Delete:** Deleting a tenant (`delete()`) performs a Soft Delete (`isActive = false`). The Authentication service strictly blocks login for any user belonging to an inactive tenant.

### ✅ Verification Guide (Manual Test)

Since automated tests are isolated in a specific profile, you can verify this behavior manually in your Development environment using HTTP Clients (Postman/Curl).

#### 1. Setup Data
Assume we have:
- **Tenant A (ID: 100)** -> Has Person "Juan"
- **Tenant B (ID: 200)** -> Has Person "Pedro"
- **SuperAdmin** -> Global Access (but strictly filtered by Context)

#### 2. Test Cases

**Case A: Isolation (Tenant A vs B)**
Authenticate as TenantAdmin for **Tenant A**. Call `GET /api/v1/persons`.
- **Expected:** Returns only "Juan".
- **Security Check:** If "Pedro" appears, **ISOLATION IS BROKEN**.

Authenticate as TenantAdmin for **Tenant B**. Call `GET /api/v1/persons`.
- **Expected:** Returns only "Pedro".
- **Security Check:** If "Juan" appears, **ISOLATION IS BROKEN**.

**Case B: Zero Trust (No Context)**
Authenticate as **SuperAdmin** (Global Login), assuming NO tenant is selected in header `X-Tenant-ID`.
Call `GET /api/v1/persons`.
- **Expected:** Returns `[]` (Empty List).
- **Why?** Even SuperAdmin is subject to Zero Trust. To see data, SuperAdmin MUST impersonate a tenant explicitly (e.g., via `X-Tenant-ID: 100`).

#### 3. Troubleshooting
If you suspect data leakage:
1. Check logs for `TenantFilterAspect`: `🛡️ Tenant Isolation ACTIVE for TenantID: 100`.
2. Ensure you are NOT using native queries (`nativeQuery = true`) in Repositories without manually adding the join, as Hibernate Filters do not apply to native SQL.
