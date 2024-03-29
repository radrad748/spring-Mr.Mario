package com.radik.my.project.utils.Mappers;

import com.radik.my.project.entity.Menu;
import com.radik.my.project.utils.dto.MenuDto;

public class MenuMapper {

    public static MenuDto toMenuDto(Menu menu) {
        return MenuDto.builder()
                .id(menu.getId())
                .name(menu.getName())
                .description(menu.getDescription())
                .price(menu.getPrice())
                .typeMenu(menu.getTypeMenu()).build();
    }
}
