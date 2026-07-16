package com.rengv.cleretiano.dto;

import jakarta.validation.constraints.NotBlank;

public class SectionRequestDTO {

    @NotBlank
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
