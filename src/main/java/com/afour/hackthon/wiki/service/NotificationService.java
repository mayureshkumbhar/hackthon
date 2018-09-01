package com.afour.hackthon.wiki.service;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.afour.hackthon.wiki.entity.NotificationEntity;
import com.afour.hackthon.wiki.repository.INotificationRepository;
import com.afour.hackthon.wiki.vo.NotificationVO;
import com.afour.hackthon.wiki.vo.QuestionVO;

@Service
public class NotificationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(NotificationService.class);

	private final ModelMapper modelMapper = new ModelMapper();
	@Autowired
	private INotificationRepository iNotificationRepository;

	@Autowired
	private UserProfileService profileService;

	@Async
	public void saveNotifications(List<String> tags, String questionId) {
		Set<String> userIds = new HashSet<>();
		userIds = profileService.getAllUserByTag(tags);
		Iterator<String> itr = userIds.iterator();
		while (itr.hasNext()) {
			NotificationEntity notificationEntity = new NotificationEntity();
			notificationEntity.setQuestionId(questionId);
			notificationEntity.setViewed(false);
			notificationEntity.setUserId(itr.next());
			iNotificationRepository.save(notificationEntity);
		}

	}

	public void markNotificationAsRead(List<String> notificationIds) throws Exception {
		for (String nID : notificationIds) {
			Optional<NotificationEntity> notificationEntity = iNotificationRepository.findById(nID);
			if (!notificationEntity.isPresent()) {
				LOGGER.error("Notification not found for id: " + nID);
				throw new Exception("Notification not found for id: " + nID);
			}

			NotificationEntity notification = notificationEntity.get();
			notification.setViewed(true);
			iNotificationRepository.save(notification);
		}

	}

	public Set<NotificationVO> getNotificationsForUser(String userId) {
		Set<NotificationEntity> userNotifications = new HashSet<>();
		Set<NotificationEntity> notificationEntitySet = iNotificationRepository.findByUserIdAndIsViewed(userId, false);
		Set<NotificationVO> notificationVO2 = notificationEntitySet.stream()
				.map(notificationVO -> modelMapper.map(notificationVO, NotificationVO.class))
				.collect(Collectors.toSet());
		return notificationVO2;

	}

	/*public void addNotification() {
		NotificationEntity notificationEntity = new NotificationEntity();
		notificationEntity.setQuestionId("123412341234");
		notificationEntity.setUserId("5b8927e278e4446feb59454e");
		notificationEntity.setViewed(false);
		NotificationEntity notificationEntity1 = new NotificationEntity();
		notificationEntity1.setQuestionId("123412341234");
		notificationEntity1.setUserId("5b89a28278e4440e4442b6c3");
		notificationEntity1.setViewed(false);
		iNotificationRepository.save(notificationEntity1);
		iNotificationRepository.save(notificationEntity);
		
	}*/
}
