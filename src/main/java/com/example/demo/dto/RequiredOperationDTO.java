package com.example.demo.dto;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Data
public class RequiredOperationDTO implements Serializable {

    private Long idRequiredOperation;
    private String nameRequiredOperation;
    private String typeRequiredOperation;
    private String parentRequiredOperation;
    private LocalDate milestoneRequiredOperation;
}
