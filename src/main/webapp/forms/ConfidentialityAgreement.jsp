<head>
<script src="http://code.jquery.com/jquery-latest.js"></script>



<style>
label {
	float: left;
	width: 120px;
	font-weight: bold;
}

input,textarea {
	width: 180px;
	margin-bottom: 5px;
}

textarea {
	width: 250px;
	height: 150px;
}

.boxes {
	width: 1em;
}

#submitbutton {
	margin-left: 120px;
	margin-top: 5px;
	width: 90px;
}

br {
	clear: left;
}
</style>




<script>
function displayAgreement() {
	$.getJSON('/ssp/api/1/reference/confidentialityDisclosureAgreement/', function(data) {
	
		$.each(data.rows, function(i, row) {
var newHTML = $('#ConfidentialityAgreement').html();		
		swapOut(row.text);
			
		});

	}).error(function(jqXHR, textStatus, errorThrown) {
		swapOut(jqXHR + " " + textStatus + " " + errorThrown);
	});
}

	
	
	
	$(document).ready(function() {
		displayAgreement();		
	});	
	
</script>

</head>


<div class="ConfidentialityAgreement">

	
</div>











