package kz.dilau.htcdatamanager.web.dto.jasper;

public class JasperActViewDto {
    private String fNum;

    private String fDate;

    private String fFullname;

    private String fIIN;

    private String fCustomersign;

    public JasperActViewDto(String fNum, String fDate,  String fFullname, String fIIN, String fCustomersign){
        this.fNum = fNum;
        this.fDate = fDate;
        this.fFullname = fFullname;
        this.fIIN = fIIN;
        this.fCustomersign = fCustomersign;
    }

    public String getfNum() {
        return fNum;
    }

    public void setfNum(String fNum) {
        this.fNum = fNum;
    }

    public String getfDate() {
        return fDate;
    }

    public void setfDate(String fDate) {
        this.fDate = fDate;
    }

    public String getfFullname() {
        return fFullname;
    }

    public void setfFullname(String ffullname) {
        this.fFullname = ffullname;
    }

    public String getfIIN() {
        return fIIN;
    }

    public void setfIIN(String fIIN) {
        this.fIIN = fIIN;
    }

    public String getfCustomersign() {
        return fCustomersign;
    }

    public void setfCustomersign(String fCustomersign) {
        this.fCustomersign = fCustomersign;
    }
}
