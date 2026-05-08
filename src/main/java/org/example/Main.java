package org.example;

import org.example.model.*;
import org.example.service.*;
import org.example.service.impl.*;
import java.util.Scanner;

public class Main {
    static Scanner scan = new Scanner(System.in);
    static IStudentService studentService = new StudentServiceImpl();
    static IInstructorService instructorService = new InstructorServiceImpl();
    static ICourseService courseService = new CourseServiceImpl();
    static IEnrollmentService enrollmentService = new EnrollmentServiceImpl(studentService);
    static ITuitionService tuitionService = new TuitionServiceImpl(studentService, enrollmentService);

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
            System.out.print("Choice: ");
            int choice = scan.nextInt();

            switch (choice) {
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
            System.out.println("[0] Back  [1] Add  [2] View All  [3] Update  [4] Remove");
            System.out.print("Choice: ");
            switch (scan.nextInt()) {
                case 1 -> {
                    System.out.print("ID: "); int id = scan.nextInt(); scan.nextLine();
                    System.out.print("Name: "); String name = scan.nextLine();
                    System.out.print("Program: "); String prog = scan.nextLine();
                    studentService.addStudent(new Student(id, name, prog));
                }
                case 2 -> {
                    var list = studentService.getAllStudents();
                    if (list.isEmpty()) System.out.println("No students.");
                    else list.forEach(System.out::println);
                }
                case 3 -> {
                    System.out.print("ID to update: "); int id = scan.nextInt(); scan.nextLine();
                    System.out.print("New Name: "); String name = scan.nextLine();
                    System.out.print("New Program: "); String prog = scan.nextLine();
                    studentService.updateStudent(new Student(id, name, prog));
                }
                case 4 -> {
                    System.out.print("ID to remove: ");
                    studentService.removeStudent(scan.nextInt());
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
            System.out.print("Choice: ");
            switch (scan.nextInt()) {
                case 1 -> {
                    System.out.print("ID: "); int id = scan.nextInt(); scan.nextLine();
                    System.out.print("Name: "); String name = scan.nextLine();
                    System.out.print("Department: "); String dept = scan.nextLine();
                    instructorService.addInstructor(new Instructor(id, name, dept));
                }
                case 2 -> {
                    var list = instructorService.getAllInstructors();
                    if (list.isEmpty()) System.out.println("No instructors.");
                    else list.forEach(System.out::println);
                }
                case 3 -> {
                    System.out.print("Instructor ID: "); int iid = scan.nextInt();
                    System.out.print("Section ID: "); int sid = scan.nextInt();
                    Section sec = enrollmentService.findSectionById(sid);
                    if (sec == null) System.out.println("Section not found.");
                    else instructorService.assignInstructorToSection(iid, sec);
                }
                case 4 -> {
                    System.out.print("ID to remove: ");
                    instructorService.removeInstructor(scan.nextInt());
                }
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
            System.out.print("Choice: ");
            switch (scan.nextInt()) {
                case 1 -> {
                    System.out.print("ID: "); int id = scan.nextInt(); scan.nextLine();
                    System.out.print("Code: "); String code = scan.nextLine();
                    System.out.print("Name: "); String name = scan.nextLine();
                    System.out.print("Program: "); String prog = scan.nextLine();
                    System.out.print("Units: "); int units = scan.nextInt();
                    courseService.addCourse(new Course(id, code, name, prog, units));
                }
                case 2 -> {
                    var list = courseService.getAllCourses();
                    if (list.isEmpty()) System.out.println("No courses.");
                    else list.forEach(System.out::println);
                }
                case 3 -> {
                    System.out.print("ID to update: "); int id = scan.nextInt(); scan.nextLine();
                    System.out.print("New Code: "); String code = scan.nextLine();
                    System.out.print("New Name: "); String name = scan.nextLine();
                    System.out.print("New Program: "); String prog = scan.nextLine();
                    System.out.print("New Units: "); int units = scan.nextInt();
                    courseService.updateCourse(new Course(id, code, name, prog, units));
                }
                case 4 -> {
                    System.out.print("ID to remove: ");
                    courseService.removeCourse(scan.nextInt());
                }
                case 5 -> {
                    System.out.print("Course ID: "); int cid = scan.nextInt();
                    System.out.print("Section ID: "); int sid = scan.nextInt();
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
            System.out.print("Choice: ");
            switch (scan.nextInt()) {
                case 1 -> {
                    System.out.print("Department ID: "); int id = scan.nextInt(); scan.nextLine();
                    System.out.print("Department Name: "); String name = scan.nextLine();
                    enrollmentService.addDepartment(new Department(id, name));
                }
                case 2 -> {
                    System.out.print("Department ID: "); int deptId = scan.nextInt();
                    System.out.print("Section ID: "); int secId = scan.nextInt(); scan.nextLine();
                    System.out.print("Section Name (e.g. BSIT-1A): "); String sname = scan.nextLine();
                    System.out.print("Max Capacity: "); int cap = scan.nextInt();
                    enrollmentService.addSection(deptId, new Section(secId, sname, cap));
                }
                case 3 -> {
                    System.out.print("Department ID: ");
                    enrollmentService.viewDepartmentHierarchy(scan.nextInt());
                }
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
            System.out.print("Choice: ");
            switch (scan.nextInt()) {
                case 1 -> {
                    System.out.print("Student ID: "); int sid = scan.nextInt();
                    System.out.print("Section ID: "); int secId = scan.nextInt();
                    enrollmentService.enrollStudentInSection(sid, secId);
                }
                case 2 -> {
                    System.out.print("Department ID: ");
                    enrollmentService.viewDepartmentHierarchy(scan.nextInt());
                }
                case 0 -> back = true;
                default -> System.out.println("Invalid.");
            }
        }
    }
