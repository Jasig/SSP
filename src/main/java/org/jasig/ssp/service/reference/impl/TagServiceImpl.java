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

import java.util.List;

import org.jasig.ssp.dao.reference.TagDao;
import org.jasig.ssp.model.reference.Tag;
import org.jasig.ssp.service.reference.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Tag service implementation
 * 
 * @author archna.jindal
 * 
 */
@Service
@Transactional
public class TagServiceImpl extends
		AbstractReferenceService<Tag>
		implements TagService {

	@Autowired
	transient private TagDao dao;

	@Override
	protected TagDao getDao() {
		return dao;
	}

	/**
	 * Sets the data access object
	 * 
	 * @param dao
	 *            the data access object
	 */
	protected void setDao(final TagDao dao) {
		this.dao = dao;
	}

	@Override
	public List<Tag> facetSearch(String programCode, String termCode) {
		return dao.facetSearch(programCode,termCode);
	}
}