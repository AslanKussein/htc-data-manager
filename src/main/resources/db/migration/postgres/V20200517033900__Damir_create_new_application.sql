create table htc_dm_dic_house_condition
(
  id         bigserial             not null
    primary key,
  name_en    varchar(255)          not null,
  name_kz    varchar(255)          not null,
  name_ru    varchar(255)          not null,
  is_removed boolean default false not null
);

alter table htc_dm_dic_house_condition
  owner to postgres;
comment on table htc_dm_dic_house_condition is 'Справочник состояние недвижимости';

create table htc_dm_dic_metadata_status
(
  id         bigserial             not null
    primary key,
  name_en    varchar(255)          not null,
  name_kz    varchar(255)          not null,
  name_ru    varchar(255)          not null,
  code       varchar(255)          not null
    constraint uk_metadata_status_code
      unique,
  is_removed boolean default false not null
);

alter table htc_dm_dic_metadata_status
  owner to postgres;

comment on table htc_dm_dic_metadata_status is 'Справочник статус подтверждения метаданных недвижимости';

create table htc_dm_application
(
  id                    bigserial             not null primary key,
  created_by            varchar(255)          not null,
  created_date          timestamp             not null,
  last_modified_by      varchar(255)          not null,
  last_modified_date    timestamp             not null,
  is_removed            boolean default false not null,
  application_status_id bigint                not null
    constraint fk_application_status
      references htc_dm_dic_application_status,
  operation_type_id     bigint                not null
    constraint fk_application_operation
      references htc_dm_dic_operation_type,
  object_type_id        bigint                not null
    constraint fk_application_object_type
      references htc_dm_dic_object_type,
  client_login          varchar(100)          not null,
  current_agent         varchar(100)          not null
);

alter table htc_dm_application
  owner to postgres;

comment on table htc_dm_application is 'Заявка';

create table htc_dm_purchase_info
(
  id                          bigserial not null
    primary key,
  balcony_area_from           numeric(19, 2),
  balcony_area_to             numeric(19, 2),
  ceiling_height_from         numeric(19, 2),
  ceiling_height_to           numeric(19, 2),
  floor_from                  integer,
  floor_to                    integer,
  kitchen_area_from           numeric(19, 2),
  kitchen_area_to             numeric(19, 2),
  land_area_from              numeric(19, 2),
  land_area_to                numeric(19, 2),
  living_area_from            numeric(19, 2),
  living_area_to              numeric(19, 2),
  number_of_bedrooms_from     integer,
  number_of_bedrooms_to       integer,
  number_of_floors_from       integer,
  number_of_floors_to         integer,
  number_of_rooms_from        integer,
  number_of_rooms_to          integer,
  object_price_from           numeric(19, 2),
  object_price_to             numeric(19, 2),
  total_area_from             numeric(19, 2),
  total_area_to               numeric(19, 2),
  year_of_construction_from   integer,
  year_of_construction_to     integer,
  apartments_on_the_site_from integer,
  apartments_on_the_site_to   integer,
  material_of_construction_id bigint
    constraint fk_purchase_info_material
      references htc_dm_dic_material_of_construction,
  yard_type_id                bigint
    constraint fk_purchase_info_yard_type
      references htc_dm_dic_yard_type,
  concierge                   boolean,
  wheelchair                  boolean,
  playground                  boolean,
  parking_types               jsonb,
  types_of_elevator           jsonb
);

alter table htc_dm_purchase_info
  owner to postgres;

comment on table htc_dm_purchase_info is 'Метаданные по покупке';

create table htc_dm_purchase_data
(
  id                           bigserial    not null primary key,
  city_id                      bigint
    constraint fk_purchase_data_city
      references htc_dm_dic_city,
  district_id                  bigint
    constraint fk_purchase_data_district
      references htc_dm_dic_district,
  purchase_info_id             bigint
    constraint fk_purchase_data_info
      references htc_dm_purchase_info,
  application_id               bigint
    constraint fk_purchase_data_application
      references htc_dm_application,
  mortgage                     boolean,
  has_probability_of_bidding   boolean,
  the_size_of_trades           integer      null,
  note                         varchar(500) null,
  possible_reasons_for_bidding jsonb
);

