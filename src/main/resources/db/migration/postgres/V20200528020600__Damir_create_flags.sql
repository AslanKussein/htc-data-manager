create table htc_dm_dic_application_flag
(
  id                bigserial             not null primary key,
  name_en           varchar(255)          not null,
  name_kz           varchar(255)          not null,
  name_ru           varchar(255)          not null,
  operation_type_id bigint
    constraint fk_app_flag_operation
      references htc_dm_dic_operation_type,
  is_removed        boolean default false not null
);

alter table htc_dm_dic_application_flag
  owner to postgres;

insert into htc_dm_dic_application_flag (id, name_en, name_kz, name_ru, operation_type_id, is_removed)
values (1, 'срочно', 'срочно', 'срочно',
        (select id from htc_dm_dic_operation_type where is_removed = false and code = '001001'), false);

insert into htc_dm_dic_application_flag (id, name_en, name_kz, name_ru, operation_type_id, is_removed)
values (2, 'онлайн продажа', 'онлайн продажа', 'онлайн продажа',
        (select id from htc_dm_dic_operation_type where is_removed = false and code = '001002'), false);
insert into htc_dm_dic_application_flag (id, name_en, name_kz, name_ru, operation_type_id, is_removed)
values (3, 'горящее предложение', 'горящее предложение', 'горящее предложение',
        (select id from htc_dm_dic_operation_type where is_removed = false and code = '001002'), false);
insert into htc_dm_dic_application_flag (id, name_en, name_kz, name_ru, operation_type_id, is_removed)
values (4, 'без комиссии', 'без комиссии', 'без комиссии',
        (select id from htc_dm_dic_operation_type where is_removed = false and code = '001002'), false);

select setval('htc_dm_dic_application_flag_id_seq', max(id)) from htc_dm_dic_application_flag;

insert into htc_dm_dic_all_dict(code, name_kz, name_ru, name_en, is_editable, is_removed)
values ('ApplicationFlag', 'Признак заявки', 'Признак заявки', 'Application flag', true, false);

alter table htc_dm_purchase_data
  add column application_flags jsonb;
alter table htc_dm_sell_data
  add column application_flags jsonb;