package kz.dilau.htcdatamanager.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kz.dilau.htcdatamanager.domain.Event;
import kz.dilau.htcdatamanager.service.dictionary.DictionaryDao;
import kz.dilau.htcdatamanager.web.dto.common.DictionaryMultilangItemDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

import static java.util.Objects.nonNull;
import static kz.dilau.htcdatamanager.util.DictionaryMappingTool.mapMultilangDictionary;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "EventViewDto" )
public class EventViewDto extends  EventDto{

    private DictionaryMultilangItemDto eventType;

    public EventViewDto(Event event) {
        this.id = event.getId();
        this.eventDate = event.getEventDate();
        this.eventTypeId = event.getEventType().getId();
        this.eventType = mapMultilangDictionary(event.getEventType());
        if (nonNull(event.getSourceApplication())) {
            this.sourceApplicationId = event.getSourceApplication().getId();
            this.isSourceReserved = event.getSourceApplication().isReservedRealProperty();
        }
        if (nonNull(event.getTargetApplication())) {
            this.targetApplicationId = event.getTargetApplication().getId();
            this.isTargetReserved = event.getTargetApplication().isReservedRealProperty();
        }
        this.description = event.getDescription();
        this.comment = event.getComment();
    }
}
