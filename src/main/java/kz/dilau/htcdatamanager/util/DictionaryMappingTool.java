package kz.dilau.htcdatamanager.util;

import kz.dilau.htcdatamanager.web.dto.common.DictionaryMultilangItemDto;
import kz.dilau.htcdatamanager.web.dto.common.MultiLangText;
import kz.dilau.htcdatamanager.domain.GeneralCharacteristics;
import kz.dilau.htcdatamanager.domain.base.BaseCustomDictionary;
import lombok.experimental.UtilityClass;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@UtilityClass
public class DictionaryMappingTool {
    public static DictionaryMultilangItemDto mapMultilangDictionary(BaseCustomDictionary dictionary) {
        if (isNull(dictionary)) {
            return DictionaryMultilangItemDto.NULL_OBJECT;
        }

        return DictionaryMultilangItemDto.builder()
                .id((Long) dictionary.getId())
                .name(MultiLangText.builder()
                        .nameKz(dictionary.getMultiLang().getNameKz())
                        .nameRu(dictionary.getMultiLang().getNameRu())
                        .nameEn(dictionary.getMultiLang().getNameEn())
                        .build())
                .build();
    }

    public static MultiLangText mapDictionaryToText(BaseCustomDictionary dictionary) {
        if (isNull(dictionary)) {
            return MultiLangText.NULL_OBJECT;
        }

        return MultiLangText.builder()
                .nameKz(dictionary.getMultiLang().getNameKz())
                .nameRu(dictionary.getMultiLang().getNameRu())
                .nameEn(dictionary.getMultiLang().getNameEn())
                .build();
    }

    public static MultiLangText mapAddressToMultiLang(GeneralCharacteristics generalCharacteristics, String apartmentNumber) {
        if (isNull(generalCharacteristics)) {
            return MultiLangText.NULL_OBJECT;
        }
        MultiLangText result = concatMultiLangWithMultiLang(mapDictionaryToText(generalCharacteristics.getCity()),
                mapDictionaryToText(generalCharacteristics.getDistrict()), ", ");
        result = concatMultiLangWithMultiLang(result, mapDictionaryToText(generalCharacteristics.getStreet()), ", ");
        result = concatStringWithMultiLang(result,
                generalCharacteristics.getHouseNumber() + (nonNull(generalCharacteristics.getHouseNumberFraction()) ? "/" + generalCharacteristics.getHouseNumberFraction() : "") + " " + apartmentNumber, " ");
        return result;
    }

    public static MultiLangText concatMultiLangWithMultiLang(MultiLangText first, MultiLangText second, String separator) {
        return MultiLangText.builder()
                .nameKz(first.getNameKz() + separator + second.getNameKz())
                .nameRu(first.getNameRu() + separator + second.getNameRu())
                .nameEn(first.getNameEn() + separator + second.getNameEn())
                .build();
    }

    public static MultiLangText concatStringWithMultiLang(MultiLangText multiLangText, String text, String separator) {
        return MultiLangText.builder()
                .nameKz(multiLangText.getNameKz() + separator + text)
                .nameRu(multiLangText.getNameRu() + separator + text)
                .nameEn(multiLangText.getNameEn() + separator + text)
                .build();
    }
}
