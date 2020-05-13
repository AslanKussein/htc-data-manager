alter table htc_dm_application
    drop column client_id,
    add column client_login varchar(222) not null;
