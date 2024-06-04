package com.riwi.prueba.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.riwi.prueba.domain.entities.User;

public interface UserRepository extends JpaRepository<User,Integer> {
}