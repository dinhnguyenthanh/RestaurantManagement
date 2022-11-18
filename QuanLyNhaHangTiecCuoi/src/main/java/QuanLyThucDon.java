import ConnectDB.DBC;

import java.lang.constant.DynamicCallSiteDesc;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QuanLyThucDon {
    private List<ThucDon> dsThucPham = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);
    public String bangThucDon = String.format("thucdon%d",laySoThucDon());

    {
        DBC con = new DBC();
        String sql = "CREATE TABLE `test`.`"+bangThucDon+"` (\n" +
                "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                "  `idThucPham` INT NOT NULL,\n" +
                "  `loaiThucPham` VARCHAR(45) NOT NULL,\n" +
                "  `giaThucPham` DECIMAL NOT NULL,\n" +
                "  PRIMARY KEY (`id`))\n" +
                "ENGINE = InnoDB\n" +
                "DEFAULT CHARACTER SET = utf8\n" +
                "COLLATE = utf8_unicode_ci;";
        try {
            PreparedStatement ps = con.getConnection().prepareStatement(sql);
            ps.execute();
            ps.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        con.close();
        capNhatSoThucDon();
    }
    public QuanLyThucDon() throws SQLException {

    }
    //lấy
    private int nhapID(){
        System.out.print("\nNhập ID thực phẩm muốn thêm:");
        int id = scanner.nextInt();scanner.nextLine();
        return id;
    }
    public BigDecimal layDonGiaThucAn(int id) throws SQLException {
        DBC con = new DBC();
        String sql = "select donGia from thucan where id = ?;";
        PreparedStatement ps = con.getConnection().prepareStatement(sql);
        ps.setInt(1,nhapID());
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getBigDecimal(1);
    }
    public BigDecimal layDonGiaThucUong(int id) throws SQLException {
        DBC con = new DBC();
        String sql = "select donGia from thucUong where id = ?;";
        PreparedStatement ps = con.getConnection().prepareStatement(sql);
        ps.setInt(1,nhapID());
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getBigDecimal(1);
    }
    public int laySoThucDon() throws SQLException {
        DBC con = new DBC();
        String sql = "Select dem FROM demthucdon WHERE ID = 1;";
        PreparedStatement ps = con.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int kq = rs.getInt(1);
        rs.close();
        ps.close();
        con.close();
        return kq+1;
    }
    public void capNhatSoThucDon() throws SQLException {
        DBC con = new DBC();
        int dem = laySoThucDon() +1;
        String sql = "UPDATE demthucdon SET dem = ? WHERE ID = 1;";
        PreparedStatement ps = con.getConnection().prepareStatement(sql);
        ps.setInt(1,dem);
        ps.executeUpdate();
        ps.close();
        con.close();
    }
    //phương thức

    public void luuXuongDatabase(ThucDon td) throws SQLException {
        DBC con = new DBC();
        //Lưu thông tin sảnh cưới vào database
        String sql = "INSERT INTO "+ bangThucDon +" (idThucPham, loaiThucPham, giaThucPham) VALUE (?,?,?)";
        PreparedStatement ps = con.getConnection().prepareStatement(sql);
        ps.setInt(1,td.getIdThucPham());
        ps.setString(2,td.getLoaiThucPham());
        ps.setBigDecimal(3,td.getGiaThucPham());
        ps.execute();
        ps.close();
        con.close();
        System.out.println("Thêm sảnh cưới thành công!!");
    }

    public void luuThongTinThucDon() throws SQLException {
        DBC con = new DBC();
        String sql = "INSERT INTO danhsanhthucdon (tenThucDon, giaTien) VALUE (?,?)";
        PreparedStatement ps = con.getConnection().prepareStatement(sql);
        ps.setString(1,bangThucDon);
        ps.setBigDecimal(2,tongGiaTienThucDon());
        ps.execute();
        ps.close();
        con.close();
    }

    public BigDecimal tongGiaTienThucDon() throws SQLException {
        double tong = 0;
        DBC con = new DBC();
        String sql = "select giaThucPham from "+bangThucDon+";";
        PreparedStatement ps = con.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            BigDecimal f= rs.getBigDecimal(1);
            double a = f.doubleValue();
            tong+=a;
        }
        rs.close();
        ps.close();
        con.close();
        return BigDecimal.valueOf(tong);
    }

    public void themTP() throws SQLException {
        int luaChon;
        do{
            System.out.println("1. Thêm thức ăn");
            System.out.println("2. Thêm thưc uống");
            System.out.println("0. Lưu dữ liệu và trở về.");
            System.out.print("Lựa chọn của bạn là: ");
            do{
                luaChon = scanner.nextInt();
                scanner.nextLine();
                if(luaChon < 0 || luaChon > 2)
                    System.out.print("Nhập sai, nhập lại: ");
            }while (luaChon < 0 || luaChon > 2);

            switch (luaChon) {
                case 1 -> {
                    QuanLyThucAn quanLyThucAn = new QuanLyThucAn();
                    quanLyThucAn.hienThiCSDL();
                    int id = nhapID();
                    BigDecimal donGiaThucAn = layDonGiaThucAn(id);
                    String loaiTP = "TA";
                    ThucDon td = new ThucDon(id,loaiTP,donGiaThucAn);
                    System.out.println("Có muốn lưu xuống database không?");
                    System.out.print("Lựa chọn của bạn(Y/N): ");
                    if(scanner.nextLine().equalsIgnoreCase("Y")){
                        luuXuongDatabase(td);
                    }
                }
                case 2 -> {
                    QuanLyThucUong quanLyThucUong = new QuanLyThucUong();
                    quanLyThucUong.hienThiCSDL();
                    int id = nhapID();
                    BigDecimal donGiaThucUong = layDonGiaThucUong(id);
                    String loaiTP = "TU";
                    ThucDon td = new ThucDon(id,loaiTP,donGiaThucUong);
                    System.out.println("Có muốn lưu xuống database không?");
                    System.out.print("Lựa chọn của bạn(Y/N): ");
                    if(scanner.nextLine().equalsIgnoreCase("Y")){
                        luuXuongDatabase(td);
                    }
                }
                default ->{
                    System.out.print("\nBạn có chắc muốn trở lại?" + "\nLựa chọn của bạn là (Y/N):");
                    String tl = scanner.nextLine();
                    if(tl.toUpperCase().contains("N"))
                        luaChon = 1;
                    else {
                        System.out.println("\n");
                    }
                }
            }
        }while (luaChon > 0);
    }

    public void xoaTP() throws SQLException {
        System.out.print("Nhập ID thực phẩm muốn xóa:");
        int xoa = scanner.nextInt(); scanner.nextLine();
        DBC con = new DBC();
        String sql = "DELETE FROM thucdon WHERE (id = ?);";
        PreparedStatement ps = con.getConnection().prepareStatement(sql);
        ps.setInt(1,xoa);
        ps.execute();
        System.out.println("Xóa thực phẩm thành công!!");

        //cập nhật lại ID
        ps.execute("ALTER TABLE thucdon DROP id;");
        ps.execute("ALTER TABLE thucdon AUTO_INCREMENT = 1;");
        ps.execute("ALTER TABLE thucdon ADD id int UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY FIRST;");
        System.out.println("Đã cập nhật lại ID!");
        ps.close();
        con.close();
    }

    public void hienThiCSDL() throws SQLException {
        System.out.println("\nHiển thị danh sách thức ăn trong databse");
        DBC con = new DBC();
        String sql = "SELECT * FROM thucdon;";
        PreparedStatement ps = con .getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        System.out.printf("\n%5s","id");
        System.out.printf("%10s","ID thực phẩm");
        System.out.printf("%20s","Loại thực phẩm");
        System.out.printf("%20s\n","Giá thực phẩm");
        while(rs.next()){
            System.out.printf("\n%5d",rs.getInt("id"));
            System.out.printf("%10d",rs.getInt("idThucPham"));
            System.out.printf("%20s",rs.getString("loaiThucPham"));
            System.out.printf("%,20.0f",rs.getBigDecimal("giaThucPham"));
        }
        rs.close();
        ps.close();
        con.close();
    }

    //phương thức sử dụng database
    public void capNhatTP() throws SQLException {
        DBC con = new DBC();
        scanner.nextLine();
        System.out.print("Nhập tên cột cần thay đổi (tên dưới database):");String tenCot = scanner.nextLine();
        System.out.print("Nhập giá trị cần thay đổi:");String giaTri = scanner.nextLine();
        String sql = "UPDATE thucdon SET `"+tenCot+"` = \""+giaTri+"\" WHERE ID = ?;";
        PreparedStatement ps = con.getConnection().prepareStatement(sql);
        System.out.print("Nhập ID thực phẩm cần chỉnh sửa / cập nhật: ");
        ps.setInt(1,scanner.nextInt());scanner.nextLine();
        ps.executeUpdate();
        ps.close();
        con.close();
        System.out.println("Đã cập nhật!!");
    }

    //menu
    public void MenuQLTP() throws SQLException {
        int luaChon;
        QuanLyThucDon quanLyThucDon = new QuanLyThucDon();
        do{
            System.out.println("\n========================== QUẢN LÝ THỰC ĐƠN ===========================");
            System.out.println("1. Thêm thực phẩm.");
            System.out.println("2. Xóa thực phẩm.");
            System.out.println("3. Cập nhật thực phẩm");
            System.out.println("4. Xuất danh sách thực phẩm.");
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
                    themTP();
                    luuThongTinThucDon();
                }
                case 2 -> {
                    xoaTP();
                }
                case 3 -> {
                    capNhatTP();
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
                        System.out.println("\n");
                    }
                }
            }
        }while (luaChon > 0);
    }

    @Override
    public String toString() {
        StringBuilder f = new StringBuilder();
        for(ThucDon s: dsThucPham){
            f.append(s.toString());
            f.append("\n");
        }
        return f.toString();
    }

    // getter và setter
    public List<ThucDon> getDsThucPham() {
        return dsThucPham;
    }
    public void setDsThucPham(List<ThucDon> dsThucDon) {
        this.dsThucPham = dsThucDon;
    }
}

