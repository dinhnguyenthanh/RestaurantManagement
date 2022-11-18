import ConnectDB.DBC;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QuanLyThucPham {
    private final Scanner scanner = new Scanner(System.in);
    protected List<ThucPham> dsThucPham = new ArrayList<>();
    public static int soCotTTTP ;
    public static int soThucPham ;

    //Các phương thức
    public void themTP() throws SQLException {
        int luaChon;
        do {
            System.out.println("======= Thêm thực phẩm =======");
            System.out.println("1. Thêm thức ăn.");
            System.out.println("2. Thêm thức uống.");
            System.out.println("0. Lưu dữ liệu và trở về.");
            System.out.print("Lựa chọn của bạn là: ");
            do {
                luaChon = scanner.nextInt();
                if (luaChon < 0 || luaChon > 2)
                    System.out.print("Nhập sai, nhập lại: ");
            } while (luaChon < 0 || luaChon > 2);
            scanner.nextLine();
            switch (luaChon) {
                case 1:{
                    QuanLyThucAn quanLyThucAn = new QuanLyThucAn();
                    quanLyThucAn.themThucAn();
                }
                case 2:{
                    QuanLyThucUong quanLyThucUong = new QuanLyThucUong();
                    quanLyThucUong.themThucUong();
                }
                default :{
                    System.out.print("\nBạn có chắc muốn trở lại?" + "\nLựa chọn của bạn là (Y/N):");
                    String tl = scanner.nextLine();
                    if(tl.toUpperCase().contains("N"))
                        luaChon = 1;
                    else {
                        System.out.println("\n\n\t\t\t\t\t\t\t\t\t\t\t-------\n\n");
                    }
                    break;
                }
            }
        }while (luaChon > 0);
    }

    public void xoaTP(ThucPham tp){
        this.dsThucPham.remove(tp);
    }

    public void capNhatTP() throws SQLException {
        DBC con = new DBC();
        scanner.nextLine();
        System.out.println("Bạn có muốn cập nhập danh sách thực phẩm ko?");
        System.out.print("Lựa chọn của bạn là (Y/N): ");
        String lc = scanner.nextLine();
        if(lc.toUpperCase().equals("Y")){
            System.out.print("Nhập tên cột cần thay đổi (tên dưới database):");String tenCot = scanner.nextLine();
            System.out.print("Nhập giá trị cần thay đổi:");String giaTri = scanner.nextLine();
            String sql = "UPDATE danhsachthucpham SET `"+tenCot+"` = \""+giaTri+"\" WHERE ID = ?;";
            PreparedStatement ps = con.getConnection().prepareStatement(sql);
            System.out.print("Nhập ID thực phẩm cần chỉnh sửa / cập nhật: ");
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
            String sql1 = "UPDATE thuoctinhthucpham SET `"+tenCot1+"` = \""+giaTri1+"\" WHERE ID = ?;";
            PreparedStatement ps1 = con.getConnection().prepareStatement(sql1);
            System.out.print("Nhập ID thucpham cần chỉnh sửa / cập nhật: ");
            ps1.setInt(1,scanner.nextInt());scanner.nextLine();
            ps1.executeUpdate();
            ps1.close();
        }
        con.close();
        System.out.println("Đã cập nhật!!");
    }

    public List<ThucPham> traCuu(String tk){
        List<ThucPham> kq = new ArrayList<>();
        for(ThucPham tp: this.dsThucPham)
            if(tp.getTenTP().contains(tk))
                kq.add(tp);
        return kq;
    }

    public void hienThi(List<ThucPham> kq){
        for (ThucPham tp: kq)
            System.out.println(tp);
    }

    //Nhập
    private String nhapTen(){
        System.out.print("Nhập tên thực phẩm: ");
        return scanner.nextLine();
    }

    private BigDecimal nhapDonGia(){
        System.out.print("Nhập đơn giá thực phẩm: ");
        return scanner.nextBigDecimal();
    }
    //menu
    public void MenuQLTP() throws SQLException {
        int luaChon;
        QuanLyThucPham quanLyThucPham = new QuanLyThucPham();
        do{
            System.out.println("========================== QUẢN LÝ THỰC PHẨM ===========================");
            System.out.println("1. Quản lý thức ăn.");
            System.out.println("2. Quản lý thức uống.");
            System.out.println("0. Lưu dữ liệu và trở về.");
            System.out.print("Lựa chọn của bạn là: ");
            do{
                luaChon = scanner.nextInt();
                if(luaChon < 0 || luaChon > 2)
                    System.out.print("Nhập sai, nhập lại: ");
            }while (luaChon < 0 || luaChon > 2);
            scanner.nextLine();
            switch (luaChon) {
                case 1 -> {
                    QuanLyThucAn quanLyThucAn = new QuanLyThucAn();
                    quanLyThucAn.menuQLTA();
                }
                case 2 -> {
                    QuanLyThucUong quanLyThucUong = new QuanLyThucUong();
                    quanLyThucUong.menuQLTU();
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
        for(ThucPham tp: dsThucPham){
            f.append(tp.toString());
            f.append("\n");
        }
        return f.toString();
    }

    // getter và setter
    public List<ThucPham> getDsThucPham() {
        return dsThucPham;
    }
    public void setDsThucPham(List<ThucPham> dsThucPham) {
        this.dsThucPham = dsThucPham;
    }
}