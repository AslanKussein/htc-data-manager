create table htc_dm_credit_programm
(
    id                 bigserial             not null primary key,
    name_ru            varchar(255)          not null,
    name_en            varchar(255)          not null,
    name_kz            varchar(255)          not null,
    min_down_payment   bigint                not null,
    max_down_payment   bigint                not null,
    min_credit_period  numeric(19,2)         not null,
    max_credit_period  numeric(19,2)         not null,
    percent            numeric(19,2)         not null,
    is_removed         boolean default false not null
);

comment on table htc_dm_credit_programm is 'Справочник Программ кредитования';
comment on column htc_dm_credit_programm.name_ru is 'Наименование программы кредитования ru' ;
comment on column htc_dm_credit_programm.name_kz is 'Наименование программы кредитования kz';
comment on column htc_dm_credit_programm.name_en is 'Наименование программы кредитования en';
comment on column htc_dm_credit_programm.min_down_payment is 'Минимальный первоначальный взнос';
comment on column htc_dm_credit_programm.max_down_payment is 'Максимальный первоначальный взнос';
comment on column htc_dm_credit_programm.min_credit_period is 'Минимальный срок (год)';
comment on column htc_dm_credit_programm.max_credit_period is 'Максимальный срок (год)';
comment on column htc_dm_credit_programm.percent is 'Ставка вознаграждения (%)';