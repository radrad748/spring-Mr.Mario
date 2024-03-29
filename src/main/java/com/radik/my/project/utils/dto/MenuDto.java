package com.radik.my.project.utils.dto;

import com.radik.my.project.entity.TypeMenu;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@ToString
@Builder
public class MenuDto {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private TypeMenu typeMenu;
}
