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
package org.jasig.ssp.factory.reference.impl;

import org.jasig.ssp.dao.reference.TagDao;
import org.jasig.ssp.factory.reference.AbstractReferenceTOFactory;
import org.jasig.ssp.factory.reference.TagTOFactory;
import org.jasig.ssp.model.reference.Tag;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.reference.TagTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Tag transfer object factory implementation
 * 
 * 
 */
@Service
@Transactional(readOnly = true)
public class TagTOFactoryImpl
		extends
		AbstractReferenceTOFactory<TagTO, Tag>
		implements TagTOFactory {

	public TagTOFactoryImpl() {
		super(TagTO.class,
				Tag.class);
	}

	@Autowired
	private transient TagDao dao;

	@Override
	protected TagDao getDao() {
		return dao;
	}

	@Override
	public Tag from(final TagTO tObject) throws ObjectNotFoundException {
		Tag model = super.from(tObject);
		model.setCode(tObject.getCode());
		return model;
	}
}