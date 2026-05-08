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