INSERT INTO public.tb_locations(id_location, name, created_at) VALUES (999, 'Test_name', now());
INSERT INTO public.tb_devices(id_device, name_device, branch_device, measure_unit, id_location) VALUES (999, 'ZZZ-999', 'zzz', 'yota', 999);

INSERT INTO public.tb_measures(date_time, value, device_id) VALUES (now(), 66, 999);
INSERT INTO public.tb_measures(date_time, value, device_id) VALUES (now(), 56, 999);
INSERT INTO public.tb_measures(date_time, value, device_id) VALUES (now(), 76, 999);
INSERT INTO public.tb_measures(date_time, value, device_id) VALUES (now(), 46, 999);
