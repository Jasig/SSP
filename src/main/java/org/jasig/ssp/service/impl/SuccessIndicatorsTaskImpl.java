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
package org.jasig.ssp.service.impl;

import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.hibernate.FlushMode;
import org.hibernate.SessionFactory;
import org.jasig.ssp.dao.PersonSuccessIndicatorAlertDao;
import org.jasig.ssp.dao.PersonSuccessIndicatorCountDao;
import org.jasig.ssp.dao.external.TermDao;
import org.jasig.ssp.model.EarlyAlert;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonSuccessIndicatorAlert;
import org.jasig.ssp.model.PersonSuccessIndicatorCount;
import org.jasig.ssp.model.external.Term;
import org.jasig.ssp.model.reference.SuccessIndicator;
import org.jasig.ssp.service.EarlyAlertService;
import org.jasig.ssp.service.EvaluatedSuccessIndicatorService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.SuccessIndicatorsTask;
import org.jasig.ssp.service.reference.CampusService;
import org.jasig.ssp.service.reference.ConfigService;
import org.jasig.ssp.service.reference.SuccessIndicatorService;
import org.jasig.ssp.transferobject.EvaluatedSuccessIndicatorTO;
import org.jasig.ssp.transferobject.SuccessIndicatorEvaluation;
import org.jasig.ssp.util.CallableExecutor;
import org.jasig.ssp.util.collections.Pair;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortDirection;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.util.transaction.WithTransaction;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;


/**
 * Implements the Success Indicators background job that examines
 *   only configured success indicators for students inside of
 *   SSP. Depending on the config set it can store a count
 *   of High, Medium, and Low indicators and/or creates an
 *   Early Alert on Low.
 */
@Service
public class SuccessIndicatorsTaskImpl implements SuccessIndicatorsTask {

	private static final Logger LOGGER = LoggerFactory.getLogger(SuccessIndicatorsTaskImpl.class);

    private static final Class<Pair<Long, Long>> BATCH_RETURN_TYPE =
            (Class<Pair<Long, Long>>) new Pair<Long,Long>(null,null).getClass();

    //reusing the external person stuff because it fits and this job should be quicker regardless
    private static final String BATCH_SIZE_CONFIG_NAME = "task_external_person_sync_batch_size";
    private static final int DEFAULT_BATCH_SIZE = 100;
    private static final String MAX_BATCHES_PER_EXECUTION_CONFIG_NAME = "task_external_person_sync_max_batches_per_exec";
    private static final String EARLY_ALERT_CAMPUS_CODE_CONFIG_NAME = "task_success_indicator_early_alert_campus_code";
    private static final int DEFAULT_MAX_BATCHES_PER_EXECUTION = -1; // unlimited

    @Autowired
    private ConfigService configService;

	@Autowired
	private SuccessIndicatorService successIndicatorService;

    @Autowired
    private PersonService personService;

    @Autowired
    private EvaluatedSuccessIndicatorService evaluatedSuccessIndicatorService;

    @Autowired
    private EarlyAlertService earlyAlertService;

    @Autowired
    private CampusService campusService;

    @Autowired
    private PersonSuccessIndicatorCountDao personSuccessIndicatorCountDao;

    @Autowired
    private PersonSuccessIndicatorAlertDao personSuccessIndicatorAlertDao;

    @Autowired
    private TermDao termDao;

	@Autowired
	private transient WithTransaction withTransaction;

    @Autowired
    protected transient SessionFactory sessionFactory;

    private transient long nextPersonIndex = 0;
    private static Map<String, Pair<Boolean, Boolean>> configuredSuccessIndicatorsByCode;
    private static List<SuccessIndicator> configuredSuccessIndicators;



