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

<portlet:renderURL var="cancelUrl" >
    <portlet:param name="formattedCourse" value="${course.formattedCourse}"/>
    <portlet:param name="termCode" value="${course.termCode}"/>
    <portlet:param name="termName" value="${courseTermName}"/>
     <portlet:param name="sectionCode" value="${course.sectionCode}"/>
</portlet:renderURL>
<portlet:renderURL var="doneUrl" escapeXml="false">
    <portlet:param name="confirm" value="CONFIRMED_ID"/>
    <portlet:param name="studentName" value="STUDENTNAME"/>
    <portlet:param name="formattedCourse" value="${course.formattedCourse}"/>
    <portlet:param name="sectionCode" value="${course.sectionCode}"/>
    <portlet:param name="termCode" value="${course.termCode}"/>
    <portlet:param name="termName" value="${courseTermName}"/>
</portlet:renderURL>

<c:set var="n"><portlet:namespace/></c:set>

<script src="<c:url value="/js/libs/json2.js" />" type="text/javascript"></script>
<script src="<rs:resourceURL value="/rs/jquery/1.11.0/jquery-1.11.0.min.js"/>" type="text/javascript"></script>
<script src="<rs:resourceURL value="/rs/jqueryui/1.8.13/jquery-ui-1.8.13.min.js"/>" type="text/javascript"></script>
<script src="<c:url value="/js/early-alert-shared.js" />" type="text/javascript"></script>
<script src="<c:url value="/js/early-alert-form.js" />" type="text/javascript"></script>

<link href="<c:url value="/resources/css/early-alert.css" />" rel="stylesheet" type="text/css">

