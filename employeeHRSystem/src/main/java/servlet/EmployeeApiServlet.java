package servlet;

import dao.ReportDAO;
import model.Employee;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.*;
import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet("/EmployeeApiServlet")
public class EmployeeApiServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        try {
            ReportDAO dao = new ReportDAO();

            if ("employeesByDept".equals(action)) {

                String dept = request.getParameter("dept");
                List<Employee> list = dao.getEmployeesByDept(dept);

                JSONArray arr = new JSONArray();

                for (Employee e : list) {
                    JSONObject obj = new JSONObject();
                    obj.put("id", e.getId());
                    obj.put("name", e.getName());
                    obj.put("department", e.getDepartment());
                    obj.put("salary", e.getSalary());
                    arr.put(obj);
                }

                response.getWriter().write(arr.toString());
            }

            else if ("employeeById".equals(action)) {

                int id = Integer.parseInt(request.getParameter("id"));
                Employee e = dao.getEmployeeById(id);

                JSONObject obj = new JSONObject();

                if (e != null) {
                    obj.put("id", e.getId());
                    obj.put("name", e.getName());
                    obj.put("department", e.getDepartment());
                    obj.put("salary", e.getSalary());
                }

                response.getWriter().write(obj.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}