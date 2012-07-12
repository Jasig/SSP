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

	function addSelectItem(uid, name, container) {
		var inputs = container.find('input');
		var id = inputs.length + 1;

		var html = '<option value="'+ uid +'">' + name + '</option>';
		container.append($(html));
	}

	$(document).ready(function() {
		populateSpecialServices();
	});

</script>

</head>


<div class="report">
	<h1>Special Services</h1>
	<form action="/ssp/api/1/report/SpecialServices/" method="get" class="alert-form">


		<!-- Special Service Groups -->
		<div class="ea-input">
			<select id="SpecialServiceGroupIds" name="specialServiceGroupIds"
				multiple="multiple"></select>
		</div>
		<div class="ea-label">
			<span>Special Service Groups:</span>
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














