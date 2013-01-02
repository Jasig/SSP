package org.jasig.ssp.web.api.reports;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
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

import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.reference.EarlyAlertOutcomeService;
import org.jasig.ssp.service.reference.ReferralSourceService;
import org.jasig.ssp.service.reference.SpecialServiceGroupService;
import org.jasig.ssp.service.reference.StudentTypeService;
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
	
	String concatSpecialGroupsNameFromUUIDs(List<UUID> specialServiceGroupIds, SpecialServiceGroupService ssgService) 
			throws ObjectNotFoundException{
		// Get the actual names of the UUIDs for the special groups
				final StringBuffer specialGroupsNamesStringBuffer = new StringBuffer();
				if ((specialServiceGroupIds != null)
						&& (specialServiceGroupIds.size() > 0)) {
					final Iterator<UUID> ssgIter = specialServiceGroupIds.iterator();
					while (ssgIter.hasNext()) {
						specialGroupsNamesStringBuffer.append("\u2022 " + ssgService.get(ssgIter.next()).getName());
						specialGroupsNamesStringBuffer.append("    ");																		
					}		
					
				}
		return specialGroupsNamesStringBuffer.toString();
	}
	
	String concatStudentTypesFromUUIDs(List<UUID> studentTypeIds, StudentTypeService studentTypeService) 
			throws ObjectNotFoundException{
		// Get the actual names of the UUIDs for the special groups
		final StringBuffer studentTypeStringBuffer = new StringBuffer();
		if ((studentTypeIds != null)
				&& (studentTypeIds.size() > 0)) {
			final Iterator<UUID> stIter = studentTypeIds.iterator();
			while (stIter.hasNext()) {
				studentTypeStringBuffer.append("\u2022 " + studentTypeService.get(stIter.next()).getName());
				studentTypeStringBuffer.append("    ");																		
			}	
		}
		return studentTypeStringBuffer.toString();
	}
	
	String concatReferralSourcesFromUUIDS(List<UUID> referralSourcesIds, ReferralSourceService referralSourcesService) 
			throws ObjectNotFoundException{
		// Get the actual names of the UUIDs for the referralSources
				final StringBuffer referralSourcesNameStringBuffer = new StringBuffer();
				if ((referralSourcesIds != null) && (referralSourcesIds.size() > 0)) {
					final Iterator<UUID> referralSourceIter = referralSourcesIds
							.iterator();
					while (referralSourceIter.hasNext()) {
						referralSourcesNameStringBuffer.append("\u2022 " + referralSourcesService.get(
								referralSourceIter.next()).getName());
						
						referralSourcesNameStringBuffer.append("    ");																		
					}
					
				}
				return referralSourcesNameStringBuffer.toString();
	}
	
	Map<String, Object> setStartDateEndDateToMap(Map<String, Object> parameters, Date startDate, Date endDate){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		if(startDate != null)
			parameters.put("startDate", sdf.format(startDate));
		else
			parameters.put("startDate","Not Given");
			
		if(endDate != null)
			parameters.put("endDate", sdf.format(endDate));
		else
			parameters.put("endDate","Not Given");	
		return parameters;

	}
	
	String concatEarlyAlertOutcomesFromUUIDs(List<UUID> outcomeIds, EarlyAlertOutcomeService earlyAlertOutcomeService) 
			throws ObjectNotFoundException{
		// Get the actual names of the UUIDs for the special groups
				final StringBuffer outcomeNamesStringBuffer = new StringBuffer();
				if ((outcomeIds != null)
						&& (outcomeIds.size() > 0)) {
					final Iterator<UUID> ssgIter = outcomeIds.iterator();
					while (ssgIter.hasNext()) {
						outcomeNamesStringBuffer.append("\u2022 " + earlyAlertOutcomeService.get(ssgIter.next()).getName());
						outcomeNamesStringBuffer.append("    ");																		
					}		
					
				}
		return outcomeNamesStringBuffer.toString();
	}

}
