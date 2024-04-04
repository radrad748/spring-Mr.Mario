package com.radik.my.project.utils.dto;


import com.radik.my.project.entity.OrderModel;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
@ToString
public class OrderDto {

    private Long id;
    private String nameRestaurant;
    private BigDecimal sum;
    private List<OrderModel> listMenu;
    private String date;

    OrderDto(Long id, String nameRestaurant, BigDecimal sum, List<OrderModel> listMenu, String date) {
        this.id = id;
        this.nameRestaurant = nameRestaurant;
        this.sum = sum;
        this.listMenu = listMenu;
        this.date = date;
    }

    public static OrderDtoBuilder builder() {
        return new OrderDtoBuilder();
    }

    public static class OrderDtoBuilder {
        private Long id;
        private String nameRestaurant;
        private BigDecimal sum;
        private List<OrderModel> listMenu;
        private String date;

        OrderDtoBuilder() {
        }

        public OrderDtoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public OrderDtoBuilder nameRestaurant(String nameRestaurant) {
            this.nameRestaurant = nameRestaurant;
            return this;
        }

        public OrderDtoBuilder sum(BigDecimal sum) {
            this.sum = sum;
            return this;
        }

        public OrderDtoBuilder listMenu(List<OrderModel> listMenu) {
            this.listMenu = listMenu;
            return this;
        }

        public OrderDtoBuilder date(LocalDateTime date) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
            this.date = date.format(formatter);
            return this;
        }

        public OrderDto build() {
            return new OrderDto(this.id, this.nameRestaurant, this.sum, this.listMenu, this.date);
        }

        public String toString() {
            return "OrderDto.OrderDtoBuilder(id=" + this.id + ", nameRestaurant=" + this.nameRestaurant + ", sum=" + this.sum + ", listMenu=" + this.listMenu + ", date=" + this.date + ")";
        }
    }
}
