alter table htc_dm_dic_district
  add column city_id bigint
    constraint fk_district_city
    references htc_dm_dic_city;


alter table htc_dm_dic_possible_reason_for_bidding
  add column operation_type_id bigint
    constraint fk_possible_reason_operation
    references htc_dm_dic_operation_type;