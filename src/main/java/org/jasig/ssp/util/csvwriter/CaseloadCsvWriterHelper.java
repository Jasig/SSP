package org.jasig.ssp.util.csvwriter;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.ScrollableResults;
import org.jasig.ssp.model.PersonSearchResult2;
import org.jasig.ssp.service.reference.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import au.com.bytecode.opencsv.CSVWriter;

public class CaseloadCsvWriterHelper {

	


	private HttpServletResponse response;
	private static String[] colHeaders = new String[16];
	
	private static String COL_PERSON_ID = "PERSON_ID";
	private static String COL_SCHOOL_ID = "SCHOOL_ID";
	private static String COL_FIRST_NAME = "FIRST_NAME";
	private static String COL_MIDDLE_NAME = "MIDDLE_NAME";
	private static String COL_LAST_NAME = "LAST_NAME";
	private static String COL_BIRTH_DATE = "BIRTH_DATE";
	private static String COL_STUDENT_TYPE_NAME = "STUDENT_TYPE";
	private static String COL_CURRENT_APPOINTMENT_START_TIME = "CURRENT_APPOINTMENT_START_TIME";
	private static String COL_STUDENT_INTAKE_COMPLETE_DATE = "STUDENT_INTAKE_COMPLETE_DATE";
	private static String COL_ACTIVE_ALERTS = "ACTIVE_ALERTS";
	private static String COL_CLOSED_ALERTS = "CLOSED_ALERTS";
	private static String COL_NUM_EARLY_ALERT_RESPONSES_REQUIRED = "NUM_EARLY_ALERT_RESPONSES_REQUIRED";
	private static String COL_COACH_FIRST_NAME = "COACH_FIRST_NAME";
	private static String COL_COACH_LAST_NAME = "COACH_LAST_NAME";
	private static String COL_CURRENT_PROGRAM_STATUS = "CURRENT_PROGRAM_STATUS";
	private static String COL_START_TERM = "TART_TERM";
	
	static {
		colHeaders[0] = COL_PERSON_ID;
		colHeaders[1] = COL_SCHOOL_ID;
		colHeaders[2] = COL_FIRST_NAME;
		colHeaders[3] = COL_MIDDLE_NAME;
		colHeaders[4] = COL_LAST_NAME;
		colHeaders[5] = COL_BIRTH_DATE;
		colHeaders[6] = COL_STUDENT_TYPE_NAME;
		colHeaders[7] = COL_CURRENT_APPOINTMENT_START_TIME;
		colHeaders[8] = COL_STUDENT_INTAKE_COMPLETE_DATE;
		colHeaders[9] = COL_ACTIVE_ALERTS;
		colHeaders[10] = COL_CLOSED_ALERTS;
		colHeaders[11] = COL_NUM_EARLY_ALERT_RESPONSES_REQUIRED;
		colHeaders[12] = COL_COACH_FIRST_NAME;
		colHeaders[13] = COL_COACH_LAST_NAME;
		colHeaders[14] = COL_CURRENT_PROGRAM_STATUS;
		colHeaders[15] = COL_START_TERM;

	}
	
	

	public CaseloadCsvWriterHelper(HttpServletResponse response) {
		super();
		this.response = response;
	}

	public CSVWriter initCsvWriter()
			throws IOException {
		return new CSVWriter(response.getWriter());
	}

	public void renderMergedOutputModel(ScrollableResults results, Long maxCount) throws IOException {
		CSVWriter csvWriter = initCsvWriter();
		writeCsvHeader(csvWriter);
		writeCsvBody(csvWriter, results,maxCount);
		writingDone(csvWriter);		
	}
	public void writingDone(CSVWriter csvWriter)
			throws IOException {
		csvWriter.flush();
		csvWriter.close();
	}

	public void writeCsvHeader(CSVWriter csvWriter) {
		writeLine(colHeaders, csvWriter);
	}

	public void writeLine(String[] line, CSVWriter csvWriter) {
		csvWriter.writeNext(normalizeLine(line));
	}

	/**
	 * Have to make sure all elements are wrapped in quotes, even those with
	 * null values, in order to make it easier on the SBC client to load CSV
	 * files up into HSQLDB.
	 * 
	 * @param line
	 * @return
	 */
	protected String[] normalizeLine(String[] line) {
		for (int i = 0; i < line.length; i++) {
			line[i] = StringUtils.hasLength(line[i]) ? line[i] : "";
		}
		return line;
	}

	protected String[] csvHeaderForModel(Map<String, Object> model) {
		return colHeaders;
	}


    protected void writeCsvBody(CSVWriter csvWriter, ScrollableResults results, Long maxCount) {
    	int i = 0;

            while ( results.next() && i < maxCount ) {
                Object result = results.get()[0];
				String[] bodyRow = csvRowForCaseloadRecord((PersonSearchResult2)result);
                if ( bodyRow != null ) {
                    writeLine(bodyRow, csvWriter);
                }
                i++;
        }
    }

	private String[] csvRowForCaseloadRecord(PersonSearchResult2 result) {
		List<String> row = new ArrayList<String>();
		row.add(result.getPersonId() == null ? null : result.getPersonId().toString());
		row.add(result.getSchoolId());
		row.add(result.getFirstName());
		row.add(result.getMiddleName());
		row.add(result.getLastName());
		row.add(formatDate(result.getBirthDate()));
		row.add(result.getStudentTypeName());
		row.add(formatDate(result.getCurrentAppointmentStartTime()));
		row.add(formatDate(result.getStudentIntakeCompleteDate()));
		row.add(new Integer(result.getActiveAlerts()).toString());
		row.add(new Integer(result.getClosedAlerts()).toString());
		row.add(new Integer(result.getNumberEarlyAlertResponsesRequired()).toString());
		row.add(result.getActualStartTerm());
        return row.toArray(new String[row.size()]);
	}

	private String formatDate(Date date) {
		if(date == null)
		{
			return null;
		}
		SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
		return formatter.format(date);
	}
 
}
