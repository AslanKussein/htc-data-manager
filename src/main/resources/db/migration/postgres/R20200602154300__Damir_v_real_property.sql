drop view if exists htc_dm_v_real_property_register;

create or replace view htc_dm_v_real_property_register (id, created_date, application_id, sell_data_id,
                                                        object_price, total_area, number_of_rooms,
                                                        metadata_id, building_id, apartment_number,
                                                        cadastral_number, files_map)
as
select rp.id               as id,
       rp.created_date     as created_date,
       a.id                as application_id,
       sd.id               as sell_data_id,
       sd.object_price     as object_price,
       rpm.total_area      as total_area,
       rpm.number_of_rooms as number_of_rooms,
       rpm.id              as metadata_id,
       rp.building_id      as building_id,
       rp.apartment_number as apartment_number,
       rp.cadastral_number as cadastral_number,
       rp.files_map        as files_map
from htc_dm_real_property rp
       join htc_dm_sell_data sd
            on sd.real_property_id = rp.id
       join htc_dm_application a
            on sd.application_id = a.id and a.is_removed = false
       join htc_dm_real_property_metadata rpm
            on rp.id = rpm.real_property_id and rpm.metadata_status_id = 1
       join htc_dm_general_characteristics gc
            on rpm.general_characteristics_id = gc.id
;