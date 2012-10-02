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
Ext.define('Ssp.controller.tool.studentintake.PersonalViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	citizenshipsStore: 'citizenshipsStore',
    	sspConfig: 'sspConfig'
    },  
	init: function() {
		var me=this;
    	var disabled = me.sspConfig.get('syncStudentPersonalDataWithExternalData');
    	firstName = Ext.ComponentQuery.query('#firstName')[0].setDisabled(disabled);
		middleName = Ext.ComponentQuery.query('#middleName')[0].setDisabled(disabled);
		lastName = Ext.ComponentQuery.query('#lastName')[0].setDisabled(disabled);
		studentId = Ext.ComponentQuery.query('#studentId')[0];
		studentId.setDisabled(disabled);
		// set the field label and supply an asterisk for required
		studentId.setFieldLabel(me.sspConfig.get('studentIdAlias') + Ssp.util.Constants.REQUIRED_ASTERISK_DISPLAY);
		Ext.apply(studentId, {
	                  minLength: me.sspConfig.get('studentIdMinValidationLength'),
	                  minLengthText: '',
	                  maxLength: me.sspConfig.get('studentIdMaxValidationLength'),
	                  maxLengthText: '',
	                  vtype: 'studentIdValidator',
	                  vtypeText: me.sspConfig.get('studentIdValidationErrorText')
                     });
		birthDate = Ext.ComponentQuery.query('#birthDate')[0].setDisabled(disabled);
		homePhone = Ext.ComponentQuery.query('#homePhone')[0].setDisabled(disabled);
		workPhone = Ext.ComponentQuery.query('#workPhone')[0].setDisabled(disabled);
		address = Ext.ComponentQuery.query('#address')[0].setDisabled(disabled);
		city = Ext.ComponentQuery.query('#city')[0].setDisabled(disabled);
		state = Ext.ComponentQuery.query('#state')[0].setDisabled(disabled);
		zipCode = Ext.ComponentQuery.query('#zipCode')[0].setDisabled(disabled);
		primaryEmailAddress = Ext.ComponentQuery.query('#primaryEmailAddress')[0].setDisabled(disabled);

		return me.callParent(arguments);
    }
});