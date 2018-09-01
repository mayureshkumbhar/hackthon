package com.afour.hackthon.wiki.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.afour.hackthon.wiki.service.CommonService;

@RestController
@RequestMapping("/common")
public class CommonController {

	@Autowired
	private CommonService commonService;
	
	@GetMapping(value="/stats", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Map> getStats() {
		Map<String, Long> result = commonService.getStats();
		return new ResponseEntity(result, HttpStatus.OK);
	}
}
