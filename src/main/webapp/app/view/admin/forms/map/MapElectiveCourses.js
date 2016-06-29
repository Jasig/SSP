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
Ext.define('Ssp.view.admin.forms.map.MapElectiveCourses', {
	extend: 'Ext.window.Window',
    alias : 'widget.mapelectivecourses',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.map.MapElectiveCoursesViewController',
    inject: {
        mapEventUtils: 'mapEventUtils',
        currentMapPlan: 'currentMapPlan',
        textStore: 'textStore'
	},
    title: 'Edit Template Elective Courses',
    floating: true,
    centered: true,
    closable: true,
	resizable: true,
	modal: true,
    width : 750,
    height : 550,
    //bodyPadding: 2,
    layout : {
		type : 'hbox',
		align : 'stretch'
	},
	initComponent : function() {
	    var me = this;
		Ext.apply(this, {
            items : [{
                xtype : 'coursesview',
                width : 300
            }, {
                xtype : 'associateelectivecoursesadmin',
                flex : 1
            }],
            dockedItems: [{
                xtype: 'toolbar',
                dock: 'top',
                items: [{
                    xtype: 'button',
                    itemId: 'cancelButton',
                    text: me.textStore.getValueByCode('ssp.label.close-button', 'Close'),
                    listeners: {
                        click: function () {
                            if (me.mapEventUtils && me.currentMapPlan) {
                                me.mapEventUtils.loadTemplate(me.currentMapPlan.get('id'));
                            }
                            me.close();
                        },
                        scope: me
                    }
                }]
            }]
		});

		return this.callParent(arguments);
    }
});