    // intentionally not transactional; each iteration should be its own transaction.
    @Override
    public void exec(CallableExecutor<Pair<Long,Long>> batchExec) {

        if ( Thread.currentThread().isInterrupted() ) {
            LOGGER.info("Abandoning success indicator count/alert task because of thread interruption");
            return;
        }

        //Check if we have configured success indicators, no point in proceeding if none are set
        if (MapUtils.isEmpty(configuredSuccessIndicatorsByCode)) {
            final List<SuccessIndicator> rawConfiguredSuccessIndicators =
                    successIndicatorService.getWithShowInCaseloadOrGenerateEarlyAlert(true, true);

            if (CollectionUtils.isNotEmpty(rawConfiguredSuccessIndicators)) {
                //process success indicators into Map
                configuredSuccessIndicatorsByCode = Maps.newHashMap();
                for (final SuccessIndicator rawIndicator : rawConfiguredSuccessIndicators) {
                    configuredSuccessIndicatorsByCode.put(rawIndicator.getCode(),
                            new Pair<>(rawIndicator.isShowInCaseload(), rawIndicator.isGenerateEarlyAlert()));
                }
                configuredSuccessIndicators = rawConfiguredSuccessIndicators;
            } else {
                LOGGER.info("Abandoning success indicator count/alert task because no configured success indicators were found!");
                return;
            }
        }

        LOGGER.info("BEGIN : Success indicator count/alert task...");

        int recordsProcessed = 0;
        int batch = 0;
        Exception error = null;
        int maxBatchesAllowed = DEFAULT_MAX_BATCHES_PER_EXECUTION;
        while ( true ) {
            // Check this config every time in case someone wants to abort a
            // long-running execution.
            maxBatchesAllowed = getMaxBatchesAllowed();
            if ( maxBatchesAllowed == 0 ) {
                LOGGER.info("Abandoning success indicator count/alert task at position [{}]"
                                + " and batch [{}] because the  batch limit has been"
                                + " set to zero. Records processed: [{}]",
                        new Object[] {nextPersonIndex, batch, recordsProcessed });
                break;
            }

            error = null;
            batch++;

            // again, look up config every time to allow for relatively immediate
            // control over runnaway executions
            int batchSize = getBatchSize();
            final SortingAndPaging sAndP = SortingAndPaging.createForSingleSortWithPaging(
                    ObjectStatus.ACTIVE,
                    new BigDecimal(nextPersonIndex).intValueExact(),  // API mismatch... see more comments below
                    batchSize,
                    "username",
                    SortDirection.ASC.toString(), null);

            Pair<Long,Long> processedOfTotal = null;
            try {
                if ( batchExec == null ) {
                    processedOfTotal = processIndicatorsForPersonInTransaction(sAndP);
                } else {
                    processedOfTotal = batchExec.exec(new Callable<Pair<Long, Long>>() {
                        @Override
                        public Pair<Long, Long> call() throws Exception {
                            return processIndicatorsForPersonInTransaction(sAndP);
                        }
                    });
                }
            } catch ( InterruptedException e ) {
                Thread.currentThread().interrupt(); // reassert
            } catch (final Exception e) {
                error = e;
            } finally {

                if ( processedOfTotal == null ) {
                    if ( error != null ) {
                        LOGGER.error("Abandoning success indicator count/alert task at"
                                        + " position [{}] and batch [{}] because of a"
                                        + " processing error. Will resume at that"
                                        + " position at the next execution.",
                                new Object[] {nextPersonIndex, batch - 1, error });
                        break;
                    }
                    if ( Thread.currentThread().isInterrupted() ) {
                        LOGGER.error("Abandoning success indicator count/alert task at"
                                        + " position [{}] and batch [{}] because of an"
                                        + " InterruptionException. Will resume at that"
                                        + " position at the next execution.",
                                nextPersonIndex, batch - 1);
                        break;
                    }
                    // programmer error, no clue what to do so let the NPE's fly...
                }

                nextPersonIndex += processedOfTotal.getFirst();
                recordsProcessed += processedOfTotal.getFirst();

                LOGGER.info("Processed [{}] of [{}] candidate person records"
                                + " as of batch [{}] of [{}]. Total records processed [{}].",
                        new Object[] {nextPersonIndex, processedOfTotal.getSecond(),
                                batch, maxBatchesAllowed, recordsProcessed });

                if ( processedOfTotal.getFirst() == 0 ) {
                    // shouldn't happen but want to guard against endless loops
                    LOGGER.debug("Appear to be more records to process but"
                            + " last batch processed zero records. Exiting"
                            + " success indicator count/alert task.");
                    nextPersonIndex = 0;
                    break;
                }

                if ( maxBatchesAllowed > 0 && batch >= getMaxBatchesAllowed() ) {
                    LOGGER.debug("No more batches allowed for this execution."
                            + " Exiting success indicator count/alert task. Will resume at"
                            + " index [{}] on next execution.", nextPersonIndex);
                    break;
                }

                if ( nextPersonIndex >= processedOfTotal.getSecond() ) {
                    nextPersonIndex = 0;
                    LOGGER.debug("Reached the end of the list of candidate"
                            + " persons for success indicator count/alert task. More batches are allowed, so"
                            + " starting over at index 0.");

                    // no break!!
                }

                if ( recordsProcessed >= processedOfTotal.getSecond() ) {
                    LOGGER.debug("More batches allowed, but all candidate" +
                            " person records have already been processed"
                            + " in this execution. Will resume at index"
                            + " [{}] on next execution.", nextPersonIndex);
                    break;
                }

                // Mismatch between the PagedResponse and SortingAndPaging
                // APIs mean we can't actually deal with total result sets
                // larger than Integer.MAX_VALUE
                if ( nextPersonIndex > Integer.MAX_VALUE ) {
                    LOGGER.warn("Cannot process more than {} total persons,"
                                    + " even across executions. Abandoning and"
                                    + " resetting success indicator count/alert task.",
                            Integer.MAX_VALUE);
                    nextPersonIndex = 0;
                    break;
                }

            }
        }

        LOGGER.info("END : success indicator count/alert task.");
    }

