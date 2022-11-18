import java.math.BigDecimal;

public class ThucUong extends ThucPham {
    private String hangSanXuat;
    public static int dem;


    public ThucUong(String tenTP, BigDecimal giaTP, String hangSanXuat) {
        super(tenTP, giaTP);
        this.hangSanXuat = hangSanXuat;

    }


    @Override
    public String toString() {
        return String.format("\nMa thuc pham: %s%s\nHang san xuat: %s"
                , MaTP.THUCUONG.layMaTP(), super.toString(), this.getHangSanXuat());
    }

    public String getHangSanXuat() {
        return hangSanXuat;
    }
    public void setHangSanXuat(String hangSanXuat) {
        this.hangSanXuat = hangSanXuat;
    }
}
