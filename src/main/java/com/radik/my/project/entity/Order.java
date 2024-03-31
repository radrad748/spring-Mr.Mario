package com.radik.my.project.entity;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
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

    public Order(Long id, Restaurant restaurant, User user, BigDecimal sum, boolean delivery, List<CountMenuOrder> countMo, LocalDateTime createDate) {
        this.id = id;
        this.restaurant = restaurant;
        this.user = user;
        this.sum = sum;
        this.delivery = delivery;
        this.countMo = countMo;
        this.createDate = createDate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(precision = 10, scale = 2, nullable = false, columnDefinition = "DECIMAL(10, 2) DEFAULT 0.00")
    private BigDecimal sum;
    @Column(columnDefinition = "BIT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    boolean delivery;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private List<CountMenuOrder> countMo;
    @CreationTimestamp
    @Column(name = "create_date")
    private LocalDateTime createDate;

}
