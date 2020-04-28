create table htc_dm_assignment
(
  id                 bigserial             not null primary key,
  created_by         varchar(255)          not null,
  created_date       timestamp             not null,
  last_modified_by   varchar(255)          not null,
  last_modified_date timestamp             not null,
  is_removed         boolean default false not null,
  agent              varchar(255)          not null,
  application_id     bigint                not null
    constraint fk_assignment_application
      references htc_dm_application
);

comment on table htc_dm_assignment is ''Таблица истории назначения заявок агентам'';
comment on column htc_dm_assignment.agent is ''Логин агента'';
comment on column htc_dm_assignment.application_id is ''ID заявки (htc_dm_application)'';