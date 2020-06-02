alter table htc_dm_favorites
	add constraint htc_dm_favorites_htc_dm_real_property_id_fk
		foreign key (real_property_id) references htc_dm_real_property;

alter table htc_dm_favorites
	add constraint htc_dm_favorites_uk
		unique (client_login, real_property_id);

