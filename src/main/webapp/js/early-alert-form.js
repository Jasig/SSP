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
 /**
*	01/29/2014	Jonathan Hart	TAPS 20140023	IRSC Added Comments field check to validate function
*	06/17/2014	Jonathan Hart	TAPS 20140039	Added Faculty Interventions to EA form
**/
"use strict";
var ssp = ssp || {};

(function($) {
	
    /*
     *  NB:  This is a temporary workaround for the 1.0 release (until we have a 
     *  chance to implement a better system).  The following Ids (uuids) *MUST* 
     *  match the values in src/main/webapp/app/util/Constants.js and the 
     *  corresponding rows in the database.
     */
    var CONSTANT_IDS = {
        OTHER_EARLY_ALERT_REASON_ID: 'b2d11335-5056-a51a-80ea-074f8fef94ea',
        OTHER_EARLY_ALERT_SUGGESTION_ID: 'b2d1120c-5056-a51a-80ea-c779a3109f8f'
    };

    var buildSelectors = function(container) {
        var rslt = {};
        $.each({
            errorsDiv:               '.errors',
            errorTemplate:           '.error-message-template',
            loadingMessage:          '.loading-message',
            alertForm:               '.alert-form',
            course:                  '.field-course',
            term:                    '.field-term',
            student:                 '.field-student',
            enrollmentStatus:        '.field-enrollment-status',
            netId:                   '.field-net-id',
            studentSchoolId:         '.field-student-school-id',
            studentEmail:            '.field-student-email',
            studentType:             '.field-student-type',
            assignedCounselor:       '.field-assigned-counselor',
            office:                  '.field-office',
            phone:                   '.field-phone',
            department:              '.field-department',
            emailCc:                 '.field-email-cc',
            campus:                  '.field-campus',
            reasons:                 '.field-reasons',
            reasonsId:               '.field-reasons-id',
            reasonsOtherHidden:      '.field-reasons-other-hidden',
            reasonsAddEdit:          '.reasons-add-edit',
            reasonsDialog:           '.reasons-dialog',
            suggestions:             '.field-suggestions',
            suggestionsId:           '.field-suggestions-id',
            suggestionsOtherHidden:  '.field-suggestions-other-hidden',
            suggestionsAddEdit:      '.suggestions-add-edit',
            suggestionsDialog:       '.suggestions-dialog',
			interventions:           '.field-interventions', //TAPS 20140039
            interventionsId:         '.field-interventions-id', //TAPS 20140039
            interventionsAddEdit:    '.interventions-add-edit', //TAPS 20140039
            interventionsDialog:     '.interventions-dialog', //TAPS 20140039
            comments:                '.field-comments',
            noticeDialog:            '.notice-dialog',
            buttonSend:              '.button-send'
        }, function(name, value) {
            rslt[name] = container + ' ' + value;
        });
        return rslt;
    };
    
    ssp.EarlyAlertForm = function(container, options) {

        var selectors = buildSelectors(container);

        /*
         * Person Data Function
         */
        var getPersonData = function(personId) {
            var rslt = [];
            $.ajax({
                url: options.urls.person.replace('STUDENTID', personId),
                async: false,
                dataType: 'json',
                error: function(jqXHR, textStatus, errorThrown) {
                    // Display the error
                    var response = $.parseJSON(jqXHR.responseText);
                    showError(jqXHR.status + ': ' + errorThrown, response.message);
                },
                success: function(data, textStatus, jqXHR) {
                    rslt = data;
                },
                type: 'GET'
            });
            return rslt;
        };
        
        /*
         * Campus Data Function
         */
        var getCampusData = function() {
        	var rslt = [];
            $.ajax({
                url: options.urls.campus,
                async: false,
                dataType: 'json',
                error: function(jqXHR, textStatus, errorThrown) {
                    // Display the error
                    var response = $.parseJSON(jqXHR.responseText);
                    showError(jqXHR.status + ': ' + errorThrown, response.message);
                },
                success: function(data, textStatus, jqXHR) {
                    rslt = data.rows;
                },
                type: 'GET'
            });
            return rslt;
        };

        /*
         * Referral Reason Data Function
         */
        var getReasonsData = function() {
            var rslt = [];
            $.ajax({
                url: options.urls.reasons,
                async: false,
                dataType: 'json',
                error: function(jqXHR, textStatus, errorThrown) {
                    // Display the error
                    var response = $.parseJSON(jqXHR.responseText);
                    showError(jqXHR.status + ': ' + errorThrown, response.message);
                },
                success: function(data, textStatus, jqXHR) {
                    rslt = data.rows;
                },
                type: 'GET'
            });
            return rslt;
        };

        /*
         * Suggestions Data Function
         */
        var getSuggestionsData = function() {
            var rslt = [];
            $.ajax({
                url: options.urls.suggestions,
                async: false,
                dataType: 'json',
                error: function(jqXHR, textStatus, errorThrown) {
                    // Display the error
                    var response = $.parseJSON(jqXHR.responseText);
                    showError(jqXHR.status + ': ' + errorThrown, response.message);
                },
                success: function(data, textStatus, jqXHR) {
                    rslt = data.rows;
                },
                type: 'GET'
            });
            return rslt;
        };
		
		//TAPS 20140039 BEGIN
		/*
         * Interventions Data Function
         */
        var getInterventionsData = function() {
            var rslt = [];
            $.ajax({
                url: options.urls.interventions,
                async: false,
                dataType: 'json',
                error: function(jqXHR, textStatus, errorThrown) {
                    // Display the error
                    var response = $.parseJSON(jqXHR.responseText);
                    showError(jqXHR.status + ': ' + errorThrown, response.message);
                },
                success: function(data, textStatus, jqXHR) {
                    rslt = data.rows;
                },
                type: 'GET'
            });
            return rslt;
        };
		//TAPS 20140039 END

        /*
         * Error Handling Functions
         */
        var showError = function(title, body) {
            var err = $(selectors.errorTemplate).clone();
            err.removeClass('error-message-template').addClass('error-message');
            err.find('.error-title').html(title);
            err.find('.error-body').html(body);
            err.appendTo(selectors.errorsDiv);
            err.slideDown(1000);
        };
        var clearErrors = function() {
            $(selectors.errorsDiv).html('');
        };

        /*
         * Validation Function
         */
        var validate = function() {
            var rslt = true;  // default
            if (!$(selectors.campus).val()) {
                rslt = false;
            	$(selectors.campus).addClass('invalid');
            } else {
                $(selectors.campus).removeClass('invalid');
            }
            if(!$(selectors.reasonsId) || $(selectors.reasonsId).length <= 0) {
                rslt = false;
            	$(selectors.reasonsAddEdit).addClass('invalid');
            } else {
                $(selectors.reasonsAddEdit).removeClass('invalid');
            }
            var emailCc = $(selectors.emailCc).val().trim();
            // True regex email validation is a pipe-dream. Just a sanity
            // check here.
            if ('' !== emailCc && !(/.+@.+/.test(emailCc))) {
                rslt = false;
                $(selectors.emailCc).addClass('invalid');
            } else {
                $(selectors.emailCc).removeClass('invalid');
            }
			
			// TAPS 20140023 - 01/29/2014 Check to see if user entered comments
			var commentsFld = $(selectors.comments).val().trim();
			if ('' == commentsFld || !$(selectors.comments).val()) {
				rslt = false;
				$(selectors.comments).addClass('invalid');
			} else {
				$(selectors.comments).removeClass('invalid');
			}
			
			//TAPS 20140039 BEGIN Add Validation for Faculty Interventions
			if(!$(selectors.interventionsId) || $(selectors.interventionsId).length <= 0) {
                rslt = false;
            	$(selectors.interventionsAddEdit).addClass('invalid');
            } else {
                $(selectors.interventionsAddEdit).removeClass('invalid');
            }
			//TAPS 20140039 END
			
            return rslt;
        }

        /*
         * Submit Function
         */
        var submitEarlyAlert = function(sendNotice) {

            // Start with a clean slate...
            clearErrors();

            // Marshal the POST data
            var postData = {
                courseName: options.parameters.courseName,
                courseTitle: options.parameters.courseTitle,
                courseTermCode: options.parameters.term,
                emailCC: $(selectors.emailCc).val(),
                campusId: $(selectors.campus).val(),
                earlyAlertReasonIds: [], // Set below...
                earlyAlertReasonOtherDescription: $(selectors.reasonsOtherHidden).val(),
                earlyAlertSuggestionIds: [],  // Set below...
                earlyAlertSuggestionOtherDescription: $(selectors.suggestionsOtherHidden).val(),
				earlyAlertInterventionIds: [],  //TAPS 20140039
                comment: $(selectors.comments).val(),
                sendEmailToStudent: sendNotice
            };
            $(selectors.reasonsId).each(function() {
            	postData.earlyAlertReasonIds.push( $(this).val() );
            });
            $(selectors.suggestionsId).each(function() {
            	postData.earlyAlertSuggestionIds.push( $(this).val() );
            });
			//TAPS 20140039 BEGIN
            $(selectors.interventionsId).each(function() {
            	postData.earlyAlertInterventionIds.push( $(this).val() );
            });
			//TAPS 20140039 END
            
            
            
            // Submit the alert
            $.ajax({
                url: options.urls.submit.replace('STUDENTID', options.parameters.studentId),
                async: false,
                contentType: 'application/json',
                data: JSON.stringify(postData),
                // processData: false,
                dataType: 'json',
                error: function(jqXHR, textStatus, errorThrown) {
                    // Display the error
                    var response = $.parseJSON(jqXHR.responseText);
                    showError(jqXHR.status + ': ' + errorThrown, response.message);
                },
                success: function(data, textStatus, jqXHR) {
                	// Return to the roster screen, with a message
                    var url = options.urls.done.replace('STUDENTNAME', escape(studentName));
                    url = url.replace('CONFIRMED_ID', escape(data.id));
                    window.location = url;
                },
                type: 'POST'
            });

        };

        /*
         * Core Variables...
         */

        var student = getPersonData(options.parameters.studentId);
        var campuses = getCampusData();
        var reasons = getReasonsData();
        var suggestions = getSuggestionsData();
		var interventions = getInterventionsData(); //TAPS 20140039
        
        // studentName
        var studentName = student.firstName + ' '
                + (student.middleInitial ? ' ' + student.middleInitial + ' ' : ' ')
                + student.lastName;

        /*
         * Populate the fields...
         */

        // course
        $(selectors.course).text(options.parameters.courseName + ' - ' + options.parameters.courseTitle);

        // term
        $(selectors.term).text(options.parameters.term);

        // student
        $(selectors.student).text(studentName);

        // enrollment status
        $(selectors.enrollmentStatus).text(ssp.getStatusCodeName(options.parameters.enrollmentStatusCode, options));

        // netId
        $(selectors.netId).text(student.username);
        
        // schoolId
        $(selectors.studentSchoolId).text(student.schoolId);

        // studentEmail
        $(selectors.studentEmail).text(student.primaryEmailAddress);

        // studentType
        $(selectors.studentType).text(student.studentType && student.studentType.name);

        // assignedCounselor
        $(selectors.assignedCounselor).text(student.coach && student.coach.lastName + ', ' + student.coach.firstName);

        // office
        // phone
        // department

        // campus
        $.each(campuses, function(index, value) {
        	var html = '<option value="' + value.id + '">' + value.name + '</option>';
            $(selectors.campus).append(html);
        });

        // reason
        $.each(reasons, function(index, value) {
        	var html = '<li><input type="checkbox" value="' + value.id + '">' + value.name;
        	if (value.id === CONSTANT_IDS.OTHER_EARLY_ALERT_REASON_ID) {
                html += '<br/><input type="text" name="earlyAlertReasonOtherDescription" value="" style="width:15.5em" placeholder="Type a reason for the referral..." />';
            }
        	html += '</li>';
            $(selectors.reasonsDialog).find('ul').append(html);
        });
        var reasonsDlgOptions = {
            autoOpen: false,
            buttons: {
                'OK': function() {
                    $(selectors.reasons).html('');  // Clear
                    $(this).find('li').each(function() {
                        var chk = $(this).find('input');
                        if (chk.attr('checked')) {
                        	var html = '<input type="hidden" class="field-reasons-id" value="' + chk.val() + '" />';
                            html += (chk.val() === CONSTANT_IDS.OTHER_EARLY_ALERT_REASON_ID) 
                                ? $(this).find(':text').val() + '<input type="hidden" class="field-reasons-other-hidden" value="' + $(this).find(':text').val() + '" />'
                                : $(this).text();
                            $(selectors.reasons).append('<li>' + html + '</li>');
                        }
                    });
                    $(this).dialog('close');
                },
                'Cancel': function() { $(this).dialog('close'); },
            },
            modal: true,
            title: 'Edit Referral Reasons' //TAPS 20140039 Corrected wording to match field
        };
        var reasonsDlg = $(selectors.reasonsDialog).dialog(reasonsDlgOptions);
        $(selectors.reasonsAddEdit).click(function() {
            reasonsDlg.dialog('open');
        });
        

        // suggestions
        $.each(suggestions, function(index, value) {
        	var html = '<li><input type="checkbox" value="' + value.id + '">' + value.name;
        	if (value.id === CONSTANT_IDS.OTHER_EARLY_ALERT_SUGGESTION_ID) {
                html += '<input type="text" name="earlyAlertSuggestionOtherDescription" value="" placeholder="Type a suggestion..." />';
            }
        	html += '</li>';
            $(selectors.suggestionsDialog).find('ul').append(html);
        });
        var suggestionsDlgOptions = {
            autoOpen: false,
            buttons: {
                'OK': function() {
                    $(selectors.suggestions).html('');  // Clear
                    $(this).find('li').each(function() {
                        var chk = $(this).find('input');
                        if (chk.attr('checked')) {
                        	var html = '<input type="hidden" class="field-suggestions-id" value="' + chk.val() + '" />';
                            html += (chk.val() === CONSTANT_IDS.OTHER_EARLY_ALERT_SUGGESTION_ID) 
                                ? $(this).find(':text').val() + '<input type="hidden" class="field-suggestions-other-hidden" value="' + $(this).find(':text').val() + '" />'
                                : $(this).text();
                            $(selectors.suggestions).append('<li>' + html + '</li>');
                        }
                    });
                    $(this).dialog('close');
                },
                'Cancel': function() { $(this).dialog('close'); },
            },
            modal: true,
            title: 'Edit Faculty Suggestions'
        };
        var suggestionsDlg = $(selectors.suggestionsDialog).dialog(suggestionsDlgOptions);
        $(selectors.suggestionsAddEdit).click(function() {
            suggestionsDlg.dialog('open');
        });
		
		//TAPS 20140039 BEGIN
		// interventions
        $.each(interventions, function(index, value) {
        	var html = '<li><input type="checkbox" value="' + value.id + '">' + value.name + '</li>';
            $(selectors.interventionsDialog).find('ul').append(html);
        });
        var interventionsDlgOptions = {
            autoOpen: false,
            buttons: {
                'OK': function() {
                    $(selectors.interventions).html('');  // Clear
                    $(this).find('li').each(function() {
                        var chk = $(this).find('input');
                        if (chk.attr('checked')) {
                        	var html = '<input type="hidden" class="field-interventions-id" value="' + chk.val() + '" />';
                            html += $(this).text();
                            $(selectors.interventions).append('<li>' + html + '</li>');
                        }
                    });
                    $(this).dialog('close');
                },
                'Cancel': function() { $(this).dialog('close'); },
            },
            modal: true,
            title: 'Edit Faculty Interventions'
        };
        var interventionsDlg = $(selectors.interventionsDialog).dialog(interventionsDlgOptions);
        $(selectors.interventionsAddEdit).click(function() {
            interventionsDlg.dialog('open');
        });
		//TAPS 20140039 END
        
        // send button
        var noticeDlgOptions = {
            autoOpen: false,
            buttons: {
                'Yes': function() {
                    noticeDlg.dialog('close');
                    submitEarlyAlert(true);
                },
                'No': function() {
                    noticeDlg.dialog('close');
                    submitEarlyAlert(false);
                }
            },
            modal: true,
            title: 'Send EarlyAlert'
        };

        var noticeDlg = $(selectors.noticeDialog).dialog(noticeDlgOptions);
        $(selectors.buttonSend).click(function() {
            clearErrors();
            if (validate()) {
                noticeDlg.dialog('open');
            } else {
                showError('Validation Error',
                    'Could not submit the EarlyAlert because not enough information was provided or some form fields contained invalid data. Please correct the highlighted fields.');
            }
        });

        /*
         * The interface is ready to display...
         */

        $(selectors.loadingMessage).slideUp(500);
        $(selectors.alertForm).slideDown(1000);

    };

})(jQuery);