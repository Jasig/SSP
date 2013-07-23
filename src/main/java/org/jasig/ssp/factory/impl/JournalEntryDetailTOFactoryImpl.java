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
import java.util.Set;

import org.jasig.ssp.dao.JournalEntryDetailDao;
import org.jasig.ssp.dao.reference.JournalStepJournalStepDetailDao;
import org.jasig.ssp.factory.AbstractAuditableTOFactory;
import org.jasig.ssp.factory.JournalEntryDetailTOFactory;
import org.jasig.ssp.model.JournalEntry;
import org.jasig.ssp.model.JournalEntryDetail;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.reference.JournalStepDetail;
import org.jasig.ssp.model.reference.JournalStepJournalStepDetail;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.reference.JournalStepDetailService;
import org.jasig.ssp.transferobject.JournalEntryDetailTO;
import org.jasig.ssp.transferobject.reference.ReferenceLiteTO;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * JournalEntryDetail transfer object factory implementation
 */
@Service
@Transactional(readOnly = true)
public class JournalEntryDetailTOFactoryImpl
		extends
		AbstractAuditableTOFactory<JournalEntryDetailTO, JournalEntryDetail>
		implements JournalEntryDetailTOFactory {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(JournalEntryDetailTOFactoryImpl.class);

	@Autowired
	private transient JournalStepDetailService journalStepDetailService;

	public JournalEntryDetailTOFactoryImpl() {
		super(JournalEntryDetailTO.class,
				JournalEntryDetail.class);
	}

	@Autowired
	private transient JournalEntryDetailDao dao;

	@Autowired
	private transient JournalStepJournalStepDetailDao journalStepJournalStepDetailDao;

	@Override
	protected JournalEntryDetailDao getDao() {
		return dao;
	}

	@Override
	public JournalEntryDetail from(final JournalEntryDetailTO tObject)
			throws ObjectNotFoundException {
		throw new UnsupportedOperationException(
				"JournalEntryDetailTOs must be converted with the from(JournalEntryDetailTO, JournalStepDetail) method.");
	}

	@Override
	public Set<JournalEntryDetail> asSet(
			final Collection<JournalEntryDetailTO> tObjects)
			throws ObjectNotFoundException {
		throw new UnsupportedOperationException(
				"JournalEntryDetailTO sets must be converted with the asSet(Collection<JournalEntryDetailTO>, JournalEntry) method.");
	}

	public JournalEntryDetail from(final JournalEntryDetailTO tObject,
			final JournalEntry journalEntry,
			final JournalStepDetail journalStepDetail)
			throws ObjectNotFoundException, ValidationException {
		final JournalEntryDetail model = super.from(tObject);

		if (tObject.getJournalStep() == null) {
			throw new ValidationException("Missing JournalStep");
		}

		if (journalEntry == null) {
			throw new ValidationException("Missing JournalEntry");
		}

		if (journalStepDetail == null) {
			throw new ValidationException("Missing JournalStepDetail");
		}

		model.setJournalEntry(journalEntry);

		// Would be better served by not even trying to store the pointer to the
		// association if the front end isn't going to preserve that relationship.
		// This is all just ending up way too complicated.
		final PagingWrapper<JournalStepJournalStepDetail> jsJsds = journalStepJournalStepDetailDao
				.getAllForJournalStepDetailAndJournalStep(journalStepDetail
						.getId(), tObject.getJournalStep().getId(),
						SortingAndPaging.createForSingleSortWithPaging(
								ObjectStatus.ALL, 0, -1, "modifiedDate", "DESC", null));

		List<JournalStepJournalStepDetail> inactiveJsJsds = Lists.newArrayList();

		for ( JournalStepJournalStepDetail jsJsd : jsJsds.getRows() ) {
			if ( jsJsd.getObjectStatus() == ObjectStatus.ACTIVE ) {
				// just take the first
				model.setJournalStepJournalStepDetail(jsJsd);
				break;
			} else {
				inactiveJsJsds.add(jsJsd);
			}
		}

		if ( model.getJournalStepJournalStepDetail() == null ) {
			if ( inactiveJsJsds.isEmpty() ) {
				throw new ValidationException(
						"Could not find an association for JournalStep and JournalStepDetail.");
			}

			// We have to allow this b/c we have no good way given the typical
			// usage pattern of these factory objects of figuring out whether
			// this is really an invalid request or just a reiteration of a
			// previously selected association that just happened to have been
			// soft-deleted later on.
			model.setJournalStepJournalStepDetail(inactiveJsJsds.get(0));
		}

		return model;
	}

	@Override
	public Set<JournalEntryDetail> asSet(
			final Collection<JournalEntryDetailTO> tObjects,
			final JournalEntry journalEntry)
			throws ObjectNotFoundException {
		final Set<JournalEntryDetail> models = Sets.newHashSet();
		for (final JournalEntryDetailTO tObject : tObjects) {
			for (final ReferenceLiteTO<JournalStepDetail> jsd : tObject
					.getJournalStepDetails()) {
				try {
					models.add(from(tObject, journalEntry,
							journalStepDetailService.get(jsd.getId())));
				} catch (final ValidationException exc) {
					throw new ObjectNotFoundException(jsd.getId(),
							"JournalStepDetail", exc);
				}
			}
		}

		return models;
	}
}