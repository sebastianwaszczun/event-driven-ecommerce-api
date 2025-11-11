package com.waszczun.sebastian.ecommerce.repository;

import com.waszczun.sebastian.ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
