ALTER TABLE tb_devices ADD id_location int;
ALTER TABLE tb_devices ADD CONSTRAINT fk_id_location FOREIGN KEY (id_location) REFERENCES tb_location(id_location);