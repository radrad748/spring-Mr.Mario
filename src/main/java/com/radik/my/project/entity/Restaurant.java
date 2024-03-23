package com.radik.my.project.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "restaurant")
@Data
@ToString
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50, unique = true, nullable = false)
    private String name;
    @Column(length = 50, nullable = false)
    private String number;
    @Column(length = 50, nullable = false)
    private String address;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "restaurant_id")
    private List<Comment> comments;
    //private List<Menu> menu;

}
