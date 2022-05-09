INSERT INTO public.tb_devices (id_device,name_device, branch_device, measure_unit, created_at, id_location) VALUES(1000,'ABC-123', 'motorola', 'amp', now(), 14);
INSERT INTO public.tb_measures (id, date_time, value, device_id) VALUES(100, now(), 10, 1000);
INSERT INTO public.tb_measures (id, date_time, value, device_id) VALUES(101, now(), 20, 1000);
INSERT INTO public.tb_measures (id, date_time, value, device_id) VALUES(102, now(), 100, 1000);
INSERT INTO public.tb_measures (id, date_time, value, device_id) VALUES(103, now(), 120, 1000);
