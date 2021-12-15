package com.example.demo.entity;
import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table(name = "T_Group_Operation")
public class Group_Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name= "id_group_operation")
    private Long idGroupOperation;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_group")
    private Group group;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_operation")
    private Operation operation;

    @Column(name = "rate")
    private Double rate;

}
