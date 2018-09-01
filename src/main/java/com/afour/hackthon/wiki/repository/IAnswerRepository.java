package com.afour.hackthon.wiki.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.afour.hackthon.wiki.entity.AnswerEntity;

public interface IAnswerRepository extends MongoRepository<AnswerEntity, String>{

	@Query("{ $and: [ { 'question.id' : ?0 }, { isSpam: false } ] }")
	List<AnswerEntity> findAnswersForQuestion(String qstnId, Sort sort);
}
