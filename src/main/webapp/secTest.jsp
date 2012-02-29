<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<body>
<h1>Security Test Page</h1>
<p>
Anyone can view this page.
</p>

<p>
Your principal object is: <%= request.getUserPrincipal() %>
</p>

<p>
<sec:authorize url='/ssp/api/example'>You can currently access "/ssp/api/example".</sec:authorize>
</p>

<p>
<sec:authorize url='/api/example'>You can currently access "/api/example".</sec:authorize>
</p>

<p>
<sec:authorize url='/api/reference/challenge/all'>You can currently access "/api/reference/challenge/all".</sec:authorize>
</p>

<p>
<sec:authorize url='/ssp/api/reference/challenge/all'>You can currently access "/ssp/api/reference/challenge/all".</sec:authorize>
</p>

</body>
</html>