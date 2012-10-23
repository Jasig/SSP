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
<sec:authorize url='/api/1/reference/challenge/all'>You can currently access "/api/reference/challenge/all".</sec:authorize>
</p>

<p>
<sec:authorize url='/ssp/api/1/reference/challenge/all'>You can currently access "/ssp/api/reference/challenge/all".</sec:authorize>
</p>

</body>
</html>