"use strict";
var ssp = ssp || {};

(function($, fluid) {

    ssp.EarlyAlertRoster = function(container, options) {
    	
        // construct the new component
        var that = fluid.initView('ssp.EarlyAlertRoster', container, options);

        /*
         * Course List Data Function
         */
        var getCourseListData = function() {
        	var rslt = [];
            $.ajax({
                url: options.urls.courseList,
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
         * Roster Data Function
         */
        var getRosterData = function() {
        	var rslt = [];
        	var formattedCourse = that.locate('courseSelect').val();
        	if (formattedCourse) {
                $.ajax({
                    url: options.urls.roster.replace('FORMATTEDCOURSE', formattedCourse),
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
        	}
            return rslt;
        };

        /*
         * Error Handling Functions
         */
        var showError = function(title, body) {
            var err = that.locate('errorTemplate').clone();
            err.removeClass('error-message-template').addClass('error-message');
            err.find('.error-title').html(title);
            err.find('.error-body').html(body);
            err.appendTo(that.locate('errorsDiv'));
            err.slideDown(1000);
        };
        var clearErrors = function() {
            $(that.locate('errorsDiv')).html('');
        };

        /*
         * Roster Management Functions
         */
        var loadRoster = function() {
            var data = getRosterData();
            var newModel = fluid.copy(that.pager.model);
            newModel.totalRange = data.length;
            newModel.pageIndex = 0;
            newModel.pageCount = Math.max(1, Math.floor((newModel.totalRange - 1) / newModel.pageSize) + 1);
            fluid.clear(that.pager.options.dataModel);
            fluid.model.copyModel(that.pager.options.dataModel, data);
            that.pager.permutation = undefined;
            that.pager.events.onModelChange.fire(newModel, that.pager.model, that.pager);
            fluid.model.copyModel(that.pager.model, newModel);
        }

        var refreshRoster = function() {
        	clearErrors();
        	var roster = that.locate('roster');
            var loadingMessage = that.locate('loadingMessage');
            roster.slideUp(500, function() {
                loadingMessage.slideDown(1000, function() {
                    loadRoster();
                    loadingMessage.slideUp(1000, function() {
                        roster.slideDown(500);
                    });
                });
            });
        };
        
        // Initialize the courseSelect
        var courseSelect = that.locate('courseSelect');
        var courses = getCourseListData();
        if (courses && courses.length != 0) {
            $(courses).each(function(index, course) {
                courseSelect.append('<option value="' + course.formattedCourse  + '">' + course.formattedCourse + ' - ' + course.title + '</option>');
            });
            courseSelect.change(refreshRoster);

            // Initialize the courseSelect
            var pagerOptions = {
                dataModel: getRosterData(),
                columnDefs: [
                    { key: 'firstName', valuebinding: '*.firstName', sortable: true },
                    { key: 'middleName', valuebinding: '*.middleName', sortable: true },
                    { key: 'lastName', valuebinding: '*.lastName', sortable: true },
                    { key: 'studentType', valuebinding: '*.studentType', sortable: true },
                    { key: 'schoolId', valuebinding: '*.schoolId', sortable: true }
                ],
                bodyRenderer: {
                    type: 'fluid.pager.selfRender',
                    options: {
                        selectors: { root: that.options.selectors.rosterTable },
                        row: 'row:'
                    }
                },
                pagerBar: {
                    type: 'fluid.pager.pagerBar', 
                    options: {
                        pageList: {
                            type: 'fluid.pager.renderedPageList',
                            options: { linkBody: 'a' }
                        }
                    }
                }
            };
            that.pager = fluid.pager(container, pagerOptions);

            // Click event for selecting a student
            $(container + ' ' + that.options.selectors.rosterTable + ' tr').live('click', function() {
                var schoolId = $(this).find('.schoolId').text();
                var formattedCourse = that.locate('courseSelect').val();
                var alertFormUrl = options.urls.enterAlert.replace('SCHOOLID', schoolId).replace('FORMATTEDCOURSE', formattedCourse);
                window.location = alertFormUrl;
            });
        } else {
        	showError('No Available Courses', 'There are no courses available for this user in SSP.');
        }

    }

    // defaults
    fluid.defaults('ssp.EarlyAlertRoster', {
    	selectors: {
            courseSelect:            '.course-select',
            errorsDiv:               '.errors',
            errorTemplate:           '.error-message-template',
            loadingMessage:          '.loading-message',
            roster:                  '.roster',
            rosterTable:             '.roster-table'
        }
    });
	
})(jQuery, fluid);
