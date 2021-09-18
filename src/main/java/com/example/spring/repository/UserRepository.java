package com.example.spring.repository;

import org.springframework.data.repository.CrudRepository;
import com.example.spring.model.User;

public interface UserRepository extends CrudRepository<User, Integer> {

    User findByEmail(String email);
}
