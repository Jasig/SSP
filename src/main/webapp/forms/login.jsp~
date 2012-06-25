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
