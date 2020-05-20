insert into htc_dm_dic_house_condition (name_en, name_kz, name_ru, is_removed) values ('С ремонтом', 'С ремонтом', 'С ремонтом', false);
insert into htc_dm_dic_house_condition (name_en, name_kz, name_ru, is_removed) values ('Без ремонта', 'Без ремонта', 'Без ремонта', false);
insert into htc_dm_dic_house_condition (name_en, name_kz, name_ru, is_removed) values ('Дизайнерский ремонт', 'Дизайнерский ремонт', 'Дизайнерский ремонт', false);

insert into htc_dm_dic_object_type (name_en, name_kz, name_ru, code, is_removed) values ('Коммерческое помещение', 'Коммерческое помещение', 'Коммерческое помещение', '003003', false);
insert into htc_dm_dic_object_type (name_en, name_kz, name_ru, code, is_removed) values ('Новостройки', 'Новостройки', 'Новостройки', '003004', false);

insert into htc_dm_dic_metadata_status (name_en, name_kz, name_ru, code, is_removed) values ('Approved', 'Подтверждено', 'Подтверждено', '001001', false);
insert into htc_dm_dic_metadata_status (name_en, name_kz, name_ru, code, is_removed) values ('Not approved', 'Не подтверждено', 'Не подтверждено', '001002', false);
insert into htc_dm_dic_metadata_status (name_en, name_kz, name_ru, code, is_removed) values ('Archive', 'Архив', 'Архив', '001003', false);

insert into htc_dm_dic_all_dict (name_en, name_kz, name_ru, code, is_editable, is_removed) values ('House condition', 'Состояние недвижимости', 'Состояние недвижимости', 'HouseCondition', true, false);
insert into htc_dm_dic_all_dict (name_en, name_kz, name_ru, code, is_editable, is_removed) values ('Metadata status', 'Статус метадаты недвижимости', 'Статус метадаты недвижимости', 'MetadataStatus', false, false);