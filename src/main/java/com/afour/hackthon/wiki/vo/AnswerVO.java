package com.afour.hackthon.wiki.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.StringJoiner;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AnswerVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4673525464017337825L;
	private String id;
	private String answer;
	private Date createDate;
	private Date modifiedDate;
	private UserProfileVO createdBy;
	private QuestionVO question;
	private boolean isAccpeted;
	private Set<String> upBy;
	private Set<String> downBy;
	private boolean isSpam;
	private Set<String> spammedBy;
	
	public String validate() {
		StringJoiner joiner = new StringJoiner("|");
		
		if(StringUtils.isBlank(this.answer))
			joiner.add("Answer should not be empty/NULL");
		if(null == this.createdBy)
			joiner.add("Answer posted by should be empty/NULL");
		
		return  (joiner.length() > 0 ) ? joiner.toString() : null;
	}
	
}
