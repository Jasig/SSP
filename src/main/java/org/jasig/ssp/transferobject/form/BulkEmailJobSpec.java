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
package org.jasig.ssp.transferobject.form;


import org.jasig.ssp.transferobject.ImmutablePersonIdentifiersTO;
import org.jasig.ssp.util.collections.Pair;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class BulkEmailJobSpec implements Serializable, HasPersonSearchRequestCoreSpec<BulkEmailStudentRequestForm> {

	private BulkEmailStudentRequestForm coreSpec;

	/**
	 * Remember the {@code id} field on these TOs is {@code null} if the person is "external-only". That's how
	 * we support sending email to search results that aren't entirely composed of internal {@code Persons}.
	 *
	 * <p>Terrible field name is an attempt to be explicit about the fact that this collection is derived
	 * by executing a {@code Person} search defined by the content of {@code coreSpec.criteria}</p>
	 */
	private List<ImmutablePersonIdentifiersTO> personIdentifiersFromCoreSpecCriteria;

	public BulkEmailJobSpec() {
		this(null,null);
	}

	public BulkEmailJobSpec(BulkEmailStudentRequestForm coreSpec) {
		this(coreSpec, null);
	}

	public BulkEmailJobSpec(BulkEmailStudentRequestForm coreSpec, List<ImmutablePersonIdentifiersTO> personIdentifiersFromCoreSpecCriteria) {
		this.coreSpec = coreSpec;
		this.personIdentifiersFromCoreSpecCriteria = personIdentifiersFromCoreSpecCriteria;
	}

	public BulkEmailStudentRequestForm getCoreSpec() {
		return coreSpec;
	}

	public void setCoreSpec(BulkEmailStudentRequestForm coreSpec) {
		this.coreSpec = coreSpec;
	}

	public List<ImmutablePersonIdentifiersTO> getPersonIdentifiersFromCoreSpecCriteria() {
		return personIdentifiersFromCoreSpecCriteria;
	}

	public void setPersonIdentifiersFromCoreSpecCriteria(List<ImmutablePersonIdentifiersTO> personIdentifiersFromCoreSpecCriteria) {
		this.personIdentifiersFromCoreSpecCriteria = personIdentifiersFromCoreSpecCriteria;
	}
}
