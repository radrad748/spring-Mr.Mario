package com.radik.my.project.repositories;

import com.radik.my.project.entity.Share;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class ShareDao {

    @PersistenceContext
    private EntityManager entityManager;

    public void save(Share share) {
        entityManager.persist(share);
    }

    @Transactional
    public void delete(Share share) {
        entityManager.remove(share);
    }

    public List<Share> findAll() {
        TypedQuery<Share> query = entityManager.createQuery("from Share", Share.class);
        return query.getResultList();
    }
    public List<Share> findAllDesc() {
        TypedQuery<Share> query = entityManager.createQuery("from Share s order by s.id desc", Share.class);
        return query.getResultList();
    }

}
