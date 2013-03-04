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
Ext.define('Ssp.view.person.EditPerson', {
	extend: 'Ext.form.Panel',
	alias: 'widget.editperson',
	mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.person.EditPersonViewController',
	initComponent: function() {	
		Ext.apply(this, 
				{
					border: 0,	    
				    fieldDefaults: {
				        msgTarget: 'side',
				        labelAlign: 'right',
				        labelWidth: 100
				    },
					items: [{
			            xtype: 'fieldset',
			            border: 0,
			            title: '',
			            defaultType: 'textfield',

			       items: [{
			        fieldLabel: 'First Name',
			        name: 'firstName',
			        itemId: 'firstName',
			        id: 'editPersonFirstName',
			        maxLength: 50,
			        allowBlank:false,
			        width: 350
			    },{
			        fieldLabel: 'Middle Name',
			        name: 'middleName',
			        itemId: 'middleName',
			        id: 'editPersonMiddleName',
			        maxLength: 50,
			        allowBlank:true,
			        width: 350
			    },{
			        fieldLabel: 'Last Name',
			        name: 'lastName',
			        itemId: 'lastName',
			        id: 'editPersonLastName',
			        maxLength: 50,
			        allowBlank:false,
			        width: 350
			    },{
			        fieldLabel: 'Student ID',
			        name: 'schoolId',
			        minLength: 7,
			        maxLength: 7,
			        itemId: 'studentId',
			        allowBlank:false,
			        width: 350
			    },{
			    	xtype: 'button',
			    	tooltip: 'Load record from external system',
			    	text: 'Retrieve from External',
			    	itemId: 'retrieveFromExternalButton'
			    },{
			        fieldLabel: 'Home Phone',
			        name: 'homePhone',
			        emptyText: 'xxx-xxx-xxxx',
			        maxLength: 25,
			        allowBlank:true,
			        itemId: 'homePhone',
			        width: 350
			    },{
			        fieldLabel: 'Work Phone',
			        name: 'workPhone',
			        emptyText: 'xxx-xxx-xxxx',
			        maxLength: 25,
			        allowBlank:true,
			        itemId: 'workPhone',
			        width: 350
			    },{
			        fieldLabel: 'School Email',
			        name: 'primaryEmailAddress',
			        vtype:'email',
			        maxLength: 100,
			        allowBlank:true,
			        itemId: 'primaryEmailAddress',
			        width: 350
			    },{
			        fieldLabel: 'Alternate Email',
			        name: 'secondaryEmailAddress',
			        vtype:'email',
			        maxLength: 100,
			        allowBlank:true,
			        itemId: 'secondaryEmailAddress',
			        width: 350
			    }]
			}]
		});
		
		return this.callParent(arguments);
	}
});