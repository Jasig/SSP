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

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.external.RegistrationStatusByTerm;
import org.jasig.ssp.model.reference.AbstractReference;
import org.jasig.ssp.model.reference.ServiceReason;
import org.jasig.ssp.model.reference.SpecialServiceGroup;
import org.jasig.ssp.transferobject.PersonTO;
import org.jasig.ssp.transferobject.reference.ReferenceLiteTO;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;

import static com.google.common.collect.Iterables.addAll;
import static com.google.common.collect.Iterables.filter;

public class PersonReportTO extends PersonTO {

	
	private List<RegistrationStatusByTerm> registrationStatusByTerms;
	/**
	 * Construct a transfer object from a related model instance
	 * 
	 * @param model
	 *            Initialize instance from the data in this model
	 */
	public PersonReportTO(final Person model) {		
		super(model);
	}
	
	 
	
	/**
	 * Convert a collection of models to a List of equivalent transfer objects.
	 * 
	 * @param models
	 *            A collection of models to convert to transfer objects
	 * @return List of equivalent transfer objects
	 */
	
	public static List<PersonReportTO> toPersonTOList(
			@NotNull final Collection<Person> models) {
		final List<PersonReportTO> tos = Lists.newArrayList();
		for (final Person model : models) {
			tos.add(new PersonReportTO(model)); // NOPMD by jon.adams on 5/13/12
		}

		return tos;
	}

	public List<ReferenceLiteTO<ServiceReason>> getActiveServiceReasons() {
		return getActiveAssociationsIn(getServiceReasons());
	}

	public List<ReferenceLiteTO<SpecialServiceGroup>> getActiveSpecialServiceGroups() {
		return getActiveAssociationsIn(getSpecialServiceGroups());
	}

	protected <R extends AbstractReference> List<ReferenceLiteTO<R>> getActiveAssociationsIn(List<ReferenceLiteTO<R>> all) {
		final List<ReferenceLiteTO<R>> active = Lists.newArrayList();
		if ( all == null || all.isEmpty() ) {
			return active;
		}
		addAll(active, filter(all, new Predicate<ReferenceLiteTO<R>>() {
			@Override
			public boolean apply(@Nullable ReferenceLiteTO<R> candidate) {
				return candidate.getObjectStatus() == ObjectStatus.ACTIVE;
			}
		}));
		return ReferenceLiteTO.sortByName(active);
	}

	public List<RegistrationStatusByTerm> getRegistrationStatusByTersm(){
		return registrationStatusByTerms;
	}

	
	public void setRegistrationStatusByTerm(List<RegistrationStatusByTerm> registrationStatusByTerms){
		 this.registrationStatusByTerms = registrationStatusByTerms;
	}
	
	
	public String getRegisteredTerms(){
		StringBuilder registeredTerms = new StringBuilder("");
		String space = "";
		if(registrationStatusByTerms != null && registrationStatusByTerms.size() > 0){
			for(RegistrationStatusByTerm status:registrationStatusByTerms){
				registeredTerms.append(space);
				registeredTerms.append(status.getTermCode());
				space = " ";
			}
		}
		return registeredTerms.toString();
	}
	
	public String getPaymentStatus(){
		if(registrationStatusByTerms == null)
		{
			return "N";
		}
		StringBuilder builder = new StringBuilder("");
		String space = "";
		for (RegistrationStatusByTerm status : registrationStatusByTerms) 
		{
			if(status.getTuitionPaid() != null && !status.getTuitionPaid().trim().isEmpty()){
				builder.append(space).
				append(status.getTermCode())
				.append("=")
				.append(status.getTuitionPaid());
				space = " ";
			}
		}
		return builder.toString();
	}

}
