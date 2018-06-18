<%--

    Licensed to Apereo under one or more contributor license
    agreements. See the NOTICE file distributed with this work
    for additional information regarding copyright ownership.
    Apereo licenses this file to you under the Apache License,
    Version 2.0 (the "License"); you may not use this file
    except in compliance with the License.  You may obtain a
    copy of the License at the following location:

      http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing,
    software distributed under the License is distributed on an
    "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
    KIND, either express or implied.  See the License for the
    specific language governing permissions and limitations
    under the License.

--%>
<jsp:directive.include file="/WEB-INF/jsp/include.jsp"/>

<portlet:renderURL var="enterAlertUrl" escapeXml="false">
    <portlet:param name="action" value="enterAlert"/>
    <portlet:param name="schoolId" value="SCHOOLID"/>
    <portlet:param name="formattedCourse" value="FORMATTEDCOURSE"/>
    <portlet:param name="sectionCode" value="SECTIONCODE"/>
    <portlet:param name="termCode" value="TERMCODE"/>
    <portlet:param name="facultySchoolId" value="FACULTYID"/>
</portlet:renderURL>

<c:set var="n"><portlet:namespace/></c:set>

<script src="<rs:resourceURL value="/rs/jquery/1.11.0/jquery-1.11.0.min.js"/>" type="text/javascript"></script>
<script src="<rs:resourceURL value="/rs/jqueryui/1.8.13/jquery-ui-1.8.13.min.js"/>" type="text/javascript"></script>
<script src="<rs:resourceURL value="/rs/fluid/1.4.0/js/fluid-all-1.4.0.min.js"/>" type="text/javascript"></script>
<script src="<c:url value="/js/early-alert-shared.js" />" type="text/javascript"></script>
<script src="<c:url value="/js/early-alert-roster.js" />" type="text/javascript"></script>

<link href="<c:url value="/resources/css/early-alert.css" />" rel="stylesheet" type="text/css">

