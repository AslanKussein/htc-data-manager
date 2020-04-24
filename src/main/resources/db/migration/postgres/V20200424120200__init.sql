create table htc_dm_application
(
 id bigserial not null
 constraint htc_dm_application_pkey
 primary key,
 created_by varchar(255) not null,
 created_date timestamp not null,
 last_modified_by varchar(255) not null,
 last_modified_date timestamp not null,
 is_removed boolean default false not null,
 the_amount_of_the_contract numeric(19, 2),
 contract_period date,
 encumbrance boolean,
 is_exchange boolean,
 is_commission_included_in_the_price boolean,
 mortgage boolean,
 note varchar(255),
 object_price numeric(19, 2),
 has_probability_of_bidding boolean,
 is_shared_ownership_property boolean,
 the_size_of_trades varchar(255),
 application_status_id bigint not null
 constraint fkqjr8xmmta49x3thqgapgel66y
 references htc_dm_dic_application_status,
 operation_type_id bigint not null
 constraint fk9le0q92shrjtvidg69jqd08i
 references htc_dm_dic_operation_type,
 owner_id bigint not null
 constraint fkqtopm9xmd1yai54mpdbm0f22e
 references htc_dm_real_property_owner,
 real_property_id bigint
 constraint fkbk1xuj5yjv456i352x3b9cvlj
 references htc_dm_real_property
);

alter table htc_dm_application
 owner to postgres;

create table htc_dm_application_possible_reason_for_bidding
(
 application_id bigint not null
 constraint fk3h16b8rc78ahghmgpqj2mdo4t
 references htc_dm_application,
 possible_reason_for_bidding_id bigint not null
 constraint fk6ar6ic69x544bq25akysgsox3
 references htc_dm_dic_possible_reason_for_bidding,
 constraint htc_dm_application_possible_reason_for_bidding_pkey
 primary key (application_id, possible_reason_for_bidding_id)
);

alter table htc_dm_application_possible_reason_for_bidding
 owner to postgres;


create table htc_dm_application_status_history
(
 id bigserial not null
 constraint htc_dm_application_status_history_pkey
 primary key,
 created_by varchar(255) not null,
 created_date timestamp not null,
 last_modified_by varchar(255) not null,
 last_modified_date timestamp not null,
 is_removed boolean default false not null,
 comment varchar(255),
 application_id bigint
 constraint fkqd3lvcukvg5nqx773o9j5wg9j
 references htc_dm_application,
 application_status_id bigint
 constraint fkbhaomos4gu32ypcj2mp3bsfx2
 references htc_dm_dic_application_status
);

alter table htc_dm_application_status_history
 owner to postgres;

create table htc_dm_dic_application_status
(
 id bigserial not null
 constraint htc_dm_dic_application_status_pkey
 primary key,
 name_en varchar(255) not null,
 name_kz varchar(255) not null,
 name_ru varchar(255) not null,
 code varchar(255) not null
 constraint uk_52y3hqtlbc3qecinptcimmlxd
 unique,
 is_enabled boolean default true not null,
 is_removed boolean default false not null
);

alter table htc_dm_dic_application_status
 owner to postgres;

create table htc_dm_dic_city
(
 id bigserial not null
 constraint htc_dm_dic_city_pkey
 primary key,
 name_en varchar(255) not null,
 name_kz varchar(255) not null,
 name_ru varchar(255) not null
);

alter table htc_dm_dic_city
 owner to postgres;

create table htc_dm_dic_country
(
 id bigserial not null
 constraint htc_dm_dic_country_pkey
 primary key,
 name_en varchar(255) not null,
 name_kz varchar(255) not null,
 name_ru varchar(255) not null
);

alter table htc_dm_dic_country
 owner to postgres;

create table htc_dm_dic_district
(
 id bigserial not null
 constraint htc_dm_dic_district_pkey
 primary key,
 name_en varchar(255) not null,
 name_kz varchar(255) not null,
 name_ru varchar(255) not null
);

alter table htc_dm_dic_district
 owner to postgres;

