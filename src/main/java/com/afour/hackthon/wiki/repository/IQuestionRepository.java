package com.afour.hackthon.wiki.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.afour.hackthon.wiki.entity.QuestionEntity;

public interface IQuestionRepository extends MongoRepository<QuestionEntity, String> {

}
