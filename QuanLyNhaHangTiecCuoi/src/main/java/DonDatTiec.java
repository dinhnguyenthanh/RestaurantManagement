import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class DonDatTiec {
    private static final Scanner scanner = new Scanner(System.in);
    private String tenBuaTiec;
    private String thoiDiem;
    private GregorianCalendar ngayThue;
    private int idSanh;
    private BigDecimal donGiaSanh;
    private String tenMenu;
    private BigDecimal donGiaMenu;

    public DonDatTiec(String tenBuaTiec, String thoiDiem,GregorianCalendar ngayThue,
                      int idSanh, BigDecimal donGiaSanh,
                      String tenMenu, BigDecimal donGiaMenu) {
        this.tenBuaTiec = tenBuaTiec;
        this.thoiDiem = thoiDiem;
        this.ngayThue = ngayThue;
        this.idSanh = idSanh;
        this.donGiaSanh = donGiaSanh;
        this.tenMenu = tenMenu;
        this.donGiaMenu = donGiaMenu;
    }

    public DonDatTiec() {

    }


    @Override
    public String toString() {
        return String.format("Tên bữa tiệc: %s\nThời điểm: %s\nNgày đặt: %s\nID Sảnh: %d\nĐơn giá sảnh: %,.0f VND\nTen menu: %s\nĐơn giá menu: %,.0f VND\n",
                this.tenBuaTiec,this.thoiDiem,this.ngayThue,this.idSanh,this.donGiaSanh, this.getTenMenu(),this.donGiaMenu);
    }


    //Các phương thức getter và setter
    public String getTenBuaTiec() {
        return tenBuaTiec;
    }
    public void setTenBuaTiec(String tenBuaTiec) {
        this.tenBuaTiec = tenBuaTiec;
    }
    public GregorianCalendar getNgayThue() {
        return ngayThue;
    }
    public void setNgayThue(GregorianCalendar ngayThue) {
        this.ngayThue = ngayThue;
    }
    public String getThoiDiem() {
        return thoiDiem;
    }
    public void setThoiDiem(String thoiDiem) {
        this.thoiDiem = thoiDiem;
    }
    public int getIdSanh() {
        return idSanh;
    }
    public void setIdSanh(int idSanh) {
        this.idSanh = idSanh;
    }
    public BigDecimal getDonGiaSanh() {
        return donGiaSanh;
    }
    public void setDonGiaSanh(BigDecimal donGiaSanh) {
        this.donGiaSanh = donGiaSanh;
    }
    public BigDecimal getDonGiaMenu() {
        return donGiaMenu;
    }
    public void setDonGiaMenu(BigDecimal donGiaMenu) {
        this.donGiaMenu = donGiaMenu;
    }
    public String getTenMenu() {
        return tenMenu;
    }
    public void setTenMenu(String tenMenu) {
        this.tenMenu = tenMenu;
    }
}
