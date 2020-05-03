-- auto-generated definition
create sequence htc_dm_mortgage_id_seq;

alter sequence htc_dm_mortgage_id_seq owner to postgres;


-- auto-generated definition
create table htc_dm_mortgage
(
    id                bigint  default nextval('htc_dm_mortgage_id_seq'::regclass) not null
        constraint htc_dm_mortgage_pk
            primary key,
    login               varchar(255)                                                not null,
    credit_sum        bigint                                                      not null,
    credit_term       bigint                                                      not null,
    total_income      bigint                                                      not null,
    active_credit     boolean default false                                       not null,
    active_credit_sum bigint,
    is_removed     boolean default false                                       not null
);

comment on column htc_dm_mortgage.login is 'Логин';

comment on column htc_dm_mortgage.credit_sum is 'Сумма кредита, тг';

comment on column htc_dm_mortgage.credit_term is 'Срок кредита, мес';

comment on column htc_dm_mortgage.total_income is 'Общий доход, тг';

comment on column htc_dm_mortgage.active_credit is 'Действующие кредиты';

comment on column htc_dm_mortgage.active_credit_sum is 'Платеж по действующим займам, тг/мес';

alter table htc_dm_mortgage
    owner to postgres;

