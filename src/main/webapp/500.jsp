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
<%--
Special handling for file upload since this is typically the page that ends up
rendering when those operations fail. Since we're using Ext.js for file upload,
this response is going to be written into an iframe. If the response content
type does not indicate that the response can be handled as markup, the browser
will "helpfully" wrap the response payload in <pre></pre> tags before writing
it into the iFrame. Ext.js cannot then parse the response as valid json and the
user will get no meaningful error message, if they get an error message at all.
So... to convince the browser to leave the response alone in these cases we need
to set a text/html content type on the response. More info:

  http://www.sencha.com/forum/archive/index.php/t-17248.html

In other, non-file-upload cases, though, we want to set the correct content type
(application/json). Hence the conditional test of the exception type to try to
determine if we're dealing with an upload or not.

Interestingly, you can't wrap page directives in conditionals because they're
actually evaluated during the compilation phase. Hence the usage of
response.setContentType():

  http://java.boot.by/wcd-guide/ch06s02.html

--%>
<%@page isErrorPage="true" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page import="javax.servlet.jsp.JspException" %>
<%@ page import="org.springframework.web.multipart.MaxUploadSizeExceededException" %>
<%@ page import="org.springframework.web.multipart.MultipartException" %>
<%@ page import="org.slf4j.Logger,org.slf4j.LoggerFactory" %>
<%! static final Logger LOGGER = LoggerFactory.getLogger("500.jsp"); %>
<% LOGGER.error("Exception caught in view layer for URI: {}", pageContext.getErrorData().getRequestURI(), pageContext.getException()); %>
<% if (pageContext.getException().getCause() instanceof MultipartException ) { %>
	<% response.setContentType("text/html"); %>
<% } else { %>
	<% response.setContentType("application/json"); %>
<% } %>{"success":false, "uri":"${pageContext.errorData.requestURI}", "http status code":"${pageContext.errorData.statusCode}", "exception":"${pageContext.errorData.throwable}"}