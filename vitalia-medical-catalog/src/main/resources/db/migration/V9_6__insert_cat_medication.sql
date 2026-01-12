-- ============================================================
-- Script: V9_7__create_cat_healthcare_provider.sql
-- Módulo: vitalia-medical-catalog
-- Descripción: Creación de la tabla CAT_HEALTHCARE_PROVIDER.
-- ============================================================

INSERT INTO CAT_MEDICATION (CODE, GENERIC_NAME, COMMERCIAL_NAME, CONCENTRATION, PHARMACEUTICAL_FORM, PRESENTATION, CREATED_BY, CREATED_DATE) VALUES
('MED-001', 'Acetaminofén (Paracetamol)', 'Panadol', '500 mg', 'Tableta', 'Caja x 100 tabletas', 'SYSTEM', CURRENT_TIMESTAMP),
('MED-002', 'Ibuprofeno', 'Advil', '400 mg', 'Tableta', 'Caja x 50 tabletas', 'SYSTEM', CURRENT_TIMESTAMP),
('MED-003', 'Amoxicilina', 'Amoxil', '500 mg', 'Cápsula', 'Frasco x 100 cápsulas', 'SYSTEM', CURRENT_TIMESTAMP),
('MED-004', 'Metformina', 'Glucophage', '850 mg', 'Tableta', 'Caja x 30 tabletas', 'SYSTEM', CURRENT_TIMESTAMP),
('MED-005', 'Losartán Potásico', 'Cozaar', '50 mg', 'Tableta Recubierta', 'Caja x 30 tabletas', 'SYSTEM', CURRENT_TIMESTAMP),
('MED-006', 'Atorvastatina', 'Lipitor', '20 mg', 'Tableta', 'Caja x 30 tabletas', 'SYSTEM', CURRENT_TIMESTAMP),
('MED-007', 'Omeprazol', 'Prilosec', '20 mg', 'Cápsula con gránulos entéricos', 'Frasco x 30 cápsulas', 'SYSTEM', CURRENT_TIMESTAMP),
('MED-008', 'Salbutamol', 'Ventolin', '100 mcg/dosis', 'Inhalador de dosis medida', 'Frasco x 200 dosis', 'SYSTEM', CURRENT_TIMESTAMP),
('MED-009', 'Diclofenaco Sódico', 'Voltaren', '75 mg/3ml', 'Solución Inyectable', 'Ampolla x 3ml', 'SYSTEM', CURRENT_TIMESTAMP),
('MED-010', 'Dipirona', 'Novalgina', '1 g/2ml', 'Solución Inyectable', 'Ampolla x 2ml', 'SYSTEM', CURRENT_TIMESTAMP),
('MED-011', 'Enalapril Maleato', 'Vasotec', '20 mg', 'Tableta', 'Caja x 50 tabletas', 'SYSTEM', CURRENT_TIMESTAMP),
('MED-012', 'Hidroclorotiazida', 'Microzide', '25 mg', 'Tableta', 'Caja x 30 tabletas', 'SYSTEM', CURRENT_TIMESTAMP),
('MED-013', 'Ciprofloxacino', 'Cipro', '500 mg', 'Tableta Recubierta', 'Caja x 10 tabletas', 'SYSTEM', CURRENT_TIMESTAMP),
('MED-014', 'Prednisolona', 'Delta-Cortef', '5 mg', 'Tableta', 'Caja x 30 tabletas', 'SYSTEM', CURRENT_TIMESTAMP),
('MED-015', 'Loratadina', 'Claritin', '10 mg', 'Tableta', 'Caja x 30 tabletas', 'SYSTEM', CURRENT_TIMESTAMP),
('MED-016', 'Insulina Humana NPH', 'Humulin N', '100 UI/ml', 'Suspensión Inyectable', 'Vial x 10ml', 'SYSTEM', CURRENT_TIMESTAMP),
('MED-017', 'Metoclopramida', 'Reglan', '10 mg', 'Tableta', 'Caja x 20 tabletas', 'SYSTEM', CURRENT_TIMESTAMP),
('MED-018', 'Espironolactona', 'Aldactone', '25 mg', 'Tableta', 'Caja x 30 tabletas', 'SYSTEM', CURRENT_TIMESTAMP),
('MED-019', 'Tramadol Clorhidrato', 'Ultram', '50 mg', 'Cápsula', 'Caja x 20 cápsulas', 'SYSTEM', CURRENT_TIMESTAMP),
('MED-020', 'Ácido Acetilsalicílico', 'Aspirina', '100 mg', 'Tableta', 'Caja x 100 tabletas', 'SYSTEM', CURRENT_TIMESTAMP),
('MED-021', 'Ceftriaxona', 'Rocephin', '1 g', 'Polvo para inyectable', 'Frasco vial', 'SYSTEM', CURRENT_TIMESTAMP),
('MED-022', 'Levofloxacina', 'Levaquin', '500 mg', 'Tableta recubierta', 'Caja x 7 tabletas', 'SYSTEM', CURRENT_TIMESTAMP),
('MED-023', 'Valsartán', 'Diovan', '160 mg', 'Tableta recubierta', 'Caja x 28 tabletas', 'SYSTEM', CURRENT_TIMESTAMP),
('MED-024', 'Metoprolol Sucinato', 'Toprol XL', '50 mg', 'Tableta de liberación prolongada', 'Caja x 30 tabletas', 'SYSTEM', CURRENT_TIMESTAMP),
('MED-025', 'Furosemida', 'Lasix', '40 mg', 'Tableta', 'Caja x 20 tabletas', 'SYSTEM', CURRENT_TIMESTAMP),
('MED-026', 'Enoxaparina Sódica', 'Lovenox', '40 mg/0.4ml', 'Solución en jeringa prellenada', 'Caja x 2 jeringas', 'SYSTEM', CURRENT_TIMESTAMP),
('MED-027', 'Clopidogrel', 'Plavix', '75 mg', 'Tableta recubierta', 'Caja x 28 tabletas', 'SYSTEM', CURRENT_TIMESTAMP),
('MED-028', 'Dexametasona', 'Decadron', '4 mg/ml', 'Solución inyectable', 'Ampolla x 1ml', 'SYSTEM', CURRENT_TIMESTAMP),
('MED-029', 'Fluconazol', 'Diflucan', '150 mg', 'Cápsula', 'Caja x 1 cápsula', 'SYSTEM', CURRENT_TIMESTAMP),
('MED-030', 'Ondansetrón', 'Zofran', '8 mg', 'Tableta', 'Caja x 10 tabletas', 'SYSTEM', CURRENT_TIMESTAMP);
