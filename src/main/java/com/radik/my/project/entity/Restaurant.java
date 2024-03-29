package com.radik.my.project.entity;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
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
    private String title;
    @Column(length = 50, nullable = false)
    private String phone;
    @Column(length = 100, nullable = false)
    private String address;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "restaurant_id")
    private List<Comment> comments;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id")
    private List<Menu> menu;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "restaurant_id")
    private List<Order> orders;
    @CreationTimestamp
    @Column(name = "create_date")
    private LocalDateTime dateTime;

    public static void main(String[] args) {
        Restaurant r = new Restaurant();
        r.setAddress("Улица: Московская 14");
        r.setTitle(RestaurantName.THE_LOT.getValue());
        r.setPhone("(+393) 022-565-456");

    }

}
