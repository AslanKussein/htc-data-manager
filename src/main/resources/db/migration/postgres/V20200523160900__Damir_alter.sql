alter table htc_dm_real_property_file
  add column created_by         varchar(255)          not null,
  add column created_date       timestamp             not null,
  add column last_modified_by   varchar(255)          not null,
  add column last_modified_date timestamp             not null,
  add column is_removed         boolean default false not null;