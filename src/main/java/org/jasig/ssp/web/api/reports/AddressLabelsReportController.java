package org.jasig.ssp.web.api.reports;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;

//import org.jasig.ssp.factory.PersonTOFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonSpecialServiceGroup;
import org.jasig.ssp.model.reference.SpecialServiceGroup;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.transferobject.reports.AddressLabelSearchTO;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Service methods for manipulating data about people in the system.
 * <p>
 * Mapped to URI path <code>/1/person</code>
 */

// TODO: Add PreAuthorize
@Controller
@RequestMapping("/1/report/AddressLabels")
public class AddressLabelsReportController extends BaseController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(AddressLabelsReportController.class);

	@Autowired
	private transient PersonService service;

	//@Autowired
	//private transient PersonTOFactory factory;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public @ResponseBody
	void getAddressLabels(HttpServletResponse response,
			final @RequestParam(required = false) ObjectStatus status,	
		
			final @RequestParam(required = false) String tintakeDatefrom, 
			final @RequestParam(required = false) String tintakeDateTo,
			final @RequestParam(required = false) String thomeDepartment,
			final @RequestParam(required = false) String tcoachId,
			final @RequestParam(required = false) String programStatus,
			final @RequestParam(required = false) List<UUID> specialServiceGroupIds,
			final @RequestParam(required = false) List<UUID> referralSourcesIds,
			final @RequestParam(required = false) String tanticipatedStartTerm,
			final @RequestParam(required = false) Integer tanticipatedStartYear,
			final @RequestParam(required = false) List<UUID> studentTypeIds,
			final @RequestParam(required = false) String tregistrationTerm,
			final @RequestParam(required = false) String tregistrationYear,
			
			
			final @RequestParam(required = false) Integer start,
			final @RequestParam(required = false) Integer limit,
			final @RequestParam(required = false) String sort,
			final @RequestParam(required = false) String sortDirection) throws ObjectNotFoundException, JRException, IOException{

		
		
		LOGGER.debug("PROGRAMSTATUS: " + programStatus);
		 
		//Iterator<UUID> myiter = specialServiceGroupIds.iterator();
		//while(myiter.hasNext())
		//{
		//	LOGGER.debug("This is an iterator " + myiter.next());
		//}		
		
		final Date intakeDatefrom = null;
		final Date intakeDateTo= null;
		final UUID homeDepartment= null;
		final UUID coachId= null;

		final String anticipatedStartTerm= null;
		final Integer anticipatedStartYear= null;
		
		final String registrationTerm= null;
		final Integer registrationYear= null;	
		
		
		
		AddressLabelSearchTO searchForm = new AddressLabelSearchTO(intakeDateTo, homeDepartment,
			coachId, programStatus, specialServiceGroupIds,
			referralSourcesIds, anticipatedStartTerm,
			anticipatedStartYear, studentTypeIds,
			registrationTerm, registrationYear);
		
		
		final List<Person> people = service.peopleFromCriteria(
				searchForm,
				SortingAndPaging
						.createForSingleSort(status, start, limit, sort,
								sortDirection,
								null));
		
		LOGGER.debug("Before Person Iter: " + people.size());
		Iterator<Person> personIter = people.iterator();
		while(personIter.hasNext())
		{
			Person p = personIter.next();
			LOGGER.debug("This is an iterator " + p.getSpecialServiceGroups().toString());
			Iterator<PersonSpecialServiceGroup> sgiter = p.getSpecialServiceGroups().iterator();
			while(sgiter.hasNext()){
				//sgiter.next().getSpecialServiceGroup().getName();
				PersonSpecialServiceGroup pssg = sgiter.next();
				LOGGER.debug("\t\t" + pssg.getSpecialServiceGroup().getName());
			}			
		}
		LOGGER.debug("After Person Iter");

		Map parameters = new HashMap();
		parameters.put("ReportTitle", "Address Report");
		parameters.put("DataFile", "Person.java - Bean Array");

		JRBeanArrayDataSource beanDs = new JRBeanArrayDataSource(
				people.toArray());
		InputStream is = getClass().getResourceAsStream(
				"/reports/addressLabels.jasper");
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		JasperFillManager.fillReportToStream(is, os, parameters, beanDs);
		InputStream decodedInput = new ByteArrayInputStream(os.toByteArray());
		JasperExportManager.exportReportToPdfStream(decodedInput,
				response.getOutputStream());
		response.flushBuffer();
		is.close();
		os.close();
	}
	
	
	
	
	
	
	
	

	@Override
	protected Logger getLogger() {
		// TODO Auto-generated method stub
		return null;
	}

}