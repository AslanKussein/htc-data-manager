alter table htc_dm_dic_city
  add column is_removed boolean default false;
alter table htc_dm_dic_country
  add column is_removed boolean default false;
alter table htc_dm_dic_district
  add column is_removed boolean default false;
alter table htc_dm_dic_heating_system
  add column is_removed boolean default false;
alter table htc_dm_dic_material_of_construction
  add column is_removed boolean default false;
alter table htc_dm_dic_parking_type
  add column is_removed boolean default false;
alter table htc_dm_dic_possible_reason_for_bidding
  add column is_removed boolean default false;
alter table htc_dm_dic_property_developer
  add column is_removed boolean default false;
alter table htc_dm_dic_sewerage
  add column is_removed boolean default false;
alter table htc_dm_dic_street
  add column is_removed boolean default false;
alter table htc_dm_dic_type_of_elevator
  add column is_removed boolean default false;
alter table htc_dm_dic_yard_type
  add column is_removed boolean default false;