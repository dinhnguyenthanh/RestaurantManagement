import ConnectDB.DBC;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class HoaDon {
    private GregorianCalendar ngayTaoHoaDon;
    private static final Scanner scanner = new Scanner(System.in);

    public HoaDon(GregorianCalendar ngayTaoHoaDon){
        java.util.Date utilDate = new java.util.Date();
        java.sql.Date dt = new java.sql.Date(utilDate.getTime());
        java.util.GregorianCalendar cal = new java.util.GregorianCalendar();
        cal.setTime(dt);
        this.ngayTaoHoaDon = ngayTaoHoaDon;
    }

    public HoaDon(){

    }

    public int nhapID(){
        System.out.print("Nhập ID:");
        int id = scanner.nextInt();scanner.nextLine();
        return id;
    }

    public String layThu(int id) throws SQLException, ParseException {
        DBC con = new DBC();
        String sql = "select ngayThue from danhsachdondattiec WHERE ID = ?;";
        PreparedStatement ps = con.getConnection().prepareStatement(sql);
        ps.setInt(1,id);
        ResultSet rs = ps.executeQuery();
        rs.next();
        String kq = rs.getString(1);
        Date d = new SimpleDateFormat("dd/MM/yyyy").parse(kq);
        java.util.GregorianCalendar thu = new java.util.GregorianCalendar();
        thu.setTime(d);
        // Getting the day of the week
        String t = "";
        switch (thu.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.SUNDAY -> {
                t = "chuNhat";
            }
            case Calendar.MONDAY -> {
                t = "thuHai";
            }
            case Calendar.TUESDAY -> {
                t = "thuBa";
            }
            case Calendar.WEDNESDAY -> {
                t = "thuTu";
            }
            case Calendar.THURSDAY -> {
                t = "thuNam";
            }
            case Calendar.FRIDAY -> {
                t = "thuSau";
            }
            case Calendar.SATURDAY -> {
                t = "thuBay";
            }
        }
        con.close();
        return t;
    }

    public String layThoiDiem(int id) throws SQLException {
        DBC con = new DBC();
        String sql = "select thoiDiem from danhsachdondattiec WHERE ID = ?;";
        PreparedStatement ps = con.getConnection().prepareStatement(sql);
        ps.setInt(1,id);
        scanner.nextLine();
        ResultSet rs = ps.executeQuery();
        rs.next();
        String kq = rs.getString(1);
        con.close();
        return kq;
    }

    public double layHeSoGia(String thoiDiem, int id) throws SQLException, ParseException {
        DBC con = new DBC();
        String sql = "Select "+layThu(id)+" from hesogia where buoi = ?;";
        PreparedStatement ps = con.getConnection().prepareStatement(sql);
        ps.setString(1,layThoiDiem(id));
        ResultSet rs = ps.executeQuery();
        rs.next();
        double kq = rs.getDouble(1);
        rs.close();
        ps.close();
        con.close();
        return kq;
    }

    public BigDecimal tongGiaTienHoaDon(int id) throws SQLException, ParseException {
        double tong = 0;
        //
        DBC con = new DBC();
        String sql = "select donGiaSanh from danhsachdondattiec WHERE ID = ?;";
        PreparedStatement ps = con.getConnection().prepareStatement(sql);
        ps.setInt(1,id);
        ResultSet rs = ps.executeQuery();
        rs.next();
        BigDecimal f= rs.getBigDecimal(1);
        double a = f.doubleValue();
        tong+=a*layHeSoGia(layThoiDiem(id),id);

        String sql1 = "select donGiaMenu from danhsachdondattiec WHERE ID = ?;";
        PreparedStatement ps1 = con.getConnection().prepareStatement(sql);
        ps1.setInt(1,id);
        ResultSet rs1 = ps1.executeQuery();
        rs1.next();
        BigDecimal f1= rs1.getBigDecimal(1);
        double a1 = f1.doubleValue();
        tong+=a1;
        rs.close();
        ps.close();
        con.close();
        return BigDecimal.valueOf(tong);
    }

    public void xuatHoaDon() throws SQLException, ParseException {
        DBC con = new DBC();

        QuanLyDonDatTiec quanLyDonDatTiec = new QuanLyDonDatTiec();
        quanLyDonDatTiec.hienThiCSDL();
        System.out.print("\nNhập ID:");
        int id = scanner.nextInt();scanner.nextLine();
        BigDecimal tongTien = tongGiaTienHoaDon(id);
        String sql = "SELECT * FROM test.danhsachdondattiec WHERE ID = ?;";
        PreparedStatement ps = con.getConnection().prepareStatement(sql);
        ps.setInt(1,id);
        ResultSet rs = ps.executeQuery();
        System.out.printf("\nNgày: %s",this.ngayTaoHoaDon);
        System.out.printf("\n%15s","Tên buổi tiệc");
        System.out.printf("%15s","Thời điểm");
        System.out.printf("%15s","Ngày thuê");
        System.out.printf("%15s","Giá sảnh");
        System.out.printf("%15s","Tên Menu");
        System.out.printf("%15s\n","Giá Menu");
        while (rs.next()){
            System.out.printf("\n%15s",rs.getString("tenBuoiTiec"));
            System.out.printf("%15s",rs.getString("thoiDiem"));
            System.out.printf("%15s",rs.getString("ngayThue"));
            System.out.printf("%,15.0f",rs.getBigDecimal("donGiaSanh"));
            System.out.printf("%15s",rs.getString("tenMenu"));
            System.out.printf("%,15.0f\n",rs.getBigDecimal("donGiaMenu"));
        }
        rs.close();
        ps.close();
        con.close();
        System.out.printf("\n===> TỔNG THÀNH TIỀN: %,.0f VND\n",tongTien.doubleValue());
    }

    public GregorianCalendar getNgayTaoHoaDon() {
        return ngayTaoHoaDon;
    }
    public void setNgayTaoHoaDon(GregorianCalendar ngayTaoHoaDon) {
        this.ngayTaoHoaDon = ngayTaoHoaDon;
    }
}
