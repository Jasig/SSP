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
package org.jasig.ssp.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.jasig.ssp.model.FileUploadResponse;
import org.jasig.ssp.model.StudentDocument;
import org.jasig.ssp.transferobject.StudentDocumentTO;
import org.jasig.ssp.web.api.validation.ValidationException;

/**
 * Person service
 */
public interface StudentDocumentService extends PersonAssocAuditableService<StudentDocument>  {


	StudentDocument createStudentDocFromUploadBean(StudentDocumentTO file,
			String fileLocation, UUID personId) throws ObjectNotFoundException;

	String getStudentDocumentsBaseDir();

	void downloadFile(UUID id, HttpServletResponse response)
			throws ObjectNotFoundException, FileNotFoundException, IOException;


	void createStudentDoc(UUID personId, StudentDocumentTO uploadItem,
			FileUploadResponse extjsFormResult) throws IOException,
			ObjectNotFoundException, ValidationException;
		 
}