<%--

    Licensed to Jasig under one or more contributor license
    agreements. See the NOTICE file distributed with this work
    for additional information regarding copyright ownership.
    Jasig licenses this file to you under the Apache License,
    Version 2.0 (the "License"); you may not use this file
    except in compliance with the License. You may obtain a
    copy of the License at:

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing,
    software distributed under the License is distributed on
    an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
    KIND, either express or implied. See the License for the
    specific language governing permissions and limitations
    under the License.

--%>
<jsp:directive.include file="/WEB-INF/jsp/include.jsp"/>

<portlet:renderURL var="cancelUrl" />
<portlet:renderURL var="doneUrl" escapeXml="false"> 
    <portlet:param name="confirm" value="true"/>
    <portlet:param name="studentName" value="STUDENTNAME"/>
</portlet:renderURL>

<c:set var="n"><portlet:namespace/></c:set>

<c:set var="not_applicable" value="Not Used" />
<c:set var="select_subject_abbreviation" value="Select Subject Abbreviations to get list." />
<c:set var="all_found" value="ALL" />
<c:set var="date_range_student" value = "Date Range Student Entered:"/>
<c:set var="date_range_activity" value = "Date Range Activity Created:"/>
<c:set var="date_range_early_alert" value = "Date Range Early Alert Created:"/>
<c:set var="date_range_early_response" value = "Date Range Early Response Created:"/>
<c:set var="date_range_journal_entry" value = "Date Range Journal Entry Created:"/>
<c:set var="date_range_map_entry" value = "Date Range Map Entry Created:"/>

<script src="<rs:resourceURL value="/rs/jquery/1.6.1/jquery-1.6.1.min.js"/>" type="text/javascript"></script>
<script src="http://code.jquery.com/jquery-1.8.0.min.js" type="text/javascript"></script>
<!-- <script src="http://code.jquery.com/jquery-latest.js"></script> -->
<script src="<rs:resourceURL value="/rs/jqueryui/1.8.13/jquery-ui-1.8.13.min.js"/>" type="text/javascript"></script>
<script src="<rs:resourceURL value="/rs/fluid/1.4.0/js/fluid-all-1.4.0.min.js"/>" type="text/javascript"></script>
<script src="<c:url value="/js/reports.js" />" type="text/javascript"></script>
<script src="<c:url value="/js/libs/jquery.print.js" />" type="text/javascript"></script>

<link href="<c:url value="/resources/css/report.css" />" rel="stylesheet" type="text/css">

<!-- Portlet -->
<div id="${n}reportSelector" class="fl-widget portlet report" role="section">
  
  <!-- Portlet Titlebar -->
  <div class="fl-widget-titlebar titlebar portlet-titlebar" role="sectionhead">
  	<h2 class="title" role="heading"><spring:message code="reports.title"/></h2>
    <div class="fl-col-flex2 toolbar" role="toolbar">
      <div class="fl-col">
        <label for="reports-select" class="course-label"><spring:message code="reports.select.label"/>:</label>
        <select class="reports-select">
        </select>
      </div>
      <div class="fl-col fl-text-align-right">&nbsp;</div>
    </div>
    <div style="clear:both"></div>
  </div>

  <!-- Portlet Content -->
  <div class="fl-widget-content content portlet-content" role="main">
  
  	<!-- Portlet Message -->
  	<div class="portlet-msg-info portlet-msg info loading-message" role="status" style="display: none;">
    	<div class="titlebar"></script>
        <h3 class="title"><spring:message code="loading"/> . . .</h3>
      </div>
      <div class="content">
    	  <p>Please wait while the system finished loading forms...</p>
      </div>
    </div>
    

	<div class="reports-form-body"> 



<div class="hideable-form generalStudent-form" style="display:none">
	<h1>General Student Report</h1>
	<form action="/ssp/api/1/report/AddressLabels/" method="get"
		target="_top" onSubmit="return validateForm(this);" class="alert-form">
		
		
		<!-- Home Department -->
		<div class="ea-input">
			<select id="generalStudentHomeDepartment" name="homeDepartment" class="input-home-department-group">
				<option value="">${not_applicable}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Home Department:</span>
		</div>
		<div class="ea-clear"></div>

		<!-- Assigned Coach -->
		<div class="ea-input">
			<select id="assignedCounselorGroup" name="coachId" class="input-assigned-counselor-group">
				<option value="">${all_found}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Assigned Coach:</span>
		</div>
		<div class="ea-clear"></div>		
		
		
		<!-- program Status -->
		<div class="ea-input">
			<select id="programStatusGroup" name="programStatus" class="input-program-status-group">
				<option value="">${not_applicable}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Program Status:</span>
		</div>
		<div class="ea-clear"></div>

		<!-- Student Type -->
		<div class="ea-input">
			<select class="input-student-type-group" id="StudentTypeIds" name="studentTypeIds" multiple="multiple">
			<option value="">${not_applicable}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Student Type:</span>
		</div>
		<div class="ea-clear"></div>
		
		<!-- Service Reason -->
		<div class="ea-input">
			<select class="input-service-reason-group" id="ServiceReasonIds" name="serviceReasonIds" multiple="multiple">
			<option value="">${not_applicable}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Service Reason:</span>
		</div>
		<div class="ea-clear"></div>

		<!-- Special Service Groups -->
		<div class="ea-input">
			<select  class="input-special-service-group" id="SpecialServiceGroupIds" name="specialServiceGroupIds"
				multiple="multiple">
				<option value="">${not_applicable}</option>
				</select>
		</div>
		<div class="ea-label">
			<span>Special Service Groups:</span>
		</div>
		<div class="ea-clear"></div>

		<!-- Referral Source -->
		<div class="ea-input">
			<select class='input-referral-source-group' id="ReferralSourceGroup" name="referralSourcesIds"
				multiple="multiple">
				<option value="">${not_applicable}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Referral Source:</span>
		</div>
		<div class="ea-clear"></div>

		<!-- Date Range Description -->
		<div class="ea-input">
		</div>
		<div class="ea-label">
			<span>${date_range_student}</span>
		</div>
		<div class="ea-clear"></div>

		<!-- Date Range Selection -->
		<div class="ea-time-span">
			<div class="ea-term">
				<div class="ea-input">
					<select id="generalStudentTermCode" name="termCode" class="input-term-group">
						<option value="">${not_applicable}</option>
						</select>
				</div>
				<div class="ea-label">
					<span>Term:</span>
				</div>
				<div class="ea-clear"></div>
			</div>
			<div class="ea-date-range">
				<!-- Date From -->
				<div class="ea-input">
					<input class="input-calendar-type" type="textbox" name="createDateFrom" id="generalStudentCreateDateFrom">
				</div>
				<div class="ea-label">
					<span>Student Added From (inclusive)::</span>
				</div>
				<div class="ea-clear"></div><!-- Date To -->
				<div class="ea-input">
					<input class="input-calendar-type" type="textbox" name="createDateTo" id="generalStudentCreateDateTo">
				</div>
				<div class="ea-label">
					<span>Student Added To (exclusive)::</span>
				</div>
				<div class="ea-clear"></div>
			</div>
			<div class="ea-buttons">
				<div>
					
									<button class="switch-date-range-type button" value="setbyrange">
									</button>
									
				</div>
			</div>
		</div>
		
		<!-- Actual Start Term -->
		<div class="ea-input">
			<select id="generalStudentActualStartTerm" name="actualStartTerm" class="input-term-group">
			<option value="">${not_applicable}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Actual Start Term:</span>
		</div>
		<div class="ea-clear"></div>

		<!-- Actual Start Year -->
		<div class="ea-input">
			<select id="generalStudentActualStartYear" name="actualStartYear" class="input-report-year-group">
			<option value="">${not_applicable}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Actual Start Year:</span>
		</div>

		<!-- Anticipated Start Term -->
		<div class="ea-input">
			<select id="generalStudentAnticipatedStartTerm" name="anticipatedStartTerm" class="input-term-group">
			<option value="">${not_applicable}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Anticipated Start Term:</span>
		</div>
		<div class="ea-clear"></div>

		<!-- Anticipated Start Year -->
		<div class="ea-input">
			<select id="generalStudentAnticipatedStartYear" name="anticipatedStartYear" class="input-report-year-group">
			<option value="">${not_applicable}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Anticipated Start Year:</span>
		</div>
		<div class="ea-clear"></div>

		<!-- output type -->
		<div class="ea-input">
			<label><span>pdf</span></label><input type="radio" name="reportType"
				value="pdf" checked /><br /> <label><span>csv</span></label><input
				type="radio" name="reportType" value="csv" />
		</div> 

		<div class="ea-label">
			<span>Output Type:</span>
		</div>
		<div class="ea-clear"></div>

		<div class="ea-buttons">
			<div class="buttons">
				<input class="button primary button-send" type="submit"
					value="submit" />
			</div>
		</div>
	</form>
</div>





