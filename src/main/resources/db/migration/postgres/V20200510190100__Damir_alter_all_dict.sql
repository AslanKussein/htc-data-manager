alter table htc_dm_dic_all_dict
  drop column is_system,
  drop column is_enabled;

alter table htc_dm_dic_application_status
  drop column is_enabled;

alter table htc_dm_dic_object_type
  drop column is_enabled;

alter table htc_dm_dic_operation_type
  drop column is_enabled;

alter table htc_dm_dic_yes_no
  drop column is_enabled;

alter table htc_dm_dic_event_type
  add column code varchar(255) null
    constraint un_event_type_code
      unique,
  add column is_removed boolean default false;

delete from htc_dm_dic_all_dict;

insert into htc_dm_dic_all_dict(code, name_kz, name_ru, name_en, is_editable, is_removed)
values ('City', 'Город', 'Город', 'City', true, false);
insert into htc_dm_dic_all_dict(code, name_kz, name_ru, name_en, is_editable, is_removed)
values ('Country', 'Страна', 'Страна', 'Country', true, false);
insert into htc_dm_dic_all_dict(code, name_kz, name_ru, name_en, is_editable, is_removed)
values ('District', 'Район', 'Район', 'District', true, false);
insert into htc_dm_dic_all_dict(code, name_kz, name_ru, name_en, is_editable, is_removed)
values ('Street', 'Улица', 'Улица', 'Street', true, false);
insert into htc_dm_dic_all_dict(code, name_kz, name_ru, name_en, is_editable, is_removed)
values ('HeatingSystem', 'Тип отопления', 'Тип отопления', 'Heating system', true, false);
insert into htc_dm_dic_all_dict(code, name_kz, name_ru, name_en, is_editable, is_removed)
values ('MaterialOfConstruction', 'Материал постройки', 'Материал постройки', 'Material of construction', true, false);
insert into htc_dm_dic_all_dict(code, name_kz, name_ru, name_en, is_editable, is_removed)
values ('ParkingType', 'Тип паркинга', 'Тип паркинга', 'Parking type', true, false);
insert into htc_dm_dic_all_dict(code, name_kz, name_ru, name_en, is_editable, is_removed)
values ('PossibleReasonForBidding', 'Причины торга', 'Причины торга', 'Possible reason for bidding', true, false);
insert into htc_dm_dic_all_dict(code, name_kz, name_ru, name_en, is_editable, is_removed)
values ('PropertyDeveloper', 'Застройщик', 'Застройщик', 'Property developer', true, false);
insert into htc_dm_dic_all_dict(code, name_kz, name_ru, name_en, is_editable, is_removed)
values ('Sewerage', 'Тип канализации', 'Тип канализации', 'Sewerage', true, false);
insert into htc_dm_dic_all_dict(code, name_kz, name_ru, name_en, is_editable, is_removed)
values ('TypeOfElevator', 'Тип лифта', 'Тип лифта', 'Type of elevator', true, false);
insert into htc_dm_dic_all_dict(code, name_kz, name_ru, name_en, is_editable, is_removed)
values ('YardType', 'Тип двора', 'Тип двора', 'Yard type', true, false);

insert into htc_dm_dic_all_dict(code, name_kz, name_ru, name_en, is_editable, is_removed)
values ('EventType', 'Тип события', 'Тип события', 'Event type', false, false);
insert into htc_dm_dic_all_dict(code, name_kz, name_ru, name_en, is_editable, is_removed)
values ('ObjectType', 'Тип объекта', 'Тип объекта', 'Object type', false, false);
insert into htc_dm_dic_all_dict(code, name_kz, name_ru, name_en, is_editable, is_removed)
values ('OperationType', 'Тип операции', 'Тип операции', 'Operation type', false, false);
insert into htc_dm_dic_all_dict(code, name_kz, name_ru, name_en, is_editable, is_removed)
values ('ApplicationStatus', 'Статус заявки', 'Статус заявки', 'Application status', false, false);