package com.waszczun.sebastian.ecommerce.repository;

import com.waszczun.sebastian.ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public class UserRepository extends JpaRepository<User, Long> {

}
