import java.math.BigDecimal;

public class ThucDon {
    private int idThucPham;
    private String loaiThucPham;
    private BigDecimal giaThucPham;

    public ThucDon(int idThucPham, String loaiThucPham, BigDecimal giaThucPham){
        this.idThucPham = idThucPham;
        this.loaiThucPham = loaiThucPham;
        this.giaThucPham = giaThucPham;
    }

    public int getIdThucPham() {
        return idThucPham;
    }
    public void setIdThucPham(int idThucPham) {
        this.idThucPham = idThucPham;
    }
    public String getLoaiThucPham() {
        return loaiThucPham;
    }
    public void setLoaiThucPham(String loaiThucPham) {
        this.loaiThucPham = loaiThucPham;
    }
    public BigDecimal getGiaThucPham() {
        return giaThucPham;
    }
    public void setGiaThucPham(BigDecimal giaThucPham) {
        this.giaThucPham = giaThucPham;
    }
}
