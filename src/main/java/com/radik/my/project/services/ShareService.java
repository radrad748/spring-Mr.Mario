package com.radik.my.project.services;

import com.radik.my.project.entity.CountMenuShare;
import com.radik.my.project.entity.Menu;
import com.radik.my.project.entity.Restaurant;
import com.radik.my.project.entity.Share;
import com.radik.my.project.entity.enums.ShareType;
import com.radik.my.project.repositories.ShareDao;
import com.radik.my.project.utils.exceptions.NotCorrectDataServices;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ShareService {

    private final ShareDao shareDao;
    private final RestaurantService resService;

    @Transactional
    public void saveShare(String resTitle, String description, ShareType type, Map<Menu, Integer> menuMap, Integer discount, LocalDateTime term) {
        Restaurant res = resService.getRestaurant(resTitle);
        if (res == null) throw new NotCorrectDataServices("Ресторан с таким названием: " + resTitle + " не существует");

        Share share = Share.builder()
                .description(description)
                .restaurant(res)
                .type(type)
                .discount(discount)
                .term(term).build();

        List<CountMenuShare> menu = convertToListMenu(menuMap, share);

        share.setCountMList(menu);

        shareDao.save(share);
    }

    public void deleteExpiredShare() {
        List<Share> shareList = shareDao.findAll();
        LocalDateTime date = LocalDateTime.now();

        for (Share s : shareList) {
            if (s.getTerm().isBefore(date)) shareDao.delete(s);
        }
    }
    public List<Share> findAll() {
        return shareDao.findAll();
    }
    public List<Share> findAllDesc() {
        return shareDao.findAllDesc();
    }

    /* ------------------------------------------------------------------ */

    private List<CountMenuShare> convertToListMenu(Map<Menu, Integer> menuMap, Share share) {
        List<CountMenuShare> result = new ArrayList<>();

        for (Map.Entry<Menu, Integer> entry: menuMap.entrySet()) {
            CountMenuShare count = new CountMenuShare();
            count.setCount(entry.getValue());
            count.setMenu(entry.getKey());
            count.setShare(share);
            result.add(count);
        }

        return result;
    }

}
