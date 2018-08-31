package com.afour.hackthon.wiki.entity;

import java.util.Date;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection="answers")
public class AnswerEntity {

	@Id
	private String id;
	private String answer;
	private Date createDate;
	private Date modifiedDate;
	
	@DBRef
	private UserProfileEntity createBy;
	
	@DBRef
	private QuestionEntity question;
	private boolean isAccpeted;
	private Set<String> upBy;
	private Set<String> downBy;
	private boolean isSpam;
	
}
