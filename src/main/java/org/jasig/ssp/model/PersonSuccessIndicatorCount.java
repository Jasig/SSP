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
package org.jasig.ssp.model;

import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;


/**
 * Used to store a count of high/med/low Success Indicators from the Success Indicator
 *   count background task. Only configured Success Indicators count towards the total.
 */
@Entity
public class PersonSuccessIndicatorCount extends AbstractAuditable  implements Auditable {

	private static final long serialVersionUID = 7609991651488688420L;

	@NotNull
	@ManyToOne()
	@JoinColumn(name = "person_id", updatable = false, nullable = false)
	private Person person;

    @Nullable
    private Integer lowCount;

    @Nullable
    private Integer mediumCount;

    @Nullable
    private Integer highCount;

    @Nullable
    private Integer lowAlertCount;


    //getters and setters
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

    @Nullable
    public Integer getLowCount() {
        return lowCount;
    }

    public void setLowCount(@Nullable Integer lowCount) {
        this.lowCount = lowCount;
    }

    @Nullable
    public Integer getMediumCount() {
        return mediumCount;
    }

    public void setMediumCount(@Nullable Integer mediumCount) {
        this.mediumCount = mediumCount;
    }

    @Nullable
    public Integer getHighCount() {
        return highCount;
    }

    public void setHighCount(@Nullable Integer highCount) {
        this.highCount = highCount;
    }

    @Nullable
    public Integer getLowAlertCount() {
        return lowAlertCount;
    }

    public void setLowAlertCount(@Nullable Integer lowAlertCount) {
        this.lowAlertCount = lowAlertCount;
    }


    @Override
    protected int hashPrime() {
        return 11;
    }

    @Override
    final public int hashCode() {
        int result = hashPrime();
        result *= hashField("id", getId());
        result *= hashField("objectStatus", getObjectStatus());
        result *= hashField("person", person);
        result *= hashField("lowCount", getLowCount());
        result *= hashField("mediumCount", getMediumCount());
        result *= hashField("highCount", getHighCount());
        return result;
    }
}