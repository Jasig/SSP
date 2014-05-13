package org.jasig.ssp.service.impl;

import org.jasig.ssp.service.PersonSearchService;
import org.jasig.ssp.service.RefreshDirectoryPersonTask;
import org.jasig.ssp.service.external.BatchedTask;
import org.jasig.ssp.util.CallableExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class RefreshPersonDirectoryImpl implements BatchedTask<Void>,
		RefreshDirectoryPersonTask {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(RefreshPersonDirectoryImpl.class);
	
	@Autowired
	PersonSearchService personSearchService;
	
	@Override
	public void exec(CallableExecutor<Void> batchExecutor) {
		
		try{
			personSearchService.refreshDirectoryPerson();
		}catch(Exception exp){
			
		}finally{
			
		}
	}

	@Override
	public Class<Void> getBatchExecReturnType() {
		return Void.TYPE;
	}

}
