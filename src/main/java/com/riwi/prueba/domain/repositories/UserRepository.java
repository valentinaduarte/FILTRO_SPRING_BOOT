package com.riwi.prueba.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.riwi.prueba.domain.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
}