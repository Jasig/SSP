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
Ext.define('Ssp.view.admin.forms.caseload.CaseloadReassignmentSource', {
	extend: 'Ext.grid.Panel',
	alias : 'widget.caseloadassignmentsource',
	title: 'Assigned Students',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.caseload.CaseloadReassignmentSourceViewController',
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
		                  dragGroup: 'gridtogrid',
		                  enableDrag: true
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
       		   		        name: 'sourceCoachBox',
       		   		        itemId: 'sourceCoachBox',
       		   		        fieldLabel: 'Currently Assigned To',
       		   		        emptyText: 'Select One',
       		   		        store: this.coachesStore,
       		   		        valueField: 'id',
    		   		        displayField: 'fullName',
       		   		        mode: 'local',
    				        editable: false
       		                       }]  
     		       		  },     		       		
     		              {
     		               xtype: 'toolbar',
     		               items: [{
     		                   text: 'Add',
     		                   xtype: 'button',
     		                   hidden: !me.authenticatedPerson.hasAccess('CASELOAD_REASSIGNMENT_ADD_BUTTON'),
     		                   action: 'add',
     		                   itemId: 'addButton'
     		               }, '-', {
     		                   text: 'Add All',
     		                   xtype: 'button',
     		                   hidden: !me.authenticatedPerson.hasAccess('CASELOAD_REASSIGNMENT_ADD_ALL_BUTTON'),
     		                   action: 'addAll',
     		                   itemId: 'addAllButton'
     		               }]
     		           }]    	
    	});
    	
    	return me.callParent(arguments);
    }
});