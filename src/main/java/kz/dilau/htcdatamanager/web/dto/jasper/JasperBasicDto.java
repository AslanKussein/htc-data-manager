package kz.dilau.htcdatamanager.web.dto.jasper;

public class JasperBasicDto {

    private String fName;

    private String fVal;

    public String getfName() {

        return fName;
    }

    public void setfName(String fieldFirst) {
        this.fName = fieldFirst;
    }

    public String getfVal() {
        return fVal;
    }

    public void setfVal(String field_2) {
        fVal = field_2;
    }

    public JasperBasicDto (String field_1, String field_2) {
        this.fName = field_1;
        this.fVal = field_2;
    }

    public JasperBasicDto () {
    }
}
