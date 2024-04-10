package com.radik.my.project.utils.dto;

import com.radik.my.project.entity.Menu;
import com.radik.my.project.entity.enums.ShareType;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Data
@ToString
@Builder
public class ShareDto {

    private Long id;
    private String description;
    private String resTitle;
    private ShareType type;
    List<CountMenuShareDto> menuList;
    private Integer discount;
    private String term;
    private BigDecimal allPrice;
    private BigDecimal priceWithoutDiscount;

}
