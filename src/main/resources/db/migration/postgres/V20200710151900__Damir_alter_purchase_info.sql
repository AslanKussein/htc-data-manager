alter table htc_dm_purchase_info
  add column sewerage_id bigint
    constraint fk_purchase_info_sewerage
      references htc_dm_dic_sewerage,
  add column heating_system_id bigint
    constraint fk_purchase_info_heating
      references htc_dm_dic_heating_system;