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
Ext.define('Ssp.view.admin.forms.shg.EditSelfHelpGuideAvailableChallengesAdmin', {
	extend: 'Ext.grid.Panel',
	alias : 'widget.editselfhelpguideavailablechallengesadmin',
	title: 'Available Challenges',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.shg.EditSelfHelpGuideAvailableChallengesAdminViewController',
    inject: {
        apiProperties: 'apiProperties',
        authenticatedPerson: 'authenticatedPerson',
        columnRendererUtils: 'columnRendererUtils'
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
    		      enableDragDrop: false,
				  cls: 'configgrid',
    		      columns: [
    		                { header: 'Name',  
    		                  dataIndex: 'name',
    		                  flex: 1 
    		                }
    		           ],
    		        
    		           dockedItems: [
     		       		{
     		       			xtype: 'pagingtoolbar',
     		       		    dock: 'bottom',
     		       		    displayInfo: true,
     		       		    pageSize: me.apiProperties.getPagingSize()
     		       		}]
     		               	
    	});
    	
    	return me.callParent(arguments);
    }
});