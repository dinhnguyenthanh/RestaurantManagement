import ConnectDB.DBC;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QuanLyThucAn {
    private static final Scanner scanner = new Scanner(System.in);
    private List<ThucAn> dsThucAn = new ArrayList<>();

    public void themThucAn() throws SQLException {
        String ten = nhapTen();
        BigDecimal gia = nhapDonGia();
        boolean anChay = isChay();
        ThucAn tp = new ThucAn(ten,gia,anChay);
        dsThucAn.add(tp);
        System.out.println("Có muốn lưu xuống database không?");
        System.out.print("Lựa chọn của bạn(Y/N): ");
        if(scanner.nextLine().equalsIgnoreCase("Y")){
            luuXuongDatabase(tp);
        }
    }

    public void xoaThucAn() throws SQLException {
        DBC con = new DBC();
        String sql ="DELETE FROM thucan WHERE (`id` = ?);";
        PreparedStatement ps = con.getConnection().prepareStatement(sql);
        System.out.print("Nhập id thức ăn muốn xóa:");
        ps.setInt(1,scanner.nextInt());scanner.nextLine();
        ps.execute();
        System.out.println("Xóa thành công");
        ps.execute("ALTER TABLE thucan DROP id;");
        ps.execute("ALTER TABLE thucan AUTO_INCREMENT = 1;");
        ps.execute("ALTER TABLE thucan ADD id int UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY FIRST;");
        System.out.println("Đã cập nhật lại ID!");
        ps.close();
        con.close();
    }

    public void luuXuongDatabase(ThucAn ta) throws SQLException {
        DBC con = new DBC();
        String sql = "INSERT INTO `test`.`thucan` (`maTP`,`tenTP`, `donGia`, `anChay`) VALUES (?,?,?,?);";
        PreparedStatement ps = con.getConnection().prepareStatement(sql);
            ps.setString(1, ta.getMaTP());
            ps.setString(2,ta.getTenTP());
            ps.setBigDecimal(3,ta.getGiaTP());
            ps.setBoolean(4,ta.isAnChay());
            ps.execute();
        ps.close();
        con.close();
    }

    public void hienThi(List<ThucAn> kq)  {
        System.out.println("=======THỨC ĂN=========");
        System.out.printf("\n%10s","Mã thức ăn");
        System.out.printf("%20s","Tên thức ăn");
        System.out.printf("%20s","Đơn giá");
        System.out.printf("%20s","Ăn chay");
        for (ThucAn s: kq){
            System.out.printf("\n%10s",s.getMaTP());
            System.out.printf("%20s",s.getTenTP());
            System.out.printf("%20s",s.getGiaTP());
            System.out.printf("%20s",s.isAnChay());
        }
    }

    public void hienThiCSDL() throws SQLException {
        System.out.println("\nHiển thị danh sách thức ăn trong databse");
        DBC con = new DBC();
        String sql = "SELECT * FROM thucan;";
        PreparedStatement ps = con .getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        System.out.printf("\n%5s","id");
        System.out.printf("%15s","Mã thức ăn");
        System.out.printf("%20s","Tên thức ăn");
        System.out.printf("%20s","Đơn giá");
        System.out.printf("%20s","Ăn chay");
        while(rs.next()){
            System.out.printf("\n%5d",rs.getInt("id"));
            System.out.printf("%15s",rs.getString("maTP"));
            System.out.printf("%20s",rs.getString("tenTP"));
            System.out.printf("%,20.0f",rs.getBigDecimal("donGia"));
            System.out.printf("%20s",rs.getString("anChay"));
        }
        rs.close();
        ps.close();
        con.close();
    }

    public void capNhatThucAn() throws SQLException {
        DBC con = new DBC();
        scanner.nextLine();
        System.out.print("Nhập tên cột cần thay đổi (tên dưới database):");String tenCot = scanner.nextLine();
        System.out.print("Nhập giá trị cần thay đổi:");String giaTri = scanner.nextLine();
        String sql = "UPDATE thucan SET `"+tenCot+"` = \""+giaTri+"\" WHERE ID = ?;";
        PreparedStatement ps = con.getConnection().prepareStatement(sql);
        System.out.print("Nhập ID thức ăn cần chỉnh sửa / cập nhật: ");
        ps.setInt(1,scanner.nextInt());scanner.nextLine();
        ps.executeUpdate();
        ps.close();
        con.close();
        System.out.println("Đã cập nhật!!");
    }

    public List<ThucAn> traCuuThucAn(String tk){
        List<ThucAn> kq = new ArrayList<>();
        for (ThucAn s: this.dsThucAn) {
            if(s.getTenTP().contains(tk))
                kq.add(s);
        }
        return kq;
    }

    public void traCuuThucAnCSDL() throws SQLException {
        scanner.nextLine();
        DBC con = new DBC();
        System.out.print("Nhập từ khóa tra cứu: ");
        String kq = scanner.nextLine();
        String sql = String.format("SELECT * from thucan where tenTP like '%s';","%"+kq+"%");
        PreparedStatement ps = con.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        System.out.printf("\n%5s%15s%15s%15s%15s\n","id","Mã thức ăn","Tên thức ăn","Đơn giá","Ăn chay");
        while (rs.next()){
            System.out.printf("%5d", rs.getInt("id"));
            System.out.printf("%15s",rs.getString("maTP"));
            System.out.printf("%15s", rs.getString("tenTP"));
            System.out.printf("%,15.0f\n", rs.getBigDecimal("donGia"));
            System.out.printf("%15s", rs.getBoolean("anChay"));
        }
        rs.close();
        ps.close();
        con.close();
    }

    public void menuQLTA() throws SQLException {
        int luaChon;
        QuanLyThucAn quanLyThucAn = new QuanLyThucAn();
        do{
            System.out.println("\n======= QUẢN LÝ THỰC ĂN =======");
            System.out.println("1. Thêm thức ăn.");
            System.out.println("2. Xóa thức ăn.");
            System.out.println("3. Cập thực thức ăn");
            System.out.println("4. Xuất danh sách thức ăn.");
            System.out.println("5. Tra cứu thức ăn theo tên.");
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
                    themThucAn();
                }
                case 2 -> {
                    xoaThucAn();
                }
                case 3 -> {
                    capNhatThucAn();
                }
                case 4 -> {
                    hienThi(dsThucAn);
                    hienThiCSDL();
                }
                case 5 -> {
                    System.out.print("Tra cứu thức ăn ở DS hiện tại hay ở Database?\n1. DS hiện tại\n2. Database\nLựa chọn của bạn: ");
                    if(scanner.nextInt()==1) {
                        scanner.nextLine();
                        System.out.print("Nhập tên thức ăn cần tra cứu: ");
                        quanLyThucAn.hienThi(quanLyThucAn.traCuuThucAn(scanner.nextLine()));
                    }
                    else{
                        traCuuThucAnCSDL();
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
        for(ThucAn ta: dsThucAn){
            f.append(ta.toString());
            f.append("\n");
        }
        return f.toString();
    }

    private String nhapTen(){
        System.out.print("\nNhập tên thức ăn: ");
        return scanner.nextLine();
    }
    private BigDecimal nhapDonGia(){
        System.out.print("Nhập đơn giá thực phẩm: ");
        BigDecimal gia = scanner.nextBigDecimal();
        scanner.nextLine();
        return gia;
    }
    private boolean isChay(){
        System.out.println("Có phải là đồ chay không?");
        System.out.print("Trả lời (Y/N):");
        return scanner.nextLine().equalsIgnoreCase("Y");
    }

    public List<ThucAn> getDsThucAn() {
        return dsThucAn;
    }
    public void setDsThucAn(List<ThucAn> dsThucAn) {
        this.dsThucAn = dsThucAn;
    }
}
