package kz.dilau.htcdatamanager.web.dto.common;


import kz.dilau.htcdatamanager.domain.base.BaseCustomDictionary;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageDto<T> {

    private ListDto<T> data;
    private int pageNumber;
    private long total;
    private long size;
    private boolean editable;

    public PageDto(Page<T> entity, List<T> entityDto) {
        this.setData(new ListDto(entityDto));
        this.setPageNumber(entity.getNumber());
        this.setTotal(entity.getTotalElements());
        this.setSize(entity.getTotalPages());
    }

    public PageDto(List<BaseCustomDictionary> list, int pageNumber, int pageSize, Long count) {
        this.setData(new ListDto(list));
        this.setPageNumber(pageNumber);
        this.setTotal(count);
        this.setSize((long) Math.ceil((double)count / (double)pageSize));
    }

    public PageDto(List<BaseCustomDictionary> list, int pageNumber, int pageSize, Long count, boolean editable) {
        this.setData(new ListDto(list));
        this.setPageNumber(pageNumber);
        this.setTotal(count);
        this.setSize((long) Math.ceil((double)count / (double)pageSize));
        this.setEditable(editable);
    }
}