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
	function populatePersonIds() {
		$.getJSON("/ssp/api/1/person/", function(data) {
			var container = $("#PersonIds");

			$.each(data.rows, function(i, row) {
				addSelectItem(row.id, row.firstName + " " + row.lastName, container);
			});

		}).error(function(jqXHR, textStatus, errorThrown) {
			alert(jqXHR + " " + textStatus + " " + errorThrown + " " + errorThrown);
		});
	}

	function addSelectItem(uid, name, container) {
		var inputs = container.find('input');
		var id = inputs.length + 1;

		var html = '<option value="'+ uid +'">' + name + '</option>';
		container.append($(html));
	}

	
	
	
	$(document).ready(function() {
		populatePersonIds();		
	});	
	
</script>

</head>


<div class="AddressLabelForm">
	<h1>Student Action Plan Form</h1>
	<form action="/ssp/api/1/report/AddressLabels/" method="get">
		<div class="box">
			<p>Student Action Plan:</p>
			<p>required fields are denoted by an asterisc</p>
			<label><span>Students</span></label> <select
				id="PersonIds" name="programStatus"
				class="custom-class1 custom-class2" style="width: 200px;">
				<option value=""></option>
				</select>
			<br /> <input type="submit" />
		</div>
	</form>
</div>











