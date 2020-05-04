insert into htc_dm_dic_event_type(id, name_kz, name_ru, name_en) values (1, 'Показ', 'Показ', 'Показ');
insert into htc_dm_dic_event_type(id, name_kz, name_ru, name_en) values (2, 'Звонок', 'Звонок', 'Звонок');
insert into htc_dm_dic_event_type(id, name_kz, name_ru, name_en) values (3, 'Встреча', 'Встреча', 'Встреча');

alter table htc_dm_event
  add column created_by varchar(255) not null,
  add column created_date timestamp not null,
  add column last_modified_by varchar(255) not null,
  add column last_modified_date timestamp not null,
  add column is_removed boolean default false not null;

alter table htc_dm_event
  rename column source_application to source_application_id;
alter table htc_dm_event
  rename column targer_application to target_application_id;

alter table htc_dm_event
  drop column client_id;