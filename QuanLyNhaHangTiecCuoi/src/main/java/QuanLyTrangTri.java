import ConnectDB.DBC;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QuanLyTrangTri {
    private static final Scanner scanner = new Scanner(System.in);
    private List<TrangTri> dsTrangTri = new ArrayList<>();

    public void themTrangTri() throws SQLException {
        String ten = "Trang trí phối cảnh";
        BigDecimal gia = nhapDonGia();
        TrangTri k = new TrangTri(ten,gia);
        dsTrangTri.add(k);
        System.out.println("Có muốn lưu xuống database không?");
        System.out.print("Lựa chọn của bạn(Y/N): ");
        if(scanner.nextLine().equalsIgnoreCase("Y")){
            luuXuongDatabase(k);
        }
    }

    public void xoaTrangTri() throws SQLException {
        DBC con = new DBC();
        String sql ="DELETE FROM trangtri WHERE (`id` = ?);";
        PreparedStatement ps = con.getConnection().prepareStatement(sql);
        System.out.print("Nhập id DV trang trí muốn xóa:");
        ps.setInt(1,scanner.nextInt());scanner.nextLine();
        ps.execute();
        System.out.println("Xóa thành công");
        ps.execute("ALTER TABLE trangtri DROP id;");
        ps.execute("ALTER TABLE trangtri AUTO_INCREMENT = 1;");
        ps.execute("ALTER TABLE trangtri ADD id int UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY FIRST;");
        System.out.println("Đã cập nhật lại ID!");
        ps.close();
        con.close();
    }

    public void luuXuongDatabase(TrangTri k) throws SQLException {
        DBC con = new DBC();
        String sql = "INSERT INTO `test`.`trangtri` (`maDV`,`tenDV`, `donGia`) VALUES (?,?,?);";
        PreparedStatement ps = con.getConnection().prepareStatement(sql);
        ps.setString(1, k.getMaDV());
        ps.setString(2,k.getTenDV());
        ps.setBigDecimal(3,k.getGiaDV());
        ps.execute();
        ps.close();
        con.close();
    }

    public void hienThi(List<TrangTri> kq)  {
        System.out.println("=======TRANG TRÍ=========");
        System.out.printf("\n%10s","Mã dịch vụ");
        System.out.printf("%20s","Tên dịch vụ");
        System.out.printf("%20s","Đơn giá");
        for (TrangTri k: kq){
            System.out.printf("\n%10s",k.getMaDV());
            System.out.printf("%20s",k.getTenDV());
            System.out.printf("%20s",k.getGiaDV());
        }
    }

    public void hienThiCSDL() throws SQLException {
        System.out.println("\nHiển thị danh sách dịch vụ trang trí trong databse");
        DBC con = new DBC();
        String sql = "SELECT * FROM trangtri;";
        PreparedStatement ps = con .getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        System.out.printf("\n%5s","id");
        System.out.printf("%15s","Mã dịch vụ");
        System.out.printf("%20s","Tên dịch vụ");
        System.out.printf("%20s","Đơn giá");
        while(rs.next()){
            System.out.printf("\n%5d",rs.getInt("id"));
            System.out.printf("%15s",rs.getString("maDV"));
            System.out.printf("%20s",rs.getString("tenDV"));
            System.out.printf("%,20.0f",rs.getBigDecimal("donGia"));
        }
        rs.close();
        ps.close();
        con.close();
    }

    public void capNhatTrangTri() throws SQLException {
        DBC con = new DBC();
        scanner.nextLine();
        System.out.print("Nhập tên cột cần thay đổi (tên dưới database):");String tenCot = scanner.nextLine();
        System.out.print("Nhập giá trị cần thay đổi:");String giaTri = scanner.nextLine();
        String sql = "UPDATE trangtri SET `"+tenCot+"` = \""+giaTri+"\" WHERE ID = ?;";
        PreparedStatement ps = con.getConnection().prepareStatement(sql);
        System.out.print("Nhập ID DV trang trí cần chỉnh sửa / cập nhật: ");
        ps.setInt(1,scanner.nextInt());scanner.nextLine();
        ps.executeUpdate();
        ps.close();
        con.close();
        System.out.println("Đã cập nhật!!");
    }

    public List<TrangTri> traCuuTrangTri(String tk){
        List<TrangTri> kq = new ArrayList<>();
        for (TrangTri s: this.dsTrangTri) {
            if(s.getTenDV().contains(tk))
                kq.add(s);
        }
        return kq;
    }

    public void traCuuTrangTriCSDL() throws SQLException {
        scanner.nextLine();
        DBC con = new DBC();
        System.out.print("Nhập từ khóa tra cứu: ");
        String kq = scanner.nextLine();
        String sql = String.format("SELECT * from trangtri where tenDV like '%s';","%"+kq+"%");
        PreparedStatement ps = con.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        System.out.printf("\n%5s%15s%15s%15s\n","id","Mã DV","Tên DV","Đơn giá");
        while (rs.next()){
            System.out.printf("%5d", rs.getInt("id"));
            System.out.printf("%15s",rs.getString("maDV"));
            System.out.printf("%15s", rs.getString("tenDV"));
            System.out.printf("%,15.0f\n", rs.getBigDecimal("donGia"));
        }
        rs.close();
        ps.close();
        con.close();
    }

    public void menuQLTT() throws SQLException {
        int luaChon;
        QuanLyTrangTri quanLyTrangTri = new QuanLyTrangTri();
        do{
            System.out.println("\n======= QUẢN LÝ TRANG TRÍ =======");
            System.out.println("1. Thêm DV trang trí.");
            System.out.println("2. Xóa DV trang trí.");
            System.out.println("3. Cập thực DV trang trí");
            System.out.println("4. Xuất danh sách DV trang trí.");
            System.out.println("5. Tra cứu DV trang trí theo tên.");
            System.out.println("0. Lưu dữ liệu và trở về.");
            System.out.print("Lựa chọn của bạn là: ");
            do{
                luaChon = scanner.nextInt();
                if(luaChon < 0 || luaChon > 5)
                    System.out.print("Nhập sai, nhập lại: ");
            }while (luaChon < 0 || luaChon > 5);
            scanner.nextLine();
            switch (luaChon) {
                case 1 -> themTrangTri();
                case 2 -> xoaTrangTri();
                case 3 -> capNhatTrangTri();
                case 4 -> {
                    hienThi(dsTrangTri);
                    hienThiCSDL();
                }
                case 5 -> {
                    System.out.print("Tra cứu DV trang trí ở DS hiện tại hay ở Database?\n1. DS hiện tại\n2. Database\nLựa chọn của bạn: ");
                    if(scanner.nextInt()==1) {
                        scanner.nextLine();
                        System.out.print("Nhập tên DV trang trí cần tra cứu: ");
                        quanLyTrangTri.hienThi(quanLyTrangTri.traCuuTrangTri(scanner.nextLine()));
                    }
                    else{
                        traCuuTrangTriCSDL();
                    }
                }
                default -> System.out.println("\n\n\t\t\t\t\t\t\t\t\t\t\t-------\n\n");
            }
        }while (luaChon > 0);
    }

    @Override
    public String toString() {
        StringBuilder f = new StringBuilder();
        for(TrangTri k: dsTrangTri){
            f.append(k.toString());
            f.append("\n");
        }
        return f.toString();
    }

    private BigDecimal nhapDonGia(){
        System.out.print("Nhập đơn giá trang trí: ");
        BigDecimal gia = scanner.nextBigDecimal();
        scanner.nextLine();
        return gia;
    }

    public List<TrangTri> getDsTrangTri() {
        return dsTrangTri;
    }
    public void setDsTrangTri(List<TrangTri> dsTrangTri) {
        this.dsTrangTri = dsTrangTri;
    }
}
