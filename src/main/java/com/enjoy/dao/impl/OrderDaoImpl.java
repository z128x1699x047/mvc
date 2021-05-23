package com.enjoy.dao.impl;

import org.springframework.stereotype.Repository;

import com.enjoy.dao.OrderDao;
@Repository
public class OrderDaoImpl implements OrderDao {

	@Override
	public String getDetial(String id) {
		System.out.println("订单");
		return "订单！！！";
	}

}
