"use strict";
var ssp = ssp || {};

(function($) {

	var getPersonData = function(id) {
		// TODO:  Replace mock data!
		return {
            "id" : "58ba5ee3-734e-4ae9-b9c5-943774b4de41",
			 "createdDate" : 1331269200000,
			 "createdBy" :
			    {"id" : "91f46e39-cea8-422b-b215-00f6bcf5d280",
			     "firstName" : "John",
			     "lastName" : "Doe"}, 
			 "modifiedDate" : 1331269200000,
			 "modifiedBy" :
			    {"id" : "91f46e39-cea8-422b-b215-00f6bcf5d280",
			     "firstName" : "John",
			     "lastName" : "Doe"}, 
			 "objectStatus" : "ACTIVE",
			 "firstName" : "Sally",
			 "middleInitial" : null,
			 "lastName" : "Student",
			 "birthDate" : null,
			 "primaryEmailAddress" : "sally.student@university.edu",
			 "secondaryEmailAddress" : null,
			 "username" : "sally.student",
			 "userId" : null,
			 "homePhone" : "123-456-7890",
			 "workPhone" : null,
			 "cellPhone" : null,
			 "addressLine1" : "444 West Third Street",
			 "addressLine2" : null,
			 "city" : "Dayton",
			 "state" : "OH",
			 "zipCode" : "45402",
			 "photoUrl" : null,
			 "schoolId" : null,
			 "enabled" : false,
			 "coach" :
			    {"id" : "91f46e39-cea8-422b-b215-00f6bcf5d280",
			     "firstName" : "Amy",
			     "lastName" : "Advisor"}, 
			 "strengths" : "Good test taker",
			 "abilityToBenefit" : true,
			 "anticipatedStartTerm" : "Fall",
			 "anticipatedStartYear" : "2012",
			 "studentType" :
			    {"id" : "ce0d041d-d3ac-42a1-9085-7f744240e07e",
			     "name" : "Early Alert"},
			 "studentIntakeRequestDate" : 1331269200000,
			 "currentAppointment" : 
			    {"id" : "",
			     "startDate" : 1331269200000,
			     "endDate" : 1331269200000},
			 "specialServiceGroupIds" : 
			    [{"id" : "437f6f2b-7b1e-47b4-8603-9e1208605c0d"},
			     {"id" : "7bf36968-ad48-4c5f-9838-ebf495713c08"}
			    ],
			 "referralSourceIds" : 
			    [{"id" : "a8c83fd4-ee33-46ee-8d89-9b2956696f37"},
			     {"id" : "55aebdf7-466b-49c4-a08c-6e01eaf101d7"}
			    ],
			 "serviceReasonIds" : 
			    [{"id" : "d95ea029-90c1-4be0-b547-8054fc6914b7"},
			     {"id" : "c9ff0ce6-aec6-4be2-b1d1-9ca385edc637"}
			    ],
			 "programStatus" :
			    {"id" : "acf7d721-196f-4353-9508-fe86c61e3c1d",
			     "name" : "Active"}
			};
	}

    var getCampusData = function() {
        // TODO:  replace w/ ajax...
    	return [
    	        {"id" : "6201b18d-9d1d-48d3-82d4-092e52ad676b",
    	      "createdBy" : 
    	         {"id" : "91f46e39-cea8-422b-b215-00f6bcf5d280",
    	          "firstName" : "John",
    	          "lastName" : "Doe"},  
    	      "createdDate" : "1332216000000",
    	      "modifiedBy" : 
    	         {"id" : "91f46e39-cea8-422b-b215-00f6bcf5d280",
    	          "firstName" : "John",
    	          "lastName" : "Doe"},
    	      "modifiedDate" : "1332216000000",
    	      "objectStatus" : "ACTIVE",
    	      "name" : "Main Campus",
    	      "description" : "Main campus",
    	      "earlyAlertCoordinatorId" : "91f46e39-cea8-422b-b215-00f6bcf5d280"},
    	     {"id" : "c57f11e1-7ef2-4989-9ac2-7b0690563995",
    	      "createdBy" : 
    	         {"id" : "91f46e39-cea8-422b-b215-00f6bcf5d280",
    	          "firstName" : "John",
    	          "lastName" : "Doe"},
    	      "createdDate" : "1332216000000",
    	      "modifiedBy" : 
    	         {"id" : "91f46e39-cea8-422b-b215-00f6bcf5d280",
    	          "firstName" : "John",
    	          "lastName" : "Doe"},  
    	      "modifiedDate" : "1332216000000",
    	      "objectStatus" : "ACTIVE",
    	      "name" : "North Campus",
    	      "description" : "North campus",
    	      "earlyAlertCoordinatorId" : "91f46e39-cea8-422b-b215-00f6bcf5d280"}
    	    ];
    };
    
    var getReasonsData = function() {
        // TODO:  replace w/ ajax...
        return [
                {"id" : "6201b18d-9d1d-48d3-82d4-092e52ad676b",
            "createdBy" : 
            {"id" : "91f46e39-cea8-422b-b215-00f6bcf5d280",
             "firstName" : "John",
             "lastName" : "Doe"},  
         "createdDate" : "1332216000000",
         "modifiedBy" : 
            {"id" : "91f46e39-cea8-422b-b215-00f6bcf5d280",
             "firstName" : "John",
             "lastName" : "Doe"},  
         "modifiedDate" : "1332216000000",
         "objectStatus" : "ACTIVE",
         "name" : "Academic Concern",
         "description" : "Academic concern",
         "sortOrder" : 1},
        {"id" : "c57f11e1-7ef2-4989-9ac2-7b0690563995",
         "createdBy" : 
            {"id" : "91f46e39-cea8-422b-b215-00f6bcf5d280",
             "firstName" : "John",
             "lastName" : "Doe"},  
         "createdDate" : "1332216000000",
         "modifiedBy" : 
            {"id" : "91f46e39-cea8-422b-b215-00f6bcf5d280",
             "firstName" : "John",
             "lastName" : "Doe"},  
         "modifiedDate" : "1332216000000",
         "objectStatus" : "ACTIVE",
         "name" : "Excessive Absences",
         "description" : "Excessive absences",
         "sortOrder" : 2}
       ]

    };

    var getSuggestionsData = function() {
        // TODO:  replace w/ ajax...
        return [
            { "id" : "6201b18d-9d1d-48d3-82d4-092e52ad676b",
            "createdBy" : 
            {"id" : "91f46e39-cea8-422b-b215-00f6bcf5d280",
             "firstName" : "John",
             "lastName" : "Doe"},  
         "createdDate" : "1332216000000",
         "modifiedBy" : 
            {"id" : "91f46e39-cea8-422b-b215-00f6bcf5d280",
             "firstName" : "John",
             "lastName" : "Doe"},  
         "modifiedDate" : "1332216000000",
         "objectStatus" : "ACTIVE",
         "name" : "See Advisor or Coach",
         "description" : "See advisor or coach",
         "sortOrder" : 1},
        {"id" : "c57f11e1-7ef2-4989-9ac2-7b0690563995",
         "createdBy" : 
            {"id" : "91f46e39-cea8-422b-b215-00f6bcf5d280",
             "firstName" : "John",
             "lastName" : "Doe"},  
         "createdDate" : "1332216000000",
         "modifiedBy" : 
            {"id" : "91f46e39-cea8-422b-b215-00f6bcf5d280",
             "firstName" : "John",
             "lastName" : "Doe"},  
         "modifiedDate" : "1332216000000",
         "objectStatus" : "ACTIVE",
         "name" : "The Tutoring/Learning Center",
         "description" : "See the Tutoring/Learning Center.",
         "sortOrder" : 2}
        ];
    }

    var buildSelectors = function(container) {
        var rslt = {};
        $.each({
            course:             '.field-course',
            term:               '.field-term',
            student:            '.field-student',
            netId:              '.field-net-id',
            studentEmail:       '.field-student-email',
            studentType:        '.field-student-type',
            assignedCounselor:  '.field-assigned-counselor',
            office:             '.field-office',
            phone:              '.field-phone',
            department:         '.field-department',
            campus:             '.field-campus',
            reason:             '.field-reason',
            suggestions:        '.field-suggestions',
            suggestionsAddEdit: '.suggestions-add-edit',
            suggestionsDialog:  '.suggestions-dialog'
        }, function(name, value) {
            rslt[name] = container + ' ' + value;
        });
        return rslt;
    };
    
    ssp.EarlyAlertForm = function(container, parameters) {

        var selectors = buildSelectors(container);

        var student = getPersonData(parameters.studentId);
        var campuses = getCampusData();
        var reasons = getReasonsData();
        var suggestions = getSuggestionsData();

        /*
         * Populate the fields...
         */

        // course
        $(selectors.course).text(parameters.course);

        // term
        $(selectors.term).text(parameters.term);

        // student
        var studentName = student.firstName + ' '
                + (student.middleInitial ? ' ' + student.middleInitial + ' ' : ' ')
                + student.lastName;
        $(selectors.student).text(studentName);

        // netId
        $(selectors.netId).text(student.schoolId);

        // studentEmail
        $(selectors.studentEmail).text(student.primaryEmailAddress);

        // studentType
        $(selectors.studentType).text(student.studentType.name);

        // assignedCounselor
        $(selectors.assignedCounselor).text(student.coach.lastName + ', ' + student.coach.firstName);

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
        	var html = '<option value="' + value.id + '">' + value.name + '</option>';
            $(selectors.reason).append(html);
        });

        // suggestions
        $.each(suggestions, function(index, value) {
        	var html = '<li><input type="checkbox" value="' + value.id + '">' + value.name + '</li>';
            $(selectors.suggestionsDialog).find('ul').append(html);
        });
        var dialogOptions = {
            autoOpen: false,
            buttons: {
                'OK': function() {
                    $(selectors.suggestions).html('');  // Clear
                    $(this).find('li').each(function() {
                        var chk = $(this).find('input');
                        if (chk.attr('checked')) {
                            var html = '<li>' + $(this).text() + '</li>';
                            $(selectors.suggestions).append(html);
                        }
                    });
                    $(this).dialog('close');
                },
                'Cancel': function() { $(this).dialog('close'); },
            },
            modal: true,
            title: 'Edit Faculty Suggestions'
        };
        var dlg = $(selectors.suggestionsDialog).dialog(dialogOptions);
        $(selectors.suggestionsAddEdit).click(function() {
            dlg.dialog('open');
        });

    };

})(jQuery);
