package org.example.service.impl;
import org.example.model.Instructor;
import org.example.model.Section;
import org.example.service.IInstructorService;
import java.util.ArrayList;
import java.util.List;

public class InstructorServiceImpl implements IInstructorService {
    private List<Instructor> instructors = new ArrayList<>();

    @Override
    public void addInstructor(Instructor i) {
        instructors.add(i);
        System.out.println("Instructor added: " + i.getName());
    }

    @Override
    public void assignInstructorToSection(int instructorId, Section section) {
        Instructor inst = getInstructorDetails(instructorId);
        if (inst != null) {
            section.setInstructor(inst);
            System.out.println("Assigned " + inst.getName() + " to section " + section.getSectionName());
        } else {
            System.out.println("Instructor not found.");
        }
    }

    @Override
    public Instructor getInstructorDetails(int id) {
        return instructors.stream().filter(i -> i.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void removeInstructor(int id) {
        instructors.removeIf(i -> i.getId() == id);
        System.out.println("Instructor removed.");
    }

    @Override
    public List<Instructor> getAllInstructors() { return instructors; }
}