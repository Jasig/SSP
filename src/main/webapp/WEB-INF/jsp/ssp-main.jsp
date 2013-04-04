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

<!-- ExtJS Styles -->
<link rel="stylesheet" type="text/css" href="<c:url value="/js/libs/ext-4.1/resources/css/ext-all.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/js/libs/ext-4.1/src/ux/css/CheckHeader.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/js/libs/ext-4.1/src/ux/css/ItemSelector.css" />">

<!-- SSP Theme -->
<link href="<c:url value="/resources/css/tabs.css" />" rel="stylesheet" type="text/css" />
<link href="<c:url value="/resources/css/ssp-ext-theme.css" />" rel="stylesheet" type="text/css" />

<!-- ExtJS Lib -->
<c:choose>
	<c:when test="${useMinified}">
		<script type="text/javascript" src="<c:url value="/js/libs/ext-4.1/ext-all.js" />"></script>
	</c:when>
	<c:otherwise>
		<script type="text/javascript" src="<c:url value="/js/libs/ext-4.1/ext.js" />"></script>
	</c:otherwise>
</c:choose>

<script type="text/javascript" src="<c:url value="/js/libs/ext-4.1/src/ux/CheckColumn.js" />"></script>
<script type="text/javascript" src="<c:url value="/js/libs/ext-4.1/src/ux/form/MultiSelect.js" />"></script>
<script type="text/javascript" src="<c:url value="/js/libs/ext-4.1/src/ux/form/ItemSelector.js" />"></script>   

<!-- DEFT Lib -->
<script type="text/javascript" src="<c:url value="/js/libs/deft/deft-0.6.8pre.js" />"></script>

<script type="text/javascript">
	// setting renderSSPFullScreen to true will render the app 
	// in the full Viewport size.
	// This option will exclude any divs and render the SSP App
	// to the body of element of the page.
	// If renderSSPFullScreen is set to false
	// then the sspParentDivId will allow you to set a div
	// in which to render the SSP App.
	var renderSSPFullScreen = false;
	var sspParentDivId = '${n}ssp';
</script>

<!-- SSP Application -->
<c:choose>
	<c:when test="${useMinified}">
		<script type="text/javascript" src="<c:url value="/app-all.js" />"></script>
	</c:when>
	<c:otherwise>
		<script type="text/javascript" src="<c:url value="/app.js" />"></script>
	</c:otherwise>
</c:choose>
<div class="sspOuter">
	<div id="${n}ssp"></div>
</div>
