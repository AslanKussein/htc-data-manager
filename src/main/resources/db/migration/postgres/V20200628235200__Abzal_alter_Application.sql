alter table htc_dm_application
    add column pay_type_id bigint default null,
    add column payed_sum numeric(19, 2) default null,
    add column payed_client_login varchar(255) default null,
    add column is_payed boolean  default false;

alter table htc_dm_application
    add constraint htc_dm_purchase_data_htc_dm_dic_pay_type_id_fk
        foreign key (pay_type_id) references htc_dm_dic_pay_type;

