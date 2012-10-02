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
Ext.define('Ssp.view.admin.forms.campus.CampusEarlyAlertRoutingsAdmin', {
	extend: 'Ext.panel.Panel',
	alias : 'widget.campusearlyalertroutingsadmin',
	title: 'Campus Admin',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.campus.CampusEarlyAlertRoutingsAdminViewController',
	inject: {
		campus: 'currentCampus'
	},
    height: '100%',
	width: '100%',
	layout: 'fit',
    initComponent: function(){
        var me=this;
    	Ext.apply(me,
    			{  
    			  title: 'Campuses Admin - ' + me.campus.get('name'),
    		      items: [{
    		    	xtype:'earlyalertroutingsadmin',
    		    	flex: 1
    		      }],
  	            
    		      dockedItems: [{
		               xtype: 'toolbar',
		               items: [{
	       		                   text: 'Finished Editing Campus',
	       		                   xtype: 'button',
	       		                   itemId: 'finishButton'
	       		               }]
		           }]
    	});

    	return me.callParent(arguments);
    }
});