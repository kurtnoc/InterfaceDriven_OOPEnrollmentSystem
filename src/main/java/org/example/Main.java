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
