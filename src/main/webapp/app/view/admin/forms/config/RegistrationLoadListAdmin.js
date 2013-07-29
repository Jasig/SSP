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
Ext.define('Ssp.view.admin.forms.config.RegistrationLoadListAdmin', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.registrationloadlistadmin',
    title: 'Registration Load Admin',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
    inject: {
        apiProperties: 'apiProperties',
        authenticatedPerson: 'authenticatedPerson',
        columnRendererUtils: 'columnRendererUtils'
    },
    height: '100%',
    width: '100%',
    
    initComponent: function(){
        var me = this;
        
        
        
        Ext.apply(me, {
            
            autoScroll: true,
            selType: 'rowmodel',
			cls: 'configgrid',
            columns: [{
                header: 'Name',
                dataIndex: 'name',
                flex: .30
            }, {
                header: 'Current Value',
                dataIndex: 'value',
                flex: .70
            }]
        });
        
        return me.callParent(arguments);
    }
});
