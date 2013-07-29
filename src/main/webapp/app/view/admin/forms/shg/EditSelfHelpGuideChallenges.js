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
Ext.define('Ssp.view.admin.forms.shg.EditSelfHelpGuideChallenges',{
	extend: 'Ext.grid.Panel',
	alias : 'widget.editselfhelpguidechallenges',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.shg.EditSelfHelpGuideChallengesViewController',
	title: 'Assigned Challenges',
    inject: {
    	selfHelpGuideQuestionsStore: 'selfHelpGuideQuestionsStore',
        apiProperties: 'apiProperties',
        authenticatedPerson: 'authenticatedPerson',
        columnRendererUtils: 'columnRendererUtils',
    	parent: 'currentSelfHelpGuide'
    },	
	initComponent: function() {
    	var me=this;
        Ext.apply(me, {
	          viewConfig: {
	        	  itemId: 'gridView',
	        	  plugins: {
	                  ptype: 'gridviewdragdrop',
	                  dropGroup: 'gridtogrid',
	                  dragGroup: 'gridtogrid',
			          enableDrop: true,
			          enableDrag: true
	        	  }
	           },	        	  
		      enableDragDrop: true,
		      selType: 'rowmodel',
			  cls: 'configgrid',
		      columns: [
		                { 
		                  header: 'Question Number',  
			              dataIndex: 'questionNumber',
			              flex: 1 
			            },		                
		                { 
			              header: 'Challenge Name',  
		                  dataIndex: 'name',
		                  flex: 3 
		                },
		                { 
		                  header: 'Critical',  
    		              dataIndex: 'critical',
		                  xtype: 'checkcolumn',
    		              flex: 1 
    		             },
		                { 
    		              header: 'Mandatory',  
  		                  dataIndex: 'mandatory',
		                  xtype: 'checkcolumn',
		                  flex: 1 
  		                } 		                
		           ],
            dockedItems: [{
	               xtype: 'toolbar',
	               items: [{
    		                   text: 'Delete Challenge',
    		                   xtype: 'button',
    		                   action: 'delete',
     		                   hidden: !me.authenticatedPerson.hasAccess('SELF_HELP_GUIDE_DELETE_BUTTON'),
    		                   itemId: 'deleteChallengeButton'
    		               }]
	           },
	       		{
	       			xtype: 'pagingtoolbar',
	       		    dock: 'bottom',
	       		    displayInfo: true,
	       		    pageSize: this.apiProperties.getPagingSize()
	       		}]            
        });

        return this.callParent(arguments);
    }	
});