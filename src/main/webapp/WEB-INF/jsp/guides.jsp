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

		
		<link rel="stylesheet" href="/styles/lib/jquery.mobile-1.0.1.min.css" />
		<link rel="stylesheet" href="/styles/lib/apprise.min.css" />
		<link rel="stylesheet" href="/styles/stylesheet.min.css" />
		<!--[if IE 7]>
			<link rel="stylesheet" href="/styles/win-ie7.min.css">
		<![endif]-->
		<!--[if IE 8]>
			<link rel="stylesheet" href=/styles/win-ie8.min.css">
		<![endif]-->
		
		<link media="only screen and (min-device-width: 768px) and (max-device-width: 1024px)" rel="stylesheet" href="styles/ipad.min.css" />
		
		<script type="text/javascript" src="/MyGPS/scripts/lib/json2.js"></script>
		<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js"></script>
		<script type="text/javascript" src="/MyGPS/scripts/lib/jquery.mobile-1.0rc1.min.js"></script>
		<script type="text/javascript" src="/MyGPS/scripts/lib/jquery.tmpl.min.js"></script>
		<script type="text/javascript" src="/MyGPS/scripts/lib/jquery.tmpl.loadTemplates.min.js"></script>
		<script type="text/javascript" src="/MyGPS/scripts/lib/jquery.simplemodal.1.4.2.min.js"></script>
		<script type="text/javascript" src="/MyGPS/scripts/lib/jquery.parameter.min.js"></script>
		<script type="text/javascript" src="/MyGPS/scripts/lib/underscore-min.js"></script>
		<script type="text/javascript" src="/MyGPS/scripts/lib/knockout-1.2.1.js"></script>
		<script type="text/javascript" src="/MyGPS/scripts/lib/ko.jqm.bindings.min.js"></script>
		<script type="text/javascript" src="/MyGPS/scripts/lib/namespace.min.js"></script>
		<script type="text/javascript" src="/MyGPS/scripts/lib/apprise-1.5.min.js"></script>
		<script type="text/javascript" src="/MyGPS/scripts/MyGPS-2.1.0.min.js"></script>

		
		<!-- Guides Page -->
		
		<div id="guides-page" class="page" data-role="page">
			
			<script type="text/javascript" src="scripts/guides.js"></script>
			
			<div class="header">
				<div class="banner" data-bind="template: { name: 'bannerTemplate' }"></div>
				<div data-role="header" data-theme="b">
					<a href="<portlet:renderURL></portlet:renderURL>" data-icon="home" data-rel="back">Home</a>
					<h1>&nbsp;</h1>
					<a href="login.jsp" rel="external" class="ui-btn-right" data-bind="visible: !viewModel.authenticated()" data-role="button" data-icon="custom">Login</a>
					<p class="person-name" data-bind="text: authenticatedPersonName"></p>
					<a href="../j_spring_security_logout" rel="external" class="ui-btn-right" data-bind="visible: viewModel.authenticated()" data-role="button" data-icon="custom">Logout</a>
				</div>
			</div>
			
			<div class="content" data-role="content">
				
				<div class="guide-list">
					<h2>Self Help Guides</h2>
					<p>Please select a Self Help Guide from this list to begin:</p>
					<ul data-role="listview" data-inset="true" data-bind="template: { name: 'guideTemplate', foreach: selfHelpGuides }">
					</ul>
				</div>
			</div>

			<div class="footer" data-bind="template: { name: 'footerTemplate' }"></div>
			
		</div>
			