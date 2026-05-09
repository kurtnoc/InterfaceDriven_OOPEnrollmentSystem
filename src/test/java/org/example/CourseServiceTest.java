
package org.example;

import org.example.model.Course;
import org.example.service.impl.CourseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CourseServiceTest {
    private CourseServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new CourseServiceImpl();
    }

    @Test
    void addCourse_shouldIncreaseListSize() {
        service.addCourse(new Course(1, "INTEPROG", "Integrative Programming", "BSIT", 3, null));
        assertEquals(1, service.getAllCourses().size());
    }

    @Test
    void findById_shouldReturnCorrectCourse() {
        service.addCourse(new Course(1, "INTEPROG", "Integrative Programming", "BSIT", 3, null));
        Course c = service.findById(1);
        assertNotNull(c);
        assertEquals("Integrative Programming", c.getCourseName());
    }

    @Test
    void findById_nonExistent_shouldReturnNull() {
        assertNull(service.findById(999));
    }

    @Test
    void updateCourse_shouldChangeCourseName() {
        service.addCourse(new Course(1, "INTEPROG", "Old Name", "BSIT", 3, null));
        service.updateCourse(new Course(1, "INTEPROG", "New Name", "BSIT", 3, null));
        assertEquals("New Name", service.findById(1).getCourseName());
    }

    @Test
    void removeCourse_shouldDecreaseListSize() {
        service.addCourse(new Course(1, "INTEPROG", "Integrative Programming", "BSIT", 3, null));
        service.removeCourse(1);
        assertEquals(0, service.getAllCourses().size());
    }

    @Test
    void course_withPrerequisite_shouldStorePrereqId() {
        service.addCourse(new Course(2, "DISMATH", "Discrete Math", "BSIT", 3, 1));
        assertEquals(1, service.findById(2).getPrerequisiteCourseId());
    }

    @Test
    void course_withNoPrerequisite_prereqIdShouldBeNull() {
        service.addCourse(new Course(1, "INTEPROG", "Integrative Programming", "BSIT", 3, null));
        assertNull(service.findById(1).getPrerequisiteCourseId());
    }
}
