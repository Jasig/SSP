<%@ page import="javax.servlet.jsp.JspException" %>
<%@page isErrorPage="true" contentType="text/html" %>

<html>
	<body>
		Request that failed: ${pageContext.errorData.requestURI}
		<br />
		Status code: ${pageContext.errorData.statusCode}
		<br />
		Exception: ${pageContext.errorData.throwable}
		<br />
		${pageContext.errorData.servletName}
	</body>
</html>