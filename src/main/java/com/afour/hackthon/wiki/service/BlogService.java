package com.afour.hackthon.wiki.service;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ValidationException;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.afour.hackthon.wiki.commons.Constants;
import com.afour.hackthon.wiki.entity.BlogEntity;
import com.afour.hackthon.wiki.entity.UserProfileEntity;
import com.afour.hackthon.wiki.exceptions.NotFoundException;
import com.afour.hackthon.wiki.repository.IBlogRepository;
import com.afour.hackthon.wiki.repository.IUserProfileRepository;
import com.afour.hackthon.wiki.vo.BlogVO;

@Service
public class BlogService {

	private static final Logger LOGGER = LoggerFactory.getLogger(BlogService.class);
	private final ModelMapper modelMapper = new ModelMapper();
	
	@Autowired
	private TaggingService tagService;
	
	@Autowired
	private IUserProfileRepository userProfileRepository;
	
	@Autowired
	private IBlogRepository blogRepository;
	
	public BlogVO getBlog(String blogId, String userId) {

		Optional<BlogEntity> entity =  blogRepository.findById(blogId);
		if(!entity.isPresent()) {
			LOGGER.error("Blog not found for id: " + blogId);
			throw new NotFoundException("Blog not found");
		}
		
		BlogEntity blogEntity = entity.get();
		
		if(StringUtils.isNotBlank(userId)) {
			Set<String> viewedSet = blogEntity.getViewedBy();
			if(CollectionUtils.isEmpty(viewedSet)) {
				viewedSet = new HashSet<>();
			}
			viewedSet.add(userId);
			blogEntity.setViewedBy(viewedSet);
			blogEntity = blogRepository.save(blogEntity);
		}
		
		
		BlogVO blogVO = modelMapper.map(blogEntity, BlogVO.class);
		LOGGER.debug("Blog for id [{}] : {}" , blogId, blogVO);
		return blogVO;
	
	}

	public BlogVO postBlog(BlogVO blogVO) {
		
		String validationMsg = blogVO.validate();
		if(StringUtils.isNotBlank(validationMsg)) {
			throw new ValidationException(validationMsg);
		}
		
		Optional<UserProfileEntity> userEntity = userProfileRepository.findById(blogVO.getCreatedBy().getId());
		if(!userEntity.isPresent()) {
			LOGGER.error("UserProfile for not found for id: " + blogVO.getCreatedBy());
			throw new NotFoundException("UserProfile for not found");
		}
		
		UserProfileEntity userProfileModel = userEntity.get();
		userProfileModel.setKarma(userProfileModel.getKarma() + Constants.QSTN_ASKD_POINT);
		
		Map<String, Object> result = tagService.getTag(blogVO.getDescription(),false);
		
		BlogEntity blogEntity = new BlogEntity();
		blogEntity.setTitle(blogVO.getTitle());
		blogEntity.setDescription(blogVO.getDescription());
		blogEntity.setCreatedBy(userProfileModel);
		blogEntity.setCreatedDate(new Date());
		blogEntity.setTags((Set<String>) result.get("tags"));
		blogEntity.setSpam((boolean) result.get("isSpam"));
		blogEntity = blogRepository.insert(blogEntity);
		
		BlogVO postedBlogVO = modelMapper.map(blogEntity, BlogVO.class);
		return postedBlogVO;
	}

	public BlogVO updateBlog(String blogId, BlogVO blogVO) {
		
		String validationMsg = blogVO.validate();
		if(StringUtils.isNotBlank(validationMsg)) {
			throw new ValidationException(validationMsg);
		}
		
		Optional<BlogEntity> entity =  blogRepository.findById(blogId);
		if(!entity.isPresent()) {
			LOGGER.error("Blog  not found for id: " + blogId);
			throw new NotFoundException("Blog for not found");
		}
		
		BlogEntity currBlogEntity = entity.get();
		
		Map<String, Object> result = tagService.getTag(blogVO.getTitle(),false);
		
		currBlogEntity.setTitle(blogVO.getTitle());
		currBlogEntity.setDescription(blogVO.getDescription());
		currBlogEntity.setModifiedDate(new Date());
		currBlogEntity.setTags((Set<String>) result.get("tags"));
		currBlogEntity.setSpam((boolean) result.get("isSpam"));
		currBlogEntity.setSpammedBy(Collections.EMPTY_SET);
		currBlogEntity = blogRepository.save(currBlogEntity);
		
		BlogVO updatedBlogVO = modelMapper.map(currBlogEntity, BlogVO.class);
		return updatedBlogVO;
		
	}
	
	public Set<BlogVO> getBlogs(String userId) {
		LOGGER.debug("getting blogs posted by user {}" , userId);
		
		List<BlogEntity> blogEntitySet; 
		
		if(StringUtils.isNotBlank(userId)) {
			blogEntitySet = blogRepository.findByUserId(userId, new Sort(Sort.Direction.DESC, "createdDate"));	
		}else {
			blogEntitySet = blogRepository.findAllByNotSpam(new Sort(Sort.Direction.DESC, "createdDate"));
		}
		Set<BlogVO> blogVOs2 = blogEntitySet.stream()
				 					.map(blogEntity -> modelMapper.map(blogEntity, BlogVO.class))
				 					.collect(Collectors.toSet());
		LOGGER.debug("Blogs posted by user {} are {}", userId, blogVOs2);
		return blogVOs2;
	}

}
