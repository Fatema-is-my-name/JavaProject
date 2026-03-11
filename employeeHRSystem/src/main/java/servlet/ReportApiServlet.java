package servlet;

import dao.ReportDAO;
import model.Employee;
import model.Attendance;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet("/api/report")
public class ReportApiServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        JSONArray result = new JSONArray();

        try {
            ReportDAO dao = new ReportDAO();

            List<Employee> employees = dao.getEmployees();
            List<Attendance> attendance = dao.getAttendance();

            // Dept → Employee → Salary
            TreeMap<String, TreeMap<String, Double>> deptReport = new TreeMap<>();

            for (Employee e : employees) {
                deptReport
                        .computeIfAbsent(e.getDepartment(), k -> new TreeMap<>())
                        .put(e.getName(), e.getSalary());
            }

            // Employee → Attendance
            HashMap<Integer, Employee> empIndex = new HashMap<>();
            for (Employee e : employees) empIndex.put(e.getId(), e);

            HashMap<Employee, Integer> attendanceReport = new HashMap<>();

            for (Attendance a : attendance) {
                Employee e = empIndex.get(a.getEmpId());
                if (e != null)
                    attendanceReport.put(e, a.getDays());
            }

            // Convert collections → JSON
            for (String dept : deptReport.keySet()) {

                TreeMap<String, Double> empMap = deptReport.get(dept);

                for (String empName : empMap.keySet()) {

                    JSONObject obj = new JSONObject();
                    obj.put("department", dept);
                    obj.put("name", empName);
                    obj.put("salary", empMap.get(empName));

                    // attendance lookup
                    for (Employee e : attendanceReport.keySet()) {
                        if (e.getName().equals(empName)) {
                            obj.put("attendance", attendanceReport.get(e));
                            break;
                        }
                    }

                    result.put(obj);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        response.getWriter().write(result.toString());
    }
}