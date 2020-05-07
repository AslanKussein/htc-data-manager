alter table htc_dm_application
add column mortgage_sale boolean default false;

alter table htc_dm_application
    add column is_pledged boolean default false;

alter table htc_dm_application
    add column property_type varchar(255);

alter table htc_dm_application
    add column layout_type varchar(255);

alter table htc_dm_application
    add column objects_type_market varchar(255);