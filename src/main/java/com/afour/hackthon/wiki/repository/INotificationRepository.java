package com.afour.hackthon.wiki.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.afour.hackthon.wiki.entity.NotificationEntity;
import com.afour.hackthon.wiki.entity.QuestionEntity;

public interface INotificationRepository  extends MongoRepository<NotificationEntity, String> {


	Set<NotificationEntity> findByUserIdAndIsViewed(String userId, boolean b);

}
