create table htc_dm_settings (
id bigserial not null primary key,
key varchar (255) constraint uk_settings
      unique,
val varchar (255),
created_by varchar (255),
created_date timestamp,
last_modified_by varchar (255),
last_modified_date timestamp ,
is_removed boolean
)