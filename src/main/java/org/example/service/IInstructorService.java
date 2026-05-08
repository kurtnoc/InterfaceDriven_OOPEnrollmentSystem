package org.example.service;
import org.example.model.Instructor;
import org.example.model.Section;

public interface IInstructorService {
    void addInstructor(Instructor instructor);
    void assignInstructorToSection(int instructorId, Section section);
    Instructor getInstructorDetails(int instructorId);
    void removeInstructor(int instructorId);
    java.util.List<Instructor> getAllInstructors();
}