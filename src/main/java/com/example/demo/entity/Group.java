package com.example.demo.entity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name="T_Group")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id_group")
    private Long idGroup;

    @Column(name="name_group")
    private String nameGroup;

    @Column(name="description")
    private String description;

    @Column(name="family")
    private String family;

    @Column(name="super_family")
    private String superFamily;

    @Column(name="speciality")
    private String speciality;

    @OneToMany(mappedBy = "group")
    List<Article> articles = new ArrayList<>();

//    @ManyToMany
//    @JoinTable(
//            name = "groupe_operation",
//            joinColumns = @JoinColumn(name = "id_group"),
//            inverseJoinColumns = @JoinColumn(name = "id_operation"))
//    private List<Operation> operations = new ArrayList<>();
    @OneToMany(mappedBy = "group")
    private List<Group_Operation> groupe_operation= new ArrayList<>();


}
