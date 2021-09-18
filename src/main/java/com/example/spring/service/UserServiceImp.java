package com.example.spring.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.spring.model.Role;
import com.example.spring.model.User;
import com.example.spring.repository.UserRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImp implements UserService {
    public final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImp(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void save(String name, String email, int age, String password, String role) {
        Set<Role>roles = new HashSet<>();
        if (role.equals("ADMIN") | role.equals("USER,ADMIN")){
            roles.add(new Role(1, "ROLE_ADMIN"));
            roles.add(new Role(2, "ROLE_USER"));
        } else {
            roles.add(new Role(2, "ROLE_USER"));
        }
        User user = new User(name, email, age, password, roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
    @Override
    @SuppressWarnings("unchecked")
    public List<User> findAll() {
        List<User>users = (List<User>) userRepository.findAll();
        return users;
    }

    @Override
    @SuppressWarnings("unchecked")
    public User getById(int id) {
        User user = userRepository.findById(id).orElse(new User());
        return user;
    }

    @Override
    @SuppressWarnings("unchecked")
    public User getAuthorized() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return user;
    }

    @Override
    public void updateUser(int id, String name, String email, int age, String password, String role) {
        Set<Role> roles = new HashSet<>();
        if (role.equals("ADMIN") | role.equals("USER,ADMIN")){
            roles.add(new Role(1, "ROLE_ADMIN"));
            roles.add(new Role(2, "ROLE_USER"));
        } else {
            roles.add(new Role(2, "ROLE_USER"));
        }
        User user = new User(name, email, age, password, roles);
        user.setId(id);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void removeUser(int id) {
        userRepository.delete(getById(id));
    }
}
