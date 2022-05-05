create table public.tb_devices (
    id_device serial primary key,
    name_device varchar(255) NOT NULL,
    branch_device varchar(255) NOT NULL,
    measure_unit varchar(5) NOT NULL,
    location_id int not null,
    created_at timestamp,
    foreign key(location_id) references tb_locations(id_location)
)