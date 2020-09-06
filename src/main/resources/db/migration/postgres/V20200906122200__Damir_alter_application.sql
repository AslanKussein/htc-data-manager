alter table htc_dm_application
  drop column view_count;

alter table htc_dm_real_property
  add column view_count integer null;

update htc_dm_real_property set view_count = 0 where view_count is null;