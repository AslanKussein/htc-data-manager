alter table htc_dm_purchase_data
  drop column districts;

create table htc_dm_purchase_data_districts
(
  purchase_data_id bigint not null
    constraint fk_purchase_data_data
      references htc_dm_purchase_data,
  district_id bigint not null
    constraint fk_purchase_data_district
      references htc_dm_dic_district
);

insert into htc_dm_purchase_data_districts(purchase_data_id, district_id)
select id, district_id from htc_dm_purchase_data where district_id is not null;
