package com.radik.my.project.services;

import com.radik.my.project.entity.Comment;
import com.radik.my.project.entity.Restaurant;
import com.radik.my.project.entity.User;
import com.radik.my.project.repositories.CommentDao;
import com.radik.my.project.repositories.RestaurantDao;
import com.radik.my.project.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentDao comDao;

    private final RestaurantDao resDao;

    private final UserRepository userRepository;


    @Transactional
    public void saveComment(String text, String userEmail, String resTitle) {
        if (Objects.isNull(text) || text.trim().isEmpty() || Objects.isNull(userEmail) || userEmail.trim().isEmpty() ||
                Objects.isNull(resTitle) || resTitle.trim().isEmpty())
            throw new RuntimeException("Некорректные данные");

        User user = userRepository.findByEmail(userEmail);
        Restaurant res = resDao.get(resTitle);

        Comment comment = new Comment();
        comment.setText(text);
        comment.setUser(user);
        comment.setRestaurant(res);

        comDao.save(comment);
    }

    @Transactional(readOnly = true)
    public List<Comment> getCommentsDesc(int size) {
        return comDao.getCommentsDesc(size);
    }

    public Long countToRestaurant(Restaurant res) {
        return comDao.countToRestaurant(res);
    }

    @Transactional
    public void deleteById(Long id) {
        if (Objects.isNull(id) || id <= 0) throw new RuntimeException("id не может быть null или меньше и равно 0");

        comDao.deleteById(id);
    }


}
