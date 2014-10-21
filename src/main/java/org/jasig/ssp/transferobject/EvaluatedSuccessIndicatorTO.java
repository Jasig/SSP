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
package org.jasig.ssp.transferobject;

import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.SuccessIndicatorGroup;

import java.util.UUID;

public class EvaluatedSuccessIndicatorTO {

    private String id;
    private UUID personId;
    private UUID indicatorId;
    private String indicatorName;
    private String indicatorDescription;
    private String indicatorCode;
    private String indicatorModelName;
    private String indicatorModelCode;
    private SuccessIndicatorGroup indicatorGroupCode;
    private int indicatorSortOrder;
    private ObjectStatus objectStatus;
    private String displayValue;
    private SuccessIndicatorEvaluation evaluation;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UUID getPersonId() {
        return personId;
    }

    public void setPersonId(UUID personId) {
        this.personId = personId;
    }

    public UUID getIndicatorId() {
        return indicatorId;
    }

    public void setIndicatorId(UUID indicatorId) {
        this.indicatorId = indicatorId;
    }

    public String getIndicatorName() {
        return indicatorName;
    }

    public void setIndicatorName(String indicatorName) {
        this.indicatorName = indicatorName;
    }

    public String getIndicatorDescription() {
        return indicatorDescription;
    }

    public void setIndicatorDescription(String indicatorDescription) {
        this.indicatorDescription = indicatorDescription;
    }

    public String getIndicatorCode() {
        return indicatorCode;
    }

    public void setIndicatorCode(String indicatorCode) {
        this.indicatorCode = indicatorCode;
    }

    public String getIndicatorModelName() {
        return indicatorModelName;
    }

    public void setIndicatorModelName(String indicatorModelName) {
        this.indicatorModelName = indicatorModelName;
    }

    public String getIndicatorModelCode() {
        return indicatorModelCode;
    }

    public void setIndicatorModelCode(String indicatorModelCode) {
        this.indicatorModelCode = indicatorModelCode;
    }

    public SuccessIndicatorGroup getIndicatorGroupCode() {
        return indicatorGroupCode;
    }

    public void setIndicatorGroupCode(SuccessIndicatorGroup indicatorGroupCode) {
        this.indicatorGroupCode = indicatorGroupCode;
    }

    public ObjectStatus getObjectStatus() {
        return objectStatus;
    }

    public void setObjectStatus(ObjectStatus objectStatus) {
        this.objectStatus = objectStatus;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    public void setDisplayValue(String displayValue) {
        this.displayValue = displayValue;
    }

    public SuccessIndicatorEvaluation getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(SuccessIndicatorEvaluation evaluation) {
        this.evaluation = evaluation;
    }

    public int getIndicatorSortOrder() {
        return indicatorSortOrder;
    }

    public void setIndicatorSortOrder(int indicatorSortOrder) {
        this.indicatorSortOrder = indicatorSortOrder;
    }
}
