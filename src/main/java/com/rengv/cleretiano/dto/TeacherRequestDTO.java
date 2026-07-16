package com.rengv.cleretiano.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class TeacherRequestDTO {

    @NotBlank
    private String code;

    @NotBlank
    private String dni;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private List<Long> courseIds;

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

    public List<Long> getCourseIds() {
        return courseIds;
    }

    public void setCourseIds(List<Long> courseIds) {
        this.courseIds = courseIds;
    }
}
