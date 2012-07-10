package org.jasig.ssp.factory.impl;

import java.util.Collection;
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

		final PagingWrapper<JournalStepJournalStepDetail> jsJsds = journalStepJournalStepDetailDao
				.getAllForJournalStepDetailAndJournalStep(journalStepDetail
						.getId(), tObject.getJournalStep().getId(),
						new SortingAndPaging(ObjectStatus.ACTIVE));
		if (jsJsds.getResults() == 1) {
			model.setJournalStepJournalStepDetail(jsJsds.getRows().iterator()
					.next());
		} else if (jsJsds.getResults() > 1) {
			final StringBuilder sb = new StringBuilder();
			for (final JournalStepJournalStepDetail journalStepJournalStepDetail : jsJsds
					.getRows()) {
				if (sb.length() > 0) {
					sb.append(", ");
				}

				sb.append(journalStepJournalStepDetail);
			}

			LOGGER.error("Multiple Active JournalStepJournalStepDetail objects exists for a Journal Step and Journal Step Detail: "
					+ sb.toString());
			throw new ValidationException(
					"Too many associations for JournalStep and JournalStepDetail were found.");
		} else {
			throw new ValidationException(
					"Could not find an association for JournalStep and JournalStepDetail.");
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