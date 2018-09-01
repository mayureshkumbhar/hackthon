package com.afour.hackthon.wiki.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

@Service
public class TaggingService {

	private static final Logger LOGGER = LoggerFactory.getLogger(TaggingService.class);
	
	@Value("${app.tagservice.url}")
	private String tagServiceUrl;
	
	@Autowired
	private RestTemplate restTemplate;
	
	public Map<String, Object> getTag(String input, boolean isQue){
		
		LOGGER.debug("Getting tags for [{}]", input);
		Map<String, Object> params = new HashMap<>();
		params.put("isQuestion", isQue);
		params.put("input", input);
		
		Map<String, List<String>> map  = callTaggingService(params);
		Map<String, Object> result = new HashMap<>();
		if(!CollectionUtils.isEmpty(map)) {
			result.put("isSpam", !map.get("spam").isEmpty());
			result.put("tags", new HashSet<>(map.get("tags")));
			return result;
		}else {
			result.put("isSpam", false);
			result.put("tags", Collections.EMPTY_SET);
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, List<String>> callTaggingService(Map<String, Object> params) {
		LOGGER.debug("Calling tagging service api: uri [{}] and param {}", tagServiceUrl, params);
		HttpEntity<Map<String, Object>> request = new HttpEntity<>(params);
		try {
			Map<String, List<String>> response = restTemplate.postForObject(tagServiceUrl, request, Map.class);
			LOGGER.debug("Response recieved: {}", response);
			return response;
		}catch (Exception e) {
			LOGGER.error("Error occured while calling Tagging API", e);
			return Collections.emptyMap();
		}
		
	}
}
