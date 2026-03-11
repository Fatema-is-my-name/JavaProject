package servlet;

import dao.ReportDAO;
import model.Employee;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.*;

@WebServlet("/FilterServlet")
public class FilterServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            ReportDAO dao = new ReportDAO();

            String dept = request.getParameter("dept");
            String empIdStr = request.getParameter("empId");

            List<String> departments = dao.getDepartments();
            request.setAttribute("departments", departments);

            List<Employee> employees = null;
            Employee selectedEmployee = null;

            if (dept != null && !dept.isEmpty()) {
                employees = dao.getEmployeesByDept(dept);
            } else {
                employees = dao.getAllEmployees();
            }

            if (empIdStr != null && !empIdStr.isEmpty()) {
                int empId = Integer.parseInt(empIdStr);
                selectedEmployee = dao.getEmployeeById(empId);
            }

            request.setAttribute("employees", employees);
            request.setAttribute("selectedEmployee", selectedEmployee);
            request.setAttribute("selectedDept", dept);

        } catch (Exception e) {
            e.printStackTrace();
        }

        request.getRequestDispatcher("filter.jsp").forward(request, response);
    }
}