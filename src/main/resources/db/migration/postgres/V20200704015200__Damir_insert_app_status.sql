select setval('htc_dm_dic_application_status_id_seq', max(id)) from htc_dm_dic_application_status;

insert into htc_dm_dic_application_status (name_en, name_kz, name_ru, code, is_removed) values ('Согласование успешной реализации заявки', 'Согласование успешной реализации заявки', 'Согласование успешной реализации заявки', '002011', false);
insert into htc_dm_dic_application_status (name_en, name_kz, name_ru, code, is_removed) values ('Согласование не реализованной заявки', 'Согласование не реализованной заявки', 'Согласование не реализованной заявки', '002012', false);

update htc_dm_dic_application_status set name_en = 'Успешно реализован', name_kz = 'Успешно реализован', name_ru = 'Успешно реализован' where code = '002008';
update htc_dm_dic_application_status set name_en = 'Не реализован', name_kz = 'Не реализован', name_ru = 'Не реализован' where code = '002009';

alter table htc_dm_application
  add column target_application_id bigint null
    constraint fk_app_target_application
      references htc_dm_application;