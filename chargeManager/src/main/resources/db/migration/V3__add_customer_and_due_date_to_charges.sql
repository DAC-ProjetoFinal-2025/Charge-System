ALTER TABLE charges 
ADD COLUMN customer VARCHAR(255),
ADD COLUMN due_date VARCHAR(20);

-- Se você já tiver dados e quiser torná-las obrigatórias depois:
-- UPDATE charges SET customer = 'UNKNOWN', due_date = '2026-01-01' WHERE customer IS NULL;
-- ALTER TABLE charges ALTER COLUMN customer SET NOT NULL;
-- ALTER TABLE charges ALTER COLUMN due_date SET NOT NULL;
