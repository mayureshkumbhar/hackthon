package com.afour.hackthon.wiki.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.afour.hackthon.wiki.entity.QuestionEntity;

public interface IQuestionRepository extends MongoRepository<QuestionEntity, String> {
	
	//@Query("{ 'createdBy.id' : ?0 }")
	@Query("{ $and: [ { 'createdBy.id' : ?0  }, { isSpam: false } ] }")
	List<QuestionEntity> findByUserId(String userId, Sort sort);
	
	@Query("{ isSpam: false }")
	List<QuestionEntity> findAllByNotSpam(Sort sort);

	@Query(value="{}", fields="{tags : 1, _id : 0}")
	List<QuestionEntity> findTagsAndExcludeOther();
	
	@Query("{ $and: [ { 'tags' : ?0 }, { isSpam: false } ] }")
	List<QuestionEntity> findByTags(String tag, Sort sort);
	

}
