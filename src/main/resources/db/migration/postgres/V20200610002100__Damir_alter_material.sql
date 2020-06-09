alter table htc_dm_dic_material_of_construction
  add column object_type_id bigint
    constraint fk_material_object_type
    references htc_dm_dic_object_type;