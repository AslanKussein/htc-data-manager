package kz.dilau.htcdatamanager.domain;

import kz.dilau.htcdatamanager.domain.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Min;

import static kz.dilau.htcdatamanager.config.Constants.TABLE_NAME_PREFIX;

@Getter
@Setter
@Entity
@Table(name = TABLE_NAME_PREFIX + "data")//todo rename table later
public class Data2 extends BaseEntity<Long> {
    @Min(0)
    private Integer floorFrom;
    @Min(0)
    private Integer floorTo;
    @Min(1)
    private Integer numberOfRoomsFrom;
    @Min(1)
    private Integer numberOfRoomsTo;
    @Min(0)
    private Integer totalAreaFrom;
    @Min(0)
    private Integer totalAreaTo;
    @Min(0)
    private Integer livingAreaFrom;
    @Min(0)
    private Integer livingAreaTo;
    @Min(0)
    private Integer kitchenAreaFrom;
    @Min(0)
    private Integer kitchenAreaTo;
    @Min(0)
    private Integer balconyAreaFrom;
    @Min(0)
    private Integer balconyAreaTo;
    @Min(0)
    private Integer ceilingHeightFrom;
    @Min(0)
    private Integer ceilingHeightTo;
    @Min(0)
    private Integer numberOfBedroomsFrom;
    @Min(0)
    private Integer numberOfBedroomsTo;
}
