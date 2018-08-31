package com.afour.hackthon.wiki.vo;

import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AnswerVO {

	private String id;
	private String answer;
	private Date createDate;
	private Date modifiedDate;
	private UserProfileVO createBy;
	private QuestionVO question;
	private boolean isAccpeted;
	private Set<String> upBy;
	private Set<String> downBy;
	private boolean isSpam;
	
}
