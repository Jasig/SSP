package org.jasig.ssp.web.api.reports;

import org.jasig.ssp.security.permissions.Permission;
import org.jasig.ssp.service.AppointmentService;
import org.jasig.ssp.service.SecurityService;
import org.jasig.ssp.transferobject.reports.ReportListTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/1/report")
public class Reports {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(Reports.class);
	
	@Autowired
	protected transient SecurityService securityService;
	
	
	private static final String ROLE_ACCOMMODATION_READ = "ROLE_ACCOMMODATION_READ";
	
	@Autowired
	protected transient AppointmentService service;
	
	public Reports() {
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize(Permission.SECURITY_REPORT_READ)
	public @ResponseBody ReportListTO getReportList(){
		
		if (securityService.hasAuthority(ROLE_ACCOMMODATION_READ)) {
			return new ReportListTO(true);
		}		
			return new ReportListTO(false);
	}

}
