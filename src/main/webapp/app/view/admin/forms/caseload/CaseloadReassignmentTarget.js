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
Ext.define('Ssp.view.admin.forms.caseload.CaseloadReassignmentTarget', {
	extend: 'Ext.grid.Panel',
	alias : 'widget.caseloadassignmenttarget',
	title: 'Students to Re-Assign',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.caseload.CaseloadReassignmentTargetViewController',
    inject: {
        apiProperties: 'apiProperties',
        authenticatedPerson: 'authenticatedPerson',
        coachesStore: 'coachesStore',
        reassignCaseloadStore: 'reassignCaseloadStore'
    },
	height: '100%',
	width: '100%',

    initComponent: function(){
    	var me=this;
    	Ext.apply(me,
    			{
		          viewConfig: {
		        	  plugins: {
		                  ptype: 'gridviewdragdrop',
		                  dropGroup: 'gridtogrid',
		                  enableDrag: false
		        	  }
		          },
    		      autoScroll: true,
    		      selType: 'rowmodel',
    		      multiSelect: true,    		      
    		      columns: [
       		                { 
        		               header: 'School ID',  
        		               dataIndex: 'schoolId',
        		               field: {
        		                  xtype: 'textfield'
        		               },
        		               flex: 1
        		             },     		                
    		                { 
    		                  header: 'Name',  
    		                  dataIndex: 'fullName',
    		                  field: {
    		                      xtype: 'textfield'
    		                  },
    		                  flex: 2
    		                },
    		                { 
      		                  header: 'Student Type',  
      		                  dataIndex: 'studentTypeName',
      		                  field: {
      		                      xtype: 'textfield'
      		                  },
      		                  flex: 1
      		                }    		                
    		            ],
    	    		           dockedItems: [
    	    		      		       		{
    	    		       		               xtype: 'toolbar',
    	    		         		           dock: 'top',
    	    		        		               items: [{
    	    		        		   		        xtype: 'combobox',
    	    		        		   		        name: 'targetCoachBox',
    	    		        		   		        itemId: 'targetCoachBox',
    	    		        		   		        fieldLabel: 'Assign To Coach',
    	    		        		   		        emptyText: 'Select One',
    	    		        		   		        store: this.coachesStore,
    	    		        		   		        valueField: 'id',
    	    		        		   		        displayField: 'fullName',
    	    		        		   		        mode: 'local',
    	    		        		   		        typeAhead: true,
    	    		        		   		        queryMode: 'local',
    	    		        		   		        allowBlank: true
    	    		        		                       }]  
    	    		      		       		  },     		       		
    	    		      		              {
    	    		      		               xtype: 'toolbar',
    	    		      		               items: [{
    	    		      		                   text: 'Remove',
    	    		      		                   xtype: 'button',
    	    		      		                   hidden: !me.authenticatedPerson.hasAccess('CASELOAD_REASSIGNMENT_REMOVE_BUTTON'),
    	    		      		                   action: 'remove',
    	    		      		                   itemId: 'removeButton'
    	    		      		               },
    	    		      		               {
    	    		      		                   text: 'Save',
    	    		      		                   xtype: 'button',
    	    		      		                   hidden: !me.authenticatedPerson.hasAccess('CASELOAD_REASSIGNMENT_SAVE_BUTTON'),
    	    		      		                   action: 'save',
    	    		      		                   itemId: 'saveButton'
    	    		      		               }]
    	    		      		           }]      	
    	});
    	
    	return me.callParent(arguments);
    }
});