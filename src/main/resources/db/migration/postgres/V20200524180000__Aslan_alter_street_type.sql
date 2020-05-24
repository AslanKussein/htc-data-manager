alter table htc_dm_dic_street
    add constraint htc_dm_dic_street_htc_dm_street_type_id_fk
        foreign key (street_type_id) references htc_dm_street_type;