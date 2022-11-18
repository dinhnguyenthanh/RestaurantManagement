import ConnectDB.DBC;

import java.math.BigDecimal;
import java.sql.*;
import java.util.GregorianCalendar;

public class Test {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/qlnhtc",
                "root",
                "281001");

        System.out.println("connect success");

        DBC con = new DBC();
        PreparedStatement ps = con.getConnection().prepareStatement("select donGia from danhsachdichvu where ID = 1;");
        ResultSet rs = ps.executeQuery();
        rs.next();
        BigDecimal f = rs.getBigDecimal(1);
        double a = f.doubleValue();
        System.out.println(a*2);
        rs.close();
        ps.close();
        con.close();

        QuanLyThucAn quanLyThucAn = new QuanLyThucAn();
        quanLyThucAn.themThucAn();
        System.out.println(quanLyThucAn.toString());
        quanLyThucAn.themThucAn();
        System.out.println(quanLyThucAn.toString());
        quanLyThucAn.hienThi(quanLyThucAn.getDsThucAn());

        QuanLyThucUong quanLyThucUong = new QuanLyThucUong();
        quanLyThucUong.themThucUong();
        System.out.println(quanLyThucUong.toString());
        quanLyThucUong.themThucUong();
        System.out.println(quanLyThucUong.toString());

    }
}
