INSERT INTO public.tb_devices (id_device,name_device, branch_device, measure_unit, created_at) VALUES(1000,'ABC-123', 'motorola', 'amp', now(), 1);
INSERT INTO public.tb_measures (date_time, value, device_id) VALUES(now(), 10, 1000);
INSERT INTO public.tb_measures (date_time, value, device_id) VALUES(now(), 20, 1000);
INSERT INTO public.tb_measures (date_time, value, device_id) VALUES(now(), 10, 1000);
INSERT INTO public.tb_measures (date_time, value, device_id) VALUES(now(), 120, 1000);
