package com.riwi.prueba.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.riwi.prueba.domain.entities.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer>{
}