<!-- Portlet -->
<div id="${n}earlyAlert" class="fl-widget portlet early-alert" role="section">

  <!-- Errors -->
  <div class="errors">
  </div>
  
  <!-- Portlet Titlebar -->
  <div class="fl-widget-titlebar titlebar portlet-titlebar" role="sectionhead">
    <h2 class="title" role="heading"><spring:message code="early.alert.details"/></h2>
  </div>

  <!-- Portlet Content -->
  <div class="fl-widget-content content portlet-content" role="main">
  
    <!-- Portlet Message -->
    <div class="portlet-msg-info portlet-msg info loading-message" role="status">
        <div class="titlebar">
        <h3 class="title"><spring:message code="loading"/> . . .</h3>
      </div>
      <div class="content">
          <p><spring:message code="please.wait.while.the.system.finishes.loading.roster"/></p>
      </div>
    </div>
    
    <form method="POST" class="alert-form" style="display: none;">
    
      <!-- Course -->
      <div class="ea-input">
        <span class="field-course"></span>
      </div>
      <div class="ea-required">&nbsp;</div>
      <div class="ea-label">
        <span><spring:message code="course"/>:</span>
      </div>
      <div class="ea-clear"></div>

      <!-- Term -->
      <div class="ea-input">
        <span class="field-term"></span>
      </div>
      <div class="ea-required">&nbsp;</div>
      <div class="ea-label">
        <span><spring:message code="term"/>:</span>
      </div>
      <div class="ea-clear"></div>

      <!-- Faculty ID -->
      <div class="ea-input">
        <span class="field-facultyid"></span>
      </div>
      <div class="ea-required">&nbsp;</div>
      <div class="ea-label">
        <span><spring:message code="Faculty"/>:</span>
      </div>
      <div class="ea-clear"></div>


      <!-- Student -->
      <div class="ea-input">
        <span class="field-student"></span>
      </div>
      <div class="ea-required">&nbsp;</div>
      <div class="ea-label">
        <span><spring:message code="student"/>:</span>
      </div>
      <div class="ea-clear"></div>

      <!-- Enrollment Status -->
      <div class="ea-input">
        <span class="field-enrollment-status"></span>
      </div>
      <div class="ea-required">&nbsp;</div>
      <div class="ea-label">
        <span><spring:message code="enrollment.status"/>:</span>
      </div>
      <div class="ea-clear"></div>

      <!-- Net ID -->
      <div class="ea-input">
        <span class="field-net-id"></span>
      </div>
      <div class="ea-required">&nbsp;</div>
      <div class="ea-label">
        <span><spring:message code="net.id"/>:</span>
      </div>
      <div class="ea-clear"></div>
      
            <!-- Student ID -->
      <div class="ea-input">
        <span class="field-student-school-id"></span>
      </div>
      <div class="ea-required">&nbsp;</div>
      <div class="ea-label">
        <span><spring:message code="student.school.id"/>:</span>
      </div>
      <div class="ea-clear"></div>
      

      <!-- Student Email -->
      <div class="ea-input">
        <span class="field-student-email"></span>
      </div>
      <div class="ea-required">&nbsp;</div>
      <div class="ea-label">
        <span><spring:message code="student.email"/>:</span>
      </div>
      <div class="ea-clear"></div>

      <!-- Student Type -->
      <div class="ea-input">
        <span class="field-student-type"></span>
      </div>
      <div class="ea-required">&nbsp;</div>
      <div class="ea-label">
        <span><spring:message code="student.type"/>:</span>
      </div>
      <div class="ea-clear"></div>
      
      <!-- Early Alert Summary -->
      <c:choose>
        <c:when test="${fn:length(earlyAlerts)==0}">
            <div class="ea-input">
                <span ><spring:message code="early.alert.summary.no.alerts"/></span>
            </div>
      <div class="ea-label">
        <span><spring:message code="early.alert.summary"/>:</span>
      </div>
      <div class="ea-clear"></div>
      </c:when>
      <c:otherwise>
     
          <div class="ea-label early-alert-display-hide">
           <a> <span><spring:message code="early.alert.summary.hide"/></span></a>
         </div>
         <div class="ea-label early-alert-display-show">
            <a><span><spring:message code="early.alert.summary.show"/></span></a>
         </div>
         <div class="ea-clear"></div>
          <div class="ea-input">
            <div class="early-alert-display">
            <table summary="<spring:message code="roster"/>" xmlns:rsf="http://ponder.org.uk" class="portlet-table roster-table" style="width:100%;">
              <thead>
                <tr>
                  <th  class="flc-pager-sort-header"><spring:message code="early.alert.created.date"/></th>
                  <th  class="flc-pager-sort-header"><spring:message code="early.alert.status"/></th>
                  <th  class="flc-pager-sort-header"><spring:message code="early.alert.last.response.date"/></th>
                  <th  class="flc-pager-sort-header"><spring:message code="early.alert.course.title"/></th>
                  <th class="flc-pager-sort-header"><spring:message code="early.alert.course.term.name"/></th>
                  
                </tr>
              </thead>
              <tbody class="roster-body">
              <c:forEach var="ob" varStatus="status" items="${earlyAlerts}">
                <tr >
                   <td class="createdDate"><fmt:formatDate pattern="MMMMM d, yyyy h:mm a" value="${ob.createdDate}" /></td>
                  <td class="status"><c:out value="${ob.status}"/></td>
                  <td class="lastResponseDate"><fmt:formatDate pattern="MMMMM d, yyyy h:mm a" value="${ob.lastResponseDate}" /></td>
                  <td class="courseTitle"><c:out value="${ob.courseTitle}"/></td>
                  <td class="courseTermName"><c:out value="${ob.courseTermName}"/></td>
                </tr>
               </c:forEach>
              </tbody>
            </table>
