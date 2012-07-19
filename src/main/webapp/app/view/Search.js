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
		    	              /* { header: "Photo", dataIndex: 'photoUrl', renderer: me.columnRendererUtils.renderPhotoIcon, flex: 50 }, */		        
		    	              { text: 'Name', dataIndex: 'lastName', renderer: me.columnRendererUtils.renderStudentDetails, flex: 50}
		    	              ],
    	          
		    	    dockedItems: [{
		       			xtype: 'pagingtoolbar',
		       		    store: me.store,
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
		                        boxLabel  : 'In My Caseload',
		                        itemId: 'searchCaseloadCheck',
		                        name      : 'searchInCaseload',
		                        inputValue: false
		                    }]
		       		},{
		       			xtype: 'toolbar',
		       			dock: 'top',
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