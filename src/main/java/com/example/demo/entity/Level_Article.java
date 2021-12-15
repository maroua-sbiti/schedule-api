package com.example.demo.entity;
import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table(name = "T_Level_Article")
public class Level_Article {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id_level_article")
    private Long idLevelArticle;

    @ManyToOne
    @JoinColumn(name = "id_level")
    private Level level;

    @ManyToOne
    @JoinColumn(name = "id_article")
    private Article article;

    @Column(name = "quantity")
    private double quantity;
}
