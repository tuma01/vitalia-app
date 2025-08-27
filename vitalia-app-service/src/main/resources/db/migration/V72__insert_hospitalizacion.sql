INSERT INTO HOSPITALIZACION (
    FECHA_INGRESO, FECHA_EGRESO, MOTIVO_INGRESO, MOTIVO_EGRESO, DIAGNOSTICO, TRATAMIENTO,
    OBSERVACIONES, FK_ID_ROOM, FK_ID_PATIENT_VISIT, FK_ID_DEPARTAMENTO_HOSPITAL, PROCEDIMIENTOS_REALIZADOS,
    INSTRUCCIONES_PLAN_ALTA, CONDICION_AL_ALTA, COMPLICACIONES_DURANTE_ESTANCIA, RESUMEN_EXAMEN_FISICO_INGRESO,
    TIPO_ADMISION
) VALUES (
    '2025-07-21 08:30:00', '2025-07-25 14:00:00', 'Dolor abdominal severo', 'Mejora clínica', 'Apendicitis aguda', 'Cirugía laparoscópica',
    'Paciente respondió bien al tratamiento',
    1,  -- FK_ID_ROOM
    1,  -- FK_ID_PATIENT_VISIT
    1,  -- FK_ID_DEPARTAMENTO_HOSPITAL
    'Apendicectomía realizada con éxito',
    'Reposo, dieta blanda, control en 7 días',
    'Estable',
    'Ninguna complicación',
    'Paciente alerta, sin signos de infección',
    'URGENTE'
);

INSERT INTO MEDICAMENTOS_AL_ALTA (ID_HOSPITALIZACION, MEDICAMENTO) VALUES
(1, 'Paracetamol 500mg'),
(1, 'Omeprazol 20mg'),
(1, 'Ibuprofeno 400mg');

COMMIT;