package com.xws.application.service;

import com.xws.application.model.BusinessProcess;
import com.xws.application.repository.BusinessProcessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusinessProcessService {

	@Autowired
	private BusinessProcessRepository repository;

	public void save(BusinessProcess process, String documentId) throws Exception {
		repository.store(process, documentId);
	}

}
