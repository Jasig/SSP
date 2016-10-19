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
Ext.define('Ssp.controller.admin.config.BackgroundJobDisplayAdminViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	store: 'backgroundJobStore',
    	unpagedStore: 'backgroundJobStore',
        backgroundJob: 'currentBackgroundJob',
    	formUtils: 'formRendererUtils',
		adminSelectedIndex: 'adminSelectedIndex',
		storeUtils:'storeUtils'
    },
    config: {
    	containerToLoadInto: 'adminforms',
    	formToDisplay: 'backgrounddobdetails'
    },
    control: {
		view: {
			itemdblclick: 'onDetailsClick'
		}
    },       
	init: function() {
		var me=this;

		var params = {
			    store: me.store,
				unpagedStore: me.unpagedStore,
				propertyName: "displayName",
				grid: me.getView(),
				model: me.backgroundJob,
				selectedIndex: me.adminSelectedIndex};
		me.storeUtils.onStoreUpdate(params);
		
		return me.callParent(arguments);
    },

    onDetailsClick: function(view, record, item, index, event, eventListenerOpts) {
		var me = this;
   		this.adminSelectedIndex.set('value', -1);

        if (record) {
            var backgroundjob = new  Ext.create('Ssp.model.BackgroundJob');
            backgroundjob.populateFromGenericObject(record.data);
            me.backgroundJob.data = record.data;

            if ( me.backgroundJobPopUp ) {
                me.backgroundJobPopUp.destroy();
            }

            me.backgroundJobPopUp = Ext.create('Ssp.view.admin.forms.config.BackgroundJobDetails', {
                backgroundJobPopUp: backgroundjob
            });

            me.backgroundJobPopUp.show();
        }else{
            Ext.Msg.alert('SSP Error', 'Please select an background job for more details or to run the job.');
        }
   	},

	destroy: function() {
		var me=this;
		if ( me.backgroundJobPopUp ) {
			me.backgroundJobPopUp.destroy();
		}
		return me.callParent( arguments );
	}
});