<div class="hideable-form counselor-case-management-report" style="display:none">
	<h1>Coach Case Management Report</h1>
	<form action="/ssp/api/1/report/pretransitioned" method="get"
		target="_top" onSubmit="return validateForm(this);" class="alert-form">

		<!-- Home Department -->
		<div class="ea-input">
			<select id="counselorCaseManagementHomeDepartment" name="homeDepartment" class="input-home-department-group">
				<option value="">${not_applicable}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Home Department:</span>
		</div>
		<div class="ea-clear"></div>


		<!-- Assigned Coach -->
		<div class="ea-input">
			<select id="assignedCounselorGroup" name="coachId" class="input-assigned-counselor-group">
				<option value="">${all_found}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Assigned Coach:</span>
		</div>
		<div class="ea-clear"></div>


		<!-- program Status -->
		<div class="ea-input">
			<select id="programStatusGroup" name="programStatus" class="input-program-status-group">
				<option value="">${not_applicable}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Program Status:</span>
		</div>
		<div class="ea-clear"></div>

		<!-- Student Type -->
		<div class="ea-input">
			<select class="input-student-type-group" id="counselorCaseStudentTypeIds" name="studentTypeIds" multiple="multiple">
			<option value="">${not_applicable}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Student Type:</span>
		</div>
		
		<div class="ea-clear"></div>
		<!-- Service Reason -->
		<div class="ea-input">
			<select class="input-service-reason-group" id="counselorCaseServiceReasonIds" name="serviceReasonIds" multiple="multiple">
			<option value="">${not_applicable}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Service Reason:</span>
		</div>
		<div class="ea-clear"></div>

		<!-- Special Service Groups -->
		<div class="ea-input">
			<select  class="input-special-service-group" id="SpecialServiceGroupIds" name="specialServiceGroupIds"
				multiple="multiple">
				<option value="">${not_applicable}</option>
				</select>
		</div>
		<div class="ea-label">
			<span>Special Service Groups:</span>
		</div>
		<div class="ea-clear"></div>

		<!-- Referral Source -->
		<div class="ea-input">
			<select class='input-referral-source-group' id="ReferralSourceGroup" name="referralSourcesIds"
				multiple="multiple">
				<option value="">${not_applicable}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Referral Source:</span>
		</div>
		<div class="ea-clear"></div>


		<!-- Date Range Description -->
		<div class="ea-input">
		</div>
		<div class="ea-label">
			<span>${date_range_student}</span>
		</div>
		<div class="ea-clear"></div>
		
		<!-- Date Range Selection -->
		<div class="ea-time-span">
			<div class="ea-term">
				<div class="ea-input">
					<select id="caseloadActivityTermCode" name="termCode" class="input-term-group">
						<option value="">${not_applicable}</option>
						</select>
				</div>
				<div class="ea-label">
					<span>Term:</span>
				</div>
				<div class="ea-clear"></div>
			</div>
			<div class="ea-date-range">
				<!-- Date From -->
				<div class="ea-input">
					<input class="input-calendar-type" type="textbox" name="createDateFrom" id="counselorCaseManagementCreateDateFrom">
				</div>
				<div class="ea-label">
					<span>Student Added From (inclusive)::</span>
				</div>
				<div class="ea-clear"></div><!-- Date To -->
				<div class="ea-input">
					<input class="input-calendar-type" type="textbox" name="createDateTo" id="counselorCaseManagementCreateDateTo">
				</div>
				<div class="ea-label">
					<span>Student Added To (exclusive)::</span>
				</div>
				<div class="ea-clear"></div>
			</div>
			<div class="ea-buttons">
				<div>

									<button class="switch-date-range-type button" value="setbyrange">
									</button>
									
				</div>
			</div>
		</div>
		
		<!-- Anticipated Start Term -->
		<div class="ea-input">
			<select id="anticipatedStartTerm" name="anticipatedStartTerm" class="input-term-group">
			<option value="">${not_applicable}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Anticipated Start Term:</span>
		</div>
		<div class="ea-clear"></div>

		<!-- Anticipated Start Year -->
		<div class="ea-input">
			<select id="anticipatedStartYear" name="anticipatedStartYear" class="input-report-year-group">
			<option value="">${not_applicable}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Anticipated Start Year:</span>
		</div>
		<div class="ea-clear"></div>

		<!-- output type -->
		<div class="ea-input">
			<label><span>pdf</span></label><input type="radio" name="reportType"
				value="pdf" checked /><br /> <label><span>csv</span></label><input
				type="radio" name="reportType" value="csv" />
		</div> 

		<div class="ea-label">
			<span>Output Type:</span>
		</div>
		<div class="ea-clear"></div>

		<div class="ea-buttons">
			<div class="buttons">
				<input class="button primary button-send" type="submit"
					value="submit" />
			</div>
		</div>
	</form>
</div>




 

<div class="hideable-form caseload-activity-report" style="display:none">
	<h1>Caseload Activity Report</h1>
	<form action="/ssp/api/1/report/caseloadactivity" method="get"
		target="_top" onSubmit="return validateForm(this);" class="alert-form">

		<!-- Assigned Coach -->
		<div class="ea-input">
			<select id="assignedCounselorGroup" name="coachId" class="input-assigned-counselor-group">
				<option value="">${all_found}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Assigned Coach:</span>
		</div>
		<div class="ea-clear"></div>

		<!-- Home Department -->
		<div class="ea-input">
			<select id="caseloadActivityHomeDepartment" name="homeDepartment" class="input-home-department-group">
				<option value="">${not_applicable}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Home Department:</span>
		</div>
		<div class="ea-clear"></div>



		<!-- Student Type -->
		<div class="ea-input">
			<select class="input-student-type-group" id="StudentTypeIds" name="studentTypeIds" multiple="multiple">
			<option value="">${not_applicable}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Student Type:</span>
		</div>
		
		<!--Special Service Groups -->
		<div class="ea-input">
			<select  class="input-special-service-group" id="caseloadActivitySpecialServiceGroupIds" name="specialServiceGroupIds"
				multiple="multiple">
				<option value="">${not_applicable}</option>
				</select>
		</div>
		<div class="ea-label">
			<span>Special Service Groups:</span>
		</div>
		<div class="ea-clear"></div>
		
		
		<div class="ea-clear"></div>
		<!-- Service Reason -->
		<div class="ea-input">
			<select class="input-service-reason-group" id="caseloadActivityServiceReasonIds" name="serviceReasonIds" multiple="multiple">
			<option value="">${not_applicable}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Service Reason:</span>
		</div>
		<div class="ea-clear"></div>
		
		<!-- Date Range Description -->
		<div class="ea-input">
		</div>
		<div class="ea-label">
			<span>${date_range_activity}</span>
		</div>
		<div class="ea-clear"></div>
		
		<!-- Date Range Selection -->
		<div class="ea-time-span">
			<div class="ea-term">
				<div class="ea-input">
					<select id="caseloadActivityTermCode" name="termCode" class="input-term-group">
						<option value="">${not_applicable}</option>
						</select>
				</div>
				<div class="ea-label">
					<span>Term:</span>
				</div>
				<div class="ea-clear"></div>
			</div>
			<div class="ea-date-range">
				<!-- Date From -->
				<div class="ea-input">
					<input class="input-calendar-type" type="textbox" name="createDateFrom" id="caseloadActivityCreateDateFrom">
				</div>
				<div class="ea-label">
					<span>Start Date (inclusive)::</span>
				</div>
				<div class="ea-clear"></div><!-- Date To -->
				<div class="ea-input">
					<input class="input-calendar-type" type="textbox" name="createDateTo" id="caseloadActivityCreateDateTo">
				</div>
				<div class="ea-label">
					<span>End Date (exclusive)::</span>
				</div>
				<div class="ea-clear"></div>
			</div>
			<div class="ea-buttons">
				<div>
									<button class="switch-date-range-type button" value="setbyrange">
									</button>
					</div>
			</div>
		</div>

		
		
		<!-- output type -->
		<div class="ea-input">
			<label><span>pdf</span></label><input type="radio" name="reportType"
				value="pdf" checked /><br /> <label><span>csv</span></label><input
				type="radio" name="reportType" value="csv" />
		</div> 

		<div class="ea-label">
			<span>Output Type:</span>
		</div>
		<div class="ea-clear"></div>

		<div class="ea-buttons">
			<div class="buttons">
				<input class="button primary button-send" type="submit"
					value="submit" />
			</div>
		</div>
	</form>
</div>




