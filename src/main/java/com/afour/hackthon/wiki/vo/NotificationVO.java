package com.afour.hackthon.wiki.vo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NotificationVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2239643740956817369L;
	private String id;
	private String questionId;
	private boolean isViewed;
	private String userId;

}
