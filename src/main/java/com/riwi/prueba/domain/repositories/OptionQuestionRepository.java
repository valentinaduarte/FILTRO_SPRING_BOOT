package com.riwi.prueba.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.riwi.prueba.domain.entities.OptionQuestion;

@Repository
public interface OptionQuestionRepository extends JpaRepository<OptionQuestion, Integer> {

}