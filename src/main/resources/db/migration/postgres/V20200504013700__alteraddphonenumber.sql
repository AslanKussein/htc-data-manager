
ALTER TABLE htc_dm_add_phone_number
    ADD COLUMN created_by varchar(255)          not null;


ALTER TABLE htc_dm_add_phone_number
    ADD COLUMN created_date timestamp          not null;


ALTER TABLE htc_dm_add_phone_number
    ADD COLUMN last_modified_by varchar(255)          not null;

ALTER TABLE htc_dm_add_phone_number
    ADD COLUMN last_modified_date timestamp        not null;

ALTER TABLE htc_dm_add_phone_number
    ADD COLUMN is_removed boolean default false         not null;

