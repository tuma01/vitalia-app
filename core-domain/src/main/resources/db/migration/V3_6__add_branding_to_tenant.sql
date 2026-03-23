-- ============================================
-- Script: V3_6__add_branding_to_tenant.sql
-- Adición de Branding a DMN_TENANT
-- ============================================

ALTER TABLE DMN_TENANT ADD COLUMN LOGO_URL VARCHAR(255) AFTER DESCRIPTION;
ALTER TABLE DMN_TENANT ADD COLUMN FAVICON_URL VARCHAR(255) AFTER LOGO_URL;