create table htc_dm_dic_event_type
(
 id bigserial not null
 constraint htc_dm_dic_event_type_pkey
 primary key,
 name_en varchar(255) not null,
 name_kz varchar(255) not null,
 name_ru varchar(255) not null
);

alter table htc_dm_dic_event_type
 owner to postgres;

create table htc_dm_dic_heating_system
(
 id bigserial not null
 constraint htc_dm_dic_heating_system_pkey
 primary key,
 name_en varchar(255) not null,
 name_kz varchar(255) not null,
 name_ru varchar(255) not null
);

alter table htc_dm_dic_heating_system
 owner to postgres;

create table htc_dm_dic_material_of_construction
(
 id bigserial not null
 constraint htc_dm_dic_material_of_construction_pkey
 primary key,
 name_en varchar(255) not null,
 name_kz varchar(255) not null,
 name_ru varchar(255) not null,
 code varchar(255)
);

alter table htc_dm_dic_material_of_construction
 owner to postgres;

create table htc_dm_dic_object_type
(
 id bigserial not null
 constraint htc_dm_dic_object_type_pkey
 primary key,
 name_en varchar(255) not null,
 name_kz varchar(255) not null,
 name_ru varchar(255) not null,
 code varchar(255) not null
 constraint uk_mpy89owi8bw99uqrvonr0olsy
 unique,
 is_enabled boolean default true not null,
 is_removed boolean default false not null
);

alter table htc_dm_dic_object_type
 owner to postgres;

create table htc_dm_dic_operation_type
(
 id bigserial not null
 constraint htc_dm_dic_operation_type_pkey
 primary key,
 name_en varchar(255) not null,
 name_kz varchar(255) not null,
 name_ru varchar(255) not null,
 code varchar(255) not null
 constraint uk_t520448o798gkj7hf9rcikunf
 unique,
 is_enabled boolean default true not null,
 is_removed boolean default false not null
);

alter table htc_dm_dic_operation_type
 owner to postgres;

create table htc_dm_dic_parking_type
(
 id bigserial not null
 constraint htc_dm_dic_parking_type_pkey
 primary key,
 name_en varchar(255) not null,
 name_kz varchar(255) not null,
 name_ru varchar(255) not null
);

alter table htc_dm_dic_parking_type
 owner to postgres;

create table htc_dm_dic_possible_reason_for_bidding
(
 id bigserial not null
 constraint htc_dm_dic_possible_reason_for_bidding_pkey
 primary key,
 name_en varchar(255) not null,
 name_kz varchar(255) not null,
 name_ru varchar(255) not null,
 operation_code varchar(255)
);

alter table htc_dm_dic_possible_reason_for_bidding
 owner to postgres;

create table htc_dm_dic_property_developer
(
 id bigserial not null
 constraint htc_dm_dic_property_developer_pkey
 primary key,
 name_en varchar(255) not null,
 name_kz varchar(255) not null,
 name_ru varchar(255) not null
);

alter table htc_dm_dic_property_developer
 owner to postgres;

create table htc_dm_dic_residential_complex
(
 id bigserial not null
 constraint htc_dm_dic_residential_complex_pkey
 primary key,
 house_name varchar(255),
 number_of_entrances integer,
 general_characteristics_id bigint
 constraint fk6ryiuq52e3mey6aisxbw4ku6r
 references htc_dm_general_characteristics,
 general_characteristics_real_property_id bigint
);

alter table htc_dm_dic_residential_complex
 owner to postgres;

create table htc_dm_dic_sewerage
(
 id bigserial not null
 constraint htc_dm_dic_sewerage_pkey
 primary key,
 name_en varchar(255) not null,
 name_kz varchar(255) not null,
 name_ru varchar(255) not null
);

alter table htc_dm_dic_sewerage
 owner to postgres;

create table htc_dm_dic_street
(
 id bigserial not null
 constraint htc_dm_dic_street_pkey
 primary key,
 name_en varchar(255) not null,
 name_kz varchar(255) not null,
 name_ru varchar(255) not null
);

alter table htc_dm_dic_street
 owner to postgres;

