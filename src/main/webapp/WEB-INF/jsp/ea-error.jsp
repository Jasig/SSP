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

<link href="<c:url value="/resources/css/early-alert.css" />" rel="stylesheet" type="text/css">

<div id="${n}earlyAlert" class="fl-widget portlet early-alert" role="section">
  <!-- Errors -->
  <div class="errors">
    <div class="portlet-msg-error portlet-msg error-message error" role="status" >
      <div class="titlebar">
        <h3 class="title"><span class="error-title"><spring:message code="error.generic.title"/></span></h3>
      </div>
      <div class="content">
        <p><span class="error-body"><c:out value="${exception.message}"/></span></p>
      </div>
    </div>
  </div>

  <br/><br/>
  <a href="<portlet:renderURL portletMode="view" windowState="normal"/>"><spring:message code="error.goback"/></a>
</div>