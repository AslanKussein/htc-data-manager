package kz.dilau.htcdatamanager.web.rest.vm;

import kz.dilau.htcdatamanager.domain.Gender;
import kz.dilau.htcdatamanager.domain.YardType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.Date;

@Getter
@Setter
public class ApplicationForSell {
    //Блок "О сделке"
    @NotNull
    @Min(1)
    private Long operationTypeId;
    @NotNull
    @Min(1)
    private Long objectTypeId;
    @NotNull
    @Min(0)
    private Double objectPrice;
    private Boolean mortgage;//ипотека
    private Boolean encumbrance;//обременение
    private Boolean sharedOwnershipProperty;//общая долевая собственность
    private Boolean exchange;//обмен
    private Boolean probabilityOfBidding;//вероятность торга
    private String theSizeOfTrades;//размер торга
    @Min(1)
    private Long possibleReasonForBiddingId;
    @NotBlank
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
    @Min(1)
    private Long residentialComplexId;
    @Min(0)
    private Integer floor;
    private String apartmentNumber;
    @NotNull
    @Min(1)
    private Integer numberOfRooms;
    @NotNull
    @Min(0)
    private Integer totalArea;
    @NotNull
    @Min(0)
    private Integer livingArea;
    @NotNull
    @Min(0)
    private Integer kitchenArea;
    @Min(0)
    private Integer balconyArea;
    @NotNull
    @Min(0)
    private Integer ceilingHeight;
    @NotNull
    @Min(0)
    private Integer numberOfBedrooms;
    private Boolean atelier;//студия
    private Boolean separateBathroom;

    private Long districtId;
    private Integer numberOfFloors;//residentialComplex
    private String apartmentsOnTheSite;//residentialComplex
    private String materialOfConstruction;
    private Integer yearOfConstruction;//residentialComplex
    private String typeOfElevator;
    private Boolean concierge;
    private Boolean wheelchair;
    private YardType yardType;
    private Boolean playground;
    @Min(1)
    private Long parkingId;

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
