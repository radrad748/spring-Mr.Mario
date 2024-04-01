package com.radik.my.project.services;

import com.radik.my.project.entity.User;
import com.radik.my.project.repositories.CodeAnswer;
import com.radik.my.project.repositories.UserRepository;
import com.radik.my.project.utils.exceptions.NotCorrectUserDetailsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

    public User get(Long id) {
        if (id == null) throw new NotCorrectUserDetailsException("id не может быть null");
        return userRepository.findById(id).orElse(null);
    }

    public User get(String email) {
        if (email == null) throw new NotCorrectUserDetailsException("email не может быть null");
        return userRepository.findByEmail(email);
    }

    public void delete(Long id) {
        if (id == null) throw new NotCorrectUserDetailsException("id не может быть null");
        if (!userRepository.existsById(id)) throw new NotCorrectUserDetailsException("пользователя с таким id не существует");

        userRepository.deleteById(id);
    }

    public User create(User user) {
        if (user == null) throw new NotCorrectUserDetailsException("user не может быть null");
        if (ifExistEmail(user.getEmail())) return null;

        String passwordEncode = encoder.encode(user.getPassword());
        user.setPassword(passwordEncode);

        return userRepository.save(user);
    }

    public User update(User user) {
        if (user == null) throw new NotCorrectUserDetailsException("при обновление user не может быть null");

        if (!userRepository.existsById(user.getId())) throw new NotCorrectUserDetailsException("обновление данных user -> такого user не существует");

        return userRepository.save(user);
    }

    public User addCount(Long id, int count) {
        if (count < 0 || id == null) throw new RuntimeException("некорректные данные");
        if (!ifExistId(id)) throw new NotCorrectUserDetailsException("user с таким id не существует");

        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            if (count == 0) return optionalUser.get();

            User user = optionalUser.get();
            BigDecimal userCount = user.getCount();
            userCount = userCount.add(new BigDecimal(count));
            user.setCount(userCount);

            return userRepository.save(user);
        } else throw new NotCorrectUserDetailsException("user с таким email не существует");
    }

    public void modifyName(Long id, String name) {
        if (id == null || Objects.isNull(name) || name.trim().isEmpty()) throw new RuntimeException("некорректные данные");
        if (!userRepository.existsById(id)) throw new NotCorrectUserDetailsException("user с таким id не существует");

        User user = userRepository.findById(id).get();
        user.setFirstName(name);
        userRepository.save(user);
    }
    public void modifySurname(Long id, String surname) {
        if (id == null || Objects.isNull(surname) || surname.trim().isEmpty()) throw new RuntimeException("некорректные данные");
        if (!userRepository.existsById(id)) throw new NotCorrectUserDetailsException("user с таким id не существует");

        User user = userRepository.findById(id).get();
        user.setLastName(surname);
        userRepository.save(user);
    }

    public void modifyEmail(Long id, String email) {
        if (id == null || Objects.isNull(email) || email.trim().isEmpty()) throw new RuntimeException("некорректные данные");
        if (!userRepository.existsById(id)) throw new NotCorrectUserDetailsException("user с таким id не существует");
        if (ifExistEmail(email)) throw new NotCorrectUserDetailsException("user с таким email уже существует");

        User user = userRepository.findById(id).get();
        user.setEmail(email);
        userRepository.save(user);
    }

    public boolean ifExistEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean ifExistId(Long id) {
        return userRepository.existsById(id);
    }

    public CodeAnswer modifyPassword(Long id, String password, String newPassword) {
        if (id == null || id < 0 || Objects.isNull(password) || password.trim().isEmpty() ||
                Objects.isNull(newPassword) || newPassword.trim().isEmpty())
            throw new RuntimeException("некорректные данные");

        User user = userRepository.findById(id).orElse(null);
        if (user == null) throw new NotCorrectUserDetailsException("user с таким id не существует");

        boolean checkPassword = encoder.matches(password, user.getPassword());
        if (!checkPassword) return CodeAnswer.WRONG_PASSWORD;

        user.setPassword(encoder.encode(newPassword));
        userRepository.save(user);
        return CodeAnswer.OK;
    }

}