create table htc_dm_dic_type_of_elevator
(
 id bigserial not null
 constraint htc_dm_dic_type_of_elevator_pkey
 primary key,
 name_en varchar(255) not null,
 name_kz varchar(255) not null,
 name_ru varchar(255) not null
);

alter table htc_dm_dic_type_of_elevator
 owner to postgres;

create table htc_dm_dic_yard_type
(
 id bigserial not null
 constraint htc_dm_dic_yard_type_pkey
 primary key,
 name_en varchar(255) not null,
 name_kz varchar(255) not null,
 name_ru varchar(255) not null
);

alter table htc_dm_dic_yard_type
 owner to postgres;

create table htc_dm_dic_yes_no
(
 id bigserial not null
 constraint htc_dm_dic_yes_no_pkey
 primary key,
 name_en varchar(255) not null,
 name_kz varchar(255) not null,
 name_ru varchar(255) not null,
 code varchar(255) not null
 constraint uk_39wwh3052erk13ugxmkatv2q0
 unique,
 is_enabled boolean default true not null,
 is_removed boolean default false not null
);

alter table htc_dm_dic_yes_no
 owner to postgres;

create table htc_dm_event
(
 id bigserial not null
 constraint htc_dm_event_pkey
 primary key,
 client_id bigint,
 comment varchar(255),
 description varchar(255),
 event_date timestamp,
 event_type_id bigint
 constraint fklkok3fy5bsyx5r0i8snrk1k63
 references htc_dm_dic_event_type,
 source_application bigint
 constraint fkesrfri2qnqglru5b47t5og045
 references htc_dm_application,
 targer_application bigint
 constraint fkhdi4wh0c1d6bx20b8x6tgbwvs
 references htc_dm_application,
 app_id bigint
 constraint fkdcvxmfufcbf659cy9yvk1o7m7
 references htc_dm_application,
 app_id2 bigint
 constraint fk8ejcm2wvn1oh4t0bgpucreara
 references htc_dm_application,
 event_type bigint
 constraint fkklmuc2t0fuc6k3ntytd8j7r6j
 references htc_dm_dic_event_type
);

alter table htc_dm_event
 owner to postgres;

create table htc_dm_general_characteristics
(
 id bigserial not null
 constraint htc_dm_general_characteristics_pkey
 primary key,
 apartments_on_the_site varchar(255),
 ceiling_height numeric(19, 2),
 concierge boolean,
 house_number integer,
 house_number_fraction varchar(255),
 housing_class varchar(255),
 housing_condition varchar(255),
 number_of_apartments integer,
 number_of_floors integer,
 playground boolean,
 wheelchair boolean,
 year_of_construction integer,
 city_id bigint
 constraint fk4s1k6sjwfct4b5tmy1gbeituj
 references htc_dm_dic_city,
 district_id bigint
 constraint fk28yq232ytxukiv3id0y6pidam
 references htc_dm_dic_district,
 material_of_construction_id bigint
 constraint fk990ndfixvn1bs8ng5jrbfl38o
 references htc_dm_dic_material_of_construction,
 parking_type_id bigint
 constraint fk3rx2m83145o5tavr5hh452qvw
 references htc_dm_dic_parking_type,
 property_developer_id bigint
 constraint fkrhv0gme35vwqkbwenvn17f10q
 references htc_dm_dic_property_developer,
 street_id bigint
 constraint fkthp1xp78ylwn24vt5aaa9mfg9
 references htc_dm_dic_street,
 yard_type_id bigint
 constraint fk4ik9u2ou6uv85rk8sh5int3c
 references htc_dm_dic_yard_type
);

alter table htc_dm_general_characteristics
 owner to postgres;

create table htc_dm_general_characteristics_parking_type
(
 general_characteristics_id bigint not null
 constraint fko3xca97oylv62mbsbrdyp11jm
 references htc_dm_general_characteristics,
 parking_type_id bigint not null
 constraint fk8y5i7qdumwnmhrfxtorwcwe08
 references htc_dm_dic_parking_type,
 constraint htc_dm_general_characteristics_parking_type_pkey
 primary key (general_characteristics_id, parking_type_id)
);

