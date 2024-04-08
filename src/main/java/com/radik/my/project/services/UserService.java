package com.radik.my.project.services;

import com.radik.my.project.entity.User;
import com.radik.my.project.entity.enums.Role;
import com.radik.my.project.repositories.CodeAnswer;
import com.radik.my.project.repositories.UserRepository;
import com.radik.my.project.utils.Mappers.UserMapper;
import com.radik.my.project.utils.dto.UserDto;
import com.radik.my.project.utils.exceptions.NotCorrectUserDetailsException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

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

    @Transactional
    public void delete(Long id) {
        if (id == null) throw new NotCorrectUserDetailsException("id не может быть null");
        if (!userRepository.existsById(id)) throw new NotCorrectUserDetailsException("пользователя с таким id не существует");

        userRepository.deleteById(id);
    }

    @Transactional
    public User create(User user) {
        if (user == null) throw new NotCorrectUserDetailsException("user не может быть null");
        if (ifExistEmail(user.getEmail())) return null;

        String passwordEncode = encoder.encode(user.getPassword());
        user.setPassword(passwordEncode);

        return userRepository.save(user);
    }

    @Transactional
    public User update(User user) {
        if (user == null) throw new NotCorrectUserDetailsException("при обновление user не может быть null");

        if (!userRepository.existsById(user.getId())) throw new NotCorrectUserDetailsException("обновление данных user -> такого user не существует");

        return userRepository.save(user);
    }

    @Transactional
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

    @Transactional
    public void modifyName(Long id, String name) {
        if (id == null || Objects.isNull(name) || name.trim().isEmpty()) throw new RuntimeException("некорректные данные");
        if (!userRepository.existsById(id)) throw new NotCorrectUserDetailsException("user с таким id не существует");

        User user = userRepository.findById(id).get();
        user.setFirstName(name);
        userRepository.save(user);
    }
    @Transactional
    public void modifySurname(Long id, String surname) {
        if (id == null || Objects.isNull(surname) || surname.trim().isEmpty()) throw new RuntimeException("некорректные данные");
        if (!userRepository.existsById(id)) throw new NotCorrectUserDetailsException("user с таким id не существует");

        User user = userRepository.findById(id).get();
        user.setLastName(surname);
        userRepository.save(user);
    }

    @Transactional
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

    @Transactional
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

    public Long getCount() {
        return userRepository.count();
    }

    public List<UserDto> findAll(int page, int size) {
        List<User> pageList = userRepository.findAll(PageRequest.of(page, size)).getContent();
        return UserMapper.toListUserDto(pageList);
    }
    @Transactional
    public void block(Long id) {
        if (Objects.isNull(id)) throw new NotCorrectUserDetailsException("id не может быть null");
        if (!userRepository.existsById(id)) throw new NotCorrectUserDetailsException("Пользователя с таким id не существует");

        User user = userRepository.findById(id).get();
        user.block();
        userRepository.save(user);
    }
    @Transactional
    public void unblock(Long id) {
        if (Objects.isNull(id)) throw new NotCorrectUserDetailsException("id не может быть null");
        if (!userRepository.existsById(id)) throw new NotCorrectUserDetailsException("Пользователя с таким id не существует");

        User user = userRepository.findById(id).get();
        user.unblock();
        userRepository.save(user);
    }
    @Transactional
    public void addRole(Long id, Role role) {
        User user = userRepository.findById(id).get();
        boolean addRole = user.addRole(role);
        if (addRole) userRepository.save(user);
    }
    @Transactional
    public void deleteRole(Long id, Role role) {
        User user = userRepository.findById(id).get();
        boolean deleteRole = user.deleteRole(role);
        if (deleteRole) userRepository.save(user);
    }
}
