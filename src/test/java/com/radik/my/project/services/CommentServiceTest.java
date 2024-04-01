package com.radik.my.project.services;


import com.radik.my.project.entity.Comment;
import com.radik.my.project.entity.Restaurant;
import com.radik.my.project.entity.User;
import com.radik.my.project.repositories.CommentDao;
import com.radik.my.project.repositories.RestaurantDao;
import com.radik.my.project.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


public class CommentServiceTest {

    private final CommentDao comDao = Mockito.mock(CommentDao.class);
    private final RestaurantDao resDao = Mockito.mock(RestaurantDao.class);
    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private CommentService commentService;

    @BeforeEach
    void initCommentService() {
        commentService = new CommentService(comDao, resDao, userRepository);
    }

    @Test
    void saveComment_whenArgNotValid_thenThrowException() {
        assertThrows(RuntimeException.class, () -> {
            commentService.saveComment(null, "email", "title");
        });

        assertThrows(RuntimeException.class, () -> {
            commentService.saveComment("text", "", "title");
        });

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            commentService.saveComment(null, "", "title");
        });

        String expectExceptionMessage = "Некорректные данные";
        String actualExceptionMessage = exception.getMessage();
        assertEquals(expectExceptionMessage, actualExceptionMessage);
    }

    @Test
    void saveComment_challengeMethod_userRepository_findByEmail() {
        commentService.saveComment("text", "email@gmail.com", "title");

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(userRepository, times(1)).findByEmail(captor.capture());
        assertThat(captor.getValue()).isEqualTo("email@gmail.com");
    }

    @Test
    void saveComment_challengeMethod_restaurantDao_get() {
        commentService.saveComment("text", "email@gmail.com", "title");

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(resDao, times(1)).get(captor.capture());
        assertThat(captor.getValue()).isEqualTo("title");
    }


    @Test
    void saveComment_challengeMethod_commentDao_save() {
        commentService.saveComment("text", "email@gmail.com", "title");

        verify(comDao, times(1)).save(any());
    }

    @Test
    void saveComment_checkWhatCommentSaved_thenChallengeMethod_saveComment() {
        User user = getUser("email@gmail.com");
        Restaurant res = getRestaurant("title");

        when(userRepository.findByEmail(any())).thenReturn(user);
        when(resDao.get(any())).thenReturn(res);

        commentService.saveComment("text", "email@gmail.com", "title");

        ArgumentCaptor<Comment> captor = ArgumentCaptor.forClass(Comment.class);
        verify(comDao, times(1)).save(captor.capture());

        assertThat(captor.getValue().getText()).isEqualTo("text");
        assertThat(captor.getValue().getUser().getEmail()).isEqualTo("email@gmail.com");
        assertThat(captor.getValue().getRestaurant().getTitle()).isEqualTo("title");
    }

    private User getUser(String email) {
        return User.builder()
                .firstName("name")
                .lastName("surname")
                .email(email)
                .password("Aa1234")
                .build();
    }
    private Restaurant getRestaurant(String title) {
        Restaurant res = new Restaurant();
        res.setTitle(title);
        return res;
    }
}
