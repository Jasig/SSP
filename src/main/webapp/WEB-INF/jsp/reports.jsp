<jsp:directive.include file="/WEB-INF/jsp/include.jsp"/>

<c:set var="n"><portlet:namespace/></c:set>

<head>
<script src="http://code.jquery.com/jquery-latest.js"></script>



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
</style>




<script>

function populateSpecialServices()
{
	$.getJSON("/ssp/api/1/reference/specialServiceGroup/",
	  	function(data) {
		var container = $("#specialServiceGroupIds");


	$.each(data.rows, function(i,row){
	      addSelectItem(row.id,row.name,container);      
	    });



	 	 }
	)
	.error(function(jqXHR, textStatus, errorThrown) { 
		alert(jqXHR +" "+ textStatus+" "+ errorThrown); 
	      }
	      );
}


function populateProgramStatus()
{
	$.getJSON("/ssp/api/1/reference/programStatus/",
	  	function(data) {
		var container = $("#ProgramStatusGroup");


	$.each(data.rows, function(i,row){
	      addSelectItem(row.id,row.name,container);      
	    });



	 	 }
	)
	.error(function(jqXHR, textStatus, errorThrown) { 
		alert(jqXHR +" "+ textStatus+" "+ errorThrown); 
	      }
	      );
}


function populateReferralSource()
{
	$.getJSON("/ssp/api/1/reference/referralSource/",
	  	function(data) {
		var container = $("#ReferralSourceGroup");


	$.each(data.rows, function(i,row){
	      addSelectItem(row.id,row.name,container);      
	    });



	 	 }
	)
	.error(function(jqXHR, textStatus, errorThrown) { 
		alert(jqXHR +" "+ textStatus+" "+ errorThrown); 
	      }
	      );
}


function addSelectItem(uid,name,container) {
   var inputs = container.find('input');
   var id = inputs.length+1;

   var html = '<option value="'+ uid +'">' + name + '</option>';
   container.append($(html));
}

populateSpecialServices();
populateProgramStatus();
populateReferralSource();


</script>

</head>






<p>
Hello Steveo
</p>

<div class="formList">
<ul>
<li>Address labels</li>
</ul> 
</div>

<div class="right_block_box">
	<div class="AddressLabelForm">
<h1>Address labels</h1>
		<form action="/ssp/api/1/report/AddressLabels/" method="get" target="_blank">
	           <div class="box">             
                       <p>Address Label Report Criteria:</p>    
		       <p>required fields are denoted by an asterisc</p>         
                       <label><span>Home Department</span></label>
                           <select id="standard-dropdown" name="standard-dropdown" class="custom-class1 custom-class2" style="width: 200px;">
		    		<option value="1" class="test-class-1">Item 1</option>
		    	   </select>
</br>
                       <label><span>Assigned Counselor/Coach</span></label>
                           <select id="standard-dropdown" name="standard-dropdown" class="custom-class1 custom-class2" style="width: 200px;">
		    		<option value="1" class="test-class-1">Item 1</option>
		    	   </select></br>
                       <label><span>Program Status</span></label>
		                   <select id="ProgramStatusGroup" name="standard-dropdown" class="custom-class1 custom-class2" style="width: 200px;">
			    	   </select>
                       </br>
                       <label><span>Special Service Groups</span></label>
			    	<select id="specialServiceGroupIds" name="specialServiceGroupIds" multiple="multiple">
			    	</select>
                       </br>
                       <label><span>Referral Source</span></label>
			    	<select id="ReferralSourceGroup" name="multi-select-control" multiple="multiple">
			    	</select>
                       </br>
                       <label><span>Counseling Variable Type</span></label>
			    	<select id="multi-select-control" name="multi-select-control" multiple="multiple">
			    		<option value="1" selected="selected">Item 1</option>
			    		<option value="2">Item 2</option>
			    		<option value="3">Item 3</option>
			    		<option value="4">Item 4</option>
			    		<option value="5" disabled="disabled">Item 5 (disabled)</option>
			    	</select>
                       </br>
                       <label><span>Registration Term</span></label><input type="text" class="input_text" name="registrationTerm" id="registrationTerm"/>  <br/>            
                       <label><span>Registration Year</span></label><input type="text" class="input_text" name="registrationYear" id="registrationYear"/><br/>
                       <label><span>Anticipated Start Term</span></label><input type="text" class="input_text" name="anticipatedStartTerm" id="anticipatedStartTerm"/><br/>              
                       <label><span>Anticipated Start Year</span></label><input type="text" class="input_text" name="anticipatedStartYear" id="anticipatedStartYear"/><br/>
	<input type="submit"/>
                    </div> 
                </form>
         </div>
	
</div>


















