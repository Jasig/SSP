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
<%@ include file="../../../includes/htmlHeader.jsp" %>

<body>
	<!-- page -->
	<div class="page">
		<!-- header -->
		<div id="header">
			<div class="header-holder">
				<h1 class="logo"><a href="<%= WebProperties.getInstance().getInstHomeUrl() %>" target="_blank"><%= WebProperties.getInstance().getAppTitle() %></a></h1>
			</div>
		</div>
		<!-- wrapper -->
		<div id="wrapper">
			<!-- main -->
			<div id="main" class="alt-main">
				<!-- login form -->
				<form id="loginForm" action="/ssp/j_spring_security_check" method="post" class="login-form">
					<fieldset>
						<h2>
							<c:choose>
								<c:when test="${not empty param.login_error}">
									<span class="error">Login failed. </span><span>Please try again.</span>
								</c:when>
								<c:otherwise>
									Welcome to <%= WebProperties.getInstance().getAppTitle() %>. <span>Please log in.</span>
								</c:otherwise>
							</c:choose>
						</h2>
						<div class="row">
							<label for="name">NAME</label>
							<span class="text"><input type="text" value="" id="j_username" name="j_username" /></span>
						</div>
						<div class="row">
							<label for="pass">PASSWORD</label>
							<span class="text"><input type="password" value="" id="j_password" name="j_password" /></span>
						</div>
						<div class="row">
							<div class="check-holder">
								<input type="checkbox" class="ch-box" id="_spring_security_remember_me" name="_spring_security_remember_me" />
								<label for="check">Please keep me logged in</label>
							</div>
						</div>
						<div class="row">
							<input type="image" src="/ssp/images/login.png" class="submit" alt="submit button" id="submitButton" name="submitButton" />
						</div>
					</fieldset>
				</form>
			</div>
		</div>
		
	</div>
</body>
