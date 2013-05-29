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
Ext.define('Ssp.controller.tool.map.CoursesGridController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
        store: 'coursesStore',
        formUtils: 'formRendererUtils',
    },
	init: function() {
		var me=this;
		
		me.formUtils.reconfigureGridPanel( me.getView(), me.store);
		me.store.load();
		me.store.addListener('load', this.sortAfterLoad, this);
		return me.callParent(arguments);
    },
	
	sortAfterLoad: function(){
		var me = this;
		me.store.sort('formattedCourse','ASC');
	},
    
    control:{
    	view:{
    		    itemdblclick: 'onItemDblClick'
    		}
    },
    
    onItemDblClick: function(grid, record, item, index, e, eOpts) {
		var me = this;
    	if(me.courseDetailsPopUp == null || me.courseDetailsPopUp.isDestroyed){
    		me.courseDetailsPopUp = Ext.create('Ssp.view.tools.map.CourseDetails');
    		me.courseDetailsPopUp.center();
    	}
		me.courseDetailsPopUp.query("#formatted_course_title")[0].setValue( record.get("formattedCourse") + " : " + record.get("title"));
		me.courseDetailsPopUp.query("#description")[0].setValue(record.get("description"));
		me.courseDetailsPopUp.query("#minCreditHours")[0].setValue(record.get("minCreditHours"));
		me.courseDetailsPopUp.query("#maxCreditHours")[0].setValue(record.get("maxCreditHours"));
		me.courseDetailsPopUp.query("#departmentCode")[0].setValue(record.get("departmentCode"));
		me.courseDetailsPopUp.query("#divisionCode")[0].setValue(record.get("divisionCode"));
		me.courseDetailsPopUp.query("#tags")[0].setValue(record.get("tags"));
		var masterSylComponent = me.courseDetailsPopUp.query("#mastersyllabus")[0];
		if(!record.get("masterSyllabusLink") || record.get("masterSyllabusLink") =='')
		{
			masterSylComponent.hidden = true;
		}
		var academicLinkComponent = me.courseDetailsPopUp.query("#academiclink")[0];
		if(!record.get("academicLink") || record.get("academicLink") =='')
		{
			academicLinkComponent.hidden = true;
		}
		masterSylComponent.setFieldLabel("<a href=\""+record.get("masterSyllabusLink")+"\">Master Syllabus</a>");
		academicLinkComponent.setFieldLabel("<a href=\""+record.get("academicLink")+"\">Academic Link</a>");
		
		me.courseDetailsPopUp.show();
    },

    destroy:function(){
	    var me=this;
		if(me.courseDetailsPopUp != null && !me.courseDetailsPopUp.isDestroyed)
			me.courseDetailsPopUp.close();
    }
    
    
});