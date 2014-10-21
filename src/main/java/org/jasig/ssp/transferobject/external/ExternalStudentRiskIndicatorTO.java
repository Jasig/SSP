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
package org.jasig.ssp.transferobject.external;

import org.jasig.ssp.model.external.ExternalStudentRiskIndicator;

import java.io.Serializable;

public class ExternalStudentRiskIndicatorTO implements Serializable,
        ExternalDataTO<ExternalStudentRiskIndicator>  {

    private String schoolId;
    private String modelCode;
    private String modelName;
    private String indicatorCode;
    private String indicatorName;
    private String indicatorValueDescription;
    private String indicatorValue;

    public ExternalStudentRiskIndicatorTO() {
        super();
    }

    public ExternalStudentRiskIndicatorTO(ExternalStudentRiskIndicator model){
        super();
        from(model);
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
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

    public String getIndicatorCode() {
        return indicatorCode;
    }

    public void setIndicatorCode(String indicatorCode) {
        this.indicatorCode = indicatorCode;
    }

    public String getIndicatorName() {
        return indicatorName;
    }

    public void setIndicatorName(String indicatorName) {
        this.indicatorName = indicatorName;
    }

    public String getIndicatorValueDescription() {
        return indicatorValueDescription;
    }

    public void setIndicatorValueDescription(String indicatorValueDescription) {
        this.indicatorValueDescription = indicatorValueDescription;
    }

    public String getIndicatorValue() {
        return indicatorValue;
    }

    public void setIndicatorValue(String indicatorValue) {
        this.indicatorValue = indicatorValue;
    }

    @Override
    public void from(ExternalStudentRiskIndicator model) {
        schoolId = model.getSchoolId();
        modelCode = model.getModelCode();
        modelName = model.getModelName();
        indicatorCode = model.getIndicatorCode();
        indicatorName = model.getIndicatorName();
        indicatorValueDescription = model.getIndicatorValueDescription();
        indicatorValue = model.getIndicatorValue();
    }
}
