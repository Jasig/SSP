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
Ext.define('Ssp.model.reference.SuccessIndicator', {
    extend: 'Ssp.model.reference.AbstractReference',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
        apiProperties: 'apiProperties'
    },

    fields: [
        {name: 'modelCode', type: 'string'},
        {name: 'modelName', type: 'string'},
        {name: 'indicatorGroup', type: 'string', defaultValue: 'RISK'},

        {name: 'code', type: 'string'},
        {name: 'instruction', type: 'string'},
        {name: 'evaluationType', type: 'string', defaultValue: 'SCALE'},

        {name: 'scaleEvaluationHighFrom', type: 'float', defaultValue: undefined, useNull: true},
        {name: 'scaleEvaluationHighTo', type: 'float', defaultValue: undefined, useNull: true},
        {name: 'scaleEvaluationMediumFrom', type: 'float', defaultValue: undefined, useNull: true},
        {name: 'scaleEvaluationMediumTo', type: 'float', defaultValue: undefined, useNull: true},
        {name: 'scaleEvaluationLowFrom', type: 'float', defaultValue: undefined, useNull: true},
        {name: 'scaleEvaluationLowTo', type: 'float', defaultValue: undefined, useNull: true},

        {name: 'stringEvaluationHigh', type: 'string', defaultValue: undefined},
        {name: 'stringEvaluationMedium', type: 'string', defaultValue: undefined},
        {name: 'stringEvaluationLow', type: 'string', defaultValue: undefined},

        {name: 'noDataExistsEvaluation', type: 'string', defaultValue: 'DEFAULT'},
        {name: 'noDataMatchesEvaluation', type: 'string', defaultValue: 'DEFAULT'},

        {name: 'sortOrder', type: 'int', defaultValue: 0}
    ]
});