create table public.tb_measures (
    id serial primary key,
    "date_time" timestamp NOT NULL,
    "value" numeric NOT NULL,
    "device_id" varchar(255) NOT NULL
)
