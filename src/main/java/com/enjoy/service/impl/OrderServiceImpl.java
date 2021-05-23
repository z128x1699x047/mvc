package com.enjoy.service.impl;

import com.enjoy.service.EruptSimultaneouslyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enjoy.dao.OrderDao;
import com.enjoy.service.OrderService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderDao orderDao;
	
	@Override
	public String getDetail(String id) {
		return orderDao.getDetial(id);
	}

	@Override
	public List<EruptSimultaneouslyService.Response> getDetailBatch(List<EruptSimultaneouslyService.Request> requests) {
		List<EruptSimultaneouslyService.Response> responses = new ArrayList<>();
		for(EruptSimultaneouslyService.Request request:requests){
			EruptSimultaneouslyService.Response response = new EruptSimultaneouslyService.Response();
			response.setSerialNo(request.getSerialNo());
			response.setOrderMoney(new Random().nextInt(1000));
		}
		return responses;
	}

}
