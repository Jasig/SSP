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
Ext.define('Ssp.view.tools.actionplan.TaskTree', {
	extend: 'Ext.tree.Panel',
	alias : 'widget.tasktree',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.actionplan.TaskTreeViewController',
    inject: {
        store: 'treeStore'
    },
	height: 250,
	width: '100%',
    initComponent: function(){
    	Ext.apply(this,
    			{
    			 store: this.store,
    			 useArrows: true,
    			 rootVisible: false,
    			 dockedItems: [
     		              {
     		               xtype: 'toolbar',
     		               items: [/*{
     	                      xtype: 'textfield',
     	                      fieldLabel: 'Search'
     	                     },
     	                      {
     	                    	  xtype: 'button',
     	                    	  text: 'GO',
     	                    	  action: 'search',
     	                    	  itemId: 'searchButton'
     	                      }*/
	                       {
	                         xtype: "label",
	                         text: "Select a task to add to the Student's Action Plan:"
	                       }]
     		           }]
     		       	
    	});
    	
    	return this.callParent(arguments);
    }
});