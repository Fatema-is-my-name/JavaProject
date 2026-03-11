package model;

public class Attendance {
    private int empId;
    private int year;
    private int month;
    private int days;

    public Attendance(int empId, int year, int month, int days) {
        this.empId = empId;
        this.year = year;
        this.month = month;
        this.days = days;
    }

    public int getEmpId() { return empId; }
    public int getYear() { return year; }
    public int getMonth() { return month; }
    public int getDays() { return days; }
}