package org.example.service;
import org.example.model.*;

public interface IEnrollmentService {
    void enrollStudentInSection(int studentId, int sectionId);
    void viewDepartmentHierarchy(int departmentId);
    void addDepartment(Department department);
    void addSection(int departmentId, Section section);
    Department findDepartmentById(int id);
    Section findSectionById(int id);
}