<div class="hideable-form special-services-form" style="display:none">
	<h1>Special Services</h1>
	<form action="/ssp/api/1/report/SpecialServices/" method="get" class="alert-form">

		<!-- Student Type -->
		<div class="ea-input">
			<select class="input-student-type-group" id="specialServicesStudentTypeIds" name="studentTypeIds" multiple="multiple">
			<option value="">${not_applicable}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Student Type:</span>
		</div>
		<div class="ea-clear"></div>
		
		<!-- Service Reason -->
		<div class="ea-input">
			<select class="input-service-reason-group" id="specialServicesServiceReasonIds" name="serviceReasonIds" multiple="multiple">
			<option value="">${not_applicable}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Service Reason:</span>
		</div>
		<div class="ea-clear"></div>

		<!-- Special Service Groups -->
		<div class="ea-input">
			<select class="input-special-service-group" id="SpecialServiceGroupIds" name="specialServiceGroupIds"
				multiple="multiple">
				<option value="">${not_applicable}</option>
				</select>
		</div>
		<div class="ea-label">
			<span>Special Service Groups:</span>
		</div>
		<div class="ea-clear"></div>

		<!-- output type -->
		<div class="ea-input">
			<label><span>pdf</span></label><input type="radio" name="reportType"
				value="pdf" checked /><br /> <label><span>csv</span></label><input
				type="radio" name="reportType" value="csv" />
		</div>
		<div class="ea-label">
			<span>Output Type:</span>
		</div>
		<div class="ea-clear"></div>

		<div class="ea-buttons">
			<div class="buttons">
				<input class="button primary button-send" type="submit"
					value="submit" />
			</div>
		</div>
	</form>
</div>


<div class="hideable-form caseLoad-form" style="display:none">
	<h1>Current Caseload Statuses Report</h1>
	<form action="/ssp/api/1/report/Caseload/" method="get"
		target="_top" onSubmit="return validateForm(this);" class="alert-form">
		<!-- Home Department -->
		<div class="ea-input">
			<select id="currentCaseloadStatusHomeDepartment" name="homeDepartment" class="input-home-department-group">
				<option value="">${not_applicable}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Home Department:</span>
		</div>
		<div class="ea-clear"></div>
		

		<!-- Student Type -->
		<div class="ea-input">
			<select class="input-student-type-group" id="StudentTypeIds" name="studentTypeIds" multiple="multiple">
			<option value="">${not_applicable}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Student Type:</span>
		</div>
		<div class="ea-clear"></div>
		
		<!-- Service Reason -->
		<div class="ea-input">
			<select class="input-service-reason-group" id="currentCaseloadStatusServiceReasonIds" name="serviceReasonIds" multiple="multiple">
			<option value="">${not_applicable}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Service Reason:</span>
		</div>
		<div class="ea-clear"></div>
		
		<!-- Special Service Groups -->
		<div class="ea-input">
			<select class="input-special-service-group" id="currentCaseloadStatusSpecialServiceGroupIds" name="specialServiceGroupIds"
				multiple="multiple">
				<option value="">${not_applicable}</option>
				</select>
		</div>
		<div class="ea-label">
			<span>Special Service Groups:</span>
		</div>
		<div class="ea-clear"></div>

		

		<!-- output type -->
		<div class="ea-input">
			<label><span>pdf</span></label><input type="radio" name="reportType"
				value="pdf" checked /><br /> <label><span>csv</span></label><input
				type="radio" name="reportType" value="csv" />
		</div> 

		<div class="ea-label">
			<span>Output Type:</span>
		</div>
		<div class="ea-clear"></div>

		<div class="ea-buttons">
			<div class="buttons">
				<input class="button primary button-send" type="submit"
					value="submit" />
			</div>
		</div>
	</form>
</div>


<div class="hideable-form counseling-reference-guide" style="display:none">
	<h1>Counseling Reference Guide</h1>
	<form action="/ssp/api/1/report/counselingreference" method="get" class="alert-form">
		<!-- output type -->
		<div class="ea-input">
			<label><span>pdf</span></label><input type="radio" name="reportType"
				value="pdf" checked /><br /> <label><span>csv</span></label><input
				type="radio" name="reportType" value="csv" />
		</div>
		<div class="ea-label">
			<span>Output Type:</span>
		</div>
		<div class="ea-clear"></div>

		<div class="ea-buttons">
			<div class="buttons">
				<input class="button primary button-send" type="submit"
					value="submit" />
			</div>
		</div>
	</form>
</div>

 
<div class="hideable-form confidentiality-agreement-form" style="width:800; display:none" >
<h1>Confidentiality Agreement Form</h1>
<p><a class='print-conf-form'>print</a></p>

<div class="confidentiality-agreement-form-content" style="width:800; padding: 5px; margin:25px; border-width:1px; border-style:solid;" ></div>
</div>

</div>

<div class="hideable-form early-alert-case-counts-report" style="display:none">
	<h1>Early Alert Case Counts</h1>
	<form action="/ssp/api/1/report/earlyalertcasecounts" method="get"
		target="_top" onSubmit="return validateForm(this);" class="alert-form">

		<!-- Campus -->
		<div class="ea-input">
			<select id="earlyAlertCaseCountsCampusName" name="campusId" class="input-campus-group">
				<option value="">${not_applicable}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Campus:</span>
		</div>
		<div class="ea-clear"></div>
		
		<!-- Roster Status 
		<div class="ea-input">
			<select id="earlyAlertCaseCountRosterStatus" name="rosterStatus" class="input-roster-status-code-group">
				<option value="">${not_applicable}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Roster Status:</span>
		</div>
		<div class="ea-clear"></div>	
		-->
		<!-- Term -->
		<div class="ea-input">
			<select id="termCodes" multiple="multiple" name="termCodes" class="input-term-group">
			<option value="">${not_applicable}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Term:</span>
		</div>
		<div class="ea-clear"></div>	


		
		<!-- output type -->
		<div class="ea-input">
			<label><span>pdf</span></label><input type="radio" name="reportType"
				value="pdf" checked /><br /> <label><span>csv</span></label><input
				type="radio" name="reportType" value="csv" />
		</div> 

		<div class="ea-label">
			<span>Output Type:</span>
		</div>
		<div class="ea-clear"></div>

		<div class="ea-buttons">
			<div class="buttons">
				<input class="button primary button-send" type="submit"
					value="submit" />
			</div>
		</div>
	</form>
</div>


<div class="hideable-form early-alert-student-report" style="display:none">
	<h1>Early Alert Student Report</h1>
	<form action="/ssp/api/1/report/earlyalertstudent" method="get"
		target="_top" onSubmit="return validateForm(this);" class="alert-form">	
		
		<!-- Home Department -->
		<div class="ea-input">
			<select id="earlyAlertStudentHomeDepartment" name="homeDepartment" class="input-home-department-group">
				<option value="">${not_applicable}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Home Department:</span>
		</div>
		<div class="ea-clear"></div>

		
		<!-- Roster Status 
		<div class="ea-input">
			<select id="earlyAlertStudentRosterStatus" name="rosterStatus" class="input-roster-status-code-group">
				<option value="">${not_applicable}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Roster Status:</span>
		</div>
		<div class="ea-clear"></div>
		-->
		
		<!-- Assigned Coach -->
		<div class="ea-input">
			<select id="earlyAlertStudentCounselorGroup" name="coachId" class="input-assigned-counselor-group">
				<option value="">${all_found}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Assigned Coach:</span>
		</div>
		<div class="ea-clear"></div>		
		
		
		<!-- program Status -->
		<div class="ea-input">
			<select id="earlyAlertStudentProgramStatusGroup" name="programStatus" class="input-program-status-group">
				<option value="">${not_applicable}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Program Status:</span>
		</div>
		<div class="ea-clear"></div>

		<!-- Student Type -->
		<div class="ea-input">
			<select class="input-student-type-group" id="earlyAlertStudentStudentTypeIds" name="studentTypeIds" multiple="multiple">
			<option value="">${not_applicable}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Student Type:</span>
		</div>
		<div class="ea-clear"></div>
		
		<!-- Service Reason -->
		<div class="ea-input">
			<select class="input-service-reason-group" id="earlyAlertStudentServiceReasonIds" name="serviceReasonIds" multiple="multiple">
			<option value="">${not_applicable}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Service Reason:</span>
		</div>
		<div class="ea-clear"></div>

		<!-- Special Service Groups -->
		<div class="ea-input">
			<select  class="input-special-service-group" id="earlyAlertStudentSpecialServiceGroupIds" name="specialServiceGroupIds"
				multiple="multiple">
				<option value="">${not_applicable}</option>
				</select>
		</div>
		<div class="ea-label">
			<span>Special Service Groups:</span>
		</div>
		<div class="ea-clear"></div>
		
		<!-- Date Range Description -->
		<div class="ea-input">
		</div>
		<div class="ea-label">
			<span>${date_range_early_alert}</span>
		</div>
		<div class="ea-clear"></div>
		
		<!-- Semester Date Range Selection -->
		<div class="ea-time-span">
			<div class="ea-term">
				<div class="ea-input">
					<select id="earlyAlertStudentTermCode" name="termCode" class="input-term-group">
						<option value="">${not_applicable}</option>
						</select>
				</div>
				<div class="ea-label">
					<span>Term:</span>
				</div>
				<div class="ea-clear"></div>
			</div>
			<div class="ea-date-range">
				<!-- Date From -->
				<div class="ea-input">
					<input class="input-calendar-type" type="textbox" name="createDateFrom" id="earlyAlertStudentCreateDateFrom">
				</div>
				<div class="ea-label">
					<span>Start Date (inclusive)::</span>
				</div>
				<div class="ea-clear"></div><!-- Date To -->
				<div class="ea-input">
					<input class="input-calendar-type" type="textbox" name="createDateTo" id="earlyAlertStudentCreateDateTo">
				</div>
				<div class="ea-label">
					<span>End Date (exclusive)::</span>
				</div>
				<div class="ea-clear"></div>
			</div>
			<div class="ea-buttons">
				<div>

									<button class="switch-date-range-type button" value="setbyrange">
									</button>

				</div>
			</div>
		</div>

	

		<!-- output type -->
		<div class="ea-input">
			<label><span>pdf</span></label><input type="radio" name="reportType"
				value="pdf" checked /><br /> <label><span>csv</span></label><input
				type="radio" name="reportType" value="csv" />
		</div> 

		<div class="ea-label">
			<span>Output Type:</span>
		</div>
		<div class="ea-clear"></div>

		<div class="ea-buttons">
			<div class="buttons">
				<input class="button primary button-send" type="submit"
					value="submit" />
			</div>
		</div>
	</form>
