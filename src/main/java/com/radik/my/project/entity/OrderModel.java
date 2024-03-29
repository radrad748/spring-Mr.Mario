package com.radik.my.project.entity;

import com.radik.my.project.utils.dto.MenuDto;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class OrderModel {

    public OrderModel(MenuDto menu, int count) {
        this.menu = menu;
        this.count = count;
    }

    private MenuDto menu;
    private int count;

}
