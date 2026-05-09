
package org.example;

import org.example.model.Instructor;
import org.example.model.Section;
import org.example.service.impl.InstructorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InstructorServiceTest {
    private InstructorServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new InstructorServiceImpl();
        service.addInstructor(new Instructor(1, "Prof. Santos", "CCS"));
    }

    @Test
    void addInstructor_shouldIncreaseListSize() {
        assertEquals(1, service.getAllInstructors().size());
    }

    @Test
    void getInstructorDetails_shouldReturnCorrectInstructor() {
        Instructor inst = service.getInstructorDetails(1);
        assertNotNull(inst);
        assertEquals("Prof. Santos", inst.getName());
    }

    @Test
    void getInstructorDetails_nonExistent_shouldReturnNull() {
        assertNull(service.getInstructorDetails(999));
    }

    @Test
    void removeInstructor_shouldDecreaseListSize() {
        service.removeInstructor(1);
        assertEquals(0, service.getAllInstructors().size());
    }

    @Test
    void assignInstructorToSection_shouldSetInstructorOnSection() {
        Section sec = new Section(1, "BSIT-1A", 30);
        service.assignInstructorToSection(1, sec);
        assertNotNull(sec.getInstructor());
        assertEquals("Prof. Santos", sec.getInstructor().getName());
    }

    @Test
    void assignInstructorToSection_nonExistentInstructor_sectionInstructorShouldRemainNull() {
        Section sec = new Section(1, "BSIT-1A", 30);
        service.assignInstructorToSection(999, sec);
        assertNull(sec.getInstructor());
    }
}