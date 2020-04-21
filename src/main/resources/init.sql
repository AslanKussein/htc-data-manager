-- Статус заявки
insert into htc_dm_dic_application_status (name_en, name_kz, name_ru, code, is_enabled, is_removed) values ('Первичный контакт', 'Первичный контакт', 'Первичный контакт', '002001', true, false);
insert into htc_dm_dic_application_status (name_en, name_kz, name_ru, code, is_enabled, is_removed) values ('Встреча', 'Встреча', 'Встреча', '002002', true, false);
insert into htc_dm_dic_application_status (name_en, name_kz, name_ru, code, is_enabled, is_removed) values ('Договор на оказание услуг', 'Договор на оказание услуг', 'Договор на оказание услуг', '002003', true, false);
insert into htc_dm_dic_application_status (name_en, name_kz, name_ru, code, is_enabled, is_removed) values ('Реклама', 'Реклама', 'Реклама', '002004', true, false);
insert into htc_dm_dic_application_status (name_en, name_kz, name_ru, code, is_enabled, is_removed) values ('Фотосет', 'Фотосет', 'Фотосет', '002005', true, false);
insert into htc_dm_dic_application_status (name_en, name_kz, name_ru, code, is_enabled, is_removed) values ('Показ', 'Показ', 'Показ', '002006', true, false);
insert into htc_dm_dic_application_status (name_en, name_kz, name_ru, code, is_enabled, is_removed) values ('Закрытие сделки', 'Закрытие сделки', 'Закрытие сделки', '002007', true, false);
insert into htc_dm_dic_application_status (name_en, name_kz, name_ru, code, is_enabled, is_removed) values ('Успешно', 'Успешно', 'Успешно', '002008', true, false);
insert into htc_dm_dic_application_status (name_en, name_kz, name_ru, code, is_enabled, is_removed) values ('Завершен', 'Завершен', 'Завершен', '002009', true, false);
-- Город
insert into htc_dm_dic_city (name_en, name_kz, name_ru) values ('Nur-Sultan', 'Нұр-Сұлтан', 'Нур-Султан');
insert into htc_dm_dic_city (name_en, name_kz, name_ru) values ('Almaty', 'Алматы', 'Алматы');
insert into htc_dm_dic_city (name_en, name_kz, name_ru) values ('Shymkent', 'Шымкент', 'Шымкент');
-- Страна
insert into htc_dm_dic_country (name_en, name_kz, name_ru) values ('Kazakhstan', 'Қазақстан', 'Казахстан');
-- Район
insert into htc_dm_dic_district (name_en, name_kz, name_ru) values ('Алматинский район', 'Алматинский район', 'Алматинский район');
insert into htc_dm_dic_district (name_en, name_kz, name_ru) values ('Байконурский район', 'Байконурский район', 'Байконурский район');
insert into htc_dm_dic_district (name_en, name_kz, name_ru) values ('Есильский район', 'Есильский район', 'Есильский район');
insert into htc_dm_dic_district (name_en, name_kz, name_ru) values ('Сарыаркинский район', 'Сарыаркинский район', 'Сарыаркинский район');
-- Отопительная система
insert into htc_dm_dic_heating_system (name_en, name_kz, name_ru) values ('центральное', 'центральное', 'центральное');
insert into htc_dm_dic_heating_system (name_en, name_kz, name_ru) values ('на газе', 'на газе', 'на газе');
insert into htc_dm_dic_heating_system (name_en, name_kz, name_ru) values ('на твердом топливе', 'на твердом топливе', 'на твердом топливе');
insert into htc_dm_dic_heating_system (name_en, name_kz, name_ru) values ('на жидком топливе', 'на жидком топливе', 'на жидком топливе');
insert into htc_dm_dic_heating_system (name_en, name_kz, name_ru) values ('смешанное', 'смешанное', 'смешанное');
insert into htc_dm_dic_heating_system (name_en, name_kz, name_ru) values ('без отопления', 'без отопления', 'без отопления');
-- Материал постройки
insert into htc_dm_dic_material_of_construction (name_en, name_kz, name_ru, code) values ('кирпичный', 'кирпичный', 'кирпичный', 'common');
insert into htc_dm_dic_material_of_construction (name_en, name_kz, name_ru, code) values ('панельный', 'панельный', 'панельный', 'common');
insert into htc_dm_dic_material_of_construction (name_en, name_kz, name_ru, code) values ('монолитный', 'монолитный', 'монолитный', 'common');
insert into htc_dm_dic_material_of_construction (name_en, name_kz, name_ru, code) values ('иное', 'иное', 'иное', 'common');
insert into htc_dm_dic_material_of_construction (name_en, name_kz, name_ru, code) values ('деревянный', 'деревянный', 'деревянный', 'house');
insert into htc_dm_dic_material_of_construction (name_en, name_kz, name_ru, code) values ('каркасно-камышитовый', 'каркасно-камышитовый', 'каркасно-камышитовый', 'house');
insert into htc_dm_dic_material_of_construction (name_en, name_kz, name_ru, code) values ('пеноблочный', 'пеноблочный', 'пеноблочный', 'house');
insert into htc_dm_dic_material_of_construction (name_en, name_kz, name_ru, code) values ('сэндвич-панели', 'сэндвич-панели', 'сэндвич-панели', 'house');
insert into htc_dm_dic_material_of_construction (name_en, name_kz, name_ru, code) values ('каркасно-щитовой', 'каркасно-щитовой', 'каркасно-щитовой', 'house');
insert into htc_dm_dic_material_of_construction (name_en, name_kz, name_ru, code) values ('шлакоблочный', 'шлакоблочный', 'шлакоблочный', 'house');
-- Тип объекта
insert into htc_dm_dic_object_type (name_en, name_kz, name_ru, code, is_enabled, is_removed) values ('Apartment', 'Пәтер', 'Квартира', '003001', true, false);
insert into htc_dm_dic_object_type (name_en, name_kz, name_ru, code, is_enabled, is_removed) values ('House', 'Үй', 'Частный дом', '003002', true, false);
-- Тип операции
insert into htc_dm_dic_operation_type (name_en, name_kz, name_ru, code, is_enabled, is_removed) values ('Buy', 'Сатып алу', 'Купить', '001001', true, false);
insert into htc_dm_dic_operation_type (name_en, name_kz, name_ru, code, is_enabled, is_removed) values ('Sell', 'Сату', 'Продать', '001002', true, false);
-- Тип паркинга
insert into htc_dm_dic_parking_type (name_en, name_kz, name_ru) values ('парковка отсутствует', 'парковка отсутствует', 'парковка отсутствует');
insert into htc_dm_dic_parking_type (name_en, name_kz, name_ru) values ('наземный паркинг', 'наземный паркинг', 'наземный паркинг');
insert into htc_dm_dic_parking_type (name_en, name_kz, name_ru) values ('подземный паркинг', 'подземный паркинг', 'подземный паркинг');
-- Возможные причины торга
insert into htc_dm_dic_possible_reason_for_bidding (name_en, name_kz, name_ru, operation_code) values ('встреча', 'встреча', 'встреча', '001002');
insert into htc_dm_dic_possible_reason_for_bidding (name_en, name_kz, name_ru, operation_code) values ('срочная продажа', 'срочная продажа', 'срочная продажа', '001002');
insert into htc_dm_dic_possible_reason_for_bidding (name_en, name_kz, name_ru, operation_code) values ('оплата наличными', 'оплата наличными', 'оплата наличными', '001002');
insert into htc_dm_dic_possible_reason_for_bidding (name_en, name_kz, name_ru, operation_code) values ('проблемы с выплатами по ипотеке', 'проблемы с выплатами по ипотеке', 'проблемы с выплатами по ипотеке', '001002');
insert into htc_dm_dic_possible_reason_for_bidding (name_en, name_kz, name_ru, operation_code) values ('наследство/подарок', 'наследство/подарок', 'наследство/подарок', '001002');
insert into htc_dm_dic_possible_reason_for_bidding (name_en, name_kz, name_ru, operation_code) values ('другое', 'другое', 'другое', '001002');
insert into htc_dm_dic_possible_reason_for_bidding (name_en, name_kz, name_ru, operation_code) values ('низкий спрос', 'низкий спрос', 'низкий спрос', '001001');
-- Застройщик
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('Nova City Development','Nova City Development','Nova City Development');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('АстанаКазСтрой-Универсал','АстанаКазСтрой-Универсал','АстанаКазСтрой-Универсал');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('ТОО Строительство зданий и сооружений','ТОО Строительство зданий и сооружений','ТОО Строительство зданий и сооружений');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('Aibyn Construction Group','Aibyn Construction Group','Aibyn Construction Group');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('Aluan-AS','Aluan-AS','Aluan-AS');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('Aruana Хан-Тенгри','Aruana Хан-Тенгри','Aruana Хан-Тенгри');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('Astana Capital building project','Astana Capital building project','Astana Capital building project');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('Astana Trade International','Astana Trade International','Astana Trade International');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('BAUMASTER','BAUMASTER','BAUMASTER');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('BAZIS-A','BAZIS-A','BAZIS-A');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('BBK Group','BBK Group','BBK Group');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('BI Group','BI Group','BI Group');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('Caspian Service Kazakhstan','Caspian Service Kazakhstan','Caspian Service Kazakhstan');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('Center Beton Company','Center Beton Company','Center Beton Company');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('DiCOLDiPartners','DiCOLDiPartners','DiCOLDiPartners');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('Elite Market Group','Elite Market Group','Elite Market Group');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('Expolist Investment','Expolist Investment','Expolist Investment');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('G-Park','G-Park','G-Park');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('Gradum Development & Investment','Gradum Development & Investment','Gradum Development & Investment');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('Highvill Kazakhstan','Highvill Kazakhstan','Highvill Kazakhstan');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('Inshaat Invest','Inshaat Invest','Inshaat Invest');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('KAZ CAPITAL INVEST','KAZ CAPITAL INVEST','KAZ CAPITAL INVEST');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('Kazakhstan Invest Group-A','Kazakhstan Invest Group-A','Kazakhstan Invest Group-A');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('MABEX TRADE LTD','MABEX TRADE LTD','MABEX TRADE LTD');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('Mainstreet','Mainstreet','Mainstreet');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('Nur Astana Kurylys','Nur Astana Kurylys','Nur Astana Kurylys');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('ORDA INVEST','ORDA INVEST','ORDA INVEST');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('Otau Group','Otau Group','Otau Group');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('Pioneer Invest','Pioneer Invest','Pioneer Invest');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('SADI-групп','SADI-групп','SADI-групп');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('SAT-NS','SAT-NS','SAT-NS');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('Sembol developements','Sembol developements','Sembol developements');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('Sensata Group','Sensata Group','Sensata Group');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('Tamiz Invest Group','Tamiz Invest Group','Tamiz Invest Group');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('White Gold Development','White Gold Development','White Gold Development');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('Адалстройсервис НС','Адалстройсервис НС','Адалстройсервис НС');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('Азат-НТ Курылыс','Азат-НТ Курылыс','Азат-НТ Курылыс');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('Азбука Жилья Новостройки','Азбука Жилья Новостройки','Азбука Жилья Новостройки');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('Азбука жилья','Азбука жилья','Азбука жилья');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('Азия','Азия','Азия');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('АйМак-Инвест Групп','АйМак-Инвест Групп','АйМак-Инвест Групп');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('Академия жилья','Академия жилья','Академия жилья');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('Акмола Курылыс Материалдары','Акмола Курылыс Материалдары','Акмола Курылыс Материалдары');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('Алмас','Алмас','Алмас');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('Альнура Холдинг','Альнура Холдинг','Альнура Холдинг');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('Альтаир СК 7','Альтаир СК 7','Альтаир СК 7');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('Альянсстройинвест','Альянсстройинвест','Альянсстройинвест');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('АО Корпорация Ак ауыл','АО Корпорация Ак ауыл','АО Корпорация Ак ауыл');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('Асем Кала','Асем Кала','Асем Кала');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('АССМ','АССМ','АССМ');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('Астана Недвижимость','Астана Недвижимость','Астана Недвижимость');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('Байтур','Байтур','Байтур');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('БАХУС','БАХУС','БАХУС');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('Бахус-Астана','Бахус-Астана','Бахус-Астана');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('Бахыт Астана – 2005 ТОО','Бахыт Астана – 2005 ТОО','Бахыт Астана – 2005 ТОО');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('Биком Инжиниринг','Биком Инжиниринг','Биком Инжиниринг');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('Бонита Инжиниринг','Бонита Инжиниринг','Бонита Инжиниринг');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('Бразерс Компани ТОО','Бразерс Компани ТОО','Бразерс Компани ТОО');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('Бурлин Газ Строй','Бурлин Газ Строй','Бурлин Газ Строй');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('Восток ЛТД','Восток ЛТД','Восток ЛТД');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('Гражданстрой','Гражданстрой','Гражданстрой');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('Гранит Сервис','Гранит Сервис','Гранит Сервис');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('Дуние Курылыс','Дуние Курылыс','Дуние Курылыс');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('Евростандарт-Аква','Евростандарт-Аква','Евростандарт-Аква');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('Елорда даму','Елорда даму','Елорда даму');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('Жана Жол Курылыс','Жана Жол Курылыс','Жана Жол Курылыс');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('Жана Курылыс Трейдинг','Жана Курылыс Трейдинг','Жана Курылыс Трейдинг');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('Жана Курылыс','Жана Курылыс','Жана Курылыс');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('ЖСК "Собственное жилье 2017"','ЖСК "Собственное жилье 2017"','ЖСК "Собственное жилье 2017"');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('ЖСК Айнур-Астана','ЖСК Айнур-Астана','ЖСК Айнур-Астана');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('Инвестиционная строительная компания ASI','Инвестиционная строительная компания ASI','Инвестиционная строительная компания ASI');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('Каз Джордан констракшн компани','Каз Джордан констракшн компани','Каз Джордан констракшн компани');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('Казмонтажстрой','Казмонтажстрой','Казмонтажстрой');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('Каражат НС','Каражат НС','Каражат НС');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('Компания Сарыарка-Курылыс','Компания Сарыарка-Курылыс','Компания Сарыарка-Курылыс');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('Континентстрой','Континентстрой','Континентстрой');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('Концерн Строймонолит-Астана','Концерн Строймонолит-Астана','Концерн Строймонолит-Астана');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('ЛАД-строй','ЛАД-строй','ЛАД-строй');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('Лея','Лея','Лея');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('МАК Алматыгорстрой','МАК Алматыгорстрой','МАК Алматыгорстрой');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('МС-7','МС-7','МС-7');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('Найза-Курылыс','Найза-Курылыс','Найза-Курылыс');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('не указан','не указан','не указан');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('НОРД','НОРД','НОРД');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('Объединение-Сайран','Объединение-Сайран','Объединение-Сайран');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('ОМК-центр','ОМК-центр','ОМК-центр');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('Палаццо ТОО','Палаццо ТОО','Палаццо ТОО');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('Паритет-2003','Паритет-2003','Паритет-2003');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('Прайс Астана Строй','Прайс Астана Строй','Прайс Астана Строй');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('Проектная компания Айя','Проектная компания Айя','Проектная компания Айя');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('ПСК Клён','ПСК Клён','ПСК Клён');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('Ромул','Ромул','Ромул');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('Сана','Сана','Сана');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('СК «Фаворитстрой»','СК «Фаворитстрой»','СК «Фаворитстрой»');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('Сормайтремстрой','Сормайтремстрой','Сормайтремстрой');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('Строительная корпорация «Кулагер»','Строительная корпорация «Кулагер»','Строительная корпорация «Кулагер»');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('Строй-Контракт','Строй-Контракт','Строй-Контракт');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('Стройжилинвест','Стройжилинвест','Стройжилинвест');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('Стройинвест-СК','Стройинвест-СК','Стройинвест-СК');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('Стройкласс','Стройкласс','Стройкласс');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('Стройпроект','Стройпроект','Стройпроект');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('Султан','Султан','Султан');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('Сункар - 777','Сункар - 777','Сункар - 777');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('Толендi ЖСК','Толендi ЖСК','Толендi ЖСК');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('ТОО "ANT-HEAP"','ТОО "ANT-HEAP"','ТОО "ANT-HEAP"');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('ТОО "АКАN Engineering"','ТОО "АКАN Engineering"','ТОО "АКАN Engineering"');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('ТОО Global Building Contract','ТОО Global Building Contract','ТОО Global Building Contract');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('ТОО «Тандем Астана»','ТОО «Тандем Астана»','ТОО «Тандем Астана»');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('ТОО Азия','ТОО Азия','ТОО Азия');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('ТОО Арка курылыс','ТОО Арка курылыс','ТОО Арка курылыс');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('ТОО Аурика ','ТОО Аурика ','ТОО Аурика ');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('ТОО Биык Шанырак','ТОО Биык Шанырак','ТОО Биык Шанырак');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('ТОО Даса-Недвижимость','ТОО Даса-Недвижимость','ТОО Даса-Недвижимость');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('ТОО Жана курылыс','ТОО Жана курылыс','ТОО Жана курылыс');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('ТОО КазСтройПодряд','ТОО КазСтройПодряд','ТОО КазСтройПодряд');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('ТОО Корпорация Астана-Стройинвест','ТОО Корпорация Астана-Стройинвест','ТОО Корпорация Астана-Стройинвест');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('ТОО МТС Компани ЛТД','ТОО МТС Компани ЛТД','ТОО МТС Компани ЛТД');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('ТОО Ротор','ТОО Ротор','ТОО Ротор');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('ТОО СК Айкен','ТОО СК Айкен','ТОО СК Айкен');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('ТОО СК Астанатрансстрой','ТОО СК Астанатрансстрой','ТОО СК Астанатрансстрой');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('Туркуаз Инвест','Туркуаз Инвест','Туркуаз Инвест');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('Фортуна НС','Фортуна НС','Фортуна НС');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('Шанг Хи Груп','Шанг Хи Груп','Шанг Хи Груп');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('Шар Курылыс','Шар Курылыс','Шар Курылыс');
insert into htc.htc_dm_dic_property_developer (name_en, name_kz, name_ru) values ('Эверест','Эверест','Эверест');
-- Канализация
insert into htc_dm_dic_sewerage (name_en, name_kz, name_ru) values ('центральная', 'центральная', 'центральная');
insert into htc_dm_dic_sewerage (name_en, name_kz, name_ru) values ('есть возможность подведения', 'есть возможность подведения', 'есть возможность подведения');
insert into htc_dm_dic_sewerage (name_en, name_kz, name_ru) values ('септик', 'септик', 'септик');
insert into htc_dm_dic_sewerage (name_en, name_kz, name_ru) values ('нет', 'нет', 'нет');
-- Улица
insert into htc_dm_dic_street (name_en, name_kz, name_ru) values ('Кенесары', 'Кенесары', 'Кенесары');
insert into htc_dm_dic_street (name_en, name_kz, name_ru) values ('Амман', 'Амман', 'Амман');
insert into htc_dm_dic_street (name_en, name_kz, name_ru) values ('Сарыарка', 'Сарыарка', 'Сарыарка');
insert into htc_dm_dic_street (name_en, name_kz, name_ru) values ('Достык', 'Достык', 'Достык');
-- Тип лифта
insert into htc_dm_dic_type_of_elevator (name_kz, name_en, name_ru) values ('пассажирский лифт', 'пассажирский лифт', 'пассажирский лифт');
insert into htc_dm_dic_type_of_elevator (name_kz, name_en, name_ru) values ('грузовой лифт', 'грузовой лифт', 'грузовой лифт');
-- Площадка
insert into htc_dm_dic_yard_type (name_kz, name_en, name_ru) values ('ашық', 'open', 'открытый');
insert into htc_dm_dic_yard_type (name_kz, name_en, name_ru) values ('жабық', 'private', 'закрытый');
-- Да/нет
insert into htc_dm_dic_yes_no (name_en, name_kz, name_ru, code, is_enabled, is_removed) values ('Yes', 'Йә', 'Да', 'true', true, false);
insert into htc_dm_dic_yes_no (name_en, name_kz, name_ru, code, is_enabled, is_removed) values ('No', 'Жоқ', 'Нет', 'false', true, false);
-- Жилой комплекс
insert
	into
	htc.htc_dm_general_characteristics ( apartments_on_the_site,
	ceiling_height,
	concierge,
	house_number,
	house_number_fraction,
	housing_class,
	housing_condition,
	number_of_apartments,
	number_of_floors,
	playground,
	wheelchair,
	year_of_construction,
	city_id,
	district_id,
	material_of_construction_id,
	parking_type_id,
	property_developer_id,
	street_id,
	yard_type_id)
values(1,
1,
true,
171,
'/2',
'комфорт класс',
'хорошее состояние',
10,
12,
true,
true,
1991,
1,
1,
1,
1,
5,
1,
1);
insert into htc_dm_dic_residential_complex (id, house_name, number_of_entrances, general_characteristics_id) values(1, 'Авиценна', 9, 1);
