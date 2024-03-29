package com.radik.my.project.entity;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "menu")
@Data
@ToString
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50, nullable = false)
    private String name;
    @Column(length = 400)
    private String description;
    @Column(precision = 10, scale = 2, nullable = false, columnDefinition = "DECIMAL(10, 2) DEFAULT 0.00")
    private BigDecimal price;
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;
    @Enumerated(EnumType.ORDINAL)
    private TypeMenu typeMenu;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "order_menu",
            joinColumns = @JoinColumn(name = "menu_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "order_id", referencedColumnName = "id"))
    private List<Order> orders;
    @CreationTimestamp
    @Column(name = "create_date")
    private LocalDateTime dateTime;

}
