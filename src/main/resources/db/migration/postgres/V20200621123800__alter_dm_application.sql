alter table htc_dm_application add column application_source_id bigint;

create table htc_dm_dic_application_source (
id bigint constraint htc_dm_dic_application_source_pkey
      primary key ,
code varchar(255) not null  constraint uk_app_source_code
      unique,
name_en varchar(255),
name_kz varchar(255),
name_ru varchar(255),
is_removed boolean default false not null
);


alter table htc_dm_application add constraint  htc_dm_application_fk_source foreign key (application_source_id) references htc_dm_dic_application_source (id) ;

COMMENT on table htc_dm_dic_application_source is 'Справочник источника (создания) заявки';

insert into htc_dm_dic_application_source (id,  code, name_en, name_kz, name_ru, is_removed) values (1, '002001', 'from CRM', 'CRM-дан', 'из CRM', false );
insert into htc_dm_dic_application_source (id,  code, name_en, name_kz, name_ru, is_removed) values (2, '002002', 'from CA', 'КҚ-дан', 'из КП', false );