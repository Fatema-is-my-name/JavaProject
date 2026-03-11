package dao;

import model.Employee;
import model.Attendance;

import java.sql.*;
import java.util.*;

public class ReportDAO {

    private String url = "jdbc:mysql://localhost:3306/companydb";
    private String user = "root";
    private String pass = "root";

    private Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url, user, pass);
    }

    // Fetch employees
    public List<Employee> getEmployees() throws Exception {
        List<Employee> list = new ArrayList<>();

        Connection con = getConnection();
        String sql = "SELECT * FROM employees";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            list.add(new Employee(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("department"),
                    rs.getDouble("salary")
            ));
        }

        con.close();
        return list;
    }
    public List<String> getDepartments() throws Exception {
        List<String> list = new ArrayList<>();

        Connection con = getConnection();
        String sql = "SELECT DISTINCT department FROM employees";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) list.add(rs.getString(1));
        con.close();
        return list;
    }

    public List<Employee> getEmployeesByDept(String dept) throws Exception {
        List<Employee> list = new ArrayList<>();

        Connection con = getConnection();
        String sql = "SELECT * FROM employees WHERE department=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, dept);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            list.add(new Employee(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("department"),
                    rs.getDouble("salary")
            ));
        }

        con.close();
        return list;
    }

    public Employee getEmployeeById(int id) throws Exception {
        Connection con = getConnection();
        String sql = "SELECT * FROM employees WHERE id=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return new Employee(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("department"),
                    rs.getDouble("salary")
            );
        }
        return null;
    }

    public List<Employee> getAllEmployees() throws Exception {
        return getEmployees(); // reuse existing
    }
    // Fetch attendance
    public List<Attendance> getAttendance() throws Exception {
        List<Attendance> list = new ArrayList<>();

        Connection con = getConnection();
        String sql = "SELECT * FROM attendance";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            list.add(new Attendance(
                    rs.getInt("emp_id"),
                    rs.getInt("year"),
                    rs.getInt("month"),
                    rs.getInt("days_present")
            ));
        }

        con.close();
        return list;
    }
}