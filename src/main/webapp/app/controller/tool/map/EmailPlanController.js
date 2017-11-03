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
Ext.define('Ssp.controller.tool.map.EmailPlanController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
		person: 'currentPerson',
		currentMapPlan: 'currentMapPlan'
    },
    control: {
    	optionsEmailView: '#optionsEmailView',
    	'fullFormat': {
    	   selector: '#fullFormat',
    	   listeners: {
            change: 'onoptionsEmailFormatClick'
           }
        },
        'matrixFormat': {
    	   selector: '#matrixFormat',
    	   listeners: {
            change: 'onemailmatrixFormatClick'
           }
        },
        'shortMatrixFormat': {
    	   selector: '#shortMatrixFormat',
    	   listeners: {
            change: 'onemailShortMatrixFormatClick'
           }
        }
    },

	init: function() {
		var me=this;
		Ext.apply(Ext.form.field.VTypes, {
		    //  vtype validation function
		    multiemail : function(val, field) {
		        var email = /^([\w\-\'\-]+)(\.[\w-\'\-]+)*@([\w\-]+\.){1,5}([A-Za-z]){2,4}$/;
				var addresses = val.split(',');
				var matches = true;
				addresses.forEach(function(address){
					if (!email.test(address.trim())) {
						matches = false;
						return;
					}
				});
		        return matches;
		    },
		    multiemailText : "This field needs to be in comma delimited email format: abcd@ssp.org,abcd@ssp.org"
		});

		me.getOptionsEmailView().hide();
		
		var emailToField = Ext.ComponentQuery.query('#emailTo',me.getView())[0];
		
		var emailCCField = Ext.ComponentQuery.query('#emailCC',me.getView())[0];
		
		emailToField.setValue(me.handleNull(me.person.get('primaryEmailAddress')));
		
		var emailCCCoach = me.handleNull(me.person.getCoachPrimaryEmailAddress());
		
		var emailCCContact = me.handleNull(me.currentMapPlan.get('contactEmail'));
		
		var emailCCOwner = me.handleNull(me.currentMapPlan.get('ownerEmail'));
		
		var emailCC = '';
		
		if (emailCCCoach != "") {
			emailCC = emailCCCoach;
		}
		
		if (emailCCContact != "") {
			if (emailCC != '') {
					if (emailCC.indexOf(emailCCContact) == -1) {
						emailCC = emailCC + "," + emailCCContact;
                    }
            } else {
				emailCC = emailCCContact;
            }
		}
		
		if (emailCCOwner != "") {
			if (emailCC != '') {
				if (emailCC.indexOf(emailCCOwner) == -1) {
					emailCC = emailCC + "," + emailCCOwner;
                }
			} else {
				emailCC = emailCCOwner;
            }
		}
		emailCCField.setValue(emailCC);
		return this.callParent(arguments);
    },

    onoptionsEmailFormatClick: function(cb, nv, ov) {
        var me=this;
        if (nv) {
            me.getOptionsEmailView().show();
        }
    },
    
    onemailmatrixFormatClick: function(cb, nv, ov) {
        var me=this;
        if (nv) {
            me.getOptionsEmailView().hide();
        }
    },
	
    onemailShortMatrixFormatClick: function(cb, nv, ov) {
        var me=this;
        if (nv) {
            me.getOptionsEmailView().hide();
        }
    },

	handleNull: function(value, defaultValue) {
		if (defaultValue == null || defaultValue == undefined) {
			defaultValue = "";
        }
		if (value == null || value == undefined || value == 'null') {
			return defaultValue;
        }
		return value;
	}
});
