package com.enjoy.service;

import java.util.List;

public interface OrderService {
	String getDetail(String id);
	List<EruptSimultaneouslyService.Response> getDetailBatch(List<EruptSimultaneouslyService.Request> requests);
}
