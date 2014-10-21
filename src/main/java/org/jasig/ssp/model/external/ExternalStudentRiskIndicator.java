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
package org.jasig.ssp.model.external;

import org.hibernate.annotations.Immutable;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Immutable
@Table(name = "v_external_student_risk_indicator")
public class ExternalStudentRiskIndicator extends AbstractExternalData implements
        Serializable, ExternalData {

    @Column(nullable = false, length = 50)
    @NotNull
    @NotEmpty
    @Size(max = 50)
    private String schoolId;

    @Column(nullable = false, length = 50)
    @NotNull
    @NotEmpty
    @Size(max = 50)
    private String modelCode;

    @Column(nullable = false, length = 100)
    @NotNull
    @NotEmpty
    @Size(max = 100)
    private String modelName;

    @Column(nullable = false, length = 50)
    @NotNull
    @NotEmpty
    @Size(max = 50)
    private String indicatorCode;

    @Column(nullable = false, length = 100)
    @NotNull
    @NotEmpty
    @Size(max = 100)
    private String indicatorName;

    @Column(nullable = true, length = 2500)
    @Size(max = 2500)
    private String indicatorValueDescription;

    @Column(nullable = false, length = 50)
    @NotNull
    @NotEmpty
    @Size(max = 50)
    private String indicatorValue;

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
}
