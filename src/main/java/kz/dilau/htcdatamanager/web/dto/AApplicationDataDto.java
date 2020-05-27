package kz.dilau.htcdatamanager.web.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

@Getter
@Setter
@MappedSuperclass
public abstract class AApplicationDataDto {
    @ApiModelProperty(value = "Ипотека")
    protected Boolean mortgage;//ипотека
    @ApiModelProperty(value = "Вероятность торга")
    protected Boolean probabilityOfBidding;//вероятность торга
    @ApiModelProperty(value = "Размер торга")
    protected Integer theSizeOfTrades;//размер торга
    @ApiModelProperty(value = "ID возможных причин торга")
    protected List<Long> possibleReasonForBiddingIdList;
    @ApiModelProperty(value = "ID признаков заявки")
    protected List<Long> applicationFlagIdList;
    @ApiModelProperty(value = "Примечание")
    protected String note;

    public List<Long> getPossibleReasonForBiddingIdList() {
        if (isNull(possibleReasonForBiddingIdList)) {
            possibleReasonForBiddingIdList = new ArrayList<>();
        }
        return possibleReasonForBiddingIdList;
    }

    public List<Long> getApplicationFlagIdList() {
        if (isNull(applicationFlagIdList)) {
            applicationFlagIdList = new ArrayList<>();
        }
        return applicationFlagIdList;
    }
}
