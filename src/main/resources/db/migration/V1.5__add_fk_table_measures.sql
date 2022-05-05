ALTER TABLE tb_measures ALTER COLUMN device_id TYPE int USING device_id::integer;
ALTER TABLE tb_measures ADD CONSTRAINT fk_id_device FOREIGN KEY (device_id) REFERENCES tb_devices(id_device);