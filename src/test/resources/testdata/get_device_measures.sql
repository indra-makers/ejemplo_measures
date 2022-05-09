INSERT INTO public.tb_devices (id_device,name_device, branch_device, measure_unit, created_at) VALUES(2000,'ABC-123', 'motorola', 'amp', now());
INSERT INTO public.tb_measures (date_time, value, device_id) VALUES(now(), 10, 2000);
INSERT INTO public.tb_measures (date_time, value, device_id) VALUES(now(), 20, 2000);
INSERT INTO public.tb_measures (date_time, value, device_id) VALUES(now(), 10, 2000);
INSERT INTO public.tb_measures (date_time, value, device_id) VALUES(now(), 120, 2000);
