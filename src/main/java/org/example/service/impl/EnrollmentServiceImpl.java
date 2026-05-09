package org.example.service.impl;
import org.example.model.*;
import org.example.service.IEnrollmentService;
import org.example.service.IStudentService;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentServiceImpl implements IEnrollmentService {
    private List<Department> departments = new ArrayList<>();
    private IStudentService studentService;
    private StudentServiceImpl studentServiceImpl;

    public EnrollmentServiceImpl(IStudentService studentService) {
        this.studentService = studentService;
    }

    public EnrollmentServiceImpl(StudentServiceImpl studentServiceImpl) {
        this.studentServiceImpl = studentServiceImpl;
    }

    @Override
    public void addDepartment(Department dept) {
        departments.add(dept);
        System.out.println("Department added: " + dept.getDepartmentName());
    }

    @Override
    public void addSection(int deptId, Section section) {
        Department dept = findDepartmentById(deptId);
        if (dept != null) {
            dept.getSections().add(section);
            System.out.println("Section " + section.getSectionName() + " added to " + dept.getDepartmentName());
        } else {
            System.out.println("Department not found.");
        }
    }

    @Override
    public void enrollStudentInSection(int studentId, int sectionId) {
        Student student = studentService.findById(studentId);
        if (student == null) { System.out.println("Student not found."); return; }

        Section section = findSectionById(sectionId);
        if (section == null) { System.out.println("Section not found."); return; }

        if (section.isFull()) {
            System.out.printf("ENROLLMENT REJECTED: Section '%s' is full (%d/%d).%n",
                    section.getSectionName(),
                    section.getEnrolledStudents().size(),
                    section.getMaxCapacity());
            return;
        }

        // Check if already enrolled
        boolean alreadyEnrolled = section.getEnrolledStudents().stream()
                .anyMatch(s -> s.getId() == studentId);
        if (alreadyEnrolled) { System.out.println("Student already enrolled in this section."); return; }
//Check Prerequisite Feature
        for (Course course : section.getCourses()) {
            Integer prereqId = course.getPrerequisiteCourseId();
            if (prereqId != null && !studentService.hasPassedCourse(student.getId(), prereqId)) {
                System.out.printf(
                        "ENROLLMENT REJECTED: %s has not passed the prerequisite (Course ID: %d) for '%s'.%n",
                        student.getName(), prereqId, course.getCourseName()
                );
                return;
            }
        }

        section.getEnrolledStudents().add(student);
        System.out.printf("SUCCESS: %s enrolled in section '%s' (%d/%d).%n",
                student.getName(), section.getSectionName(),
                section.getEnrolledStudents().size(), section.getMaxCapacity());
    }

    @Override
    public void viewDepartmentHierarchy(int departmentId) {
        Department dept = findDepartmentById(departmentId);
        if (dept == null) { System.out.println("Department not found."); return; }

        System.out.println("\n========================================");
        System.out.println("Department: " + dept.getDepartmentName());
        System.out.println("========================================");

        if (dept.getSections().isEmpty()) {
            System.out.println("  No sections found.");
            return;
        }

        for (Section sec : dept.getSections()) {
            System.out.printf("%n  [Section] %s | Capacity: %d/%d%n",
                    sec.getSectionName(), sec.getEnrolledStudents().size(), sec.getMaxCapacity());

            Instructor inst = sec.getInstructor();
            System.out.println("  Instructor: " + (inst != null ? inst.getName() : "Not assigned"));

            System.out.println("  Courses:");
            if (sec.getCourses().isEmpty()) System.out.println("    (none)");
            else sec.getCourses().forEach(c ->
                    System.out.printf("    - %s (%s) | %d units%n", c.getCourseName(), c.getCourseCode(), c.getUnits()));

            System.out.println("  Students:");
            if (sec.getEnrolledStudents().isEmpty()) System.out.println("    (none)");
            else sec.getEnrolledStudents().forEach(s ->
                    System.out.printf("    - [%d] %s | %s%n", s.getId(), s.getName(), s.getProgram()));
        }
        System.out.println("========================================\n");
    }

    @Override
    public Department findDepartmentById(int id) {
        return departments.stream().filter(d -> d.getDepartmentId() == id).findFirst().orElse(null);
    }

    @Override
    public Section findSectionById(int id) {
        return departments.stream()
                .flatMap(d -> d.getSections().stream())
                .filter(s -> s.getSectionId() == id)
                .findFirst().orElse(null);
    }

    public List<Department> getAllDepartments() { return departments; }
}