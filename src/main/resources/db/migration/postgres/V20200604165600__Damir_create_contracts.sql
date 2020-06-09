create table htc_dm_dic_contract_status
(
  id         bigserial             not null
    primary key,
  name_en    varchar(255)          not null,
  name_kz    varchar(255)          not null,
  name_ru    varchar(255)          not null,
  code       varchar(255)          not null
    constraint uk_contract_status_code
      unique,
  is_removed boolean default false not null
);

alter table htc_dm_dic_contract_status
  owner to postgres;

comment on table htc_dm_dic_contract_status is 'Справочник статусов договоров ОУ';

create table htc_dm_application_contract
(
  id                 bigserial             not null primary key,
  application_id     bigint                not null
    constraint fk_contract_application
      references htc_dm_application,
  contract_number    varchar(50)           null
    constraint uk_app_contract_number
      unique,
  contract_period    timestamp             null,
  print_date         timestamp             null,
  contract_sum       numeric(20)           null,
  commission         numeric(10)           null,
  is_exclusive       boolean               null,
  status_id          bigint                not null
    constraint fk_contract_status
      references htc_dm_dic_contract_status,
  guid               varchar(100)          null,
  created_by         varchar(255)          not null,
  created_date       timestamp             not null,
  last_modified_by   varchar(255)          not null,
  last_modified_date timestamp             not null,
  is_removed         boolean default false not null
);

alter table htc_dm_application_contract
  owner to postgres;

comment on table htc_dm_application_contract is 'Сущность договора ОУ';
comment on column htc_dm_application_contract.contract_period is 'Срок действия договора';
comment on column htc_dm_application_contract.contract_number is 'Номер договора';
comment on column htc_dm_application_contract.print_date is 'Дата печати договора';
comment on column htc_dm_application_contract.contract_sum is 'Сумма по договору';
comment on column htc_dm_application_contract.commission is 'Размер комиссии';
comment on column htc_dm_application_contract.is_exclusive is 'Признак эксклюзивного договора';
comment on column htc_dm_application_contract.guid is 'Идентификатор прикрепленого документа';