package kz.dilau.htcdatamanager.web.dto.jasper;

public class JasperActDto {
    private String fDate;

    private String fFullname;

    private String fCustsign;

    private String fSuppliersign;

    public String getfDate() {
        return fDate;
    }

    public JasperActDto(String fDate, String fFullname, String fCustsign, String fSuppliersign){
        this.fDate = fDate;
        this.fFullname = fFullname;
        this.fCustsign = fCustsign;
        this.fSuppliersign = fSuppliersign;
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

    public String getfCustsign() {
        return fCustsign;
    }

    public void setfCustsign(String fCustsign) {
        this.fCustsign = fCustsign;
    }

    public String getfSuppliersign() {
        return fSuppliersign;
    }

    public void setfSuppliersign(String fSuppliersign) {
        this.fSuppliersign = fSuppliersign;
    }
}
