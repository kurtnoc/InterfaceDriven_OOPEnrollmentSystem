package org.example.model;
public class Student extends Person {
    private String program;

    public Student() {}
    public Student(int id, String name, String program) {
        super(id, name);
        this.program = program;
    }

    public String getProgram() { return program; }
    public void setProgram(String p) { this.program = p; }

    @Override
    public String toString() {
        return String.format("Student{id=%d, name='%s', program='%s'}", getId(), getName(), program);
    }
}