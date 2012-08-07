<jsp:directive.include file="/WEB-INF/jsp/include.jsp"/>

<portlet:renderURL var="cancelUrl" />
<portlet:renderURL var="doneUrl" escapeXml="false"> 
    <portlet:param name="confirm" value="true"/>
    <portlet:param name="studentName" value="STUDENTNAME"/>
</portlet:renderURL>

<c:set var="n"><portlet:namespace/></c:set>

<script src="<rs:resourceURL value="/rs/jquery/1.6.1/jquery-1.6.1.min.js"/>" type="text/javascript"></script>
<script src="http://code.jquery.com/jquery-latest.js"></script>
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
	<form action="/ssp/api/1/report/AddressLabels/" method="post"
		target="_top" onSubmit="return validateForm(this);" class="alert-form">
		<!-- program Status -->
		<div class="ea-input">
			<select id="programStatusGroup" name="programStatus" class="input-program-status-group">
				<option value=""></option>
			</select>
		</div>
		<div class="ea-label">
			<span>Program Status:</span>
		</div>
		<div class="ea-clear"></div>

		<!-- Student Type -->

		<div class="ea-input">
			<select class="input-student-type-group" id="StudentTypeIds" name="studentTypeIds" multiple="multiple"></select>
		</div>
		<div class="ea-label">
			<span>Student Type:</span>
		</div>
		<div class="ea-clear"></div>

		<!-- Special Service Groups -->
		<div class="ea-input">
			<select  class="input-special-service-group" id="SpecialServiceGroupIds" name="specialServiceGroupIds"
				multiple="multiple"></select>
		</div>
		<div class="ea-label">
			<span>Special Service Groups:</span>
		</div>
		<div class="ea-clear"></div>

		<!-- Referral Source -->
		<div class="ea-input">
			<select class='input-referral-source-group' id="ReferralSourceGroup" name="referralSourcesIds"
				multiple="multiple">
			</select>
		</div>
		<div class="ea-label">
			<span>Referral Source:</span>
		</div>
		<div class="ea-clear"></div>


		<!-- Date From -->
		<div class="ea-input">
			<input class="input-calendar-type" type="textbox" name="createDateFrom" id="createDateFrom" />
		</div>
		<div class="ea-label">
			<span>Student Added From:</span>
		</div>
		<div class="ea-clear"></div>

		<!-- Date To -->
		<div class="ea-input">
			<input class="input-calendar-type" type="textbox" name="createDateTo" id="createDateTo" />
		</div>
		<div class="ea-label">
			<span>Student Added To:</span>
		</div>
		<div class="ea-clear"></div>

		<!-- Anticipated Start Term -->
		<div class="ea-input">
			<select id="anticipatedStartTerm" name="anticipatedStartTerm">
				<option value=""></option>
				<option value="Fall" class="test-class-1">Fall</option>
				<option value="Winter" class="test-class-1">Winter</option>
				<option value="Spring" class="test-class-1">Spring</option>
				<option value="Summer" class="test-class-1">Summer</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Anticipated Start Term:</span>
		</div>
		<div class="ea-clear"></div>

		<!-- Anticipated Start Year -->
		<div class="ea-input">
			<select id="anticipatedStartYear" name="anticipatedStartYear">
				<option value=""></option>
				<option value="2010" class="test-class-1">2010</option>
				<option value="2011" class="test-class-1">2011</option>
				<option value="2012" class="test-class-1">2012</option>
				<option value="2013" class="test-class-1">2013</option>
				<option value="2014" class="test-class-1">2014</option>
				<option value="2015" class="test-class-1">2015</option>
				<option value="2016" class="test-class-1">2016</option>
				<option value="2017" class="test-class-1">2017</option>
				<option value="2018" class="test-class-1">2018</option>
				<option value="2019" class="test-class-1">2019</option>
				<option value="2020" class="test-class-1">2020</option>
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



<div class="hideable-form special-services-form" style="display:none">
	<h1>Special Services</h1>
	<form action="/ssp/api/1/report/SpecialServices/" method="post" class="alert-form">


		<!-- Special Service Groups -->
		<div class="ea-input">
			<select class="input-special-service-group" id="SpecialServiceGroupIds" name="specialServiceGroupIds"
				multiple="multiple"></select>
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

 
<div class="hideable-form confidentiality-agreement-form" style="width:800; display:none" >
<h1>Confidentiality Agreement Form</h1>
<p><a class='print-conf-form'>print</a></p>

<div class="confidentiality-agreement-form-content" style="width:800; padding: 5px; margin:25px; border-width:1px; border-style:solid;" ></div>
</div>

</div>




	</div>
    
  </div> <!-- end: portlet-body -->

</div> <!-- end: portlet -->
    	

<script type="text/javascript">
    var ${n} = {};
    ${n}.jQuery = jQuery.noConflict(true);

    ${n}.jQuery(function() {
        var $ = up.jQuery;
        var options = {};
	ssp.ReportSelector('#${n}reportSelector', options);
});
</script>
