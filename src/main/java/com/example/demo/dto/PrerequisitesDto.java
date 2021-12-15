package com.example.demo.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class PrerequisitesDto implements Serializable {

    private String operation;
    private String parent;
    private LocalDate prerequisiteMilestone;

}
