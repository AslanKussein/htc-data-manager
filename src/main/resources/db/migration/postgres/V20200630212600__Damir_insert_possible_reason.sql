INSERT INTO htc_dm_dic_possible_reason_for_bidding (id, name_en, name_kz, name_ru, operation_code) VALUES (8, 'встреча', 'встреча', 'встреча', '001001');

update htc_dm_dic_possible_reason_for_bidding b set operation_type_id = (select t.id from htc_dm_dic_operation_type t where t.code = b.operation_type_id);