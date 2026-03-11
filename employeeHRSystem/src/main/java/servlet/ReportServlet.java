package servlet;

import dao.ReportDAO;
import model.Employee;
import model.Attendance;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.*;

@WebServlet("/ReportServlet")
public class ReportServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            ReportDAO dao = new ReportDAO();

            List<Employee> employees = dao.getEmployees();
            List<Attendance> attendance = dao.getAttendance();
            
            //SALARIES GROUPED BY DEPT
            // Dept → Employee → Salary
            //Outer Treemap- keyed by department name.
            //Inner Treemap - - keyed by employee name, with the value being their salary.
            TreeMap<String, TreeMap<String, Double>> deptReport = new TreeMap<>();

            for (Employee e : employees) {
                deptReport
                		//If that dept absent, create new treemap for key "that dept" 
                        .computeIfAbsent(e.getDepartment(), k -> new TreeMap<>())
                        .put(e.getName(), e.getSalary());
            }
			/*
			- Alice (HR, 50k)
			- Bob (HR, 60k)
			- Carol (IT, 70k)
			deptReport{
				  "HR" = { "Alice"=50000.0, "Bob"=60000.0 },
				  "IT" = { "Carol"=70000.0 }
				}	*/
            
            //Looks up employees by their id from the attendance list, then mapping them to their attendance days.
            // Employee → Attendance
            //Given an empId, you can retrieve the corresponding Employee.
            HashMap<Integer, Employee> empIndex = new HashMap<>();
            for (Employee e : employees) empIndex.put(e.getId(), e);
            
            //Using the Employee object as the key in attendanceReport.
            HashMap<Employee, Integer> attendanceReport = new HashMap<>();

            for (Attendance a : attendance) {
                Employee e = empIndex.get(a.getEmpId());
                if (e != null)
                    attendanceReport.put(e, a.getDays());
            }

            request.setAttribute("deptReport", deptReport);
            request.setAttribute("attendanceReport", attendanceReport);

        } catch (Exception e) {
            e.printStackTrace();
        }

        request.getRequestDispatcher("report.jsp").forward(request, response);
    }
}