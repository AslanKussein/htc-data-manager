alter table htc_dm_purchase_data drop column payed_sum;

alter table htc_dm_purchase_data drop column payed_client_login;

alter table htc_dm_purchase_data drop column is_payed;

alter table htc_dm_purchase_data drop constraint htc_dm_purchase_data_htc_dm_dic_pay_type_id_fk;

alter table htc_dm_purchase_data drop column pay_type_id;

