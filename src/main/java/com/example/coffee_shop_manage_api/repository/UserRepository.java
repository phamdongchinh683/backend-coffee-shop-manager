package com.example.coffee_shop_manage_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.coffee_shop_manage_api.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
 boolean existsByUsername(String username);

 Optional<User> findByUsername(String username);
}
