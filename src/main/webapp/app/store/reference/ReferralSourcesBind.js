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
/**
 * This is a special case where the item selector on the add/edit person
 * appointment view is bound to the store and adds new fields. Since,
 * I don't yet know all of the fields to clean-up and the new
 * fields cause issues in the reference admin tools after loading the
 * bound store, I am creating a separate store to use for this case.
 * TODO: Clean-up this condition and resolve in a better way.
 */
Ext.define('Ssp.store.reference.ReferralSourcesBind', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.ReferralSource',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('referralSource')});
    }
});