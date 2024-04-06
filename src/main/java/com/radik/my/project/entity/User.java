package com.radik.my.project.entity;

import com.radik.my.project.entity.converter.RoleConverter;
import com.radik.my.project.entity.enums.Role;
import com.radik.my.project.utils.valid.Password;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "user")
@Data
@ToString
public class User implements UserDetails {

    public User() {
        count = new BigDecimal("00.00");
        roles = new HashSet<>();
        roles.add(Role.USER);
        active = true;
        nonLocke = true;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Size(min = 3, max = 50, message = "Длина должна быть от 2 до 50 символов")
    @Column(length = 20, nullable = false)
    private String firstName;
    @NotNull
    @Size(min = 3, max = 50, message = "Длина должна быть от 2 до 50 символов")
    @Column(length = 20, nullable = false)
    private String lastName;
    @Email(message ="Некорректный email адрес")
    @NotNull
    @Email
    @Column(length = 50, nullable = false, unique = true)
    private String email;
    @NotNull
    @Password
    @Column(nullable = false)
    private String password;
    @Column(precision = 10, scale = 2, nullable = false, columnDefinition = "DECIMAL(10, 2) DEFAULT 0.00")
    private BigDecimal count;
    @Column(columnDefinition = "SET('user', 'admin')")
    @Convert(converter = RoleConverter.class)
    private Set<Role> roles;
    @Column(columnDefinition = "BIT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean active;
    @Column(name = "non_locke", columnDefinition = "BIT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean nonLocke;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<Comment> comments;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<Order> orders;
    @CreationTimestamp
    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        /*List<GrantedAuthority> authorities = new ArrayList<>();
        for(Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.name()));
        }
        return authorities;*/
        return roles;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return nonLocke;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public static class UserBuilder {
        private Long id;
        private String firstName;
        private String lastName;
        private String email;
        private String password;
        private BigDecimal count;
        private boolean active;
        private Set<Role> roles;

        UserBuilder() {
            count = new BigDecimal("00.00");
            roles = new HashSet<>();
            roles.add(Role.USER);
            active = true;
        }

        public UserBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }
        public UserBuilder lastName(String lastName) {
            this.lastName = lastName;
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

        public UserBuilder roleAdd(Role role) {
            this.roles.add(role);
            return this;
        }
        public UserBuilder rolesAdd(Role... roles) {
            this.roles.addAll(Arrays.asList(roles));
            return this;
        }

        public User build() {
            User user = new User();
            user.setFirstName(this.firstName);
            user.setLastName(this.lastName);
            user.setEmail(this.email);
            user.setPassword(this.password);
            user.setCount(this.count);
            user.setRoles(this.roles);
            return user;
        }

        public String toString() {
            return "User.UserBuilder(id=" + this.id + ", name=" + this.firstName + ",lastName=" + this.lastName + ", email=" + this.email + ", password=" + this.password + ", count=" + this.count + "active=" + this.active + ", role=" + this.roles + ")";
        }
    }
}
