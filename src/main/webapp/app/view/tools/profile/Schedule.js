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
Ext.define('Ssp.view.tools.profile.Schedule', {
    extend: 'Ext.form.Panel',
    alias: 'widget.profileschedule',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
    width: '100%',
    height: '100%',
    initComponent: function(){
        var me = this;
        Ext.apply(me, {
            border: 1,
            bodyPadding: 0,
            layout: 'anchor',
            defaults: {
                anchor: '100%'
            },
            items: [
			{
                xtype: 'profilecurrentschedule',
                anchor: '100% , 50%'
				
            }/*, {
                xtype: 'tbspacer',
                height: '10'
            }, {
                xtype: 'profiledroppedcourses',
                anchor: '100% , 50%'
            }*/
			]
        
        });
        
        return me.callParent(arguments);
    }
    
});
