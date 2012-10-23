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
Ext.define('Ssp.view.admin.forms.campus.DefineCampus',{
	extend: 'Ext.panel.Panel',
	alias : 'widget.definecampus',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.campus.DefineCampusViewController',
	title: 'Define a Campus',
	height: '100%',
	width: '100%',
	layout:'card',
	initComponent: function() {
		var me=this;
        Ext.applyIf(me, {
        	activeItem: 0,
        	
        	dockedItems: [{
	               xtype: 'toolbar',
	               dock: 'top',
	               items: [{
	                   text: 'Cancel',
	                   xtype: 'button',
	                   itemId: 'cancelCampusEditorButton'
	               },{
	                   text: 'Prev',
	                   xtype: 'button',
	                   action: 'prev',
	                   itemId: 'prevButton'
	               }, '-', {
	                   text: 'Next',
	                   xtype: 'button',
	                   action: 'next',
	                   itemId: 'nextButton'
	               }, '-', {
	                   text: 'Finish',
	                   xtype: 'button',
	                   action: 'finish',
	                   itemId: 'finishButton'
	               }]
	           }],
        	
        	items: [{
        	    xtype:'editcampus',
        	    flex: 1
        	},{
        		xtype:'campusearlyalertroutingsadmin',
        	    flex: 1
        	}]
        });
        return me.callParent(arguments);
	}
});