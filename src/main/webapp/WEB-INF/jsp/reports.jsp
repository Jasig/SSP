<jsp:directive.include file="/WEB-INF/jsp/include.jsp"/>

<c:set var="n"><portlet:namespace/></c:set>


		





<p>
Hello Steveo
</p>

<div class="formList">
<ul>
<li>Address labels</li>
<li>Milk</li>
</ul> 
</div>

<div class="right_block_box">
	<div class="AddressLabelForm">

		<form>
	           <div class="box">             
                       <h1>Address Label Report:</h1>             
                       <label><span>Intake Date</span><input type="text" class="input_text" name="intakeDateTo" id="intakeDateTo"/></label><br/>              
                       <label><span>Home Department</span><input type="text" class="input_text" name="homeDepartment" id="homeDepartment"/></label><br/>
                       <label><span>Coach</span><input type="text" class="input_text" name="coachId" id="coachId"/></label><br/>
                       <label><span>Program Status</span><input type="text" class="input_text" name="programStatus" id="programStatus"/></label>  <br/>            
                       <label><span>Special Service Groups</span><input type="text" class="input_text" name="specialServiceGroupId" id="specialServiceGroupId"/></label><br/>
                       <label><span>Referral Source</span><input type="text" class="input_text" name="referralSourcesId" id="referralSourcesId"/></label><br/>
                       <label><span>Anticipated Start Term</span><input type="text" class="input_text" name="anticipatedStartTerm" id="anticipatedStartTerm"/></label><br/>              
                       <label><span>Anticipated Start Year</span><input type="text" class="input_text" name="anticipatedStartYear" id="anticipatedStartYear"/></label><br/>
                       <label><span>Student Type</span><input type="text" class="input_text" name="studentTypeId" id="studentTypeId"/></label><br/>
                       <label><span>Registration Term</span><input type="text" class="input_text" name="registrationTerm" id="registrationTerm"/></label>  <br/>            
                       <label><span>Registration Year</span><input type="text" class="input_text" name="registrationYear" id="registrationYear"/></label><br/>
                       <label><input type="button" class="button" value="Submit Form" /></label>  
                    </div> 
                </form>
         </div>
	
</div>
