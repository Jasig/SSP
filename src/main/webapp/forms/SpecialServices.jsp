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














