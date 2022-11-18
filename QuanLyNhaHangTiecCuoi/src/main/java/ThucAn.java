import java.math.BigDecimal;
import java.util.Scanner;

public class ThucAn extends ThucPham {
    private static final Scanner scanner = new Scanner(System.in);
    public static String maTA = MaTP.THUCAN.layMaTP();
    private boolean anChay;
    public static int dem = 0;

    static {
        maTA = String.format("%s%03d",maTA,++dem);
    }

    public ThucAn(String tenTP, BigDecimal giaTP, boolean anChay) {
        super(maTA,tenTP, giaTP);
        this.setAnChay(anChay);
    }

    @Override
    public String toString() {
        return String.format("\nMa thuc pham: %s%s\nAn chay: %s"
                , MaTP.THUCAN.layMaTP(), super.toString(), this.isAnChay());
    }

    public boolean isAnChay() {
        return anChay;
    }
    public void setAnChay(boolean anChay) {
        this.anChay = anChay;
    }
}
