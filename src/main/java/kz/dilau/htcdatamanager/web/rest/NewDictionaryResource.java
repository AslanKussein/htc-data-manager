package kz.dilau.htcdatamanager.web.rest;

import io.swagger.annotations.ApiOperation;
import kz.dilau.htcdatamanager.domain.base.BaseCustomDictionary;
import kz.dilau.htcdatamanager.service.DictionaryCacheService;
import kz.dilau.htcdatamanager.service.NewDictionaryService;
import kz.dilau.htcdatamanager.web.dto.common.PageDto;
import kz.dilau.htcdatamanager.web.dto.dictionary.DictionaryFilterDto;
import kz.dilau.htcdatamanager.web.dto.dictionary.DictionaryItemRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static kz.dilau.htcdatamanager.config.Constants.NEW_DICTIONARIES_REST_ENDPOINT;

@RequiredArgsConstructor
@RestController
@RequestMapping(NEW_DICTIONARIES_REST_ENDPOINT)
@Slf4j
public class NewDictionaryResource {
    private final NewDictionaryService dictionaryService;
    private final DictionaryCacheService dictionaryCacheService;

    @ApiOperation(value = "Список значений по справочнику с пагинацией", response = BaseCustomDictionary.class)
    @PostMapping("list/pageable")
    public ResponseEntity getDictionaryValues(@RequestBody DictionaryFilterDto filterDto) {
        PageDto<BaseCustomDictionary> aDictionaries = dictionaryCacheService.getDictionary(filterDto);
        return ResponseEntity.ok(aDictionaries);
    }

    @ApiOperation(value = "Список значений по справочнику", responseContainer = "List", response = BaseCustomDictionary.class)
    @GetMapping("/{dictionaryName}/list")
    public ResponseEntity getDictionaryValues(@PathVariable("dictionaryName") String dictionaryName) {
        List<BaseCustomDictionary> aDictionaries = dictionaryCacheService.getDictionary(dictionaryName);
        return ResponseEntity.ok(aDictionaries);
    }

    @ApiOperation(value = "Список значений по справочнику по родительскому объекту", responseContainer = "List", response = BaseCustomDictionary.class)
    @GetMapping("/{dictionaryName}/list/{parentId}")
    public ResponseEntity getDictionaryValuesByParent(@PathVariable("dictionaryName") String dictionaryName,
                                                      @PathVariable("parentId") Long parentId) {
        List aDictionaries = dictionaryService.getChildList(parentId, dictionaryName);
        return ResponseEntity.ok(aDictionaries);
    }

    @ApiOperation(value = "Значение справочника по id", response = BaseCustomDictionary.class)
    @GetMapping("/{dictionaryName}/{id}")
    public ResponseEntity getDictionaryValue(@PathVariable("dictionaryName") String dictionaryName,
                                             @PathVariable("id") Long id) {
        BaseCustomDictionary aDictionaryItem = dictionaryCacheService.loadDictionaryByIdFromDatabase(dictionaryName, id);
        return ResponseEntity.ok(aDictionaryItem);
    }

    @ApiOperation(value = "Сохранение справочных данных", response = Long.class)
    @PostMapping
    public ResponseEntity save(@RequestBody DictionaryItemRequestDto requestDto) {
        return ResponseEntity.ok(dictionaryService.save(requestDto));
    }

    @ApiOperation(value = "Обновление справочных данных", response = Long.class)
    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") Long id,
                                 @RequestBody DictionaryItemRequestDto requestDto) {
        return ResponseEntity.ok(dictionaryService.update(id, requestDto));
    }

    @ApiOperation(value = "Удаление справочных данных", response = Long.class)
    @DeleteMapping("/{dictionaryName}/{id}")
    public ResponseEntity delete(@PathVariable("dictionaryName") String dictionaryName,
                                 @PathVariable("id") Long id) {
        return ResponseEntity.ok(dictionaryService.delete(id, dictionaryName));
    }

    @ApiOperation(value = "Очистка кэша справочников", response = Long.class)
    @GetMapping("/clearCache")
    public ResponseEntity clearCache() {
        dictionaryCacheService.clearDictionaries();
        return ResponseEntity.ok("");
    }
}
