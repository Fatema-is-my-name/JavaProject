package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class NewDAO {
    private String url = "jdbc:mysql://localhost:3306/companydb";
    private String user = "root";
    private String pass = "root";

    private Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url, user, pass);
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
}