    @Override
    public Class<Pair<Long, Long>> getBatchExecReturnType() {
        return BATCH_RETURN_TYPE;
    }

    protected Pair<Long, Long> processIndicatorsForPersonInTransaction(final SortingAndPaging sAndP) throws Exception {
        return withTransaction.withNewTransaction(new Callable<Pair<Long, Long>>() {
            @Override
            public Pair<Long, Long> call() throws Exception {
                return processIndicatorsForPerson(sAndP);
            }
        });
    }

    protected Pair<Long,Long> processIndicatorsForPerson(final SortingAndPaging sAndP) throws InterruptedException, ObjectNotFoundException {

        sessionFactory.getCurrentSession().setFlushMode(FlushMode.COMMIT);

        // Use InterruptedExceptions instead of manipulating return value b/c
        // the return value actually means "total number of possible processable"
        // rows, so if it is returned gracefully, the caller will likely
        // incorrectly update its internal state, as is the case with the
        // scheduled job call site.

        if ( Thread.currentThread().isInterrupted() ) {
            LOGGER.info("Abandoning success indicator count/alert task because of thread interruption");
            throw new InterruptedException();
        }

        LOGGER.info("success indicator count/alert task Selecting [{}] records starting at [{}]",
                sAndP.getMaxResults(), nextPersonIndex);

        final PagingWrapper<Person> people = personService.getAll(sAndP);

        if ( people.getRows().isEmpty() ) {
            LOGGER.info("success indicator count/alert task found 0 records starting at [{}]", nextPersonIndex);
            return new Pair(0L, people.getResults());
        }

        if ( Thread.currentThread().isInterrupted() ) {
            LOGGER.info("Abandoning success indicator count/alert task because of thread interruption");
            throw new InterruptedException();
        }

        long peopleCnt = 0;
        for (final Person person : people) {
            peopleCnt++;

            if (Thread.currentThread().isInterrupted()) {
                LOGGER.info("Abandoning success indicator count/alert task because of thread interruption on person {}",
                        person.getSchoolId());
                throw new InterruptedException();
            }

            LOGGER.debug("Evaluating configured Success Indicators for person schoolId {}", person.getSchoolId());

            final List<EvaluatedSuccessIndicatorTO> evaluatedSuccessIndicators =
                    evaluatedSuccessIndicatorService.getForPerson(person.getId(), ObjectStatus.ALL, configuredSuccessIndicators);

            if (Thread.currentThread().isInterrupted()) {
                LOGGER.info("Abandoning success indicator count/alert task because of thread interruption on person {}",
                        person.getSchoolId());
                throw new InterruptedException();
            }

            if (CollectionUtils.isNotEmpty(evaluatedSuccessIndicators)) {
                processEvaluatedIndicators(person, evaluatedSuccessIndicators);
            } else {
                LOGGER.debug("Error in indicator count/alert task because no evaluated success indicators returned for person: {}",
                        person.getSchoolId());
            }
        }

        return new Pair(peopleCnt, people.getResults());
    }

