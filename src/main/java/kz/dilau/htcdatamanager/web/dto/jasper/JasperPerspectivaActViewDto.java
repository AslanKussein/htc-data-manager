package kz.dilau.htcdatamanager.web.dto.jasper;

public class JasperPerspectivaActViewDto {
    private String fNum;

    private String fSellerFullnameAndAddress;

    private String fDate;

    public JasperPerspectivaActViewDto(String fNum, String fSellerFullnameAndAddress, String fDate) {
        this.fNum = fNum;
        this.fSellerFullnameAndAddress = fSellerFullnameAndAddress;
        this.fDate = fDate;
    }

    public JasperPerspectivaActViewDto(){

    }

    public String getfNum() {
        return fNum;
    }

    public void setfNum(String fNum) {
        this.fNum = fNum;
    }

    public String getfSellerFullnameAndAddress() {
        return fSellerFullnameAndAddress;
    }

    public void setfSellerFullnameAndAddress(String fSellerFullnameAndAddress) {
        this.fSellerFullnameAndAddress = fSellerFullnameAndAddress;
    }

    public String getfDate() {
        return fDate;
    }

    public void setfDate(String fDate) {
        this.fDate = fDate;
    }
}
