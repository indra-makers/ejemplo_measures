create table public.tb_measures (
    id serial primary key,
    date_time timestamp NOT NULL,
    "value" numeric NOT NULL,
    device_id int NOT NULL,
    foreign key(device_id) references tb_devices(id_device)
)
