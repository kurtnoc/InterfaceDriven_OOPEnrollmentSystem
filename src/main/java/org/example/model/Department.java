package org.example.model;
import java.util.ArrayList;
import java.util.List;

public class Department {
    private int departmentId;
    private String departmentName;
    private List<Section> sections = new ArrayList<>();

    public Department() {}
    public Department(int departmentId, String departmentName) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
    }

    public int getDepartmentId() { return departmentId; }
    public void setDepartmentId(int id) { this.departmentId = id; }
    public String getDepartmentName() { return departmentName; }
    public void setDepartmentName(String n) { this.departmentName = n; }
    public List<Section> getSections() { return sections; }

    @Override
    public String toString() {
        return String.format("Department{id=%d, name='%s', sections=%d}",
                departmentId, departmentName, sections.size());
    }
}