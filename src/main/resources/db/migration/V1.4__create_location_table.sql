create table public.tb_locations (
    id_location serial primary key,
    name varchar(255) NOT NULL,
    created_at timestamp NOT NULL,
    updated_at timestamp
);
ALTER TABLE tb_devices ADD id_location int NOT NULL;
ALTER TABLE tb_devices ADD CONSTRAINT fk_id_location FOREIGN KEY (id_location) REFERENCES tb_locations(id_location);
