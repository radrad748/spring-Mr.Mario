package com.radik.my.project.entity;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "order_table")
@Data
@ToString
@Builder
public class Order {
    public Order() {}

    public Order(Long id, Restaurant restaurant, User user, BigDecimal sum, boolean delivery, List<Menu> menu, LocalDateTime createDate) {
        this.id = id;
        this.restaurant = restaurant;
        this.user = user;
        this.sum = sum;
        this.delivery = delivery;
        this.menu = menu;
        this.createDate = createDate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @NotNull
    @Column(precision = 10, scale = 2, nullable = false, columnDefinition = "DECIMAL(10, 2) DEFAULT 0.00")
    private BigDecimal sum;
    @Column(columnDefinition = "BIT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    boolean delivery;
    @NotNull
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "order_menu",
    joinColumns = @JoinColumn(name = "order_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "menu_id", referencedColumnName = "id"))
    private List<Menu> menu;
    @Column(name = "create_date")
    private LocalDateTime createDate;

}
