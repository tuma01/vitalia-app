-- *******************************************************
-- Script de Limpieza de Identidad Pura (SaaS Elite)
-- *******************************************************
-- Elimina columnas de discriminador de herencia en DMN_PERSON.

ALTER TABLE DMN_PERSON DROP COLUMN PERSON_TYPE;
