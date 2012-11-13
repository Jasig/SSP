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
    control: {
    	firstNameField: '#firstName',
    	middleNameField: '#middleName',
    	lastNameField: '#lastName',
    	studentIdField: '#studentId',
    	birthDateField: '#birthDate',
    	homePhoneField: '#homePhone',
    	workPhoneField: '#workPhone',
    	addressLine1Field: '#addressLine1',
    	addressLine2Field: '#addressLine2',
    	cityField: '#city',
    	stateField: '#state',
    	zipCodeField: '#zipCode',
    	primaryEmailAddressField: '#primaryEmailAddress'
    },
	init: function() {
		var me=this;
    	var disabled = me.sspConfig.get('syncStudentPersonalDataWithExternalData');
		// disable externally loaded fields
    	me.getFirstNameField().setDisabled(disabled);
		me.getMiddleNameField().setDisabled(disabled);
		me.getLastNameField().setDisabled(disabled);
		me.getBirthDateField().setDisabled(disabled);
		me.getHomePhoneField().setDisabled(disabled);
		me.getWorkPhoneField().setDisabled(disabled);
		me.getAddressLine1Field().setDisabled(disabled);
		me.getAddressLine2Field().setDisabled(disabled);
		me.getCityField().setDisabled(disabled);
		me.getStateField().setDisabled(disabled);
		me.getZipCodeField().setDisabled(disabled);
		me.getPrimaryEmailAddressField().setDisabled(disabled);		
		studentIdField = me.getStudentIdField();
		studentIdField.setDisabled(disabled);
		// set the field label and supply an asterisk for required
		studentIdField.setFieldLabel(me.sspConfig.get('studentIdAlias') + Ssp.util.Constants.REQUIRED_ASTERISK_DISPLAY);
		Ext.apply(studentIdField, {
            minLength: me.sspConfig.get('studentIdMinValidationLength'),
            minLengthText: '',
            maxLength: me.sspConfig.get('studentIdMaxValidationLength'),
        	maxLengthText: '',
        	vtype: 'studentIdValidator',
        	vtypeText: me.sspConfig.get('studentIdValidationErrorText')
         });

		return me.callParent(arguments);
    }
});