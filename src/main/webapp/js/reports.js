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
		
		var loadTermInput = function(url, container) {
			$.getJSON(url, function(data) {
				$.each(data.rows, function(i, row) {
					addSelectItem(row.code, row.name, container);
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
			loadGroupInput("/ssp/api/1/reference/earlyAlertReferral/", that
					.locate('earlyAlertReferralGroup'));
			loadGroupInput("/ssp/api/1/reference/campus/", that
					.locate('campusGroup'));
			loadGroupInput("/ssp/api/1/reference/earlyAlertOutcome/", that
							.locate('earlyAlertOutcomeGroup'));		
			loadTermInput("/ssp/api/1/reference/term/", that
					.locate('termGroup'));
			// 1000 limit is max allowed by server side
			loadCoachInput("/ssp/api/1/person/coach/?sort=lastName&page=1&start=0&limit=1000", that
					.locate('assignedCounselorGroup'));			


			loadTextForm(
					"/ssp/api/1/reference/confidentialityDisclosureAgreement/",
					that.locate('confidentialityAgreementFormContent'));
		}
		
		var initializeDateRange = function(){
			var button = $('.switch-date-range-type').filter(':visible');
			
			if(button.length == 0)
				return;
			var section = button.parent().parent().parent();
			var term = section.find('.ea-term');
			var dateRange = section.find('.ea-date-range');
			var value = button.val();
			if(value == 'setbyrange'){
				term.show();
				dateRange.hide();
				
				button.text('use date range');
			} else {
				term.hide();
				dateRange.show();
				button.text('use term');
			}
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
			
			var switchDateRangeType = that.locate('switchDateRangeType');
			$(switchDateRangeType).click(function(event) {
				var value = $(event.target).val();
				var button = $(event.target);
				var section = button.parent().parent().parent();
				var term = section.find('.ea-term');
				var dateRange = section.find('.ea-date-range');
				if(value == 'setbyrange'){
					term.find('[name="termCode"]').val("");
					term.hide();
					dateRange.show();
					button.val('setbyterm');
					button.text('use term');
				} else {
					term.show();
					dateRange.find('[name="createDateFrom"]').val("");
					dateRange.find('[name="createDateTo"]').val("");
					dateRange.hide();
					button.val('setbyrange');
					button.text('use date range');
				}
				return false;
			});
			
		});

		// construct the new component
		var that = fluid.initView('ssp.ReportSelector', container, options);

		var reportsSelectChange = function() {
			var reportsSelect = that.locate('reportsSelect');
			loadReportForm(reportsSelect.val());
			initializeDateRange();
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
                .append('<option value="caseLoadForm">Current Caseload Statuses Report</option>');		
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
		reportsSelect
				.append('<option value="" disabled >- Early Alert Reports -</option>');
		reportsSelect
				.append('<option value="earlyAlertCaseCounts">Early Alert Case Counts Report</option>');
		
		reportsSelect
		.append('<option value="earlyAlertStudent">Early Alert Student Report</option>');
		
		reportsSelect
		.append('<option value="earlyAlertStudentReferral">Early Alert Student Referral Report</option>');
		
		reportsSelect
		.append('<option value="earlyAlertStudentProgress">Early Alert Student Progress Report</option>');
		
		reportsSelect
		.append('<option value="earlyAlertStudentOutreach">Early Alert Student Outreach Report</option>');
		
		reportsSelect
		.append('<option value="earlyAlertStudentOutcome">Early Alert Student Outcome Report</option>');
		
		//TODO Controller Generated but DAO's incomplete at this time.
		//reportsSelect
		//.append('<option value="earlyAlertClass">Early Alert Class Report</option>');


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
							earlyAlertCaseCounts : '.early-alert-case-counts-report',
							earlyAlertStudent : '.early-alert-student-report',
							earlyAlertStudentReferral : '.early-alert-student-referral-report',
							confidentialityAgreementFormContent : '.confidentiality-agreement-form-content',
							earlyAlertStudentProgress : '.early-alert-student-progress-report',
							earlyAlertStudentOutreach : '.early-alert-student-outreach-report',
							earlyAlertStudentOutcome : '.early-alert-student-outcome-report',
							earlyAlertClass : '.early-alert-class-report',
							programStatusGroup : '.input-program-status-group',
							studentTypeGroup : '.input-student-type-group',
							specialServiceGroup : '.input-special-service-group',
							referralSourceGroup : '.input-referral-source-group',
							earlyAlertReferralGroup: '.input-early-alert-referral-group',
							assignedCounselorGroup : '.input-assigned-counselor-group',
							campusGroup: '.input-campus-group',
							termGroup: '.input-term-group',
							earlyAlertOutcomeGroup: '.input-early-alert-outcome-group',
							calendarType : '.input-calendar-type',
							switchDateRangeType : '.switch-date-range-type',
							termRange : '.ea-term',
							dateRange : '.ea-date-range',
							hideableform : '.hideable-form',
							printConfForm : '.print-conf-form',
							loadingMessage: '.loading-message',
						}
					});

})(jQuery, fluid);
