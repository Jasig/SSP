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
Ext.define('Ssp.view.Main', {
	extend: 'Ext.panel.Panel',
    alias: 'widget.mainview',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    inject: {
    	authenticatedPerson: 'authenticatedPerson'
    },
    controller: 'Ssp.controller.MainViewController',
    initComponent: function(){
    	var me=this;
    	Ext.apply(me,
		    			{
		    	    layout: {
		    	    	type: 'hbox',
		    	    	align: 'stretch'
		    	    },

		    	    dockedItems: {
		    	        xtype: 'toolbar',
		    	        items: [{
		    			            xtype: 'button',
		    			            text: 'Students',
		    			            hidden: !me.authenticatedPerson.hasAccess('STUDENTS_NAVIGATION_BUTTON'),
		    			            itemId: 'studentViewNav',
		    			            action: 'displayStudentRecord'
		    			        }, {
		    			            xtype: 'button',
		    			            text: 'Admin',
		    			            hidden: !me.authenticatedPerson.hasAccess('ADMIN_NAVIGATION_BUTTON'),
		    			            itemId: 'adminViewNav',
		    			            action: 'displayAdmin'
		    			        },{
			       		        	xtype: 'tbspacer',
			       		        	flex: 1
			       		        },{
	    		    	    	  id: 'report',
	    		    	    	  xtype: 'sspreport'
	    		    	    	}]
		    	    }    		
    			});
    	
    	return me.callParent(arguments);
    }
});