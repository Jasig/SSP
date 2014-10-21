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
package org.jasig.ssp.model.reference;

import org.hibernate.validator.constraints.NotEmpty;
import org.jasig.ssp.model.SuccessIndicatorEvaluationType;
import org.jasig.ssp.model.SuccessIndicatorGroup;
import org.jasig.ssp.transferobject.SuccessIndicatorEvaluation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class SuccessIndicator extends AbstractReference {

    @Column(nullable = true, length = 50)
    @Size(max = 50)
    private String modelCode;

    @Column(nullable = true, length = 100)
    @Size(max = 100)
    private String modelName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SuccessIndicatorGroup indicatorGroup;

    @Column(nullable = false, length = 50)
    @NotNull
    @NotEmpty
    @Size(max = 50)
    private String code;

    @Column(nullable = true, length = 1024)
    @Size(max = 1024)
    private String instruction;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SuccessIndicatorEvaluationType evaluationType;

    @Column(nullable = true)
    private BigDecimal scaleEvaluationHighFrom;

    @Column(nullable = true)
    private BigDecimal scaleEvaluationHighTo;

    @Column(nullable = true)
    private BigDecimal scaleEvaluationMediumFrom;

    @Column(nullable = true)
    private BigDecimal scaleEvaluationMediumTo;

    @Column(nullable = true)
    private BigDecimal scaleEvaluationLowFrom;

    @Column(nullable = true)
    private BigDecimal scaleEvaluationLowTo;

    @Column(nullable = true, length = 1024)
    @Size(max = 1024)
    private String stringEvaluationHigh;

    @Column(nullable = true, length = 1024)
    @Size(max = 1024)
    private String stringEvaluationMedium;

    @Column(nullable = true, length = 1024)
    @Size(max = 1024)
    private String stringEvaluationLow;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SuccessIndicatorEvaluation noDataExistsEvaluation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SuccessIndicatorEvaluation noDataMatchesEvaluation;

    // we have a mix of int and short sortOrders in other models... perf gains from short probably aren't worth
    // the potential mismatches with API clients who don't support the int/short distinction.
    @Column(nullable = false)
    @NotNull
    private Integer sortOrder;

    @Override
    protected int hashPrime() {
        return 1987;
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

    @Override
    public int hashCode() {
        return hashPrime() * super.hashCode()
                * hashField("modelCode", modelCode)
                * hashField("code", code);
    }

}
