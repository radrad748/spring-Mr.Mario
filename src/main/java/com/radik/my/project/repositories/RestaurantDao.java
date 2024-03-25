package com.radik.my.project.repositories;

import com.radik.my.project.entity.Restaurant;
import com.radik.my.project.utils.exeptions.NotCorrectUserDetailsException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Objects;


@Repository
public class RestaurantDao {

    @PersistenceContext
    private EntityManager entityManager;


    public Restaurant get(String title) {
        if (Objects.isNull(title) || title.trim().isEmpty()) throw new NotCorrectUserDetailsException("Название не может быть null или пустым");

        TypedQuery<Restaurant> query = entityManager
                .createQuery("SELECT r from Restaurant r WHERE r.title = :title", Restaurant.class);
        query.setParameter("title", title);

        List<Restaurant> restaurant = query.getResultList();
        if (restaurant.isEmpty()) return null;

        return restaurant.get(0);
    }


}
