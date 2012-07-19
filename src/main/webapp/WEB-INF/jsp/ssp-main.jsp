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
<script type="text/javascript" src="<c:url value="/js/libs/ext-4.1/ext-all.js" />"></script>
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
<script type="text/javascript" src="<c:url value="/app.js" />"></script>

<div class="sspOuter">
	<div id="${n}ssp"></div>
</div>