alter table htc_dm_purchase_data
  owner to postgres;

comment on table htc_dm_purchase_data is 'Данные по покупке';

create table htc_dm_application_status_history
(
  id                    bigserial             not null
    primary key,
  created_by            varchar(255)          not null,
  created_date          timestamp             not null,
  last_modified_by      varchar(255)          not null,
  last_modified_date    timestamp             not null,
  is_removed            boolean default false not null,
  comment               varchar(255),
  application_id        bigint
    constraint fk_status_history_application
      references htc_dm_application,
  application_status_id bigint
    constraint fk_status_history_status
      references htc_dm_dic_application_status
);

alter table htc_dm_application_status_history
  owner to postgres;

comment on table htc_dm_application_status_history is 'История статусов заявки';

create table htc_dm_event
(
  id                 bigserial             not null
    primary key,
  created_by         varchar(255)          not null,
  created_date       timestamp             not null,
  last_modified_by   varchar(255)          not null,
  last_modified_date timestamp             not null,
  is_removed         boolean default false not null,
  comment            varchar(500),
  description        varchar(500),
  event_date         timestamp,
  event_type_id      bigint
    constraint fk_event_type
      references htc_dm_dic_event_type,
  source_application bigint
    constraint fk_event_source_app
      references htc_dm_application,
  targer_application bigint
    constraint fk_event_target_app
      references htc_dm_application
);

alter table htc_dm_event
  owner to postgres;

comment on table htc_dm_event is 'События по заявке';

create table htc_dm_building
(
  id                    bigserial             not null primary key,
  city_id               bigint
    constraint fk_building_city
      references htc_dm_dic_city,
  district_id           bigint
    constraint fk_building_district
      references htc_dm_dic_district,
  street_id             bigint
    constraint fk_building_street
      references htc_dm_dic_street,
  house_number          integer,
  house_number_fraction varchar(20)           null,
  postcode              varchar(20)           not null
    constraint fk_building_postcode
      unique,
  latitude              numeric(19, 2)        NULL,
  longitude             numeric(19, 2)        NULL,
  created_by            varchar(255)          not null,
  created_date          timestamp             not null,
  last_modified_by      varchar(255)          not null,
  last_modified_date    timestamp             not null,
  is_removed            boolean default false not null
);

alter table htc_dm_building
  owner to postgres;

comment on table htc_dm_building is 'Здание/Строение';

create table htc_dm_general_characteristics
(
  id                          bigserial not null
    primary key,
  property_developer_id       bigint
    constraint fk_gen_characteristics_developer
      references htc_dm_dic_property_developer,
  housing_class               varchar(255),
  year_of_construction        integer,
  number_of_floors            integer,
  number_of_apartments        integer,
  apartments_on_the_site      integer,
  concierge                   boolean,
  wheelchair                  boolean,
  material_of_construction_id bigint
    constraint fk_gen_characteristics_material
      references htc_dm_dic_material_of_construction,
  yard_type_id                bigint
    constraint fk_gen_characteristics_yard
      references htc_dm_dic_yard_type,
  playground                  boolean,
  parking_types               jsonb,
  types_of_elevator           jsonb
);

alter table htc_dm_general_characteristics
  owner to postgres;

comment on table htc_dm_dic_residential_complex is 'Справочник ЖК';

create table htc_dm_dic_residential_complex
(
  id                         bigserial             not null
    primary key,
  house_name                 varchar(255),
  number_of_entrances        integer,
  is_removed                 boolean default false not null,
  building_id                bigint                not null
    constraint fk_residential_complex_building
      references htc_dm_building,
  general_characteristics_id bigint                not null
    constraint fk_residential_complex_characteristics
      references htc_dm_general_characteristics,
);

alter table htc_dm_dic_residential_complex
  owner to postgres;

comment on table htc_dm_dic_residential_complex is 'Справочник ЖК';

