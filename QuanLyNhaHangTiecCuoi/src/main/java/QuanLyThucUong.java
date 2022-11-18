import ConnectDB.DBC;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QuanLyThucUong {
    private static final Scanner scanner = new Scanner(System.in);
    protected List<ThucUong> dsThucUong = new ArrayList<>();

    public void themThucUong() throws SQLException {
        String ten = nhapTen();
        BigDecimal gia = nhapDonGia();
        String hang = nhapHangSanXuat();
        ThucUong tp = new ThucUong(ten,gia,hang);
        dsThucUong.add(tp);
        System.out.println("Có muốn lưu xuống database không?");
        System.out.print("Lựa chọn của bạn(Y/N): ");
        if(scanner.nextLine().equalsIgnoreCase("Y")){
            luuXuongDatabase(tp);
        }
    }

    public void xoaThucUong() throws SQLException {
        DBC con = new DBC();
        String sql ="DELETE FROM thucuong WHERE (`id` = ?);";
        PreparedStatement ps = con.getConnection().prepareStatement(sql);
        System.out.print("Nhập id thức uống muốn xóa:");
        ps.setInt(1,scanner.nextInt());scanner.nextLine();
        ps.execute();
        System.out.println("Xóa thành công");
        ps.execute("ALTER TABLE thucuong DROP id;");
        ps.execute("ALTER TABLE thucuong AUTO_INCREMENT = 1;");
        ps.execute("ALTER TABLE thucuong ADD id int UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY FIRST;");
        System.out.println("Đã cập nhật lại ID!");
        ps.close();
        con.close();
    }

    public void luuXuongDatabase(ThucUong tu) throws SQLException {
        DBC con = new DBC();
        String sql = "INSERT INTO `test`.`thucuong` (`maTP`, `tenTP`, `donGia`, `hangSanXuat`) VALUES (?,?,?,?);";
        PreparedStatement ps = con.getConnection().prepareStatement(sql);
        ps.setString(1,tu.getMaTP());
        ps.setString(2,tu.getTenTP());
        ps.setBigDecimal(3,tu.getGiaTP());
        ps.setString(4,tu.getHangSanXuat());
        ps.execute();

        ps.close();
        con.close();
    }

    public void hienThi(List<ThucUong> kq) throws SQLException {
        System.out.println("=======THỨC ĂN=========");
        System.out.printf("\n%10s","Mã thức uống");
        System.out.printf("%20s","Tên thức uống");
        System.out.printf("%20s","Đơn giá");
        System.out.printf("%20s","Hãng sản xuất");
        for (ThucUong s: kq){
            System.out.printf("\n%10s",s.getMaTP());
            System.out.printf("%20s",s.getTenTP());
            System.out.printf("%20s",s.getGiaTP());
            System.out.printf("%20s",s.getHangSanXuat());
        }
    }

    public void hienThiCSDL() throws SQLException {
        System.out.println("\nHiển thị danh sách thức uống trong databse");
        DBC con = new DBC();
        String sql = "SELECT * FROM thucuong;";
        PreparedStatement ps = con .getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        System.out.printf("\n%5s","id");
        System.out.printf("%15s","Mã thức uống");
        System.out.printf("%20s","Tên thức uống");
        System.out.printf("%20s","Đơn giá");
        System.out.printf("%20s","Hãng sản xuất");
        while(rs.next()){
            System.out.printf("\n%5d",rs.getInt("id"));
            System.out.printf("%15s",rs.getString("maTP"));
            System.out.printf("%20s",rs.getString("tenTP"));
            System.out.printf("%,20.0f",rs.getBigDecimal("donGia"));
            System.out.printf("%20s",rs.getString("hangSanXuat"));
        }
        rs.close();
        ps.close();
        con.close();
    }

    public void capNhatThucUong() throws SQLException {
        DBC con = new DBC();
        scanner.nextLine();
        System.out.print("Nhập tên cột cần thay đổi (tên dưới database):");String tenCot = scanner.nextLine();
        System.out.print("Nhập giá trị cần thay đổi:");String giaTri = scanner.nextLine();
        String sql = "UPDATE thucuong SET `"+tenCot+"` = \""+giaTri+"\" WHERE ID = ?;";
        PreparedStatement ps = con.getConnection().prepareStatement(sql);
        System.out.print("Nhập ID thức uống cần chỉnh sửa / cập nhật: ");
        ps.setInt(1,scanner.nextInt());scanner.nextLine();
        ps.executeUpdate();
        ps.close();
        con.close();
        System.out.println("Đã cập nhật!!");
    }

    public List<ThucUong> traCuuThucUong(String tk) {
        List<ThucUong> kq = new ArrayList<>();
        for (ThucUong s : this.dsThucUong) {
            if (s.getTenTP().contains(tk))
                kq.add(s);
        }
        return kq;
    }

    public void traCuuThucUongCSDL() throws SQLException {
        scanner.nextLine();
        DBC con = new DBC();
        System.out.print("Nhập từ khóa tra cứu: ");
        String kq = scanner.nextLine();
        String sql = String.format("SELECT * from thucuong where tenTP like '%s';","%"+kq+"%");
        PreparedStatement ps = con.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        System.out.printf("\n%5s%15s%15s%15s%15s\n","id","Mã thức uống","Tên thức uống","Đơn giá","Hãng SX");
        while (rs.next()){
            System.out.printf("%5d", rs.getInt("id"));
            System.out.printf("%15s",rs.getString("maTP"));
            System.out.printf("%15s", rs.getString("tenTP"));
            System.out.printf("%,15.0f\n", rs.getBigDecimal("donGia"));
            System.out.printf("%15s", rs.getString("hangSanXuat"));
        }
        rs.close();
        ps.close();
        con.close();
    }

    public void menuQLTU() throws SQLException {
        int luaChon;
        QuanLyThucUong quanLyThucUong = new QuanLyThucUong();
        do{
            System.out.println("\n======= QUẢN LÝ THỰC UỐNG =======");
            System.out.println("1. Thêm thức uống.");
            System.out.println("2. Xóa thức uống.");
            System.out.println("3. Cập thực thức uống");
            System.out.println("4. Xuất danh sách thức uống.");
            System.out.println("5. Tra cứu thức uống theo tên.");
            System.out.println("0. Lưu dữ liệu và trở về.");
            System.out.print("Lựa chọn của bạn là: ");
            do{
                luaChon = scanner.nextInt();
                if(luaChon < 0 || luaChon > 5)
                    System.out.print("Nhập sai, nhập lại: ");
            }while (luaChon < 0 || luaChon > 5);
            scanner.nextLine();
            switch (luaChon) {
                case 1 -> {
                    themThucUong();
                }
                case 2 -> {
                    xoaThucUong();
                }
                case 3 -> {
                    capNhatThucUong();
                }
                case 4 -> {
                    hienThi(dsThucUong);
                    hienThiCSDL();
                }
                case 5 -> {
                    System.out.print("Tra cứu thức uống ở DS hiện tại hay ở Database?\n1. DS hiện tại\n2. Database\nLựa chọn của bạn: ");
                    if(scanner.nextInt()==1) {
                        scanner.nextLine();
                        System.out.print("Nhập tên thức uống cần tra cứu: ");
                        quanLyThucUong.hienThi(quanLyThucUong.traCuuThucUong(scanner.nextLine()));
                    }
                    else{
                        traCuuThucUongCSDL();
                    }

                }
                default ->{
                    System.out.println("\n\n\t\t\t\t\t\t\t\t\t\t\t-------\n\n");
                }
            }
        }while (luaChon > 0);
    }

    @Override
    public String toString() {
        StringBuilder f = new StringBuilder();
        for(ThucUong tu: dsThucUong){
            f.append(tu.toString());
            f.append("\n");
        }
        return f.toString();
    }

    private String nhapTen(){
        System.out.print("\nNhập tên thức uống: ");
        return scanner.nextLine();
    }
    private BigDecimal nhapDonGia(){
        System.out.print("Nhập đơn giá thực phẩm: ");
        BigDecimal gia = scanner.nextBigDecimal();
        scanner.nextLine();
        return gia;
    }
    private String nhapHangSanXuat(){
        System.out.print("Nhập hãng sản xuất:");
        return scanner.nextLine();
    }

    public List<ThucUong> getDsThucUong() {
        return dsThucUong;
    }
    public void setDsThucUong(List<ThucUong > dsThucUong) {
        this.dsThucUong = dsThucUong;
    }
}
