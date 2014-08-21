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
package org.jasig.ssp.factory.impl;

import java.util.Collection;
import java.util.List;

import org.jasig.ssp.factory.EarlyAlertSearchResultTOFactory;
import org.jasig.ssp.model.EarlyAlertSearchResult;
import org.jasig.ssp.transferobject.EarlyAlertSearchResultTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

@Service
@Transactional
public class EarlyAlertSearchResultTOFactoryImpl implements
		EarlyAlertSearchResultTOFactory {


	@Override
	public EarlyAlertSearchResultTO from(EarlyAlertSearchResult model) {
		return new EarlyAlertSearchResultTO(model);
	}

	@Override
	public List<EarlyAlertSearchResultTO> asTOList(
			Collection<EarlyAlertSearchResult> models) {
		final List<EarlyAlertSearchResultTO> tos = Lists.newArrayList();
		if ((models != null) && !models.isEmpty()) {
			for (EarlyAlertSearchResult model : models) {
				tos.add(from(model));
			}
		}
		return tos;
	}
}
