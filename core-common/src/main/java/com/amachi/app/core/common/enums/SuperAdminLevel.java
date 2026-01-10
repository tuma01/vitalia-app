package com.amachi.app.core.common.enums;

public enum SuperAdminLevel {
    LEVEL_1, //Máximo control dentro del tenant o sistema. Puede crear o eliminar usuarios, asignar roles, cambiar configuraciones críticas.
    LEVEL_2, //Control medio. Puede gestionar usuarios y roles, pero no tareas críticas de configuración global.
    LEVEL_3  //Nivel básico o soporte. Solo acciones limitadas, ver datos o hacer cambios menores.
}
