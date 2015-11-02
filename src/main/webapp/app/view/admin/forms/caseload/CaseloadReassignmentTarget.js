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
Ext.define('Ssp.view.admin.forms.caseload.CaseloadReassignmentTarget', {
	extend: 'Ext.grid.Panel',
	alias : 'widget.caseloadassignmenttarget',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.caseload.CaseloadReassignmentTargetViewController',
    inject: {
        apiProperties: 'apiProperties',
        authenticatedPerson: 'authenticatedPerson',
        coachesStore: 'coachesStore',
        reassignCaseloadStore: 'reassignCaseloadStore',
        textStore: 'sspTextStore'
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
				  title: me.textStore.getValueByCode('ssp.label.caseload-reassignment.students-to-reassign-title','Students to Reassign'),
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
    	    		        		   		        name: 'targetCoachBox',
    	    		        		   		        itemId: 'targetCoachBox',
    	    		        		   		        fieldLabel: me.textStore.getValueByCode('ssp.label.caseload-reassignment.assign-to-coach','Assign To Coach'),
    	    		        		   		        emptyText: me.textStore.getValueByCode('ssp.empty-text.caseload-reassignment.assign-to-coach','Select One'),
    	    		        		   		        store: this.coachesStore,
    	    		        		   		        valueField: 'id',
    	    		        		   		        displayField: 'fullName',
    	    		        		   		        mode: 'local',
    	    		        				        editable: false,
    	    		        		   		        queryMode: 'local',
    	    		        		   		        allowBlank: true
    	    		        		                       }]  
    	    		      		       		  },     		       		
    	    		      		              {
    	    		      		               xtype: 'toolbar',
    	    		      		               items: [{
    	    		      		                   text: me.textStore.getValueByCode('ssp.label.remove-button','Remove'),
    	    		      		                   xtype: 'button',
    	    		      		                   hidden: !me.authenticatedPerson.hasAccess('CASELOAD_REASSIGNMENT_REMOVE_BUTTON'),
    	    		      		                   action: 'remove',
    	    		      		                   itemId: 'removeButton'
    	    		      		               },
    	    		      		               {
    	    		      		                   text: me.textStore.getValueByCode('ssp.label.save-button','Save'),
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