    private int getMaxBatchesAllowed() {
        String maxBatchesStr =
                configService.getByNameNullOrDefaultValue(MAX_BATCHES_PER_EXECUTION_CONFIG_NAME);
        int maxBatches = DEFAULT_MAX_BATCHES_PER_EXECUTION;
        try {
            maxBatches = Integer.parseInt(maxBatchesStr);
            LOGGER.debug("Using [{}] configured batches-per-execution limit of"
                            + " [{}]. (Negative values treated as unlimited.)",
                    MAX_BATCHES_PER_EXECUTION_CONFIG_NAME, maxBatches);
        } catch ( NumberFormatException e ) {
            // only info b/c this thing is potentially called so frequently and
            // the defaults are probably fine, so we'd just be spamming the logs
            // with a warn or higher
            LOGGER.info("Failed to parse [{}] config [{}] to an integer. Falling"
                            + " back to [{}]. (Negative values treated as unlimited.)",
                    new Object[]{MAX_BATCHES_PER_EXECUTION_CONFIG_NAME,
                            maxBatchesStr, maxBatches});
        }
        return maxBatches;
    }

    private int getBatchSize() {
        String batchSizeStr = configService.getByNameNullOrDefaultValue(BATCH_SIZE_CONFIG_NAME);
        int batchSize = DEFAULT_BATCH_SIZE;
        try {
            batchSize = Integer.parseInt(batchSizeStr);
            LOGGER.debug("Using [{}] configured batch size of [{}]. (Negative values treated as unlimited.)",
                    BATCH_SIZE_CONFIG_NAME, batchSize);
        } catch ( NumberFormatException e ) {
            // only info b/c this thing is potentially called so frequently and
            // the defaults are probably fine, so we'd just be spamming the logs
            // with a warn or higher
            LOGGER.info("Failed to parse [{}] config [{}] to an integer. Falling"
                            + " back to [{}]. (Negative values treated as unlimited.)",
                    new Object[]{BATCH_SIZE_CONFIG_NAME,
                            batchSizeStr, batchSize});
        }
        return batchSize;
    }

    /**
     * Loop through evaluated success indicators for person and store count and/or generate alert depending on config
     */
    private void processEvaluatedIndicators(final Person person,
                                            final List<EvaluatedSuccessIndicatorTO> evaluatedSuccessIndicators) {
        int lowCount = 0;
        int medCount = 0;
        int highCount = 0;
        int lowCountsAlertedOn = 0;
        Map<UUID, Boolean> lowMap = getPersonSuccessIndicatorAlertMap(person);

        for (final EvaluatedSuccessIndicatorTO evaluatedIndicator : evaluatedSuccessIndicators) {
            if (configuredSuccessIndicatorsByCode.containsKey(evaluatedIndicator.getIndicatorCode())) {

                //counts
                if (configuredSuccessIndicatorsByCode.get(evaluatedIndicator.getIndicatorCode()).getFirst() == true) {
                    if (evaluatedIndicator.getEvaluation().equals(SuccessIndicatorEvaluation.LOW)) {
                        LOGGER.trace("RESULT: [" + evaluatedIndicator.getEvaluation().toString() + "|LOW]");
                        lowCount++;
                    } else if (evaluatedIndicator.getEvaluation().equals(SuccessIndicatorEvaluation.MEDIUM)) {
                        LOGGER.trace("RESULT: [" + evaluatedIndicator.getEvaluation().toString() + "|MEDIUM]");
                        medCount++;
                    } else if (evaluatedIndicator.getEvaluation().equals(SuccessIndicatorEvaluation.HIGH)) {
                        LOGGER.trace("RESULT: [" + evaluatedIndicator.getEvaluation().toString() + "|HIGH]");
                        highCount++;
                    } else {
                        LOGGER.trace("RESULT: [" + evaluatedIndicator.getEvaluation().toString() + "|DEFAULT]");
                        //likely is DEFAULT or NO DATA and we don't count that
                    }
                }

                //alerts
                if (configuredSuccessIndicatorsByCode.get(evaluatedIndicator.getIndicatorCode()).getSecond() == true) {
                    /*
                     * TODO: in the future we'd take a count of alerts below and compare to previous value. If less,
                     *  we would minus the count as a indicator improved. If it's more we'd either create a new EA
                     *   or if possible find the one already created (it would be unique because this is system
                     *   created) and add a Response. There are still things to figure out, for example how
                     *    much detailed info can we provide? (name, value, evaluation) etc.
                     */
                    if (evaluatedIndicator.getEvaluation().equals(SuccessIndicatorEvaluation.LOW)) {
                        LOGGER.trace("EA CREATE: [" + evaluatedIndicator.getEvaluation().toString() + "]");
                        lowCountsAlertedOn++;
                        updateLowMap(lowMap,evaluatedIndicator.getIndicatorId());
                    } else {
                        LOGGER.trace("EA NOT CREATED: [" + evaluatedIndicator.getEvaluation().toString() + "]");
                    }
                }

            } else {
                LOGGER.debug("Error in indicator count/alert task because Success Indicator not found in Map for: {} and person: {}",
                        evaluatedIndicator.getIndicatorCode(), person.getSchoolId());
            }
        }

        //save results
        personSuccessIndicatorCountDao.deleteAllSuccessIndicatorCountsForPerson(person.getId()); //for ease at this time delete all existing and flush
        final PersonSuccessIndicatorCount countResult = new PersonSuccessIndicatorCount();
        countResult.setPerson(person);
        countResult.setLowCount(lowCount);
        countResult.setMediumCount(medCount);
        countResult.setHighCount(highCount);
        countResult.setLowAlertCount(lowCountsAlertedOn);
        personSuccessIndicatorCountDao.save(countResult); //save the new or updated count

        processEarlyAlert(person, lowMap);

    } //end processEvaluatedSuccessIndicators