<!-- Portlet -->
<div id="${n}earlyAlert" class="fl-widget portlet early-alert" role="section">
  
  <!-- Errors -->
  <div class="errors">
  </div>

  <!-- Unrecognized user -->
  <div class="portlet-msg-error portlet-msg error error-message unregognized-user" role="status" style="display: none;">
    <div class="titlebar">
      <h3 class="title"><spring:message code="unregognized.person"/>: ${renderRequest.remoteUser}</h3>
    </div>
    <div class="content">
      <p><spring:message code="no.information.is.available.for.the.current.user.in.ssp"/></p>
    </div>
  </div>

  <!-- Portlet Message (success) -->
  <div class="portlet-msg-success portlet-msg success sent-message" role="status" style="display: none;">
    <div class="titlebar">
      <h3 class="title"><spring:message code="early.alert.sent"/></h3>
    </div>
    <div class="content">
      <p><spring:message code="alert.sent.prefix"/> <strong><c:out value="${studentName}"/></strong> <spring:message code="alert.sent.suffix"/></p>
    </div>
  </div>



  <!-- Portlet Titlebar -->
  <div class="fl-widget-titlebar titlebar portlet-titlebar" role="sectionhead">
  	<h2 class="title" role="heading"><spring:message code="course.roster"/></h2>
    <div class="fl-col-flex2 toolbar" role="toolbar">
      <div class="fl-col">
        <select class="course-select">
        </select>
      </div>
      <div class="fl-col fl-text-align-right">&nbsp;</div>
    </div>
    <div style="clear:both"></div>
  </div>

  <p><spring:message code="click.a.student.from.the.roster.to.send.an.early.alert"/></p>

  <!-- Portlet Content -->
  <div class="fl-widget-content content portlet-content" role="main">
  
  	<!-- Portlet Message (loading) -->
  	<div class="portlet-msg-info portlet-msg info loading-message" role="status" style="display: none;">
    	<div class="titlebar">
        <h3 class="title"><spring:message code="loading"/> . . .</h3>
      </div>
      <div class="content">
    	  <p><spring:message code="please.wait.while.the.system.finishes.loading.roster"/></p>
      </div>
    </div>

    <!-- Portlet Section -->
    <div class="fl-pager roster">   
        <div class="fl-col-flex2">
          <div class="fl-col view-filter">&nbsp;
            <!-- Search... perhaps later -->
            <!--form class="roster-search-form" style="display:inline">
              <input type="text" class="roster-search-input"/>
              <input type="submit" value="<spring:message code="search"/>"/>
            </form-->
          </div>
          <div class="fl-col flc-pager-top view-pager">
            <ul id="pager-top" class="fl-pager-ui">
              <li class="flc-pager-previous"><a href="javascript:;">&lt; <spring:message code="previous"/></a></li>
              <li>
                <ul class="fl-pager-links flc-pager-links" style="margin:0; display:inline">
                  <li class="flc-pager-pageLink"><a href="javascript:;">1</a></li>
                </ul>
              </li>
              <li class="flc-pager-next"><a href="javascript:;"><spring:message code="next"/> &gt;</a></li>
              <li>
                <span class="flc-pager-summary"><spring:message code="show"/></span>
                <span> <select class="pager-page-size flc-pager-page-size">
                <option value="10">10</option>
                <option value="20">20</option>
                <option value="50">50</option>
                <option value="100">100</option>
                <option value="150">150</option>
                </select></span> <spring:message code="per.page"/>
              </li>
            </ul>
          </div>
        </div>
        <div style="clear:both"></div>

        <table summary="<spring:message code="roster"/>" xmlns:rsf="http://ponder.org.uk" class="portlet-table roster-table" style="width:100%;">
          <thead>
            <tr rsf:id="header:">
              <th id="${n}schoolId" class="flc-pager-sort-header"><a rsf:id="schoolId" title="Click to sort" href="javascript:;"><spring:message code="student.school.id"/></a></th>
              <th id="${n}firstName" class="flc-pager-sort-header"><a rsf:id="firstName" title="Click to sort" href="javascript:;"><spring:message code="first.name"/></a></th>
              <th id="${n}middleName" class="flc-pager-sort-header"><a rsf:id="middleName" title="Click to sort" href="javascript:;"><spring:message code="middle.name"/></a></th>
              <th id="${n}lastName" class="flc-pager-sort-header"><a rsf:id="lastName" title="Click to sort" href="javascript:;"><spring:message code="last.name"/></a></th>
              <th id="${n}status" class="flc-pager-sort-header"><a rsf:id="status" title="Click to sort" href="javascript:;"><spring:message code="status"/></a></th>
	      <th id="${n}facultySchoolId" class="flc-pager-sort-header"><a rsf:id="facultySchoolId" title="Click to sort" href="javascript:;">Faculty Id</a></th>

            </tr>
          </thead>
          <tbody class="roster-body">
            <tr rsf:id="row:">
              <td headers="${n}schoolId" rsf:id="schoolId" class="schoolId"></td>
              <td headers="${n}firstName" rsf:id="firstName"></td>
              <td headers="${n}middleName" rsf:id="middleName"></td>
              <td headers="${n}lastName" rsf:id="lastName"></td>
              <td headers="${n}status" rsf:id="status"></td>
	      <td headers="${n}facultySchoolId" rsf:id="facultySchoolId" class="facultySchoolId"></td>

             
            </tr>
          </tbody>
        </table>

      </div>
    
  </div> <!-- end: portlet-body -->

  	<!-- Error Message Template -->
  	<div class="portlet-msg-error portlet-msg error error-message-template" role="status" style="display: none;">
    	<div class="titlebar">
        <h3 class="title"><span class="error-title"></span></h3>
      </div>
      <div class="content">
    	  <p><span class="error-body"></span></p>
      </div>
    </div>

</div> <!-- end: portlet -->
    	
<script type="text/javascript">
    var ${n} = {};
    ${n}.jQuery = jQuery.noConflict(true);
    ${n}.fluid = fluid;
    fluid = null;
    fluid_1_4 = null;

    ${n}.jQuery(function() {
        var $ = up.jQuery;
        var fluid = up.fluid;

  <c:choose>
    <c:when test="${user != null}">
        var options = {
        	urls: {
        		courseList: '<c:url value="/api/1/person/${user.schoolId}/instruction/course"/>',
        		enterAlert: '${enterAlertUrl}',
        		roster: '<c:url value="/api/1/person/${user.schoolId}/instruction/course/FORMATTEDCOURSE/roster?termCode=TERMCODE&sectionCode=SECTIONCODE"/>',
                configByName: '<c:url value="/api/1/reference/config?name=CONFIGNAME" />',
                enrollmentStatus: '<c:url value="/api/1/reference/enrollmentStatus?limit=-1"/>'
        	},
            course_id_delim: ':',
            statusMappingConfigName: 'status_code_mappings',
            initialSelectedCourse: '${initialSelectedCourse}'
        };
        ssp.EarlyAlertRoster('#${n}earlyAlert', options);
    </c:when>
    <c:otherwise>
        $('#${n}earlyAlert .unregognized-user').slideDown(1000);
    </c:otherwise>
  </c:choose>

        // Confirm submission
        if (${studentName != null ? 'true' : 'false'}) {
            $('#${n}earlyAlert .sent-message').slideDown(1000);
        }

    });
</script>
