create table htc_dm_dic_pay_type
(
  id bigserial not null
    constraint htc_dm_dic_pay_type_pkey
      primary key,
  name_en varchar(255) not null,
  name_kz varchar(255) not null,
  name_ru varchar(255) not null,
  is_removed boolean default false not null
);

alter table htc_dm_dic_pay_type
  owner to postgres;

INSERT INTO htc_dm_dic_pay_type (id, name_en, name_kz, name_ru, is_removed) VALUES (1, 'Бронь покупки', 'Бронь покупки', 'Бронь покупки', false);
INSERT INTO htc_dm_dic_pay_type (id, name_en, name_kz, name_ru, is_removed) VALUES (2, 'Купить сейчас 3%', 'Купить сейчас 3%', 'Купить сейчас 3%', false);
INSERT INTO htc_dm_dic_pay_type (id, name_en, name_kz, name_ru, is_removed) VALUES (3, 'Купить сейчас 100%', 'Купить сейчас 100%', 'Купить сейчас 100%', false);
