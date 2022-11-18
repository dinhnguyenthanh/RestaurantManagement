import ConnectDB.DBC;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;

public class QuanLyDonDatTiec {
    private List<DonDatTiec> dsDonDatTiec = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);


    //Nhập
    private String nhapTen(){
        System.out.print("\nNhập tên buổi tiệc: ");
        return scanner.nextLine();
    }
    private GregorianCalendar nhapNgayThue(){
        System.out.print("Nhập ngày thuê: ");
        int ngay = scanner.nextInt(); scanner.nextLine();
        System.out.print("Nhập tháng thuê: ");
        int thang = scanner.nextInt(); scanner.nextLine();
        System.out.print("Nhập năm thuê: ");
        int nam = scanner.nextInt(); scanner.nextLine();
        GregorianCalendar d = new GregorianCalendar(nam, --thang,ngay);
        /*SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        f.format(d.getTime());
        Calendar.DAY_OF_WEEK;*/
        scanner.nextLine();
        return d;
    }
    private String nhapThoiDiem(){
        System.out.print("Nhập thời điểm thuê(S/C/T): ");
        String b;
        if(scanner.nextLine().equalsIgnoreCase("S"))
            return "SANG";
        else if(scanner.nextLine().equalsIgnoreCase("C"))
            return "CHIEU";
        else return "TOI";
    }
    private int nhapIDSanh(){
        System.out.print("\nNhập ID sảnh cưới muốn thuê:");
        int idSanh = scanner.nextInt();scanner.nextLine();
        return idSanh;
    }
    public BigDecimal layDonGiaSanh(int idSanh) throws SQLException {
        DonDatTiec donDatTiec = new DonDatTiec();
        DBC con = new DBC();
        String sql = "select donGiaSanhCuoi from sanhcuoi where id = ?;";
        PreparedStatement ps = con.getConnection().prepareStatement(sql);
        ps.setInt(1,idSanh);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getBigDecimal(1);
    }


    //
    public void luuXuongDatabase(DonDatTiec DonDatTiec) throws SQLException {
        //connect database
        DBC con = new DBC();
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        //Lưu thông tin sảnh cưới vào database
        String sql = "INSERT INTO danhsachdondattiec (tenBuoiTiec, thoiDiem, ngayThue, idSanh, donGiaSanh,tenMenu,donGiaMenu) VALUE (?,?,?,?,?,?,?)";
        PreparedStatement ps = con.getConnection().prepareStatement(sql);
        ps.setString(1,DonDatTiec.getTenBuaTiec());
        ps.setString(2,DonDatTiec.getThoiDiem());
        ps.setString(3, String.format("%s", f.format(DonDatTiec.getNgayThue().getTime())));
        ps.setInt(4,DonDatTiec.getIdSanh());
        ps.setBigDecimal(5,DonDatTiec.getDonGiaSanh());
        ps.setString(6,DonDatTiec.getTenMenu());
        ps.setBigDecimal(7,DonDatTiec.getDonGiaMenu());
        ps.execute();
        ps.close();
        con.close();
        System.out.println("Thêm đơn đặt tiệc thành công!!");
    }

    public void themDDT() throws SQLException {
        String ten = nhapTen();
        GregorianCalendar ngayThue = nhapNgayThue();
        String thoiDiem = nhapThoiDiem();
        //Sảnh
        QuanLySanhCuoi quanLySanhCuoi = new QuanLySanhCuoi();
        quanLySanhCuoi.hienThiCSDL();
        int idSanh = nhapIDSanh();
        BigDecimal donGiaSanh = layDonGiaSanh(idSanh);
        scanner.nextLine();
        //Menu
        QuanLyThucDon quanLyThucDon = new QuanLyThucDon();
        quanLyThucDon.MenuQLTP();
        String tenMenu = quanLyThucDon.bangThucDon;
        BigDecimal tongTien = quanLyThucDon.tongGiaTienThucDon();
        //Dịch vụ
        DonDatTiec DonDatTiec = new DonDatTiec(ten,thoiDiem,ngayThue,idSanh,donGiaSanh,tenMenu,tongTien);
        this.dsDonDatTiec.add(DonDatTiec);
        System.out.println("Có muốn lưu xuống database không?");
        System.out.print("Lựa chọn của bạn(Y/N): ");
        if(scanner.nextLine().equalsIgnoreCase("Y")){
            luuXuongDatabase(DonDatTiec);
        }
    }

    public void xoaDDT() throws SQLException {
        System.out.print("Nhập ID đơn đặt tiệc hàng muốn xóa:");
        int xoa = scanner.nextInt(); scanner.nextLine();
        DBC con = new DBC();
        String sql = "DELETE FROM danhsachdondattiec WHERE (id = ?);";
        PreparedStatement ps = con.getConnection().prepareStatement(sql);
        ps.setInt(1,xoa);
        ps.execute();
        System.out.println("Xóa đơn đặt tiệc thành công!!");

        //cập nhật lại ID
        ps.execute("ALTER TABLE danhsachdondattiec DROP id;");
        ps.execute("ALTER TABLE danhsachdondattiec AUTO_INCREMENT = 1;");
        ps.execute("ALTER TABLE danhsachdondattiec ADD id int UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY FIRST;");
        System.out.println("Đã cập nhật lại ID!");
        ps.close();
        con.close();
    }

    public void hienThi(List<DonDatTiec> kq){
        System.out.println("======= ĐƠN ĐẶT TIỆC =========");
        System.out.printf("\n%15s","Tên buổi tiệc");
        System.out.printf("%15s","Thời điểm");
        System.out.printf("%15s","Ngày thuê");
        System.out.printf("%10s","ID sảnh");
        System.out.printf("%15s","Đơn giá sảnh");
        System.out.printf("%15s","Tên Menu");
        System.out.printf("%15s\n","Giá Menu");
        for (DonDatTiec s: kq){
            System.out.printf("\n%15s",s.getTenBuaTiec());
            System.out.printf("%15s",s.getThoiDiem());
            System.out.printf("%15s",s.getNgayThue());
            System.out.printf("%10s",s.getIdSanh());
            System.out.printf("%,15.0f\n",s.getDonGiaSanh());
            System.out.printf("%15s",s.getTenMenu());
            System.out.printf("%,15.0f\n",s.getDonGiaMenu());
        }
    }

    public void hienThiCSDL() throws SQLException {
        System.out.println("\nHiển thị danh sách thức ăn trong databse");
        DBC con = new DBC();
        String sql = "SELECT * FROM danhsachdondattiec;";
        PreparedStatement ps = con .getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        System.out.printf("\n%5s","ID");
        System.out.printf("%15s","Tên buổi tiệc");
        System.out.printf("%15s","Thời điểm");
        System.out.printf("%15s","Ngày thuê");
        System.out.printf("%10s","ID sảnh");
        System.out.printf("%15s","Đơn giá sảnh");
        System.out.printf("%15s","Tên Menu");
        System.out.printf("%15s\n","Giá Menu");

        while(rs.next()){
            System.out.printf("\n%5d",rs.getInt("id"));
            System.out.printf("%15s",rs.getString("tenBuoiTiec"));
            System.out.printf("%15s",rs.getString("thoiDiem"));
            System.out.printf("%15s",rs.getString("ngayThue"));
            System.out.printf("%10d",rs.getInt("idSanh"));
            System.out.printf("%,15.0f",rs.getBigDecimal("donGiaSanh"));
            System.out.printf("%15s",rs.getString("tenMenu"));
            System.out.printf("%,15.0f",rs.getBigDecimal("donGiaMenu"));
        }
        rs.close();
        ps.close();
        con.close();
    }


    //phương thức sử dụng database
    public void capNhatDDT() throws SQLException {
        DBC con = new DBC();
        scanner.nextLine();
        System.out.print("Nhập tên cột cần thay đổi (tên dưới database):");String tenCot = scanner.nextLine();
        System.out.print("Nhập giá trị cần thay đổi:");String giaTri = scanner.nextLine();
        String sql = "UPDATE danhsachdondattiec SET `"+tenCot+"` = \""+giaTri+"\" WHERE ID = ?;";
        PreparedStatement ps = con.getConnection().prepareStatement(sql);
        System.out.print("Nhập ID đơn đặt tiệc cần chỉnh sửa / cập nhật: ");
        ps.setInt(1,scanner.nextInt());scanner.nextLine();
        ps.executeUpdate();
        ps.close();
        con.close();
        System.out.println("Đã cập nhật!!");
    }

    //menu
    public void MenuQLDDT() throws SQLException {
        int luaChon;
        QuanLyDonDatTiec quanLyDonDatTiec = new QuanLyDonDatTiec();
        do{
            System.out.println("\n========================== QUẢN LÝ CHO THUÊ ===========================");
            System.out.println("1. Thêm đơn đặt tiệc.");
            System.out.println("2. Xóa đơn đặt tiệc.");
            System.out.println("3. Cập nhật đơn đặt tiệc");
            System.out.println("4. Xuất danh sách đơn đặt tiệc.");
            System.out.println("0. Lưu dữ liệu và trở về.");
            System.out.print("Lựa chọn của bạn là: ");
            do{
                luaChon = scanner.nextInt();
                if(luaChon < 0 || luaChon > 4)
                    System.out.print("Nhập sai, nhập lại: ");
            }while (luaChon < 0 || luaChon > 4);
            scanner.nextLine();
            switch (luaChon) {
                case 1 -> {
                    themDDT();
                }
                case 2 -> {
                    xoaDDT();
                }
                case 3 -> {
                    capNhatDDT();
                }
                case 4 -> {
                    hienThiCSDL();
                }
                default ->{
                    System.out.print("\nBạn có chắc muốn trở lại?" + "\nLựa chọn của bạn là (Y/N):");
                    String tl = scanner.nextLine();
                    if(tl.toUpperCase().contains("N"))
                        luaChon = 1;
                    else {
                        System.out.println("\n\n\t\t\t\t\t\t\t\t\t\t\t-------\n\n");
                    }
                }
            }
        }while (luaChon > 0);
    }


    public List<DonDatTiec> getDsDonDatTiec() {
        return dsDonDatTiec;
    }
    public void setDsDonDatTiec(List<DonDatTiec> dsDonDatTiec) {
        this.dsDonDatTiec = dsDonDatTiec;
    }
}
