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
package org.jasig.ssp.web.api;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.naming.OperationNotSupportedException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.jasig.ssp.model.MapStatusReport;
import org.jasig.ssp.model.MapStatusReportCourseDetails;
import org.jasig.ssp.model.MapStatusReportSubstitutionDetails;
import org.jasig.ssp.model.MapStatusReportTermDetails;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.Plan;
import org.jasig.ssp.model.SubjectAndBody;
import org.jasig.ssp.model.external.ExternalStudentTranscriptCourse;
import org.jasig.ssp.model.external.ExternalSubstitutableCourse;
import org.jasig.ssp.model.external.Term;
import org.jasig.ssp.service.MapStatusReportService;
import org.jasig.ssp.service.MessageService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.PlanService;
import org.jasig.ssp.service.RequestTrustService;
import org.jasig.ssp.service.SecurityService;
import org.jasig.ssp.service.external.ExternalPersonPlanStatusService;
import org.jasig.ssp.service.external.TermService;
import org.jasig.ssp.service.reference.ConfigService;
import org.jasig.ssp.transferobject.MapStatusReportCourseDetailTO;
import org.jasig.ssp.transferobject.MapStatusReportSubstitutionDetailTO;
import org.jasig.ssp.transferobject.MapStatusReportTO;
import org.jasig.ssp.transferobject.MapStatusReportTermDetailTO;
import org.jasig.ssp.transferobject.PlanOutputTO;
import org.jasig.ssp.transferobject.ServiceResponse;
import org.jasig.ssp.transferobject.reports.MapStatusReportPerson;
import org.jasig.ssp.util.security.DynamicPermissionChecking;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/1/person/{personId}/map/statusReport")
public class MapStatusReportController  extends AbstractBaseController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(MapStatusReportController.class);

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
	
	@Autowired
	private PlanService service;
	
	@Autowired
	private PersonService personService;
	
	@Autowired
	private TermService termService;
	
	@Autowired
	private transient SecurityService securityService;

	@Autowired
	private transient RequestTrustService requestTrustService;
	
	@Autowired
	private transient ConfigService configService;
	
	@Autowired
	private transient MessageService messageService;
	
	@Autowired
	private transient MapStatusReportService mapStatusReportService;
	
	@Autowired
	private transient ExternalPersonPlanStatusService planStatusService;
	
	@Autowired
	private transient PlanService planService;
	
	

 
	/**
	 * Retrieves the specified list from persistent storage.
	 * 
	 * @param id
	 *            The specific id to use to lookup the associated data.
	 * @return The specified instance if found.
	 * @throws ObjectNotFoundException
	 *             If specified object could not be found.
	 * @throws ValidationException
	 *             If that specified data is not invalid.
	 */
	@RequestMapping(method = RequestMethod.GET)
	@DynamicPermissionChecking
	public @ResponseBody
	MapStatusReportTO get(final @PathVariable UUID personId,
			final HttpServletRequest request) throws ObjectNotFoundException,
			ValidationException {

		assertStandardMapReadApiAuthorization(request);
		
		Collection<MapStatusReport> rows = mapStatusReportService.getAllForPerson(new Person(personId), new SortingAndPaging(ObjectStatus.ACTIVE)).getRows();
		if(!rows.isEmpty())
			return new MapStatusReportTO(rows.iterator().next());
		return null;
	}

	@RequestMapping(value = "/courseDetails",method = RequestMethod.GET)
	@DynamicPermissionChecking
	public @ResponseBody
	List<MapStatusReportCourseDetailTO> getCourseDetails(final @PathVariable UUID personId,
			final HttpServletRequest request)  {

		assertStandardMapReadApiAuthorization(request);
		List<MapStatusReportCourseDetailTO> resultTO = new ArrayList<MapStatusReportCourseDetailTO>();
		
		List<MapStatusReportCourseDetails> result = mapStatusReportService.getAllCourseDetailsForPerson(new Person(personId));
		Collections.sort(result, new Comparator<MapStatusReportCourseDetails>() {
			@Override
			public int compare(MapStatusReportCourseDetails o1, MapStatusReportCourseDetails o2) {
				Term o1Term, o2Term;
				try {
					 o1Term = termService.getByCode(o1.getTermCode());
					 o2Term = termService.getByCode(o2.getTermCode());
				} catch (Exception e)
				{
					return 0;
				}
				return o1Term.getStartDate().compareTo(o2Term.getStartDate());
			}
		});		
		for (MapStatusReportCourseDetails mapStatusReportCourseDetails : result) {
			resultTO.add(new MapStatusReportCourseDetailTO(mapStatusReportCourseDetails));
		}
		return resultTO;
	}

	@RequestMapping(value = "/termDetails",method = RequestMethod.GET)
	@DynamicPermissionChecking
	public @ResponseBody
	List<MapStatusReportTermDetailTO> getTermDetails(final @PathVariable UUID personId,
			final HttpServletRequest request)  {

		List<MapStatusReportTermDetailTO> resultTO = new ArrayList<MapStatusReportTermDetailTO>();
		assertStandardMapReadApiAuthorization(request);
		
		List<MapStatusReportTermDetails> result = mapStatusReportService.getAllTermDetailsForPerson(new Person(personId));
		Collections.sort(result, new Comparator<MapStatusReportTermDetails>() {
			@Override
			public int compare(MapStatusReportTermDetails o1, MapStatusReportTermDetails o2) {
				Term o1Term, o2Term;
				try {
					 o1Term = termService.getByCode(o1.getTermCode());
					 o2Term = termService.getByCode(o2.getTermCode());
				} catch (Exception e)
				{
					return 0;
				}
				return o1Term.getStartDate().compareTo(o2Term.getStartDate());
			}
		});			
		for (MapStatusReportTermDetails mapStatusReportCourseDetails : result) {
			resultTO.add(new MapStatusReportTermDetailTO(mapStatusReportCourseDetails));
		}
		return resultTO;
	}	

	@RequestMapping(value = "/substitutionDetails",method = RequestMethod.GET)
	@DynamicPermissionChecking
	public @ResponseBody
	List<MapStatusReportSubstitutionDetailTO> getSubstitutionDetails(final @PathVariable UUID personId,
			final HttpServletRequest request)  {

		List<MapStatusReportSubstitutionDetailTO> resultTO = new ArrayList<MapStatusReportSubstitutionDetailTO>();
		assertStandardMapReadApiAuthorization(request);
		
		List<MapStatusReportSubstitutionDetails> result = mapStatusReportService.getAllSubstitutionDetailsForPerson(new Person(personId));
		Collections.sort(result, new Comparator<MapStatusReportSubstitutionDetails>() {
			@Override
			public int compare(MapStatusReportSubstitutionDetails o1, MapStatusReportSubstitutionDetails o2) {
				Term o1Term, o2Term;
				try {
					 o1Term = termService.getByCode(o1.getTermCode());
					 o2Term = termService.getByCode(o2.getTermCode());
				} catch (Exception e)
				{
					return 0;
				}
				return o1Term.getStartDate().compareTo(o2Term.getStartDate());
			}
		});	
		for (MapStatusReportSubstitutionDetails mapStatusReportCourseDetails : result) {
			resultTO.add(new MapStatusReportSubstitutionDetailTO(mapStatusReportCourseDetails));
		}
		return resultTO;
	}	
	
	@RequestMapping(value = "/calculateStatus",method = RequestMethod.PUT)
	@PreAuthorize("hasRole('ROLE_PERSON_MAP_WRITE')")
	public @ResponseBody
	Boolean calculateStatus(final @PathVariable UUID personId,
			final HttpServletRequest request) throws ObjectNotFoundException, ValidationException  {

		mapStatusReportService.calculateStatusForStudent(personId);

		return true;
	}

	@PreAuthorize("hasRole('ROLE_PERSON_MAP_WRITE')")
	@RequestMapping(method = RequestMethod.PUT)
	public @ResponseBody
	MapStatusReportTO save(@PathVariable final UUID id, @Valid @RequestBody final MapStatusReportTO obj)
			throws OperationNotSupportedException {
		throw new OperationNotSupportedException("MapStatusReports are read only");
	}
	
	@PreAuthorize("hasRole('ROLE_PERSON_MAP_WRITE')")
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody
	MapStatusReportTO create(@PathVariable final UUID id, @Valid @RequestBody final MapStatusReportTO obj)
			throws OperationNotSupportedException {
		throw new OperationNotSupportedException("MapStatusReports are read only");
	}
	/**
	 * Marks the specified plan instance with a status of
	 * {@link ObjectStatus#INACTIVE}.
	 * 
	 * @param id
	 *            The id of the data instance to mark deleted.
	 * @return Success boolean.
	 * @throws ObjectNotFoundException
	 *             If specified object could not be found.
	 */
	@PreAuthorize("hasRole('ROLE_PERSON_MAP_WRITE')")
	@RequestMapping(method = RequestMethod.DELETE)
	public @ResponseBody
	ServiceResponse delete(@PathVariable final UUID id)
			throws OperationNotSupportedException {
		throw new OperationNotSupportedException("MapStatusReports are read only");

	}
	
	private void assertStandardMapReadApiAuthorization(HttpServletRequest request)
			throws AccessDeniedException {
		if ( securityService.hasAuthority("ROLE_PERSON_READ") ||
				securityService.hasAuthority("ROLE_PERSON_MAP_READ") ) {
			return;
		}
		try {
			requestTrustService.assertHighlyTrustedRequest(request);
		} catch ( AccessDeniedException e ) {
			throw new AccessDeniedException("Untrusted request with"
					+ " insufficient permissions.", e);
		}
	}

	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

}
