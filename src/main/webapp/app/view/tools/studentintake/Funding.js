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
Ext.define('Ssp.view.tools.studentintake.Funding', {
	extend: 'Ext.form.Panel',
	alias: 'widget.studentintakefunding',
	id : 'StudentIntakeFunding',   
    width: '100%',
    height: '100%',
	minHeight: 1000,
	minWidth: 600,
	style: 'padding: 0px 5px 5px 10px',
	initComponent: function() {	
		Ext.apply(this, 
				{
					autoScroll: true,
					border: 0,
				    bodyPadding: 0,
				    layout: 'anchor',
				    defaults: {
				        anchor: '100%'
				    },
				    defaultType: 'checkbox',
				    dockedItems: [{
		       	    xtype: 'toolbar',
				    items: [{
  		                   text: 'Check All',
  		                   xtype: 'button',
  		                   width: 75,
  		                   itemId: 'checkButton',
  		                   handler: function () {
  		                   var checkboxes = Ext.getCmp('StudentIntakeFunding').query('[isCheckbox]');
  		                   Ext.Array.each(checkboxes, function (checkbox) {
  		                	   	  checkbox.setValue(1);
  		                   		})
				    }}, 
 		            {
  	  		                   text: 'Clear All',
  	  		                   xtype: 'button',
  	  		                   width: 75,
  	  		                   itemId: 'clearButton',
  	  		                   handler: function () {
  	  		                   var checkboxes = Ext.getCmp('StudentIntakeFunding').query('[isCheckbox]');
  	  		                   Ext.Array.each(checkboxes, function (checkbox) {
  	  		                       checkbox.setValue(0);
  	  		                   });
  		               }
				    }]
				    }
				    ]
				});
		
		return this.callParent(arguments);
	}
});