alter table htc_dm_general_characteristics_parking_type
 owner to postgres;


create table htc_dm_general_characteristics_type_of_elevator
(
 general_characteristics_id bigint not null
 constraint fk2lhbmk4133rmh78i2kukghg1c
 references htc_dm_general_characteristics,
 type_of_elevator_id bigint not null
 constraint fkkmw2o7sow1iu9g1iuwcd1197
 references htc_dm_dic_type_of_elevator,
 constraint htc_dm_general_characteristics_type_of_elevator_pkey
 primary key (general_characteristics_id, type_of_elevator_id)
);

alter table htc_dm_general_characteristics_type_of_elevator
 owner to postgres;

create table htc_dm_purchase_info
(
 balcony_area_from numeric(19, 2),
 balcony_area_to numeric(19, 2),
 ceiling_height_from numeric(19, 2),
 ceiling_height_to numeric(19, 2),
 floor_from integer,
 floor_to integer,
 kitchen_area_from numeric(19, 2),
 kitchen_area_to numeric(19, 2),
 land_area_from numeric(19, 2),
 land_area_to numeric(19, 2),
 living_area_from numeric(19, 2),
 living_area_to numeric(19, 2),
 number_of_bedrooms_from integer,
 number_of_bedrooms_to integer,
 number_of_floors_from integer,
 number_of_floors_to integer,
 number_of_rooms_from integer,
 number_of_rooms_to integer,
 object_price_from numeric(19, 2),
 object_price_to numeric(19, 2),
 total_area_from numeric(19, 2),
 total_area_to numeric(19, 2),
 real_property_id bigint not null
 constraint htc_dm_purchase_info_pkey
 primary key
 constraint fk9rbx3nsj1ccd5gqggc3wly8cs
 references htc_dm_real_property
);

alter table htc_dm_purchase_info
 owner to postgres;


create table htc_dm_real_property
(
 id bigserial not null
 constraint htc_dm_real_property_pkey
 primary key,
 created_by varchar(255) not null,
 created_date timestamp not null,
 last_modified_by varchar(255) not null,
 last_modified_date timestamp not null,
 is_removed boolean default false not null,
 apartment_number varchar(255),
 atelier boolean,
 balcony_area numeric(19, 2),
 cadastral_number varchar(255)
 constraint uk_cs8w4nbdfui5k6vnsrwgvfxgd
 unique,
 files_map jsonb,
 floor integer,
 kitchen_area numeric(19, 2),
 land_area numeric(19, 2),
 living_area numeric(19, 2),
 number_of_bedrooms integer,
 number_of_rooms integer,
 separate_bathroom boolean,
 total_area numeric(19, 2),
 general_characteristics_id bigint
 constraint fk54keu9qj3m1dpmu3oq849q9gi
 references htc_dm_general_characteristics,
 heating_system_id bigint
 constraint fk7mm5snta1nlq6nb8funvvydaj
 references htc_dm_dic_heating_system,
 object_type_id bigint
 constraint fkh6rflw8sj8vy0ks9q7nb9esx1
 references htc_dm_dic_object_type,
 residential_complex_id bigint
 constraint fkmnr4x2v4qnabrs0964walmtp4
 references htc_dm_dic_residential_complex,
 sewerage_id bigint
 constraint fkdrrhffgq8xnpu0n85am9ihyw9
 references htc_dm_dic_sewerage
);

alter table htc_dm_real_property
 owner to postgres;

create table htc_dm_real_property_owner
(
 id bigserial not null
 constraint htc_dm_real_property_owner_pkey
 primary key,
 created_by varchar(255) not null,
 created_date timestamp not null,
 last_modified_by varchar(255) not null,
 last_modified_date timestamp not null,
 is_removed boolean default false not null,
 email varchar(255),
 first_name varchar(255) not null,
 gender varchar(255) not null,
 patronymic varchar(255),
 phone_number varchar(255) not null
 constraint uk_tpd4g5782csh734txeoqparwx
 unique,
 surname varchar(255)
);

alter table htc_dm_real_property_owner
 owner to postgres;