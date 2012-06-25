<jsp:directive.include file="/WEB-INF/jsp/include.jsp"/>

<c:set var="n"><portlet:namespace/></c:set>

<head>
<script src="http://code.jquery.com/jquery-latest.js"></script>

<script>

function populateAddress()
{
	clearFormDiv();
	$('#rightbar').load('/ssp/forms/AddressLabel.jsp');
}

function populateSS()
{
	clearFormDiv();
	$('#rightbar').load('/ssp/forms/SpecialServices.jsp');
}

function clearFormDiv()
{
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
#leftbar{ float:left; width:35%; background:#fff;}
#rightbar{ margin:0 0 0 45%; background:#E6E6E6; padding-bottom: 2em; padding-left: 2em;  height:500}

</style>


</head>

<div id="container" style="height:100%;">
<div id="leftbar" style="height:100%;">
<ul>
<li><a onclick="populateAddress();" >Address labels</a></li>
<li><a onclick="populateSS();" >Special Services labels</a></li>
<li><a onclick="clearFormDiv();" >Clear Form</a></li>
</ul> 
</div>

<div id="rightbar" style="height:1000;" ></div>
<br style="clear:both;"/>

</div>















