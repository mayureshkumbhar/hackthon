package com.afour.hackthon.wiki.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.afour.hackthon.wiki.entity.UserProfileEntity;

public interface IUserProfileRepository extends MongoRepository<UserProfileEntity, String> {

	UserProfileEntity findByProviderId(String providerId);

	List<UserProfileEntity> findByTags(String next);
}
