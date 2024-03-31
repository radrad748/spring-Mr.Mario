package com.radik.my.project.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "order_menu")
@Data
@ToString
public class OrderMenu {
}
