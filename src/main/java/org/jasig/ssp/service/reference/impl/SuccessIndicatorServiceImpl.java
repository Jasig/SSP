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

import org.apache.commons.lang.StringUtils;
import org.jasig.ssp.dao.reference.AbstractReferenceAuditableCrudDao;
import org.jasig.ssp.dao.reference.SuccessIndicatorDao;
import org.jasig.ssp.model.SuccessIndicatorEvaluationType;
import org.jasig.ssp.model.SuccessIndicatorGroup;
import org.jasig.ssp.model.reference.SuccessIndicator;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.reference.SuccessIndicatorService;
import org.jasig.ssp.transferobject.SuccessIndicatorEvaluation;
import org.jasig.ssp.transferobject.reference.SuccessIndicatorTO;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SuccessIndicatorServiceImpl extends AbstractReferenceService<SuccessIndicator>
        implements SuccessIndicatorService {

    private static final String MODEL_CODE_SYSTEM_PREFIX = "system";
    private static final String INDICATOR_CODE_SYSTEM_PREFIX = "system";

    @Autowired
    private SuccessIndicatorDao dao;

    @Override
    protected AbstractReferenceAuditableCrudDao<SuccessIndicator> getDao() {
        return dao;
    }

    @Override
    @Deprecated
    public SuccessIndicator create(SuccessIndicator obj) throws ObjectNotFoundException, ValidationException {
        throw new UnsupportedOperationException("Use the create(SuccessIndicatorTO) overload");
    }

    @Override
    @Deprecated
    public SuccessIndicator save(SuccessIndicator obj) throws ObjectNotFoundException, ValidationException {
        throw new UnsupportedOperationException("Use the save(SuccessIndicatorTO) overload");
    }

    @Override
    @Transactional(rollbackFor = ValidationException.class)
    public SuccessIndicator create(SuccessIndicatorTO spec) throws ValidationException {
        if (spec.getId() != null) {
            throw new ValidationException("Must not specify an ID");
        }

        // Yes, lots of this is duplicated in the Hibernate annotations, but we have no good way of ensuring that
        // those validations trigger nor processing failures in a UX-meaningful way.
        final SuccessIndicatorGroup indicatorGroup = spec.getIndicatorGroup();
        if ( indicatorGroup == SuccessIndicatorGroup.STUDENT || indicatorGroup == SuccessIndicatorGroup.INTERVENTION ) {
            throw new ValidationException("Programmatic creation of SuccessIndicators in the STUDENT and INTERVENTION groups is not allowed.");
        }
        if ( indicatorGroup == null ) {
            throw new ValidationException("Must specify an indicator group");
        }
        final SuccessIndicatorEvaluationType evaluationType = spec.getEvaluationType();
        if ( evaluationType == null ) {
            throw new ValidationException("Must specify an evaluation type");
        }
        String modelCode = spec.getModelCode();
        if ( StringUtils.isBlank(modelCode) ) {
            throw new ValidationException("Must specify a model code");
        }
        modelCode = modelCode.trim();
        if ( modelCode.toLowerCase().startsWith(MODEL_CODE_SYSTEM_PREFIX) ) {
            throw new ValidationException("The [" + MODEL_CODE_SYSTEM_PREFIX + "] model code prefix is reserved.");
        }
        String code = spec.getCode();
        if ( StringUtils.isBlank(code) ) {
            throw new ValidationException("Must specify a code");
        }
        code = code.trim();
        if ( code.toLowerCase().startsWith(INDICATOR_CODE_SYSTEM_PREFIX) ) {
            throw new ValidationException("The [" + INDICATOR_CODE_SYSTEM_PREFIX + "] indicator code prefix is reserved.");
        }
        final Integer sortOrder = spec.getSortOrder();
        if ( sortOrder == null ) {
            throw new ValidationException("Must specify a sort order");
        }
        final String name = spec.getName();
        if ( StringUtils.isBlank(name) ) {
            throw new ValidationException("Must specify a name");
        }
        final String modelName = spec.getModelName();
        if ( StringUtils.isBlank(modelName) ) {
            throw new ValidationException("Must specify a model name");
        }
        final String description = spec.getDescription();
        if ( StringUtils.isBlank(description) ) {
            throw new ValidationException("Must specify a description");
        }

        SuccessIndicator model = new SuccessIndicator();

        model.setCode(code);
        model.setName(spec.getName());
        model.setDescription(description);
        model.setInstruction(spec.getInstruction());
        model.setModelCode(modelCode);
        model.setModelName(modelName);
        model.setIndicatorGroup(indicatorGroup);
        model.setEvaluationType(evaluationType);
        model.setScaleEvaluationHighFrom(spec.getScaleEvaluationHighFrom());
        model.setScaleEvaluationHighTo(spec.getScaleEvaluationHighTo());
        model.setScaleEvaluationMediumFrom(spec.getScaleEvaluationMediumFrom());
        model.setScaleEvaluationMediumTo(spec.getScaleEvaluationMediumTo());
        model.setScaleEvaluationLowFrom(spec.getScaleEvaluationLowFrom());
        model.setScaleEvaluationLowTo(spec.getScaleEvaluationLowTo());
        model.setStringEvaluationHigh(spec.getStringEvaluationHigh());
        model.setStringEvaluationMedium(spec.getStringEvaluationMedium());
        model.setStringEvaluationLow(spec.getStringEvaluationLow());
        model.setNoDataMatchesEvaluation(spec.getNoDataMatchesEvaluation() == null ? SuccessIndicatorEvaluation.DEFAULT : spec.getNoDataMatchesEvaluation());
        model.setNoDataExistsEvaluation(spec.getNoDataExistsEvaluation() == null ? SuccessIndicatorEvaluation.DEFAULT : spec.getNoDataMatchesEvaluation());
        model.setObjectStatus(spec.getObjectStatus());
        model.setSortOrder(sortOrder);

        return dao.save(model);
    }

    @Override
    @Transactional(rollbackFor = ValidationException.class)
    public SuccessIndicator save(SuccessIndicatorTO spec) throws ValidationException, ObjectNotFoundException {
        if (spec.getId() == null) {
            throw new ValidationException("Must specify the ID of the SuccessIndicator to edit");
        }

        // We'll shoot for a convention of assuming every field in the 'spec' represents a potential change request
        // (as opposed to just ignoring fields you can't edit), with the exception of a few, standard calculated
        // fields for which we should never trust the client (audit field in particular). This mostly works well
        // for our UI client which typically tries to send *everything* and which typically sends models that match up
        // exactly with our TOs, so there's no such thing, really, as a 'missing field'. But it does pose problems for
        // clients who sent partial TOs, but this has been a known archtectural issue throughout SSP for quiet some time
        // now.

        final SuccessIndicator model = dao.get(spec.getId());
        if ( model == null ) {
            throw new ObjectNotFoundException(spec.getId(), SuccessIndicator.class.getName());
        }

        final SuccessIndicatorGroup currentIndicatorGroup = model.getIndicatorGroup();
        if ( !(currentIndicatorGroup == spec.getIndicatorGroup()) ) {
            throw new ValidationException("Cannot reassign to a different indicator group");
        }
        final SuccessIndicatorEvaluationType evaluationType = spec.getEvaluationType();
        if ( evaluationType == null ) {
            throw new ValidationException("Cannot delete the evaluation type field");
        }

        final boolean isSystemGroup =  currentIndicatorGroup == SuccessIndicatorGroup.STUDENT ||
                currentIndicatorGroup == SuccessIndicatorGroup.INTERVENTION;
        if ( isSystemGroup ) {

            if ( !(StringUtils.equals(model.getCode(), spec.getCode())) ) {
                throw new ValidationException("Cannot modify the code field on a system-owned indicator");
            }

            if ( !(StringUtils.equals(model.getName(), spec.getName())) ) {
                throw new ValidationException("Cannot modify the name field on a system-owned indicator");
            }

            if ( !(StringUtils.equals(model.getDescription(), spec.getDescription())) ) {
                throw new ValidationException("Cannot modify the description field on a system-owned indicator");
            }

            if ( !(StringUtils.equals(model.getModelCode(), spec.getModelCode())) ) {
                throw new ValidationException("Cannot modify the model code field on a system-owned indicator");
            }

            if ( !(StringUtils.equals(model.getModelName(), spec.getModelName())) ) {
                throw new ValidationException("Cannot modify the model name field on a system-owned indicator");
            }

        } else {

            if ( StringUtils.isBlank(spec.getCode()) ) {
                throw new ValidationException("Cannot delete the code field");
            }
            final String code = spec.getCode().trim();
            if ( code.toLowerCase().startsWith(INDICATOR_CODE_SYSTEM_PREFIX) ) {
                throw new ValidationException("The [" + INDICATOR_CODE_SYSTEM_PREFIX + "] indicator code prefix is reserved.");
            }
            model.setCode(code);
            if ( StringUtils.isBlank(spec.getName()) ) {
                throw new ValidationException("Cannot delete the name field");
            }
            model.setName(spec.getName());
            if ( StringUtils.isBlank(spec.getDescription()) ) {
                throw new ValidationException("Cannot delete the description field");
            }
            model.setDescription(spec.getDescription());
            model.setInstruction(spec.getInstruction()); // deletion fine
            if ( StringUtils.isBlank(spec.getModelCode()) ) {
                throw new ValidationException("Cannot delete the model code field");
            }
            final String modelCode = spec.getModelCode().trim();
            if ( modelCode.toLowerCase().startsWith(MODEL_CODE_SYSTEM_PREFIX) ) {
                throw new ValidationException("The [" + MODEL_CODE_SYSTEM_PREFIX + "] model code prefix is reserved.");
            }
            model.setModelCode(modelCode);
            if ( StringUtils.isBlank(spec.getModelName()) ) {
                throw new ValidationException("Cannot delete the model name field");
            }
            model.setModelName(spec.getModelName());

        }

        model.setEvaluationType(spec.getEvaluationType());
        model.setScaleEvaluationHighFrom(spec.getScaleEvaluationHighFrom());
        model.setScaleEvaluationHighTo(spec.getScaleEvaluationHighTo());
        model.setScaleEvaluationMediumFrom(spec.getScaleEvaluationMediumFrom());
        model.setScaleEvaluationMediumTo(spec.getScaleEvaluationMediumTo());
        model.setScaleEvaluationLowFrom(spec.getScaleEvaluationLowFrom());
        model.setScaleEvaluationLowTo(spec.getScaleEvaluationLowTo());
        model.setStringEvaluationHigh(spec.getStringEvaluationHigh());
        model.setStringEvaluationMedium(spec.getStringEvaluationMedium());
        model.setStringEvaluationLow(spec.getStringEvaluationLow());
        model.setNoDataMatchesEvaluation(spec.getNoDataMatchesEvaluation() == null ? SuccessIndicatorEvaluation.DEFAULT : spec.getNoDataMatchesEvaluation());
        model.setNoDataExistsEvaluation(spec.getNoDataExistsEvaluation() == null ? SuccessIndicatorEvaluation.DEFAULT : spec.getNoDataMatchesEvaluation());
        model.setObjectStatus(spec.getObjectStatus());
        model.setSortOrder(spec.getSortOrder());

        return dao.save(model);

    }
}
