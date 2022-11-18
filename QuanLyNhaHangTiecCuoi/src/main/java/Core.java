import java.sql.*;
import java.text.ParseException;
import java.util.*;

public class Core {
    public static int demMenu;
    public static void main(String[] args) throws SQLException, ParseException {
        Scanner sc = new Scanner(System.in);
        int luaChon;
        do {
            System.out.println("===============================================================================================");
            System.out.println("================================= QUẢN LÝ NHÀ HÀNG TIỆC CƯỚI ==================================");
            System.out.println("===============================================================================================");
            System.out.println("1. Quản lý sảnh cưới.");
            System.out.println("2. Quản lý dịch vụ.");
            System.out.println("3. Quản lý thông tin thực phẩm.");
            System.out.println("4. Quan lý thuê sảnh cưới.");
            System.out.println("5. Hóa đơn thanh toán.");
            System.out.println("6. Doanh thu theo tháng và theo quý.");
            System.out.println("0. Thoát chương trình.");
            System.out.print("Lựa chọn của bạn là: ");
            do {
                luaChon = sc.nextInt();
                if (luaChon < 0 || luaChon > 6)
                    System.out.print("Nhập sai, nhập lại: ");
            } while (luaChon < 0 || luaChon > 6);
            sc.nextLine();
            switch (luaChon) {
                /////////////////////////////////////////
                case 1: {
                    QuanLySanhCuoi quanLySanhCuoi = new QuanLySanhCuoi();
                    quanLySanhCuoi.MenuQLSC();
                }
                break;
                /////////////////////////////////////////
                case 2: {
                    QuanLyDichVu quanLyDichVu = new QuanLyDichVu();
                    quanLyDichVu.MenuQLDV();
                }
                break;
                /////////////////////////////////////////
                case 3: {
                    QuanLyThucPham quanLyThucPham = new QuanLyThucPham();
                    quanLyThucPham.MenuQLTP();
                }
                break;
                ///////
                case 4:
                    QuanLyDonDatTiec quanLyDonDatTiec = new QuanLyDonDatTiec();
                    quanLyDonDatTiec.MenuQLDDT();
                    break;
                case 5:
                    HoaDon hoaDon = new HoaDon();
                    hoaDon.xuatHoaDon();
                    break;
                case 6:
                    //doanh thu theo tháng và quý
                    break;
                default:
                    System.out.println("Tam biet!!");
                    break;
            }
        } while (luaChon > 0);
    }
    /*  GregorianCalendar g = new GregorianCalendar(2021, Calendar.JANUARY,15);
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println(f.format(g.getTime()));
        System.out.println(Calendar.DAY_OF_WEEK);
        System.out.println(ThoiDiem.SANG);*/
}