package com.radik.my.project.repositories;

import com.radik.my.project.entity.Comment;
import com.radik.my.project.entity.Menu;
import com.radik.my.project.entity.Restaurant;
import com.radik.my.project.entity.User;
import com.radik.my.project.entity.enums.Role;
import com.radik.my.project.entity.enums.ShareType;
import com.radik.my.project.services.ShareService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.Rollback;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ComponentScan("com.radik.my.project")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CommentDaoTest {

    @Autowired
    private CommentDao commentDao;
    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void getCommentDesc_returnSizeComments() {
        List<Comment>  sizeFor = commentDao.getCommentsDesc(4);
        List<Comment>  sizeFive = commentDao.getCommentsDesc(5);

        assertAll("2 сценария: лист sizeFor ждем размер-4; лист sizeFive ждем размер-5",
                () -> assertEquals(4, sizeFor.size()),
                () -> assertEquals(5, sizeFive.size())
        );
    }

    @Test
    void getCommentDesc_returnOrderDesc() {
        TypedQuery<Long> query = entityManager.createQuery("select c.id from Comment c order by c.id desc", Long.class);
        query.setMaxResults(5);
        List<Long> theBiggestCommentsId = query.getResultList();
        List<Comment> comments = commentDao.getCommentsDesc(5);


        assertAll("comments id sizeFive должен быть начиная с наибольшего id в убывающем порядке",
                () -> assertEquals(theBiggestCommentsId.get(0), comments.get(0).getId()),
                () -> assertEquals(theBiggestCommentsId.get(1), comments.get(1).getId()),
                () -> assertEquals(theBiggestCommentsId.get(2), comments.get(2).getId()),
                () -> assertEquals(theBiggestCommentsId.get(3), comments.get(3).getId()),
                () -> assertEquals(theBiggestCommentsId.get(4), comments.get(4).getId())
        );
    }

    /* --------------------------------------------------------------------------------------------------- */


    /*@Test
    void a() {
        TypedQuery<Menu> query = entityManager.createQuery("from Menu", Menu.class);

        List<Menu> shareMenu = query.getResultList();

        Map<Menu, Integer> map = new HashMap<>();
        map.put(shareMenu.get(6), 1);
        map.put(shareMenu.get(8), 1);

        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime nextMonthDateTime = currentDateTime.plusMonths(1);


        String description = "Хорошее предложение от ресторана The lot блюдо Кучмачи и бутылка вкусного вина Carranc" +
                "со скидкой 30%. Успей посетить ресторан и и получить специальное предложение, действие акции длиться всего месяц";

        shareService.saveShare("The lot", description, ShareType.ONE, map, 30, nextMonthDateTime);
    } */


}
