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
package org.jasig.ssp.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.jasig.ssp.dao.StudentDocumentDao;
import org.jasig.ssp.model.FileUploadResponse;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.StudentDocument;
import org.jasig.ssp.service.AbstractAuditableCrudService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.SecurityService;
import org.jasig.ssp.service.StudentDocumentService;
import org.jasig.ssp.service.reference.ConfidentialityLevelService;
import org.jasig.ssp.transferobject.StudentDocumentTO;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * Person service implementation
 * 
 * @author tony.arland
 */
@Service
@Transactional
public class StudentDocumentServiceImpl extends AbstractAuditableCrudService<StudentDocument>
		implements StudentDocumentService {

	@Autowired 
	private StudentDocumentDao dao;
	
	@Autowired
	private transient ConfidentialityLevelService confidentialityLevelService;
	
	@Autowired
	private PersonService personService;
	
	@Autowired
	private transient SecurityService securityService;
	
	@Value("#{configProperties.student_documents_base_dir}")
	private  String studentDocumentsBaseDir = "";
	
	@Value("#{configProperties.student_documents_volumes}")
	private  String studentDocumentsVolumes  = "";
	
	@Value("#{configProperties.student_documents_file_types}")
	private  String studentDocumentsFileTypes  = "";

	private Set<String> fileTypes;
	
	private List<String> volumes = new ArrayList<String>();

	private static int lastVolumeIndex = 0;

	private void initVolumes() {
		
		if(StringUtils.isEmpty(studentDocumentsVolumes))
		{
			volumes.add("");
		}
		else
		{
			StringTokenizer tokenizer = new StringTokenizer(studentDocumentsVolumes, ",");
			while(tokenizer.hasMoreElements())
			{
				volumes.add((String) tokenizer.nextElement());
			}
		}
	}
	
	private void initFileTypes() {
		// Tree set since we happen to dump this set out onto the screen when
		// there's a problem, so it's nice to have the accepted file types
		// sorted
		fileTypes = new TreeSet<String>();
		if(!(StringUtils.isEmpty(getStudentDocumentsFileTypes()))) {
			String[] tokenizedTypes = getStudentDocumentsFileTypes().split(",");
			for ( String type : tokenizedTypes ) {
				type = StringUtils.trimToNull(type);
				if ( type != null ) {
					if ( type.startsWith(".") ) {
						type = type.substring(1);
					}
					fileTypes.add(type.replaceFirst("\\.", ""));
				}
			}
		}
	}
	
	@Override
	public PagingWrapper<StudentDocument> getAllForPerson(Person person,
			SortingAndPaging sAndP) {
		return dao.getAllForPerson(person, sAndP,securityService.currentlyAuthenticatedUser());
	}

	@Override
	protected StudentDocumentDao getDao() {
		return dao;
	}

	@Override
	public StudentDocument save(StudentDocument obj)
			throws ObjectNotFoundException, ValidationException {
		return dao.save(obj);
	}

	@Override
	public StudentDocument createStudentDocFromUploadBean(StudentDocumentTO bean, String fileLocation,UUID studentId) throws ObjectNotFoundException {
		
		StudentDocument studentDocument = new StudentDocument();
		studentDocument.setComments(bean.getComment());
		studentDocument.setFileLocation(fileLocation);
		studentDocument.setName(bean.getName());
		studentDocument.setAuthor(getSecurityService().currentlyAuthenticatedUser().getPerson());
		if(bean.getConfidentialityLevelId() != null)
		{
			studentDocument.setConfidentialityLevel(getConfidentialityLevelService().get(bean.getConfidentialityLevelId()));
		}
		studentDocument.setPerson(getPersonService().get(studentId));
		studentDocument.setFileName(bean.getFile().getOriginalFilename());
		return studentDocument;
	}
	
	@Override
	public void downloadFile(UUID id, HttpServletResponse response)
			throws ObjectNotFoundException, FileNotFoundException, IOException {
		StudentDocument studentDocument = get(id);
		if (studentDocument == null) {
			throw new ObjectNotFoundException(
					"could not find document with id of " + id,
					StudentDocument.class.toString());
		}
		File file = new File(calculateAbsoluteFileLocation(studentDocument.getFileLocation()));
		InputStream is = new FileInputStream(file);
		response.setHeader("Content-Disposition", "attachment; filename="+studentDocument.getFileName()); 
		// copy it to response's OutputStream
		IOUtils.copy(is, response.getOutputStream());
		response.flushBuffer();
	}

	private String calculateAbsoluteFileLocation(String relativeFileLocation) {
		return new File(new File(getStudentDocumentsBaseDir()), relativeFileLocation).getAbsolutePath();
	}

	private synchronized String calculateNewRelativeFileLocation(String originalFileName) {
		
		if(volumes.isEmpty())
		{
			initVolumes();
		}
		
		//base + next volume
		String volume = volumes.get(lastVolumeIndex++);
		volume =  "".equals(volume) ? volume : "/"+volume;
		String location = volume;
		//+ year + month + originalFileName
		location = location+"/"+Calendar.getInstance().get(Calendar.YEAR) +"/"+Calendar.getInstance().get(Calendar.MONTH) + "/"+Calendar.getInstance().getTimeInMillis();
		
		//mod the index so the index is never > volumes.size()
		lastVolumeIndex = lastVolumeIndex % volumes.size();
		
		return FilenameUtils.separatorsToSystem(location);
	}

	@Override
	public void createStudentDoc(UUID personId, StudentDocumentTO uploadItem,
			FileUploadResponse extjsFormResult) throws IOException,
			ObjectNotFoundException, ValidationException {
		CommonsMultipartFile file = uploadItem.getFile();
		
		validateFile(file);
		
		String relativeFileLocation = calculateNewRelativeFileLocation(file.getOriginalFilename());
		String absoluteFileLocation = calculateAbsoluteFileLocation(relativeFileLocation);
		File savedFile = new File(absoluteFileLocation);
		savedFile.mkdirs();
		file.transferTo(savedFile);
		StudentDocument doc = createStudentDocFromUploadBean(uploadItem,relativeFileLocation,personId);
		save(doc);
		//set extjs return - sucsess
		extjsFormResult.setSuccess(true);
	}
	
	private synchronized void validateFile(CommonsMultipartFile file) {
		//validate file extension 
		if(fileTypes == null)
		{
			initFileTypes();
		}
		if ( fileTypes.isEmpty() ) {
			throw new IllegalArgumentException("File upload is disabled because no accepted file types have been configured.");
		}
		String fname = file.getOriginalFilename().trim();
		int extDelimIdx = fname.lastIndexOf(".");
		if ( extDelimIdx < 0 ) {
			throw new IllegalArgumentException("File type not accepted.  Please try one of these types: "+fileTypes);
		}
		if ( extDelimIdx == fname.length() - 1 ) {
			throw new IllegalArgumentException("File type not accepted.  Please try one of these types: "+fileTypes);
		}
		if ( !(fileTypes.contains(fname.substring(extDelimIdx + 1))) ) {
			throw new IllegalArgumentException("File type not accepted.  Please try one of these types: "+fileTypes);
		}
	}

	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

	public ConfidentialityLevelService getConfidentialityLevelService() {
		return confidentialityLevelService;
	}

	public void setConfidentialityLevelService(
			ConfidentialityLevelService confidentialityLevelService) {
		this.confidentialityLevelService = confidentialityLevelService;
	}

	public PersonService getPersonService() {
		return personService;
	}

	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}

	@Override
	public String getStudentDocumentsBaseDir() {
		return studentDocumentsBaseDir;
	}

	public String getStudentDocumentsVolumes() {
		return studentDocumentsVolumes;
	}

	public  Set<String> getFileTypes() {
		return fileTypes;
	}

	public  void setFileTypes(Set<String> fileTypes) {
		this.fileTypes = fileTypes;
	}

	public String getStudentDocumentsFileTypes() {
		return studentDocumentsFileTypes;
	}

	public void setStudentDocumentsFileTypes(String studentDocumentsFileTypes) {
		this.studentDocumentsFileTypes = studentDocumentsFileTypes;
	}




}