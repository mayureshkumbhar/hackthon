package com.afour.hackthon.wiki.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.StringJoiner;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class QuestionVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4991888108637348762L;
	private String id;
	private String question;
	private String description;
	//@JsonIgnoreProperties(value= {"firstName","lastName","email","karma","tags","complete","summary"})
	private UserProfileVO createdBy;
	private boolean isSpam;
	private Date createdDate;
	private Date modifiedDate;
	private Set<String> tags;
	private Set<String> spammedBy;
	private Set<String> viewedBy;
	
	
	public String validate() {
		StringJoiner joiner = new StringJoiner("|");
		
		if(StringUtils.isBlank(this.question))
			joiner.add("Question should not be empty/NULL");
		if(null == this.createdBy)
			joiner.add("Question posted by should be empty/NULL");
		
		return  (joiner.length() > 0 ) ? joiner.toString() : null;
	}
	
	
}
