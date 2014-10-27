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
Ext.define('Ssp.model.tool.indicator.SuccessIndicator', {
	extend: 'Ssp.model.AbstractBase',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
         apiProperties: 'apiProperties'
    }, 

	fields: [
		{name: 'indicatorGroup', type: 'string', defaultValue: 'RISK'},
		{name: 'modelCode', type: 'string'},
		{name: 'modelName', type: 'string'},
		{name: 'name', type: 'string'},
		{name: 'description', type: 'string'},
		{name: 'code', type: 'string'},
		{name: 'instruction', type: 'string'},
		{name: 'evaluationType', type: 'string', defaultValue: 'SCALE'},
		
		{name: 'scaleEvaluationHighFrom', type: 'float'},
		{name: 'scaleEvaluationHighTo', type: 'float'},
		{name: 'scaleEvaluationMediumFrom', type: 'float'},
		{name: 'scaleEvaluationMediumTo', type: 'float'},
		{name: 'scaleEvaluationLowFrom', type: 'float'},
		{name: 'scaleEvaluationLowTo', type: 'float'},
		
		{name: 'stringEvaluationHigh', type: 'string'},
		{name: 'stringEvaluationMedium', type: 'string'},
		{name: 'stringEvaluationLow', type: 'string'},
		
		{name: 'noDataExistsEvaluation', type: 'string'},
		{name: 'noDataMatchEvaluation', type: 'string'},
		
		{name: 'sortOrder', type: 'int'}
	]
});