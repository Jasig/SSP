/*
 * Licensed to Apereo under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Apereo licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
"use strict";
var ssp = ssp || {};

(function($, fluid) {
	ssp.ReportSelector = function(container, options) {
		var easeRate = 400;
		var requests = 1;
		var timer = setInterval(checkIfReady, 100);

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
			requests++;
			$.getJSON(url, function(data) {
				requests--;
				
				$.each(data.rows, function(i, row) {
					addSelectItem(row.id, row.name, container);
				});

			}).error(function(jqXHR, textStatus, errorThrown) {
				handerServerError(jqXHR + " " + textStatus + " " + errorThrown);
				requests--;
			});
		}
		
		var loadCoachInput = function(url, container) {
			requests++;
			$.getJSON(url, function(data) {
				requests--;
				
				$.each(data.rows, function(i, row) {
					addSelectItem(row.id, row.lastName + ", " + row.firstName, container);
				});

			}).error(function(jqXHR, textStatus, errorThrown) {
				handerServerError(jqXHR + " " + textStatus + " " + errorThrown);
				requests--;
			});
		}
		
		var loadTermInput = function(url, container) {
			requests++;
			$.getJSON(url, function(data) {
				requests--;
				
				$.each(data.rows, function(i, row) {
					addSelectItem(row.code, row.name, container);
				});

			}).error(function(jqXHR, textStatus, errorThrown) {
				handerServerError(jqXHR + " " + textStatus + " " + errorThrown);
				requests--;
			});
		}	
		
		var loadConfigInput = function(url, container) {
			requests++;
			$.getJSON(url, function(data) {
				requests--;
				
				var values = $.parseJSON(data.value);
				for(var index in values){
					addSelectItem(index, values[index], container);
				}

			}).error(function(jqXHR, textStatus, errorThrown) {
				handerServerError(jqXHR + " " + textStatus + " " + errorThrown);
				requests--;
			});
		}
		
		var loadReportYearInput = function(url, container) {
			requests++;
			$.getJSON(url, function(data) {
				var values = new Array();
				requests--;
				
				$.each(data.rows, function(i, row) {
					
					if($.inArray(row.reportYear, values) < 0){
						values.push(row.reportYear);
						addSelectItem(row.reportYear, row.reportYear, container);
					}
				});

			}).error(function(jqXHR, textStatus, errorThrown) {
				var headers = jqXHR.getAllResponseHeaders();
				alert(headers + " " + textStatus + " " + errorThrown);
				requests--;
			});
		}
		
		var loadHomeDepartmentInput = function(url, container) {
			requests++;
			$.getJSON(url, function(data) {
				requests--;
				
				$.each(data, function(i, datum) {
					addSelectItem(datum, datum, container);
				});
			}).error(function(jqXHR, textStatus, errorThrown) {
				handerServerError(jqXHR + " " + textStatus + " " + errorThrown);
				requests--;
			});
		}

		var loadTextForm = function(url, container) {
			requests++;
			$.getJSON(url, function(data) {
				requests--;
                if (data && data.name && data.text) {
				    $(container).html("<h3>" + data.name + "</h3>" + "<p>" + data.text + "</p>");
                }

			}).error(function(jqXHR, textStatus, errorThrown) {
				handerServerError(jqXHR + " " + textStatus + " " + errorThrown);
				requests--;
			});
		}
		
		var handerServerError = function(jqXHR, textStatus, errorThrown){
			window.console&&console.log("Error loading references.");
		}
		
		var loadCourseNumberInput = function(url, container) {
			requests++;
			$.getJSON(url, function(data) {
				requests--;
				container.html('');
				addSelectItem("", "Not Used", container);
				var values = [];
				if(!data || data.length <= 0)
					return;
				data.forEach(function(row) {
					values[row.number] = row.number;
				});
				for(var index in values) 
					addSelectItem(index, index, container);

			}).error(function(jqXHR, textStatus, errorThrown) {
				handerServerError(jqXHR + " " + textStatus + " " + errorThrown);
				requests--;
			});
		}
		
		var loadFormattedCourseInput = function(url, container) {
			requests++;
			$.getJSON(url, function(data) {
				requests--;
				container.html('');
				addSelectItem("", "Not Used", container);
				var values = [];
				if(!data || data.length <= 0)
					return;
				data.forEach(function(row) {
					values[row.formattedCourse] = row.formattedCourse;
				});
				for(var index in values) 
					addSelectItem(index, index, container);

			}).error(function(jqXHR, textStatus, errorThrown) {
				handerServerError(jqXHR + " " + textStatus + " " + errorThrown);
				requests--;
			});
		}
		
		var loadSubjectAbbreviationInput = function(url, container) {
			requests++;
			$.getJSON(url, function(data) {
				requests--;
				container.html('');
				addSelectItem("", "Not Used", container);
				var values = [];
				if(!data || data.length <= 0)
					return;
				data.forEach(function(row) {
					values[row.subjectAbbreviation] = row.subjectAbbreviation;
				});
				for(var index in values) 
					addSelectItem(index, index, container);
			}).error(function(jqXHR, textStatus, errorThrown) {
				handerServerError(jqXHR + " " + textStatus + " " + errorThrown);
				requests--;
			});
		}

		// load Forms
		var loadForm = function() {
			
			loadGroupInput("/ssp/api/1/reference/programStatus/?sort=name&start=0&limit=-1&sortDirection=ASC", that
					.locate('programStatusGroup'));
			loadGroupInput("/ssp/api/1/reference/studentType/?sort=name&start=0&limit=-1&sortDirection=ASC", that
					.locate('studentTypeGroup'));
			loadGroupInput("/ssp/api/1/reference/campus/?sort=name&start=0&limit=-1&sortDirection=ASC", that
					.locate('homeCampusGroup'));
			loadGroupInput("/ssp/api/1/reference/specialServiceGroup/?sort=name&start=0&limit=-1&sortDirection=ASC", that
					.locate('specialServiceGroup'));
			loadGroupInput("/ssp/api/1/reference/referralSource/?sort=name&start=0&limit=-1&sortDirection=ASC", that
					.locate('referralSourceGroup'));
			loadGroupInput("/ssp/api/1/reference/earlyAlertReferral/?sort=name&start=0&limit=-1&sortDirection=ASC", that
					.locate('earlyAlertReferralGroup'));
			loadGroupInput("/ssp/api/1/reference/campus/?sort=name&start=0&limit=-1&sortDirection=ASC", that
					.locate('campusGroup'));
			loadGroupInput("/ssp/api/1/reference/earlyAlertOutcome/?sort=name&start=0&limit=-1&sortDirection=ASC", that
							.locate('earlyAlertOutcomeGroup'));	
			
			loadGroupInput("/ssp/api/1/reference/serviceReason/?sort=name&start=0&limit=-1&sortDirection=ASC", that
					.locate('serviceReasonGroup'));	
			
			loadGroupInput("/ssp/api/1/reference/disabilityStatus/?sort=name&start=0&limit=-1&sortDirection=ASC", that
					.locate('disabilityStatusGroup'));	
			
			loadGroupInput("/ssp/api/1/reference/disabilityType/?sort=name&start=0&limit=-1&sortDirection=ASC", that
					.locate('disabilityTypeGroup'));
			
			loadGroupInput("/ssp/api/1/reference/journalStepDetail/?sort=name&start=0&limit=-1&sortDirection=ASC", that
					.locate('journalStepDetailGroup'));
			
			loadTermInput("/ssp/api/1/reference/term/?sort=startDate&start=0&limit=-1&sortDirection=DESC", that
					.locate('termGroup'));
			
			loadReportYearInput("/ssp/api/1/reference/term/?sort=startDate&start=0&limit=-1&sortDirection=DESC", that
					.locate('reportYearGroup'));
			
			loadConfigInput("/ssp/api/1/reference/config/?name=status_code_mappings", that
					.locate('rosterStatusCodeGroup'));
			
			// 1000 limit is max allowed by server side
			loadCoachInput("/ssp/api/1/person/coach/?sort=lastName&page=1&start=0&limit=-1&sortDirection=ASC", that
					.locate('assignedCounselorGroup'));
			
			loadCoachInput("/ssp/api/1/person/coach/?sort=lastName&page=1&start=0&limit=-1&sortDirection=ASC", that
					.locate('watcherGroup'));
			loadHomeDepartmentInput("/ssp/api/1/personStaffDetails/homeDepartments/?sort=name&start=0&limit=-1&sortDirection=ASC", that
					.locate('homeDepartmentGroup'))
			loadTextForm(
					"/ssp/api/1/reference/confidentialityDisclosureAgreement/getlivecda",
					that.locate('confidentialityAgreementFormContent'));
			
			loadSubjectAbbreviationInput("/ssp/api/1//reference/course/search?sort=name&start=0&limit=-1&sortDirection=ASC", that
					.locate('subjectAbbreviationGroup'));

			loadGroupInput("/ssp/api/1/reference/journalSource/?sort=name&start=0&limit=-1&sortDirection=ASC", that
					.locate('journalSourceGroup'));

			requests--;
		}
		
		function checkIfReady() {
			if (requests === 0) {
				clearInterval(timer);
				loadPage();
			}
		}

		var initializeDateRange = function() {
			var button = $('.switch-date-range-type').filter(':visible');

			if (button.length == 0) {
				return;
			}
			var section = button.closest(".ea-time-span");
			var term = section.find('.ea-term');
			var dateRange = section.find('.ea-date-range');
			var value = button.val();
			if (value == 'setbyrange') {
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
				var section = button.closest(".ea-time-span");
				var term = section.find('.ea-term');
				var dateRange = section.find('.ea-date-range');
				if (value == 'setbyrange') {
					term.find('[name="termCode"]').val("");
					term.find('[name="alertTermCode"]').val("");
					term.hide();
					dateRange.show();
					button.val('setbyterm');
					button.text('use term');
				} else {
					term.show();
					dateRange.find('[name="createDateFrom"]').val("");
					dateRange.find('[name="createDateTo"]').val("");
					dateRange.find('[name="alertCreateDateFrom"]').val("");
					dateRange.find('[name="alertCreateDateTo"]').val("");
					dateRange.hide();
					button.val('setbyrange');
					button.text('use date range');
				}
				return false;
			});
			$('select[multiple="multiple"]').val("");
			$('select[multiple="multiple"]').change(function(event){
				var values = $(event.target).val();
				var index = $.inArray("", values);
				if(index >= 0) {
					$(event.target).find('option').not(":first-child").prop('selected', false);
				}
			});
			
			$('select[class="input-subject-abbreviation-group"]').change(function(event){
				var value = $(event.target).val();
				if (value && value.length > 0) {
					loadCourseNumberInput("/ssp/api/1//reference/course/search?subjectAbbreviation=" + value, that
							.locate('courseNumberGroup'));
				} else {
					var container = that.locate('courseNumberGroup');
					container.html('');
					addSelectItem("", "Not Used", container);
				}
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
		});
		
		var getReportsList = function() {
        	var rslt = [];
            $.ajax({
                url: '/ssp/api/1/report',
                async: false,
                dataType: 'json',
                error: function(jqXHR, textStatus, errorThrown) {
                    handerServerError(jqXHR + " " + textStatus + " " + errorThrown);
                },
                success: function(data, textStatus, jqXHR) {
                    rslt = data.reports;
                },
                type: 'GET'
            });
            return rslt;
        };
        	
		function loadPage(){
		//add here to include in drop down
			var reports = getReportsList();
			
			if (reports && reports.length != 0) {
	            $(reports).each(function(index, report) {
	            	if(report.first)
	            		reportsSelect.append('<option value="' + report.first  + '">' + report.second + '</option>');
	            	else
	            		reportsSelect.append('<option value="" disabled >' + report.second + '</option>');
	            });
			}
			reportsSelectChange();
			loadingMessage.slideUp(easeRate);
		}
		
		//TODO Controller Generated but DAO's incomplete at this time.
		//reportsSelect
		//.append('<option value="earlyAlertClass">Early Alert Class Report</option>');

		reportsSelect.change(reportsSelectChange);
	}

	// defaults
	fluid
		.defaults(
			'ssp.ReportSelector', {
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
					earlyAlertCourseCounts : '.early-alert-course-counts-report',
					earlyAlertReasonCounts : '.early-alert-reason-counts-report',
					earlyAlertStudent : '.early-alert-student-report',
					earlyAlertStudentReferral : '.early-alert-student-referral-report',
					confidentialityAgreementFormContent : '.confidentiality-agreement-form-content',
					earlyAlertStudentProgress : '.early-alert-student-progress-report',
					earlyAlertStudentOutreach : '.early-alert-student-outreach-report',
					earlyAlertStudentOutcome : '.early-alert-student-outcome-report',
					studentChallenges : '.student-challenges-report',
					journalSessionDetail : '.journal-session-detail-report',
					journalCaseNotes : '.journal-case-notes-by-student-report',
					earlyAlertClass : '.early-alert-class-report',
					numberCoursesInPlan : '.number-courses-in-plan-report',
					numberPlansByOwner : '.number-plans-by-owner-report',
					numberStudentsByStatus : '.number-students-by-status-report',
					disabilityServices : '.disability-services-report',
					programStatusGroup : '.input-program-status-group',
					studentTypeGroup : '.input-student-type-group',
					homeCampusGroup : '.input-home-campus-group',
					specialServiceGroup : '.input-special-service-group',
					serviceReasonGroup: '.input-service-reason-group',
					specialServiceCourseReport: '.special-service-course-report',
					referralSourceGroup : '.input-referral-source-group',
					earlyAlertReferralGroup: '.input-early-alert-referral-group',
					assignedCounselorGroup : '.input-assigned-counselor-group',
					watcherGroup : '.input-watcher-group',
					journalStepDetailGroup : '.input-journal-step-detail-group',
					campusGroup: '.input-campus-group',
					termGroup: '.input-term-group',
					courseNumberGroup: '.input-course-number-group',
					formattedCourseGroup: '.input-formatted-course-group',
					subjectAbbreviationGroup: '.input-subject-abbreviation-group',
					reportYearGroup: '.input-report-year-group',
					earlyAlertOutcomeGroup: '.input-early-alert-outcome-group',
					disabilityStatusGroup: '.input-disability-status-group',
					disabilityTypeGroup: '.input-disability-type-group',
					rosterStatusCodeGroup: '.input-roster-status-code-group',
					homeDepartmentGroup: '.input-home-department-group',
					calendarType : '.input-calendar-type',
					switchDateRangeType : '.switch-date-range-type',
					termRange : '.ea-term',
					dateRange : '.ea-date-range',
					hideableform : '.hideable-form',
					printConfForm : '.print-conf-form',
					loadingMessage: '.loading-message',
					journalSourceGroup : '.input-journal-source-group'
					}
				});
})(jQuery, fluid);