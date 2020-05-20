alter table htc_dm_event
  drop column source_application,
  drop column targer_application;

alter table htc_dm_event
  add column source_application_id bigint
    constraint fk_event_source_app
      references htc_dm_application,
  add column targer_application_id bigint
    constraint fk_event_target_app
      references htc_dm_application;