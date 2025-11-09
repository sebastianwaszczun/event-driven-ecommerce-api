package com.waszczun.sebastian.ecommerce.model;

import jakarta.persistence.Entity;
import lombok.RequiredArgsConstructor;

@Entity
@RequiredArgsConstructor
public class User {

    final String firstName;
    final String lastName;
    final int age;
}
