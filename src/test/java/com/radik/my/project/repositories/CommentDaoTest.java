package com.radik.my.project.repositories;

import com.radik.my.project.MyFinalProjectApplication;
import com.radik.my.project.entity.Comment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;


@ExtendWith(SpringExtension.class)
@ComponentScan("com.radik.my.project")
@SpringBootTest(classes = MyFinalProjectApplication.class)
//@Sql(scripts = "classpath:sql/mr_mario_user.sql", config = @SqlConfig(encoding = "UTF-8"))
//@Sql(scripts = "classpath:sql/mr_mario_restaurant.sql", config = @SqlConfig(encoding = "UTF-8"))
//@Sql(scripts = "classpath:sql/mr_mario_comment.sql", config = @SqlConfig(encoding = "UTF-8"))
public class CommentDaoTest {

    @Autowired
    private CommentDao commentDao;

    @Test
    void a() {
        List<Comment> a = commentDao.getCommentsDesc(5);
        System.out.println(a.size());
    }

}
