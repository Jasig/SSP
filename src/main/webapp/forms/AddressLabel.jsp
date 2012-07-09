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
function loadPdf(url, timeout){
   $.ajax({
     url: url,
     success: function(data){
       window.open(url);
     },
     error: function(error, status){
       window.alert("Problem retrieving PDF.\nThe error status is: " + status);
     },
     timeout: timeout,
     dataType: "application/pdf"   
  });
}




	function populateSpecialServices() {
		$.getJSON("/ssp/api/1/reference/specialServiceGroup/", function(data) {
			var container = $("#SpecialServiceGroupIds");

			$.each(data.rows, function(i, row) {
				addSelectItem(row.id, row.name, container);
			});

		}).error(function(jqXHR, textStatus, errorThrown) {
			alert(jqXHR + " " + textStatus + " " + errorThrown);
		});
	}

	function populateProgramStatus() {
		$.getJSON("/ssp/api/1/reference/programStatus/", function(data) {
			var container = $("#ProgramStatusGroup");

			$.each(data.rows, function(i, row) {
				addSelectItem(row.id, row.name, container);
			});

		}).error(function(jqXHR, textStatus, errorThrown) {
			alert(jqXHR + " " + textStatus + " " + errorThrown);
		});
	}

	function populateReferralSource() {
		$.getJSON("/ssp/api/1/reference/referralSource/", function(data) {
			var container = $("#ReferralSourceGroup");

			$.each(data.rows, function(i, row) {
				addSelectItem(row.id, row.name, container);
			});

		}).error(function(jqXHR, textStatus, errorThrown) {
			alert(jqXHR + " " + textStatus + " " + errorThrown);
		});
	}

	function populateStudentType() {
		$.getJSON("/ssp/api/1/reference/studentType/", function(data) {
			var container = $("#StudentTypeIds");

			$.each(data.rows, function(i, row) {
				addSelectItem(row.id, row.name, container);
			});

		}).error(function(jqXHR, textStatus, errorThrown) {
			alert(jqXHR + " " + textStatus + " " + errorThrown);
		});
	}

	function addSelectItem(uid, name, container) {
		var inputs = container.find('input');
		var id = inputs.length + 1;

		var html = '<option value="'+ uid +'">' + name + '</option>';
		container.append($(html));
	}

	$(document).ready(function() {
		populateSpecialServices();
		populateProgramStatus();
		populateReferralSource();
		populateStudentType();
	});




function validateForm(form)
{
var rgx = /(\d{4})-(\d{2})-(\d{2})/;

if(form.createDateFrom.value != '' && !form.createDateFrom.value.match(rgx))
{
	alert('Date Student Added To does not match (yyyy-MM-dd');
form.createDateFrom.focus();
	return false;
}
if(form.createDateTo.value != '' && !form.createDateTo.value.match(rgx))
{
	alert('Date Student Added From does not match (yyyy-MM-dd');
form.createDateTo.focus();
	return false;
}
return true;
}

</script>

</head>


<div class="AddressLabelForm">
	<h1>Address labels</h1>
	<form action="/ssp/api/1/report/AddressLabels/" method="get" target="_top" onSubmit="return validateForm(this);" >
		<div class="box">
			<p>Address Label Report Criteria:</p>
			<p>required fields are denoted by an asterisc</p>
			<label><span>Program Status</span></label> <select
				id="ProgramStatusGroup" name="programStatus"
				class="custom-class1 custom-class2" style="width: 200px;">
				<option value=""></option>
			</select> <br /> <label><span>Student Type</span></label> <select
				id="StudentTypeIds" name="studentTypeIds" multiple="multiple"></select>
			<br /> <label><span>Special Service Groups</span></label> <select
				id="SpecialServiceGroupIds" name="specialServiceGroupIds"
				multiple="multiple"></select> <br /> <label><span>Referral
					Source</span></label> <select id="ReferralSourceGroup" name="referralSourcesIds"
				multiple="multiple" /></select> <br /> <label><span>Date
					Student Added From</span></label><input type="text" name="createDateFrom"
				id="createDateFrom" />(yyyy-MM-dd)<br /> 
<label><span>Date Student Added To</span></label><input type="text" name="createDateTo" id="createDateTo" />(yyyy-MM-dd)<br />
			<label><span>Anticipated Start Term</span></label> <select
				id="anticipatedStartTerm" name="anticipatedStartTerm"
				class="custom-class1 custom-class2" style="width: 200px;">
				<option value=""></option>
				<option value="Fall" class="test-class-1">Fall</option>
				<option value="Winter" class="test-class-1">Winter</option>
				<option value="Spring" class="test-class-1">Spring</option>
				<option value="Summer" class="test-class-1">Summer</option>
			</select><br /> <label><span>Anticipated Start Year</span></label> <select
				id="anticipatedStartYear" name="anticipatedStartYear"
				class="custom-class1 custom-class2" style="width: 200px;">
				<option value=""></option>
				<option value="2010" class="test-class-1">2010</option>
				<option value="2011" class="test-class-1">2011</option>
				<option value="2012" class="test-class-1">2012</option>
				<option value="2013" class="test-class-1">2013</option>
				<option value="2014" class="test-class-1">2014</option>
				<option value="2015" class="test-class-1">2015</option>
				<option value="2016" class="test-class-1">2016</option>
				<option value="2017" class="test-class-1">2017</option>
				<option value="2018" class="test-class-1">2018</option>
				<option value="2019" class="test-class-1">2019</option>
				<option value="2020" class="test-class-1">2020</option>
			</select><br />
			<br />
			<br />
			<div
				style="border: 1px solid; border-radius: 5px; -moz-border-radius: 5px; padding: 5px; width: 250px">
				
					<span>Report Output</span>
				
				<br /> <label><span>pdf</span></label><input type="radio"
					name="reportType" value="pdf" checked /><br /> <label><span>csv</span></label><input
					type="radio" name="reportType" value="csv" /><br /> 
			</div>
			<br /> <input type="submit" />
		</div>
	</form>
</div>









