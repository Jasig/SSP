Ext.define('Ssp.view.Search', {
	extend: 'Ext.grid.Panel',
	alias : 'widget.search',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.SearchViewController',
    inject: {
    	authenticatedPerson: 'authenticatedPerson',
    	apiProperties: 'apiProperties',
    	columnRendererUtils: 'columnRendererUtils',
    	programStatusesStore: 'programStatusesStore',
    	sspConfig: 'sspConfig'
    },
    initComponent: function(){
    	var me=this;
    	Ext.applyIf(me,
    			   {
    		        submitEmptyText: false,
    				title: 'Students',
    	            collapsible: true,
    	            collapseDirection: 'left',
    	        	width: '100%',
    	        	height: '100%',
		    	    columns: [{ text: 'Name', dataIndex: 'lastName', renderer: me.columnRendererUtils.renderSearchStudentName, flex: 50}],
        
		    	    dockedItems: [{
		       			xtype: 'pagingtoolbar',
		       		    itemId: 'searchGridPager',
		       			dock: 'bottom',
		       		    displayInfo: true,
		       		    pageSize: me.apiProperties.getPagingSize()
		       		},{
		       			xtype: 'toolbar',
		       			dock: 'top',
		       			itemId: 'searchBar',
	    	            hidden: !me.authenticatedPerson.hasAccess('STUDENT_SEARCH'),
		       		    items: [{
				        	xtype: 'textfield',
		   		        	itemId: 'searchText',
		   		        	enableKeyEvents: true,
		   		        	emptyText: 'Name or ' + me.sspConfig.get('studentIdAlias'),
		   		        	width: 200
		   		        },{
		   		        	xtype: 'button',
		   		        	tooltip: 'Find a Student',
		   		        	itemId: 'searchButton',
				            width: 32,
				            height: 32,
				            cls: 'searchIcon'
		   		        },{
		   		        	xtype: 'tbspacer',
		   		        	width: 5
		   		        },{
		   		        	xtype: 'checkboxfield',
		                    boxLabel  : 'My Caseload',
		                    itemId: 'searchCaseloadCheck',
		                    name      : 'searchInCaseload',
		                    hidden: !me.authenticatedPerson.hasAccess('CASELOAD_SEARCH'),
		                    inputValue: false
		                },{
	    		        	xtype: 'tbspacer',
	    		        	flex: 1
		    		    },{
		    	            tooltip: 'Display Caseload Filters',
		    	            text: '',
		    	            width: 25,
		    	            height: 25,
		    	            hidden: false,
		    	            cls: 'displayCaseloadIcon',
		    	            xtype: 'button',
		    	            itemId: 'displayCaseloadBarButton'				        	
		    	        }]
		       		},{
		       			xtype: 'toolbar',
		       			dock: 'top',
		       			itemId: 'caseloadBar',
		       			hidden: !me.authenticatedPerson.hasAccess('CASELOAD_FILTERS'),
		       		    items: [{
					        xtype: 'combobox',
					        itemId: 'caseloadStatusCombo',
					        name: 'programStatusId',
					        fieldLabel: '',
					        emptyText: 'Select One',
					        store: me.programStatusesStore,
					        valueField: 'id',
					        displayField: 'name',
					        mode: 'local',
					        typeAhead: false,
					        editable: false,
					        queryMode: 'local',
					        allowBlank: true,
					        forceSelection: false,
					        width: 200,
					        labelWidth: 125
						},{
				        	xtype: 'button',
				        	tooltip: 'Retrieve My Caseload',
				        	itemId: 'retrieveCaseloadButton',
				            width: 32,
				            height: 32,
				            cls: 'retrieveCaseloadIcon'
				        },{
	    		        	xtype: 'tbspacer',
	    		        	flex: 1
		    		    },{
		    	            tooltip: 'Display Search Filters',
		    	            text: '',
		    	            width: 25,
		    	            height: 25,
		    	            hidden: false,
		    	            cls: 'displaySearchIcon',
		    	            xtype: 'button',
		    	            itemId: 'displaySearchBarButton'				        	
		    	        }]
		       		    
		       		},{
		       			xtype: 'toolbar',
		       			dock: 'top',
		       			items: [{
		    			    tooltip: 'Add Student',
		    			    text: '',
		    			    width: 25,
		    			    height: 25,
		    			    hidden: !me.authenticatedPerson.hasAccess('ADD_STUDENT_BUTTON'),
		    			    cls: 'addPersonIcon',
		    			    xtype: 'button',
		    			    itemId: 'addPersonButton'
		    			},{
		    			    tooltip: 'Edit Student',
		    			    text: '',
		    			    width: 25,
		    			    height: 25,
		    			    hidden: !me.authenticatedPerson.hasAccess('EDIT_STUDENT_BUTTON'),
		    			    cls: 'editPersonIcon',
		    			    xtype: 'button',
		    			    itemId: 'editPersonButton'
		    			},{
		    			    tooltip: 'Delete Student',
		    			    text: '',
		    			    width: 25,
		    			    height: 25,
		    			    hidden: !me.authenticatedPerson.hasAccess('DELETE_STUDENT_BUTTON'),
		    			    cls: 'deletePersonIcon',
		    			    xtype: 'button',
		    			    itemId: 'deletePersonButton'
		    			},{
	    		        	xtype: 'tbspacer',
	    		        	flex: 1
		    		    },{
		    			    tooltip: 'Set Student to Active status',
		    			    text: '',
		    			    width: 25,
		    			    height: 25,
		    			    hidden: !me.authenticatedPerson.hasAccess('SET_ACTIVE_STATUS_BUTTON'),
		    			    cls: 'setActiveStatusIcon',
		    			    xtype: 'button',
		    			    action: 'active',
		    			    itemId: 'setActiveStatusButton'
			    		},{
		    			    tooltip: 'Transition Student',
		    			    text: '',
		    			    width: 25,
		    			    height: 25,
		    			    hidden: !me.authenticatedPerson.hasAccess('SET_TRANSITION_STATUS_BUTTON'),
		    			    cls: 'setTransitionStatusIcon',
		    			    xtype: 'button',
		    			    action: 'transition',
		    			    itemId: 'setTransitionStatusButton'
		    			},{
		    			    tooltip: 'Set Student to Non-Participating status',
		    			    text: '',
		    			    width: 25,
		    			    height: 25,
		    			    hidden: !me.authenticatedPerson.hasAccess('SET_NON_PARTICIPATING_STATUS_BUTTON'),
		    			    cls: 'setNonParticipatingStatusIcon',
		    			    xtype: 'button',
		    			    action: 'non-participating',
		    			    itemId: 'setNonParticipatingStatusButton'
		    			},{
		    			    tooltip: 'Set Student to No-Show status',
		    			    text: '',
		    			    width: 25,
		    			    height: 25,
		    			    hidden: !me.authenticatedPerson.hasAccess('SET_NO_SHOW_STATUS_BUTTON'),
		    			    cls: 'setNoShowStatusIcon',
		    			    xtype: 'button',
		    			    action: 'no-show',
		    			    itemId: 'setNoShowStatusButton'
		    			}]
		       		}]
		    	    });
    	
    	return me.callParent(arguments);
    }
});