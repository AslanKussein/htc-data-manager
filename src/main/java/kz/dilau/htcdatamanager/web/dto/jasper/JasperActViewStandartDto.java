package kz.dilau.htcdatamanager.web.dto.jasper;

public class JasperActViewStandartDto extends JasperActViewDto {
    private String fSign;

    public JasperActViewStandartDto(String fNum, String fDate, String fFullname, String fIIN, String fCustomersign, String fSign) {
        super( fNum,  fDate,   fFullname,  fIIN,  fCustomersign);
        this.fSign = fSign;
    }

    public String getfSign() {
        return fSign;
    }

    public void setfSign(String fSign) {
        this.fSign = fSign;
    }
}
