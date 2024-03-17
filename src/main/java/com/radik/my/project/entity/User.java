package com.radik.my.project.entity;

import com.radik.my.project.utils.valid.Password;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "user")
@Data
@ToString
public class User /*implements UserDetails*/ {

    public User() {
        count = new BigDecimal("00.00");
        role = Role.USER;
        active = true;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Size(min = 3, max = 20, message = "Длина должна быть от 2 до 50 символов")
    @Column(length = 20, nullable = false)
    private String username;
    @Email(message ="Некорректный email адрес")
    @NotNull
    @Column(length = 50, nullable = false, unique = true)
    private String email;
    @NotNull
    @Password
    @Column(length = 50, nullable = false)
    private String password;
    @Column(precision = 10, scale = 2, nullable = false, columnDefinition = "DECIMAL(10, 2) DEFAULT 0.00")
    private BigDecimal count;
    @Enumerated(EnumType.ORDINAL)
    private Role role;
    @Column(columnDefinition = "BOOLEAN DEFAULT true")
    private boolean active;
    @CreationTimestamp
    @Column(name = "create_date")
    private LocalDate createDate;

    //@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.name()));
        return authorities;
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public static class UserBuilder {
        private Long id;
        private String username;
        private String email;
        private String password;
        private BigDecimal count;
        private boolean active;
        private Role role;

        UserBuilder() {
            count = new BigDecimal("00.00");
            role = Role.USER;
            active = true;
        }

        public UserBuilder username(String username) {
            this.username = username;
            return this;
        }

        public UserBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder count(BigDecimal count) {
            this.count = count;
            return this;
        }
        public UserBuilder active(boolean active) {
            this.active = active;
            return this;
        }

        public UserBuilder role(Role role) {
            this.role = role;
            return this;
        }

        public User build() {
            User user = new User();
            user.setUsername(this.username);
            user.setEmail(this.email);
            user.setPassword(this.password);
            user.setCount(this.count);
            user.setRole(this.role);
            return user;
        }

        public String toString() {
            return "User.UserBuilder(id=" + this.id + ", name=" + this.username + ", email=" + this.email + ", password=" + this.password + ", count=" + this.count + "active=" + this.active + ", role=" + this.role + ")";
        }
    }
}
