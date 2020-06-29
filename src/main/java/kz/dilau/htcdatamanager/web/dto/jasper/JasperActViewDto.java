package kz.dilau.htcdatamanager.web.dto.jasper;

public class JasperActViewDto {
    private String fNum;

    private String fDate;

    private String fFullname;

    /*public JasperActViewDto () {

    }*/

    public JasperActViewDto(String fNum, String fDate,  String fFullname) {
        this.fNum = fNum;
        this.fDate = fDate;
        this.fFullname = fFullname;
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

}
