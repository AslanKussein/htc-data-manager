alter table htc_dm_event
  drop column targer_application_id;

alter table htc_dm_event
  add column target_application_id bigint
    constraint fk_event_target_app
      references htc_dm_application;