<!DOCTYPE html>
<html ng-app="hrApp">
<head>

<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.8.2/angular.min.js"></script>

</head>
<body ng-controller="HrController">

<h2>Employee Filter (AngularJS)</h2>

Department:
<select ng-model="selectedDept"
        ng-change="loadEmployees()"
        ng-options="d for d in departments">
<option value="">All</option>
</select>

<br/><br/>

Employee:
<select ng-model="selectedEmpId"
        ng-change="loadEmployeeDetails()"
        ng-options="e.id as e.name for e in employees">
<option value="">All</option>
</select>

<br/><br/>

Name: <input type="text" ng-model="employee.name" readonly><br/>
Dept: <input type="text" ng-model="employee.department" readonly><br/>
Salary: <input type="text" ng-model="employee.salary" readonly><br/>
<button ng-click="showReport()">Show Report</button>
<hr>
<!-- <h3>Report</h3>

<div ng-if="report.length==0">
No data
</div>

<div ng-repeat="r in report">
    <b>{{r.department}}</b><br>
    &nbsp;&nbsp;{{r.name}} : {{r.salary}}<br>
</div> -->
<h3>Report</h3>

<table border="1" ng-if="report.length">
<tr>
<th>Dept</th>
<th>Name</th>
<th>Salary</th>
<th>Attendance</th>
</tr>

<tr ng-repeat="r in report">
<td>{{r.department}}</td>
<td>{{r.name}}</td>
<td>{{r.salary}}</td>
<td>{{r.attendance}}</td>
</tr>
</table>
<script>
var app = angular.module("hrApp", []);

app.controller("HrController", function($scope, $http) {

    $scope.departments = ["HR","IT","Finance","Admin"];
    $scope.employees = [];
    $scope.employee = {};
    $scope.report = [];

    // Load employees by dept OR ALL
    $scope.loadEmployees = function() {

        var url = "EmployeeApiServlet?action=employeesByDept&dept=";

        if ($scope.selectedDept)
            url += "&dept=" + $scope.selectedDept;

        $http.get(url).then(function(res) {
            $scope.employees = res.data;
            $scope.employee = {};
        });
    };

    // Load single employee
    $scope.loadEmployeeDetails = function() {

        if (!$scope.selectedEmpId) {
            $scope.employee = {};
            return;
        }

        $http.get("EmployeeApiServlet?action=employeeById&id=" + $scope.selectedEmpId)
            .then(function(res) {
                $scope.employee = res.data;
            });
    };

    // REPORT
    $scope.showReport = function() {

        var url = "api/report";

        if ($scope.selectedEmpId)
            url = "api/report?action=employeeReport&id=" + $scope.selectedEmpId;
        else if ($scope.selectedDept)
            url = "api/report?action=deptReport&dept=" + $scope.selectedDept;

        $http.get(url).then(function(res) {
            $scope.report = res.data;
        });
    };

});
</script>

</body>
</html>