</div>

<div class="hideable-form early-alert-student-referral-report" style="display:none">
	<h1>Early Alert Student Referral Report</h1>
	<form action="/ssp/api/1/report/earlyalertstudentreferral" method="get"
		target="_top" onSubmit="return validateForm(this);" class="alert-form">	
		
		<!-- Home Department -->
		<div class="ea-input">
			<select id="earlyAlertStudentReferralHomeDepartment" name="homeDepartment" class="input-home-department-group">
				<option value="">${not_applicable}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Home Department:</span>
		</div>
		<div class="ea-clear"></div>

		
				<!-- Roster Status 
		<div class="ea-input">
			<select id="EarlyAlerStudentReferralRosterStatus" name="rosterStatus" class="input-roster-status-code-group">
				<option value="">${not_applicable}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Roster Status:</span>
		</div>
		<div class="ea-clear"></div>
-->
		<!-- Assigned Coach -->
		<div class="ea-input">
			<select id="earlyAlertStudentReferralCounselorGroup" name="coachId" class="input-assigned-counselor-group">
				<option value="">${all_found}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Assigned Coach:</span>
		</div>
		<div class="ea-clear"></div>		
		
		
		<!-- program Status -->
		<div class="ea-input">
			<select id="earlyAlertStudentReferralProgramStatusGroup" name="programStatus" class="input-program-status-group">
				<option value="">${not_applicable}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Program Status:</span>
		</div>
		<div class="ea-clear"></div>

		<!-- Referral Source -->
		<div class="ea-input">
			<select class='input-early-alert-referral-group' id="earlyAlertStudentReferralEarlyAlertReferralId" name="earlyAlertReferralId">
			</select>
		</div>
		<div class="ea-label">
			<span>Referred To:</span>
		</div>
		<div class="ea-clear"></div>
		
		<!-- Date Range Description -->
		<div class="ea-input">
		</div>
		<div class="ea-label">
			<span>${date_range_early_response}</span>
		</div>
		<div class="ea-clear"></div>
		
		<!-- Semester -->
		<div class="ea-time-span">
			<div class="ea-term">
				<div class="ea-input">
					<select id="earlyAlertStudentReferralTermCode" name="termCode" class="input-term-group">
						<option value="">${not_applicable}</option>
						</select>
				</div>
				<div class="ea-label">
					<span>Term:</span>
				</div>
				<div class="ea-clear"></div>
			</div>
			<div class="ea-date-range">
				<!-- Date From -->
				<div class="ea-input">
					<input class="input-calendar-type" type="textbox" name="createDateFrom" id="earlyAlertStudentReferralCreateDateFrom">
				</div>
				<div class="ea-label">
					<span>Start Date (inclusive)::</span>
				</div>
				<div class="ea-clear"></div><!-- Date To -->
				<div class="ea-input">
					<input class="input-calendar-type" type="textbox" name="createDateTo" id="earlyAlertStudentReferralCreateDateTo">
				</div>
				<div class="ea-label">
					<span>End Date (exclusive)::</span>
				</div>
				<div class="ea-clear"></div>
			</div>
			<div class="ea-buttons">
				<div>
									<button class="switch-date-range-type button" value="setbyrange">
									</button>
									
				</div>
			</div>
		</div>

		<!-- output type -->
		<div class="ea-input">
			<label><span>pdf</span></label><input type="radio" name="reportType"
				value="pdf" checked /><br /> <label><span>csv</span></label><input
				type="radio" name="reportType" value="csv" />
		</div> 

		<div class="ea-label">
			<span>Output Type:</span>
		</div>
		<div class="ea-clear"></div>

		<div class="ea-buttons">
			<div class="buttons">
				<input class="button primary button-send" type="submit"
					value="submit" />
			</div>
		</div>
	</form>
</div>

<div class="hideable-form early-alert-student-progress-report" style="display:none">
	<h1>Early Alert Student Progress Report</h1>
	<form action="/ssp/api/1/report/earlyalertstudentprogress" method="get"
		target="_top" onSubmit="return validateForm(this);" class="alert-form">	
		
		<!-- Home Department -->
		<div class="ea-input">
			<select id="earlyAlertStudentProgressHomeDepartment" name="homeDepartment" class="input-home-department-group">
				<option value="">${not_applicable}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Home Department:</span>
		</div>
		<div class="ea-clear"></div>


		<!-- Roster Status
		<div class="ea-input">
			<select id="EarlyAlertStudentProgressRosterStatus" name="rosterStatus" class="input-roster-status-code-group">
				<option value="">${not_applicable}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Roster Status:</span>
		</div>
		<div class="ea-clear"></div>
		 -->
		<!-- Assigned Coach -->
		<div class="ea-input">
			<select id="earlyAlertStudentProgressCounselorGroup" name="coachId" class="input-assigned-counselor-group">
				<option value="">${all_found}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Assigned Coach:</span>
		</div>
		<div class="ea-clear"></div>		
		
		
		<!-- program Status -->
		<div class="ea-input">
			<select id="earlyAlertStudentProgressProgramStatusGroup" name="programStatus" class="input-program-status-group">
				<option value="">${not_applicable}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Program Status:</span>
		</div>
		<div class="ea-clear"></div>

		<!-- Student Type -->
		<div class="ea-input">
			<select class="input-student-type-group" id="earlyAlertStudentProgressStudentTypeIds" name="studentTypeIds" multiple="multiple">
			<option value="">${not_applicable}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Student Type:</span>
		</div>
		<div class="ea-clear"></div>
		
		<!-- Service Reason -->
		<div class="ea-input">
			<select class="input-service-reason-group" id="earlyAlertStudentProgressServiceReasonIds" name="serviceReasonIds" multiple="multiple">
			<option value="">${not_applicable}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Service Reason:</span>
		</div>
		<div class="ea-clear"></div>

		<!-- Special Service Groups -->
		<div class="ea-input">
			<select  class="input-special-service-group" id="earlyAlertStudentProgressSpecialServiceGroupIds" name="specialServiceGroupIds"
				multiple="multiple">
				<option value="">${not_applicable}</option>
				</select>
		</div>
		<div class="ea-label">
			<span>Special Service Groups:</span>
		</div>
		<div class="ea-clear"></div>

		<!-- Initial Semester -->
		<div class="ea-input">
			<select id="earlyAlertStudentProgressTermCodeInitial" name="termCodeInitial" class="input-term-group">
			</select>
		</div>
		<div class="ea-label">
			<span>Initial Term:</span>
		</div>
		<div class="ea-clear"></div>
		
		<!-- Initial Semester -->
		<div class="ea-input">
			<select id="earlyAlertStudentProgressTermCodeComparitor" name="termCodeComparitor" class="input-term-group">
			</select>
		</div>
		<div class="ea-label">
			<span>Comparative Term:</span>
		</div>
		<div class="ea-clear"></div>	

		<!-- output type -->
		<div class="ea-input">
			<label><span>pdf</span></label><input type="radio" name="reportType"
				value="pdf" checked /><br /> <label><span>csv</span></label><input
				type="radio" name="reportType" value="csv" />
		</div> 

		<div class="ea-label">
			<span>Output Type:</span>
		</div>
		<div class="ea-clear"></div>

		<div class="ea-buttons">
			<div class="buttons">
				<input class="button primary button-send" type="submit"
					value="submit" />
			</div>
		</div>
	</form>
</div>

