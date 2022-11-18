import java.math.BigDecimal;

public class SanhCuoi {
    private static int dem;
    private String maSanh;
    private String tenSanh;
    private String viTri;
    private int sucChua;
    private BigDecimal giaThue;

    {
        maSanh = String.format("S%03d",++dem);
    }
    public SanhCuoi(String tenSanh, String viTri,int sucChua, BigDecimal giaThue){
        this.tenSanh = tenSanh;
        this.viTri = viTri;
        this.sucChua = sucChua;
        this.giaThue = giaThue;
    }

    public SanhCuoi() {
    }

    @Override
    public String toString() {
        return String.format("Ma Sanh: %s\nTen sanh: %s\nVi tri: %s\nSuc Chua: %s ban\nGia: %,.0f VND\n",
                this.maSanh,this.tenSanh,this.viTri,this.sucChua,this.giaThue);
    }

    //các phương thức getter&setter
    public String getMaSanh() {
        return maSanh;
    }
    public void setMaSanh(String maSanh) {
        this.maSanh = maSanh;
    }
    public String getTenSanh() {
        return tenSanh;
    }
    public void setTenSanh(String tenSanh) {
        this.tenSanh = tenSanh;
    }
    public String getViTri() {
        return viTri;
    }
    public void setViTri(String viTri) {
        this.viTri = viTri;
    }
    public int getSucChua() {
        return sucChua;
    }
    public void setSucChua(int sucChua) {
        this.sucChua = sucChua;
    }
    public BigDecimal getGiaThue() {
        return giaThue;
    }
    public void setGiaThue(BigDecimal giaThue) {
        this.giaThue = giaThue;
    }
}
