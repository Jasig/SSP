<jsp:directive.include file="/WEB-INF/jsp/include.jsp"/>

<c:set var="n"><portlet:namespace/></c:set>

<head>
<script src="http://code.jquery.com/jquery-latest.js"></script>

<script>

function populateForm(url)
{
	$('#stagingDiv').html('');
	$('#stagingDiv').load(url, function (responseText, textStatus, XMLHttpRequest) {
	    if (textStatus == "success") {
         	var newHTML = $('#stagingDiv').html();		
			swapOut(newHTML);
    	}
    	if (textStatus == "error") {
		 	swapOut("There is an error with this form");
    	}
	});		
}

function swapOut(newHTML){
	$('#rightbar').html(newHTML);
}

function clearFormDiv(){
     jQuery('#rightbar').html('');
}
</script>

<style>






label{
float: left;
width: 120px;
font-weight: bold;
}

input, textarea{
width: 180px;
margin-bottom: 5px;
}

textarea{
width: 250px;
height: 150px;
}

.boxes{
width: 1em;
}

#submitbutton{
margin-left: 120px;
margin-top: 5px;
width: 90px;
}

br{
clear: left;
}

#container{width:100%; margin:0 auto; background:#fff;}
#leftbar{ float:left; width:35; background:#fff; height:100%;border:.5px solid; padding-left:5em;}
#rightbar{ margin:0 0 0 45%;  background:#E6E6E6; padding-bottom: 2em; padding-left: 2em;  height:100% border:.5px solid;}

</style>
 

</head>

<div id="container" style="height:100%;">
<div id="leftbar" style="height:100%;border:.5px solid;">
<p>Select Report Form</p>
<ul>
<li><a onclick="populateForm('/ssp/forms/AddressLabel.jsp');" style='cursor:default'>Address Labels</a></li>
<li><a onclick="populateForm('/ssp/forms/SpecialServices.jsp');" style='cursor:default'>Special Services Report</a></li>
<li><a onclick="populateForm('/ssp/forms/ConfidentialityAgreement.jsp');" style='cursor:default'>Confidendiality Agreement</a></li>
<li><a href="/ssp/api/1/person/f549ecab-5110-4cc1-b2bb-369cac854dea/task/print/">Test Student Action Plan Report for Kenneth</a></li>
<li><a href="/ssp/api/1/report/f549ecab-5110-4cc1-b2bb-369cac854dea/History/">Test Student History Report for Kenneth</a></li>
</ul> 
</div>

<div id="rightbar"></div>
<br style="clear:both;"/>

</div>

<div id="stagingDiv" style="display:none" ></div>

<script>
$(document).ready(function() {
		populateForm('/ssp/forms/AddressLabel.jsp');
	});
</script>










