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
package org.jasig.ssp.service.external;

import java.util.List;

import org.jasig.ssp.model.external.Term;
import org.jasig.ssp.service.ObjectNotFoundException;

public interface TermService extends ExternalReferenceDataService<Term> {

	Term getCurrentTerm() throws ObjectNotFoundException;

	List<Term> getCurrentAndFutureTerms() throws ObjectNotFoundException;

	List<Term> facetSearch(String tag, String programCode);

	List<Term> getTermsWithRegistrationWindowOpenIfAny();
	List<Term> getTermsByCodes(List<String> codes);
}