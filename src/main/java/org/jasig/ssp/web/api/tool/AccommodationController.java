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

import java.util.Collection; 
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.List; 

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
import org.jasig.ssp.transferobject.AbstractAuditableTO; 
import org.jasig.ssp.transferobject.PersonDisabilityAccommodationTO;
import org.jasig.ssp.transferobject.PersonDisabilityAgencyTO;
import org.jasig.ssp.transferobject.PersonDisabilityTypeTO;
import org.jasig.ssp.transferobject.ServiceResponse;
import org.jasig.ssp.transferobject.reference.AbstractReferenceTO; 
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

import com.google.common.collect.Lists; 

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
		formTO.setReferenceData(referenceData(formTO)); 
		return formTO;
	}

	/**
	 * Return all the data that is necessary to complete an accommodation form.
	 * 
	 * @return Service response with success value, in the JSON format.
	 */
	public Map<String, Object> referenceData(AccommodationFormTO formTO) throws ObjectNotFoundException {
		final Map<String, Object> refData = new HashMap<String, Object>();

		/*final SortingAndPaging sAndP = new SortingAndPaging(ObjectStatus.ACTIVE);

		refData.put("disabilityAccommodations", DisabilityAccommodationTO
				.toTOList(disabilityAccommodationService.getAll(sAndP).getRows()));		

		refData.put("disabilityAgencies", DisabilityAgencyTO
				.toTOList(disabilityAgencyService.getAll(sAndP).getRows()));
	
		refData.put("disabilityStatuses", DisabilityStatusTO
				.toTOList(disabilityStatusService.getAll(sAndP).getRows()));		
		
		refData.put("disabilityTypes", DisabilityTypeTO
				.toTOList(disabilityTypeService.getAll(sAndP).getRows()));*/
		
		
		final SortingAndPaging sAndP = SortingAndPaging
				      .createForSingleSortWithPaging(ObjectStatus.ALL, 0, -1, null, null, null);
				
				   refData.put("disabilityAccommodations", disabilityAccommodationReferenceDataFor(formTO, sAndP));
				   refData.put("disabilityAgencies", disabilityAgencieReferenceDataFor(formTO, sAndP));
				   refData.put("disabilityStatuses", disabilityStatuseReferenceDataFor(formTO, sAndP));
				   refData.put("disabilityTypes", disabilityTypeReferenceDataFor(formTO, sAndP));
				
		
		return refData;
	}

	private List<DisabilityAccommodationTO> disabilityAccommodationReferenceDataFor(AccommodationFormTO formTO,
			                          SortingAndPaging sAndP) {
			    return filterInactiveExceptFor(
			       associatedIds(formTO.getPersonDisabilityAccommodations(), PERSON_ASSOC_DISABILITY_ACCOMMODATION_UUID_EXTRACTOR),
			       DisabilityAccommodationTO.toTOList(disabilityAccommodationService.getAll(sAndP).getRows()));
			  } 
	
	private List<DisabilityAgencyTO> disabilityAgencieReferenceDataFor(AccommodationFormTO formTO,
            SortingAndPaging sAndP) {
				return filterInactiveExceptFor(
				associatedIds(formTO.getPersonDisabilityAgencies(), PERSON_ASSOC_DISABILITY_AGENCY_UUID_EXTRACTOR),
				DisabilityAgencyTO.toTOList(disabilityAgencyService.getAll(sAndP).getRows()));
	} 
	
	private List<DisabilityTypeTO> disabilityTypeReferenceDataFor(AccommodationFormTO formTO,
            SortingAndPaging sAndP) {
				return filterInactiveExceptFor(
				associatedIds(formTO.getPersonDisabilityTypes(), PERSON_ASSOC_DISABILITY_TYPE_UUID_EXTRACTOR),
				DisabilityTypeTO.toTOList(disabilityTypeService.getAll(sAndP).getRows()));
	} 
	
	private List<DisabilityStatusTO> disabilityStatuseReferenceDataFor(AccommodationFormTO formTO, SortingAndPaging sAndP) {
		   final List<DisabilityStatusTO> allTOs =
				   DisabilityStatusTO.toTOList(disabilityStatusService.getAll(sAndP).getRows());
	
		  if ( formTO.getPersonDisability() == null ||
		      formTO.getPersonDisability().getDisabilityStatusId() == null ) {
		
		      return filterInactiveExceptFor(Lists.<UUID>newArrayListWithCapacity(0), allTOs);
		
		   }
		
		    return filterInactiveExceptFor(
		       Lists.newArrayList(formTO.getPersonDisability().getDisabilityStatusId()),
		        allTOs);
		  } 
	
	
	private <T extends AbstractReferenceTO> List<T>
	 filterInactiveExceptFor(Collection<UUID> ids, List<T> toFilter) {
	  List<T> filtered = Lists.newArrayListWithCapacity(toFilter.size());
	  for ( T filterable : toFilter ) {
	   if ( filterable.getObjectStatus() == ObjectStatus.ACTIVE ||
	      ids.contains(filterable.getId())) {
	     filtered.add(filterable);
	   	}
	  }
	   return filtered;
	 } 
	
	private static interface UUIDExtractor<T extends AbstractAuditableTO> {
		   UUID fromTO(T to);
		 } 
			
	private static final UUIDExtractor<PersonDisabilityAccommodationTO> PERSON_ASSOC_DISABILITY_ACCOMMODATION_UUID_EXTRACTOR =
			   new UUIDExtractor<PersonDisabilityAccommodationTO>() {
			     @Override
			     public UUID fromTO(PersonDisabilityAccommodationTO to) {
			       return to.getDisabilityAccommodationId();
			      }
			    };
			    
    private static final UUIDExtractor<PersonDisabilityAgencyTO> PERSON_ASSOC_DISABILITY_AGENCY_UUID_EXTRACTOR =
			   new UUIDExtractor<PersonDisabilityAgencyTO>() {
			     @Override
			     public UUID fromTO(PersonDisabilityAgencyTO to) {
			       return to.getDisabilityAgencyId();
			      }
			    };
			    
    private static final UUIDExtractor<PersonDisabilityTypeTO> PERSON_ASSOC_DISABILITY_TYPE_UUID_EXTRACTOR =
			   new UUIDExtractor<PersonDisabilityTypeTO>() {
			     @Override
			     public UUID fromTO(PersonDisabilityTypeTO to) {
			       return to.getDisabilityTypeId();
			      }
			    };

	// no personassoc superclass for these Person*TOs, hence the callback
	 private <T extends AbstractAuditableTO> Collection<UUID>
	 associatedIds(List<T> tos, UUIDExtractor<T> uuidExtractor) {
	   List<UUID> uuids = Lists.newArrayList();
	  if ( tos == null ) {
	    return uuids;
	  }
	  for ( T to : tos ) {
	    uuids.add(uuidExtractor.fromTO(to));
	  }
	 return uuids;
	 } 
			
	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}