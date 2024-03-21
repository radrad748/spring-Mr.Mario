package com.radik.my.project.services;

import com.radik.my.project.entity.User;
import com.radik.my.project.repositories.UserRepository;
import com.radik.my.project.utils.exeptions.NotCorrectUserDetailsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

    public Optional<User> get(Long id) {
        if (id == null) throw new NotCorrectUserDetailsException("id не может быть null");
        return userRepository.findById(id);
    }

    public Optional<User> get(String email) {
        if (email == null) throw new NotCorrectUserDetailsException("email не может быть null");
        return userRepository.findByEmail(email);
    }

    public void delete(Long id) {
        if (id == null) throw new NotCorrectUserDetailsException("id не может быть null");
        userRepository.deleteById(id);
    }

    public User create(User user) {
        if (user == null) throw new NotCorrectUserDetailsException("user не может быть null");

        String passwordEncode = encoder.encode(user.getPassword());
        user.setPassword(passwordEncode);

        return userRepository.save(user);
    }

    public User update(User user) {
        if (user == null) throw new NotCorrectUserDetailsException("при обновление user не может быть null");

        User oldUser = get(user.getId()).orElse(null);
        if (oldUser == null) throw new NotCorrectUserDetailsException("обновление данных user -> такого user не существует");

        return userRepository.save(user);
    }

    public User addCount(Long id, int count) {
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


}
