package kz.dilau.htcdatamanager.web.rest.vm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;

import java.util.Date;

@ApiModel()
@Builder
public class RecentlyCreatedApplication {
    @ApiModelProperty()
    private Long id;
    @ApiModelProperty()
    private Date date;
    @ApiModelProperty()
    private Long operationTypeId;
    @ApiModelProperty()
    private String clientFullName;
    @ApiModelProperty()
    private String phoneNumber;
}
