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
package org.jasig.ssp.web.api.tool; // NOPMD

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.validation.Valid;

import org.jasig.ssp.factory.tool.AccommodationFormTOFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.tool.AccommodationForm;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.reference.DisabilityAgencyService;
import org.jasig.ssp.service.reference.DisabilityAccommodationService;
import org.jasig.ssp.service.reference.DisabilityStatusService;
import org.jasig.ssp.service.reference.DisabilityTypeService;
import org.jasig.ssp.service.tool.AccommodationService;
import org.jasig.ssp.transferobject.ServiceResponse;
import org.jasig.ssp.transferobject.reference.DisabilityAgencyTO;
import org.jasig.ssp.transferobject.reference.DisabilityAccommodationTO;
import org.jasig.ssp.transferobject.reference.DisabilityStatusTO;
import org.jasig.ssp.transferobject.reference.DisabilityTypeTO;
import org.jasig.ssp.transferobject.tool.AccommodationFormTO;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.AbstractBaseController;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Accommodation tool services
 * <p>
 * Mapped to URI path <code>/1/tool/accommodation</code>
 */
@Controller
@RequestMapping("/1/tool/accommodation")
public class AccommodationController extends AbstractBaseController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(AccommodationController.class);

	@Autowired
	private transient AccommodationService service;

	@Autowired
	private transient AccommodationFormTOFactory factory;

	@Autowired
	private transient DisabilityAgencyService disabilityAgencyService;

	@Autowired
	private transient DisabilityAccommodationService disabilityAccommodationService;
	
	@Autowired
	private transient DisabilityStatusService disabilityStatusService;
	
	@Autowired
	private transient DisabilityTypeService disabilityTypeService;

	/**
	 * Save changes to a AccommodationForm
	 * 
	 * @param studentId
	 *            Student identifier
	 * @param accommodationForm
	 *            Incoming data
	 * @return Service response with success value, in the JSON format.
	 * @throws ValidationException
	 *             If form data was not valid.
	 * @throws ObjectNotFoundException
	 *             If any reference look up data couldn't be loaded.
	 */
	@PreAuthorize("hasRole('ROLE_ACCOMMODATION_WRITE')")
	@RequestMapping(value = "/{studentId}", method = RequestMethod.PUT)
	public @ResponseBody
	ServiceResponse save(final @PathVariable UUID studentId,
			final @Valid @RequestBody AccommodationFormTO accommodationForm)
			throws ObjectNotFoundException, ValidationException {
		final AccommodationForm model = factory.from(accommodationForm);
		model.getPerson().setId(studentId);
		return new ServiceResponse(service.save(model));
	}

	/**
	 * Using the studentId passed, return the {@link AccommodationFormTO} in its
	 * current state, creating it if necessary.
	 * 
	 * @param studentId
	 *            Student identifier Any errors will throw this generic
	 *            exception.
	 * @return Service response with success value, in the JSON format.
	 * @throws ObjectNotFoundException
	 *             If any reference data could not be loaded.
	 */
	@RequestMapping(value = "/{studentId}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ACCOMMODATION_READ')")
	public @ResponseBody
	AccommodationFormTO load(final @PathVariable UUID studentId)
			throws ObjectNotFoundException {
		final AccommodationFormTO formTO = new AccommodationFormTO(
				service.loadForPerson(studentId));
		formTO.setReferenceData(referenceData());
		return formTO;
	}

	/**
	 * Return all the data that is necessary to complete an accommodation form.
	 * 
	 * @return Service response with success value, in the JSON format.
	 */
	public Map<String, Object> referenceData() {
		final Map<String, Object> refData = new HashMap<String, Object>();

		final SortingAndPaging sAndP = new SortingAndPaging(ObjectStatus.ACTIVE);

		refData.put("disabilityAccommodations", DisabilityAccommodationTO
				.toTOList(disabilityAccommodationService.getAll(sAndP).getRows()));		

		refData.put("disabilityAgencies", DisabilityAgencyTO
				.toTOList(disabilityAgencyService.getAll(sAndP).getRows()));
	
		refData.put("disabilityStatuses", DisabilityStatusTO
				.toTOList(disabilityStatusService.getAll(sAndP).getRows()));		
		
		refData.put("disabilityTypes", DisabilityTypeTO
				.toTOList(disabilityTypeService.getAll(sAndP).getRows()));
		
		
		return refData;
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}