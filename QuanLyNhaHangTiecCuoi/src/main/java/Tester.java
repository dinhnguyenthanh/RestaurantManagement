import java.math.BigDecimal;
import java.sql.*;

public class Tester {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        //DATABASE
        System.out.println("loading");
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/?user=root",
                "root",
                "281001");
        System.out.println("loaded");


        String sql = "SELECT * FROM product WHERE name like concat('%', ? , '%')";
        PreparedStatement stm1 = conn.prepareStatement(sql);
        stm1.setString(1, "IPHONE");
        ResultSet rs1 = stm1.executeQuery();
        while (rs1.next()){
            System.out.printf("%d: %s - %.2f\n", rs1.getInt("id"),
                    rs1.getString("name"), rs1.getDouble("price"));
        }
        conn.close();
        /////////////////////////////////////////////////////////////////////////////////////////////

        /////////////////////////////////////////////////////////////////////////////////////////////
        /*System.out.println("=================================================================");
        System.out.println("==Sanh cuoi==");
        QuanLySanhCuoi qlsc = new QuanLySanhCuoi();
        SanhCuoi a = new SanhCuoi("A", "Tang 1", 50, new BigDecimal("200000000"));
        SanhCuoi b = new SanhCuoi("B", "Sanh ngoai troi", 60, new BigDecimal("242253434"));
        SanhCuoi c = new SanhCuoi("C", "Tang tret", 50, new BigDecimal("300000000"));
        qlsc.themSC(a);
        qlsc.themSC(b);
        qlsc.themSC(c);
        System.out.println(qlsc);
        System.out.println();
        System.out.println("Tra cuu theo suc chua:\n");
        qlsc.hienThi(qlsc.traCuuSC("A"));

        System.out.println("==Dich Vu==");
        DichVu dv1 = new Karaoke("Karaoke",new BigDecimal(1000000),4.5);
        DichVu dv2 = new TrangTri("Trang tri phoi canh",new BigDecimal(23231312));
        DichVu dv3 = new ThueCaSi("Thue ca si",new BigDecimal(999999999),"Son Tung M-TP",7);

        QuanLyDichVu qldv = new QuanLyDichVu();
        qldv.themDV(dv1);
        qldv.themDV(dv2);
        qldv.themDV(dv3);
        System.out.println(qldv);*/
    }
}
