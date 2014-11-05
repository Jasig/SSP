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
Ext.define('Ssp.view.tools.accommodation.AgencyContacts', {
	extend: 'Ext.panel.Panel',
	alias: 'widget.disabilityagencycontacts',
    width: '100%',
    height: '100%',
    autoScroll: true,
	initComponent: function() {	
        var me = this;
		Ext.apply(me,
				{
		    		bodyPadding: 10,
		    		border: 0,
					items: [{
						xtype: 'form',
						id : 'AccommodationDisabilityAgencies',
					    layout: 'anchor',
					    border: 0,
					    defaults: {
					        anchor: '100%'
					    },
					    defaultType: 'checkbox'
					},{
						xtype: 'form',
						id : 'AccommodationAgencyContactName',
					    layout: 'anchor',
					    border: 0,
					    defaults: {
					        anchor: '95%'
					    },
					    items: [{
	                        xtype: 'textfield',
	                        fieldLabel: 'Name of Contact',
	                        name: 'contactName',
							maxLength: 50
	                    }]
					}]			
				});
		
		return me.callParent(arguments);
	}
});