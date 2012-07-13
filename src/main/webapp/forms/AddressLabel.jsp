<head>

<script>
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

	function validateForm(form) {
		var rgx = /(\d{4})-(\d{2})-(\d{2})/;

		if (form.createDateFrom.value != ''
				&& !form.createDateFrom.value.match(rgx)) {
			alert('Date Student Added To does not match (yyyy-MM-dd)');
			form.createDateFrom.focus();
			return false;
		}
		if (form.createDateTo.value != ''
				&& !form.createDateTo.value.match(rgx)) {
			alert('Date Student Added From does not match (yyyy-MM-dd)');
			form.createDateTo.focus();
			return false;
		}
		return true;
	}
</script>

</head>

<div class="report">
	<h1>Address labels</h1>
	<form action="/ssp/api/1/report/AddressLabels/" method="get"
		target="_top" onSubmit="return validateForm(this);" class="alert-form">
		<!-- program Status -->
		<div class="ea-input">
			<select id="ProgramStatusGroup" name="programStatus">
				<option value=""></option>
			</select>
		</div>
		<div class="ea-label">
			<span>Program Status:</span>
		</div>
		<div class="ea-clear"></div>

		<!-- Student Type -->

		<div class="ea-input">
			<select id="StudentTypeIds" name="studentTypeIds" multiple="multiple"></select>
		</div>
		<div class="ea-label">
			<span>Student Type:</span>
		</div>
		<div class="ea-clear"></div>

		<!-- Special Service Groups -->
		<div class="ea-input">
			<select id="SpecialServiceGroupIds" name="specialServiceGroupIds"
				multiple="multiple"></select>
		</div>
		<div class="ea-label">
			<span>Special Service Groups:</span>
		</div>
		<div class="ea-clear"></div>

		<!-- Referral Source -->
		<div class="ea-input">
			<select id="ReferralSourceGroup" name="referralSourcesIds"
				multiple="multiple">
			</select>
		</div>
		<div class="ea-label">
			<span>Referral Source:</span>
		</div>
		<div class="ea-clear"></div>


		<!-- Date From -->
		<div class="ea-input">
			<input type="text" name="createDateFrom" id="createDateFrom" />
		</div>
		<div class="ea-label">
			<span>Date Student Added From:</span>
		</div>
		<div class="ea-clear"></div>

		<!-- Date To -->
		<div class="ea-input">
			<input type="text" name="createDateTo" id="createDateTo" />
		</div>
		<div class="ea-label">
			<span>Date Student Added To:</span>
		</div>
		<div class="ea-clear"></div>

		<!-- Anticipated Start Term -->
		<div class="ea-input">
			<select id="anticipatedStartTerm" name="anticipatedStartTerm">
				<option value=""></option>
				<option value="Fall" class="test-class-1">Fall</option>
				<option value="Winter" class="test-class-1">Winter</option>
				<option value="Spring" class="test-class-1">Spring</option>
				<option value="Summer" class="test-class-1">Summer</option>
			</select>
		</div>
		<div class="ea-label">
			<span>Anticipated Start Term:</span>
		</div>
		<div class="ea-clear"></div>

		<!-- Anticipated Start Year -->
		<div class="ea-input">
			<select id="anticipatedStartYear" name="anticipatedStartYear">
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
			</select>
		</div>
		<div class="ea-label">
			<span>Anticipated Start Term:</span>
		</div>
		<div class="ea-clear"></div>

		<!-- output type -->
		<div class="ea-input">
			<label><span>pdf</span></label><input type="radio" name="reportType"
				value="pdf" checked /><br /> <label><span>csv</span></label><input
				type="radio" name="reportType" value="csv" />
		</div>

		<div class="ea-label">
			<span>Output Type:</span>
		</div>
		<div class="ea-clear"></div>

		<div class="ea-buttons">
			<div class="buttons">
				<input class="button primary button-send" type="submit"
					value="submit" />
			</div>
		</div>
	</form>
</div>