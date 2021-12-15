package com.example.demo.dto;
import lombok.Data;
import org.apache.tomcat.jni.Local;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
public class ScheduleDataDto implements Serializable {

    private String buildingName;
    private String levelName;
    private String superFamilyName;
    private String parent;
    private String operationName;
    private List<RequiredOperationDTO> requiredOperations;
    private Double taskDuration;
    private LocalDate predictedStart;
    private LocalDate predictedEnd;
    private LocalDate levelStartAvailability;
    private LocalDate levelEndAvailability;



}
