package com.afour.hackthon.wiki.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection="user_profile")
public class UserProfileModel implements Serializable {

	private static final long serialVersionUID = -5948663421684212213L;

	@Id
	private String userId;
	@NotBlank
	private String firstName;
	
	@NotBlank
	private String lastName;
	
	@NotBlank
	private String username;
	private String email;
	private String providerId;
	private boolean isComplete;
	private String summary;
	private int karma;
	private Set<String> tags;
	private Date creationDate = new Date();
}
