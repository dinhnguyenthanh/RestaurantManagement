import ConnectDB.DBC;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QuanLyThueCaSi {
    private static final Scanner scanner = new Scanner(System.in);
    private List<ThueCaSi> dsThueCaSi = new ArrayList<>();

    public void themThueCaSi() throws SQLException {
        String ten = "Thuê ca sĩ";
        BigDecimal gia = nhapDonGia();
        String tenCS = nhapTenCaSi();
        int soBai = nhapSoLuongBaiHat();
        ThueCaSi k = new ThueCaSi(ten,gia,tenCS,soBai);
        dsThueCaSi.add(k);
        System.out.println("Có muốn lưu xuống database không?");
        System.out.print("Lựa chọn của bạn(Y/N): ");
        if(scanner.nextLine().equalsIgnoreCase("Y")){
            luuXuongDatabase(k);
        }
    }

    public void xoaThueCaSi() throws SQLException {
        DBC con = new DBC();
        String sql ="DELETE FROM thuecasi WHERE (`id` = ?);";
        PreparedStatement ps = con.getConnection().prepareStatement(sql);
        System.out.print("Nhập id DV thuê ca sĩ muốn xóa:");
        ps.setInt(1,scanner.nextInt());scanner.nextLine();
        ps.execute();
        System.out.println("Xóa thành công");
        ps.execute("ALTER TABLE thuecasi DROP id;");
        ps.execute("ALTER TABLE thuecasi AUTO_INCREMENT = 1;");
        ps.execute("ALTER TABLE thuecasi ADD id int UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY FIRST;");
        System.out.println("Đã cập nhật lại ID!");
        ps.close();
        con.close();
    }

    public void luuXuongDatabase(ThueCaSi k) throws SQLException {
        DBC con = new DBC();
        String sql = "INSERT INTO `test`.`thuecasi` (`maDV`,`tenDV`, `donGia`, `tenCaSi`,`soLuongBaiHat`) VALUES (?,?,?,?,?);";
        PreparedStatement ps = con.getConnection().prepareStatement(sql);
        ps.setString(1, k.getMaDV());
        ps.setString(2,k.getTenDV());
        ps.setBigDecimal(3,k.getGiaDV());
        ps.setString(4,k.getTenCS());
        ps.setInt(5,k.getSoLuongBH());
        ps.execute();
        ps.close();
        con.close();
    }

    public void hienThi(List<ThueCaSi> kq)  {
        System.out.println("=======THUÊ CA SĨ=========");
        System.out.printf("\n%10s","Mã dịch vụ");
        System.out.printf("%20s","Tên dịch vụ");
        System.out.printf("%20s","Đơn giá");
        System.out.printf("%20s","Tên ca sĩ");
        System.out.printf("%20s","Số lượng bài");
        for (ThueCaSi k: kq){
            System.out.printf("\n%10s",k.getMaDV());
            System.out.printf("%20s",k.getTenDV());
            System.out.printf("%20s",k.getGiaDV());
            System.out.printf("%20s",k.getTenCS());
            System.out.printf("%20d",k.getSoLuongBH());
        }
    }

    public void hienThiCSDL() throws SQLException {
        System.out.println("\nHiển thị danh sách dịch vụ thuê ca sĩ trong databse");
        DBC con = new DBC();
        String sql = "SELECT * FROM thuecasi;";
        PreparedStatement ps = con .getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        System.out.printf("\n%5s","id");
        System.out.printf("%15s","Mã dịch vụ");
        System.out.printf("%20s","Tên dịch vụ");
        System.out.printf("%20s","Đơn giá");
        System.out.printf("%20s","Tên ca sĩ");
        System.out.printf("%20s","Số lượng bài");
        while(rs.next()){
            System.out.printf("\n%5d",rs.getInt("id"));
            System.out.printf("%15s",rs.getString("maDV"));
            System.out.printf("%20s",rs.getString("tenDV"));
            System.out.printf("%,20.0f",rs.getBigDecimal("donGia"));
            System.out.printf("%20s",rs.getString("tenCaSi"));
            System.out.printf("%20d",rs.getInt("soLuongBaiHat"));
        }
        rs.close();
        ps.close();
        con.close();
    }

    public void capNhatThueCaSi() throws SQLException {
        DBC con = new DBC();
        scanner.nextLine();
        System.out.print("Nhập tên cột cần thay đổi (tên dưới database):");String tenCot = scanner.nextLine();
        System.out.print("Nhập giá trị cần thay đổi:");String giaTri = scanner.nextLine();
        String sql = "UPDATE thuecasi SET `"+tenCot+"` = \""+giaTri+"\" WHERE ID = ?;";
        PreparedStatement ps = con.getConnection().prepareStatement(sql);
        System.out.print("Nhập ID DV thuê ca sĩ cần chỉnh sửa / cập nhật: ");
        ps.setInt(1,scanner.nextInt());scanner.nextLine();
        ps.executeUpdate();
        ps.close();
        con.close();
        System.out.println("Đã cập nhật!!");
    }

    public List<ThueCaSi> traCuuThueCaSi(String tk){
        List<ThueCaSi> kq = new ArrayList<>();
        for (ThueCaSi s: this.dsThueCaSi) {
            if(s.getTenDV().contains(tk))
                kq.add(s);
        }
        return kq;
    }

    public void traCuuThueCaSiCSDL() throws SQLException {
        scanner.nextLine();
        DBC con = new DBC();
        System.out.print("Nhập từ khóa tra cứu: ");
        String kq = scanner.nextLine();
        String sql = String.format("SELECT * from thuecasi where tenDV like '%s';","%"+kq+"%");
        PreparedStatement ps = con.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        System.out.printf("\n%5s%15s%15s%15s%15s%15s\n","id","Mã DV","Tên DV","Đơn giá","Tên ca sĩ","Số lượng bài");
        while (rs.next()){
            System.out.printf("%5d", rs.getInt("id"));
            System.out.printf("%15s",rs.getString("maDV"));
            System.out.printf("%15s", rs.getString("tenDV"));
            System.out.printf("%,15.0f\n", rs.getBigDecimal("donGia"));
            System.out.printf("%15s", rs.getString("tenCaSi"));
            System.out.printf("%15.1d", rs.getInt("soLuongBaiHat"));
        }
        rs.close();
        ps.close();
        con.close();
    }

    public void menuQLTCS() throws SQLException {
        int luaChon;
        QuanLyThueCaSi quanLyThueCaSi = new QuanLyThueCaSi();
        do{
            System.out.println("\n======= QUẢN LÝ THUÊ CA SĨ =======");
            System.out.println("1. Thêm DV thuê ca sĩ.");
            System.out.println("2. Xóa DV ThueCaSi.");
            System.out.println("3. Cập thực DV thuê ca sĩ");
            System.out.println("4. Xuất danh sách DV thuê ca sĩ.");
            System.out.println("5. Tra cứu DV thuê ca sĩ theo tên.");
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
                    themThueCaSi();
                }
                case 2 -> {
                    xoaThueCaSi();
                }
                case 3 -> {
                    capNhatThueCaSi();
                }
                case 4 -> {
                    hienThi(dsThueCaSi);
                    hienThiCSDL();
                }
                case 5 -> {
                    System.out.print("Tra cứu DV thuê ca sĩ ở DS hiện tại hay ở Database?\n1. DS hiện tại\n2. Database\nLựa chọn của bạn: ");
                    if(scanner.nextInt()==1) {
                        scanner.nextLine();
                        System.out.print("Nhập tên DV thuê ca sĩ cần tra cứu: ");
                        quanLyThueCaSi.hienThi(quanLyThueCaSi.traCuuThueCaSi(scanner.nextLine()));
                    }
                    else{
                        traCuuThueCaSiCSDL();
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
        for(ThueCaSi k: dsThueCaSi){
            f.append(k.toString());
            f.append("\n");
        }
        return f.toString();
    }

    private BigDecimal nhapDonGia(){
        System.out.print("Nhập đơn giá ThueCaSi: ");
        BigDecimal gia = scanner.nextBigDecimal();
        scanner.nextLine();
        return gia;
    }
    private int nhapSoLuongBaiHat(){
        System.out.print("Nhập số lượng bài hát: ");
        int soBai = scanner.nextInt();
        scanner.nextLine();
        return soBai;
    }
    private String nhapTenCaSi(){
        System.out.print("Nhập tên ca sĩ: ");
        return scanner.nextLine();
    }

    public List<ThueCaSi> getDsThueCaSi() {
        return dsThueCaSi;
    }
    public void setDsThueCaSi(List<ThueCaSi> dsThueCaSi) {
        this.dsThueCaSi = dsThueCaSi;
    }
}
