
package org.example.model;
public class Instructor extends Person {
    private String department;

    public Instructor() {}
    public Instructor(int id, String name, String department) {
        super(id, name);
        this.department = department;
    }

    public String getDepartment() { return department; }
    public void setDepartment(String d) { this.department = d; }

    @Override
    public String toString() {
        return String.format("Instructor{id=%d, name='%s', department='%s'}", getId(), getName(), department);
    }
}