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
Ext.define('Ssp.controller.admin.config.MessageQueueDisplayAdminViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	store: 'messageQueueStore',
    	unpagedStore: 'messageQueueStore',
        message: 'currentMessage',
    	formUtils: 'formRendererUtils',
		adminSelectedIndex: 'adminSelectedIndex',
		storeUtils:'storeUtils'
    },
    config: {
    	containerToLoadInto: 'adminforms',
    	formToDisplay: 'messagequeuedetails'
    },
    control: {
		view: {
			itemdblclick: 'onDetailsClick'
		}
    },       
	init: function() {
		var me=this;

		var params = {
			    store:me.store,
				unpagedStore:me.unpagedStore,
				propertyName:"subject",
				grid:me.getView(),
				model:me.message,
				selectedIndex: me.adminSelectedIndex};
		me.storeUtils.onStoreUpdate(params);
		
		return me.callParent(arguments);
    },

    onDetailsClick: function(view, record, item, index, event, eventListenerOpts) {
		var me = this;
   		this.adminSelectedIndex.set('value', -1);

        if (record) {
            var message = new  Ext.create('Ssp.model.Message');
            message.populateFromGenericObject(record.data);
            me.message.data = record.data;

            if ( me.messageQueuePopup ) {
                me.messageQueuePopup.destroy();
            }

            me.messageQueuePopup = Ext.create('Ssp.view.admin.forms.config.MessageQueueDetails', {
                messageQueuePopup: message
            });

            me.messageQueuePopup.show();
        }else{
            Ext.Msg.alert('SSP Error', 'Please select an message for more details.');
        }
   	},

	destroy: function() {
		var me=this;
		if ( me.messageQueuePopup ) {
			me.messageQueuePopup.destroy();
		}
		return me.callParent( arguments );
	}
});
