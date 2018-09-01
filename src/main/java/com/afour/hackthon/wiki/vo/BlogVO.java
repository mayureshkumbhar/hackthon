package com.afour.hackthon.wiki.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.StringJoiner;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BlogVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5372228983592326561L;
	private String id;
	private String title;
	private String description;
	private UserProfileVO createdBy;
	private boolean isSpam;
	private Date createdDate;
	private Date modifiedDate;
	private Set<String> tags;
	private Set<String> spammedBy;
	private Set<String> viewedBy;
	
	public String validate() {
		StringJoiner joiner = new StringJoiner("|");
		
		if(StringUtils.isBlank(this.title))
			joiner.add("Title should not be empty/NULL");
		if(null == this.createdBy)
			joiner.add("Blog posted by should be empty/NULL");
		
		return  (joiner.length() > 0 ) ? joiner.toString() : null;
	}
}
