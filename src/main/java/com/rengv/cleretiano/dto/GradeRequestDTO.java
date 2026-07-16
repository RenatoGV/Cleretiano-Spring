package com.rengv.cleretiano.dto;

import jakarta.validation.constraints.NotBlank;

public class GradeRequestDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String level;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
