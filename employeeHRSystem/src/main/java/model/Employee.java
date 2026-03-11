package model;

public class Employee {
    private int id;
    private String name;
    private String department;
    private double salary;

    public Employee(int id, String name, String department, double salary) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.salary = salary;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public double getSalary() { return salary; }
    
    //required for custom object key
	/*
	 * This is EXACTLY what makes:
	 * 
	 * HashMap<Employee, ...>
	 */
    //- hash code of an Employee object is just its id.
    @Override
    public int hashCode() { return id; }
	/*
	 * For large or negative IDs
	 * @Override public int hashCode() { return Objects.hash(id); }
	 */

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Employee)) return false;
        //- If o is instance of Employee,It compares the id of the current object with the id of the other Employee.
        return id == ((Employee)o).id;
    }
}