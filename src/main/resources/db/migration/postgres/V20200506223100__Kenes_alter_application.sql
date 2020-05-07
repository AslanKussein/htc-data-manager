alter table htc_dm_real_property
  add column latitude double precision,
  add column longitude double precision;

alter table htc_dm_application
    drop column latitude,
    drop column longitude;