<jsp:directive.include file="/WEB-INF/jsp/include.jsp"/>

<c:set var="n"><portlet:namespace/></c:set>

<script src="<rs:resourceURL value="/rs/jquery/1.6.1/jquery-1.6.1.min.js"/>" type="text/javascript"></script>
<script src="<rs:resourceURL value="/rs/jqueryui/1.8.13/jquery-ui-1.8.13.min.js"/>" type="text/javascript"></script>
<script src="<c:url value="/js/early-alert-form.js" />" type="text/javascript"></script>

<link href="<c:url value="/resources/css/early-alert.css" />" rel="stylesheet" type="text/css">

<!-- Portlet -->
<div id="${n}earlyAlert" class="fl-widget portlet early-alert" role="section">
  
  <!-- Portlet Titlebar -->
  <div class="fl-widget-titlebar titlebar portlet-titlebar" role="sectionhead">
  	<h2 class="title" role="heading"><spring:message code="early.alert.details"/></h2>
  </div>

  <!-- Portlet Content -->
  <div class="fl-widget-content content portlet-content" role="main">
  
  	<!-- Portlet Message -->
  	<div class="portlet-msg-info portlet-msg info loading-message" role="status" style="display: none;">
    	<div class="titlebar">
        <h3 class="title"><spring:message code="loading"/> . . .</h3>
      </div>
      <div class="content">
    	  <p><spring:message code="please.wait.while.the.system.finishes.loading.roster"/></p>
      </div>
    </div>
    
    <form>
    
      <!-- Course -->
      <div class="ea-input">
        <span>ENGLISH - 124 - 001 - Academic Writing and Literature</span>
      </div>
      <div class="ea-required">&nbsp;</div>
      <div class="ea-label">
        <span>Course:</span>
      </div>
      <div class="ea-clear"></div>

      <!-- Term -->
      <div class="ea-input">
        <span>11/SD</span>
      </div>
      <div class="ea-required">&nbsp;</div>
      <div class="ea-label">
        <span>Term:</span>
      </div>
      <div class="ea-clear"></div>

      <!-- Student -->
      <div class="ea-input">
        <span>James K Polk</span>
      </div>
      <div class="ea-required">&nbsp;</div>
      <div class="ea-label">
        <span>Student:</span>
      </div>
      <div class="ea-clear"></div>

      <!-- Net ID -->
      <div class="ea-input">
        <span>5555555</span>
      </div>
      <div class="ea-required">&nbsp;</div>
      <div class="ea-label">
        <span>Net ID:</span>
      </div>
      <div class="ea-clear"></div>

      <!-- Student Email -->
      <div class="ea-input">
        <span>james.polk@president.gov</span>
      </div>
      <div class="ea-required">&nbsp;</div>
      <div class="ea-label">
        <span>Student Email:</span>
      </div>
      <div class="ea-clear"></div>

      <!-- Student Type -->
      <div class="ea-input">
        <span>ARC</span>
      </div>
      <div class="ea-required">&nbsp;</div>
      <div class="ea-label">
        <span>Student Type:</span>
      </div>
      <div class="ea-clear"></div>

      <!-- Assigned Counselor/Coach -->
      <div class="ea-input">
        <span>Lumpkin, Hortense</span>
      </div>
      <div class="ea-required">&nbsp;</div>
      <div class="ea-label">
        <span>Assigned Counselor/Coach:</span>
      </div>
      <div class="ea-clear"></div>

      <!-- Office -->
      <div class="ea-input">
        <span>6122A</span>
      </div>
      <div class="ea-required">&nbsp;</div>
      <div class="ea-label">
        <span>Office:</span>
      </div>
      <div class="ea-clear"></div>

      <!-- Phone -->
      <div class="ea-input">
        <span>555-5555</span>
      </div>
      <div class="ea-required">&nbsp;</div>
      <div class="ea-label">
        <span>Phone:</span>
      </div>
      <div class="ea-clear"></div>

      <!-- Department -->
      <div class="ea-input">
        <span>Academic Resource Center</span>
      </div>
      <div class="ea-required">&nbsp;</div>
      <div class="ea-label">
        <span>Department:</span>
      </div>
      <div class="ea-clear"></div>

      <!-- Email CC -->
      <div class="ea-input">
        <input type="text" value="" />
      </div>
      <div class="ea-required">&nbsp;</div>
      <div class="ea-label">
        <span>Email cc:</span>
      </div>
      <div class="ea-clear"></div>

      <!-- Campus -->
      <div class="ea-input">
        <select>
          <option>Dayton Campus</option>
        </select>
      </div>
      <div class="ea-required">*</div>
      <div class="ea-label">
        <span>Campus:</span>
      </div>
      <div class="ea-clear"></div>

      <!-- Referral Reason -->
      <div class="ea-input">
        <select>
          <option>Academic Concern</option>
        </select>
      </div>
      <div class="ea-required">*</div>
      <div class="ea-label">
        <span>Referral Reason:</span>
      </div>
      <div class="ea-clear"></div>

      <!-- Faculty Suggestions -->
      <div class="ea-input">
        <ul>
          <li>The Tutoring &amp; Learning Center</li>
          <li>See Advisor or Coach</li>
        </ul>
        <p><a href="">Add/Edit</a></p>
      </div>
      <div class="ea-required">&nbsp;</div>
      <div class="ea-label">
        <span>Faculty Suggestions:</span>
      </div>
      <div class="ea-clear"></div>

      <!-- Comments -->
      <div class="ea-input">
        <textarea></textarea>
      </div>
      <div class="ea-required">&nbsp;</div>
      <div class="ea-label">
        <span>Comments:</span>
      </div>
      <div class="ea-clear"></div>

      <!-- Buttons -->
      <div class="ea-buttons">
        <div class="buttons">
          <input class="button primary" type="submit" value="Send Early Alert">
          <a class="button" href="">Cancel</a>
        </div>
      </div>
      <div class="ea-required">&nbsp;</div>
      <div class="ea-label">&nbsp;</div>
      <div class="ea-clear"></div>
    
    </form>
    
  </div> <!-- end: portlet-body -->

</div> <!-- end: portlet -->
    	
<script type="text/javascript">
    var ${n} = {};
    ${n}.jQuery = jQuery.noConflict(true);

    ${n}.jQuery(function() {
        var $ = up.jQuery;

    });
</script>
