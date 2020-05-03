create table htc_dm_notes
(
    id      bigserial    not null
        constraint htc_dm_notes_pkey primary key,
    text text not null,
    deleted boolean default false,
    real_property_id numeric references htc_dm_real_property(id)
)