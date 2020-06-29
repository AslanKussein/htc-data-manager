package kz.dilau.htcdatamanager.web.dto.jasper;

public class JasperActDto {
    private String fDate;

    private String fFullname;

    public String getfDate() {
        return fDate;
    }

    public JasperActDto(String fDate, String fFullname){
        this.fDate = fDate;
        this.fFullname = fFullname;
    }

    public JasperActDto() {

    }

    public void setfDate(String fDate) {
        this.fDate = fDate;
    }

    public String getfFullname() {
        return fFullname;
    }

    public void setfFullname(String fFullname) {
        this.fFullname = fFullname;
    }
}