</div>
            </div>
         <div class="ea-label">
            <span><spring:message code="early.alert.summary"/>:</span>
         </div>
         <div class="ea-clear"></div>
      </c:otherwise>
    </c:choose>
     <!-- end: Early Alert Summary -->

      <!-- Assigned Counselor/Coach -->
      <div class="ea-input">
        <span class="field-assigned-counselor"></span>
      </div>
      <div class="ea-required">&nbsp;</div>
      <div class="ea-label">
        <span><spring:message code="assigned.counselor.coach"/>:</span>
      </div>
      <div class="ea-clear"></div>

      <!-- Office -->
      <div class="ea-input">
        <span class="field-office"></span>
      </div>
      <div class="ea-required">&nbsp;</div>
      <div class="ea-label">
        <span><spring:message code="office"/>:</span>
      </div>
      <div class="ea-clear"></div>

      <!-- Phone -->
      <div class="ea-input">
        <span class="field-phone"></span>
      </div>
      <div class="ea-required">&nbsp;</div>
      <div class="ea-label">
        <span><spring:message code="phone"/>:</span>
      </div>
      <div class="ea-clear"></div>

      <!-- Department -->
      <div class="ea-input">
        <span class="field-department"></span>
      </div>
      <div class="ea-required">&nbsp;</div>
      <div class="ea-label">
        <span><spring:message code="department"/>:</span>
      </div>
      <div class="ea-clear"></div>

      <!-- Email CC -->
      <div class="ea-input">
        <input type="text" class="field-email-cc" value="" />
      </div>
      <div class="ea-required">&nbsp;</div>
      <div class="ea-label">
        <span><spring:message code="email.cc"/>:</span>
      </div>
      <div class="ea-clear"></div>

      <!-- Campus -->
      <div class="ea-input">
        <select class="field-campus">
          <option class="prompt" value=""><spring:message code="select.a.campus"/></option>
        </select>
      </div>
      <div class="ea-required">*</div>
      <div class="ea-label">
        <span><spring:message code="campus"/>:</span>
      </div>
      <div class="ea-clear"></div>
      
       <!-- Referral Reasons -->
      <div class="ea-input">
        <ul class="field-reasons"></ul>
        <p><a href="javascript:void(0);" class="reasons-add-edit"><img src="<c:url value="/rs/famfamfam/silk/1.3/add.png" />" alt="<spring:message code="add.edit"/>" /> <spring:message code="add.edit"/></a></p>
      </div>
      <div class="ea-required">&nbsp;</div>
      <div class="ea-label">
        <span><spring:message code="referral.reasons"/>:</span>
      </div>
      <div class="ea-clear"></div>

      <!-- Faculty Suggestions -->
      <div class="ea-input">
        <ul class="field-suggestions"></ul>
        <p><a href="javascript:void(0);" class="suggestions-add-edit"><img src="<c:url value="/rs/famfamfam/silk/1.3/add.png" />" alt="<spring:message code="add.edit"/>" /> <spring:message code="add.edit"/></a></p>
      </div>
      <div class="ea-required">&nbsp;</div>
      <div class="ea-label">
        <span><spring:message code="faculty.suggestions"/>:</span>
      </div>
      <div class="ea-clear"></div>

      <!-- Comments -->
      <div class="ea-input">
        <textarea class="field-comments"></textarea>
      </div>
      <div class="ea-required">&nbsp;</div>
      <div class="ea-label">
        <span><spring:message code="comments"/>:</span>
      </div>
      <div class="ea-clear"></div>

      <!-- Buttons -->
      <div class="ea-buttons">
        <div class="buttons">
          <input class="button primary button-send" type="button" value="<spring:message code="send.early.alert"/>" />
          <a class="button" href="${cancelUrl}"><spring:message code="cancel"/></a>
        </div>
      </div>
      <div class="ea-required">&nbsp;</div>
      <div class="ea-label">&nbsp;</div>
      <div class="ea-clear"></div>
      
      <div class="reasons-dialog" style="display: none;">
        <ul>
        </ul>
      </div>
            
      <div class="suggestions-dialog" style="display: none;">
        <ul>
        </ul>
      </div>
      

    
      <div class="notice-dialog" style="display: none;">
        <p><spring:message code="send.email.notice.to.student"/></p>
      </div>

    </form>
    
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

    ${n}.jQuery(function() {
        var $ = up.jQuery;
        
        var options = {
            urls: {
                person:       '<c:url value="/api/1/person/STUDENTID" />',
                campus:       '<c:url value="/api/1/reference/campus?limit=-1" />',
                reasons:       '<c:url value="/api/1/reference/earlyAlertReason?limit=-1" />',
                suggestions:  '<c:url value="/api/1/reference/earlyAlertSuggestion?limit=-1" />',
                submit:       '<c:url value="/api/1/person/STUDENTID/earlyAlert" />',
                configByName: '<c:url value="/api/1/reference/config?name=CONFIGNAME" />',
                enrollmentStatus: '<c:url value="/api/1/reference/enrollmentStatus?limit=-1"/>',
                done:         '${doneUrl}'
            },
            parameters: {
                courseName:   '${course.formattedCourse}',
                courseTitle:  '${course.title}',
                termCode:     '${course.termCode}',
                termName:     '${courseTermName}',
                sectionCode:  '${course.sectionCode}',
                studentId:    '${studentId}',
                enrollmentStatusCode: '${enrollment.statusCode}',
		        facultyid: '${facultyId}'
            },

            statusMappingConfigName: 'status_code_mappings'
        };
        ssp.EarlyAlertForm('#${n}earlyAlert', options);

    });
</script>
