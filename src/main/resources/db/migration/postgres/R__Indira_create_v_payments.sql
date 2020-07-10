drop view if exists htc_dm_v_payment;

create or replace view htc_dm_v_payment as
select c.id, c.application_id, null sell_application_id , c.contract_number,  c.commission payed_sum, 6  pay_type_id,
       c.file_guid, c.created_by, c.created_date
from htc_dm_application_contract c
union
select d.id, d.application_id, d.sell_application_id , d.contract_number , d.payed_sum, d.pay_type_id, d.file_guid, d.created_by, d.created_date
from htc_dm_application_deposit d