create table htc_dm_deposit_numb (
id bigserial not null primary key,
code varchar (50) not null constraint uk_htc_dm_deposit_numb
      unique,
nmb bigint not null
);



alter table htc_dm_application_deposit add contract_number varchar(50) not null ;
alter table htc_dm_application_deposit add constraint uk_htc_dm_application_deposit unique (contract_number);