create table htc_dm_real_property_file
(
  id                         bigserial             not null primary key,
  application_id             bigint                not null
    constraint fk_rp_file_application
      references htc_dm_application,
  real_property_id           bigint                not null
    constraint fk_rp_file_real_property
      references htc_dm_real_property,
  metadata_status_id         bigint                not null
    constraint fk_rp_file_metadata_status
      references htc_dm_dic_metadata_status,
  files_map                    jsonb
);

alter table htc_dm_real_property_file
  owner to postgres;

comment on table htc_dm_real_property_file is 'Прикрепленные файлы по продаже объекта недвижимости';

alter table htc_dm_sell_data
  drop column files_map;