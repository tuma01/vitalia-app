package com.amachi.app.vitalia.common.utils;

import lombok.Getter;

@Getter
public enum TypeRoomEnum {

    ESTANDAR("Habitación estándar"),
    SUITE("Habitación lujosa con baño privado"),
    CUIDADOS_INTENSIVOS("Habitación para cuidados intensivos"),
    PEDIATRICOS("Habitación diseñada específicamente para niños, con características adaptadas a sus necesidades"),
    PARTOS("Habitación especializada equipada para el parto y el cuidado postparto"),
    AISLAMIENTO("Habitación aislada para pacientes con enfermedades contagiosas, con medidas especiales para prevenir la propagación de la enfermedad"),
    OBSERVACION("Habitación donde se monitorean pacientes durante un período breve para evaluar su condición antes de decidir si necesitan ser admitidos en el hospital o dados de alta"),
    ONCOLOGIA("Habitación especializada para pacientes con cáncer, con equipos y personal especializados en el tratamiento de esta enfermedad"),
    DIALISIS("Habitación equipada con máquinas de diálisis para pacientes con insuficiencia renal"),
    CUIDADOS_PALIATIVOS("Habitación diseñada para brindar cuidados compasivos a pacientes con enfermedades terminales, enfocándose en el alivio del dolor y el apoyo emociona");

    public final String label;

    TypeRoomEnum(String label) {
        this.label = label;
    }
}