    private Map<UUID, Boolean> getPersonSuccessIndicatorAlertMap (Person person) {
        Map<UUID, Boolean> map = new HashMap<>();
        for (PersonSuccessIndicatorAlert personSuccessIndicatorAlert: personSuccessIndicatorAlertDao.get(person)) {
            map.put(personSuccessIndicatorAlert.getSuccessIndicator().getId(), new Boolean(false));
        }
        return map;
    }

    private void updateLowMap(Map<UUID, Boolean> map, UUID id) {
        map.put(id, new Boolean(true));
    }

    private void processEarlyAlert(Person person, Map<UUID, Boolean> map) {
        for (UUID id : map.keySet()) {
            SuccessIndicator successIndicator = getSuccessIndicator(id);
            PersonSuccessIndicatorAlert personSuccessIndicatorAlert = personSuccessIndicatorAlertDao.get(person, successIndicator);
            Boolean hasLowSuccessInd = map.get(id);
            if (hasLowSuccessInd && personSuccessIndicatorAlert == null) {
                createEarlyAlert(person, successIndicator);
                personSuccessIndicatorAlert = new PersonSuccessIndicatorAlert();
                personSuccessIndicatorAlert.setPerson(person);
                personSuccessIndicatorAlert.setSuccessIndicator(successIndicator);
                personSuccessIndicatorAlertDao.save(personSuccessIndicatorAlert);
            } else if (!hasLowSuccessInd) {
                personSuccessIndicatorAlertDao.delete(personSuccessIndicatorAlert);
            }
        }
    }

    private SuccessIndicator getSuccessIndicator (UUID id) {
        try {
            return successIndicatorService.get(id);
        } catch (ObjectNotFoundException onfe) {
            LOGGER.info("Abandoning success indicator task impl because success indicator: {} was not found", id);
            throw new RuntimeException(onfe);
        }

    }
    private void createEarlyAlert(Person person, SuccessIndicator successIndicator) {
        try {
            EarlyAlert earlyAlert = new EarlyAlert();
            earlyAlert.setPerson(person);
            earlyAlert.setCampus(campusService.getByCode(configService.getByNameException(EARLY_ALERT_CAMPUS_CODE_CONFIG_NAME)));
            earlyAlert.setComment("Low Success Indicator Alert: " + successIndicator.getName() + " - " + successIndicator.getDescription());
            earlyAlert.setCourseTermCode(getCurrentOrNextTerm());
            earlyAlertService.create(earlyAlert);
        } catch (ObjectNotFoundException e) {
            LOGGER.info("Error creating Low Success Indicator Alert for person {} and success indicator {}", person.getId(), successIndicator.getId());
            LOGGER.info("Low Success Indicator Alert Error", e);
        } catch (ValidationException e) {
            LOGGER.info("Error creating Low Success Indicator Alert for person {} and success indicator {}", person.getId(), successIndicator.getId());
            LOGGER.info("Low Success Indicator Alert Error", e);
        }
    }

    private String getCurrentOrNextTerm () throws ObjectNotFoundException {
        Term term = termDao.getCurrentTerm();
        if (term == null) {
            term = termDao.getNextTerm(new Date());
        }
        if (term != null) {
            return term.getCode();
        } else {
            return null;
        }
    }
}