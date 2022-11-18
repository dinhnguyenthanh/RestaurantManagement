import ConnectDB.DBC;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QuanLyKaraoke {
    private static final Scanner scanner = new Scanner(System.in);
    private List<Karaoke> dsKaraoke = new ArrayList<>();

    public void themKaRaoKe() throws SQLException {
        String ten = "Karaoke";
        BigDecimal gia = nhapDonGia();
        double soGio = nhapSoGioThue();
        Karaoke k = new Karaoke(ten,gia,soGio);
        dsKaraoke.add(k);
        System.out.println("Có muốn lưu xuống database không?");
        System.out.print("Lựa chọn của bạn(Y/N): ");
        if(scanner.nextLine().equalsIgnoreCase("Y")){
            luuXuongDatabase(k);
        }
    }

    public void xoaKaraoke() throws SQLException {
        DBC con = new DBC();
        String sql ="DELETE FROM karaoke WHERE (`id` = ?);";
        PreparedStatement ps = con.getConnection().prepareStatement(sql);
        System.out.print("Nhập id karaoke muốn xóa:");
        ps.setInt(1,scanner.nextInt());scanner.nextLine();
        ps.execute();
        System.out.println("Xóa thành công");
        ps.execute("ALTER TABLE karaoke DROP id;");
        ps.execute("ALTER TABLE karaoke AUTO_INCREMENT = 1;");
        ps.execute("ALTER TABLE karaoke ADD id int UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY FIRST;");
        System.out.println("Đã cập nhật lại ID!");
        ps.close();
        con.close();
    }

    public void luuXuongDatabase(Karaoke k) throws SQLException {
        DBC con = new DBC();
        String sql = "INSERT INTO `test`.`karaoke` (`maDV`,`tenDV`, `donGia`, `soGio`) VALUES (?,?,?,?);";
        PreparedStatement ps = con.getConnection().prepareStatement(sql);
        ps.setString(1, k.getMaDV());
        ps.setString(2,k.getTenDV());
        ps.setBigDecimal(3,k.getGiaDV());
        ps.setDouble(4,k.getThoiGianThue());
        ps.execute();
        ps.close();
        con.close();
    }

    public void hienThi(List<Karaoke> kq)  {
        System.out.println("=======KARAOKE=========");
        System.out.printf("\n%10s","Mã dịch vụ");
        System.out.printf("%20s","Tên dịch vụ");
        System.out.printf("%20s","Đơn giá");
        System.out.printf("%20s","Số giờ thuê");
        for (Karaoke k: kq){
            System.out.printf("\n%10s",k.getMaDV());
            System.out.printf("%20s",k.getTenDV());
            System.out.printf("%20s",k.getGiaDV());
            System.out.printf("%20.1f",k.getThoiGianThue());
        }
    }

    public void hienThiCSDL() throws SQLException {
        System.out.println("\nHiển thị danh sách dịch vụ karaoke trong databse");
        DBC con = new DBC();
        String sql = "SELECT * FROM karaoke;";
        PreparedStatement ps = con .getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        System.out.printf("\n%5s","id");
        System.out.printf("%15s","Mã dịch vụ");
        System.out.printf("%20s","Tên dịch vụ");
        System.out.printf("%20s","Đơn giá");
        System.out.printf("%20s","Số giờ thuê");
        while(rs.next()){
            System.out.printf("\n%5d",rs.getInt("id"));
            System.out.printf("%15s",rs.getString("maDV"));
            System.out.printf("%20s",rs.getString("tenDV"));
            System.out.printf("%,20.0f",rs.getBigDecimal("donGia"));
            System.out.printf("%20.1f",rs.getDouble("soGioThue"));
        }
        rs.close();
        ps.close();
        con.close();
    }

    public void capNhatKaraoke() throws SQLException {
        DBC con = new DBC();
        scanner.nextLine();
        System.out.print("Nhập tên cột cần thay đổi (tên dưới database):");String tenCot = scanner.nextLine();
        System.out.print("Nhập giá trị cần thay đổi:");String giaTri = scanner.nextLine();
        String sql = "UPDATE karaoke SET `"+tenCot+"` = \""+giaTri+"\" WHERE ID = ?;";
        PreparedStatement ps = con.getConnection().prepareStatement(sql);
        System.out.print("Nhập ID karaoke cần chỉnh sửa / cập nhật: ");
        ps.setInt(1,scanner.nextInt());scanner.nextLine();
        ps.executeUpdate();
        ps.close();
        con.close();
        System.out.println("Đã cập nhật!!");
    }

    public List<Karaoke> traCuuKaraoke(String tk){
        List<Karaoke> kq = new ArrayList<>();
        for (Karaoke s: this.dsKaraoke) {
            if(s.getTenDV().contains(tk))
                kq.add(s);
        }
        return kq;
    }

    public void traCuuKaraokeCSDL() throws SQLException {
        scanner.nextLine();
        DBC con = new DBC();
        System.out.print("Nhập từ khóa tra cứu: ");
        String kq = scanner.nextLine();
        String sql = String.format("SELECT * from karaoke where tenDV like '%s';","%"+kq+"%");
        PreparedStatement ps = con.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        System.out.printf("\n%5s%15s%15s%15s%15s\n","id","Mã DV","Tên DV","Đơn giá","Số giờ thuê");
        while (rs.next()){
            System.out.printf("%5d", rs.getInt("id"));
            System.out.printf("%15s",rs.getString("maDV"));
            System.out.printf("%15s", rs.getString("tenDV"));
            System.out.printf("%,15.0f\n", rs.getBigDecimal("donGia"));
            System.out.printf("%15.1f", rs.getDouble("soGioThue"));
        }
        rs.close();
        ps.close();
        con.close();
    }

    public void menuQLK() throws SQLException {
        int luaChon;
        QuanLyKaraoke quanLyKaraoke = new QuanLyKaraoke();
        do{
            System.out.println("\n======= QUẢN LÝ KARAOKE =======");
            System.out.println("1. Thêm DV karaoke.");
            System.out.println("2. Xóa DV karaoke.");
            System.out.println("3. Cập thực DV karaoke");
            System.out.println("4. Xuất danh sách DV karaoke.");
            System.out.println("5. Tra cứu DV karaoke theo tên.");
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
                    themKaRaoKe();
                }
                case 2 -> {
                    xoaKaraoke();
                }
                case 3 -> {
                    capNhatKaraoke();
                }
                case 4 -> {
                    hienThi(dsKaraoke);
                    hienThiCSDL();
                }
                case 5 -> {
                    System.out.print("Tra cứu thức ăn ở DS hiện tại hay ở Database?\n1. DS hiện tại\n2. Database\nLựa chọn của bạn: ");
                    if(scanner.nextInt()==1) {
                        scanner.nextLine();
                        System.out.print("Nhập tên thức ăn cần tra cứu: ");
                        quanLyKaraoke.hienThi(quanLyKaraoke.traCuuKaraoke(scanner.nextLine()));
                    }
                    else{
                        traCuuKaraokeCSDL();
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
        for(Karaoke k: dsKaraoke){
            f.append(k.toString());
            f.append("\n");
        }
        return f.toString();
    }

    private BigDecimal nhapDonGia(){
        System.out.print("Nhập đơn giá karaoke: ");
        BigDecimal gia = scanner.nextBigDecimal();
        scanner.nextLine();
        return gia;
    }
    private double nhapSoGioThue(){
        System.out.print("Nhập số giờ thuê karaoke: ");
        double soGio = scanner.nextDouble();
        scanner.nextLine();
        return soGio;
    }

    public List<Karaoke> getDsKaraoke() {
        return dsKaraoke;
    }
    public void setDsKaraoke(List<Karaoke> dsKaraoke) {
        this.dsKaraoke = dsKaraoke;
    }
}
