alter table htc_dm_deposit_numb
add last_modified_by varchar(255);

alter table htc_dm_deposit_numb
add last_modified_date timestamp ;

alter table htc_dm_deposit_numb
add is_removed boolean ;

alter table htc_dm_deposit_numb
add created_by varchar (255);


alter table htc_dm_deposit_numb
add created_date timestamp ;