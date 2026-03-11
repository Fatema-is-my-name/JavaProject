package services;

import model.Employee;
import model.Attendance;

import java.util.*;

public class ReportService {

    // Dept → Employee → Salary
    public TreeMap<String, TreeMap<String, Double>> deptEmployeeSalary(List<Employee> employees) {

        TreeMap<String, TreeMap<String, Double>> report = new TreeMap<>();

        for (Employee e : employees) {
            report
                .computeIfAbsent(e.getDepartment(), k -> new TreeMap<>())
                .put(e.getName(), e.getSalary());
        }

        return report;
    }

    // Year → Month → Dept → Total Salary
    public TreeMap<Integer, TreeMap<Integer, TreeMap<String, Double>>> salarySummary(List<Employee> employees) {

        TreeMap<Integer, TreeMap<Integer, TreeMap<String, Double>>> report = new TreeMap<>();

        int year = 2025;
        int month = 1;

        for (Employee e : employees) {

            report
                .computeIfAbsent(year, y -> new TreeMap<>())
                .computeIfAbsent(month, m -> new TreeMap<>())
                .merge(e.getDepartment(), e.getSalary(), Double::sum);
        }

        return report;
    }

    // Employee → Attendance Days
    public HashMap<Employee, Integer> employeeAttendance(List<Employee> employees, List<Attendance> attendanceList) {

        HashMap<Integer, Employee> empIndex = new HashMap<>();
        for (Employee e : employees) {
            empIndex.put(e.getId(), e);
        }

        HashMap<Employee, Integer> result = new HashMap<>();

        for (Attendance a : attendanceList) {
            Employee e = empIndex.get(a.getEmpId());
            result.put(e, a.getDays());
        }

        return result;
    }
}