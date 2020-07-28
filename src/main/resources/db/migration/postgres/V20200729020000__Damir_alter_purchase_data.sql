alter table htc_dm_purchase_data
  add column districts jsonb;

update htc_dm_purchase_data set districts = '[{"id": ' + district_id + '}]';

alter table htc_dm_purchase_data
  drop column district_id;