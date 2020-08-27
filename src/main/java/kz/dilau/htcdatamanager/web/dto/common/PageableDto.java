package kz.dilau.htcdatamanager.web.dto.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Data
@ApiModel(description = "Модель пагинации")
public class PageableDto {

    @PositiveOrZero
    @ApiModelProperty(notes = "Начальная страница", required = true)
    private int pageNumber = 0;

    @Positive
    @ApiModelProperty(notes = "Количество страниц", required = true, example = "10")
    private int pageSize = 10;

    @ApiModelProperty(notes = "Сортировка по полю", required = false, example = "id")
    private String sortBy = "id";

    @ApiModelProperty(notes = "Направление сортировки", required = false)
    private Sort.Direction direction = Sort.Direction.DESC;

}
