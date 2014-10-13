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
package org.jasig.ssp.transferobject.reports;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.jasig.ssp.transferobject.PersonTO;

/**
 * AddressLabelSearch transfer object
 */
public class EarlyAlertStudentSearchTO
		implements Serializable {

	private static final long serialVersionUID = 3118831549819428989L;

    private String termCode;

	private Date startDate;

	private Date endDate;

    private Date responseDateTo;

    private Date responseDateFrom;
	
	private PersonSearchFormTO addressLabelSearchTO;
	
	private List<UUID> outcomeIds;

	public EarlyAlertStudentSearchTO(final PersonSearchFormTO addressLabelSearchTO,
								final String termCode, final Date startDate, final Date endDate) {
		super();
		
		this.addressLabelSearchTO = addressLabelSearchTO;
        this.termCode = termCode;
		this.startDate = startDate;
		this.endDate = endDate;
	}

    public EarlyAlertStudentSearchTO(final PersonSearchFormTO addressLabelSearchTO,
                                     final String termCode, final Date startDate, final Date endDate,
                                     final Date responseDateFrom, final Date responseDateTo) {
        super();

        this.addressLabelSearchTO = addressLabelSearchTO;
        this.termCode = termCode;
        this.startDate = startDate;
        this.endDate = endDate;
        this.responseDateFrom = responseDateFrom;
        this.responseDateTo = responseDateTo;
    }

    public String getTermCode() {
        return termCode;
    }


	public Date getStartDate() {
		return startDate == null ? null : new Date(
				startDate.getTime());
	}

	public void setStartDate(final Date startDate) {
		this.startDate = startDate == null ? null : new Date(
				startDate.getTime());
	}

	public Date getEndDate() {
		return endDate == null ? null : new Date(endDate.getTime());
	}

    public void setEndDate(final Date endDate) {
        this.endDate = endDate == null ? null : new Date(
                endDate.getTime());
    }

    public Date getResponseDateFrom() {
        return responseDateFrom == null ? null : new Date(responseDateFrom.getTime());
    }

    public void setResponseDateFrom(final Date responseDateFrom) {
        this.responseDateFrom = responseDateFrom == null ? null : new Date(
                responseDateFrom.getTime());
    }

    public Date getResponseDateTo() {
        return responseDateTo == null ? null : new Date(responseDateTo.getTime());
    }

    public void setResponseDateTo(final Date responseDateTo) {
        this.responseDateTo = responseDateTo == null ? null : new Date(
                responseDateTo.getTime());
    }


    public PersonSearchFormTO getAddressLabelSearchTO() {
		return addressLabelSearchTO;
	}

	public void setAddressLabelSearchTO(PersonSearchFormTO addressLabelSearchTO) {
		this.addressLabelSearchTO = addressLabelSearchTO;
	}


	/**
	 * @return the outcomeIds
	 */
	public List<UUID> getOutcomeIds() {
		return outcomeIds;
	}


	/**
	 * @param outcomeIds the outcomeIds to set
	 */
	public void setOutcomeIds(List<UUID> outcomeIds) {
		this.outcomeIds = outcomeIds;
	}

}