import java.math.BigDecimal;
import java.util.Scanner;

public class ThueCaSi extends DichVu{
    private String tenCS;
    private int soLuongBH;
    private static final Scanner scanner = new Scanner(System.in);
    public static String maDV = MaDV.THUECASI.layMaDV();
    public static int dem = 0;

    static {
        maDV = String.format("%s%03d",maDV,++dem);
    }

    public ThueCaSi(String tenDV, BigDecimal giaDV, String tenCS, int soLuongBH){
        super(maDV,tenDV, giaDV);
        this.tenCS = tenCS;
        this.soLuongBH = soLuongBH;
    }
    @Override
    public String toString() {
        return String.format("\nMa dich vu: %s%s\nTen ca si: %s\nSo luong bai hat: %d bai"
                , MaDV.THUECASI.layMaDV(),super.toString(),this.tenCS,this.soLuongBH);
    }

    public String getTenCS() {
        return tenCS;
    }
    public void setTenCS(String tenCS) {
        this.tenCS = tenCS;
    }
    public int getSoLuongBH() {
        return soLuongBH;
    }
    public void setSoLuongBH(int soLuongBH) {
        this.soLuongBH = soLuongBH;
    }
}