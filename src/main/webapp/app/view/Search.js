Ext.define('Ssp.view.Search', {
	extend: 'Ext.grid.Panel',
	alias : 'widget.search',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.SearchViewController',
    inject: {
    	appEventsController: 'appEventsController',
    	authenticatedPerson: 'authenticatedPerson',
    	apiProperties: 'apiProperties',
    	columnRendererUtils: 'columnRendererUtils',
        store: 'searchStore',
        programStatusesStore: 'programStatusesStore',
        sspConfig: 'sspConfig'
    },
    initComponent: function(){
    	var me=this;
    	Ext.apply(me,
    			   {
    		        submitEmptyText: false,
    				title: 'Students',
    	            collapsible: true,
    	            collapseDirection: 'left',
    	        	width: '100%',
    	        	height: '100%',
		    	    columns: [
		    	              { text: 'Name', dataIndex: 'lastName', renderer: me.columnRendererUtils.renderSearchStudentName, flex: 50}
		    	              ],
        
		    	    dockedItems: [{
		       			xtype: 'pagingtoolbar',
		       		    itemId: 'searchGridPager',
		       			dock: 'bottom',
		       		    displayInfo: true,
		       		    pageSize: me.apiProperties.getPagingSize()
		       		},{
		       			xtype: 'toolbar',
		       			dock: 'top',
	    	            hidden: !me.authenticatedPerson.hasAccess('STUDENT_SEARCH'),
		       			defaults: {
		       				labelWidth: 50
		       			},
		       		    items: [
		       		        {
		       		        	xtype: 'textfield',
		       		        	itemId: 'searchText',
		       		        	emptyText: 'Name or ' + me.sspConfig.get('studentIdAlias'),
		       		        	width: 200
		       		        },{
		       		        	xtype: 'button',
		       		        	tooltip: 'Find a Student',
		       		        	itemId: 'searchButton',
					            width: 30,
					            height: 23,
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
		                    }]
		       		},{
		       			xtype: 'toolbar',
		       			dock: 'top',
		       			hidden: !me.authenticatedPerson.hasAccess('CASELOAD_FILTERS'),
		       		    items: [
		       		        {
		    			        xtype: 'combobox',
		    			        itemId: 'caseloadStatusCombo',
		    			        name: 'programStatusId',
		    			        fieldLabel: '',
		    			        emptyText: 'Select One',
		    			        store: me.programStatusesStore,
		    			        valueField: 'id',
		    			        displayField: 'name',
		    			        mode: 'local',
		    			        typeAhead: true,
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
		       		        }
		       		    ]
		       		    
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
						    tooltip: 'Transition Student',
						    text: '',
						    width: 25,
						    height: 25,
						    hidden: !me.authenticatedPerson.hasAccess('TRANSITION_STUDENT_BUTTON'),
						    cls: 'transitionStudentIcon',
						    xtype: 'button',
						    itemId: 'transitionStudentButton'
						},{
						    tooltip: 'Set Student to Non-Participating status',
						    text: '',
						    width: 25,
						    height: 25,
						    hidden: !me.authenticatedPerson.hasAccess('SET_NON_PARTICIPATING_BUTTON'),
						    cls: 'setNonParticipatingIcon',
						    xtype: 'button',
						    itemId: 'setNonParticipatingButton'
						},{
						    tooltip: 'Set Student to No-Show status',
						    text: '',
						    width: 25,
						    height: 25,
						    hidden: !me.authenticatedPerson.hasAccess('SET_NO_SHOW_BUTTON'),
						    cls: 'setNoShowIcon',
						    xtype: 'button',
						    itemId: 'setNoShowButton'
						},{
				            tooltip: 'Display Search',
				            text: '',
				            width: 20,
				            height: 20,
				            hidden: true,
				            cls: 'displayPhotoListIcon',
				            xtype: 'button',
				            itemId: 'displayPhotoButton'				        	
				        },{
				            tooltip: 'Display Caseload',
				            text: '',
				            width: 20,
				            height: 20,
				            hidden: true,
				            cls: 'displayListIcon',
				            xtype: 'button',
				            itemId: 'displayListButton'				        	
				        }]
		       		}]
		    	    });
    	
    	return me.callParent(arguments);
    }
});