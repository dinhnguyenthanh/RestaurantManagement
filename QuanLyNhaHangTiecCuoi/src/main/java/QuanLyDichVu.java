import ConnectDB.DBC;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QuanLyDichVu {
    private final Scanner scanner = new Scanner(System.in);
    private List<DichVu> dsDichVu = new ArrayList<>();
    public static int soCotTTDV = 0;
    public static int soDichVu = 0;

    //Các phương thức
    public void themDV() {
        String ten = nhapTen();
        BigDecimal donGia = nhapDonGia();
        scanner.nextLine();
        /*DichVu dichVu = new DichVu(ten,donGia);
        this.dsDichVu.add(dichVu);*/
    }
    public void xoaDV(DichVu dv){
        this.dsDichVu.remove(dv);
    }

    public void capNhatDV() throws SQLException {
        DBC con = new DBC();
        scanner.nextLine();
        System.out.println("Bạn có muốn cập nhập danh sách dịch vụ ko?");
        System.out.print("Lựa chọn của bạn là (Y/N): ");
        String lc = scanner.nextLine();
        if(lc.toUpperCase().equals("Y")){
            System.out.print("Nhập tên cột cần thay đổi (tên dưới database):");String tenCot = scanner.nextLine();
            System.out.print("Nhập giá trị cần thay đổi:");String giaTri = scanner.nextLine();
            String sql = "UPDATE danhsachdichvu SET `"+tenCot+"` = \""+giaTri+"\" WHERE ID = ?;";
            PreparedStatement ps = con.getConnection().prepareStatement(sql);
            System.out.print("Nhập ID dịch vụ cần chỉnh sửa / cập nhật: ");
            ps.setInt(1,scanner.nextInt());scanner.nextLine();
            ps.executeUpdate();
            ps.close();
        }

        //cập nhập bảng thuộc tính
        System.out.println("Bạn có muốn cập nhập thuộc tích ko?");
        System.out.print("Lựa chọn của bạn là (Y/N): ");
        String lc1 = scanner.nextLine();
        if(lc1.toUpperCase().equals("Y")){
            System.out.print("Nhập tên cột cần thay đổi (tên dưới database):");String tenCot1 = scanner.nextLine();
            System.out.print("Nhập giá trị cần thay đổi:");String giaTri1 = scanner.nextLine();
            String sql1 = "UPDATE thuoctinhdichvu SET `"+tenCot1+"` = \""+giaTri1+"\" WHERE ID = ?;";
            PreparedStatement ps1 = con.getConnection().prepareStatement(sql1);
            System.out.print("Nhập ID dịch vụ cần chỉnh sửa / cập nhật: ");
            ps1.setInt(1,scanner.nextInt());scanner.nextLine();
            ps1.executeUpdate();
            ps1.close();
        }
        con.close();
        System.out.println("Đã cập nhật!!");
    }
    public List<DichVu> tracCuu(String tk){
        List<DichVu> kq = new ArrayList<>();
        for(DichVu dv: this.dsDichVu)
            if(dv.getTenDV().contains(tk))
                kq.add(dv);
        return kq;
    }
    public void hienThi(List<DichVu> kq){
        for (DichVu dv: kq)
            System.out.println(dv);
    }

    //Nhập
    private String nhapTen(){
        System.out.print("Nhập tên dịch vụ: ");
        return scanner.nextLine();
    }
    private BigDecimal nhapDonGia(){
        System.out.print("Nhập đơn giá dịch vụ: ");
        return scanner.nextBigDecimal();
    }
    //menu
    public void MenuQLDV() throws SQLException {
        int luaChon;
        QuanLyDichVu quanLyDichVu = new QuanLyDichVu();
        do{
            System.out.println("========================== QUẢN LÝ DỊCH VỤ ===========================");
            System.out.println("1. Quản lý karaoke.");
            System.out.println("2. Quản lý thuê ca sĩ.");
            System.out.println("3. Quản lý trang trí phối cảnh.");
            System.out.println("0. Lưu dữ liệu và trở về.");
            System.out.print("Lựa chọn của bạn là: ");
            do{
                luaChon = scanner.nextInt();
                if(luaChon < 0 || luaChon > 3)
                    System.out.print("Nhập sai, nhập lại: ");
            }while (luaChon < 0 || luaChon > 3);
            scanner.nextLine();
            switch (luaChon) {
                case 1 -> {
                    QuanLyKaraoke quanLyKaraoke = new QuanLyKaraoke();
                    quanLyKaraoke.menuQLK();
                }
                case 2 -> {
                    QuanLyThueCaSi quanLyThueCaSi = new QuanLyThueCaSi();
                    quanLyThueCaSi.menuQLTCS();
                }
                case 3 ->{
                    QuanLyTrangTri quanLyTrangTri = new QuanLyTrangTri();
                    quanLyTrangTri.menuQLTT();
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
        for(DichVu dv: dsDichVu){
            f.append(dv.toString());
            f.append("\n");
        }
        return f.toString();
    }

    // getter và setter
    public List<DichVu> getDsDichVu() {
        return dsDichVu;
    }
    public void setDsDichVu(List<DichVu> dsDichVu) {
        this.dsDichVu = dsDichVu;
    }
}
