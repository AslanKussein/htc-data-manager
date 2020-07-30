drop view if exists htc_dm_v_real_property_register;

create or replace view htc_dm_v_real_property_register (id, created_date, application_id, sell_data_id,
                                                        object_price, total_area, number_of_rooms,
                                                        metadata_id, building_id, average_price, apartment_number,
                                                        cadastral_number, is_reserved, files_map)
as
select rp.id                                            as id,
       rp.created_date                                  as created_date,
       sd.application_id                                as application_id,
       sd.id                                            as sell_data_id,
       sd.object_price                                  as object_price,
       rpm.total_area                                   as total_area,
       rpm.number_of_rooms                              as number_of_rooms,
       rpm.id                                           as metadata_id,
       b.id                                             as building_id,
       coalesce(rpba.average_price, rpda.average_price) as average_price,
       rp.apartment_number                              as apartment_number,
       rp.cadastral_number                              as cadastral_number,
       rp.is_reserved                                   as is_reserved,
       rpf.files_map                                    as files_map
from htc_dm_real_property rp
       join htc_dm_sell_data sd
            on sd.id = (select hdsd.id
                        from htc_dm_sell_data hdsd
                               join htc_dm_application hda
                                    on hdsd.application_id = hda.id
                                      and hda.is_removed = false
                        where hdsd.real_property_id = rp.id
                        order by hdsd.object_price
                        limit 1)
       join htc_dm_real_property_metadata rpm
            on rp.id = rpm.real_property_id and rpm.metadata_status_id = 1
       left join htc_dm_real_property_file rpf
                 on rp.id = rpf.real_property_id and rpf.metadata_status_id = 1
       join htc_dm_general_characteristics gc
            on rpm.general_characteristics_id = gc.id
       join htc_dm_building b
            on rp.building_id = b.id
       left join htc_dm_real_property_analytics rpba
                 on rp.building_id = rpba.building_id
       left join htc_dm_real_property_analytics rpda
                 on rpda.district_id = b.district_id and rpda.house_class_id = gc.house_class_id;