package kz.dilau.htcdatamanager.web.rest.vm;

import lombok.Data;

import java.util.List;

@Data
public class ListResponse<T> {
    public ListResponse(List<T> data) {
        this.data = data;
    }

    private List<T> data;
}
