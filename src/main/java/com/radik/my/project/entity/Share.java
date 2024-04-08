package com.radik.my.project.entity;

import com.radik.my.project.entity.enums.ShareType;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "share")
@Data
@ToString
@Builder
public class Share {
    public Share() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 1000)
    private String description;
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;
    @Enumerated(value = EnumType.ORDINAL)
    private ShareType type;
    @ManyToMany
    @JoinTable(name = "share_menu",
    joinColumns = @JoinColumn(name = "share_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "menu_id", referencedColumnName = "id"))
    List<Menu> menuList;
    @Column(nullable = false, columnDefinition = "TINYINT UNSIGNED")
    private Integer discount;
    private LocalDateTime term;
    @CreationTimestamp
    @Column(name = "create_date")
    private LocalDateTime dateTime;
}
