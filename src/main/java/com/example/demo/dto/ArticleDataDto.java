package com.example.demo.dto;
import lombok.Data;
import java.io.Serializable;

@Data
public class ArticleDataDto implements Serializable {
    private String buildingName;
    private String levelName;
    private String superFamilyName;
    private String familyName;
    private String groupName;
    private String articleName;
    private String articleDescription;
    private double articleQuantity;
}
