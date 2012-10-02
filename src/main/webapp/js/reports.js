/*
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
"use strict";
var ssp = ssp || {};

(function($, fluid) {
	ssp.ReportSelector = function(container, options) {
		var easeRate = 400;

		// load the report form
		var loadReportForm = function(containerId) {
			// hide all forms
			var hideableForms = that.locate('hideableform');
			$(hideableForms).filter(":visible").toggle(easeRate);

			// show the new form
			var container = that.locate(containerId);
			$(container).toggle(easeRate);
		}

		var loadGroupInput = function(url, container) {
			$.getJSON(url, function(data) {
				$.each(data.rows, function(i, row) {
					addSelectItem(row.id, row.name, container);
				});

			}).error(function(jqXHR, textStatus, errorThrown) {
				alert(jqXHR + " " + textStatus + " " + errorThrown);
			});
		}
		
		var loadCoachInput = function(url, container) {
			$.getJSON(url, function(data) {
				$.each(data.rows, function(i, row) {
					addSelectItem(row.id, row.lastName + ", " + row.firstName, container);
				});

			}).error(function(jqXHR, textStatus, errorThrown) {
				alert(jqXHR + " " + textStatus + " " + errorThrown);
			});
		}		

		var loadTextForm = function(url, container) {
			$.getJSON(url, function(data) {
				$.each(data.rows, function(i, row) {
					$.each(data.rows, function(i, row) {
						$(container).html(row.text);
					});
				});

			}).error(function(jqXHR, textStatus, errorThrown) {
				alert(jqXHR + " " + textStatus + " " + errorThrown);
			});
		}

		// load Forms
		var loadForm = function() {
			
			loadGroupInput("/ssp/api/1/reference/programStatus/", that
					.locate('programStatusGroup'));
			loadGroupInput("/ssp/api/1/reference/studentType/", that
					.locate('studentTypeGroup'));
			loadGroupInput("/ssp/api/1/reference/specialServiceGroup/", that
					.locate('specialServiceGroup'));
			loadGroupInput("/ssp/api/1/reference/referralSource/", that
					.locate('referralSourceGroup'));
			loadCoachInput("/ssp/api/1/person/coach/", that
					.locate('assignedCounselorGroup'));			


			loadTextForm(
					"/ssp/api/1/reference/confidentialityDisclosureAgreement/",
					that.locate('confidentialityAgreementFormContent'));
		}

		function addSelectItem(uid, name, container) {
			var inputs = container.find('input');
			var id = inputs.length + 1;

			var html = '<option value="' + uid + '">' + name + '</option>';
			container.append($(html));
		}

		$(document).ready(function() {
			var calendarType = that.locate('calendarType');
			$(calendarType).datepicker({
				showOn : 'button',
				buttonImageOnly : true,
				buttonImage : '/ssp/images/calendar.gif'
			});
		});

		// construct the new component
		var that = fluid.initView('ssp.ReportSelector', container, options);

		var reportsSelectChange = function() {
			var reportsSelect = that.locate('reportsSelect');
			loadReportForm(reportsSelect.val());
		};

		var printConfForm = that.locate('printConfForm');
		$(printConfForm).attr("href", "javascript:void( 0 )").click(
				function() {
					var confidentialityAgreementFormContent = that
							.locate('confidentialityAgreementFormContent');
					// Print the DIV.
					$(confidentialityAgreementFormContent).print();

					// Cancel click event.
					return (false);
				});

		var loadingMessage = that.locate('loadingMessage');
		var reportsSelect = that.locate('reportsSelect');
				
    		loadingMessage.slideDown(easeRate, function() {
    			loadForm();	
        		loadingMessage.slideUp(easeRate);
			reportsSelectChange();
    		});
        	
		
		//add here to include in drop down
		reportsSelect
				.append('<option value="generalStudentForm">General Student Report</option>');
		reportsSelect
                .append('<option value="caseLoadForm">Caseload Report</option>');		
		reportsSelect
                .append('<option value="caseloadActivityReport">Caseload Activity Report</option>');			
		reportsSelect
		        .append('<option value="confidentialityAgreementForm">Confidentiality Agreement</option>');		
		reportsSelect
		        .append('<option value="counselingRefGuideForm">Counseling Reference Guide</option>');		
		reportsSelect
				.append('<option value="counselorCaseManagementReport">Counselor Case Management Report</option>');
		reportsSelect
				.append('<option value="specialServicesForm">Special Services Report</option>');
		
		reportsSelect.change(reportsSelectChange);
	}

	// defaults
	fluid
			.defaults(
					'ssp.ReportSelector',
					{
						selectors : {
							reportsSelect : '.reports-select',
							reportFormBody : '.reports-form-body',
							generalStudentForm : '.generalStudent-form',
							specialServicesForm : '.special-services-form',
							counselingRefGuideForm : '.counseling-reference-guide',							
							counselorCaseManagementReport : '.counselor-case-management-report',
							caseLoadForm : '.caseLoad-form',
							confidentialityAgreementForm : '.confidentiality-agreement-form',
							caseloadActivityReport : '.caseload-activity-report',
							confidentialityAgreementFormContent : '.confidentiality-agreement-form-content',
							programStatusGroup : '.input-program-status-group',
							studentTypeGroup : '.input-student-type-group',
							specialServiceGroup : '.input-special-service-group',
							referralSourceGroup : '.input-referral-source-group',
							assignedCounselorGroup : '.input-assigned-counselor-group',
							calendarType : '.input-calendar-type',
							hideableform : '.hideable-form',
							printConfForm : '.print-conf-form',
							loadingMessage: '.loading-message',
						}
					});

})(jQuery, fluid);
