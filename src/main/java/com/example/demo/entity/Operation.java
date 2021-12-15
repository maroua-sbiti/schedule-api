package com.example.demo.entity;
import lombok.Data;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "T_Operation")
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_operation")
    private Long idOperation;

    @Column(name = "operation")
    private String operation;

    @Column(name = "parent")
    private String parent;

    @Column(name="operationType")
    private Character operationType;

    @Column(name = "prerequisiteMilestone")
    private LocalDate prerequisiteMilestone;

    @OneToMany(mappedBy = "operation")
    private List<Group_Operation> groupOperations = new ArrayList<>();

    @ManyToMany()
    @JoinTable(
            name = "T_operation_prerequisite",
            joinColumns = @JoinColumn(name = "id_operation"),
            inverseJoinColumns = @JoinColumn(name = "id_operation_prerequisite"))
    private List<Operation> prerequisite;

    @ManyToMany(mappedBy = "prerequisite")
    private List<Operation> prerequisites = new ArrayList<>();

}
