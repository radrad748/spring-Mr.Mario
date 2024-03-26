package com.radik.my.project.repositories;

import com.radik.my.project.entity.Comment;
import com.radik.my.project.entity.Restaurant;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class CommentDao {

    @PersistenceContext
    private EntityManager entityManager;

    public void save(Comment comment) {
        entityManager.persist(comment);
    }

    public List<Comment> getCommentsDesc(int size) {
        TypedQuery<Comment> query = entityManager.createQuery("select c from Comment c order by c.id desc", Comment.class);
        query.setMaxResults(size);

        return query.getResultList();
    }

    public Long countToRestaurant(Restaurant res) {
        TypedQuery<Long> query = entityManager.createQuery("select count(c) from Comment c where c.restaurant = :restaurant", Long.class);
        query.setParameter("restaurant", res);

        return query.getSingleResult();
    }

    public void deleteById(Long id) {
        Query query = entityManager.createQuery("delete from Comment c where c.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

}
