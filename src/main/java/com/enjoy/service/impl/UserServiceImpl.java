package com.enjoy.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.enjoy.dao.UserDao;
import com.enjoy.service.UserService;
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private MessageSource messageSource;
	@Autowired
	private UserDao userDao;
	@Override
	public String getDetial(String id) {
		//messageSource.getMessage(code, args, defaultMessage, locale)
		return userDao.getDetial(id);
	}

}
