-- auto-generated definition
create sequence htc_dm_add_phone_number_id_seq;

alter sequence htc_dm_add_phone_number_id_seq owner to postgres;
create table htc_dm_add_phone_number
(
    id      bigint  default nextval('htc_dm_add_phone_number_id_seq'::regclass)     not null
        constraint htc_dm_add_phone_number_pkey primary key,
    phone_number varchar(255)   not null,
    client_id bigint references htc_dm_client(id)
)
