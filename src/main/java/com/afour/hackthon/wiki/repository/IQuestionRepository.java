package com.afour.hackthon.wiki.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.afour.hackthon.wiki.entity.QuestionEntity;

public interface IQuestionRepository extends MongoRepository<QuestionEntity, String> {
	
	@Query("{ 'createdBy.id' : ?0 }")
	List<QuestionEntity> findByUserId(String userId, Sort sort);

}
