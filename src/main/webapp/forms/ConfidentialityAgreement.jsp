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



<script>
	function displayAgreement() {
		$.getJSON('/ssp/api/1/reference/confidentialityDisclosureAgreement/', function(data) {
	
			$.each(data.rows, function(i, row) {
				$('.ConfidentialityAgreement').html(row.text);		
			});

		}).error(function(jqXHR, textStatus, errorThrown) {
			alert(jqXHR + " " + textStatus + " " + errorThrown);
		});
	}

	
	
	
	$(document).ready(function() {
		displayAgreement();		
	});	
	
</script>

</head>


<div class="ConfidentialityAgreement" style="width:800" ></div>