create table htc_dm_real_property_metadata
(
  id                         bigserial             not null primary key,
  application_id             bigint                not null
    constraint fk_rp_meta_application
      references htc_dm_application,
  house_condition_id         bigint
    constraint fk_rp_meta_house_condition
      references htc_dm_dic_house_condition,
  residential_complex_id     bigint
    constraint fk_rp_meta_res_complex
      references htc_dm_dic_residential_complex,
  sewerage_id                bigint
    constraint fk_rp_meta_sewerage
      references htc_dm_dic_sewerage,
  heating_system_id          bigint
    constraint fk_rp_meta_heating_system
      references htc_dm_dic_heating_system,
  general_characteristics_id bigint
    constraint fk_rp_meta_gen_characteristics
      references htc_dm_general_characteristics,
  metadata_status_id         bigint
    constraint fk_rp_meta_metadata_status
      references htc_dm_dic_metadata_status,
  created_by                 varchar(255)          not null,
  created_date               timestamp             not null,
  last_modified_by           varchar(255)          not null,
  last_modified_date         timestamp             not null,
  is_removed                 boolean default false not null,
  floor                      integer,
  number_of_rooms            integer,
  number_of_bedrooms         integer,
  total_area                 numeric(19, 2),
  living_area                numeric(19, 2),
  kitchen_area               numeric(19, 2),
  balcony_area               numeric(19, 2),
  atelier                    boolean,
  separate_bathroom          boolean
);

alter table htc_dm_real_property_metadata
  owner to postgres;

comment on table htc_dm_real_property_metadata is 'Мета данные объекта недвижимости';

create table htc_dm_real_property
(
  id                 bigserial             not null
    primary key,
  created_by         varchar(255)          not null,
  created_date       timestamp             not null,
  last_modified_by   varchar(255)          not null,
  last_modified_date timestamp             not null,
  is_removed         boolean default false not null,
  apartment_number   varchar(255),
  cadastral_number   varchar(255)
    constraint uk_real_property_cadastral_number
      unique,
  building_id        bigint
    constraint fk_real_property_building
      references htc_dm_building
);

alter table htc_dm_real_property
  owner to postgres;

comment on table htc_dm_real_property is 'Объект недвижимости';

alter table htc_dm_real_property_metadata
  add column real_property_id bigint not null
    constraint fk_rp_metadata_property
      references htc_dm_real_property;

create table htc_dm_sell_data
(
  id                           bigserial    not null primary key,
  application_id               bigint
    constraint fk_sell_data_application
      references htc_dm_application,
  real_property_id             bigint       null
    constraint fk_sell_data_property
      references htc_dm_real_property,
  object_price                 numeric(19, 2),
  encumbrance                  boolean,
  is_shared_ownership_property boolean,
  is_exchange                  boolean,
  files_map                    jsonb,
  possible_reasons_for_bidding jsonb,
  mortgage                     boolean,
  has_probability_of_bidding   boolean,
  the_size_of_trades           integer      null,
  description                  varchar(1000),
  note                         varchar(500) null
);

alter table htc_dm_sell_data
  owner to postgres;

comment on table htc_dm_sell_data is 'Данные по продаже объекта недвижимости';

create table htc_dm_assignment
(
  id                 bigserial             not null primary key,
  created_by         varchar(255)          not null,
  created_date       timestamp             not null,
  last_modified_by   varchar(255)          not null,
  last_modified_date timestamp             not null,
  is_removed         boolean default false not null,
  agent              varchar(255)          not null,
  application_id     bigint                not null
    constraint fk_assign_application
      references htc_dm_application
);

alter table htc_dm_assignment
  owner to postgres;

comment on table htc_dm_assignment is 'Таблица истории назначения заявок агентам';
comment on column htc_dm_assignment.agent is 'Логин агента';
comment on column htc_dm_assignment.application_id is 'ID заявки (htc_dm_application)';

create table htc_dm_notes
(
  id               bigserial not null primary key,
  text             text      not null,
  deleted          boolean default false,
  real_property_id bigint
    constraint fk_notes_real_property
      references htc_dm_real_property
);

alter table htc_dm_notes
  owner to postgres;