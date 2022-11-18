import java.math.BigDecimal;
import java.util.Scanner;

public class TrangTri extends DichVu{
    private static final Scanner scanner = new Scanner(System.in);
    public static String maDV = MaDV.TRANGTRI.layMaDV();
    public static int dem = 0;

    static {
        maDV = String.format("%s%03d",maDV,++dem);
    }

    public TrangTri(String tenDV, BigDecimal giaDV){
        super(maDV,tenDV, giaDV);
    }

    @Override
    public String toString() {
        return String.format("\nMa dich vu: %s%s", MaDV.TRANGTRI.layMaDV(),super.toString());
    }

}