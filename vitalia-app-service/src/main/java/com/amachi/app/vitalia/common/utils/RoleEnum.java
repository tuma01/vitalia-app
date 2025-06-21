package com.amachi.app.vitalia.common.utils;

import lombok.Getter;

@Getter
public enum RoleEnum {

    USER("ROLE_USER", "Standard user with basic access"),
    ADMIN("ROLE_ADMIN", "Administrator with full access"),
    DOCTOR("ROLE_DOCTOR", "Medical professional with specialized access"),
    NURSE("ROLE_NURSE", "Nurse with specific permissions"),
    PATIENT("ROLE_PATIENT", "Patient with limited access"),
    MODERATOR("ROLE_MODERATOR", "Moderator with limited administrative access"),
    GUEST("ROLE_GUEST", "Guest user with minimal access"),
    SUPER_ADMIN("ROLE_SUPER_ADMIN", "Super administrator with elevated permissions"),
    CONTENT_CREATOR("ROLE_CONTENT_CREATOR", "User who can create content"),
    CONTENT_EDITOR("ROLE_CONTENT_EDITOR", "User who can edit content"),
    SUPPORT("ROLE_SUPPORT", "User who provides support"),
    ANALYST("ROLE_ANALYST", "User who can analyze data");

    private final String name;
    private final String description;

    RoleEnum(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public String toString() {
        return name + ": " + description;
    }

    /*
        USUARIO("ROLE_USUARIO", "Usuario estándar con acceso básico a las funcionalidades del sistema."),
        ADMINISTRADOR("ROLE_ADMINISTRADOR", "Administrador con acceso completo, capaz de gestionar usuarios y configuraciones."),
        MODERADOR("ROLE_MODERADOR", "Moderador con acceso administrativo limitado, encargado de supervisar contenido y usuarios."),
        INVITADO("ROLE_INVITADO", "Usuario invitado con acceso mínimo, generalmente sin permisos para realizar acciones."),
        SUPER_ADMINISTRADOR("ROLE_SUPER_ADMINISTRADOR", "Superadministrador con permisos elevados para gestionar el sistema en su totalidad."),
        CREADOR_DE_CONTENIDO("ROLE_CREADOR_DE_CONTENIDO", "Usuario que puede crear, publicar y gestionar contenido dentro de la plataforma."),
        EDITOR_DE_CONTENIDO("ROLE_EDITOR_DE_CONTENIDO", "Usuario que puede editar y revisar contenido creado por otros usuarios."),
        SOPORTE("ROLE_SOPORTE", "Usuario que proporciona soporte técnico y asistencia a los usuarios."),
        ANALISTA("ROLE_ANALISTA", "Usuario que puede analizar datos y generar informes sobre el rendimiento del sistema.");


        USUARIO (ROLE_USUARIO): Descripción: Este rol corresponde a un usuario estándar que tiene acceso básico a las funcionalidades del sistema. Pueden realizar tareas como acceder a información y realizar operaciones permitidas, pero no tienen privilegios administrativos.
        ADMINISTRADOR (ROLE_ADMINISTRADOR): Descripción: Este rol proporciona acceso completo al sistema. Los administradores pueden gestionar usuarios, configuraciones, y realizar tareas críticas que afectan a la operación general de la aplicación.
        MODERADOR (ROLE_MODERADOR): Descripción: Moderadores tienen un rol intermedio que les permite supervisar el contenido y los usuarios. Pueden editar o eliminar publicaciones, así como gestionar conflictos entre usuarios. Sin embargo, no tienen la misma autoridad que un administrador.
        INVITADO (ROLE_INVITADO): Descripción: Este rol es para usuarios que acceden al sistema sin una cuenta registrada. Tienen acceso muy limitado, generalmente solo pueden ver información pública y no realizar ninguna acción que requiera autenticación.
        SUPER ADMINISTRADOR (ROLE_SUPER_ADMINISTRADOR): Descripción: Este rol tiene permisos elevados para gestionar el sistema en su totalidad. Pueden realizar todas las acciones de un administrador, así como gestionar otros administradores y realizar configuraciones críticas del sistema.
        CREADOR DE CONTENIDO (ROLE_CREADOR_DE_CONTENIDO): Descripción: Usuarios con este rol tienen la capacidad de crear y publicar contenido dentro de la plataforma. Pueden gestionar su propio contenido y posiblemente también el de otros, dependiendo de los permisos específicos.
        EDITOR DE CONTENIDO (ROLE_EDITOR_DE_CONTENIDO): Descripción: Este rol permite a los usuarios editar y revisar contenido creado por otros. Los editores suelen tener la responsabilidad de garantizar que el contenido cumpla con las pautas y estándares de calidad.
        SOPORTE (ROLE_SOPORTE): Descripción: Los usuarios en este rol proporcionan soporte técnico y asistencia a los usuarios del sistema. Pueden ayudar a resolver problemas y responder preguntas relacionadas con el uso de la aplicación.
        ANALISTA (ROLE_ANALISTA): Descripción: Este rol se centra en el análisis de datos y la generación de informes. Los analistas pueden examinar el rendimiento del sistema, identificar tendencias y proporcionar información valiosa para la toma de decisiones.
     */
}
