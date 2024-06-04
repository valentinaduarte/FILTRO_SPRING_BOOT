package com.riwi.prueba.domain.repositories;

import org.hibernate.type.descriptor.java.IntegerJavaType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.riwi.prueba.domain.entities.Survey;

import jakarta.mail.search.IntegerComparisonTerm;

@Repository
public interface SurveyRepository extends JpaRepository<Survey,Integer> {

}
