package com.afour.hackthon.wiki.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.afour.hackthon.wiki.model.UserProfileModel;

public interface IUserProfileRepository extends MongoRepository<UserProfileModel, String> {

	UserProfileModel findByProviderId(String providerId);
}
