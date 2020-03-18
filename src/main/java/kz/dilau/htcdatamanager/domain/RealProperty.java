package kz.dilau.htcdatamanager.domain;

import kz.dilau.htcdatamanager.domain.dictionary.City;
import kz.dilau.htcdatamanager.domain.dictionary.Street;
import kz.dilau.htcdatamanager.domain.dictionary.ZhK;

import javax.persistence.*;

@Entity
@Table(name = "htc_dm_real_property")
public class RealProperty extends AuditableBaseEntity<RealProperty> {
    @Column(unique = true)
    private String cadastralNumber;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    private City city;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zhk_id", referencedColumnName = "id")
    private ZhK zhK;
    private Street street;
    private String houseNumber;
    private Integer drob;
    private String stroenie;
    private String flatNumber;
    private Integer area;
    private Integer livingArea;
    private Integer kitchenArea;
    private Integer balconyArea;
    private Integer numberOfSpalnya;
    private Boolean isStudiya;
    private Boolean razdelnyiSanuzel;
    private Integer ceilingHeight;
    private String district;
    private Integer etazhnostDoma;
    private Integer countOfApartmentsOnArea;
    private String material;
    private Integer buildYear;
    private String elevator;
    private Boolean hasConcierge;
    private Boolean hasPramPlace;
    private String parkingType;
    private String yardType;
    private Boolean hasPlayground;
    private Integer numberOfRooms;

    public String getCadastralNumber() {
        return cadastralNumber;
    }

    public void setCadastralNumber(String cadastralNumber) {
        this.cadastralNumber = cadastralNumber;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public ZhK getZhK() {
        return zhK;
    }

    public void setZhK(ZhK zhK) {
        this.zhK = zhK;
    }

    public Street getStreet() {
        return street;
    }

    public void setStreet(Street street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public Integer getDrob() {
        return drob;
    }

    public void setDrob(Integer drob) {
        this.drob = drob;
    }

    public String getStroenie() {
        return stroenie;
    }

    public void setStroenie(String stroenie) {
        this.stroenie = stroenie;
    }

    public String getFlatNumber() {
        return flatNumber;
    }

    public void setFlatNumber(String flatNumber) {
        this.flatNumber = flatNumber;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public Integer getLivingArea() {
        return livingArea;
    }

    public void setLivingArea(Integer livingArea) {
        this.livingArea = livingArea;
    }

    public Integer getKitchenArea() {
        return kitchenArea;
    }

    public void setKitchenArea(Integer kitchenArea) {
        this.kitchenArea = kitchenArea;
    }

    public Integer getBalconyArea() {
        return balconyArea;
    }

    public void setBalconyArea(Integer balconyArea) {
        this.balconyArea = balconyArea;
    }

    public Integer getNumberOfSpalnya() {
        return numberOfSpalnya;
    }

    public void setNumberOfSpalnya(Integer numberOfSpalnya) {
        this.numberOfSpalnya = numberOfSpalnya;
    }

    public Boolean getStudiya() {
        return isStudiya;
    }

    public void setStudiya(Boolean studiya) {
        isStudiya = studiya;
    }

    public Boolean getRazdelnyiSanuzel() {
        return razdelnyiSanuzel;
    }

    public void setRazdelnyiSanuzel(Boolean razdelnyiSanuzel) {
        this.razdelnyiSanuzel = razdelnyiSanuzel;
    }

    public Integer getCeilingHeight() {
        return ceilingHeight;
    }

    public void setCeilingHeight(Integer ceilingHeight) {
        this.ceilingHeight = ceilingHeight;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Integer getEtazhnostDoma() {
        return etazhnostDoma;
    }

    public void setEtazhnostDoma(Integer etazhnostDoma) {
        this.etazhnostDoma = etazhnostDoma;
    }

    public Integer getCountOfApartmentsOnArea() {
        return countOfApartmentsOnArea;
    }

    public void setCountOfApartmentsOnArea(Integer countOfApartmentsOnArea) {
        this.countOfApartmentsOnArea = countOfApartmentsOnArea;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public Integer getBuildYear() {
        return buildYear;
    }

    public void setBuildYear(Integer buildYear) {
        this.buildYear = buildYear;
    }

    public String getElevator() {
        return elevator;
    }

    public void setElevator(String elevator) {
        this.elevator = elevator;
    }

    public Boolean getHasConcierge() {
        return hasConcierge;
    }

    public void setHasConcierge(Boolean hasConcierge) {
        this.hasConcierge = hasConcierge;
    }

    public Boolean getHasPramPlace() {
        return hasPramPlace;
    }

    public void setHasPramPlace(Boolean hasPramPlace) {
        this.hasPramPlace = hasPramPlace;
    }

    public String getParkingType() {
        return parkingType;
    }

    public void setParkingType(String parkingType) {
        this.parkingType = parkingType;
    }

    public String getYardType() {
        return yardType;
    }

    public void setYardType(String yardType) {
        this.yardType = yardType;
    }

    public Boolean getHasPlayground() {
        return hasPlayground;
    }

    public void setHasPlayground(Boolean hasPlayground) {
        this.hasPlayground = hasPlayground;
    }

    public Integer getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(Integer numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }
}
