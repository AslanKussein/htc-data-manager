package kz.dilau.htcdatamanager.web.rest.vm;

import lombok.Builder;

import java.util.Date;

@Builder
public class RecentlyCreatedApplication {
    private Long id;
    private Date date;
    private Long operationTypeId;
    private String clientFullName;
    private String phoneNumber;
}
