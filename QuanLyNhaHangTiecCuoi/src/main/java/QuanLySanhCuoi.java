import ConnectDB.DBC;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QuanLySanhCuoi {
    //khai báo
    private List<SanhCuoi> dsSanhCuoi = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);

    //Nhập
    private String nhapTen(){
        System.out.print("Nhập tên sảnh cưới: ");
            return scanner.nextLine();
    }
    private String nhapViTri(){
        System.out.print("Nhập vị trí sảnh cưới: ");
        return scanner.nextLine();
    }
    private int nhapSucChua(){
        System.out.print("Nhập sức chứa sảnh cưới: ");
        return scanner.nextInt();
    }
    private BigDecimal nhapDonGia(){
        System.out.print("Nhập đơn giá sảnh cưới: ");
        return scanner.nextBigDecimal();
    }

    public void luuXuongDatabase(SanhCuoi sanhCuoi) throws SQLException {
        //connect database
        DBC con = new DBC();
        //Lưu thông tin sảnh cưới vào database
        String sql = "INSERT INTO sanhcuoi (maSanhCuoi, tenSanhCuoi, viTriSanhCuoi, sucChuaToiDa, donGiaSanhCuoi) VALUE (?,?,?,?,?)";
        PreparedStatement ps = con.getConnection().prepareStatement(sql);
        ps.setString(1,sanhCuoi.getMaSanh());
        ps.setString(2,sanhCuoi.getTenSanh());
        ps.setString(3,sanhCuoi.getViTri());
        ps.setInt(4,sanhCuoi.getSucChua());
        ps.setBigDecimal(5,sanhCuoi.getGiaThue());
        ps.execute();
        ps.close();
        con.close();
        System.out.println("Thêm sảnh cưới thành công!!");
    }

    public void themSC() throws SQLException {
        String ten = nhapTen();
        String viTri = nhapViTri();
        int sucChua = nhapSucChua();
        scanner.nextLine();
        BigDecimal donGia = nhapDonGia();
        scanner.nextLine();
        SanhCuoi sanhCuoi = new SanhCuoi(ten,viTri,sucChua,donGia);
        this.dsSanhCuoi.add(sanhCuoi);
        System.out.println("Có muốn lưu xuống database không?");
        System.out.print("Lựa chọn của bạn(Y/N): ");
        if(scanner.nextLine().equalsIgnoreCase("Y")){
            luuXuongDatabase(sanhCuoi);
        }
    }

    public void xoaSC() throws SQLException {
        System.out.print("Nhập ID sảnh cưới muốn xóa:");
        int xoa = scanner.nextInt(); scanner.nextLine();
        DBC con = new DBC();
        String sql = "DELETE FROM sanhcuoi WHERE (id = ?);";
        PreparedStatement ps = con.getConnection().prepareStatement(sql);
        ps.setInt(1,xoa);
        ps.execute();
        System.out.println("Xóa sảnh cưới thành công!!");

        //cập nhật lại ID
        ps.execute("ALTER TABLE sanhcuoi DROP id;");
        ps.execute("ALTER TABLE sanhcuoi AUTO_INCREMENT = 1;");
        ps.execute("ALTER TABLE sanhcuoi ADD id int UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY FIRST;");
        System.out.println("Đã cập nhật lại ID!");
        ps.close();
        con.close();
    }

    public List<SanhCuoi> traCuuSC(String tk){
        List<SanhCuoi> kq = new ArrayList<SanhCuoi>();
        for (SanhCuoi s: this.dsSanhCuoi) {
            if(s.getTenSanh().contains(tk))
                kq.add(s);
            if(s.getViTri().contains(tk))
                kq.add(s);
        }
        return kq;
    }

    public List<SanhCuoi> traCuuSC(int sc) {
        List<SanhCuoi> kq = new ArrayList<SanhCuoi>();
        for (SanhCuoi s : this.dsSanhCuoi) {
            if (s.getSucChua() == sc)
                kq.add(s);
        }
        return kq;
    }

    private void traCuuTenSCCSDL() throws SQLException {
        scanner.nextLine();
        DBC con = new DBC();
        System.out.print("Nhập từ khóa tra cứu: ");
        String kq = scanner.nextLine();
        String sql = String.format("SELECT * from sanhcuoi where tenSanhCuoi like '%s';","%"+kq+"%");
        PreparedStatement ps = con.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        System.out.printf("\n%5s%15s%15s%15s%15s%15s\n","id","Mã sảnh","Tên sảnh","Vị trí","Sức chứa","Đơn giá");
        while (rs.next()){
            System.out.printf("%5d", rs.getInt("id"));
            System.out.printf("%15s",rs.getString("maSanhCuoi"));
            System.out.printf("%15s", rs.getString("tenSanhCuoi"));
            System.out.printf("%15s", rs.getString("viTriSanhCuoi"));
            System.out.printf("%15d", rs.getInt("sucChuaToiDa"));
            System.out.printf("%,15.0f\n", rs.getBigDecimal("donGiaSanhCuoi"));
        }
        rs.close();
        ps.close();
        con.close();
    }

    private void traCuuViTriSCCSDL() throws SQLException {
        scanner.nextLine();
        DBC con = new DBC();
        System.out.print("Nhập từ khóa tra cứu: ");
        String kq = scanner.nextLine();
        String sql = String.format("SELECT * from sanhcuoi where viTriSanhCuoi like '%s';","%"+kq+"%");
        PreparedStatement ps = con.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        System.out.printf("\n%5s%15s%15s%15s%15s%15s\n","id","Mã sảnh","Tên sảnh","Vị trí","Sức chứa","Đơn giá");
        while (rs.next()){
            System.out.printf("%5d", rs.getInt("id"));
            System.out.printf("%15s",rs.getString("maSanhCuoi"));
            System.out.printf("%15s", rs.getString("tenSanhCuoi"));
            System.out.printf("%15s", rs.getString("viTriSanhCuoi"));
            System.out.printf("%15d", rs.getInt("sucChuaToiDa"));
            System.out.printf("%,15.0f\n", rs.getBigDecimal("donGiaSanhCuoi"));
        }
        rs.close();
        ps.close();
        con.close();
    }

    private void traCuuSucChuaSCCSDL() throws SQLException {
        DBC con = new DBC();
        String sql = "SELECT * from sanhcuoi where sucChuaToiDa >= ? and sucChuaToiDa <= ?;";
        PreparedStatement ps = con.getConnection().prepareStatement(sql);
        System.out.print("Nhập khoảng sức chứa (số bàn) cần tra cứu: ");
        int khoang1 = scanner.nextInt(); int khoang2 = scanner.nextInt();
        if(khoang1 >= khoang2){
            ps.setInt(1,khoang2);
            ps.setInt(2,khoang1);
        }
        else {
            ps.setInt(1,khoang1);
            ps.setInt(2,khoang2);
        }
        ResultSet rs = ps.executeQuery();
        System.out.printf("\n%5s%15s%15s%15s%15s%15s\n","id","Mã sảnh","Tên sảnh","Vị trí","Sức chứa","Đơn giá");
        while (rs.next()){
            System.out.printf("%5d", rs.getInt("id"));
            System.out.printf("%15s",rs.getString("maSanhCuoi"));
            System.out.printf("%15s", rs.getString("tenSanhCuoi"));
            System.out.printf("%15s", rs.getString("viTriSanhCuoi"));
            System.out.printf("%15d", rs.getInt("sucChuaToiDa"));
            System.out.printf("%,15.0f\n", rs.getBigDecimal("donGiaSanhCuoi"));
        }
        rs.close();
        ps.close();
        con.close();
    }

    public void hienThi(List<SanhCuoi> kq){
        System.out.println("======= SẢNH CƯỚI =========");
        System.out.printf("\n%15s","Mã sảnh");
        System.out.printf("%20s","Tên sảnh");
        System.out.printf("%20s","Vị trí");
        System.out.printf("%20s","sức chứa");
        System.out.printf("%20s\n","Giá thuê");
        for (SanhCuoi s: kq){
            System.out.printf("\n%10s",s.getMaSanh());
            System.out.printf("%20s",s.getTenSanh());
            System.out.printf("%20s",s.getViTri());
            System.out.printf("%20d",s.getSucChua());
            System.out.printf("%,20.0f\n",s.getGiaThue());
        }
    }

    public void hienThiCSDL() throws SQLException {
        System.out.println("\nHiển thị danh sách thức ăn trong databse");
        DBC con = new DBC();
        String sql = "SELECT * FROM sanhcuoi;";
        PreparedStatement ps = con .getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        System.out.printf("\n%5s","id");
        System.out.printf("%15s","Mã sảnh");
        System.out.printf("%20s","Tên sảnh");
        System.out.printf("%20s","Vị trí");
        System.out.printf("%20s","Sức chứa");
        System.out.printf("%20s","Giá Thuê");

        while(rs.next()){
            System.out.printf("\n%5d",rs.getInt("id"));
            System.out.printf("%15s",rs.getString("maSanhCuoi"));
            System.out.printf("%20s",rs.getString("tenSanhCuoi"));
            System.out.printf("%20s",rs.getString("viTriSanhCuoi"));
            System.out.printf("%20d",rs.getInt("sucChuaToiDa"));
            System.out.printf("%,20.0f",rs.getBigDecimal("donGiaSanhCuoi"));

        }
        rs.close();
        ps.close();
        con.close();
    }

    //phương thức sử dụng database
    public void capNhatSC() throws SQLException {
        DBC con = new DBC();
        scanner.nextLine();
        System.out.print("Nhập tên cột cần thay đổi (tên dưới database):");
        String tenCot = scanner.nextLine();
        System.out.print("Nhập giá trị cần thay đổi:");
        String giaTri = scanner.nextLine();

        String sql = "UPDATE sanhcuoi SET `"+tenCot+"` = \""+giaTri+"\" WHERE ID = ?;";
        PreparedStatement ps = con.getConnection().prepareStatement(sql);
        System.out.print("Nhập ID sảnh cưới cần chỉnh sửa / cập nhật: ");
        ps.setInt(1,scanner.nextInt());scanner.nextLine();
        ps.executeUpdate();
        ps.close();
        con.close();
        System.out.println("Đã cập nhật!!");
    }

    //menu
    public void MenuQLSC() throws SQLException {
        int luaChon;
        QuanLySanhCuoi quanLySanhCuoi = new QuanLySanhCuoi();
        do{
            System.out.println("\n========================== QUẢN LÝ SẢNH CƯỚI ===========================");
            System.out.println("1. Thêm sảnh cưới.");
            System.out.println("2. Xóa sảnh cưới.");
            System.out.println("3. Cập nhật sảnh cưới");
            System.out.println("4. Xuất danh sách sảnh cưới.");
            System.out.println("5. Tra cứu sảnh cưới theo tên.");
            System.out.println("6. Tra cứu sảnh cưới theo sức chứa.");
            System.out.println("7. Tra cứu sảnh cưới theo vị trí.");
            System.out.println("0. Lưu dữ liệu và trở về.");
            System.out.print("Lựa chọn của bạn là: ");
            do{
                luaChon = scanner.nextInt();
                if(luaChon < 0 || luaChon > 7)
                    System.out.print("Nhập sai, nhập lại: ");
            }while (luaChon < 0 || luaChon > 7);
            scanner.nextLine();
            switch (luaChon) {
                case 1 -> {
                    themSC();
                }
                case 2 -> {
                    xoaSC();
                }
                case 3 -> {
                    capNhatSC();
                }
                case 4 -> {
                    hienThi(dsSanhCuoi);
                    hienThiCSDL();
                }
                case 5 -> {
                    System.out.print("Tra cứu sảnh cưới ở DS hiện tại hay ở Database?\n1. DS hiện tại\n2. Database\nLựa chọn của bạn: ");
                    if(scanner.nextInt()==1) {
                        scanner.nextLine();
                        System.out.print("Nhập tên sảnh cưới cần tra cứu: ");
                        quanLySanhCuoi.hienThi(quanLySanhCuoi.traCuuSC(scanner.nextLine()));
                    }
                    else{
                        traCuuTenSCCSDL();
                    }
                }
                case 6 -> {
                    System.out.print("Tra cứu sảnh cưới ở DS hiện tại hay ở Database?\n1. DS hiện tại\n2. Database\nLựa chọn của bạn: ");
                    if(scanner.nextInt()==1) {
                        System.out.print("Nhập sức chứa sảnh cưới cần tra cứu: ");
                        int SC = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("Danh sách các sảnh cưới có sức chứa là \"" + SC + "\" : ");
                        quanLySanhCuoi.hienThi(quanLySanhCuoi.traCuuSC(SC));
                    }
                    else {
                        traCuuSucChuaSCCSDL();
                    }
                }
                case 7 -> {
                    System.out.print("Tra cứu sảnh cưới ở DS hiện tại hay ở Database?\n1. DS hiện tại\n2. Database\nLựa chọn của bạn: ");
                    if(scanner.nextInt()==1) {
                        System.out.print("Nhập vị trí sảnh cưới cần tra cứu: ");
                        String tk = scanner.nextLine();
                        System.out.println("Danh sách các sảnh cưới có chứa \"" + tk + "\" là : ");
                        quanLySanhCuoi.hienThi(quanLySanhCuoi.traCuuSC(tk));
                    }
                    else{
                        traCuuViTriSCCSDL();
                    }
                }
                default ->{
                    System.out.print("\nBạn có chắc muốn trở lại?" + "\nLựa chọn của bạn là (Y/N):");
                    String tl = scanner.nextLine();
                    if(tl.toUpperCase().contains("N"))
                        luaChon = 4;
                    else {
                        System.out.println("\n\n\t\t\t\t\t\t\t\t\t\t\t-------\n\n");
                    }
                }
            }
        }while (luaChon > 0);
    }

    @Override
    public String toString() {
        StringBuilder f = new StringBuilder();
        for(SanhCuoi s: dsSanhCuoi){
            f.append(s.toString());
            f.append("\n");
        }
        return f.toString();
    }

    //getter & setter
    public List<SanhCuoi> getDsSanhCuoi(){
        return this.dsSanhCuoi;
    }
    public void setDsSanhCuoi(List<SanhCuoi> dsSanhCuoi) {
        this.dsSanhCuoi = dsSanhCuoi;
    }
}
