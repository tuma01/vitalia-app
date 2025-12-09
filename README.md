# Vitalia App 🏥

**Vitalia App** es un sistema integral de **Gestión Hospitalaria** diseñado para administrar operaciones médicas, personal y pacientes de manera eficiente y escalable.

## 📖 Descripción General

El proyecto está construido como una aplicación modular en **Java**, utilizando **Maven** para la gestión de dependencias y **Docker** para la orquestación de servicios locales (Base de datos y Servidor de Correo).

## 🏗️ Arquitectura y Módulos

El sistema sigue una arquitectura modular para separar responsabilidades:

*   **`vitalia-authentication`**: Módulo encargado de la seguridad, autenticación y gestión de sesiones de usuarios.
*   **`vitalia-service`**: Contiene la lógica de negocio principal ("Core"). Gestiona:
    *   Pacientes (`patient`)
    *   Empleados (`employee`)
    *   Administración y Super Admin (`superadmin`)
    *   Gestión de Personas (`person`)
    *   Tenencia Múltiple (`tenant`)
    *   Avatares y Perfiles (`avatar`)
    *   Notificaciones y Correos (`email`)
*   **`vitalia-geography`**: Gestión de datos geográficos, direcciones o ubicaciones de sedes.
*   **`vitalia-common`**: Utilidades compartidas, DTOs y modelos base utilizados por otros módulos.
*   **`vitalia-test`**: Módulo dedicado a pruebas de integración y unitarias.

## 🚀 Stack Tecnológico

*   **Lenguaje**: Java
*   **Gestión de Proyectos**: Maven
*   **Base de Datos**: MySQL 8.0
*   **Contenedores**: Docker & Docker Compose
*   **Herramientas Desarrollo**: Lombok, MapStruct
*   **Pruebas de Correo**: MailDev

## 🛠️ Configuración y Ejecución Local

### Prerrequisitos
*   Java JDK instalado
*   Maven instalado
*   Docker y Docker Compose instalados

### Pasos para iniciar

1.  **Levantar servicios de infraestructura**:
    Ejecuta el archivo `docker-compose.yml` para iniciar la base de datos MySQL y el servidor de correos MailDev.
    ```bash
    docker-compose up -d
    ```

    *   **MySQL**: Puerto `3306` (Base de datos: `hospital_db`, Usuario: `root`, Pass: `bolivia`)
    *   **MailDev**: Puerto `1080` (Interfaz Web) y `1025` (SMTP)

2.  **Compilar el proyecto**:
    Ejecuta el siguiente comando en la raíz del proyecto para descargar dependencias y compilar los módulos.
    ```bash
    mvn clean install
    ```

3.  **Ejecutar la aplicación**:
    (Instrucciones pendientes según la clase principal definida en `vitalia-service` o el módulo de arranque).

## 🔐 Credenciales por Defecto (Entorno Local)

*   **Base de Datos**:
    *   User: `root`
    *   Pass: `bolivia`
*   **MailDev**: Acceso web en `http://localhost:1080`
