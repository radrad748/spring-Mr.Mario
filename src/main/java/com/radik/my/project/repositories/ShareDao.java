package com.radik.my.project.repositories;

import com.radik.my.project.entity.Share;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class ShareDao {

    @PersistenceContext
    private EntityManager entityManager;

    public void save(Share share) {
        entityManager.persist(share);
    }

}
