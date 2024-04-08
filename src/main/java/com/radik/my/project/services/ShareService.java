package com.radik.my.project.services;

import com.radik.my.project.entity.Menu;
import com.radik.my.project.entity.Restaurant;
import com.radik.my.project.entity.Share;
import com.radik.my.project.entity.enums.ShareType;
import com.radik.my.project.repositories.ShareDao;
import com.radik.my.project.utils.exceptions.NotCorrectDataServices;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShareService {

    private final ShareDao shareDao;
    private final RestaurantService resService;

    @Transactional
    public void saveShare(String resTitle, String description, ShareType type, List<Menu> menuList, Integer discount) {
        Restaurant res = resService.getRestaurant(resTitle);
        if (res == null) throw new NotCorrectDataServices("Ресторан с таким названием: " + resTitle + " не существует");

        Share share = Share.builder()
                .description(description)
                .restaurant(res)
                .type(type)
                .menuList(menuList)
                .discount(discount).build();

        shareDao.save(share);
    }

}
