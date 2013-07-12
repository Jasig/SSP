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

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.validation.Valid;

import org.jasig.ssp.factory.reference.StudentDocumentTOFactory;
import org.jasig.ssp.model.FileUploadRepsonse;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.Plan;
import org.jasig.ssp.model.StudentDocument;
import org.jasig.ssp.security.SspUser;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.SecurityService;
import org.jasig.ssp.service.StudentDocumentService;
import org.jasig.ssp.service.reference.ConfigService;
import org.jasig.ssp.transferobject.PagedResponse;
import org.jasig.ssp.transferobject.PlanTO;
import org.jasig.ssp.transferobject.ServiceResponse;
import org.jasig.ssp.transferobject.StudentDocumentTO;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Controller
@RequestMapping("/1/person/{personId}/studentdocument")
public class StudentDocumentController  extends AbstractBaseController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(StudentDocumentController.class);

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
	
	@Autowired
	private PersonService personService;
	
	@Autowired
	private transient SecurityService securityService;
	
	@Autowired
	private transient ConfigService configService;
	
	@Autowired
	private transient StudentDocumentService studentDocumentService;
	
	@Autowired
	private transient StudentDocumentTOFactory factory;

 
	/**
	 * Retrieves the specified list from persistent storage.
	 * 
	 * @param id
	 *            The specific id to use to lookup the associated data.
	 * @return The specified instance if found.
	 * @throws IOException 
	 * @throws IllegalStateException 
	 * @throws ObjectNotFoundException
	 *             If specified object could not be found.
	 * @throws ValidationException
	 *             If that specified data is not invalid.
	 */
	@RequestMapping(method = RequestMethod.POST)
    public @ResponseBody String create(@PathVariable UUID personId,StudentDocumentTO uploadItem, BindingResult result) throws IllegalStateException, IOException, ObjectNotFoundException, ValidationException{
		 FileUploadRepsonse extjsFormResult = new FileUploadRepsonse();
         
	        if (result.hasErrors()){
	            for(ObjectError error : result.getAllErrors()){
	                System.err.println("Error: " + error.getCode() +  " - " + error.getDefaultMessage());
	            }
	             
	            //set extjs return - error
	            extjsFormResult.setSuccess(false);
	            return extjsFormResult.toString();
	        }
	        CommonsMultipartFile file = uploadItem.getFile();
	        String fileLocation = "/Users/Dev/studentdocs/"+file.getOriginalFilename();
	        file.transferTo(new File(fileLocation));
	        
	        StudentDocument doc = getStudentDocumentService().createStudentDocFromUploadBean(uploadItem,fileLocation,personId);
	        
	        getStudentDocumentService().save(doc);
	         
	        //set extjs return - sucsess
	        extjsFormResult.setSuccess(true);
	         
	        return extjsFormResult.toString();
	 	}

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody PagedResponse<StudentDocumentTO> getAllForStudent(@PathVariable UUID personId) throws ObjectNotFoundException
	{
		PagingWrapper<StudentDocument> result = getStudentDocumentService().getAllForPerson(getPersonService().get(personId), null);
		return new PagedResponse<StudentDocumentTO>(true, result.getResults(), getFactory().asTOList(result.getRows()));
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public @ResponseBody
	StudentDocumentTO save(@PathVariable final UUID id, @Valid @RequestBody final StudentDocumentTO obj)
			throws ValidationException, ObjectNotFoundException, CloneNotSupportedException {
		if (id == null) {
			throw new ValidationException(
					"You submitted without an id to the save method.  Did you mean to create?");
		}

		if (obj.getId() == null) {
			obj.setId(id);
		}
		final StudentDocument oldDocument = getStudentDocumentService().get(id);
		
		oldDocument.setAuthor(securityService.currentlyAuthenticatedUser().getPerson());
		oldDocument.setComments(obj.getComment());
		oldDocument.setName(obj.getName());

		StudentDocument newDocument = getStudentDocumentService().save(oldDocument);
		return getFactory().from(newDocument);
	}	
	
	
	/**
	 * Marks the specified data instance with a status of
	 * {@link ObjectStatus#INACTIVE}.
	 * 
	 * @param id
	 *            The id of the data instance to mark deleted.
	 * @return Success boolean.
	 * @throws ObjectNotFoundException
	 *             If specified object could not be found.
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody
	ServiceResponse delete(@PathVariable final UUID id)
			throws ObjectNotFoundException {
		getStudentDocumentService().delete(id);
		return new ServiceResponse(true);
	}
	public StudentDocumentService getStudentDocumentService() {
		return studentDocumentService;
	}

	public void setStudentDocumentService(StudentDocumentService studentDocumentService) {
		this.studentDocumentService = studentDocumentService;
	}

	public PersonService getPersonService() {
		return personService;
	}

	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}

	public StudentDocumentTOFactory getFactory() {
		return factory;
	}

	public void setFactory(StudentDocumentTOFactory factory) {
		this.factory = factory;
	}

	}
