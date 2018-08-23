package com.afour.hackthon.wiki.vo;

import java.io.Serializable;
import java.util.Set;

import com.afour.hackthon.wiki.commons.ProfileStatusEnum;

import lombok.Data;

@Data
public class UserProfileVO implements Serializable {

	private static final long serialVersionUID = -5948663421684212213L;

	private String userId;
	private String firstName;
	private String lastName;
	private String username;
	private String email;
	private boolean isComplete;
	private String summary;
	private int karma;
	private Set<String> tags;
	
}
