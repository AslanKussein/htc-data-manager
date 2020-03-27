package kz.dilau.htcdatamanager.web.rest.vm;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(exclude = "operations")
public class CheckOperationGroupDto {
    private String code;
    private List<String> operations;
}
