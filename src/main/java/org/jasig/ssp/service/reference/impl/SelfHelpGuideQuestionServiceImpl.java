/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp.service.reference.impl;

import java.util.ArrayList;

import org.jasig.ssp.dao.reference.SelfHelpGuideQuestionDao;
import org.jasig.ssp.model.reference.SelfHelpGuideQuestion;
import org.jasig.ssp.service.reference.SelfHelpGuideQuestionService;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SelfHelpGuideQuestionServiceImpl extends
		AbstractReferenceService<SelfHelpGuideQuestion>
		implements SelfHelpGuideQuestionService {

	@Autowired
	transient private SelfHelpGuideQuestionDao dao;

	protected void setDao(final SelfHelpGuideQuestionDao dao) {
		this.dao = dao;
	}

	@Override
	protected SelfHelpGuideQuestionDao getDao() {
		return dao;
	}

	@Override
	public PagingWrapper<SelfHelpGuideQuestion> getAllForParent(
			SortingAndPaging createForSingleSort, String selfReferenceGuideId) {
		if(selfReferenceGuideId == null)
		{
			return new PagingWrapper<SelfHelpGuideQuestion>(new ArrayList<SelfHelpGuideQuestion>());
		}
		return dao.getAllForParent(createForSingleSort,selfReferenceGuideId);
	}
	
	
}
