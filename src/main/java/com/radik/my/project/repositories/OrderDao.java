package com.radik.my.project.repositories;

import com.radik.my.project.entity.Order;
import com.radik.my.project.entity.User;
import com.radik.my.project.entity.enums.PeriodOrders;
import lombok.Data;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Repository
public class OrderDao {

    @PersistenceContext
    private EntityManager entityManager;

    public Order save(Order order) {
        return entityManager.merge(order);
    }

    public List<Order> getOrders(User user, LocalDateTime start, LocalDateTime end) {
        TypedQuery<Order> query = entityManager.createQuery("select distinct o from Order o join fetch o.countMo where o.user = :user and o.createDate between :startDate and :endDate", Order.class);
        query.setParameter("startDate", start);
        query.setParameter("endDate", end);
        query.setParameter("user", user);

        return query.getResultList();
    }
}
