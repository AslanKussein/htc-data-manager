create table htc_dm_application
(
 id bigserial not null
 constraint htc_dm_application_pkey
 primary key,
 created_by varchar(255) not null,
 created_date timestamp not null,
 last_modified_by varchar(255) not null,
 last_modified_date timestamp not null,
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

INSERT INTO public.htc_dm_application (id, created_by, created_date, last_modified_by, last_modified_date, the_amount_of_the_contract, contract_period, encumbrance, is_exchange, is_commission_included_in_the_price, mortgage, note, object_price, has_probability_of_bidding, is_shared_ownership_property, the_size_of_trades, application_status_id, operation_type_id, owner_id, real_property_id) VALUES (1, 'admin', '2020-04-21 08:33:53.319000', 'system', '2020-04-21 08:47:23.348000', 1000000.00, '2021-04-21', true, true, true, true, 'Nice house', 1000000.00, true, true, 'theSizeOfTrades', 1, 2, 2, 1);
INSERT INTO public.htc_dm_application (id, created_by, created_date, last_modified_by, last_modified_date, the_amount_of_the_contract, contract_period, encumbrance, is_exchange, is_commission_included_in_the_price, mortgage, note, object_price, has_probability_of_bidding, is_shared_ownership_property, the_size_of_trades, application_status_id, operation_type_id, owner_id, real_property_id) VALUES (3, 'admin', '2020-04-21 20:11:21.000000', 'system', '2020-04-21 11:12:51.638000', 1500000.00, '2021-04-21', true, true, true, true, 'Real house', 1500000.00, true, true, 'theSizeOfTrades', 2, 2, 3, 3);
INSERT INTO public.htc_dm_application (id, created_by, created_date, last_modified_by, last_modified_date, the_amount_of_the_contract, contract_period, encumbrance, is_exchange, is_commission_included_in_the_price, mortgage, note, object_price, has_probability_of_bidding, is_shared_ownership_property, the_size_of_trades, application_status_id, operation_type_id, owner_id, real_property_id) VALUES (37, 'admin', '2020-04-21 12:15:30.769000', 'system', '2020-04-21 12:16:28.760000', 2000000.00, '2021-04-21', true, true, true, true, 'Perfect house', 2000000.00, true, true, 'theSizeOfTrades', 1, 1, 4, 54);
INSERT INTO public.htc_dm_application (id, created_by, created_date, last_modified_by, last_modified_date, the_amount_of_the_contract, contract_period, encumbrance, is_exchange, is_commission_included_in_the_price, mortgage, note, object_price, has_probability_of_bidding, is_shared_ownership_property, the_size_of_trades, application_status_id, operation_type_id, owner_id, real_property_id) VALUES (71, 'System', '2020-04-22 04:20:54.146000', 'system', '2020-04-22 04:20:54.146000', 1.00, '2020-04-02', null, null, false, null, null, null, null, null, null, 1, 2, 73, 90);
INSERT INTO public.htc_dm_application (id, created_by, created_date, last_modified_by, last_modified_date, the_amount_of_the_contract, contract_period, encumbrance, is_exchange, is_commission_included_in_the_price, mortgage, note, object_price, has_probability_of_bidding, is_shared_ownership_property, the_size_of_trades, application_status_id, operation_type_id, owner_id, real_property_id) VALUES (4, 'System', '2020-04-21 11:47:33.872000', 'system', '2020-04-21 11:47:33.872000', 2500000.00, '2021-04-21', true, true, true, true, 'Beatiful house', 2500000.00, true, true, 'theSizeOfTrades', 2, 1, 5, 55);
INSERT INTO public.htc_dm_application (id, created_by, created_date, last_modified_by, last_modified_date, the_amount_of_the_contract, contract_period, encumbrance, is_exchange, is_commission_included_in_the_price, mortgage, note, object_price, has_probability_of_bidding, is_shared_ownership_property, the_size_of_trades, application_status_id, operation_type_id, owner_id, real_property_id) VALUES (5, 'System', '2020-04-21 11:47:53.849000', 'system', '2020-04-21 11:47:53.849000', 3000000.00, '2021-04-21', true, true, true, true, 'Cool house', 3000000.00, true, true, 'theSizeOfTrades', 1, 1, 6, 56);
INSERT INTO public.htc_dm_application (id, created_by, created_date, last_modified_by, last_modified_date, the_amount_of_the_contract, contract_period, encumbrance, is_exchange, is_commission_included_in_the_price, mortgage, note, object_price, has_probability_of_bidding, is_shared_ownership_property, the_size_of_trades, application_status_id, operation_type_id, owner_id, real_property_id) VALUES (2, 'System', '2020-04-21 09:56:04.623000', 'system', '2020-04-21 10:58:55.158000', 3500000.00, '2021-04-21', true, true, true, true, 'Big house', 3500000.00, true, true, 'theSizeOfTrades', 2, 2, 7, 2);
INSERT INTO public.htc_dm_application (id, created_by, created_date, last_modified_by, last_modified_date, the_amount_of_the_contract, contract_period, encumbrance, is_exchange, is_commission_included_in_the_price, mortgage, note, object_price, has_probability_of_bidding, is_shared_ownership_property, the_size_of_trades, application_status_id, operation_type_id, owner_id, real_property_id) VALUES (70, 'System', '2020-04-22 04:05:56.255000', 'system', '2020-04-22 04:05:56.255000', 1.00, '2020-04-03', null, null, false, null, null, null, null, null, null, 1, 2, 72, 89);
INSERT INTO public.htc_dm_application (id, created_by, created_date, last_modified_by, last_modified_date, the_amount_of_the_contract, contract_period, encumbrance, is_exchange, is_commission_included_in_the_price, mortgage, note, object_price, has_probability_of_bidding, is_shared_ownership_property, the_size_of_trades, application_status_id, operation_type_id, owner_id, real_property_id) VALUES (104, 'system', '2020-04-24 03:07:04.449000', 'system', '2020-04-24 03:07:04.449000', 1000.00, '2020-03-31', null, null, false, null, null, null, null, null, null, 1, 2, 109, 126);
INSERT INTO public.htc_dm_application (id, created_by, created_date, last_modified_by, last_modified_date, the_amount_of_the_contract, contract_period, encumbrance, is_exchange, is_commission_included_in_the_price, mortgage, note, object_price, has_probability_of_bidding, is_shared_ownership_property, the_size_of_trades, application_status_id, operation_type_id, owner_id, real_property_id) VALUES (105, 'system', '2020-04-24 03:12:09.809000', 'system', '2020-04-24 03:12:09.809000', 10000000.00, '2020-05-29', null, null, false, null, null, null, null, null, null, 1, 2, 115, 129);
INSERT INTO public.htc_dm_application (id, created_by, created_date, last_modified_by, last_modified_date, the_amount_of_the_contract, contract_period, encumbrance, is_exchange, is_commission_included_in_the_price, mortgage, note, object_price, has_probability_of_bidding, is_shared_ownership_property, the_size_of_trades, application_status_id, operation_type_id, owner_id, real_property_id) VALUES (106, 'system', '2020-04-24 03:38:02.712000', 'system', '2020-04-24 03:38:02.712000', 10000000.00, '2020-05-02', null, null, false, null, null, null, null, null, null, 1, 2, 123, 137);
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

INSERT INTO public.htc_dm_application_status_history (id, created_by, created_date, last_modified_by, last_modified_date, comment, application_id, application_status_id) VALUES (1, 'system', '2020-04-21 11:54:19.142000', 'system', '2020-04-21 11:54:19.142000', 'AbraCadabra', 1, 1);
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

INSERT INTO public.htc_dm_dic_application_status (id, name_en, name_kz, name_ru, code, is_enabled, is_removed) VALUES (1, 'Первичный контакт', 'Первичный контакт', 'Первичный контакт', '002001', true, false);
INSERT INTO public.htc_dm_dic_application_status (id, name_en, name_kz, name_ru, code, is_enabled, is_removed) VALUES (2, 'Встреча', 'Встреча', 'Встреча', '002002', true, false);
INSERT INTO public.htc_dm_dic_application_status (id, name_en, name_kz, name_ru, code, is_enabled, is_removed) VALUES (3, 'Договор на оказание услуг', 'Договор на оказание услуг', 'Договор на оказание услуг', '002003', true, false);
INSERT INTO public.htc_dm_dic_application_status (id, name_en, name_kz, name_ru, code, is_enabled, is_removed) VALUES (4, 'Реклама', 'Реклама', 'Реклама', '002004', true, false);
INSERT INTO public.htc_dm_dic_application_status (id, name_en, name_kz, name_ru, code, is_enabled, is_removed) VALUES (5, 'Фотосет', 'Фотосет', 'Фотосет', '002005', true, false);
INSERT INTO public.htc_dm_dic_application_status (id, name_en, name_kz, name_ru, code, is_enabled, is_removed) VALUES (6, 'Показ', 'Показ', 'Показ', '002006', true, false);
INSERT INTO public.htc_dm_dic_application_status (id, name_en, name_kz, name_ru, code, is_enabled, is_removed) VALUES (7, 'Закрытие сделки', 'Закрытие сделки', 'Закрытие сделки', '002007', true, false);
INSERT INTO public.htc_dm_dic_application_status (id, name_en, name_kz, name_ru, code, is_enabled, is_removed) VALUES (8, 'Успешно', 'Успешно', 'Успешно', '002008', true, false);
INSERT INTO public.htc_dm_dic_application_status (id, name_en, name_kz, name_ru, code, is_enabled, is_removed) VALUES (9, 'Завершен', 'Завершен', 'Завершен', '002009', true, false);
INSERT INTO public.htc_dm_dic_application_status (id, name_en, name_kz, name_ru, code, is_enabled, is_removed) VALUES (35, 'string', 'string', 'string', 'string', true, false);
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

INSERT INTO public.htc_dm_dic_city (id, name_en, name_kz, name_ru) VALUES (1, 'Nur-Sultan', 'Нұр-Сұлтан', 'Нур-Султан');
INSERT INTO public.htc_dm_dic_city (id, name_en, name_kz, name_ru) VALUES (2, 'Almaty', 'Алматы', 'Алматы');
INSERT INTO public.htc_dm_dic_city (id, name_en, name_kz, name_ru) VALUES (3, 'Shymkent', 'Шымкент', 'Шымкент');
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

INSERT INTO public.htc_dm_dic_country (id, name_en, name_kz, name_ru) VALUES (1, 'Kazakhstan', 'Қазақстан', 'Казахстан');
INSERT INTO public.htc_dm_dic_country (id, name_en, name_kz, name_ru) VALUES (34, 'string', 'string', 'string');
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

INSERT INTO public.htc_dm_dic_district (id, name_en, name_kz, name_ru) VALUES (1, 'Алматинский район', 'Алматинский район', 'Алматинский район');
INSERT INTO public.htc_dm_dic_district (id, name_en, name_kz, name_ru) VALUES (2, 'Байконурский район', 'Байконурский район', 'Байконурский район');
INSERT INTO public.htc_dm_dic_district (id, name_en, name_kz, name_ru) VALUES (3, 'Есильский район', 'Есильский район', 'Есильский район');
INSERT INTO public.htc_dm_dic_district (id, name_en, name_kz, name_ru) VALUES (4, 'Сарыаркинский район', 'Сарыаркинский район', 'Сарыаркинский район');
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

INSERT INTO public.htc_dm_dic_event_type (id, name_en, name_kz, name_ru) VALUES (1, 'Event', 'Event', 'Event');
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

INSERT INTO public.htc_dm_dic_heating_system (id, name_en, name_kz, name_ru) VALUES (1, 'центральное', 'центральное', 'центральное');
INSERT INTO public.htc_dm_dic_heating_system (id, name_en, name_kz, name_ru) VALUES (2, 'на газе', 'на газе', 'на газе');
INSERT INTO public.htc_dm_dic_heating_system (id, name_en, name_kz, name_ru) VALUES (3, 'на твердом топливе', 'на твердом топливе', 'на твердом топливе');
INSERT INTO public.htc_dm_dic_heating_system (id, name_en, name_kz, name_ru) VALUES (4, 'на жидком топливе', 'на жидком топливе', 'на жидком топливе');
INSERT INTO public.htc_dm_dic_heating_system (id, name_en, name_kz, name_ru) VALUES (5, 'смешанное', 'смешанное', 'смешанное');
INSERT INTO public.htc_dm_dic_heating_system (id, name_en, name_kz, name_ru) VALUES (6, 'без отопления', 'без отопления', 'без отопления');
INSERT INTO public.htc_dm_dic_heating_system (id, name_en, name_kz, name_ru) VALUES (34, 'string', 'string', 'string');
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

INSERT INTO public.htc_dm_dic_material_of_construction (id, name_en, name_kz, name_ru, code) VALUES (1, 'кирпичный', 'кирпичный', 'кирпичный', 'common');
INSERT INTO public.htc_dm_dic_material_of_construction (id, name_en, name_kz, name_ru, code) VALUES (2, 'панельный', 'панельный', 'панельный', 'common');
INSERT INTO public.htc_dm_dic_material_of_construction (id, name_en, name_kz, name_ru, code) VALUES (3, 'монолитный', 'монолитный', 'монолитный', 'common');
INSERT INTO public.htc_dm_dic_material_of_construction (id, name_en, name_kz, name_ru, code) VALUES (4, 'иное', 'иное', 'иное', 'common');
INSERT INTO public.htc_dm_dic_material_of_construction (id, name_en, name_kz, name_ru, code) VALUES (5, 'деревянный', 'деревянный', 'деревянный', 'house');
INSERT INTO public.htc_dm_dic_material_of_construction (id, name_en, name_kz, name_ru, code) VALUES (6, 'каркасно-камышитовый', 'каркасно-камышитовый', 'каркасно-камышитовый', 'house');
INSERT INTO public.htc_dm_dic_material_of_construction (id, name_en, name_kz, name_ru, code) VALUES (7, 'пеноблочный', 'пеноблочный', 'пеноблочный', 'house');
INSERT INTO public.htc_dm_dic_material_of_construction (id, name_en, name_kz, name_ru, code) VALUES (8, 'сэндвич-панели', 'сэндвич-панели', 'сэндвич-панели', 'house');
INSERT INTO public.htc_dm_dic_material_of_construction (id, name_en, name_kz, name_ru, code) VALUES (9, 'каркасно-щитовой', 'каркасно-щитовой', 'каркасно-щитовой', 'house');
INSERT INTO public.htc_dm_dic_material_of_construction (id, name_en, name_kz, name_ru, code) VALUES (10, 'шлакоблочный', 'шлакоблочный', 'шлакоблочный', 'house');
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

INSERT INTO public.htc_dm_dic_object_type (id, name_en, name_kz, name_ru, code, is_enabled, is_removed) VALUES (1, 'Apartment', 'Пәтер', 'Квартира', '003001', true, false);
INSERT INTO public.htc_dm_dic_object_type (id, name_en, name_kz, name_ru, code, is_enabled, is_removed) VALUES (2, 'House', 'Үй', 'Частный дом', '003002', true, false);
INSERT INTO public.htc_dm_dic_object_type (id, name_en, name_kz, name_ru, code, is_enabled, is_removed) VALUES (34, 'string', 'string', 'string', '11', true, false);
INSERT INTO public.htc_dm_dic_object_type (id, name_en, name_kz, name_ru, code, is_enabled, is_removed) VALUES (35, '1', '234ing', 's1r213ing', '1', true, false);
INSERT INTO public.htc_dm_dic_object_type (id, name_en, name_kz, name_ru, code, is_enabled, is_removed) VALUES (37, '11', '2314ing', 's1r213i1ng', '111', true, false);
INSERT INTO public.htc_dm_dic_object_type (id, name_en, name_kz, name_ru, code, is_enabled, is_removed) VALUES (38, '11', '2314ing', 's1r213i1ng', '1111', true, false);
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

INSERT INTO public.htc_dm_dic_operation_type (id, name_en, name_kz, name_ru, code, is_enabled, is_removed) VALUES (1, 'Buy', 'Сатып алу', 'Купить', '001001', true, false);
INSERT INTO public.htc_dm_dic_operation_type (id, name_en, name_kz, name_ru, code, is_enabled, is_removed) VALUES (2, 'Sell', 'Сату', 'Продать', '001002', true, false);
INSERT INTO public.htc_dm_dic_operation_type (id, name_en, name_kz, name_ru, code, is_enabled, is_removed) VALUES (34, 'string', 'string', 'string', 'string', true, false);
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

INSERT INTO public.htc_dm_dic_parking_type (id, name_en, name_kz, name_ru) VALUES (1, 'парковка отсутствует', 'парковка отсутствует', 'парковка отсутствует');
INSERT INTO public.htc_dm_dic_parking_type (id, name_en, name_kz, name_ru) VALUES (2, 'наземный паркинг', 'наземный паркинг', 'наземный паркинг');
INSERT INTO public.htc_dm_dic_parking_type (id, name_en, name_kz, name_ru) VALUES (3, 'подземный паркинг', 'подземный паркинг', 'подземный паркинг');
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

INSERT INTO public.htc_dm_dic_possible_reason_for_bidding (id, name_en, name_kz, name_ru, operation_code) VALUES (1, 'встреча', 'встреча', 'встреча', '001002');
INSERT INTO public.htc_dm_dic_possible_reason_for_bidding (id, name_en, name_kz, name_ru, operation_code) VALUES (2, 'срочная продажа', 'срочная продажа', 'срочная продажа', '001002');
INSERT INTO public.htc_dm_dic_possible_reason_for_bidding (id, name_en, name_kz, name_ru, operation_code) VALUES (3, 'оплата наличными', 'оплата наличными', 'оплата наличными', '001002');
INSERT INTO public.htc_dm_dic_possible_reason_for_bidding (id, name_en, name_kz, name_ru, operation_code) VALUES (4, 'проблемы с выплатами по ипотеке', 'проблемы с выплатами по ипотеке', 'проблемы с выплатами по ипотеке', '001002');
INSERT INTO public.htc_dm_dic_possible_reason_for_bidding (id, name_en, name_kz, name_ru, operation_code) VALUES (5, 'наследство/подарок', 'наследство/подарок', 'наследство/подарок', '001002');
INSERT INTO public.htc_dm_dic_possible_reason_for_bidding (id, name_en, name_kz, name_ru, operation_code) VALUES (6, 'другое', 'другое', 'другое', '001002');
INSERT INTO public.htc_dm_dic_possible_reason_for_bidding (id, name_en, name_kz, name_ru, operation_code) VALUES (7, 'низкий спрос', 'низкий спрос', 'низкий спрос', '001001');
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

INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (1, 'Nova City Development', 'Nova City Development', 'Nova City Development');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (2, 'АстанаКазСтрой-Универсал', 'АстанаКазСтрой-Универсал', 'АстанаКазСтрой-Универсал');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (3, 'ТОО Строительство зданий и сооружений', 'ТОО Строительство зданий и сооружений', 'ТОО Строительство зданий и сооружений');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (4, 'Aibyn Construction Group', 'Aibyn Construction Group', 'Aibyn Construction Group');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (5, 'Aluan-AS', 'Aluan-AS', 'Aluan-AS');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (6, 'Aruana Хан-Тенгри', 'Aruana Хан-Тенгри', 'Aruana Хан-Тенгри');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (7, 'Astana Capital building project', 'Astana Capital building project', 'Astana Capital building project');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (8, 'Astana Trade International', 'Astana Trade International', 'Astana Trade International');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (9, 'BAUMASTER', 'BAUMASTER', 'BAUMASTER');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (10, 'BAZIS-A', 'BAZIS-A', 'BAZIS-A');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (11, 'BBK Group', 'BBK Group', 'BBK Group');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (12, 'BI Group', 'BI Group', 'BI Group');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (13, 'Caspian Service Kazakhstan', 'Caspian Service Kazakhstan', 'Caspian Service Kazakhstan');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (14, 'Center Beton Company', 'Center Beton Company', 'Center Beton Company');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (15, 'DiCOLDiPartners', 'DiCOLDiPartners', 'DiCOLDiPartners');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (16, 'Elite Market Group', 'Elite Market Group', 'Elite Market Group');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (17, 'Expolist Investment', 'Expolist Investment', 'Expolist Investment');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (18, 'G-Park', 'G-Park', 'G-Park');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (19, 'Gradum Development & Investment', 'Gradum Development & Investment', 'Gradum Development & Investment');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (20, 'Highvill Kazakhstan', 'Highvill Kazakhstan', 'Highvill Kazakhstan');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (21, 'Inshaat Invest', 'Inshaat Invest', 'Inshaat Invest');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (22, 'KAZ CAPITAL INVEST', 'KAZ CAPITAL INVEST', 'KAZ CAPITAL INVEST');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (23, 'Kazakhstan Invest Group-A', 'Kazakhstan Invest Group-A', 'Kazakhstan Invest Group-A');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (24, 'MABEX TRADE LTD', 'MABEX TRADE LTD', 'MABEX TRADE LTD');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (25, 'Mainstreet', 'Mainstreet', 'Mainstreet');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (26, 'Nur Astana Kurylys', 'Nur Astana Kurylys', 'Nur Astana Kurylys');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (27, 'ORDA INVEST', 'ORDA INVEST', 'ORDA INVEST');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (28, 'Otau Group', 'Otau Group', 'Otau Group');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (29, 'Pioneer Invest', 'Pioneer Invest', 'Pioneer Invest');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (30, 'SADI-групп', 'SADI-групп', 'SADI-групп');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (31, 'SAT-NS', 'SAT-NS', 'SAT-NS');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (32, 'Sembol developements', 'Sembol developements', 'Sembol developements');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (33, 'Sensata Group', 'Sensata Group', 'Sensata Group');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (34, 'Tamiz Invest Group', 'Tamiz Invest Group', 'Tamiz Invest Group');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (35, 'White Gold Development', 'White Gold Development', 'White Gold Development');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (36, 'Адалстройсервис НС', 'Адалстройсервис НС', 'Адалстройсервис НС');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (37, 'Азат-НТ Курылыс', 'Азат-НТ Курылыс', 'Азат-НТ Курылыс');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (38, 'Азбука Жилья Новостройки', 'Азбука Жилья Новостройки', 'Азбука Жилья Новостройки');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (39, 'Азбука жилья', 'Азбука жилья', 'Азбука жилья');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (40, 'Азия', 'Азия', 'Азия');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (41, 'АйМак-Инвест Групп', 'АйМак-Инвест Групп', 'АйМак-Инвест Групп');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (42, 'Академия жилья', 'Академия жилья', 'Академия жилья');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (43, 'Акмола Курылыс Материалдары', 'Акмола Курылыс Материалдары', 'Акмола Курылыс Материалдары');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (44, 'Алмас', 'Алмас', 'Алмас');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (45, 'Альнура Холдинг', 'Альнура Холдинг', 'Альнура Холдинг');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (46, 'Альтаир СК 7', 'Альтаир СК 7', 'Альтаир СК 7');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (47, 'Альянсстройинвест', 'Альянсстройинвест', 'Альянсстройинвест');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (48, 'АО Корпорация Ак ауыл', 'АО Корпорация Ак ауыл', 'АО Корпорация Ак ауыл');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (49, 'Асем Кала', 'Асем Кала', 'Асем Кала');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (50, 'АССМ', 'АССМ', 'АССМ');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (51, 'Астана Недвижимость', 'Астана Недвижимость', 'Астана Недвижимость');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (52, 'Байтур', 'Байтур', 'Байтур');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (53, 'БАХУС', 'БАХУС', 'БАХУС');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (54, 'Бахус-Астана', 'Бахус-Астана', 'Бахус-Астана');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (55, 'Бахыт Астана – 2005 ТОО', 'Бахыт Астана – 2005 ТОО', 'Бахыт Астана – 2005 ТОО');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (56, 'Биком Инжиниринг', 'Биком Инжиниринг', 'Биком Инжиниринг');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (57, 'Бонита Инжиниринг', 'Бонита Инжиниринг', 'Бонита Инжиниринг');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (58, 'Бразерс Компани ТОО', 'Бразерс Компани ТОО', 'Бразерс Компани ТОО');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (59, 'Бурлин Газ Строй', 'Бурлин Газ Строй', 'Бурлин Газ Строй');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (60, 'Восток ЛТД', 'Восток ЛТД', 'Восток ЛТД');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (61, 'Гражданстрой', 'Гражданстрой', 'Гражданстрой');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (62, 'Гранит Сервис', 'Гранит Сервис', 'Гранит Сервис');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (63, 'Дуние Курылыс', 'Дуние Курылыс', 'Дуние Курылыс');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (64, 'Евростандарт-Аква', 'Евростандарт-Аква', 'Евростандарт-Аква');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (65, 'Елорда даму', 'Елорда даму', 'Елорда даму');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (66, 'Жана Жол Курылыс', 'Жана Жол Курылыс', 'Жана Жол Курылыс');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (67, 'Жана Курылыс Трейдинг', 'Жана Курылыс Трейдинг', 'Жана Курылыс Трейдинг');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (68, 'Жана Курылыс', 'Жана Курылыс', 'Жана Курылыс');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (69, 'ЖСК "Собственное жилье 2017"', 'ЖСК "Собственное жилье 2017"', 'ЖСК "Собственное жилье 2017"');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (70, 'ЖСК Айнур-Астана', 'ЖСК Айнур-Астана', 'ЖСК Айнур-Астана');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (71, 'Инвестиционная строительная компания ASI', 'Инвестиционная строительная компания ASI', 'Инвестиционная строительная компания ASI');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (72, 'Каз Джордан констракшн компани', 'Каз Джордан констракшн компани', 'Каз Джордан констракшн компани');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (73, 'Казмонтажстрой', 'Казмонтажстрой', 'Казмонтажстрой');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (74, 'Каражат НС', 'Каражат НС', 'Каражат НС');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (75, 'Компания Сарыарка-Курылыс', 'Компания Сарыарка-Курылыс', 'Компания Сарыарка-Курылыс');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (76, 'Континентстрой', 'Континентстрой', 'Континентстрой');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (77, 'Концерн Строймонолит-Астана', 'Концерн Строймонолит-Астана', 'Концерн Строймонолит-Астана');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (78, 'ЛАД-строй', 'ЛАД-строй', 'ЛАД-строй');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (79, 'Лея', 'Лея', 'Лея');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (80, 'МАК Алматыгорстрой', 'МАК Алматыгорстрой', 'МАК Алматыгорстрой');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (81, 'МС-7', 'МС-7', 'МС-7');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (82, 'Найза-Курылыс', 'Найза-Курылыс', 'Найза-Курылыс');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (83, 'не указан', 'не указан', 'не указан');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (84, 'НОРД', 'НОРД', 'НОРД');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (85, 'Объединение-Сайран', 'Объединение-Сайран', 'Объединение-Сайран');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (86, 'ОМК-центр', 'ОМК-центр', 'ОМК-центр');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (87, 'Палаццо ТОО', 'Палаццо ТОО', 'Палаццо ТОО');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (88, 'Паритет-2003', 'Паритет-2003', 'Паритет-2003');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (89, 'Прайс Астана Строй', 'Прайс Астана Строй', 'Прайс Астана Строй');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (90, 'Проектная компания Айя', 'Проектная компания Айя', 'Проектная компания Айя');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (91, 'ПСК Клён', 'ПСК Клён', 'ПСК Клён');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (92, 'Ромул', 'Ромул', 'Ромул');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (93, 'Сана', 'Сана', 'Сана');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (94, 'СК «Фаворитстрой»', 'СК «Фаворитстрой»', 'СК «Фаворитстрой»');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (95, 'Сормайтремстрой', 'Сормайтремстрой', 'Сормайтремстрой');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (96, 'Строительная корпорация «Кулагер»', 'Строительная корпорация «Кулагер»', 'Строительная корпорация «Кулагер»');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (97, 'Строй-Контракт', 'Строй-Контракт', 'Строй-Контракт');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (98, 'Стройжилинвест', 'Стройжилинвест', 'Стройжилинвест');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (99, 'Стройинвест-СК', 'Стройинвест-СК', 'Стройинвест-СК');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (100, 'Стройкласс', 'Стройкласс', 'Стройкласс');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (101, 'Стройпроект', 'Стройпроект', 'Стройпроект');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (102, 'Султан', 'Султан', 'Султан');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (103, 'Сункар - 777', 'Сункар - 777', 'Сункар - 777');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (104, 'Толендi ЖСК', 'Толендi ЖСК', 'Толендi ЖСК');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (105, 'ТОО "ANT-HEAP"', 'ТОО "ANT-HEAP"', 'ТОО "ANT-HEAP"');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (106, 'ТОО "АКАN Engineering"', 'ТОО "АКАN Engineering"', 'ТОО "АКАN Engineering"');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (107, 'ТОО Global Building Contract', 'ТОО Global Building Contract', 'ТОО Global Building Contract');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (108, 'ТОО «Тандем Астана»', 'ТОО «Тандем Астана»', 'ТОО «Тандем Астана»');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (109, 'ТОО Азия', 'ТОО Азия', 'ТОО Азия');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (110, 'ТОО Арка курылыс', 'ТОО Арка курылыс', 'ТОО Арка курылыс');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (111, 'ТОО Аурика ', 'ТОО Аурика ', 'ТОО Аурика ');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (112, 'ТОО Биык Шанырак', 'ТОО Биык Шанырак', 'ТОО Биык Шанырак');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (113, 'ТОО Даса-Недвижимость', 'ТОО Даса-Недвижимость', 'ТОО Даса-Недвижимость');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (114, 'ТОО Жана курылыс', 'ТОО Жана курылыс', 'ТОО Жана курылыс');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (115, 'ТОО КазСтройПодряд', 'ТОО КазСтройПодряд', 'ТОО КазСтройПодряд');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (116, 'ТОО Корпорация Астана-Стройинвест', 'ТОО Корпорация Астана-Стройинвест', 'ТОО Корпорация Астана-Стройинвест');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (117, 'ТОО МТС Компани ЛТД', 'ТОО МТС Компани ЛТД', 'ТОО МТС Компани ЛТД');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (118, 'ТОО Ротор', 'ТОО Ротор', 'ТОО Ротор');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (119, 'ТОО СК Айкен', 'ТОО СК Айкен', 'ТОО СК Айкен');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (120, 'ТОО СК Астанатрансстрой', 'ТОО СК Астанатрансстрой', 'ТОО СК Астанатрансстрой');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (121, 'Туркуаз Инвест', 'Туркуаз Инвест', 'Туркуаз Инвест');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (122, 'Фортуна НС', 'Фортуна НС', 'Фортуна НС');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (123, 'Шанг Хи Груп', 'Шанг Хи Груп', 'Шанг Хи Груп');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (124, 'Шар Курылыс', 'Шар Курылыс', 'Шар Курылыс');
INSERT INTO public.htc_dm_dic_property_developer (id, name_en, name_kz, name_ru) VALUES (125, 'Эверест', 'Эверест', 'Эверест');
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

INSERT INTO public.htc_dm_dic_residential_complex (id, house_name, number_of_entrances, general_characteristics_id, general_characteristics_real_property_id) VALUES (1, 'Авиценна', 9, 1, null);
INSERT INTO public.htc_dm_dic_residential_complex (id, house_name, number_of_entrances, general_characteristics_id, general_characteristics_real_property_id) VALUES (2, 'str123ng', 1, 69, null);
INSERT INTO public.htc_dm_dic_residential_complex (id, house_name, number_of_entrances, general_characteristics_id, general_characteristics_real_property_id) VALUES (3, 'str123ng', 1, 70, null);
INSERT INTO public.htc_dm_dic_residential_complex (id, house_name, number_of_entrances, general_characteristics_id, general_characteristics_real_property_id) VALUES (5, 'str123ng', 1, 72, null);
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

INSERT INTO public.htc_dm_dic_sewerage (id, name_en, name_kz, name_ru) VALUES (1, 'центральная', 'центральная', 'центральная');
INSERT INTO public.htc_dm_dic_sewerage (id, name_en, name_kz, name_ru) VALUES (2, 'есть возможность подведения', 'есть возможность подведения', 'есть возможность подведения');
INSERT INTO public.htc_dm_dic_sewerage (id, name_en, name_kz, name_ru) VALUES (3, 'септик', 'септик', 'септик');
INSERT INTO public.htc_dm_dic_sewerage (id, name_en, name_kz, name_ru) VALUES (4, 'нет', 'нет', 'нет');
INSERT INTO public.htc_dm_dic_sewerage (id, name_en, name_kz, name_ru) VALUES (34, 'string', 'string', 'string');
INSERT INTO public.htc_dm_dic_sewerage (id, name_en, name_kz, name_ru) VALUES (35, 'string', 'string', 'string');
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

INSERT INTO public.htc_dm_dic_street (id, name_en, name_kz, name_ru) VALUES (1, 'Кенесары', 'Кенесары', 'Кенесары');
INSERT INTO public.htc_dm_dic_street (id, name_en, name_kz, name_ru) VALUES (2, 'Амман', 'Амман', 'Амман');
INSERT INTO public.htc_dm_dic_street (id, name_en, name_kz, name_ru) VALUES (3, 'Сарыарка', 'Сарыарка', 'Сарыарка');
INSERT INTO public.htc_dm_dic_street (id, name_en, name_kz, name_ru) VALUES (4, 'Достык', 'Достык', 'Достык');
INSERT INTO public.htc_dm_dic_street (id, name_en, name_kz, name_ru) VALUES (34, 'string', 'string', 'string');
INSERT INTO public.htc_dm_dic_street (id, name_en, name_kz, name_ru) VALUES (35, 'string', 'string', 'string');
INSERT INTO public.htc_dm_dic_street (id, name_en, name_kz, name_ru) VALUES (36, 'string', 'string', 'string');
INSERT INTO public.htc_dm_dic_street (id, name_en, name_kz, name_ru) VALUES (37, 'string', 'string', 'string');
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

INSERT INTO public.htc_dm_dic_type_of_elevator (id, name_en, name_kz, name_ru) VALUES (1, 'пассажирский лифт', 'пассажирский лифт', 'пассажирский лифт');
INSERT INTO public.htc_dm_dic_type_of_elevator (id, name_en, name_kz, name_ru) VALUES (2, 'грузовой лифт', 'грузовой лифт', 'грузовой лифт');
INSERT INTO public.htc_dm_dic_type_of_elevator (id, name_en, name_kz, name_ru) VALUES (34, 'string', 'string', 'string');
INSERT INTO public.htc_dm_dic_type_of_elevator (id, name_en, name_kz, name_ru) VALUES (35, 'string', 'string', 'string');
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

INSERT INTO public.htc_dm_dic_yard_type (id, name_en, name_kz, name_ru) VALUES (1, 'open', 'ашық', 'открытый');
INSERT INTO public.htc_dm_dic_yard_type (id, name_en, name_kz, name_ru) VALUES (2, 'private', 'жабық', 'закрытый');
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

INSERT INTO public.htc_dm_dic_yes_no (id, name_en, name_kz, name_ru, code, is_enabled, is_removed) VALUES (1, 'Yes', 'Йә', 'Да', 'true', true, false);
INSERT INTO public.htc_dm_dic_yes_no (id, name_en, name_kz, name_ru, code, is_enabled, is_removed) VALUES (2, 'No', 'Жоқ', 'Нет', 'false', true, false);
INSERT INTO public.htc_dm_dic_yes_no (id, name_en, name_kz, name_ru, code, is_enabled, is_removed) VALUES (34, 'string', 'string', 'string', 'string', true, false);
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

INSERT INTO public.htc_dm_event (id, client_id, comment, description, event_date, event_type_id, source_application, targer_application, app_id, app_id2, event_type) VALUES (3, 2, 'Event', 'Event', '2020-04-21 09:37:58.834000', 1, 1, 1, null, null, null);
INSERT INTO public.htc_dm_event (id, client_id, comment, description, event_date, event_type_id, source_application, targer_application, app_id, app_id2, event_type) VALUES (37, 1, 'string', 'string', '2020-04-21 11:45:07.467000', 1, 1, 1, null, null, null);
INSERT INTO public.htc_dm_event (id, client_id, comment, description, event_date, event_type_id, source_application, targer_application, app_id, app_id2, event_type) VALUES (38, 1, 'string', 'string', '2020-04-21 12:27:37.669000', 1, 1, 1, null, null, null);
INSERT INTO public.htc_dm_event (id, client_id, comment, description, event_date, event_type_id, source_application, targer_application, app_id, app_id2, event_type) VALUES (40, 1, 'string', 'string', '2020-04-21 14:01:30.387000', 1, 1, 1, null, null, null);
INSERT INTO public.htc_dm_event (id, client_id, comment, description, event_date, event_type_id, source_application, targer_application, app_id, app_id2, event_type) VALUES (41, 1, 'string', 'string', '2020-04-21 14:01:30.387000', 1, 1, 1, null, null, null);
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

INSERT INTO public.htc_dm_general_characteristics (id, apartments_on_the_site, ceiling_height, concierge, house_number, house_number_fraction, housing_class, housing_condition, number_of_apartments, number_of_floors, playground, wheelchair, year_of_construction, city_id, district_id, material_of_construction_id, parking_type_id, property_developer_id, street_id, yard_type_id) VALUES (6, 'apartmentsOnTheSite', 10.00, true, 56, '1', '5', 'housingCondition', 133, 6, true, true, 2010, 1, 1, 1, 1, 1, 1, 1);
INSERT INTO public.htc_dm_general_characteristics (id, apartments_on_the_site, ceiling_height, concierge, house_number, house_number_fraction, housing_class, housing_condition, number_of_apartments, number_of_floors, playground, wheelchair, year_of_construction, city_id, district_id, material_of_construction_id, parking_type_id, property_developer_id, street_id, yard_type_id) VALUES (7, 'apartmentsOnTheSite', 10.00, true, 56, '1', '5', 'housingCondition', 133, 6, true, true, 2010, 1, 1, 1, 1, 1, 1, 1);
INSERT INTO public.htc_dm_general_characteristics (id, apartments_on_the_site, ceiling_height, concierge, house_number, house_number_fraction, housing_class, housing_condition, number_of_apartments, number_of_floors, playground, wheelchair, year_of_construction, city_id, district_id, material_of_construction_id, parking_type_id, property_developer_id, street_id, yard_type_id) VALUES (8, 'apartmentsOnTheSite', 10.00, true, 56, '1', '5', 'housingCondition', 133, 6, true, true, 2010, 1, 1, 1, 1, 1, 1, 1);
INSERT INTO public.htc_dm_general_characteristics (id, apartments_on_the_site, ceiling_height, concierge, house_number, house_number_fraction, housing_class, housing_condition, number_of_apartments, number_of_floors, playground, wheelchair, year_of_construction, city_id, district_id, material_of_construction_id, parking_type_id, property_developer_id, street_id, yard_type_id) VALUES (39, 'apartmentsOnTheSite', 10.00, true, 56, '1', '5', 'housingCondition', 133, 6, true, true, 2010, 1, 1, 1, 1, 1, 1, 1);
INSERT INTO public.htc_dm_general_characteristics (id, apartments_on_the_site, ceiling_height, concierge, house_number, house_number_fraction, housing_class, housing_condition, number_of_apartments, number_of_floors, playground, wheelchair, year_of_construction, city_id, district_id, material_of_construction_id, parking_type_id, property_developer_id, street_id, yard_type_id) VALUES (40, 'apartmentsOnTheSite', 10.00, true, 56, '1', '5', 'housingCondition', 133, 6, true, true, 2010, 1, 1, 1, 1, 1, 1, 1);
INSERT INTO public.htc_dm_general_characteristics (id, apartments_on_the_site, ceiling_height, concierge, house_number, house_number_fraction, housing_class, housing_condition, number_of_apartments, number_of_floors, playground, wheelchair, year_of_construction, city_id, district_id, material_of_construction_id, parking_type_id, property_developer_id, street_id, yard_type_id) VALUES (48, 'apartmentsOnTheSite', 10.00, true, 56, '1', '5', 'housingCondition', 123, 6, true, true, 2012, 1, 1, 1, 1, 1, 1, 1);
INSERT INTO public.htc_dm_general_characteristics (id, apartments_on_the_site, ceiling_height, concierge, house_number, house_number_fraction, housing_class, housing_condition, number_of_apartments, number_of_floors, playground, wheelchair, year_of_construction, city_id, district_id, material_of_construction_id, parking_type_id, property_developer_id, street_id, yard_type_id) VALUES (60, 'apartmentsOnTheSite', 10.00, true, 56, '1', '5', 'housingCondition', 133, 6, true, true, 2010, 1, 1, 1, 1, 1, 1, 1);
INSERT INTO public.htc_dm_general_characteristics (id, apartments_on_the_site, ceiling_height, concierge, house_number, house_number_fraction, housing_class, housing_condition, number_of_apartments, number_of_floors, playground, wheelchair, year_of_construction, city_id, district_id, material_of_construction_id, parking_type_id, property_developer_id, street_id, yard_type_id) VALUES (61, 'apartmentsOnTheSite', 10.00, true, 56, '1', '5', 'housingCondition', 133, 6, true, true, 2010, 1, 1, 1, 1, 1, 1, 1);
INSERT INTO public.htc_dm_general_characteristics (id, apartments_on_the_site, ceiling_height, concierge, house_number, house_number_fraction, housing_class, housing_condition, number_of_apartments, number_of_floors, playground, wheelchair, year_of_construction, city_id, district_id, material_of_construction_id, parking_type_id, property_developer_id, street_id, yard_type_id) VALUES (62, 'apartmentsOnTheSite', 10.00, true, 56, '1', '5', 'housingCondition', 133, 6, true, true, 2010, 1, 1, 1, 1, 1, 1, 1);
INSERT INTO public.htc_dm_general_characteristics (id, apartments_on_the_site, ceiling_height, concierge, house_number, house_number_fraction, housing_class, housing_condition, number_of_apartments, number_of_floors, playground, wheelchair, year_of_construction, city_id, district_id, material_of_construction_id, parking_type_id, property_developer_id, street_id, yard_type_id) VALUES (65, 'apartmentsOnTheSite', 10.00, true, 56, '1', '5', 'housingCondition', 133, 6, true, true, 2010, 1, 1, 1, 1, 1, 1, 1);
INSERT INTO public.htc_dm_general_characteristics (id, apartments_on_the_site, ceiling_height, concierge, house_number, house_number_fraction, housing_class, housing_condition, number_of_apartments, number_of_floors, playground, wheelchair, year_of_construction, city_id, district_id, material_of_construction_id, parking_type_id, property_developer_id, street_id, yard_type_id) VALUES (66, 'apartmentsOnTheSite', 10.00, true, 56, '1', '5', 'housingCondition', 133, 6, true, true, 2010, 1, 1, 1, 1, 1, 1, 1);
INSERT INTO public.htc_dm_general_characteristics (id, apartments_on_the_site, ceiling_height, concierge, house_number, house_number_fraction, housing_class, housing_condition, number_of_apartments, number_of_floors, playground, wheelchair, year_of_construction, city_id, district_id, material_of_construction_id, parking_type_id, property_developer_id, street_id, yard_type_id) VALUES (67, 'apartmentsOnTheSite', 10.00, true, 56, '1', '5', 'housingCondition', 133, 6, true, true, 2010, 1, 1, 1, 1, 1, 1, 1);
INSERT INTO public.htc_dm_general_characteristics (id, apartments_on_the_site, ceiling_height, concierge, house_number, house_number_fraction, housing_class, housing_condition, number_of_apartments, number_of_floors, playground, wheelchair, year_of_construction, city_id, district_id, material_of_construction_id, parking_type_id, property_developer_id, street_id, yard_type_id) VALUES (72, '123', 1.00, true, 1, '123', '123', '123', 1, 1, true, true, 1, 1, 1, 1, 1, 11, 1, null);
INSERT INTO public.htc_dm_general_characteristics (id, apartments_on_the_site, ceiling_height, concierge, house_number, house_number_fraction, housing_class, housing_condition, number_of_apartments, number_of_floors, playground, wheelchair, year_of_construction, city_id, district_id, material_of_construction_id, parking_type_id, property_developer_id, street_id, yard_type_id) VALUES (69, '123', 1.00, true, 1, '123', '123', '123', 1, 1, true, true, 1, 1, 1, 1, 1, 1, 1, 1);
INSERT INTO public.htc_dm_general_characteristics (id, apartments_on_the_site, ceiling_height, concierge, house_number, house_number_fraction, housing_class, housing_condition, number_of_apartments, number_of_floors, playground, wheelchair, year_of_construction, city_id, district_id, material_of_construction_id, parking_type_id, property_developer_id, street_id, yard_type_id) VALUES (71, '123', 1.00, true, 1, '123', '123', '123', 1, 1, true, true, 1, 1, 1, 1, 1, 1, 1, 1);
INSERT INTO public.htc_dm_general_characteristics (id, apartments_on_the_site, ceiling_height, concierge, house_number, house_number_fraction, housing_class, housing_condition, number_of_apartments, number_of_floors, playground, wheelchair, year_of_construction, city_id, district_id, material_of_construction_id, parking_type_id, property_developer_id, street_id, yard_type_id) VALUES (70, '123', 1.00, true, 1, '123', '123', '123', 1, 1, true, true, 1, 1, 1, 1, 1, 1, 1, 1);
INSERT INTO public.htc_dm_general_characteristics (id, apartments_on_the_site, ceiling_height, concierge, house_number, house_number_fraction, housing_class, housing_condition, number_of_apartments, number_of_floors, playground, wheelchair, year_of_construction, city_id, district_id, material_of_construction_id, parking_type_id, property_developer_id, street_id, yard_type_id) VALUES (5, 'apartmentsOnTheSite', 3.00, true, 20, '1', 'housingClass', 'housingCondition', 3, 32, true, true, 2012, 1, 1, 1, 1, 1, 1, 1);
INSERT INTO public.htc_dm_general_characteristics (id, apartments_on_the_site, ceiling_height, concierge, house_number, house_number_fraction, housing_class, housing_condition, number_of_apartments, number_of_floors, playground, wheelchair, year_of_construction, city_id, district_id, material_of_construction_id, parking_type_id, property_developer_id, street_id, yard_type_id) VALUES (2, 'apartmentsOnTheSite', 3.00, true, 20, '1', 'housingClass', 'housingCondition', 3, 32, true, true, 2012, 1, 1, 1, 1, 1, 1, 1);
INSERT INTO public.htc_dm_general_characteristics (id, apartments_on_the_site, ceiling_height, concierge, house_number, house_number_fraction, housing_class, housing_condition, number_of_apartments, number_of_floors, playground, wheelchair, year_of_construction, city_id, district_id, material_of_construction_id, parking_type_id, property_developer_id, street_id, yard_type_id) VALUES (1, '1', 1.00, true, 171, '/2', 'комфорт класс', 'хорошее состояние', 10, 12, true, true, 1991, 1, 1, 1, 1, 1, 1, 1);
INSERT INTO public.htc_dm_general_characteristics (id, apartments_on_the_site, ceiling_height, concierge, house_number, house_number_fraction, housing_class, housing_condition, number_of_apartments, number_of_floors, playground, wheelchair, year_of_construction, city_id, district_id, material_of_construction_id, parking_type_id, property_developer_id, street_id, yard_type_id) VALUES (102, '12', 123.00, true, 171, null, 'комфорт класс', 'хорошее состояние', 10, 12, true, true, 1991, 2, 1, 1, null, 1, 1, 1);
INSERT INTO public.htc_dm_general_characteristics (id, apartments_on_the_site, ceiling_height, concierge, house_number, house_number_fraction, housing_class, housing_condition, number_of_apartments, number_of_floors, playground, wheelchair, year_of_construction, city_id, district_id, material_of_construction_id, parking_type_id, property_developer_id, street_id, yard_type_id) VALUES (103, '12', 321.00, true, 171, null, 'комфорт класс', 'хорошее состояние', 10, 12, true, true, 1991, 2, 1, 1, null, 1, 1, 1);
INSERT INTO public.htc_dm_general_characteristics (id, apartments_on_the_site, ceiling_height, concierge, house_number, house_number_fraction, housing_class, housing_condition, number_of_apartments, number_of_floors, playground, wheelchair, year_of_construction, city_id, district_id, material_of_construction_id, parking_type_id, property_developer_id, street_id, yard_type_id) VALUES (139, null, 2.00, null, 10, '1', null, null, null, null, null, null, null, 1, 3, null, null, null, 2, null);
INSERT INTO public.htc_dm_general_characteristics (id, apartments_on_the_site, ceiling_height, concierge, house_number, house_number_fraction, housing_class, housing_condition, number_of_apartments, number_of_floors, playground, wheelchair, year_of_construction, city_id, district_id, material_of_construction_id, parking_type_id, property_developer_id, street_id, yard_type_id) VALUES (142, null, 3.00, null, 10, null, null, null, null, null, null, null, null, 1, 2, null, null, null, 1, null);
INSERT INTO public.htc_dm_general_characteristics (id, apartments_on_the_site, ceiling_height, concierge, house_number, house_number_fraction, housing_class, housing_condition, number_of_apartments, number_of_floors, playground, wheelchair, year_of_construction, city_id, district_id, material_of_construction_id, parking_type_id, property_developer_id, street_id, yard_type_id) VALUES (150, null, 2.50, null, 10, null, null, null, null, null, null, null, null, 1, 1, null, null, null, 1, null);
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

INSERT INTO public.htc_dm_general_characteristics_type_of_elevator (general_characteristics_id, type_of_elevator_id) VALUES (69, 1);
INSERT INTO public.htc_dm_general_characteristics_type_of_elevator (general_characteristics_id, type_of_elevator_id) VALUES (70, 1);
INSERT INTO public.htc_dm_general_characteristics_type_of_elevator (general_characteristics_id, type_of_elevator_id) VALUES (71, 1);
INSERT INTO public.htc_dm_general_characteristics_type_of_elevator (general_characteristics_id, type_of_elevator_id) VALUES (72, 1);
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

INSERT INTO public.htc_dm_real_property (id, created_by, created_date, last_modified_by, last_modified_date, apartment_number, atelier, balcony_area, cadastral_number, files_map, floor, kitchen_area, land_area, living_area, number_of_bedrooms, number_of_rooms, separate_bathroom, total_area, general_characteristics_id, heating_system_id, object_type_id, residential_complex_id, sewerage_id) VALUES (89, 'system', '2020-04-22 04:05:56.265000', 'system', '2020-04-22 04:05:56.265000', '171', null, null, null, null, null, 123.00, null, 123.00, 1, 3, null, 123.00, 102, null, 1, 1, null);
INSERT INTO public.htc_dm_real_property (id, created_by, created_date, last_modified_by, last_modified_date, apartment_number, atelier, balcony_area, cadastral_number, files_map, floor, kitchen_area, land_area, living_area, number_of_bedrooms, number_of_rooms, separate_bathroom, total_area, general_characteristics_id, heating_system_id, object_type_id, residential_complex_id, sewerage_id) VALUES (90, 'system', '2020-04-22 04:20:54.147000', 'system', '2020-04-22 04:20:54.147000', '171', null, null, null, null, null, 123.00, null, 123.00, 1, 3, null, 123.00, 103, null, 1, 1, null);
INSERT INTO public.htc_dm_real_property (id, created_by, created_date, last_modified_by, last_modified_date, apartment_number, atelier, balcony_area, cadastral_number, files_map, floor, kitchen_area, land_area, living_area, number_of_bedrooms, number_of_rooms, separate_bathroom, total_area, general_characteristics_id, heating_system_id, object_type_id, residential_complex_id, sewerage_id) VALUES (126, 'system', '2020-04-24 03:07:04.449000', 'system', '2020-04-24 03:07:04.449000', '10', null, null, null, null, null, 8.00, null, 40.00, 1, 2, null, 50.00, 139, null, 1, null, null);
INSERT INTO public.htc_dm_real_property (id, created_by, created_date, last_modified_by, last_modified_date, apartment_number, atelier, balcony_area, cadastral_number, files_map, floor, kitchen_area, land_area, living_area, number_of_bedrooms, number_of_rooms, separate_bathroom, total_area, general_characteristics_id, heating_system_id, object_type_id, residential_complex_id, sewerage_id) VALUES (129, 'system', '2020-04-24 03:12:09.810000', 'system', '2020-04-24 03:12:09.810000', '10', null, null, null, null, null, 10.00, null, 100.00, 1, 3, null, 120.00, 142, null, 1, null, null);
INSERT INTO public.htc_dm_real_property (id, created_by, created_date, last_modified_by, last_modified_date, apartment_number, atelier, balcony_area, cadastral_number, files_map, floor, kitchen_area, land_area, living_area, number_of_bedrooms, number_of_rooms, separate_bathroom, total_area, general_characteristics_id, heating_system_id, object_type_id, residential_complex_id, sewerage_id) VALUES (55, 'a.kussein', '2020-04-21 11:47:53.850000', 'a.kussein', '2020-04-21 14:15:40.504000', '432', true, 10.00, '453111454336', '{"PHOTO": ["123123", "2354"], "HOUSING_PLAN": ["123123", "5435"], "VIRTUAL_TOUR": ["55", "36546"]}', 5, 10.00, 20.00, 30.00, 2, 2, true, 60.00, 61, 1, 1, null, 1);
INSERT INTO public.htc_dm_real_property (id, created_by, created_date, last_modified_by, last_modified_date, apartment_number, atelier, balcony_area, cadastral_number, files_map, floor, kitchen_area, land_area, living_area, number_of_bedrooms, number_of_rooms, separate_bathroom, total_area, general_characteristics_id, heating_system_id, object_type_id, residential_complex_id, sewerage_id) VALUES (3, 'AllTest', '2020-04-21 09:56:04.642000', 'AllTest', '2020-04-21 11:12:51.647000', '332', true, 10.00, '45345436', '{"PHOTO": ["23534"], "HOUSING_PLAN": ["54354"], "VIRTUAL_TOUR": ["36546"]}', 5, 10.00, 20.00, 30.00, 2, 2, true, 60.00, 48, 1, 1, null, 1);
INSERT INTO public.htc_dm_real_property (id, created_by, created_date, last_modified_by, last_modified_date, apartment_number, atelier, balcony_area, cadastral_number, files_map, floor, kitchen_area, land_area, living_area, number_of_bedrooms, number_of_rooms, separate_bathroom, total_area, general_characteristics_id, heating_system_id, object_type_id, residential_complex_id, sewerage_id) VALUES (56, 'AppTest', '2020-04-21 12:15:30.785000', 'AppTest', '2020-04-21 12:16:28.761000', '433', true, 10.00, '4534115436', '{"PHOTO": ["2354"], "HOUSING_PLAN": ["5435"], "VIRTUAL_TOUR": ["36546"]}', 5, 10.00, 20.00, 30.00, 2, 2, true, 60.00, 67, 1, 1, null, 1);
INSERT INTO public.htc_dm_real_property (id, created_by, created_date, last_modified_by, last_modified_date, apartment_number, atelier, balcony_area, cadastral_number, files_map, floor, kitchen_area, land_area, living_area, number_of_bedrooms, number_of_rooms, separate_bathroom, total_area, general_characteristics_id, heating_system_id, object_type_id, residential_complex_id, sewerage_id) VALUES (54, 'test', '2020-04-21 11:47:33.872000', 'test', '2020-04-21 11:47:33.872000', '434', true, 10.00, '453454336', '{"PHOTO": ["2354"], "HOUSING_PLAN": ["5435"], "VIRTUAL_TOUR": ["36546"]}', 5, 10.00, 20.00, 30.00, 2, 2, true, 60.00, 60, 1, 1, null, 1);
INSERT INTO public.htc_dm_real_property (id, created_by, created_date, last_modified_by, last_modified_date, apartment_number, atelier, balcony_area, cadastral_number, files_map, floor, kitchen_area, land_area, living_area, number_of_bedrooms, number_of_rooms, separate_bathroom, total_area, general_characteristics_id, heating_system_id, object_type_id, residential_complex_id, sewerage_id) VALUES (2, 'AppTest', '2020-04-21 09:56:04.642000', 'AppTest', '2020-04-21 11:12:51.647000', '333', null, 10.00, '4534543623', '{"PHOTO": ["23534"], "HOUSING_PLAN": ["54354"], "VIRTUAL_TOUR": ["36546"]}', 5, 10.00, 20.00, 30.00, 2, 2, true, 60.00, 48, 1, 1, null, 1);
INSERT INTO public.htc_dm_real_property (id, created_by, created_date, last_modified_by, last_modified_date, apartment_number, atelier, balcony_area, cadastral_number, files_map, floor, kitchen_area, land_area, living_area, number_of_bedrooms, number_of_rooms, separate_bathroom, total_area, general_characteristics_id, heating_system_id, object_type_id, residential_complex_id, sewerage_id) VALUES (1, 'test', '2020-04-21 08:33:53.336000', 'test', '2020-04-21 14:16:39.416000', '12', true, 10.00, '14123', '{"PHOTO": ["7683899f-7eee-4dec-b2c4-0ad0e724aa03\"", "1", "123123"], "HOUSING_PLAN": ["1", "123123", "7683899f-7eee-4dec-b2c4-0ad0e724aa03"], "VIRTUAL_TOUR": ["1"]}', 5, 10.00, 30.00, 20.00, 2, 2, true, 60.00, 5, 1, 1, null, 1);
INSERT INTO public.htc_dm_real_property (id, created_by, created_date, last_modified_by, last_modified_date, apartment_number, atelier, balcony_area, cadastral_number, files_map, floor, kitchen_area, land_area, living_area, number_of_bedrooms, number_of_rooms, separate_bathroom, total_area, general_characteristics_id, heating_system_id, object_type_id, residential_complex_id, sewerage_id) VALUES (137, 'system', '2020-04-24 03:38:02.713000', 'system', '2020-04-24 03:38:02.713000', '10', null, null, null, null, null, 6.00, null, 38.00, 1, 1, null, 45.00, 150, null, 1, null, null);
create table htc_dm_real_property_owner
(
 id bigserial not null
 constraint htc_dm_real_property_owner_pkey
 primary key,
 created_by varchar(255) not null,
 created_date timestamp not null,
 last_modified_by varchar(255) not null,
 last_modified_date timestamp not null,
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

INSERT INTO public.htc_dm_real_property_owner (id, created_by, created_date, last_modified_by, last_modified_date, email, first_name, gender, patronymic, phone_number, surname) VALUES (123, 'system', '2020-04-24 03:38:02.703000', 'system', '2020-04-24 03:38:02.703000', null, 'Канат', 'MALE', null, '76094441122', 'Ислам');
INSERT INTO public.htc_dm_real_property_owner (id, created_by, created_date, last_modified_by, last_modified_date, email, first_name, gender, patronymic, phone_number, surname) VALUES (72, 'system', '2020-04-22 04:05:55.851000', 'system', '2020-04-22 04:05:55.851000', 'asd@asd.kl', 'asd', 'MALE', null, '71111111111', 'asd');
INSERT INTO public.htc_dm_real_property_owner (id, created_by, created_date, last_modified_by, last_modified_date, email, first_name, gender, patronymic, phone_number, surname) VALUES (5, 'system', '2020-04-21 12:39:38.293000', 'system', '2020-04-21 12:39:38.293000', 'string', 'Artur', 'MALE', 'Amantayevich', '77854788925', 'Saduov');
INSERT INTO public.htc_dm_real_property_owner (id, created_by, created_date, last_modified_by, last_modified_date, email, first_name, gender, patronymic, phone_number, surname) VALUES (3, 'system', '2020-04-21 08:35:10.316000', 'system', '2020-04-21 08:35:10.316000', '111', 'Olga', 'FEMALE', 'Nikitina', '71111111112', 'Semina');
INSERT INTO public.htc_dm_real_property_owner (id, created_by, created_date, last_modified_by, last_modified_date, email, first_name, gender, patronymic, phone_number, surname) VALUES (7, 'system', '2020-04-22 01:28:42.000000', 'system', '2020-04-23 01:30:05.000000', 'test@test.kz', 'Timur', 'MALE', 'Kasymkhanyly', '77045848789', 'Kabylbekov');
INSERT INTO public.htc_dm_real_property_owner (id, created_by, created_date, last_modified_by, last_modified_date, email, first_name, gender, patronymic, phone_number, surname) VALUES (6, 'system', '2020-04-22 01:16:09.000000', 'system', '2020-04-22 01:16:17.000000', 'test@test.kz', 'Obivan', 'MALE', 'null', '78748896577', 'Kenobi');
INSERT INTO public.htc_dm_real_property_owner (id, created_by, created_date, last_modified_by, last_modified_date, email, first_name, gender, patronymic, phone_number, surname) VALUES (4, 'system', '2020-04-21 12:29:42.703000', 'system', '2020-04-21 12:29:42.703000', 'string', 'Jack', 'MALE', 'null', '77458548965', 'Sparrow');
INSERT INTO public.htc_dm_real_property_owner (id, created_by, created_date, last_modified_by, last_modified_date, email, first_name, gender, patronymic, phone_number, surname) VALUES (2, 'system', '2020-04-21 08:33:53.024000', 'system', '2020-04-21 08:33:53.024000', 'email', 'Katya', 'FEMALE', 'Ivanovna', '77011111111', 'Ivanova');
INSERT INTO public.htc_dm_real_property_owner (id, created_by, created_date, last_modified_by, last_modified_date, email, first_name, gender, patronymic, phone_number, surname) VALUES (73, 'system', '2020-04-22 04:20:54.138000', 'system', '2020-04-22 04:20:54.138000', 'asd@asd.kz', 'фыв', 'MALE', 'фв', '77082222222', 'фыв');
INSERT INTO public.htc_dm_real_property_owner (id, created_by, created_date, last_modified_by, last_modified_date, email, first_name, gender, patronymic, phone_number, surname) VALUES (109, 'system', '2020-04-24 03:07:04.442000', 'system', '2020-04-24 03:07:04.442000', null, 'Али', 'UNKNOWN', null, '77024079841', 'Алиев');
INSERT INTO public.htc_dm_real_property_owner (id, created_by, created_date, last_modified_by, last_modified_date, email, first_name, gender, patronymic, phone_number, surname) VALUES (115, 'system', '2020-04-24 03:12:09.802000', 'system', '2020-04-24 03:12:09.802000', null, 'Сергей', 'UNKNOWN', null, '77778884443', 'Водопьянов');