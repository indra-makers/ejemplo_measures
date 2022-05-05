ALTER TABLE public.tb_devices ADD fk_id_location serial NOT NULL;
ALTER TABLE public.tb_devices ADD CONSTRAINT fk_id_location FOREIGN KEY (fk_id_location) REFERENCES tb_locations(id_location);