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











