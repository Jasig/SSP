<!DOCTYPE html>
<html>
	<head>
		<title>Resources - Contact Your Coach</title>
		
		<link rel="stylesheet" href="/styles/lib/jquery.mobile-1.0.1.min.css" />
		<link rel="stylesheet" href="/styles/lib/apprise.min.css" />
		<link rel="stylesheet" href="/styles/stylesheet.min.css" />
		<!--[if IE 7]>
			<link rel="stylesheet" href="/styles/win-ie7.min.css">
		<![endif]-->
		<!--[if IE 8]>
			<link rel="stylesheet" href="/styles/win-ie8.min.css">
		<![endif]-->
		
		<link media="only screen and (min-device-width: 768px) and (max-device-width: 1024px)" rel="stylesheet" href="/styles/ipad.min.css" />
		
		<script type="text/javascript" src="/scripts/lib/json2.js"></script>
		<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js"></script>
		<script type="text/javascript" src="/scripts/lib/jquery.mobile-1.0rc1.min.js"></script>
		<script type="text/javascript" src="/scripts/lib/jquery.tmpl.min.js"></script>
		<script type="text/javascript" src="/scripts/lib/jquery.tmpl.loadTemplates.min.js"></script>
		<script type="text/javascript" src="/scripts/lib/jquery.simplemodal.1.4.2.min.js"></script>
		<script type="text/javascript" src="/scripts/lib/jquery.parameter.min.js"></script>
		<script type="text/javascript" src="/scripts/lib/underscore-min.js"></script>
		<script type="text/javascript" src="/scripts/lib/knockout-1.2.1.js"></script>
		<script type="text/javascript" src="/scripts/lib/ko.jqm.bindings.min.js"></script>
		<script type="text/javascript" src="/scripts/lib/namespace.min.js"></script>
		<script type="text/javascript" src="/scripts/lib/apprise-1.5.min.js"></script>
		<script type="text/javascript" src="/scripts/MyGPS-2.1.0.min.js"></script>
	</head>
	<body>
		
		<!-- Contact Page -->
		
		<div id="contact-page" class="page" data-role="page">
			
			<script type="text/javascript" src="scripts/contact.js"></script>
			
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
				<div class="contact-form">
					<h2>Contact Your Coach</h2>
					<p>We are here to help you achieve your goals, address your action plan items, and meet the challenges you have identified.</p>
					<form>
						<div data-role="fieldcontain">
						    <label for="subject">Subject:</label>
						    <input type="text" name="subject" id="subject" data-bind="value: subject" />
						</div>
						<div data-role="fieldcontain">
							<label for="message">Message:</label>
							<textarea cols="40" rows="8" name="message" id="message" data-bind="value: message"></textarea>
						</div>
						<div class="contact-form-button-container">
							<button type="submit" data-theme="b" data-inline="true" name="submit" value="send" data-bind="click: function () { viewModel.contactCoach( viewModel.subject(), viewModel.message(), { result: function () { $.mobile.changePage( 'index.html' ); } } ); }">Send</button>
						</div>
					</form>
				</div>
			</div>
			
			<div class="footer" data-bind="template: { name: 'footerTemplate' }"></div>
			
		</div>
		
	</body>
</html>