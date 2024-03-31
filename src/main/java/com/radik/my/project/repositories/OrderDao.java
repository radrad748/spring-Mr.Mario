package com.radik.my.project.repositories;

import com.radik.my.project.entity.Order;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class OrderDao {

    @PersistenceContext
    private EntityManager entityManager;

    public Order save(Order order) {
        return entityManager.merge(order);
    }

}
