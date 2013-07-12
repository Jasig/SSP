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
Ext.define('Ssp.model.util.TreeRequest', {
    extend: 'Ext.data.Model',
    fields: [{name: 'url', type: 'string'},
             {name: 'nodeType', type: 'string'},
             {name: 'isLeaf', type: 'boolean'},
             {name: 'nodeToAppendTo', defaultValue: null},
             {name: 'destroyBeforeAppend', type: 'boolean', defaultValue: false},
             {name: 'enableCheckedItems', type: 'boolean', defaultValue: true},
             {name: 'expanded', type:'boolean',defaultValue: false},
             {name: 'expandable', type:'boolean', defaultValue: true},
             {name: 'callbackFunc',type:'auto'},
             {name: 'callbackScope', type: 'auto'},
             {name: 'removeParentWhenNoChildrenExist', type: 'boolean', defaultValue: false},
             {name: 'includeToolTip', type: 'boolean', defaultValue: false},
             {name: 'toolTipFieldName', type: 'string', defaultValue: ""},
             {name: 'node', type: 'auto'}]
});