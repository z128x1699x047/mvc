package com.enjoy.dao.impl;

import org.springframework.stereotype.Repository;

import com.enjoy.dao.UserDao;
@Repository
public class UserDaoImpl implements UserDao {

	@Override
	public String getDetial(String id) {
		return "cxy";
	}

}
