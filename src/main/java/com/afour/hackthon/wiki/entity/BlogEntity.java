package com.afour.hackthon.wiki.entity;

import java.util.Date;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection="blogs")
public class BlogEntity {

	@Id
	private String id;
	private String title;
	private String description;
	@DBRef
	private UserProfileEntity createdBy;
	private boolean isSpam;
	private Date createdDate;
	private Date modifiedDate;
	private Set<String> tags;
	private Set<String> spammedBy;
	private Set<String> viewedBy;
	
}