<div class="hideable-form early-alert-student-outreach-report" style="display:none">
	<h1>Early Alert Student Outreach Report</h1>
	<form action="/ssp/api/1/report/earlyalertstudentoutcome" method="get"
		target="_top" onSubmit="return validateForm(this);" class="alert-form">	
		<input type="hidden" id="earlyAlertStudentOutreachOutcomeType" name="outcomeType" value="earlyAlertOutreachIds"/>
		<!-- Home Department -->
		<div class="ea-input">
			<select id="earlyAlertStudentOutreachHomeDepartment" name="homeDepartment" class="input-home-department-group">
				<option value="">${not_applicable}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Home Department:</span>
		</div>
		<div class="ea-clear"></div>

		
				<!-- Roster Status 
		<div class="ea-input">
			<select id="earlyAlertStudentOutreachRosterStatus" name="rosterStatus" class="input-roster-status-code-group">
				<option value="">${not_applicable}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Roster Status:</span>
		</div>
		<div class="ea-clear"></div>
		-->
		<!-- Assigned Coach -->
		<div class="ea-input">
			<select id="earlyAlertStudentOutreachCounselorGroup" name="coachId" class="input-assigned-counselor-group">
				<option value="">${all_found}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Assigned Coach:</span>
		</div>
		<div class="ea-clear"></div>		
		
		<!-- program Status -->
		<div class="ea-input">
			<select id="earlyAlertStudentOutreachProgramStatusGroup" name="programStatus" class="input-program-status-group">
				<option value="">${not_applicable}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Program Status:</span>
		</div>
		<div class="ea-clear"></div>

		<div class="ea-input">
			<select class="input-student-type-group" id="earlyAlertStudentOutcomeStudentTypeIds" name="studentTypeIds" multiple="multiple">
			<option value="">${not_applicable}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Student Type:</span>
		</div>
		<div class="ea-clear"></div>
		
		<!-- Service Reason -->
		<div class="ea-input">
			<select class="input-service-reason-group" id="earlyAlertStudentOutcomesServiceReasonIds" name="serviceReasonIds" multiple="multiple">
			<option value="">${not_applicable}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Service Reason:</span>
		</div>
		<div class="ea-clear"></div>

		<!-- Special Service Groups -->
		<div class="ea-input">
			<select  class="input-special-service-group" id="earlyAlertStudentOutcomeSpecialServiceGroupIds" name="specialServiceGroupIds"
				multiple="multiple">
				<option value="">${not_applicable}</option>
				</select>
		</div>
		<div class="ea-label">
			<span>Special Service Groups:</span>
		</div>
		<div class="ea-clear"></div>
		
		<!-- Outcome -->
		<div class="ea-input">
			<select id="earlyAlertStudentOutreachOutcomeGroup" name="outcomeIds" class="input-early-alert-outcome-group" multiple="multiple" >
				<option value="">${all_found}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Outcome:</span>
		</div>
		<div class="ea-clear"></div>

		<!-- Date Range Description -->
		<div class="ea-input">
		</div>
		<div class="ea-label">
			<span>${date_range_early_response}</span>
		</div>
		<div class="ea-clear"></div>
		
		<!-- Semester -->
		<div class="ea-time-span">
			<div class="ea-term">
				<div class="ea-input">
					<select id="earlyAlertStudentOutreachTermCode" name="termCode" class="input-term-group">
						<option value="">${not_applicable}</option>
						</select>
				</div>
				<div class="ea-label">
					<span>Term:</span>
				</div>
				<div class="ea-clear"></div>
			</div>
			<div class="ea-date-range">
				<!-- Date From -->
				<div class="ea-input">
					<input class="input-calendar-type" type="textbox" name="createDateFrom" id="earlyAlertStudentOutreachCreateDateFrom">
				</div>
				<div class="ea-label">
					<span>Start Date (inclusive)::</span>
				</div>
				<div class="ea-clear"></div><!-- Date To -->
				<div class="ea-input">
					<input class="input-calendar-type" type="textbox" name="createDateTo" id="earlyAlertStudentOutreachCreateDateTo">
				</div>
				<div class="ea-label">
					<span>End Date (exclusive)::</span>
				</div>
				<div class="ea-clear"></div>
			</div>
			<div class="ea-buttons">
				<div>
					
									<button class="switch-date-range-type button" value="setbyrange">
									</button>
									
				</div>
			</div>
		</div>
		<!-- output type -->
		<div class="ea-input">
			<label><span>pdf</span></label><input type="radio" name="reportType"
				value="pdf" checked /><br /> <label><span>csv</span></label><input
				type="radio" name="reportType" value="csv" />
		</div> 

		<div class="ea-label">
			<span>Output Type:</span>
		</div>
		<div class="ea-clear"></div>

		<div class="ea-buttons">
			<div class="buttons">
				<input class="button primary button-send" type="submit"
					value="submit" />
			</div>
		</div>
	</form>
</div>

<div class="hideable-form early-alert-student-outcome-report" style="display:none">
	<h1>Early Alert Student Outcome Report</h1>
	<form action="/ssp/api/1/report/earlyalertstudentoutcome" method="get"
		target="_top" onSubmit="return validateForm(this);" class="alert-form">	
		<input type="hidden" id="earlyAlertStudentOutcomeOutcomeType"  name="outcomeType" value="earlyAlertOutcome"/>
		<!-- Home Department -->
		<div class="ea-input">
			<select id="earlyAlertStudentOutcomeHomeDepartment" name="homeDepartment" class="input-home-department-group">
				<option value="">${not_applicable}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Home Department:</span>
		</div>
		<div class="ea-clear"></div>

		
				<!-- Roster Status 
		<div class="ea-input">
			<select id="earlyAlertStudentOutcomeRosterStatus" name="rosterStatus" class="input-roster-status-code-group">
				<option value="">${not_applicable}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Roster Status:</span>
		</div>
		<div class="ea-clear"></div>
		-->
		<!-- Assigned Coach -->
		<div class="ea-input">
			<select id="earlyAlertStudentOutcomeCounselorGroup" name="coachId" class="input-assigned-counselor-group">
				<option value="">${all_found}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Assigned Coach:</span>
		</div>
		<div class="ea-clear"></div>		
		
		<!-- program Status -->
		<div class="ea-input">
			<select id="earlyAlertStudentOutcomeProgramStatusGroup" name="programStatus" class="input-program-status-group">
				<option value="">${not_applicable}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Program Status:</span>
		</div>
		<div class="ea-clear"></div>
		
		<!-- Student Type -->
		<div class="ea-input">
			<select class="input-student-type-group" id="earlyAlertStudentOutcomeStudentTypeIds" name="studentTypeIds" multiple="multiple">
			<option value="">${not_applicable}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Student Type:</span>
		</div>
		<div class="ea-clear"></div>
		
		<!-- Service Reason -->
		<div class="ea-input">
			<select class="input-service-reason-group" id="earlyAlertStudentOutcomeServiceReasonIds" name="serviceReasonIds" multiple="multiple">
			<option value="">${not_applicable}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Service Reason:</span>
		</div>
		<div class="ea-clear"></div>

		<!-- Special Service Groups -->
		<div class="ea-input">
			<select  class="input-special-service-group" id="earlyAlertStudentOutcomeSpecialServiceGroupIds" name="specialServiceGroupIds"
				multiple="multiple">
				<option value="">${not_applicable}</option>
				</select>
		</div>
		<div class="ea-label">
			<span>Special Service Groups:</span>
		</div>
		<div class="ea-clear"></div>
		
		<!-- Outcome -->
		<div class="ea-input">
			<select id="earlyAlertStudentOutcomeOutcomeGroup" name="outcomeIds" class="input-early-alert-outcome-group" multiple="multiple" >
				<option value="">${all_found}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Outcome:</span>
		</div>
		<div class="ea-clear"></div>

		<!-- Date Range Description -->
		<div class="ea-input">
		</div>
		<div class="ea-label">
			<span>${date_range_early_response}</span>
		</div>
		<div class="ea-clear"></div>
		<!-- Semester -->
		<div class="ea-time-span">
			<div class="ea-term">
				<div class="ea-input">
					<select id="earlyAlertStudentOutcomeTermCode" name="termCode" class="input-term-group">
						<option value="">${not_applicable}</option>
						</select>
				</div>
				<div class="ea-label">
					<span>Term:</span>
				</div>
				<div class="ea-clear"></div>
			</div>
			<div class="ea-date-range">
				<!-- Date From -->
				<div class="ea-input">
					<input class="input-calendar-type" type="textbox" name="createDateFrom" id="earlyAlertStudentOutcomeCreateDateFrom">
				</div>
				<div class="ea-label">
					<span>Start Date (inclusive)::</span>
				</div>
				<div class="ea-clear"></div><!-- Date To -->
				<div class="ea-input">
					<input class="input-calendar-type" type="textbox" name="createDateTo" id="earlyAlertStudentOutcomeCreateDateTo">
				</div>
				<div class="ea-label">
					<span>End Date (exclusive)::</span>
				</div>
				<div class="ea-clear"></div>
			</div>
			<div class="ea-buttons">
				<div>
									<button class="switch-date-range-type button" value="setbyrange">
									</button>
								
				</div>
			</div>
		</div>
		<!-- output type -->
		<div class="ea-input">
			<label><span>pdf</span></label><input type="radio" name="reportType"
				value="pdf" checked /><br /> <label><span>csv</span></label><input
				type="radio" name="reportType" value="csv" />
		</div> 

		<div class="ea-label">
			<span>Output Type:</span>
		</div>
		<div class="ea-clear"></div>

		<div class="ea-buttons">
			<div class="buttons">
				<input class="button primary button-send" type="submit"
					value="submit" />
			</div>
		</div>
	</form>
