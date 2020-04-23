package kz.dilau.htcdatamanager.util;

import kz.dilau.htcdatamanager.web.dto.common.PageableDto;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@UtilityClass
public class PageableUtils {
    public static PageRequest createPageRequest(PageableDto dto) {
        return PageRequest.of(dto.getPageNumber(), dto.getPageSize(), Sort.by(dto.getDirection(), dto.getSortBy()));
    }
}
