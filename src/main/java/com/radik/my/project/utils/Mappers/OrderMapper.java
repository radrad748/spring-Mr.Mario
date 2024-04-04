package com.radik.my.project.utils.Mappers;

import com.radik.my.project.entity.CountMenuOrder;
import com.radik.my.project.entity.Order;
import com.radik.my.project.entity.OrderModel;
import com.radik.my.project.utils.dto.MenuDto;
import com.radik.my.project.utils.dto.OrderDto;

import java.util.ArrayList;
import java.util.List;

public class OrderMapper {

    public static List<OrderDto> getListOrdersDto(List<Order> orders) {
        List<OrderDto> result = new ArrayList<>();

        for (Order o : orders) {
            OrderDto dto = OrderDto.builder()
                    .id(o.getId())
                    .nameRestaurant(o.getRestaurant().getTitle())
                    .sum(o.getSum())
                    .date(o.getCreateDate()).build();

            List<OrderModel> listMenu = new ArrayList<>();
            for (CountMenuOrder count : o.getCountMo()) {
                MenuDto menuDto = MenuMapper.toMenuDto(count.getMenu());
                listMenu.add(new OrderModel(menuDto, count.getCount()));
            }
            dto.setListMenu(listMenu);
            result.add(dto);
        }

        return result;
    }

}
