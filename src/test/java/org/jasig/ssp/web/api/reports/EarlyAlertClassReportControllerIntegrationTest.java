package org.jasig.ssp.web.api.reports;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.engine.JRException;

import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.service.stub.Stubs.CampusFixture;
import org.jasig.ssp.util.service.stub.Stubs.TermFixture;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;

import com.google.common.base.Predicate;

public class EarlyAlertClassReportControllerIntegrationTest extends
		AbstractReportControllerIntegrationTest {



	@Autowired
	private transient EarlyAlertClassReportController controller;


	//@Test
	public void testGetEarlyAlertClassReportWithFilters()
			throws IOException, ObjectNotFoundException, JRException {
		final MockHttpServletResponse response = new MockHttpServletResponse();
		
		controller.getEarlyAlertClassReport(response, 
						CampusFixture.TEST.id(), 
						TermFixture.FALL_2012.name(),
						null,
						null,
						"csv");

		// "body" is the actual results and the header that describes its columns.
		// This is as opposed to rows which precede the header, which describe
		// the filtering criteria
		final List<String> expectedReportBodyLines = new ArrayList<String>(4);
		//TODO Eliminate ,, from code
		expectedReportBodyLines.add(",,,,,,,,NON ,,");
		expectedReportBodyLines.add("COURSE,SECTION,,CAMPUS,,EARLY ALERTS,ENROLLE,PASSIN,,WITHDRAW,Z GRADE");
		expectedReportBodyLines.add( ",,,,,,,,,,null");
		expectedReportBodyLines.add("null,,null,,null,null,null,null,null,null,");	}

	@Test
	public void testGetEarlyAlertClassReportWithNoFilter()
			throws IOException, ObjectNotFoundException, JRException {

		sessionFactory.getCurrentSession().flush();

		final MockHttpServletResponse response = new MockHttpServletResponse();

		controller.getEarlyAlertClassReport(response, 
				null, 
				null,
				null,
				null,
				"csv");
		final List<String> expectedReportBodyLines = new ArrayList<String>(4);
		//TODO Eliminate ,, from code
		expectedReportBodyLines.add(",,,,,,,,NON ,,");
		expectedReportBodyLines.add("COURSE,SECTION,,CAMPUS,,EARLY ALERTS,ENROLLE,PASSIN,,WITHDRAW,Z GRADE");
		expectedReportBodyLines.add( ",,,,,,,,,,null");
		expectedReportBodyLines.add("null,,null,,null,null,null,null,null,null,");

		expectReportBodyLines(expectedReportBodyLines, response, null);
	}
	
	@Override
	protected Predicate<String> afterHeader() {
		return afterLineContaining("Early Alert Class Report");
	}

}
