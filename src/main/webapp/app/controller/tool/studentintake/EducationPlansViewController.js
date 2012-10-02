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
Ext.define('Ssp.controller.tool.studentintake.EducationPlansViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	formUtils: 'formRendererUtils',
    	model: 'currentStudentIntake',
    	sspConfig: 'sspConfig'
    },
    control: {
    	parentsDegreeField: '#collegeDegreeForParents',
    	collegeDegreeForParentsCheckOn: '#collegeDegreeForParentsCheckOn',
    	collegeDegreeForParentsCheckOff: '#collegeDegreeForParentsCheckOff',
    	specialNeedsField: '#specialNeeds',
    	specialNeedsCheckOn: '#specialNeedsCheckOn',
    	specialNeedsCheckOff: '#specialNeedsCheckOff'
    },
	init: function() {
		var me=this;
		var personEducationPlan = me.model.get('personEducationPlan');
		var parentsDegreeLabel = me.sspConfig.get('educationPlanParentsDegreeLabel');
		var specialNeedsLabel = me.sspConfig.get('educationPlanSpecialNeedsLabel');
		var collegeDegreeForParents = me.model.get('personEducationPlan').get('collegeDegreeForParents')
		var specialNeeds = me.model.get('personEducationPlan').get('specialNeeds');
		me.getParentsDegreeField().setFieldLabel(parentsDegreeLabel);
		me.getSpecialNeedsField().setFieldLabel(specialNeedsLabel);
		
		if ( personEducationPlan != null && personEducationPlan != undefined )
		{
			// college degree for parents
			me.getCollegeDegreeForParentsCheckOn().setValue(collegeDegreeForParents);
			me.getCollegeDegreeForParentsCheckOff().setValue(!collegeDegreeForParents);
			
			me.getSpecialNeedsCheckOn().setValue( specialNeeds );
			me.getSpecialNeedsCheckOff().setValue( !specialNeeds );
		}		

		return me.callParent(arguments);
    }
});