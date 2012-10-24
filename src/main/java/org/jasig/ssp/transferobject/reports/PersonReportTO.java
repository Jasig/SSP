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

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.reference.SpecialServiceGroup;
import org.jasig.ssp.transferobject.PersonTO;
import org.jasig.ssp.transferobject.reference.ReferenceLiteTO;

import com.google.common.collect.Lists;

public class PersonReportTO extends PersonTO {

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

	public String getSpecialServiceGroupNames()
	{
		List<ReferenceLiteTO<SpecialServiceGroup>> specialServiceGroups = this.getSpecialServiceGroups();
		if ( specialServiceGroups == null || specialServiceGroups.isEmpty() ) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		Iterator<ReferenceLiteTO<SpecialServiceGroup>> specialServiceGroupsIter = specialServiceGroups.iterator();
		while(specialServiceGroupsIter.hasNext())
		{
			ReferenceLiteTO<SpecialServiceGroup> tempSpecialServiceGroup = specialServiceGroupsIter.next();
			sb.append("\u2022 " + tempSpecialServiceGroup.getName());
			sb.append("\n");

		}
		sb.append("\n");

		return sb.toString();

	}

}
