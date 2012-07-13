<head>
<script src="http://code.jquery.com/jquery-latest.js"></script>



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


<div class="ConfidentialityAgreement" style="width:800" ></div>











