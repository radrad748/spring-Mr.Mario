package com.radik.my.project.utils.Mappers;

import com.radik.my.project.entity.CountMenuShare;
import com.radik.my.project.entity.Menu;
import com.radik.my.project.entity.Share;
import com.radik.my.project.utils.dto.CountMenuShareDto;
import com.radik.my.project.utils.dto.MenuDto;
import com.radik.my.project.utils.dto.ShareDto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ShareMapper {

    public static ShareDto toShareDto(Share share) {
        List<CountMenuShareDto> menuList = new ArrayList<>();

        for (CountMenuShare m : share.getCountMList()) {
            CountMenuShareDto countDto = new CountMenuShareDto();
            countDto.setMenu(MenuMapper.toMenuDto(m.getMenu()));
            countDto.setCount(m.getCount());
            menuList.add(countDto);
        }

        String term = termToString(share.getTerm());
        BigDecimal allPrice = allPriceShare(share, menuList);

        return ShareDto.builder()
                .id(share.getId())
                .description(share.getDescription())
                .resTitle(share.getRestaurant().getTitle())
                .type(share.getType())
                .menuList(menuList)
                .discount(share.getDiscount())
                .allPrice(allPrice)
                .term(term).build();
    }

    public static List<ShareDto> toListShareDto(List<Share> shareList) {
        List<ShareDto> result = new ArrayList<>();

        for (int i = 0; i < shareList.size(); i++) {
            result.add(ShareMapper.toShareDto(shareList.get(i)));
        }

        return result;
    }

    private static String termToString(LocalDateTime term) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return term.format(formatter);
    }

    private static BigDecimal allPriceShare(Share share, List<CountMenuShareDto> menuList) {
        BigDecimal perCent = new BigDecimal(share.getDiscount());

        BigDecimal init = BigDecimal.ZERO;
        for (CountMenuShareDto menu : menuList) {
            init = init.add(menu.getMenu().getPrice());
            init = init.multiply(new BigDecimal(menu.getCount()));
        }

        BigDecimal countPerCent = init.divide(new BigDecimal("100"));
        countPerCent = countPerCent.multiply(perCent);
        init = init.subtract(countPerCent);

        return init.setScale(2, RoundingMode.HALF_UP);
    }

}
