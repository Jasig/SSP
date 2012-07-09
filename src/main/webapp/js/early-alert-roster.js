"use strict";
var ssp = ssp || {};

(function($, fluid) {
	
	// TODO:  replace w/ ajax...
    var mockCourses = [
        { title: 'ENGLISH - 124 - 001 - Academic Writing and Literature', id: '001' },
        { title: 'ENGLISH - 125 - 012 - Writing and Academic Inquiry', id: '002' },
        { title: 'ENGLISH - 223 - 003 - Creative Writing', id: '003' },
        { title: 'ENGLISH - 225 - 010 - Academic Argumentation', id: '004' }
    ];

    // TODO:  replace w/ ajax...
    var mockRoster = [
        { firstName: 'James', middleInitial: 'K', lastName: 'Polk', studentType: 'ARC' },
        { firstName: 'George', middleInitial: 'W', lastName: 'Bush', studentType: 'ARC' },
        { firstName: 'Franklin', middleInitial: 'D', lastName: 'Roosevelt', studentType: 'ARC' },
        { firstName: 'Warren', middleInitial: 'G', lastName: 'Harding', studentType: 'ARC' },
        { firstName: 'Harry', middleInitial: 'S', lastName: 'Truman', studentType: 'ARC' },
        { firstName: 'Dwight', middleInitial: 'D', lastName: 'Eisenhower', studentType: 'ARC' },
        { firstName: 'Ulysses', middleInitial: 'S', lastName: 'Grant', studentType: 'ARC' },
        { firstName: 'Chester', middleInitial: 'A', lastName: 'Arthur', studentType: 'ARC' },
        { firstName: 'Lyndon', middleInitial: 'B', lastName: 'Johnson', studentType: 'ARC' },
        { firstName: 'Gerald', middleInitial: 'R', lastName: 'Ford', studentType: 'ARC' },
        { firstName: 'John', middleInitial: 'F', lastName: 'Kennedy', studentType: 'ARC' },
        { firstName: 'Richard', middleInitial: 'M', lastName: 'Nixon', studentType: 'ARC' },
        { firstName: 'William', middleInitial: 'J', lastName: 'Clinton', studentType: 'ARC' }
    ];

    ssp.EarlyAlertRoster = function(container, options) {
		
        // construct the new component
        var that = fluid.initView('ssp.EarlyAlertRoster', container, options);

        var loadRoster = function() {
            var data = that.options.dataFunction(that);
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
        
        // Courses Select
        var courseSelect = that.locate('courseSelect');
        $(mockCourses).each(function(index, course) {
            courseSelect.append('<option value="' + course.id  + '">' + course.title + '</option>');
        });
        courseSelect.change(refreshRoster);
        
        // Click event for selecting a student
        $(container + ' ' + that.options.selectors.rosterTable + ' tr').live('click', function() {
            var studentId = $(this).find('td:first').text();  // TODO:  Fix!
            var courseId = $(this).find('td:first').text();  // TODO:  Fix!
            var alertFormUrl = that.options.enterAlertUrl.replace('STUDENTID', studentId).replace('COURSEID', courseId);
            window.location = alertFormUrl;
        });
        
        // Pager
        var pagerOptions = {
            dataModel: that.options.dataFunction(that),
            columnDefs: [
                { key: 'firstName', valuebinding: '*.firstName', sortable: true },
                { key: 'middleInitial', valuebinding: '*.middleInitial', sortable: true },
                { key: 'lastName', valuebinding: '*.lastName', sortable: true },
                { key: 'studentType', valuebinding: '*.studentType', sortable: true }
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
    }

    // defaults
    fluid.defaults('ssp.EarlyAlertRoster', {
    	dataFunction: function(that) {
    		var courseSelect = that.locate('courseSelect');
    		var course = courseSelect.val();  // TODO:  Use this parameter    		
    		return mockRoster.slice(0);
    	},
    	selectors: {
            courseSelect: '.course-select',
            loadingMessage: '.loading-message',
            roster: '.roster',
            rosterTable: '.roster-table'
        }
    });
	
})(jQuery, fluid);
