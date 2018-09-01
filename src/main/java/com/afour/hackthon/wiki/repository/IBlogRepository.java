package com.afour.hackthon.wiki.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.afour.hackthon.wiki.entity.BlogEntity;

public interface IBlogRepository extends MongoRepository<BlogEntity, String> {

	
	@Query("{ $and: [ { 'createdBy.id' : ?0  }, { isSpam: false } ] }")
	List<BlogEntity> findByUserId(String userId, Sort sort);
	
	@Query("{ isSpam: false }")
	List<BlogEntity> findAllByNotSpam(Sort sort);

}
