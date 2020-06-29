alter table htc_dm_application
  drop column pay_type_id,
  drop column payed_sum,
  drop column payed_client_login,
  drop column is_payed;

create table htc_dm_application_deposit
(
  id bigserial not null primary key,
  application_id  bigint null
    constraint fk_app_deposit_application
    references htc_dm_application,
  sell_application_id  bigint null
    constraint fk_app_deposit_sell_application
    references htc_dm_application,
  pay_type_id bigint null
    constraint fk_app_deposit_pay_type
    references htc_dm_dic_pay_type,
  payed_sum numeric(19, 2) default null,
  payed_client_login varchar(255) default null,
  print_date  timestamp,
  guid               varchar(100)          null,
  created_by         varchar(255)          not null,
  created_date       timestamp             not null,
  last_modified_by   varchar(255)          not null,
  last_modified_date timestamp             not null,
  is_removed         boolean default false not null
);

alter table htc_dm_application_deposit
  owner to postgres;

comment on table htc_dm_application_deposit is 'Сущность договора аванса/задатка, либо бронирования с КП';
comment on column htc_dm_application_deposit.application_id is 'Ссылка на заявку на покупку';
comment on column htc_dm_application_deposit.sell_application_id is 'Ссылка на заявку на продажу';
comment on column htc_dm_application_deposit.pay_type_id is 'Тип оплаты';
comment on column htc_dm_application_deposit.payed_sum is 'Сумма';
comment on column htc_dm_application_deposit.payed_client_login is 'Логин пользователя, сделавшего оплату';
comment on column htc_dm_application_deposit.print_date is 'Дата формирования договора о задатке/авансе';
comment on column htc_dm_application_deposit.guid is 'Идентификатор договора о задатке/авансе';
