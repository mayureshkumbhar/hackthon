package com.afour.hackthon.wiki.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection="user_profile")
public class UserProfileEntity implements Serializable {

	private static final long serialVersionUID = -5948663421684212213L;

	@Id
	private String id;
	private String firstName;
	private String lastName;
	private String username;
	private String email;
	private String providerId;
	private boolean isComplete;
	private String summary;
	private String picture;
	private int karma;
	private Set<String> tags;
	private Date creationDate;
	private Date modifiedDate;
}
