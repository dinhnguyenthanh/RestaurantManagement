import java.math.BigDecimal;
import java.util.Scanner;

public class Karaoke extends DichVu{
    private static final Scanner scanner = new Scanner(System.in);
    private double thoiGianThue;
    public static String maDV = MaDV.KARAOKE.layMaDV();
    public static int dem = 0;

    static {
        maDV = String.format("%s%03d",maDV,++dem);
    }

    public Karaoke(String tenDV, BigDecimal giaDV, double thoigianThue){
        super(maDV,tenDV, giaDV);
        this.thoiGianThue = thoigianThue;
    }

    @Override
    public String toString() {
        return String.format("\nMa dich vu: %s%s\nThoi gian thue: %.1f gio", MaDV.KARAOKE.layMaDV(),super.toString(),this.thoiGianThue);
    }

    public double getThoiGianThue() {
        return thoiGianThue;
    }
    public void setThoiGianThue(double thoiGianThue) {
        this.thoiGianThue = thoiGianThue;
    }
}
