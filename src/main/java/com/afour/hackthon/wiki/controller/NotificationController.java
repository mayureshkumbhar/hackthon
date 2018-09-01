package com.afour.hackthon.wiki.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.afour.hackthon.wiki.service.NotificationService;
import com.afour.hackthon.wiki.vo.NotificationVO;

@RestController
@RequestMapping("/notification")
public class NotificationController {
	@Autowired
	private NotificationService notificationService;

	@GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Set<NotificationVO>> getAllNotificationForUser(@PathVariable("userId") String userId) {
		Set<NotificationVO> notificationVO = notificationService.getNotificationsForUser(userId);
		return new ResponseEntity<>(notificationVO, HttpStatus.OK);
	}

	@PostMapping(value = "/markRead", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Map> markNotificationAsRead(
			@RequestBody List<String> notificationIds) throws Exception {
		notificationService.markNotificationAsRead(notificationIds);
		Map<String, String> result = new HashMap<>();
		result.put("message", "marked");
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	/*@PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public void addNotification() {
		notificationService.addNotification();

		// return new ResponseEntity<>(new String("added notifications"),HttpStatus.OK);
	}*/

}
