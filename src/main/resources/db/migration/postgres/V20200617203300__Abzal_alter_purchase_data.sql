alter table htc_dm_purchase_data
    add column pay_type_id bigserial null,
    add column payed_sum numeric(19, 2) null,
    add column payed_client_login varchar(255) null,
    add column is_payed boolean null;

alter table htc_dm_purchase_data
    add constraint htc_dm_purchase_data_htc_dm_dic_pay_type_id_fk
        foreign key (pay_type_id) references htc_dm_dic_pay_type;

