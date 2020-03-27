package kz.dilau.htcdatamanager.web.rest.vm;

import kz.dilau.htcdatamanager.domain.Gender;
import kz.dilau.htcdatamanager.domain.YardType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.Date;

@Getter
@Setter
public class ApplicationForBuy {
    //Блок "О сделке"
    @NotNull
    @Min(1)
    private Long operationTypeId;
    @NotNull
    @Min(1)
    private Long objectTypeId;
    private Double objectPriceFrom;
    private Double objectPriceTo;
    private Boolean mortgage;//ипотека
    private Boolean probabilityOfBidding;//вероятность торга
    private String theSizeOfTrades;//размер торга
    @Min(1)
    private Long possibleReasonForBiddingId;

    private Date contractPeriod;
    @NotNull
    @Min(0)
    private Integer amount;
    @NotNull
    private boolean isCommissionIncludedInThePrice = false;
    private String note;

    //Блок "Об объекте"
    @NotNull
    @Min(1)
    private Long cityId;
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
    private Boolean atelier;//студия
    private Boolean separateBathroom;

    private Integer districtId;
    private Integer numberOfFloors;
    private Integer apartmentsOnTheSite;
    private String materialOfConstruction;
    private Integer yearOfConstruction;
    private String typeOfElevator;
    private Boolean concierge;
    private Boolean wheelchair;
    @Min(1)
    private Long parkingId;
    private YardType yardType;
    private Boolean playground;//todo какой тип?


    //Блок "О клиенте"
    private Long clientId;
    @NotBlank
    private String firstName;
    @NotBlank
    private String surname;
    private String patronymic;
    @NotNull
    @Pattern(
            regexp = "^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$",
            message = "Mobile number is invalid"
    )//todo regex
    private String phoneNumber;
    @Email
    private String email;
    private Gender gender = Gender.UNKNOWN;
}
