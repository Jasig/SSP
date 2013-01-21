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
package org.jasig.ssp.web.api.reports;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

import org.jasig.ssp.factory.PersonTOFactory;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.external.Term;
import org.jasig.ssp.model.reference.AbstractReference;
import org.jasig.ssp.model.reference.EarlyAlertOutcome;
import org.jasig.ssp.model.reference.ReferralSource;
import org.jasig.ssp.model.reference.SpecialServiceGroup;
import org.jasig.ssp.model.reference.StudentType;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.ReferenceService;
import org.jasig.ssp.service.external.TermService;
import org.jasig.ssp.service.reference.EarlyAlertOutcomeService;
import org.jasig.ssp.service.reference.ReferralSourceService;
import org.jasig.ssp.service.reference.SpecialServiceGroupService;
import org.jasig.ssp.service.reference.StudentTypeService;
import org.jasig.ssp.service.reference.impl.AbstractReferenceService;
import org.jasig.ssp.transferobject.PersonTO;
import org.jasig.ssp.util.DateTerm;
import org.jasig.ssp.web.api.AbstractBaseController;
import org.slf4j.Logger;

public class EarlyAlertReportBaseController extends AbstractBaseController {

	@Override
	protected Logger getLogger() {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected void generateReport(HttpServletResponse response, Map<String, Object> parameters, 
			Collection<?> beanCollection, String url, String reportType, String reportName) throws JRException, IOException{
		
		final InputStream is = getClass().getResourceAsStream(url);
		final ByteArrayOutputStream os = new ByteArrayOutputStream();
		
		
		JRDataSource beanDS;
		if (beanCollection == null || beanCollection.size() <= 0) {
			beanDS = new JREmptyDataSource();
		} else {
			beanDS = new JRBeanCollectionDataSource(beanCollection);
		}
		
		JasperFillManager.fillReportToStream(is, os, parameters, beanDS);
		final InputStream decodedInput = new ByteArrayInputStream(
				os.toByteArray());

		if ("pdf".equals(reportType)) {
			response.setHeader(
					"Content-disposition",
					"attachment; filename=" + reportName + ".pdf");
			JasperExportManager.exportReportToPdfStream(decodedInput,
					response.getOutputStream());
		} else if ("csv".equals(reportType)) {
			response.setContentType("application/vnd.ms-excel");
			response.setHeader(
					"Content-disposition",
					"attachment; filename=" + reportName + ".csv");

			final JRCsvExporter exporter = new JRCsvExporter();
			exporter.setParameter(JRExporterParameter.INPUT_STREAM,
					decodedInput);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
					response.getOutputStream());
			exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,
					Boolean.FALSE);

			exporter.exportReport();
		}
		
		response.flushBuffer();
		is.close();
		os.close();		
	}
	
	@SuppressWarnings("unchecked")
	protected String concatSpecialGroupsNameFromUUIDs(List<UUID> specialServiceGroupIds, SpecialServiceGroupService ssgService) 
			throws ObjectNotFoundException{
		return concatFromUUIDReferenceServices(specialServiceGroupIds,  (AbstractReferenceService<SpecialServiceGroup>)ssgService);
	}
	
	@SuppressWarnings("unchecked")
	protected String concatStudentTypesFromUUIDs(List<UUID> studentTypeIds, StudentTypeService studentTypeService) 
			throws ObjectNotFoundException{
		return concatFromUUIDReferenceServices(studentTypeIds,  (AbstractReferenceService<StudentType>)studentTypeService);
	}
	
	@SuppressWarnings("unchecked")
	protected String concatReferralSourcesFromUUIDS(List<UUID> referralSourcesIds, ReferralSourceService referralSourcesService) 
			throws ObjectNotFoundException{
		return concatFromUUIDReferenceServices(referralSourcesIds,  (AbstractReferenceService<ReferralSource>)referralSourcesService);
	}
	
	protected Map<String, Object> setDateTermToMap(Map<String, Object> parameters, DateTerm dateTerm){
		parameters.put("startDate", dateTerm.startDateString());
		parameters.put("endDate", dateTerm.endDateString());
		parameters.put("term", dateTerm.getTermName());
		parameters.put("termName", dateTerm.getTermName());
		parameters.put("termCode", dateTerm.getTermCode());
		return parameters;

	}
	
	@SuppressWarnings("unchecked")
	protected String concatEarlyAlertOutcomesFromUUIDs(List<UUID> outcomeIds, EarlyAlertOutcomeService referenceService) 
			throws ObjectNotFoundException{
		return concatFromUUIDReferenceServices(outcomeIds,  (AbstractReferenceService<EarlyAlertOutcome>)referenceService);
	}
	
	protected List<UUID> cleanUUIDListOfNulls(List<UUID> uuids){
		ArrayList<UUID> cleanUUIDs = null;
		if(uuids != null){
			cleanUUIDs = new ArrayList<UUID>();
			Iterator<UUID> iterator = uuids.iterator();
			while(iterator.hasNext()){
				UUID uuid = iterator.next();
				if(uuid == null)
					continue;
				cleanUUIDs.add(uuid);
			}
		}
		return cleanUUIDs;
	}
	
	protected String getFullName(PersonTO person){
		return person == null ? "" : 
			person.getFirstName() + " " + person.getLastName();
	}
	
	protected PersonTO getPerson(UUID personId, PersonService personService, PersonTOFactory personTOFactory) 
			throws ObjectNotFoundException{
		if(personId != null)
		{
			return  personTOFactory.from( personService.get(personId));
		}	
		return null;
	}
	
	protected String concatNamesFromStrings(List<String> strs) 
			throws ObjectNotFoundException{
				final StringBuffer namesStringBuffer = new StringBuffer();
				if ((strs != null) && (strs.size() > 0)) {
					final Iterator<String> strIter = strs
							.iterator();
					while (strIter.hasNext()) {
						namesStringBuffer.append("\u2022 " + 
								strIter.next());
						namesStringBuffer.append("    ");																		
					}
					
				}
				return namesStringBuffer.toString();
	}
	
	private String concatFromUUIDReferenceServices(List<UUID> outcomeIds, @SuppressWarnings("rawtypes") AbstractReferenceService referenceService) 
			throws ObjectNotFoundException{
		ArrayList<String> strs = new ArrayList<String>();
				if ((outcomeIds != null)
						&& (outcomeIds.size() > 0)) {
					final Iterator<UUID> ssgIter = outcomeIds.iterator();
					while (ssgIter.hasNext()) {
						strs.add(((AbstractReference)referenceService.get(ssgIter.next())).getName());																	
					}		
					
				}
		return concatNamesFromStrings(strs) ;
	}

}
