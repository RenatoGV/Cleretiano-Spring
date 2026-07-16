package com.rengv.cleretiano.dto;

import java.util.List;

public class TeacherResponseDTO {

    private Long id;
    private String code;
    private String dni;
    private String firstName;
    private String lastName;
    private List<CourseResponseDTO> courses;

    public TeacherResponseDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<CourseResponseDTO> getCourses() {
        return courses;
    }

    public void setCourses(List<CourseResponseDTO> courses) {
        this.courses = courses;
    }
}
