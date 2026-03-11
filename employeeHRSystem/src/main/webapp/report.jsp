<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%@ page import="java.util.*, model.Employee" %>

<h2>Department → Employee → Salary</h2>

<%
/*  Because getAttribute returns a generic Object, 
you need to cast it to the actual(original) type which is (TreeMap<String, TreeMap<String, Double>>).
*/

TreeMap<String, TreeMap<String, Double>> deptReport =
    (TreeMap<String, TreeMap<String, Double>>) request.getAttribute("deptReport");
//Pull deptReport
if (deptReport != null) {
    for (String dept : deptReport.keySet()) {
%>
<!--Print Dept name in bold got from deptReport Keys(Dept name) Set  -->
<b><%= dept %></b><br/>

<%
		//Iterate through the inner map (empMap) and print employee name(Key set) and salary(value of key emp(name)).
        TreeMap<String, Double> empMap = deptReport.get(dept);

        for (String emp : empMap.keySet()) {
%>

&nbsp;&nbsp;<%= emp %> : <%= empMap.get(emp) %><br/>

<%
        }
%>
<br/>

<%
    }
}
%>


<h2>Employee Attendance</h2>

<%
HashMap<Employee, Integer> attendanceReport =
    (HashMap<Employee, Integer>) request.getAttribute("attendanceReport");

if (attendanceReport != null) {
    for (Employee e : attendanceReport.keySet()) {
%>

<%= e.getName() %> : <%= attendanceReport.get(e) %> days<br/>

<%
    }
}
%>
</body>
</html>