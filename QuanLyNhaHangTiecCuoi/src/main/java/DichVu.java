import java.math.BigDecimal;

public abstract class DichVu {
    private String maDV;
    private BigDecimal giaDV;
    private String tenDV;
    public static int dem;

    {
        maDV = String.format("TP%03d",++dem);
    }

    public DichVu(String maDV, String tenDV, BigDecimal giaDV){
        this.maDV = maDV;
        this.tenDV = tenDV;
        this.giaDV = giaDV;
    }

    @Override
    public String toString() {
        return String.format("\nTen dich vu: %s\nGia dich vu: %,.0f VND", this.tenDV,this.giaDV);
    }


    public BigDecimal getGiaDV() {
        return giaDV;
    }
    public void setGiaDV(BigDecimal giaDV) {
        this.giaDV = giaDV;
    }
    public String getTenDV() {
        return tenDV;
    }
    public void setTenDV(String tenDV) {
        this.tenDV = tenDV;
    }
    public String getMaDV() {
        return maDV;
    }
    public void setMaDV(String maDV) {
        this.maDV = maDV;
    }
}
