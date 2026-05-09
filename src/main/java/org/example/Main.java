
package org.example;

import org.example.model.*;
import org.example.service.impl.*;

public class Main {
    static StudentServiceImpl    studentService    = new StudentServiceImpl();
    static InstructorServiceImpl instructorService = new InstructorServiceImpl();
    static CourseServiceImpl     courseService     = new CourseServiceImpl();
    static EnrollmentServiceImpl enrollmentService = new EnrollmentServiceImpl(studentService);
    static TuitionServiceImpl    tuitionService    = new TuitionServiceImpl(studentService, enrollmentService);

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            System.out.println("\n======================================");
            System.out.println("===  INTERFACE-DRIVEN ENROLLMENT   ===");
            System.out.println("======================================");
            System.out.println("[0] Exit");
            System.out.println("[1] Student Management");
            System.out.println("[2] Instructor Management");
            System.out.println("[3] Course Management");
            System.out.println("[4] Department & Section Management");
            System.out.println("[5] Enrollment");
            System.out.println("[6] Tuition Fee Management");
            switch (readInt("Choice: ")) {
                case 1 -> studentMenu();
                case 2 -> instructorMenu();
                case 3 -> courseMenu();
                case 4 -> departmentMenu();
                case 5 -> enrollmentMenu();
                case 6 -> tuitionMenu();
                case 0 -> { System.out.println("Goodbye!"); running = false; }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    // ── STUDENT MENU ──────────────────────────────────────
    static void studentMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n=== STUDENT MANAGEMENT ===");
            System.out.println("[0] Back  [1] Add  [2] View All  [3] Update  [4] Remove  [5] Mark Course Passed");
            switch (readInt("Choice: ")) {
                case 1 -> {
                    int id       = readInt("ID: ");
                    String name  = readLine("Name: ");
                    String prog  = readLine("Program: ");
                    studentService.addStudent(new Student(id, name, prog));
                }
                case 2 -> {
                    var list = studentService.getAllStudents();
                    if (list.isEmpty()) System.out.println("No students.");
                    else list.forEach(System.out::println);
                }
                case 3 -> {
                    int id       = readInt("ID to update: ");
                    String name  = readLine("New Name: ");
                    String prog  = readLine("New Program: ");
                    studentService.updateStudent(new Student(id, name, prog));
                }
                case 4 -> studentService.removeStudent(readInt("ID to remove: "));
                case 5 -> {
                    int sid = readInt("Student ID: ");
                    int cid = readInt("Course ID they passed: ");
                    studentService.markCoursePassed(sid, cid);
                }
                case 0 -> back = true;
                default -> System.out.println("Invalid.");
            }
        }
    }

    // ── INSTRUCTOR MENU ───────────────────────────────────
    static void instructorMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n=== INSTRUCTOR MANAGEMENT ===");
            System.out.println("[0] Back  [1] Add  [2] View All  [3] Assign to Section  [4] Remove");
            switch (readInt("Choice: ")) {
                case 1 -> {
                    int id       = readInt("ID: ");
                    String name  = readLine("Name: ");
                    String dept  = readLine("Department: ");
                    instructorService.addInstructor(new Instructor(id, name, dept));
                }
                case 2 -> {
                    var list = instructorService.getAllInstructors();
                    if (list.isEmpty()) System.out.println("No instructors.");
                    else list.forEach(System.out::println);
                }
                case 3 -> {
                    int iid      = readInt("Instructor ID: ");
                    int sid      = readInt("Section ID: ");
                    Section sec  = enrollmentService.findSectionById(sid);
                    if (sec == null) System.out.println("Section not found.");
                    else instructorService.assignInstructorToSection(iid, sec);
                }
                case 4 -> instructorService.removeInstructor(readInt("ID to remove: "));
                case 0 -> back = true;
                default -> System.out.println("Invalid.");
            }
        }
    }

    // ── COURSE MENU ───────────────────────────────────────
    static void courseMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n=== COURSE MANAGEMENT ===");
            System.out.println("[0] Back  [1] Add  [2] View All  [3] Update  [4] Remove  [5] Assign to Section");
            switch (readInt("Choice: ")) {
                case 1 -> {
                    int id       = readInt("ID: ");
                    String code  = readLine("Code: ");
                    String name  = readLine("Name: ");
                    String prog  = readLine("Program: ");
                    int units    = readInt("Units: ");
                    int prereq   = readInt("Prerequisite Course ID (0 = none): ");
                    courseService.addCourse(new Course(id, code, name, prog, units,
                            prereq == 0 ? null : prereq));
                }
                case 2 -> {
                    var list = courseService.getAllCourses();
                    if (list.isEmpty()) System.out.println("No courses.");
                    else list.forEach(System.out::println);
                }
                case 3 -> {
                    int id       = readInt("ID to update: ");
                    String code  = readLine("New Code: ");
                    String name  = readLine("New Name: ");
                    String prog  = readLine("New Program: ");
                    int units    = readInt("New Units: ");
                    int prereq   = readInt("Prerequisite Course ID (0 = none): ");
                    courseService.updateCourse(new Course(id, code, name, prog, units,
                            prereq == 0 ? null : prereq));
                }
                case 4 -> courseService.removeCourse(readInt("ID to remove: "));
                case 5 -> {
                    int cid  = readInt("Course ID: ");
                    int sid  = readInt("Section ID: ");
                    Course c = courseService.findById(cid);
                    Section s = enrollmentService.findSectionById(sid);
                    if (c == null || s == null) System.out.println("Course or Section not found.");
                    else { s.getCourses().add(c); System.out.println("Course assigned to section."); }
                }
                case 0 -> back = true;
                default -> System.out.println("Invalid.");
            }
        }
    }

    // ── DEPARTMENT & SECTION MENU ─────────────────────────
    static void departmentMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n=== DEPARTMENT & SECTION MANAGEMENT ===");
            System.out.println("[0] Back  [1] Add Department  [2] Add Section  [3] View Hierarchy");
            switch (readInt("Choice: ")) {
                case 1 -> {
                    int id       = readInt("Department ID: ");
                    String name  = readLine("Department Name: ");
                    enrollmentService.addDepartment(new Department(id, name));
                }
                case 2 -> {
                    int deptId   = readInt("Department ID: ");
                    int secId    = readInt("Section ID: ");
                    String sname = readLine("Section Name (e.g. BSIT-1A): ");
                    int cap      = readInt("Max Capacity: ");
                    enrollmentService.addSection(deptId, new Section(secId, sname, cap));
                }
                case 3 -> enrollmentService.viewDepartmentHierarchy(readInt("Department ID: "));
                case 0 -> back = true;
                default -> System.out.println("Invalid.");
            }
        }
    }

    // ── ENROLLMENT MENU ───────────────────────────────────
    static void enrollmentMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n=== ENROLLMENT ===");
            System.out.println("[0] Back  [1] Enroll Student in Section  [2] View Department Hierarchy");
            switch (readInt("Choice: ")) {
                case 1 -> {
                    int sid   = readInt("Student ID: ");
                    int secId = readInt("Section ID: ");
                    enrollmentService.enrollStudentInSection(sid, secId);
                }
                case 2 -> enrollmentService.viewDepartmentHierarchy(readInt("Department ID: "));
                case 0 -> back = true;
                default -> System.out.println("Invalid.");
            }
        }
    }

    // ── TUITION MENU ──────────────────────────────────────
    static void tuitionMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n=== TUITION FEE MANAGEMENT ===");
            System.out.println("[0] Back  [1] Calculate Fee  [2] Make Payment  [3] View Balance  [4] Grant Scholarship");
            switch (readInt("Choice: ")) {
                case 1 -> {
                    int sid      = readInt("Student ID: ");
                    double rate  = readDouble("Rate per unit in PHP (0 = use default PHP 500.00): ");
                    if (rate <= 0) rate = 500.0;
                    tuitionService.calculateFee(sid, rate);
                }
                case 2 -> {
                    int sid      = readInt("Student ID: ");
                    double amt   = readDouble("Amount to pay (PHP): ");
                    tuitionService.makePayment(sid, amt);
                }
                case 3 -> tuitionService.getRemainingBalance(readInt("Student ID: "));
                case 4 -> tuitionService.grantScholarship(readInt("Student ID to grant scholarship: "));
                case 0 -> back = true;
                default -> System.out.println("Invalid.");
            }
        }
    }

    // ── INPUT HELPERS ─────────────────────────────────────
    static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(new java.util.Scanner(System.in).nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("  [!] Invalid input. Please enter a whole number.");
            }
        }
    }

    static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(new java.util.Scanner(System.in).nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("  [!] Invalid input. Please enter a number (e.g. 1500.00).");
            }
        }
    }

    static String readLine(String prompt) {
        System.out.print(prompt);
        return new java.util.Scanner(System.in).nextLine().trim();
    }
}