</div>


<div class="hideable-form early-alert-class-report" style="display:none">
	<h1>Early Alert Class Report</h1>
	<form action="/ssp/api/1/report/earlyalertclass" method="get"
		target="_top" onSubmit="return validateForm(this);" class="alert-form">

		<!-- Campus -->
		<div class="ea-input">
			<select id="earlyAlertClassCampusName" name="campusId" class="input-campus-group">
				<option value="">${not_applicable}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Campus:</span>
		</div>
		<div class="ea-clear"></div>	
		
				<!-- Roster Status 
		<div class="ea-input">
			<select id="earlyAlertClassRosterStatus" name="rosterStatus" class="input-roster-status-code-group">
				<option value="">${not_applicable}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Roster Status:</span>
		</div>
		<div class="ea-clear"></div>
		-->
		<!-- Term -->
		<div class="ea-input">
			<select id="earlyAlertClassTermCode" name="termCode" class="input-term-group">
			</select>
		</div>
		<div class="ea-label">
			<span>Term:</span>
		</div>
		<div class="ea-clear"></div>	


		
		<!-- output type -->
		<div class="ea-input">
			<label><span>pdf</span></label><input type="radio" name="reportType"
				value="pdf" checked /><br /> <label><span>csv</span></label><input
				type="radio" name="reportType" value="csv" />
		</div> 

		<div class="ea-label">
			<span>Output Type:</span>
		</div>
		<div class="ea-clear"></div>

		<div class="ea-buttons">
			<div class="buttons">
				<input class="button primary button-send" type="submit"
					value="submit" />
			</div>
		</div>
	</form>
</div>

<div class="hideable-form disability-services-report" style="display:none">
	<h1>Disability Services Report</h1>
	<form action="/ssp/api/1/report/disabilityservices/" method="get"
		target="_top" onSubmit="return validateForm(this);" class="alert-form">

		<!-- Home Department -->
		<div class="ea-input">
			<select id="disabilityServicesHomeDepartment" name="homeDepartment" class="input-home-department-group">
				<option value="">${not_applicable}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Home Department:</span>
		</div>
		<div class="ea-clear"></div>


		<!-- Assigned Coach -->
		<div class="ea-input">
			<select id="disabilityServicesAssignedCounselorGroup" name="coachId" class="input-assigned-counselor-group">
				<option value="">${all_found}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Assigned Coach:</span>
		</div>
		<div class="ea-clear"></div>	
		
		<!-- ODS Counselor -->
		<div class="ea-input">
			<select id="disabilityServicesODSCounselorGroup" name="odsCoachId" class="input-assigned-counselor-group">
				<option value="">${all_found}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>ODS Counselor:</span>
		</div>
		<div class="ea-clear"></div>	
		
		<!-- Disability Status -->
		<div class="ea-input">
			<select class='input-disability-status-group' id="disabilityServicesDisabilityStatusId" name="disabilityStatusId">
				<option value="">${not_applicable}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Disability Status:</span>
		</div>
		<div class="ea-clear"></div>	
		
		<!-- Disability Type -->
		<div class="ea-input">
			<select class='input-disability-type-group' id="disabilityServicesDisabilityTypeId" name="disabilityTypeId">
				<option value="">${not_applicable}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Disability Type:</span>
		</div>
		<div class="ea-clear"></div>	
			
		<!-- program Status -->
		<div class="ea-input">
			<select id="disabilityServicesProgramStatusGroup" name="programStatus" class="input-program-status-group">
				<option value="">${not_applicable}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Program Status:</span>
		</div>
		<div class="ea-clear"></div>

		<!-- Student Type -->
		<div class="ea-input">
			<select class="input-student-type-group" id="disabilityServicesStudentTypeIds" name="studentTypeIds" multiple="multiple">
			<option value="">${not_applicable}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Student Type:</span>
		</div>
		<div class="ea-clear"></div>
		
		<!-- Service Reason -->
		<div class="ea-input">
			<select class="input-service-reason-group" id="disabilityServicesServiceReasonIds" name="serviceReasonIds" multiple="multiple">
			<option value="">${not_applicable}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Service Reason:</span>
		</div>
		<div class="ea-clear"></div>

		<!-- Special Service Groups -->
		<div class="ea-input">
			<select  class="input-special-service-group" id="disabilityServicesSpecialServiceGroupIds" name="specialServiceGroupIds"
				multiple="multiple">
				<option value="">${not_applicable}</option>
				</select>
		</div>
		<div class="ea-label">
			<span>Special Service Groups:</span>
		</div>
		<div class="ea-clear"></div>

		<!-- Referral Source -->
		<div class="ea-input">
			<select class='input-referral-source-group' id="disabilityServicesReferralSourceGroup" name="referralSourcesIds"
				multiple="multiple">
				<option value="">${not_applicable}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Referral Source:</span>
		</div>
		<div class="ea-clear"></div>


		<!-- Date Range Description -->
		<div class="ea-input">
		</div>
		<div class="ea-label">
				<span>${date_range_student}</span>
		</div>
		<div class="ea-clear"></div>
		<!-- Date Range Selection -->
		<div class="ea-time-span">
			<div class="ea-term">
				<div class="ea-input">
					<select id="disabilityServicesTermCode" name="termCode" class="input-term-group">
						<option value="">${not_applicable}</option>
						</select>
				</div>
				<div class="ea-label">
					<span>Term:</span>
				</div>
				<div class="ea-clear"></div>
			</div>
			<div class="ea-date-range">
				<!-- Date From -->
				<div class="ea-input">
					<input class="input-calendar-type" type="textbox" name="createDateFrom" id="disabilityServicesCreateDateFrom">
				</div>
				<div class="ea-label">
					<span>Student Added From (inclusive)::</span>
				</div>
				<div class="ea-clear"></div><!-- Date To -->
				<div class="ea-input">
					<input class="input-calendar-type" type="textbox" name="createDateTo" id="disabilityServicesCreateDateTo">
				</div>
				<div class="ea-label">
					<span>Student Added To (exclusive)::</span>
				</div>
				<div class="ea-clear"></div>
			</div>
			<div class="ea-buttons">
				<div>
									<button class="switch-date-range-type button" value="setbyrange">
									</button>
								
				</div>
			</div>
			
		</div>

		<!-- Actual Start Term -->
		<div class="ea-input">
			<select id="disabilityServicesActualStartTerm" name="actualStartTerm" class="input-term-group">
			<option value="">${not_applicable}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Actual Start Term:</span>
		</div>
		<div class="ea-clear"></div>

		<!-- Actual Start Year -->
		<div class="ea-input">
			<select id="disabilityServicesActualStartYear" name="actualStartYear" class="input-report-year-group">
			<option value="">${not_applicable}</option>
			</select>
		</div>
		
		<div class="ea-label">
			<span>Actual Start Year:</span>
		</div>
		<div class="ea-clear"></div>
		
		<!-- Anticipated Start Term -->
		<div class="ea-input">
			<select id="disabilityServicesAnticipatedStartTerm" name="anticipatedStartTerm" class="input-term-group">
			<option value="">${not_applicable}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Anticipated Start Term:</span>
		</div>
		<div class="ea-clear"></div>

		<!-- Anticipated Start Year -->
		<div class="ea-input">
			<select id="disabilityServicesAnticipatedStartYear" name="anticipatedStartYear" class="input-report-year-group">
			<option value="">${not_applicable}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Anticipated Start Year:</span>
		</div>
		<div class="ea-clear"></div>

		<!-- output type -->
		<div class="ea-input">
			<label><span>pdf</span></label><input type="radio" name="reportType"
				value="pdf" checked /><br /> <label><span>csv</span></label><input
				type="radio" name="reportType" value="csv" />
		</div> 

		<div class="ea-label">
			<span>Output Type:</span>
		</div>
		<div class="ea-clear"></div>

		<div class="ea-buttons">
			<div class="buttons">
				<input class="button primary button-send" type="submit"
					value="submit" />
			</div>
		</div>
	</form>
</div>

