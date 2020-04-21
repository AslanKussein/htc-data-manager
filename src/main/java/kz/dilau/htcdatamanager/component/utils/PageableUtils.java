package kz.dilau.htcdatamanager.component.utils;

import kz.dilau.htcdatamanager.component.common.dto.PageableDto;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@UtilityClass
public class PageableUtils {
    public static PageRequest createPageRequest(PageableDto dto) {
        return PageRequest.of(dto.getPageNumber(), dto.getPageSize(), Sort.by(dto.getDirection(), dto.getSortBy()));
    }
}
