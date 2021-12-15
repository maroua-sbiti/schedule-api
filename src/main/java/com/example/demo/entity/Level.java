package com.example.demo.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name="T_Level")
public class Level {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id_level")
    private Long idLevel;

    @Column(name="level_name")
    private String nameLevel;

    @Column(name="starting_availability")
    @Temporal(TemporalType.DATE)
    private Date startingAvailability;

    @Column(name="end_availability")
    @Temporal(TemporalType.DATE)
    private Date endAvailability;

    @Column(name="name_building")
    private String nameBuilding;

    @OneToMany(mappedBy = "level")
    @JsonIgnore
    private List<Level_Article> levelArticles = new ArrayList<>();

}