<div class="hideable-form disability-accomodation-report" style="display:none">
	<h1>Disability Services Report</h1>
	<form action="/ssp/api/1/report/disabilityservices/" method="get"
		target="_top" onSubmit="return validateForm(this);" class="alert-form">

		<!-- Home Department -->
		<div class="ea-input">
			<select id="disabilityAccomodationHomeDepartment" name="homeDepartment" class="input-home-department-group">
				<option value="">${not_applicable}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Home Department:</span>
		</div>
		<div class="ea-clear"></div>


		<!-- Assigned Coach -->
		<div class="ea-input">
			<select id="disabilityAccomodationAssignedCounselorGroup" name="coachId" class="input-assigned-counselor-group">
				<option value="">${all_found}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Assigned Coach:</span>
		</div>
		<div class="ea-clear"></div>	
		
	
		<!-- Disability Status -->
		<div class="ea-input">
			<select class='input-disability-status-group' id="ddisabilityAccomodationDisabilityStatusId" name="disabilityStatusId">
				<option value="">${not_applicable}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Disability Status:</span>
		</div>
		<div class="ea-clear"></div>	
		
		<!-- Disability Type -->
		<div class="ea-input">
			<select class='input-disability-type-group' id="disabilityAccomodationDisabilityTypeId" name="disabilityTypeId">
				<option value="">${not_applicable}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Disability Type:</span>
		</div>
		<div class="ea-clear"></div>	
		
		
		<!-- program Status -->
		<div class="ea-input">
			<select id="disabilityAccomodationProgramStatusGroup" name="programStatus" class="input-program-status-group">
				<option value="">${not_applicable}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Program Status:</span>
		</div>
		<div class="ea-clear"></div>

		<!-- Student Type -->
		<div class="ea-input">
			<select class="input-student-type-group" id="disabilityAccomodationStudentTypeIds" name="studentTypeIds" multiple="multiple">
			<option value="">${not_applicable}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Student Type:</span>
		</div>
		<div class="ea-clear"></div>
		
		<!-- Service Reason -->
		<div class="ea-input">
			<select class="input-service-reason-group" id="disabilityAccomodationServiceReasonIds" name="serviceReasonIds" multiple="multiple">
			<option value="">${not_applicable}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Service Reason:</span>
		</div>
		<div class="ea-clear"></div>

		<!-- Special Service Groups -->
		<div class="ea-input">
			<select  class="input-special-service-group" id="disabilityAccomodationSpecialServiceGroupIds" name="specialServiceGroupIds"
				multiple="multiple">
				<option value="">${not_applicable}</option>
				</select>
		</div>
		<div class="ea-label">
			<span>Special Service Groups:</span>
		</div>
		<div class="ea-clear"></div>

		<!-- Referral Source -->
		<div class="ea-input">
			<select class='input-referral-source-group' id="disabilityAccomodationReferralSourceGroup" name="referralSourcesIds"
				multiple="multiple">
				<option value="">${not_applicable}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Referral Source:</span>
		</div>
		<div class="ea-clear"></div>

		<!-- Date Range Description -->
		<div class="ea-input">
		</div>
		<div class="ea-label">
				<span>${date_range_student}</span>
		</div>
		<div class="ea-clear"></div>

		<!-- Date Range Description -->
		<div class="ea-input">
		</div>
		<div class="ea-label">
				<span>${date_range_student}</span>
		</div>
		<div class="ea-clear"></div>
		<!-- Date Range Selection -->
		<div class="ea-time-span">
			<div class="ea-term">
				<div class="ea-input">
					<select id="disabilityAccomodationTermCode" name="termCode" class="input-term-group">
						<option value="">${not_applicable}</option>
						</select>
				</div>
				<div class="ea-label">
					<span>Term:</span>
				</div>
				<div class="ea-clear"></div>
			</div>
			<div class="ea-date-range">
				<!-- Date From -->
				<div class="ea-input">
					<input class="input-calendar-type" type="textbox" name="createDateFrom" id="disabilityAccomodationCreateDateFrom">
				</div>
				<div class="ea-label">
					<span>Student Added From (inclusive)::</span>
				</div>
				<div class="ea-clear"></div><!-- Date To -->
				<div class="ea-input">
					<input class="input-calendar-type" type="textbox" name="createDateTo" id="disabilityAccomodationCreateDateTo">
				</div>
				<div class="ea-label">
					<span>Student Added To (exclusive)::</span>
				</div>
				<div class="ea-clear"></div>
			</div>
			<div>
								<button class="switch-date-range-type button" value="setbyrange">
								</button>
			</div>
		</div>

		<!-- Actual Start Term -->
		<div class="ea-input">
			<select id="disabilityAccomodationActualStartTerm" name="actualStartTerm" class="input-term-group">
			<option value="">${not_applicable}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Actual Start Term:</span>
		</div>
		<div class="ea-clear"></div>

		<!-- Actual Start Year -->
		<div class="ea-input">
			<select id="disabilityAccomodationActualStartYear" name="actualStartYear" class="input-report-year-group">
			<option value="">${not_applicable}</option>
			</select>
		</div>
		
		<div class="ea-label">
			<span>Actual Start Year:</span>
		</div>
		<div class="ea-clear"></div>
		
		<!-- Anticipated Start Term -->
		<div class="ea-input">
			<select id="disabilityAccomodationAnticipatedStartTerm" name="anticipatedStartTerm" class="input-term-group">
			<option value="">${not_applicable}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Anticipated Start Term:</span>
		</div>
		<div class="ea-clear"></div>

		<!-- Anticipated Start Year -->
		<div class="ea-input">
			<select id="disabilityAccomodationAnticipatedStartYear" name="anticipatedStartYear" class="input-report-year-group">
			<option value="">${not_applicable}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Anticipated Start Year:</span>
		</div>
		<div class="ea-clear"></div>

		<!-- output type -->
		<div class="ea-input">
			<label><span>pdf</span></label><input type="radio" name="reportType"
				value="pdf" checked /><br /> <label><span>csv</span></label><input
				type="radio" name="reportType" value="csv" />
		</div> 

		<div class="ea-label">
			<span>Output Type:</span>
		</div>
		<div class="ea-clear"></div>

		<div class="ea-buttons">
			<div class="buttons">
				<input class="button primary button-send" type="submit"
					value="submit" />
			</div>
		</div>
	</form>
</div> <!-- end disability services report -->

<div class="hideable-form journal-session-detail-report" style="display:none">
	<h1>Journal Session Details Report</h1>
	<form action="/ssp/api/1/report/journalsessiondetail" method="get"
		target="_top" onSubmit="return validateForm(this);" class="alert-form">	
		<!-- Home Department -->
		<div class="ea-input">
			<select id="journalSessionDetailHomeDepartment" name="homeDepartment" class="input-home-department-group">
				<option value="">${not_applicable}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Home Department:</span>
		</div>
		<div class="ea-clear"></div>

		
		<!-- Assigned Coach -->
		<div class="ea-input">
			<select id="journalSessionDetailCounselorGroup" name="coachId" class="input-assigned-counselor-group">
				<option value="">${all_found}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Assigned Coach:</span>
		</div>
		<div class="ea-clear"></div>		
		
		<!-- program Status -->
		<div class="ea-input">
			<select id="journalSessionDetailProgramStatusGroup" name="programStatus" class="input-program-status-group">
				<option value="">${not_applicable}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Program Status:</span>
		</div>
		<div class="ea-clear"></div>
		
		<!-- Student Type -->
		<div class="ea-input">
			<select class="input-student-type-group" id="journalSessionDetailStudentTypeIds" name="studentTypeIds" multiple="multiple">
			<option value="">${not_applicable}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Student Type:</span>
		</div>
		<div class="ea-clear"></div>
		
		<!-- Service Reason -->
		<div class="ea-input">
			<select class="input-service-reason-group" id="journalSessionDetailServiceReasonIds" name="serviceReasonIds" multiple="multiple">
			<option value="">${not_applicable}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Service Reason:</span>
		</div>
		<div class="ea-clear"></div>

		<!-- Special Service Groups -->
		<div class="ea-input">
			<select  class="input-special-service-group" id="journalSessionDetailSpecialServiceGroupIds" name="specialServiceGroupIds"
				multiple="multiple">
				<option value="">${not_applicable}</option>
				</select>
		</div>
		<div class="ea-label">
			<span>Special Service Groups:</span>
		</div>
		<div class="ea-clear"></div>
		
		<!-- One Or More Items Selected Required -->
		<div class="ea-input">
			<select id="journalSessionDetailHasStepDetails" name="hasStepDetails" >
				<option value="1" selected="selected">Student has one or more items selected below</option>
				<option value="0" >Student does not have one or more items selected below</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Journal Step Detail:</span>
		</div>
		<div class="ea-clear"></div>
		
		<!-- Journal Step Detail -->
		<div class="ea-input">
			<select id="journalSessionDetailJournalStepDetailGroup" name="journalStepDetailIds" class="input-journal-step-detail-group" multiple="multiple" >
				<option value="">${all_found}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Journal Step Detail:</span>
		</div>
		<div class="ea-clear"></div>

		<!-- Date Range Description -->
		<div class="ea-input">
		</div>
		<div class="ea-label">
			<span>${date_range_journal_entry}</span>
		</div>
		<div class="ea-clear"></div>
		<!-- Semester -->
		<div class="ea-time-span">
			<div class="ea-term">
				<div class="ea-input">
					<select id="journalSessionDetailTermCode" name="termCode" class="input-term-group">
						<option value="">${not_applicable}</option>
						</select>
				</div>
				<div class="ea-label">
					<span>Term:</span>
				</div>
				<div class="ea-clear"></div>
			</div>
			<div class="ea-date-range">
				<!-- Date From -->
				<div class="ea-input">
					<input class="input-calendar-type" type="textbox" name="createDateFrom" id="journalSessionDetailCreateDateFrom">
				</div>
				<div class="ea-label">
					<span>Start Date (inclusive)::</span>
				</div>
				<div class="ea-clear"></div><!-- Date To -->
				<div class="ea-input">
					<input class="input-calendar-type" type="textbox" name="createDateTo" id="journalSessionDetailCreateDateTo">
				</div>
				<div class="ea-label">
					<span>End Date (exclusive)::</span>
				</div>
				<div class="ea-clear"></div>
			</div>
			<div class="ea-buttons">

								<button class="switch-date-range-type button" value="setbyrange">
								</button>
							
			</div>
		</div>
		<!-- output type -->
		<div class="ea-input">
			<label><span>pdf</span></label><input type="radio" name="reportType"
				value="pdf" checked /><br /> <label><span>csv</span></label><input
				type="radio" name="reportType" value="csv" />
		</div> 

		<div class="ea-label">
			<span>Output Type:</span>
		</div>
		<div class="ea-clear"></div>

		<div class="ea-buttons">
			<div class="buttons">
				<input class="button primary button-send" type="submit"
					value="submit" />
			</div>
		</div>
	</form>
