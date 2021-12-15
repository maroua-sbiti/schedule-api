package com.example.demo.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name="T_Article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id_article")
    private Long idArticle;

    @Column(name="reference")
    private String reference;

    @Column(name="description")
    private String description;

    @Column(name="unit")
    private String unit;

    @Column(name="unit_cost")
    private Double unitCost;

    @Column(name="unit_time")
    private Double unitTime;

    @OneToMany(mappedBy = "article")
    @JsonIgnore
    private List<Level_Article> levelArticles = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "id_group", nullable = false)
    private Group group;
}
