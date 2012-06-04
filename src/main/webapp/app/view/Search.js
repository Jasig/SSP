Ext.define('Ssp.view.Search', {
	extend: 'Ext.grid.Panel',
	alias : 'widget.search',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.SearchViewController',
    inject: {
    	appEventsController: 'appEventsController',
    	apiProperties: 'apiProperties',
    	columnRendererUtils: 'columnRendererUtils',
    	person: 'currentPerson',
        store: 'studentsStore',
        programStatusesStore: 'programStatusesStore'
    },
    initComponent: function(){
    	Ext.apply(this,
    			   {
    				title: 'Students',
    	            collapsible: true,
    	            collapseDirection: 'left',
    	            store: this.store,
    	        	width: '100%',
    	        	height: '100%',
		    	    columns: [
		    	              { header: "Photo", dataIndex: 'photoUrl', renderer: this.columnRendererUtils.renderPhotoIcon, flex: 50 },		        
		    	              { text: 'Name', xtype:'templatecolumn', tpl:'{firstName} {middleInitial} {lastName}', flex: 50},
		    	              {
	    			    	        xtype:'actioncolumn',
	    			    	        width:65,
	    			    	        header: 'Action',
	    			    	        items: [{
	    			    	            icon: Ssp.util.Constants.GRID_ITEM_EDIT_ICON_PATH,
	    			    	            tooltip: 'Edit Student',
	    			    	            handler: function(grid, rowIndex, colIndex) {
	    			    	            	var rec = grid.getStore().getAt(rowIndex);
	    			    	            	var panel = grid.up('panel');
	    			    	                panel.person.data=rec.data;
	    			    	                panel.appEventsController.getApplication().fireEvent('editPerson');
	    			    	            },
	    			    	            scope: this
	    			    	        },{
	    			    	            icon: Ssp.util.Constants.GRID_ITEM_DELETE_ICON_PATH,
	    			    	            tooltip: 'Delete Student',
	    			    	            handler: function(grid, rowIndex, colIndex) {
	    			    	    			Ext.Msg.alert('Attention','This feature is not available yet.');	    			    	            	
	    			    	            },
	    			    	            scope: this
	    			    	        }]
	    		                }],
    	          
		    	    dockedItems: [{
		       			xtype: 'pagingtoolbar',
		       		    store: this.store,
		       			dock: 'bottom',
		       		    displayInfo: true,
		       		    pageSize: this.apiProperties.getPagingSize()
		       		},{
		       			xtype: 'toolbar',
		       			dock: 'top',
		       			defaults: {
		       				labelWidth: 50
		       			},
		       		    items: [
		       		        {
		       		        	xtype: 'textfield',
		       		        	fieldLabel: 'Search'
		       		        },{
		       		        	xtype: 'button',
		       		        	itemId: 'searchButton',
		       		        	text: 'GO'
		       		        },{
		       		        	xtype: 'tbspacer',
		       		        	flex: 1
		       		        },{
		       		        	tooltip: 'Search records in your caseload',
		       		        	xtype: 'checkbox',
		       		        	boxLabel: 'Against Caseload'
		       		        }
		       		    ]
		       		},{
				        dock: 'top',
				        xtype: 'toolbar',
				        items: [{
				            tooltip: 'Add Student',
				            text: 'Add Student',
				            xtype: 'button',
				            itemId: 'addButton'
				        }]
				    }/*,{
		       			xtype: 'toolbar',
		       			dock: 'top',
		       		    items: [
		       		        {
		    			        xtype: 'combobox',
		    			        itemId: 'caseloadStatusCombo',
		    			        name: 'programStatusId',
		    			        fieldLabel: 'Caseload Status',
		    			        emptyText: 'Select One',
		    			        store: this.programStatusesStore,
		    			        valueField: 'id',
		    			        displayField: 'name',
		    			        mode: 'local',
		    			        typeAhead: true,
		    			        queryMode: 'local',
		    			        allowBlank: true,
		    			        forceSelection: false,
		    			        labelWidth: 100
		    				},{
		       		        	xtype: 'tbspacer',
		       		        	flex: 1
		       		        }
		       		    ]
		       		    
		       		}*/]
		    	    });
    	
    	return this.callParent(arguments);
    }
});