/**
 * Licensed to Apereo under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Apereo licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp.factory.reference.impl;

import org.jasig.ssp.dao.reference.ExternalViewDao;
import org.jasig.ssp.factory.reference.AbstractReferenceTOFactory;
import org.jasig.ssp.factory.reference.ExternalViewTOFactory;
import org.jasig.ssp.model.reference.ExternalView;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.reference.ExternalViewTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * ExternalView transfer object factory implementation
 */
@Service
@Transactional(readOnly = true)
public class ExternalViewTOFactoryImpl extends AbstractReferenceTOFactory<ExternalViewTO, ExternalView>
            implements ExternalViewTOFactory {

    @Autowired
	private transient ExternalViewDao dao;


    public ExternalViewTOFactoryImpl() {
        super(ExternalViewTO.class, ExternalView.class);
    }

	@Override
	protected ExternalViewDao getDao() {
		return dao;
	}

	@Override
	public ExternalView from(final ExternalViewTO tObject) throws ObjectNotFoundException {
        final ExternalView model = super.from(tObject);

        model.setUrl(tObject.getUrl());
		model.setVariableUserIdentifier(tObject.getVariableUserIdentifier());
        model.setVariableStudentIdentifier(tObject.getVariableStudentIdentifier());
        model.setEmbedded(tObject.isEmbedded());

        return model;
	}
}