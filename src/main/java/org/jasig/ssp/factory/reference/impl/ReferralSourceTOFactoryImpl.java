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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.jasig.ssp.dao.reference.ReferralSourceDao;
import org.jasig.ssp.factory.reference.AbstractReferenceTOFactory;
import org.jasig.ssp.factory.reference.ReferralSourceTOFactory;
import org.jasig.ssp.model.reference.ReferralSource;
import org.jasig.ssp.transferobject.reference.ReferralSourceTO;

@Service
@Transactional(readOnly = true)
public class ReferralSourceTOFactoryImpl extends
		AbstractReferenceTOFactory<ReferralSourceTO, ReferralSource>
		implements ReferralSourceTOFactory {

	public ReferralSourceTOFactoryImpl() {
		super(ReferralSourceTO.class, ReferralSource.class);
	}

	@Autowired
	private transient ReferralSourceDao dao;

	@Override
	protected ReferralSourceDao getDao() {
		return dao;
	}

}
