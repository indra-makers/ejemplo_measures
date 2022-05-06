create table public.tbl_locations (
    id serial primary key,
    name varchar(255) NOT NULL,
    created_at timestamp default now()
)