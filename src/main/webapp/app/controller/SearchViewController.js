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

Ext.define('defaultTabLabel', { 
	index: 0 
});  

Ext.define('Ssp.controller.SearchViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	apiProperties: 'apiProperties',
        appEventsController: 'appEventsController',
        authenticatedPerson: 'authenticatedPerson',
        caseloadFilterCriteria: 'caseloadFilterCriteria',
        caseloadStore: 'caseloadStore',
        watchListStore: 'watchListStore',
        caseloadService: 'caseloadService',
        exportService: 'exportService',
        watchListService: 'watchListService',
        columnRendererUtils: 'columnRendererUtils',
        formUtils: 'formRendererUtils',
        person: 'currentPerson',
        caseloadActionsStore: 'caseloadActionsStore',
        personLite: 'personLite',
        personProgramStatusService: 'personProgramStatusService',
        preferences: 'preferences',
        programStatusesStore: 'caseloadFilterProgramStatusesStore',
        programStatusService: 'caseloadFilterProgramStatusService',
        searchCriteria: 'searchCriteria',
        searchService: 'searchService',
        searchStore: 'directoryPersonSearchStore',
		termsStore: 'termsStore',
        configStore: 'configurationOptionsUnpagedStore',
        textStore:'sspTextStore'
    },

    control: {
    	view: {
    		itemclick: 'onSelectionChange',
			viewready: 'onViewReady'
    	},
    	caseloadStatusCombo: {
    		selector: '#caseloadStatusCombo',
    		listeners: {
    			select: 'onCaseloadStatusComboSelect'
    		}
    	},
    	caseloadActionCombo: {
    		selector: '#caseloadActionCombo',
    		listeners: {
    			select: 'onCaseloadActionComboSelect'
    		}
    	},

    	searchGridPager: '#searchGridPager',
    	searchBar: '#searchBar',
		upTool: '#upTool',

    	addPersonButton: {
    		selector: '#addPersonButton',
    		listeners: {
    			click: 'onAddPersonClick'
    		}
    	},

    	editPersonButton: {
    		selector: '#editPersonButton',
    		listeners: {
    			click: 'onEditPersonClick'
    		}
    	},

		'setTransitionStatusButton': {
			click: 'onSetProgramStatusClick'
		},

		'setNonParticipatingStatusButton': {
			click: 'onSetProgramStatusClick'
		},

		'setNoShowStatusButton': {
			click: 'onSetProgramStatusClick'
		},
		'setActiveStatusButton': {
			click: 'onSetProgramStatusClick'
		},
		'setInactiveButton': {
			click: 'onSetProgramStatusClick'
		},
		'searchStudentButton': {
		click: 'onSearchButtonClick'
		},
		'resetStudentSearchButton': {
		    click: 'onResetClick'
		}

    },

    eventsWired: false,

	init: function() {
		var me=this;
        var tabSelection = me.getView().tabContext;
        me.registerTabNavHandlers();

	   	// ensure the selected person is not loaded twice
		// once on load and once on selection
	   //	me.personLite.set('id','');

	   	me.SEARCH_GRID_VIEW_TYPE_IS_SEARCH = 0;
	   	me.SEARCH_GRID_VIEW_TYPE_IS_CASELOAD = 1;
	   	me.SEARCH_GRID_VIEW_TYPE_IS_WATCHLIST = 2;

		if (me.caseloadActionsStore.getTotalCount() == 0) {
			me.caseloadActionsStore.load();
		};
		if (me.termsStore.getTotalCount() == 0) {
				me.termsStore.addListener("load", me.onTermsStoreLoad, me);
				me.termsStore.load();
		};
		if (me.configStore.getTotalCount() == 0) {
			me.configStore.load();
		};

		me.onTextStoreLoad();

		me.personLite.on('idchanged', me.personChanged, me);
	   	// load program statuses
		me.getProgramStatuses();

		switch(tabSelection) {
			case 'myCaseload':
	            me.displayCaseload();
				break;
			case 'watchList':
	            me.displayWatchList();
				break;
			case 'search':
	            me.displaySearch();
		}
		return me.callParent(arguments);
    },

   personChanged: function() {
	   var record = this.getView().getStore().findRecord("id", this.personLite.get("id"));
	   if (record != null) {
			if(!this.getView().getSelectionModel().isSelected(record )) {
				this.getView().getSelectionModel().select(record, false, true);
			}
	   } else {
			this.getView().getSelectionModel().deselectAll(true);
	   }
   },

    onTextStoreLoad:function() {
    	var me = this;
    	var index = !me.authenticatedPerson.hasAccess('CASELOAD_SEARCH') ? 0 : 2;
    	me.setGridView();
    },

	onSelectionChange: function( view, record, item, index, eventObj ) {
		var me=this;
        var skipCallBack = this.appEventsController.getApplication().fireEvent('personNav', record, me);

		if( skipCallBack ) {
			if ( record ) {
				if( record.get("id") ) {
					if( me.personLite.get('id') != record.get("id") ) {
						var person = new Ssp.model.Person();
						// clear the person record
						me.person.data = person.data;
						me.updatePerson(record);
						me.appEventsController.getApplication().fireEvent('loadPerson');
					}
				} else if(me.authenticatedPerson.hasAccess('ADD_STUDENT_BUTTON')) {
					var person = new Ssp.model.Person();
					// clear the person record
					me.person.data = person.data;
					me.instantCaseloadAssignment(record);
				}
			}
		}
	},

    updatePerson: function( persons ) {
        var me=this;

        var person = me.getRecordFromArray(persons);
		if ( person.data.id != null ) {
			me.personLite.set('id', person.data.id);
		} else {
			me.personLite.set('id', person.data.personId);
		}
		me.personLite.set('firstName', person.data.firstName);
		me.personLite.set('middleName', person.data.middleName);
		me.personLite.set('lastName', person.data.lastName);
		me.personLite.set('displayFullName', person.data.firstName + ' ' + person.data.lastName);
	},

	getRecordFromArray: function(records) {
		var record;
		if (Array.isArray(records)) {
			if(records.length == 1) {
				record = record[0];
            } else {
				throw "more than one record found, expect only one"
            }
		} else {
			record = records;
        }
		return record
	},

    getTabPanel: function() {
        var me = this;
        return me.tabPanel = me.tabPanel || me.getView().tabPanelAccessor();
    },

    registerTabNavHandlers: function() {
        var me = this;
        if ( me.getTabPanel() ) {
            me.getTabPanel().on('tabchange', me.onTabChange, me);
        }
    },

    deRegisterTabNavHandlers: function() {
        var me = this;
        if ( me.getTabPanel() ) {
            me.getTabPanel().un('tabchange', me.onTabChange, me);
        }
    },

    onTabChange: function(tabPanel, newCard, oldCard, eOpts) {
    	//alert('tab is changing');
    	//set the 'make default tab' text
    	var me = this;
		var activeTabIndex = tabPanel.items.findIndex('id', newCard.id);		               					
		if(activeTabIndex == defaultTabLabel.index) {
			Ext.getCmp('defaultTabLabel').setText(me.textStore.getValueByCode('ssp.label.default-search-tab', 'Default Search Tab'));
		}
		else {		
			Ext.getCmp('defaultTabLabel').setText(me.textStore.getValueByCode('ssp.label.click-to-make-default', 'Click to Make Default'));
		}		   
    	
        var me = this;
        if ( newCard.items.items[0] === me.getView() ) {
            me.acceptFocus(oldCard);
        }
    },

    onViewReady: function() {
        var me = this;
        me.acceptFocus();
    },

    acceptFocus: function(prevTab) {
        var me = this;
        var eventsPreviouslyWired = me.eventsWired;
        if ( !(me.eventsWired) ) {
            me.appEventsController.assignEvent({eventName: 'doAddPerson', callBackFunc: me.onAddPerson, scope: me});
            me.appEventsController.assignEvent({eventName: 'doPersonButtonEdit', callBackFunc: me.onEditPerson, scope: me});
            me.appEventsController.assignEvent({eventName: 'doRetrieveCaseload', callBackFunc: me.getCaseload, scope: me});
            me.appEventsController.assignEvent({eventName: 'doPersonStatusChange', callBackFunc: me.onDoPersonStatusChange, scope: me});
            me.appEventsController.assignEvent({eventName: 'onStudentWatchAction', callBackFunc: me.onViewReady, scope: me});

            me.appEventsController.assignEvent({eventName: 'toolsNav', callBackFunc: me.onToolsNav, scope: me});
            me.appEventsController.assignEvent({eventName: 'collapseStudentRecord', callBackFunc: me.onCollapseStudentRecord, scope: me});
            me.appEventsController.assignEvent({eventName: 'expandStudentRecord', callBackFunc: me.onExpandStudentRecord, scope: me});
            me.appEventsController.assignEvent({eventName: 'afterPersonProgramStatusChange', callBackFunc: me.onAfterPersonProgramStatusChange, scope: me});
            me.appEventsController.assignEvent({eventName: 'onPersonSearchSuccess', callBackFunc: me.searchSuccess, scope: me});
            me.appEventsController.assignEvent({eventName: 'onPersonSearchFailure', callBackFunc: me.searchFailure, scope: me});
            me.appEventsController.assignEvent({eventName: 'updateEarlyAlertCounts', callBackFunc: me.onUpdateEarlyAlertCounts, scope: me});
            me.appEventsController.assignEvent({eventName: 'updateSearchStoreRecord', callBackFunc: me.onUpdateSearchStoreRecord, scope: me});
            me.eventsWired = true;
        }
        if ( !(eventsPreviouslyWired) || (prevTab && !(me.getIsSearch())) ) {
            me.initSearchGrid();
            me.harmonizePersonLite();
        }
    },



	harmonizePersonLite:function() {
		var me = this;
		if (me.personLite.get('id')) {
			var foundIndex = me.getView().getStore().findExact("id", me.personLite.get('id'));
            if ( foundIndex >= 0) {
				me.getView().getSelectionModel().select(foundIndex);
			}
		}
	},

	onUpdateEarlyAlertCounts: function(params) {
		var me = this;
		var person = me.caseloadStore.findRecord("id",params.personId, 0, false, false, true);
		if (person != null) {
			person.set('numberOfEarlyAlerts', params.openEarlyAlerts);
			person.set('numberEarlyAlertResponsesRequired', params.lateEarlyAlertResponses);
		}

		person = me.searchStore.findRecord("id",params.personId, 0, false, false, true);
		if (person != null) {
			person.set('numberOfEarlyAlerts', params.openEarlyAlerts);
			person.set('numberEarlyAlertResponsesRequired', params.lateEarlyAlertResponses);
		}
		person = me.watchListStore.findRecord("id",params.personId, 0, false, false, true);
		if (person != null) {
			person.set('numberOfEarlyAlerts', params.openEarlyAlerts);
			person.set('numberEarlyAlertResponsesRequired', params.lateEarlyAlertResponses);
		}
	},

    destroy: function() {
    	var me=this;

        me.deRegisterTabNavHandlers();
		me.termsStore.removeListener("load", me.onTermsStoreLoad, me);
		me.textStore.removeListener("load", me.onTextStoreLoad, me, {single: true});
		me.personLite.un('idchanged', me.personChanged, me);
		me.appEventsController.removeEvent({eventName: 'doPersonButtonEdit', callBackFunc: me.onEditPerson, scope: me});
		me.appEventsController.removeEvent({eventName: 'doAddPerson', callBackFunc: me.onAddPerson, scope: me});
		me.appEventsController.removeEvent({eventName: 'doRetrieveCaseload', callBackFunc: me.getCaseload, scope: me});
		me.appEventsController.removeEvent({eventName: 'doPersonStatusChange', callBackFunc: me.onDoPersonStatusChange, scope: me});
        me.appEventsController.removeEvent({eventName: 'toolsNav', callBackFunc: me.onToolsNav, scope: me});
		me.appEventsController.removeEvent({eventName: 'onStudentWatchAction', callBackFunc: me.onViewReady, scope: me});
    	me.appEventsController.removeEvent({eventName: 'collapseStudentRecord', callBackFunc: me.onCollapseStudentRecord, scope: me});
	   	me.appEventsController.removeEvent({eventName: 'expandStudentRecord', callBackFunc: me.onExpandStudentRecord, scope: me});
	   	me.appEventsController.removeEvent({eventName: 'onPersonSearchSuccess', callBackFunc: me.searchSuccess, scope: me});
		me.appEventsController.removeEvent({eventName: 'onPersonSearchFailure', callBackFunc: me.searchFailure, scope: me});
		me.appEventsController.removeEvent({eventName: 'updateEarlyAlertCounts', callBackFunc: me.onUpdateEarlyAlertCounts, scope: me});
		me.appEventsController.removeEvent({eventName: 'updateSearchStoreRecord', callBackFunc: me.onUpdateSearchStoreRecord, scope: me});
	   	me.appEventsController.removeEvent({eventName: 'afterPersonProgramStatusChange', callBackFunc: me.onAfterPersonProgramStatusChange, scope: me});

	   	if ( me.emailStudentPopup ) {
	   	    me.emailStudentPopup.destroy();
	   	}

		if(me.instantCaseload != null && !me.instantCaseload.isDestroyed) {
			me.instantCaseload.close();
        }

		me.eventsWired = false;
		return me.callParent( arguments );
    },

    initSearchGrid: function(forceSearchFormDisplay) {
	   	var me=this;

       	if(me.getIsSearch()) {
			me.displaySearch(forceSearchFormDisplay);
		} else {
            if (me.getIsWatchList()) {
                me.getWatchList(true);
                me.displayWatchList();
            } else {
                if ( me.authenticatedPerson.hasAccess('CASELOAD_FILTERS') ) {
					if( me.preferences.get('SEARCH_GRID_VIEW_TYPE') != me.SEARCH_GRID_VIEW_TYPE_IS_CASELOAD) {
	                    me.preferences.set('SEARCH_GRID_VIEW_TYPE', me.SEARCH_GRID_VIEW_TYPE_IS_CASELOAD);
	                    // default caseload to Active students if no program status is defined
	                    me.getCaseload(true);
						me.displayCaseload();
					} else {
						me.loadCaseload();
					}
                    
                } else {
                    me.search();
                    me.displaySearch(forceSearchFormDisplay);
                }
            }
        }
    },

    selectFirstItem: function() {
    	var me=this;
    	if ( me.getView().getStore().getCount() > 0) {
        	me.getView().getSelectionModel().select(0);
    	} else {
    		// if no record is available, then cast event
    		// to reset the profile tool fields
    		me.personLite.set('id', "");
    		me.appEventsController.getApplication().fireEvent('loadPerson');
    	}
    	me.appEventsController.getApplication().fireEvent('updateStudentRecord');
    	me.refreshPagingToolBar();
    },
    onCollapseStudentRecord: function(applyColumns) {
		var me = this;
		me.preferences.set('SEARCH_VIEW_SIZE', "COLLAPSED");
		if (applyColumns === true) {
			me.applyColumns();
		}
        me.showColumn(false,'birthDate');
		me.showColumn(false,'primaryEmailAddress');
		if( me.getIsCaseload() || me.getIsWatchList() ){
			me.showColumn(false,'coach');
			me.showColumn(false,'currentProgramStatusName');
			me.showColumn(true,'studentType')
		} else {
			me.showColumn(true,'coach');
			me.showColumn(true,'currentProgramStatusName');
			me.showColumn(false,'studentType');
		}
	},

	onExpandStudentRecord: function(applyColumns) {
		var me = this;
		me.preferences.set('SEARCH_VIEW_SIZE', "EXPANDED");
		if(applyColumns === true) {
			me.applyColumns();
		}
	    me.showColumn(true,'birthDate');
		me.showColumn(true,'studentType');
		me.showColumn(true,'primaryEmailAddress');
		if (me.getIsCaseload() || me.getIsWatchList()) {
			me.showColumn(false,'coach');
			me.showColumn(false,'currentProgramStatusName');
		} else {
			me.showColumn(true,'coach');
			me.showColumn(true,'currentProgramStatusName');

		}
	},

	setGridView: function( view ) {
		var me=this;
		if (me.getIsExpanded()) {
			me.onExpandStudentRecord(true);
		}else{
			me.onCollapseStudentRecord(true);
		}
	},

	getIsExpanded:function() {
		var me= this;
		if(me.preferences.get('SEARCH_VIEW_SIZE') == "EXPANDED") {
			return true;
        } else {
		    return false;
		}
	},

	getIsCaseload: function() {
		var me= this;
		if(me.getView().tabContext === 'myCaseload') {
			return true;
        } else {
		    return false;
		}
	},
	getIsSearch: function() {
		var me= this;
		if(me.getView().tabContext === 'search') {
			return true;
        } else {
		    return false;
		}
	},
	getIsWatchList: function() {
		var me= this;
		if(me.getView().tabContext === 'watchList') {
			return true;
        } else {
		    return false;
		}
	},
	onToolsNav: function() {
		var searchView = Ext.ComponentQuery.query('searchtab')[0];
		searchView.collapse();
	},

	displaySearch: function(forceSearchFormDisplay) {
		var me = this;

		if ( forceSearchFormDisplay === undefined || forceSearchFormDisplay === null || forceSearchFormDisplay ) {
			me.getSearchBar().show();
			me.getSearchStudentButton().show();
			me.getResetStudentSearchButton().show();
			me.getUpTool().show();
			Ext.ComponentQuery.query('searchForm')[2].show(); //component always has three from SearchTab using 3 Search.js, 3rd is the "search" tab
		}
		me.setGridView();
	},

	displayCaseload: function(){
		var me=this;
		me.getSearchBar().hide();
		me.getSearchStudentButton().hide();
		me.getResetStudentSearchButton().hide();
		me.getUpTool().hide();
		me.setGridView();
	},

	displayWatchList: function(){
		var me=this;
		me.getSearchBar().hide();
		me.getSearchStudentButton().hide();
		me.getResetStudentSearchButton().hide();
		me.getUpTool().hide();
		me.setGridView();

	},

	applyColumns: function() {
		var me=this;
		var grid = me.getView();
		var store;
		var sortableColumns = true;
		if ( me.getIsCaseload() ) {
				store = me.caseloadStore;
		} else {
            if( me.getIsWatchList() ) {
                store = me.watchListStore;
            } else {
                store = me.searchStore;
                store.pageSize = store.data.length;
                if (me.searchStore.data.length < 1) {
                    me.getCaseloadActionCombo().hide();
					me.getSetNonParticipatingStatusButton().hide();
					me.getSetNoShowStatusButton().hide();
					me.getSetActiveStatusButton().hide();
					me.getSetInactiveButton().hide();
					
                } else {
                    if (me.authenticatedPerson.hasAnyBulkPermissions()) {
                        me.getCaseloadActionCombo().show();
                    }
					me.getSetNonParticipatingStatusButton().show();
					me.getSetNoShowStatusButton().show();
					me.getSetActiveStatusButton().show();
					me.getSetInactiveButton().show();
                }
            }
        }

		columns = [
	              { sortable: sortableColumns, header: me.textStore.getValueByCode('ssp.label.first-name', 'First Name'), dataIndex: 'firstName', flex: 1 },
	              { sortable: sortableColumns, header: me.textStore.getValueByCode('ssp.label.middle-name', 'Middle Name'), dataIndex: 'middleName', flex: me.getIsExpanded() ? 0.4:0.2},
	              { sortable: sortableColumns, header: me.textStore.getValueByCode('ssp.label.last-name', 'Last Name'), dataIndex: 'lastName', flex: 1},
				  { sortable: sortableColumns, header: me.textStore.getValueByCode('ssp.label.dob', 'DOB'), dataIndex: 'birthDate', renderer: Ext.util.Format.dateRenderer('m/d/Y'), flex: 0.5},
	              { sortable: sortableColumns, header: me.textStore.getValueByCode('ssp.label.coach-name', 'Coach'), dataIndex: 'coach', renderer: me.columnRendererUtils.renderCoachName, flex: 1},
	              { sortable: sortableColumns, header: me.textStore.getValueByCode('ssp.label.search.student-type', 'Type'), dataIndex: 'studentType', renderer: me.columnRendererUtils.renderStudentType, flex: me.getIsExpanded() ? 0.5:0.2},
				  { sortable: sortableColumns, header: me.textStore.getValueByCode('ssp.label.student-id', 'Student Id'), dataIndex: 'schoolId', flex: me.getIsExpanded() ? 0.5:1},
				  { sortable: sortableColumns, header: me.textStore.getValueByCode('ssp.label.email', 'Email'), dataIndex: 'primaryEmailAddress', flex: 0.8},
	              { sortable: sortableColumns, header: me.textStore.getValueByCode('ssp.label.program-status', 'Status'), dataIndex: 'currentProgramStatusName', flex: 0.2},
	              { sortable: sortableColumns, header: me.textStore.getValueByCode('ssp.label.number-of-early-alerts', 'Alerts'), dataIndex: 'numberOfEarlyAlerts', flex: 0.2},
		          { sortable: sortableColumns, header: me.textStore.getValueByCode('ssp.label.number-of-success-indicators', 'Risk'), dataIndex: 'numberOfLowMediumConfiguredSuccessIndicators', flex: 0.2, getSortParam: function() {return 'configLowIndicatorsCount';}}
				];

		grid.getView().getRowClass = function(row, index) {
			var cls = "";
			var today = Ext.Date.clearTime( new Date() );
			var tomorrow = Ext.Date.clearTime( new Date() );
			tomorrow.setDate( today.getDate() + 1 );
			// set apppointment date color first. early alert will over-ride appointment color.
			if (row.get('currentAppointmentStartTime') != null)	{
				if ( me.formUtils.dateWithin(today, tomorrow, row.get('currentAppointmentStartTime') ) ) {
					cls = 'caseload-appointment-indicator'
				}
			}

			// early alert color will over-ride the appointment date
			if ( row.get('numberOfEarlyAlerts') != null) {
				if (row.get('numberOfEarlyAlerts') > 0) {
					cls = 'caseload-early-alert-indicator'
				}
			}

			// early alert color will over-ride the alert
			if ( row.get('numberEarlyAlertResponsesRequired') != null) {
				if (row.get('numberEarlyAlertResponsesRequired') > 0) {
					cls = 'caseload-early-alert-response-required-indicator'
				}
			}

			if (row.get('id') == "") {
				cls = "directory-person-is-external-person-only";
			}

			return cls;
	    };

		me.formUtils.reconfigureGridPanel(grid, store, columns);

		if(me.getSearchGridPager) {
			me.getSearchGridPager().bindStore(store);
		}

		me.refreshPagingToolBar();
	},


	 showColumn: function( show, dataIndex ) {
		var me=this;
	    var column = me.getColumn(dataIndex);

		if ( column ) {
		   column.setVisible(show);
	    }
	},

	getColumn:function(dataIndex) {
		var me=this;
		var columns = me.getView().columns;
		for(var i = 0; i < columns.length; i++) {
			if(columns[i].dataIndex == dataIndex) {
				return columns[i];
            }
		}
		return null;
	},

    onAddPersonClick: function( button ) {
    	var me=this;

        var skipCallBack = this.appEventsController.getApplication().fireEvent('personButtonAdd',me);
        if(skipCallBack) {
        	me.onAddPerson();
        }
	},

	onTermsStoreLoad:function() {
		var me = this;
		me.termsStore.removeListener( "onTermsStoreLoad", me.onTermsStoreLoad, me );

	},

	onEditPersonClick: function( button ) {
    	var me=this;
        var skipCallBack = this.appEventsController.getApplication().fireEvent('personButtonEdit',me);
        if(skipCallBack) {
        	me.onEditPerson();
        }
	},

	onAddPerson: function() {
		var me=this;
		var model = new Ssp.model.Person();
    	me.person.data = model.data;
    	me.personLite.set('id','');
		me.loadCaseloadAssignment();
	},

	onEditPerson: function() {
		var me=this;
		var records = this.getView().getSelectionModel().getSelection();
		if (records.length>0) {
			me.loadCaseloadAssignment();
		} else {
			Ext.Msg.alert(
				me.textStore.getValueByCode('ssp.message.search.error-title',' SSP Error'),
				me.textStore.getValueByCode('ssp.message.search.select-student-to-edit','Please select a student to edit.')
				);
		}
	},

    refreshPagingToolBar: function() {
		if(this.getSearchGridPager() != null) {
    		this.getSearchGridPager().onLoad();
    	}
    },

    loadCaseloadAssignment: function() {
    	var comp = this.formUtils.loadDisplay('mainview', 'caseloadassignment', true, {flex:1});
    },

	loadStudentToolsView: function() {
    	this.appEventsController.getApplication().fireEvent('displayStudentRecordView');
    },

	onSetProgramStatusClick: function( button ) {
		var me=this;
		var action = button.action;
		me.setProgramStatus(action);
	},

	findResultPerson: function(personId) {
		var me=this;
		if ( me.getIsCaseload() ) {
			return me.caseloadStore.findRecord("personId", personId, 0, false, false, true);
		} else if ( me.getIsWatchList() ) {
			return me.watchListStore.findRecord("personId", personId, 0, false, false, true);
		} else {
			return me.searchStore.findRecord("id", personId, 0, false, false, true);
		}
	},

	isProgramStatusChanging: function(action, person) {
		var currentStatus = person.get('currentProgramStatusName');
		switch ( action ) {
			case 'transition':
				return Ssp.util.Constants.TRANSITIONED_PROGRAM_STATUS_NAME !== currentStatus;
			case 'active':
				return Ssp.util.Constants.ACTIVE_PROGRAM_STATUS_NAME !== currentStatus;
			case 'inactive':
				return Ssp.util.Constants.INACTIVE_PROGRAM_STATUS_NAME !== currentStatus;
			case 'no-show':
				return Ssp.util.Constants.NO_SHOW_PROGRAM_STATUS_NAME !== currentStatus;
			// always let you specify non-participating as a *change* b/c you can further qualify it with a 'reason'
		}
		return true;
	},

	setProgramStatus: function( action ) {
		var me=this;
		var personId = me.personLite.get('id');
		var programStatusId = "";
		if (personId != "") {
			var person = me.findResultPerson(personId);
			if ( !(person) ) {
				var defaultMsg = 'Please select a student for which to change status';
				Ext.Msg.alert(
					me.textStore.getValueByCode('ssp.message.search.error-title','SSP Error'),
					me.textStore.getValueByCode('ssp.message.search.select-student-to-change-status',defaultMsg)
					);
				return;
			}

			if ( !(me.isProgramStatusChanging(action, person)) ) {
				var defaultMsg = 'Selected person already has the requested status';
				Ext.Msg.alert(
					me.textStore.getValueByCode('ssp.message.search.error-title','SSP Error'),
					me.textStore.getValueByCode('ssp.message.search.selected-student-already-has-status',defaultMsg)
					);
				return;
			}

			if ( action === 'transition' ) {
				/*
				 * Temp fix for SSP-434
				 *
				 * Temporarily removing Transition Action from this button.
				 * TODO: Ensure that this button takes the user to the Journal Tool and initiates a
				 * Journal Entry.
				 * // me.appEventsController.getApplication().fireEvent('transitionStudent');
				 */
				var defaultMsg = 'Manual selection of the \'Transition\' status is not supported';
				Ext.Msg.alert(
					me.textStore.getValueByCode('ssp.message.search.error-title','SSP Error'),
					me.textStore.getValueByCode('ssp.message.search.transition-status-not-supported',defaultMsg)
					);
			}

			var keepGoing = this.appEventsController.getApplication().fireEvent('personStatusChange',action,personId);
			if ( keepGoing ) {
				// has to be factored into a separate method b/c the personStatusChange() may need to trigger
				// an async action which need to complete before allowing this event to proceed.
				me.onDoPersonStatusChange(action, personId);
			}
		} else {
			var defaultMsg = 'Please select a student for which to change status';
			Ext.Msg.alert(
				me.textStore.getValueByCode('ssp.message.search.error-title','SSP Error'),
				me.textStore.getValueByCode('ssp.message.search.select-student-to-change-status',defaultMsg)
				);
		}
	},

	onDoPersonStatusChange: function(action, personId) {
		var me = this;
		if ( !(me.getView().isActiveTab) ) {
			return true; // otherwise you'll get the same event processed once for every tab you've visited
		}
		if(action == 'non-participating') {
			var dialog = Ext.create('Ssp.view.ProgramStatusChangeReasonWindow', {
				height: 150,
				width: 500
			}).show();
			return true;
		}
		var successEvent = {}
		if (action==='active') {
			programStatusId = Ssp.util.Constants.ACTIVE_PROGRAM_STATUS_ID;
			successEvent.programStatusName = Ssp.util.Constants.ACTIVE_PROGRAM_STATUS_NAME;
		}
		if (action==='no-show') {
			programStatusId = Ssp.util.Constants.NO_SHOW_PROGRAM_STATUS_ID;
			successEvent.programStatusName = Ssp.util.Constants.NO_SHOW_PROGRAM_STATUS_NAME;
		}
		if (action==='inactive') {
			programStatusId = Ssp.util.Constants.INACTIVE_PROGRAM_STATUS_ID;
			successEvent.programStatusName = Ssp.util.Constants.INACTIVE_PROGRAM_STATUS_NAME;
		}
		successEvent.programStatusId = programStatusId;
		successEvent.personId = personId;

		personProgramStatus = new Ssp.model.PersonProgramStatus();
		personProgramStatus.set('programStatusId', programStatusId );
		personProgramStatus.set('effectiveDate', Ext.Date.now() );
		me.getView().setLoading( true );
		me.personProgramStatusService.save(
			personId,
			personProgramStatus.data,
			{
				success: me.newSaveProgramStatusSuccess(successEvent),
				failure: me.saveProgramStatusFailure,
				scope: me
			});
		return true;
	},

	newSaveProgramStatusSuccess: function(successEvent) {
		var me = this;
		return function(r,scope) {
			me.saveProgramStatusSuccess(successEvent, r, scope);
		}
	},

	saveProgramStatusSuccess: function(successEvent, r, scope) {
		var me=scope;
		me.getView().setLoading( false );
		me.appEventsController.getApplication().fireEvent('afterPersonProgramStatusChange', successEvent);
	},

	saveProgramStatusFailure: function( r, scope) {
		var me=scope;
		var defaultMsg = 'Failed to process program status change. Please contact your system administrator.';
		Ext.Msg.alert(
			me.textStore.getValueByCode('ssp.message.search.error-title','SSP Error'),
			me.textStore.getValueByCode('ssp.message.search.failed-to-process-program-status-change',defaultMsg)
			);
		me.getView().setLoading( false );
	},

	onAfterPersonProgramStatusChange: function(event) {
		var me = this;
		var resultPerson = me.findResultPerson(event.personId);
		if ( resultPerson ) {
			resultPerson.set("currentProgramStatusName", event.programStatusName);
			this.initSearchGrid(false);
		}

		// No great, centralized place to act as a bus for these sort of change events... maybe the tool
		// nav component, which is currently responsible for loading that singleton person in the first place.
		// But it doesn't really handle any other state changes. So we opt instead for syncing up the models
		// here, close to where the "core" program status changing code is located. Maybe the 'correct' mechanism
		// is for the singleton person model to watch for change events on search result models?
		if ( me.person.get("id") === event.personId ) {
			me.person.set("currentProgramStatusName", event.programStatusName);
		}

		return true; // make sure event propagates
	},

    /*********** SEARCH BAR ***************/

	onSearchClick: function(button) {
		var me=this;
		me.setSearchCriteria();
		if ( me.searchCriteria.get('searchTerm') != "") {
			me.search();
		} else {
			me.clearSearch();
		}
	},

	setSearchCriteria: function(){
		var me=this;
		me.searchCriteria.set('searchTerm', searchTerm);
		me.searchCriteria.set('outsideCaseload', outsideCaseload);
	},

	clearSearch: function() {
		var me=this;
		me.searchStore.removeAll();
		me.searchStore.totalCount = 0;
		me.getSearchGridPager().onLoad();
	},

	onSearchKeyPress: function(comp,e){
		var me=this;
        if(e.getKey()==e.ENTER){
    		me.setSearchCriteria();
        	if ( me.searchCriteria.get('searchTerm') != "") {
    			me.search();
    		} else {
    			me.clearSearch();
    		}
        }
    },

	search: function(){
		var me=this;
		me.setGridView();
		if ( me.searchCriteria.get('searchTerm') != "") {
			me.getView().setLoading( true );
			me.searchService.search(
					Ext.String.trim(me.searchCriteria.get('searchTerm')),
					me.searchCriteria.get('outsideCaseload'),
					{
					success: me.searchSuccess,
					failure: me.searchFailure,
					scope: me
			});
		}
	},

    searchSuccess: function() {
    	var me=this;
        me.setGridView();
    	me.getView().setLoading( false );
    	me.personChanged();
		me.getSearchGridPager().onLoad();
    },

    searchFailure: function() {
    	var me=this;
		me.personLite.set('id', "");
    	me.appEventsController.getApplication().fireEvent('loadPerson');
    	me.getView().setLoading( false );
    },


    /**************** CASELOAD FILTERS *********************/

	onRetrieveCaseloadClick: function( button ) {
		var me=this;
        var skipCallBack = this.appEventsController.getApplication().fireEvent('retrieveCaseload',me);

        if(skipCallBack) {
        	if(me.getView().tabContext === 'watchList') {
        		me.getWatchList();
        	} else {
        		me.getCaseload();
        	}
        }

	},

	onCaseloadStatusComboSelect: function( comp, records, eOpts ) {
		var me=this;
		if ( records.length > 0) {
			me.caseloadFilterCriteria.set('programStatusId', records[0].get('id') );
			me.caseloadFilterCriteria.set('programStatusName', records[0].get('name') );
     	}

        var skipCallBack = this.appEventsController.getApplication().fireEvent('retrieveCaseload',me);
        if(skipCallBack) {
        	if(me.getView().tabContext === 'watchList') {
        		me.getWatchList();
        	} else {
        		me.getCaseload();
        	}
        }
	},
	buildBulkActionSearchCriteria: function() {
		var me = this;
		var criteria = {};

		if ( me.getIsCaseload() ) {
			criteria.myCaseload = true;
		} else if( me.getIsWatchList() ) {
			criteria.myWatchList = true;
		} else {
			// programmer error
			throw "Unrecognized search panel state";
		}
		return criteria;
	},
	onBulkProgramStatusChangeFailure: function(resp) {
		var me = this;
		me.appEventsController.loadMaskOff();
		if ( resp && resp.responseText ) {
			// Big chunk of this messaging copy/pasted from EmailStudentForm
			var rspTextStruct = Ext.decode(resp.responseText);
			if ( rspTextStruct.message && rspTextStruct.message.indexOf("Person search parameters matched no records") > -1 ) {
				var defaultMsg = 'No user records matched your current search criteria. <br/><br/> Retry with different search criteria.';
				Ext.Msg.alert(
					me.textStore.getValueByCode('ssp.message.search.error-title','SSP Error'),
					me.textStore.getValueByCode('ssp.message.search.no-user-match',defaultMsg)
					);
			} else {
				var defaultMsg = 'There was an issue processing your bulk program status change request. Please contact your system administrator.';
				Ext.Msg.alert(
					me.textStore.getValueByCode('ssp.message.search.error-title','SSP Error'),
					me.textStore.getValueByCode('ssp.message.search.bulk-program-status-change-error-body',defaultMsg)
					);
			}
		} else {
			var defaultMsg = 'There was an issue processing your bulk program status change request. Please contact your system administrator.';
			Ext.Msg.alert(
				me.textStore.getValueByCode('ssp.message.search.error-title','SSP Error'),
				me.textStore.getValueByCode('ssp.message.search.bulk-program-status-change-error-body',defaultMsg)
				);
		}
	},
	onBulkProgramStatusChangeSuccess: function() {
		var me = this;
		me.appEventsController.loadMaskOff();
		var defaultMsg = 'Your bulk Program Status change request has ' +
                     'been queued successfully. Bulk changes are processed gradually and may not be reflected immediately on-screen.';
		Ext.Msg.alert(
			me.textStore.getValueByCode('ssp.message.search.bulk-program-status-change-queued-title','Bulk Program Status Change Request Queued'),
			me.textStore.getValueByCode('ssp.message.search.bulk-program-status-change-queued-body',defaultMsg)
			);
	},
	doBulkProgramStatusChange: function(action, programStatusChangeReasonId, programStatusChangeReasonName) {
		var me = this;

		var model = Ext.create('Ssp.model.BulkProgramStatusChangeRequest', {
			programStatusSpec: Ext.create('Ssp.model.PersonProgramStatus', {
				programStatusId: me.translateProgramStatusActionToDomainId(action),
				programStatusChangeReasonId: programStatusChangeReasonId === undefined ? null : programStatusChangeReasonId
			}).data, // if you just attach the model itself, the way we do JSON serialization will result in a whole bunch
					 // of Ext.js infrastructure fields being sent
			criteria: me.buildBulkActionSearchCriteria()
		});

		var url = me.apiProperties.createUrl( me.apiProperties.getItemUrl('bulk') )+'/programStatus';
		var jsonData = model.data;

		me.apiProperties.makeRequest({
			url: url,
			method: 'POST',
			jsonData: jsonData,
			successFunc: me.onBulkProgramStatusChangeSuccess,
			failureFunc: me.onBulkProgramStatusChangeFailure,
			scope: me
		});
	},
	newDoBulkProgramStatusChange: function(action) {
		var me = this;
		return function(programStatusChangeReasonId, programStatusChangeReasonName) {
			me.doBulkProgramStatusChange(action, programStatusChangeReasonId, programStatusChangeReasonName);
		}
	},
	cancelBulkProgramStatusChange: function(action) {
		var me = this;
		me.appEventsController.loadMaskOff();
	},
	newCancelBulkProgramStatusChange: function(action) {
		var me = this;
		return function() {
			me.cancelBulkProgramStatusChange(action);
		}
	},
	bulkProgramStatusChange: function(action) {
		var me = this;
		me.appEventsController.loadMaskOn();
		if(action === 'PROGRAM_STATUS_NON_PARTICIPATING') {
			var dialog = Ext.create('Ssp.view.ProgramStatusChangeReasonWindow', {
				height: 150,
				width: 500,
				isBulk: true,
				actionCallbacks: {
					ok: me.newDoBulkProgramStatusChange(action),
					cancel: me.newCancelBulkProgramStatusChange(action),
					scope: me
				}
			}).show();
			if ( !(dialog) || !(dialog.isVisible()) ) {
				if ( dialog && Ext.isFunction(dialog.destroy) ) {
					dialog.destroy();
				}
				me.cancelBulkProgramStatusChange(action);
			} // otherwise dialog takes care of destroying itself.
		} else {
			me.doBulkProgramStatusChange(action);
		}
	},
	onBulkWatchChangeFailure: function(resp, action) {
		var me = this;
		var displayName = me.translateWatchActionToDisplayName(action);
		me.appEventsController.loadMaskOff();
		if ( resp && resp.responseText ) {
			// Big chunk of this messaging copy/pasted from EmailStudentForm
			var rspTextStruct = Ext.decode(resp.responseText);
			if ( rspTextStruct.message && rspTextStruct.message.indexOf("Person search parameters matched no records") > -1 ) {
				var defaultMsg = 'No user records matched your current search criteria. <br/><br/> Retry with different search criteria.';
				Ext.Msg.alert(
					me.textStore.getValueByCode('ssp.message.search.error-title','SSP Error'),
					me.textStore.getValueByCode('ssp.message.search.no-user-match',defaultMsg)
					);
			} else {
				var defaultMsg = 'There was an issue processing your bulk %DISPLAY-NAME% change request. Please contact your system administrator.';
				Ext.Msg.alert(
					me.textStore.getValueByCode('ssp.message.search.error-title','SSP Error'),
					me.textStore.getValueByCode('ssp.message.search.bulk-watch-change-error-body',defaultMsg,{'%DISPLAY-NAME%':displayName})
					);
			}
		} else {
			var defaultMsg = 'There was an issue processing your bulk %DISPLAY-NAME% change request. Please contact your system administrator.';
			Ext.Msg.alert(
				me.textStore.getValueByCode('ssp.message.search.error-title','SSP Error'),
				me.textStore.getValueByCode('ssp.message.search.bulk-watch-change-error-body',defaultMsg,{'%DISPLAY-NAME%':displayName})
				);
		}
	},
	onBulkWatchChangeSuccess: function(action) {
		var me = this;
		var displayName = me.translateWatchActionToDisplayName(action);
		me.appEventsController.loadMaskOff();

		var defaultMsg = 'Your bulk %DISPLAY-NAME% request has ' +
                     'been queued successfully. Bulk changes are processed gradually and may not be reflected immediately on-screen.';
		Ext.Msg.alert(
			me.textStore.getValueByCode('ssp.message.search.bulk-watch-change-success-title','Bulk %DISPLAY-NAME% Request Queued',{'%DISPLAY-NAME%':displayName}),
			me.textStore.getValueByCode('ssp.message.search.bulk-watch-change-success-body',defaultMsg,{'%DISPLAY-NAME%':displayName})
			);
	},
	newOnBulkWatchChangeFailure: function(action) {
		var me = this;
		return function(resp) {
			me.onBulkWatchChangeFailure(resp, action);
		}
	},
	newOnBulkWatchChangeSuccess: function(action) {
		var me = this;
		return function() {
			me.onBulkWatchChangeSuccess(action);
		}
	},
	bulkWatchChange: function(action) {
		var me = this;
		me.appEventsController.loadMaskOn();

		var model = {
			watchSpec: {
				operation: me.translateWatchActionToDomainOperation(action),
				watcherId: me.authenticatedPerson.get('id')
			},
			criteria: me.buildBulkActionSearchCriteria()
		};

		var url = me.apiProperties.createUrl( me.apiProperties.getItemUrl('bulk') )+'/watch';
		var jsonData = model;

		me.apiProperties.makeRequest({
			url: url,
			method: 'POST',
			jsonData: jsonData,
			successFunc: me.newOnBulkWatchChangeSuccess(action),
			failureFunc: me.newOnBulkWatchChangeFailure(action),
			scope: me
		});
	},
	bulkEmail: function() {
		var me=this;
		if ( me.emailStudentPopup ) {
			me.emailStudentPopup.destroy();
		}
		me.emailStudentPopup = Ext.create('Ssp.view.EmailStudentView',{
			isBulk: true,
			bulkCriteria: me.buildBulkActionSearchCriteria()
		});
		me.emailStudentPopup.show();
	},
	exportSearch: function(searchType) {
		var me = this;
		var url = me.exportService.buildExportCaseloadUrl(me.translateSelectedStatusToSearchableStatus(),
														me.translateCurrentTabToApiPath(), { limit: -1 });
		window.open(url,'_blank');
	},
	onExportConfirm: function(btnId) {
		var me = this;
		if (btnId=="ok") {
			me.exportSearch();
		}
	},
	onBulkEmailConfirm: function(btnId) {
		var me = this;
		if (btnId=="ok") {
			me.bulkEmail();
		}
	},
	onBulkProgramStatusChangeConfirm: function(btnId, action) {
		var me = this;
		if (btnId=="ok") {
			me.bulkProgramStatusChange(action);
		}
	},
	onBulkWatchChangeConfirm: function(btnId, action) {
		var me = this;
		if (btnId=="ok") {
			me.bulkWatchChange(action);
		}
	},
	newOnBulkProgramStatusChangeConfirm: function(action) {
		var me = this;
		return function(btnId) {
			me.onBulkProgramStatusChangeConfirm(btnId, action);
		}
	},
	newOnBulkWatchChangeConfirm: function(action) {
		var me = this;
		return function(btnId) {
			me.onBulkWatchChangeConfirm(btnId, action);
		}
	},
	promptWithExportCount: function(count) {
		var me = this;
		var defaultMsg = "%COUNT% user/s will be exported. <span style=\";font-weight: bold\">Continue</span> ?";
		count = parseInt(count);
		// loadMaskOff() copy/pasted in all prompt*() functions to try to delay that dismissal as long
		// as possible... let all 'background' lookup and computation complete before we re-engage the UI
		me.appEventsController.loadMaskOff();

		Ext.Msg.confirm({
			title: me.textStore.getValueByCode('ssp.message.search.confirm-title','Confirm'),
			msg: me.textStore.getValueByCode('ssp.message.search.confirm-export-count',defaultMsg,{'%COUNT%':count}),
			buttons: Ext.Msg.OKCANCEL,
			fn: me.onExportConfirm,
			scope: me
		});
	},
	promptWithBulkEmailCount: function(count) {
		var me = this;
		count = parseInt(count);
		var maxEmail =  parseInt(me.configStore.getConfigByName('mail_bulk_message_limit').trim());
		// loadMaskOff() copy/pasted in all prompt*() functions to try to delay that dismissal as long
		// as possible... let all 'background' lookup and computation complete before we re-engage the UI
		me.appEventsController.loadMaskOff();
		if(maxEmail > 0 && count > maxEmail) {
			var defaultMsg = 'The number of students in your request (%COUNT%) exceed the ' +
				  		 'bulk email limit (%MAX-EMAIL%). <br/><br/>Consider exporting results to a CSV file and using that ' +
						 'file as input to a third party bulk email application.';
			Ext.Msg.alert(
				me.textStore.getValueByCode('ssp.message.search.bulk-email-too-many-title','Too Many Search Results'),
				me.textStore.getValueByCode('ssp.message.search.bulk-email-too-many-body',defaultMsg,{'%COUNT%':count,'%MAX-EMAIL%':maxEmail})
				);
			return;
		} else if ( count === 0 ) {
			var defaultMsg = 'Cannot send bulk email to an empty caseload/watchlist/search result.';
			Ext.Msg.alert(
				me.textStore.getValueByCode('ssp.message.search.bulk-email-too-few-title','Too Few Search Results'),
				me.textStore.getValueByCode('ssp.message.search.bulk-email-too-few-body',defaultMsg)
				);
			return;
		} else {
			var defaultMsg = '%COUNT% users/s will be emailed. If a Journal Entry is selected when composing the email, the actual number MAY BE LESS. (Selected users who are not currently activated (typically unassigned) will be excluded from the Journal Entry creation.) <br/><br/><span style=\";font-weight: bold\">Continue</span>?';
			Ext.Msg.confirm({
				title: me.textStore.getValueByCode('ssp.message.search.confirm-title','Confirm'),
				msg: me.textStore.getValueByCode('ssp.message.search.confirm-email-count',defaultMsg,{'%COUNT%':count}),
				buttons: Ext.Msg.OKCANCEL,
				fn: me.onBulkEmailConfirm,
				scope: me
			});
		}
	},
	promptWithBulkProgramStatusChangeCount: function(count, action) {
		var me = this;
		var message;
		count = parseInt(count);
		var programStatusName = me.translateProgramStatusActionToDisplayName(action);
		// loadMaskOff() copy/pasted in all prompt*() functions to try to delay that dismissal as long
		// as possible... let all 'background' lookup and computation complete before we re-engage the UI

		me.appEventsController.loadMaskOff();
		if ( programStatusName === null ) {
			Ext.Msg.alert(
				me.textStore.getValueByCode('ssp.message.search.error-title','SSP Error'),
				me.textStore.getValueByCode('ssp.message.search.unrecognized-target-program-status-body','Unrecognized target Program Status.')
				);
			return;
		}
		if ( count === 0 ) {
			Ext.Msg.alert(
				me.textStore.getValueByCode('ssp.message.search.change-program-status-empty-search-title','Too Few Search Results'),
				me.textStore.getValueByCode('ssp.message.search.change-program-status-empty-search-body','Cannot change Program Status on an empty caseload/watchlist/search result.')
				);
			return;
		}
		var confirmMsg = '';
		if(action === 'PROGRAM_STATUS_NON_PARTICIPATING'){
			var defaultMsg = "<p>ATTENTION: %COUNT% users have been selected for consideration to transition to '%PROGRAM-STATUS-NAME%' status with a bulk reason" +
			    " (see next dialog).<p> <p> Actual number MAY BE LESS. </p><p>Selected users " +
				" with '%PROGRAM-STATUS-NAME%'  status, and selected users who are not currently activated (typically unassigned)" +
				" and non-students will be excluded from this action.</p><p style=\"text-align:center;font-weight: bold\"><br/> Continue ?</p>";
			confirmMsg = me.textStore.getValueByCode('ssp.message.search.confirm-bulk-non-participating-program-status-change-count',defaultMsg,{'%COUNT%':count,'%PROGRAM-STATUS-NAME%':programStatusName})
		} else {
			var defaultMsg = "<p>ATTENTION: %COUNT% users have been selected for consideration to transition to '%PROGRAM-STATUS-NAME%' status. Actual number MAY BE LESS. </p><p>Selected students " +
				"already with '%PROGRAM-STATUS-NAME%' status and selected students who are not currently activated (typically unassigned) and non-students will be excluded from this action.</p><p style=\"text-align:center;font-weight: bold\"><br/> Continue ?</p>";
			confirmMsg = me.textStore.getValueByCode('ssp.message.search.confirm-bulk-program-status-change-count',defaultMsg,{'%COUNT%':count,'%PROGRAM-STATUS-NAME%':programStatusName});
		}

		Ext.Msg.confirm({
			title: me.textStore.getValueByCode('ssp.message.search.confirm-title','Confirm'),
			msg: confirmMsg,
			buttons: Ext.Msg.OKCANCEL,
			fn: me.newOnBulkProgramStatusChangeConfirm(action),
			scope: me
		});
	},
	promptWithBulkWatchChangeCount: function(count, action) {
		var me = this;
		var message;
		count = parseInt(count);
		var watchActionName = me.translateWatchActionToDisplayName(action);
		// loadMaskOff() copy/pasted in all prompt*() functions to try to delay that dismissal as long
		// as possible... let all 'background' lookup and computation complete before we re-engage the UI

		me.appEventsController.loadMaskOff();
		if ( watchActionName === null ) {
			Ext.Msg.alert(
				me.textStore.getValueByCode('ssp.message.search.error-title','SSP Error'),
				me.textStore.getValueByCode('ssp.message.search.unrecognized-watch-action-body','Unrecognized Watch action.')
				);
			return;
		}
		if ( count === 0 ) {
			var defaultMsg = 'Cannot %WATCH-ACTION-NAME% an empty caseload/watchlist/search result.';
            Ext.Msg.alert(
				me.textStore.getValueByCode('ssp.message.search.watch-action-no-results-title','Too Few Search Results'),
				me.textStore.getValueByCode('ssp.message.search.watch-action-no-results-body',defaultMsg,{'%WATCH-ACTION-NAME%':watchActionName})
				);
			return;
		}
		var defaultMsg = "<p>ATTENTION: %COUNT% selected users will be considered for %WATCH-ACTION-NAME%"
	        ". Actual number MAY BE LESS.</p><p>(Selected users " +
			"who are not currently activated (typically unassigned) will be excluded from this action.)</p><p style=\"text-align:center;font-weight: bold\"><br/>Continue ?</p>";
		Ext.Msg.confirm({
			title: me.textStore.getValueByCode('ssp.message.search.confirm-title','Confirm'),
			msg: me.textStore.getValueByCode('ssp.message.search.confirm-watch-action',defaultMsg,{'%WATCH-ACTION-NAME%':watchActionName,'%COUNT%':count}),
			buttons: Ext.Msg.OKCANCEL,
			fn: me.newOnBulkWatchChangeConfirm(action),
			scope: me
		});
	},

	promptWithCustomizableNotice: function() {
	    var me = this;
	    me.appEventsController.loadMaskOff();
		Ext.Msg.alert(
			me.textStore.getValueByCode('ssp.message.search.customizable-export-only-avail-search-tab-title','Only Available in Search Tab'),
			me.textStore.getValueByCode('ssp.message.search.customizable-export-only-avail-search-tab-body','The Customizable Export is only Available in the Search Tab.')
			);
	},

	onBulkActionCountFailure: function(cnt, action) {
		var me = this;
		me.appEventsController.loadMaskOff();
		var defaultMsg = 'Failed to look up the number of records which would be affected by the ' +
						 'requested action. Retry or contact your system administrator';
		Ext.Msg.alert(
			me.textStore.getValueByCode('ssp.message.search.error-title','SSP Error'),
			me.textStore.getValueByCode('ssp.message.search.bulk-action-count-body',defaultMsg)
			);
	},
	onBulkActionCountSuccess: function(cnt, action) {
		var me = this;
		if ( action === 'EXPORT' ) {
			me.promptWithExportCount(cnt);
		} else if ( action === 'EMAIL' ) {
			me.promptWithBulkEmailCount(cnt);
		} else if ( action.indexOf('PROGRAM_STATUS_') === 0 ) {
			me.promptWithBulkProgramStatusChangeCount(cnt, action);
		} else if ( action.indexOf('WATCH') !== -1 ) {
            me.promptWithBulkWatchChangeCount(cnt, action);
        } else if ( action === 'CUSTOM_EXPORT' ) {
            me.promptWithCustomizableNotice();
		} else {
			Ext.Msg.alert(
				me.textStore.getValueByCode('ssp.message.search.error-title','SSP Error'),
				me.textStore.getValueByCode('ssp.message.search.unrecognized-bulk-action-body','Unrecognized bulk action request')
				);
		}
	},
	newBulkActionCountResultFailureCallback: function(action) {
		var me = this;
		return function(cnt) {
			return me.onBulkActionCountFailure(cnt, action);
		}
	},
	newBulkActionCountResultSuccessCallback: function(action) {
		var me = this;
		return function(cnt) {
			return me.onBulkActionCountSuccess(cnt, action);
		}
	},
	translateCurrentTabToApiPath: function() {
		var me = this;
		if ( me.getIsCaseload() ) {
			return 'caseload';
		}
		if ( me.getIsWatchList() ) {
			return 'watchlist';
		}
		return 'search';
	},
	translateSelectedStatusToSearchableStatus: function() {
		var me = this;
		if ( me.getCaseloadStatusCombo() ) {
            if ( !(me.getCaseloadStatusCombo().getValue()) ) {
                 var defaultProgramStatusConfigValue = me.configStore.getConfigByName('my_caseload_watchlist_default_program_status');
                return me.translateMyCaseloadWatchlistProgramStatusConfigValueToDomainId(defaultProgramStatusConfigValue);
            } else if ( me.getCaseloadStatusCombo().getValue() === 'All' ) {
                return null;
            } else {
                return me.getCaseloadStatusCombo().getValue()
            }
		}
	},
	translateProgramStatusActionToDisplayName: function(action) {
		switch (action) {
			case 'PROGRAM_STATUS_ACTIVE':
				return Ssp.util.Constants.ACTIVE_PROGRAM_STATUS_NAME;
			case 'PROGRAM_STATUS_INACTIVE':
				return Ssp.util.Constants.INACTIVE_PROGRAM_STATUS_NAME;
			case 'PROGRAM_STATUS_NON_PARTICIPATING':
				return Ssp.util.Constants.NON_PARTICIPATING_PROGRAM_STATUS_NAME;
			case 'PROGRAM_STATUS_NO_SHOW':
				return Ssp.util.Constants.NO_SHOW_PROGRAM_STATUS_NAME;
		}
		return null;
	},
	translateProgramStatusActionToDomainId: function(action) {
		switch (action) {
			case 'PROGRAM_STATUS_ACTIVE':
				return Ssp.util.Constants.ACTIVE_PROGRAM_STATUS_ID;
			case 'PROGRAM_STATUS_INACTIVE':
				return Ssp.util.Constants.INACTIVE_PROGRAM_STATUS_ID;
			case 'PROGRAM_STATUS_NON_PARTICIPATING':
				return Ssp.util.Constants.NON_PARTICIPATING_PROGRAM_STATUS_ID;
			case 'PROGRAM_STATUS_NO_SHOW':
				return Ssp.util.Constants.NO_SHOW_PROGRAM_STATUS_ID;
		}
		return null;
	},
	translateWatchActionToDomainOperation: function(action) {
		return action;
	},
	translateWatchActionToDisplayName: function(action) {
		switch (action) {
			case 'WATCH':
				return 'Watch';
			case 'UNWATCH':
				return 'Unwatch';
		}
		return null;
	},
	onCaseloadActionComboSelect: function( comp, records, eOpts ){
		var me=this;
		if(me.getIsSearch()) {
			me.appEventsController.getApplication().fireEvent('onSearchActionComboSelect', records);
			comp.setValue(null);
			return;
		}
		if(records.length > 0) {
			// TODO this really needs to be moved into a modal dialog, else these counts could come back and be
			// completely irrelevant w/r/t whatever the user is looking at currently. The loading mask is not
			// modal, but it's the short-term stopgap measure to try to discourage people from interacting with
			// the UI while we look up the count.
			me.appEventsController.loadMaskOn();
			if(me.getIsCaseload()) {
				me.caseloadService.getCaseloadCount(me.translateSelectedStatusToSearchableStatus(), {
					success: me.newBulkActionCountResultSuccessCallback(records[0].get('id')),
					failure: me.newBulkActionCountResultFailureCallback(records[0].get('id')),
					scope: me
				});
			} else if(me.getIsWatchList()) {
				me.watchListService.getWatchlistCount(me.translateSelectedStatusToSearchableStatus(), {
					success: me.newBulkActionCountResultSuccessCallback(records[0].get('id')),
					failure: me.newBulkActionCountResultFailureCallback(records[0].get('id')),
					scope: me
				});
			}
			comp.setValue(null);
		}
	},

	translateMyCaseloadWatchlistProgramStatusConfigValueToDomainId: function(configValue) {
    	switch (configValue) {
            case Ssp.util.Constants.MY_CASELOAD_WATCHLIST_ALL_PROGRAM_STATUS_CONFIG_VALUE:
                return Ssp.util.Constants.MY_CASELOAD_WATCHLIST_ALL_PROGRAM_STATUS_ID;
            case Ssp.util.Constants.MY_CASELOAD_WATCHLIST_ACTIVE_PROGRAM_STATUS_CONFIG_VALUE:
                return Ssp.util.Constants.MY_CASELOAD_WATCHLIST_ACTIVE_PROGRAM_STATUS_ID;
            case Ssp.util.Constants.MY_CASELOAD_WATCHLIST_INACTIVE_PROGRAM_STATUS_CONFIG_VALUE:
                return Ssp.util.Constants.MY_CASELOAD_WATCHLIST_INACTIVE_PROGRAM_STATUS_ID;
            case Ssp.util.Constants.MY_CASELOAD_WATCHLIST_NON_PARTICIPATING_PROGRAM_STATUS_CONFIG_VALUE:
                return Ssp.util.Constants.MY_CASELOAD_WATCHLIST_NON_PARTICIPATING_PROGRAM_STATUS_ID;
            case Ssp.util.Constants.MY_CASELOAD_WATCHLIST_NON_SHOW_PROGRAM_STATUS_CONFIG_VALUE:
                return Ssp.util.Constants.MY_CASELOAD_WATCHLIST_NON_SHOW_PROGRAM_STATUS_ID;
            case Ssp.util.Constants.MY_CASELOAD_WATCHLIST_TRANSITIONED_PROGRAM_STATUS_CONFIG_VALUE:
                return Ssp.util.Constants.MY_CASELOAD_WATCHLIST_TRANSITIONED_PROGRAM_STATUS_ID;
		}
		return Ssp.util.Constants.MY_CASELOAD_WATCHLIST_ACTIVE_PROGRAM_STATUS_ID;
	},

	getProgramStatuses: function() {
		var me=this;
		me.programStatusService.getAll({
			success:me.getProgramStatusesSuccess,
			failure:me.getProgramStatusesFailure,
			scope: me
	    });
	},

    getProgramStatusesSuccess: function(r, scope) {
    	var me=scope;
    	var activeProgramStatusId = "";
    	var programStatus;
    	if (me.programStatusesStore && me.getCaseloadStatusCombo() && me.programStatusesStore.getCount() > 0) {
    	        var defaultProgramStatusConfigValue = me.configStore.getConfigByName('my_caseload_watchlist_default_program_status');
    			me.getCaseloadStatusCombo().setValue(me.translateMyCaseloadWatchlistProgramStatusConfigValueToDomainId(defaultProgramStatusConfigValue));
    	}
    },

    getProgramStatusesFailure: function( r, scope) {
    	var me=scope;
    },
	getWatchList: function() {
    	var me=this;
		me.setGridView();
		me.getView().setLoading( true );
		me.watchListService.getWatchList(
			me.translateSelectedStatusToSearchableStatus(),
			me.watchListStore,
			{
				success:me.getCaseloadSuccess,
				failure:me.getCaseloadFailure,
				scope: me
			});
	},
	getCaseload: function() {
    	var me=this;
		me.preferences.set('SEARCH_GRID_VIEW_TYPE',1);
		me.setGridView();
		me.loadCaseload();
	},
	
	loadCaseload: function() {
		var me=this;
		me.getView().setLoading( true );
		me.caseloadService.getCaseload(
			me.translateSelectedStatusToSearchableStatus(),
			me.caseloadStore,
			{
				success:me.getCaseloadSuccess,
				failure:me.getCaseloadFailure,
				scope: me
			});
	},

    getCaseloadSuccess: function( r, scope) {
    	var me=scope;
    	if(me.getSearchGridPager) {
    		me.getSearchGridPager().onLoad();
    	}
		me.harmonizePersonLite();
		if ( me.getIsCaseload() && me.getCaseloadActionCombo() ) {
            if(me.caseloadStore.data.length < 1) {
                me.getCaseloadActionCombo().hide();
            } else {
                if (me.authenticatedPerson.hasAnyBulkPermissions()) {
                    me.getCaseloadActionCombo().show();

                }
            }
        } else {
            if( me.getIsWatchList() ) {
                if(me.watchListStore.getTotalCount() < 1) {
                    me.getCaseloadActionCombo().hide();
                } else {
                    if (me.authenticatedPerson.hasAnyBulkPermissions()) {
                        me.getCaseloadActionCombo().show();
                    }
                }
            }
		}
		me.getView().setLoading( false );
    },

    getCaseloadFailure: function( r, scope) {
    	var me=scope;
    	me.getView().setLoading( false );
    },

    instantCaseloadAssignment: function(record) {
    	var me=this;
		if (me.instantCaseload == null || me.instantCaseload.isDestroyed) {
       		me.instantCaseload = Ext.create('Ssp.view.person.InstantCaseloadAssignment',{hidden:true,
       			schoolIdValue:record.get("schoolId"),
       			coachIdValue:record.get("coachId"),
       			studentTypeNameValue:record.get("studentTypeName"),
       			campusNameValue:record.get("campusName")});
        }
		me.instantCaseload.show();
    },

    onUpdateSearchStoreRecord:function(params) {
    	var me = this;
		
		/* view is set to caseload always on return, not sure why this is work around so caseload and search are test, SSP-3096*/
		var record = me.caseloadStore.findRecord("schoolId", params.person.get("schoolId"), 0, false, false, true);
		
		if(record == null) {
			record = me.searchStore.findRecord("schoolId", params.person.get("schoolId"), 0, false, false, true);
    	}
		record.set("id", params.person.get("id"));
    	record.set("firstName", params.person.get("firstName"));
    	record.set("middleName", params.person.get("middleName"));
    	record.set("lastName", params.person.get("lastName"));
		var coach = params.person.get("coach");
		if (coach) {
			record.set("coachLastName",  coach.lastName);
			record.set("coachFirstName",  coach.firstName);
			record.set("coachId",   coach.id);
		}
    	record.set("currentProgramStatusName",params.person.get("currentProgramStatusName"));
    	me.updatePerson(record);
    },
	
	onSearchButtonClick: function(button){
			var me = this;
			me.getAddPersonButton().show();
			this.appEventsController.getApplication().fireEvent("onStudentSearchRequested");
	},
	
	onResetClick: function(button){
		var me=this;
		Ext.ComponentQuery.query('#searchBar')[2].getForm().reset();
		me.searchStore.removeAll();
		me.appEventsController.getApplication().fireEvent("onPersonSearchSuccess");
	}
});