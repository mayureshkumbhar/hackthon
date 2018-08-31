package com.afour.hackthon.wiki.vo;

import java.io.Serializable;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserProfileVO implements Serializable {

	private static final long serialVersionUID = -5948663421684212213L;

	private String id;
	private String firstName;
	private String lastName;
	private String username;
	private String email;
	private String providerId;
	private String picture;
	private boolean isComplete;
	private String summary;
	private int karma;
	private Set<String> tags;
	
}
