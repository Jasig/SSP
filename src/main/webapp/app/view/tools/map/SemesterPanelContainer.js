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
Ext.define('Ssp.view.tools.map.SemesterPanelContainer', {
	extend : 'Ext.form.Panel',
	alias : 'widget.semesterpanelcontainer',
	mixins : [ 'Deft.mixin.Injectable', 'Deft.mixin.Controllable' ],
	controller:'Ssp.controller.tool.map.SemesterPanelContainerViewController',
	inject : {
		appEventsController: 'appEventsController'
	},
	width : '100%',
	height : '100%',
	
	initComponent : function() {
		var me = this;
		Ext.apply(me, {
			border : 0,
			bodyPadding : 0,
			layout : 'fit',
            autoScroll : true,
			itemId: 'semesterpanel',
			store: me.store,
            defaults : {
                anchor : '100% ,100%'
            },
			items : [ {
				xtype : 'container',
				fieldLabel : '',
				layout : 'hbox',
				margin : '0 0 0 0',
				padding : '2 2 2 2',
				width : '100%',
				height : '100%',
				autoScroll : true,
				autoHeight: true,
                minHeight: 0,
				itemId: "semestersets",
				flex : 1,
			} ]
		});

		return me.callParent(arguments);
	}

});