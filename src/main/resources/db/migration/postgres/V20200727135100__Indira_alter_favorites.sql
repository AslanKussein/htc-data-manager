alter table htc_dm_favorites add device_uuid varchar (255);
alter table htc_dm_favorites alter client_login drop not null;

alter table htc_dm_favorites drop constraint htc_dm_favorites_uk;

alter table htc_dm_favorites add constraint htc_dm_favorites_ck_login_Device check (not (client_login is null and device_uuid is null));

alter table htc_dm_favorites add constraint htc_dm_favorites_uk_login_Device unique (client_login, device_uuid, real_property_id);