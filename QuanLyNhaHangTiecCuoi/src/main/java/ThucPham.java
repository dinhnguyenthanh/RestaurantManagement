import java.math.BigDecimal;

public class ThucPham {
    private String maTP;
    private String tenTP;
    private BigDecimal giaTP;
    public static int dem;

    {
        maTP = String.format("TP%03d",++dem);
    }

    public ThucPham(String maTP,String tenTP, BigDecimal giaTP){
        this.maTP = maTP;
        this.tenTP = tenTP;
        this.giaTP = giaTP;
    }

    public ThucPham(String tenTP, BigDecimal giaTP) {
        this.tenTP = tenTP;
        this.giaTP = giaTP;
    }

    @Override
    public String toString() {
        return String.format("\nTen thuc pham: %s\nGia thuc pham: %,.0f VND", this.tenTP,this.giaTP);
    }

    public String getTenTP() {
        return tenTP;
    }
    public void setTenTP(String tenTP) {
        this.tenTP = tenTP;
    }
    public BigDecimal getGiaTP() {
        return giaTP;
    }
    public void setGiaTP(BigDecimal giaTP) {
        this.giaTP = giaTP;
    }
    public String getMaTP() {
        return maTP;
    }
    public void setMaTP(String maTP) {
        this.maTP = maTP;
    }
}
