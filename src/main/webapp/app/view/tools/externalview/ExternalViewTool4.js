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
Ext.define('Ssp.view.tools.externalview.ExternalViewTool4', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.externalviewtool4',
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
        externalViewService: 'externalViewService',
        person: 'currentPerson'
    },
    width: '98%',
    height: '100%',
    initComponent: function() {
        var me = this;
        Ext.apply(me, {
            title: " ",
            layout: {
                type: 'fit'
            }
        });

        if (me.externalViewService) {
            me.setLoading(true);
            me.externalViewService.getExternalViewWithSubstitutedUrl('956f01ba-5b05-4456-bed8-6f08f52c9a79',
                me.person.get('id'), {
                    success: me.getExternalViewSuccess,
                    failure: me.getExternalViewFailure,
                    scope: me
            });
        }

        me.callParent(arguments);
    },

    getExternalViewSuccess: function(response, scope) {
        var me = scope;

        if (response && response.url) {
            if (response.embedded) {
                me.add([{
                    xtype: 'box',
                    autoEl: {
                        tag: 'iframe',
                        autoScroll: true,
                        src:  response.url
                    }
                }]);
            } else {
                window.open(response.url, '_blank');
            }
            me.setLoading(false);
        } else {
            me.getExternalViewFailure();
        }
    },

    getExternalViewFailure: function(r, scope) {
        var me = scope;
        Ext.Msg.alert('SSP Error', 'External View 4\'s URL couldn\'t be Loaded. Please try again or contact your system administrator.');
        me.setLoading( false );
    }
});