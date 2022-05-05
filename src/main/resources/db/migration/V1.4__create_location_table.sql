CREATE TABLE public.tb_locations(
    id_location serial primary key,
    name_location varchar(50) not null,
    created_at timestamp default now(),
    update_at timestamp
)