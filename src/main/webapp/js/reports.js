"use strict";
var ssp = ssp || {};

(function($, fluid) {
	

	ssp.ReportSelector = function(container, options) {

		//load the report form
		var loadReportForm = function(url){
			var reportFormBody = that.locate('reportFormBody');			
			reportFormBody.load(url, function (responseText, textStatus, XMLHttpRequest) {
		    		if (textStatus == "success") {
		 			var newHTML = reportFormBody.html();		
	    			}
	    			if (textStatus == "error") {
			 		alert('there is an error opening this form');
	    			}
			});	
		}

		// construct the new component
		var that = fluid.initView('ssp.ReportSelector', container, options);

		var reportsSelectChange = function() {
			var reportsSelectt = that.locate('reportsSelect');
			//alert(reportsSelectt.val());
			loadReportForm(reportsSelect.val());
		};
	
		// Courses Select
		var reportsSelect = that.locate('reportsSelect');
		reportsSelect.append('<option value="/ssp/forms/AddressLabel.jsp">Address Labels Report</option>');
		reportsSelect.append('<option name="test1" value="/ssp/forms/SpecialServices.jsp">Special Services Report</option>');
		reportsSelect.change(reportsSelectChange);
	}

	// defaults
        fluid.defaults('ssp.ReportSelector', {
	    	selectors: {
		    reportsSelect: '.reports-select',
		    reportFormBody: '.reports-form-body'     
		}
	});
	
})(jQuery, fluid);
