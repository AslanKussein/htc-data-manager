-- auto-generated definition
create sequence htc_dm_client_file_id_seq;

alter sequence htc_dm_client_file_id_seq owner to postgres;
create table htc_dm_client_file
(
    id      bigint  default nextval('htc_dm_client_file_id_seq'::regclass)     not null
        constraint htc_dm_client_file_pkey primary key,
    guid varchar(255)   not null,
    client_id bigint references htc_dm_client(id)
)