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
		var container = $("#SpecialServiceGroupIds");


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


function populateStudentType()
{
	$.getJSON("/ssp/api/1/reference/studentType/",
	  	function(data) {
		var container = $("#StudentTypeIds");


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
//populateProgramStatus();
//populateReferralSource();
//populateStudentType();


</script>

</head>


	<div class="AddressLabelForm">
<h1>Special Services</h1>
		<form action="/ssp/api/1/report/SpecialServices/" method="get">
	           <div class="box">             
                       <p>Special Services Groups Report Criteria:</p>    
		       
                      
                       
                       <label><span>Special Service Groups</span></label>
			    	<select id="SpecialServiceGroupIds" name="specialServiceGroupIds" multiple="multiple"></select>
                       <br/>                      
	<input type="submit"/>
                    </div> 
                </form>
         </div>
	
