</div><!-- end journal session detail report -->


<div class="hideable-form number-courses-in-plan-report" style="display:none">
	<h1>Journal Session Details Report</h1>
	<form action="/ssp/api/1/report/map/numbercourses" method="get"
		target="_top" onSubmit="return validateForm(this);" class="alert-form">	
		
		<!-- Subject Abbreviations -->
		<div class="ea-input">
			<select  class="input-subject-abbreviation-group" id="numberCoursesPlanSubjectAbbreviation" name="subjectAbbreviation"
				>
				<option value="">${not_applicable}</option>
				</select>
		</div>
		<div class="ea-label">
			<span>Subject Abbreviations:</span>
		</div>
		<div class="ea-clear"></div>
		
		<!-- Course Numbers 
		<div class="ea-input">
			<select  class="input-course-number-group" id="numberCoursesPlanCourseNumber" name="courseNumber"
				>
				<option value="">${select_subject_abbreviation}</option>
				</select>
		</div>
		<div class="ea-label">
			<span>Course Numbers:</span>
		</div>
		<div class="ea-clear"></div>-->
		
		<!-- Formatted Course -->
		<div class="ea-input">
			<select  class="input-formatted-course-group" id="numberCoursesPlanFormattedCourse" name="formattedCourse"
				>
				<option value="">${select_subject_abbreviation}</option>
				</select>
		</div>
		<div class="ea-label">
			<span>Course:</span>
		</div>
		<div class="ea-clear"></div>
		
		<!-- Plan Status -->
		<div class="ea-input">
			<select  class="input-plan-status-group" id="numberCoursesPlanStatus" name="planStatus">
				<option value="">${not_applicable}</option>
				<option value="onPlan">On Plan</option>
				<option value="offPlan">Off Plan</option>
				</select>
		</div>
		<div class="ea-label">
			<span>Plan Status:</span>
		</div>
		<div class="ea-clear"></div>
		
		<!-- Term -->
		<div class="ea-input">
			<select id="numberCoursesTermCodes"  name="termCode" class="input-term-group">
			<option value="">${not_applicable}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Term:</span>
		</div>
		<div class="ea-clear"></div>	
		
		<!-- output type -->
		<div class="ea-input">
			<label><span>pdf</span></label><input type="radio" name="reportType"
				value="pdf" checked /><br /> <label><span>csv</span></label><input
				type="radio" name="reportType" value="csv" />
		</div> 

		<div class="ea-label">
			<span>Output Type:</span>
		</div>
		<div class="ea-clear"></div>

		<div class="ea-buttons">
			<div class="buttons">
				<input class="button primary button-send" type="submit"
					value="submit" />
			</div>
		</div>
	</form>
</div><!-- end number courses in plan report -->

<div class="hideable-form number-plans-by-advisor-report" style="display:none">
	<h1>Journal Session Details Report</h1>
	<form action="/ssp/api/1/report/map/numberplansbyadvisor" method="get"
		target="_top" onSubmit="return validateForm(this);" class="alert-form">	
		
		<!-- Date Range Description -->
		<div class="ea-input">
		</div>
		<div class="ea-label">
			<span>${date_range_map_entry}</span>
		</div>
		<div class="ea-clear"></div>
		<!-- Semester -->
		<div class="ea-time-span">
			<div class="ea-term">
				<div class="ea-input">
					<select id="numberPlansByAdvisorTermCode" name="termCode" class="input-term-group">
						<option value="">${not_applicable}</option>
						</select>
				</div>
				<div class="ea-label">
					<span>Term:</span>
				</div>
				<div class="ea-clear"></div>
			</div>
			<div class="ea-date-range">
				<!-- Date From -->
				<div class="ea-input">
					<input class="input-calendar-type" type="textbox" name="createDateFrom" id="numberPlansByAdvisorCreateDateFrom">
				</div>
				<div class="ea-label">
					<span>Start Date (inclusive)::</span>
				</div>
				<div class="ea-clear"></div><!-- Date To -->
				<div class="ea-input">
					<input class="input-calendar-type" type="textbox" name="createDateTo" id="numberPlansByAdvisorCreateDateTo">
				</div>
				<div class="ea-label">
					<span>End Date (exclusive)::</span>
				</div>
				<div class="ea-clear"></div>
			</div>
			<div class="ea-buttons">

					<button class="switch-date-range-type button" value="setbyrange">
					</button>
							
			</div>
		</div>
		<!-- output type -->
		<div class="ea-input">
			<label><span>pdf</span></label><input type="radio" name="reportType"
				value="pdf" checked /><br /> <label><span>csv</span></label><input
				type="radio" name="reportType" value="csv" />
		</div> 

		<div class="ea-label">
			<span>Output Type:</span>
		</div>
		<div class="ea-clear"></div>

		<div class="ea-buttons">
			<div class="buttons">
				<input class="button primary button-send" type="submit"
					value="submit" />
			</div>
		</div>
	</form>
</div><!-- number plans by advisor report -->

<div class="hideable-form number-students-by-status-report" style="display:none">
	<h1>Journal Session Details Report</h1>
	<form action="/ssp/api/1/report/map/numberstudentsbystatus" method="get"
		target="_top" onSubmit="return validateForm(this);" class="alert-form">	
		
		<!-- Subject Abbreviations -->
		<div class="ea-input">
			<select  class="input-subject-abbreviation-group" id="numberStudentsByStatusSubjectAbbreviations" name="subjectAbbreviation"
				>
				<option value="">${not_applicable}</option>
				</select>
		</div>
		<div class="ea-label">
			<span>Subject Abbreviations:</span>
		</div>
		<div class="ea-clear"></div>
		
		<!-- Course Numbers 
		<div class="ea-input">
			<select  class="input-course-number-group" id="numberStudentsByStatusCourseNumbers" name="courseNumber"
				>
				<option value="">${select_subject_abbreviation}</option>
				</select>
		</div>
		<div class="ea-label">
			<span>Course Numbers:</span>
		</div>
		<div class="ea-clear"></div>-->
		
		<!-- Formatted Course -->
		<div class="ea-input">
			<select  class="input-formatted-course-group" id="numberStudentsByStatusFormattedCourse" name="formattedCourse"
				>
				<option value="">${select_subject_abbreviation}</option>
				</select>
		</div>
		<div class="ea-label">
			<span>Course:</span>
		</div>
		<div class="ea-clear"></div>
		
		<!-- Term -->
		<div class="ea-input">
			<select id="numberStudentsByStatusTermCodes"  name="termCode" class="input-term-group">
			<option value="">${not_applicable}</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Term:</span>
		</div>
		<div class="ea-clear"></div>	
		<!-- output type -->
		<div class="ea-input">
			<label><span>pdf</span></label><input type="radio" name="reportType"
				value="pdf" checked /><br /> <label><span>csv</span></label><input
				type="radio" name="reportType" value="csv" />
		</div> 

		<div class="ea-label">
			<span>Output Type:</span>
		</div>
		<div class="ea-clear"></div>

		<div class="ea-buttons">
			<div class="buttons">
				<input class="button primary button-send" type="submit"
					value="submit" />
			</div>
		</div>
	</form>
</div><!-- number studens by status report -->

	</div>
    
  </div> <!-- end: portlet-body -->

</div> <!-- end: portlet -->
    	

<script type="text/javascript">
    var ${n} = {};
    function validateForm(form)
    {
    	return form;
    }
    ${n}.jQuery = jQuery.noConflict(true);

    ${n}.jQuery(function() {
        var $ = up.jQuery;
        var options = {};
	ssp.ReportSelector('#${n}reportSelector', options);
});
</script>