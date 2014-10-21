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
package org.jasig.ssp.transferobject.reference;

import org.jasig.ssp.model.SuccessIndicatorEvaluationType;
import org.jasig.ssp.model.SuccessIndicatorGroup;
import org.jasig.ssp.model.reference.SuccessIndicator;
import org.jasig.ssp.transferobject.SuccessIndicatorEvaluation;
import org.jasig.ssp.transferobject.TransferObject;

import java.math.BigDecimal;
import java.util.UUID;

public class SuccessIndicatorTO extends AbstractReferenceTO<SuccessIndicator>
        implements TransferObject<SuccessIndicator> {

    private String modelCode;
    private String modelName;
    private SuccessIndicatorGroup indicatorGroup;
    private String code;
    private String instruction;
    private SuccessIndicatorEvaluationType evaluationType;
    private BigDecimal scaleEvaluationHighFrom;
    private BigDecimal scaleEvaluationHighTo;
    private BigDecimal scaleEvaluationMediumFrom;
    private BigDecimal scaleEvaluationMediumTo;
    private BigDecimal scaleEvaluationLowFrom;
    private BigDecimal scaleEvaluationLowTo;
    private String stringEvaluationHigh;
    private String stringEvaluationMedium;
    private String stringEvaluationLow;
    private SuccessIndicatorEvaluation noDataExistsEvaluation;
    private SuccessIndicatorEvaluation noDataMatchesEvaluation;
    private Integer sortOrder;

    public SuccessIndicatorTO() {
        super();
    }

    public SuccessIndicatorTO(final UUID id, final String name,
                              final String description) {
        super(id, name, description);
    }

    @Override
    public void from(final SuccessIndicator model) {
        super.from(model);
        setModelCode(model.getModelCode());
        setModelName(model.getModelName());
        setIndicatorGroup(model.getIndicatorGroup());
        setCode(model.getCode());
        setInstruction(model.getInstruction());
        setEvaluationType(model.getEvaluationType());
        setScaleEvaluationHighFrom(model.getScaleEvaluationHighFrom());
        setScaleEvaluationHighTo(model.getScaleEvaluationHighTo());
        setScaleEvaluationMediumFrom(model.getScaleEvaluationMediumFrom());
        setScaleEvaluationMediumTo(model.getScaleEvaluationMediumTo());
        setScaleEvaluationLowFrom(model.getScaleEvaluationLowFrom());
        setScaleEvaluationLowTo(model.getScaleEvaluationLowTo());
        setStringEvaluationHigh(model.getStringEvaluationHigh());
        setStringEvaluationMedium(model.getStringEvaluationMedium());
        setStringEvaluationLow(model.getStringEvaluationLow());
        setNoDataExistsEvaluation(model.getNoDataExistsEvaluation());
        setNoDataMatchesEvaluation(model.getNoDataMatchesEvaluation());
        setSortOrder(model.getSortOrder());
    }

    public String getModelCode() {
        return modelCode;
    }

    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public SuccessIndicatorGroup getIndicatorGroup() {
        return indicatorGroup;
    }

    public void setIndicatorGroup(SuccessIndicatorGroup indicatorGroup) {
        this.indicatorGroup = indicatorGroup;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public SuccessIndicatorEvaluationType getEvaluationType() {
        return evaluationType;
    }

    public void setEvaluationType(SuccessIndicatorEvaluationType evaluationType) {
        this.evaluationType = evaluationType;
    }

    public BigDecimal getScaleEvaluationHighFrom() {
        return scaleEvaluationHighFrom;
    }

    public void setScaleEvaluationHighFrom(BigDecimal scaleEvaluationHighFrom) {
        this.scaleEvaluationHighFrom = scaleEvaluationHighFrom;
    }

    public BigDecimal getScaleEvaluationHighTo() {
        return scaleEvaluationHighTo;
    }

    public void setScaleEvaluationHighTo(BigDecimal scaleEvaluationHighTo) {
        this.scaleEvaluationHighTo = scaleEvaluationHighTo;
    }

    public BigDecimal getScaleEvaluationMediumFrom() {
        return scaleEvaluationMediumFrom;
    }

    public void setScaleEvaluationMediumFrom(BigDecimal scaleEvaluationMediumFrom) {
        this.scaleEvaluationMediumFrom = scaleEvaluationMediumFrom;
    }

    public BigDecimal getScaleEvaluationMediumTo() {
        return scaleEvaluationMediumTo;
    }

    public void setScaleEvaluationMediumTo(BigDecimal scaleEvaluationMediumTo) {
        this.scaleEvaluationMediumTo = scaleEvaluationMediumTo;
    }

    public BigDecimal getScaleEvaluationLowFrom() {
        return scaleEvaluationLowFrom;
    }

    public void setScaleEvaluationLowFrom(BigDecimal scaleEvaluationLowFrom) {
        this.scaleEvaluationLowFrom = scaleEvaluationLowFrom;
    }

    public BigDecimal getScaleEvaluationLowTo() {
        return scaleEvaluationLowTo;
    }

    public void setScaleEvaluationLowTo(BigDecimal scaleEvaluationLowTo) {
        this.scaleEvaluationLowTo = scaleEvaluationLowTo;
    }

    public String getStringEvaluationHigh() {
        return stringEvaluationHigh;
    }

    public void setStringEvaluationHigh(String stringEvaluationHigh) {
        this.stringEvaluationHigh = stringEvaluationHigh;
    }

    public String getStringEvaluationMedium() {
        return stringEvaluationMedium;
    }

    public void setStringEvaluationMedium(String stringEvaluationMedium) {
        this.stringEvaluationMedium = stringEvaluationMedium;
    }

    public String getStringEvaluationLow() {
        return stringEvaluationLow;
    }

    public void setStringEvaluationLow(String stringEvaluationLow) {
        this.stringEvaluationLow = stringEvaluationLow;
    }

    public SuccessIndicatorEvaluation getNoDataExistsEvaluation() {
        return noDataExistsEvaluation;
    }

    public void setNoDataExistsEvaluation(SuccessIndicatorEvaluation noDataExistsEvaluation) {
        this.noDataExistsEvaluation = noDataExistsEvaluation;
    }

    public SuccessIndicatorEvaluation getNoDataMatchesEvaluation() {
        return noDataMatchesEvaluation;
    }

    public void setNoDataMatchesEvaluation(SuccessIndicatorEvaluation noDataMatchesEvaluation) {
        this.noDataMatchesEvaluation = noDataMatchesEvaluation;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
}
