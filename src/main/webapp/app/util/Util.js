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
		apiProperties: 'apiProperties'
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


	}
});