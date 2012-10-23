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

<c:set var="n"><portlet:namespace/></c:set>
  
<link rel="stylesheet" href="<c:url value="/MyGPS/styles/lib/jquery.mobile-1.0.1.min.css"/>" />
<link rel="stylesheet" href="<c:url value="/MyGPS/styles/lib/apprise.css"/>" />
<link rel="stylesheet" href="<c:url value="/MyGPS/styles/stylesheet.css"/>" />


<link media="only screen and (min-device-width: 768px) and (max-device-width: 1024px)" rel="stylesheet" href="<c:url value="/MyGPS/styles/ipad.css"/>" />

<script type="text/javascript" src="<c:url value="/MyGPS/scripts/lib/json2.js"/>"></script>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.js"></script>
<script type="text/javascript" src="<c:url value="/MyGPS/scripts/lib/jquery.mobile-1.0rc1.js"/>"></script>
<script type="text/javascript" src="<c:url value="/MyGPS/scripts/lib/jquery.tmpl.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/MyGPS/scripts/lib/jquery.tmpl.loadTemplates.js"/>"></script>
<script type="text/javascript" src="<c:url value="/MyGPS/scripts/lib/jquery.simplemodal.1.4.2.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/MyGPS/scripts/lib/jquery.parameter.js"/>"></script>
<script type="text/javascript" src="<c:url value="/MyGPS/scripts/lib/underscore-min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/MyGPS/scripts/lib/knockout-1.2.1.debug.js"/>"></script>
<script type="text/javascript" src="<c:url value="/MyGPS/scripts/lib/ko.jqm.bindings.js"/>"></script>
<script type="text/javascript" src="<c:url value="/MyGPS/scripts/lib/namespace.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/MyGPS/scripts/lib/apprise-1.5.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/MyGPS/scripts/MyGPS-2.1.0.js"/>"></script>

<div id="home-page" class="page" data-role="page">
	
	<script type="text/javascript" src="<c:url value="/MyGPS/scripts/home.js"/>"></script>
	
	<div class="header">
		<div class="banner" data-bind="template: { name: 'bannerTemplate' }"></div>
		<div data-role="header" data-theme="b">
			<h1>&nbsp;</h1>
			<a href="login.jsp" rel="external" class="ui-btn-right" data-bind="visible: !viewModel.authenticated()" data-role="button" data-icon="custom">Login</a>
			<p class="person-name" data-bind="text: authenticatedPersonName"></p>
			<a href="../j_spring_security_logout" rel="external" class="ui-btn-right" data-bind="visible: viewModel.authenticated()" data-role="button" data-icon="custom">Logout</a>
		</div>
	</div>
	
	<div data-role="content" class="content">
		
		<div class="introduction">
			<h2>Welcome</h2>
			<p>This self help tool will assist you in identifying and overcoming challenges or barriers to your success at Sinclair Community College. Please use the Self Help Guides to begin the process of identifying the challenges you might face, and discovering the solutions available to meet those challenges. The tool will assist you in building a Personal Road Map that will guide you on your journey to success. Good luck on that journey!</p>		
			<h2>Tools for Your Success</h2>
			<ul data-role="listview" data-inset="true">
				<li><a href="<portlet:renderURL><portlet:param name="action" value="showGuides"/></portlet:renderURL>">Self Help Guides</a></li>
				<li style="display: none;" data-bind="visible: canContactCoach"><a href="<portlet:renderURL><portlet:param name="action" value="showContacts"/></portlet:renderURL>">Contact Your Coach</a></li>
				<li><a href="<portlet:renderURL><portlet:param name="action" value="search"/></portlet:renderURL>">Search for Resources</a></li>
			</ul>
			
		</div>
		
		<div class="task-list" data-bind="template: { name: 'tasksTemplate' }"></div>
		
	</div>
	
	<div class="footer" data-bind="template: { name: 'footerTemplate' }"></div>
				
</div>
		
<script type="text/javascript">

	var ${n} = {};
	${n}.jQuery = jQuery.noConflict(true);

	${n}.jQuery(function() {
		var $ = ${n}.jQuery;

		// do anything?

	});

</script>
