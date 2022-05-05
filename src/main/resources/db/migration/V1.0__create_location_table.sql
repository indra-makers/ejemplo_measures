create table public.tb_locations (
    id_location serial primary key,
    name varchar(50) NOT NULL,
    create_at timestamp,
    update_at timestamp
)
