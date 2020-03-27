package kz.dilau.htcdatamanager.web.rest.vm;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.stream.Stream;

@Getter
@Setter
public class RecentlyCreatedApplication {
    private Long id;
    private Date date;
    private Long operationTypeId;
    private String clientFullName;
    private Stream phoneNumber;
}
