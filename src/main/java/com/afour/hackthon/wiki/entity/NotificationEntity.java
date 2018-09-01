package com.afour.hackthon.wiki.entity;

import java.util.Date;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "notification")
public class NotificationEntity {
	@Id
	private String id;
	private String questionId;
	private boolean isViewed;
	private String userId;
	
}
