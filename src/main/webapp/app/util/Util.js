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
Ext.define('Ssp.util.Util', {  
    extend: 'Ext.Component',
	mixins: [ 'Deft.mixin.Injectable' ],
	inject: {
		apiProperties: 'apiProperties',
		appEventsController: 'appEventsController',
		configurationOptionsUnpagedStore: 'configurationOptionsUnpagedStore'
	},

    statics: {
        decorateFormField: function(field) {
            var fl=field.getFieldLabel(), ab=field.allowBlank;
            if (fl){
                field.labelStyle=Ssp.util.Constants.SSP_LABEL_STYLE; // this may or may not affect an already-rendered field. but is no setter to call.
            }
            if (ab===false) {
                // definitely have to use the setter if changing the fieldLabel here, else won't affect an already-rendered field
                if (fl && fl.indexOf(Ssp.util.Constants.REQUIRED_ASTERISK_DISPLAY) < 0) {
                    field.setFieldLabel(fl + Ssp.util.Constants.REQUIRED_ASTERISK_DISPLAY);
                }
            } else if (fl) {
                while ( fl.indexOf(Ssp.util.Constants.REQUIRED_ASTERISK_DISPLAY) >= 0 ) {
                    field.setFieldLabel(fl.replace(Ssp.util.Constants.REQUIRED_ASTERISK_DISPLAY, ''));
                    fl = field.getFieldLabel();
                }
            }
        }
    },

	initComponent: function() {
		return this.callParent( arguments );
	},

    loaderXTemplateRenderer: function(loader, response, active) {
        var tpl = new Ext.XTemplate(response.responseText);
        var targetComponent = loader.getTarget();
        var cleanArr = Ext.create( 'Ssp.util.TemplateDataUtil' ).prepareTemplateData( targetComponent.store );
        targetComponent.update( tpl.apply( cleanArr ) );
        return true;
    },

	getCurrentServerDate: function(opts) {

		if ( !(opts.success) ) {
			return;
		}

		var me = this;

		me.apiProperties.makeRequest({
			url: me.apiProperties.createUrl( me.apiProperties.getItemUrl('serverDateTime') ),
			method: 'GET',
			successFunc: function(r) {
				if (r && r.responseText ) {
					var jsonData = Ext.decode(r.responseText);
					var date = Ext.Date.parse(jsonData.date, 'c');
					opts.success.apply(opts.scope, [date]);
				} else if (opts.failure) {
					opts.failure.apply(opts.scope, [r]);
				}
			},
			failureFunc: function(r) {
				if (opts.failure) {
					opts.failure.apply(opts.scope, [r]);
				} else {
					me.apiProperties.handleError( r );
				}
			},
			scope: me
		});


	},
	
	// in the utils because needed by two different controllers each destroyed by the other
	onUpdateEarlyAlertsResponseStatus: function(models, earlyAlertResponse){
		if(!models || models.length == 0)
			return;
	    var me = this;
    	var params = {"openEarlyAlerts":0, "lateEarlyAlertResponses":0,"personId":models[0].get("personId")};
		me.configurationOptionsUnpagedStore.clearFilter();
    	var maxResponse = me.configurationOptionsUnpagedStore.getConfigByName("maximum_days_before_early_alert_response");
    	var miniumResponseDate = null;
    	if(maxResponse && maxResponse > 0){
    		miniumResponseDate = new Date();
    		miniumResponseDate.setDate(miniumResponseDate.getDate() - maxResponse);
    	}
		
        Ext.Array.each( models, function(model, index){
        	model.set("responseRequired", false);
        	if(earlyAlertResponse != null && earlyAlertResponse.earlyAlertId == model.get("id")){
    			if(earlyAlertResponse.closed == true)
    				model.set("closedDate", new Date());
    			model.set("lastResponseDate", new Date())
    		}
        	if(!model.get("closedDate")){
        		params.openEarlyAlerts++;
        			var lastResponseDate = model.get("lastResponseDate") != null ? model.get("lastResponseDate") : model.get("createdDate");
        			if(miniumResponseDate && (lastResponseDate - miniumResponseDate) < 0){
        				params.lateEarlyAlertResponses++;
        				model.set("responseRequired", true);
        			}
			}
        });
        me.appEventsController.getApplication().fireEvent('updateEarlyAlertCounts', params);
    }
});