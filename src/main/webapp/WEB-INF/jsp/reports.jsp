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
    	<div class="titlebar">
        <h3 class="title"><spring:message code="loading"/> . . .</h3>
      </div>
      <div class="content">
    	  <p><spring:message code="please.wait.while.the.syst em.finishes.loading.roster"/></p>
      </div>
    </div>
    

	<div class="reports-form-body"> 
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
