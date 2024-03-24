package com.radik.my.project.services;

import com.radik.my.project.entity.Restaurant;
import com.radik.my.project.repositories.RestaurantDao;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantService {

   private final RestaurantDao resDao;

   @Cacheable("restaurant")
   public Restaurant getRestaurant(String title) {
      return resDao.get(title);
   }
}
