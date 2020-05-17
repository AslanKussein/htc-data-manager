alter table htc_dm_general_characteristics
  add column ceiling_height numeric(19,2),
  add column house_condition_id bigint
    constraint fk_gen_characteristics_house_cond
      references htc_dm_dic_house_condition;

alter table htc_dm_real_property_metadata
  drop column house_condition_id;