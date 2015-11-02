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
Ext.define('Ssp.view.admin.forms.caseload.CaseloadReassignmentSource', {
	extend: 'Ext.grid.Panel',
	alias : 'widget.caseloadassignmentsource',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.caseload.CaseloadReassignmentSourceViewController',
    inject: {
        apiProperties: 'apiProperties',
        programStatusesStore: 'programStatusesStore',
        authenticatedPerson: 'authenticatedPerson',
        allCoachesCurrentStore: 'allCoachesCurrentStore',
        reassignCaseloadStore: 'reassignCaseloadStore',
        textStore: 'sspTextStore'
    },
	height: '100%',
	width: '100%',
    enableDragAndDrop: true,
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
				  title: me.textStore.getValueByCode('ssp.label.caseload-reassignment.assigned-students-title','Assigned Students'),
				  cls: 'configgrid',
    		      columns: [
    		                { 
      		                  header: me.textStore.getValueByCode('ssp.label.student-id','Student Id'),
      		                  dataIndex: 'schoolId',
      		                  field: {
      		                      xtype: 'textfield'
      		                  },
      		                  flex: 1
      		                },    		                
    		                { 
    		                  header: me.textStore.getValueByCode('ssp.label.caseload-reassignment.name','Name'),
    		                  dataIndex: 'fullName',
    		                  field: {
      		                      xtype: 'textfield'
    		                  },
    		                  flex: 2
    		                },
    		                { 
      		                  header: me.textStore.getValueByCode('ssp.label.student-type','Student Type'),
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
					id: 'sourceCoachBoxId',
					name: 'sourceCoachBox',
					itemId: 'sourceCoachBox',
					fieldLabel: me.textStore.getValueByCode('ssp.label.caseload-reassignment.currently-assigned-to','Currently Assigned To'),
					emptyText: me.textStore.getValueByCode('ssp.empty-text.caseload-reassignment.currently-assigned-to','Select One'),
					store: me.allCoachesCurrentStore,
					valueField: 'id',
					displayField: 'fullName',
					mode: 'local',
					labelWidth: 120,
					editable: false,
				allowBlank: true
				},'->',
			   {
				xtype: 'combobox',
				multiSelect: false,
				fieldLabel: me.textStore.getValueByCode('ssp.label.program-status','Program Status'),
				emptyText: me.textStore.getValueByCode('ssp.empty-text.program-status','Select Status'),
				store: me.programStatusesStore,
				valueField: 'id',
				displayField: 'name',
				columnWidth: 0.5,
				itemId: 'sourceProgramStatusBox',
				name: 'sourceProgramStatusBox',
				id: 'sourceProgramStatusBoxId',
        		mode: 'local',
				editable: false,
				allowBlank: true
			   }]
     		       		  },
     		              {
     		               xtype: 'toolbar',
     		               items: [{
     		                   text: me.textStore.getValueByCode('ssp.label.add-button','Add'),
     		                   xtype: 'button',
     		                   hidden: !me.authenticatedPerson.hasAccess('CASELOAD_REASSIGNMENT_ADD_BUTTON'),
     		                   action: 'add',
     		                   itemId: 'addButton'
     		               }, '-', {
     		                   text: me.textStore.getValueByCode('ssp.label.add-all-button','Add All'),
     		                   xtype: 'button',
     		                   hidden: !me.authenticatedPerson.hasAccess('CASELOAD_REASSIGNMENT_ADD_ALL_BUTTON'),
     		                   action: 'addAll',
     		                   itemId: 'addAllButton'
     		               }, '-', {
							   text: me.textStore.getValueByCode('ssp.label.reset-button','Reset'),
							   xtype: 'button',
							   hidden: !me.authenticatedPerson.hasAccess('CASELOAD_REASSIGNMENT_ADD_ALL_BUTTON'),
							   action: 'reset',
							   itemId: 'resetButton'
						   }]
     		           }]    	
    	});
    	
    	return me.callParent(arguments);
    }
});