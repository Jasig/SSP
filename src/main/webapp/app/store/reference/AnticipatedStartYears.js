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
Ext.define('Ssp.store.reference.AnticipatedStartYears', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.reference.AbstractReference',
    autoLoad: false,
    constructor: function(){
		return this.callParent(arguments);
    },
    data: [{ name: "2010", description: "2010" },
           { name: "2011", description: "2011" },
           { name: "2012", description: "2012" },
           { name: "2013", description: "2013" },
           { name: "2014", description: "2014" },
           { name: "2015", description: "2015" }]
});