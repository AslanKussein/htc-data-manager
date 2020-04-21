package kz.dilau.htcdatamanager.component.dataaccess;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ListResponse<T> {
    private List<T> data;
}
