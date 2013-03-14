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
Ext.define('Ssp.model.AbstractBase', {
    extend: 'Ext.data.Model',
    fields: [{name: 'id', type: 'string'},
             {name: 'createdBy',
              convert: function(value, record) {
		            var obj  = {id:value.id || '',
		                        firstName: value.firstName || '',
		                        lastName: value.lastName || ''};	
		            return obj;
		      }
             },
             {name: 'modifiedBy',
              convert: function(value, record) {
 		            var obj  = {id:value.id || '',
 		                        firstName: value.firstName || '',
 		                        lastName: value.lastName || ''};	
 		            return obj;
 		      }
             }
             ,{name: 'createdDate', type: 'date', dateFormat: 'time'}
             /*,{name: 'objectStatus', type: 'string'}*/
             /*,
             {name: 'modifiedDate', type: 'date', dateFormat: 'time'},
             */],
    
	populateFromGenericObject: function( record ){
		if (record != null)
		{
			for (fieldName in this.data)
	    	{
				if ( record[fieldName] )
	    		{
	    			this.set( fieldName, record[fieldName] );
	    		}
	    	}
		}
    },
    
    getCreatedByPersonName: function(){
    	return this.get('createdBy').firstName + ' ' + this.get('createdBy').lastName;
    },

    getCreatedById: function(){
    	return this.get('createdBy').id + ' ' + this.get('createdBy').id;
    }

});

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
Ext.define('Ssp.model.reference.AbstractReference', {
    extend: 'Ssp.model.AbstractBase',
    fields: [{name: 'name', type: 'string'},
             {name: 'description', type: 'string'}]
});
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
Ext.define('Ssp.store.reference.AbstractReferences', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.reference.AbstractReference',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
        apiProperties: 'apiProperties'
    },

	constructor: function(){
		var me=this;
		Ext.apply(me, { 
						    proxy: me.apiProperties.getProxy(''), 
							autoLoad: false,
							autoSync: false,
						    pageSize: me.apiProperties.getPagingSize(),
						    params : {
								page : 0,
								start : 0,
								limit : me.apiProperties.getPagingSize()
							}						
						}
		);
		return me.callParent(arguments);
	}
});
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
Ext.define('Ssp.view.admin.AdminMain', {
	extend: 'Ext.panel.Panel',
    alias: 'widget.adminmain',
    id: 'AdminMain',
    title: 'Admin Main',
    height: '100%',
    width: '100%',
	initComponent: function() {	
		Ext.apply(this, 
				{
				    layout: {
				    	type: 'border',
				    	align: 'stretch'
				    },
				    split: true
				});
		
	     this.callParent(arguments);
	}
});
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
Ext.define('Ssp.view.admin.AdminForms', {
	extend: 'Ext.container.Container',
	alias : 'widget.adminforms',
    id: 'AdminForms',
	width: '100%',
	height: '100%',
	layout: 'fit'
});
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
Ext.define('Ssp.view.component.MappedTextField', {
	extend: 'Ext.form.field.Text',
	alias : 'widget.mappedtextfield',
	config: {
		parentId: null,
		validationExpression: '[a-zA-Z]'
	},
	initComponent: function() {	
		return this.callParent(arguments);
	}
});
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
Ext.define('Ssp.view.component.MappedTextArea', {
	extend: 'Ext.form.field.TextArea',
	alias : 'widget.mappedtextarea',
	config: {
		parentId: null,
		validationExpression: '[a-zA-Z]'
	},
	initComponent: function() {	
		return this.callParent(arguments);
	}
});
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
Ext.define('Ssp.view.component.MappedCheckBox', {
	extend: 'Ext.form.field.Checkbox',
	alias : 'widget.mappedcheckbox',
	config: {
		mapId: null
	},
	initComponent: function() {	
		return this.callParent(arguments);
	}
});
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
Ext.define('Ssp.view.component.MappedRadioButton', {
	extend: 'Ext.form.field.Radio',
	alias : 'widget.mappedradiobutton',
	config: {
		parentId: null,
		validationExpression: '[a-zA-Z]'
	},
	initComponent: function() {	
		return this.callParent(arguments);
	}
});
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
Ext.define('Ssp.view.Tools', {
	extend: 'Ext.panel.Panel',
	alias : 'widget.tools',
	id: 'Tools',
	width: '100%',
	height: '100%',
	layout: 'fit'
});
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
Ext.define('Ssp.view.tools.profile.SpecialServiceGroups', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.profilespecialservicegroups',
    mixins: ['Deft.mixin.Injectable'],
    inject: {
        store: 'profileSpecialServiceGroupsStore'
    },
    width: '100%',
    height: '100%',
    initComponent: function(){
        var me = this;
        Ext.apply(me, {
            title: 'Service Groups',
            hideHeaders: true,
            store: me.store,
            autoScroll: true,
            tools: [{
                xtype: 'button',
                itemId: 'serviceGroupEdit',
                width: 20,
                height: 20,
                cls: 'editPencilIcon',
                text:'',
                tooltip: 'Edit'
            }],
            columns: [{
                header: 'Group',
                dataIndex: 'name',
                flex: 1,
            }],
        });
        
        return me.callParent(arguments);
    }
});

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
Ext.define('Ssp.view.tools.profile.ReferralSources', {
	extend: 'Ext.grid.Panel',
	alias : 'widget.profilereferralsources',
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	store: 'profileReferralSourcesStore'
    },
	width: '100%',
	height: '100%',
	initComponent: function() {
		var me=this;
		Ext.apply(me, 
				{
			        hideHeaders: true,
			        autoScroll: true,
		            store: me.store,
    		        columns: [
    		                { header: 'Source',  
    		                  dataIndex: 'name',
    		                  flex: 1,
    		                }],
				});
		
		return me.callParent(arguments);
	}
});
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
Ext.define('Ssp.view.tools.profile.ServiceReasons', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.profileservicereasons',
    mixins: ['Deft.mixin.Injectable'],
    inject: {
        store: 'profileServiceReasonsStore'
    },
    width: '100%',
    height: '100%',
    initComponent: function(){
        var me = this;
        Ext.apply(me, {
            title: 'Service Reasons',
            hideHeaders: true,
            autoScroll: true,
            
            store: me.store,
            tools: [{
                xtype: 'button',
                itemId: 'serviceReasonEdit',
                width: 20,
                height: 20,
				cls: 'editPencilIcon',
				text:'',
				tooltip: 'Edit'
            }],
            columns: [{
                header: 'Reason',
                dataIndex: 'name',
                flex: 1
            
            }],
        });
        
        return me.callParent(arguments);
    }
});

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
Ext.define('Ssp.view.tools.actionplan.AddTask', {
	extend: 'Ext.panel.Panel',
	alias: 'widget.addtask',
	mixins: [ 'Deft.mixin.Injectable'],
	inject: {
    	model: 'currentTask'
    },
	width: '100%',
    height: '100%',
    initComponent: function() {
		Ext.apply(this,{
			autoScroll: true,
			layout: {
				type: 'vbox',
				align: 'stretch'
			},
			title: 'Add Action Plan Tasks',
			items: [{ xtype: 'tasktree', flex: .5 },
			        { xtype: 'addtaskform', flex: .5 }]
		});
		
		return this.callParent(arguments);
	}
});

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
Ext.define('Ssp.view.tools.studentintake.Challenges', {
	extend: 'Ext.form.Panel',
	alias: 'widget.studentintakechallenges',
	id : 'StudentIntakeChallenges',
    width: '100%',
    height: '100%',
	initComponent: function() {
		var me=this;
		Ext.apply(me, 
				{
		    	    autoScroll: true,
					border: 0,	
				    bodyPadding: 5,
				    layout: 'anchor',
				    defaults: {
				        anchor: '100%'
				    },
				    defaultType: 'checkbox'
				});
		
		return me.callParent(arguments);
	}
});
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
Ext.define('Ssp.view.tools.studentintake.EducationLevels', {
	extend: 'Ext.form.Panel',
	alias: 'widget.studentintakeeducationlevels',
	id : 'StudentIntakeEducationLevels',
    width: '100%',
    height: '100%',
	initComponent: function() {	
		Ext.apply(this, 
				{
					autoScroll: true,
				    bodyPadding: 5,
				    border: 0,
				    layout: 'anchor',
				    defaults: {
				        anchor: '100%'
				    },
				    defaultType: 'checkbox'
				});
		
		return this.callParent(arguments);
	}
});
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
Ext.define('Ssp.view.tools.studentintake.Funding', {
	extend: 'Ext.form.Panel',
	alias: 'widget.studentintakefunding',
	id : 'StudentIntakeFunding',   
    width: '100%',
    height: '100%',
	initComponent: function() {	
		Ext.apply(this, 
				{
					autoScroll: true,
					border: 0,
				    bodyPadding: 5,
				    layout: 'anchor',
				    defaults: {
				        anchor: '100%'
				    },
				    defaultType: 'checkbox'
				});
		
		return this.callParent(arguments);
	}
});
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
Ext.define('Ssp.view.tools.sis.StudentInformationSystem', {
	extend: 'Ext.panel.Panel',
	alias : 'widget.studentinformationsystem',
	title: 'Student Information System',	
	width: '100%',
	height: '100%',   
	autoScroll: true,
	initComponent: function() {	
		var me=this;
		Ext.apply(me, 
				{		
					border: 0,
				    items: [ Ext.createWidget('tabpanel', {
				        width: '100%',
				        height: '100%',
				        activeTab: 0,
						border: 0,
				        items: [ { title: 'Registration',
				        	       autoScroll: true,
				        		   items: [{xtype: 'sisregistration'}]
				        		},{
				            		title: 'Transcript',
				            		autoScroll: true,
				            		border: 0,
				            		items: [{xtype: 'sistranscript'}]
				        		},{
				            		title: 'Assessment',
				            		autoScroll: true,
				            		border: 0,
				            		items: [{xtype: 'sisassessment'}]
				        		}]
				    })]
			    
		});
		
		return me.callParent(arguments);
	}
});
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
Ext.define('Ssp.view.tools.sis.Registration', {
	extend: 'Ext.form.Panel',
	alias: 'widget.sisregistration',
    width: '100%',
    height: '100%',
    autoScroll: true,
    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'displayfield',
                    fieldLabel: 'Academic Status',
                    anchor: '100%'
                },
                {
                    xtype: 'displayfield',
                    fieldLabel: 'Registration Status',
                    anchor: '100%'
                },
                {
                    xtype: 'displayfield',
                    fieldLabel: 'Start Term',
                    anchor: '100%'
                },
                {
                    xtype: 'displayfield',
                    fieldLabel: 'CUM GPA',
                    anchor: '100%'
                }
            ]
        });

        me.callParent(arguments);
    }
});
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
Ext.define('Ssp.view.tools.sis.Assessment', {
	extend: 'Ext.grid.Panel',
	alias: 'widget.sisassessment',
    width: '100%',
    height: '100%',
    autoScroll: true,
    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            columns: [
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'string',
                    text: 'Type'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'string',
                    text: 'Score'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'string',
                    text: 'Status'
                },
                {
                    xtype: 'gridcolumn',
                    text: 'Date',
				    renderer: Ext.util.Format.dateRenderer('m/d/Y')
                }
            ],
            viewConfig: {

            }
        });

        me.callParent(arguments);
    }
});
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
Ext.define('Ssp.model.reference.PersonalityType', {
	extend: 'Ssp.model.reference.AbstractReference',
    fields: []
});
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
Ext.define('Ssp.store.reference.PersonalityTypes', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.PersonalityType',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('personalityType')});
    }
});
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
Ext.define('Ssp.model.reference.MilitaryAffiliation', {
	extend: 'Ssp.model.reference.AbstractReference',
    fields: []
});
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
Ext.define('Ssp.store.reference.MilitaryAffiliations', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.MilitaryAffiliation',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('militaryAffiliation')});
    }
});
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
Ext.define('Ssp.model.Placement', {
    extend: 'Ssp.model.AbstractBase',
    fields: [{name:'score',type:'string'},
             {name:'status',type:'string'},
             {name:'name',type:'string'},
             {name:'subTestName',type:'string'},
             {
            	 name: 'type',
            	 convert: function(value, record) {
            		 return record.get('name') + ' '+ record.get('subTestName');
            	 }
             },             
             {name: 'takenDate',type: 'date', dateFormat: 'time'}]
});
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
Ext.define('Ssp.store.Placement', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.Placement',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
        apiProperties: 'apiProperties'
    },
	constructor: function(){
		Ext.apply(this, {
							proxy: this.apiProperties.getProxy(this.apiProperties.getItemUrl('placement')),
							autoLoad: false
						});
		return this.callParent(arguments);
	}
});
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
Ext.define('Ssp.model.reference.DisabilityStatus', {
	extend: 'Ssp.model.reference.AbstractReference',
    fields: []
});
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
Ext.define('Ssp.store.reference.DisabilityStatuses', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.DisabilityStatus',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('disabilityStatus')});
    }
});
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
Ext.define('Ssp.model.reference.DisabilityType', {
	extend: 'Ssp.model.reference.AbstractReference',
    fields: []
});
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
Ext.define('Ssp.store.reference.DisabilityTypes', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.DisabilityType',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('disabilityType')});
    }
});
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
Ext.define('Ssp.model.reference.Lassi', {
	extend: 'Ssp.model.reference.AbstractReference',
    fields: []
});
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
Ext.define('Ssp.store.reference.Lassis', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.Lassi',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('lassi')});
    }
});
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
Ext.define('Ssp.model.reference.DisabilityAccommodation', {
	extend: 'Ssp.model.reference.AbstractReference',
    fields: [{name: 'useDescription', type: 'boolean'},
             {name: 'descriptionFieldLabel', type: 'string'},
             {name: 'descriptionFieldType', type: 'string'}]
});
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
Ext.define('Ssp.store.reference.DisabilityAccommodations', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.DisabilityAccommodation',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('disabilityAccommodation')});
    }
});
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
Ext.define('Ssp.model.reference.DisabilityAgency', {
	extend: 'Ssp.model.reference.AbstractReference',
    fields: []
});
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
Ext.define('Ssp.store.reference.DisabilityAgencies', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.DisabilityAgency',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('disabilityAgency')});
    }
});
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
Ext.define('Ssp.model.reference.CampusService', {
	extend: 'Ssp.model.reference.AbstractReference',
    fields: []
});
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
Ext.define('Ssp.store.reference.CampusServices', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.CampusService',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('campusService')});
    }
});
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
Ext.define('Ssp.model.reference.ConfidentialityDisclosureAgreement', {
    extend: 'Ssp.model.reference.AbstractReference',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties'
    },    
    fields: [{name:'text',type:'string'}],

	constructor: function(){
		Ext.apply(this.getProxy(), 
				{ 
			url: this.apiProperties.createUrl( this.apiProperties.getItemUrl('confidentialityDisclosureAgreement') )
			    }
		);
		return this.callParent(arguments);
	}, 	
	
	proxy: {
		type: 'rest',
		url: '',
		actionMethods: {
			create: "POST", 
			read: "GET", 
			update: "PUT",
			destroy: "DELETE"
		},
		reader: {
			type: 'json'
		},
	    writer: {
	        type: 'json',
	        successProperty: 'success'
	    }
	}
});
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
Ext.define('Ssp.store.reference.ConfidentialityDisclosureAgreements', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.ConfidentialityDisclosureAgreement',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('confidentialityDisclosureAgreement')});
    }
});
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
Ext.define('Ssp.model.reference.EarlyAlertReferral', {
	extend: 'Ssp.model.reference.AbstractReference',
    fields: [{name:'acronym',type:'string'},
             {name:'sortOrder',type:'int'}]
});
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
 * This is a special case where the item selector on the add/edit early alert
 * response referrals is bound to the store and adds new fields. Since,
 * I don't yet know all of the fields to clean-up and the new
 * fields cause issues in the reference admin tools after loading the
 * bound store, I am creating a separate store to use for this case.
 * TODO: Clean-up this condition and resolve in a better way.
 */
Ext.define('Ssp.store.reference.EarlyAlertReferralsBind', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.EarlyAlertReferral',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('earlyAlertReferral')});
    }
});
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
Ext.define('Ssp.model.reference.EducationGoal', {
	extend: 'Ssp.model.reference.AbstractReference',
    fields: []
});
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
Ext.define('Ssp.store.reference.EducationGoals', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.EducationGoal',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('educationGoal')});
    }
});
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
Ext.define('Ssp.model.reference.EducationLevel', {
	extend: 'Ssp.model.reference.AbstractReference',
    fields: []
});
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
Ext.define('Ssp.store.reference.EducationLevels', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.EducationLevel',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('educationLevel')});
    }
});
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
Ext.define('Ssp.model.reference.ReferralSource', {
	extend: 'Ssp.model.reference.AbstractReference',
    fields: []
});
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
Ext.define('Ssp.model.reference.SpecialServiceGroup', {
	extend: 'Ssp.model.reference.AbstractReference',
    fields: []
});
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
Ext.define('Ssp.store.reference.SpecialServiceGroupsBind', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.SpecialServiceGroup',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('specialServiceGroup')});
    }
});
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
Ext.define('Ssp.controller.tool.accommodation.GeneralViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
	init: function() {
		var me=this;
		return me.callParent(arguments);
    }
});
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
Ext.define('Ssp.controller.tool.accommodation.AccommodationToolViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
        apiProperties: 'apiProperties',
        authenticatedPerson: 'authenticatedPerson',
        appEventsController: 'appEventsController',
    	formUtils: 'formRendererUtils',
    	disabilityAccommodationsStore: 'disabilityAccommodationsStore',
    	disabilityAgenciesStore: 'disabilityAgenciesStore',
    	accommodation: 'currentAccommodation',
    	disabilityStatusesStore: 'disabilityStatusesStore',
    	disabilityTypesStore: 'disabilityTypesStore',
        personLite: 'personLite',
        person: 'currentPerson',
        service: 'accommodationService'
    }, 
    config: {
    	accommodationForm: null
    },
    control: {
		'saveButton': {
			click: 'onSaveClick'
		},
		
    	'cancelButton': {
    		click: 'onCancelClick'
    	},
    	
    	saveSuccessMessage: '#saveSuccessMessage'
	},
    
	init: function() {
		var me=this;	
		
		// Load the views dynamically
		// otherwise duplicate id's will be registered
		// on cancel
		me.initAccommodationViews();
	
		// This enables mapped text fields and mapped text areas
		// to be shown or hidden upon selection from a parent object
		// such as a dynamic check box.
		me.appEventsController.getApplication().addListener('dynamicCompChange', function( comp ){
			var tfArr = Ext.ComponentQuery.query('.mappedtextfield');
			var taArr = Ext.ComponentQuery.query('.mappedtextarea');
			
			// show or hide mapped text fields
			Ext.each(tfArr,function(item, index){
				if (comp.id==item.parentId)
				{
					if(comp.checked)
					{
						item.show();
						Ext.apply(item,{allowBlank:false});
					}else{
						item.hide();
						Ext.apply(item,{allowBlank:true});
					}
				}	
			},this);
			
			// show or hide mapped text area components
			Ext.each(taArr,function(item, index){
				if (comp.id==item.parentId)
				{
					if(comp.checked)
					{
						item.show();
						Ext.apply(item,{allowBlank:false});
					}else{
						item.hide();
						Ext.apply(item,{allowBlank:true});
					}
				}	
			},this);
		},me);
		
		// display loader
		me.getView().setLoading( true );
		
		me.service.get(me.personLite.get('id'),{
			success: me.getAccommodationSuccess,
			failure: me.getAccommodationFailure,
			scope: me
		});
		
		return me.callParent(arguments);
    },

    destroy: function() {
    	//this.appEventsController.removeEvent({eventName: 'dynamicCompChange', callBackFunc: this.onDynamicCompChange, scope: this});

        return this.callParent( arguments );
    },     
    
    initAccommodationViews: function(){
    	var me=this;
    	var items = [ Ext.createWidget('tabpanel', {
	        width: '100%',
	        height: '100%',
	        activeTab: 0,
			border: 0,
	        items: [ { title: 'General',
	        	       autoScroll: true,
	        		   items: [{xtype: 'disabilitygeneral'}]
	        		},{
	            		title: 'Agency Contacts',
	            		autoScroll: true,
	            		items: [{xtype: 'disabilityagencycontacts'}]
	        		},{
	            		title: 'Disability',
	            		autoScroll: true,
	            		items: [{xtype: 'disabilitytypes'}]
	        		},{
	            		title: 'Disposition',
	            		autoScroll: true,
	            		items: [{xtype: 'disabilitydisposition'}]
	        		},{
	            		title: 'Accommodations',
	            		autoScroll: true,
	            		items: [{xtype: 'disabilityaccommodations'}]
	        		}]
	    	})
	    
		];
    	
    	me.getView().add( items );
    },
    
    getAccommodationSuccess: function( r, scope ){
    	var me=scope;
    	var accommodationClass;
		
    	// hide the loader
    	me.getView().setLoading( false );
    	
    	if ( r != null )
    	{
            accommodationClass = Ext.ModelManager.getModel('Ssp.model.tool.accommodation.AccommodationForm');
    		me.accommodation.data = accommodationClass.getProxy().getReader().read( r ).records[0].data;
    		me.buildAccommodation( me.accommodation );
    	}else{
    		Ext.Msg.alert('Error','There was an error loading the Accommodation form for this student.');
    	}
	},
	
	getAccommodationFailure: function( response, scope){
		var me=scope;
		me.getView().setLoading( false );
	},
    
	buildAccommodation: function( formData ){
		var me=this; // formData
		
    	// PERSON RECORD
		var person = formData.data.person;
		var personDisability = formData.data.personDisability;
		var personDisabilityAgencies = formData.data.personDisabilityAgencies;
		var personDisabilityTypes = formData.data.personDisabilityTypes;
		var personDisabilityAccommodations = formData.data.personDisabilityAccommodations;
		
		var accommodationGeneralForm = Ext.getCmp('AccommodationGeneral');
		var accommodationAgencyContactNameForm = Ext.getCmp('AccommodationAgencyContactName');
		var accommodationDispositionForm = Ext.getCmp('AccommodationDisposition');
		
		/*
		 * For drawing reference check boxes dynamically
		 */
		var disabilityAgencyFormProps;
		var disabilityAgenciesAdditionalFieldsMap;	
		var disabilityTypesFormProps;
		var disabilityTypesAdditionalFieldsMap;
		var disabilityAccommodationsFormProps;
		var disabilityAccommodationsAdditionalFieldsMap;
		var defaultLabelWidth;

		// REFERENCE OBJECTS
		var disabilityAccommodations = me.formUtils.alphaSortByField( formData.data.referenceData.disabilityAccommodations, 'name' );
		var disabilityAgencies = me.formUtils.alphaSortByField( formData.data.referenceData.disabilityAgencies, 'name' );
		var disabilityStatuses =  me.formUtils.alphaSortByField( formData.data.referenceData.disabilityStatuses, 'name' );
		var disabilityTypes = me.formUtils.alphaSortByField( formData.data.referenceData.disabilityTypes, 'name');
		
		me.disabilityAgenciesStore.loadData( disabilityAgencies );
		me.disabilityAccommodationsStore.loadData( disabilityAccommodations );
		me.disabilityStatusesStore.loadData( disabilityStatuses );
		me.disabilityTypesStore.loadData( disabilityTypes );
		
		// LOAD RECORDS FOR EACH OF THE FORMS
		
		if ( personDisability != null && personDisability != undefined ){
			accommodationGeneralForm.loadRecord( personDisability );
		}
		
		if ( personDisability != null && personDisability != undefined ){
			accommodationAgencyContactNameForm.loadRecord( personDisability );
		}
		
		if ( personDisability != null && personDisability != undefined ){
			accommodationDispositionForm.loadRecord( personDisability );
		}	

		defaultLabelWidth = 150;

		// DEFINE DISABILITY AGENCY DYNAMIC FIELDS
		
		disabilityAgenciesAdditionalFieldsMap = [{parentId: Ssp.util.Constants.DISABILITY_AGENCY_OTHER_ID, 
											  parentName: "other",
											  name: "description", 
											  label: "Please Explain", 
											  fieldType: "mappedtextarea",
											  labelWidth: defaultLabelWidth}];
		
		disabilityAgencyFormProps = {
				mainComponentType: 'checkbox',
				formId: 'AccommodationDisabilityAgencies',
                fieldSetTitle: 'Select all agencies that apply',
                itemsArr: disabilityAgencies, 
                selectedItemsArr: personDisabilityAgencies, 
                idFieldName: 'id', 
                selectedIdFieldName: 'disabilityAgencyId',
                additionalFieldsMap: disabilityAgenciesAdditionalFieldsMap };
		
		me.formUtils.createForm( disabilityAgencyFormProps );
		
		// DEFINE DISABILITY TYPE DYNAMIC FIELDS
		
		disabilityTypesAdditionalFieldsMap = [];
		
		// For each disability type provide a mapped description field
		// this will allow the user to provide a description for the disability
		Ext.Array.each(disabilityTypes, function(name,index,self){
			var item = disabilityTypes[index];
			var map = {parentId: item.id, 
				  parentName: item.name,
				  name: "description", 
				  label: "Please Explain", 
				  fieldType: "mappedtextarea",
				  labelWidth: 80,
				  width: '350'};
			disabilityTypesAdditionalFieldsMap.push(map);
		});	
		
		disabilityTypesFormProps = {
		mainComponentType: 'checkbox',
		formId: 'AccommodationDisabilityTypes',
		fieldSetTitle: 'Select all that apply',
		itemsArr: disabilityTypes, 
		selectedItemsArr: personDisabilityTypes, 
		idFieldName: 'id', 
		selectedIdFieldName: 'disabilityTypeId',
		additionalFieldsMap: disabilityTypesAdditionalFieldsMap };
		
		me.formUtils.createForm( disabilityTypesFormProps );

		// DEFINE DISABILITY ACCOMMODATION DYNAMIC FIELDS			

		disabilityAccommodationsAdditionalFieldsMap = [];
		
		// For each disability type provide a mapped description field
		// this will allow the user to provide a description for the disability
		Ext.Array.each(disabilityAccommodations, function(name,index,self){
			var map;
			var item = disabilityAccommodations[index];
			var fieldType = "mappedtextfield";
			if (item.useDescription === true)
			{
				// determine field type
				// long = mappedtextarea
				// short = mappedtextfield
				if (item.descriptionFieldType.toLowerCase() === "long")
				{
					fieldType = "mappedtextarea";
				}				
				
				map = {parentId: item.id, 
						  parentName: item.name,
						  name: "description", 
						  label: item.descriptionFieldLabel, 
						  fieldType: fieldType,
						  labelWidth: 100,
						  width: '350'};
				disabilityAccommodationsAdditionalFieldsMap.push(map);				
			}
		});		
		
		disabilityAccommodationsFormProps = {
			mainComponentType: 'checkbox',
			formId: 'AccommodationDisabilityAccommodations',
			fieldSetTitle: 'Select all that apply',
			itemsArr: disabilityAccommodations, 
			selectedItemsArr: personDisabilityAccommodations, 
			idFieldName: 'id', 
			selectedIdFieldName: 'disabilityAccommodationId',
			additionalFieldsMap: disabilityAccommodationsAdditionalFieldsMap };
		
		me.formUtils.createForm( disabilityAccommodationsFormProps );
	},
	
	onSaveClick: function( button ) {
		var me=this;
		var formUtils = me.formUtils;

		var accommodationForm = Ext.getCmp('AccommodationGeneral').getForm();
		var disabilityAgencyContactNameForm = Ext.getCmp('AccommodationAgencyContactName').getForm();
		var disabilityDispositionForm = Ext.getCmp('AccommodationDisposition').getForm();
		var disabilityAgenciesForm = Ext.getCmp('AccommodationDisabilityAgencies').getForm();
		var disabilityTypesForm = Ext.getCmp('AccommodationDisabilityTypes').getForm();
		var disabilityAccommodationsForm = Ext.getCmp('AccommodationDisabilityAccommodations').getForm();

		var disabilityAgenciesFormValues = null;
		var disabilityAccommodationsFormValues = null;
		var disabilityTypesFormValues = null;
		var accommodationFormModel = null;
		var personId = "";
		var accommodationData = {};
		
		var formsToValidate = [accommodationForm,
		             disabilityAgencyContactNameForm,
		             disabilityDispositionForm,
		             disabilityAgenciesForm,
		             disabilityAccommodationsForm,
		             disabilityTypesForm];

		// validate and save the model
		var validateResult = me.formUtils.validateForms( formsToValidate );
		if ( validateResult.valid )
		{
			// update the model with changes from the forms
            accommodationForm.updateRecord( me.accommodation.get('personDisability') );
	
			// update the model with changes from the forms
            disabilityAgencyContactNameForm.updateRecord( me.accommodation.get('personDisability') );

			// update the model with changes from the forms
			disabilityDispositionForm.updateRecord( me.accommodation.get('personDisability') );
			
			// save the full model
			personId = me.accommodation.get('person').data.id;
			accommodationData = {
				person: me.accommodation.get('person').data,
				personDisability: me.accommodation.get('personDisability').data,
				personDisabilityAgencies: [],
				personDisabilityAccommodations: [],
				personDisabilityTypes: []
			};
						
			// date saved as null is ok 
			if (accommodationData.personDisability.eligibleLetterDate != null)
			{
				// account for date offset
				accommodationData.personDisability.eligibleLetterDate = me.formUtils.fixDateOffset( accommodationData.personDisability.eligibleLetterDate );
			}

			// date saved as null is ok 
			if (accommodationData.personDisability.ineligibleLetterDate != null)
			{
				// account for date offset
                accommodationData.personDisability.ineligibleLetterDate = me.formUtils.fixDateOffset( accommodationData.personDisability.ineligibleLetterDate );
			}			
			
			// cleans properties that will be unable to be saved if not null
			// arrays set to strings should be null rather than string in saved
			// json
            accommodationData.person = me.person.setPropsNullForSave( accommodationData.person );

            accommodationData.personDisability.personId = personId;
			// Clean personDisability props that won't save as empty string
            accommodationData.personDisability = me.accommodation.get('personDisability').setPropsNullForSave( accommodationData.personDisability );
			
			disabilityAgenciesFormValues = disabilityAgenciesForm.getValues();
            accommodationData.personDisabilityAgencies = formUtils.createTransferObjectsFromSelectedValues('disabilityAgencyId', disabilityAgenciesFormValues, personId);

			disabilityTypesFormValues = disabilityTypesForm.getValues();
            accommodationData.personDisabilityTypes = formUtils.createTransferObjectsFromSelectedValues('disabilityTypeId', disabilityTypesFormValues, personId);
			
			disabilityAccommodationsFormValues = disabilityAccommodationsForm.getValues();
            accommodationData.personDisabilityAccommodations = formUtils.createTransferObjectsFromSelectedValues('disabilityAccommodationId', disabilityAccommodationsFormValues, personId);
			
			// display loader
			me.getView().setLoading( true );
			
			me.service.save(me.personLite.get('id'), accommodationData, {
				success: me.saveAccommodationSuccess,
				failure: me.saveAccommodationFailure,
				scope: me
			});

		}else{
			me.formUtils.displayErrors( validateResult.fields );
		}
	},
	
	saveAccommodationSuccess: function(r, scope) {
		var me=scope;

		me.getView().setLoading( false );
		
		if( r.success == true ) {
			me.formUtils.displaySaveSuccessMessage( me.getSaveSuccessMessage() );							
		}								
	},
	
	saveAccommodationFailure: function(response, scope) {
		var me=scope;
		me.getView().setLoading( false );							
	},	
	
	onCancelClick: function( button ){
		var me=this;
		me.getView().removeAll();
		me.initAccommodationViews();
		me.buildAccommodation( me.accommodation );
	}
});
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
Ext.define('Ssp.view.tools.accommodation.General', {
    extend: 'Ext.form.Panel',
    alias: 'widget.disabilitygeneral',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.accommodation.GeneralViewController',
    inject: {
    	disabilityStatusesStore: 'disabilityStatusesStore'
    }, 
    height: '100%',
    width: '100%',
    id : 'AccommodationGeneral',
    initComponent: function() {
        var me = this;
        Ext.apply(me, {
			autoScroll: true,
		    layout: {
		    	type: 'vbox',
		    	align: 'stretch'
		    },
		    border: 0,
		    padding: 10,
		    defaults: {
		        anchor: '95%'
		    },
		    fieldDefaults: {
		        msgTarget: 'side',
		        labelAlign: 'right',
		        labelWidth: 200
		    },
		    defaultType: 'textfield',
            items: [{
		                xtype: 'displayfield',
		                name: 'odsRegistrationDate',
		                fieldLabel: 'ODS Registration Date',
		                renderer: Ext.util.Format.dateRenderer('m/d/Y')
		            },{
                        xtype: 'combobox',
                        name: 'disabilityStatusId',
                        fieldLabel: 'ODS Status',
				        emptyText: 'Select One',
				        store: me.disabilityStatusesStore,
				        valueField: 'id',
				        displayField: 'name',
				        mode: 'local',
				        typeAhead: true,
				        queryMode: 'local',
				        allowBlank: true
                    },
                    {
                        xtype: 'textareafield',
                        fieldLabel: 'Please Explain Temporary Eligibility',
                        name: 'tempEligibilityDescription'
                    },
                    {
                        xtype: 'textfield',
                        name: 'intakeCounselor',
                        fieldLabel: 'ODS Counselor'
                    },
                    {
                        xtype: 'textfield',
                        name: 'referredBy',
                        fieldLabel: 'Referred to ODS by'
                    }]
        });

        me.callParent(arguments);
    }
});
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
Ext.define('Ssp.view.tools.accommodation.AgencyContacts', {
	extend: 'Ext.panel.Panel',
	alias: 'widget.disabilityagencycontacts',
    width: '100%',
    height: '100%',
    autoScroll: true,
	initComponent: function() {	
        var me = this;
		Ext.apply(me,
				{
		    		bodyPadding: 10,
		    		border: 0,
					items: [{
						xtype: 'form',
						id : 'AccommodationDisabilityAgencies',
					    layout: 'anchor',
					    border: 0,
					    defaults: {
					        anchor: '100%'
					    },
					    defaultType: 'checkbox'
					},{
						xtype: 'form',
						id : 'AccommodationAgencyContactName',
					    layout: 'anchor',
					    border: 0,
					    defaults: {
					        anchor: '95%'
					    },
					    items: [{
	                        xtype: 'textfield',
	                        fieldLabel: 'Name of Contact',
	                        name: 'contactName'
	                    }]
					}]			
				});
		
		return me.callParent(arguments);
	}
});
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
Ext.define('Ssp.view.tools.accommodation.Disposition', {
    extend: 'Ext.form.Panel',
    alias: 'widget.disabilitydisposition',
    height: '100%',
    width: '100%',
    id : 'AccommodationDisposition',
    initComponent: function() {
        var me = this;
        Ext.apply(me, {
        	border: 0,
            padding: 10,
		    defaults: {
		        anchor: '95%'
		    },
            autoScroll: true,
            items: [{
		                xtype: 'fieldcontainer',
		                fieldLabel: 'Disposition',
		                layout: 'vbox',
		                items: [
                            {
                                xtype: 'checkboxfield',
                                boxLabel: 'Release Signed',
                                name: 'releaseSigned'
                            },
                            {
                                xtype: 'checkboxfield',
                                boxLabel: 'Records Requested',
                                name: 'recordsRequested'
                            },
                            {
                                xtype: 'textfield',
                                fieldLabel: 'Records Requested From',
        		                labelWidth: 150,
                                name: 'recordsRequestedFrom'
                            },
                            {
                                xtype: 'checkboxfield',
                                boxLabel: 'Referred for Screening of LD/ADD',
                                name: 'referForScreening'
                            },
                            {
                                xtype: 'textfield',
                                fieldLabel: 'Documents Requested From',
                                labelWidth: 180,
                                name: 'documentsRequestedFrom'
                            },
                            {
                                xtype: 'checkboxfield',
                                boxLabel: 'Rights and Duties',
                                name: 'rightsAndDuties'
                            },
                            {
                                xtype: 'checkboxfield',
                                boxLabel: 'Eligibility Letter Sent',
                                name: 'eligibleLetterSent'
                            },{
        				    	xtype: 'datefield',
        				    	fieldLabel: 'Eligibility Letter Date',
        				    	itemId: 'eligibleLetterDate',
        				    	labelWidth: 180,
        				    	altFormats: 'm/d/Y|m-d-Y',
        				    	invalidText: '{0} is not a valid date - it must be in the format: 06/02/2012 or 06-02-2012',
        				        name: 'eligibleLetterDate',
        				        allowBlank:true
        				    },
                            {
                                xtype: 'checkboxfield',
                                boxLabel: 'Ineligibility Letter Sent',
                                name: 'ineligibleLetterSent'
                            },{
        				    	xtype: 'datefield',
        				    	fieldLabel: 'Ineligibility Letter Date',
        				    	itemId: 'ineligibleLetterDate',
        				    	labelWidth: 180,
        				    	altFormats: 'm/d/Y|m-d-Y',
        				    	invalidText: '{0} is not a valid date - it must be in the format: 06/02/2012 or 06-02-2012',
        				        name: 'ineligibleLetterDate',
        				        allowBlank:true
        				    },
                            {
                                xtype: 'checkboxfield',
                                boxLabel: 'No disability documentation received',
                                name: 'noDocumentation'
                            },
                            {
                                xtype: 'checkboxfield',
                                boxLabel: 'Inadequate documentation',
                                name: 'inadequateDocumentation'
                            },
                            {
                                xtype: 'checkboxfield',
                                boxLabel: 'Document states individual has no disability',
                                name: 'noDisability'
                            },
                            {
                                xtype: 'checkboxfield',
                                boxLabel: 'HS reports no special ed placement/no report',
                                name: 'noSpecialEd'
                            }
                        ]
                    },
                    {
                        xtype: 'checkboxfield',
                        boxLabel: '',
                        fieldLabel: 'On Medication',
                        name: 'onMedication'
                    },
                    {
                        xtype: 'textareafield',
                        fieldLabel: 'Please list medications',
                        name: 'medicationList'
                    },
                    {
                        xtype: 'textareafield',
                        fieldLabel: 'Functional limitations?, please explain',
                        name: 'functionalLimitations'
                    }]
        });

        me.callParent(arguments);
    }

});
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
Ext.define('Ssp.view.tools.accommodation.Accommodation', {
	extend: 'Ext.panel.Panel',
	alias : 'widget.accommodation',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.accommodation.AccommodationToolViewController',
    inject: {
    	authenticatedPerson: 'authenticatedPerson'
    },
	title: 'Accommodation',
	width: '100%',
	height: '100%',
	initComponent: function() {	
		var me=this;
		Ext.apply(me, 
				{		
		    		layout: 'fit',
		    		padding: 0,
		    		border: 0,
					dockedItems: [{
				        dock: 'top',
				        xtype: 'toolbar',
				        items: [{
				        			xtype: 'button', 
				        			itemId: 'saveButton', 
				        			text:'Save',
				        			hidden: !me.authenticatedPerson.hasAccess('ACCOMMODATION_SAVE_BUTTON')
				        	   },{
					        	     xtype: 'button', 
					        	     itemId: 'cancelButton', 
					        	     text:'Cancel',
					        	     hidden: !me.authenticatedPerson.hasAccess('ACCOMMODATION_CANCEL_BUTTON')
				        	   },{
				        	    	xtype: 'label',
				        	    	html: Ssp.util.Constants.DATA_SAVE_SUCCESS_MESSAGE,
				        	    	itemId: 'saveSuccessMessage',
				        	    	style: Ssp.util.Constants.DATA_SAVE_SUCCESS_MESSAGE_STYLE,
				        	    	hidden: true
				        	    }]
					}],
				    
				    items: []
			    
		});
		
		return me.callParent(arguments);
	}
});
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
Ext.define('Ssp.view.tools.accommodation.Accommodations', {
	extend: 'Ext.form.Panel',
	alias: 'widget.disabilityaccommodations',
	id:'AccommodationDisabilityAccommodations',
    width: '100%',
    height: '100%',
    autoScroll: true,
	initComponent: function() {	
        var me = this;
		Ext.apply(me, 
				{
				    bodyPadding: 10,
				    border: 0,
				    layout: 'anchor',
				    defaults: {
				        anchor: '95%'
				    },
				    defaultType: 'checkbox'
				});
		
		return me.callParent(arguments);
	}
});
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
Ext.define('Ssp.view.tools.accommodation.DisabilityTypes', {
	extend: 'Ext.form.Panel',
	alias: 'widget.disabilitytypes',
	id: 'AccommodationDisabilityTypes',
    width: '100%',
    height: '100%',
    autoScroll: true,
	initComponent: function() {	
        var me = this;
		Ext.apply(me, 
				{
				    bodyPadding: 10,
				    border: 0,
				    layout: 'anchor',
				    defaults: {
				        anchor: '95%'
				    },
				    defaultType: 'checkbox'
				});
		
		return me.callParent(arguments);
	}
});
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
Ext.define('Ssp.view.tools.displacedworker.DisplacedWorker', {
	extend: 'Ext.panel.Panel',
	alias : 'widget.displacedworker',
	title: 'Displaced Workers',	
	width: '100%',
	height: '100%',   
	autoScroll: true,
	initComponent: function() {	
		return this.callParent(arguments);
	}
});
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
Ext.define('Ssp.view.tools.studentsuccess.StudentSuccess', {
	extend: 'Ext.panel.Panel',
	alias : 'widget.studentsuccess',
	title: 'Student Success',	
	width: '100%',
	height: '100%',   
	autoScroll: true,
	initComponent: function() {	
		return this.callParent(arguments);
	}
});
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
Ext.define('Ssp.view.ErrorWindow', {
	extend: 'Ext.window.Window',
	alias : 'widget.ssperrorwindow',
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	columnRendererUtils: 'columnRendererUtils',
    	store: 'errorsStore'
    },
	width: '100%',
	height: '100%',
	title: 'Error! Please correct the errors listed below:',
    initComponent: function(){
    	var me=this;
    	Ext.apply(me,
    			   {
				    modal: true, 
		    		layout: 'fit',
    				items: [{
    			        xtype: 'grid',
    			        border: false,
    			        columns:[{ header: 'Error', 
		 				           dataIndex: 'label',
						           sortable: false,
						           menuDisabled: true,
						           flex:.25 
						         },{ header: 'Message', 
						           dataIndex: 'errorMessage',
						           renderer: me.columnRendererUtils.renderErrorMessage,
						           sortable: false,
						           menuDisabled: true,
						           flex:.75 
						         }],     
    			        store: me.store
    			    }],
    			    bbar: [
    			           { xtype: 'button', 
    			        	 text: 'OK', 
    			        	 itemId: 'closeButton', 
    			        	 handler: function() {
    			        		 me.close();
    			             }
    			           }
    			         ]
		    	    });
    	
    	return me.callParent(arguments);
    }
});
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
Ext.define('Ssp.view.Report',{
	extend: 'Ext.Component',
	alias: 'widget.sspreport',
	height: 0,
	width: 0,
    config: {
    	downloadForm: null,
    	downloadFrame: null
    },
    autoEl: {tag: 'iframe', cls: 'x-hidden', src: Ext.SSL_SECURE_URL},
    initComponent: function(){
    	var me=this;
    	me.downloadForm = Ext.getBody().createChild({
    		tag: 'form'
    		, cls: 'x-hidden'
    		, id: 'sspPortletReportform'
    		, target: 'sspPortletIFrame'
    		});
    	
    	me.downloadFrame = Ext.getBody().createChild({
    		tag: 'iframe'
    		, cls: 'x-hidden'
    		, id: 'sspPortletIFrame'
    		, name: 'iframe'
    		, src: Ext.SSL_SECURE_URL
    		});

    	return me.callParent(arguments);
    },
    
    load: function(config){
    	this.getEl().dom.src = config.url + (config.params ? '?' + Ext.urlEncode(config.params) : '');
    },
    
    loadBlankReport: function( url ){
    	window.open(url,'_blank','');
    },
    
    postReport: function( args ){
		var me=this;
    	Ext.Ajax.request({
			url: args.url,
			form: me.downloadForm,
			params: args.params,
			isUpload: true,
			headers: { 'Content-Type': 'application/json' },
			success: function(response,responseText){
	  			  //Ext.Msg.alert('Notification','Please download your report.');
	  		},
			failure: function(response, options) {
				Ext.Msg.alert('Notification',response.responseText);
	  	    },
			scope: me
		},me);
    }
});
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
Ext.define('Ssp.model.SimpleItemDisplay', {
    extend: 'Ext.data.Model',
    fields: [{name:'name', type: 'string'}]
});
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
Ext.define('Ssp.model.ObjectPermission', {
    extend: 'Ext.data.Model',
    fields: [{name:'name',type:'string'},
             {name:'hasAccess',type:'boolean'}]
});
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
Ext.define('Ssp.model.Preferences', {
    extend: 'Ext.data.Model',
    fields: [{name:'SEARCH_GRID_VIEW_TYPE',type:'int', defaultValue:1}, // 0 display search, 1 display caseload
    		 {name:'ACTION_PLAN_ACTIVE_VIEW',type:'int',defaultValue:0}, // 0 for Tasks, 1 for Goals
    		 {name:'EARLY_ALERT_SELECTED_NODE',type:'auto', defaultValue:""}]
});
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
Ext.define('Ssp.model.FieldError', {
    extend: 'Ext.data.Model',
    fields: [{name:'label', type: 'string'},
             {name: 'errorMessage', type: 'string'}]
});
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
             {name: 'toolTipFieldName', type: 'string', defaultValue: ""}]
});
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
Ext.define('Ssp.model.Configuration', {
    extend: 'Ext.data.Model',
	fields: [
	         /*
		      * Set this option to true to lock editing on fields that relate to the external_data
		      * source. This will restrict you from editing any data that relates to external after
		      * you have first added a record to the system. All editing will be disabled for personal
		      * data, including changing a studentId/schoolId after the record has been added to the
		      * system.  
		      */
             {name: 'syncStudentPersonalDataWithExternalData', 
    	      type: 'boolean', 
    	      defaultValue: false
    	     },
    	     /*
    	      * Set this option to true to display the retrieveFromExternalDataButton on the Caseload
    	      * Assignment Screen when adding a new record. This will allow you to populate a student's record
    	      * from the external_data source while adding a new record to the system.
    	      */
    	     {
    	    	name: 'allowExternalRetrievalOfStudentDataFromCaseloadAssignment',
    	    	type: 'boolean',
    	    	defaultValue: true
    	     },
    	     /*
    	      * Set this option to true to lock editing of Coach Assignments for records in the system.
    	      * When this option is set to true, all Coach Assignments will be populated from an external system
    	      * through the external_data tables/views of this application.
    	      */
    	     {name: 'coachSetFromExternalData', 
    	      type: 'boolean', 
    	      defaultValue: false
    	     },
    	     /*
    	      * Set this option to true to lock editing of Student Type Assignments for records in the system.
    	      * When this option is set to true, all Student Type Assignments will be populated from an external system
    	      * through the external_data tables/views of this application.
    	      */
    	     {name: 'studentTypeSetFromExternalData', 
       	      type: 'boolean', 
       	      defaultValue: false
       	     },
    	     /*
    	      * Set this option to the label you would like to see for the studentId values in the system.
    	      * For instance: Use this to label your studentId for your institution's naming convention.
    	      */
             {
    	      name: 'studentIdAlias', 
    	      type: 'string', 
    	      defaultValue: 'Tartan ID'
    	     },
    	     /*
    	      * Minimum data length for a studentId/schoolId in the application.
    	      */
    	     {name: 'studentIdMinValidationLength', 
    	      type: 'number', 
    	      defaultValue: 3
    	     },
    	     /*
    	      * Error message for a studentId/schoolId that exceeds the specified minimum validation length.
    	      */
       	     {name: 'studentIdMinValidationErrorText', 
       	      type: 'string', 
       	      defaultValue: 'The entered value is not long enough.'
       	     },
    	     /*
    	      * Maximum data length for a studentId/schoolId in the application.
    	      */
    	     {name: 'studentIdMaxValidationLength', 
       	      type: 'number', 
       	      defaultValue: 8
       	     },
    	     /*
    	      * Error message for a studentId/schoolId that exceeds the specified maximum validation length.
    	      */
       	     {name: 'studentIdMaxValidationErrorText', 
      	      type: 'string', 
      	      defaultValue: 'The entered value is too long.'
      	     },
    	     /*
    	      * Values to validate for the allowable characters in a studentId/schoolId. This value will
    	      * be converted and applied as a regular expression, so all regular expressions values should be
    	      * available to this value.
    	      * 
    	      * For example: 
    	      * 
    	      * Only digits use "0-9".  
    	      * Only alphabetical characters use: "a-zA-Z".
    	      * Alphabetical characters and digits use: "a-zA-Z0-9"
    	      */
    	     {name: 'studentIdAllowableCharacters', 
          	  type: 'string', 
          	  defaultValue: 'a-zA-Z0-9'
          	 },
    	     /*
    	      * Error message for a studentId/schoolId validation error.
    	      */
    	     {name: 'studentIdValidationErrorText', 
             	  type: 'string', 
             	  defaultValue: 'Not a valid Student Id'
             },
    	     /*
    	      * Set to 1 to display the Employment Shift option on the Student Intake Tool.
    	      */
    	     {
              name: 'displayStudentIntakeDemographicsEmploymentShift', 
              type: 'boolean', 
              defaultValue: true
             },
    	     /*
    	      * Label to use for the Parent's Degree question on the Education Plan tab of the Student Intake Tool.
    	      */
    	     {
              name: 'educationPlanParentsDegreeLabel', 
              type: 'string', 
              defaultValue: 'Have your parents obtained a college degree?'
             },
    	     /*
    	      * Label to use for the Special Needs question on the Education Plan tab of the Student Intake Tool.
    	      */
    	     {
              name: 'educationPlanSpecialNeedsLabel', 
              type: 'string', 
              defaultValue: 'Special needs or require special accommodation?'
             },
    	     /*
    	      * Label to use for the Coach field displays across the application.
    	      */
    	     {
              name: 'coachFieldLabel', 
              type: 'string', 
              defaultValue: 'Coach'
            }],
             
     	constructor: function(){
     		var me=this;
     		me.callParent(arguments);
     		// apply student id validator for use in 
     		// form fields throughout the application
     		var minStudentIdLen = me.get('studentIdMinValidationLength');
    		var maxStudentIdLen = me.get('studentIdMaxValidationLength');
    		var allowableCharacters = me.get('studentIdAllowableCharacters');
    		// Example RegEx - /(^[1-9]{7,9})/
    		var regExString = '^([' + allowableCharacters + ']';
    		regExString = regExString + '{' + minStudentIdLen + ',';
    		regExString = regExString + maxStudentIdLen + '})';
    		var validStudentId = new RegExp( regExString );
            var studentIdValErrorText = 'You should only use the following character list for input: ' + allowableCharacters;
    		me.set('studentIdValidationErrorText',studentIdValErrorText);
    		me.set('studentIdMinValidationErrorText', 'Value should be at least ' + minStudentIdLen + ' characters & no more than ' + maxStudentIdLen + ' characters'); 	
            me.set('studentIdMaxValidationErrorText', 'Value should be at least ' + minStudentIdLen + ' characters & no more than ' + maxStudentIdLen + ' characters'); 

    		Ext.apply(Ext.form.field.VTypes, {
                //  vtype validation function
                studentIdValidator: function(val, field) {
                    return validStudentId.test(val);
                }
            });
            
    		return me;
    	}
});
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
Ext.define('Ssp.model.CaseloadPerson', {
    extend: 'Ext.data.Model',
    fields: [{name:'personId', type: 'string'},
             {name: 'schoolId', type: 'string'},
             {name:'firstName', type:'string'},
             {name:'lastName', type: 'string'},
             {name:'middleName',type:'string'},
             {name: 'studentTypeName', type: 'string'},
             {name: 'currentAppointmentStartDate', type: 'date', dateFormat: 'time'},
             {name: 'numberOfEarlyAlerts', type: 'string'},
             {name: 'studentIntakeComplete', type: 'boolean'},
             {name: 'currentAppointmentStartTime', type: 'date', dateFormat: 'time'},
             {name: 'currentProgramStatusName', type: 'string'}],            
             
     getFullName: function(){ 
      	var firstName = this.get('firstName') || "";
      	var middleName = this.get('middleName') || "";
      	var lastName = this.get('lastName') || "";
      	return firstName + " " + middleName + " " + lastName;
     },
     
     getStudentTypeName: function(){
     	return ((this.get('studentTypeName') != null)? this.get('studentTypeName') : "");   	
     }       
});
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
Ext.define('Ssp.model.SearchPerson', {
    extend: 'Ext.data.Model',
    fields: [{name:'id', type: 'string'},
             {name: 'schoolId', type: 'string'},
             {name: 'firstName', type: 'string'},
             {name: 'middleName', type: 'string'},
             {name: 'lastName', type: 'string'},
             {name: 'photoUrl', type: 'string'},
             {name: 'currentProgramStatusName', type: 'string'},
             {name: 'coach', type: 'auto'}],

     getFullName: function(){ 
    	var me=this;
     	var firstName = me.get('firstName')? me.get('firstName') : "";
     	var middleName = me.get('middleName')? me.get('middleName') : "";
     	var lastName = me.get('lastName')? me.get('lastName') : "";
     	return firstName + " " + middleName + " " + lastName;
     },
     
     getCoachFullName: function(){
    	var me=this;
      	var firstName = me.get('coach')? me.get('coach').firstName : "";
      	var lastName = me.get('coach')? me.get('coach').lastName : "";
      	return firstName + " " + lastName;
     }
});
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
Ext.define('Ssp.model.SearchCriteria', {
    extend: 'Ext.data.Model',
    fields: [{name:'searchTerm', type: 'string'},
             {name: 'outsideCaseload', type: 'boolean', defaultValue: true}]
});
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
Ext.define('Ssp.model.CaseloadFilterCriteria', {
    extend: 'Ext.data.Model',
    fields: [{name:'programStatusId', type: 'string'}]
});
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
Ext.define('Ssp.model.PersonLite', {
    extend: 'Ext.data.Model',
    fields: [{name:'id',type:'string'},
             {name:'firstName', type:'string'},
             {name:'lastName', type: 'string'},
             {name:'middleName',type:'string'},
             {name: 'displayFullName', 
	          convert: function(value, record) {
	        	  return record.get('firstName') + " " + record.get('lastName');
		      }
             }]
});
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
Ext.define('Ssp.model.PersonSearchLite', {
    extend: 'Ext.data.Model',
    fields: [{name:'id',type:'string'},
             {name:'firstName', type:'string'},
             {name:'lastName', type: 'string'},
             {name:'middleName',type:'string'},
             {name: 'displayFullName', 
   	          convert: function(value, record) {
   	        	  return record.get('firstName') + " " + record.get('lastName');
   		      }
             }]        
});
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
Ext.define('Ssp.model.tool.accommodation.PersonDisabilityType', {
	extend: 'Ssp.model.AbstractBase',
    fields: ['personId',
             'disabilityTypeId']
});
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
Ext.define('Ssp.model.tool.accommodation.PersonDisabilityAgency', {
	extend: 'Ssp.model.AbstractBase',
    fields: ['personId',
             'disabilityAgencyId',
             'description']
});
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
Ext.define('Ssp.model.tool.accommodation.PersonDisabilityAccommodation', {
	extend: 'Ssp.model.AbstractBase',
    fields: ['personId',
             'disabilityAccommodationId',
             'description']
});
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
Ext.define('Ssp.model.tool.accommodation.PersonDisability', {
    extend: 'Ssp.model.AbstractBase',
    fields: [{name: 'personId', type: 'string'},
             {name: 'odsRegistrationDate', type: 'date', dateFormat: 'time', useNull: true},
             {name: 'disabilityStatusId', type: 'string'},
             {name: 'intakeCounselor', type: 'string'},
             {name: 'referredBy', type: 'string'},
             {name: 'contactName', type: 'string'},
             {name: 'releaseSigned', type: 'boolean', useNull:true},
             {name: 'recordsRequested', type: 'boolean', useNull:true},
             {name: 'recordsRequestedFrom', type: 'string'},
             {name: 'referForScreening', type: 'boolean', useNull:true},
             {name: 'documentsRequestedFrom', type: 'string'},
             {name: 'rightsAndDuties', type: 'string'},	 
             {name: 'eligibleLetterSent', type: 'boolean', useNull:true},
             {name: 'eligibleLetterDate', type: 'date', dateFormat: 'time'},
             {name: 'ineligibleLetterSent', type: 'boolean', useNull:true},
             {name: 'ineligibleLetterDate', type: 'date', dateFormat: 'time'},
             {name: 'noDocumentation', type: 'boolean', useNull:true},
             {name: 'inadequateDocumentation', type: 'boolean', useNull:true},
             {name: 'noDisability', type: 'boolean', useNull:true},
             {name: 'noSpecialEd', type: 'boolean', useNull:true},
             {name: 'tempEligibilityDescription', type: 'string'},
             {name: 'onMedication', type: 'boolean', useNull:true},
             {name: 'medicationList', type: 'string'},
             {name: 'functionalLimitations', type: 'string'}],

     /*
      * cleans properties that will be unable to be saved if not null
      */ 
     setPropsNullForSave: function( jsonData ){	
 		// remove registration date field.
    	// it was only a substitute for the createdDate,
    	// so the createdDate field would not be modified by
    	// extjs when the record is updated by it's form prior to save.
    	delete jsonData.odsRegistrationDate;
    	 
    	if ( jsonData.intakeCounselor == "")
 		{
 			jsonData.intakeCounselor = null;
 		}
 		if ( jsonData.referredBy == "")
 		{
 			jsonData.referredBy = null;
 		}
 		return jsonData;
     }
});
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
Ext.define('Ssp.model.tool.accommodation.AccommodationForm', {
	extend: 'Ext.data.Model',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
        apiProperties: 'apiProperties'
    }, 

	fields: [{name: 'person', 
		      convert: function(value, record) {
		            var person  = Ext.create('Ssp.model.Person',{});
		            person.populateFromGenericObject( value );		
		            return person;
		      	}
             },
              {name: 'personDisability', 
   		      convert: function(value, record) {
		            var personDisability = Ext.create('Ssp.model.tool.accommodation.PersonDisability',{});
		            personDisability.populateFromGenericObject( value );
		            if (value !== null)
		            {
		            	personDisability.set('odsRegistrationDate',value.createdDate);
		            }
		            return personDisability;
		      	}
             },
             'personDisabilityAgencies',
             'personDisabilityAccommodations',
             'personDisabilityTypes',
             'referenceData'],

	autoLoad: false,
 	proxy: {
		type: 'rest',
		url: '/ssp/api/1/tool/accommodation/',
		actionMethods: {
			create: "POST", 
			read: "GET", 
			update: "PUT",
			destroy: "DELETE"
		},
		reader: {
			type: 'json'
		},
	    writer: {
	        type: 'json',
	        successProperty: 'success'
	    }
	}
});
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
Ext.define('Ssp.model.tool.studentintake.StudentIntakeForm', {
	extend: 'Ext.data.Model',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
        apiProperties: 'apiProperties'
    }, 

	fields: [{name: 'person', 
		      convert: function(value, record) {
		            var person  = Ext.create('Ssp.model.Person',{});
		            person.populateFromGenericObject( value );		
		            return person;
		      	}
             },
              {name: 'personDemographics', 
   		      convert: function(value, record) {
		            var personDemographics = Ext.create('Ssp.model.tool.studentintake.PersonDemographics',{});
		            personDemographics.populateFromGenericObject( value );
		            return personDemographics;
		      	}
             },
             {name: 'personEducationGoal', 
   		      convert: function(value, record) {
   		            var personEducationGoal = Ext.create('Ssp.model.tool.studentintake.PersonEducationGoal',{});
   		            personEducationGoal.populateFromGenericObject( value );  		
   		            return personEducationGoal;
   		      	}
             },
             {name: 'personEducationPlan', 
      		  convert: function(value, record) {
      		            var personEducationPlan = Ext.create('Ssp.model.tool.studentintake.PersonEducationPlan',{});
      		            personEducationPlan.populateFromGenericObject( value );
      		            return personEducationPlan;
      		    }
             },
             'personEducationLevels',
             'personFundingSources',
             'personChallenges',
             'referenceData'],

	autoLoad: false,
 	proxy: {
		type: 'rest',
		url: '/ssp/api/1/tool/studentIntake/',
		actionMethods: {
			create: "POST", 
			read: "GET", 
			update: "PUT",
			destroy: "DELETE"
		},
		reader: {
			type: 'json'
		},
	    writer: {
	        type: 'json',
	        successProperty: 'success'
	    }
	}
});
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
Ext.define('Ssp.model.tool.journal.JournalEntryDetail', {
    extend: 'Ext.data.Model',
    fields: [{name:'group',type:'string'},
             {name:'id', type: 'string'},
             {name:'name', type: 'string'}]
});
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
Ext.define('Ssp.model.ApiUrl', {
    extend: 'Ext.data.Model',
    fields: [{name:'name',type:'string'},
             {name:'url',type:'string'}]
});
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
Ext.define('Ssp.mixin.ApiProperties', {	
	Extend: 'Ext.Component',
    mixins: [ 'Deft.mixin.Injectable' ],
    config: {
    	baseUrl: '',
    	baseApiUrl: ''
    },
    inject: {
    	apiUrlStore: 'apiUrlStore' 
    },
    statics: {
    	getBaseApiUrl: function(){
    		var apiVersion = "1";
    	    var base = document.getElementsByTagName('base')[0];
    	    if (base && base.href && (base.href.length > 0)) {
    	        base = base.href;
    	    } else {
    	        base = document.URL;
    	    }
    	    return base.substr(0, base.indexOf("/", base.indexOf("//") + 2) + 1) + Ext.Loader.getPath('ContextName') + '/api/' + apiVersion + '/';
    	},
    	
    	getBaseAppUrl: function(){
    		var apiVersion = "1";
    	    var base = document.getElementsByTagName('base')[0];
    	    if (base && base.href && (base.href.length > 0)) {
    	        base = base.href;
    	    } else {
    	        base = document.URL;
    	    }
    	    return base.substr(0, base.indexOf("/", base.indexOf("//") + 2) + 1) + Ext.Loader.getPath('ContextName');
    	}
    },
    
	initComponent: function(){
		var me=this;
		
		me.baseUrl = Ssp.mixin.ApiProperties.getBaseAppUrl();
		me.baseApiUrl = Ssp.mixin.ApiProperties.getBaseApiUrl();
			
		this.callParent(arguments);
	},
	
	getContext: function() {
		return Ssp.mixin.ApiProperties.getBaseAppUrl();
	},

	getAPIContext: function() {
		return Ssp.mixin.ApiProperties.getBaseApiUrl();
	},
	
	createUrl: function(value){
		return Ssp.mixin.ApiProperties.getBaseApiUrl() + value;
	},
	
	getPagingSize: function(){
		return 20;
	},
	
	getProxy: function(url){
		var proxyObj = {
			type: 'rest',
			url: this.createUrl(url),
			actionMethods: {
				create: "POST", 
				read: "GET", 
				update: "PUT", 
				destroy: "DELETE"
			},
			reader: {
				type: 'json',
				root: 'rows',
				totalProperty: 'results',
				successProperty: 'success',
				message: 'message'
			},
		    writer: {
		        type: 'json',
		        successProperty: 'success'
		    }
		};
		return proxyObj;
	},
	
	/*
	 * @args - {}
	 *    url - url of the request
	 *    method - 'PUT', 'POST', 'GET', 'DELETE'
	 *    jsonData - data to send
	 *    successFunc - success function
	 *    scope - scope
	 */
	makeRequest: function( args ){
		var me=this;
		var contentType = "application/json";
		var errorHandler = me.handleError;
		var scope = me;
		if (args.failureFunc != null)
		{
			errorHandler = args.failureFunc;
		}
		if ( args.contentType != null)
		{
			contentType = args.contentType;
		}
		if (args.scope != undefined && args.scope != null)
		{
			scope = args.scope;
		}
		Ext.Ajax.request({
			url: args.url,
			method: args.method,
			headers: { 'Content-Type': contentType },
			jsonData: args.jsonData || '',
			success: args.successFunc,
			failure: errorHandler,
			scope: scope
		},me);		
	},
	
	handleError: function( response ) {
		var me=this;
		var msg = 'Status Error: ' + response.status + ' - ' + response.statusText;
		var r;

		if (response.status==403)
		{
			Ext.Msg.confirm({
	   		     title:'Access Denied Error',
	   		     msg: "It looks like you are trying to access restricted information or your login session has expired. Would you like to login to continue working in SSP?",
	   		     buttons: Ext.Msg.YESNO,
	   		     fn: function( btnId ){
	   		    	if (btnId=="yes")
	   		    	{
	   		    		// force a login
	   		    		window.location.reload();
	   		    	}else{
	   		    		// force a login
	   		    		window.location.reload();
	   		    	}
	   		    },
	   		     scope: me
	   		});
		}
		
		// Handle call not found result
		if (response.status==404)
		{
			Ext.Msg.alert('SSP Error', msg);
		}

		if ( response.status==500 )
		{
			// Handle responseText is json returned from SSP
			if( response.responseText != null )
			{
				if ( response.responseText != "")
				{
					r = Ext.decode(response.responseText);
					if (r.message != null)
					{
						if ( r.message != "")
						{
							msg = msg + " " + r.message;
							Ext.Msg.alert('SSP Error', msg);							
						}
					}else{
						Ext.Msg.alert('Internal Server Error - 500', 'Unable to determine the source of this error. See logs for additional details.');
					}
				}
			}
		}		
		
		if ( response.status==200 )
		{
			// Handle responseText is json returned from SSP
			if( response.responseText != null )
			{
				if ( response.responseText != "")
				{
					r = Ext.decode(response.responseText);
					if (r.message != null)
					{
						if ( r.message != "")
						{
							msg = msg + " " + r.message;
							Ext.Msg.alert('SSP Error', msg);							
						}
					}
				}
			}
		}

	},
	
	/*
	 * Returns the base url of an item in the system.
	 * @itemName - the name of the item to locate.
	 * 	the returned item is returned by name from the apiUrlStore.
	 */
	getItemUrl: function( itemName ){
		var record = this.apiUrlStore.findRecord('name', itemName);
		var url = "";
		if (record != null)
			url = record.get('url');
		return url;
	},
	
	getReporter: function(){
		return Ext.ComponentQuery.query('sspreport')[0];
	}
});
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
Ext.define('Ssp.mixin.controller.ItemSelectorInitializer', {
    statics: {
        fieldNameIdx: 1,
        getNextFieldNameIdx: function() {
            return this.fieldNameIdx++;
        }
    },

    nextFieldName: function() {
        return 'itemSelectorField' + this.self.getNextFieldNameIdx();
    },

    defineSelectorField: function(initialFieldValues, componentDefOverrides) {
        return Ext.apply({
            xtype: 'itemselectorfield',
            anchor: '100%',
            height: 250,
            name: this.nextFieldName(),
            displayField: 'name',
            valueField: 'id',
            value: ((initialFieldValues && initialFieldValues.length>0) ? initialFieldValues : [] ),
            allowBlank: true,
            buttons: ["add", "remove"],
            listeners: {
                toField: {
                    boundList: {
                        scope: this,
                        drop: this.maybeRefireFromFieldLoadWithNonEmptyStore
                    }
                },
                fromField: {
                    boundList: {
                        scope: this,
                        itemdblclick: this.maybeRefireFromFieldLoadWithNonEmptyStore
                    }
                }
            }
        }, componentDefOverrides);
    },

    defineAndAddSelectorField: function(view, initialFieldValues, componentDefOverrides) {
        var componentDef = this.defineSelectorField(initialFieldValues, componentDefOverrides);
        view.add([componentDef]);
        this.registerAdditionalListeners(view, componentDef);
    },

    // Drops, double clicks and button clicks have to be handled separately -
    // there's no single event they both fire from ItemSelector to indicate that
    // a selection has been updated. But they both have the same problem when
    // selecting the last item in the "fromField" (see below). Drop handlers are
    // registered with standard view config above. Button registration is a
    // little bit different and is handled here. Note that you can't just
    // replace itemSelector.onAddBtnClick b/c that method is registered as the
    // button handler when the button is created, so the button won't see your
    // replacement.
    registerAdditionalListeners: function(view, componentDef) {
        var me = this;
        var itemSelector = me.findItemSelector(view, componentDef);
        var addButton = itemSelector.query('button[iconCls=x-form-itemselector-add]')[0];
        var origAddButtonHandler = addButton.handler;
        addButton.setHandler(function() {
            var me = this;
            origAddButtonHandler.apply(itemSelector);
            me.maybeRefireFromFieldLoadWithNonEmptyStore(view, componentDef);
        }, me);
    },

    // Hack to work around a bug in ItemSelector.setValue() which prevents
    // selecting the last item in the "fromField". The fromField's store count
    // is decremented immediately when an item is selected, but setValue(),
    // which is called after that decrement, assumes the fromField store is
    // uninitialized if that decrement results in an empty fromField store. In
    // that case it just registers a 'load' event listener on the store and
    // returns. The early return prevents the ItemSelector from seeing an
    // updated list of selected items.
    maybeRefireFromFieldLoadWithNonEmptyStore: function(view, componentDef) {
        var me = this;
        var itemSelector = me.findItemSelector(view, componentDef);
        var fromField = me.itemSelector.fromField;
        var toField = me.itemSelector.toField;
        var origGetCount = fromField.store.getCount;
        // this is safe b/c we don't even start to initialize the view
        // until after the store has been initialized, which obviates the guard
        // against uninitialized fromField stores in ItemSelector.setValue()
        if ( origGetCount.apply(fromField.store) === 0 ) {
            fromField.store.getCount = function() { return 1; };
            fromField.store.fireEvent('load', fromField.store);
            fromField.store.getCount = origGetCount;
        }
    },

    // Factored into method rather than inlined in defineAndAddSelectorField()
    // b/c we need to be able to find the item selector component when
    // callbacks/listeners fire, which could theoretically happen before
    // defineAndAddSelectorField() could cache this result
    findItemSelector: function(view, componentDef) {
        var me = this;
        me.itemSelector = me.itemSelector ||  view.form.findField(componentDef.name);
        return me.itemSelector;
    }
});

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
Ext.define('Ssp.util.FormRendererUtils',{	
	extend: 'Ext.Component',
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
        appEventsController: 'appEventsController',
        errorsStore: 'errorsStore'
    },
	config: {
		additionalFieldsKeySeparator: '_'
	},
	
	initComponent: function() {
		
    	// Create a custom validator for
		// mapped field types
		Ext.apply(Ext.form.field.VTypes, {
            //  vtype validation function
            mappedFieldValidator: function(val, field) {
            	var valid = true;
            	var exp = new RegExp(field.validationExpression);
            	var check = Ext.ComponentQuery.query('#'+field.parentId)[0];
            	if (check != null)
            	{
            		if (check.getValue()==true)
            		{
                    	valid = exp.test(val);
            		}
            	}
            	return valid;
            }
        });	

    	// Create a custom validator for
		// dates that are not required
		Ext.apply(Ext.form.field.VTypes, {
            //  vtype validation function
            forceDateValidator: function(val, field) {
            	var valid = true;
            	var exp = new RegExp(/\d{4}\/\d{2}\/\d{2} \d{2}:\d{2}:\d{2}/);
            	var check = field;
            	if (check != null)
            	{
            		if (check.getValue()==true)
            		{
                    	valid = exp.test(val);
            		}
            	}
            	return valid;
            }
        });		
		
		return this.callParent(arguments);
    },
    
    constructor: function(){
    	this.callParent(arguments);
    	return this;
    },
	
    cleanAll: function(view){
    	if (view.items)
    	{
        	if (view.items.length > 0)
        	{
        		view.removeAll(true);
        	}	
    	}
    },
    
    cleanItems: function(view){
    	var i = view.items.length;
    	while (view.items.length > 0)
    	{
    		var item = view.items.getAt(i);
    		if (item != undefined)
    			view.remove(item, true);
    		i=i-1;
    	}
    },	
	
    getProfileFormItems: function(){
		var cleaner = Ext.create( 'Ssp.util.TemplateDataUtil' );
		var applicationFormsStore =  Ext.getStore('ApplicationForms');
        return cleaner.prepareTemplateData(  applicationFormsStore );  	
    },    
    
    /**
     * Builds a dynamic group of radio buttons.
     * @args
     *  @formId 
     *  @radioGroupId
     *  @itemsArr
     *  @selectedItemId
     *  @idFieldName
     *  @selectedIdFieldName
     *  @required
     */
    createRadioButtonGroup: function( args ){
    	var me=this;
    	var formId = args.formId;
    	var radioGroupId = args.radioGroupId;
    	var radioGroupFieldSetId = args.radioGroupFieldSetId;
    	var selectedItemId = args.selectedItemId;
    	var additionalFieldsMap = args.additionalFieldsMap;
    	var itemsArr = args.itemsArr;
    	var idFieldName = args.idFieldName;
    	var selectedIdFieldName = args.selectedIdFieldName;
    	var selectedItem = args.selectedItemsArr[0];
    	var form = Ext.getCmp(formId);
    	var rbGroup = Ext.getCmp(radioGroupId);
		var items = itemsArr;
		var setSelectedItems = false;
		var additionalFieldArr = [];
		var fieldSet = Ext.ComponentQuery.query('#'+radioGroupFieldSetId)[0];
		// Define the radio buttons
		Ext.each(items,function(item,index){
			var itemId = item[idFieldName];
			var comp = {xtype:'mappedradiobutton'};
			comp.id = itemId;
			comp.boxLabel = item.name;
			comp.name = selectedIdFieldName;
			comp.inputValue = item[idFieldName];
			comp.listeners = {
				change: function(comp, oldValue, newValue, eOpts){
					me.appEventsController.getApplication().fireEvent('dynamicCompChange', comp);
				}	
			};

			// retrieve the additional field maps
			additionalFieldsArr = me.getMappedFields( itemId, additionalFieldsMap );		

			// populate the additional fields with selected values
			Ext.each(additionalFieldsArr,function(field,index){
				var names = field.name.split( me.additionalFieldsKeySeparator );
				if ( field.parentId == selectedItemId )
				{
					field.setValue( selectedItem[names[1]] );
				}else{
					field.setValue("");
				}
			},this);

			// hide items that are not selected
			me.hideEmptyFields( additionalFieldsArr );
			
			// add the items to the form
			fieldSet.add( additionalFieldsArr );			
			
			// selected radio button
			if (selectedItemId==item[idFieldName])
			{
				comp.checked = true;
			}

			// add radio button to the radiogroup
			rbGroup.add(comp);
			
		}, this);
    },
    
    /**
     * Provides the ability to build a form with a dynamic
     * set of elements. (checkboxes or radio)
     * 
     * Assumes an items array with: {id: id, name: name, description: description}
     * @args
     *    @mainComponentType = the main component type for the form ('checkbox', 'radio') 
     *    @radioGroupId = the radiobuttongroup id value / optional if building a checkbox based form
     *    @radioGroupFieldSetId = the fieldset in which the radiobutton group is located
     *    @selectedItemId - required value for the 'radio' select button id and optional for 'checkbox' mainComponentType  
     *    @formId = the form to add the items into.
     *    @fieldSetTitle - Provides a fieldset title for the fields
     *    @itemsArr = the array of items to add to the form
     *    @selectedItemsArr = the array of items to select in the form
     *    @idFieldName = the id field in the items array
     *    @selectedIdFieldName - the id field in the selectedItems array. 
     *                        This value can be the same name or a different name than the idFieldName.
     *    @additionalFieldsMap - a series of fields to provide for description related field items
     */
    createForm: function( args ){
    	var me=this;
    	var mainComponentType = args.mainComponentType;
    	var formId = args.formId;
    	var fieldSetTitle = args.fieldSetTitle || null;
    	var itemsArr = args.itemsArr;
    	var selectedItemsArr = args.selectedItemsArr || null;
    	var idFieldName = args.idFieldName;
    	var selectedIdFieldName = args.selectedIdFieldName;
    	var additionalFieldsMap = args.additionalFieldsMap || [];
    	var form = Ext.getCmp(formId);
		var selectedItems = selectedItemsArr;
		var selectedItem;
		var selectedId;
		var setSelectedItems = false;
		var otherId = "";
		var wrapper = {xtype: 'fieldset', padding: 0, border: 0, layout: { type: 'auto' },title: fieldSetTitle};
		var formFields = [];
		var selectedItems = selectedItemsArr || [];
		
		if ( mainComponentType == 'radio' )
		{
			this.createRadioButtonGroup(args);
		}else{
			Ext.each(itemsArr, function(item, index){
				var itemId = item[idFieldName];
				// create the items for the form
				var comp = {xtype: 'mappedcheckbox'};
				comp.id = itemId;
				comp.mapId = itemId;
				comp.boxLabel = item.name;
				comp.name = item.name;
				comp.inputValue = itemId;
				comp.listeners = {
					change: function(comp, oldValue, newValue, eOpts){
						me.appEventsController.getApplication().fireEvent('dynamicCompChange', comp);
					}	
				};

				// loop through additional fields map and add description type fields to the form
				var fieldsArr = [];
				fieldsArr.push( comp );
				
				additionalFieldsArr = me.getMappedFields( itemId, additionalFieldsMap );				
				
				// determine if the component is selected
				for (var s=0; s<selectedItems.length; s++)
				{
					selectedItem = selectedItems[s]
					selectedId = selectedItem[selectedIdFieldName];					
					if (selectedId==item[idFieldName])
					{
						comp.checked = true;					
						
						// populate the additional fields with selected values
						for(var z=0; z<additionalFieldsArr.length; z++)
						{
							var field = additionalFieldsArr[z];
							var names = field.name.split( me.additionalFieldsKeySeparator );
							if ( field.parentId == selectedId )
							{
								field.setValue( selectedItem[names[1]] );
							}else{
								field.setValue("");
							}
						}							

						break;
					}
				}
				
				this.hideEmptyFields(additionalFieldsArr);
				
		    	// add a fieldset if additional fields exist for this item
		    	if (additionalFieldsArr.length>0)
		    	{
			    	var fields = {xtype: 'fieldset', padding: 0, border: 0, layout: { type: 'auto' },title: ''};
			    	Ext.Array.insert(fieldsArr, 1, additionalFieldsArr);
			    	Ext.apply(fields, {items: fieldsArr});
		    	}
				
				// if a fieldset is not defined, then just return a checkbox
				if (fieldsArr.length > 1)
				{
					formFields.push(fields);
				}else{
					formFields.push( comp );
				}
				
			}, me);
			
			form.removeAll();
			Ext.apply(wrapper, {items: formFields});
			form.insert(form.items.length, wrapper);
		}
    },
    
    /**
     * Hides fields with no value set.
     */
    hideEmptyFields: function( arr ){
    	Ext.each(arr, function(item, index){
    		if (item.getValue()=="")
    		{
    			item.hide();
    			Ext.apply(item,{allowBlank:true});
    		}else{
    			item.show();
    			Ext.apply(item,{allowBlank:false});
    		}
    	});
    },
    
    /**
     * Loops over a collection of field maps and 
     * returns an array of fields
     */
    getMappedFields: function( itemId, maps ){
		var additionalFieldsArr = [];
		Ext.each(maps, function(map,index){
			if (itemId==map.parentId)
				additionalFieldsArr.push( this.getFieldFromMap( map ) );   		
    	},this);

    	return additionalFieldsArr;
    },
    
    /**
     * returns a field based on the parameters
     * in the map.
     * @param map object
     *  fieldType
     *  name
     *  parentId
     *  label
     *  labelWidth
     *  
     *  Example:
     *  {parentId: '365e8c95-f356-4f1f-8d79-4771ae8b0291',
	 *   parentName: "other",
	 *   name: "otherDescription", 
	 *   label: "Please Explain", 
     *   fieldType: "textarea",
     *   labelWidth: defaultLabelWidth}
     * 
     * @returns a field created by it's xtype
     */
    getFieldFromMap: function( map ){
    	var field = Ext.createWidget( map.fieldType.toLowerCase() );
    	var valErrorText = 'Not a valid input.';
    	var validationExpression=field.validationExpression;
    	if (map.validationExpression != null)
    		validationExpression=map.validationExpression;
    	if (map.validationErrorMessage != null)
    		valErrorText = map.validationErrorMessage;

    	Ext.apply(field, {
    		parentId: map.parentId, 
    		name: map.parentId + this.additionalFieldsKeySeparator + map.name, 
    		fieldLabel: map.label, 
    		labelWidth: map.labelWidth,
    		anchor: '100%',
    		vtype: 'mappedFieldValidator',
    		vtypeText: valErrorText,
    		validationExpression: validationExpression
    	});    	
    	
    	// This field get's hidden when it's parent
    	// is not selected
    	field.on("hide", 
    		function(comp, eOpts){
    			comp.setValue("");
    	});
    	
    	return field;
    },
    
    /**
     * Determines if the string is using
     * the additionalFields model based on
     * a separator for the field.
     */
    isAdditionalFieldKey: function( key ){
    	var isKey = (key.indexOf( this.additionalFieldsKeySeparator ) != -1);
    	return isKey;
    },

    /**
     * @param obj - a values object from a form
     * @returns the value of the associated item in the mapped field
     */
    getMappedFieldValueFromFormValuesByIdKey: function( obj, id ){
    	var returnVal = "";
    	Ext.iterate(obj, function(key, value) {
			if ( this.isAdditionalFieldKey( key ) )
			{
				keys = key.split( this.additionalFieldsKeySeparator );
				if ( keys[0]==id )
				{
					returnVal = value;
				}
			} 
		},this);
    	return returnVal;
    },    
    
    /**
     * @param obj - a values object from a form
     * @returns array of additionalField objects with name/value pairs
     */
    getAdditionalFieldsArrayFromFormValues: function( obj ){
    	var fields = [];
    	Ext.iterate(obj, function(key, value) {
			if ( this.isAdditionalFieldKey( key ) )
			{
				keys = key.split( this.additionalFieldsKeySeparator );
				fields.push( {id: keys[0], name: keys[1], value: value} );
			} 
		},this);
    	return fields;
    },
    
    /**
     * @param obj - a values object from a form
     * @returns the object clean of any keys that the signature
     *          of an additional form field. ie. a description field
     *          for one of the items in the form.
     */
    dropAdditionalFieldsKeysFromFormValues: function( obj ){
    	Ext.iterate(obj, function(key, value) {
			if ( this.isAdditionalFieldKey( key ) )
			{
				delete obj[key];
			} 
		},this);
    	return obj;
    },
    
    /**
     * Method to create a json transfer object from
     * the selected values in a form.
     * This method is specifically for use with the
     * AdditionalFieldMappings related object type,
     * for dynamic check and radio objects.
     * @idKey = the supplied name of the key field in the transfer object
     * @formValues = an object containing key value pairs from the form
     * @personId = a related key value for the object
     * 
     * @returns = an array of transfer objects for the selected items in the form
     */
	createTransferObjectsFromSelectedValues: function(idKey, formValues, personId){
		var transferObjects = [];	
		var formUtils = this.formUtils;
		var additionalFieldArr = [];
		
		// Loop through all the values in the form
		// find the objects with an '_' character and save them to a new array
		additionalFieldsArr = this.getAdditionalFieldsArrayFromFormValues( formValues );

		// delete keys that match an additional fields signature, since they will be used to determine mapped description fields
		// and not actual selected items
		// compare the values in each of the keys against the selected items to create 
		// a series of personEducationLevel objects to save
		
		formValues = this.dropAdditionalFieldsKeysFromFormValues( formValues );
		
		// Loop through all the values in the form and create
		// transfer objects for them with an id field name matching
		// the supplied idKey. For each transfer object, loop over the
		// the additionalFields and match the id's to determine
		// additional fields that should be supplied as descriptions
		// against the mapped fields
		Ext.iterate(formValues, function(key, value) {
			var tObj = new Object();
			tObj[idKey]=value;
			tObj['personId'] = personId;
			Ext.Array.each( additionalFieldsArr, function(field, index){
				if (value==field.id)
					tObj[field.name]=field.value;
			}, this);
			transferObjects.push( tObj );
		});
		
		return transferObjects;
	},    
	
	/**
	 * Allows an additional field to be hidden until
	 * an item is selected that is associated with the 
	 * hidden field.
	 */
    onMapFieldHidden: function( comp, eOpts){
    	comp.setValue("");
    },
	
	/**
	 * @params
	 * @arrayToSort - the array to sort props on
	 * @fieldName - the field name to sort on
	 * 
	 * @returns - returns the sorted array
	 */
    alphaSortByField: function( arrayToSort, fieldName ){
    	return Ext.Array.sort(arrayToSort, function(a, b){
    		 var nameA=a[fieldName].toLowerCase(), nameB=b[fieldName].toLowerCase()
    		 if (nameA < nameB) //sort string ascending
    		  return -1
    		 if (nameA > nameB)
    		  return 1
    		 return 0 //default return value (no sorting)
    		});
    },    
    
    /**
     * @params
     * @values - an object of name/value pairs
     * @modelType - the model type to set and return
     * 
     * @returns - a typed model object
     */
    getSelectedValuesAsTransferObject: function( values, modelType ){
		var selectedItems = [];
		for ( prop in values )
		{
			var obj = Ext.create(modelType,{id: values[prop]} );
			selectedItems.push( obj.data );
		}
		return selectedItems;
    },
 
    /**
     * @params
     * @values - an object of name/value pairs
     * 
     * @returns - an array of selected ids
     */
    getSelectedIdsAsArray: function( values ){
		var selectedIds = [];
		for ( prop in values )
		{
			selectedIds.push( {id: values[prop]} );
		}
		return selectedIds;
    },    
    
 	/**
	 * load a display into a container in the interface.
	 * @containerAlias = alias/xtype of the container into which to load the display.
	 * @compAlias = alias/xtype of the component that is loaded
	 * @removeExisting = boolean: true=remove existing items before load, 
	 *                            false=keep existing items
	 * @args = optional params space
	 */	
	loadDisplay: function( containerAlias, compAlias, removeExisting, args ) {	
		var comp = null;
		var store = null;
		var view = Ext.ComponentQuery.query(containerAlias.toLowerCase())[0];
		
		if (view.items.length > 0 && removeExisting==true)
			view.removeAll();

		// create the new widget
		comp =  Ext.widget(compAlias.toLowerCase(), args);	
		
		// add to the container
		view.add( comp );
		
		return comp;
	},

    /**
     * Compares a value against a record in a store, based on a provided
     * field for the comparison, as well as, criteria to find the record
     * in the store to use to compare against. If the values match
     * then the associated field will be hidden in the interface. Otherwise,
     * the field is shown.
     * @elementName - the field to hide
     * @compareValue - the value to compare against
     * @compareFieldName - the field in a record to compare against. For example: 'id'
     * @store - the store in which to find a value to compare against
     * @recordField - the name of the field in the store to find a record
     * @recordValue - the value of the field in the store to find a record against
     */
    showHideFieldByStoreValue: function( elementName, compareValue, compareFieldName, store, recordField, recordValue ){
		var queryValue = '#'+elementName;
    	var field = Ext.ComponentQuery.query(queryValue)[0];
		var record = store.findRecord(recordField, recordValue);
		if ( compareValue==record.get( compareFieldName ) )
		{
			field.show();
		}else{
			field.hide();
		}
    },
	
    /**
     * Method to allow a gridPanel to be reconfigured to display
     * a new set of columns or a new store of data.
     */
    reconfigureGridPanel: function(gridPanel, store, columns) {
    	var me = gridPanel,
            headerCt = me.headerCt;

        if (me.lockable) {
            me.reconfigureLockable(store, columns);
        } else {
            if (columns) {
                headerCt.suspendLayout = true;
                headerCt.removeAll();
                headerCt.add(columns);
            }
            if (store) {
                store = Ext.StoreManager.lookup(store);
                me.down('pagingtoolbar').bindStore(store);
                me.bindStore(store);
            } else {
                me.getView().refresh();
            }
            if (columns) {
                headerCt.suspendLayout = false;
                me.forceComponentLayout();
            }
        }
        me.fireEvent('reconfigure', me);
    },
    
	/**
	 * Determines if one or more forms have invalid fields
	 * and ensures that the first invalid item is visible
	 * in the interface.
	 * 
	 * Returns a result object with an array of invalid fields
	 * and a valid flag to determine if the form is valid
	 * 
	 */
	validateForms: function( forms ){
		var me=this;
		var form;
		var result = {fields:[],valid:true};			
		Ext.Array.each(forms, function(form, index){
			var f;
			if (form.isValid()==false)
			{
				// collect all invalid fields
				// from the form
				var invalidFields = me.findInvalidFields( form );
				if (invalidFields.items.length>0)
				{
					Ext.Array.each(invalidFields.items,function(field,index){
						result.fields.push( me.cleanInvalidField( field ) );
					},me);
				}
				
				// find the first invalid field and display it
				if (result.valid==true)
				{
					// flag the form invalid
					result.valid=false;
					f = form.findInvalid()[0];
					if (f) 
					{
						f.ensureVisible();
					}
				}
			}
		});
		
		return result;	
	},
	
	/**
	 * Method to collect all of the invalid
	 * fields from a form.
	 */
	findInvalidFields: function(form)
	{
		return form.getFields().filterBy(function(field) {
	        var result=false;
			if (!field.validate())
	        {
				result = true;
	        }
			return result;
	    });
	},

	/**
	 * Clean a fields label of span tags used for
	 * outputing error asterisks on validatable fields
	 */
	cleanInvalidField: function( field )
	{
		return new Ssp.model.FieldError({
			label: field.fieldLabel.replace(Ssp.util.Constants.REQUIRED_ASTERISK_DISPLAY,"","gi"),
			errorMessage: field.activeErrors.join('. ')
		});
	},
	
	displayErrors: function( fields ){
		this.errorsStore.loadData( fields );
		Ext.create('Ssp.view.ErrorWindow', {
		    height: 300,
		    width: 500
		}).show();
	},
	
	displaySaveSuccessMessage: function( scope ){
		var me=scope;
		var hideMessage = function () {
			me.hide();
		};
		var id = setTimeout(hideMessage, Ssp.util.Constants.DATA_SAVE_SUCCESS_MESSAGE_TIMEOUT);
		me.show();
	},
    
	/**
	 * Fix a date to correct for the GMT Offset in ExtJS.
	 */
	fixDateOffset: function( dateToFix ) {
		return new Date(dateToFix.toUTCString().substr(0, 25));
	},
	
	/**
	 * Fix a date to correct for the GMT Offset in ExtJS.
	 */
	fixDateOffsetWithTime: function( dateToFix ) {
		return new Date(dateToFix.getUTCFullYear(), dateToFix.getUTCMonth(), dateToFix.getUTCDate(),  dateToFix.getUTCHours(), dateToFix.getUTCMinutes(), dateToFix.getUTCSeconds());
	},
	
	dateWithin: function(beginDate,endDate,checkDate) {
    	var b,e,c;
    	b = Date.parse(beginDate);
    	e = Date.parse(endDate);
    	c = Date.parse(checkDate);
    	if((c <= e && c >= b)) {
    		return true;
    	}
    	return false;
    },
    
    /*
     * This method is available to return an array of simple items
     * with a name property for display in a multiselect list when
     * the list is for display purposes only. This method will
     * return an array of items that have been compared against
     * a reference store and assigned a name from the models in
     * the store.
     * 
     * @params:
     *  referenceStore - A store with reference data for defining the assigned name label.
     *  selectedIdsArray - An array of selected id values to compare against.
     *  noItemsPropertyLabel - A label to display if no items matched. For instance: 'Suggestions' will create a single record looking like: No Suggestions. 
     */
    getSimpleItemsForDisplay: function(referenceStore, selectedIdsArray, noItemsPropertyLabel){
    	var me=this;
    	var selectedItems=[];
    	Ext.Array.each( selectedIdsArray, function(selectedItem,index){
			var item = {name: ""};
			var referenceItem;
			if ( selectedItem instanceof Object)
			{
	    		referenceItem = referenceStore.getById( selectedItem.id );				
			}else{
				referenceItem = referenceStore.getById( selectedItem );				
			}
    		if (referenceItem != null)
    		{
    			item = {name: referenceItem.get('name')};
    		}
			selectedItems.push( item );
		});
		if (selectedItems.length==0)
			selectedItems.push({name:'No ' + noItemsPropertyLabel});
		return selectedItems;
    }
});


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
Ext.define('Ssp.util.ColumnRendererUtils',{
	extend: 'Ext.Component',

	initComponent: function() {
		return this.callParent( arguments );
    },

	renderFriendlyBoolean: function(val, metaData, record) {
		var result = "";
		if (val !== null )
        {
           if (val !== "")
           {
        	   result = ((val===true || val === 'true')?'Yes':'No');
           }
        }
        
        return result;
	},    
    
	renderTaskName: function(val, metaData, record) {
		var strHtml = '<div style="white-space:normal !important;">';
        strHtml += '<p>' + record.get('name').toUpperCase() + '</p>';
		strHtml += '<p>' + record.get('description') + '</p>';
		strHtml += '</div>';
	    return strHtml;
	},

	renderTaskDueDate: function(val, metaData, record) {
		var strHtml = '<div style="white-space:normal !important;">';
        strHtml += '<p>' + Ext.util.Format.date( record.get('dueDate') ,'m/d/Y') + '</p>';
        strHtml += '<p>' + ((record.get('completedDate') != null) ? 'COMPLETE' : 'ACTIVE' ) + '</p>';
		strHtml += '<p>' + record.get('confidentialityLevel').name.toUpperCase() + '<br/>' + record.getCreatedByPersonName().toUpperCase() + '</p>';
		strHtml += '</div>';
	    return strHtml;
	},	
	
	renderGoalName: function(val, metaData, record) {
		var strHtml = '<div style="white-space:normal !important;">';
        strHtml += '<p>' + record.get('name').toUpperCase() + '</p>';
		strHtml += '<p>' + record.get('description') + '</p>';
		strHtml += '</div>';
	    return strHtml;	
	},	
	
	renderConfidentialityLevelName: function(val, metaData, record) {
		var strHtml = '<div style="white-space:normal !important;">';
        strHtml += '<p>' + record.get('confidentialityLevel').name.toUpperCase() + '</p>';
		strHtml += '</div>';
	    return strHtml;		
	},

	renderCreatedByDate: function(val, metaData, record) {
	    return Ext.util.Format.date( record.get('createdDate'),'m/d/Y');		
	},

	renderCreatedByDateWithTime: function(val, metaData, record) {
	    return Ext.util.Format.date( record.get('createdDate'),'m/d/Y g:i A');		
	},	

	renderCreatedBy: function(val, metaData, record) {
	    return record.get('createdBy').firstName.toUpperCase() + ' ' + record.get('createdBy').lastName.toUpperCase();		
	},	
	
	renderCreatedByDateAndName: function(val, metaData, record) {
		var strHtml = '<div style="white-space:normal !important;">';
        strHtml += '<p>' + record.get('createdBy').firstName.toUpperCase() + ' ' + record.get('createdBy').lastName.toUpperCase() + '</p>';
        strHtml += '<p>' + Ext.util.Format.date( record.get('createdDate'),'m/d/Y') + '</p>';
        strHtml += '</div>';
	    return strHtml;		
	},
 
	renderJournalSourceName: function(val, metaData, record) {
		var strHtml = '<div style="white-space:normal !important;">';
        strHtml += '<p>' + record.get('journalSource').name.toUpperCase() + '</p>';
         strHtml += '</div>';
	    return strHtml;		
	},	
	
	renderPhotoIcon: function(val) {
	    return '<img src="' + val + '">';
	},

	renderCoachName: function(val, metaData, record) {
		var strHtml = '<div>';
		strHtml += '<p>' + record.getCoachFullName() + '</p>';
        strHtml += '</div>';
	    return strHtml;
	},
	
	renderSearchStudentName: function(val, metaData, record) {
		var strHtml = '<div>';
		strHtml += '<p>' + record.getFullName() + '</p>';
        strHtml += '</div>';
	    return strHtml;
	},

	renderPersonFullName: function(val, metaData, record) {
		var strHtml = '<div>';
		strHtml += '<p>' + record.getPersonFullName() + '</p>';
        strHtml += '</div>';
	    return strHtml;
	},	
	
	renderStudentDetails: function(val, metaData, record) {
		var strHtml = '<div>';
		strHtml += '<p>' + record.getFullName() + '</p>';
		strHtml += '<p>COACH: ' + record.getCoachFullName() + '</p>';
        strHtml += '<p>ID: ' + record.get('schoolId') + '</p>';
        strHtml += '<p>STATUS: ' + record.get('currentProgramStatusName') + '</p>';
        strHtml += '</div>';
	    return strHtml;
	},
	
	renderStudentType: function(val, metaData, record) {
		var strHtml = '<div>';
        strHtml += '<p>' + record.getStudentTypeName() + '</p>';
        strHtml += '</div>';
	    return strHtml;
	},
	
	renderAddToolIcon: function(value,meta,record,rowIx,ColIx, store) {
	    return (record.get("active")==false)?
	                'addToolIcon':
	                'hideAddToolIcon';
	},

	renderErrorMessage: function(val, metaData, record) {
		var strHtml = '<div style="white-space:normal !important;">';
        strHtml += '<p>' + record.get('errorMessage') + '</p>';
		strHtml += '</div>';
	    return strHtml;
	},
	
	renderEarlyAlertStatus: function(val, metaData, record) {
		var status = ((record.get('closedDate') != null)? 'Closed' : 'Open');
		var strHtml = '<div style="white-space:normal !important;">';
        strHtml += '<p>' + ((record.get('nodeType').toLowerCase() == 'early alert')? status : "N/A") + '</p>';
		strHtml += '</div>';
	    return strHtml;
	},
	
	/**
	 * This method is used to return an object with id values
	 * an array format expected by the ExtJS multiSelect or itemSelect
	 * components.
	 * 
	 * Translates: 
	 * [{"id":"1"},{"id":"2"},{"id":"3"}]
	 * 
	 * Into:
	 * ["1","2","3"]
	 */
	getSelectedIdsForMultiSelect: function( arr ){
		var selectedIds = [];
		Ext.each(arr,function(item,index){
			selectedIds.push(item["id"]);
		});
		return selectedIds;
	}
});
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
Ext.define('Ssp.util.TreeRendererUtils',{	
	extend: 'Ext.Component',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	treeStore: 'treeStore'
    },

	initComponent: function() {
		return this.callParent(arguments);
    },
 
    /*
     * Find a child in the tree.
     * @id - the object id of the node to find
     */
    getNodeById: function( id ){
    	return this.treeStore.getNodeById( id );
    },    
    
    /*
     * Clears the treeStore instance, so new folders can be assigned.
     */
    clearRootCategories: function(){
    	// clear tree
    	this.treeStore.setRootNode({
	        text: 'root',
	        expanded: true,
	        children: []
	    });
    },
    
    /*
     * Appends children to the tree under a specified parentNode
     * @parentNode - the node at which to append
     * @children - the child nodes to append
     *    - Should be in the format of nodes as returned by the createNodes methods of this class. 
     */
    appendChildren: function(nodeToAppendTo, children) {
    	// Append to the root if no node is defined
    	if (nodeToAppendTo == null)
    	{
    		nodeToAppendTo = this.treeStore.getRootNode();
    	}else
    	{
    		// if not using the root
    		// then clean the node before append
    		nodeToAppendTo.removeAll();
    	}
    	
    	// only append if there are children
    	if (children.length > 0)
    	{
    		nodeToAppendTo.appendChild( children );
    	}
    },
    
    getNameFromNodeId: function( value ) {
    	var arr = value.split('_');
    	return arr[1];
    },
 
    getIdFromNodeId: function( value ) {
    	var arr = value.split('_');
    	return arr[0];
    },    
    
    /*
     * @records - Array of records in json format.
     * @isLeaf - Determines if the returned nodes array contains branch or leaf elements
     *             The default is to return branch elements.  
     */
    createNodes: function(records, isLeaf){
    	var nodes = [];
    	Ext.each(records, function(name, index) {
    		nodes.push({
    	    	        text: records[index].get('name'),
    	    	        id: records[index].get('id'),
    	    	        leaf: isLeaf || false
    	    	      });
    	});
    	return nodes;
    },
    
    /*
     * @records - Array of records in json format.
     * @isLeaf - Determines if the returned nodes array contains branch or leaf elements
     *             The default is to return branch elements.
     * @nodeType - An optional description for the node type. Used to identify the type of node for drag
     *             and drop functionality. For example with a nodeType set to 'challenge', the node will
     *             be created with an id such as 12345_challenge.
     * @expanded - whether or not a branch should load expanded   
     */
    createNodesFromJson: function(records, isLeaf, nodeType, enableCheckSelection, expanded, expandable, includeToolTip, toolTipFieldName){
    	var nodeIdentifier = "";
    	var enableCheckSelection = enableCheckSelection;
    	var nodes = [];
    	var nodeName = nodeType || "";
    	if (nodeName != "")
    	{
    		nodeIdentifier = '_' + nodeName;
    	}
    	Ext.each(records, function(name, index) {
    		var nodeData = {
        	        text: records[index].name,
        	        id: records[index].id + nodeIdentifier,
        	        qtip: ((includeToolTip === true)? records[index][toolTipFieldName] : ""),
        	        leaf: isLeaf || false,
        	        expanded: expanded,
        	        expandable: expandable
        	      };
        	
        	if (enableCheckSelection && isLeaf==true)
        		nodeData['checked']=false;
        	
    		nodes.push( nodeData );
    	});
    	
    	return nodes;
    },   
 
    /*
     * Retrieves items to populate the tree store.
     * @args.url - The url for the request to get items
	 * @args.nodeType - An optional name to append to the id to determine the name of node
	 * @args.isLeaf - Boolean, whether or not the items are branch or leaf nodes
	 * @args.nodeToAppendTo = the rootNode to append the items
	 * @args.enableCheckedItems = boolean to determine if a checkbox is created for leaf items in the tree
     * @args.expanded - boolean to determine whether a branch should appear as expanded
     * @args.expandable - boolean to determine whether or not the branch can be expanded or collapsed
     * @args.
     */
    getItems: function( treeRequest ){
    	var me=this;
    	var destroyBeforeAppend = treeRequest.get('destroyBeforeAppend');
    	var url = treeRequest.get('url');
    	var isLeaf = treeRequest.get('isLeaf');
    	var enableCheckSelection = treeRequest.get('enableCheckedItems');
    	var nodeToAppendTo = treeRequest.get('nodeToAppendTo');
    	var nodeType = treeRequest.get('nodeType');
    	var expanded = treeRequest.get('expanded');
    	var expandable = treeRequest.get('expandable');
    	var callbackFunc = treeRequest.get('callbackFunc');
    	var callbackScope = treeRequest.get('callbackScope');
    	var removeParentWhenNoChildrenExist = treeRequest.get('removeParentWhenNoChildrenExist');
    	var includeToolTip = treeRequest.get('includeToolTip');
    	var toolTipFieldName = treeRequest.get('toolTipFieldName');
    	// retrieve items
		me.apiProperties.makeRequest({
			url: me.apiProperties.createUrl( url ),
			method: 'GET',
			jsonData: '',
			successFunc: function(response,view){
		    	var r = Ext.decode(response.responseText);
		    	var records = r.rows;
		    	var nodes = [];
		    	if (records.length > 0)
		    	{
		    		nodes = me.createNodesFromJson(records, isLeaf, nodeType, enableCheckSelection, expanded, expandable, includeToolTip, toolTipFieldName);
		    		me.appendChildren( nodeToAppendTo, nodes);
		    	}else{
		    		me.appendChildren( nodeToAppendTo, []);
		    		if (removeParentWhenNoChildrenExist==true)
		    		{
		    			nodeToAppendTo.remove(true);
		    		}
		    	}
		    	
	    		if (callbackFunc != null && callbackFunc != "")
	    			callbackFunc( callbackScope );
			}
		});
    },    
    
});
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
Ext.define('Ssp.util.Constants',{
	extend: 'Ext.Component',
	
    statics: {
    	/* 
    	 * id values referenced in the restrictedIds Array will be restricted
    	 * from administrative functionality through the use of a
    	 * test against a supplied id value. In general, these
    	 * items are critically linked to other operations in the UI,
    	 * such as a field value like 'Other' which displays a description
    	 * field to collect additional associated data.
    	 * 
    	 * For example:
    	 * This method can be used inside a function call from a button or other
    	 * interface item to determine if the item is restricted. 
    	 * Ssp.util.Constants.isRestrictedAdminItemId( selection.get('id')  )
    	 * 
    	 */
    	isRestrictedAdminItemId: function( id ){
    		var restrictedIds = [
    		            Ssp.util.Constants.DISABILITY_AGENCY_OTHER_ID,
    		        	Ssp.util.Constants.EDUCATION_GOAL_OTHER_ID,
    		        	Ssp.util.Constants.EDUCATION_GOAL_MILITARY_ID,
    		        	Ssp.util.Constants.EDUCATION_GOAL_BACHELORS_DEGREE_ID,
    		        	Ssp.util.Constants.EDUCATION_LEVEL_NO_DIPLOMA_GED_ID,
    		        	Ssp.util.Constants.EDUCATION_LEVEL_GED_ID,
    		        	Ssp.util.Constants.EDUCATION_LEVEL_HIGH_SCHOOL_GRADUATION_ID,
    		        	Ssp.util.Constants.EDUCATION_LEVEL_SOME_COLLEGE_CREDITS_ID,
    		        	Ssp.util.Constants.EDUCATION_LEVEL_OTHER_ID,
    		        	Ssp.util.Constants.FUNDING_SOURCE_OTHER_ID,
    		        	Ssp.util.Constants.CHALLENGE_OTHER_ID,
    		        	Ssp.util.Constants.OTHER_EARLY_ALERT_OUTCOME_ID,
    		        	Ssp.util.Constants.OTHER_EARLY_ALERT_REASON_ID,
    		        	Ssp.util.Constants.OTHER_EARLY_ALERT_SUGGESTION_ID,
    		        	Ssp.util.Constants.EARLY_ALERT_JOURNAL_TRACK_ID,
    		        	Ssp.util.Constants.ACTIVE_PROGRAM_STATUS_ID,
    		        	Ssp.util.Constants.NO_SHOW_PROGRAM_STATUS_ID,
    		        	Ssp.util.Constants.NON_PARTICIPATING_PROGRAM_STATUS_ID,
    		        	Ssp.util.Constants.INACTIVE_PROGRAM_STATUS_ID,
    		        	Ssp.util.Constants.TRANSITIONED_PROGRAM_STATUS_ID
    		        ];
    		return ((Ext.Array.indexOf( restrictedIds, id ) != -1)? true : false);
    	},
    	
    	// DEFAULT CONFIDENTIALITY LEVEL ID
    	// If a value is assigned here then it will be used for the default
    	// confidentiality level for lists in the SSP portlet.
    	// default to EVERYONE: 'b3d077a7-4055-0510-7967-4a09f93a0357'
    	DEFAULT_SYSTEM_CONFIDENTIALITY_LEVEL_ID: '',

    	// DISABILITY AGENCY - ID VALUES RELATED TO ACCOMMODATION DISABILITY AGENCIES
        DISABILITY_AGENCY_OTHER_ID: '224b03d9-90da-4f9c-8959-ea2e97661f40',
    	   	
    	// EDUCATION GOALS - ID VALUES RELATED TO STUDENT INTAKE EDUCATION GOALS
        EDUCATION_GOAL_OTHER_ID: '78b54da7-fb19-4092-bb44-f60485678d6b',
        EDUCATION_GOAL_MILITARY_ID: '6c466885-d3f8-44d1-a301-62d6fe2d3553',
        EDUCATION_GOAL_BACHELORS_DEGREE_ID: 'efeb5536-d634-4b79-80bc-1e1041dcd3ff',
        
        // EDUCATION LEVELS - ID VALUES RELATED TO STUDENT INTAKE EDUCATION LEVELS
        EDUCATION_LEVEL_NO_DIPLOMA_GED_ID: '5d967ba0-e086-4426-85d5-29bc86da9295',
        EDUCATION_LEVEL_GED_ID: '710add1c-7b53-4cbe-86cb-8d7c5837d68b',
        EDUCATION_LEVEL_HIGH_SCHOOL_GRADUATION_ID: 'f4780d23-fd8a-4758-b772-18606dca32f0',
        EDUCATION_LEVEL_SOME_COLLEGE_CREDITS_ID: 'c5111182-9e2f-4252-bb61-d2cfa9700af7',
        EDUCATION_LEVEL_OTHER_ID: '247165ae-3db4-4679-ac95-ca96488c3b27',
        
        // FUNDING SOURCES - ID VALUES RELATED TO STUDENT INTAKE FUNDING SOURCES
        FUNDING_SOURCE_OTHER_ID: '365e8c95-f356-4f1f-8d79-4771ae8b0291',
        
        // CHALLENGES - ID VALUES RELATED TO STUDENT INTAKE CHALLENGES
        CHALLENGE_OTHER_ID: '365e8c95-f356-4f1f-8d79-4771ae8b0291',
    
        // EARLY ALERT OUTCOME - ID VALUES RELATED TO EARLY ALERT OUTCOMES
        OTHER_EARLY_ALERT_OUTCOME_ID: '0a080114-3799-1bf5-8137-9a778e200004',
        OTHER_EARLY_ALERT_REASON_ID: 'b2d11335-5056-a51a-80ea-074f8fef94ea',
        OTHER_EARLY_ALERT_SUGGESTION_ID: 'b2d1120c-5056-a51a-80ea-c779a3109f8f',
        
        // EARLY ALERT - JOURNAL TRACK
        EARLY_ALERT_JOURNAL_TRACK_ID: 'b2d07b38-5056-a51a-809d-81ea2f3b27bf',
        
        // PROGRAM STATUS - ID VALUES RELATED TO PROGRAM STATUS REFERENCE DATA
        ACTIVE_PROGRAM_STATUS_ID: 'b2d12527-5056-a51a-8054-113116baab88',
        NON_PARTICIPATING_PROGRAM_STATUS_ID: 'b2d125c3-5056-a51a-8004-f1dbabde80c2',
        NO_SHOW_PROGRAM_STATUS_ID: 'b2d12640-5056-a51a-80cc-91264965731a',
        INACTIVE_PROGRAM_STATUS_ID: 'b2d125a4-5056-a51a-8042-d50b8eff0df1',
        TRANSITIONED_PROGRAM_STATUS_ID: 'b2d125e3-5056-a51a-800f-6891bc7d1ddc',
        
        // ICON PATHS FOR ACTION STYLE GRID BUTTONS NOT APPLIED THROUGH CSS 
        GRID_ITEM_DELETE_ICON_PATH: '/ssp/images/delete-icon.png',
        GRID_ITEM_EDIT_ICON_PATH: '/ssp/images/edit-icon.jpg',
        GRID_ITEM_CLOSE_ICON_PATH: '/ssp/images/close-icon.jpg',
        GRID_ITEM_MAIL_REPLY_ICON_PATH: '/ssp/images/mail-reply-icon.png',
        DEFAULT_NO_STUDENT_PHOTO_URL:'/ssp/images/no-photo.jpg',
        
        // CAN BE APPLIED TO THE LABEL OF A FIELD TO SHOW A RED REQUIRED ASTERISK
        REQUIRED_ASTERISK_DISPLAY: '<span style="color: rgb(255, 0, 0); padding-left: 2px;">*</span>',

        // CAN BE APPLIED TO THE LABEL OF A FIELD OR CONTAINER TO ALTER THE LABEL STYLE
        SSP_LABEL_STYLE: "color:#04408c;",        
        
        // CONFIGURES THE MESSAGE DISPLAYED NEXT TO THE SAVE BUTTON FOR TOOLS WHERE A SAVE IS ON A SINGLE SCREEN
        // FOR EXAMPLE: THIS FUNCTIONALITY IS APPLIED TO THE STUDENT INTAKE TOOL, ACTION PLAN STRENGTHS AND CONFIDENTIALITY DISCLOSURE AGREEMENT
        DATA_SAVE_SUCCESS_MESSAGE_STYLE: "font-weight: 'bold'; color: rgb(0, 0, 0); padding-left: 2px;",
        DATA_SAVE_SUCCESS_MESSAGE: '&#10003 Data was successfully saved',
        DATA_SAVE_SUCCESS_MESSAGE_TIMEOUT: 3000
    },

	initComponent: function() {
		return this.callParent( arguments );
    }
});
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
Ext.define('Ssp.store.Caseload', {
    // extend: 'Ext.data.Store',
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.CaseloadPerson',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
        apiProperties: 'apiProperties'
    },
	constructor: function(){
		var me=this;
		Ext.apply(me, {
						proxy: me.apiProperties.getProxy(me.apiProperties.getItemUrl('personCaseload')),
						autoLoad: false,
						autoSync: false,
					    pageSize: me.apiProperties.getPagingSize(),
					    params : {
							page : 0,
							start : 0,
							limit : me.apiProperties.getPagingSize()
						}
					});
		return me.callParent(arguments);
	}
});
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
Ext.define('Ssp.store.JournalEntryDetails', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.tool.journal.JournalEntryDetail',
	groupField: 'group'
});
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
Ext.define('Ssp.store.EarlyAlertCoordinators', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.PersonLite',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
        apiProperties: 'apiProperties'
    },
	constructor: function(){
		var me=this;
		Ext.apply(me, {
						proxy: me.apiProperties.getProxy(me.apiProperties.getItemUrl('personCoach')+'/?sort=lastName'),
						autoLoad: false,
						pageSize: 1000, // max allowed server-side
						params : {
							page : 0,
							start : 0,
							limit : 1000 // max allowed server-side
						}
					});
		return me.callParent(arguments);
	}
});
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
Ext.define('Ssp.store.admin.AdminTreeMenus',{
	extend: 'Ext.data.TreeStore',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
        columnRendererUtils: 'columnRendererUtils'
    },
	autoLoad: false,
    constructor: function(){
    	var me=this;
    	var items = {
    	    	text: 'Administrative Tools',
    	    	title: 'Administrative Tools',
    	    	form: '',
    	        expanded: true,
    	        children: [ {
			    	            text: 'Student Success',
			    	            title: 'Student Success',
			    	            form: '',
			    	            expanded: false,
			    	            children: [{
									text: 'Campus Services',
									title: 'Campus Services',
									store: 'campusServices',
							        form: 'AbstractReferenceAdmin',
									leaf: true
							    },{
									text: 'LASSI Skill Components',
									title: 'LASSI Skill Components',
									store: 'lassis',
							        form: 'AbstractReferenceAdmin',
									leaf: true
							    },{
									text: 'Personality Types',
									title: 'Personality Types',
									store: 'personalityTypes',
							        form: 'AbstractReferenceAdmin',
									leaf: true
							    }]
			                },{
			    	            text: 'Accommodation',
			    	            title: 'Accommodation',
			    	            form: '',
			    	            expanded: false,
			    	            children: [{
									text: 'Disability Accommodations',
									title: 'Disability Accommodations',
									store: 'disabilityAccommodations',
							        form: 'AbstractReferenceAdmin',
									leaf: true,
									columns: [
					    		                { header: 'Name',  
					    		                  dataIndex: 'name',
					    		                  required: true,
					    		                  field: {
					    		                      xtype: 'textfield'
					    		                  },
					    		                  flex: 1 },
					      		                { header: 'Additional Desc',
					    		                  required: true,
					      		                  dataIndex: 'useDescription', 
					      		                  flex: .2,
					      		                  renderer: me.columnRendererUtils.renderFriendlyBoolean,
					      		                  field: {
					      		                      xtype: 'checkbox'
					      		                  }
					      		                },
					    		                { header: 'Desc Label',
					    		                  required: false,
					    		                  dataIndex: 'descriptionFieldLabel',
					    		                  field: {
					    		                      xtype: 'textfield'
					    		                  },
					    		                  flex: .2
					    		                },
					    		                { header: 'Long/Short Desc',
					    		                  required: false,
					    		                  dataIndex: 'descriptionFieldType',
					    		                  field: {
					    		                      xtype: 'textfield'
					    		                  },
					    		                  flex: .2
					    		                }
					    		           ]
							    },{
									text: 'Disability Agencies',
									title: 'Disability Agencies',
									store: 'disabilityAgencies',
							        form: 'AbstractReferenceAdmin',
									leaf: true
							    },{
									text: 'Disability Statuses',
									title: 'Disability Statuses',
									store: 'disabilityStatuses',
							        form: 'AbstractReferenceAdmin',
									leaf: true
							    },{
									text: 'Disability Types',
									title: 'Disability Types',
									store: 'disabilityTypes',
							        form: 'AbstractReferenceAdmin',
									leaf: true
							    }]
			                },{
    	        	            text: 'Caseload',
    	        	            title: 'Caseload',
    	        	            form: '',
    	        	            expanded: false,
    	        	            children: [{
									text: 'Program Status Change Reasons',
									title: 'Program Status Change Reasons',
									store: 'programStatusChangeReasons',
							        form: 'AbstractReferenceAdmin',
									leaf: true
							    }]
    	                    },
    						{
    							text: 'Caseload Assignment',
    							title: 'Caseload Assignment',
    							form: '',
    							expanded: false,
    							children: [{
    										text: 'Referral Sources',
    										title: 'Referral Sources',
    										store: 'referralSources',
    								        form: 'AbstractReferenceAdmin',
    										leaf: true
    								    },{
    										text: 'Special Service Groups',
    										title: 'Special Service Groups',
    										store: 'specialServiceGroups',
    								        form: 'AbstractReferenceAdmin',
    										leaf: true
    									},
    									{
    										text: 'Service Reasons',
    										title: 'Service Reasons',
    										store: 'serviceReasons',
    								        form: 'AbstractReferenceAdmin',
    										leaf: true
    								    },{
    										text: 'Student Types',
    										title: 'Student Types',
    										store: 'studentTypes',
    								        form: 'AbstractReferenceAdmin',
    										leaf: true,
    										columns: [
    						    		                { header: 'Name',  
    						    		                  dataIndex: 'name',
    						    		                  required: true,
    						    		                  field: {
    						    		                      xtype: 'textfield'
    						    		                  },
    						    		                  flex: .25 },
    						    		                { header: 'Description',
    						    		                  required: false,
    						    		                  dataIndex: 'description',
    						    		                  field: {
    						    		                      xtype: 'textfield'
    						    		                  },
    						    		                  flex: 1},
    						      		                { header: 'Require Initial Appointment',
    						    		                  required: true,
    						      		                  dataIndex: 'requireInitialAppointment', 
    						      		                  flex: .25,
    						      		                  renderer: me.columnRendererUtils.renderFriendlyBoolean,
    						      		                  field: {
    						      		                      xtype: 'checkbox'
    						      		                  }
    						    		                }
    						    		           ]
    									}
    							]
    						},{
    							text: 'Student Intake',
    							title: 'Student Intake',
    							form: '',
    							expanded: false,
    							children: [
    									{
    										text: 'Child Care Arrangements',
    										title: 'Child Care Arrangements',
    										store: 'childCareArrangements',
    								        form: 'AbstractReferenceAdmin',
    										leaf: true
    									},
    									{
    										text: 'Citizenships',
    										title: 'Citizenships',
    										store: 'citizenships',
    								        form: 'AbstractReferenceAdmin',
    										leaf: true
    								    },
    								    {
    								    	text: 'Confidentiality Disclosure Agreement',
    								    	title: 'Confidentiality Disclosure Agreement',
    								    	store: '',
    								        form: 'ConfidentialityDisclosureAgreementAdmin',
    										leaf: true
    								    },
    								    {
    								    	text: 'Education Goals',
    								    	title: 'Education Goals',
    								    	store: 'educationGoals',
    								        form: 'AbstractReferenceAdmin',
    										leaf: true
    								    }
    								    ,{
    								    	text: 'Education Levels',
    								    	title: 'Education Levels',
    								    	store: 'educationLevels',
    								        form: 'AbstractReferenceAdmin',
    										leaf: true
    								    }
    								    ,{
    								    	text: 'Ethnicities',
    								    	title: 'Ethnicities',
    								    	store: 'ethnicities',
    								        form: 'AbstractReferenceAdmin',
    										leaf: true
    								    }
    								    ,{
    								    	text: 'Funding Sources',
    								    	title: 'Funding Sources',
    								    	store: 'fundingSources',
    								        form: 'AbstractReferenceAdmin',
    										leaf: true
    								    }
    								    ,{
    								    	text: 'Marital Statuses',
    								    	title: 'Marital Statuses',
    								    	store: 'maritalStatuses',
    								        form: 'AbstractReferenceAdmin',
    										leaf: true
    								    },{
    								    	text: 'Military Affiliations',
    								    	title: 'Military Affiliations',
    								    	store: 'militaryAffiliations',
    								        form: 'AbstractReferenceAdmin',
    										leaf: true
    								    }
    								    ,{
    								    	text: 'Student Statuses',
    								    	title: 'Student Statuses',
    								    	store: 'studentStatuses',
    								        form: 'AbstractReferenceAdmin',
    										leaf: true
    								    }
    								    ,{
    								    	text: 'Veteran Statuses',
    								    	title: 'Veteran Statuses',
    								    	store: 'veteranStatuses',
    								        form: 'AbstractReferenceAdmin',
    										leaf: true
    								    }
    							]
    						},{
    							text: 'Counseling Reference Guide',
    							title: 'Counseling Reference Guide',
    							form: '',
    							expanded: false,
    							children: [{
											text: 'Categories',
											title: 'Categories',
											store: 'challengeCategories',
											form: 'AbstractReferenceAdmin',
											leaf: true
										},{
    										text: 'Challenges',
    										title: 'Challenges',
    										store: '',
    										form: 'ChallengeAdmin',
    										leaf: true
    									},{
    										text: 'Referrals',
    										title: 'Referrals',
    										store: '',
    										form: 'ChallengeReferralAdmin',
    										leaf: true
    									}]
    						},{
    							text: 'MyGPS',
    							title: 'MyGPS',
    							form: '',
    							expanded: false,
    							children: [{
											text: 'Self Help Guides',
											title: 'Self Help Guides',
											store: 'selfHelpGuides',
											form: 'selfhelpguideadmin',
											leaf: true
										}]
    						},
    						{
    							text: 'Security',
    							title: 'Security',
    							form: '',
    							expanded: false,
    							children: [{text: 'Confidentiality Levels',
    										title: 'Confidentiality Levels',
    										store: 'confidentialityLevels',
    										form: 'AbstractReferenceAdmin',
    										leaf: true,
    										columns: [{ header: 'Name',  
    						    		                  dataIndex: 'name',
    						    		                  required: true,
    						    		                  field: {
    						    		                      xtype: 'textfield'
    						    		                  },
    						    		                  flex: 50 },
    						    		                { header: 'Description',
    						    		                  dataIndex: 'description', 
    						    		                  flex: 50,
    						    		                  field: {
    						    		                      xtype: 'textfield'
    						    		                  },
    						    		                  flex: 50 },
    						      		                { header: 'Acronym',
    						      		                  dataIndex: 'acronym',
    						      		                  required: true,
    						      		                  flex: 50,
    						      		                  field: {
    						      		                      xtype: 'textfield'
    						      		                  }
    						    		                }]
    									}]
    						},{
    							text: 'Early Alert',
    							title: 'Early Alert',
    							form: '',
    							expanded: false,
    							children: [{
							    	text: 'Campuses',
									title: 'Campuses',
									store: '',
									form: 'CampusAdmin',
									leaf: true
								},{
									text: 'Outcomes',
									title: 'Outcomes',
									store: 'earlyAlertOutcomes',
							        form: 'AbstractReferenceAdmin',
									leaf: true
								},{
									text: 'Outreaches',
									title: 'Outreaches',
									store: 'earlyAlertOutreaches',
							        form: 'AbstractReferenceAdmin',
									leaf: true
								},{
									text: 'Reasons',
									title: 'Reasons',
									store: 'earlyAlertReasons',
							        form: 'AbstractReferenceAdmin',
									leaf: true
								},{
									text: 'Referrals',
									title: 'Referrals',
									store: 'earlyAlertReferrals',
							        form: 'AbstractReferenceAdmin',
									leaf: true,
									columns: [
					    		                { header: 'Name',  
					    		                  dataIndex: 'name',
					    		                  required: true,
					    		                  field: {
					    		                      xtype: 'textfield'
					    		                  },
					    		                  flex: 50 },
					    		                { header: 'Description',
					    		                  required: false,
					    		                  dataIndex: 'description', 
					    		                  flex: 50,
					    		                  field: {
					    		                      xtype: 'textfield'
					    		                  },
					    		                  flex: 50 },
					      		                { header: 'Acronym',
					    		                  required: true,
					      		                  dataIndex: 'acronym', 
					      		                  flex: 50,
					      		                  field: {
					      		                      xtype: 'textfield'
					      		                  }
					    		                }
					    		           ]
								},{
									text: 'Suggestions',
									title: 'Suggestions',
									store: 'earlyAlertSuggestions',
							        form: 'AbstractReferenceAdmin',
									leaf: true
								}]
    						},{
    							text: 'Journal',
    							title: 'Journal',
    							form: '',
    							expanded: false,
    							children: [{
									text: 'Sources',
									title: 'Sources',
									store: 'journalSources',
							        form: 'AbstractReferenceAdmin',
									leaf: true
								},{
									text: 'Details',
									title: 'Details',
									store: '',
							        form: 'JournalStepDetailAdmin',
									leaf: true
								},{
									text: 'Steps',
									title: 'Steps',
									store: '',
							        form: 'JournalStepAdmin',
									leaf: true
								},{
									text: 'Tracks',
									title: 'Tracks',
									store: 'journalTracks',
							        form: 'AbstractReferenceAdmin',
									leaf: true
								}]
    						}
    	                   
    	        ]

    	    };
    	
    	Ext.apply(me,{
    		root: items,
    		folderSort: true,
    		sorters: [{
    		    property: 'text',
    		    direction: 'ASC'
    		}]
    	});
		return me.callParent(arguments);
    }


});
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
Ext.define('Ssp.store.PeopleSearchLite', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.PersonSearchLite',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
        apiProperties: 'apiProperties'
    },
	constructor: function(){
		var me=this;
		Ext.apply(me, {
							proxy: me.apiProperties.getProxy(me.apiProperties.getItemUrl('personSearch')+'/?outsideCaseload=true&requireProgramStatus=false'),
							autoLoad: false,
							autoSync: false,
						    pageSize: me.apiProperties.getPagingSize(),
						    params : {
								page : 0,
								start : 0,
								limit : me.apiProperties.getPagingSize()
							}
						});
		return me.callParent(arguments);
	}
});
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
Ext.define('Ssp.store.Search', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.SearchPerson',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
        apiProperties: 'apiProperties'
    },
	constructor: function(){
		var me=this;
		Ext.apply(me, {
							proxy: me.apiProperties.getProxy(me.apiProperties.getItemUrl('personSearch')),
							autoLoad: false,
							autoSync: false,
						    pageSize: me.apiProperties.getPagingSize(),
						    params : {
								page : 0,
								start : 0,
								limit : me.apiProperties.getPagingSize()
							}
						});
		return me.callParent(arguments);
	}
});
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
Ext.define('Ssp.service.AbstractService', {  
    extend: 'Ext.Component',	
    initComponent: function() {
		return this.callParent( arguments );
    },
    
    /**
     * Remove all inactive objects from an array
     * of items 
     */
    filterInactiveChildren: function( arr ){
    	var me=this;
    	var itemsToErase = new Array();
    	if (arr != null)
    	{
    		itemsToErase = me.collectInactiveChildren( arr );
    	}
  
    	Ext.Array.forEach(itemsToErase,function(item,idx){
    		// Erase each inactive steps
			Ext.Array.remove(item.array, item.inactiveItem);
    	});

    	return arr;
    },
    
    /**
     * Method to determine if a collection of objects
     * and their children are inactive
     */
    collectInactiveChildren: function( arr ){
		var me=this;
    	var itemsToErase = [];
    	Ext.Array.each( arr, function( item, s){
    		// determine inactive
    		if ( me.isItemInactive( item ) )
    		{
    			itemsToErase.push({array:arr, inactiveItem: item}); 			
    		}else{
    			// recurse children
    			for (prop in item)
    			{
    				if (item[prop] instanceof Array)
    				{
    					var items = me.collectInactiveChildren( item[prop] );
    					itemsToErase = Ext.Array.merge(itemsToErase, items);
    				}
    			} 
    		}
    	});
    	return itemsToErase;
    },
    
    /**
     * Determine if an object is Inactive
     */
    isItemInactive: function( item ){
    	var isInactive = false;
    	for (prop in item)
    	{
    		if (prop == "objectStatus")
    		{
    			if (item[prop].toLowerCase()=="inactive")
    			{
    				isInactive=true;
    			}
    		}
    	} 
    	return isInactive;
    }
});
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
Ext.define('Ssp.service.AppointmentService', {  
    extend: 'Ssp.service.AbstractService',   		
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	apiProperties: 'apiProperties',
    	appointment: 'currentAppointment',
    	currentPersonAppointment: 'currentPersonAppointment'
    },
    initComponent: function() {
		return this.callParent( arguments );
    },

    getBaseUrl: function( id ){
		var me=this;
		var baseUrl = me.apiProperties.createUrl( me.apiProperties.getItemUrl('personAppointment') );
    	baseUrl = baseUrl.replace('{id}', id);
    	return baseUrl;
    },
    
    getCurrentAppointment: function( personId, callbacks ){
		var me=this;
		var url = me.getBaseUrl( personId );
		var appointment = new Ssp.model.Appointment();
		var personAppointment = new Ssp.model.PersonAppointment();
	    var success = function( response, view ){
	    	var r;
	    	if (response.responseText != "")
	    	{
	    	   r = Ext.decode(response.responseText);
		   		if (r != null)
		   		{
		   			me.currentPersonAppointment.populateFromGenericObject(r);
		   			
		   			if (me.currentPersonAppointment.get('id') != "")
		   			{
		   				me.appointment.populateFromGenericObject({
		   				   "id": me.currentPersonAppointment.get('id'),
		   				   "appointmentDate": Ext.Date.clearTime(me.currentPersonAppointment.get('startTime'), true),
		   				   "startTime": me.currentPersonAppointment.get('startTime').getTime(),
		   				   "endTime": me.currentPersonAppointment.get('endTime').getTime()
		   			   });
		   			}
		   		}
	    	}
			callbacks.success( r, callbacks.scope );
	    };

	    var failure = function( response ){
	    	me.apiProperties.handleError( response );	    	
	    	callbacks.failure( response, callbacks.scope );
	    };
	    
	    // reset the appointments
	    me.appointment.data = appointment.data;
	    me.currentPersonAppointment.data = personAppointment.data;
	    
		// load the person to edit
		me.apiProperties.makeRequest({
			url: url + '/current',
			method: 'GET',
			successFunc: success,
			failureFunc: failure,
			scope: me
		});    	
    },
    
    saveAppointment: function( personId, jsonData, callbacks ){
		var me=this;
		var url = me.getBaseUrl( personId );
	    var success = function( response, view ){
	    	var r = Ext.decode(response.responseText);
			callbacks.success( r, callbacks.scope );
	    };

	    var failure = function( response ){
	    	me.apiProperties.handleError( response );	    	
	    	callbacks.failure( response, callbacks.scope );
	    };
		
    	if (personId != "")
    	{
    		id = jsonData.id;
    		
    		// save the appointment
    		if (id=="")
    		{				
    			me.apiProperties.makeRequest({
        			url: url,
        			method: 'POST',
        			jsonData: jsonData,
        			successFunc: success,
        			failureFunc: failure,
        			scope: me
        		});				
    		}else{
    			// update
        		me.apiProperties.makeRequest({
        			url: url+"/"+id,
        			method: 'PUT',
        			jsonData: jsonData,
        			successFunc: success,
        			failureFunc: failure,
        			scope: me
        		});	
    		}     		
    	}else{
    		Ext.Msg.alert('SSP Error', 'Error determining student to which to save an appointment. Unable to save to appointment.');
    	}  	
    }
});
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
Ext.define('Ssp.service.AssessmentService', {  
    extend: 'Ssp.service.AbstractService',   		
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	apiProperties: 'apiProperties'
    },
    initComponent: function() {
		return this.callParent( arguments );
    },

    getBaseUrl: function( id ){
		var me=this;
		var baseUrl = me.apiProperties.createUrl( me.apiProperties.getItemUrl('personAssessment') );
    	baseUrl = baseUrl.replace('{id}', id);
    	return baseUrl;
    },
    
    getAll: function( personId, callbacks ){
		var me=this;
		var url = me.getBaseUrl( personId );
	    var success = function( response, view ){
	    	var r = Ext.decode(response.responseText);
			callbacks.success( r, callbacks.scope );
	    };

	    var failure = function( response ){
	    	me.apiProperties.handleError( response );	    	
	    	callbacks.failure( response, callbacks.scope );
	    };
	    
		me.apiProperties.makeRequest({
			url: url,
			method: 'GET',
			successFunc: success,
			failureFunc: failure,
			scope: me
		});    	
    }
});
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
Ext.define('Ssp.service.CaseloadService', {  
    extend: 'Ssp.service.AbstractService',   		
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	apiProperties: 'apiProperties',
    	store: 'caseloadStore'
    },
    initComponent: function() {
		return this.callParent( arguments );
    },
    
    getBaseUrl: function(){
		var me=this;
		var baseUrl = me.apiProperties.createUrl( me.apiProperties.getItemUrl('personCaseload') );
    	return baseUrl;
    },

    getCaseload: function( programStatusId, callbacks ){
    	var me=this;
	    
		// clear the store
		me.store.removeAll();

		// Set the Url for the Caseload Store
		// including param definitions because the params need
		// to be applied prior to load and not in a params 
		// definition from the load method or the paging
		// toolbar applied to the SearchView will not
		// apply the params when using next or previous
		// page
		Ext.apply(me.store.getProxy(),{url: me.getBaseUrl()+'?programStatusId='+programStatusId});

	    me.store.load({
		    callback: function(records, operation, success) {
		        if (success)
		        {
			    	if (callbacks != null)
			    	{
			    		callbacks.success( records, callbacks.scope );
			    	}		        	
		        }else{
			    	if (callbacks != null)
			    	{
			    		callbacks.failure( records, callbacks.scope );
			    	}
		        }
		    },
		    scope: me
		});
    },
    
    getCaseloadById: function( personId, callbacks ){
    	var me=this;
    	var success = function( response, view ){
    		var r = Ext.decode(response.responseText);
	    	if (r.rows.length > 0)
	    	{
	    		me.store.removeAll();
	    		me.store.loadData(r.rows);
	    	}
	    	if (callbacks != null)
	    	{
	    		callbacks.success( r, callbacks.scope );
	    	}	
	    };

	    var failure = function( response ){
	    	me.apiProperties.handleError( response );	    	
	    	if (callbacks != null)
	    	{
	    		callbacks.failure( response, callbacks.scope );
	    	}
	    };
	    
		me.apiProperties.makeRequest({
			url: me.getBaseUrl()+'/'+personId+'/caseload',
			method: 'GET',
			successFunc: success,
			failureFunc: failure,
			scope: me
		});
    }    
});
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
Ext.define('Ssp.service.CampusService', {  
    extend: 'Ssp.service.AbstractService',   		
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	apiProperties: 'apiProperties',
    	model: 'currentCampus'
    },
    config: {
    	personUrl: null
    },
    initComponent: function() {
		return this.callParent( arguments );
    },
    
    getBaseUrl: function( id ){
		var me=this;
		var baseUrl = me.apiProperties.createUrl( me.apiProperties.getItemUrl('campus') );
    	return baseUrl;
    },

    getCampus: function( id, callbacks ){
    	var me=this;
	    var success = function( response, view ){
	    	var r = Ext.decode(response.responseText);
	    	var model = new Ssp.model.reference.Campus();
	    	me.model.data = model.data;
	    	if (response.responseText != "")
	    	{
		    	r = Ext.decode(response.responseText);
		    	me.model.populateFromGenericObject(r);	    		
	    	}
	    	callbacks.success( r, callbacks.scope );
	    };

	    var failure = function( response ){
	    	me.apiProperties.handleError( response );	    	
	    	callbacks.failure( response, callbacks.scope );
	    };
	    
		// load the person to edit
		me.apiProperties.makeRequest({
			url: me.getBaseUrl()+'/'+id,
			method: 'GET',
			successFunc: success,
			failureFunc: failure,
			scope: me
		});
    },   
    
    saveCampus: function( jsonData, callbacks ){
    	var me=this;
    	var id=jsonData.id;
        var url = me.getBaseUrl();
	    var success = function( response, view ){
	    	var r = Ext.decode(response.responseText);
			callbacks.success( r, callbacks.scope );
	    };

	    var failure = function( response ){
	    	me.apiProperties.handleError( response );	    	
	    	callbacks.failure( response, callbacks.scope );
	    };
        
    	// save
		if (id=="")
		{
			// create
			me.apiProperties.makeRequest({
    			url: url,
    			method: 'POST',
    			jsonData: jsonData,
    			successFunc: success,
    			failureFunc: failure,
    			scope: me
    		});				
		}else{
			// update
    		me.apiProperties.makeRequest({
    			url: url+"/"+id,
    			method: 'PUT',
    			jsonData: jsonData,
    			successFunc: success,
    			failureFunc: failure,
    			scope: me
    		});	
		}   	
    },
    
    destroy: function( id, callbacks ){
    	var me=this;
	    var success = function( response, view ){
	    	var r = Ext.decode(response.responseText);
			callbacks.success( r, id, callbacks.scope );
	    };

	    var failure = function( response ){
	    	me.apiProperties.handleError( response );	    	
	    	callbacks.failure( response, callbacks.scope );
	    };
	    
    	me.apiProperties.makeRequest({
   		   url: me.getBaseUrl()+"/"+id,
   		   method: 'DELETE',
   		   successFunc: success,
   		   failureFunc: failure,
   		   scope: me
   	    }); 
    }
});
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
Ext.define('Ssp.service.CampusEarlyAlertRoutingService', {  
    extend: 'Ssp.service.AbstractService',   		
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	apiProperties: 'apiProperties',
    	model: 'currentCampusEarlyAlertRouting',
    	store: 'campusEarlyAlertRoutingsStore'
    },
    config: {
    	personUrl: null
    },
    initComponent: function() {
		return this.callParent( arguments );
    },
    
    getBaseUrl: function( campusId ){
		var me=this;
		var baseUrl = me.apiProperties.createUrl( me.apiProperties.getItemUrl('campusEarlyAlertRouting') );
		baseUrl = baseUrl.replace("{id}",campusId);
		return baseUrl;
    },

    getCampusEarlyAlertRouting: function( campusId, id, callbacks ){
    	var me=this;
    	var url = me.getBaseUrl( campusId );
	    var success = function( response, view ){
	    	var r = Ext.decode(response.responseText);
	    	var model = new Ssp.model.reference.CampusEarlyAlertRouting();
	    	me.model.data = model.data;
	    	if (response.responseText != "")
	    	{
		    	r = Ext.decode(response.responseText);
		    	me.model.populateFromGenericObject(r);	    		
	    	}
	    	callbacks.success( r, callbacks.scope );
	    };

	    var failure = function( response ){
	    	me.apiProperties.handleError( response );	    	
	    	callbacks.failure( response, callbacks.scope );
	    };
	    
		// load
		me.apiProperties.makeRequest({
			url: url+'/'+id,
			method: 'GET',
			successFunc: success,
			failureFunc: failure,
			scope: me
		});
    },   

    getAllCampusEarlyAlertRoutings: function( campusId, callbacks ){
    	var me=this;
    	var url = me.getBaseUrl( campusId );
	    var success = function( response, view ){
	    	var r = Ext.decode(response.responseText);
	    	if (response.responseText != "")
	    	{
		    	r = Ext.decode(response.responseText);
		    	me.store.loadData( r.rows );	    		
	    	}
	    	callbacks.success( r, callbacks.scope );
	    };

	    var failure = function( response ){
	    	me.apiProperties.handleError( response );	    	
	    	callbacks.failure( response, callbacks.scope );
	    };
	    
		// load
		me.apiProperties.makeRequest({
			url: url,
			method: 'GET',
			successFunc: success,
			failureFunc: failure,
			scope: me
		});
    },     
    
    saveCampusEarlyAlertRouting: function( campusId, jsonData, callbacks ){
    	var me=this;
    	var id=jsonData.id;
        var url = me.getBaseUrl( campusId );
	    var success = function( response, view ){
	    	var r = Ext.decode(response.responseText);
			callbacks.success( r, callbacks.scope );
	    };

	    var failure = function( response ){
	    	me.apiProperties.handleError( response );	    	
	    	callbacks.failure( response, callbacks.scope );
	    };
	    
    	// save
		if (id=="")
		{
			// create
			me.apiProperties.makeRequest({
    			url: url,
    			method: 'POST',
    			jsonData: jsonData,
    			successFunc: success,
    			failureFunc: failure,
    			scope: me
    		});				
		}else{
			// update
    		me.apiProperties.makeRequest({
    			url: url+"/"+id,
    			method: 'PUT',
    			jsonData: jsonData,
    			successFunc: success,
    			failureFunc: failure,
    			scope: me
    		});	
		}   	
    },
    
    destroy: function( campusId, id, callbacks ){
    	var me=this;
        var url = me.getBaseUrl( campusId );
	    var success = function( response, view ){
	    	var r = Ext.decode(response.responseText);
			callbacks.success( r, id, callbacks.scope );
	    };

	    var failure = function( response ){
	    	me.apiProperties.handleError( response );	    	
	    	callbacks.failure( response, callbacks.scope );
	    };
	    
    	me.apiProperties.makeRequest({
   		   url: url+"/"+id,
   		   method: 'DELETE',
   		   successFunc: success,
   		   failureFunc: failure,
   		   scope: me
   	    }); 
    }
});
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
Ext.define('Ssp.service.ConfidentialityDisclosureAgreementService', {  
    extend: 'Ssp.service.AbstractService',   		
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	apiProperties: 'apiProperties'
    },
    initComponent: function() {
		return this.callParent( arguments );
    },
    
    getBaseUrl: function(){
		var me=this;
		var baseUrl = me.apiProperties.createUrl( me.apiProperties.getItemUrl('confidentialityDisclosureAgreement') );
		return baseUrl;
    },
    
    save: function( jsonData, callbacks ){
		var me=this;
		var url = me.getBaseUrl();
	    var success = function( response, view ){
	    	var r = Ext.decode(response.responseText);
			callbacks.success( r, callbacks.scope );
	    };

	    var failure = function( response ){
	    	me.apiProperties.handleError( response );	    	
	    	callbacks.failure( response, callbacks.scope );
	    };
		
		id = jsonData.id;

		// save
		if (id=="")
		{				
			me.apiProperties.makeRequest({
    			url: url,
    			method: 'POST',
    			jsonData: jsonData,
    			successFunc: success,
    			failureFunc: failure,
    			scope: me
    		});				
		}else{
			// update
    		me.apiProperties.makeRequest({
    			url: url+"/"+id,
    			method: 'PUT',
    			jsonData: jsonData,
    			successFunc: success,
    			failureFunc: failure,
    			scope: me
    		});	
		}
    }   
});
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
var cc = 0;

Ext.define('Ssp.service.EarlyAlertService', {  
    extend: 'Ssp.service.AbstractService',          
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
        apiProperties: 'apiProperties',
        earlyAlertOutcomesStore: 'earlyAlertOutcomesStore',
        treeStore: 'earlyAlertsTreeStore',
        treeUtils: 'treeRendererUtils',
		currentEarlyAlertResponsesGridStore: 'currentEarlyAlertResponsesGridStore'
    },
    initComponent: function() {
        return this.callParent( arguments );
    },
    
    getBaseUrl: function( personId ){
        var me=this;
        var baseUrl = me.apiProperties.createUrl( me.apiProperties.getItemUrl('personEarlyAlert') );
        baseUrl = baseUrl.replace( '{personId}', personId );
        return baseUrl;
    },

    standardFailure: function(callbacks) {
        var me = this;
        return function(response) {
            me.apiProperties.handleError( response );
            if (callbacks)
            {
                callbacks.failure( response, callbacks.scope );
            }
        }
    },

    get: function( earlyAlertId, personId, callbacks ) {
        var me = this;
        var success = function( response ) {
            var r;
            if ( response.responseText ) {
                r = Ext.decode(response.responseText);
            }
            if ( callbacks ) {
                callbacks.success( r, callbacks.scope );
            }
        };
        var failure = me.standardFailure(callbacks);
        me.apiProperties.makeRequest({
            url: me.getBaseUrl(personId) + "/" + earlyAlertId,
            method: 'GET',
            successFunc: success,
            failureFunc: failure,
            scope: me
        });
    },

    getAll: function( personId, callbacks ){
        var me=this;
        var success = function( response, view ){
            var r = Ext.decode(response.responseText);
            if (r.rows.length > 0)
            {
                
                me.populateEarlyAlerts( r.rows,personId );
                
            }
            if (callbacks != null)
            {
                callbacks.success( r, callbacks.scope );
            }   
        };

        var failure = function( response ){
            me.apiProperties.handleError( response );           
            if (callbacks != null)
            {
                callbacks.failure( response, callbacks.scope );
            }
        };
        
        // clear the early alerts
        me.treeStore.setRootNode({
            text: 'EarlyAlerts',
            leaf: false,
            expanded: false
        });
        
        me.apiProperties.makeRequest({
            url: me.getBaseUrl(personId),
            method: 'GET',
            successFunc: success,
            failureFunc: failure,
            scope: me
        });
    },

    getAllEarlyAlertResponses: function( personId, earlyAlertId, callbacks ){
        var me=this;
        var url = "";
        var node = me.cleanResponses( earlyAlertId );
        var success = function( response, view ){
            var r = Ext.decode(response.responseText);
            me.resetEarlyAlertResponsesStore();
            if (r.rows.length > 0)
            {
               
                me.populateEarlyAlertResponses( node, r.rows );
                
            }
            if (callbacks != null)
            {
                callbacks.success( r, callbacks.scope );
            }   
        };

        var failure = function( response ){
            me.apiProperties.handleError( response );           
            if (callbacks != null)
            {
                callbacks.failure( response, callbacks.scope );
            }
        };
        
        url = me.getBaseUrl(personId);
        if (earlyAlertId != null && earlyAlertId != "")
        {
            me.apiProperties.makeRequest({
                url: url + '/' + earlyAlertId + '/response',
                method: 'GET',
                successFunc: success,
                failureFunc: failure,
                scope: me
            });         
        }
    },  

    getAllEarlyAlertCount: function( personId, earlyAlertId, callbacks ){
        var me=this;
        var url = "";
        //var node = me.cleanResponses( earlyAlertId );
        var success = function( response, view ){
            var r = Ext.decode(response.responseText);
            if (callbacks != null)
            {
                callbacks.success( personId, earlyAlertId, r.rows.length, callbacks.scope );
            } 
        };

        var failure = function( response ){
            
            me.apiProperties.handleError( response );           
            if (callbacks != null)
            {
                callbacks.failure( response, callbacks.scope );
            }
        };
        url = me.getBaseUrl(personId);
		
        if (earlyAlertId != null && earlyAlertId != "")
        {
            me.apiProperties.makeRequest({
                url: url + '/' + earlyAlertId + '/response',
                method: 'GET',
                successFunc: success,
                failureFunc: failure,
                scope: me
            });       
        }
    },    
    
    populateEarlyAlerts: function( records, personId ){
        var me=this;
        
        Ext.Array.each( records, function(record, index){
             // count = me.getAllEarlyAlertCount(personId, record.id);
            //record.iconCls='earlyAlertTreeIcon';
            record.leaf=false;
            record.nodeType='early alert';
            record.gridDisplayDetails=record.courseName + " - " + record.courseTitle ; 
            record.noOfResponses = 0;
            record.expanded=false;
        });

        me.treeStore.getRootNode().appendChild(records);
    },

    resetEarlyAlertResponsesStore: function() {
        var me = this;
        me.currentEarlyAlertResponsesGridStore.removeAll();
    },
    
    populateEarlyAlertResponses: function( node, records ){
        var me=this;
        
		var dataArray = [];
        Ext.Array.each( records, function(record, index){
            record.leaf=true;
            record.nodeType='early alert response';
            record.gridDisplayDetails=me.earlyAlertOutcomesStore.getById(record.earlyAlertOutcomeId).get('name');
			dataArray.push(record);
        });
		
		me.currentEarlyAlertResponsesGridStore.loadData(dataArray);

        node.appendChild(records);
    },
    
    cleanResponses: function( earlyAlertId ){
        var me=this;
        node = me.treeStore.getNodeById( earlyAlertId );
        node.removeAll();
        return node;
    },
    
    save: function( personId, jsonData, callbacks ){
        var me=this;
        var id=jsonData.id;
        var url = me.getBaseUrl(personId);
        var success = function( response, view ){
            var r = Ext.decode(response.responseText);
            callbacks.success( r, callbacks.scope );
        };

        var failure = function( response ){
            me.apiProperties.handleError( response );           
            callbacks.failure( response, callbacks.scope );
        };
        
        // save
        if (id=="")
        {
            // create
            me.apiProperties.makeRequest({
                url: url,
                method: 'POST',
                jsonData: jsonData,
                successFunc: success,
                failureFunc: failure,
                scope: me
            });             
        }else{
            // update
            me.apiProperties.makeRequest({
                url: url+"/"+id,
                method: 'PUT',
                jsonData: jsonData,
                successFunc: success,
                failureFunc: failure,
                scope: me
            }); 
        }       
    }
});
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
Ext.define('Ssp.service.EarlyAlertResponseService', {  
    extend: 'Ssp.service.AbstractService',   		
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	apiProperties: 'apiProperties'
    },
    initComponent: function() {
		return this.callParent( arguments );
    },
    
    getBaseUrl: function( personId, earlyAlertId ){
		var me=this;
		var baseUrl = me.apiProperties.createUrl( me.apiProperties.getItemUrl('personEarlyAlertResponse') );
		baseUrl = baseUrl.replace( '{personId}', personId );
		baseUrl = baseUrl.replace( '{earlyAlertId}', earlyAlertId );
		return baseUrl;
    },

    save: function( personId, earlyAlertId, jsonData, callbacks ){
    	var me=this;
    	var id = jsonData.id;
    	var success = function( response, view ){
    		var r = Ext.decode(response.responseText);
    		if (r.id.length > 0)
	    	{
		    	if (callbacks != null)
		    	{
		    		callbacks.success( r, callbacks.scope );
		    	}
	    	}
	    };

	    var failure = function( response ){
	    	me.apiProperties.handleError( response );	    	
	    	if (callbacks != null)
	    	{
	    		callbacks.failure( response, callbacks.scope );
	    	}
	    };
	    
		if ( id.length > 0 )
		{
			// editing
			this.apiProperties.makeRequest({
				url: me.getBaseUrl( personId, earlyAlertId )+"/"+id,
				method: 'PUT',
				jsonData: jsonData,
				successFunc: success,
				failureFunc: failure,
				scope: me
			});
			
		}else{
			// adding
			this.apiProperties.makeRequest({
				url: me.getBaseUrl( personId, earlyAlertId ),
				method: 'POST',
				jsonData: jsonData,
				successFunc: success,
				failureFunc: failure,
				scope: me
			});		
		}
    }  
});
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
Ext.define('Ssp.service.EarlyAlertReferralService', {  
    extend: 'Ssp.service.AbstractService',   		
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	apiProperties: 'apiProperties'
    },
    initComponent: function() {
		return this.callParent( arguments );
    },
    
    getBaseUrl: function(){
		var me=this;
		var baseUrl = me.apiProperties.createUrl( me.apiProperties.getItemUrl('earlyAlertReferral') );
		return baseUrl;
    },

    getAll: function( callbacks ){
    	var me=this;
	    var success = function( response, view ){
	    	var r = Ext.decode(response.responseText);
			callbacks.success( r, callbacks.scope );
	    };

	    var failure = function( response ){
	    	me.apiProperties.handleError( response );	    	
	    	callbacks.failure( response, callbacks.scope );
	    };
	    
		me.apiProperties.makeRequest({
			url: me.getBaseUrl(),
			method: 'GET',
			successFunc: success,
			failureFunc: failure,
			scope: me
		});
    },
    
    save: function( jsonData, callbacks ){
		var me=this;
		var url = me.getBaseUrl();
	    var success = function( response, view ){
	    	var r = Ext.decode(response.responseText);
			callbacks.success( r, callbacks.scope );
	    };

	    var failure = function( response ){
	    	me.apiProperties.handleError( response );	    	
	    	callbacks.failure( response, callbacks.scope );
	    };
		
		id = jsonData.id;

		// save
		if (id=="")
		{				
			me.apiProperties.makeRequest({
    			url: url,
    			method: 'POST',
    			jsonData: jsonData,
    			successFunc: success,
    			failureFunc: failure,
    			scope: me
    		});				
		}else{
			// update
    		me.apiProperties.makeRequest({
    			url: url+"/"+id,
    			method: 'PUT',
    			jsonData: jsonData,
    			successFunc: success,
    			failureFunc: failure,
    			scope: me
    		});	
		}
    }   
});
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
Ext.define('Ssp.service.JournalEntryService', {  
    extend: 'Ssp.service.AbstractService',   		
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	apiProperties: 'apiProperties'
    },
    initComponent: function() {
		return this.callParent( arguments );
    },
    
    getBaseUrl: function( personId ){
		var me=this;
		var baseUrl = me.apiProperties.createUrl( me.apiProperties.getItemUrl('personJournalEntry') );
		baseUrl = baseUrl.replace('{id}', personId );
		return baseUrl;
    },

    getAll: function( personId, callbacks ){
    	var me=this;
	    var success = function( response, view ){
	    	var r = Ext.decode(response.responseText);
	    	// filter the inactive items returned in the result
    		r.rows = me.superclass.filterInactiveChildren( r.rows );
	    	callbacks.success( r, callbacks.scope );
	    };

	    var failure = function( response ){
	    	me.apiProperties.handleError( response );	    	
	    	callbacks.failure( response, callbacks.scope );
	    };
	    
		me.apiProperties.makeRequest({
			url: me.getBaseUrl( personId ),
			method: 'GET',
			successFunc: success,
			failureFunc: failure,
			scope: me
		});
    },
    
    save: function( personId, jsonData, callbacks ){
		var me=this;
		var url = me.getBaseUrl( personId );
	    var success = function( response, view ){
	    	var r = Ext.decode(response.responseText);
			callbacks.success( r, callbacks.scope );
	    };

	    var failure = function( response ){
	    	me.apiProperties.handleError( response );	    	
	    	callbacks.failure( response, callbacks.scope );
	    };
		
		id = jsonData.id;

		// save
		if (id=="")
		{				
			me.apiProperties.makeRequest({
    			url: url,
    			method: 'POST',
    			jsonData: jsonData,
    			successFunc: success,
    			failureFunc: failure,
    			scope: me
    		});				
		}else{
			// update
    		me.apiProperties.makeRequest({
    			url: url+"/"+id,
    			method: 'PUT',
    			jsonData: jsonData,
    			successFunc: success,
    			failureFunc: failure,
    			scope: me
    		});	
		}
    },
    
    destroy: function( personId, id, callbacks ){
    	var me=this;
	    var success = function( response, view ){
	    	var r = Ext.decode(response.responseText);
			callbacks.success( r, id, callbacks.scope );
	    };

	    var failure = function( response ){
	    	me.apiProperties.handleError( response );	    	
	    	callbacks.failure( response, callbacks.scope );
	    };
	    
    	me.apiProperties.makeRequest({
   		   url: me.getBaseUrl( personId )+"/"+id,
   		   method: 'DELETE',
   		   successFunc: success,
   		   failureFunc: failure,
   		   scope: me
   	    }); 
    }
});
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
Ext.define('Ssp.service.PersonService', {  
    extend: 'Ssp.service.AbstractService',   		
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	apiProperties: 'apiProperties',
    	person: 'currentPerson',
    	sspConfig: 'sspConfig'
    },
    config: {
    	personUrl: null
    },
    initComponent: function() {
		return this.callParent( arguments );
    },
    
    getBaseUrl: function( id ){
		var me=this;
		var baseUrl = me.apiProperties.createUrl( me.apiProperties.getItemUrl('person') );
    	return baseUrl;
    },

    beforeGetRequestSuccess: function( response, callbacks ) {
        var me=this;
        var r;
        if ( response && response.responseText ) {
            r = me.superclass.filterInactiveChildren( [ Ext.decode(response.responseText) ] )[0];
        }
        callbacks.success( r, callbacks.scope );
    },

    beforeGetRequestFailure: function ( response, callbacks ) {
        var me=this;
        me.apiProperties.handleError( response );
        callbacks.failure( response, callbacks.scope );
    },

    newBeforeGetRequestSuccess: function(callbacks) {
        var me = this;
        return function(response) {
            me.beforeGetRequestSuccess(response, callbacks);
        }
    },

    newBeforeGetRequestFailure: function(callbacks) {
        var me = this;
        return function(response) {
            me.beforeGetRequestFailure(response, callbacks);
        }
    },

    get: function( id, callbacks ){
    	var me=this;
		// load the person to edit
		me.apiProperties.makeRequest({
			url: me.getBaseUrl()+'/'+id,
			method: 'GET',
			successFunc: me.newBeforeGetRequestSuccess(callbacks),
			failureFunc: me.newBeforeGetRequestFailure(callbacks),
			scope: me
		});
    },

    getBySchoolId: function( schoolId, callbacks ){
    	var me=this;
		// load the person to edit
		me.apiProperties.makeRequest({
			url: me.getBaseUrl()+'/bySchoolId/'+schoolId,
			method: 'GET',
            successFunc: me.newBeforeGetRequestSuccess(callbacks),
            failureFunc: me.newBeforeGetRequestFailure(callbacks),
            scope: me
		});
    },

    getLite: function ( id, callbacks ) {
        var me=this;
        me.apiProperties.makeRequest({
            url: me.getBaseUrl()+'/lite/'+id,
            method: 'GET',
            successFunc: me.newBeforeGetRequestSuccess(callbacks),
            failureFunc: me.newBeforeGetRequestFailure(callbacks),
            scope: me
        });
    },

    getSearchLite: function ( id, callbacks ) {
        var me=this;
        me.apiProperties.makeRequest({
            url: me.getBaseUrl()+'/searchlite/'+id,
            method: 'GET',
            successFunc: me.newBeforeGetRequestSuccess(callbacks),
            failureFunc: me.newBeforeGetRequestFailure(callbacks),
            scope: me
        });
    },
    
    save: function( jsonData, callbacks ){
    	var me=this;
    	var id=jsonData.id;
        var url = me.getBaseUrl();

	    var success = function( response, view ){
			var r = response.responseText ? Ext.decode(response.responseText) : null;
			if ( callbacks.statusCode && callbacks.statusCode[response.status] ) {
				callbacks.statusCode[response.status](r, callbacks.scope);
			} else {
				callbacks.success(r, callbacks.scope);
			}
	    };

	    var failure = function( response ){
	    	var r;
			// Before statusCode callbacks were introduced, "legacy" failure
			// callbacks expected unparsed responses, whereas legacy success
			// callbacks expected parsed responses. Also, legacy failure
			// callbacks all assumed the PersonService handled error dialog
			// rendering. If a statusCode-specific callback exists, though,
			// we assume the view wants to perform very specific work on that
			// particular error type so we skip the dialog rendering here.
			// (Dialog rendering is the default behavior in
			// me.apiProperties.handleError( response );)
			if ( callbacks.statusCode[response.status] ) {
				callbacks.statusCode[response.status](response, callbacks.scope);
			} else {
				me.apiProperties.handleError( response );
				callbacks.failure(response, callbacks.scope);
			}
	    };

    	// save the person
		if (id=="")
		{
			// create
			me.apiProperties.makeRequest({
    			url: url,
    			method: 'POST',
    			jsonData: jsonData,
    			successFunc: success,
    			failureFunc: failure,
    			scope: me
    		});				
		}else{
			// update
    		me.apiProperties.makeRequest({
    			url: url+"/"+id,
    			method: 'PUT',
    			jsonData: jsonData,
    			successFunc: success,
    			failureFunc: failure,
    			scope: me
    		});	
		}   	
    },
    
    destroy: function( id, callbacks ){
    	var me=this;
	    var success = function( response, view ){
	    	var r = Ext.decode(response.responseText);
			callbacks.success( r, callbacks.scope );
	    };

	    var failure = function( response ){
	    	me.apiProperties.handleError( response );	    	
	    	callbacks.failure( response, callbacks.scope );
	    };
	    
    	me.apiProperties.makeRequest({
   		   url: me.getBaseUrl()+"/"+id,
   		   method: 'DELETE',
   		   successFunc: success,
   		   failureFunc: failure,
   		   scope: me
   	    }); 
    }
});
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
Ext.define('Ssp.service.PlacementService', {  
    extend: 'Ssp.service.AbstractService',   		
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	apiProperties: 'apiProperties',
        store: 'placementStore'
    },
    initComponent: function() {
		return this.callParent( arguments );
    },

    getBaseUrl: function( id ){
		var me=this;
		var baseUrl = me.apiProperties.createUrl( me.apiProperties.getItemUrl('placement') );
    	baseUrl = baseUrl.replace('{id}', id);
    	return baseUrl;
    },
    
    getAll: function( personId, callbacks ){
		var me=this;
		var url = me.getBaseUrl( personId );
	    var success = function( response, view ){
	    	var r = Ext.decode(response.responseText);
			callbacks.success( r, callbacks.scope );
	    };

	    var failure = function( response ){
	    	me.apiProperties.handleError( response );	    	
	    	callbacks.failure( response, callbacks.scope );
	    };
	    
		me.apiProperties.makeRequest({
			url: url,
			method: 'GET',
			successFunc: success,
			failureFunc: failure,
			scope: me
		});    	
    }
});
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
Ext.define('Ssp.service.ProgramStatusService', {  
    extend: 'Ssp.service.AbstractService',   		
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	apiProperties: 'apiProperties',
    	store: 'programStatusesStore'
    },
    initComponent: function() {
		return this.callParent( arguments );
    },
    
    getBaseUrl: function(){
		var me=this;
		var baseUrl = me.apiProperties.createUrl( me.apiProperties.getItemUrl('programStatus') );
    	return baseUrl;
    },

    getAll: function( callbacks ){
    	var me=this;
    	var success = function( response, view ){
    		var r = Ext.decode(response.responseText);
    		if (r.rows.length > 0)
	    	{
    		    me.store.removeAll();
    			me.store.loadData(r.rows);
	    	}
	    	if (callbacks != null)
	    	{
	    		callbacks.success( r, callbacks.scope );
	    	}	
	    };

	    var failure = function( response ){
	    	me.apiProperties.handleError( response );	    	
	    	if (callbacks != null)
	    	{
	    		callbacks.failure( response, callbacks.scope );
	    	}
	    };
	    	    
		me.apiProperties.makeRequest({
			url: me.getBaseUrl(),
			method: 'GET',
			successFunc: success,
			failureFunc: failure,
			scope: me
		});
    }
});
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
Ext.define('Ssp.service.ReferralSourceService', {  
    extend: 'Ssp.service.AbstractService',   		
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	apiProperties: 'apiProperties'
    },
    initComponent: function() {
		return this.callParent( arguments );
    },
    
    getBaseUrl: function(){
		var me=this;
		var baseUrl = me.apiProperties.createUrl( me.apiProperties.getItemUrl('referralSource') );
		return baseUrl;
    },

    getAll: function( callbacks ){
    	var me=this;
	    var success = function( response, view ){
	    	var r = Ext.decode(response.responseText);
			callbacks.success( r, callbacks.scope );
	    };

	    var failure = function( response ){
	    	me.apiProperties.handleError( response );	    	
	    	callbacks.failure( response, callbacks.scope );
	    };
	    
		me.apiProperties.makeRequest({
			url: me.getBaseUrl(),
			method: 'GET',
			successFunc: success,
			failureFunc: failure,
			scope: me
		});
    },
    
    save: function( jsonData, callbacks ){
		var me=this;
		var url = me.getBaseUrl();
	    var success = function( response, view ){
	    	var r = Ext.decode(response.responseText);
			callbacks.success( r, callbacks.scope );
	    };

	    var failure = function( response ){
	    	me.apiProperties.handleError( response );	    	
	    	callbacks.failure( response, callbacks.scope );
	    };
		
		id = jsonData.id;

		// save
		if (id=="")
		{				
			me.apiProperties.makeRequest({
    			url: url,
    			method: 'POST',
    			jsonData: jsonData,
    			successFunc: success,
    			failureFunc: failure,
    			scope: me
    		});				
		}else{
			// update
    		me.apiProperties.makeRequest({
    			url: url+"/"+id,
    			method: 'PUT',
    			jsonData: jsonData,
    			successFunc: success,
    			failureFunc: failure,
    			scope: me
    		});	
		}
    }   
});
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
Ext.define('Ssp.service.SearchService', {  
    extend: 'Ssp.service.AbstractService',   		
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	apiProperties: 'apiProperties',
    	store: 'searchStore'
    },
    initComponent: function() {
		return this.callParent( arguments );
    },
    
    getBaseUrl: function(){
		var me=this;
		var baseUrl = me.apiProperties.createUrl( me.apiProperties.getItemUrl('personSearch') );
    	return baseUrl;
    },

	searchWithParams: function(params, callbacks) {
		var me=this;

		me.store.removeAll();

		// Set params in the url for Search Store
		// because the params need to be applied prior to load and not in a params
		// definition from the load method or the paging
		// toolbar applied to the SearchView will not
		// apply the params when using next or previous page

		queryStr = "";
		for (var paramName in params) {
			// TODO url encoding?
			if ( queryStr ) {
				queryStr += "&";
			}
			queryStr += paramName + "=" + params[paramName];
		}
		if ( !("sort" in params) ) {
			if ( queryStr ) {
				queryStr += "&";
			}
			queryStr += "sort=lastName";
		}

		Ext.apply(me.store.getProxy(),{url: me.getBaseUrl()+'?'+queryStr});

		me.store.load({
			params: {

			},
			callback: function(records, operation, success) {
				if (success)
				{
					if (callbacks != null)
					{
						callbacks.success( records, callbacks.scope );
					}
				}else{
					if (callbacks != null)
					{
						callbacks.failure( records, callbacks.scope );
					}
				}
			},
			scope: me
		});
	},

    search: function( searchTerm, outsideCaseload, callbacks ){
    	var me = this;
		me.searchWithParams({
			searchTerm: searchTerm,
			outsideCaseload: outsideCaseload
		}, callbacks);
    }
});
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
Ext.define('Ssp.service.SpecialServiceGroupService', {  
    extend: 'Ssp.service.AbstractService',   		
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	apiProperties: 'apiProperties'
    },
    initComponent: function() {
		return this.callParent( arguments );
    },
    
    getBaseUrl: function(){
		var me=this;
		var baseUrl = me.apiProperties.createUrl( me.apiProperties.getItemUrl('specialServiceGroup') );
		return baseUrl;
    },

    getAll: function( callbacks ){
    	var me=this;
	    var success = function( response, view ){
	    	var r = Ext.decode(response.responseText);
			callbacks.success( r, callbacks.scope );
	    };

	    var failure = function( response ){
	    	me.apiProperties.handleError( response );	    	
	    	callbacks.failure( response, callbacks.scope );
	    };
	    
		me.apiProperties.makeRequest({
			url: me.getBaseUrl(),
			method: 'GET',
			successFunc: success,
			failureFunc: failure,
			scope: me
		});
    },
    
    save: function( jsonData, callbacks ){
		var me=this;
		var url = me.getBaseUrl();
	    var success = function( response, view ){
	    	var r = Ext.decode(response.responseText);
			callbacks.success( r, callbacks.scope );
	    };

	    var failure = function( response ){
	    	me.apiProperties.handleError( response );	    	
	    	callbacks.failure( response, callbacks.scope );
	    };
		
		id = jsonData.id;

		// save
		if (id=="")
		{				
			me.apiProperties.makeRequest({
    			url: url,
    			method: 'POST',
    			jsonData: jsonData,
    			successFunc: success,
    			failureFunc: failure,
    			scope: me
    		});				
		}else{
			// update
    		me.apiProperties.makeRequest({
    			url: url+"/"+id,
    			method: 'PUT',
    			jsonData: jsonData,
    			successFunc: success,
    			failureFunc: failure,
    			scope: me
    		});	
		}
    }   
});
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
Ext.define('Ssp.service.StudentIntakeService', {  
    extend: 'Ssp.service.AbstractService',   		
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	apiProperties: 'apiProperties'
    },
    initComponent: function() {
		return this.callParent( arguments );
    },

    getBaseUrl: function(){
		var me=this;
		var baseUrl = me.apiProperties.createUrl( me.apiProperties.getItemUrl('studentIntakeTool') );
    	return baseUrl;
    },
    
    get: function( personId, callbacks ){
		var me=this;
		var url = me.getBaseUrl();
	    var success = function( response, view ){
	    	var r = Ext.decode(response.responseText);
	    	// filter inactive items
    		r.personEducationLevels = me.superclass.filterInactiveChildren(r.personEducationLevels);
    		r.personFundingSources = me.superclass.filterInactiveChildren(r.personFundingSources);
    		r.personChallenges = me.superclass.filterInactiveChildren(r.personChallenges);
    		callbacks.success( r, callbacks.scope );
	    };

	    var failure = function( response ){
	    	me.apiProperties.handleError( response );	    	
	    	callbacks.failure( response, callbacks.scope );
	    };
	    
		me.apiProperties.makeRequest({
			url: url+'/'+personId,
			method: 'GET',
			successFunc: success,
			failureFunc: failure,
			scope: me
		});    	
    },
    
    save: function( personId, jsonData, callbacks ){
		var me=this;
		var url = me.getBaseUrl();
	    var success = function( response, view ){
	    	var r = Ext.decode(response.responseText);
			callbacks.success( r, callbacks.scope );
	    };

	    var failure = function( response ){
	    	me.apiProperties.handleError( response );	    	
	    	callbacks.failure( response, callbacks.scope );
	    };
		
		// save
		if (personId=="")
		{				
			me.apiProperties.makeRequest({
    			url: url,
    			method: 'POST',
    			jsonData: jsonData,
    			successFunc: success,
    			failureFunc: failure,
    			scope: me
    		});				
		}else{
			// update
    		me.apiProperties.makeRequest({
    			url: url+"/"+personId,
    			method: 'PUT',
    			jsonData: jsonData,
    			successFunc: success,
    			failureFunc: failure,
    			scope: me
    		});	
		}	
    }
});
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
Ext.define('Ssp.service.TranscriptService', {  
    extend: 'Ssp.service.AbstractService',   		
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	apiProperties: 'apiProperties'
    },
    initComponent: function() {
		return this.callParent( arguments );
    },

    getBaseUrl: function( id ){
		var me=this;
		var baseUrl = me.apiProperties.createUrl( me.apiProperties.getItemUrl('personTranscript') );
    	baseUrl = baseUrl.replace('{id}', id);
    	return baseUrl;
    },

    getSummary: function ( personId, callbacks ) {
        var me = this;
        me.doGet(personId, callbacks,  me.getBaseUrl( personId ) + "/summary" );
    },
    
    getFull: function( personId, callbacks ){
        var me = this;
		me.doGet(personId, callbacks,  me.getBaseUrl( personId ) + "/full" );
    },

    doGet: function( personId, callbacks, url ) {
        var me=this;
        var success = function( response ){
            var r = Ext.decode(response.responseText);
            callbacks.success( r, callbacks.scope );
        };

        var failure = function( response ){
            me.apiProperties.handleError( response );
            callbacks.failure( response, callbacks.scope );
        };

        me.apiProperties.makeRequest({
            url: url,
            method: 'GET',
            successFunc: success,
            failureFunc: failure,
            scope: me
        });
    }
});
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
Ext.define('Ssp.service.AccommodationService', {
    extend: 'Ssp.service.AbstractService',   		
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	apiProperties: 'apiProperties'
    },
    initComponent: function() {
		return this.callParent( arguments );
    },

    getBaseUrl: function(){
		var me=this;
		var baseUrl = me.apiProperties.createUrl( me.apiProperties.getItemUrl('accommodationTool') );
    	return baseUrl;
    },
    
    get: function( personId, callbacks ){
		var me=this;
		var url = me.getBaseUrl();
	    var success = function( response, view ){
	    	var r = Ext.decode(response.responseText);
	    	// filter inactive items
    		r.personDisabilityAgencies = me.superclass.filterInactiveChildren(r.personDisabilityAgencies);
    		r.personDisabilityTypes = me.superclass.filterInactiveChildren(r.personDisabilityTypes);
    		r.personDisabilityAccommodations = me.superclass.filterInactiveChildren(r.personDisabilityAccommodations);
    		callbacks.success( r, callbacks.scope );
	    };

	    var failure = function( response ){
	    	me.apiProperties.handleError( response );	    	
	    	callbacks.failure( response, callbacks.scope );
	    };
	    
		me.apiProperties.makeRequest({
			url: url+'/'+personId,
			method: 'GET',
			successFunc: success,
			failureFunc: failure,
			scope: me
		});    	
    },
    
    save: function( personId, jsonData, callbacks ){
		var me=this;
		var url = me.getBaseUrl();
	    var success = function( response, view ){
	    	var r = Ext.decode(response.responseText);
			callbacks.success( r, callbacks.scope );
	    };

	    var failure = function( response ){
	    	me.apiProperties.handleError( response );	    	
	    	callbacks.failure( response, callbacks.scope );
	    };
		
		// save
		if (personId=="")
		{				
			me.apiProperties.makeRequest({
    			url: url,
    			method: 'POST',
    			jsonData: jsonData,
    			successFunc: success,
    			failureFunc: failure,
    			scope: me
    		});				
		}else{
			// update
    		me.apiProperties.makeRequest({
    			url: url+"/"+personId,
    			method: 'PUT',
    			jsonData: jsonData,
    			successFunc: success,
    			failureFunc: failure,
    			scope: me
    		});	
		}	
    }
});
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
Ext.define('Ssp.service.PersonProgramStatusService', {  
    extend: 'Ssp.service.AbstractService',   		
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	apiProperties: 'apiProperties',
    	formUtils: 'formRendererUtils'
    },
    initComponent: function() {
		return this.callParent( arguments );
    },
    
    getBaseUrl: function( personId ){
		var me=this;
		var baseUrl = me.apiProperties.createUrl( me.apiProperties.getItemUrl('personProgramStatus') );
    	baseUrl = baseUrl.replace("{id}",personId);
		return baseUrl;
    },

    save: function( personId, jsonData, callbacks ){
		var me=this;
		var url = me.getBaseUrl( personId );
	    var success = function( response, view ){
	    	var r = Ext.decode(response.responseText);
	    	callbacks.success( r, callbacks.scope );
	    };

	    var failure = function( response ){
	    	me.apiProperties.handleError( response );	    	
	    	callbacks.failure( response, callbacks.scope );
	    };
		
    	if (personId != "")
    	{
    		id = jsonData.id;

    		// save the program status
    		if (id=="")
    		{
    			// Fix dates for GMT offset to UTC
    			jsonData.effectiveDate = me.formUtils.fixDateOffset( jsonData.effectiveDate );
	
    			me.apiProperties.makeRequest({
        			url: url,
        			method: 'POST',
        			jsonData: jsonData,
        			successFunc: success,
        			failureFunc: failure,
        			scope: me
        		});		
    		}else{
    			
    			// update
        		me.apiProperties.makeRequest({
        			url: url+"/"+id,
        			method: 'PUT',
        			jsonData: jsonData,
        			successFunc: success,
        			failureFunc: failure,
        			scope: me
        		});	
    		}     		
    	}else{
    		Ext.Msg.alert('SSP Error', 'Error determining student to which to save a Program Status. Unable to save Program Status. See your system administrator for assistance.');
    	}  	
    }   
});
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
Ext.define('Ssp.controller.ApplicationEventsController', {
	extend: 'Ext.Base',
	config: {
		app: null
	},
	
	constructor: function(config){
		this.initConfig(config);
		return this.callParent(arguments);
	},
	
	setApplication: function(app){
		this.app = app;
	},	
	
	getApplication: function(){
		return this.app;
	},
	
	/**
	 * @args
	 *   eventName - the name of an event to listen against
	 *   callBackFunc - the function to run when the event occurs
	 *   scope - the scope to run the function under
	 */
	assignEvent: function( args ){
		if ( !this.getApplication().hasListener(args.eventName) )
		{
			this.getApplication().addListener(args.eventName, args.callBackFunc, args.scope);
		}
	},

	/**
	 * @args
	 *   eventName - the name of an event to listen against
	 *   callBackFunc - the function to run when the event occurs
	 *   scope - the scope to run the function under
	 */
	removeEvent: function( args ){
		if ( this.getApplication().hasListener( args.eventName ))
		{
			this.getApplication().removeListener( args.eventName, args.callBackFunc, args.scope );
		}
	}
});
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
Ext.define('Ssp.controller.AdminViewController', {
	extend: 'Deft.mvc.ViewController',    
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	campusesStore: 'campusesStore',
    	campusServicesStore: 'campusServicesStore',
    	challengeCategoriesStore: 'challengeCategoriesStore',
        challengesStore: 'challengesStore',
    	challengeReferralsStore: 'challengeReferralsStore',
    	childCareArrangementsStore: 'childCareArrangementsStore',
    	citizenshipsStore: 'citizenshipsStore',
    	confidentialityLevelsStore: 'confidentialityLevelsStore',
    	disabilityAccommodationsStore: 'disabilityAccommodationsStore',
    	disabilityAgenciesStore: 'disabilityAgenciesStore',
    	disabilityStatusesStore: 'disabilityStatusesStore',
    	disabilityTypesStore: 'disabilityTypesStore',
		earlyAlertOutcomesStore: 'earlyAlertOutcomesStore',
		earlyAlertOutreachesStore: 'earlyAlertOutreachesStore',
		earlyAlertReasonsStore: 'earlyAlertReasonsStore',
		earlyAlertReferralsStore: 'earlyAlertReferralsStore',
		earlyAlertSuggestionsStore: 'earlyAlertSuggestionsStore',
    	educationGoalsStore: 'educationGoalsStore',
    	educationLevelsStore: 'educationLevelsStore',
    	employmentShiftsStore: 'employmentShiftsStore',
    	ethnicitiesStore: 'ethnicitiesStore',
    	formUtils: 'formRendererUtils',
    	fundingSourcesStore: 'fundingSourcesStore',
    	gendersStore: 'gendersStore',
        journalSourcesStore: 'journalSourcesStore',
        journalStepsStore: 'journalStepsStore',
        journalTracksStore: 'journalTracksStore',
        lassisStore: 'lassisStore',
    	maritalStatusesStore: 'maritalStatusesStore',
    	militaryAffiliationsStore: 'militaryAffiliationsStore',
    	personalityTypesStore: 'personalityTypesStore',
    	programStatusChangeReasonsStore: 'programStatusChangeReasonsStore',
    	referralSourcesStore: 'referralSourcesStore',
    	serviceReasonsStore: 'serviceReasonsStore',
    	specialServiceGroupsStore: 'specialServiceGroupsStore',
        statesStore: 'statesStore',
        studentStatusesStore: 'studentStatusesStore',
        studentTypesStore: 'studentTypesStore',
    	veteranStatusesStore: 'veteranStatusesStore'
    },

    control: {
		view: {
			itemclick: 'onItemClick'
		}
		
	},
	
	init: function() {
		return this.callParent(arguments);
    }, 
    
	/*
	 * Handle selecting an item in the tree grid
	 */
	onItemClick: function(view,record,item,index,eventObj) {
		var storeName = "";
		var columns = null;
		if (record.raw != undefined )
		{
			if ( record.raw.form != "")
			{
				if (record.raw.store != "")
				{
					storeName = record.raw.store;
				}
				if (record.raw.columns != null)
				{
					columns = record.raw.columns;
				}
				this.loadAdmin( record.raw.title, record.raw.form, storeName, columns );         
			}
		}
	},

	loadAdmin: function( title ,form, storeName, columns ) {
		var me=this;
		var comp = this.formUtils.loadDisplay('adminforms',form, true, {});
		var store = null;
		
		// set a store if defined
		if (storeName != "")
		{
			store = me[storeName+'Store'];
			// If the store was set, then modify
			// the component to use the store
			if (store != null)
			{
				// pass the columns for editing
				if (columns != null)
				{
					// comp.reconfigure(store, columns); // ,columns
					me.formUtils.reconfigureGridPanel(comp, store, columns);
				}else{
					// comp.reconfigure(store);
					me.formUtils.reconfigureGridPanel(comp, store);
				}
				
				comp.getStore().load();
			}
		}
		
		if (Ext.isFunction(comp.setTitle))
			comp.setTitle(title + ' Admin');
	}
});
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
Ext.define('Ssp.controller.MainViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	appEventsController: 'appEventsController',
        formUtils: 'formRendererUtils'
    },
    config: {
    	personButtonsVisible: true
    },
    control: {
    	view: {
    		add: 'setListeners'
    	},
    	
    	'studentViewNav': {
			click: 'onStudentRecordViewNavClick'
		},

		'adminViewNav': {
			click: 'onAdminViewNavClick'
		}
	},
	
	init: function() {
		this.displayStudentRecordView();
		
		return this.callParent(arguments);
    },
    
    setListeners: function(container, component, index, obj){
    	/**
		 * TODO: Figure out a better workaround than this for loading
		 * the listener that allows the display to be reset after
		 * saving the caseload assignment. This works because the Profile
		 * tool is dynamically added to the tools display after the interface
		 * is rendered. This event has to be assigned to the application after
		 * the application's onLaunch method has already fired.
		 * The issue with using the profile instance is that there may later
		 * be a requirement to load a different tool than the Profile first in the stack.
		 */
		if(component instanceof Ext.ClassManager.get('Ssp.view.tools.profile.Profile'))
		{
	       this.appEventsController.assignEvent({eventName: 'displayStudentRecordView', callBackFunc: this.onDisplayStudentRecordView, scope: this});			
		}
    },
    
    destroy: function() {
	   	this.appEventsController.removeEvent({eventName: 'displayStudentRecordView', callBackFunc: this.onDisplayStudentRecordView, scope: this});

        return this.callParent( arguments );
    },
    
    onDisplayStudentRecordView: function(){
    	this.displayStudentRecordView();
    },
    
    onStudentRecordViewNavClick: function(obj, eObj){ 
		this.displayStudentRecordView();
	},
	
	onAdminViewNavClick: function(obj, eObj){ 
		this.displayAdminView();
	},
    
    displayStudentRecordView: function(){
    	var me=this;
    	var mainView = Ext.ComponentQuery.query('mainview')[0];
    	var arrViewItems;
    	
    	if (mainView.items.length > 0)
		{
			mainView.removeAll();
		}
		
		arrViewItems = [{xtype:'search',flex: 2},
					    {xtype: 'studentrecord', flex: 4}];
		
		mainView.add( arrViewItems );
    },
    
    displayAdminView: function() { 
    	var me=this;
    	var mainView = Ext.ComponentQuery.query('mainview')[0];
    	var arrViewItems;	
    	
    	if (mainView.items.length > 0)
		{
			mainView.removeAll();
		}
		
		arrViewItems = [{xtype:'adminmain',
					     items:[{xtype: 'admintreemenu', region:'west' ,  width: 275}, 
					            {xtype: 'adminforms', region:'center' }],
					     flex:5}];
		
		mainView.add( arrViewItems );
    }
});
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
Ext.define('Ssp.controller.SearchViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	apiProperties: 'apiProperties',
        appEventsController: 'appEventsController',
        authenticatedPerson: 'authenticatedPerson',
        caseloadFilterCriteria: 'caseloadFilterCriteria',
        caseloadStore: 'caseloadStore',
        caseloadService: 'caseloadService',
        columnRendererUtils: 'columnRendererUtils',
        formUtils: 'formRendererUtils',
        person: 'currentPerson',
        personLite: 'personLite',
        personService: 'personService',
        personProgramStatusService: 'personProgramStatusService',
        preferences: 'preferences',
        programStatusesStore: 'programStatusesStore',
        programStatusService: 'programStatusService',
        searchCriteria: 'searchCriteria',
        searchService: 'searchService',
        searchStore: 'searchStore',
        sspConfig: 'sspConfig'
    },
    
    control: {
    	view: {
    		selectionchange: 'onSelectionChange',
			viewready: 'onViewReady'
    	},    	
 
    	caseloadStatusCombo: {
    		selector: '#caseloadStatusCombo',
    		listeners: {
    			select: 'onCaseloadStatusComboSelect'
    		} 
    	},

    	'retrieveCaseloadButton': {
    		click: 'onRetrieveCaseloadClick'
    	},    	
    	
    	searchGridPager: '#searchGridPager',
    	searchText: {
    		selector: '#searchText',
		    listeners:{   
		        keypress: 'onSearchKeyPress'  
		    } 
    	},
    	
    	searchCaseloadCheck: '#searchCaseloadCheck',
    	searchBar: '#searchBar',
    	caseloadBar: '#caseloadBar',

    	'searchButton': {
    		click: 'onSearchClick'
    	},
    	
    	'displaySearchBarButton': {
    		click: 'onDisplaySearchBarClick'
    	},
    	
    	'displayCaseloadBarButton': {
    		click: 'onDisplayCaseloadBarClick'
    	},

    	addPersonButton: {
    		selector: '#addPersonButton',
    		listeners: {
    			click: 'onAddPersonClick'
    		}
    	},
    	
    	editPersonButton: {
    		selector: '#editPersonButton',
    		listeners: {
    			click: 'onEditPersonClick'
    		}
    	},
    	
    	deletePersonButton: {
    		selector: '#deletePersonButton',
    		listeners: {
    			click: 'onDeletePersonClick'
    		}
    	},
    	
		'setTransitionStatusButton': {
			click: 'onSetProgramStatusClick'
		},
		
		'setNonParticipatingStatusButton': {
			click: 'onSetProgramStatusClick'
		},
		
		'setNoShowStatusButton': {
			click: 'onSetProgramStatusClick'
		},
		
		'setActiveStatusButton': {
			click: 'onSetProgramStatusClick'
		}
    },
    
	init: function() {
		var me=this;    	

	   	// ensure the selected person is not loaded twice
		// once on load and once on selection
	   	me.personLite.set('id','');

    	// set the search results to the stored
	   	// search results
		me.getSearchText().setValue( me.searchCriteria.get('searchTerm') );
	   	me.getSearchCaseloadCheck().setValue( !me.searchCriteria.get('outsideCaseload') );
		
		
		return me.callParent(arguments);
    },
    
	onSelectionChange: function(selModel,records,eOpts){ 
		var me=this;
		var person = new Ssp.model.Person();
		// clear the person record
		me.person.data = person.data;
		if (records.length > 0)
		{
			if (records[0].data.id != null)
			{
				me.personLite.set('id', records[0].data.id);
			}else{
				me.personLite.set('id', records[0].data.personId);
			}
			me.personLite.set('firstName', records[0].data.firstName);
			me.personLite.set('middleName', records[0].data.middleName);
			me.personLite.set('lastName', records[0].data.lastName);
			me.personLite.set('displayFullName', records[0].data.firstName + ' ' + records[0].data.lastName);
			me.appEventsController.getApplication().fireEvent('loadPerson');			
		}
	},

	onViewReady: function(comp, eobj){
		var me=this;
        me.appEventsController.assignEvent({eventName: 'collapseSearch', callBackFunc: me.onCollapseSearch, scope: me});
		me.appEventsController.assignEvent({eventName: 'collapseStudentRecord', callBackFunc: me.onCollapseStudentRecord, scope: me});
	   	me.appEventsController.assignEvent({eventName: 'expandStudentRecord', callBackFunc: me.onExpandStudentRecord, scope: me});
	   	me.appEventsController.assignEvent({eventName: 'setNonParticipatingProgramStatusComplete', callBackFunc: me.onSetNonParticipatingProgramStatusComplete, scope: me});
	
	   	me.initSearchGrid();

	   	// load program statuses
		me.getProgramStatuses();	
	},

    destroy: function() {
    	var me=this;
        me.appEventsController.removeEvent({eventName: 'collapseSearch', callBackFunc: me.onCollapseSearch, scope: me});
    	me.appEventsController.removeEvent({eventName: 'collapseStudentRecord', callBackFunc: me.onCollapseStudentRecord, scope: me});
	   	me.appEventsController.removeEvent({eventName: 'expandStudentRecord', callBackFunc: me.onExpandStudentRecord, scope: me});
	   	me.appEventsController.removeEvent({eventName: 'retrieveCaseload', callBackFunc: me.onRetrieveCaseload, scope: me});
	   	return me.callParent( arguments );
    },
    
    initSearchGrid: function(){
	   	var me=this;
    	// load search if preference is set
	   	if ( me.preferences.get('SEARCH_GRID_VIEW_TYPE')==0 )
		{
			me.search();
			me.displaySearchBar();
		}else{
			// otherwise load caseload if caseload is
			// available to user. this will ensure
			// caseload will load on first entrance into
			// the program
			if ( me.authenticatedPerson.hasAccess('CASELOAD_FILTERS') )
			{
				me.preferences.set('SEARCH_GRID_VIEW_TYPE',1);
				// default caseload to Active students if no program status is defined
				if ( me.caseloadFilterCriteria.get('programStatusId') == "")
				{
					me.caseloadFilterCriteria.set('programStatusId', Ssp.util.Constants.ACTIVE_PROGRAM_STATUS_ID );
				}
				me.getCaseload();
				me.displayCaseloadBar();
			}else{
				me.search();
				me.displaySearchBar();
			}
		}	
    },
    
    selectFirstItem: function(){
    	var me=this;
    	if ( me.getView().getStore().getCount() > 0)
    	{
        	me.getView().getSelectionModel().select(0);
    	}else{
    		// if no record is available, then cast event
    		// to reset the profile tool fields
    		me.personLite.set('id', "");
    		me.appEventsController.getApplication().fireEvent('loadPerson');
    	}
    	
    	me.refreshPagingToolBar();    	
    },
    
    onCollapseStudentRecord: function(){
	},
	
	onExpandStudentRecord: function(){
	},  

	setGridView: function( view ){
		var me=this;
		me.applyColumns();
	},
	
	onCollapseSearch: function() {
		var searchView = Ext.ComponentQuery.query('search')[0];
		searchView.collapse();
	},
	
	onDisplaySearchBarClick: function( button ){
		this.displaySearchBar();
	},
	
	onDisplayCaseloadBarClick: function( button ){
		this.displayCaseloadBar();
	},
	
	displaySearchBar: function(){
		var me=this;
		me.preferences.set('SEARCH_GRID_VIEW_TYPE',0);
		me.getCaseloadBar().hide();
		me.getSearchBar().show();
		me.setGridView();
	},

	displayCaseloadBar: function(){
		var me=this;
		me.preferences.set('SEARCH_GRID_VIEW_TYPE',1);
		me.getCaseloadBar().show();
		me.getSearchBar().hide();
		me.setGridView();
	},
	
	applyColumns: function(){
		var me=this;
		var grid = me.getView();
		var store;
		var sortableColumns = false;
		var studentIdAlias = me.sspConfig.get('studentIdAlias');
		if ( me.preferences.get('SEARCH_GRID_VIEW_TYPE')==1 )
		{
			store = me.caseloadStore;
			columns = [
    	              { sortable: sortableColumns, header: 'First', dataIndex: 'firstName', flex: 1 },		        
    	              { sortable: sortableColumns, header: 'MI', dataIndex: 'middleName', flex: .2},
    	              { sortable: sortableColumns, header: 'Last', dataIndex: 'lastName', flex: 1},
    	              { sortable: sortableColumns, header: 'Type', dataIndex: 'studentType', renderer: me.columnRendererUtils.renderStudentType, flex: .2},
    	              { sortable: sortableColumns, header: studentIdAlias, dataIndex: 'schoolId', flex: 1},
    	              { sortable: sortableColumns, header: 'Alerts', dataIndex: 'numberOfEarlyAlerts', flex: .2}
    	              ];
		}else{
			store = me.searchStore;
			columns = [
    	              /* { header: "Photo", dataIndex: 'photoUrl', renderer: this.columnRendererUtils.renderPhotoIcon, flex: 50 }, */		        
    	              /*{ sortable: sortableColumns, header: 'Student', dataIndex: 'lastName', renderer: me.columnRendererUtils.renderSearchStudentName, flex: .25 },*/
    	              { sortable: sortableColumns, header: 'First', dataIndex: 'firstName', flex: .2},		        
    	              { sortable: sortableColumns, header: 'MI', dataIndex: 'middleName', flex: .05},
    	              { sortable: sortableColumns, header: 'Last', dataIndex: 'lastName', flex: .2},
    	              { sortable: sortableColumns, header: 'Coach', dataIndex: 'coach', renderer: me.columnRendererUtils.renderCoachName, flex: .25 },
    	              { sortable: sortableColumns, header: studentIdAlias, dataIndex: 'schoolId', flex: .15},
    	              { sortable: sortableColumns, header: 'Status', dataIndex: 'currentProgramStatusName', flex: .15}
    	              ];		
		}

		grid.getView().getRowClass = function(row, index)
	    {
			var cls = "";
			var today = Ext.Date.clearTime( new Date() );
			var tomorrow = Ext.Date.clearTime( new Date() );
			tomorrow.setDate( today.getDate() + 1 );
			// set apppointment date color first. early alert will over-ride appointment color.
			if (row.get('currentAppointmentStartTime') != null)
			{
				if ( me.formUtils.dateWithin(today, tomorrow, row.get('currentAppointmentStartTime') ) )
				{
					cls = 'caseload-appointment-indicator'
				}
			}
			
			// early alert color will over-ride the appointment date
			if ( row.get('numberOfEarlyAlerts') != null)
			{
				if (row.get('numberOfEarlyAlerts') > 0)
				{
					cls = 'caseload-early-alert-indicator'
				}				
			}

			return cls;
	    };  		
		
		me.formUtils.reconfigureGridPanel(grid, store, columns);
	},

    onAddPersonClick: function( button ){
    	var me=this;
    	me.onAddPerson();
	},
	
	onEditPersonClick: function( button ){
    	var me=this;
    	me.onEditPerson();
	},

	onDeletePersonClick: function( button ){
    	var me=this;
    	me.onDeletePerson();
	},	
	
	onAddPerson: function(){
		var me=this;
		var model = new Ssp.model.Person();
    	me.person.data = model.data;
    	me.personLite.set('id','');
		me.loadCaseloadAssignment();
	},
	
	onEditPerson: function(){
		var me=this;
		var records = this.getView().getSelectionModel().getSelection();
		if (records.length>0)
		{
			me.loadCaseloadAssignment();
		}else{
			Ext.Msg.alert('Error','Please select a student to edit.');
		}
	},

	onDeletePerson: function(){
	    var records = this.getView().getSelectionModel().getSelection();
		if (records.length>0)
		{
			this.deleteConfirmation();
		}else{
			Ext.Msg.alert('Error','Please select a student to delete.');
		}
	},	
	
    deleteConfirmation: function() {
    	var message = 'You are about to delete the student: "'+ this.person.getFullName() + '". Would you like to continue?';
    	var model = this.person;
        if (model.get('id') != "") 
        {  
           Ext.Msg.confirm({
   		     title:'Delete Student?',
   		     msg: message,
   		     buttons: Ext.Msg.YESNO,
   		     fn: this.deletePerson,
   		     scope: this
   		   });
        }else{
     	   Ext.Msg.alert('SSP Error', 'Unable to delete student.'); 
        }
     },	
	
	deletePerson: function( btnId  ){
     	var me=this;
     	var id = me.personLite.get('id');
     	if (btnId=="yes")
     	{
     	   me.getView().setLoading( true );
           me.personService.destroy( id,
        		   {
        	   success: me.deletePersonSuccess,
        	   failure: me.deletePersonFailure,
        	   scope: me
           });	
     	}	
	},
	
	deletePersonSuccess: function( r, scope ){
		var me=scope;
		var store = me.searchStore;
		var id = me.personLite.get('id');
		me.getView().setLoading( false );
	    store.remove( store.getById( id ) );
	},
	
	deletePersonFailure: function( r, scope ){
		var me=scope;
		me.getView().setLoading( false );
	},

    refreshPagingToolBar: function(){
    	this.getSearchGridPager().onLoad();
    },
    
    loadCaseloadAssignment: function(){
    	var comp = this.formUtils.loadDisplay('mainview', 'caseloadassignment', true, {flex:1});    	
    },
  
    onSetProgramStatusClick: function( button ){
    	var me=this;
    	var action = button.action;
    	switch ( action )
    	{
    		case 'active':
    			me.setProgramStatus( action );
    			break;
    		
    		case 'no-show':
    			me.setProgramStatus( action );
    			break;
    			
    		case 'transition':
    	     	/* 
    	     	 * Temp fix for SSP-434
    	     	 * 
    	     	 * Temporarily removing Transition Action from this button.
    			 * TODO: Ensure that this button takes the user to the Journal Tool and initiates a
    			 * Journal Entry.
    			 * // me.appEventsController.getApplication().fireEvent('transitionStudent');
    	     	 */
    	     	 break;
    	     	 
    		case 'non-participating':
    			Ext.create('Ssp.view.ProgramStatusChangeReasonWindow', {
    			    height: 150,
    			    width: 500
    			}).show();
    			break;
    	}
    },
    
    setProgramStatus: function( action ){
	   	var me=this;
	   	var personId = me.personLite.get('id');
	   	var programStatusId = "";
	   	if (personId != "")
	   	{
	   		if (action=='active')
	   		{
	   			programStatusId = Ssp.util.Constants.ACTIVE_PROGRAM_STATUS_ID;
	   		}
	   		
	   		if (action=='no-show')
	   		{
	   			programStatusId = Ssp.util.Constants.NO_SHOW_PROGRAM_STATUS_ID;
	   		}
	   		personProgramStatus = new Ssp.model.PersonProgramStatus();
	   		personProgramStatus.set('programStatusId', programStatusId );
	   		personProgramStatus.set('effectiveDate', Ext.Date.now() );
	   		me.personProgramStatusService.save( 
	   				personId, 
	   				personProgramStatus.data, 
	   				{
	   			success: me.saveProgramStatusSuccess,
	               failure: me.saveProgramStatusFailure,
	               scope: me 
	           });    		
	   	}else{
	   		Ext.Msg.alert('SSP Error','Unable to determine student to set to No-Show status');
	   	}
    },
    
    saveProgramStatusSuccess: function( r, scope){
    	var me=scope;
    	me.getView().setLoading( false );
		//me.getCaseload();
    	me.initSearchGrid();
    },

    saveProgramStatusFailure: function( r, scope){
    	var me=scope;
    	me.getView().setLoading( false );
    },
    
    onSetNonParticipatingProgramStatusComplete: function(){
    	this.initSearchGrid();
    },
    
    /*********** SEARCH BAR ***************/
    
	onSearchClick: function(button){
		var me=this;
		me.setSearchCriteria();
		if ( me.searchCriteria.get('searchTerm') != "")
		{
			me.search();
		}else{
			me.clearSearch();
		}	
	},
	
	setSearchCriteria: function(){
		var me=this;
		var outsideCaseload = !me.getSearchCaseloadCheck().getValue();
		var searchTerm = me.getSearchText().value;
		// store search term
		me.searchCriteria.set('searchTerm', searchTerm);
		me.searchCriteria.set('outsideCaseload', outsideCaseload);
	},
	
	clearSearch: function(){
		var me=this;
		me.searchStore.removeAll();
		me.searchStore.totalCount = 0;
		me.getSearchGridPager().onLoad();
	},
	
	onSearchKeyPress: function(comp,e){
		var me=this;
        if(e.getKey()==e.ENTER){  
    		me.setSearchCriteria();
        	if ( me.searchCriteria.get('searchTerm') != "")
    		{
    			me.search();
    		}else{
    			me.clearSearch();
    		}   
        }  
    },
	
	search: function(){
		var me=this;
		me.preferences.set('SEARCH_GRID_VIEW_TYPE',0);
		me.setGridView();
		if ( me.searchCriteria.get('searchTerm') != "")
		{
			me.getView().setLoading( true );
			me.searchService.search( 
					me.searchCriteria.get('searchTerm'), 
					me.searchCriteria.get('outsideCaseload'),
					{
					success: me.searchSuccess,
					failure: me.searchFailure,
					scope: me
			});				
		}
	},

    searchSuccess: function( r, scope){
    	var me=scope;
    	me.getView().setLoading( false );
		me.selectFirstItem();
		me.getSearchGridPager().onLoad();
    },

    searchFailure: function( r, scope){
    	var me=scope;
    	me.getView().setLoading( false );
    },
    
    
    /**************** CASELOAD FILTERS *********************/
    
	onRetrieveCaseloadClick: function( button ){
		var me=this;
		me.getCaseload();
	},
	
	onCaseloadStatusComboSelect: function( comp, records, eOpts ){
		var me=this;
		if ( records.length > 0)
    	{
			me.caseloadFilterCriteria.set('programStatusId', records[0].get('id') );
     	}
	},
    
	getProgramStatuses: function(){
		var me=this;
		me.programStatusService.getAll({
			success:me.getProgramStatusesSuccess, 
			failure:me.getProgramStatusesFailure, 
			scope: me
	    });
	},

    getProgramStatusesSuccess: function( r, scope){
    	var me=scope;
    	var activeProgramStatusId = "";
    	var programStatus;
    	if ( me.programStatusesStore.getCount() > 0)
    	{
    		me.getCaseloadStatusCombo().setValue( me.caseloadFilterCriteria.get('programStatusId') );
    	   	/*
    		if ( me.preferences.get('SEARCH_GRID_VIEW_TYPE')==1 )
    		{
    	   		me.getCaseload();
    		}
    		*/
    	}
    },	

    getProgramStatusesFailure: function( r, scope){
    	var me=scope;
    },     
    
	getCaseload: function(){
    	var me=this;
		me.preferences.set('SEARCH_GRID_VIEW_TYPE',1);
		me.setGridView();
		me.getView().setLoading( true );
		me.caseloadService.getCaseload( me.caseloadFilterCriteria.get( 'programStatusId' ), 
    		{success:me.getCaseloadSuccess, 
			 failure:me.getCaseloadFailure, 
			 scope: me});		
	},
    
    getCaseloadSuccess: function( r, scope){
    	var me=scope;
    	me.getView().setLoading( false );
		me.selectFirstItem();
		me.getSearchGridPager().onLoad();
    },

    getCaseloadFailure: function( r, scope){
    	var me=scope;
    	me.getView().setLoading( false );
    }
});
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
Ext.define('Ssp.controller.StudentRecordViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable'],
	inject: {
		appEventsController: 'appEventsController',
		formUtils: 'formRendererUtils',
		apiProperties: 'apiProperties',
        personLite: 'personLite'
	},
	
    config: {
        personViewHistoryUrl: ''
       
    },
	
    control: {
		view: {
			collapse: 'onCollapsed',
			expand: 'onExpanded'
		},
		'studentRecordEditButton': {
            click: 'onStudentRecordEditButtonClick'
        },
		'viewCoachingHistoryButton': {
            click: 'onViewCoachingHistoryButtonClick'
        },
        'emailCoachButton': {
            click: 'onEmailCoachButtonClick'
        },
	},
	
    init: function() {
		var me=this;
		var personId = me.personLite.get('id');
		
		me.personViewHistoryUrl = me.apiProperties.getAPIContext() + me.apiProperties.getItemUrl('personViewHistory');
		
        me.personViewHistoryUrl = me.personViewHistoryUrl.replace('{id}',personId);
 		return this.callParent(arguments);
    },
    
    onCollapsed: function(){
    	this.appEventsController.getApplication().fireEvent('collapseStudentRecord');
    },
    
    onExpanded: function(){
    	this.appEventsController.getApplication().fireEvent('expandStudentRecord');
    },
	
	onStudentRecordEditButtonClick: function(button){
        var me=this;
        
        var comp = this.formUtils.loadDisplay('mainview', 'caseloadassignment', true, {flex:1}); 
    },
	
	onEmailCoachButtonClick: function(button){
        var me=this;
        this.appEventsController.getApplication().fireEvent('emailCoach');
        
    },
	
	onViewCoachingHistoryButtonClick: function(button){
        var me=this;
       this.appEventsController.getApplication().fireEvent('viewCoachHistory');
    }
});
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
Ext.define('Ssp.controller.ProgramStatusChangeReasonWindowViewController', {
	extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	appEventsController: 'appEventsController',
    	personLite: 'personLite',
    	programStatusChangeReasonsStore: 'programStatusChangeReasonsStore',
    	personProgramStatusService: 'personProgramStatusService'
    },
    control: {
		view: {
			show: 'onShow'
		},
		
		'saveButton': {
			click: 'onSaveClick'
		},
		
		'cancelButton': {
			click: 'onCancelClick'
		},
		
		programStatusChangeReasonCombo: '#programStatusChangeReasonCombo'
	},
	
	init: function() {	
    	var me=this;
		return me.callParent(arguments);
    },
    
    onShow: function(){
    	var me=this;
		me.getProgramStatusChangeReasonCombo().reset();
    	me.programStatusChangeReasonsStore.load({params:{start:0, limit:50}});
    },
    
    onSaveClick: function( button ){
	   	var me=this;
	   	var personId = me.personLite.get('id');
	   	var valid = me.getProgramStatusChangeReasonCombo().isValid();
	   	var reasonId = me.getProgramStatusChangeReasonCombo().value;
	   	if (valid && reasonId != "")
	   	{
		   	if (personId != "")
		   	{
		   		personProgramStatus = new Ssp.model.PersonProgramStatus();
		   		personProgramStatus.set('programStatusId',Ssp.util.Constants.NON_PARTICIPATING_PROGRAM_STATUS_ID);
		   		personProgramStatus.set('effectiveDate', new Date());
		   		personProgramStatus.set('programStatusChangeReasonId', reasonId );
			   	me.getView().setLoading( true );
		   		me.personProgramStatusService.save( 
		   				personId, 
		   				personProgramStatus.data, 
		   				{
	   					success: me.saveProgramStatusSuccess,
		               failure: me.saveProgramStatusFailure,
		               scope: me 
		        });
		   	}else{
		   		Ext.Msg.alert('SSP Error','Unable to determine student to set to No-Show status');
		   	}	   		
	   	}else{
	   		Ext.Msg.alert('SSP Error','Please correct the hilited errors in the form');
	   	}
    },

    saveProgramStatusSuccess: function( r, scope){
    	var me=scope;
    	me.getView().setLoading( false );
		me.appEventsController.getApplication().fireEvent('setNonParticipatingProgramStatusComplete');
		me.close();
    },

    saveProgramStatusFailure: function( r, scope){
    	var me=scope;
    	me.getView().setLoading( false );
    },    
    
    onCancelClick: function( button ){
    	this.close();
    },
    
    close: function(){
    	this.getView().close();
    }
});
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
Ext.define('Ssp.controller.person.CaseloadAssignmentViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	appointmentService: 'appointmentService',
    	appEventsController: 'appEventsController',
    	apiProperties: 'apiProperties',
     	appointment: 'currentAppointment',
     	formUtils: 'formRendererUtils',
        person: 'currentPerson',
        personLite: 'personLite',
        personService: 'personService',
        personProgramStatusService: 'personProgramStatusService',
        currentPersonAppointment: 'currentPersonAppointment'
    },
    control: {
    	'saveButton':{
    		click: 'onSaveClick'
    	},
    	
    	'cancelButton': {
    		click: 'onCancelClick'
    	},

    	'printButton':{
    		click: 'onPrintClick'
    	},
    	
    	'emailButton': {
    		click: 'onEmailClick'
    	},
    	
    	resetActiveStatusCheck: '#resetActiveStatusCheck'
    },
    
	init: function() {
		var me=this;
		me.resetAppointmentModels();

		var id = me.personLite.get('id');
		// load the person record and init the view
		if (id.length > 0)
		{
			me.getView().setLoading( true );
			
	    	me.personService.get( id, {success:me.getPersonSuccess, 
	    									  failure:me.getPersonFailure, 
	    									  scope: me} );
		}else{
			me.initForms();
			me.updateTitle();
		}
		
		me.appEventsController.assignEvent({eventName: 'studentNameChange', callBackFunc: this.onPersonNameChange, scope: this});    
		
		return this.callParent(arguments);
    },

	resetAppointmentModels: function() {
		var me=this;
		// initialize the appointment and personAppointment
		var personAppointment = new Ssp.model.PersonAppointment();
		var appointment = new Ssp.model.Appointment();
		me.appointment.data = appointment.data;
		me.currentPersonAppointment.data = personAppointment.data;
	},
    
    destroy: function(){
		this.appEventsController.removeEvent({eventName: 'studentNameChange', callBackFunc: this.onPersonNameChange, scope: this});    
    	
    	return this.callParent( arguments );
    },
  
    initForms: function(){
		// retrieve the appointment screen and define items for the screen
    	var caseloadAssignmentView, items; 
    	var caseloadAssignmentView = Ext.ComponentQuery.query('.caseloadassignment')[0];
		
		items = [{ title: 'Personal'+Ssp.util.Constants.REQUIRED_ASTERISK_DISPLAY,
        	       autoScroll: true,
        		   items: [{xtype: 'editperson'}]
        		},{
            		title: 'Appointment'+Ssp.util.Constants.REQUIRED_ASTERISK_DISPLAY,
            		autoScroll: true,
            		items: [{xtype: 'personcoach'},
            		        {xtype:'personappointment'}]
        		},{
            		title: 'Special Service Groups',
            		autoScroll: true,
            		items: [{xtype: 'personspecialservicegroups'}]
        		},{
            		title: 'Referral Sources',
            		autoScroll: true,
            		items: [{xtype: 'personreferralsources'}]
        		},{
            		title: 'Reasons for Service',
            		autoScroll: true,
            		items: [{xtype: 'personservicereasons'}]
        		},{
            		title: 'Ability to Benefit/Anticipated Start Date',
            		autoScroll: true,
            		items: [{xtype: 'personanticipatedstartdate'}]
        		}];
    	
    	// adding a record, so simply init the view
		caseloadAssignmentView.add(items);
    },

    getPersonSuccess: function( r, scope ){
		var me=scope;
    	var person = new Ssp.model.Person();
		me.getView().setLoading( false );
    	me.person.data = person.data;
    	me.person.populateFromGenericObject(r);
		me.getCurrentAppointment();
		me.updateTitle();
    },
    
    getPersonFailure: function( response, scope ){
    	var me=scope;  	
    	me.getView().setLoading( false );
    },

    getCurrentAppointment: function(){
		var me=this;
		var personId = me.person.get('id');
    	if (personId != null)
		{
			
			me.getView().setLoading( true );
		    me.appointmentService.getCurrentAppointment( personId, {success:me.getAppointmentSuccess, 
			    						                             failure:me.getAppointmentFailure, 
			    						                             scope: me} );
		}else{
			me.initForms();
		}    	
    },
    
    getAppointmentSuccess: function( r, scope ){
		var me=scope;	
		me.getView().setLoading( false );
		me.initForms();
    },
    
    getAppointmentFailure: function( response, scope ){
    	var me=scope;   	
    	me.getView().setLoading( false );
    },    
    
    onPersonNameChange: function(){
    	this.updateTitle();
    },
    
    updateTitle: function(){
    	var me=this;
    	me.getView().setTitle( 'Caseload Assignment ' + ((me.person.get('id') != "")?"Edit":"Add") + ' - ' + me.person.getFullName());
    },
    
    onSaveClick: function(button){
		var me=this;
		me.doSave();
	},

	// record saving heavy-lifting, independent of any particular event
	doSave: function() {
		var me=this;
		var model=me.person;
		var id = model.get('id');
		var jsonData = new Object();
		var currentPersonAppointment;
		
		// edit person view
		var personView = Ext.ComponentQuery.query('.editperson')[0];
		var personForm = personView.getForm();

		// coach view
		var coachView = Ext.ComponentQuery.query('.personcoach')[0];
		var coachForm = coachView.getForm();		
		
		// appointment view
		var appointmentView = Ext.ComponentQuery.query('.personappointment')[0];
		var appointmentForm = appointmentView.getForm();
		
		// special service groups view
		var specialServiceGroupsView = Ext.ComponentQuery.query('.personspecialservicegroups')[0];
		var specialServiceGroupsForm = specialServiceGroupsView.getForm();
		var specialServiceGroupsItemSelector = Ext.ComponentQuery.query('#specialServiceGroupsItemSelector')[0];
		var selectedSpecialServiceGroups = [];
		
		// referral sources view
		var referralSourcesView = Ext.ComponentQuery.query('.personreferralsources')[0];
		var referralSourcesForm = referralSourcesView.getForm();
		var referralSourcesItemSelector = Ext.ComponentQuery.query('#referralSourcesItemSelector')[0];	
		var selectedReferralSources = [];
		
		// service reasons view
		var serviceReasonsView = Ext.ComponentQuery.query('.personservicereasons')[0];
		var serviceReasonsForm = serviceReasonsView.getForm();
		var selectedServiceReasons = [];

		// anticipated start date view
		var anticipatedStartDateView = Ext.ComponentQuery.query('.personanticipatedstartdate')[0];
		var anticipatedStartDateForm = anticipatedStartDateView.getForm();

		var formsToValidate = [personForm,
	                 coachForm,
	                 appointmentForm,
	                 anticipatedStartDateForm,
	                 serviceReasonsForm,
	                 specialServiceGroupsForm,
	                 referralSourcesForm];		

		var validateResult = me.formUtils.validateForms( formsToValidate );
		// Validate all of the forms
		if ( validateResult.valid ) 
		{
			personForm.updateRecord();	
			anticipatedStartDateForm.updateRecord();

			//set coach and student type
			model.setCoachId( coachForm.findField('coachId').getValue() );
			model.setStudentTypeId( coachForm.findField('studentTypeId').getValue() );			
			
			// update the appointment
			appointmentForm.updateRecord();
						
			// set special service groups
			specialServiceGroupsFormValues = specialServiceGroupsItemSelector.getValue();
			selectedSpecialServiceGroups = me.getSelectedItemSelectorIdsForTransfer(specialServiceGroupsFormValues);
			model.set('specialServiceGroups', selectedSpecialServiceGroups);

			// referral sources
			referralSourcesFormValues = referralSourcesItemSelector.getValue();
			selectedReferralSources = me.getSelectedItemSelectorIdsForTransfer(referralSourcesFormValues);
			model.set('referralSources', selectedReferralSources);
			
			// set the service reasons
			serviceReasonsFormValues = serviceReasonsForm.getValues();
			selectedServiceReasons = me.formUtils.getSelectedIdsAsArray( serviceReasonsFormValues );
			model.set('serviceReasons', selectedServiceReasons);
						
			me.getView().setLoading( true );
			
			// ensure props are null if necessary
			jsonData = model.setPropsNullForSave( model.data );
			
			me.personService.save( jsonData, 
	    			               {success:me.savePersonSuccess, 
				                    failure:me.savePersonFailure,
				                    statusCode: {
				                      409: me.savePersonConflict
				                    },
				                    scope: me} );

		}else{
			me.formUtils.displayErrors( validateResult.fields );
		}
    },
    
    savePersonSuccess: function( r, scope ){
		var me=scope;
		var personProgramStatus;
		me.getView().setLoading( false );    	
    	if (r.id != "")
		{
    		// new student save an Active program status
    		if ( me.person.get('id') == "" || me.getResetActiveStatusCheck().checked == true)
    		{
    			personProgramStatus = new Ssp.model.PersonProgramStatus();
    			personProgramStatus.set('programStatusId',Ssp.util.Constants.ACTIVE_PROGRAM_STATUS_ID);
    			personProgramStatus.set('effectiveDate', Ext.Date.now() );
    			me.personProgramStatusService.save( 
    					r.id, 
    					personProgramStatus.data, 
    					{
    				success: me.saveProgramStatusSuccess,
                    failure: me.saveProgramStatusFailure,
                    scope: me 
                });
    		}
    		
    		// populate the person object with result
    		me.person.populateFromGenericObject( r );
    		me.saveAppointment();
		}else{
			Ext.Msg.alert('Error','Error saving student record. Please see your administrator for additional details.')
		}    	
    },
    
    savePersonFailure: function( response, scope ){
    	var me=scope;
    	me.getView().setLoading( false );
    },

	savePersonConflict: function( response, scope ) {
		var me=scope;
		me.savePersonFailure(response, scope);
		var dialogOpts = {
			buttons: Ext.Msg.YESNOCANCEL,
			icon: Ext.Msg.WARNING,
			fn: me.resolvePersonConflict,
			scope: me
		};
		var model=me.person;
		var id = model.get('id');
		if ( id ) {
			dialogOpts.title = "Conflicting Student Record Updates";
			dialogOpts.msg = "Your changes did not save because another user" +
				" modified the student record while you were filling out" +
				" this form. Do you want to save your changes anyway?<br/><br/>" +
				"Press 'Yes' to overwrite the existing record with your changes.<br/>" +
				"Press 'No' to discard your changes and load the existing record into this form.<br/>" +
				"Press 'Cancel' to do nothing and resume editing.";
		} else {
			var conflictingPersonId = me.parseConflictingPersonId(response);
			if ( conflictingPersonId ) {
				dialogOpts.title = "Student Already on File";
				dialogOpts.msg = "The student record did not save because another" +
					" student record already exists for the specified external" +
					" identifier. Do you want to save your changes anyway?<br/><br/>" +
					"Press 'Yes' to overwrite the existing record with your changes.<br/>" +
					"Press 'No' to discard your changes and load the existing record into this form.<br/>" +
					"Press 'Cancel' to do nothing and resume editing.";
				dialogOpts.personId = conflictingPersonId;
			} else {
				dialogOpts.buttons = Ext.Msg.OK;
				dialogOptsicon = Ext.Msg.ERROR;
				dialogOpts.fn = function() {
					// no-op
				};
				dialogOpts.title = "Unresolvable Student Record Conflict";
				dialogOpts.msg = "Your changes could not be saved because" +
					" they conflict with an existing student record but the" +
					" exact cause of the conflict could not be determined.<br/><br/>" +
					" Either contact your system administrator or try" +
					" searching for an existing student record with the" +
					" same name and/or identifier.";
			}
		}
		Ext.Msg.show(dialogOpts);
	},

	parseConflictingPersonId: function(response) {
		var me=this;
		if ( !(response.responseText) ) {
			return null;
		}
		var parsedResponseText = Ext.decode(response.responseText);
		var responseDetail = parsedResponseText.detail;
		if ( !(responseDetail) ) {
			return null;
		}
		var attemptedLookupTypeInfo = responseDetail.typeInfo;
		if ( !(attemptedLookupTypeInfo) ) {
			return null;
		}
		var attemptedLookupType = attemptedLookupTypeInfo.name;
		if ( !(attemptedLookupType) || "org.jasig.ssp.model.Person" !== attemptedLookupType ) {
			return null;
		}
		var lookupFields = responseDetail.lookupFields;
		if ( !(lookupFields) ) {
			return null;
		}
		return lookupFields.id || null;
	},

	resolvePersonConflict: function(buttonId, text, opt) {
		var me=this;
		if (buttonId === "yes") {
			// User elected to blindly overwrite persistent record w/ current
			// form. Even so, need to pull back the existing record b/c we still
			// need to get the correct username. Do not want the silently
			// calculated default username to override the externally-provided
			// value or a value set by another user.
			me.personService.get( opt.personId, {success:me.doBlindOverwriteOfPersistentPerson,
				failure: me.getPersonFailure, // TODO need to lock form somehow - do *not* want to send the wrong username
				scope: me} );

		} else if ( buttonId === "no" ) {
			// User elected to discard current edits and reload persistent
			// record. Same concerns here w/r/t making sure we're saving
			// over the correct back-end record.
			me.personService.get( opt.personId, {success:me.doReloadWithExistingPersonRecord,
				failure: me.getPersonFailure, // TODO need to lock form somehow - do *not* want to send the wrong username
				scope: me} );
		} else {
			// nothing to do
		}
	},

	doBlindOverwriteOfPersistentPerson: function(r, scope) {
		var me = scope;
		if (!(me.assertMatchingSchoolIds(r))) {
			return;
		}
		me.person.set('id', r.id);
		me.person.data.username = r.username;
		me.doSave();
	},

	doReloadWithExistingPersonRecord: function(r, scope) {
		var me = scope;
		if (!(me.assertMatchingSchoolIds(r))) {
			return;
		}
		// Do this with basically the same mechanism that
		// SearchViewController.js does to launch the edit form in the first
		// place. (Would be a huge patch to get each individual form to
		// reset/reload itself so we just reload the entire view.)
		var model = new Ssp.model.Person();
		me.person.data = model.data;
		me.personLite.set('id', r.id);
		me.resetAppointmentModels();
		me.formUtils.loadDisplay('mainview', 'caseloadassignment', true, {flex:1});
	},

	assertMatchingSchoolIds: function(personLookupResult) {
		var me = this;
		if ( me.person.data.schoolId ) {
			if ( me.person.data.schoolId.toUpperCase() !== personLookupResult.schoolId.toUpperCase() ) {
				// would usually only happen if there's not a record on
				// file with the proposed schoolID, but there is a record
				// on file with the silently calculated username
				Ext.Msg.alert("Form Save Error",
					"Could not overwrite the existing record" +
					" because the system might have found multiple" +
					" conflicting records or detected it was at risk of" +
					" updating the wrong record your edits. Please contact"
					+ " your system administrators.");
				return false;
			}
		}
		return true;
	},
    
    saveProgramStatusSuccess: function( r, scope ){
		var me=scope;	
    },    
    
    saveProgramStatusFailure: function( response, scope ){
    	var me=scope;  	
    },       
    
    saveAppointment: function(){
    	var me=this;
    	var jsonData, personId;
    	if (me.appointment.get('appointmentDate') != null && me.appointment.get('startTime') != null && me.appointment.get('endTime') !=null && me.appointment.get('studentIntakeRequested'))
		{
    		// Fix dates for GMT offset to UTC
    		me.currentPersonAppointment.set( 'startTime', me.formUtils.fixDateOffsetWithTime(me.appointment.getStartDate() ) );
    		me.currentPersonAppointment.set( 'endTime', me.formUtils.fixDateOffsetWithTime( me.appointment.getEndDate() ) );
    		me.currentPersonAppointment.set(  'studentIntakeRequested', me.appointment.get('studentIntakeRequested'));
    		me.currentPersonAppointment.set(  'intakeEmail', me.appointment.get('intakeEmail'));
    		
    		jsonData = me.currentPersonAppointment.data;
    		personId = me.person.get('id');
			
    		me.appointmentService.saveAppointment( personId, 
    				                               jsonData, 
    				                               {success: me.saveAppointmentSuccess,
    			                                    failure: me.saveAppointmentFailure,
    			                                    scope: me } );
		}else{
			// no appointment is required
			// load students view
			me.loadStudentToolsView();			
		}
    },
    
    saveAppointmentSuccess: function( r, scope ){
		var me=scope; 
    	me.getView().setLoading( false );
		me.loadStudentToolsView();  	
    },    
    
    saveAppointmentFailure: function( response, scope ){
    	var me=scope;  	
    	me.getView().setLoading( false );   	
    },    
    
    getSelectedItemSelectorIdsForTransfer: function(values){
		var selectedIds = new Array();
    	Ext.Array.each(values,function(name, index){
    		if ( name != undefined )
    		{
        		selectedIds.push({"id":name});    			
    		}
		});
    	return selectedIds;
    }, 
    
    onCancelClick: function(button){
		this.loadStudentToolsView();
    },
 
    onPrintClick: function(button){
		Ext.Msg.alert('Attention','This feature is not yet implemented');
    },    

    onEmailClick: function(button){
		Ext.Msg.alert('Attention','This feature is not yet implemented');
    },      
    
    loadStudentToolsView: function(){
    	this.appEventsController.getApplication().fireEvent('displayStudentRecordView');
    }
});
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
Ext.define('Ssp.controller.person.EditPersonViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	appEventsController: 'appEventsController',
        person: 'currentPerson',
        personService: 'personService',
        sspConfig: 'sspConfig'
    },
    control: {
    	retrieveFromExternalButton: {
    		selector: '#retrieveFromExternalButton',
    		listeners: {
                click: 'onRetrieveFromExternalClick'
            }       		
    	},
 		
    	firstNameField: {
    		selector: '#firstName',
    		listeners: {
                change: 'onStudentNameChange'
            }
    	},
    	
    	middleNameField: {
    		selector: '#middleName',
    		listeners: {
                change: 'onStudentNameChange'
            }    		
    	},
    	
    	lastNameField: {
    		selector: '#lastName',
    		listeners: {
                change: 'onStudentNameChange'
            }
    	}, 
    	
    	studentIdField: {
    		selector: '#studentId',
    		listeners: {
                validityChange: 'onStudentIdValidityChange'
            }
    	},
    	
    	homePhoneField: '#homePhone',
    	workPhoneField: '#workPhone',
    	homePhoneField: '#homePhone',
    	primaryEmailAddressField: '#primaryEmailAddress',
    	secondaryEmailAddressField: '#secondaryEmailAddress'
    },  
	init: function() {
		var me=this;
    	var disabled = me.sspConfig.get('syncStudentPersonalDataWithExternalData');		
		var displayRetrieveFromExternalButton = me.sspConfig.get('allowExternalRetrievalOfStudentDataFromCaseloadAssignment');
    	// alias the studentId field and provide validation
		var studentIdField = me.getStudentIdField();
		studentIdField.setFieldLabel(me.sspConfig.get('studentIdAlias') + Ssp.util.Constants.REQUIRED_ASTERISK_DISPLAY);
		Ext.apply(studentIdField, {
	                  minLength: me.sspConfig.get('studentIdMinValidationLength'),
	                  minLengthText: me.sspConfig.get('studentIdMinValidationErrorText'),
	                  maxLength: me.sspConfig.get('studentIdMaxValidationLength'),
	                  maxLengthText: me.sspConfig.get('studentIdMaxValidationErrorText'),
	                  vtype: 'studentIdValidator',
	                  vtypeText: me.sspConfig.get('studentIdValidationErrorText')
                     });		
		
		// when editing a student, 
		if (me.person.get('id') != "")
		{
			// set the retrieve from SSI button visbility
			me.getRetrieveFromExternalButton().setVisible( false );
		
			// disable fields if the external configuration mode is enabled
			me.getFirstNameField().setDisabled(disabled);
			me.getMiddleNameField().setDisabled(disabled);
			me.getLastNameField().setDisabled(disabled);
			studentIdField.setDisabled(disabled);
			me.getHomePhoneField().setDisabled(disabled);
			me.getWorkPhoneField().setDisabled(disabled);
			me.getPrimaryEmailAddressField().setDisabled(disabled);
			me.getSecondaryEmailAddressField().setDisabled(disabled);
		}
		
		me.getView().getForm().reset();
		me.getView().loadRecord( me.person );	

		// use config to determine if the retrieveFromExternalButton should be visible
		if (me.person.get('id') == "")
		{
			me.getRetrieveFromExternalButton().setVisible( displayRetrieveFromExternalButton );			
			// enable the retrieveFromExternalButton if the studentId field is valid
			me.setRetrieveFromExternalButtonDisabled( !studentIdField.isValid() );		
		}else{
			me.getRetrieveFromExternalButton().setVisible(false);
		}
		
		return me.callParent(arguments);
    },
    
    onStudentNameChange: function( comp, newValue, oldValue, eOpts){
    	var me=this;
    	me.person.set(comp.name,newValue);
    	me.appEventsController.getApplication().fireEvent('studentNameChange');
    },
    
    onStudentIdValidityChange: function(comp, isValid, eOpts){
    	var me=this;
        me.setRetrieveFromExternalButtonDisabled( !isValid );
    },
    
    setRetrieveFromExternalButtonDisabled: function( enabled ){
    	this.getRetrieveFromExternalButton().setDisabled( enabled );
    },
    
    onRetrieveFromExternalClick: function( button ){
    	var me=this;
    	var studentIdField = me.getStudentIdField();
    	var schoolId = studentIdField.value;
    	if ( studentIdField.isValid() )
    	{
    		if (schoolId != "")
    		{
    			me.getView().setLoading( true );
    			me.personService.getBySchoolId( schoolId,{
    				success: me.getBySchoolIdSuccess,
    				failure: me.getBySchoolIdFailure,
    				scope: me
    			});
    		}
    	}else{
    		Ext.Msg.alert('SSP Error','Please correct the errors in your form.');
    	}
    },
    
    getBySchoolIdSuccess: function( r, scope ){
		var me=scope;
		var model = new Ssp.model.Person();
		me.getView().setLoading( false );
		if ( r != null)
		{
			me.getView().getForm().reset();
			model.populateFromExternalData( r );
			me.person.data = model.data;
			me.getView().loadRecord( me.person );
		}else{
			Ext.Msg.alert('SSP Notification','There were no records found with the provided ID. Please try a different value.');
		}
    },    
    
    getBySchoolIdFailure: function( response, scope ){
    	var me=scope;
    	me.getView().setLoading( false );
    }
});
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
Ext.define('Ssp.controller.person.CoachViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	appEventsController: 'appEventsController',
    	coachesStore: 'coachesStore',
    	person: 'currentPerson', 	
    	sspConfig: 'sspConfig',
        studentTypesStore: 'studentTypesStore'
    },
    config: {
    	inited: false
    },
    control: {
    	departmentField: '#departmentField',
    	phoneField: '#phoneField',
    	officeField: '#officeField',
    	emailAddressField: '#emailAddressField',

    	coachCombo: {
    		selector: '#coachCombo',
    		listeners: {
        		change: 'onCoachComboChange',
        		select: 'onCoachComboSelect'
    		} 
    	},
    	
    	studentTypeCombo: {
    		selector: '#studentTypeCombo',
    		listeners: {
        		select: 'onStudentTypeComboSelect'
    		}     		
    	}
    },
    
	init: function() {
		var me=this;

		if ( me.person.get('id') != "")
		{
			me.getCoachCombo().setDisabled( me.sspConfig.get('coachSetFromExternalData') );
			me.getStudentTypeCombo().setDisabled( me.sspConfig.get('studentTypeSetFromExternalData') );			
		}
		
		me.studentTypesStore.load();
		me.coachesStore.load(function(records, operation, success) {
	          if(!success)
	          {
	        	  Ext.Msg.alert('Error','Unable to load Coaches. Please see your system administrator for assistance.');
	          }
		 });
		
		me.initForm();
		
		return this.callParent(arguments);
    },

	initForm: function(){
		var me=this;
		me.getView().getForm().reset();
		me.getCoachCombo().setValue( me.person.getCoachId() );
		me.getStudentTypeCombo().setValue( me.person.getStudentTypeId() );
		me.inited=true;
	},    
    
	onCoachComboSelect: function(comp, records, eOpts){
		var me=this;
		var coach;
		if(records.length>0){
			coach=records[0];
			me.displayCoachDepartment( coach );
		}
	},
	
	onCoachComboChange: function(comp, newValue, oldValue, eOpts){
		var me=this;
		var coach = me.coachesStore.getById(newValue);
		if(coach != null){
			me.displayCoachDepartment( coach );
		}
	},
	
	displayCoachDepartment: function( coach ){
		var me=this;
		me.getDepartmentField().setValue( coach.get('departmentName') );
		me.getPhoneField().setValue( coach.get('workPhone') );
		me.getEmailAddressField().setValue( coach.get('primaryEmailAddress') );
		me.getOfficeField().setValue( coach.get('officeLocation') );
	},

	onStudentTypeComboSelect: function(comp, records, eOpts){
		var me=this;
		var studentType, requireInitialAppointment;
		if(records.length>0){
			me.appEventsController.getApplication().fireEvent('studentTypeChange');
		}
	},
	
	onStudentTypeComboChange: function(comp, newValue, oldValue, eOpts){
		var me=this;
		var studentType, requireInitialAppointment;
		studentType = me.studentTypesStore.getById(newValue);
		if(studentType != null){
			me.appEventsController.getApplication().fireEvent('studentTypeChange');
		}
	}
});
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
Ext.define('Ssp.controller.person.AppointmentViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	appEventsController: 'appEventsController',
    	appointment: 'currentAppointment',
    	formUtils: 'formRendererUtils',
    	studentTypesStore: 'studentTypesStore'
    },
    control: {
    	appointmentDateField: '#appointmentDateField',
    	startTimeField: '#startTimeField',
    	endTimeField: '#endTimeField',
    	'studentIntakeRequestedField':{
    		change: 'onHideRequestEmail'
    	} 
    },
    
	init: function() {
		var me=this;

		me.appEventsController.assignEvent({eventName: 'studentTypeChange', callBackFunc: this.onStudentTypeChange, scope: this});
		
		// require a date beyond today for all new appointments
		if (me.appointment.get('id') == "")
		{
			today = new Date();
			me.getAppointmentDateField().setMinValue( Ext.Date.clearTime( today ) );
		}
				
		me.getView().getForm().reset();
		me.getView().loadRecord( me.appointment );

		me.assignAppointmentRequiredFields();
		
		return me.callParent(arguments);
    },
    onHideRequestEmail: function(field, newValue,oldValue, eOpts)
    {
    	var emailBox = Ext.ComponentQuery.query('#intakeEmailField')[0];
    	if(newValue)
    	{
    		emailBox.show();
    	}
    	else
    	{
    		emailBox.hide();
    	}
    },
    destroy: function(){
    	this.appEventsController.removeEvent({eventName: 'studentTypeChange', callBackFunc: this.onStudentTypeChange, scope: this});    	
    	
    	return this.callParent( arguments );
    },
    
    onStudentTypeChange: function(){
    	this.assignAppointmentRequiredFields();
    },
    
    assignAppointmentRequiredFields: function(){
    	// TODO: Decouple this interaction from
    	// the Coach.js screen
    	var me=this;
    	var studentTypeCombo = Ext.ComponentQuery.query('#studentTypeCombo')[0];
    	var newValue = studentTypeCombo.getValue();
    	var studentType, requireAppointment, appendToLabelValue;
    	var appointmentField = me.getAppointmentDateField();
    	var startTimeField = me.getStartTimeField();
    	var endTimeField = me.getEndTimeField();
    	studentType = me.studentTypesStore.getById(newValue);
    	if (studentType != null)
    	{
    		requireAppointment = studentType.get('requireInitialAppointment');
			appendToLabelValue = "";
			// enable requirement of appointment
    		if (requireAppointment==true)
    		{
    			appendToLabelValue = Ssp.util.Constants.REQUIRED_ASTERISK_DISPLAY;
    		}

    		appointmentField.setFieldLabel( appointmentField.fieldLabel.replace(Ssp.util.Constants.REQUIRED_ASTERISK_DISPLAY,"","gi")+appendToLabelValue );
    		appointmentField.allowBlank=!requireAppointment;
    		appointmentField.validate();
    		
    		startTimeField.setFieldLabel( startTimeField.fieldLabel.replace(Ssp.util.Constants.REQUIRED_ASTERISK_DISPLAY,"","gi")+appendToLabelValue );
    		startTimeField.allowBlank=!requireAppointment;
    		startTimeField.validate();
    		
    		endTimeField.setFieldLabel( endTimeField.fieldLabel.replace(Ssp.util.Constants.REQUIRED_ASTERISK_DISPLAY,"","gi")+appendToLabelValue );
    		endTimeField.allowBlank=!requireAppointment;
    		endTimeField.validate();
    	}
    }
});
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
Ext.define('Ssp.controller.person.SpecialServiceGroupsViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	columnRendererUtils: 'columnRendererUtils',
    	person: 'currentPerson',
    	store: 'specialServiceGroupsBindStore',
    	service: 'specialServiceGroupService',
        itemSelectorInitializer: 'itemSelectorInitializer'
    },
	init: function() {
		var me=this;
				
		me.service.getAll({
			success: me.getAllSuccess,
			failure: me.getAllFailure,
			scope: me
		});
		
		return me.callParent(arguments);
    },
    
	getAllSuccess: function( r, scope ){
		var me=scope;
    	var selectedSpecialServiceGroups = me.columnRendererUtils.getSelectedIdsForMultiSelect( me.person.get('specialServiceGroups') );
    	if (r.rows.length > 0)
    	{
    		me.store.loadData(r.rows);

            me.itemSelectorInitializer.defineAndAddSelectorField(me.getView(), selectedSpecialServiceGroups, {
                itemId: 'specialServiceGroupsItemSelector',
                name: 'specialServiceGroups',
                fieldLabel: 'Service Groups',
                store: me.store
            });

    	}
	},
	
    getAllFailure: function( response, scope ){
    	var me=scope;  	
    }

});
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
Ext.define('Ssp.controller.person.ReferralSourcesViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	columnRendererUtils: 'columnRendererUtils',
    	person: 'currentPerson',
        store: 'referralSourcesBindStore',
        service: 'referralSourceService',
        itemSelectorInitializer: 'itemSelectorInitializer'
    },
	init: function() {
		var me=this;
		
		me.service.getAll({
			success: me.getAllSuccess,
			failure: me.getAllFailure,
			scope: me
		});
		
		return me.callParent(arguments);
    },
    
	getAllSuccess: function( r, scope ){

        var me=scope;
        var selectedReferralSources = me.columnRendererUtils.getSelectedIdsForMultiSelect( me.person.get('referralSources') );
        if (r.rows.length > 0)
        {
            me.store.loadData(r.rows);

            me.itemSelectorInitializer.defineAndAddSelectorField(me.getView(), selectedReferralSources, {
                itemId: 'referralSourcesItemSelector',
                name: 'referralSources',
                fieldLabel: 'Referral Sources',
                store: me.store
            });

        }

	},
	
    getAllFailure: function( response, scope ){
    	var me=scope;  	
    }

});
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
Ext.define('Ssp.controller.person.ServiceReasonsViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	formUtils: 'formRendererUtils',
        person: 'currentPerson',
        serviceReasonsStore: 'serviceReasonsStore'
    },
    
	init: function() {
		var me=this;

		var serviceReasonsSuccessFunc = function(records,operation,success){
			if (records.length > 0)
	    	{
	    		var items = [];
				Ext.Array.each(records,function(item,index){
	    			items.push(item.raw);
	    		});
				var serviceReasonsFormProps = {
	    				mainComponentType: 'checkbox',
	    				formId: 'personservicereasons', 
	                    fieldSetTitle: 'Select all that apply:',
	                    itemsArr: items, 
	                    selectedItemsArr: me.person.get('serviceReasons'), 
	                    idFieldName: 'id', 
	                    selectedIdFieldName: 'id',
	                    additionalFieldsMap: [] };
	    		
	    		me.formUtils.createForm( serviceReasonsFormProps );	    		
	    	}
		};
		
		me.serviceReasonsStore.load({scope: me, callback: serviceReasonsSuccessFunc});
		
		return this.callParent(arguments);
    }
});
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
Ext.define('Ssp.controller.person.AnticipatedStartDateViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
        person: 'currentPerson'
    },
    
	init: function() {
		this.getView().loadRecord( this.person );
		
		return this.callParent(arguments);
    }
});
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
Ext.define('Ssp.controller.ToolsViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: ['Deft.mixin.Injectable'],
    inject: {
        appEventsController: 'appEventsController',
        apiProperties: 'apiProperties',
        authenticatedPerson: 'authenticatedPerson',
        formUtils: 'formRendererUtils',
        personLite: 'personLite',
        toolsStore: 'toolsStore'
    },
    control: {
        view: {
            itemclick: 'onItemClick',
            viewready: 'onViewReady'
        }
    
    },
    
    init: function(){
        return this.callParent(arguments);
    },
    
    onViewReady: function(comp, obj){
        var me = this;
        me.appEventsController.assignEvent({
            eventName: 'loadPerson',
            callBackFunc: me.onLoadPerson,
            scope: me
        });
        me.appEventsController.assignEvent({
            eventName: 'transitionStudent',
            callBackFunc: me.onTransitionStudent,
            scope: me
        });
        
        if (me.personLite.get('id') != "") {
            me.loadPerson();
        }
    },
    
    destroy: function(){
        var me = this;
        
        me.appEventsController.removeEvent({
            eventName: 'loadPerson',
            callBackFunc: me.onLoadPerson,
            scope: me
        });
        me.appEventsController.removeEvent({
            eventName: 'transitionStudent',
            callBackFunc: me.onTransitionStudent,
            scope: me
        });
        
        return me.callParent(arguments);
    },
    
    onLoadPerson: function(){
        this.loadPerson();
    },
    
    onTransitionStudent: function(){
        this.selectTool('journal');
        this.loadTool('journal');
    },
    
    loadPerson: function(){
        this.selectTool('profile');
        this.loadTool('profile');
    },
    
    selectTool: function(toolType){
        var tool = this.toolsStore.find('toolType', toolType)
        this.getView().getSelectionModel().select(tool);
    },
    
    onItemClick: function(grid, record, item, index){
        var me = this;
        if (record.get('active') && me.personLite.get('id') != "") {
            this.loadTool(record.get('toolType'));
            this.appEventsController.getApplication().fireEvent('collapseSearch');
        }
    },
    
    loadTool: function(toolType){
        var me = this;
        var comp;
        if (me.authenticatedPerson.hasAccess(toolType.toUpperCase() + '_TOOL')) {
            comp = me.formUtils.loadDisplay('tools', toolType, true, {});
        }
        else {
            me.authenticatedPerson.showUnauthorizedAccessAlert();
        }
    }
});

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
Ext.define('Ssp.controller.tool.profile.PlacementViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
        personLite: 'personLite',
        store: 'placementStore',
        service: 'placementService'
    },
	init: function() {
		var me=this;
		var personId = me.personLite.get('id');

    	// hide the loader
    	me.getView().setLoading( true );
    	
		me.service.getAll( personId, {
			success: me.getPlacementSuccess,
			failure: me.getPlacementFailure,
			scope: me			
		});
		
		return this.callParent(arguments);
    },
    
    getPlacementSuccess: function( r, scope ){
    	var me=scope;

        me.store.loadData(r);
    	me.getView().setLoading( false );
    	
    },
    
    getPlacementFailure: function( response, scope ){
    	var me=scope;
    	me.getView().setLoading( false );  	
    }
});
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
Ext.define('Ssp.view.tools.profile.Placement', {
	extend: 'Ext.grid.Panel',
	alias: 'widget.placement',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],	
    width: '100%',
    height: '100%',
    controller: 'Ssp.controller.tool.profile.PlacementViewController',
    autoScroll: true,
    inject: {
        store: 'placementStore'
    },
    initComponent: function() {
        var me = this;
        Ext.applyIf(me, {
            store: me.store,
            columns: [
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'type',
                    text: 'Type'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'score',
                    text: 'Score'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'status',
                    text: 'Status'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'takenDate',
                    text: 'Date',
					renderer: Ext.util.Format.dateRenderer('m/d/Y')
                }
            ],
            viewConfig: {

            }
        });

        me.callParent(arguments);
    }
});
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
Ext.define('Ssp.controller.tool.profile.ProfileToolViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	personLite: 'personLite',
		formUtils: 'formRendererUtils',
		appEventsController: 'appEventsController'
    },
    config: {
    	personViewHistoryUrl: '',
    	printConfidentialityAgreementUrl: ''
    },
    control: {
    	
    },
	init: function() {
		
		var me=this;
		var personId = me.personLite.get('id');
		
		me.appEventsController.assignEvent({
            eventName: 'viewCoachHistory',
            callBackFunc: me.onViewCoachHistory,
            scope: me
        });
		
		
		me.personViewHistoryUrl = me.apiProperties.getAPIContext() + me.apiProperties.getItemUrl('personViewHistory');
		
		me.personViewHistoryUrl = me.personViewHistoryUrl.replace('{id}',personId);
		
		me.printConfidentialityAgreementUrl = me.apiProperties.getContext() + me.apiProperties.getItemUrl('printConfidentialityDisclosureAgreement');
		return this.callParent(arguments);
    },
	
	destroy: function() {
        var me=this;
       // me.appEventsController.removeEvent({eventName: 'viewCoachHistory', callBackFunc: me.onViewCoachHistory, scope: me});
        
        return me.callParent( arguments );
    },
    
	
	
	onViewCoachHistory: function(){
      var me=this;
        me.apiProperties.getReporter().load({
            url:me.personViewHistoryUrl,
            params: ""
        });
    }
});
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
Ext.define('Ssp.model.Transcript', {
    extend: 'Ssp.model.AbstractBase',
    fields: [{name: 'gpa', type: 'auto'},
             {name: 'programs', type: 'auto'},
             {name: 'terms', type: 'auto'}]
});
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
Ext.define('Ssp.model.CourseTranscript', {
    extend: 'Ssp.model.AbstractBase',
    fields: [{name: 'termCode', type: 'string'},
        {name: 'formattedCourse', type: 'string'},
        {name: 'title', type: 'string'},
        {name: 'creditType', type: 'string'},
        {name: 'grade', type: 'string'}]
});
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
Ext.define('Ssp.controller.tool.profile.ProfilePersonViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: ['Deft.mixin.Injectable'],
    inject: {
        apiProperties: 'apiProperties',
        appEventsController: 'appEventsController',
        person: 'currentPerson',
        personLite: 'personLite',
        personService: 'personService',
        transcriptService: 'transcriptService',
        profileReferralSourcesStore: 'profileReferralSourcesStore',
        profileServiceReasonsStore: 'profileServiceReasonsStore',
        profileSpecialServiceGroupsStore: 'profileSpecialServiceGroupsStore',
        sspConfig: 'sspConfig',
        formUtils: 'formRendererUtils'
    },

    control: {
        nameField: '#studentName',
        photoUrlField: '#studentPhoto',

        studentIdField: '#studentId',
        birthDateField: '#birthDate',
        studentTypeField: '#studentType',
        programStatusField: '#programStatus',

        gpaField: '#cumGPA',
        hoursEarnedField: '#hrsEarned',
        hoursAttemptedField: '#hrsAttempted',

        academicProgramsField: '#academicPrograms',

        earlyAlertField: '#earlyAlert',

        'serviceReasonEdit': {
            click: 'onServiceReasonEditButtonClick'
        },

        'serviceGroupEdit': {
            click: 'onServiceGroupEditButtonClick'
        }

    },
    init: function(){
        var me = this;
        var id = me.personLite.get('id');
        me.resetForm();
       
        if (id != "") {
            // display loader
            me.getView().setLoading(true);

            var serviceResponses = {
                failures: {},
                successes: {},
                responseCnt: 0,
                expectedResponseCnt: 2
            }

            me.personService.get(id, {
                success: me.newServiceSuccessHandler('person', me.getPersonSuccess, serviceResponses),
                failure: me.newServiceFailureHandler('person', me.getPersonFailure, serviceResponses),
                scope: me
            });
            me.transcriptService.getSummary(id, {
                success: me.newServiceSuccessHandler('transcript', me.getTranscriptSuccess, serviceResponses),
                failure: me.newServiceFailureHandler('transcript', me.getTranscriptFailure, serviceResponses),
                scope: me
            });
        }

        return me.callParent(arguments);
    },

    resetForm: function() {
        var me = this;
        me.getView().getForm().reset();

        // Set defined configured label for the studentId field
        var studentIdAlias = me.sspConfig.get('studentIdAlias');
        me.getStudentIdField().setFieldLabel(studentIdAlias);

    },

    newServiceSuccessHandler: function(name, callback, serviceResponses) {
        var me = this;
        return me.newServiceHandler(name, callback, serviceResponses, function(name, serviceResponses, response) {
            serviceResponses.successes[name] = response;
        });
    },

    newServiceFailureHandler: function(name, callback, serviceResponses) {
        var me = this;
        return me.newServiceHandler(name, callback, serviceResponses, function(name, serviceResponses, response) {
            serviceResponses.failures[name] = response;
        });
    },

    newServiceHandler: function(name, callback, serviceResponses, serviceResponsesCallback) {
        return function(r, scope) {
            var me = scope;
            serviceResponses.responseCnt++;
            if ( serviceResponsesCallback ) {
                serviceResponsesCallback.apply(me, [name, serviceResponses, r]);
            }
            if ( callback ) {
                callback.apply(me, [ serviceResponses ]);
            }
            me.afterServiceHandler(serviceResponses);
        };
    },

    getPersonSuccess: function(serviceResponses) {
        var me = this;
        var personResponse = serviceResponses.successes.person;
        me.person.populateFromGenericObject(personResponse);

        // load and render person data
        me.profileSpecialServiceGroupsStore.removeAll();
        me.profileReferralSourcesStore.removeAll();
        me.profileServiceReasonsStore.removeAll();

        var nameField = me.getNameField();
        var photoUrlField = me.getPhotoUrlField();
        var birthDateField = me.getBirthDateField();
        var studentTypeField = me.getStudentTypeField();
        var programStatusField = me.getProgramStatusField();
        var earlyAlertField = me.getEarlyAlertField();

        var fullName = me.person.getFullName();
        var coachName = me.person.getCoachFullName();

        // load special service groups
        if (personResponse.specialServiceGroups != null) {
            me.profileSpecialServiceGroupsStore.loadData(me.person.get('specialServiceGroups'));
        }

        // load referral sources
        if (personResponse.referralSources != null) {
            me.profileReferralSourcesStore.loadData(me.person.get('referralSources'));
        }

        // load service reasons
        if (personResponse.serviceReasons != null) {
            me.profileServiceReasonsStore.loadData(me.person.get('serviceReasons'));
        }

        // load general student record
        me.getView().loadRecord(me.person);

        // load additional values
        nameField.setValue(fullName);
        birthDateField.setValue(me.person.getFormattedBirthDate());
        studentTypeField.setValue(me.person.getStudentTypeName());
        photoUrlField.setSrc(me.person.getPhotoUrl());
        programStatusField.setValue(me.person.getProgramStatusName());
        earlyAlertField.setValue(me.person.getEarlyAlertRatio());

        var studentRecordComp = Ext.ComponentQuery.query('.studentrecord')[0];
        var studentCoachButton = Ext.ComponentQuery.query('#emailCoachButton')[0];
        studentRecordComp.setTitle('Student: ' + fullName + '          ' + '  -   ID#: ' + me.person.get('schoolId'));
        studentCoachButton.setText('<u>Coach: ' + coachName + '</u>');
		
        me.appEventsController.assignEvent({
            eventName: 'emailCoach',
            callBackFunc: me.onEmailCoach,
            scope: me
        });
    },

    getPersonFailure: function() {
        // nothing to do
    },

    getTranscriptSuccess: function(serviceResponses) {
        var me = this;
        var transcriptResponse = serviceResponses.successes.transcript;

        var transcript = new Ssp.model.Transcript(transcriptResponse);
        var gpa = transcript.get('gpa');
        if ( gpa ) {
			var gpaFormatted = Ext.util.Format.number(gpa.gradePointAverage, '0.00');
            me.getGpaField().setValue(gpaFormatted);
            me.getHoursEarnedField().setValue(gpa.creditHoursForGpa);
            me.getHoursAttemptedField().setValue(gpa.creditHoursAttempted);
        }
        var programs = transcript.get('programs');
        if ( programs ) {
            var programNames = [];
            Ext.Array.each(programs, function(program) {
                programNames.push(program.programName);
            });
            me.getAcademicProgramsField().setValue(programNames.join(', '));
        }
    },

    getTranscriptFailure: function() {
        // nothing to do
    },

    afterServiceHandler: function(serviceResponses) {
        var me = this;
        if ( serviceResponses.responseCnt >= serviceResponses.expectedResponseCnt ) {
            me.getView().setLoading(false);
        }
    },

    destroy: function() {
        var me=this;
        //me.appEventsController.removeEvent({eventName: 'emailCoach', callBackFunc: me.onEmailCoach, scope: me});

        return me.callParent( arguments );
    },

    onEmailCoach: function(){
        var me = this;
        if (me.person.getCoachPrimaryEmailAddress()) {
            window.location = 'mailto:' + me.person.getCoachPrimaryEmailAddress();
        }
    },

    onServiceReasonEditButtonClick: function(button){
        var me=this;

        var comp = this.formUtils.loadDisplay('mainview', 'caseloadassignment', true, {flex:1});

    },

    onServiceGroupEditButtonClick: function(button){
        var me=this;

        var comp = this.formUtils.loadDisplay('mainview', 'caseloadassignment', true, {flex:1});

    }

});

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
Ext.define('Ssp.controller.tool.profile.ServicesProvidedViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
        person: 'currentPerson'
    },

	init: function() {
		var me=this;

		return this.callParent(arguments);
    }
});
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
Ext.define('Ssp.controller.tool.profile.ProfileCoachViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	appEventsController: 'appEventsController',
        person: 'currentPerson',
        personLite: 'personLite',
        personService: 'personService',
        sspConfig: 'sspConfig'
    },
    
    control: {
    	
    	coachNameField: '#coachName',
    	coachWorkPhoneField: '#coachWorkPhone',
    	coachDepartmentNameField: '#coachDepartmentName',
    	coachOfficeLocationField: '#coachOfficeLocation',
    	coachPrimaryEmailAddressField: '#coachPrimaryEmailAddress'
    	
    	
    },
	init: function() {
		var me=this;
		
		var studentIdAlias = me.sspConfig.get('studentIdAlias');
		var id =  me.personLite.get('id');
		me.getView().getForm().reset();

				
		
		if (id != "")
		{
			// display loader
			me.getView().setLoading( true );
			me.personService.get( id, {
				success: me.getPersonSuccess,
				failure: me.getPersonFailure,
				scope: me
			});
		}
		
		return me.callParent(arguments);
    },
    
    getPersonSuccess: function( r, scope ){
    	var me=scope;
		
		
		var coachNameField = me.getCoachNameField();
		var coachWorkPhoneField = me.getCoachWorkPhoneField();
		var coachDepartmentNameField = me.getCoachDepartmentNameField();
		var coachOfficeLocationField = me.getCoachOfficeLocationField();
		var coachPrimaryEmailAddressField = me.getCoachPrimaryEmailAddressField();
		
		var id= me.personLite.get('id');
		var studentIdAlias = me.sspConfig.get('studentIdAlias');
		
		// load the person data
		me.person.populateFromGenericObject(r);		
		
    	
   	
			
		
		// load general student record
		me.getView().loadRecord( me.person );
		
		// load additional values
		
		coachNameField.setValue( me.person.getCoachFullName() );
		coachWorkPhoneField.setValue( me.person.getCoachWorkPhone() );
		coachDepartmentNameField.setValue( me.person.getCoachDepartmentName() );
		coachOfficeLocationField.setValue( me.person.getCoachOfficeLocation() );
		coachPrimaryEmailAddressField.setValue( me.person.getCoachPrimaryEmailAddress() );
		

		
		
		// hide the loader
    	me.getView().setLoading( false ); 
    },
    
    getPersonFailure: function( response, scope){
    	var me=scope;
    	me.getView().setLoading( false );
    }
});
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
Ext.define('Ssp.view.tools.profile.Coach', {
    extend: 'Ext.form.Panel',
    alias : 'widget.profilecoach',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.profile.ProfileCoachViewController',
    inject: {
        columnRendererUtils: 'columnRendererUtils',
        sspConfig: 'sspConfig'
    },
    width: '100%',
    height: '100%',
    initComponent: function() { 
        var me=this;
        Ext.apply(me, 
                {
                    border: 0,  
                    bodyPadding: 0,
                    layout: 'anchor',
                    defaults: {
                        anchor: '100%'  
                    },
					items: [{
                        xtype: 'fieldcontainer',
                        fieldLabel: '',
                        layout: 'hbox',
                        margin: '0 5 0 0',
                        defaultType: 'displayfield',
                        fieldDefaults: {
                            msgTarget: 'side',
                            labelAlign: 'right',
                            labelWidth: 100
                        },
                        items: [{
                            xtype: 'fieldset',
                            border: 0,
                            title: '',
                            defaultType: 'displayfield',
                            defaults: {
                                anchor: '100%'
                            },
                            flex: .55,
                            items:[{
                        
                                    fieldLabel: me.sspConfig.get('coachFieldLabel'),
                                    name: 'coachName',
                                    itemId: 'coachName',
                                    labelWidth: 80
                                },{
                                    fieldLabel: 'Phone',
                                    name: 'coachWorkPhone',
                                    itemId: 'coachWorkPhone',
                                    labelWidth: 80
                                },{
                                    fieldLabel: 'Email',
                                    name: 'coachPrimaryEmailAddress',
                                    itemId: 'coachPrimaryEmailAddress',
                                    labelWidth: 80
                                },{
                                    fieldLabel: 'Department',
                                    name: 'coachDepartmentName',
                                    itemId: 'coachDepartmentName',
                                    labelWidth: 80
                                },{
                                    fieldLabel: 'Office',
                                    name: 'coachOfficeLocation',
                                    itemId: 'coachOfficeLocation',
                                    labelWidth: 80
                                }]
                            
                        }]
                               
                      }]
                    
                });
        
         return me.callParent(arguments);
    }
    
});
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
Ext.define('Ssp.controller.tool.profile.ProfileContactViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	appEventsController: 'appEventsController',
        person: 'currentPerson',
        personLite: 'personLite',
        personService: 'personService',
        sspConfig: 'sspConfig',
		authenticatedPerson: 'authenticatedPerson',
		formUtils: 'formRendererUtils',
    },
    
    control: {
    	nameField: '#studentName',
    	birthDateField: '#birthDate',
    	addressField: '#address',
    	alternateAddressInUseField: '#alternateAddressInUse',
    	alternateAddressField: '#alternateAddress',
		primaryEmailAddressField: '#primaryEmailAddress',
		primaryEmailAddress: {
            click :    'onPrimaryEmailAddressClick',
		},    
        editButton: {
    		click: 'onEditClick'
    	}        
           
        
		
    },
	init: function() {
		var me=this;
		
		var studentIdAlias = me.sspConfig.get('studentIdAlias');
		var id =  me.personLite.get('id');
		me.getView().getForm().reset();

			
		
		if (id != "")
		{
			// display loader
			me.getView().setLoading( true );
			me.personService.get( id, {
				success: me.getPersonSuccess,
				failure: me.getPersonFailure,
				scope: me
			});
		}
		
		return me.callParent(arguments);
    },
    
    getPersonSuccess: function( r, scope ){
    	var me=scope;
		
		
		var nameField = me.getNameField();
		
		var birthDateField = me.getBirthDateField();
		
		var primaryEmailAddressField = me.getPrimaryEmailAddressField();
		
		var id= me.personLite.get('id');
		var studentIdAlias = me.sspConfig.get('studentIdAlias');
		var fullName;
		var alternateAddressInUse = "No";
		var primaryEmailWithLink = "";
		
		// load the person data
		me.person.populateFromGenericObject(r);		
		
    	fullName = me.person.getFullName();
   	
			
		
		// load general student record
		me.getView().loadRecord( me.person );
		
		// load additional values
		nameField.setValue( fullName );
		
		birthDateField.setValue( me.person.getFormattedBirthDate() );
		
		primaryEmailWithLink = '<u>' + me.person.get('primaryEmailAddress') + '</u>';
		
		
		primaryEmailAddressField.setValue(primaryEmailWithLink);
		

		me.getAddressField().setValue(me.person.buildAddress());
		
		me.getAlternateAddressField().setValue(me.person.buildAlternateAddress());
		
		if (me.person.get('alternateAddressInUse')!=null)
		{
			if (me.person.get('alternateAddressInUse')===true)
			{
				alternateAddressInUse = "Yes";
			}
		}
		
		me.getAlternateAddressInUseField().setValue( alternateAddressInUse );
		
		// hide the loader
    	me.getView().setLoading( false ); 
    },
    
    getPersonFailure: function( response, scope){
    	var me=scope;
    	me.getView().setLoading( false );
    },
	
	onPrimaryEmailAddressClick: function(){
        var me = this;
        if (me.person.get('primaryEmailAddress')) {
            window.location = 'mailto:' + me.person.get('primaryEmailAddress');
        }
    },
	
	onEditClick: function(button){
		this.displayIntake();
	},	
	displayIntake: function(){
        var me = this;
        var comp;
        if (me.authenticatedPerson.hasAccess('STUDENTINTAKE_TOOL')) {
            comp = me.formUtils.loadDisplay('tools', 'studentintake', true, {});
        }
        else {
            me.authenticatedPerson.showUnauthorizedAccessAlert();
        }	
     }	
});
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
Ext.define('Ssp.view.tools.profile.Contact', {
    extend: 'Ext.form.Panel',
    alias: 'widget.profilecontact',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.profile.ProfileContactViewController',
    inject: {
        columnRendererUtils: 'columnRendererUtils',
        sspConfig: 'sspConfig'
    },
    width: '100%',
    height: '100%',
    initComponent: function(){
        var me = this;
        Ext.apply(me, {
            border: 0,
            bodyPadding: 0,
            layout: 'anchor',
            defaults: {
                anchor: '100%'
            },
            
            items: [{
                xtype: 'fieldcontainer',
                fieldLabel: '',
                margin: '0 5 5 0',
                layout: 'hbox',
                defaultType: 'displayfield',
                fieldDefaults: {
                    msgTarget: 'side',
                    labelAlign: 'right',
                    labelWidth: 100
                },
                items: [{
                    xtype: 'fieldset',
                    border: 0,
                    title: '',
                    defaultType: 'displayfield',
                    defaults: {
                        anchor: '100%'
                    },
                    padding: 0,
                    flex: .60,
                    items: [{
                        fieldLabel: '',
                        name: 'name',
                        itemId: 'studentName',
                        padding: '0 0 0 10',
                        height: '20',
                        style: 'font-weight:bold;color: #00008B'
                    
                    }, {
                        xtype: 'fieldset',
                        border: 1,
                        cls: 'ssp-form',
                        title: 'Mailing Address',
                        defaultType: 'displayfield',
                        defaults: {
                            anchor: '100%'
                        },
                        flex: .40,
                        items: [{
                            fieldLabel: 'Non-local',
                            name: 'nonLocalAddress',
                            labelWidth: 80,
                            renderer: me.columnRendererUtils.renderFriendlyBoolean
                        }, {
                            fieldLabel: 'Address',
                            height: '60',
                            name: 'address',
                            labelWidth: 80,
                            itemId: 'address'
                        }]
                    }, {
                        xtype: 'fieldset',
                        border: 0,
                        title: '',
                        flex: .60,
                        defaultType: 'displayfield',
                        padding: '0 0 10 0',
                        defaults: {
                            anchor: '100%'
                        },
                        items: [{
                            fieldLabel: 'Home Phone',
                            name: 'homePhone'
                        }, {
                            fieldLabel: 'Mobile Phone',
                            name: 'cellPhone'
                        }, {
                            fieldLabel: 'School Official',
                            name: 'primaryEmailAddress',
                            itemId: 'primaryEmailAddress',
                            listeners: {
                                render: function(c){
                                    c.getEl().on('click', function(){
                                        this.fireEvent('click', c);
                                    }, c);
                                }
                            }
                        }, {
                            fieldLabel: 'Secondary',
                            name: 'secondaryEmailAddress'
                        }, , {
                            fieldLabel: 'DOB',
                            name: 'birthDate',
                            itemId: 'birthDate'
                        }]
                    }]
                }, {
                    xtype: 'fieldset',
                    border: 0,
                    
                    flex: .05,
                
                }, {
                    xtype: 'fieldset',
                    border: 0,
                    title: '',
                    defaultType: 'displayfield',
                    defaults: {
                        anchor: '100%'
                    },
                    padding: 0,
                    flex: .40,
                    items: [{
                        fieldLabel: '',
                        padding: '0 0 0 10',
                        height: '20'
                    
                    }, {
                        xtype: 'fieldset',
                        border: 1,
                        cls: 'ssp-form',
                        title: 'Alternate Address',
                        
                        defaultType: 'displayfield',
                        defaults: {
                            anchor: '100%'
                        },
                        
                        items: [
                      {
            						xtype: 'button', 
            						itemId: 'editButton', 
            						name: 'editButton',
            						text:'Edit', 
                                    anchor: '33%',
                                    buttonAlign: 'right',                        
             						action: 'edit'
            		 },
                       
                       {
                            fieldLabel: 'In Use',
                            name: 'alternateAddressInUse',
                            labelWidth: 80,
                            itemId: 'alternateAddressInUse'
                        }, {
                            fieldLabel: 'Address',
                            name: 'alternateAddress',
                            labelWidth: 80,
                            height: '60',
                            itemId: 'alternateAddress'
                        }]
                    }]
                }]
            }]
        });
        
        return me.callParent(arguments);
    }
    
});

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
Ext.define('Ssp.controller.tool.actionplan.ActionPlanToolViewController', {
    extend: 'Deft.mvc.ViewController',	
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
        store: 'confidentialityLevelsStore'
    },
    constructor: function() {
    	var me=this;   	
    	
    	// ensure loading of all confidentiality levels in the database
    	me.store.load({
    		params:{limit:50}
    	});
    	
		return me.callParent(arguments);
    }  
});
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
Ext.define('Ssp.controller.tool.actionplan.TasksViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	appEventsController: 'appEventsController',
    	formUtils: 'formRendererUtils',
    	model: 'currentTask',
    	personLite: 'personLite',
    	store: 'tasksStore' 
    },
    
    config: {
    	appEventsController: 'appEventsController',
    	containerToLoadInto: 'tools',
    	formToDisplay: 'addtask',
    	url: ''
    },
    
    control: {
    	view: {
    		viewready: 'onViewReady'
    	}
	},
	
	init: function() {
		this.url = this.apiProperties.createUrl( this.apiProperties.getItemUrl('personTask') );
		this.url = this.url.replace('{id}',this.personLite.get('id'));

		return this.callParent(arguments);
    },

    onViewReady: function(comp, obj){
    	this.appEventsController.assignEvent({eventName: 'addTask', callBackFunc: this.onAddTask, scope: this});
    	this.appEventsController.assignEvent({eventName: 'editTask', callBackFunc: this.onEditTask, scope: this});
    	this.appEventsController.assignEvent({eventName: 'closeTask', callBackFunc: this.onCloseTask, scope: this});
    	this.appEventsController.assignEvent({eventName: 'deleteTask', callBackFunc: this.deleteConfirmation, scope: this});
    },    
 
    destroy: function() {
    	this.appEventsController.removeEvent({eventName: 'addTask', callBackFunc: this.onAddTask, scope: this});
    	this.appEventsController.removeEvent({eventName: 'editTask', callBackFunc: this.onEditTask, scope: this});
    	this.appEventsController.removeEvent({eventName: 'closeTask', callBackFunc: this.onCloseTask, scope: this});
    	this.appEventsController.removeEvent({eventName: 'deleteTask', callBackFunc: this.deleteConfirmation, scope: this});

        return this.callParent( arguments );
    },
    
    onAddTask: function() {
    	var task = new Ssp.model.tool.actionplan.Task();
    	this.model.data = task.data;
    	this.loadEditor();
    },    
    
    onEditTask: function(){
 	   this.loadEditor();
    },
    
    onCloseTask: function() {
       var me=this;
	   var store, id, model, groupName;
       model = me.model;
       id = model.get('id');
	   store = me.store;
       if (id != "") 
       {
           model.set('completed',true);
           model.set('completedDate', new Date() );
           // remove group property before save, since
           // the group property was added dynamically for
           // sorting and will invalidate the model on the
           // server side.
           groupName = model.data.group;
           delete model.data.group;
		   this.apiProperties.makeRequest({
			   url: this.url+"/"+id,
			   method: 'PUT',
			   jsonData: model.data,
			   successFunc: function(response,responseText){
				   var r = Ext.decode(response.responseText);
				   // ensure proper save
				   if ( r.id != "" )
				   {
					   model.set( "completedDate", r.completedDate );
					   model.set( "completed", r.completed );
					   // reset the group for sorting purposes
					   model.set('group',groupName);
					   model.commit();
					   store.sync();
					   // filter the tasks, so the completed task is no longer
					   // listed
					   me.appEventsController.getApplication().fireEvent('filterTasks');					   
				   }
			   },
			   scope: me
		   });
       }else{
    	   Ext.Msg.alert('SSP Error', 'Unable to delete. No id was specified to delete.'); 
       }
    },    
    
    deleteConfirmation: function() {
        var message = 'You are about to delete the task: "'+ this.model.get('name') + '". Would you like to continue?';
    	var model = this.model;
        if (model.get('id') != "") 
        {
    		// test if this is a student task
     	   if ( model.get('createdBy').id == this.personLite.get('id') )
     	   {
     		   message = "WARNING: You are about to delete a task created by this student. Would you like to continue?"; 
     	   }
     	   
           Ext.Msg.confirm({
   		     title:'Delete Task?',
   		     msg: message,
   		     buttons: Ext.Msg.YESNO,
   		     fn: this.deleteTask,
   		     scope: this
   		   });
        }else{
     	   Ext.Msg.alert('SSP Error', 'Unable to delete task.'); 
        }
     },
     
     deleteTask: function( btnId ){
     	var store = this.store;
     	var id = this.model.get('id');
     	if (btnId=="yes")
     	{
         	this.apiProperties.makeRequest({
      		   url: this.url+"/"+id,
      		   method: 'DELETE',
      		   successFunc: function(response,responseText){
      			   store.remove( store.getById( id ) );
      		   }
      	    });    		
     	}
     },    
     
     loadEditor: function(){
     	var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {});    	
     }
});
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
Ext.define('Ssp.controller.tool.actionplan.AddTasksFormViewController', {
    extend: 'Deft.mvc.ViewController',	
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	apiProperties: 'apiProperties',
    	appEventsController: 'appEventsController',
    	authenticatedPerson: 'authenticatedPerson',
    	confidentialityLevelsStore: 'confidentialityLevelsStore',
    	formUtils: 'formRendererUtils',
    	model: 'currentTask',
    	personLite: 'personLite'
    },
    config: {
    	containerToLoadInto: 'tools',
    	formToDisplay: 'actionplan',
    	model: 'currentTask',
    	url: ''
    },    
    control: {  
    	'addButton': {
			click: 'onAddClick'
		},
		
		'closeButton': {
			click: 'onCloseClick'
		}
	},
 
	init: function(){
		var me=this;
		
		// apply confidentiality level filter
		me.authenticatedPerson.applyConfidentialityLevelsFilter( me.confidentialityLevelsStore );
		
		me.url = me.apiProperties.createUrl( me.apiProperties.getItemUrl('personTask') );
		me.url = me.url.replace('{id}',me.personLite.get('id'));
		
		me.initForm();
		
    	me.appEventsController.assignEvent({eventName: 'loadTask', callBackFunc: me.initForm, scope: me});    	
    	
		return me.callParent(arguments);
	},
	
    destroy: function() {
    	var me=this;  	

    	// clear confidentiality level filter
    	me.confidentialityLevelsStore.clearFilter();
    	
    	me.appEventsController.removeEvent({eventName: 'loadTask', callBackFunc: me.initForm, scope: me});
    	
        return me.callParent( arguments );
    },	
	
	initForm: function(){
		this.getView().getForm().reset();
		this.getView().getForm().loadRecord( this.model );
		Ext.ComponentQuery.query('#confidentialityLevel')[0].setValue( this.model.get('confidentialityLevel').id );
	},
    
    onAddClick: function(button){
    	var me=this;
    	var successFunc;
    	var form = this.getView().getForm();
    	var model = this.model;
    	var jsonData;
    	var id = model.get('id');
    	if ( form.isValid() )
    	{
    		form.updateRecord();
    		
			successFunc = function(response ,view){
		    	   Ext.Msg.confirm({
		    		     title:'Success',
		    		     msg: 'The task was saved successfully. Would you like to create another task?',
		    		     buttons: Ext.Msg.YESNO,
		    		     fn: me.createTaskConfirmResult,
		    		     scope: me
		    		});
			};	
    		
			// fix timestamp due to GMT Date, set to UTC Date
    		model.set('dueDate', me.formUtils.fixDateOffsetWithTime( model.data.dueDate ) );

    		if (id == "")
    		{
        		model.set('type','SSP');
        		model.set('personId', this.personLite.get('id') );
        		model.set('confidentialityLevel',{id: form.getValues().confidentialityLevelId});
    			// add the task
    			this.apiProperties.makeRequest({
	    			url: me.url,
	    			method: 'POST',
	    			jsonData: model.data,
	    			successFunc: successFunc
	    		});
    		}else{
    			
    			// This removes the group property from
    			// a TaskGroup item before it is saved
    			// as a Task. Task grouping is handled in the Tasks display.
        		if (model.data.group != null)
        			delete model.data.group;
        		        		
        		// edit the task
	    		this.apiProperties.makeRequest({
	    			url: me.url+"/"+id,
	    			method: 'PUT',
	    			jsonData: model.data,
	    			successFunc: successFunc
	    		});    			
    		}
    	}else{
    		Ext.Msg.alert('Error', 'Please correct the errors in your form before continuing.');
    	}
    },

    createTaskConfirmResult: function( btnId ){
    	if (btnId=="yes")
    	{
    		var task = new Ssp.model.tool.actionplan.Task();
    		this.model.data = task.data;
    		this.initForm();
    	}else{
    		this.loadDisplay();
    	}
    },    
    
    onCloseClick: function(button){
    	this.loadDisplay();
    },
    
    loadDisplay: function(){
    	var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {});    	
    }
});
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
Ext.define('Ssp.controller.tool.actionplan.EditGoalFormViewController', {
    extend: 'Deft.mvc.ViewController',	
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	apiProperties: 'apiProperties',
    	authenticatedPerson: 'authenticatedPerson',
    	confidentialityLevelsStore: 'confidentialityLevelsStore',
    	formUtils: 'formRendererUtils',
    	model: 'currentGoal',
    	personLite: 'personLite',
    	preferences: 'preferences'
    },
    config: {
    	containerToLoadInto: 'tools',
    	formToDisplay: 'actionplan',
    	url: ''
    },    
    control: {
    	combo: '#confidentialityLevel',
    	
    	'saveButton': {
			click: 'onSaveClick'
		},
		
		'cancelButton': {
			click: 'onCancelClick'
		}
	},
  
	init: function() {
		var me=this;
		
		// apply confidentiality level filter
		me.authenticatedPerson.applyConfidentialityLevelsFilter( me.confidentialityLevelsStore );
		
		me.getView().getForm().loadRecord( me.model );
		me.getCombo().setValue( this.model.get('confidentialityLevel').id );
		
		return me.callParent(arguments);
    },	
	
	constructor: function(){
		this.url = this.apiProperties.getItemUrl('personGoal');
		this.url = this.url.replace('{id}',this.personLite.get('id'));
    	this.url = this.apiProperties.createUrl( this.url );
	
		return this.callParent(arguments);
	},
    
    onSaveClick: function(button){
    	var me=this;
    	var model = this.model;
    	var form, url, goalId, successFunc;
    	form = this.getView().getForm();
    	id = model.get('id');
    	if ( form.isValid() )
    	{
    		var values = form.getValues();
    		model.set('name',values.name);
    		model.set('description',values.description);
    		model.set('confidentialityLevel',{id: values.confidentialityLevelId});
    		
    		successFunc = function(response ,view){
    			me.preferences.ACTION_PLAN_ACTIVE_VIEW=1;
    			me.loadDisplay();
			};
			
    		if (id == "")
    		{
    			// add
    			this.apiProperties.makeRequest({
	    			url: this.url,
	    			method: 'POST',
	    			jsonData: model.data,
	    			successFunc: successFunc
	    		});
    		}else{
    			// edit
	    		this.apiProperties.makeRequest({
	    			url: this.url+"/"+id,
	    			method: 'PUT',
	    			jsonData: model.data,
	    			successFunc: successFunc
	    		});    			
    		}
    	}else{
    		Ext.Msg.alert('Error', 'Please correct the errors in your form before continuing.');
    	}
    },
    
    onCancelClick: function(button){
    	this.loadDisplay();
    },
    
    loadDisplay: function(){
    	var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {});
    }
});
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
Ext.define('Ssp.controller.tool.actionplan.DisplayActionPlanViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	appEventsController: 'appEventsController',
    	authenticatedPerson: 'authenticatedPerson',
    	formUtils: 'formRendererUtils',
    	goalsStore: 'goalsStore',
        personLite: 'personLite',
    	store: 'tasksStore'
    },
    
    config: {
    	filteredTaskStatus: null,
    	filterAuthenticated: false,
    	personTaskUrl: '',
    	personTaskGroupUrl: '',
    	personEmailTaskUrl: '',
    	personPrintTaskUrl: ''
    },
    
    control: {
    	'taskStatusTabs': {
    		tabchange: 'onTaskStatusTabChange'
    	},
		
		'emailTasksButton': {
			click: 'onEmailTasksClick'
		},

		'printTasksButton': {
			click: 'onPrintTasksClick'
		},
		
		'filterTasksBySelfCheck': {
			change: 'onFilterTasksBySelfChange'
		},
    	
		addTaskButton: {
			selector: '#addTaskButton',
			listeners: {
				click: 'onAddTaskClick'
			}
		},
		
		goalsPanel: '#goalsPanel',
		activeTasksGrid: '#activeTasksGrid',
		completeTasksGrid: '#completeTasksGrid',
		allTasksGrid: '#allTasksGrid'
	},
	
	init: function() {
		var me = this;
		var personId;
		var child;
		var successFunc = function(response,view){
	    	var r, records;
	    	var groupedTasks=[];
	    	r = Ext.decode(response.responseText);
	    	
	    	// hide the loader
	    	me.getView().setLoading( false );
	    	
	    	if (r != null)
	    	{
	    		Ext.Object.each(r,function(key,value){
		    		var taskGroup = key;
		    		var tasks = value;
		    		Ext.Array.each(tasks,function(task,index){
		    			task.group=taskGroup;
		    			groupedTasks.push(task);
		    		},this);
		    	},this);		    		

	    		me.store.loadData(groupedTasks);
	    		me.filteredTaskStatus="ACTIVE";
	    		me.filterTasks();
	    	}
		};
	
    	me.getAddTaskButton().setDisabled( !me.authenticatedPerson.hasPermission('ROLE_PERSON_TASK_WRITE') );
    	
		// clear any existing tasks
		me.store.removeAll();

		// display loader
		me.getView().setLoading( true );
		
		personId = me.personLite.get('id');
		me.personTaskUrl = me.apiProperties.getItemUrl('personTask');
		me.personTaskUrl = me.personTaskUrl.replace('{id}',personId);
		me.personTaskGroupUrl = me.apiProperties.getItemUrl('personTaskGroup');
		me.personTaskGroupUrl = me.personTaskGroupUrl.replace('{id}',personId);
		me.personEmailTaskUrl = me.apiProperties.getItemUrl('personEmailTask');
		me.personEmailTaskUrl = me.personEmailTaskUrl.replace('{id}',personId);	
		me.personPrintTaskUrl = me.apiProperties.getItemUrl('personPrintTask');
		me.personPrintTaskUrl = me.personPrintTaskUrl.replace('{id}',personId);
		
		me.apiProperties.makeRequest({
			url: me.apiProperties.createUrl(me.personTaskGroupUrl),
			method: 'GET',
			successFunc: successFunc 
		});
			
    	me.appEventsController.assignEvent({eventName: 'filterTasks', callBackFunc: this.onFilterTasks, scope: this});		
		
		return me.callParent(arguments);
    },
    
    destroy: function(){
    	this.appEventsController.removeEvent({eventName: 'filterTasks', callBackFunc: this.onFilterTasks, scope: this});    	
    },
    
    onFilterTasks: function(){
    	this.filterTasks();
    },
    
    onFilterTasksBySelfChange: function(comp, newComp, oldComp, eOpts){
		this.filterAuthenticated=!this.filterAuthenticated;
		this.filterTasks();
	},
    
    onTaskStatusTabChange: function(panel, newComp, oldComp, eOpts) {
		this.filteredTaskStatus = newComp.action.toUpperCase();
		this.filterTasks();
    },
    
    filterTasks: function(){
    	var me=this;
    	var filtersArr = [];
		var filterStatusFunc = null;
		var authenticatedId = me.authenticatedPerson.get('id');
		var filterAuthenticatedFunc = new Ext.util.Filter({
		    filterFn: function(item) {
				return (item.get('createdBy').id == authenticatedId); 
			},
			scope: me
		});

		switch (me.filteredTaskStatus)
		{
			case 'ACTIVE':
				filterStatusFunc = function(item) { return (item.get('completed') == false); };
				break;
			
			case 'COMPLETE':
				filterStatusFunc = function(item) { return (item.get('completed') == true); };
				break;
		}
		
		if (filterStatusFunc != null)
			filtersArr.push(filterStatusFunc);
		
		if (me.filterAuthenticated == true)
			filtersArr.push(filterAuthenticatedFunc);
		
		// reset filter
		me.store.clearFilter();
		
		// apply new filters
		me.store.filter(filtersArr);
    },

    onEmailTasksClick: function(button) {
    	var me=this;
    	var msg = me.getTaskGoalCountNotificationMessage();
		if (msg.length > 0)
		{
	           Ext.Msg.confirm({
	     		     title:' Would you like to continue emailing?',
	     		     msg: msg,
	     		     buttons: Ext.Msg.YESNO,
	     		     fn: me.emailTasksConfirm,
	     		     scope: me
	     		   });
		}else{
			me.displayEmailAddressWindow();
		}
    },
    
    emailTasksConfirm: function( btnId ){
     	var me=this;
     	if (btnId=="yes")
     	{
         	me.displayEmailAddressWindow();    		
     	}
     }, 
    
    displayEmailAddressWindow: function(){
    	var me=this;
    	Ext.create('Ext.window.Window', {
		    title: 'To whom would you like to send this Action Plan',
		    height: 200,
		    width: 400,
		    layout: 'fit',
		    modal: true,
		    items:[{
		        xtype:'form',
		        layout:'anchor',
		        items :[{
		            xtype: 'label',
		            text: 'Enter recipient email addresses separated by a comma'
		            
		        },{
		            xtype: 'textarea',
		            anchor: '100%',
		            name: 'recipientEmailAddresses'
		        }]
			}],
			dockedItems: [{
		        dock: 'bottom',
		        xtype: 'toolbar',
		        items: [{
		            text: 'Send',
		            xtype: 'button',
		            handler: me.emailTaskList,
		            scope: me
		        },{
		            text: 'Cancel',
		            xtype: 'button',
		            handler: function(button){
		            	button.up('window').close();
		            }
		        }]
    	    }]
		}).show();
    },
    
    emailTaskList: function( button ){
	    var me=this;
    	var valid = false;
	    var jsonData;
	    var emailTestArr;
	    var arrRecipientEmailAddresses = [];
	    var recipientEmailAddresses = button.up('panel').down('form').getValues().recipientEmailAddresses;
	    if (recipientEmailAddresses != null)
	    {
	    	// validate email addresses
		    if ( recipientEmailAddresses.indexOf(",") )
		    {
		    	emailTestArr = recipientEmailAddresses.split(',');
		    	// handle a list of email addresses
		    	Ext.each(emailTestArr,function(emailAddress,index){
		    		valid = this.validateEmailAddress( emailAddress );
		    		arrRecipientEmailAddresses.push( Ext.String.trim(emailAddress) );
		    		if (valid != true)
		    			return;
		    	}, this);
		    }else{
		    	valid = this.validateEmailAddress( recipientEmailAddresses );
		    	arrRecipientEmailAddresses.push( Ext.String.trim( recipientEmailAddresses ) );
		    }
		    
		    // define data to email
			jsonData = {
	    			"taskIds": me.getSelectedTasks(),
	    		    "goalIds": me.getSelectedGoals(),
	    		    "recipientIds": [],
					"recipientEmailAddresses": arrRecipientEmailAddresses
			};

		    if (valid==true)
		    {
		    	// email the task list
	    		url = this.apiProperties.createUrl( this.personEmailTaskUrl );
		    	this.apiProperties.makeRequest({
					url: url,
					method: 'POST',
					jsonData: jsonData,
					successFunc: function(){
						button.up('window').close();
						Ext.Msg.alert('Success','The task list has been sent to the listed recipient(s).');
					}
				});
		    }else{
		    	Ext.Msg.alert('Error','1 or more of the addresses you entered are invalid. Please correct the form and try again.');		    	
		    }	
	    }else{
	    	Ext.Msg.alert('Error','Unable to determine an email address please enter a valid email address.');
	    }
    },
    
    validateEmailAddress: function( value ){
    	var emailExpression = filter = new RegExp('^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$');
    	return emailExpression.test( value );
    },
    
    onPrintTasksClick: function(button) {
    	var me=this;
    	var msg = me.getTaskGoalCountNotificationMessage();
		if (msg.length > 0)
		{
           Ext.Msg.confirm({
     		     title:' Would you like to continue printing??',
     		     msg: msg,
     		     buttons: Ext.Msg.YESNO,
     		     fn: me.printTasksConfirm,
     		     scope: me
     		   });
		}else{
			me.printTasks();
		}
    },
 
    printTasksConfirm: function( btnId ){
     	var me=this;
     	if (btnId=="yes")
     	{
         	me.printTasks();    		
     	}
     },    
    
    printTasks: function() {
    	var me=this;
    	var url, jsonData;	
		var jsonData = {
				"taskIds": me.getSelectedTasks(),
		        "goalIds": me.getSelectedGoals()
		        };
 
    	url = me.apiProperties.createUrl( me.personPrintTaskUrl );

		me.apiProperties.getReporter().postReport({
			url: url,
			params: jsonData
		});
    },
    
    getTaskGoalCountNotificationMessage: function(){
		var me=this;
    	// if no tasks or goals have been added to the student's record
		// then display a notification to first add tasks and goals before
		// printing
		var notificationMsg = "";
		if ( me.store.getCount() < 1 )
		{
			notificationMsg += "This student has " + me.store.getCount() + " assigned tasks.";
		}
		
		if ( me.goalsStore.getCount() < 1 )
		{
			notificationMsg += "This student has " + me.goalsStore.getCount() + " assigned goals.";
		}
		
		return notificationMsg;
    },
		
    getSelectedTasks: function(){
    	var me=this;
    	var activeTasksGrid = me.getActiveTasksGrid();
		var completeTasksGrid = me.getCompleteTasksGrid();
		var allTasksGrid = me.getAllTasksGrid();
		var activeTaskIds = me.getSelectedIdsArray( activeTasksGrid.getView().getSelectionModel().getSelection() );
		var completeTaskIds = me.getSelectedIdsArray( completeTasksGrid.getView().getSelectionModel().getSelection() );
		var allTaskIds = me.getSelectedIdsArray( allTasksGrid.getView().getSelectionModel().getSelection() );
		var taskIds = Ext.Array.merge( activeTaskIds, completeTaskIds, allTaskIds);
		return taskIds;    	
    },
    
    getSelectedGoals: function(){
		var me=this;
    	var goalsPanel = me.getGoalsPanel();
		return me.getSelectedIdsArray( goalsPanel.getView().getSelectionModel().getSelection() );
    },
    
    getSelectedIdsArray: function(arr){
		var selectedIds = [];
		Ext.each(arr, function(item, index) {
			selectedIds.push( item.get('id') );
		});
		
		return selectedIds;
    },
    
    onAddTaskClick: function(button) {
    	this.appEventsController.getApplication().fireEvent('addTask');
    },  
});
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
Ext.define('Ssp.controller.tool.actionplan.DisplayActionPlanGoalsViewController', {
    extend: 'Deft.mvc.ViewController',	
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	apiProperties: 'apiProperties',
    	appEventsController: 'appEventsController',
    	authenticatedPerson: 'authenticatedPerson',
    	formUtils: 'formRendererUtils',
    	model: 'currentGoal',
    	personLite: 'personLite',
    	preferences: 'preferences',
    	store: 'goalsStore'
    },
    config: {
    	containerToLoadInto: 'tools',
    	formToDisplay: 'editgoalform',
    	url: ''
    },
    control: {
		view: {
			viewready: 'onViewReady'
		},
    	
    	addGoalButton:{
    		selector: '#addGoalButton',
    		listeners: {
    			click: 'onAddGoalClick'
    		}
    	}
    },
    
    constructor: function() {
    	// reconfigure the url for the current person
    	this.url = this.apiProperties.createUrl(this.apiProperties.getItemUrl('personGoal'));
    	this.url = this.url.replace('{id}',this.personLite.get('id'));
    	
    	// apply the person url to the store proxy
    	Ext.apply(this.store.getProxy(), { url: this.url });

    	// load records
    	this.store.load();

		return this.callParent(arguments);
    },

    onViewReady: function(comp, obj){
    	var me=this;
    	me.getAddGoalButton().setDisabled( !me.authenticatedPerson.hasPermission('ROLE_PERSON_GOAL_WRITE') );
    	
    	me.appEventsController.assignEvent({eventName: 'editGoal', callBackFunc: this.editGoal, scope: this});
    	me.appEventsController.assignEvent({eventName: 'deleteGoal', callBackFunc: this.deleteConfirmation, scope: this});
    
    	// display the goals pane if a goal was added to the student's record
    	if ( me.preferences.ACTION_PLAN_ACTIVE_VIEW == 1 )
    	{
    		// reset to the tasks view
    		me.preferences.ACTION_PLAN_ACTIVE_VIEW=0;
    		me.getView().expand();
    	}
    },
    
    destroy: function() {
    	this.appEventsController.removeEvent({eventName: 'editGoal', callBackFunc: this.editGoal, scope: this});
    	this.appEventsController.removeEvent({eventName: 'deleteGoal', callBackFunc: this.deleteConfirmation, scope: this});

        return this.callParent( arguments );
    },
    
    onAddGoalClick: function( button ){
		var goal = new Ssp.model.PersonGoal();
		this.model.data = goal.data;
		this.loadEditor();
    },
 
    editGoal: function(){
  	   this.loadEditor();
    },

    deleteConfirmation: function() {
       if (this.model.get('id') != "") 
       {
    	   Ext.Msg.confirm({
    		     title:'Delete Goal?',
    		     msg: 'You are about to delete the goal: "'+ this.model.get('name') + '". Would you like to continue?',
    		     buttons: Ext.Msg.YESNO,
    		     fn: this.deleteGoal,
    		     scope: this
    		});
       }else{
    	   Ext.Msg.alert('SSP Error', 'Unable to delete goal.'); 
       }
    },
    
    deleteGoal: function( btnId ){
    	var store = this.store;
    	var id = this.model.get('id');
    	if (btnId=="yes")
    	{
        	this.apiProperties.makeRequest({
     		   url: this.url+"/"+id,
     		   method: 'DELETE',
     		   successFunc: function(response,responseText){
     			  store.remove( store.getById( id ) );
     		   }
     	    });    		
    	}
    },
    
    loadEditor: function(){
    	var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {});    	
    }
});
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
Ext.define('Ssp.controller.tool.actionplan.DisplayStrengthsViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	authenticatedPerson: 'authenticatedPerson',
    	formUtils: 'formRendererUtils',
    	model: 'currentPerson',
        personLite: 'personLite',
    	service: 'personService'
    },
    
    control: {  	
    	saveButton: {
    		selector: '#saveButton',
    		listeners: {
    			click: 'onSaveClick'
    		}
    	},
    	
    	strengthsField: {
    		selector: '#strengths',
    		listeners: {
    			change: 'onStrengthsChange'
    		}
    	},
    	
    	saveSuccessMessage: '#saveSuccessMessage'
	},
	
	init: function() {
		var me=this;
        me.getSaveButton().disabled=true;
        me.getStrengthsField().setDisabled( !me.authenticatedPerson.hasAccess('ACTION_PLAN_STRENGTHS_FIELD') );

        // display loader
        me.getView().setLoading(true);
        if ( !(me.model) || !(me.model.get('id')) || !(me.personLite.get('id') === me.model.get('id')) ) {

            me.service.get(me.personLite.get('id'), {
                success: me.loadPersonSuccess,
                failure: me.loadPersonFailure,
                scope: me
            });
        } else {
            me.bindModelToView();
        }


		return me.callParent(arguments);
    },

    loadPersonSuccess: function(response, scope) {
        var me = scope;
        me.model.populateFromGenericObject(response);
        me.bindModelToView();
    },

    bindModelToView: function() {
        var me = this;
        me.getView().getForm().loadRecord( me.model );
        me.getView().setLoading(false);
    },

    loadPersonFailure: function(response, scope) {
        var me = scope;
        me.getView().setLoading(false);
    },
    
    onSaveClick: function(button) {
		var me=this;
		var form = me.getView().getForm();
		var jsonData;
		if (form.isValid())
		{
			form.updateRecord();
			jsonData = me.model.data;
			jsonData = me.model.setPropsNullForSave( me.model.data );
			me.getView().setLoading(true);
			me.service.save( jsonData , {
				success: me.savePersonSuccess,
				failure: me.savePersonFailure,
				scope: me
			});	
		}else{
			Ext.Msg.alert('Unable to save strengths. Please correct the errors in the form.');
		}	
    },

    savePersonSuccess: function( r, scope){
    	var me=scope;
    	me.getView().setLoading( false );
		me.model.commit();
		me.setSaveButtonState();
		me.formUtils.displaySaveSuccessMessage( me.getSaveSuccessMessage() );
    },

    savePersonFailure: function( response, scope ){
    	var me=scope;
    	me.getView().setLoading( false );
    },    
    
    onStrengthsChange: function(comp, oldValue, newValue, eOpts){
    	this.setSaveButtonState();
    },
    
    setSaveButtonState: function(){
    	this.getSaveButton().disabled = !this.getView().getForm().isDirty();
    }
});
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
Ext.define('Ssp.controller.tool.actionplan.TaskTreeViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
        appEventsController: 'appEventsController',
        personLite: 'personLite',
        task: 'currentTask',
    	treeUtils: 'treeRendererUtils'
    },
    config: {
    	categoryUrl: '',
    	challengeUrl: '',
    	challengeReferralUrl: '',
    	personChallengeUrl: ''
    },
    control: {
    	view: {
    		itemexpand: 'onItemExpand',
    		itemClick: 'onItemClick',
    		viewready: 'onViewReady'
    	},
   	
    	/*
    	'searchButton': {
			click: 'onSearchClick'
		}
		*/  	
    },
    
	onViewReady: function() {
		var rootNode = null;
		
		this.categoryUrl = this.apiProperties.getItemUrl('category');
		this.challengeUrl = this.apiProperties.getItemUrl('challenge');
		this.challengeReferralUrl = this.apiProperties.getItemUrl('challengeReferral');
		this.personChallengeUrl = this.apiProperties.getItemUrl('personChallenge');
		this.personChallengeUrl = this.personChallengeUrl.replace('{id}',this.personLite.get('id'));

		// clear the categories
		this.treeUtils.clearRootCategories();

    	// load student intake challenges
     	this.treeUtils.appendChildren(null,[{
	        text: 'Student Intake Challenges',
	        id: '0'+'_studentIntakeChallenge',
	        leaf: false,
	        destroyBeforeAppend: false
	      }]);
   
    	// load "all" challenges category
     	this.treeUtils.appendChildren(null,[{
	        text: 'All',
	        id: '0'+'_all',
	        leaf: false,
	        destroyBeforeAppend: false
	      }]);     	
     	
    	// load the categories
    	var treeRequest = new Ssp.model.util.TreeRequest();
    	treeRequest.set('url', this.categoryUrl);
    	treeRequest.set('nodeType','category');
    	treeRequest.set('isLeaf', false);
    	treeRequest.set('enableCheckedItems', false);	
    	this.treeUtils.getItems( treeRequest );
    },
    
    onItemExpand: function(nodeInt, obj){
    	var me=this;
    	var node = nodeInt;
    	var url = "";
    	var nodeType = "";
    	var isLeaf = false;
    	var nodeName =  me.treeUtils.getNameFromNodeId( node.data.id );
    	var id = me.treeUtils.getIdFromNodeId( node.data.id );
    	var treeRequest = new Ssp.model.util.TreeRequest();
    	var includeToolTip = false;
    	var toolTipFieldName = "";
    	switch ( nodeName )
    	{
    		case 'category':
    			url = me.categoryUrl + '/' + id + '/challenge/';
    			nodeType = 'challenge';
    			break;
    			
    		case 'studentIntakeChallenge':
    			url = me.personChallengeUrl;
    			nodeType = 'challenge';
     			break;

    		case 'all':
    			url = me.challengeUrl;
    			nodeType = 'challenge';
     			break;     			
     			
    		case 'challenge':
    			url = me.challengeUrl + '/' + id + '/challengeReferral/';
    			nodeType = 'referral';
    			isLeaf = true;
    			includeToolTip = true;
    			toolTipFieldName = "description";
    			break;
    	}
    	
    	if (url != "")
    	{
        	treeRequest.set('url', url);
        	treeRequest.set('nodeType', nodeType);
        	treeRequest.set('isLeaf', isLeaf);
        	treeRequest.set('nodeToAppendTo', node);
        	treeRequest.set('enableCheckedItems',false);
        	treeRequest.set('callbackFunc', me.onLoadComplete);
        	treeRequest.set('callbackScope', me);
        	treeRequest.set('includeToolTip', includeToolTip);
        	treeRequest.set('toolTipFieldName', toolTipFieldName);
        	me.treeUtils.getItems( treeRequest );
        	me.getView().setLoading( true );        	
    	}
    },
    
    onLoadComplete: function( scope ){
    	scope.getView().setLoading( false );
    },
    
    /*
    onSearchClick: function(){
    	Ext.Msg.alert('Attention', 'This is a beta item. Awaiting API methods to utilize for search.'); 
    },
    */
    
    onItemClick: function(view, record, item, index, e, eOpts){
    	var me=this;
    	var successFunc;
    	var name = me.treeUtils.getNameFromNodeId( record.data.id );
    	var id = me.treeUtils.getIdFromNodeId( record.data.id );
    	var challengeId = me.treeUtils.getIdFromNodeId( record.data.parentId );
    	var confidentialityLevelId = Ssp.util.Constants.DEFAULT_SYSTEM_CONFIDENTIALITY_LEVEL_ID;
    	if (name=='referral')
    	{
	    	successFunc = function(response,view){
		    	var r = Ext.decode(response.responseText);
		    	var challengeReferral = null;
		    	if (r.rows != null)
		    	{
		    		Ext.Array.each(r.rows,function(item,index){
		    			if (item.id==id)
		    			{
		    				challengeReferral = item;
		    			}
		    		});
		    		if (challengeReferral != null)
		    		{
			    		me.task.set('name', challengeReferral.name);
			    		me.task.set('description', challengeReferral.description);
			    		me.task.set('link', challengeReferral.link);
			    		me.task.set('challengeReferralId', challengeReferral.id);
		    		}
		    		me.task.set('challengeId', challengeId);
		    		me.task.set('confidentialityLevel', {id: confidentialityLevelId});
		    		me.appEventsController.getApplication().fireEvent('loadTask');
		    	}		
			};
	    	
			
			// TODO: This fix is a temp solution for the SSP-381
			// but that returns a 405 method not allowed
			// for get and put calls that require an id.
			// This method call should be replaced with a
			// get call after the 381 bug has been resolved.
			// Note: the issue appears to work fine under
			// most local environments and not under a number
			// of server environments where SSP has been deployed.
	    	me.apiProperties.makeRequest({
				url: me.apiProperties.createUrl( me.challengeReferralUrl ), // +'/'+id
				method: 'GET',
				jsonData: '',
				successFunc: successFunc 
			});
    	
    	}
    }
});
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
Ext.define('Ssp.controller.tool.studentintake.StudentIntakeToolViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
        apiProperties: 'apiProperties',
        authenticatedPerson: 'authenticatedPerson',
        appEventsController: 'appEventsController',
        challengesStore: 'challengesStore',
    	childCareArrangementsStore: 'childCareArrangementsStore',
    	citizenshipsStore: 'citizenshipsStore',
    	educationGoalsStore: 'educationGoalsStore',
    	educationLevelsStore: 'educationLevelsStore',
    	employmentShiftsStore: 'employmentShiftsStore',
    	ethnicitiesStore: 'ethnicitiesStore',
    	formUtils: 'formRendererUtils',
    	fundingSourcesStore: 'fundingSourcesStore',
    	gendersStore: 'gendersStore',
    	maritalStatusesStore: 'maritalStatusesStore',
    	militaryAffiliationsStore: 'militaryAffiliationsStore',
        personLite: 'personLite',
        person: 'currentPerson',
        statesStore: 'statesStore',
        service: 'studentIntakeService',
        studentStatusesStore: 'studentStatusesStore',
        studentIntake: 'currentStudentIntake',
    	veteranStatusesStore: 'veteranStatusesStore'        
    }, 
    config: {
    	studentIntakeForm: null
    },
    control: {
		'saveButton': {
			click: 'onSaveClick'
		},
		
    	'cancelButton': {
    		click: 'onCancelClick'
    	},
    	
    	saveSuccessMessage: '#saveSuccessMessage'
	},
    
	init: function() {
		var me=this;	
		
		// Load the views dynamically
		// otherwise duplicate id's will be registered
		// on cancel
		me.initStudentIntakeViews();
	
		// This enables mapped text fields and mapped text areas
		// to be shown or hidden upon selection from a parent object
		// such as a dynamic check box.
		me.appEventsController.getApplication().addListener('dynamicCompChange', function( comp ){
			var tfArr = Ext.ComponentQuery.query('.mappedtextfield');
			var taArr = Ext.ComponentQuery.query('.mappedtextarea');
			
			// show or hide mapped text fields
			Ext.each(tfArr,function(item, index){
				if (comp.id==item.parentId)
				{
					if(comp.checked)
					{
						item.show();
						Ext.apply(item,{allowBlank:false});
					}else{
						item.hide();
						Ext.apply(item,{allowBlank:true});
					}
				}	
			},this);
			
			// show or hide mapped text area components
			Ext.each(taArr,function(item, index){
				if (comp.id==item.parentId)
				{
					if(comp.checked)
					{
						item.show();
					}else{
						item.hide();
					}
				}	
			},this);
		},me);
		
		// display loader
		me.getView().setLoading( true );
		
		me.service.get(me.personLite.get('id'),{
			success: me.getStudentIntakeSuccess,
			failure: me.getStudentIntakeFailure,
			scope: me
		});
		
		return me.callParent(arguments);
    },

    destroy: function() {
    	//this.appEventsController.removeEvent({eventName: 'dynamicCompChange', callBackFunc: this.onDynamicCompChange, scope: this});

        return this.callParent( arguments );
    },     
    
    initStudentIntakeViews: function(){
    	var me=this;
    	var items = [ Ext.createWidget('tabpanel', {
	        width: '100%',
	        height: '100%',
	        activeTab: 0,
			border: 0,
	        items: [ { title: 'Personal'+Ssp.util.Constants.REQUIRED_ASTERISK_DISPLAY,
	        		   autoScroll: true,
	        		   items: [{xtype: 'studentintakepersonal'}]
	        		},{
	            		title: 'Demographics',
	            		autoScroll: true,
	            		items: [{xtype: 'studentintakedemographics'}]
	        		},{
	            		title: 'EduPlan',
	            		autoScroll: true,
	            		items: [{xtype: 'studentintakeeducationplans'}]
	        		},{
	            		title: 'EduLevel',
	            		autoScroll: true,
	            		items: [{xtype: 'studentintakeeducationlevels'}]
	        		},{
	            		title: 'EduGoal',
	            		autoScroll: true,
	            		items: [{xtype: 'studentintakeeducationgoals'}]
	        		},{
	            		title: 'Funding',
	            		autoScroll: true,
	            		items: [{xtype: 'studentintakefunding'}]
	        		},{
	            		title: 'Challenges',
	            		autoScroll: true,
	            		hidden: !me.authenticatedPerson.hasAccess('STUDENT_INTAKE_CHALLENGE_TAB'),
	            		items: [{xtype: 'studentintakechallenges'}]
	        		}]
		    })
	    
		];
    	
    	me.getView().add( items );
    },
    
    getStudentIntakeSuccess: function( r, scope ){
    	var me=scope;
    	var studentIntakeClass;
		
    	// hide the loader
    	me.getView().setLoading( false );
    	
    	if ( r != null )
    	{  		
        	studentIntakeClass = Ext.ModelManager.getModel('Ssp.model.tool.studentintake.StudentIntakeForm');
    		me.studentIntake.data = studentIntakeClass.getProxy().getReader().read( r ).records[0].data;
    		me.buildStudentIntake( me.studentIntake );    		
    	}else{
    		Ext.Msg.alert('Error','There was an error loading the Student Intake form for this student.');
    	}
	},
	
	getStudentIntakeFailure: function( response, scope){
		var me=scope;
		me.getView().setLoading( false );
	},
    
	buildStudentIntake: function( formData ){
		var me=this; // formData

    	// PERSON RECORD
		var person = formData.data.person;
		var personDemographics = formData.data.personDemographics;
		var personEducationPlan = formData.data.personEducationPlan;
		var personEducationGoal = formData.data.personEducationGoal;
		var personEducationLevels = formData.data.personEducationLevels;
		var personFundingSources = formData.data.personFundingSources;
		var personChallenges = formData.data.personChallenges;
		var personEducationGoalId = "";
		
		var studentIntakeEducationPlansForm = Ext.getCmp('StudentIntakeEducationPlans');
		var studentIntakeDemographicsForm = Ext.getCmp('StudentIntakeDemographics');
		var studentIntakeEducationGoalsForm = Ext.getCmp('StudentIntakeEducationGoals');
		
		var educationGoalFormProps;
		var educationGoalsAdditionalFieldsMap;
		var educationLevelFormProps;
		var educationLevelsAdditionalFieldsMap;
		var fundingSourceFormProps;
		var fundingSourcesAdditionalFieldsMap;
		var challengeFormProps;
		var challengesAdditionalFieldsMap;
		var defaultLabelWidth;

		// REFERENCE OBJECTS
		var challenges = me.formUtils.alphaSortByField( formData.data.referenceData.challenges, 'name' );
		var educationGoals = me.formUtils.alphaSortByField( formData.data.referenceData.educationGoals, 'name' );
		var educationLevels = me.formUtils.alphaSortByField( formData.data.referenceData.educationLevels, 'name' );
		var fundingSources = me.formUtils.alphaSortByField( formData.data.referenceData.fundingSources, 'name' );
		var studentStatuses =  me.formUtils.alphaSortByField( formData.data.referenceData.studentStatuses, 'name' );
		var militaryAffiliations = me.formUtils.alphaSortByField( formData.data.referenceData.militaryAffiliations, 'name' );
		
		me.challengesStore.loadData( challenges );
		me.childCareArrangementsStore.loadData( formData.data.referenceData.childCareArrangements );
		me.citizenshipsStore.loadData( formData.data.referenceData.citizenships );
		me.educationGoalsStore.loadData( educationGoals );
		me.educationLevelsStore.loadData( educationLevels );
		me.employmentShiftsStore.loadData( formData.data.referenceData.employmentShifts );
		me.ethnicitiesStore.loadData( formData.data.referenceData.ethnicities );
		me.fundingSourcesStore.loadData( fundingSources );
		me.gendersStore.loadData( formData.data.referenceData.genders );
		me.maritalStatusesStore.loadData( formData.data.referenceData.maritalStatuses );
		me.militaryAffiliationsStore.loadData( militaryAffiliations );
		me.statesStore.loadData( formData.data.referenceData.states );
		me.studentStatusesStore.loadData( studentStatuses );
		me.veteranStatusesStore.loadData( formData.data.referenceData.veteranStatuses ); 
		
		// LOAD RECORDS FOR EACH OF THE FORMS
		
		// format the dates
		Ext.getCmp('StudentIntakePersonal').loadRecord( person );
		
		if ( personDemographics != null && personDemographics != undefined ){
			studentIntakeDemographicsForm.loadRecord( personDemographics );
		}

		if ( personEducationPlan != null && personEducationPlan != undefined )
		{
			studentIntakeEducationPlansForm.loadRecord( personEducationPlan );
		}
		
		if ( personEducationGoal != null && personEducationGoal != undefined )
		{
			studentIntakeEducationGoalsForm.loadRecord( personEducationGoal );
			if (personEducationGoal.get('educationGoalId') != null)
			{
				personEducationGoalId = personEducationGoal.get('educationGoalId');
			}			 
		}

		defaultLabelWidth = 150;

		educationGoalsAdditionalFieldsMap = [
		     {
		      parentId: Ssp.util.Constants.EDUCATION_GOAL_BACHELORS_DEGREE_ID, 
			  parentName: "bachelor",
			  name: "description", 
			  label: "Describe bachelor's goal", 
			  fieldType: "mappedtextfield",
			  labelWidth: 200
			 },
		     {
			      parentId: Ssp.util.Constants.EDUCATION_GOAL_MILITARY_ID, 
				  parentName: "military",
				  name: "description", 
				  label: "Describe military goal", 
				  fieldType: "mappedtextfield",
				  labelWidth: 200
			 },
			 {
		      parentId: Ssp.util.Constants.EDUCATION_GOAL_OTHER_ID, 
			  parentName: "other",
			  name: "description", 
			  label: "Decribe your other goal", 
			  fieldType: "mappedtextfield",
			  labelWidth: 200
			 }
		];
		
		educationGoalFormProps = {
				mainComponentType: 'radio',
			    formId: 'StudentIntakeEducationGoals',
                itemsArr: educationGoals,
                selectedItemId: personEducationGoalId,
                idFieldName: 'id',
                selectedItemsArr: [ personEducationGoal.data ],
                selectedIdFieldName: 'educationGoalId',
                additionalFieldsMap: educationGoalsAdditionalFieldsMap,
                radioGroupId: 'StudentIntakeEducationGoalsRadioGroup',
                radioGroupFieldSetId: 'StudentIntakeEducationGoalsFieldSet'};		
		
		me.formUtils.createForm( educationGoalFormProps );	

		educationLevelsAdditionalFieldsMap = [{parentId: Ssp.util.Constants.EDUCATION_LEVEL_NO_DIPLOMA_GED_ID, 
			                                   parentName: "no diploma/no ged", 
			                                   name: "lastYearAttended", 
			                                   label: "Last Year Attended",
			                                   fieldType: "mappedtextfield", 
			                                   labelWidth: defaultLabelWidth,
			                                   validationExpression: '^(19|20)\\d{2}$',
			                                   validationErrorMessage: "This field requires a valid year."},
		                                      {parentId: Ssp.util.Constants.EDUCATION_LEVEL_NO_DIPLOMA_GED_ID, 
			                                   parentName: "no diploma/no ged", 
			                                   name: "highestGradeCompleted", 
			                                   label: "Highest Grade Completed", 
			                                   fieldType: "mappedtextfield", 
			                                   labelWidth: defaultLabelWidth,
                                               validationExpression: '^([0-9]|1[0-6])$',
                                               validationErrorMessage: 'This field requires a numeric value between 0 and 16'},
		                                      {parentId: Ssp.util.Constants.EDUCATION_LEVEL_GED_ID, 
			                                   parentName: "ged", 
			                                   name: "graduatedYear", 
			                                   label: "Year of GED", 
			                                   fieldType: "mappedtextfield",
			                                   labelWidth: defaultLabelWidth,
			                                   validationExpression: '^(19|20)\\d{2}$',
			                                   validationErrorMessage: "This field requires a valid year."},
		                                      {parentId: Ssp.util.Constants.EDUCATION_LEVEL_HIGH_SCHOOL_GRADUATION_ID, 
			                                   parentName: "high school graduation", 
			                                   name: "graduatedYear", 
			                                   label: "Year Graduated", 
			                                   fieldType: "mappedtextfield",
			                                   labelWidth: defaultLabelWidth,
			                                   validationExpression: '^(19|20)\\d{2}$',
			                                   validationErrorMessage: "This field requires a valid year."},
		     		                        {parentId: Ssp.util.Constants.EDUCATION_LEVEL_HIGH_SCHOOL_GRADUATION_ID, 
			                                 parentName: "high school graduation", 
			                                 name: "schoolName", 
			                                 label: "High School Attended", 
			                                 fieldType: "mappedtextfield",
			                                 labelWidth: defaultLabelWidth},
		     		                        {parentId: Ssp.util.Constants.EDUCATION_LEVEL_SOME_COLLEGE_CREDITS_ID, 
			                                 parentName: "some college credits", 
			                                 name: "lastYearAttended", 
			                                 label: "Last Year Attended", 
			                                 fieldType: "mappedtextfield",
			                                 labelWidth: defaultLabelWidth,
			                                 validationExpression: '^(19|20)\\d{2}$',
			                                 validationErrorMessage: "This field requires a valid year."},
		     		                        {parentId: Ssp.util.Constants.EDUCATION_LEVEL_OTHER_ID, 
			                                 parentName: "other", 
			                                 name: "description", 
			                                 label: "Please Explain", 
			                                 fieldType: "mappedtextarea",
			                                 labelWidth: defaultLabelWidth}];		
		
		educationLevelFormProps = {
				mainComponentType: 'checkbox',
			    formId: 'StudentIntakeEducationLevels', 
                fieldSetTitle: 'Education level completed: Select all that apply',
                itemsArr: educationLevels, 
                selectedItemsArr: personEducationLevels, 
                idFieldName: 'id', 
                selectedIdFieldName: 'educationLevelId',
                additionalFieldsMap: educationLevelsAdditionalFieldsMap };
		
		me.formUtils.createForm( educationLevelFormProps );

		fundingSourcesAdditionalFieldsMap = [{parentId: Ssp.util.Constants.FUNDING_SOURCE_OTHER_ID, 
											  parentName: "other",
											  name: "description", 
											  label: "Please Explain", 
											  fieldType: "mappedtextarea",
											  labelWidth: defaultLabelWidth}];
		
		fundingSourceFormProps = {
				mainComponentType: 'checkbox',
				formId: 'StudentIntakeFunding', 
                fieldSetTitle: 'How will you pay for college?',
                itemsArr: fundingSources, 
                selectedItemsArr: personFundingSources, 
                idFieldName: 'id', 
                selectedIdFieldName: 'fundingSourceId',
                additionalFieldsMap: fundingSourcesAdditionalFieldsMap };
		
		me.formUtils.createForm( fundingSourceFormProps );	
		
		challengesAdditionalFieldsMap = [{parentId: Ssp.util.Constants.CHALLENGE_OTHER_ID,
			                              parentName: "other",
			                              name: "description", 
			                              label: "Please Explain", 
			                              fieldType: "mappedtextarea",
			                              labelWidth: defaultLabelWidth}];
		
		challengeFormProps = {
				mainComponentType: 'checkbox',
				formId: 'StudentIntakeChallenges', 
                fieldSetTitle: 'Select all challenges that may be barriers to your academic success:',
                itemsArr: challenges, 
                selectedItemsArr: personChallenges, 
                idFieldName: 'id', 
                selectedIdFieldName: 'challengeId',
                additionalFieldsMap: challengesAdditionalFieldsMap };
		
		me.formUtils.createForm( challengeFormProps );
	},
	
	onSaveClick: function( button ) {
		var me=this;
		var formUtils = me.formUtils;
		var personalForm = Ext.getCmp('StudentIntakePersonal').getForm();
		var demographicsForm = Ext.getCmp('StudentIntakeDemographics').getForm();
		var educationPlansForm = Ext.getCmp('StudentIntakeEducationPlans').getForm();
		var educationGoalForm = Ext.getCmp('StudentIntakeEducationGoals').getForm();
		var educationLevelsForm = Ext.getCmp('StudentIntakeEducationLevels').getForm();
		var fundingForm = Ext.getCmp('StudentIntakeFunding').getForm();
		var challengesForm = Ext.getCmp('StudentIntakeChallenges').getForm();
		
		var educationGoalId = "";
		var educationGoalDescription = "";
		var educationGoalFormValues = null;
		var educationLevelFormValues = null;
		var fundingFormValues = null;
		var challengesFormValues = null;
		
		var studentIntakeFormModel = null;
		var personId = "";
		var intakeData = {};
		
		var formsToValidate = [personalForm,
		             demographicsForm,
		             educationPlansForm,
		             educationLevelsForm,
		             educationGoalForm,
		             fundingForm,
		             challengesForm];

		// validate and save the model
		var validateResult = me.formUtils.validateForms( formsToValidate );
		if ( validateResult.valid )
		{
			// update the model with changes from the forms
			personalForm.updateRecord( me.studentIntake.get('person') );
			demographicsForm.updateRecord( me.studentIntake.get('personDemographics') );
			educationPlansForm.updateRecord( me.studentIntake.get('personEducationPlan') );
			educationGoalForm.updateRecord( me.studentIntake.get('personEducationGoal') );
			
			educationGoalId = me.studentIntake.get('personEducationGoal').data.educationGoalId;
			
			// save the full model
			personId = me.studentIntake.get('person').data.id;
			intakeData = {
				person: me.studentIntake.get('person').data,
				personDemographics: me.studentIntake.get('personDemographics').data,
				personEducationGoal: me.studentIntake.get('personEducationGoal').data,
				personEducationPlan: me.studentIntake.get('personEducationPlan').data,
				personEducationLevels: [],
				personFundingSources: [],
				personChallenges: []
			};
						
			// date saved as null is ok if using External Data Sync Routine
			// and the date will not validate because the date field is disabled under
			// this mode. See SSPConfig and studentintake.PersonalViewController for additional detail.  
			if (intakeData.person.birthDate != null)
			{
				// account for date offset
				intakeData.person.birthDate = me.formUtils.fixDateOffset( intakeData.person.birthDate );			
			}

			// cleans properties that will be unable to be saved if not null
			// arrays set to strings should be null rather than string in saved
			// json
			intakeData.person = me.person.setPropsNullForSave( intakeData.person );			
			
			intakeData.personDemographics.personId = personId;
			intakeData.personEducationGoal.personId = personId;
			intakeData.personEducationPlan.personId = personId;

			educationGoalFormValues = educationGoalForm.getValues();
			educationGoalDescription = formUtils.getMappedFieldValueFromFormValuesByIdKey( educationGoalFormValues, educationGoalId );
			intakeData.personEducationGoal.description = educationGoalDescription;
			
			educationLevelFormValues = educationLevelsForm.getValues();
			intakeData.personEducationLevels = formUtils.createTransferObjectsFromSelectedValues('educationLevelId', educationLevelFormValues, personId);	
	
			fundingFormValues = fundingForm.getValues();
			intakeData.personFundingSources = formUtils.createTransferObjectsFromSelectedValues('fundingSourceId', fundingFormValues, personId);	
			
			challengesFormValues = challengesForm.getValues();
			intakeData.personChallenges = formUtils.createTransferObjectsFromSelectedValues('challengeId', challengesFormValues, personId);			

			// display loader
			me.getView().setLoading( true );
			
			me.service.save(me.personLite.get('id'), intakeData, {
				success: me.saveStudentIntakeSuccess,
				failure: me.saveStudentIntakeFailure,
				scope: me
			});

		}else{
			me.formUtils.displayErrors( validateResult.fields );
		}
	},
	
	saveStudentIntakeSuccess: function(r, scope) {
		var me=scope;

		me.getView().setLoading( false );
		
		if( r.success == true ) {
			me.formUtils.displaySaveSuccessMessage( me.getSaveSuccessMessage() );							
		}								
	},
	
	saveStudentIntakeFailure: function(response, scope) {
		var me=scope;
		me.getView().setLoading( false );							
	},	
	
	onCancelClick: function( button ){
		var me=this;
		me.getView().removeAll();
		me.initStudentIntakeViews();
		me.buildStudentIntake( me.studentIntake );	
	}
});
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
Ext.define('Ssp.controller.tool.studentintake.DemographicsViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	citizenshipsStore: 'citizenshipsStore',
    	model: 'currentStudentIntake',
    	sspConfig: 'sspConfig'
    },
    config: {
    	displayEmploymentShift: 1
    },
    control: {
		primaryCaregiverCheckOn: '#primaryCaregiverCheckOn',
		primaryCaregiverCheckOff: '#primaryCaregiverCheckOff',		
		
		'childcareNeeded': {
			change: 'onChildcareNeededChange'
		},
		
		childCareNeededCheckOn: '#childCareNeededCheckOn',
		childCareNeededCheckOff: '#childCareNeededCheckOff',
		
		'isEmployed': {
			change: 'onIsEmployedChange'
		},
		
		employedCheckOn: '#employedCheckOn',
		employedCheckOff: '#employedCheckOff',		

		'childcareArrangement': {
			hide: 'onFieldHidden'
		},
		
		'placeOfEmployment': {
			hide: 'onFieldHidden'
		},
		
		'shift': {
			hide: 'onFieldHidden'
		},
		
		'wage': {
			hide: 'onFieldHidden'
		},
		
		'totalHoursWorkedPerWeek': {
			hide: 'onFieldHidden'
		}
	},

	init: function() {
		var me=this;
		//added below 5 lines to take care of disabling entry if syncStudentPersonalDataWithExternalData is true
		var disabled = me.sspConfig.get('syncStudentPersonalDataWithExternalData');
		var studentIntakeDemographicsForm = Ext.getCmp('StudentIntakeDemographics');
		studentIntakeDemographicsForm.getForm().findField("gender").setDisabled(disabled);
		studentIntakeDemographicsForm.getForm().findField("maritalStatusId").setDisabled(disabled);
		studentIntakeDemographicsForm.getForm().findField("ethnicityId").setDisabled(disabled);
		
		var personDemographics = me.model.get('personDemographics');
		var citizenship = Ext.ComponentQuery.query('#citizenship')[0];
		var childcareNeeded = Ext.ComponentQuery.query('#childcareNeeded')[0];
		var isEmployed = Ext.ComponentQuery.query('#isEmployed')[0];
		var primaryCaregiver = me.model.get('personDemographics').get('primaryCaregiver');
		var childCareNeeded = me.model.get('personDemographics').get('childCareNeeded');
		var employed = me.model.get('personDemographics').get('employed');
		
		// Assign radio button values
		// Temp solution to assign a value to 
		// the No radio button 
		if ( personDemographics != null && personDemographics != undefined )
		{
			me.getPrimaryCaregiverCheckOn().setValue(primaryCaregiver);
			me.getPrimaryCaregiverCheckOff().setValue(!primaryCaregiver);

			me.getChildCareNeededCheckOn().setValue( childCareNeeded );
			me.getChildCareNeededCheckOff().setValue( !childCareNeeded );				

			me.getEmployedCheckOn().setValue( employed );
			me.getEmployedCheckOff().setValue( !employed );
		}
		
		me.displayStudentIntakeDemographicsEmploymentShift = me.sspConfig.get('displayStudentIntakeDemographicsEmploymentShift');
		
        me.showHideChildcareArrangement( childcareNeeded.getValue() );
        me.showHideEmploymentFields( isEmployed.getValue() );
        
		return me.callParent(arguments);
    },
    

    onChildcareNeededChange: function(radiogroup, newValue, oldValue, eOpts) {
    	this.showHideChildcareArrangement( newValue );
    },
    
    showHideChildcareArrangement: function( value ){
    	var field = Ext.ComponentQuery.query('#childcareArrangement')[0];
    	if(value.childCareNeeded=="true")
    	{
    		field.show();
    	}else{
    		field.hide();
    	}
    },
 
    onIsEmployedChange: function(radiogroup, newValue, oldValue, eOpts) {
    	this.showHideEmploymentFields( newValue );
    },    
    
    showHideEmploymentFields: function( value ){
    	var placeOfEmployment = Ext.ComponentQuery.query('#placeOfEmployment')[0];
    	var shift = Ext.ComponentQuery.query('#shift')[0];
    	var wage = Ext.ComponentQuery.query('#wage')[0];
    	var totalHoursWorkedPerWeek = Ext.ComponentQuery.query('#totalHoursWorkedPerWeek')[0];
    	if(value.employed=="true")
    	{
    		placeOfEmployment.show();
    		if (this.displayStudentIntakeDemographicsEmploymentShift)
    		{
    		    shift.show();
    		}else{
    			shift.hide();
    		}
    		wage.show();
    		totalHoursWorkedPerWeek.show();
    	}else{
    		placeOfEmployment.hide();
    		shift.hide();
    		wage.hide();
    		totalHoursWorkedPerWeek.hide();
    	}
    },    

    onFieldHidden: function( comp, eOpts){
    	comp.setValue("");
    }
    
});
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
Ext.define('Ssp.controller.tool.studentintake.PersonalViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	citizenshipsStore: 'citizenshipsStore',
    	sspConfig: 'sspConfig'
    }, 
    control: {
    	firstNameField: '#firstName',
    	middleNameField: '#middleName',
    	lastNameField: '#lastName',
    	studentIdField: '#studentId',
    	birthDateField: '#birthDate',
    	homePhoneField: '#homePhone',
    	workPhoneField: '#workPhone',
        cellPhoneField: '#cellPhone',
    	addressLine1Field: '#addressLine1',
    	addressLine2Field: '#addressLine2',
    	cityField: '#city',
    	stateField: '#state',
    	zipCodeField: '#zipCode',
    	primaryEmailAddressField: '#primaryEmailAddress'
    },
	init: function() {
		var me=this;
    	var disabled = me.sspConfig.get('syncStudentPersonalDataWithExternalData');
		// disable externally loaded fields
    	me.getFirstNameField().setDisabled(disabled);
		me.getMiddleNameField().setDisabled(disabled);
		me.getLastNameField().setDisabled(disabled);
		me.getBirthDateField().setDisabled(disabled);
		me.getHomePhoneField().setDisabled(disabled);
		me.getWorkPhoneField().setDisabled(disabled);
        me.getCellPhoneField().setDisabled(disabled);
		me.getAddressLine1Field().setDisabled(disabled);
		me.getAddressLine2Field().setDisabled(disabled);
		me.getCityField().setDisabled(disabled);
		me.getStateField().setDisabled(disabled);
		me.getZipCodeField().setDisabled(disabled);
		me.getPrimaryEmailAddressField().setDisabled(disabled);		
		studentIdField = me.getStudentIdField();
		studentIdField.setDisabled(disabled);
		// set the field label and supply an asterisk for required
		studentIdField.setFieldLabel(me.sspConfig.get('studentIdAlias') + Ssp.util.Constants.REQUIRED_ASTERISK_DISPLAY);
		Ext.apply(studentIdField, {
            minLength: me.sspConfig.get('studentIdMinValidationLength'),
            minLengthText: '',
            maxLength: me.sspConfig.get('studentIdMaxValidationLength'),
        	maxLengthText: '',
        	vtype: 'studentIdValidator',
        	vtypeText: me.sspConfig.get('studentIdValidationErrorText')
         });

		return me.callParent(arguments);
    }
});
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
Ext.define('Ssp.controller.tool.journal.JournalToolViewController', {
    extend: 'Deft.mvc.ViewController',	
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	apiProperties: 'apiProperties',
    	appEventsController: 'appEventsController',
        confidentialityLevelsStore: 'confidentialityLevelsStore',
    	formUtils: 'formRendererUtils',
    	service: 'journalEntryService',
        journalEntriesStore: 'journalEntriesStore',
        journalSourcesStore: 'journalSourcesStore',
    	journalTracksStore: 'journalTracksStore',
    	model: 'currentJournalEntry',
    	personLite: 'personLite'
    },
    config: {
    	containerToLoadInto: 'tools',
    	formToDisplay: 'editjournal',
    	personJournalUrl: ''
    },
    control: {
    	view: {
    		viewready: 'onViewReady'
    	},
    	
    	'addButton': {
			click: 'onAddClick'
		}
	},
    init: function() {
		var me = this;
		
		me.getView().setLoading( true );
		
		// clear any existing journal entries
		me.journalEntriesStore.removeAll();
		
		me.service.getAll( me.personLite.get('id'), {
			success: me.getAllJournalEntriesSuccess,
			failure: me.getAllJournalEntriesFailure,
			scope: me
		});

    	// ensure loading of all confidentiality levels in the database
    	me.confidentialityLevelsStore.load({
    		params:{limit:50}
    	});
    	
		me.journalSourcesStore.load();
		me.journalTracksStore.load();
		
		return me.callParent(arguments);
    },
 
    getAllJournalEntriesSuccess: function( r, scope ) {
		var me=scope;
		me.getView().setLoading( false );
    	if (r.rows.length > 0)
    	{
    		me.journalEntriesStore.loadData(r.rows);
    	}
	},

	getAllJournalEntriesFailure: function( response, scope ) {
		var me=scope;
		me.getView().setLoading( false );
	},    
    
    onViewReady: function(comp, obj){
    	this.appEventsController.assignEvent({eventName: 'editJournalEntry', callBackFunc: this.editJournalEntry, scope: this});
    	this.appEventsController.assignEvent({eventName: 'deleteJournalEntry', callBackFunc: this.deleteConfirmation, scope: this});
    },    
 
    destroy: function() {
    	var me=this;
    	
    	me.appEventsController.removeEvent({eventName: 'editJournalEntry', callBackFunc: me.editJournalEntry, scope: me});
    	me.appEventsController.removeEvent({eventName: 'deleteJournalEntry', callBackFunc: me.deleteConfirmation, scope: me});

        return me.callParent( arguments );
    },    
    
    onAddClick: function(button){
    	var je = new Ssp.model.tool.journal.JournalEntry();
    	this.model.data = je.data;
    	this.loadEditor();
    },
    
    editJournalEntry: function(){
    	this.loadEditor();
    },
 
    deleteConfirmation: function() {
        var me=this;
    	var message = 'You are about to delete a Journal Entry. Would you like to continue?';
    	var model = me.model;
        if (model.get('id') != "") 
        {
           Ext.Msg.confirm({
   		     title:'Delete Journal Entry?',
   		     msg: message,
   		     buttons: Ext.Msg.YESNO,
   		     fn: me.deleteJournalEntry,
   		     scope: me
   		   });
        }else{
     	   Ext.Msg.alert('SSP Error', 'Unable to delete Journal Entry.'); 
        }
     },
     
     deleteJournalEntry: function( btnId ){
     	var me=this;
    	var store = me.journalEntriesStore;
     	var id = me.model.get('id');
     	if (btnId=="yes")
     	{
     		me.getView().setLoading( true );
     		me.service.destroy( me.personLite.get('id'), id, {
     			success: me.destroyJournalEntrySuccess,
     			failure: me.destroyJournalEntryFailure,
     			scope: me
     		});  		
     	}
     },    

     destroyJournalEntrySuccess: function( r, id, scope ) {
 		var me=scope;
 		var store = me.journalEntriesStore;
 		me.getView().setLoading( false );
 		store.remove( store.getById( id ) );
 	},

 	destroyJournalEntryFailure: function( response, scope ) {
 		var me=scope;
 		me.getView().setLoading( false );
 	},      
     
    loadEditor: function(){
		var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {});    	
    }
});
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
Ext.define('Ssp.controller.tool.journal.EditJournalViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	authenticatedPerson: 'authenticatedPerson',
        appEventsController: 'appEventsController',
        confidentialityLevelsStore: 'confidentialityLevelsStore',
    	formUtils: 'formRendererUtils',
    	journalEntryService: 'journalEntryService',
    	model: 'currentJournalEntry',
    	personLite: 'personLite'
    },
    config: {
    	containerToLoadInto: 'tools',
    	mainFormToDisplay: 'journal',
    	sessionDetailsEditorDisplay: 'journaltracktree',
    	inited: false
    },

    control: {
    	entryDateField: '#entryDateField',
    	
    	removeJournalTrackButton: {
    		selector: '#removeJournalTrackButton',
    		listeners: {
    			click: 'onRemoveJournalTrackButtonClick'
    		}
    	},
    	
    	journalTrackCombo: {
    		selector: '#journalTrackCombo',
    		listeners: {
    			select: 'onJournalTrackComboSelect',
        		blur: 'onJournalTrackComboBlur'
    		} 
    	},

    	confidentialityLevelCombo: {
    		selector: '#confidentialityLevelCombo',
    		listeners: {
    			select: 'onConfidentialityLevelComboSelect'
    		} 
    	},    	

    	journalSourceCombo: {
    		selector: '#journalSourceCombo',
    		listeners: {
    			select: 'onJournalSourceComboSelect'
    		} 
    	},
    	
    	'commentText': {
    		change: 'onCommentChange'
    	},
    	
    	'saveButton': {
			click: 'onSaveClick'
		},
		
		'cancelButton': {
			click: 'onCancelClick'
		},
		
		'addSessionDetailsButton': {
			click: 'onAddSessionDetailsClick'
		}
    },
    
	init: function() {
		var me=this;
		// apply confidentiality level filter
		me.authenticatedPerson.applyConfidentialityLevelsFilter( me.confidentialityLevelsStore );
		me.initForm();	
		return me.callParent(arguments);
    },   
    
	initForm: function(){
		var me=this;
		var id = this.model.get("id");
		var journalTrackId = "";
		if ( me.model.get('journalTrack') != null )
		{
			journalTrackId = me.model.get('journalTrack').id;
		}
		me.getView().getForm().reset();
		me.getView().getForm().loadRecord( this.model );
		me.getConfidentialityLevelCombo().setValue( me.model.getConfidentialityLevelId() );
		me.getJournalSourceCombo().setValue( me.model.get('journalSource').id );
		me.getJournalTrackCombo().setValue( journalTrackId );			
		if ( me.model.get('entryDate') == null)
		{
			me.getEntryDateField().setValue( new Date() );
		}
		
		me.inited=true;
	},    
	
    destroy: function() {
    	var me=this;  	

    	// clear confidentiality level filter
    	me.confidentialityLevelsStore.clearFilter();
    	
        return me.callParent( arguments );
    },	
	
	onSaveClick: function(button) {
		var me = this;
		var record, id, jsonData, url;
		var form = this.getView().getForm();
		var values = form.getValues();
		//var handleSuccess = me.saveSuccess;
		var error = false;
		var journalTrackId="";		
		url = this.url;
		record = this.model;
		id = record.get('id');
		
		// ensure all required fields are supplied
		if ( !form.isValid() )
		{	
			error = true;
			Ext.Msg.alert('Error','Please correct the errors in your Journal Entry.');
		}
		
		// ensure a comment or journal track are supplied
		if ( record.get('comment') == "" && (record.data.journalTrack.id == null || record.data.journalTrack.id == "") )
		{
			error = true;
			Ext.Msg.alert('Error','You are required to supply a Comment or Journal Track Details for a Journal Entry.');			
		}
		
		if (error == false)
		{
    		// if a journal track is selected then validate that the details are set
    		if ( record.data.journalTrack != null)
    		{
    			journalTrackId = record.data.journalTrack.id;
    		}
			if ( (journalTrackId != null && journalTrackId != "") && record.data.journalEntryDetails.length == 0)
    		{
    			Ext.Msg.alert('SSP Error','You have a Journal Track set in your entry. Please select the associated details for this Journal Entry.');  			
    		}else{

    			// fix date from GMT to UTC
        		record.set('entryDate', me.formUtils.fixDateOffsetWithTime( record.data.entryDate ) );

    			jsonData = record.data;
    			    			
    			// null out journalTrack.id prop to prevent failure
    			// from an empty string on null field
    			if ( jsonData.journalTrack == "" )
    			{
    				jsonData.journalTrack = null;
    				jsonData.journalEntryDetails = null;
    			}
    			
    			// clean the group property from the journal
    			// entry details. It was only used for display
    			// of the details.
    			if ( jsonData.journalEntryDetails != null )
    			{
    				jsonData.journalEntryDetails = record.clearGroupedDetails( jsonData.journalEntryDetails );
    			}
    			
    			me.getView().setLoading( true );
    			
    			me.journalEntryService.save( me.personLite.get('id'), jsonData, {
    				success: me.saveSuccess,
    				failure: me.saveFailure,
    				scope: me
    			});
    		}			
		}
	},
	
	saveSuccess: function( r, scope ) {
		var me=scope;
		me.getView().setLoading( false );
		me.displayMain();
	},

	saveFailure: function( response, scope ) {
		var me=scope;
		me.getView().setLoading( false );
	},
	
	onCancelClick: function(button){
		this.displayMain();
	},

	onConfidentialityLevelComboSelect: function(comp, records, eOpts){
    	if (records.length > 0)
    	{
    		this.model.set('confidentialityLevel',{id: records[0].get('id')});
     	}
	},	
	
	onJournalSourceComboSelect: function(comp, records, eOpts){
    	if (records.length > 0)
    	{
    		this.model.set('journalSource',{id: records[0].get('id')});
     	}
	},	
	
	onJournalTrackComboSelect: function(comp, records, eOpts){
		var me=this;
		if (records.length > 0)
    	{
    		me.model.set('journalTrack',{"id": records[0].get('id')});
    		
    		// the inited property prevents the
    		// Journal Entry Details from clearing
    		// when the ViewController loads, so the details only 
    		// clear when a new journal track is selected
    		// because the init for the view sets the combo
    		if (me.inited==true)
    		{
    	   		me.model.removeAllJournalEntryDetails();
    			me.appEventsController.getApplication().fireEvent('refreshJournalEntryDetails');    			
    		}
     	}else{
     		me.removeJournalTrackAndSessionDetails();
     	}
	},
	
	onJournalTrackComboBlur: function( comp, event, eOpts){
		var me=this;
    	if (comp.getValue() == "")
    	{
     		me.removeJournalTrackAndSessionDetails();
     	}		
	},
	
	removeJournalTrackAndSessionDetails: function(){
 		var me=this;
		me.model.set("journalTrack","");
 		me.model.removeAllJournalEntryDetails();
 		me.appEventsController.getApplication().fireEvent('refreshJournalEntryDetails');
	},
	
	onRemoveJournalTrackButtonClick: function( button ){
		var me=this;
		var combo = me.getJournalTrackCombo();
		combo.clearValue();
		combo.fireEvent('select',{
			combo: combo,
			records: [],
			eOpts: {}
		});
	},
	
	onCommentChange: function(comp, newValue, oldValue, eOpts){
		this.model.set('comment',newValue);
	},
	
	onAddSessionDetailsClick: function( button ){
		if( this.model.get('journalTrack') != null && this.model.get('journalTrack') != "")
		{
			this.displaySessionDetails();
		}else{
			Ext.Msg.alert('Error', 'A Journal Track is required before selecting details.')
		}
	},
	
	displayMain: function(){
		var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getMainFormToDisplay(), true, {});
	},
	
	displaySessionDetails: function(){
		var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getSessionDetailsEditorDisplay(), true, {});
	}
});
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
Ext.define('Ssp.controller.tool.journal.DisplayDetailsViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	appEventsController: 'appEventsController',
    	model: 'currentJournalEntry',
    	store: 'journalEntryDetailsStore'
    },   
	
    control: {
		view: {
			viewready: 'onViewReady'
		}
	},
	
    init: function() {
    	var me=this;
		me.store.loadData( me.model.getGroupedDetails() );		
		return me.callParent( arguments );
    },
    
    onViewReady: function(){
    	this.appEventsController.assignEvent({eventName: 'refreshJournalEntryDetails', callBackFunc: this.onRefreshJournalEntryDetails, scope: this});
    },
    
    destroy: function(){
    	this.appEventsController.removeEvent({eventName: 'refreshJournalEntryDetails', callBackFunc: this.onRefreshJournalEntryDetails, scope: this});    	
    },
    
    onRefreshJournalEntryDetails: function(){
    	this.init();
    }
});
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
Ext.define('Ssp.controller.tool.journal.TrackTreeViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
        formUtils: 'formRendererUtils',
        journalEntry: 'currentJournalEntry',
        person: 'currentPerson',
    	treeUtils: 'treeRendererUtils'
    },
    config: {
    	containerToLoadInto: 'tools',
    	formToDisplay: 'editjournal',
    	journalTrackUrl: '',
    	journalStepUrl: '',
    	journalStepDetailUrl: ''
    },
    control: {
    	view: {
    		itemexpand: 'onItemExpand',
    		itemClick: 'onItemClick'
    	},
    	
    	'saveButton': {
			click: 'onSaveClick'
		},
		
    	'cancelButton': {
			click: 'onCancelClick'
		}
    },
    
	init: function() {
		var rootNode = null;
		this.journalTrackUrl = this.apiProperties.getItemUrl('journalTrack');
		this.journalStepUrl = this.apiProperties.getItemUrl('journalStep');
		this.journalStepDetailUrl = this.apiProperties.getItemUrl('journalStep');

		this.loadSteps();
		
		return this.callParent(arguments);
    },
    
    destroy: function() {
    	// clear the categories
		this.treeUtils.clearRootCategories();
    	
        return this.callParent( arguments );
    },    
    
    loadSteps: function(){
		// clear the categories
		this.treeUtils.clearRootCategories();
		
		var journalTrackId = this.journalEntry.get('journalTrack').id;
		
    	// load the steps
		if (journalTrackId != null && journalTrackId != "")
		{
			var treeRequest = new Ssp.model.util.TreeRequest();
	    	treeRequest.set('url', this.journalTrackUrl + '/'+ journalTrackId + '/journalStep?sort=name');
	    	treeRequest.set('nodeType','journalStep');
	    	treeRequest.set('isLeaf', false);
	    	treeRequest.set('enableCheckedItems', false);
	    	treeRequest.set('expanded',false);
	    	treeRequest.set('callbackFunc',this.afterJournalStepsLoaded);
	    	treeRequest.set('callbackScope',this);
	    	this.treeUtils.getItems( treeRequest );			
		}
    },
    
    afterJournalStepsLoaded: function( scope ){
    	// after the journal steps load expand them to
    	// display the details under each step
    	scope.getView().getView().getTreeStore().getRootNode().expandChildren();
    },
    
    onItemExpand: function(nodeInt, obj){
    	var me=this;
    	var node = nodeInt;
    	var url = me.journalStepDetailUrl;
    	var id = me.treeUtils.getIdFromNodeId( node.data.id );
    	if (url != "")
    	{
        	var treeRequest = new Ssp.model.util.TreeRequest();
        	treeRequest.set('url', url + '/' + id + '/journalStepDetail?sort=name');
        	treeRequest.set('nodeType', 'journalDetail');
        	treeRequest.set('isLeaf', true);
        	treeRequest.set('nodeToAppendTo', node);
        	treeRequest.set('enableCheckedItems',true);
	    	treeRequest.set('callbackFunc',me.afterJournalDetailsLoaded);
	    	treeRequest.set('callbackScope',me);
	    	treeRequest.set('removeParentWhenNoChildrenExist',true);
    		me.treeUtils.getItems( treeRequest );
    	}
    },

    afterJournalDetailsLoaded: function( scope ){
    	// after the journal details load select each detail
    	// that is selected in the journal
    	var journalEntryDetails = scope.journalEntry.get("journalEntryDetails");
    	if (journalEntryDetails != "" && journalEntryDetails != null)
    	{
			Ext.Array.each(journalEntryDetails,function(item,index){
				var journalStepDetails = item.journalStepDetails;
				Ext.Array.each(journalStepDetails,function(innerItem,innerIndex){
					var id = innerItem.id;
					var detailNode = scope.getView().getView().getTreeStore().getNodeById(id+'_journalDetail');
					if (detailNode != null)
					{
						detailNode.set('checked',true);
					}
				});				
			});    		
    	}
    },    
   

    onItemClick: function(view, record, item, index, e, eOpts){
    	/*
    	var me=this;
    	var journalEntry = me.journalEntry;
    	var name = me.treeUtils.getNameFromNodeId( record.data.id );
    	var checked = !record.data.checked;
    	var id = me.treeUtils.getIdFromNodeId( record.data.id );
    	var childText = record.data.text;
    	var parentId = me.treeUtils.getIdFromNodeId( record.data.parentId );
    	var parentText = record.parentNode.data.text;
    	var step = null;
    	var detail = null;
    	// add/remove the detail from the Journal Entry
    	if (name=='journalDetail')
    	{
    		step = {"id":parentId,"name":parentText};
    		detail = {"id":id,"name":childText};
    		if ( checked==true )
        	{
    			// add journal detail
    			journalEntry.addJournalDetail( step, detail );
        	}else{
        		// remove journal detail
        		journalEntry.removeJournalDetail( step, detail );
        	}
    	}
    	*/
    },
    
    onSaveClick: function( button ){
    	var me=this;
    	var journalEntry = me.journalEntry;
    	var tree = me.getView();
    	var treeUtils = me.treeUtils;
    	var records = tree.getView().getChecked();
    	
    	journalEntry.removeAllJournalEntryDetails();
    	
    	// add/remove the detail from the Journal Entry
    	Ext.Array.each(records,function(record,index){
        	var id = me.treeUtils.getIdFromNodeId( record.data.id );
        	var childText = record.data.text;
        	var parentId = me.treeUtils.getIdFromNodeId( record.data.parentId );
        	var parentText = record.parentNode.data.text;
        	var step = null;
        	var detail = null;

    		step = {"id":parentId,"name":parentText};
    		detail = {"id":id,"name":childText};
    		// add journal detail
    		journalEntry.addJournalDetail( step, detail );
    	},this);
    	// load the editor
    	this.displayJournalEditor();
    },
    
    onCancelClick: function( button ){
    	this.displayJournalEditor();
    },

    displayJournalEditor: function(){
		var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {});
    } 
    
});
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
Ext.define('Ssp.controller.tool.earlyalert.EarlyAlertToolViewController', {
    extend: 'Deft.mvc.ViewController',  
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
        appEventsController: 'appEventsController',
        confidentialityLevelsStore: 'confidentialityLevelsStore',
        campusesStore: 'campusesStore',
        earlyAlertsStore: 'earlyAlertsStore',
        earlyAlertService: 'earlyAlertService',
        earlyAlertResponseService: 'earlyAlertResponseService',
        earlyAlert: 'currentEarlyAlert',
        earlyAlertResponse: 'currentEarlyAlertResponse',
        formUtils: 'formRendererUtils',
        outcomesStore: 'earlyAlertOutcomesStore',
        outreachesStore: 'earlyAlertOutreachesStore',
        reasonsStore: 'earlyAlertReasonsStore',
        personLite: 'personLite',
        referralsStore: 'earlyAlertReferralsStore',
        suggestionsStore: 'earlyAlertSuggestionsStore',
        treeStore: 'earlyAlertsTreeStore'
    },
    config: {
        containerToLoadInto: 'tools',
        earlyAlertDetailsDisplay: 'earlyalertdetails',
        earlyAlertResponseDetailsDisplay: 'earlyalertresponsedetails'
    },
    control: {
        view: {
            viewready: 'onViewReady',
            itemexpand: 'onItemExpand',
            itemClick: 'onAlertClick'
        }
    },
    
    init: function(){
        return this.callParent(arguments);
    },

    onViewReady: function(comp, obj){
        var me=this;
        me.campusesStore.load({
            params:{limit:50}
        });
        
        me.confidentialityLevelsStore.load({
            params:{limit:50}
        });
        
        me.outcomesStore.load({
            params:{limit:50}
        });
        
        me.outreachesStore.load({
            params:{limit:50}
        });
        
        me.reasonsStore.load({
            params:{limit:50}
        });
        
        me.suggestionsStore.load({
            params:{limit:50}
        });
        
        me.referralsStore.load({
            params:{limit:50},
            callback: function(r,options,success) {
                 if(success == true) {
                         me.getEarlyAlerts(); 
                  }
                  else {
                      Ext.Msg.alert("Ssp Error","Failed to load referrals. See your system administrator for assitance.");
                  }
             }
        });

    },

    destroy: function() {
        return this.callParent( arguments );
    },    
    
    getEarlyAlerts: function(){
        var me=this;
        var pId = me.personLite.get('id');
        me.getView().setLoading(true);
        me.earlyAlertService.getAll( pId, 
            {success:me.getEarlyAlertsSuccess, 
             failure:me.getEarlyAlertsFailure, 
             scope: me});
    },
    
    getEarlyAlertsSuccess: function( r, scope){
        var me=scope;
        var personEarlyAlert;
        me.getView().setLoading(false);
        if ( me.earlyAlertsStore.getCount() > 0)
        {
            me.getView().getSelectionModel().select(0);
        }else{
            // if no record is available then set the selected early alert to null
            personEarlyAlert = new Ssp.model.tool.earlyalert.PersonEarlyAlert();
            me.earlyAlert.data = personEarlyAlert.data;
        }

		var pId = me.personLite.get('id');
		
		me.treeStore.getRootNode().eachChild(function(record){
			var eaId = record.get('id');
            me.earlyAlertService.getAllEarlyAlertCount( pId, eaId, 
                {success:me.getEarlyAlertCountSuccess, 
                 failure:me.getEarlyAlertCountFailure, 
                 scope: me});
			}
	    );
    },

    getEarlyAlertsFailure: function( r, scope){
        var me=scope;
        me.getView().setLoading(false);
    },
	
	getEarlyAlertCountSuccess: function(personId, earlyAlertId, count, scope) {
		var me = scope;
		// update the tree node w/ the count
		var originalChild = me.treeStore.getRootNode().findChildBy(function(n) {
                return earlyAlertId === n.data.id;
            }, me, true);
		me.treeStore.suspendEvents();
		originalChild.set('noOfResponses',count);
        me.treeStore.resumeEvents();
		
	},

    getEarlyAlertCountFailure: function( r, scope){
        var me=scope;
        me.getView().setLoading(false);
		
    },

    onItemExpand: function(nodeInt, obj){
        var me=this;
        var node = nodeInt;
        var nodeType = node.get('nodeType');
        var id = node.get('id' );
        var personId = me.personLite.get('id');
        if (node != null)
        {
            // use root here to prevent the expand from firing
            // when items are added to the root element in the tree
            if (nodeType == 'early alert' && id != "root" && id != "")
            {
                me.getView().setLoading(true);
                me.earlyAlertService.getAllEarlyAlertResponses(personId, id,
                        {success:me.getEarlyAlertResponsesSuccess, 
                 failure:me.getEarlyAlertResponsesFailure, 
                 scope: me} 
                );
            }           
        }
    },
  
    getEarlyAlertResponsesSuccess: function( r, scope){
        var me=scope;
        // clear the current Early Alert Response
        var earlyAlertResponse = new Ssp.model.tool.earlyalert.EarlyAlertResponse();
        me.earlyAlertResponse.data = earlyAlertResponse.data;
        me.getView().setLoading(false);
    },

    getEarlyAlertResponsesFailure: function( r, scope){
        var me=scope;
        me.getView().setLoading(false);
    },    

    onAlertClick: function(nodeInt, obj){
        var me=this;
        var record = me.getView().getSelectionModel().getSelection()[0];
		var node = nodeInt;
       
		var id = record.get('id');
		
		var personId = me.personLite.get('id');

        if (record != null)
        {
            if (record.get('nodeType')=='early alert')
            {
                for (prop in me.earlyAlert.data)
                {
                    me.earlyAlert.data[prop] = record.data[prop];
                }


				/*me.earlyAlertService.getAllEarlyAlertResponses(personId, id,
                        {success:me.getEarlyAlertResponsesSuccess, 
                 failure:me.getEarlyAlertResponsesFailure, 
                 scope: me} );*/

                me.displayEarlyAlertDetails();
            }
			/*else{
                
                for (prop in me.earlyAlertResponse.data)
                {
                    me.earlyAlertResponse.data[prop] = record.data[prop];
                }
                
                me.displayEarlyAlertResponseDetails();              
            }  */ 
        }else{
            Ext.Msg.alert('Notification','Please select an item to view.');
        }
    },

    displayEarlyAlertDetails: function(button){
        var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getEarlyAlertDetailsDisplay(), true, {reloadEarlyAlert: false});
    },
    
    displayEarlyAlertResponseDetails: function(button){
        var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getEarlyAlertResponseDetailsDisplay(), true, {});
    }
});
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
Ext.define('Ssp.controller.tool.earlyalert.EarlyAlertResponseViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	earlyAlert: 'currentEarlyAlert',
    	earlyAlertResponseService: 'earlyAlertResponseService',
    	earlyAlertService: 'earlyAlertService',
    	formUtils: 'formRendererUtils',
    	model: 'currentEarlyAlertResponse',
    	personLite: 'personLite',
		authenticatedPerson: 'authenticatedPerson'
    },
    config: {
    	containerToLoadInto: 'tools',
    	formToDisplay: 'earlyalertdetails',
        earlyAlertList: 'earlyalert'
    },
    control: {
    	outreachList: {
    		selector: '#outreachList',
            listeners: {
            	validitychange: 'onOutreachListValidityChange'
            }
    	},
    	
    	outcomeCombo: {
            selector: '#outcomeCombo',
            listeners: {
                select: 'onOutcomeComboSelect'
            }
        },

    	otherOutcomeDescriptionText: {
            selector: '#otherOutcomeDescriptionText'
        },
        
    	'saveButton': {
			click: 'onSaveClick'
		},
		
		'cancelButton': {
			click: 'onCancelClick'
		}   ,
		
		'responseGotoEAListButton': {
            click: 'onResponseGotoEAListClick'
        },
		
        'responseGotoEADetailsButton': {
            click: 'onCancelClick'
        }	
    },
    
	init: function() {
		var me=this;
		me.getView().getForm().reset();
		if(me.model.dirty)
		{
			me.model = new Ssp.model.tool.earlyalert.EarlyAlertResponse();
		}
		me.getView().getForm().loadRecord(me.model);
		me.showHideOtherOutcomeDescription();
		return me.callParent(arguments);
    },
    
    onOutcomeComboSelect: function(comp, records, eOpts){
    	this.showHideOtherOutcomeDescription();
    },
    
    showHideOtherOutcomeDescription: function(){
    	var me=this;
    	if (me.getOutcomeCombo().getValue()==Ssp.util.Constants.OTHER_EARLY_ALERT_OUTCOME_ID)
    	{
    		me.getOtherOutcomeDescriptionText().show();
    	}else{
    		me.getOtherOutcomeDescriptionText().hide();
    	}
    },
    
    onOutreachListValidityChange: function( comp, isValid, eOpts ){
    	//comp[isValid ? 'removeCls' : 'addCls']('multiselect-invalid');
    },
    
	onSaveClick: function(button) {
		var me = this;
		var record, id, jsonData, url;
		var personId = me.personLite.get('id');
		var earlyAlertId = me.earlyAlert.get('id');
		var referralsItemSelector = Ext.ComponentQuery.query('#earlyAlertReferralsItemSelector')[0];	
		var selectedReferrals = [];			
		var form = me.getView().getForm();
		var outreaches = me.getOutreachList().getValue();
		var outreachIsValid = false;
		// validate multi-select list
		// accomodate error in extjs where
		// list is not correctly marked invalid
		if ( outreaches.length > 0 )
		{
			if (outreaches[0] != "")
			{
				outreachIsValid=true;
			}
		}
		if ( outreachIsValid == false )
		{
			me.getOutreachList().setValue(["1"]);
			me.getOutreachList().setValue([]);
			me.getOutreachList().addCls('multiselect-invalid');
			me.getOutreachList().markInvalid("At least one Outreach is required.");			
		}
		
		// test for valid form entry
		if ( form.isValid() && outreachIsValid)
		{
			form.updateRecord();
			record = me.model;
			
			// populate referrals
			selectedReferrals = referralsItemSelector.getValue();
			if (selectedReferrals.length > 0)
			{			
			   record.set('earlyAlertReferralIds', selectedReferrals);
			}else{
			   // AAL : 08/01/12 : Commented line below as it was adding a "referrals" property to the API call
					// and this property isn't valid per the api spec.  Added the setting of the earlyAlertReferralIds
					// property to the empty array when none are selected.  By default this value was being set to an
					// empty string which isn't valid per the api spec and was throwing an exception on the server.
			   // record.data.referrals=null;
			   record.set('earlyAlertReferralIds', []);
			}		
			
			// set the early alert id for the response
			record.set( 'earlyAlertId', earlyAlertId ); 
			
			if(record.data.closed)
			{
				if (me.earlyAlert.get('closedById') == "" || me.earlyAlert.get('closedById') == null)
				{
					me.earlyAlert.set( 'closedById', me.authenticatedPerson.getId() );
				}
			}
			
			// jsonData for the response
			jsonData = record.data;
			
			me.getView().setLoading(true);
			me.earlyAlertResponseService.save(personId, earlyAlertId, jsonData, {
				success: me.closeEarlyAlertSuccess,
				failure: me.closeEarlyAlertFailure,
				scope: me
			});				
		}else{
			Ext.Msg.alert('Error','Please correct the indicated errors in this form.');
		}
	},
	
 	closeEarlyAlertSuccess: function( r, scope ) {
		var me=scope;
		me.getView().setLoading(false);
		me.displayMain();
	},

	closeEarlyAlertFailure: function( response, scope ) {
		var me=scope;
		me.getView().setLoading(false);
	},
	
	onCancelClick: function(button){
		this.displayMain();
	},
	
	displayMain: function(){
		var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {reloadEarlyAlert: true});
	},
	
	onResponseGotoEAListClick: function(button){
        var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getEarlyAlertList(), true, {});
    }
	
	
});
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
Ext.define('Ssp.controller.tool.earlyalert.EarlyAlertReferralsViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	columnRendererUtils: 'columnRendererUtils',
    	earlyAlertResponse: 'currentEarlyAlertResponse',
    	service: 'earlyAlertReferralService',
        store: 'earlyAlertReferralsBindStore',
        itemSelectorInitializer: 'itemSelectorInitializer'
    },
	init: function() {
		var me=this;
		me.service.getAll({
			success: me.getAllSuccess,
			failure: me.getAllFailure,
			scope: me
		});
		
		return this.callParent(arguments);
    },

	getAllSuccess: function( r, scope ){
    	var me=scope;
    	var items;
    	var view = me.getView();
    	//var selectedReferrals = me.earlyAlertResponse.get('earlyAlertReferralIds');
    	if (r.rows.length > 0)
    	{
    		me.store.loadData(r.rows);

            me.itemSelectorInitializer.defineAndAddSelectorField(me.getView(), [], {
                itemId: 'earlyAlertReferralsItemSelector',
                name: 'earlyAlertReferrals',
                fieldLabel: 'Department Referrals',
                store: me.store
            });

    	}
	},
	
    getAllFailure: function( response, scope ){
    	var me=scope;  	
    }   
});
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

// Use this to trigger additional callbacks as side-effects of completion of
// an arbitrary collection of named success/failure callbacks. The latter are
// usually async AJAX callbacks. E.g. a controller might need to issue several
// AJAX calls, then do something when all that data has been loaded (maybe just
// clear a spinner). Currently can register side-effect callbacks that either
// fire after every named callback or after at least one of each named callbacks
// has fired. Does not cache the results of the named callbacks. That could
// be added in future revisions, but the assumption is that the client is
// capable of caching those results since they are passed to client-registered
// callbacks.
//
// There is no sophistication in the callback mechanism, e.g. all "failures"
// go to the same callback... there is no statuscode-based routing.
Ext.define('Ssp.util.ResponseDispatcher',{
    extend: 'Ext.Component',
    config: {
        successCallbacks: {},
        failureCallbacks: {},
        remainingOpNames: [],
        afterAnyOp: null,
        afterLastOp: null
    },
    initComponent: function() {
        return this.callParent( arguments );
    },

    // Returns a function that is typically useful for registering with
    // of our "service" classes as a success callback.
    setSuccessCallback: function(opName, callback, callbackScope) {
        var me = this;
        me.getSuccessCallbacks()[opName] = me.newScopedCallback(callback, callbackScope);
        return function(response) {
            me.onSuccess(opName, response);
        }
    },

    // Returns a function that is typically useful for registering with
    // of our "service" classes as a failure callback.
    setFailureCallback: function(opName, callback, callbackScope) {
        var me = this;
        me.getFailureCallbacks()[opName] = me.newScopedCallback(callback, callbackScope);
    },

    setAfterAnyCallback: function(callback, callbackScope) {
        var me = this;
        me.setAfterAnyOp(me.newScopedCallback(callback, callbackScope));
    },
    setAfterLastCallback: function(callback, callbackScope) {
        var me = this;
        me.setAfterLastOp(me.newScopedCallback(callback, callbackScope));
    },
    newScopedCallback: function(callback, callbackScope) {
        if (!(callback)) {
            return null;
        }
        return { callback: callback, callbackScope: callbackScope };
    },
    invokeScopedCallback: function(callback, response, opName) {
        if ( callback ) {
            callback.callback.apply(callback.callbackScope, [response, callback.callbackScope, opName]);
        }
    },
    onSuccess: function(opName, response) {
        var me = this;
        var callback = me.getSuccessCallbacks()[opName];
        me.onResponse(opName, response, callback);
    },
    onFailure: function(opName, response) {
        var me = this;
        var callback = me.getFailureCallbacks()[opName];
        me.onResponse(opName, response, callback);
    },
    onResponse: function(opName, response, callback) {
        var me = this;
        var remainingOps = me.getRemainingOpNames();
        if ( remainingOps ) {
            me.setRemainingOpNames(remainingOps.filter(function(element){
                return element !== opName;
            }));
            remainingOps = me.getRemainingOpNames();
        }

        me.invokeScopedCallback(callback, response, opName);
        me.invokeScopedCallback(me.getAfterAnyOp(), response, opName);
        if (!(remainingOps) || remainingOps.length === 0 ) {
            me.invokeScopedCallback(me.getAfterLastOp(), response, opName);
        }
    }
});
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
Ext.define('Ssp.controller.tool.earlyalert.EarlyAlertDetailsViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
        apiProperties: 'apiProperties',
        campusesStore: 'campusesStore',
        formUtils: 'formRendererUtils',
        model: 'currentEarlyAlert',
        personService: 'personService',
        reasonsStore: 'earlyAlertReasonsStore',
        suggestionsStore: 'earlyAlertSuggestionsStore',
        selectedSuggestionsStore: 'earlyAlertDetailsSuggestionsStore',
        appEventsController: 'appEventsController',
        earlyAlertResponse: 'currentEarlyAlertResponse',
        personLite: 'personLite',
        earlyAlertService: 'earlyAlertService'
    },
    config: {
        containerToLoadInto: 'tools',
        earlyAlertResponseFormDisplay: 'earlyalertresponse',
        earlyAlertListDisplay: 'earlyalert',
        earlyAlertResponseDetailsDisplay: 'earlyalertresponsedetails'
    },
    control: {
        'finishButton': {
            click: 'onFinishButtonClick'
        },
        'detailRespondButton': {
            click: 'onDetailRespondClick'
        },
        'detailResponseGridPanel': {
            cellClick : 'onGridClick'
        },
        
        earlyAlertSuggestionsList: '#earlyAlertSuggestionsList',
        campusField: '#campusField',
        earlyAlertReasonField: '#earlyAlertReasonField',
        statusField: '#statusField',
        createdByField: '#createdByField',
        closedByField: '#closedByField'
    },

    init: function() {

        var me=this;

        me.getView().setLoading( true );

        var personId = me.personLite.get('id');
        var earlyAlertId = me.model.get('id');

        var responseDispatcher = Ext.create('Ssp.util.ResponseDispatcher', {
            remainingOpNames: ['earlyalert','earlyalertresponses']
        });
        responseDispatcher.setAfterLastCallback(me.afterLastServiceResponse, me);

        if ( me.getView().reloadEarlyAlert ) {
            me.earlyAlertService.get(earlyAlertId, personId, {
               success: responseDispatcher.setSuccessCallback('earlyalert', me.getEarlyAlertSuccess, me),
               failure: responseDispatcher.setFailureCallback('earlyalert', me.getEarlyAlertFailure, me),
               scope: responseDispatcher
            });
        } else {
            // still need to go through the request dispatcher so we can fire the
            // 'all clear' once all sync and async data loads have completed
            responseDispatcher.setSuccessCallback('earlyalert', me.bindEarlyAlertToView, me).apply(responseDispatcher, null);
        }

        me.earlyAlertService.getAllEarlyAlertResponses(personId,
            earlyAlertId, {
                success: responseDispatcher.setSuccessCallback('earlyalertresponses', me.getEarlyAlertResponsesSuccess, me),
                failure: responseDispatcher.setFailureCallback('earlyalertresponses', me.getEarlyAlertResponsesFailure, me),
                scope: responseDispatcher
            });
        
        return this.callParent(arguments);
    },

    getEarlyAlertSuccess: function(response) {
        var me = this;
        // TODO I have no idea how correct this model state management is. The
        // property copying loop that EarlyAlertToolViewController.js and
        // onGridClick() uses doesn't work here for reasons that currently
        // elude me (e.g. date types are all wrong). IMHO, it seems all wrong
        // anyway... shouldn't this all be hidden behind a Ext Store? Playing
        // low-level reinitialization games with singleton Models can't possibly
        // be right... can it?
        var earlyAlert= new Ssp.model.tool.earlyalert.PersonEarlyAlert();
        me.model.data = earlyAlert.data;
        if ( response ) {
            me.model.populateFromGenericObject(response);
        } // TODO else what? a horror show is likely to ensue...
        me.bindEarlyAlertToView();
    },

    bindEarlyAlertToView: function() {
        var me = this;
        var campus = me.campusesStore.getById( me.model.get('campusId') );
        var reasonId = ((me.model.get('earlyAlertReasonIds') != null )?me.model.get('earlyAlertReasonIds')[0].id : me.model.get('earlyAlertReasonId') );
        var reason = me.reasonsStore.getById( reasonId );

        // Reset and populate general fields comments, etc.
        me.getView().getForm().reset();
        me.getView().loadRecord( me.model );

        me.getCreatedByField().setValue( me.model.getCreatedByPersonName() );

        // Early Alert Status: 'Open', 'Closed'
        me.getStatusField().setValue( ((me.model.get('closedDate'))? 'Closed' : 'Open') );

        // Campus
        me.getCampusField().setValue( ((campus)? campus.get('name') : "No Campus Defined") );

        // Reason
        me.getEarlyAlertReasonField().setValue( ((reason)? reason.get('name') : "No Reason Defined") );

        // Suggestions
        var selectedSuggestions = me.formUtils.getSimpleItemsForDisplay( me.suggestionsStore, me.model.get('earlyAlertSuggestionIds'), 'Suggestions' );
        me.selectedSuggestionsStore.removeAll();
        me.selectedSuggestionsStore.loadData( selectedSuggestions );

        if ( me.model.get('closedById') ) {
            if ( me.model.get('closedByName') ) {
                me.getClosedByField().setValue( me.model.get('closedByName') );
            } else {
                me.getClosedByField().setValue('UNKNOWN');
            }
        }

    },

    getEarlyAlertFailure: function() {
        // no-op for now... error probably already  presented to end user as
        // part of the service call.
    },

    getEarlyAlertResponsesSuccess: function( r, scope){
        var me=scope;
        // clear the current Early Alert Response
        var earlyAlertResponse = new Ssp.model.tool.earlyalert.EarlyAlertResponse();
        me.earlyAlertResponse.data = earlyAlertResponse.data;
    },

    getEarlyAlertResponsesFailure: function( r, scope){
        // no-op for now... error probably already presented to end user as
        // part of the service call.
    },

    afterLastServiceResponse: function() {
        var me = this;
        me.getView().setLoading( false );
    },

    onFinishButtonClick: function( button ){
        var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getEarlyAlertListDisplay(), true, {});
    },

    onDetailRespondClick: function( button ){
        var me=this;
        me.loadEarlyAlertResponseForm(button);
    },

    loadEarlyAlertResponseForm: function(button){
        this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getEarlyAlertResponseFormDisplay(), true, {});
    },

    onGridClick: function(){
        var me=this;
        var record = Ext.getCmp('detailResponseGridPanel').getSelectionModel().getSelection()[0];

        for (prop in me.earlyAlertResponse.data)
                {
                    me.earlyAlertResponse.data[prop] = record.data[prop];
                }
        this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getEarlyAlertResponseDetailsDisplay(), true, {});
       
    }
});
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
Ext.define('Ssp.controller.tool.earlyalert.EarlyAlertResponseDetailsViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	outcomesStore: 'earlyAlertOutcomesStore',
    	outreachesStore: 'earlyAlertOutreachesStore',
    	referralsStore: 'earlyAlertReferralsStore',
    	formUtils: 'formRendererUtils',
        model: 'currentEarlyAlertResponse',
        selectedOutreachesStore: 'earlyAlertResponseDetailsOutreachesStore',
        selectedReferralsStore: 'earlyAlertResponseDetailsReferralsStore'
    },
    config: {
    	containerToLoadInto: 'tools',
    	formToDisplay: 'earlyalertdetails'
    },
    control: {
    	'finishButton': {
    		click: 'onFinishButtonClick'
    	},
    	
    	outcomeField: '#outcomeField',
    	createdByField: '#createdByField'
    },
	init: function() {
		var me=this;
		var me=this;
		var selectedOutreaches=[];
		var selectedReferrals=[];
		var outcome = me.outcomesStore.getById( me.model.get('earlyAlertOutcomeId') );
		
		// reset and populate general fields comments, etc.
		me.getView().getForm().reset();
		me.getView().loadRecord( me.model );

		me.getCreatedByField().setValue( me.model.getCreatedByPersonName() );
		
		// display outcome
		me.getOutcomeField().setValue( outcome.get('name') );

		// Outreaches
		selectedOutreaches = me.formUtils.getSimpleItemsForDisplay( me.outreachesStore, me.model.get('earlyAlertOutreachIds'), 'Outreaches' );
		me.selectedOutreachesStore.removeAll();
		me.selectedOutreachesStore.loadData( selectedOutreaches );

		// Referrals
		selectedReferrals = me.formUtils.getSimpleItemsForDisplay( me.referralsStore, me.model.get('earlyAlertReferralIds'), 'Referrals' );
		me.selectedReferralsStore.removeAll();
		me.selectedReferralsStore.loadData( selectedReferrals );		

		return this.callParent(arguments);
    },
    
    onFinishButtonClick: function( button ){
		var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {reloadEarlyAlert: false});
    }
});
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
Ext.define('Ssp.controller.tool.document.StudentDocumentToolViewController', {
    extend: 'Deft.mvc.ViewController',	
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	apiProperties: 'apiProperties',
    	appEventsController: 'appEventsController',
    	formUtils: 'formRendererUtils',
    	person: 'currentPerson',
    	model: 'currentDocument',
    	documentsStore: 'documentsStore',
        confidentialityLevelsStore: 'confidentialityLevelsStore'
    },
    config: {
    	containerToLoadInto: 'tools',
    	formToDisplay: 'editdocument',
    	personDocumentUrl: ''
    },
    control: {
    	view: {
    		viewready: 'onViewReady'
    	},
    	
    	'addButton': {
			click: 'onAddClick'
		}
	},
    init: function() {
		var me = this;
		var personId = this.person.get('id');
		var successFunc = function(response,view){
	    	var r = Ext.decode(response.responseText);
	    	if (r.rows.length > 0)
	    	{
	    		me.documentsStore.loadData(r.rows);
	    	}
		};

    	this.confidentialityLevelsStore.load();

		this.personDocumentUrl = this.apiProperties.createUrl( this.apiProperties.getItemUrl('personDocument') );
		this.personDocumentUrl = this.personDocumentUrl.replace('{id}',personId);		

		this.apiProperties.makeRequest({
			url: this.personDocumentUrl,
			method: 'GET',
			successFunc: successFunc
		});
    	
    	var json = {"success":true,"results":0,"rows":[]};
    	var rows = [{
    		"id":"240e97c0-7fe5-11e1-b0c4-0800200c9a66",
    		"name":"My Document",
    		"note":"This is my document",
    		"confidentialityLevel":{"id":"afe3e3e6-87fa-11e1-91b2-0026b9e7ff4c","name":"EVERYONE"},
    		"createdBy":{"id":"58ba5ee3-734e-4ae9-b9c5-943774b4de41","firstName":"System","lastName":"Administrator"},
    		"modifiedBy":{"id":"58ba5ee3-734e-4ae9-b9c5-943774b4de41","firstName":"System","lastName":"Administrator"},
    		"createdDate":1331269200000
    	}];
    	json.rows = rows;

		this.documentsStore.loadData(json.rows);
		
		return this.callParent(arguments);
    },
 
    onViewReady: function(comp, obj){
    	this.appEventsController.assignEvent({eventName: 'editDocument', callBackFunc: this.editDocument, scope: this});
    	this.appEventsController.assignEvent({eventName: 'deleteDocument', callBackFunc: this.deleteConfirmation, scope: this});
    },    
 
    destroy: function() {
    	this.appEventsController.removeEvent({eventName: 'editDocument', callBackFunc: this.editDocument, scope: this});
    	this.appEventsController.removeEvent({eventName: 'deleteDocument', callBackFunc: this.deleteConfirmation, scope: this});

        return this.callParent( arguments );
    },    
    
    onAddClick: function(button){
    	var document = new Ssp.model.PersonDocument();
    	this.model.data = document.data;
    	this.loadEditor();
    },
    
    editDocument: function(){
    	this.loadEditor();
    },
 
    deleteConfirmation: function() {
        var message = 'You are about to delete a document. Would you like to continue?';
    	var model = this.model;
        if (model.get('id') != "") 
        {
           Ext.Msg.confirm({
   		     title:'Delete Document?',
   		     msg: message,
   		     buttons: Ext.Msg.YESNO,
   		     fn: this.deleteDocument,
   		     scope: this
   		   });
        }else{
     	   Ext.Msg.alert('SSP Error', 'Unable to delete document.'); 
        }
     },
     
     deleteDocument: function( btnId ){
     	var store = this.documentsStore;
     	var id = this.model.get('id');
     	if (btnId=="yes")
     	{
     		this.apiProperties.makeRequest({
      		   url: this.personDocumentUrl+"/"+id,
      		   method: 'DELETE',
      		   successFunc: function(response,responseText){
      			   store.remove( store.getById( id ) );
      		   }
      	    });   		
     	}
     },    
    
    loadEditor: function(){
		var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {});    	
    }
});
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
Ext.define('Ssp.controller.tool.document.EditDocumentViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
        appEventsController: 'appEventsController',
    	formUtils: 'formRendererUtils',
    	person: 'currentPerson',
    	model: 'currentDocument'
    },
    config: {
    	containerToLoadInto: 'tools',
    	formToDisplay: 'studentdocuments',
    	url: '',
    	inited: false
    },

    control: {
    	'saveButton': {
			click: 'onSaveClick'
		},
		
		'cancelButton': {
			click: 'onCancelClick'
		}   	
    },
    
	init: function() {
		this.url = this.apiProperties.createUrl( this.apiProperties.getItemUrl('personDocument') );
		this.url = this.url.replace('{id}',this.person.get('id'));
		
		this.initForm();
		
		return this.callParent(arguments);
    },
 
	initForm: function(){
		var id = this.model.get("id");
		this.getView().getForm().reset();
		this.getView().getForm().loadRecord( this.model );
		if (id != null && id != "")
		{
			Ext.ComponentQuery.query('#confidentialityLevelCombo')[0].setValue( this.model.get('confidentialityLevel').id );
		}
		this.inited=true;
	},    
    
	onSaveClick: function(button) {
		var me = this;
		var record, id, jsonData, url;
		var form = this.getView().getForm();
		var values = form.getValues();
		url = this.url;
		record = this.model;
		id = record.get('id');
		
		successFunc = function(response, view) {
			me.displayMain();
		};
		
		if (form.isValid())
		{
			form.updateRecord();    		
    		record.set('confidentialityLevel',{"id": form.getValues().confidentialityLevelId});

			jsonData = record.data;
			
			if (id.length > 0)
			{
				// editing
				this.apiProperties.makeRequest({
					url: url+"/"+id,
					method: 'PUT',
					jsonData: jsonData,
					successFunc: successFunc 
				});		
			}else{
				// adding
				this.apiProperties.makeRequest({
					url: url,
					method: 'POST',
					jsonData: jsonData,
					successFunc: successFunc 
				});		
			}
		
		}else{
			Ext.Msg.alert('Error','Please correct the errors in your document.');
		}

	},
	
	onCancelClick: function(button){
		this.displayMain();
	},
	
	displayMain: function(){
		var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {});
	}
});
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
Ext.define('Ssp.controller.tool.sis.TranscriptViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	service: 'transcriptService',
        personLite: 'personLite',
        store: 'courseTranscriptsStore'
    },
	init: function() {
		var me=this;
		var personId = me.personLite.get('id');

        me.store.removeAll();

    	me.getView().setLoading( true );
    	
		me.service.getFull( personId, {
			success: me.getTranscriptSuccess,
			failure: me.getTranscriptFailure,
			scope: me			
		});
		
		return this.callParent(arguments);
    },
    
    getTranscriptSuccess: function( r, scope ){
    	var me=scope;

        var courseTranscripts = [];
        var transcript = new Ssp.model.Transcript(r);
        var terms = transcript.get('terms');
        if ( terms ) {
            Ext.Array.each(terms, function(term) {
                Ext.Array.each(term.courses, function(course) {
                    var courseTranscript = Ext.create('Ssp.model.CourseTranscript', course);
                    courseTranscript.set('termCode', term.code);
                    courseTranscripts.push(courseTranscript);
                });
            });
        }

        me.store.loadData(courseTranscripts);
        me.getView().setLoading( false );
    },
    
    getTranscriptFailure: function( response, scope ){
    	var me=scope;
    	me.getView().setLoading( false );  	
    }
});
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
Ext.define('Ssp.view.tools.profile.Transcript', {
	extend: 'Ext.grid.Panel',
	alias: 'widget.transcript',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.sis.TranscriptViewController',
    inject: {
        store: 'courseTranscriptsStore'
    },
    width: '100%',
    height: '100%',
    autoScroll: true,
    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            store: me.store,
            columns: [
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'formattedCourse',
                    text: 'Course',
                    flex: 1
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'title',
                    text: 'Title',
                    flex: 1
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'creditType',
                    text: 'Credit Type',
                    flex: 1
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'grade',
                    text: 'Grade',
                    sortable: 'false',
                    flex: 1
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'termCode',
                    text: 'Term',
                    flex: 1
                }
            ],
            viewConfig: {
                markDirty:false
            }
        });

        me.callParent(arguments);
    }
});
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
Ext.define('Ssp.controller.admin.AbstractReferenceAdminViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	authenticatedPerson: 'authenticatedPerson'
    },  
    control: {
		view: {
			beforeedit: 'onBeforeEdit',
			edit: 'editRecord'
		},
		
		'addButton': {
			click: 'addRecord'
		},

		'deleteButton': {
			click: 'deleteConfirmation'
		},
		
		recordPager: '#recordPager'
    },
    
	init: function() {
		return this.callParent(arguments);
    },

    onBeforeEdit: function( editor, e, eOpts ){
		var me=this;
		var access = me.authenticatedPerson.hasAccess('ABSTRACT_REFERENCE_ADMIN_EDIT');
		// Test if the record is restricted content 
		if ( me.authenticatedPerson.isDeveloperRestrictedContent( e.record ) )
		{
			me.authenticatedPerson.showDeveloperRestrictedContentAlert();
			return false;
		}		

		if ( access == false)
		{
			me.authenticatedPerson.showUnauthorizedAccessAlert();
		}
    	return access;
    },
    
	editRecord: function(editor, e, eOpts) {
		var record = e.record;
		var id = record.get('id');
		var jsonData = record.data;
		Ext.Ajax.request({
			url: editor.grid.getStore().getProxy().url+"/"+id,
			method: 'PUT',
			headers: { 'Content-Type': 'application/json' },
			jsonData: jsonData,
			success: function(response, view) {
				var r = Ext.decode(response.responseText);
				record.commit();
				editor.grid.getStore().sync();
			},
			failure: this.apiProperties.handleError
		}, this);
	},
	
	addRecord: function(button){
		var me=this;
		var grid = button.up('grid');
		var store = grid.getStore();
		var item = Ext.create( store.model.modelName, {}); // new Ssp.model.reference.AbstractReference();
		
		// Test if the record is restricted content	
		if ( me.authenticatedPerson.isDeveloperRestrictedContent( item ) )
		{
			me.authenticatedPerson.showDeveloperRestrictedContentAlert();
			return false;
		}
		
		// default the name property
		item.set('name','default');
		//additional required columns defined in the Admin Tree Menus Store
		Ext.Array.each(grid.columns,function(col,index){
       		if (col.required==true)
       			item.set(col.dataIndex,'default');
       	});
		
		// If the object type has a sort order prop
		// then set the sort order to the next available
		// item in the database
		if (item.sortOrder != null)
		{
			item.set('sortOrder',store.getTotalCount()+1);
		}

		// Save the item
		Ext.Ajax.request({
			url: grid.getStore().getProxy().url,
			method: 'POST',
			headers: { 'Content-Type': 'application/json' },
			jsonData: item.data,
			success: function(response, view) {
				var r = Ext.decode(response.responseText);
				item.populateFromGenericObject(r);
				store.insert(0, item );
		       	grid.plugins[0].startEdit(0, 0);
		       	grid.plugins[0].editor.items.getAt(0).selectText();
		       	store.totalCount = store.totalCount+1;
		       	me.getRecordPager().onLoad();
			},
			failure: me.apiProperties.handleError
		}, me);
	},

    deleteConfirmation: function( button ) {
 	   var me=this;
       var grid = button.up('grid');
       var store = grid.getStore();
       var selection = grid.getView().getSelectionModel().getSelection()[0];
       var message;

       if (selection != null && selection.get('id') ) 
       {
    	   // Test if the record is restricted content 
           if ( me.authenticatedPerson.isDeveloperRestrictedContent( selection ) )
    	   {
    			me.authenticatedPerson.showDeveloperRestrictedContentAlert();
    			return false;
    	   }    	   
    	   
    	   if ( !Ssp.util.Constants.isRestrictedAdminItemId( selection.get('id')  ) )
    	   {
        	   message = 'You are about to delete ' + selection.get('name') + '. Would you like to continue?';
	      	   
               Ext.Msg.confirm({
       		     title:'Delete?',
       		     msg: message,
       		     buttons: Ext.Msg.YESNO,
       		     fn: me.deleteRecord,
       		     scope: me
       		   });
               
    	   }else{
    		   Ext.Msg.alert('WARNING', 'This item is related to core SSP functionality. Please see a developer to delete this item.'); 
    	   }
        }else{
     	   Ext.Msg.alert('SSP Error', 'Please select an item to delete.'); 
        }
     },	
	
	deleteRecord: function( btnId ){
		var me=this;
		var grid=me.getView();
		var store = grid.getStore();
	    var selection = grid.getView().getSelectionModel().getSelection()[0];
     	var id = selection.get('id');
     	if (btnId=="yes")
     	{
     		me.apiProperties.makeRequest({
       		   url: store.getProxy().url+"/"+id,
       		   method: 'DELETE',
       		   successFunc: function(response,responseText){
       			   var r = Ext.decode(response.responseText);
       			   if (Boolean(r.success)==true)
       			   {
       				store.remove( store.getById( id ) );
       				store.totalCount = store.totalCount-1;
       				me.getRecordPager().onLoad();
       			    me.getRecordPager().doRefresh();
       			   }
       		   }
       	    });
       }
	}
});
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
Ext.define('Ssp.controller.admin.ConfidentialityDisclosureAgreementAdminViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	formUtils: 'formRendererUtils',
    	store: 'confidentialityDisclosureAgreementsStore',
    	service: 'confidentialityDisclosureAgreementService'
    },
    
    control: {
		'saveButton': {
			click: 'save'
		},
		
		saveSuccessMessage: '#saveSuccessMessage'
    },
    
	init: function() {
		this.store.load({scope: this, callback: this.loadConfidentialityDisclosureAgreementResult});
		
		return this.callParent(arguments);
    }, 
    
    loadConfidentialityDisclosureAgreementResult: function(records, operation, success){
    	var model = new Ssp.model.reference.ConfidentialityDisclosureAgreement();
    	if (success)
    	{
        	if ( records.length > 0 )
        	{
        		model.populateFromGenericObject(records[0].data);
        	}    		
    	}
    	this.getView().loadRecord( model );
    },
    
	save: function(button){
		var record, id, jsonData;
		var me=this;
		var view = me.getView();
		view.getForm().updateRecord();
		record = view.getRecord();
		id = record.get('id');
		jsonData = record.data;
		
		view.setLoading(true);

		me.service.save( jsonData, {
			success: me.saveSuccess,
			failure: me.saveFailure,
			scope: me
		});
	},
	
	saveSuccess: function( r, scope ){
		var me=scope;
		me.getView().setLoading(false);
		me.formUtils.displaySaveSuccessMessage( me.getSaveSuccessMessage() );
	},
	
    saveFailure: function( response, scope ){
    	var me=scope;  
		me.getView().setLoading(false);
    }
});
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
Ext.define('Ssp.controller.admin.crg.ChallengeAdminViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	confidentialityLevelsStore: 'confidentialityLevelsStore'
    },
	init: function() {
		this.confidentialityLevelsStore.load();	
		return this.callParent(arguments);
    }
});
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
Ext.define('Ssp.controller.admin.crg.ChallengeReferralAdminViewController', {
    extend: 'Deft.mvc.ViewController',
	init: function() {
		return this.callParent(arguments);
    }
});
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
Ext.define('Ssp.controller.admin.crg.DisplayChallengesAdminViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	store: 'challengesStore',
    	formUtils: 'formRendererUtils',
    	model: 'currentChallenge'
    },
    config: {
    	containerToLoadInto: 'adminforms',
    	formToDisplay: 'editchallenge'
    },
    control: {
    	'editButton': {
			click: 'onEditClick'
		},
		
		'addButton': {
			click: 'onAddClick'
		},

		'deleteButton': {
			click: 'deleteConfirmation'
		}    	
    },       
	init: function() {
		var me=this;
		
		me.formUtils.reconfigureGridPanel( me.getView(), me.store);
		me.store.load();
		
		return me.callParent(arguments);
    }, 
    
	onEditClick: function(button) {
		var grid, record;
		grid = button.up('grid');
		record = grid.getView().getSelectionModel().getSelection()[0];
        if (record) 
        {		
        	this.model.data=record.data;
        	this.displayEditor();
        }else{
     	   Ext.Msg.alert('SSP Error', 'Please select an item to edit.'); 
        }
	},
	
	onAddClick: function(button){
		var model = new Ssp.model.reference.Challenge();
		this.model.data = model.data;
		this.displayEditor();
	},
	
    deleteConfirmation: function( button ) {
  	   var me=this;
        var grid = button.up('grid');
        var store = grid.getStore();
        var selection = grid.getView().getSelectionModel().getSelection()[0];
        var message;
        if ( selection.get('id') ) 
        {
      	   if ( !Ssp.util.Constants.isRestrictedAdminItemId( selection.get('id')  ) )
    	   {
      		    message = 'You are about to delete ' + selection.get('name') + '. Would you like to continue?';
		     	      	   
	            Ext.Msg.confirm({
	    		     title:'Delete?',
	    		     msg: message,
	    		     buttons: Ext.Msg.YESNO,
	    		     fn: me.deleteRecord,
	    		     scope: me
	    		   });
	 	   }else{
			   Ext.Msg.alert('WARNING', 'This item is related to core SSP functionality. Please see a developer to delete this item.'); 
		   }
         }else{
      	   Ext.Msg.alert('SSP Error', 'Unable to delete item.'); 
         }
      },	
 	
 	deleteRecord: function( btnId ){
 		var me=this;
 		var grid=me.getView();
 		var store = grid.getStore();
 	    var selection = grid.getView().getSelectionModel().getSelection()[0];
      	var id = selection.get('id');
      	if (btnId=="yes")
      	{
      		me.apiProperties.makeRequest({
        		   url: store.getProxy().url+"/"+id,
        		   method: 'DELETE',
        		   successFunc: function(response,responseText){
        			   store.remove( store.getById( id ) );
        		   }
        	    });
        }
 	},
	
	displayEditor: function(){
		var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {});
	}
});
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
Ext.define('Ssp.controller.admin.crg.DisplayReferralsAdminViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	store: 'challengeReferralsStore',
    	formUtils: 'formRendererUtils',
    	model: 'currentChallengeReferral'
    },
    config: {
    	containerToLoadInto: 'adminforms',
    	formToDisplay: 'editreferral'
    },
    control: {
		'editButton': {
			click: 'onEditClick'
		},
		
		'addButton': {
			click: 'onAddClick'
		},

		'deleteButton': {
			click: 'deleteConfirmation'
		}    	
    },       
	init: function() {
		var me=this;
		
		me.formUtils.reconfigureGridPanel( me.getView(), me.store);
		me.store.load();
		
		return me.callParent(arguments);
    },

	onEditClick: function(button) {
		var grid, record;
		grid = button.up('grid');
		record = grid.getView().getSelectionModel().getSelection()[0];
        if (record) 
        {		
        	this.model.data=record.data;
        	this.displayEditor();
        }else{
     	   Ext.Msg.alert('SSP Error', 'Please select an item to edit.'); 
        }
	},
	
	onAddClick: function(button){
		var model = new Ssp.model.reference.ChallengeReferral();
		this.model.data = model.data;
		this.displayEditor();
	},
	
    deleteConfirmation: function( button ) {
  	   var me=this;
        var grid = button.up('grid');
        var store = grid.getStore();
        var selection = grid.getView().getSelectionModel().getSelection()[0];
        var message;
        if ( selection.get('id') ) 
        {
     	   message = 'You are about to delete ' + selection.get('name') + '. Would you like to continue?';
     	      	   
            Ext.Msg.confirm({
    		     title:'Delete?',
    		     msg: message,
    		     buttons: Ext.Msg.YESNO,
    		     fn: me.deleteRecord,
    		     scope: me
    		   });
         }else{
      	   Ext.Msg.alert('SSP Error', 'Unable to delete item.'); 
         }
      },	
 	
 	deleteRecord: function( btnId ){
 		var me=this;
 		var grid=me.getView();
 		var store = grid.getStore();
 	    var selection = grid.getView().getSelectionModel().getSelection()[0];
      	var id = selection.get('id');
      	if (btnId=="yes")
      	{
      		me.apiProperties.makeRequest({
        		   url: store.getProxy().url+"/"+id,
        		   method: 'DELETE',
        		   successFunc: function(response,responseText){
        			   store.remove( store.getById( id ) );
        		   }
        	    });
        }
 	},
	
	displayEditor: function(){
		var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {});
	}
});
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
Ext.define('Ssp.controller.admin.crg.EditChallengeViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	formUtils: 'formRendererUtils',
    	model: 'currentChallenge',
    	store: 'challengesStore'
    },
    config: {
    	containerToLoadInto: 'adminforms',
    	formToDisplay: 'challengeadmin'
    },
    control: {
    	'saveButton': {
			click: 'onSaveClick'
		},
		
		'cancelButton': {
			click: 'onCancelClick'
		}   	
    },
    
	init: function() {
		this.getView().getForm().loadRecord(this.model);
		return this.callParent(arguments);
    },
    
	onSaveClick: function(button) {
		var me = this;
		var record, id, jsonData, url;
		url = this.store.getProxy().url;
		this.getView().getForm().updateRecord();
		record = this.model;
		id = record.get('id');
		jsonData = record.data;
		successFunc = function(response, view) {
			me.displayMain();
		};
		
		if (id.length > 0)
		{
			// editing
			this.apiProperties.makeRequest({
				url: url+"/"+id,
				method: 'PUT',
				jsonData: jsonData,
				successFunc: successFunc 
			});
			
		}else{
			// adding
			this.apiProperties.makeRequest({
				url: url,
				method: 'POST',
				jsonData: jsonData,
				successFunc: successFunc 
			});		
		}
	},
	
	onCancelClick: function(button){
		this.displayMain();
	},
	
	displayMain: function(){
		var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {});
	}
});
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
Ext.define('Ssp.controller.admin.crg.EditReferralViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	formUtils: 'formRendererUtils',
    	model: 'currentChallengeReferral',
    	store: 'challengeReferralsStore'
    },
    config: {
    	containerToLoadInto: 'adminforms',
    	formToDisplay: 'challengereferraladmin'
    },
    control: {
    	'saveButton': {
			click: 'onSaveClick'
		},
		
		'cancelButton': {
			click: 'onCancelClick'
		}   	
    },
    
	init: function() {
		this.getView().getForm().loadRecord(this.model);
		return this.callParent(arguments);
    },
    
	onSaveClick: function(button) {
		var me = this;
		var record, id, jsonData, url;
		url = this.store.getProxy().url;
		this.getView().getForm().updateRecord();
		record = this.model;
		id = record.get('id');
		jsonData = record.data;
		successFunc = function(response, view) {
			me.displayMain();
		};
		
		if (id.length > 0)
		{
			// editing
			this.apiProperties.makeRequest({
				url: url+"/"+id,
				method: 'PUT',
				jsonData: jsonData,
				successFunc: successFunc 
			});
			
		}else{
			// adding
			this.apiProperties.makeRequest({
				url: url,
				method: 'POST',
				jsonData: jsonData,
				successFunc: successFunc 
			});		
		}
	},
	
	onCancelClick: function(button){
		this.displayMain();
	},
	
	displayMain: function(){
		var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {});
	}
});
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
Ext.define('Ssp.controller.admin.journal.JournalStepAdminViewController', {
    extend: 'Deft.mvc.ViewController',
	init: function() {	
		return this.callParent(arguments);
    }
});
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
Ext.define('Ssp.controller.admin.journal.JournalStepDetailAdminViewController', {
    extend: 'Deft.mvc.ViewController',
	init: function() {	
		return this.callParent(arguments);
    }
});
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
Ext.define('Ssp.controller.admin.journal.DisplayDetailsAdminViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	store: 'journalDetailsStore',
    	formUtils: 'formRendererUtils',
    	model: 'currentJournalStepDetail'
    },
    config: {
    	containerToLoadInto: 'adminforms',
    	formToDisplay: 'editjournalstepdetail'
    },
    control: {  	
    	'editButton': {
			click: 'onEditClick'
		},
		
		'addButton': {
			click: 'onAddClick'
		},

		'deleteButton': {
			click: 'deleteConfirmation'
		}    	
    },       
    
	init: function() {
		var me=this;
		
		me.formUtils.reconfigureGridPanel( me.getView(), me.store);
		me.store.load();
		
		return me.callParent(arguments);
    },    
    
	onEditClick: function(button) {
		var grid, record;
		grid = button.up('grid');
		record = grid.getView().getSelectionModel().getSelection()[0];
        if (record) 
        {		
        	this.model.data=record.data;
        	this.displayEditor();
        }else{
     	   Ext.Msg.alert('SSP Error', 'Please select an item to edit.'); 
        }
	},
	
	onAddClick: function(button){
		var model = new Ssp.model.reference.JournalStepDetail();
		this.model.data = model.data;
		this.displayEditor();
	},

    deleteConfirmation: function( button ) {
  	   var me=this;
        var grid = button.up('grid');
        var store = grid.getStore();
        var selection = grid.getView().getSelectionModel().getSelection()[0];
        var message;
        if ( selection.get('id') ) 
        {
     	   message = 'You are about to delete ' + selection.get('name') + '. Would you like to continue?';
     	      	   
            Ext.Msg.confirm({
    		     title:'Delete?',
    		     msg: message,
    		     buttons: Ext.Msg.YESNO,
    		     fn: me.deleteRecord,
    		     scope: me
    		   });
         }else{
      	   Ext.Msg.alert('SSP Error', 'Unable to delete item.'); 
         }
      },	
 	
 	deleteRecord: function( btnId ){
 		var me=this;
 		var grid=me.getView();
 		var store = grid.getStore();
 	    var selection = grid.getView().getSelectionModel().getSelection()[0];
      	var id = selection.get('id');
      	if (btnId=="yes")
      	{
      		me.apiProperties.makeRequest({
        		   url: store.getProxy().url+"/"+id,
        		   method: 'DELETE',
        		   successFunc: function(response,responseText){
        			   store.remove( store.getById( id ) );
        		   }
        	    });
        }
 	},
	
	displayEditor: function(){
		var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {});
	}
});
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
Ext.define('Ssp.controller.admin.journal.DisplayStepsAdminViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	store: 'journalStepsStore',
    	formUtils: 'formRendererUtils',
    	model: 'currentJournalStep'
    },
    config: {
    	containerToLoadInto: 'adminforms',
    	formToDisplay: 'editjournalstep'
    },
    control: {  	
    	'editButton': {
			click: 'onEditClick'
		},
		
		'addButton': {
			click: 'onAddClick'
		},

		'deleteButton': {
			click: 'deleteConfirmation'
		}    	
    },       
    
	init: function() {
		var me=this;
		
		me.formUtils.reconfigureGridPanel( me.getView(), me.store);
		me.store.load();
		
		return me.callParent(arguments);
    },    
    
	onEditClick: function(button) {
		var grid, record;
		grid = button.up('grid');
		record = grid.getView().getSelectionModel().getSelection()[0];
        if (record) 
        {		
        	this.model.data=record.data;
        	this.displayEditor();
        }else{
     	   Ext.Msg.alert('SSP Error', 'Please select an item to edit.'); 
        }
	},
	
	onAddClick: function(button){
		var model = new Ssp.model.reference.JournalStep();
		this.model.data = model.data;
		this.displayEditor();
	},
	
    deleteConfirmation: function( button ) {
  	   var me=this;
        var grid = button.up('grid');
        var store = grid.getStore();
        var selection = grid.getView().getSelectionModel().getSelection()[0];
        var message;
        if ( selection.get('id') ) 
        {
     	   message = 'You are about to delete ' + selection.get('name') + '. Would you like to continue?';
     	      	   
            Ext.Msg.confirm({
    		     title:'Delete?',
    		     msg: message,
    		     buttons: Ext.Msg.YESNO,
    		     fn: me.deleteRecord,
    		     scope: me
    		   });
         }else{
      	   Ext.Msg.alert('SSP Error', 'Unable to delete item.'); 
         }
      },	
 	
 	deleteRecord: function( btnId ){
 		var me=this;
 		var grid=me.getView();
 		var store = grid.getStore();
 	    var selection = grid.getView().getSelectionModel().getSelection()[0];
      	var id = selection.get('id');
      	if (btnId=="yes")
      	{
      		me.apiProperties.makeRequest({
        		   url: store.getProxy().url+"/"+id,
        		   method: 'DELETE',
        		   successFunc: function(response,responseText){
        			   store.remove( store.getById( id ) );
        		   }
        	    });
        }
 	},
	
	displayEditor: function(){
		var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {});
	}
});
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
Ext.define('Ssp.controller.admin.journal.EditStepViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	formUtils: 'formRendererUtils',
    	model: 'currentJournalStep',
    	store: 'journalStepsStore'
    },
    config: {
    	containerToLoadInto: 'adminforms',
    	formToDisplay: 'journalstepadmin'
    },
    control: {
    	'saveButton': {
			click: 'onSaveClick'
		},
		
		'cancelButton': {
			click: 'onCancelClick'
		}   	
    },
    
	init: function() {
		this.getView().getForm().loadRecord(this.model);
		return this.callParent(arguments);
    },
    
	onSaveClick: function(button) {
		var me = this;
		var record, id, jsonData, url;
		url = this.store.getProxy().url;
		this.getView().getForm().updateRecord();
		record = this.model;
		id = record.get('id');
		jsonData = record.data;
		successFunc = function(response, view) {
			me.displayMain();
		};
		
		if (id.length > 0)
		{
			// editing
			this.apiProperties.makeRequest({
				url: url+"/"+id,
				method: 'PUT',
				jsonData: jsonData,
				successFunc: successFunc 
			});
			
		}else{
			// adding
			this.apiProperties.makeRequest({
				url: url,
				method: 'POST',
				jsonData: jsonData,
				successFunc: successFunc 
			});		
		}
	},
	
	onCancelClick: function(button){
		this.displayMain();
	},
	
	displayMain: function(){
		var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {});
	}
});
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
Ext.define('Ssp.controller.admin.journal.EditStepDetailViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	formUtils: 'formRendererUtils',
    	model: 'currentJournalStepDetail',
    	store: 'journalDetailsStore'
    },
    config: {
    	containerToLoadInto: 'adminforms',
    	formToDisplay: 'journalstepdetailadmin'
    },
    control: {
    	'saveButton': {
			click: 'onSaveClick'
		},
		
		'cancelButton': {
			click: 'onCancelClick'
		}   	
    },
    
	init: function() {
		this.getView().getForm().loadRecord(this.model);
		return this.callParent(arguments);
    },
    
	onSaveClick: function(button) {
		var me = this;
		var record, id, jsonData, url;
		url = this.store.getProxy().url;
		this.getView().getForm().updateRecord();
		record = this.model;
		id = record.get('id');
		jsonData = record.data;
		successFunc = function(response, view) {
			me.displayMain();
		};
		
		if (id.length > 0)
		{
			// editing
			this.apiProperties.makeRequest({
				url: url+"/"+id,
				method: 'PUT',
				jsonData: jsonData,
				successFunc: successFunc 
			});
			
		}else{
			// adding
			this.apiProperties.makeRequest({
				url: url,
				method: 'POST',
				jsonData: jsonData,
				successFunc: successFunc 
			});		
		}
	},
	
	onCancelClick: function(button){
		this.displayMain();
	},
	
	displayMain: function(){
		var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {});
	}
});
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
Ext.define('Ssp.controller.admin.campus.CampusAdminViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	appEventsController: 'appEventsController',
    	campusService: 'campusService',
    	campusEarlyAlertRouting: 'currentCampusEarlyAlertRouting',
    	campusesStore: 'campusesStore',
    	earlyAlertCoordinatorsStore: 'earlyAlertCoordinatorsStore',
    	earlyAlertReasonsStore: 'earlyAlertReasonsStore',
    	formUtils: 'formRendererUtils',
    	model: 'currentCampus',
    	peopleStore: 'peopleStore'
    },
    config: {
    	containerToLoadInto: 'adminforms',
    	campusEditorForm: 'editcampus',
    	campusEarlyAlertRoutingAdminForm: 'campusEarlyAlertRoutingsAdmin'
    },
    control: {
    	view: {
    		viewready: 'onViewReady'
    	},
    	
    	'editButton': {
			click: 'onEditClick'
		},
		
		'addButton': {
			click: 'onAddClick'
		},

		'deleteButton': {
			click: 'deleteConfirmation'
		} 	
    },
	init: function() {
		var me=this;
		me.campusesStore.load();
		me.earlyAlertCoordinatorsStore.load();
		me.earlyAlertReasonsStore.load();
		me.peopleStore.load();
		return this.callParent(arguments);
    },

    onViewReady: function(comp, obj){
    	var me=this;
    	me.appEventsController.assignEvent({eventName: 'editCampusEarlyAlertRoutings', callBackFunc: me.onEditCampusEarlyAlertRoutings, scope: me});
    },    
 
    destroy: function() {
    	var me=this;
    	
    	me.appEventsController.removeEvent({eventName: 'editCampusEarlyAlertRoutings', callBackFunc: me.onEditCampusEarlyAlertRoutings, scope: me});

    	return me.callParent( arguments );
    },

    onEditCampusEarlyAlertRoutings: function(){
		var me=this;
    	var model = new Ssp.model.reference.CampusEarlyAlertRouting();
		me.campusEarlyAlertRouting.data = model.data;
		me.displayCampusEarlyAlertRoutingAdmin();
    }, 
    
	onEditClick: function(button) {
		var grid, record;
		grid = button.up('grid');
		record = grid.getView().getSelectionModel().getSelection()[0];
        if (record) 
        {		
        	this.model.data=record.data;
        	this.displayCampusEditor();
        }else{
     	   Ext.Msg.alert('SSP Error', 'Please select an item to edit.'); 
        }
	},
	
	onAddClick: function(button){
		var model = new Ssp.model.reference.Campus();
		this.model.data = model.data;
		this.displayCampusEditor();
	},
	
    deleteConfirmation: function( button ) {
  	   var me=this;
        var grid = button.up('grid');
        var store = grid.getStore();
        var selection = grid.getView().getSelectionModel().getSelection()[0];
        var message;
        if ( selection.get('id') ) 
        {
     	   message = 'You are about to delete ' + selection.get('name') + '. Would you like to continue?';
     	      	   
            Ext.Msg.confirm({
    		     title:'Delete?',
    		     msg: message,
    		     buttons: Ext.Msg.YESNO,
    		     fn: me.deleteRecord,
    		     scope: me
    		   });
         }else{
      	   Ext.Msg.alert('SSP Error', 'Unable to delete item.'); 
         }
      },	
 	
 	deleteRecord: function( btnId ){
 		var me=this;
 		var grid=me.getView();
 		var store = grid.getStore();
 	    var selection = grid.getView().getSelectionModel().getSelection()[0];
      	var id = selection.get('id');
      	if (btnId=="yes")
      	{
     		me.getView().setLoading( true );
     		me.campusService.destroy( id, {
     			success: me.destroyCampusSuccess,
     			failure: me.destroyCampusFailure,
     			scope: me
     		});
        }
 	},
 	
    destroyCampusSuccess: function( r, id, scope ) {
 		var me=scope;
 		var grid=me.getView();
 		var store = grid.getStore();
 		me.getView().setLoading( false );
 		store.remove( store.getById( id ) );
 	},

 	destroyCampusFailure: function( response, scope ) {
 		var me=scope;
 		me.getView().setLoading( false );
 	}, 

	displayCampusEarlyAlertRoutingAdmin: function(){
		var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getCampusEarlyAlertRoutingAdminForm(), true, {});
	},
 	
	displayCampusEditor: function(){
		var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getCampusEditorForm(), true, {});
	}
});
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
Ext.define('Ssp.controller.admin.campus.DefineCampusViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	campusService: 'campusService',
    	formUtils: 'formRendererUtils',
    	model: 'currentCampus',
    	store: 'campusesStore'
    },
    config: {
    	panelLayout: null,
    	containerToLoadInto: 'adminforms',
    	formToDisplay: 'campusadmin'
    },
    control: {  	
    	prevBtn: '#prevButton',
    	nextBtn: '#nextButton',
    	finishBtn: '#finishButton',
    	
    	'nextButton': {
			click: 'onNextClick'
		},
		
		'prevButton': {
			click: 'onPrevClick'
		},
		
		'finishButton': {
			click: 'onFinishClick'
		},
		
		'cancelCampusEditorButton': {
			click: 'onCancelClick'
		}
    },
	init: function() {
		this.store.load();
		this.panelLayout = this.getView().getLayout();
		this.enableButtons();
		return this.callParent(arguments);
    },

    navigate: function( direction ){
        this.panelLayout[direction]();
        this.enableButtons();
    },
    
    enableButtons: function(){
    	var layout = this.panelLayout;
    	this.getPrevBtn().setDisabled(!layout.getPrev());
        this.getNextBtn().setDisabled(!layout.getNext());
        if (!layout.getNext())
        {
        	this.getFinishBtn().setDisabled(false);
        }else{
        	this.getFinishBtn().setDisabled(true);
        }
    },
    
	onNextClick: function(button) {
		this.navigate("next");
	},
	
	onPrevClick: function(button){
		this.navigate("prev");
	},
	
	onFinishClick: function(button){
		var me=this;
		var campusView = Ext.ComponentQuery.query('.editcampus')[0];
		var campusForm = campusView.getForm();		
		var formsToValidate = [campusForm];
		var validateResult = me.formUtils.validateForms( formsToValidate );
		
		// validate the campus and save
		if ( validateResult.valid ) 
		{
			campusForm.updateRecord();
			me.getView().setLoading( true );
			me.campusService.saveCampus( me.model.data, {success:me.saveCampusSuccess, 
				  failure:me.saveCampusFailure, 
				  scope: me} );		
		}else{
			me.formUtils.displayErrors( validateResult.fields );
		}
	},

    saveCampusSuccess: function( r, scope ){
		var me=scope;
		me.getView().setLoading( false );
		me.displayMain();
    },
    
    saveCampusFailure: function( response, scope ){
    	var me=scope;  	
    	me.getView().setLoading( false );
    },

	onCancelClick: function(button){
		this.displayMain();
	},
    
	displayMain: function(){
		var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {});	
	}
});
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
Ext.define('Ssp.controller.admin.campus.EditCampusViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	campusService: 'campusService',
    	formUtils: 'formRendererUtils',
    	model: 'currentCampus',
    	store: 'campusesStore'
    },
    config: {
    	containerToLoadInto: 'adminforms',
    	formToDisplay: 'campusadmin',
    	url: null
    },
    control: {
    	'saveButton': {
			click: 'onSaveClick'
		},
		
		'cancelButton': {
			click: 'onCancelClick'
		}   	
    },
	init: function() {
		this.getView().getForm().reset();
		this.getView().getForm().loadRecord( this.model );
		return this.callParent(arguments);
    },
	onSaveClick: function(button) {
		var me = this; 
		me.getView().getForm().updateRecord();
		me.getView().setLoading( true );
		me.campusService.saveCampus( me.model.data, {
			success: me.saveSuccess,
			failure: me.saveFailure,
			scope: me
		} );
	},
	
	onCancelClick: function(button){
		this.displayMain();
	},

    saveSuccess: function( r, scope ){
		var me=scope;
		me.getView().setLoading( false );
		me.displayMain();
    },
    
    saveFailure: function( response, scope ){
    	var me=scope;  	
    	me.getView().setLoading( false );
    },	
	
	displayMain: function(){
		var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {});
	}
});
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
Ext.define('Ssp.controller.admin.campus.CampusEarlyAlertRoutingsAdminViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	formUtils: 'formRendererUtils'
    },
    config: {
    	containerToLoadInto: 'adminforms',
    	campusEditorForm: 'campusadmin'
    },
    control: {
    	'finishButton': {
			click: 'onFinishClick'
		}
    },	
    init: function() {
		return this.callParent(arguments);
    },
    
	onFinishClick: function( button ){
		var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getCampusEditorForm(), true, {});
	}
});
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
Ext.define('Ssp.controller.admin.campus.EarlyAlertRoutingsAdminViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	store: 'campusEarlyAlertRoutingsStore',
    	service: 'campusEarlyAlertRoutingService',
    	formUtils: 'formRendererUtils',
    	campus: 'currentCampus',
    	model: 'currentCampusEarlyAlertRouting'
    },
    config: {
    	containerToLoadInto: 'campusearlyalertroutingsadmin',
    	formToDisplay: 'editcampusearlyalertrouting'
    },
    control: {  	
    	'editButton': {
			click: 'onEditClick'
		},
		
		'addButton': {
			click: 'onAddClick'
		},

		'deleteButton': {
			click: 'deleteConfirmation'
		} 	
    },
	init: function() {
		var me=this;
		var campusId = me.campus.get('id');
		me.getView().setLoading( true );
		me.service.getAllCampusEarlyAlertRoutings( campusId, {
			success: me.getAllCampusEarlyAlertRoutingsSuccess,
			failure: me.getAllCampusEarlyAlertRoutingsFailure,
			scope: me
		});
		return me.callParent(arguments);
    },

    getAllCampusEarlyAlertRoutingsSuccess: function( r, scope ){
		var me=scope;
		me.getView().setLoading( false );
    },
    
    getAllCampusEarlyAlertRoutingsFailure: function( response, scope ){
    	var me=scope;  	
    	me.getView().setLoading( false );
    },    
    
	onEditClick: function(button) {
		var me=this;
		var grid, record;
		grid = button.up('grid');
		record = grid.getView().getSelectionModel().getSelection()[0];
        if (record) 
        {		
        	me.model.data=record.data;
        	me.displayEditor();
        }else{
     	   Ext.Msg.alert('SSP Error', 'Please select an item to edit.'); 
        }
	},
	
	onAddClick: function(button){
		var me=this;
		var model = new Ssp.model.reference.CampusEarlyAlertRouting();
		me.model.data = model.data;
		me.displayEditor();
	},    
 
    deleteConfirmation: function( button ) {
   	   var me=this;
         var grid = button.up('grid');
         var store = grid.getStore();
         var selection = grid.getView().getSelectionModel().getSelection()[0];
         var message;
         if(selection != null)
         {
             if ( selection.get('id') ) 
             {
          	   message = 'You are about to delete ' + selection.get('name') + '. Would you like to continue?';
          	      	   
                 Ext.Msg.confirm({
         		     title:'Delete?',
         		     msg: message,
         		     buttons: Ext.Msg.YESNO,
         		     fn: me.deleteRecord,
         		     scope: me
         		   });
              }else{
           	   Ext.Msg.alert('SSP Error', 'Unable to delete item.'); 
              }        	 
         }else{
        	Ext.Msg.alert('SSP Error', 'Select an item to delete.'); 
         }
       },	
  	
  	deleteRecord: function( btnId ){
  		var me=this;
  		var campusId = me.campus.get('id');
  		var grid=me.getView();
  		var store = grid.getStore();
  	    var selection = grid.getView().getSelectionModel().getSelection()[0];
       	var id = selection.get('id');
       	if (btnId=="yes")
       	{
       		me.service.destroy( campusId, id, {
       			success: me.destroySuccess,
       			failure: me.destroyFailure,
       			scope: me
       		});
         }
  	},
  	
  	destroySuccess: function( r, id, scope ){
  		var me=scope;
  		var grid=me.getView();
  		var store = grid.getStore();
  		store.remove( store.getById( id ) );
  	},
  	
  	destroyFailure: function( response, scope ){
  		var me=scope;
  	},
	
	displayEditor: function(){
		var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {});
	}
});
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
Ext.define('Ssp.controller.admin.campus.EditCampusEarlyAlertRoutingViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	formUtils: 'formRendererUtils',
    	model: 'currentCampusEarlyAlertRouting',
    	campus: 'currentCampus',
    	peopleSearchLiteStore: 'peopleSearchLiteStore',
    	service: 'campusEarlyAlertRoutingService',
        personService: 'personService'
    },
    config: {
    	containerToLoadInto: 'campusearlyalertroutingsadmin',
    	formToDisplay: 'earlyalertroutingsadmin'
    },
    control: {
    	'saveButton': {
			click: 'onSaveClick'
		},
		
		'cancelButton': {
			click: 'onCancelClick'
		},
		
		personCombo: '#personCombo'
    },
    
	init: function() {
		var me=this;
		var person;
		me.getView().getForm().reset();
		me.getView().getForm().loadRecord( me.model );
		if (me.model.get('id'))
		{
            // EA routing model has a person ID, first name, and last name but
            // our form represents this association in an incremental search
            // box. The latter needs to be backed by something resembling a
            // Ssp.model.PersonSearchLite model. Can't the latter directly
            // from our JSON. Previous impls used the search API to find the
            // person by fname+lname. SSP-564 changed this to an ID lookup
            // for reliability. In the future consider just passing a minimal
            // PersonSearchLite mapped from EA routing JSON model. Should
            // be much more efficient. But not sure about unexpected
            // compatibility problems with peopleSearchLiteStore.
			person = me.model.get('person');
            if ( person && person.id ) {
                me.getView().setLoading(true);
                me.personService.getSearchLite(person.id, {
                    success: me.routingPersonLookupSuccess,
                    failure: me.routingPersonLookupFailure,
                    scope: me
                });
            }
		}
		return me.callParent(arguments);
    },

    routingPersonLookupSuccess: function( r, scope ){
    	var me=scope;
    	me.getView().setLoading(false);
    	if (r && r.id )
    	{
    		me.peopleSearchLiteStore.loadData([r]);
    		me.getPersonCombo().setValue(me.model.get('person').id);
    	}
    },

    routingPersonLookupFailure: function( response, scope ){
    	var me=scope;
    	me.getView().setLoading(false);
    },
    
	onSaveClick: function(button) {
		var me = this;
		var record, jsonData, url, selectedPersonId;
		url = me.url;	
		if ( me.getView().getForm().isValid() )
		{
			me.getView().getForm().updateRecord();
			record = me.model;			
			jsonData = record.data;
			
			// set the selected person
			if (me.getPersonCombo().value != "")
			{
				jsonData.person={ id:me.getPersonCombo().value };
			}else{	
				jsonData.person=null;
			}
			
			me.getView().setLoading( true );
			me.service.saveCampusEarlyAlertRouting( me.campus.get('id'), jsonData, {
				success: me.saveSuccess,
				failure: me.saveFailure,
				scope: me
			});			
		}else{
			Ext.Msg.alert('SSP Error', 'Please correct the errors before saving this item.');
		}
	},

	saveSuccess: function( r, scope ) {
		var me=scope;
		me.getView().setLoading( false );
		me.displayMain();
	},

	saveFailure: function( response, scope ) {
		var me=scope;
		me.getView().setLoading( false );
	},
	
	onCancelClick: function(button){
		this.displayMain();
	},
	
	displayMain: function(){
		var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {});
	}
});
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
Ext.define('Ssp.model.Coach', {
    extend: 'Ssp.model.AbstractBase',
    fields: [{name:'firstName',type:'string'},
             {name:'middleName',type:'string'},
             {name:'lastName',type:'string'},
             {
                 name: 'fullName',
                 convert: function(value, record) {
                     return record.get('firstName') + ' '+ record.get('lastName');
                 }
             },
             {name:'departmentName',type:'string', defaultValue:'Web Systems'},
             {name: 'workPhone', type:'string'},
             {name: 'primaryEmailAddress', type:'string'},
             {name: 'officeLocation', type:'string', defaultValue:'13023S'}]
});
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
Ext.define('Ssp.model.reference.Gender', {
    extend: 'Ext.data.Model',
    fields: ['code',
             'title']
});
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
Ext.define('Ssp.model.reference.State', {
    extend: 'Ext.data.Model',
    fields: ['code',
             'title']
});
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
Ext.define('Ssp.model.Tool', {
    extend: 'Ext.data.Model',
    fields: [{name:'name',type:'string'},
             {name:'toolType',type:'string'},
             {name:'active',type:'boolean'},
             {name:'group',type:'string'}]
});
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
Ext.define('Ssp.controller.admin.AdminItemAssociationViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	formUtils: 'formRendererUtils',
    	treeUtils: 'treeRendererUtils'
    },

    config: {
        associatedItemType: "",
        parentItemType: "",
        parentIdAttribute: "",
        associatedItemIdAttribute: ""
    },
    
    control: {
    	view: {
    		itemexpand: 'onItemExpand',
    		itemcollapse: 'onItemCollapse'
    	},
    	
        treeView: {
            selector: '.treeview',
            listeners: {
                beforedrop: 'onBeforeDrop'
            }
        },
        
        'deleteAssociationButton': {
            click: 'deleteConfirmation'
        }
        	
    },
    
	constructor: function( config ) {
		var me=this;
		me.initConfig(config);
		return me.callParent(arguments);
    },
   
    onItemExpand: function( nodeInt, obj ){
    	var me=this;
    	var node = nodeInt;
    	var id = me.treeUtils.getIdFromNodeId(node.data.id);
    	me.getAssociatedItems(node,id);
    },

    onItemCollapse: function( node, obj ){
    	var me=this;
    	var tree = me.getView();
    	var records = tree.getView().getChecked();
    	if (records.length > 0)
    	{
        	Ext.Array.each(records, function(rec){
    	        rec.data.checked=false;
    	    },me);    		
    	}
    },    
    
    clear: function(){
    	this.treeUtils.clearRootCategories();
    },
    
    getParentItems: function(){
    	var me=this;
    	var treeRequest = new Ssp.model.util.TreeRequest();
    	treeRequest.set('url', me.apiProperties.getItemUrl( me.getParentItemType() ) );
    	treeRequest.set('nodeType', me.getParentItemType() );
    	treeRequest.set('isLeaf', false);
    	treeRequest.set('callbackFunc', me.onLoadComplete);
    	treeRequest.set('callbackScope', me);
    	me.treeUtils.getItems( treeRequest );
    	// me.getView().setLoading( true );
    }, 
    
    getAssociatedItems: function(node, id){
    	var me=this;
    	var parentUrl = me.apiProperties.getItemUrl( me.parentItemType );
    	var url = parentUrl + '/' + id + '/' + me.getAssociatedItemType();
    	var treeRequest = new Ssp.model.util.TreeRequest();
    	treeRequest.set('url',url);
    	treeRequest.set('nodeType', me.getAssociatedItemType);
    	treeRequest.set('isLeaf', true);
    	treeRequest.set('nodeToAppendTo', node);
    	treeRequest.set('enableCheckedItems', true);
    	treeRequest.set('callbackFunc', me.onLoadComplete);
    	treeRequest.set('callbackScope', me);
    	me.treeUtils.getItems( treeRequest );
    	// me.getView().setLoading( true );
    },

	onLoadComplete: function( scope ){
		var me=scope;
		//me.getView().setLoading( false );
	},
    
    onBeforeDrop: function(node, data, overModel, dropPosition, dropHandler, eOpts) {
    	var me=this;
    	var url, parentId, associatedItemId, node;
    	
    	// ensure the drop handler waits for the drop
    	dropHandler.wait=true;

    	// handle drop on a folder
        if (!overModel.isLeaf() && dropPosition == 'append')
        {
        	node = overModel;
        	parentId = me.treeUtils.getIdFromNodeId(node.data.id);
        	associatedItemId = data.records[0].get('id')
        	parentUrl = me.apiProperties.getItemUrl( me.getParentItemType() ) + '/' + parentId + '/' + me.getAssociatedItemType(); 	
        	url = me.apiProperties.createUrl( parentUrl );
        	me.apiProperties.makeRequest({
				url: url,
				method: 'POST',
				jsonData: '"' + associatedItemId + '"',
				successFunc: function(response, view) {
					me.getAssociatedItems(node, parentId);
				}
			});
        }

        // handle drop inside a folder
        if (dropPosition=='before' || dropPosition=='after')
        {
        	// provide a message or instruction if you'd like
        }	
        
        dropHandler.cancelDrop;
        
        return 1;
    },
 
    deleteConfirmation: function( button ) {
    	var me=this;
    	var tree = me.getView();
    	var treeUtils = me.treeUtils;
    	var records = tree.getView().getChecked();
    	if (records.length > 0)
    	{
     	   message = 'You are about to delete the selected item associations. Would you like to continue?';
     	      	   
            Ext.Msg.confirm({
    		     title:'Delete?',
    		     msg: message,
    		     buttons: Ext.Msg.YESNO,
    		     fn: me.onDeleteAssociationConfirm,
    		     scope: me
    		   });
    	}else{
    		Ext.Msg.alert('Error', 'Please select an item to delete.');
    	}
    },    
    
    onDeleteAssociationConfirm: function( btnId){
    	var me=this;
    	var tree = me.getView();
    	var treeUtils = me.treeUtils;
    	var records = tree.getView().getChecked();
    	var parentId, parentNode;
     	if (btnId=="yes")
     	{
        	parentId = treeUtils.getIdFromNodeId( records[0].data.parentId );
        	// To set the parentNode use the full parentId, while the previously set parentId attribute
        	// used the treeUtils.getIdFromNodeId() method to trim the id of the category description
        	// so the id from the database could be ascertained. The actual parentId attribute in the data
        	// from the record is separated by a character like this: 'guid_category'
        	parentNode = tree.getView().getStore().findRecord('id', records[0].data.parentId ); 

        	Ext.Array.each(records, function(rec){
    	        var associatedItemId = treeUtils.getIdFromNodeId( rec.data.id );
    	        this.deleteAssociation( parentId, associatedItemId, parentNode );
    	    },me);   		
    	}
	},
	
    deleteAssociation: function( parentId, associatedItemId, node ){
    	var me=this;
    	var url, parentId, associatedItemId;
    	if ( parentId != "" && associatedItemId != null)
    	{
        	parentUrl = me.apiProperties.getItemUrl( me.getParentItemType() ) + '/' + parentId + '/' + me.getAssociatedItemType(); 	
	    	url = me.apiProperties.createUrl( parentUrl );
	    	me.apiProperties.makeRequest({
				url: url,
				method: 'DELETE',
				jsonData: '"' + associatedItemId + '"',
				successFunc: successFunc = function(response, view) {
					me.getAssociatedItems(node, parentId);
				} 
			});
    	}
    }
});
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
Ext.define('Ssp.view.admin.AdminTreeMenu', {
	extend: 'Ext.tree.Panel',
	alias : 'widget.admintreemenu',
	id: 'AdminTreeMenu',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.AdminViewController',
    inject: {
    	store: 'adminTreeMenusStore'
    },    
    collapsible: true,
	initComponent: function() {	
		Ext.apply(this, 
				{
					store: this.store,
					singleExpand: true,
					fields: ['title','form','text'],	
				});
		
	     this.callParent(arguments);
	}	
}); 
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
Ext.define('Ssp.controller.ViewportViewController', {
    extend: 'Deft.mvc.ViewController',
	init: function() {
		return this.callParent(arguments);
    }
});
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
Ext.define('Ssp.view.Viewport',{
	extend: 'Ext.container.Container',
	requires: ['Ext.EventManager'],
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.ViewportViewController',
    inject: {
    	parentDivId: 'sspParentDivId',
    	renderFullScreen: 'renderSSPFullScreen'
    },
    layout: 'fit',
    id: 'sspView',
    alias: 'widget.sspview',

    preserveElOnDestroy: true,

    initComponent : function() {
        var me = this,
            html = document.body.parentNode,
            el;

        // Init the Main Shell for the application
        Ext.apply(this, {items: [{xtype:'mainview'}]}); 
        
        // Get the DOM disruption over with before the Viewport renders and begins a layout
        Ext.getScrollbarSize();
        
        // Clear any dimensions, we will size later on
        me.width = me.height = undefined;

        me.callParent(arguments);
        if (me.renderFullScreen==true)
        {
           Ext.fly(html).addCls(Ext.baseCSSPrefix + 'viewport');
        }
        if (me.autoScroll) {
            //delete me.autoScroll;
            //Ext.fly(html).setStyle('overflow', 'auto');
        }
        if (me.renderFullScreen==true)
        {
        	me.el = el = Ext.getBody();
        }else{
        	me.el = el = Ext.getElementById( this.parentDivId );
        }
        el.setHeight = Ext.emptyFn;
        el.setWidth = Ext.emptyFn;
        el.setSize = Ext.emptyFn;
        //el.dom.scroll = 'no';
        me.allowDomMove = false;
        me.renderTo = me.el;
     },
    
    onRender: function() {
        var me = this;
        me.callParent(arguments);

        // Important to start life as the proper size (to avoid extra layouts)
        // But after render so that the size is not stamped into the body

        if (this.renderFullScreen==true)
        {
            me.width = Ext.Element.getViewportWidth();
            me.height = Ext.Element.getViewportHeight();
        }else{
        	me.width = Ext.Element.getViewportWidth()-35; // me.el.getViewSize().width;
            me.height = me.el.getViewSize().height; //Ext.Element.getViewportHeight()-160;  
        }
    },

    afterFirstLayout: function() {
        var me = this;

        me.callParent(arguments);
        setTimeout(function() {
            Ext.EventManager.onWindowResize(me.fireResize, me);
        }, 1);
    },

    fireResize : function(width, height){
        // In IE we can get resize events that have our current size, so we ignore them
        // to avoid the useless layout...
    	var me = this;
    	// if renderFullScreen is configured then size to the viewport
    	// otherwise
    	// resize the container width and leave the height alone
    	// this will maintain the container size to the height as
    	// originally drawn on the page, but the width will be flexible
    	if (this.renderFullScreen==true)
    	{
    		newWidth = Ext.Element.getViewportWidth();
    		newHeight = Ext.Element.getViewportHeight();
    	}else{
    		newWidth = Ext.Element.getViewportWidth()-35; // me.width+(width-me.el.getWidth());
    		newHeight = me.el.getViewSize().height; //Ext.Element.getViewportHeight()-160; // me.height;
    	}
    		
    	if (width != me.width || height != me.height) {
	        me.setSize( newWidth, newHeight);
    	}
    }
});
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
Ext.define('Ssp.view.Main', {
	extend: 'Ext.panel.Panel',
    alias: 'widget.mainview',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    inject: {
    	authenticatedPerson: 'authenticatedPerson'
    },
    controller: 'Ssp.controller.MainViewController',
    initComponent: function(){
    	var me=this;
    	Ext.apply(me,
		    			{
		    	    layout: {
		    	    	type: 'hbox',
		    	    	align: 'stretch'
		    	    },

		    	    dockedItems: {
		    	        xtype: 'toolbar',
		    	        items: [{
		    			            xtype: 'button',
		    			            text: 'Students',
		    			            hidden: !me.authenticatedPerson.hasAccess('STUDENTS_NAVIGATION_BUTTON'),
		    			            itemId: 'studentViewNav',
		    			            action: 'displayStudentRecord'
		    			        }, {
		    			            xtype: 'button',
		    			            text: 'Admin',
		    			            hidden: !me.authenticatedPerson.hasAccess('ADMIN_NAVIGATION_BUTTON'),
		    			            itemId: 'adminViewNav',
		    			            action: 'displayAdmin'
		    			        },{
			       		        	xtype: 'tbspacer',
			       		        	flex: 1
			       		        },{
	    		    	    	  id: 'report',
	    		    	    	  xtype: 'sspreport'
	    		    	    	}]
		    	    }    		
    			});
    	
    	return me.callParent(arguments);
    }
});
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
Ext.define('Ssp.view.Search', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.search',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.SearchViewController',
    inject: {
        authenticatedPerson: 'authenticatedPerson',
        apiProperties: 'apiProperties',
        columnRendererUtils: 'columnRendererUtils',
        programStatusesStore: 'programStatusesStore',
        sspConfig: 'sspConfig'
    },
    initComponent: function(){
        var me = this;
        Ext.applyIf(me, {
            submitEmptyText: false,
            title: 'Students',
            collapsible: true,
            collapseDirection: 'left',
            width: '100%',
            height: '100%',
            columns: [{
                text: 'Name',
                dataIndex: 'lastName',
                renderer: me.columnRendererUtils.renderSearchStudentName,
                flex: 50
            }],
            
            dockedItems: [{
                xtype: 'pagingtoolbar',
                itemId: 'searchGridPager',
                dock: 'bottom',
                displayInfo: true,
                pageSize: me.apiProperties.getPagingSize(),
                listeners: {
                    afterrender: function(){
                        var a = Ext.query("button[data-qtip=Refresh]");
                        for (var x = 0; x < a.length; x++) {
                            a[x].style.display = "none";
                        }
                    }
                }
            }, {
                xtype: 'toolbar',
                dock: 'top',
                itemId: 'searchBar',
                hidden: !me.authenticatedPerson.hasAccess('STUDENT_SEARCH'),
                items: [{
                    xtype: 'textfield',
                    itemId: 'searchText',
                    enableKeyEvents: true,
                    emptyText: 'Name or ' + me.sspConfig.get('studentIdAlias'),
                    width: 200
                }, {
                    xtype: 'button',
                    tooltip: 'Find a Student',
                    itemId: 'searchButton',
                    width: 32,
                    height: 32,
                    cls: 'searchIcon'
                }, {
                    xtype: 'tbspacer',
                    width: 5
                }, {
                    xtype: 'checkboxfield',
                    boxLabel: 'My Caseload',
                    itemId: 'searchCaseloadCheck',
                    name: 'searchInCaseload',
                    hidden: !me.authenticatedPerson.hasAccess('CASELOAD_SEARCH'),
                    inputValue: false
                }, {
                    xtype: 'tbspacer',
                    flex: 1
                }, {
                    tooltip: 'Display Caseload Filters',
                    text: '',
                    width: 25,
                    height: 25,
                    hidden: false,
                    cls: 'displayCaseloadIcon',
                    xtype: 'button',
                    itemId: 'displayCaseloadBarButton'
                }]
            }, {
                xtype: 'toolbar',
                dock: 'top',
                itemId: 'caseloadBar',
                hidden: !me.authenticatedPerson.hasAccess('CASELOAD_FILTERS'),
                items: [{
                    xtype: 'combobox',
                    itemId: 'caseloadStatusCombo',
                    name: 'programStatusId',
                    fieldLabel: '',
                    emptyText: 'Select One',
                    store: me.programStatusesStore,
                    valueField: 'id',
                    displayField: 'name',
                    mode: 'local',
                    typeAhead: false,
                    editable: false,
                    queryMode: 'local',
                    allowBlank: true,
                    forceSelection: false,
                    width: 200,
                    labelWidth: 125
                }, {
                    xtype: 'button',
                    tooltip: 'Retrieve My Caseload',
                    itemId: 'retrieveCaseloadButton',
                    width: 32,
                    height: 32,
                    cls: 'retrieveCaseloadIcon'
                }, {
                    xtype: 'tbspacer',
                    flex: 1
                }, {
                    tooltip: 'Display Search Filters',
                    text: '',
                    width: 25,
                    height: 25,
                    hidden: false,
                    cls: 'displaySearchIcon',
                    xtype: 'button',
                    itemId: 'displaySearchBarButton'
                }]
            
            }, {
                xtype: 'toolbar',
                dock: 'top',
                items: [{
                    tooltip: 'Add Student',
                    text: '',
                    width: 25,
                    height: 25,
                    hidden: !me.authenticatedPerson.hasAccess('ADD_STUDENT_BUTTON'),
                    cls: 'addPersonIcon',
                    xtype: 'button',
                    itemId: 'addPersonButton'
                }, {
                    tooltip: 'Edit Student',
                    text: '',
                    width: 25,
                    height: 25,
                    hidden: !me.authenticatedPerson.hasAccess('EDIT_STUDENT_BUTTON'),
                    cls: 'editPersonIcon',
                    xtype: 'button',
                    itemId: 'editPersonButton'
                }, {
                    tooltip: 'Delete Student',
                    text: '',
                    width: 25,
                    height: 25,
                    hidden: !me.authenticatedPerson.hasAccess('DELETE_STUDENT_BUTTON'),
                    cls: 'deletePersonIcon',
                    xtype: 'button',
                    itemId: 'deletePersonButton'
                }, {
                    xtype: 'tbspacer',
                    flex: 1
                }, {
                    xtype: 'label',
                    text: 'Change Status:',
                    style: 'font-weight:bold;'
                }, {
                    tooltip: 'Set Student to Active status',
                    text: '',
                    width: 25,
                    height: 25,
                    hidden: !me.authenticatedPerson.hasAccess('SET_ACTIVE_STATUS_BUTTON'),
                    cls: 'setActiveStatusIcon',
                    xtype: 'button',
                    action: 'active',
                    itemId: 'setActiveStatusButton'
                }, {
                    tooltip: 'Set Student to Transitioned status',
                    text: '',
                    width: 25,
                    height: 25,
                    hidden: true, // Temp fix for SSP-434: !me.authenticatedPerson.hasAccess('SET_TRANSITION_STATUS_BUTTON')
                    cls: 'setTransitionStatusIcon',
                    xtype: 'button',
                    action: 'transition',
                    itemId: 'setTransitionStatusButton'
                }, {
                    tooltip: 'Set Student to Non-Participating status',
                    text: '',
                    width: 25,
                    height: 25,
                    hidden: !me.authenticatedPerson.hasAccess('SET_NON_PARTICIPATING_STATUS_BUTTON'),
                    cls: 'setNonParticipatingStatusIcon',
                    xtype: 'button',
                    action: 'non-participating',
                    itemId: 'setNonParticipatingStatusButton'
                }, {
                    tooltip: 'Set Student to No-Show status',
                    text: '',
                    width: 25,
                    height: 25,
                    hidden: !me.authenticatedPerson.hasAccess('SET_NO_SHOW_STATUS_BUTTON'),
                    cls: 'setNoShowStatusIcon',
                    xtype: 'button',
                    action: 'no-show',
                    itemId: 'setNoShowStatusButton'
                }]
            }]
        });
        
        return me.callParent(arguments);
    }
});

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
Ext.define('Ssp.view.StudentRecord', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.studentrecord',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.StudentRecordViewController',
    width: '100%',
    height: '100%',
    initComponent: function(){
        var me = this;
        Ext.apply(this, {
            title: '',
            collapsible: true,
            collapseDirection: 'right',
            cls: 'studentpanel',
            layout: {
                type: 'hbox',
                align: 'stretch'
            },
            tools: [
			{
                xtype: 'tbspacer',
                flex: .05
            },
			{
                
                text: '',
                width: 200,
                height: 20,
                xtype: 'button',
                itemId: 'emailCoachButton',
				cls: "makeTransparent"
            },
			{
				xtype: 'tbspacer',
                flex: .05
			},
			{
                tooltip: 'Coaching History',
                text: '<u>Coaching History</u>',
                width: 110,
                height: 20,
                //hidden: !me.authenticatedPerson.hasAccess('PRINT_HISTORY_BUTTON'),
                //cls: 'studentHistoryIcon',
                xtype: 'button',
				cls: "makeTransparent",
                itemId: 'viewCoachingHistoryButton'
            }, 
			{
                xtype: 'tbspacer',
                flex: .05
            },{
                xtype: 'button',
                itemId: 'studentRecordEditButton',
                text: '<u>Edit</u>',
                tooltip: 'Edit Student',
                height: 20,
                width: 50,
				cls: "makeTransparent"
                //hidden: !me.authenticatedPerson.hasAccess('EDIT_STUDENT_BUTTON')
            }],
            items: [{
                xtype: 'toolsmenu',
                flex: .60
            }, {
                xtype: 'tools',
                flex: 4.40
            }]
        });
        return this.callParent(arguments);
    }
});

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
Ext.define('Ssp.view.ProgramStatusChangeReasonWindow', {
	extend: 'Ext.window.Window',
	alias : 'widget.programstatuschangereasonwindow',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.ProgramStatusChangeReasonWindowViewController',
    inject: {
    	personLite: 'personLite',
    	store: 'programStatusChangeReasonsStore'
    },
	width: '100%',
	height: '100%',
	title: 'Please provide a reason the student will no longer be participating:',
    initComponent: function(){
    	var me=this;
    	Ext.applyIf(me,
    			   {
				    modal: true, 
		    		layout: 'anchor',
    				items: [{
                    	xtype: 'displayfield',
                    	fieldLabel: 'Student',
                    	value: me.personLite.get('displayFullName'),
                    	anchor: '95%'
                    },{
    			        xtype: 'combobox',
    			        itemId: 'programStatusChangeReasonCombo',
    			        name: 'programStatusChangeReasonId',
    			        fieldLabel: 'Reason',
    			        emptyText: 'Select One',
    			        store: me.store,
    			        valueField: 'id',
    			        displayField: 'name',
    			        mode: 'local',
    			        typeAhead: true,
    			        queryMode: 'local',
    			        allowBlank: false,
    			        forceSelection: true,
    			        anchor: '95%'
    		        }],
    	            dockedItems: [{
    		               xtype: 'toolbar',
    		               dock: 'bottom',
    		               items: [{
		       		                   text: 'Save',
		       		                   xtype: 'button',
		       		                   action: 'save',
		       		                   itemId: 'saveButton'
		       		               }, '-', {
		       		                   text: 'Cancel',
		       		                   xtype: 'button',
		       		                   action: 'cancel',
		       		                   itemId: 'cancelButton'
		       		               }]
    		           }]
		    	    });
    	
    	return me.callParent(arguments);
    }
});
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
Ext.define('Ssp.view.person.CaseloadAssignment', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.caseloadassignment',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.person.CaseloadAssignmentViewController',
    inject: {
        model: 'currentPerson',
        sspConfig: 'sspConfig'
    },
    width: '100%',
    height: '100%',
    initComponent: function(){
        var me = this;
        Ext.apply(me, {
            title: "Caseload Assignment",
            autoScroll: true,
            defaults: {
                bodyStyle: 'padding:5px'
            },
            layout: {
                type: 'accordion',
                align: 'stretch',
                titleCollapse: true,
                animate: true,
                activeOnTop: true
            },
            dockedItems: [{
                xtype: 'toolbar',
                dock: 'top',
                items: [{
                    xtype: 'container',
                    layout: 'vbox',
                    items: [{
                        xtype: 'label',
                        text: 'Fill out the following forms with assigned coach details and appointment information'
                    }, {
                        xtype: 'tbspacer',
                        height: '10'
                    }, {
                        xtype: 'container',
                        layout: 'hbox',
                        items: [{
                            xtype: 'button',
                            itemId: 'saveButton',
                            text: 'Save'
                        }, {
                            xtype: 'tbspacer',
                            height: '20'
                        }, {
                            xtype: 'button',
                            itemId: 'cancelButton',
                            text: 'Cancel',
                        }]
                    }]
                }]
            }, {
                dock: 'bottom',
                xtype: 'toolbar',
                items: [                /*,{
                 xtype: 'checkbox',
                 boxLabel: 'Send Student Intake Request',
                 name: 'sendStudentIntakeRequest'
                 },{
                 xtype: 'tbspacer',
                 width: 25
                 },{
                 xtype: 'displayfield',
                 fieldLabel: 'Last Request Date',
                 name: 'lastStudentIntakeRequestDate',
                 value: ((me.model.getFormattedStudentIntakeRequestDate().length > 0) ? me.person.getFormattedStudentIntakeRequestDate() : 'No requests have been sent')
                 }*/
                , {
                    xtype: 'tbspacer',
                    flex: 1
                }, {
                    xtype: 'checkboxfield',
                    boxLabel: "Reset the student to Active status on the assigned " + me.sspConfig.get('coachFieldLabel') + "'s Caseload",
                    itemId: 'resetActiveStatusCheck',
                    name: 'setActiveStatus',
                    //hidden: (( me.model.get('currentProgramStatusName').toLowerCase() == 'active' && me.model.get('id') != "")? true : false),
                    inputValue: false
                }, {
                    xtype: 'button',
                    itemId: 'printButton',
                    tooltip: 'Print Appointment Form',
                    hidden: true,
                    width: 30,
                    height: 30,
                    cls: 'printIcon'
                }, {
                    xtype: 'button',
                    itemId: 'emailButton',
                    hidden: true,
                    tooltip: 'Email Appointment Form',
                    width: 30,
                    height: 30,
                    cls: 'emailIcon'
                }]
            }],
            
            items: []
        });
        
        return me.callParent(arguments);
    }
    
});

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
Ext.define('Ssp.view.person.EditPerson', {
	extend: 'Ext.form.Panel',
	alias: 'widget.editperson',
	mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.person.EditPersonViewController',
	initComponent: function() {	
		Ext.apply(this, 
				{
					border: 0,	    
				    fieldDefaults: {
				        msgTarget: 'side',
				        labelAlign: 'right',
				        labelWidth: 100
				    },
					items: [{
			            xtype: 'fieldset',
			            border: 0,
			            title: '',
			            defaultType: 'textfield',

			       items: [{
			        fieldLabel: 'First Name',
			        name: 'firstName',
			        itemId: 'firstName',
			        id: 'editPersonFirstName',
			        maxLength: 50,
			        allowBlank:false,
			        width: 350
			    },{
			        fieldLabel: 'Middle Name',
			        name: 'middleName',
			        itemId: 'middleName',
			        id: 'editPersonMiddleName',
			        maxLength: 50,
			        allowBlank:true,
			        width: 350
			    },{
			        fieldLabel: 'Last Name',
			        name: 'lastName',
			        itemId: 'lastName',
			        id: 'editPersonLastName',
			        maxLength: 50,
			        allowBlank:false,
			        width: 350
			    },{
			        fieldLabel: 'Student ID',
			        name: 'schoolId',
			        minLength: 7,
			        maxLength: 7,
			        itemId: 'studentId',
			        allowBlank:false,
			        width: 350
			    },{
			    	xtype: 'button',
			    	tooltip: 'Load record from external system',
			    	text: 'Retrieve from External',
			    	itemId: 'retrieveFromExternalButton'
			    },{
			        fieldLabel: 'Username',
			        name: 'username',
			        minLength: 4,
			        maxLength: 100,
			        itemId: 'username',
			        allowBlank:false,
			        width: 350
			    },{
			        fieldLabel: 'Home Phone',
			        name: 'homePhone',
			        emptyText: 'xxx-xxx-xxxx',
			        maxLength: 25,
			        allowBlank:true,
			        itemId: 'homePhone',
			        width: 350
			    },{
			        fieldLabel: 'Work Phone',
			        name: 'workPhone',
			        emptyText: 'xxx-xxx-xxxx',
			        maxLength: 25,
			        allowBlank:true,
			        itemId: 'workPhone',
			        width: 350
			    },{
			        fieldLabel: 'School Email',
			        name: 'primaryEmailAddress',
			        vtype:'email',
			        maxLength: 100,
			        allowBlank:true,
			        itemId: 'primaryEmailAddress',
			        width: 350
			    },{
			        fieldLabel: 'Alternate Email',
			        name: 'secondaryEmailAddress',
			        vtype:'email',
			        maxLength: 100,
			        allowBlank:true,
			        itemId: 'secondaryEmailAddress',
			        width: 350
			    }]
			}]
		});
		
		return this.callParent(arguments);
	}
});
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
Ext.define('Ssp.view.person.Coach', {
	extend: 'Ext.form.Panel',
	alias: 'widget.personcoach',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.person.CoachViewController',
    inject: {
    	coachesStore: 'coachesStore',
    	sspConfig: 'sspConfig',
    	studentTypesStore: 'studentTypesStore'
    },
	initComponent: function() {	
		var me=this;
		Ext.apply(me, 
				{
			    border: 0,
			    padding: 0,
			    fieldDefaults: {
			        msgTarget: 'side',
			        labelAlign: 'right',
			        labelWidth: 200
			    },	
				items: [{
			            xtype: 'fieldset',
			            border: 0,
			            padding: 0,
			            title: '',
			            defaultType: 'textfield',
			            defaults: {
			                anchor: '50%'
			            },
			       items: [{
				        xtype: 'combobox',
				        name: 'coachId',
				        itemId: 'coachCombo',
				        fieldLabel: me.sspConfig.get('coachFieldLabel'),
				        emptyText: 'Select One',
				        store: me.coachesStore,
				        valueField: 'id',
				        displayField: 'fullName',
				        mode: 'local',
				        typeAhead: true,
				        queryMode: 'local',
				        allowBlank: false
					},{
				    	xtype: 'displayfield',
				        fieldLabel: 'Office',
				        itemId: 'officeField',
				        name: 'coachOffice'
				    },{
				    	xtype: 'displayfield',
				        fieldLabel: 'Phone',
				        itemId: 'phoneField',
				        name: 'coachPhone'
				    },{
				    	xtype: 'displayfield',
				        fieldLabel: 'Email',
				        itemId: 'emailAddressField',
				        name: 'coachEmailAddress'
				    },{
				    	xtype: 'displayfield',
				        fieldLabel: 'Department',
				        itemId: 'departmentField',
				        name: 'coachDepartment'
				    },{
				        xtype: 'combobox',
				        name: 'studentTypeId',
				        itemId: 'studentTypeCombo',
				        id: 'studentTypeCombo',
				        fieldLabel: 'Student Type',
				        emptyText: 'Select One',
				        store: me.studentTypesStore,
				        valueField: 'id',
				        displayField: 'name',
				        mode: 'local',
				        typeAhead: false,
				        editable: false,
				        queryMode: 'local',
				        allowBlank: false
					}]
			    }]
			});
		
		return me.callParent(arguments);
	}
});
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
Ext.define('Ssp.view.person.Appointment', {
	extend: 'Ext.form.Panel',
	alias: 'widget.personappointment',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.person.AppointmentViewController',
	initComponent: function() {	
		var me=this;
		Ext.apply(me, 
				{
			    fieldDefaults: {
			        msgTarget: 'side',
			        labelAlign: 'right',
			        labelWidth: 200
			    },	
			    border: 0,
			    padding: 0,
				items: [{
			            xtype: 'fieldset',
			            border: 0,
			            padding: 0,
			            title: '',
			            defaultType: 'textfield',
			            defaults: {
			            	anchor: '50%'
			            },
			       items: [{
				    	xtype: 'datefield',
				    	fieldLabel: 'Appointment Date',
				    	itemId: 'appointmentDateField',
				    	altFormats: 'm/d/Y|m-d-Y',
				    	invalidText: '{0} is not a valid date - it must be in the format: 06/21/2012 or 06-21-2012',
				    	name: 'appointmentDate',
				        allowBlank: false
				    },{
				        xtype: 'timefield',
				        name: 'startTime',
				        itemId: 'startTimeField',
				        fieldLabel: 'Start Time',
				        increment: 30,
				        typeAhead: false,
				        allowBlank: false
				    },{
				        xtype: 'timefield',
				        name: 'endTime',
				        itemId: 'endTimeField',
				        fieldLabel: 'End Time',
				        typeAhead: false,
				        allowBlank: false,
				        increment: 30
				    },{
			            xtype: 'checkboxfield',
			            fieldLabel: 'Send Student Intake Request',
				        name: 'studentIntakeRequested',
			            itemId: 'studentIntakeRequestedField',
			            inputValue: true
				    },{
			            xtype: 'textfield',
			            fieldLabel: 'Also Send Student Intake Request To Email',
				        name: 'intakeEmail',
			            itemId: 'intakeEmailField',
			            hidden: true
				    }
				    
				    ]
			    }]
			});
		
		return me.callParent(arguments);
	}
});
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
Ext.define('Ssp.view.person.SpecialServiceGroups', {
	extend: 'Ext.form.Panel',
	alias: 'widget.personspecialservicegroups',
	mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.person.SpecialServiceGroupsViewController',
	width: '100%',
    height: '100%',
    autoScroll: true,
	initComponent: function() {	
		var me=this;
		Ext.apply(me, 
				{
				    bodyPadding: 5,
				    layout: 'anchor'
				});
		
		return me.callParent(arguments);
	}
});
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
Ext.define('Ssp.view.person.ReferralSources', {
	extend: 'Ext.form.Panel',
	alias: 'widget.personreferralsources',
	mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.person.ReferralSourcesViewController',
	width: '100%',
    height: '100%',
    autoScroll: true,
	initComponent: function() {	
		var me=this;
		Ext.apply(this, 
				{
				    bodyPadding: 5,
				    layout: 'anchor'
				});
		
		return me.callParent(arguments);
	}
});
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
Ext.define('Ssp.view.person.ServiceReasons', {
	extend: 'Ext.form.Panel',
	alias: 'widget.personservicereasons',
	id: 'personservicereasons',
	mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.person.ServiceReasonsViewController',
    width: '100%',
    height: '100%',
    autoScroll: true,
	initComponent: function() {	
		Ext.apply(this, 
				{
				    bodyPadding: 0,
				    border: 0,
				    layout: 'anchor',
				    defaults: {
				        anchor: '100%'
				    },
				    defaultType: 'checkbox'
				});
		
		return this.callParent(arguments);
	}
});
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
Ext.define('Ssp.view.person.AnticipatedStartDate', {
	extend: 'Ext.form.Panel',
	alias: 'widget.personanticipatedstartdate',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.person.AnticipatedStartDateViewController',
    inject: {
    	anticipatedStartTermsStore: 'anticipatedStartTermsStore',
    },
	initComponent: function() {	
		Ext.apply(this, 
				{
		        fieldDefaults: {
		        msgTarget: 'side',
		        labelAlign: 'right',
		        labelWidth: 125
		    },
			border: 0,
			items: [{
		        xtype: 'checkboxgroup',
		        fieldLabel: 'Ability to Benefit',
		        columns: 1,
		        items: [
		            {boxLabel: '', name: 'abilityToBenefit'}
		        ]
		    },{
		        xtype: 'combobox',
		        name: 'anticipatedStartTerm',
		        fieldLabel: 'Anticipated Start Term',
		        emptyText: 'Select One',
		        store: this.anticipatedStartTermsStore,
		        valueField: 'name',
		        displayField: 'name',
		        mode: 'local',
		        typeAhead: true,
		        queryMode: 'local',
		        allowBlank: true
			},{
		        xtype: 'textfield',
		        name: 'anticipatedStartYear',
		        fieldLabel: 'Anticipated Start Year',
                minLength: 4,
                maxLength: 4,
                width: 200,
                emptyText: 'xxxx',
                maskRe: /\d/,
                regex: /^\d{4}$/,
                regexText: 'Must be a four-digit number',
		        allowBlank: true
			}]
		});
		
		return this.callParent(arguments);
	}
});
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
Ext.define('Ssp.view.ToolsMenu', {
	extend: 'Ext.grid.Panel',
	alias : 'widget.toolsmenu',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.ToolsViewController',
    inject: {
    	appEventsController: 'appEventsController',
    	columnRendererUtils: 'columnRendererUtils',
        store: 'toolsStore'
    },
    initComponent: function(){
    	Ext.apply(this,
    			   {
		    		width: '100%',
		    		height: '100%',
    				store: this.store,

    	    		features: [{
		    	        id: 'group',
		    	        ftype: 'grouping',
		    	        groupHeaderTpl: '{name}',
		    	        hideGroupedHeader: false,
		    	        enableGroupingMenu: false
		    	    }],
		    	    
    				columns:[{
    				           header: "Tools", 
    				           dataIndex: "name",
    				           sortable: false,
    				           menuDisabled: true,
    				           flex:1 },{
	    			    	        xtype:'actioncolumn',
	    			    	        width:18,
	    			    	        header: '',
	    			    	        sortable: false,
	    			    	        hidden: true,
	    			    	        items: [{
	    			    	            tooltip: 'Add Tool',
	    			    	            // icon: Ssp.util.Constants.ADD_TOOL_ICON_PATH,
	    			    	            getClass: this.columnRendererUtils.renderAddToolIcon,
	    			    	            handler: function(grid, rowIndex, colIndex) {
	    			    	            	var rec = grid.getStore().getAt(rowIndex);
	    			    	            	var panel = grid.up('panel');
	    			    	                //panel.toolId.data=rec.data.toolId;
	    			    	                panel.appEventsController.getApplication().fireEvent('addTool');
	    			    	                Ext.Msg.alert('Attention','This feature is not yet active');
	    			    	            },
	    			    	            scope: this
	    			    	        }]
	    		                }]
		    	    });
    	
    	return this.callParent(arguments);
    }
});
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
Ext.define('Ssp.view.tools.profile.Profile', {
	extend: 'Ext.form.Panel',
	alias : 'widget.profile',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.profile.ProfileToolViewController',
    
    width: '100%',
	height: '100%',
    initComponent: function() {	
		var me=this;
    	Ext.apply(me, 
				{
		    	    layout: 'fit',
		            title: 'Profile',
		            padding: 0,
		            border: 0,
					preventHeader: true,
					items: [
						Ext.createWidget('tabpanel', {
						    width: '100%',
						    height: '100%',
						    activeTab: 0,
						    itemId: 'profileTabs',
						    items: [{ 
						    	      title: 'Dashboard',
						    	      autoScroll: true,
						    		  items: [{xtype: 'profileperson'}]
						    		},{ 
						    		  title: 'Placement',
						    		  autoScroll: true,
									  items: [{xtype: 'placement'}]
						    		  //items: [{xtype: 'profilespecialservicegroups'}]
									  
						    		},{ 
						    		  title: 'Transcript',
						    		  autoScroll: true,
									  items: [{xtype: 'transcript'}]
						    		  //items: [{xtype: 'profilereferralsources'}]
									  
						    		},{ 
						    		  title: 'Contact',
						    		  autoScroll: true,
						    		  items: [{xtype: 'profilecontact'}]
                                      //items: [{xtype: 'profileservicereasons'}]
									  
						    		},{ 
						    		  title: 'Coach',
						    		  autoScroll: true,
						    		  items: [{xtype: 'profilecoach'}]
							    	}]
						})
				    ]
				});	     
    	
    	return me.callParent(arguments);
	}
});
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
Ext.define('Ssp.view.tools.profile.Person', {
    extend: 'Ext.form.Panel',
    alias: 'widget.profileperson',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.profile.ProfilePersonViewController',
    inject: {
        columnRendererUtils: 'columnRendererUtils',
        sspConfig: 'sspConfig'
    },
    width: '100%',
    height: '100%',
    initComponent: function(){
        var me = this;
        Ext.apply(me, {
            border: 0,
            bodyPadding: 0,
            layout: 'anchor',
            defaults: {
                anchor: '100%'
            },
            items: [{
                xtype: 'fieldcontainer',
                fieldLabel: '',
                layout: 'hbox',
                margin: '5 0 0 0',
                defaultType: 'displayfield',
                fieldDefaults: {
                    msgTarget: 'side'
                    //labelAlign: 'right',
                    //labelWidth: 80
                },
                items: [{
                    xtype: 'fieldset',
                    border: 0,
                    title: '',
                    defaultType: 'displayfield',
                    defaults: {
                        anchor: '100%'
                    },
                    width: 150,
                    items: [
                     {
                    	xtype: 'image',
                        fieldLabel: '',
                        src: Ssp.util.Constants.DEFAULT_NO_STUDENT_PHOTO_URL,
                        itemId: 'studentPhoto',
                        width:150,
                        height:150
                    }]},{
                    xtype: 'fieldset',
                    border: 0,
                    title: '',
                    defaultType: 'displayfield',
                    defaults: {
                        anchor: '100%'
                    },
                    flex: .40,
                    items: [{
                        fieldLabel: '',
                        name: 'name',
                        itemId: 'studentName'
                    }, {
                        fieldLabel: 'ID',
                        itemId: 'studentId',
                        name: 'schoolId',
                        labelWidth: 70
                    }, {
                        fieldLabel: 'DOB',
                        name: 'birthDate',
                        itemId: 'birthDate',
                        labelWidth: 30
                    }, {
                        fieldLabel: 'Phone',
                        name: 'homePhone',
                        labelWidth: 40
                    }, {
                        fieldLabel: '',
                        name: 'primaryEmailAddress'
                    }, {
                        fieldLabel: 'Student Type',
                        name: 'studentType',
                        itemId: 'studentType',
                        labelWidth: 80
                    }, {
                        fieldLabel: 'SSP Status',
                        name: 'programStatus',
                        itemId: 'programStatus',
                        labelWidth: 70
                    }, {
                        fieldLabel: 'Academic Program',
                        name: 'academicPrograms',
                        itemId: 'academicPrograms',
                        labelWidth: 120
                    }                   
                    ]
                
                }, {
                    xtype: 'fieldset',
                    border: 0,
                    title: '',
                    defaultType: 'displayfield',
                    defaults: {
                        anchor: '100%'
                    },
                    flex: .30,
                    items: [{
                        fieldLabel: 'GPA',
                        name: 'cumGPA',
                        itemId: 'cumGPA'
                    }, {
                        fieldLabel: 'Hrs Earned',
                        name: 'hrsEarned',
                        itemId: 'hrsEarned'
                    }, {
                        fieldLabel: 'Hrs Attempted',
                        name: 'hrsAttempted',
                        itemId: 'hrsAttempted'
                    }, {
                        fieldLabel: 'Reg',
                        name: 'registeredTerms'
                    }, {
                        fieldLabel: 'Payment',
                        name: 'paymentStatus'
                    }, {
                        fieldLabel: 'Early Alerts (Open/Total)',
                        itemId: 'earlyAlert',
                        name: 'earlyAlert'
                    }]
                
                }, {
                    xtype: 'fieldset',
                    border: 0,
                    title: '',
                    defaultType: 'displayfield',
                    defaults: {
                        anchor: '100%'
                    },
                    padding: 0,
                    flex: .30,
                    items: [{
                        xtype: 'profileservicereasons'
                    }, 
					{
                            xtype: 'tbspacer',
                            height: '20'
                        },
					{
                        xtype: 'profilespecialservicegroups'
                    }]
                }]
            }]
        });
        
        return me.callParent(arguments);
    }
    
});

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
Ext.define('Ssp.view.tools.profile.ServicesProvided', {
	extend: 'Ext.grid.Panel',
	alias : 'widget.profileservicesprovided',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.profile.ServicesProvidedViewController',	
	width: '100%',
	height: '100%',
	initComponent: function() {	
		var me=this;
		Ext.apply(me, 
				{        
			        autoScroll: true,
    		        columns: [
    		                { header: 'Provided By',  
    		                  dataIndex: 'createdBy',
    		                  flex: .50,
    		                },{ header: 'Date Provided',  
    		                  dataIndex: 'createdDate',
    		                  flex: .50,
    		                }],
				});
		
		return me.callParent(arguments);
	}
});
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
Ext.define('Ssp.view.tools.actionplan.ActionPlan', {
	extend: 'Ext.container.Container',
	alias : 'widget.actionplan',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.actionplan.ActionPlanToolViewController',
    width: '100%',
	height: '100%',   
	layout: 'fit',
	initComponent: function() {	
		Ext.apply(this,{items: [{xtype: 'displayactionplan'}]});

		return this.callParent(arguments);
	}
		
});
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
Ext.define('Ssp.view.tools.actionplan.Tasks', {
	extend: 'Ext.grid.Panel',
	alias: 'widget.tasks',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.actionplan.TasksViewController',
    inject: {
    	appEventsController: 'appEventsController',
    	authenticatedPerson: 'authenticatedPerson',
    	columnRendererUtils: 'columnRendererUtils',
    	model: 'currentTask',
        store: 'tasksStore',
    },
    layout: 'auto',
	width: '100%',
    height: '100%',
    initComponent: function(){
    	var me=this;
    	var sm = Ext.create('Ext.selection.CheckboxModel');
    	
    	Ext.apply(me,
    			{
    		        scroll: 'vertical',
    	    		store: me.store,    		
    	    		selModel: sm,
    	    		features: [{
		    	        id: 'group',
		    	        ftype: 'grouping',
		    	        groupHeaderTpl: '{name}',
		    	        depthToIndent: 0,
		    	        hideGroupedHeader: false,
		    	        enableGroupingMenu: false
		    	    }],
		
		    	    columns: [{
		    	        xtype:'actioncolumn',
		    	        width:65,
		    	        header: 'Action',
		    	        items: [{
		    	            icon: Ssp.util.Constants.GRID_ITEM_EDIT_ICON_PATH,
		    	            tooltip: 'Edit Task',
		    	            handler: function(grid, rowIndex, colIndex) {
		    	            	var rec = grid.getStore().getAt(rowIndex);
		    	            	var panel = grid.up('panel');
		    	                panel.model.data=rec.data;
		    	            	panel.appEventsController.getApplication().fireEvent('editTask');
		    	            },
		    	            getClass: function(value, metadata, record)
                            {
		    	            	// completed items cannot be edited 
		    	            	// hide if completed or if user does not have permission to edit
		    	            	var cls = 'x-hide-display';
		    	            	if ( me.authenticatedPerson.hasAccess('EDIT_TASK_BUTTON') && record.get('completedDate') == null)
		    	            	{
		    	            		cls = Ssp.util.Constants.GRID_ITEM_CLOSE_ICON_PATH;
		    	            	}
		    	            	
		    	            	return cls;                            
		    	            },
		    	            scope: me
		    	        },{
		    	            icon: Ssp.util.Constants.GRID_ITEM_CLOSE_ICON_PATH,
		    	            tooltip: 'Close Task',
		    	            handler: function(grid, rowIndex, colIndex) {
		    	            	var rec = grid.getStore().getAt(rowIndex);
		    	            	var panel = grid.up('panel');
		    	                panel.model.data=rec.data;
		    	            	panel.appEventsController.getApplication().fireEvent('closeTask');
		    	            },
		    	            getClass: function(value, metadata, record)
                            {
		    	            	// completed items cannot be closed 
		    	            	// hide if completed or if user does not have permission to edit
		    	            	var cls = 'x-hide-display';
		    	            	if ( me.authenticatedPerson.hasAccess('CLOSE_TASK_BUTTON') && record.get('completedDate') == null)
		    	            	{
		    	            		cls = Ssp.util.Constants.GRID_ITEM_CLOSE_ICON_PATH;
		    	            	}
		    	            	
		    	            	return cls;
		    	            },
		    	            scope: me
		    	        },{
		    	            icon: Ssp.util.Constants.GRID_ITEM_DELETE_ICON_PATH,
		    	            tooltip: 'Delete Task',
		    	            handler: function(grid, rowIndex, colIndex) {
		    	            	var rec = grid.getStore().getAt(rowIndex);
		    	            	var panel = grid.up('panel');
		    	                panel.model.data=rec.data;
		    	            	panel.appEventsController.getApplication().fireEvent('deleteTask');
		    	            },
		    	            getClass: function(value, metadata, record)
                            {
		    	            	// completed items cannot be deleted 
		    	            	// hide if completed or if user does not have permission to delete
		    	            	var cls = 'x-hide-display';
		    	            	if ( me.authenticatedPerson.hasAccess('DELETE_TASK_BUTTON') && record.get('completedDate') == null)
		    	            	{
		    	            		cls = Ssp.util.Constants.GRID_ITEM_CLOSE_ICON_PATH;
		    	            	}
		    	            	
		    	            	return cls;
                            },
		    	            scope: me
		    	        }]
		    	    },{
		    	        text: 'Description',
		    	        flex: 1,
		    	        tdCls: 'task',
		    	        sortable: true,
		    	        dataIndex: 'name',
		    	        renderer: me.columnRendererUtils.renderTaskName
		    	    },{
		    	        header: 'Due Date',
		    	        width: 150,
		    	        dataIndex: 'dueDate',
		    	        renderer: me.columnRendererUtils.renderTaskDueDate
		    	    }]
    	

    			});
    	
    	return me.callParent(arguments);
    }
});
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
Ext.define('Ssp.view.tools.actionplan.AddTaskForm', {
	extend: 'Ext.form.Panel',
	alias: 'widget.addtaskform',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.actionplan.AddTasksFormViewController',
    inject: {
        store: 'confidentialityLevelsStore'
    },
	width: '100%',
    height: '100%',    
	initComponent: function() {
		var me=this;
		Ext.apply(me, 
				{
			        autoScroll: true,
			        border: 0,
			        padding: 0,
		            fieldDefaults: {
		                msgTarget: 'side',
		                labelAlign: 'right',
		                labelWidth: 150
		            },
				    items: [{
				            xtype: 'fieldset',
				            title: 'Add Task',
				            defaultType: 'textfield',
					        border: 0,
					        padding: 0,
				            defaults: {
				                anchor: '95%'
				            },
				       items: [{
					    	xtype: 'displayfield',
					        fieldLabel: 'Task Name',
					        name: 'name',
					        allowBlank: false
					    },{
				    	xtype: 'textarea',
				        fieldLabel: 'Description',
				        name: 'description',
				        maxLength: 1000,
				        allowBlank:false
				    },{
				    	xtype: 'textarea',
				        fieldLabel: 'Link (HTML Supported)',
				        name: 'link',
				        maxLength: 1000,
				        allowBlank:true
				    },{
				        xtype: 'combobox',
				        itemId: 'confidentialityLevel',
				        name: 'confidentialityLevelId',
				        fieldLabel: 'Confidentiality Level',
				        emptyText: 'Select One',
				        store: me.store,
				        valueField: 'id',
				        displayField: 'name',
				        mode: 'local',
				        typeAhead: true,
				        queryMode: 'local',
				        allowBlank: false,
				        forceSelection: true
					},{
				    	xtype: 'datefield',
				    	fieldLabel: 'Target Date',
				    	altFormats: 'm/d/Y|m-d-Y',
				        name: 'dueDate',
				        allowBlank:false    	
				    }]
				    }],
				    
				    dockedItems: [{
				        dock: 'top',
				        xtype: 'toolbar',
				        items: [{xtype: 'button', 
				        	     itemId: 'addButton', 
				        	     text:'Save', 
				        	     action: 'add' },
								 , '-',
				        	     {
				            	   xtype: 'button',
				            	   itemId: 'closeButton',
				            	   text: 'Cancel',
				            	   action: 'close'}]
				    }]
				});
		
		return me.callParent(arguments);
	}
});

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
Ext.define('Ssp.view.tools.actionplan.EditGoalForm', {
	extend: 'Ext.form.Panel',
	alias: 'widget.editgoalform',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.actionplan.EditGoalFormViewController',
    inject: {
        store: 'confidentialityLevelsStore'
    },
	initComponent: function() {
        Ext.applyIf(this, {
        	title: 'Add Goal',
            fieldDefaults: {
                msgTarget: 'side',
                labelAlign: 'right',
                labelWidth: 150
            },            
        	items: [
                {
                    xtype: 'textfield',
                    fieldLabel: 'Name',
                    anchor: '100%',
                    name: 'name',
                    allowBlank: false
                },
                {
                    xtype: 'textareafield',
                    fieldLabel: 'Description',
                    anchor: '100%',
                    name: 'description',
                    allowBlank: false
                },{
			        xtype: 'combobox',
			        itemId: 'confidentialityLevel',
			        name: 'confidentialityLevelId',
			        fieldLabel: 'Confidentiality Level',
			        emptyText: 'Select One',
			        store: this.store,
			        valueField: 'id',
			        displayField: 'acronym',
			        mode: 'local',
			        typeAhead: true,
			        queryMode: 'local',
			        allowBlank: false,
			        forceSelection: true
				}],
            
            dockedItems: [{
       		               xtype: 'toolbar',
       		               items: [{
		       		                   text: 'Save',
		       		                   xtype: 'button',
		       		                   action: 'save',
		       		                   itemId: 'saveButton'
		       		               }, '-', {
		       		                   text: 'Cancel',
		       		                   xtype: 'button',
		       		                   action: 'cancel',
		       		                   itemId: 'cancelButton'
		       		               }]
       		           }]
        });

        return this.callParent(arguments);
    }	
});
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
Ext.define('Ssp.view.tools.actionplan.DisplayActionPlan', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.displayactionplan',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.actionplan.DisplayActionPlanViewController',
    inject: {
        authenticatedPerson: 'authenticatedPerson'
    },
    width: '100%',
    height: '100%',
    padding: 0,
    initComponent: function(){
        var me = this;
        Ext.apply(me, {
            layout: {
                type: 'fit',
            
            },
            title: 'Action Plan',
            autoScroll: true,
            padding: 0,
			
            items: [{
                xtype: 'tabpanel',
                activeTab: 0,
				minTabWidth: 120,
			
				style: 'ul.x-tab-strip-top{\n   padding-top: 1px;\n background: repeat-x bottom;\n  border-bottom: 1px solid;\n background-color: white;\n}',
                   
                items: [{
                    xtype: 'panel',
                    title: 'Tasks',
                    items: [{
                        xtype: 'tabpanel',
                        activeTab: 0,
                        hidden: !me.authenticatedPerson.hasAccess('ACTION_PLAN_TASKS_PANEL'),
						padding: '2 0 0 2',
                        //title: 'Tasks',
                        itemId: 'taskStatusTabs',
                        items: [{
                            title: 'Active',
                            autoScroll: true,
                            action: 'active',
                            items: [{
                                xtype: 'tasks',
                                itemId: 'activeTasksGrid'
                            }]
                        }, {
                            title: 'Complete',
                            autoScroll: true,
                            action: 'complete',
                            items: [{
                                xtype: 'tasks',
                                itemId: 'completeTasksGrid'
                            }]
                        }, {
                            title: 'All',
                            autoScroll: true,
                            action: 'all',
                            items: [{
                                xtype: 'tasks',
                                itemId: 'allTasksGrid'
                            }]
                        }],
                        
                        dockedItems: [{
                            dock: 'top',
                            xtype: 'toolbar',
                            items: [{
                                tooltip: 'Add a Task',
                                text: 'Add',
                                hidden: !me.authenticatedPerson.hasAccess('ADD_TASK_BUTTON'),
                                xtype: 'button',
                                itemId: 'addTaskButton'
                            }]
                        }]
                    }]
                }, {
                    xtype: 'displayactionplangoals',
                    itemId: 'goalsPanel',
                    flex: 1,
                    hidden: !me.authenticatedPerson.hasAccess('ACTION_PLAN_GOALS_PANEL')
                }, {
                    xtype: 'displaystrengths',
                    itemId: 'strengthsPanel',
                    hidden: !me.authenticatedPerson.hasAccess('ACTION_PLAN_STRENGTHS_PANEL')
                }]
            }],
            dockedItems: [{
                dock: 'top',
                xtype: 'toolbar',
                items: [{
                    tooltip: 'Email Action Plan',
                    text: '',
                    width: 30,
                    height: 30,
                    hidden: !me.authenticatedPerson.hasAccess('EMAIL_ACTION_PLAN_BUTTON'),
                    cls: 'emailIcon',
                    xtype: 'button',
                    itemId: 'emailTasksButton'
                }, {
                    tooltip: 'Print Action Plan',
                    text: '',
                    width: 30,
                    height: 30,
                    hidden: !me.authenticatedPerson.hasAccess('PRINT_ACTION_PLAN_BUTTON'),
                    cls: 'printIcon',
                    xtype: 'button',
                    itemId: 'printTasksButton'
                }, {
                    xtype: 'tbspacer',
                    flex: 1
                }, {
                    xtype: 'checkbox',
                    boxLabel: 'Display only tasks that I created',
                    hidden: !me.authenticatedPerson.hasAccess('FILTER_TASKS_BY_AUTHENTICATED_USER_CHECKBOX'),
                    itemId: 'filterTasksBySelfCheck'
                }]
            }]
            /*items: [
             Ext.createWidget('tabpanel', {
             width: '100%',
             height: '100%',
             activeTab: 0,
             hidden: !me.authenticatedPerson.hasAccess('ACTION_PLAN_TASKS_PANEL'),
             title: 'Tasks',
             itemId: 'taskStatusTabs',
             items: [{
             title: 'Active',
             autoScroll: true,
             action: 'active',
             items: [{xtype: 'tasks', itemId:'activeTasksGrid'}]
             },{
             title: 'Complete',
             autoScroll: true,
             action: 'complete',
             items: [{xtype: 'tasks', itemId:'completeTasksGrid'}]
             },{
             title: 'All',
             autoScroll: true,
             action: 'all',
             items: [{xtype: 'tasks', itemId:'allTasksGrid'}]
             }],
             
             dockedItems: [{
             dock: 'top',
             xtype: 'toolbar',
             items: [{
             tooltip: 'Add a Task',
             text: 'Add',
             hidden: !me.authenticatedPerson.hasAccess('ADD_TASK_BUTTON'),
             xtype: 'button',
             itemId: 'addTaskButton'
             }]
             }]
             })
             ,{
             xtype: 'displayactionplangoals',
             itemId: 'goalsPanel',
             flex: 1,
             hidden: !me.authenticatedPerson.hasAccess('ACTION_PLAN_GOALS_PANEL')
             }
             ,{
             xtype: 'displaystrengths',
             itemId: 'strengthsPanel',
             hidden: !me.authenticatedPerson.hasAccess('ACTION_PLAN_STRENGTHS_PANEL')
             }
             ],
             
             dockedItems: [{
             dock: 'top',
             xtype: 'toolbar',
             items: [{
             tooltip: 'Email Action Plan',
             text: '',
             width: 30,
             height: 30,
             hidden: !me.authenticatedPerson.hasAccess('EMAIL_ACTION_PLAN_BUTTON'),
             cls: 'emailIcon',
             xtype: 'button',
             itemId: 'emailTasksButton'
             },{
             tooltip: 'Print Action Plan',
             text: '',
             width: 30,
             height: 30,
             hidden: !me.authenticatedPerson.hasAccess('PRINT_ACTION_PLAN_BUTTON'),
             cls: 'printIcon',
             xtype: 'button',
             itemId: 'printTasksButton'
             },{
             xtype: 'tbspacer',
             flex: 1
             },{
             xtype: 'checkbox',
             boxLabel: 'Display only tasks that I created',
             hidden: !me.authenticatedPerson.hasAccess('FILTER_TASKS_BY_AUTHENTICATED_USER_CHECKBOX'),
             itemId: 'filterTasksBySelfCheck'
             }]
             }]*/
        });
        
        return me.callParent(arguments);
    }
    
});

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
Ext.define('Ssp.view.tools.actionplan.DisplayActionPlanGoals', {
	extend: 'Ext.grid.Panel',
	alias : 'widget.displayactionplangoals',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.actionplan.DisplayActionPlanGoalsViewController',
    inject: {
    	appEventsController: 'appEventsController',
    	authenticatedPerson: 'authenticatedPerson',
    	columnRendererUtils: 'columnRendererUtils',
    	model: 'currentGoal',
        store: 'goalsStore'
    },
    width: '100%',
	height: '100%',   
    layout: 'anchor',
    itemId: 'goalsPanel',
    defaults: {
        anchor: '100%'
    },	
	initComponent: function() {	
		var me=this;
    	var sm = Ext.create('Ext.selection.CheckboxModel');
		
		Ext.apply(me, {

				title: 'Goals',
				store: me.store,
				selModel: sm,
			    columns: [{
	    	        xtype:'actioncolumn',
	    	        width:65,
	    	        header: 'Action',
	    	        items: [{
	    	            icon: Ssp.util.Constants.GRID_ITEM_EDIT_ICON_PATH,
	    	            tooltip: 'Edit Goal',
	    	            handler: function(grid, rowIndex, colIndex) {
	    	            	var rec = grid.getStore().getAt(rowIndex);
	    	                var panel = grid.up('panel');
	    	                panel.model.data=rec.data;
	    	            	panel.appEventsController.getApplication().fireEvent('editGoal');
	    	            },
	    	            getClass: function(value, metadata, record)
                        {
	    	            	// hide if user does not have permission to edit
	    	            	var cls = 'x-hide-display';
	    	            	if ( me.authenticatedPerson.hasAccess('EDIT_GOAL_BUTTON') )
	    	            	{
	    	            		cls = Ssp.util.Constants.GRID_ITEM_EDIT_ICON_PATH;
	    	            	}
	    	            	
	    	            	return cls;                            
	    	            },
	    	            scope: me
	    	        },{
	    	            icon: Ssp.util.Constants.GRID_ITEM_DELETE_ICON_PATH,
	    	            tooltip: 'Delete Goal',
	    	            handler: function(grid, rowIndex, colIndex) {
	    	            	var rec = grid.getStore().getAt(rowIndex);
	    	            	var panel = grid.up('panel');
	    	                panel.model.data=rec.data;
	    	            	panel.appEventsController.getApplication().fireEvent('deleteGoal');
	    	            },
	    	            getClass: function(value, metadata, record)
                        {
	    	            	// hide if user does not have permission to delete
	    	            	var cls = 'x-hide-display';
	    	            	if ( me.authenticatedPerson.hasAccess('DELETE_GOAL_BUTTON') )
	    	            	{
	    	            		cls = Ssp.util.Constants.GRID_ITEM_EDIT_ICON_PATH;
	    	            	}
	    	            	
	    	            	return cls;                            
	    	            },
	    	            scope: me
	    	        }]
	    	    },{
	    	        header: 'Name',
	    	        flex: 1,
	    	        dataIndex: 'name',
	    	        renderer: me.columnRendererUtils.renderGoalName
	    	    },{
	    	        header: 'Confidentiality',
	    	        dataIndex: 'confidentialityLevel',
	    	        renderer: me.columnRendererUtils.renderConfidentialityLevelName
	    	    }],
	    	    
	    	    dockedItems: [{
			        dock: 'top',
			        xtype: 'toolbar',
			        items: [{
			            tooltip: 'Add a Goal',
			            text: 'Add',
			            hidden: !me.authenticatedPerson.hasAccess('ADD_GOAL_BUTTON'),
			            xtype: 'button',
			            itemId: 'addGoalButton'
			        }]
	    	    }]
		});
		
		return me.callParent(arguments);
	}
});
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
Ext.define('Ssp.view.tools.actionplan.DisplayStrengths', {
	extend: 'Ext.form.Panel',
	alias : 'widget.displaystrengths',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.actionplan.DisplayStrengthsViewController',
    inject: {
    	authenticatedPerson: 'authenticatedPerson'
    },
    width: '100%',
	height: '100%',
	initComponent: function() {	
		var me=this;
		Ext.applyIf(me,{
	        title: 'Strengths',
			items:[{
		        xtype:'form',
		        layout:'anchor',
		        items :[{
		            xtype: 'textarea',
		            anchor: '100%',
		            height: 50,
		            fieldLabel: 'Strengths',
		            itemId: 'strengths',
		            name: 'strengths'
		        }]
			}],
			
    	    dockedItems: [{
		        dock: 'top',
		        xtype: 'toolbar',
		        items: [{
		            tooltip: 'Save Strengths',
		            text: 'Save',
		            hidden: !me.authenticatedPerson.hasAccess('SAVE_STRENGTHS_BUTTON'),
		            xtype: 'button',
		            itemId: 'saveButton'
		        },{
        	    	xtype: 'label',
        	    	html: Ssp.util.Constants.DATA_SAVE_SUCCESS_MESSAGE,
        	    	itemId: 'saveSuccessMessage',
        	    	style: Ssp.util.Constants.DATA_SAVE_SUCCESS_MESSAGE_STYLE,
        	    	hidden: true
        	    }]
    	    }]
		
		});
		
		return me.callParent(arguments);
	}
});
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
Ext.define('Ssp.view.tools.actionplan.TaskTree', {
	extend: 'Ext.tree.Panel',
	alias : 'widget.tasktree',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.actionplan.TaskTreeViewController',
    inject: {
        store: 'treeStore'
    },
	height: 250,
	width: '100%',
    initComponent: function(){
    	Ext.apply(this,
    			{
    			 store: this.store,
    			 useArrows: true,
    			 rootVisible: false,
    			 dockedItems: [
     		              {
     		               xtype: 'toolbar',
     		               items: [/*{
     	                      xtype: 'textfield',
     	                      fieldLabel: 'Search'
     	                     },
     	                      {
     	                    	  xtype: 'button',
     	                    	  text: 'GO',
     	                    	  action: 'search',
     	                    	  itemId: 'searchButton'
     	                      }*/
	                       {
	                         xtype: "label",
	                         text: "Select a task to add to the Student's Action Plan:"
	                       }]
     		           }]
     		       	
    	});
    	
    	return this.callParent(arguments);
    }
});
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
Ext.define('Ssp.view.tools.studentintake.StudentIntake', {
	extend: 'Ext.panel.Panel',
	alias : 'widget.studentintake',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.studentintake.StudentIntakeToolViewController',
    inject: {
    	authenticatedPerson: 'authenticatedPerson',
        store: 'studentsStore'
    },
	title: 'Intake',	
	width: '100%',
	height: '100%',   
	initComponent: function() {
		var me=this;
		Ext.apply(me, 
				{
		    		store: me.store,
		    		layout: 'fit',
		    		padding: 0,
		    		border: 0,
		    		items: [],
						
			    		dockedItems: [{
					        dock: 'top',
					        xtype: 'toolbar',
					        items: [{
					        	     xtype: 'button', 
					        	     itemId: 'saveButton', 
					        	     text:'Save', 
					        	     action: 'save',
					        	     hidden: !me.authenticatedPerson.hasAccess('STUDENT_INTAKE_SAVE_BUTTON'),
					        	    },
					                {
					        	     xtype: 'button', 
					        	     itemId: 'cancelButton', 
					        	     text:'Cancel', 
					        	     action: 'reset',
					        	     hidden: !me.authenticatedPerson.hasAccess('STUDENT_INTAKE_CANCEL_BUTTON'),
					        	    },
					        	    {
					        	    	xtype: 'label',
					        	    	html: Ssp.util.Constants.DATA_SAVE_SUCCESS_MESSAGE,
					        	    	itemId: 'saveSuccessMessage',
					        	    	style: Ssp.util.Constants.DATA_SAVE_SUCCESS_MESSAGE_STYLE,
					        	    	hidden: true
					        	    }]
					    }]

			});
						
		return me.callParent(arguments);
	}

});
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
Ext.define('Ssp.view.tools.studentintake.Demographics', {
	extend: 'Ext.form.Panel',
	alias: 'widget.studentintakedemographics',
	id : 'StudentIntakeDemographics',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.studentintake.DemographicsViewController',
    inject: {
    	childCareArrangementsStore: 'childCareArrangementsStore',
    	citizenshipsStore: 'citizenshipsStore',
    	educationGoalsStore: 'educationGoalsStore',
    	educationLevelsStore: 'educationLevelsStore',
    	employmentShiftsStore: 'employmentShiftsStore',
    	ethnicitiesStore: 'ethnicitiesStore',
    	gendersStore: 'gendersStore',
    	maritalStatusesStore: 'maritalStatusesStore',
    	militaryAffiliationsStore: 'militaryAffiliationsStore',
    	veteranStatusesStore: 'veteranStatusesStore'
    },    
	width: '100%',
    height: '100%',
	initComponent: function() {	
		var me=this;
		Ext.apply(me, 
				{
					autoScroll: true,
				    layout: {
				    	type: 'vbox',
				    	align: 'stretch'
				    },
				    border: 0,
				    defaults: {
				        anchor: '100%'
				    },
				    fieldDefaults: {
				        msgTarget: 'side',
				        labelAlign: 'right',
				        labelWidth: 280
				    },
				    defaultType: 'displayfield',
				    items: [{
				            xtype: 'fieldset',
							border: 0,
							padding: 10,
				            title: '',
				            defaultType: 'textfield',
				            defaults: {
				                anchor: '95%'
				            },
				       items: [{
				        xtype: 'combobox',
				        name: 'maritalStatusId',
				        fieldLabel: 'Marital Status',
				        emptyText: 'Select One',
				        store: me.maritalStatusesStore,
				        valueField: 'id',
				        displayField: 'name',
				        mode: 'local',
				        typeAhead: true,
				        queryMode: 'local',
				        allowBlank: true
					},{
				        xtype: 'combobox',
				        name: 'ethnicityId',
				        fieldLabel: 'Ethnicity',
				        emptyText: 'Select One',
				        store: me.ethnicitiesStore,
				        valueField: 'id',
				        displayField: 'name',
				        mode: 'local',
				        typeAhead: true,
				        queryMode: 'local',
				        allowBlank: true
					},{
				        xtype: 'combobox',
				        name: 'gender',
				        fieldLabel: 'Gender',
				        emptyText: 'Select One',
				        store: me.gendersStore,
				        valueField: 'code',
				        displayField: 'title',
				        mode: 'local',
				        typeAhead: true,
				        queryMode: 'local',
				        allowBlank: true
					},{
				        xtype: 'combobox',
				        itemId: 'citizenship',
				        name: 'citizenshipId',
				        fieldLabel: 'Citizenship',
				        emptyText: 'Select One',
				        store: me.citizenshipsStore,
				        valueField: 'id',
				        displayField: 'name',
				        mode: 'local',
				        typeAhead: true,
				        queryMode: 'local',
				        allowBlank: true
					},{
				        fieldLabel: 'Country of citizenship',
				        itemId: 'countryOfCitizenship',
				        name: 'countryOfCitizenship'
				    },{
				        xtype: 'combobox',
				        name: 'militaryAffiliationId',
				        fieldLabel: 'Military Affiliation',
				        emptyText: 'Select One',
				        store: me.militaryAffiliationsStore,
				        valueField: 'id',
				        displayField: 'name',
				        mode: 'local',
				        typeAhead: true,
				        queryMode: 'local',
				        allowBlank: true
					},{
				        xtype: 'combobox',
				        name: 'veteranStatusId',
				        fieldLabel: 'Veteran Status',
				        emptyText: 'Select One',
				        store: me.veteranStatusesStore,
				        valueField: 'id',
				        displayField: 'name',
				        mode: 'local',
				        typeAhead: true,
				        queryMode: 'local',
				        allowBlank: true
					},{
				        xtype: "radiogroup",
				        fieldLabel: "Are you a Primary Caregiver?",
				        columns: 1,
				        items: [
				            {boxLabel: "Yes", itemId: 'primaryCaregiverCheckOn', name: "primaryCaregiver", inputValue:"true"},
				            {boxLabel: "No",  itemId: 'primaryCaregiverCheckOff', name: "primaryCaregiver", inputValue:"false"}]
				    },{
				        xtype: 'displayfield',
				        fieldLabel: 'If you have children, please indicate below'
				    },{
				        xtype: 'numberfield',
				        name: 'numberOfChildren',
				        fieldLabel: 'How many?',
				        value: 0,
				        minValue: 0,
				        maxValue: 50
				    },{
				        fieldLabel: 'Ages? Separate each age with a comma. (1,5,12)',
				        name: 'childAges'
				    },{
				        xtype: "radiogroup",
				        fieldLabel: "Childcare Needed?",
				        itemId: 'childcareNeeded',
				        columns: 1,
				        items: [
				            {boxLabel: "Yes", itemId: 'childCareNeededCheckOn', name: "childCareNeeded", inputValue:"true"},
				            {boxLabel: "No", itemId: 'childCareNeededCheckOff', name: "childCareNeeded", inputValue:"false"}]
				    },{
				        xtype: 'combobox',
				        itemId: 'childcareArrangement',
				        name: 'childCareArrangementId',
				        fieldLabel: 'If yes, what are your childcare arrangements?',
				        emptyText: 'Select One',
				        store: me.childCareArrangementsStore,
				        valueField: 'id',
				        displayField: 'name',
				        mode: 'local',
				        typeAhead: true,
				        queryMode: 'local',
				        allowBlank: true
					},{
				        xtype: "radiogroup",
				        itemId: 'isEmployed',
				        fieldLabel: "Are you employed?",
				        columns: 1,
				        items: [
				            {boxLabel: "Yes", itemId: 'employedCheckOn', name: "employed", inputValue:"true"},
				            {boxLabel: "No", itemId: 'employedCheckOff', name: "employed", inputValue:"false"}]
				    },{
				        fieldLabel: 'Place of employment',
				        itemId: 'placeOfEmployment',
				        name: 'placeOfEmployment'
				    },{
				        xtype: 'combobox',
				        name: 'shift',
				        itemId: 'shift',
				        fieldLabel: 'Shift',
				        emptyText: 'Select One',
				        store: me.employmentShiftsStore,
				        valueField: 'code',
				        displayField: 'title',
				        mode: 'local',
				        typeAhead: true,
				        queryMode: 'local',
				        allowBlank: true
					},{
				        fieldLabel: 'Wage',
				        itemId: 'wage',
				        name: 'wage'
				    },{
				        fieldLabel: 'Total hours worked weekly while attending school',
				        itemId: 'totalHoursWorkedPerWeek',
				        name: 'totalHoursWorkedPerWeek'
				    }]
				    }]
				});
		
		return me.callParent(arguments);
	}
});
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
Ext.define('Ssp.view.tools.studentintake.EducationPlans', {
	extend: 'Ext.form.Panel',
	alias: 'widget.studentintakeeducationplans',
	id : 'StudentIntakeEducationPlans',   
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.studentintake.EducationPlansViewController',
    inject: {
    	formUtils: 'formRendererUtils',
        studentStatusesStore: 'studentStatusesStore'
    },
	width: '100%',
    height: '100%',
	
    initComponent: function() {	
    	var me=this;
		Ext.apply(this, 
				{
					autoScroll: true,
				    layout: {
				    	type: 'vbox',
				    	align: 'stretch'
				    },
				    border: 0,
				    defaults: {
				        anchor: '100%'
				    },
				    fieldDefaults: {
				        msgTarget: 'side',
				        labelAlign: 'left',
				        labelWidth: 225
				    },
				    defaultType: 'displayfield',
				    items: [{
				            xtype: 'fieldset',
							border: 0,
							padding: 10,
				            title: '',
				            defaultType: 'textfield',
				            defaults: {
				                anchor: '95%'
				            },
				       items: [{
				        xtype: 'combobox',
				        name: 'studentStatusId',
				        itemId: 'studentStatusCombo',
				        fieldLabel: 'Student Status',
				        emptyText: 'Select One',
				        store: me.studentStatusesStore,
				        valueField: 'id',
				        displayField: 'name',
				        mode: 'local',
				        typeAhead: true,
				        queryMode: 'local',
				        allowBlank: true
					},{
				        xtype: 'checkboxgroup',
				        fieldLabel: 'Check all that you have completed',
				        columns: 1,
				        items: [
				            {boxLabel: 'New Student Orientation', name: 'newOrientationComplete'},
				            {boxLabel: 'Registered for Classes', name: 'registeredForClasses'}
				        ]
				    },{
				        xtype: "radiogroup",
				        fieldLabel: "Have your parents obtained a college degree?",
				        columns: 1,
				        itemId: "collegeDegreeForParents",
				        items: [
				            {boxLabel: "Yes", itemId: "collegeDegreeForParentsCheckOn", name: "collegeDegreeForParents", inputValue:true},
				            {boxLabel: "No", itemId: "collegeDegreeForParentsCheckOff", name: "collegeDegreeForParents", inputValue:false}]
				    },{
				        xtype: "radiogroup",
				        fieldLabel: "Require special accommodations?",
				        columns: 1,
				        itemId: "specialNeeds",
				        items: [
				            {boxLabel: "Yes", itemId: "specialNeedsCheckOn", name: "specialNeeds", inputValue:"y"},
				            {boxLabel: "No", itemId: "specialNeedsCheckOff", name: "specialNeeds", inputValue:"n"}]
				    },{
				        xtype: 'radiogroup',
				        fieldLabel: 'What grade did you typically earn at your highest level of education?',
				        columns: 1,
				        items: [
				            {boxLabel: 'A', name: 'gradeTypicallyEarned', inputValue: "A"},
				            {boxLabel: 'A-B', name: 'gradeTypicallyEarned', inputValue: "AB"},
				            {boxLabel: 'B', name: 'gradeTypicallyEarned', inputValue: "B"},
				            {boxLabel: 'B-C', name: 'gradeTypicallyEarned', inputValue: "BC"},
				            {boxLabel: 'C', name: 'gradeTypicallyEarned', inputValue: "C"},
				            {boxLabel: 'C-D', name: 'gradeTypicallyEarned', inputValue: "CD"},
				            {boxLabel: 'D', name: 'gradeTypicallyEarned', inputValue: "D"},
				            {boxLabel: 'D-F', name: 'gradeTypicallyEarned', inputValue: "DF"},
				            {boxLabel: 'F', name: 'gradeTypicallyEarned', inputValue: "F"}
				    		]
				        }]
				    }]
				});
		
		return me.callParent(arguments);
	}	
});
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
Ext.define('Ssp.view.tools.studentintake.Personal', {
	extend: 'Ext.form.Panel',
	alias: 'widget.studentintakepersonal',
	id: 'StudentIntakePersonal',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.studentintake.PersonalViewController',
    inject: {
    	columnRendererUtils: 'columnRendererUtils',
        statesStore: 'statesStore'
    },
	width: '100%',
    height: '100%',    
	initComponent: function() {
		var me=this;
		Ext.apply(me, 
				{
					autoScroll: true,
    		        border: 0,	
				    bodyPadding: 5,				    
					layout: 'anchor',
				    defaults: {
				        anchor: '100%'
				    },
				    fieldDefaults: {
				        msgTarget: 'side',
				        labelAlign: 'right',
				        labelWidth: 200
				    },
				    items: [{
				            xtype: 'fieldset',
				            border: 0,
				            title: '',
				            defaultType: 'textfield',
				            defaults: {
				                anchor: '95%'
				            },
				       items: [{
				    	xtype: 'displayfield',
				        fieldLabel: 'Intake Completion Date',
				        name: 'studentIntakeCompleteDate',
				        renderer: Ext.util.Format.dateRenderer('m/d/Y')
				    },{
				        fieldLabel: 'First Name',
				        name: 'firstName',
				        itemId: 'firstName',
				        maxLength: 50,
				        allowBlank:false
				    },{
				        fieldLabel: 'Middle Name',
				        name: 'middleName',
				        itemId: 'middleName',
				        maxLength: 50,
				        allowBlank:true
				    },{
				        fieldLabel: 'Last Name',
				        name: 'lastName',
				        itemId: 'lastName',
				        maxLength: 50,
				        allowBlank:false
				    },{
				        fieldLabel: 'Student ID',
				        name: 'schoolId',
				        minLength: 0,
				        maxLength: 7,
				        itemId: 'studentId',
				        allowBlank:false
				    },{
				    	xtype: 'datefield',
				    	fieldLabel: 'Birth Date',
				    	itemId: 'birthDate',
				    	altFormats: 'm/d/Y|m-d-Y',
				    	invalidText: '{0} is not a valid date - it must be in the format: 06/02/2012 or 06-02-2012',
				        name: 'birthDate',
				        allowBlank:false
				    },{
				        fieldLabel: 'Home Phone',
				        name: 'homePhone',
				        emptyText: 'xxx-xxx-xxxx',
				        maxLength: 25,
				        allowBlank:true,
				        itemId: 'homePhone' 
				    },{
				        fieldLabel: 'Work Phone',
				        name: 'workPhone',
				        emptyText: 'xxx-xxx-xxxx',
				        maxLength: 25,
				        allowBlank:true,
				        itemId: 'workPhone'
				    },{
				        fieldLabel: 'Cell Phone',
				        name: 'cellPhone',
				        emptyText: 'xxx-xxx-xxxx',
				        maxLength: 25,
				        allowBlank:true,
				        itemId: 'cellPhone'
				    },{
				        fieldLabel: 'School Email',
				        name: 'primaryEmailAddress',
				        vtype:'email',
				        maxLength: 100,
				        allowBlank:true,
				        itemId: 'primaryEmailAddress'
				    },{
				        fieldLabel: 'Alternate Email',
				        name: 'secondaryEmailAddress',
				        vtype:'email',
				        maxLength: 100,
				        allowBlank:true,
				        itemId: 'secondaryEmailAddress'
				    },{
				    	xtype: 'displayfield',
				    	fieldLabel: 'CURRENT ADDRESS'
				    },{
				    	xtype: 'displayfield',
				    	fieldLabel: 'Non-local',
				    	name: 'nonLocalAddress',
				    	renderer: me.columnRendererUtils.renderFriendlyBoolean
				    },{
				        fieldLabel: 'Address Line 1',
				        name: 'addressLine1',
				        maxLength: 50,
				        allowBlank:true,
				        itemId: 'addressLine1'
				    },{
				        fieldLabel: 'Address Line 2',
				        name: 'addressLine1',
				        maxLength: 50,
				        allowBlank:true,
				        itemId: 'addressLine2'
				    },{
				        fieldLabel: 'City',
				        name: 'city',
				        maxLength: 50,
				        allowBlank:true,
				        itemId: 'city'
				    },{
				        xtype: 'combobox',
				        name: 'state',
				        fieldLabel: 'State',
				        emptyText: 'Select a State',
				        store: me.statesStore,
				        valueField: 'code',
				        displayField: 'title',
				        mode: 'local',
				        typeAhead: true,
				        queryMode: 'local',
				        allowBlank: true,
				        forceSelection: true,
				        itemId: 'state'
					},{
				        fieldLabel: 'Zip Code',
				        name: 'zipCode',
				        maxLength: 10,
				        allowBlank:true,
				        itemId: 'zipCode'
				    },{
				    	xtype: 'displayfield',
				    	fieldLabel: 'ALTERNATE ADDRESS'
				    },{
				    	xtype:'checkbox',
				    	fieldLabel: 'In Use',
				    	name: 'alternateAddressInUse'
				    },{
				        fieldLabel: 'Address',
				        name: 'alternateAddressLine1',
				        maxLength: 50,
				        allowBlank:true,
				        itemId: 'alternateAddress'
				    },{
				        fieldLabel: 'City',
				        name: 'alternateAddressCity',
				        maxLength: 50,
				        allowBlank:true,
				        itemId: 'alternateAddressCity'
				    },{
				        xtype: 'combobox',
				        name: 'alternateAddressState',
				        fieldLabel: 'State',
				        emptyText: 'Select a State',
				        store: me.statesStore,
				        valueField: 'code',
				        displayField: 'title',
				        mode: 'local',
				        typeAhead: true,
				        queryMode: 'local',
				        allowBlank: true,
				        forceSelection: true,
				        itemId: 'alternateAddressState'
					},{
				        fieldLabel: 'Zip Code',
				        name: 'alternateAddressZipCode',
				        maxLength: 10,
				        allowBlank:true,
				        itemId: 'alternateAddressZipCode'
				    },{
				        fieldLabel: 'Country',
				        name: 'alternateAddressCountry',
				        allowBlank:true,
				        itemId: 'alternateAddressCountry'
				    }]
				    }]
				});
		
		return me.callParent(arguments);
	}
});

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
Ext.define('Ssp.view.tools.journal.Journal', {
	extend: 'Ext.grid.Panel',
	alias : 'widget.journal',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.journal.JournalToolViewController',
    inject: {
    	appEventsController: 'appEventsController',
    	authenticatedPerson: 'authenticatedPerson',
    	columnRendererUtils: 'columnRendererUtils',
    	model: 'currentJournalEntry',
        store: 'journalEntriesStore'
    },
	width: '100%',
	height: '100%',
	initComponent: function() {	
		var me=this;
    	var sm = Ext.create('Ext.selection.CheckboxModel');
		Ext.apply(me, 
				{
		            autoScroll: true,
		            title: 'Journal',
		            store: me.store,
	    		      columns: [{
					    	        xtype:'actioncolumn',
					    	        width:65,
					    	        header: 'Action',
					    	        items: [{
					    	            icon: Ssp.util.Constants.GRID_ITEM_EDIT_ICON_PATH,
					    	            tooltip: 'Edit Journal Note',
					    	            handler: function(grid, rowIndex, colIndex) {
					    	            	var rec = grid.getStore().getAt(rowIndex);
					    	            	var panel = grid.up('panel');
					    	                panel.model.data=rec.data;
					    	                panel.appEventsController.getApplication().fireEvent('editJournalEntry');
					    	            },
					    	            getClass: function(value, metadata, record)
			                            {
					    	            	// hide user does not have permission to edit
					    	            	var cls = 'x-hide-display';
					    	            	if ( me.authenticatedPerson.hasAccess('EDIT_JOURNAL_ENTRY_BUTTON') )
					    	            	{
					    	            		cls = Ssp.util.Constants.GRID_ITEM_EDIT_ICON_PATH;
					    	            	}
					    	            	
					    	            	return cls;                            
					    	            },
					    	            scope: me
					    	        },{
					    	            icon: Ssp.util.Constants.GRID_ITEM_DELETE_ICON_PATH,
					    	            tooltip: 'Delete Journal Note',
					    	            handler: function(grid, rowIndex, colIndex) {
					    	            	var rec = grid.getStore().getAt(rowIndex);
					    	            	var panel = grid.up('panel');
					    	                panel.model.data=rec.data;
					    	            	panel.appEventsController.getApplication().fireEvent('deleteJournalEntry');
					    	            },
					    	            getClass: function(value, metadata, record)
			                            {
					    	            	// hide user does not have permission to delete
					    	            	var cls = 'x-hide-display';
					    	            	if ( me.authenticatedPerson.hasAccess('DELETE_JOURNAL_ENTRY_BUTTON') )
					    	            	{
					    	            		cls = Ssp.util.Constants.GRID_ITEM_DELETE_ICON_PATH;
					    	            	}
					    	            	
					    	            	return cls;                            
					    	            },
					    	            scope: me
					    	        }]
				                },
	    		                { header: 'Date',  
		    		                  dataIndex: 'entryDate',
		    		                  flex: 1,
		    		                  //renderer: Ext.util.Format.dateRenderer('m/d/Y g:i A')
									  renderer: Ext.util.Format.dateRenderer('m/d/Y')
	    		                },
	    		                { header: 'Entered By',  
	    		                  dataIndex: 'createdBy',
	    		                  flex: 1,
	    		                  renderer: me.columnRendererUtils.renderCreatedBy
	    		                },
	      		                { header: 'Source',
	      		                  dataIndex: 'journalSource', 
	      		                  flex: 1,
	      		                  renderer: me.columnRendererUtils.renderJournalSourceName
	    		                },
	      		                { header: 'Confidentiality',
	      		                  dataIndex: 'confidentialityLevel', 
	      		                  flex: 1,
	      		                  renderer: me.columnRendererUtils.renderConfidentialityLevelName
	    		                }],
				    
				    dockedItems: [{
				        dock: 'top',
				        xtype: 'toolbar',
				        items: [{
				            tooltip: 'Add Journal Note',
				            text: 'Add',
				            xtype: 'button',
				            hidden: !me.authenticatedPerson.hasAccess('ADD_JOURNAL_ENTRY_BUTTON'),
				            itemId: 'addButton'
				        }]
				    }]
				});
		
		return me.callParent(arguments);
	}
});
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
Ext.define('Ssp.view.tools.journal.EditJournal',{
	extend: 'Ext.form.Panel',
	alias : 'widget.editjournal',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.journal.EditJournalViewController',
    inject: {
        confidentialityLevelsStore: 'confidentialityLevelsStore',
        journalSourcesStore: 'journalSourcesStore',
        journalTracksStore: 'journalTracksStore',
        model: 'currentJournalEntry'
    },	
    initComponent: function() {
    	var me=this;
    	Ext.applyIf(me, {
        	title: ((me.model.get('id') == "") ? "Add Journal" : "Edit Journal"),
        	autoScroll: true,
        	defaults: {
            	labelWidth: 150,
            	padding: 5,
            	labelAlign: 'right'
            },
        	items: [{
			    	xtype: 'datefield',
			    	fieldLabel: 'Entry Date',
			    	itemId: 'entryDateField',
			    	altFormats: 'm/d/Y|m-d-Y',
			        name: 'entryDate',
			        allowBlank:false
			     },{
			        xtype: 'combobox',
			        itemId: 'confidentialityLevelCombo',
			        name: 'confidentialityLevelId',
			        fieldLabel: 'Confidentiality Level',
			        emptyText: 'Select One',
			        store: me.confidentialityLevelsStore,
			        valueField: 'id',
			        displayField: 'name',
			        mode: 'local',
			        typeAhead: true,
			        queryMode: 'local',
			        allowBlank: false,
			        forceSelection: true,
			        anchor: '95%'
				},{
			        xtype: 'combobox',
			        itemId: 'journalSourceCombo',
			        name: 'journalSourceId',
			        fieldLabel: 'Source',
			        emptyText: 'Select One',
			        store: me.journalSourcesStore,
			        valueField: 'id',
			        displayField: 'name',
			        mode: 'local',
			        typeAhead: true,
			        queryMode: 'local',
			        allowBlank: false,
			        forceSelection: true,
			        anchor: '95%'
				},{
                    xtype: 'textareafield',
                    fieldLabel: 'Comment (Optional)',
                    itemId: 'commentText',
                    anchor: '95%',
                    name: 'comment'
                },{
			        xtype: 'fieldcontainer',
			        fieldLabel: 'Journal Track (Optional)',
			        labelWidth: 155,
			        anchor: '95%',
			        layout: 'hbox',
			        items: [{
						        xtype: 'combobox',
						        itemId: 'journalTrackCombo',
						        name: 'journalTrackId',
						        fieldLabel: '',
						        emptyText: 'Select One',
						        store: me.journalTracksStore,
						        valueField: 'id',
						        displayField: 'name',
						        mode: 'local',
						        typeAhead: true,
						        queryMode: 'local',
						        allowBlank: true,
						        forceSelection: false,
						        flex: 1
							},{
								xtype: 'tbspacer',
								width: 10
							},{
					            tooltip: 'Removes the assigned Journal Track and Session Details',
					            text: 'Remove/Reset',
					            xtype: 'button',
					            itemId: 'removeJournalTrackButton',
					            hidden: ((me.model.get('id') == "")?false : true)
				    	    }]
				},{
			        xtype: 'fieldcontainer',
			        fieldLabel: 'Session Details',
			        labelWidth: 155,
			        anchor: '95%',
			        layout: 'hbox',
			        items: [{
					            tooltip: 'Add Journal Session Details',
					            text: 'Add/Edit Session Details',
					            xtype: 'button',
					            itemId: 'addSessionDetailsButton'
				    	    }]
				},
                { xtype: 'displayjournaldetails', autoScroll: true, anchor:'95% 50%' }
				],
            
            dockedItems: [{
       		               xtype: 'toolbar',
       		               items: [{
		       		                   text: 'Save',
		       		                   xtype: 'button',
		       		                   action: 'save',
		       		                   itemId: 'saveButton'
		       		               }, '-', {
		       		                   text: 'Cancel',
		       		                   xtype: 'button',
		       		                   action: 'cancel',
		       		                   itemId: 'cancelButton'
		       		               }]
       		           }]
        });

        return me.callParent(arguments);
    }	
});
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
Ext.define('Ssp.view.tools.journal.DisplayDetails', {
	extend: 'Ext.grid.Panel',
	alias: 'widget.displayjournaldetails',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.journal.DisplayDetailsViewController',
    inject: {
        store: 'journalEntryDetailsStore'
    },
    layout:'fit',
	width: '100%',
	height: '100%',
    initComponent: function(){
    	Ext.apply(this,
    			{
    	    		store: this.store,
    	    		hideHeaders: true,
    	    		
    	    		features: [{
		    	        id: 'group',
		    	        ftype: 'grouping',
		    	        groupHeaderTpl: '{name}',
		    	        hideGroupedHeader: false,
		    	        enableGroupingMenu: false
		    	    }],
		    	    
		    	    columns: [{
		    	        text: '',
		    	        flex: 1,
		    	        sortable: false,
		    	        dataIndex: 'name'
		    	    }]
    			});
    	
    	return this.callParent(arguments);
    }
});
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
Ext.define('Ssp.view.tools.journal.TrackTree', {
	extend: 'Ext.tree.Panel',
	alias : 'widget.journaltracktree',
	mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.journal.TrackTreeViewController',
    inject: {
        store: 'treeStore'
    },
	height: 200,
	width: '100%',
	
    initComponent: function(){
    	Ext.apply(this,
    			{
   		     singleExpand: false,
			 store: this.store,
			 useArrows: true,
			 rootVisible: false ,
			 hideCollapseTool: true,

			 dockedItems: [{
		               xtype: 'toolbar',
		               items: [{
     		                   text: 'Save Details',
     		                   xtype: 'button',
     		                   action: 'save',
     		                   itemId: 'saveButton'
     		               }, '-', {
     		                   text: 'Cancel',
     		                   xtype: 'button',
     		                   action: 'cancel',
     		                   itemId: 'cancelButton'
     		               }]
		           },{
 		               xtype: 'toolbar',
  		               dock: 'top',
  		               items: [{
  		                         xtype: 'label',
  		                         text: 'Select the details for this Journal Session'
  		                       }]  
  		            }]			 
			 
    });   	
    	
    	return this.callParent(arguments);
    }
});
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
Ext.define('Ssp.view.tools.earlyalert.EarlyAlert', {
    extend: 'Ext.tree.Panel',
    alias: 'widget.earlyalert',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.earlyalert.EarlyAlertToolViewController',
    inject: {
        appEventsController: 'appEventsController',
        authenticatedPerson: 'authenticatedPerson',
        columnRendererUtils: 'columnRendererUtils',
        model: 'currentEarlyAlert',
        treeStore: 'earlyAlertsTreeStore'
    },
    width: '100%',
    height: '100%',
    initComponent: function(){
        var me = this;
        Ext.apply(me, {
            autoScroll: true,
            title: 'Early Alerts',
            cls: 'early-alert-tree-panel',
            collapsible: false,
            useArrows: true,
            rootVisible: false,
            store: me.treeStore,
            multiSelect: false,
            singleExpand: true,
            
            columns: [{
                text: 'Responses',
                flex: .5,
                dataIndex: 'noOfResponses',
                sortable: false
            }, {
                text: 'Created By',
                flex: 1,
                dataIndex: 'createdBy',
                renderer: me.columnRendererUtils.renderCreatedBy,
                sortable: false
            }, {
                text: 'Created Date',
                flex: 1,
                dataIndex: 'createdDate',
                renderer: Ext.util.Format.dateRenderer('Y-m-d g:i A'),
                sortable: false
            }, {
                text: 'Status',
                flex: .5,
                sortable: false,
                dataIndex: 'closedDate',
                renderer: me.columnRendererUtils.renderEarlyAlertStatus
            }, {
                text: 'Details',
                flex: 2,
                sortable: false,
                dataIndex: 'gridDisplayDetails'
            }],
            
            viewConfig: {
                markDirty: false
            }
        });
        
        return me.callParent(arguments);
    }
});

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
Ext.define('Ssp.view.tools.earlyalert.EarlyAlertResponse',{
    extend: 'Ext.form.Panel',
    alias : 'widget.earlyalertresponse',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.earlyalert.EarlyAlertResponseViewController',
    inject: {
        earlyAlert: 'currentEarlyAlert',
        outcomesStore: 'earlyAlertOutcomesStore',
        outreachesStore: 'earlyAlertOutreachesStore',
        referralsStore: 'earlyAlertReferralsStore'
    },
    initComponent: function() {
        var me=this;
        Ext.applyIf(me, {
            autoScroll: true,
            title: 'Early Alert Response',
            defaults:{
                labelWidth: 200
            },
            items: [
			
                {
                           xtype: 'toolbar',
                           dock: 'top',
                           items: [{
                                       text: 'Save',
                                       xtype: 'button',
                                       action: 'save',
                                       itemId: 'saveButton'
                                   }, '-', {
                                       text: 'Cancel',
                                       xtype: 'button',
                                       action: 'cancel',
                                       itemId: 'cancelButton'
                                   }]
                       },{
                    xtype: 'displayfield',
                    fieldLabel: 'Early Alert Course',
                    value: me.earlyAlert.get('courseName') +  ' ' + me.earlyAlert.get('courseTitle')
                 },{
                    xtype: 'combobox',
                    itemId: 'outcomeCombo',
                    name: 'earlyAlertOutcomeId',
                    fieldLabel: 'Outcome',
                    emptyText: 'Select One',
                    store: me.outcomesStore,
                    valueField: 'id',
                    displayField: 'name',
                    mode: 'local',
                    typeAhead: true,
                    queryMode: 'local',
                    allowBlank: false,
                    forceSelection: true,
                    anchor: '95%'
                },{
                    xtype: 'textfield',
                    itemId: 'otherOutcomeDescriptionText',
                    name: 'earlyAlertOutcomeOtherDescription',
                    fieldLabel: 'Other Outcome Description',
                    anchor: '95%'
                },{
                    xtype: 'multiselect',
                    name: 'earlyAlertOutreachIds',
                    itemId: 'outreachList',
                    fieldLabel: 'Outreach'+Ssp.util.Constants.REQUIRED_ASTERISK_DISPLAY,
                    store: me.outreachesStore,
                    displayField: 'name',
                    msgTarget: 'side',
                    valueField: 'id',
                    invalidCls: 'multiselect-invalid',
                    minSelections: 1,
                    allowBlank: false,
                    anchor: '95%'
                },{
                    xtype: 'textareafield',
                    fieldLabel: 'Comment',
                    anchor: '95%',
                    name: 'comment',
                    allowBlank: false
                },{
                    xtype: 'checkboxfield',
                    fieldLabel: 'Close',
                    name: 'closed',
                    itemId: 'closedField',
                    inputValue: true
                },{
                   xtype:'earlyalertreferrals',
                   flex: 1
                }
				],
            
            
			dockedItems: [{
                           xtype: 'toolbar',
                           items: [{
                                       text: 'Return to Early Alert List',
                                       xtype: 'button',
                                     
                                       itemId: 'responseGotoEAListButton'
                                   }, '-', {
                                       text: 'Return to Early Alert Details',
                                       xtype: 'button',
                                      
                                       itemId: 'responseGotoEADetailsButton'
                                   }]
                       }]		   
			
        });

        return me.callParent(arguments);
    }   
});
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
Ext.define('Ssp.view.tools.earlyalert.EarlyAlertReferrals', {
	extend: 'Ext.form.Panel',
	alias: 'widget.earlyalertreferrals',
	mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.earlyalert.EarlyAlertReferralsViewController',
	width: '100%',
    height: '100%',
    autoScroll: true,
	initComponent: function() {	
		var me=this;
		Ext.apply(this, 
				{
				    bodyPadding: 5,
				    layout: 'anchor'
				});
		
		return me.callParent(arguments);
	}
});
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
Ext.define('Ssp.view.tools.earlyalert.EarlyAlertDetails', {
    extend: 'Ext.form.Panel',
    alias: 'widget.earlyalertdetails',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.earlyalert.EarlyAlertDetailsViewController',
    inject: {
        model: 'currentEarlyAlert',
        selectedSuggestionsStore: 'earlyAlertDetailsSuggestionsStore',
        authenticatedPerson: 'authenticatedPerson',
        columnRendererUtils: 'columnRendererUtils',
        treeStore: 'earlyAlertsTreeStore',
		currentEarlyAlertResponsesGridStore: 'currentEarlyAlertResponsesGridStore'
    },
    width: '100%',
    height: '100%',
    title: 'Early Alert Details',

    // By default we assume the component causing this view to load has already
    // loaded the EA of interest into a shared resource (the injected
    // 'currentEarlyAlert')
    reloadEarlyAlert: false,

    initComponent: function(){
        var me = this;
        Ext.applyIf(me, {
            autoScroll: true,
            items: [{
                xtype: 'fieldcontainer',
                fieldLabel: '',
                layout: 'hbox',
                margin: '5 0 0 0',
                defaultType: 'displayfield',
                fieldDefaults: {
                    msgTarget: 'side',
                    //labelAlign: 'right',
                    //labelWidth: 80
                },
                items: [{
                    xtype: 'fieldset',
                    border: 0,
                    title: '',
                    defaultType: 'displayfield',
                    defaults: {
                        anchor: '100%'
                    },
                    flex: .40,
                    items: [{
                    
                        fieldLabel: 'Created By',
                        
                        name: 'createdByPersonName',
                        itemId: 'createdByField'
                    }, {
                    
                        fieldLabel: 'Created Date',
                        
                        name: 'createdDate',
                        itemId: 'createdDateField',
                        renderer: Ext.util.Format.dateRenderer('Y-m-d g:i A')
                    }, {
                    
                        fieldLabel: 'Course Name',
                        
                        name: 'courseName'
                    }, {
                    
                        fieldLabel: 'Campus',
                        itemId: 'campusField',
                        
                        name: 'campus'
                    }, {
                    
                        fieldLabel: 'Reason',
                        itemId: 'earlyAlertReasonField',
                        
                        name: 'earlyAlertReason'
                    }, {
                        xtype: 'multiselect',
                        name: 'earlyAlertSuggestionIds',
                        itemId: 'earlyAlertSuggestionsList',
                        fieldLabel: 'Suggestions',
                        store: me.selectedSuggestionsStore,
                        displayField: 'name',
                        anchor: '95%'
                    }, {
                    
                        fieldLabel: 'Comment',
                        name: 'comment',
                    
                    }, {
                        xtype: 'tbspacer',
                        height: '10'
                    }, {
                        xtype: 'toolbar',
                        dock: 'top',
                        items: [{
                            text: 'Respond  to selected Early Alert',
                            xtype: 'button',
                            itemId: 'detailRespondButton',
                            hidden: !me.authenticatedPerson.hasAccess('RESPOND_EARLY_ALERT_BUTTON')
                        }]
                    }, {
                        xtype: 'gridpanel',
                        title: 'Responses',
                        id: 'detailResponseGridPanel',
						store: me.currentEarlyAlertResponsesGridStore,
                        columns: [{
                            text: 'Created By',
                            flex: 1,
                            dataIndex: 'createdBy',
                            renderer: me.columnRendererUtils.renderCreatedBy,
                            sortable: false
                        }, {
                            text: 'Created Date',
                            flex: 1,
                            dataIndex: 'createdDate',
                            renderer: Ext.util.Format.dateRenderer('Y-m-d g:i A'),
                            sortable: false
                        }, {
                            text: 'Status',
                            flex: .5,
                            sortable: false,
                            dataIndex: 'closedDate',
                            renderer: me.columnRendererUtils.renderEarlyAlertStatus
                        }, {
                            text: 'Details',
                            flex: 2,
                            sortable: false,
                            dataIndex: 'gridDisplayDetails'
                        }]
                    }]
                
                }, {
                    xtype: 'fieldset',
                    border: 0,
                    title: '',
                    defaultType: 'displayfield',
                    defaults: {
                        anchor: '100%'
                    },
                    flex: .30,
                    items: [{
                        fieldLabel: 'Status',
                        name: 'status',
                        itemId: 'statusField'
                    }, {
                        fieldLabel: 'Closed By',
                        name: 'closedByPersonName',
                        itemId: 'closedByField'
                    }, {
                        fieldLabel: 'Closed Date',
                        name: 'closedDate',
                        renderer: Ext.util.Format.dateRenderer('Y-m-d g:i A')
                    }, {
                    
                        fieldLabel: 'Email CC',
                        
                        name: 'emailCC'
                    }]
                
                }]
            }],
            
            dockedItems: [{
                xtype: 'toolbar',
                items: [{
                    text: 'Return to Early Alert List',
                    xtype: 'button',
                    itemId: 'finishButton'
                }]
            }]
        });
        
        return me.callParent(arguments);
    }
});

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
Ext.define('Ssp.view.tools.earlyalert.EarlyAlertResponseDetails',{
	extend: 'Ext.form.Panel',
	alias : 'widget.earlyalertresponsedetails',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.earlyalert.EarlyAlertResponseDetailsViewController',
    inject: {
    	selectedOutreachesStore: 'earlyAlertResponseDetailsOutreachesStore',
    	selectedReferralsStore: 'earlyAlertResponseDetailsReferralsStore'
    },
    title: 'Early Alert Response Details',
	initComponent: function() {
		var me=this;
        Ext.applyIf(me, {
        	autoScroll: true,
            items: [{
	                xtype: 'displayfield',
	                fieldLabel: 'Created By',
	                anchor: '100%',
	                name: 'createdByPersonName',
	                itemId: 'createdByField'
	            },{
	                xtype: 'displayfield',
	                fieldLabel: 'Created Date',
	                anchor: '100%',
	                name: 'createdDate',
	                itemId: 'createdDateField',
	                renderer: Ext.util.Format.dateRenderer('Y-m-d g:i A')
	            },{
	                xtype: 'displayfield',
	                fieldLabel: 'Outcome',
	                anchor: '100%',
	                itemId: 'outcomeField',
	                name: 'outcome'
	            },{
		            xtype: 'multiselect',
		            name: 'earlyAlertOutreachIds',
		            itemId: 'earlyAlertOutreachList',
		            fieldLabel: 'Suggestions',
		            store: me.selectedOutreachesStore,
		            displayField: 'name',
		            anchor: '95%'
		        },{
		            xtype: 'multiselect',
		            name: 'earlyAlertReferralIds',
		            itemId: 'earlyAlertReferralsList',
		            fieldLabel: 'Referrals',
		            store: me.selectedReferralsStore,
		            displayField: 'name',
		            anchor: '95%'
		        },{
                    xtype: 'displayfield',
                    fieldLabel: 'Comment',
                    anchor: '100%',
                    name: 'comment'
                }],
            
            dockedItems: [{
       		               xtype: 'toolbar',
       		               items: [{
		       		                   text: 'Return to Early Alert Details',
		       		                   xtype: 'button',
		       		                   itemId: 'finishButton'
		       		               }]
       		           }]
        });

        return me.callParent(arguments);
    }	
});
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
Ext.define('Ssp.view.tools.document.StudentDocuments', {
	extend: 'Ext.grid.Panel',
	alias : 'widget.studentdocuments',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.document.StudentDocumentToolViewController',
    inject: {
    	appEventsController: 'appEventsController',
    	columnRendererUtils: 'columnRendererUtils',
    	model: 'currentDocument',
        store: 'documentsStore'
    },
	title: 'Student Documents',	
	width: '100%',
	height: '100%',   
	autoScroll: true,
    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
			border: 0,
			store: this.store,
			dockedItems: [{
		        dock: 'top',
		        xtype: 'toolbar',
		        items: [{
		        			xtype: 'button', 
		        			itemId: 'addButton', 
		        			text:'Add', 
		        			action: 'add'
		        	   },{
		        			xtype: 'button', 
		        			itemId: 'downloadButton', 
		        			text:'Download', 
		        			action: 'download'
		        	   }]
			}],
			
        	columns: [
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'createdDate',
                    text: 'Date Entered'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'name',
                    text: 'Name'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'note',
                    text: 'Note',
                    flex: 1
                },{
	    	        xtype:'actioncolumn',
	    	        width:65,
	    	        header: 'Action',
	    	        items: [{
	    	            icon: Ssp.util.Constants.GRID_ITEM_EDIT_ICON_PATH,
	    	            tooltip: 'Edit Task',
	    	            handler: function(grid, rowIndex, colIndex) {
	    	            	var rec = grid.getStore().getAt(rowIndex);
	    	            	var panel = grid.up('panel');
	    	                panel.model.data=rec.data;
	    	                panel.appEventsController.getApplication().fireEvent('editDocument');
	    	            },
	    	            scope: this
	    	        },{
	    	            icon: Ssp.util.Constants.GRID_ITEM_DELETE_ICON_PATH,
	    	            tooltip: 'Delete Task',
	    	            handler: function(grid, rowIndex, colIndex) {
	    	            	var rec = grid.getStore().getAt(rowIndex);
	    	            	var panel = grid.up('panel');
	    	                panel.model.data=rec.data;
	    	            	panel.appEventsController.getApplication().fireEvent('deleteDocument');
	    	            },
	    	            scope: this
	    	        }]
                }],
                
            viewConfig: {

            }
        });

        me.callParent(arguments);
    }
});
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
Ext.define('Ssp.view.tools.document.EditDocument',{
	extend: 'Ext.form.Panel',
	alias : 'widget.editdocument',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.document.EditDocumentViewController',
    initComponent: function() {
		Ext.applyIf(this, {
        	title: 'Edit Document',
            defaults: {
            	labelWidth: 150
            },
        	items: [
                {
			        xtype: 'combobox',
			        itemId: 'confidentialityLevelCombo',
			        name: 'confidentialityLevelId',
			        fieldLabel: 'Confidentiality Level',
			        emptyText: 'Select One',
			        store: this.confidentialityLevelsStore,
			        valueField: 'id',
			        displayField: 'name',
			        mode: 'local',
			        typeAhead: true,
			        queryMode: 'local',
			        allowBlank: false,
			        forceSelection: true,
			        anchor: '95%'
				},{
                    xtype: 'textfield',
                    fieldLabel: 'Name',
                    anchor: '100%',
                    name: 'name',
                    anchor: '95%',
                    allowBlank: false
                },{
                    xtype: 'textareafield',
                    fieldLabel: 'Note',
                    anchor: '100%',
                    name: 'note',
                    anchor: '95%'
                }],
            
            dockedItems: [{
       		               xtype: 'toolbar',
       		               items: [{
		       		                   text: 'Save',
		       		                   xtype: 'button',
		       		                   action: 'save',
		       		                   itemId: 'saveButton'
		       		               }, '-', {
		       		                   text: 'Cancel',
		       		                   xtype: 'button',
		       		                   action: 'cancel',
		       		                   itemId: 'cancelButton'
		       		               }]
       		           }]
        });

        return this.callParent(arguments);
    }	
});
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
Ext.define('Ssp.view.admin.forms.AbstractReferenceAdmin', {
	extend: 'Ext.grid.Panel',
	alias : 'widget.abstractreferenceadmin',
	title: 'Admin',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.AbstractReferenceAdminViewController',
    inject: {
        apiProperties: 'apiProperties',
        authenticatedPerson: 'authenticatedPerson'
    },
	height: '100%',
	width: '100%',
	autoScroll: true,

    initComponent: function(){
    	var me=this;
    	var cellEditor = Ext.create('Ext.grid.plugin.RowEditing',
		                             { clicksToEdit: 2 });
    	Ext.apply(me,
    			{
    		      plugins:cellEditor,
    		      selType: 'rowmodel',
    		      columns: [
    		                { header: 'Name',  
    		                  dataIndex: 'name',
    		                  field: {
    		                      xtype: 'textfield'
    		                  },
    		                  flex: 50 
    		                 },
    		                { header: 'Description',
    		                  dataIndex: 'description', 
    		                  flex: 50,
    		                  field: {
    		                      xtype: 'textfield'
    		                  }
    		                }
    		           ],
    		        
    		           dockedItems: [
    		       		{
    		       			xtype: 'pagingtoolbar',
    		       		    dock: 'bottom',
    		       		    itemId: 'recordPager',
    		       		    displayInfo: true,
    		       		    pageSize: me.apiProperties.getPagingSize()
    		       		},
    		              {
    		               xtype: 'toolbar',
    		               dock: 'top',
    		               items: [{
    		                   text: 'Add',
    		                   iconCls: 'icon-add',
    		                   xtype: 'button',
    		                   hidden: !me.authenticatedPerson.hasAccess('ABSTRACT_REFERENCE_ADMIN_ADD_BUTTON'),
    		                   action: 'add',
    		                   itemId: 'addButton'
    		               }, '-', {
    		                   text: 'Delete',
    		                   iconCls: 'icon-delete',
    		                   xtype: 'button',
    		                   hidden: !me.authenticatedPerson.hasAccess('ABSTRACT_REFERENCE_ADMIN_DELETE_BUTTON'),
    		                   action: 'delete',
    		                   itemId: 'deleteButton'
    		               }]
    		           },{
    		               xtype: 'toolbar',
    		               dock: 'top',
    		               items: [{
    	                      xtype: 'label',
    	                       text: 'Double-click to edit an item.'
    	                     }]
    		           }]    	
    	});
    	
    	me.callParent(arguments);
    }
});
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
Ext.define('Ssp.view.admin.forms.ConfidentialityDisclosureAgreementAdmin', {
	extend: 'Ext.form.Panel',
	alias : 'widget.confidentialitydisclosureagreementadmin',
	id: 'ConfidentialityDisclosureAgreementAdmin',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.ConfidentialityDisclosureAgreementAdminViewController',
    inject: {
        apiProperties: 'apiProperties',
        authenticatedPerson: 'authenticatedPerson'
    },
	initComponent: function() {
		var me=this;
		Ext.apply(me, 
				{
			        title: 'Confidentiality Disclosure Agreement Admin',
		    		autoScroll: true,
					width: '100%',
		    		height: '100%',
		    		bodyPadding: 5,
				    layout: 'anchor',
				    fieldDefaults: {
				        msgTarget: 'side',
				        labelAlign: 'right',
				        labelWidth: 125
				    },
				    defaultType: 'displayfield',
				       items: 
				       [{
					        fieldLabel: 'Name',
					        xtype: 'textfield',
					        disabled: !me.authenticatedPerson.hasAccess('CONFIDENTIALITY_AGREEMENT_ADMIN_FIELDS'),
					        name: 'name',
					        anchor: '95%'
					    },{
					        fieldLabel: 'Description',
					        xtype: 'textfield',
					        disabled: !me.authenticatedPerson.hasAccess('CONFIDENTIALITY_AGREEMENT_ADMIN_FIELDS'),
					        name: 'description',
					        anchor: '95%'
					    },{
		    		          xtype: 'htmleditor',
		    		          fieldLabel: 'Disclosure Agreement',
		    		          enableColors: false,
		    		          disabled: !me.authenticatedPerson.hasAccess('CONFIDENTIALITY_AGREEMENT_ADMIN_FIELDS'),
		    		          enableAlignments: false,
		    		          anchor: '95% 80%',
		    		          name: 'text'
		    		      }],
					    
		           dockedItems: [
     		              {
     		               xtype: 'toolbar',
     		               items: [{
     		                   text: 'Save',
     		                   xtype: 'button',
     		                   hidden: !me.authenticatedPerson.hasAccess('CONFIDENTIALITY_AGREEMENT_ADMIN_SAVE_BUTTON'),
     		                   action: 'save',
     		                   itemId: 'saveButton'
     		               },{
		        	    	xtype: 'label',
		        	    	html: Ssp.util.Constants.DATA_SAVE_SUCCESS_MESSAGE,
		        	    	itemId: 'saveSuccessMessage',
		        	    	style: Ssp.util.Constants.DATA_SAVE_SUCCESS_MESSAGE_STYLE,
		        	    	hidden: true
		        	    }]
     		           }]
				});
		
	     return me.callParent(arguments);
	}

});
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
Ext.define('Ssp.view.admin.forms.crg.ChallengeAdmin', {
	extend: 'Ext.container.Container',
	alias : 'widget.challengeadmin',
	title: 'Challenge Admin',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.crg.ChallengeAdminViewController',
	height: '100%',
	width: '100%',
	layout: {
        type: 'hbox',
        align: 'stretch'
    },
    initComponent: function(){
		Ext.apply(this,{
	          items: [
	                  {
	                  	xtype: 'displaychallengesadmin', 
	                  	flex: 1
	                  },{
	                  	xtype: 'displaychallengecategoriesadmin', 
	                  	flex: 1
	                  }
	                 ]});
    	return this.callParent(arguments);
    }
});
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
Ext.define('Ssp.view.admin.forms.crg.ChallengeReferralAdmin', {
	extend: 'Ext.container.Container',
	alias : 'widget.challengereferraladmin',
	title: 'Challenge Referral Admin',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.crg.ChallengeReferralAdminViewController',
	height: '100%',
	width: '100%',
	layout: {
        type: 'hbox',
        align: 'stretch'
    },
    initComponent: function(){
		Ext.apply(this,{
	          items: [
	                  {
	                  	xtype: 'displayreferralsadmin', 
	                  	flex: 1
	                  },{
		                  	xtype: 'displaychallengereferralsadmin', 
		                  	flex: 1
		                  }
	                 ]});
    	return this.callParent(arguments);
    }
});
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
Ext.define('Ssp.view.admin.forms.crg.DisplayChallengesAdmin', {
	extend: 'Ext.grid.Panel',
	alias : 'widget.displaychallengesadmin',
	title: 'Challenges Admin',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.crg.DisplayChallengesAdminViewController',
    inject: {
        apiProperties: 'apiProperties',
        authenticatedPerson: 'authenticatedPerson',
        columnRendererUtils: 'columnRendererUtils'
    },
    height: '100%',
	width: '100%',

    initComponent: function(){
    	var me=this;
    	Ext.apply(me,
    			{
		          viewConfig: {
		        	  plugins: {
		                  ptype: 'gridviewdragdrop',
		                  dragGroup: 'gridtotree',
		                  enableDrag: me.authenticatedPerson.hasAccess('CHALLENGE_CATEGORIES_ADMIN_ASSOCIATIONS'),
		        	  },
		          },
    		      autoScroll: true,
    		      selType: 'rowmodel',
    		      enableDragDrop: false,
    		      columns: [
    		                { header: 'Name',  
    		                  dataIndex: 'name',
    		                  flex: 1 
    		                },
    		                { header: 'Show On Intake',  
      		                  dataIndex: 'showInStudentIntake',
      		                  renderer: me.columnRendererUtils.renderFriendlyBoolean,
      		                  flex: 1 
      		                }
    		           ],
    		        
    		           dockedItems: [
     		       		{
     		       			xtype: 'pagingtoolbar',
     		       		    dock: 'bottom',
     		       		    displayInfo: true,
     		       		    pageSize: me.apiProperties.getPagingSize()
     		       		},
     		              {
     		               xtype: 'toolbar',
     		               items: [{
     		                   text: 'Add',
     		                   iconCls: 'icon-add',
     		                   xtype: 'button',
     		                   hidden: !me.authenticatedPerson.hasAccess('CHALLENGES_ADMIN_ADD_BUTTON'),
     		                   action: 'add',
     		                   itemId: 'addButton'
     		               }, '-', {
     		                   text: 'Edit',
     		                   iconCls: 'icon-edit',
     		                   xtype: 'button',
     		                   hidden: !me.authenticatedPerson.hasAccess('CHALLENGES_ADMIN_EDIT_BUTTON'),
     		                   action: 'edit',
     		                   itemId: 'editButton'
     		               }, '-' ,{
     		                   text: 'Delete',
     		                   iconCls: 'icon-delete',
     		                   xtype: 'button',
     		                   hidden: !me.authenticatedPerson.hasAccess('CHALLENGES_ADMIN_DELETE_BUTTON'),
     		                   action: 'delete',
     		                   itemId: 'deleteButton'
     		               }]
     		           },{
     		               xtype: 'toolbar',
     		               dock: 'top',
     		               items: [{
     	                      xtype: 'label',
     	                       text: 'Associate items by dragging a Challenge onto a Category folder'
     	                     }]
     		           }]    	
    	});
    	
    	return me.callParent(arguments);
    }
});
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
Ext.define('Ssp.view.admin.forms.crg.DisplayReferralsAdmin', {
	extend: 'Ext.grid.Panel',
	alias : 'widget.displayreferralsadmin',
	title: 'Referrals Admin',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.crg.DisplayReferralsAdminViewController',
    inject: {
        apiProperties: 'apiProperties',
        authenticatedPerson: 'authenticatedPerson'
    },
	height: '100%',
	width: '100%',

    initComponent: function(){
    	var me=this;
    	Ext.apply(me,
    			{
		          viewConfig: {
		        	  plugins: {
		                  ptype: 'gridviewdragdrop',
		                  dragGroup: 'gridtotree',
		                  enableDrag: me.authenticatedPerson.hasAccess('CHALLENGE_REFERRALS_ADMIN_ASSOCIATIONS'),
		        	  },
		          },
    		      autoScroll: true,
    		      selType: 'rowmodel',
    		      columns: [{ 
    		                  header: 'Name',  
    		                  dataIndex: 'name',
    		                  field: {
    		                      xtype: 'textfield'
    		                  },
    		                  flex: 1,
    		                  renderer : function(value, metadata, record) {
    		                      metadata.tdAttr = 'data-qtip="' + record.get('description') + '"';
    		                      return value;
    		                  }
    		                }],
    		        
    		           dockedItems: [
     		       		{
     		       			xtype: 'pagingtoolbar',
     		       		    dock: 'bottom',
     		       		    displayInfo: true,
     		       		    pageSize: me.apiProperties.getPagingSize()
     		       		},
     		              {
     		               xtype: 'toolbar',
     		               items: [{
     		                   text: 'Add',
     		                   iconCls: 'icon-add',
     		                   xtype: 'button',
     		                   hidden: !me.authenticatedPerson.hasAccess('CHALLENGE_REFERRALS_ADMIN_ADD_BUTTON'),
     		                   action: 'add',
     		                   itemId: 'addButton'
     		               }, '-', {
     		                   text: 'Edit',
     		                   iconCls: 'icon-edit',
     		                   xtype: 'button',
     		                   hidden: !me.authenticatedPerson.hasAccess('CHALLENGE_REFERRALS_ADMIN_EDIT_BUTTON'),
     		                   action: 'edit',
     		                   itemId: 'editButton'
     		               }, '-' ,{
     		                   text: 'Delete',
     		                   iconCls: 'icon-delete',
     		                   hidden: !me.authenticatedPerson.hasAccess('CHALLENGE_REFERRALS_ADMIN_DELETE_BUTTON'),
     		                   xtype: 'button',
     		                   action: 'delete',
     		                   itemId: 'deleteButton'
     		               }]
     		           },{
     		               xtype: 'toolbar',
      		              dock: 'top',
      		               items: [{
      		                         xtype: 'label',
      		                         text: 'Associate items by dragging a Referral onto a Challenge folder'
      		                       }]  
      		           }]    	
    	});
    	
    	return me.callParent(arguments);
    }
});
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
Ext.define('Ssp.view.admin.forms.crg.EditChallenge',{
	extend: 'Ext.form.Panel',
	alias : 'widget.editchallenge',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.crg.EditChallengeViewController',
    inject: {
        confidentialityLevelsStore: 'confidentialityLevelsStore'
    },
	title: 'Edit Challenge',
	initComponent: function() {
        Ext.applyIf(this, {
            items: [
                {
                    xtype: 'textfield',
                    fieldLabel: 'Challenge Name',
                    anchor: '100%',
                    name: 'name'
                },
                {
                    xtype: 'textareafield',
                    fieldLabel: 'Description',
                    anchor: '100%',
                    name: 'description'
                },
                {
                    xtype: 'textfield',
                    fieldLabel: 'Tags',
                    anchor: '100%',
                    name: 'tags'
                },{
                    xtype: 'combobox',
                    name: 'defaultConfidentialityLevelId',
                    fieldLabel: 'Confidentiality Level',
                    emptyText: 'Select One',
                    store: this.confidentialityLevelsStore,
                    valueField: 'id',
                    displayField: 'acronym',
                    mode: 'local',
                    typeAhead: true,
                    queryMode: 'local',
                    allowBlank: false,
                    forceSelection: true
            	},{
                    xtype: 'textareafield',
                    fieldLabel: 'Self Help Guide Description',
                    anchor: '100%',
                    name: 'selfHelpGuideDescription'
                },
                {
                    xtype: 'textareafield',
                    fieldLabel: 'Self Help Guide Question',
                    anchor: '100%',
                    name: 'selfHelpGuideQuestion'
                },
                {
                    xtype: 'checkboxfield',
                    fieldLabel: 'Show in Student Intake',
                    anchor: '100%',
                    name: 'showInStudentIntake'
                },
                {
                    xtype: 'checkboxfield',
                    fieldLabel: 'Show in Self Help Search',
                    anchor: '100%',
                    name: 'showInSelfHelpSearch'
                }
            ],
            
            dockedItems: [{
       		               xtype: 'toolbar',
       		               items: [{
		       		                   text: 'Save',
		       		                   xtype: 'button',
		       		                   action: 'save',
		       		                   itemId: 'saveButton'
		       		               }, '-', {
		       		                   text: 'Cancel',
		       		                   xtype: 'button',
		       		                   action: 'cancel',
		       		                   itemId: 'cancelButton'
		       		               }]
       		           }]
        });

        return this.callParent(arguments);
    }	
});
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
Ext.define('Ssp.view.admin.forms.crg.EditReferral',{
	extend: 'Ext.form.Panel',
	alias : 'widget.editreferral',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.crg.EditReferralViewController',
	title: 'Edit Referral',
	initComponent: function() {
        Ext.applyIf(this, {
            items: [
                {
                    xtype: 'textfield',
                    fieldLabel: 'Referral Name',
                    anchor: '100%',
                    name: 'name'
                },
                {
                    xtype: 'textareafield',
                    fieldLabel: 'Description',
                    anchor: '100%',
                    name: 'description'
                },{
                    xtype: 'textareafield',
                    fieldLabel: 'Public Description',
                    anchor: '100%',
                    name: 'publicDescription'
                }
                ,{
                    xtype: 'textareafield',
                    fieldLabel: 'Link (HTML supported) example &lt;a href="www.google.com"&gt;Google&lt;/a&gt;',
                    anchor: '100%',
                    name: 'link'
                },                
                {
                    xtype: 'checkboxfield',
                    fieldLabel: 'Show in Self Help Guide',
                    anchor: '100%',
                    name: 'showInSelfHelpGuide'
                }
            ],
            
            dockedItems: [{
       		               xtype: 'toolbar',
       		               items: [{
		       		                   text: 'Save',
		       		                   xtype: 'button',
		       		                   action: 'save',
		       		                   itemId: 'saveButton'
		       		               }, '-', {
		       		                   text: 'Cancel',
		       		                   xtype: 'button',
		       		                   action: 'cancel',
		       		                   itemId: 'cancelButton'
		       		               }]
       		           }]
        });

        return this.callParent(arguments);
    }	
});
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
Ext.define('Ssp.view.admin.forms.journal.JournalStepAdmin', {
	extend: 'Ext.container.Container',
	alias : 'widget.journalstepadmin',
	title: 'Challenge Admin',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.journal.JournalStepAdminViewController',
	height: '100%',
	width: '100%',
	layout: {
        type: 'hbox',
        align: 'stretch'
    },
    initComponent: function(){
		Ext.apply(this,{
	          items: [
	                  {
	                  	xtype: 'displaystepsadmin', 
	                  	flex: 1
	                  },{
	                  	xtype: 'associatetrackstepsadmin', 
	                  	flex: 1
	                  }
	                 ]});
    	return this.callParent(arguments);
    }
});
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
Ext.define('Ssp.view.admin.forms.journal.JournalStepDetailAdmin', {
	extend: 'Ext.container.Container',
	alias : 'widget.journalstepdetailadmin',
	title: 'Journal Step Detail Admin',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.journal.JournalStepDetailAdminViewController',
	height: '100%',
	width: '100%',
	layout: {
        type: 'hbox',
        align: 'stretch'
    },
    initComponent: function(){
		Ext.apply(this,{
	          items: [
	                  {
	                  	xtype: 'displaydetailsadmin', 
	                  	flex: 1
	                  },{
		                  	xtype: 'associatestepdetailsadmin', 
		                  	flex: 1
		                  }
	                 ]});
    	return this.callParent(arguments);
    }
});
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
Ext.define('Ssp.view.admin.forms.journal.DisplayDetailsAdmin', {
	extend: 'Ext.grid.Panel',
	alias : 'widget.displaydetailsadmin',
	title: 'Details Admin',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.journal.DisplayDetailsAdminViewController',
    inject: {
        apiProperties: 'apiProperties',
        authenticatedPerson: 'authenticatedPerson'
    },
	height: '100%',
	width: '100%',

    initComponent: function(){
    	var me=this;
    	Ext.apply(me,
    			{
		          viewConfig: {
		        	  plugins: {
		                  ptype: 'gridviewdragdrop',
		                  dragGroup: 'gridtotree',
		                  enableDrag: me.authenticatedPerson.hasAccess('STEP_DETAILS_ADMIN_ASSOCIATIONS')
		        	  },
		          },
    		      autoScroll: true,
    		      selType: 'rowmodel',
    		      columns: [
    		                { header: 'Name',  
    		                  dataIndex: 'name',
    		                  field: {
    		                      xtype: 'textfield'
    		                  },
    		                  flex: 1 
    		                }
    		           ],
    		        
    		           dockedItems: [
     		       		{
     		       			xtype: 'pagingtoolbar',
     		       		    dock: 'bottom',
     		       		    displayInfo: true,
     		       		    pageSize: me.apiProperties.getPagingSize()
     		       		},
     		              {
     		               xtype: 'toolbar',
     		               items: [{
     		                   text: 'Add',
     		                   iconCls: 'icon-add',
     		                   xtype: 'button',
     		                   hidden: !me.authenticatedPerson.hasAccess('JOURNAL_DETAIL_ADMIN_ADD_BUTTON'),
     		                   action: 'add',
     		                   itemId: 'addButton'
     		               }, '-', {
     		                   text: 'Edit',
     		                   iconCls: 'icon-edit',
     		                   xtype: 'button',
     		                   hidden: !me.authenticatedPerson.hasAccess('JOURNAL_DETAIL_ADMIN_EDIT_BUTTON'),
     		                   action: 'edit',
     		                   itemId: 'editButton'
     		               }, '-' ,{
     		                   text: 'Delete',
     		                   iconCls: 'icon-delete',
     		                   xtype: 'button',
     		                   hidden: !me.authenticatedPerson.hasAccess('JOURNAL_DETAIL_ADMIN_DELETE_BUTTON'),
     		                   action: 'delete',
     		                   itemId: 'deleteButton'
     		               }]
     		           },{
     		               xtype: 'toolbar',
      		               dock: 'top',
      		               items: [{
      		                         xtype: 'label',
      		                         text: 'Associate items by dragging a Detail onto a Step folder'
      		                       }]  
      		            }]    	
    	});
    	
    	return me.callParent(arguments);
    }
});
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
Ext.define('Ssp.view.admin.forms.journal.DisplayStepsAdmin', {
	extend: 'Ext.grid.Panel',
	alias : 'widget.displaystepsadmin',
	title: 'Steps Admin',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.journal.DisplayStepsAdminViewController',
    inject: {
        apiProperties: 'apiProperties',
        authenticatedPerson: 'authenticatedPerson',
        columnRendererUtils: 'columnRendererUtils'
    },
    height: '100%',
	width: '100%',

    initComponent: function(){
    	var me=this;
    	Ext.apply(me,
    			{
		          viewConfig: {
		        	  plugins: {
		                  ptype: 'gridviewdragdrop',
		                  dragGroup: 'gridtotree',
		                  enableDrag: me.authenticatedPerson.hasAccess('TRACKS_STEPS_ADMIN_ASSOCIATIONS')
		        	  },
		          },
    		      autoScroll: true,
    		      selType: 'rowmodel',
    		      enableDragDrop: false,
    		      columns: [
    		                { header: 'Name',  
    		                  dataIndex: 'name',
    		                  field: {
    		                      xtype: 'textfield'
    		                  },
    		                  flex: 1 
    		                },{ header: 'Used for Transition',  
    		                  dataIndex: 'usedForTransition',
    		                  renderer: me.columnRendererUtils.renderFriendlyBoolean,
    		                  field: {
      		                      xtype: 'checkbox'
      		                  },
    		                  flex: 1 
    		                }
    		           ],
    		        
    		           dockedItems: [
     		       		{
     		       			xtype: 'pagingtoolbar',
     		       		    dock: 'bottom',
     		       		    displayInfo: true,
     		       		    pageSize: me.apiProperties.getPagingSize()
     		       		},
     		              {
     		               xtype: 'toolbar',
     		               items: [{
     		                   text: 'Add',
     		                   iconCls: 'icon-add',
     		                   xtype: 'button',
     		                   hidden: !me.authenticatedPerson.hasAccess('JOURNAL_STEP_ADMIN_ADD_BUTTON'),
     		                   action: 'add',
     		                   itemId: 'addButton'
     		               }, '-', {
     		                   text: 'Edit',
     		                   iconCls: 'icon-edit',
     		                   xtype: 'button',
     		                   hidden: !me.authenticatedPerson.hasAccess('JOURNAL_STEP_ADMIN_EDIT_BUTTON'),
     		                   action: 'edit',
     		                   itemId: 'editButton'
     		               }, '-' ,{
     		                   text: 'Delete',
     		                   iconCls: 'icon-delete',
     		                   xtype: 'button',
     		                   hidden: !me.authenticatedPerson.hasAccess('JOURNAL_STEP_ADMIN_DELETE_BUTTON'),
     		                   action: 'delete',
     		                   itemId: 'deleteButton'
     		               }]
     		           },{
    		               xtype: 'toolbar',
      		               dock: 'top',
      		               items: [{
      		                         xtype: 'label',
      		                         text: 'Associate items by dragging a Step onto a Track folder'
      		                       }]  
      		            }]    	
    	});
    	
    	return me.callParent(arguments);
    }
});
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
Ext.define('Ssp.view.admin.forms.journal.EditStep',{
	extend: 'Ext.form.Panel',
	alias : 'widget.editjournalstep',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.journal.EditStepViewController',
	title: 'Edit Step',
	initComponent: function() {
        Ext.applyIf(this, {
            items: [
                {
                    xtype: 'textfield',
                    fieldLabel: 'Step Name',
                    anchor: '100%',
                    name: 'name'
                },
                {
                    xtype: 'textareafield',
                    fieldLabel: 'Description',
                    anchor: '100%',
                    name: 'description'
                },
                {
                    xtype: 'checkbox',
                    fieldLabel: 'Used for Transition',
                    name: 'usedForTransition'
                }],
            
            dockedItems: [{
       		               xtype: 'toolbar',
       		               items: [{
		       		                   text: 'Save',
		       		                   xtype: 'button',
		       		                   action: 'save',
		       		                   itemId: 'saveButton'
		       		               }, '-', {
		       		                   text: 'Cancel',
		       		                   xtype: 'button',
		       		                   action: 'cancel',
		       		                   itemId: 'cancelButton'
		       		               }]
       		           }]
        });

        return this.callParent(arguments);
    }	
});
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
Ext.define('Ssp.view.admin.forms.journal.EditStepDetail',{
	extend: 'Ext.form.Panel',
	alias : 'widget.editjournalstepdetail',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.journal.EditStepDetailViewController',
	title: 'Edit Detail',
	initComponent: function() {
        Ext.applyIf(this, {
            items: [
                {
                    xtype: 'textfield',
                    fieldLabel: 'Detail Name',
                    anchor: '100%',
                    name: 'name'
                },
                {
                    xtype: 'textareafield',
                    fieldLabel: 'Description',
                    anchor: '100%',
                    name: 'description'
                }],
            
            dockedItems: [{
       		               xtype: 'toolbar',
       		               items: [{
		       		                   text: 'Save',
		       		                   xtype: 'button',
		       		                   action: 'save',
		       		                   itemId: 'saveButton'
		       		               }, '-', {
		       		                   text: 'Cancel',
		       		                   xtype: 'button',
		       		                   action: 'cancel',
		       		                   itemId: 'cancelButton'
		       		               }]
       		           }]
        });

        return this.callParent(arguments);
    }	
});
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
Ext.define('Ssp.view.admin.forms.campus.CampusAdmin', {
	extend: 'Ext.grid.Panel',
	alias : 'widget.campusadmin',
	title: 'Campus Admin',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.campus.CampusAdminViewController',
    inject: {
        apiProperties: 'apiProperties',
        appEventsController: 'appEventsController',
        authenticatedPerson: 'authenticatedPerson',
        model: 'currentCampus',
        store: 'campusesStore'
    },
    height: '100%',
	width: '100%',
	layout: 'fit',
    initComponent: function(){
        var me=this;
    	Ext.apply(me,
    			{   
    		      autoScroll: true,
    		      store: me.store,
     		      columns: [{
		    	        xtype:'actioncolumn',
		    	        width: 100,
		    	        header: 'Assign Routings',
		    	        items: [{
			    	            icon: Ssp.util.Constants.GRID_ITEM_EDIT_ICON_PATH,
			    	            tooltip: 'Edit Campus Early Alert Routings',
			    	            handler: function(grid, rowIndex, colIndex) {
			    	            	var rec = grid.getStore().getAt(rowIndex);
			    	            	var panel = grid.up('panel');
			    	                panel.model.data=rec.data;
			    	            	panel.appEventsController.getApplication().fireEvent('editCampusEarlyAlertRoutings');
			    	            },
			    	            scope: me
			    	        }]
     		              },{ header: 'Name',  
    		                  dataIndex: 'name',
    		                  flex: 50 },
    		                { header: 'Description',
    		                  dataIndex: 'description', 
    		                  flex: 50
    		                }
    		           ],
    		        
    		           dockedItems: [
     		       		{
     		       			xtype: 'pagingtoolbar',
     		       		    dock: 'bottom',
     		       		    displayInfo: true,
     		       		    store: me.store,
     		       		    pageSize: me.apiProperties.getPagingSize()
     		       		},
     		              {
     		               xtype: 'toolbar',
     		               items: [{
     		                   text: 'Add',
     		                   iconCls: 'icon-add',
     		                   xtype: 'button',
     		                   hidden: !me.authenticatedPerson.hasAccess('CAMPUS_ADMIN_ADD_BUTTON'),
     		                   action: 'add',
     		                   itemId: 'addButton'
     		               }, '-', {
     		                   text: 'Edit',
     		                   iconCls: 'icon-edit',
     		                   xtype: 'button',
     		                   hidden: !me.authenticatedPerson.hasAccess('CAMPUS_ADMIN_EDIT_BUTTON'),
     		                   action: 'edit',
     		                   itemId: 'editButton'
     		               }, '-', {
     		                   text: 'Delete',
     		                   iconCls: 'icon-delete',
     		                   hidden: !me.authenticatedPerson.hasAccess('CAMPUS_ADMIN_DELETE_BUTTON'),
     		                   xtype: 'button',
     		                   action: 'delete',
     		                   itemId: 'deleteButton'
     		               }]
     		           }]  	
    	});

    	return me.callParent(arguments);
    }
});
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
Ext.define('Ssp.view.admin.forms.campus.DefineCampus',{
	extend: 'Ext.panel.Panel',
	alias : 'widget.definecampus',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.campus.DefineCampusViewController',
	title: 'Define a Campus',
	height: '100%',
	width: '100%',
	layout:'card',
	initComponent: function() {
		var me=this;
        Ext.applyIf(me, {
        	activeItem: 0,
        	
        	dockedItems: [{
	               xtype: 'toolbar',
	               dock: 'top',
	               items: [{
	                   text: 'Cancel',
	                   xtype: 'button',
	                   itemId: 'cancelCampusEditorButton'
	               },{
	                   text: 'Prev',
	                   xtype: 'button',
	                   action: 'prev',
	                   itemId: 'prevButton'
	               }, '-', {
	                   text: 'Next',
	                   xtype: 'button',
	                   action: 'next',
	                   itemId: 'nextButton'
	               }, '-', {
	                   text: 'Finish',
	                   xtype: 'button',
	                   action: 'finish',
	                   itemId: 'finishButton'
	               }]
	           }],
        	
        	items: [{
        	    xtype:'editcampus',
        	    flex: 1
        	},{
        		xtype:'campusearlyalertroutingsadmin',
        	    flex: 1
        	}]
        });
        return me.callParent(arguments);
	}
});
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
Ext.define('Ssp.view.admin.forms.campus.EditCampus',{
	extend: 'Ext.form.Panel',
	alias : 'widget.editcampus',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.campus.EditCampusViewController',
    inject: {
    	store: 'earlyAlertCoordinatorsStore'
    },
	title: 'Edit Campus',
	initComponent: function() {
		var me=this;
        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'textfield',
                    fieldLabel: 'Name',
                    anchor: '100%',
                    name: 'name'
                },{
                    xtype: 'textareafield',
                    fieldLabel: 'Description',
                    anchor: '100%',
                    name: 'description'
                },{
			        xtype: 'combobox',
			        name: 'earlyAlertCoordinatorId',
			        itemId: 'earlyAlertCoordinatorCombo',
			        fieldLabel: 'Early Alert Coordinator',
			        emptyText: 'Select One',
			        store: me.store,
			        valueField: 'id',
			        displayField: 'displayFullName',
			        mode: 'local',
			        typeAhead: true,
			        queryMode: 'local',
			        allowBlank: false,
			        width: 300
				}],
	            
	            dockedItems: [{
	       		               xtype: 'toolbar',
	       		               items: [{
			       		                   text: 'Save',
			       		                   xtype: 'button',
			       		                   action: 'save',
			       		                   itemId: 'saveButton'
			       		               }, '-', {
			       		                   text: 'Cancel',
			       		                   xtype: 'button',
			       		                   action: 'cancel',
			       		                   itemId: 'cancelButton'
			       		               }]
	       		           }]
        });

        return me.callParent(arguments);
    }	
});
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
Ext.define('Ssp.view.admin.forms.campus.CampusEarlyAlertRoutingsAdmin', {
	extend: 'Ext.panel.Panel',
	alias : 'widget.campusearlyalertroutingsadmin',
	title: 'Campus Admin',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.campus.CampusEarlyAlertRoutingsAdminViewController',
	inject: {
		campus: 'currentCampus'
	},
    height: '100%',
	width: '100%',
	layout: 'fit',
    initComponent: function(){
        var me=this;
    	Ext.apply(me,
    			{  
    			  title: 'Campuses Admin - ' + me.campus.get('name'),
    		      items: [{
    		    	xtype:'earlyalertroutingsadmin',
    		    	flex: 1
    		      }],
  	            
    		      dockedItems: [{
		               xtype: 'toolbar',
		               items: [{
	       		                   text: 'Finished Editing Campus',
	       		                   xtype: 'button',
	       		                   itemId: 'finishButton'
	       		               }]
		           }]
    	});

    	return me.callParent(arguments);
    }
});
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
Ext.define('Ssp.view.admin.forms.campus.EarlyAlertRoutingsAdmin',{
	extend: 'Ext.grid.Panel',
	alias : 'widget.earlyalertroutingsadmin',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.campus.EarlyAlertRoutingsAdminViewController',
    inject: {
        apiProperties: 'apiProperties',
        store: 'campusEarlyAlertRoutingsStore',
        columnRendererUtils: 'columnRendererUtils'
    },
    height: '100%',
	width: '100%',
    initComponent: function(){
        var me=this;
    	Ext.apply(me,
    			{   
    		      autoScroll: true,
    		      store: me.store,
    		      title: 'Early Alert Routing Groups',
     		      columns: [
    		                { header: 'Group Name',  
    		                  dataIndex: 'groupName',
    		                  flex: .4 },
      		                { header: 'Group Email',  
        		                  dataIndex: 'groupEmail',
        		                  flex: .3 },
    		                { header: 'Person',  
    		                  dataIndex: 'person',
    		                  renderer: me.columnRendererUtils.renderPersonFullName, 
    		                  flex: .3 }
    		           ],
    		        
    		           dockedItems: [{
     		               xtype: 'toolbar',
      		               dock: 'top',
      		               items: [{
      		            	   xtype: "label",
     		                   text: "Early Alert Routing Groups define optional endpoints where an Early Alert will be delivered when it's entered in the system."
     		               }]  
      		            },
     		       		{
     		       			xtype: 'pagingtoolbar',
     		       		    dock: 'bottom',
     		       		    displayInfo: true,
     		       		    store: me.store,
     		       		    pageSize: me.apiProperties.getPagingSize()
     		       		},
     		              {
     		               xtype: 'toolbar',
     		               items: [{
     		                   text: 'Add',
     		                   tooltip: 'Add Early Alert Routing Group',
     		                   iconCls: 'icon-add',
     		                   xtype: 'button',
     		                   itemId: 'addButton'
     		               }, '-', {
     		                   text: 'Edit',
     		                   tooltip: 'Edit Early Alert Routing Group',
     		                   iconCls: 'icon-edit',
     		                   xtype: 'button',
     		                   itemId: 'editButton'
     		               }, '-', {
     		                   text: 'Delete',
     		                   tooltip: 'Delete Early Alert Routing Group',
     		                   iconCls: 'icon-delete',
     		                   xtype: 'button',
     		                   itemId: 'deleteButton'
     		               }]
     		           }]  	
    	});

    	return me.callParent(arguments);
    }
});
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
Ext.define('Ssp.view.admin.forms.campus.EditCampusEarlyAlertRouting',{
	extend: 'Ext.form.Panel',
	alias : 'widget.editcampusearlyalertrouting',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.campus.EditCampusEarlyAlertRoutingViewController',
    inject: {
    	earlyAlertReasonsStore: 'earlyAlertReasonsStore',
    	peopleSearchLiteStore: 'peopleSearchLiteStore',
    	sspConfig: 'sspConfig'
    },
	title: 'Edit Routing Group',
	initComponent: function() {
        var me=this;
		Ext.applyIf(me, {
		    fieldDefaults: {
		        msgTarget: 'side',
		        labelAlign: 'right',
		        labelWidth: 125
		    },
            items: [{
			        xtype: 'combobox',
			        name: 'earlyAlertReasonId',
			        itemId: 'earlyAlertReasonCombo',
			        fieldLabel: 'Early Alert Reason',
			        emptyText: 'Select One',
			        store: me.earlyAlertReasonsStore,
			        valueField: 'id',
			        displayField: 'name',
			        mode: 'local',
			        typeAhead: true,
			        queryMode: 'local',
			        allowBlank: false,
			        width: 500
				},{
                    xtype: 'textfield',
                    fieldLabel: 'Group Name',
                    width: 500,
                    name: 'groupName',
                    allowBlank: false
                },{
                    xtype: 'textfield',
                    fieldLabel: 'Group Email',
                    name: 'groupEmail',
                    width: 500,
                    vtype:'email',
			        maxLength: 100,
			        allowBlank: false
                },{
		            xtype: 'combo',
		            store: me.peopleSearchLiteStore,
		            itemId: 'personCombo',
		            displayField: 'displayFullName',
		            emptyText: 'Name or ' + me.sspConfig.get('studentIdAlias'),
		            valueField:'id',
		            typeAhead: false,
		            fieldLabel: 'Person',
		            hideTrigger:true,
		            queryParam: 'searchTerm',
		            allowBlank: false,
		            width: 500,

		            listConfig: {
		                loadingText: 'Searching...',
		                emptyText: 'No matching people found.',
		                getInnerTpl: function() {
		                    return '{firstName} {lastName}';
		                }
		            },
		            pageSize: 10
		        }],
            
            dockedItems: [{
       		               xtype: 'toolbar',
       		               items: [{
		       		                   text: 'Save',
		       		                   xtype: 'button',
		       		                   action: 'save',
		       		                   itemId: 'saveButton'
		       		               }, '-', {
		       		                   text: 'Cancel',
		       		                   xtype: 'button',
		       		                   action: 'cancel',
		       		                   itemId: 'cancelButton'
		       		               }]
       		           }]
        });

        return me.callParent(arguments);
    }	
});
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
Ext.define('Ssp.model.AuthenticatedPerson', {
    extend: 'Ssp.model.AbstractBase',
    config: {
    	unauthorizedAccessAlertTitle: 'Unauthorized Access',
    	unauthorizedAccessAlertMessage: 'You do not have permission to access this item. Please see your system administrator if you require access to this information!'    	
    },
    fields: [{name:'permissions', type:'auto', defaultValue: null},
    		 {name:'confidentialityLevels', type:'auto', defaultValue: null},
    		 {name:'objectPermissionsCollection', type:'auto'}],  
    statics: {
    	/*
    	 * To implement a required permission against an object in the interface:
    	 * 
    	 * 1) Add a static property to this class with a name of
    	 * 'REQUIRED_PERMISSIONS_' + the object name you would like to secure. (See props below)
    	 * 
    	 * 2) Set the permissions for that object in an array as the value for the
    	 * assigned property.
    	 * 
    	 * 3) In the interface classes, determine the object access by calling the hasAccess 
    	 * method of this class and passing the object name as the sole argument, without
    	 * the appended 'REQUIRED_PERMISSIONS_' on the name.
    	 * 
    	 * For example: In a view you could assign the hidden property of a button like so:
    	 *  
    	 *  {
    	 *    xtype: 'button',
    	 *    hidden: !authenticatedPerson.hasAccess('PRINT_HISTORY_BUTTON'),
    	 *    text: 'Print History'
    	 *  }
    	 * 
    	 * The hasAccess method will return a boolean for use in securing the interface based
    	 * upon the permissions array you've defined by object name when you defined your required
    	 * permissions for the associated object.
    	 */
    	
    	/* MAIN NAVIGATION */
    	REQUIRED_PERMISSIONS_STUDENTS_NAVIGATION_BUTTON: ['ROLE_PERSON_READ'],
    	REQUIRED_PERMISSIONS_ADMIN_NAVIGATION_BUTTON: ['ROLE_REFERENCE_WRITE'],
    	
    	/* SEARCH */  	
    	REQUIRED_PERMISSIONS_STUDENT_SEARCH: ['ROLE_PERSON_SEARCH_READ'], 
    	REQUIRED_PERMISSIONS_CASELOAD_SEARCH: ['ROLE_PERSON_CASELOAD_READ'],
    	REQUIRED_PERMISSIONS_CASELOAD_FILTERS: ['ROLE_PERSON_CASELOAD_READ'],
    	REQUIRED_PERMISSIONS_SET_ACTIVE_STATUS_BUTTON: ['ROLE_PERSON_PROGRAM_STATUS_WRITE'],
    	REQUIRED_PERMISSIONS_SET_TRANSITION_STATUS_BUTTON: ['ROLE_PERSON_PROGRAM_STATUS_WRITE'],
    	REQUIRED_PERMISSIONS_SET_NON_PARTICIPATING_STATUS_BUTTON: ['ROLE_PERSON_PROGRAM_STATUS_WRITE'],
    	REQUIRED_PERMISSIONS_SET_NO_SHOW_STATUS_BUTTON: ['ROLE_PERSON_PROGRAM_STATUS_WRITE'],
    	
    	/* STUDENTS */  	
    	REQUIRED_PERMISSIONS_ADD_STUDENT_BUTTON: ['ROLE_PERSON_WRITE'],
    	REQUIRED_PERMISSIONS_EDIT_STUDENT_BUTTON: ['ROLE_PERSON_WRITE'],
    	REQUIRED_PERMISSIONS_DELETE_STUDENT_BUTTON: ['ROLE_PERSON_DELETE'],
    	
    	/* ADMIN TOOLS */
    	REQUIRED_PERMISSIONS_ABSTRACT_REFERENCE_ADMIN_ADD_BUTTON: ['ROLE_REFERENCE_WRITE'],
    	REQUIRED_PERMISSIONS_ABSTRACT_REFERENCE_ADMIN_EDIT: ['ROLE_REFERENCE_WRITE'],
    	REQUIRED_PERMISSIONS_ABSTRACT_REFERENCE_ADMIN_DELETE_BUTTON: ['ROLE_REFERENCE_WRITE'],

    	REQUIRED_PERMISSIONS_CAMPUS_ADMIN_ADD_BUTTON: ['ROLE_REFERENCE_WRITE'],
    	REQUIRED_PERMISSIONS_CAMPUS_ADMIN_EDIT_BUTTON: ['ROLE_REFERENCE_WRITE'],
    	REQUIRED_PERMISSIONS_CAMPUS_ADMIN_DELETE_BUTTON: ['ROLE_REFERENCE_WRITE'],    	

    	REQUIRED_PERMISSIONS_CONFIDENTIALITY_AGREEMENT_ADMIN_FIELDS: ['ROLE_REFERENCE_WRITE'],
    	REQUIRED_PERMISSIONS_CONFIDENTIALITY_AGREEMENT_ADMIN_SAVE_BUTTON: ['ROLE_REFERENCE_WRITE'],    	
    	
    	/* counseling reference guide admin */ 
    	
    	// delete associations
    	REQUIRED_PERMISSIONS_CHALLENGE_CATEGORIES_ASSOCIATION_ADMIN_DELETE_BUTTON: ['ROLE_REFERENCE_WRITE'],   	
    	REQUIRED_PERMISSIONS_CHALLENGE_REFERRALS_ASSOCIATION_ADMIN_DELETE_BUTTON: ['ROLE_REFERENCE_WRITE'],
    	
    	// enable drag and drop associations
    	REQUIRED_PERMISSIONS_CHALLENGE_CATEGORIES_ADMIN_ASSOCIATIONS: ['ROLE_REFERENCE_WRITE'], 
    	REQUIRED_PERMISSIONS_CHALLENGE_REFERRALS_ADMIN_ASSOCIATIONS: ['ROLE_REFERENCE_WRITE'],
    	
    	REQUIRED_PERMISSIONS_CHALLENGES_ADMIN_ADD_BUTTON: ['ROLE_REFERENCE_WRITE'],
    	REQUIRED_PERMISSIONS_CHALLENGES_ADMIN_EDIT_BUTTON: ['ROLE_REFERENCE_WRITE'],
    	REQUIRED_PERMISSIONS_CHALLENGES_ADMIN_DELETE_BUTTON: ['ROLE_REFERENCE_WRITE'],  

    	REQUIRED_PERMISSIONS_CHALLENGE_REFERRALS_ADMIN_ADD_BUTTON: ['ROLE_REFERENCE_WRITE'],
    	REQUIRED_PERMISSIONS_CHALLENGE_REFERRALS_ADMIN_EDIT_BUTTON: ['ROLE_REFERENCE_WRITE'],
    	REQUIRED_PERMISSIONS_CHALLENGE_REFERRALS_ADMIN_DELETE_BUTTON: ['ROLE_REFERENCE_WRITE'],     	

    	/* journal admin */ 
    	
    	// delete associations
    	REQUIRED_PERMISSIONS_TRACK_STEP_ASSOCIATION_ADMIN_DELETE_BUTTON: ['ROLE_REFERENCE_WRITE'],   	
    	REQUIRED_PERMISSIONS_STEP_DETAIL_ASSOCIATION_ADMIN_DELETE_BUTTON: ['ROLE_REFERENCE_WRITE'],
    	
    	// enable drag and drop associations
    	REQUIRED_PERMISSIONS_TRACKS_STEPS_ADMIN_ASSOCIATIONS: ['ROLE_REFERENCE_WRITE'], 
    	REQUIRED_PERMISSIONS_STEP_DETAILS_ADMIN_ASSOCIATIONS: ['ROLE_REFERENCE_WRITE'],
    	
    	REQUIRED_PERMISSIONS_JOURNAL_STEP_ADMIN_ADD_BUTTON: ['ROLE_REFERENCE_WRITE'],
    	REQUIRED_PERMISSIONS_JOURNAL_STEP_ADMIN_EDIT_BUTTON: ['ROLE_REFERENCE_WRITE'],
    	REQUIRED_PERMISSIONS_JOURNAL_STEP_ADMIN_DELETE_BUTTON: ['ROLE_REFERENCE_WRITE'],  

    	REQUIRED_PERMISSIONS_JOURNAL_DETAIL_ADMIN_ADD_BUTTON: ['ROLE_REFERENCE_WRITE'],
    	REQUIRED_PERMISSIONS_JOURNAL_DETAIL_ADMIN_EDIT_BUTTON: ['ROLE_REFERENCE_WRITE'],
    	REQUIRED_PERMISSIONS_JOURNAL_DETAIL_ADMIN_DELETE_BUTTON: ['ROLE_REFERENCE_WRITE'],      	
    	
    	
    	/* PROFILE TOOL */
    	REQUIRED_PERMISSIONS_PROFILE_TOOL: ['ROLE_PERSON_READ'],
    	REQUIRED_PERMISSIONS_PRINT_HISTORY_BUTTON: ['ROLE_PERSON_READ',
    	                                            'ROLE_PERSON_JOURNAL_ENTRY_READ',
    	                                            'ROLE_PERSON_TASK_READ',
    	                                            'ROLE_PERSON_EARLY_ALERT_READ',
    	                                            'ROLE_PERSON_EARLY_ALERT_RESPONSE_READ'],
        
    	REQUIRED_PERMISSIONS_PROFILE_PRINT_CONFIDENTIALITY_AGREEMENT_BUTTON: ['ROLE_REFERENCE_READ'],
    	                                        	
        /* STUDENT INTAKE TOOL */
    	REQUIRED_PERMISSIONS_STUDENTINTAKE_TOOL: ['ROLE_STUDENT_INTAKE_READ'],
    	REQUIRED_PERMISSIONS_STUDENT_INTAKE_SAVE_BUTTON: ['ROLE_STUDENT_INTAKE_WRITE'],
    	REQUIRED_PERMISSIONS_STUDENT_INTAKE_CANCEL_BUTTON: ['ROLE_STUDENT_INTAKE_WRITE'],
    	REQUIRED_PERMISSIONS_STUDENT_INTAKE_CHALLENGE_TAB: ['ROLE_PERSON_CHALLENGE_READ'],
    	 	
    	/* ACTION PLAN TOOL */
    	REQUIRED_PERMISSIONS_ACTIONPLAN_TOOL: ['ROLE_PERSON_READ','ROLE_PERSON_TASK_READ','ROLE_PERSON_GOAL_READ'],
    	REQUIRED_PERMISSIONS_EMAIL_ACTION_PLAN_BUTTON: ['ROLE_PERSON_TASK_READ','ROLE_PERSON_GOAL_READ'],
    	REQUIRED_PERMISSIONS_PRINT_ACTION_PLAN_BUTTON: ['ROLE_PERSON_TASK_READ','ROLE_PERSON_GOAL_READ'],
    	REQUIRED_PERMISSIONS_ACTION_PLAN_TASKS_PANEL: ['ROLE_PERSON_TASK_READ'],
    	REQUIRED_PERMISSIONS_FILTER_TASKS_BY_AUTHENTICATED_USER_CHECKBOX: ['ROLE_PERSON_TASK_READ'],
    	REQUIRED_PERMISSIONS_ADD_TASK_BUTTON: ['ROLE_PERSON_TASK_WRITE'],
    	REQUIRED_PERMISSIONS_EDIT_TASK_BUTTON: ['ROLE_PERSON_TASK_WRITE'],
    	REQUIRED_PERMISSIONS_CLOSE_TASK_BUTTON: ['ROLE_PERSON_TASK_WRITE'],
    	REQUIRED_PERMISSIONS_DELETE_TASK_BUTTON: ['ROLE_PERSON_TASK_DELETE'],
    	REQUIRED_PERMISSIONS_ACTION_PLAN_GOALS_PANEL: ['ROLE_PERSON_TASK_READ'],
    	REQUIRED_PERMISSIONS_ADD_GOAL_BUTTON: ['ROLE_PERSON_GOAL_WRITE'],
    	REQUIRED_PERMISSIONS_EDIT_GOAL_BUTTON: ['ROLE_PERSON_GOAL_WRITE'],
    	REQUIRED_PERMISSIONS_DELETE_GOAL_BUTTON: ['ROLE_PERSON_GOAL_DELETE'],
    	REQUIRED_PERMISSIONS_ACTION_PLAN_STRENGTHS_PANEL: ['ROLE_PERSON_READ'],
    	REQUIRED_PERMISSIONS_ACTION_PLAN_STRENGTHS_FIELD: ['ROLE_PERSON_WRITE'],
    	REQUIRED_PERMISSIONS_SAVE_STRENGTHS_BUTTON: ['ROLE_PERSON_WRITE'],
    	                                            
    	/* JOURNAL TOOL */
    	REQUIRED_PERMISSIONS_JOURNAL_TOOL:['ROLE_PERSON_JOURNAL_ENTRY_READ'],
    	REQUIRED_PERMISSIONS_ADD_JOURNAL_ENTRY_BUTTON: ['ROLE_PERSON_JOURNAL_ENTRY_WRITE'],
    	REQUIRED_PERMISSIONS_EDIT_JOURNAL_ENTRY_BUTTON: ['ROLE_PERSON_JOURNAL_ENTRY_WRITE'],
    	REQUIRED_PERMISSIONS_DELETE_JOURNAL_ENTRY_BUTTON: ['ROLE_PERSON_JOURNAL_ENTRY_DELETE'],
    	
    	/* EARLY ALERT TOOL */
    	REQUIRED_PERMISSIONS_EARLYALERT_TOOL:['ROLE_PERSON_EARLY_ALERT_READ','ROLE_PERSON_EARLY_ALERT_RESPONSE_READ'],
    	REQUIRED_PERMISSIONS_RESPOND_EARLY_ALERT_BUTTON:['ROLE_PERSON_EARLY_ALERT_RESPONSE_WRITE'],
    	REQUIRED_PERMISSIONS_EARLY_ALERT_DETAILS_BUTTON:['ROLE_PERSON_EARLY_ALERT_READ','ROLE_PERSON_EARLY_ALERT_RESPONSE_READ'],
    	
    	/* STUDENT INFORMATION SYSTEM TOOL */
     	REQUIRED_PERMISSIONS_STUDENTINFORMATIONSYSTEM_TOOL:['ROLE_PERSON_READ'],
    
		/* ACCOMMODATION TOOL */
	 	REQUIRED_PERMISSIONS_ACCOMMODATION_TOOL:['ROLE_ACCOMMODATION_READ'],
    	REQUIRED_PERMISSIONS_ACCOMMODATION_SAVE_BUTTON: ['ROLE_ACCOMMODATION_WRITE'],
    	REQUIRED_PERMISSIONS_ACCOMMODATION_CANCEL_BUTTON: ['ROLE_ACCOMMODATION_WRITE'],
    	
    	/* SELF HELP GUIDE ADMIN TOOL */
	 	REQUIRED_PERMISSIONS_SELF_HELP_GUIDE_EDIT_BUTTON:['ROLE_SELF_HELP_GUIDE_ADMIN_READ'],
	 	REQUIRED_PERMISSIONS_SELF_HELP_GUIDE_DELETE_BUTTON:['ROLE_SELF_HELP_GUIDE_ADMIN_WRITE'],
	 	REQUIRED_PERMISSIONS_SELF_HELP_GUIDE_ADD_BUTTON:['ROLE_SELF_HELP_GUIDE_ADMIN_WRITE'],
	 	REQUIRED_PERMISSIONS_SELF_HELP_GUIDE_SAVE_BUTTON:['ROLE_SELF_HELP_GUIDE_ADMIN_WRITE']
    },
    
    /**
     * Returns true if the user has access permissions 
     * for a specified object type.
     * Example use case:
     * 
     *    if ( authenticatedPerson.hasAccess('PRINT_HISTORY_BUTTON') )
     *    {
     *       // User can access the print history button
     *    }
     * 
     */
    hasAccess: function( objectName ){
    	var col = this.get('objectPermissionsCollection');
    	var permission = col.findBy(function(item,key){
    		if(objectName==item.get('name'))
    		{
    			return true;
    		}
    	});
    	return ((permission != null)? permission.get('hasAccess') : false);
    },
 
    /**
     * Creates a MixedCollection of ObjectPermission records containing the names
     * of view items to secure in the system. Each object contains a hasAccess property
     * set to true/false depending on whether or not the required permissions exist in the users
     * granted permissions. The objects reference the names of the static properties in 
     * this class and can be used to determine if view items are available in the system.
     * See the hasAccess method of this class for additional details regarding use of 
     * the ObjectPermissions MixedCollection. 
     */
    setObjectPermissions: function(){
    	var me=this;
    	var col = new Ext.util.MixedCollection();
    	var re = new RegExp(/REQUIRED_PERMISSIONS/);
    	var objectPermission, hasAccess, requiredPermissions;
    	for (prop in Ssp.model.AuthenticatedPerson)
    	{
    		if( prop.search(re) != -1)
    		{
    			requiredPermissions = Ssp.model.AuthenticatedPerson[prop];
    			hasAccess = me.hasRequiredPermissions(requiredPermissions);
    			objectPermission = new Ssp.model.ObjectPermission;
    			objectPermission.set('name',prop.replace('REQUIRED_PERMISSIONS_',""));
    			objectPermission.set('hasAccess',hasAccess);
    			col.add( objectPermission );
    		}	
    	}
    	me.set('objectPermissionsCollection',col);
    },    
    
    /**
     * Determines if a user has access to a provided array of permissions.
     * 
     * @arguments
     *  arrRequiredPermissions - an array of permissions to test against the granted permissions for this user.
     *  
     *  return true if all of the permissions exist in the user's record
     */
    hasRequiredPermissions: function( arrRequiredPermissions ){
        var access = new Array();
    	Ext.Array.each(arrRequiredPermissions,function(permission){
    		if ( this.hasPermission( permission ) == true )
   		   {
   			 access.push( true ); 
   		   }
   	    },this);
    	
        return ((access.length==arrRequiredPermissions.length)? true : false); 
    },
    
    /**
     * Determines if a user has access to the provided permission.
     * Tests against the granted permissions for this user.
     * 
     * @arguments
     *  - permission - a permission
     *  
     *  return true if the permission exists in the user's record
     */
    hasPermission: function( permission ){
   	 return Ext.Array.contains( this.get('permissions'), permission );
    },

    /**
     * Determines if a user has a supplied confidentiality level by id.
     * Tests against the granted confidentiality levels for this user.
     *      
     * @arguments
     *  id - confidentialityLevelId
     *  
     *  return true if the confidentiality level exists in the user's record
     */
    hasConfidentialityLevel: function( id ){
    	var me=this;
    	var levels = new Array();
    	var confidentialityLevels = me.get('confidentialityLevels');
    	Ext.Array.each(confidentialityLevels ,function( item ){
    	   if ( item.id == id )
		   {
			 levels.push(id); 
		   }
	    },me);
   	 	return ((levels.length==0)? false : true);
    },    
    
    /**
     * Apply a filter function to a supplied store
     * to limit the available items to the confidentiality levels within
     * a users authenticated level of confidentiality.
     */
    applyConfidentialityLevelsFilter: function( store ){
		var me=this;
    	var filtersArr = [];
		var filterAuthenticatedFunc;
		filterAuthenticatedFunc = new Ext.util.Filter({
		    filterFn: function(item) {
				return this.hasConfidentialityLevel( item.get('id') ); 
			},
			scope: me
		});
		filtersArr.push( filterAuthenticatedFunc );
		store.filter( filtersArr );
    },
    
    /**
     * Provides an unauthorized access alert message.
     */
    showUnauthorizedAccessAlert: function(){
    	Ext.Msg.alert(this.getUnauthorizedAccessAlertTitle(), this.getUnauthorizedAccessAlertMessage() );
    },
    
    /**
     * Provides a warning if a user attempts to modify data that is critical to system operation.
     */
    showDeveloperRestrictedContentAlert: function(){
		Ext.Msg.alert("WARNING", "Access to this information has been restricted due to the sensitive nature of the information and it's impact on the SSP System. Please see your system administrator if you need to make changes to this information." );
    },
    
    isDeveloperRestrictedContent: function( item ){
		var restricted = false;
		// Restricting Adding Confidentiality Levels in the system
		if (item instanceof Ssp.model.reference.ConfidentialityLevel)
		{
			restricted = true;
		}
		return restricted;
    }
});
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
Ext.define('Ssp.model.Person', {
    extend: 'Ssp.model.AbstractBase',
    fields: [{name: 'photoUrl', type: 'string'},
             {name: 'schoolId', type: 'string'},
    		 {name: 'firstName', type: 'string'},
             {name: 'middleName', type: 'string'},
    		 {name: 'lastName', type: 'string'},
             {name: 'homePhone', type: 'string'},
    		 {name: 'cellPhone', type: 'string'},
             {name: 'workPhone', type: 'string'},
             {name: 'nonLocalAddress', type:'boolean', useNull: true},
    		 {name: 'addressLine1', type: 'string'},
             {name: 'addressLine2', type: 'string'},
    		 {name: 'city', type: 'string'},
             {name: 'state', type: 'string'},
    		 {name: 'zipCode', type: 'string'},
    		 {name: 'alternateAddressInUse', type:'boolean', useNull: true},
    		 {name: 'alternateAddressLine1', type: 'string'},
             {name: 'alternateAddressLine2', type: 'string'},
    		 {name: 'alternateAddressCity', type: 'string'},
             {name: 'alternateAddressState', type: 'string'},
    		 {name: 'alternateAddressZipCode', type: 'string'},
    		 {name: 'alternateAddressCountry', type: 'string'},
             {name: 'primaryEmailAddress', type: 'string'},
    		 {name: 'secondaryEmailAddress', type: 'string'},
             {name: 'birthDate', type: 'date', dateFormat: 'time'},
    		 {name: 'username', type: 'string'},
    		 {name: 'enabled', type: 'boolean', defaultValue: true},
             {name: 'coach', type: 'auto'},
    		 {name: 'strengths', type: 'string'},
    		 {name: 'studentType',type:'auto'},
    		 {name: 'abilityToBenefit', type: 'boolean'},
    		 {name: 'anticipatedStartTerm', type: 'string'},
    		 {name: 'anticipatedStartYear', type: 'string'},
    		 {name: 'actualStartTerm', type: 'string'},
    		 {name: 'actualStartYear', type: 'string'},
    		 {name: 'studentIntakeRequestDate', type: 'date', dateFormat: 'time'},
    		 {name: 'specialServiceGroups', type: 'auto'},
    		 {name: 'referralSources', type: 'auto'},
    		 {name: 'serviceReasons', type: 'auto'},
    		 {name: 'studentIntakeCompleteDate', type: 'date', dateFormat: 'time'},
    		 {name: 'currentProgramStatusName', type: 'auto'},
    		 {name: 'registeredTerms', type: 'string'},
    		 {name: 'paymentStatus', type: 'string'},
             {name: 'activeAlertsCount', type: 'int'},
             {name: 'closedAlertsCount', type: 'int'}],
    		 		 
    getFullName: function(){ 
    	var firstName = this.get('firstName') || "";
    	var middleName = this.get('middleName') || "";
    	var lastName = this.get('lastName') || "";
    	return firstName + " " + middleName + " " + lastName;
    },
    
    getFormattedBirthDate: function(){
    	return Ext.util.Format.date( this.get('birthDate'),'m/d/Y');
    },
    
    getFormattedStudentIntakeRequestDate: function(){
    	return Ext.util.Format.date( this.get('studentIntakeRequestDate'),'m/d/Y');   	
    },
    
    getCoachId: function(){
    	var coach = this.get('coach');
    	return ((coach != null)? coach.id : "");   	
    },

    setCoachId: function( value ){
    	if (value != "")
    	{
        	if ( this.get('coach') != null)
        	{
        		this.set('coach',{"id":value});
        	}    		
    	}
    },    
    
    getCoachFullName: function(){
    	var coach = this.get('coach');
    	return ((coach != null)? coach.firstName + ' ' + coach.lastName : "");   	
    },     

    getCoachWorkPhone: function(){
    	var coach = this.get('coach');
    	return ((coach != null)? coach.workPhone : "");   	
    },    

    getCoachPrimaryEmailAddress: function(){
    	var coach = this.get('coach');
    	return ((coach != null)? coach.primaryEmailAddress : "");   	
    },    

    getCoachOfficeLocation: function(){
    	var coach = this.get('coach');
    	return ((coach != null)? coach.officeLocation : "");   	
    },    
    
    getCoachDepartmentName: function(){
    	var coach = this.get('coach');
    	return ((coach != null)? coach.departmentName : "");   	
    },
    
    getStudentTypeId: function(){
    	var studentType = this.get('studentType');
    	return ((studentType != null)? studentType.id : "");   	
    },
 
    getStudentTypeName: function(){
    	var studentType = this.get('studentType');
    	return ((studentType != null)? studentType.name : "");   	
    },    
    
    setStudentTypeId: function( value ){
    	var me=this;
    	if (value != "")
    	{
        	if ( me.get('studentType') != null)
        	{
        		me.set('studentType',{"id":value});
        	}    		
    	}
    },
    
    getPhotoUrl: function(){
    	var url =  this.get('photoUrl')   	
    	if(url == null || url == "") 	
    		url = Ssp.util.Constants.DEFAULT_NO_STUDENT_PHOTO_URL;

    	return url;
    },    
    
    setPhotoUrl: function( value ){
    	var me=this;
    	if (value != "")
    	{
        	me.set('photoUrl', value);		
    	}
    },
    
    getProgramStatusName: function(){
    	return this.get('currentProgramStatusName')? this.get('currentProgramStatusName') : "";   	
    },

    getEarlyAlertRatio: function() {
        return this.get('activeAlertsCount') + '/' + (this.get('activeAlertsCount') + this.get('closedAlertsCount'));
    },
 
    buildAddress: function(){
    	var me=this;
    	var address = "";
    	address += ((me.get('addressLine1') != null)? me.get('addressLine1') + '<br/>' : "");
    	address += ((me.get('city') != null)? me.get('city') + ', ': "");
    	address += ((me.get('state') != null)? me.get('state') + '<br/>': "");
    	address += ((me.get('zipCode') != null)? me.get('zipCode') : "");	
    	// ensure a full address was defined 
    	if (address.length < 30)
    	{
    		address = "";
    	}    	
    	return address;   	
    },
    
    buildAlternateAddress: function(){
    	var me=this;
    	var alternateAddress = "";
    	alternateAddress += ((me.get('alternateAddressLine1') != null)? me.get('alternateAddressLine1') + '<br/>' : "");
    	alternateAddress += ((me.get('alternateAddressCity') != null)? me.get('alternateAddressCity') : "");
    	alternateAddress += ((me.get('alternateAddressState') != null)? ', ' + me.get('alternateAddressState') + '<br/>': "");
    	alternateAddress += ((me.get('alternateAddressZipCode') != null)? me.get('alternateAddressZipCode') : "<br />");	
    	alternateAddress += ((me.get('alternateAddressCountry') != null)? ', ' + me.get('alternateAddressCountry') : "");	
    	// ensure a full address was defined
    	if (alternateAddress.length < 30)
    	{
    		alternateAddress = "";
    	}
    	return alternateAddress;   	
    },
    
    /*
     * cleans properties that will be unable to be saved if not null
     */ 
    setPropsNullForSave: function( jsonData ){
		delete jsonData.studentIntakeCompleteDate;
		delete jsonData.currentProgramStatusName;

		if ( jsonData.studentType == "")
		{
			jsonData.studentType = null;
		}
		
		if ( jsonData.coach == "")
		{
			jsonData.coach = null;
		}
		
		if( jsonData.serviceReasons == "" )
		{
			jsonData.serviceReasons=null;
		}
		
		if( jsonData.specialServiceGroups == "" )
		{
			jsonData.specialServiceGroups=null;
		}

		if( jsonData.referralSources == "" )
		{
			jsonData.referralSources=null;
		}

		return jsonData;
    },
    
    populateFromExternalData: function( jsonData ){
    	var me=this;
    	me.set('photoUrl',jsonData.photoUrl);
    	me.set('schoolId',jsonData.schoolId);
    	me.set('firstName',jsonData.firstName);
    	me.set('middleName',jsonData.middleName);
    	me.set('lastName', jsonData.lastName);	
    	me.set('anticipatedStartTerm',jsonData.anticipatedStartTerm);
    	me.set('anticipatedStartYear',jsonData.anticipatedStartYear);
    	me.set('homePhone', jsonData.homePhone);
    	me.set('cellPhone', jsonData.cellPhone);
    	me.set('workPhone', jsonData.workPhone);
    	me.set('addressLine1', jsonData.addressLine1);
    	me.set('addressLine2', jsonData.addressLine2);
    	me.set('city', jsonData.city);
    	me.set('state', jsonData.state);
    	me.set('zipCode', jsonData.zipCode);
    	me.set('primaryEmailAddress', jsonData.primaryEmailAddress);
    	me.set('secondaryEmailAddress', jsonData.secondaryEmailAddress);
    	me.set('birthDate', jsonData.birthDate);
    	me.set('username', jsonData.username);
    	me.set('photoUrl', jsonData.photoUrl);
    }
});
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
Ext.define('Ssp.model.PersonAppointment', {
    extend: 'Ssp.model.AbstractBase',
    fields: [{name:'startTime', type: 'date', dateFormat: 'time'},
             {name: 'endTime', type: 'date', dateFormat: 'time'},
             {name: 'attended', type: 'boolean'}],  

	setAppointment: function( startDate, endDate ){
		var me=this;
		if (startDate != null && endDate != null)
		{
		   me.set('startTime', startDate);
		   me.set('endTime', endDate);  		
		}
	}

});
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
Ext.define('Ssp.model.Appointment', {
    extend: 'Ssp.model.AbstractBase',
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	formUtils: 'formRendererUtils'
    },    
    fields: [{name:'appointmentDate', type: 'date', dateFormat: 'time'},
             {name: 'startTime', type: 'date', dateFormat: 'time'},
             {name: 'endTime', type: 'date', dateFormat: 'time'},        
             {name: 'studentIntakeRequested', type: 'boolean'},
             {name: 'intakeEmail', type: 'string'}],        
             
    getStartDate: function(){
		var me=this;
    	var startDate = me.get('appointmentDate');
    	startDate.setHours( me.get('startTime').getHours() );
		startDate.setMinutes( me.get('startTime').getMinutes() );
		return startDate;
    },
    
    getEndDate: function(){
    	var me=this;
    	var endDate = me.get('appointmentDate');
		endDate.setHours( me.get('endTime').getHours() );
		endDate.setMinutes( me.get('endTime').getMinutes() );
		return endDate;    	
    }
});
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
Ext.define('Ssp.model.PersonGoal', {
    extend: 'Ssp.model.AbstractBase',
    fields: [{name:'name',type:'string'},
             {name:'description',type:'string'},
             {name: 'confidentialityLevel',
                 convert: function(value, record) {
                	 var defaultConfidentialityLevelId = Ssp.util.Constants.DEFAULT_SYSTEM_CONFIDENTIALITY_LEVEL_ID;
                	 var obj  = {id:defaultConfidentialityLevelId,name: ''};
                	 if (value != null)
                	 {
                		 if (value != "")
                		 {
                    		 obj.id  = value.id;
                    		 obj.name = value.name;                			 
                		 }
                	 }
   		            return obj;
                 }
   		      }]
});
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
Ext.define('Ssp.model.PersonDocument', {
    extend: 'Ssp.model.AbstractBase',
    fields: [{name:'name',type:'string'},
             {name:'note',type:'string'},
             {name: 'confidentialityLevel',type: 'auto'}]
});
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
Ext.define('Ssp.model.PersonProgramStatus', {
    extend: 'Ssp.model.AbstractBase',
    fields: [{name:'programStatusId', type: 'string'},
             {name: 'effectiveDate', type: 'date', dateFormat: 'time'},
             {name: 'expirationDate', type: 'date', dateFormat: 'time', defaultValue: null},
             {name: 'programStatusChangeReasonId', type: 'string', defaultValue: null}]
});
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
Ext.define('Ssp.model.tool.studentintake.PersonDemographics', {
    extend: 'Ssp.model.AbstractBase',
    fields: [{name: 'personId', type: 'string'},
             {name: 'coachId', type: 'string'},
             {name: 'maritalStatusId', type: 'string'},
             {name: 'citizenshipId', type: 'string'},
             {name: 'ethnicityId', type: 'string'},
             {name: 'militaryAffiliationId', type: 'string'},
             {name: 'veteranStatusId', type: 'string'},
             {name: 'primaryCaregiver', type: 'boolean'},
             {name: 'childCareNeeded', type: 'boolean'},
             {name: 'employed', type: 'boolean'},
             {name: 'numberOfChildren', type: 'int'},	 
             {name: 'countryOfResidence', type: 'string'},
             {name: 'paymentStatus', type: 'string'},
             {name: 'gender', type: 'string'},
             {name: 'countryOfCitizenship', type: 'string'},
             {name: 'childAges', type: 'string'},
             {name: 'placeOfEmployment', type: 'string'},
             {name: 'shift', type: 'string'},
             {name: 'wage', type: 'string'},
             {name: 'totalHoursWorkedPerWeek', type: 'int'},
             {name: 'local', type: 'string'},
             {name: 'childCareArrangementId', type: 'string'}]
});
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
Ext.define('Ssp.model.tool.studentintake.PersonEducationGoal', {
	extend: 'Ssp.model.AbstractBase',
    fields: [{name: 'personId', type: 'string'},
    		 {name: 'educationGoalId', type: 'string'},
    		 {name: 'description', type: 'string'},
    		 {name: 'plannedMajor', type: 'string'},
    		 {name: 'howSureAboutMajor', type: 'int'},
    		 {name: 'careerDecided', type:'boolean'},
    		 {name: 'plannedOccupation', type: 'string'},
    		 {name: 'howSureAboutOccupation', type:'int'},
    		 {name: 'confidentInAbilities', type: 'boolean'},
    		 {name: 'additionalAcademicProgramInformationNeeded', type: 'boolean'}]
});
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
Ext.define('Ssp.model.tool.studentintake.PersonEducationPlan', {
	extend: 'Ssp.model.AbstractBase',
    fields: [{name: 'personId', type: 'string'},
             {name: 'studentStatusId', type: 'string'},
             {name: 'newOrientationComplete', type: 'boolean'},
             {name: 'registeredForClasses', type: 'boolean'},
             {name: 'specialNeeds', type: 'boolean'},
             {name: 'gradeTypicallyEarned', type: 'string'},
             {name: 'collegeDegreeForParents', type: 'boolean'}]
});
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
Ext.define('Ssp.model.tool.actionplan.Task', {
    extend: 'Ssp.model.AbstractBase',
    fields: [{name:'name',type:'string'},
             {name:'description',type:'string'},
             {name:'link',type:'string'},
             {name:'dueDate', type:'date', dateFormat: 'time'},
             {name:'reminderSentDate', type:'date', dateFormat:'time'},
             {name: 'confidentialityLevel',
                 convert: function(value, record) {
                	 var defaultConfidentialityLevelId = Ssp.util.Constants.DEFAULT_SYSTEM_CONFIDENTIALITY_LEVEL_ID;
                	 var obj  = {id:defaultConfidentialityLevelId,name: ''};
                	 if (value != null)
                	 {
                		 if (value != "")
                		 {
                    		 obj.id  = value.id;
                    		 obj.name = value.name;                			 
                		 }
                	 }
   		            return obj;
                 }
   		      },
             {name:'deletable',type:'boolean'},
             //{name:'closableByStudent',type:'boolean'},
             {name:'completed',type:'boolean'},
             {name:'completedDate', type:'date', dateFormat:'time'},
             {name:'challengeId',type:'string'},
             {name:'type',type:'string'},
             {name:'personId',type:'string'}]
});
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
Ext.define('Ssp.model.tool.earlyalert.PersonEarlyAlert', {
    extend: 'Ssp.model.AbstractBase',
    fields: [{name:'personId', type: 'string'},
             {name:'courseName', type:'string'},
             {name:'courseTitle', type:'string'},
             {name:'emailCC', type:'string'},
             {name:'campusId', type:'string'},
             {name:'earlyAlertReasonId', type:'string'},
             {name:'earlyAlertReasonIds', type:'string'},
             {name:'earlyAlertReasonOtherDescription', type:'string'},
             {name:'earlyAlertSuggestionIds', type:'auto'},
             {name:'earlyAlertSuggestionOtherDescription', type:'string'},
             {name:'comment', type:'string'},
             {name:'closedDate', type: 'date', dateFormat: 'time'},
             {name:'closedById', type:'string'},
             {name:'closedByName', type:'string'},
             {name:'sendEmailToStudent', type:'boolean'}]
});
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
Ext.define('Ssp.model.tool.earlyalert.PersonEarlyAlertTree', {
    extend: 'Ssp.model.AbstractBase',
    fields: [{name:'personId', type: 'string'},
             {name:'courseName',type:'string'},
             {name:'courseTitle',type:'string'},
             {name:'emailCC',type:'string'},
             {name:'campusId',type:'string'},
             {name:'earlyAlertReasonId',type:'string'},
             {name:'earlyAlertReasonIds',type:'auto'},
             {name:'earlyAlertReasonOtherDescription',type:'string'},
             {name:'earlyAlertSuggestionIds',type:'auto'},
             {name:'earlyAlertSuggestionOtherDescription',type:'string'},
             {name:'comment',type:'string'},
             {name:'closedDate',type: 'date', dateFormat: 'time'},
             {name:'closedById',type:'string'},
             {name:'closedByName',type:'string'},
             {name:'sendEmailToStudent', type:'boolean'},
             
             /* props for tree manipulation */
             {name:'iconCls',type:'string'},
             {name:'leaf',type:'boolean',defaultValue: false},
             {name:'expanded',type:'boolean'},
             {name:'text',type: 'string'},
             {name:'nodeType',type:'string',defaultValue:'early alert'},
             {name:'gridDisplayDetails', type:'string'},
             {name: 'noOfResponses', type:'string'},
             /* end props for tree manipulation */
             
             {name:'earlyAlertId',type:'string'},
             {name:'earlyAlertOutcomeId',type:'string'},
             {name:'earlyAlertOutcomeOtherDescription',type:'string'},
             {name:'earlyAlertReferralIds',type:'auto'},
             {name:'earlyAlertOutreachIds',type:'auto'},
             {name:'comment',type:'string'}]
});
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
Ext.define('Ssp.model.tool.earlyalert.EarlyAlertResponseGrid', {
    extend: 'Ssp.model.AbstractBase',
    fields: [{name:'personId', type: 'string'},
             {name:'courseName', type:'string'},
             {name:'courseTitle', type:'string'},
             {name:'emailCC', type:'string'},
             {name:'campusId', type:'string'},
             {name:'earlyAlertReasonId', type:'string'},
             {name:'earlyAlertReasonIds', type:'string'},
             {name:'earlyAlertReasonOtherDescription', type:'string'},
             {name:'earlyAlertSuggestionIds', type:'auto'},
             {name:'earlyAlertSuggestionOtherDescription', type:'string'},
             {name:'comment', type:'string'},
             {name:'closedDate', type: 'date', dateFormat: 'time'},
             {name:'closedById', type:'string'}, 
             {name:'sendEmailToStudent', type:'boolean'},
			 
			 {name:'nodeType',type:'string',defaultValue:'early alert'},
             {name:'gridDisplayDetails', type:'string'},
			 
			 {name:'earlyAlertId',type:'string'},
             {name:'earlyAlertOutcomeId',type:'string'},
             {name:'earlyAlertOutcomeOtherDescription',type:'string'},
             {name:'earlyAlertReferralIds',type:'auto'},
             {name:'earlyAlertOutreachIds',type:'auto'},
             {name:'comment',type:'string'}]
});
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
Ext.define('Ssp.model.tool.earlyalert.EarlyAlertResponse', {
    extend: 'Ssp.model.AbstractBase',
    fields: [{name:'earlyAlertId',type:'string'},
             {name:'earlyAlertOutcomeId',type:'string'},
             {name:'earlyAlertOutcomeOtherDescription',type:'string'},
             {name:'earlyAlertReferralIds',type:'auto'},
             {name:'earlyAlertOutreachIds',type:'auto'},
             {name:'comment',type:'string'},
             {name:'closed',type:'boolean'}]
});
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
Ext.define('Ssp.model.tool.journal.JournalEntry', {
    extend: 'Ssp.model.AbstractBase',
    fields: [{name:'comment',type:'string'},
             {name:'entryDate',type: 'date',dateFormat:'time', defaultValue: new Date()},
             {name:'confidentialityLevel',
                 convert: function(value, record) {
                	 var defaultConfidentialityLevelId = Ssp.util.Constants.DEFAULT_SYSTEM_CONFIDENTIALITY_LEVEL_ID;
                	 var obj  = {id:defaultConfidentialityLevelId,name: ''};
                	 if (value != null)
                	 {
                		 if (value != "")
                		 {
                    		 obj.id  = value.id;
                    		 obj.name = value.name;                			 
                		 }
                	 }
   		            return obj;
                 }
   		      },
			 {name:'journalSource', type:'auto'},
			 {name:'journalTrack', type:'auto'},
			 {name:'journalEntryDetails',type:'auto',defaultValue:[]}],
	
	getConfidentialityLevelId: function(){
		return this.get('confidentialityLevel').id;
	},
			 
	addJournalDetail: function( step, detail){
		var stepExists = false;
		Ext.Array.each(this.get("journalEntryDetails"),function(item,index){
			if (item.journalStep.id == step.id){
				// step exists. add the journal detail
				stepExists=true;
				item.journalStepDetails.push(detail);
			}
		});
		if (stepExists==false){
			this.addJournalStep( step, detail );
		}
	},
	
	removeJournalDetail: function( step, detail ){
		Ext.Array.each(this.get("journalEntryDetails"),function(item,index){
			if (item.journalStep.id == step.id){
				Ext.Array.each( item.journalStepDetails, function(innerItem, innerIndex){
					// remove the detail
					if ( innerItem.id == detail.id ){
						Ext.Array.remove(item.journalStepDetails,innerItem);
					}
				},this);
								
				// no details remain, so remove the step
				if (item.journalStepDetails.length<1)
				{
					this.removeJournalStep( step );
				}
			}
		},this);
	},
	
	addJournalStep: function( step, detail ){
		this.get("journalEntryDetails").push( {"journalStep":step, "journalStepDetails": [detail] } );
	},
	
	removeJournalStep: function( step ){
		var journalEntryDetails = this.get("journalEntryDetails");
		Ext.Array.each(journalEntryDetails,function(item,index){
			if (item.journalStep.id == step.id){
				Ext.Array.remove(journalEntryDetails,item);
			}
		});
	},
	
	removeAllJournalEntryDetails: function(){
		this.set('journalEntryDetails',[]);
	},
	
	getGroupedDetails: function(){
		var groupedDetails=[];
		var journalEntryDetails = this.get('journalEntryDetails');
		Ext.Array.each(journalEntryDetails,function(item,index){
			var stepName=item.journalStep.name;
    		var details = item.journalStepDetails;
    		Ext.Array.each(details,function(detail,index){
    			detail.group=stepName;
    			groupedDetails.push( detail );
    		},this);
    	},this);
		return groupedDetails;
	},
	
	/**
	 * Used to clean the group property from journalEntryDetails,
	 * so that they save correctly. This method should only be
	 * used against json data that is ready to be saved and not
	 * against this object itself.
	 */
	clearGroupedDetails: function( journalEntryDetails ){
		Ext.Array.each(journalEntryDetails,function(item,index){
    		var details = item.journalStepDetails;
    		Ext.Array.each(details,function(detail,index){
    			delete detail.group;
    		},this);
    	},this);
		return journalEntryDetails;		
	}
});
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
Ext.define('Ssp.model.reference.Challenge', {
    extend: 'Ssp.model.reference.AbstractReference',
    fields: [{name: 'tags', type:'string'},
             {name: 'showInStudentIntake', type: 'boolean'},
             {name: 'showInSelfHelpSearch', type: 'boolean'},
             {name: 'selfHelpGuideQuestion', type: 'string'},
             {name: 'selfHelpGuideDescription', type: 'string'},
             {name: 'defaultConfidentialityLevelId', type: 'string'},
             {name: 'referralCount', type: 'int'}]
});

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
Ext.define('Ssp.model.reference.ChallengeCategory', {
    extend: 'Ssp.model.reference.AbstractReference',
    fields: [],
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + 'challengeCategory/'});
    }
});
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
Ext.define('Ssp.model.reference.ChallengeReferral', {
    extend: 'Ssp.model.reference.AbstractReference',
    fields: [{name: 'publicDescription', type: 'string'},
             {name:'link',type:'string'},
             {name: 'showInSelfHelpGuide', type: 'boolean'}]
});
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
Ext.define('Ssp.model.reference.JournalTrack', {
	extend: 'Ssp.model.reference.AbstractReference',
    fields: []
});
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
Ext.define('Ssp.model.reference.JournalStep', {
	extend: 'Ssp.model.reference.AbstractReference',
    fields: [{name:'usedForTransition',type:'boolean'}]
});
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
Ext.define('Ssp.model.reference.JournalStepDetail', {
	extend: 'Ssp.model.reference.AbstractReference',
    fields: []
});
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
Ext.define('Ssp.model.reference.ConfidentialityLevel', {
    extend: 'Ssp.model.reference.AbstractReference',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties'
    },
    
    fields: [{name:'acronym',type:'string',defaultValue:'DEFAULT'}] ,

	constructor: function(){
		Ext.apply(this.getProxy(), 
				{ 
			url: this.apiProperties.createUrl( this.apiProperties.getItemUrl('confidentialityLevel') )
			    }
		);
		return this.callParent(arguments);
	},

    proxy: {
		type: 'rest',
		url: '',
		actionMethods: {
			create: "POST", 
			read: "GET", 
			update: "PUT", 
			destroy: "DELETE"
		},
		reader: {
			type: 'json',
			successProperty: 'success',
			message: 'message'
		},
	    writer: {
	        type: 'json',
	        successProperty: 'success'
	    }
	}
});
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
Ext.define('Ssp.model.reference.ConfidentialityDisclosureAgreement', {
    extend: 'Ssp.model.reference.AbstractReference',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties'
    },    
    fields: [{name:'text',type:'string'}],

	constructor: function(){
		Ext.apply(this.getProxy(), 
				{ 
			url: this.apiProperties.createUrl( this.apiProperties.getItemUrl('confidentialityDisclosureAgreement') )
			    }
		);
		return this.callParent(arguments);
	}, 	
	
	proxy: {
		type: 'rest',
		url: '',
		actionMethods: {
			create: "POST", 
			read: "GET", 
			update: "PUT",
			destroy: "DELETE"
		},
		reader: {
			type: 'json'
		},
	    writer: {
	        type: 'json',
	        successProperty: 'success'
	    }
	}
});
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
Ext.define('Ssp.store.Coaches', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.Coach',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
        apiProperties: 'apiProperties'
    },
	constructor: function(){
		var me=this;
		Ext.apply(me, {
						proxy: me.apiProperties.getProxy(me.apiProperties.getItemUrl('personCoach')+'/?sort=lastName'),
						autoLoad: false,
						pageSize: 1000, // max allowed server-side
						params : {
							page : 0,
							start : 0,
							limit : 1000 // max allowed server-side
						}
					});
		return me.callParent(arguments);
	}
});
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
Ext.define('Ssp.store.Goals', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.PersonGoal',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties'
    },    
	constructor: function(){
		Ext.apply(this, { proxy: this.apiProperties.getProxy( this.apiProperties.getItemUrl('personGoal') ),
						  autoLoad: false });
		return this.callParent(arguments);
	},
});
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
Ext.define('Ssp.store.JournalEntries', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.tool.journal.JournalEntry',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	currentPerson: 'currentPerson',
    	apiProperties: 'apiProperties'
    },    
	constructor: function(){
		Ext.apply(this, { proxy: this.apiProperties.getProxy( this.apiProperties.getItemUrl('personJournalEntry') ),
						  autoLoad: false });
		return this.callParent(arguments);
	},
});
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
Ext.define('Ssp.store.EarlyAlerts', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.tool.earlyalert.PersonEarlyAlert',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties'
    },    
	constructor: function(){
		Ext.apply(this, { proxy: this.apiProperties.getProxy( this.apiProperties.getItemUrl('personEarlyAlert') ),
						  autoLoad: false });
		return this.callParent(arguments);
	}
});
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
Ext.define('Ssp.store.Documents', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.PersonDocument',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties'
    },    
	constructor: function(){
		Ext.apply(this, { proxy: this.apiProperties.getProxy( this.apiProperties.getItemUrl('personDocument') ),
						  autoLoad: false });
		return this.callParent(arguments);
	}
});
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
Ext.define('Ssp.store.reference.AnticipatedStartTerms', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.reference.AbstractReference',
    autoLoad: false,
    constructor: function(){
		return this.callParent(arguments);
    },
    data: [{ name: "FA", description: "FA" },
           { name: "WI", description: "WI" },
           { name: "SP", description: "SP" },
           { name: "SU", description: "SU" }]
});
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
Ext.define('Ssp.store.reference.Challenges', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.Challenge',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('challenge')});
    }
});
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
Ext.define('Ssp.store.reference.ChallengeCategories', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.ChallengeCategory',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('category')});
    }
});
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
Ext.define('Ssp.store.reference.ChallengeReferrals', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.ChallengeReferral',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('challengeReferral')});
    }
});
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
Ext.define('Ssp.store.reference.ConfidentialityLevels', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.ConfidentialityLevel',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('confidentialityLevel')});
    }
});
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
Ext.define('Ssp.store.reference.Genders', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.reference.Gender',
	autoLoad: false
});
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
Ext.define('Ssp.store.reference.JournalStepDetails', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.JournalStepDetail',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('journalStepDetail')});
    }
});
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
Ext.define('Ssp.store.reference.JournalSteps', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.JournalStep',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('journalStep')});
    }
});
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
Ext.define('Ssp.store.reference.JournalTracks', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.JournalTrack',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('journalTrack')});
    }
});
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
Ext.define('Ssp.store.People', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.Person',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
        apiProperties: 'apiProperties'
    },
	constructor: function(){
		Ext.apply(this, {
							proxy: this.apiProperties.getProxy(this.apiProperties.getItemUrl('person')),
							autoLoad: false
						});
		return this.callParent(arguments);
	}
});
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
Ext.define('Ssp.store.reference.States', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.reference.State',
	autoLoad: false
});
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
Ext.define('Ssp.store.Students', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.Person',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
        apiProperties: 'apiProperties'
    },
	constructor: function(){
		Ext.apply(this, {
							proxy: this.apiProperties.getProxy(this.apiProperties.getItemUrl('person')),
							autoLoad: false
						});
		return this.callParent(arguments);
	}
});
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
Ext.define('Ssp.store.Tools', {
    extend: 'Ext.data.Store',
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	authenticatedPerson: 'authenticatedPerson'
    },
    model: 'Ssp.model.Tool',
    autoLoad: false,
    
    constructor: function(){

        var me=this;
        me.callParent( arguments );

        var sspTools = [{ group:'beta', name: "Main", toolType: "profile", active: true },
            { group:'beta', name: "Intake", toolType: "studentintake", active: true },
            { group:'beta', name: "Action Plan", toolType: "actionplan", active: true },
            { group:'beta', name: "Journal", toolType: "journal", active: true },
            { group:'rc1', name: "Early Alert", toolType: "earlyalert", active: true },
            { group:'rc1', name: "Accommodation", toolType: "accommodation", active: true }

            /*
             { group:'rc1', name: "SIS", toolType: "StudentInformationSystem", active: true },
             { group:'rc1', name: "Documents", toolType: "StudentDocuments", active: false },
             { group:'rc1', name: "Displaced Workers", toolType: "DisplacedWorker", active: false },
             { group:'rc1', name: "Student Success", toolType: "StudentSuccess", active: false }
             */
        ];

        // set the model
        me.loadData( me.applySecurity( sspTools ) );

        return me;

    },
    
    applySecurity: function( tools ){
    	var me=this;
    	var sspSecureTools = [];
    	
    	Ext.Array.each( tools, function( tool, index){
    		var toolSecurityIdentifier = tool.toolType.toUpperCase() + '_TOOL';
    		if ( me.authenticatedPerson.hasAccess( toolSecurityIdentifier ) )
    		{
    			sspSecureTools.push( tool );
    		}
    	});
    	
    	return sspSecureTools;
    }
           
    //groupField: 'group'
});
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
Ext.define('Ssp.store.reference.YesNo', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.reference.AbstractReference',
    constructor: function(){
    	var items= [{id: "Y", name: "Yes"},
    			    {id: "N", name: "No"}];
    	
    	Ext.apply(this,{
    		items: items 
    	});
    	Ext.apply(this, {autoLoad: false});
		this.callParent(arguments);
    }
});
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
Ext.define('Ssp.controller.admin.crg.AssociateChallengeCategoriesAdminViewController', {
	extend: 'Ssp.controller.admin.AdminItemAssociationViewController',
    config: {
        associatedItemType: 'challenge',
        parentItemType: 'category',
        parentIdAttribute: 'categoryId',
        associatedItemIdAttribute: 'challengeId'
    },
	constructor: function(){
		this.callParent(arguments);

		this.clear();
		this.getParentItems();
		
		return this;
	}
});
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
Ext.define('Ssp.view.admin.forms.crg.AssociateChallengeCategoriesAdmin', {
	extend: 'Ext.tree.Panel',
	alias : 'widget.displaychallengecategoriesadmin',
	title: 'Challenge Category Associations',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.crg.AssociateChallengeCategoriesAdminViewController',
    inject: {
    	authenticatedPerson: 'authenticatedPerson',
        store: 'treeStore'
    },
	height: '100%',
	width: '100%',
    initComponent: function(){
    	var me=this;
    	Ext.apply(me,
    			{
    		     singleExpand: true,
    			 store: me.store,
    			 useArrows: true,
    			 rootVisible: false,
    			 singleExpand: true,
			     viewConfig: {
			        plugins: {
			            ptype: 'treeviewdragdrop',
			            dropGroup: 'gridtotree',
			            enableDrop: true
			        }
			     },
    			 dockedItems: [{
     				        dock: 'top',
     				        xtype: 'toolbar',
     				        items: [{
     				            tooltip: 'Delete selected association',
     				            text: 'Delete Associations',
     				            hidden: !me.authenticatedPerson.hasAccess('CHALLENGE_CATEGORIES_ASSOCIATION_ADMIN_DELETE_BUTTON'),
     				            xtype: 'button',
     				            itemId: 'deleteAssociationButton'
     				        }]
     		    	    }] 
     		       	
    	});
    	
    	return me.callParent(arguments);
    }
});
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
Ext.define('Ssp.controller.admin.crg.AssociateChallengeReferralsAdminViewController', {
	extend: 'Ssp.controller.admin.AdminItemAssociationViewController',
    config: {
        associatedItemType: 'challengeReferral',
        parentItemType: 'challenge',
        parentIdAttribute: 'challengeId',
        associatedItemIdAttribute: 'challengeReferralId'
    },
	constructor: function(){
		var me=this;
		me.callParent(arguments);
		me.clear();
		me.getParentItems();		
		return me;
	}	
});
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
Ext.define('Ssp.view.admin.forms.crg.AssociateChallengeReferralsAdmin', {
	extend: 'Ext.tree.Panel',
	alias : 'widget.displaychallengereferralsadmin',
	title: 'Challenge Referral Associations',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.crg.AssociateChallengeReferralsAdminViewController',
    inject: {
    	authenticatedPerson: 'authenticatedPerson',
        store: 'treeStore'
    },
    viewConfig: {
        plugins: {
            ptype: 'treeviewdragdrop'
        }
    },
	height: '100%',
	width: '100%',
    initComponent: function(){
    	var me=this;
    	Ext.apply(me,
    			{
    		     autoScroll: true,
    			 store: me.store,
    			 useArrows: true,
    			 rootVisible: false,
    			 singleExpand: true,
			     viewConfig: {
				        plugins: {
				            ptype: 'treeviewdragdrop',
				            dropGroup: 'gridtotree',
				            enableDrop: true
				        }
				 },    			 
    		     dockedItems: [{
     				        dock: 'top',
     				        xtype: 'toolbar',
     				        items: [{
     				            tooltip: 'Delete selected association',
     				            text: 'Delete Associations',
     				            xtype: 'button',
     				            hidden: !me.authenticatedPerson.hasAccess('CHALLENGE_REFERRALS_ASSOCIATION_ADMIN_DELETE_BUTTON'),
     				            itemId: 'deleteAssociationButton'
     				        }]
     		    	    }]
     		       	
    	});
    	
    	return me.callParent(arguments);
    }
});
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
Ext.define('Ssp.controller.admin.journal.AssociateTrackStepsAdminViewController', {
	extend: 'Ssp.controller.admin.AdminItemAssociationViewController',
    config: {
        associatedItemType: 'journalStep',
        parentItemType: 'journalTrack',
        parentIdAttribute: 'journalTrackId',
        associatedItemIdAttribute: 'journalStepId'
    },
	constructor: function(){
		this.callParent(arguments);
		
		this.clear();
		this.getParentItems();
		
		return this;
	}	
});
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
Ext.define('Ssp.view.admin.forms.journal.AssociateTrackStepsAdmin', {
	extend: 'Ext.tree.Panel',
	alias : 'widget.associatetrackstepsadmin',
	title: 'Track Steps Associations',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.journal.AssociateTrackStepsAdminViewController',
    inject: {
    	authenticatedPerson: 'authenticatedPerson',
        store: 'treeStore'
    },
	height: '100%',
	width: '100%',
    initComponent: function(){
    	var me=this;
    	Ext.apply(me,
    			{
    			 store: me.store,
    			 useArrows: true,
    			 rootVisible: false,
    			 singleExpand: true,
			     viewConfig: {
			        plugins: {
			            ptype: 'treeviewdragdrop',
			            dropGroup: 'gridtotree',
			            enableDrop: true
			        }
			     },
    			 dockedItems: [{
     				        dock: 'top',
     				        xtype: 'toolbar',
     				        items: [{
     				            tooltip: 'Delete selected association',
     				            text: 'Delete Associations',
     				            xtype: 'button',
     				            hidden: !me.authenticatedPerson.hasAccess('TRACK_STEP_ASSOCIATION_ADMIN_DELETE_BUTTON'),
     				            itemId: 'deleteAssociationButton'
     				        }]
     		    	    }] 
     		       	
    	});
    	
    	return me.callParent(arguments);
    }
});
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
Ext.define('Ssp.controller.admin.journal.AssociateStepDetailsAdminViewController', {
	extend: 'Ssp.controller.admin.AdminItemAssociationViewController',
    config: {
        associatedItemType: 'journalStepDetail',
        parentItemType: 'journalStep',
        parentIdAttribute: 'journalStepId',
        associatedItemIdAttribute: 'journalStepDetailId'
    },
	constructor: function(){
		var me=this;
		me.callParent(arguments);
		me.clear();
		me.getParentItems();	
		return me;
	}
});
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
Ext.define('Ssp.view.admin.forms.journal.AssociateStepDetailsAdmin', {
	extend: 'Ext.tree.Panel',
	alias : 'widget.associatestepdetailsadmin',
	title: 'Step Details Associations',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.journal.AssociateStepDetailsAdminViewController',
    inject: {
    	authenticatedPerson: 'authenticatedPerson',
        store: 'treeStore'
    },
    viewConfig: {
        plugins: {
            ptype: 'treeviewdragdrop'
        }
    },
	height: '100%',
	width: '100%',
    initComponent: function(){
    	var me=this;
    	Ext.apply(me,
    			{
    		     autoScroll: true,
    			 store: me.store,
    			 useArrows: true,
    			 rootVisible: false,
    			 singleExpand: true,
			     viewConfig: {
				        plugins: {
				            ptype: 'treeviewdragdrop',
				            dropGroup: 'gridtotree',
				            enableDrop: true
				        }
				 },    			 
    		     dockedItems: [{
     				        dock: 'top',
     				        xtype: 'toolbar',
     				        items: [{
     				            tooltip: 'Delete selected association',
     				            text: 'Delete Associations',
     				            xtype: 'button',
     				            hidden: !me.authenticatedPerson.hasAccess('STEP_DETAIL_ASSOCIATION_ADMIN_DELETE_BUTTON'),
     				            itemId: 'deleteAssociationButton'
     				        }]
     		    	    }]
     		       	
    	});
    	
    	return me.callParent(arguments);
    }
});
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
Ext.define('Ssp.model.tool.actionplan.TaskGroup', {
    extend: 'Ssp.model.tool.actionplan.Task',
    fields: [{name:'group',type:'string'}]
});
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
Ext.define('Ssp.store.Tasks', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.tool.actionplan.TaskGroup',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties'
    },    
	constructor: function(){
		Ext.apply(this, { proxy: this.apiProperties.getProxy( this.apiProperties.getItemUrl('personTaskGroup') ),
						  autoLoad: false });
		return this.callParent(arguments);
	},
	
	groupField: 'group'
});
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
Ext.define('Ssp.model.reference.EmploymentShift', {
	extend: 'Ssp.model.reference.AbstractReference',
    fields: ['code',
             'title']  
});
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
Ext.define('Ssp.store.reference.EmploymentShifts', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.reference.EmploymentShift',
	autoLoad: false
});
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
Ext.define('Ssp.model.reference.Campus', {
    extend: 'Ssp.model.reference.AbstractReference',
    fields: [{name:'earlyAlertCoordinatorId', type: 'string'}]
});
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
Ext.define('Ssp.model.reference.CampusEarlyAlertRouting', {
    extend: 'Ssp.model.AbstractBase',
    fields: [{name:'earlyAlertReasonId',type:'string'},
             {name:'person',type:'auto'},
             {name:'groupName',type:'string'},
             {name:'groupEmail',type:'string'}],
             
    getPersonFullName: function(){
    	var me=this;
    	var person;
    	var fullName = "";
    	if (me.get('person') != null)
    	{
    		person = me.get('person');
    		fullName = person.firstName + ' ' + person.lastName;
    	}
    	return fullName;
    }
});
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
Ext.define('Ssp.model.reference.ChildCareArrangement', {
	extend: 'Ssp.model.reference.AbstractReference',
    fields: []
});
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
Ext.define('Ssp.model.reference.Citizenship', {
	extend: 'Ssp.model.reference.AbstractReference',
    fields: []
});
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
Ext.define('Ssp.model.reference.EarlyAlertOutcome', {
	extend: 'Ssp.model.reference.AbstractReference',
    fields: [{name:'sortOrder',type:'int'}]
});
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
Ext.define('Ssp.model.reference.EarlyAlertOutreach', {
	extend: 'Ssp.model.reference.AbstractReference',
    fields: [{name:'sortOrder',type:'int'}]
});
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
Ext.define('Ssp.model.reference.EarlyAlertReason', {
	extend: 'Ssp.model.reference.AbstractReference',
    fields: [{name:'sortOrder',type:'int'}]
});
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
Ext.define('Ssp.model.reference.EarlyAlertReferral', {
	extend: 'Ssp.model.reference.AbstractReference',
    fields: [{name:'acronym',type:'string'},
             {name:'sortOrder',type:'int'}]
});
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
Ext.define('Ssp.model.reference.EarlyAlertSuggestion', {
	extend: 'Ssp.model.reference.AbstractReference',
    fields: [{name:'sortOrder',type:'int'}]
});
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
Ext.define('Ssp.model.reference.Ethnicity', {
	extend: 'Ssp.model.reference.AbstractReference',
    fields: []
});
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
Ext.define('Ssp.model.reference.FundingSource', {
	extend: 'Ssp.model.reference.AbstractReference',
    fields: []
});
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
Ext.define('Ssp.model.reference.JournalSource', {
	extend: 'Ssp.model.reference.AbstractReference',
    fields: []
});
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
Ext.define('Ssp.model.reference.MaritalStatus', {
	extend: 'Ssp.model.reference.AbstractReference',
    fields: []
});
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
Ext.define('Ssp.model.reference.ProgramStatus', {
	extend: 'Ssp.model.reference.AbstractReference',
    fields: [{name:'programStatusChangeReasonRequired',type:'boolean'}]
});
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
Ext.define('Ssp.model.reference.ProgramStatusChangeReason', {
	extend: 'Ssp.model.reference.AbstractReference',
    fields: []
});
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
Ext.define('Ssp.model.reference.ReferralSource', {
	extend: 'Ssp.model.reference.AbstractReference',
    fields: []
});
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
Ext.define('Ssp.model.reference.ServiceReason', {
	extend: 'Ssp.model.reference.AbstractReference',
    fields: []
});
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
Ext.define('Ssp.model.reference.SpecialServiceGroup', {
	extend: 'Ssp.model.reference.AbstractReference',
    fields: []
});
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
Ext.define('Ssp.model.reference.StudentStatus', {
	extend: 'Ssp.model.reference.AbstractReference',
    fields: []
});
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
Ext.define('Ssp.model.reference.StudentType', {
	extend: 'Ssp.model.reference.AbstractReference',
    fields: [{name:'requireInitialAppointment',type:'boolean'}]
});
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
Ext.define('Ssp.model.reference.VeteranStatus', {
	extend: 'Ssp.model.reference.AbstractReference',
    fields: []
});
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
Ext.define('Ssp.store.reference.Campuses', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.Campus',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('campus')});
    }
});
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
Ext.define('Ssp.store.reference.CampusEarlyAlertRoutings', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.CampusEarlyAlertRouting',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('campusEarlyAlertRouting')});
    }
});
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
Ext.define('Ssp.store.reference.ChildCareArrangements', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.ChildCareArrangement',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('childCareArrangement')});
    }
});
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
Ext.define('Ssp.store.reference.Citizenships', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.Citizenship',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('citizenship')});
    }
});
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
Ext.define('Ssp.store.reference.EarlyAlertOutcomes', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.EarlyAlertOutcome',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('earlyAlertOutcome')});
    }
});
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
Ext.define('Ssp.store.reference.EarlyAlertOutreaches', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.EarlyAlertOutreach',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('earlyAlertOutreach')});
    }
});
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
Ext.define('Ssp.store.reference.EarlyAlertReasons', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.EarlyAlertReason',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('earlyAlertReason')});
    }
});
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
Ext.define('Ssp.store.reference.EarlyAlertReferrals', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.EarlyAlertReferral',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('earlyAlertReferral')});
    }
});
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
Ext.define('Ssp.store.reference.EarlyAlertSuggestions', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.EarlyAlertSuggestion',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('earlyAlertSuggestion')});
    }
});
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
Ext.define('Ssp.store.reference.Ethnicities', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.Ethnicity',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('ethnicity')});
    }
});
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
Ext.define('Ssp.store.reference.FundingSources', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.FundingSource',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('fundingSource')});
    }
});
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
Ext.define('Ssp.store.reference.JournalSources', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.JournalSource',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('journalSource')});
    }
});
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
Ext.define('Ssp.store.reference.MaritalStatuses', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.MaritalStatus',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('maritalStatus')});
    }
});
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
Ext.define('Ssp.store.reference.ProgramStatuses', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.ProgramStatus',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('programStatus')});
    }
});
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
Ext.define('Ssp.store.reference.ProgramStatusChangeReasons', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.ProgramStatusChangeReason',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('programStatusChangeReason')});
    }
});
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
Ext.define('Ssp.store.reference.ReferralSources', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.ReferralSource',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('referralSource')});   
    }
});
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
Ext.define('Ssp.store.reference.ServiceReasons', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.ServiceReason',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('serviceReason')});
    }
});
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
Ext.define('Ssp.store.reference.SpecialServiceGroups', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.SpecialServiceGroup',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('specialServiceGroup')});
    }
});
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
Ext.define('Ssp.store.reference.StudentStatuses', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.StudentStatus',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('studentStatus')});
    }
});
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
Ext.define('Ssp.store.reference.StudentTypes', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.StudentType',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('studentType')});
    }
});
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
Ext.define('Ssp.store.reference.VeteranStatuses', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.VeteranStatus',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('veteranStatus')});
    }
});
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
Ext.define('Ssp.controller.tool.studentintake.EducationGoalsViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	formUtils: 'formRendererUtils',
    	model: 'currentStudentIntake',
    	sspConfig: 'sspConfig'
    },
    control: {
    	careerDecidedCheckOn: '#careerDecidedCheckOn',
    	careerDecidedCheckOff: '#careerDecidedCheckOff',
    	confidentInAbilitiesCheckOn: '#confidentInAbilitiesCheckOn',
    	confidentInAbilitiesCheckOff: '#confidentInAbilitiesCheckOff',
    	additionalAcademicProgramInformationNeededCheckOn: '#additionalAcademicProgramInformationNeededCheckOn',
    	additionalAcademicProgramInformationNeededCheckOff: '#additionalAcademicProgramInformationNeededCheckOff'
    },
	init: function() {
		var me=this;
		var personEducationGoal = me.model.get('personEducationGoal');
		var careerDecided = me.model.get('personEducationGoal').get('careerDecided')
		var confidentInAbilities = me.model.get('personEducationGoal').get('confidentInAbilities');
		var additionalAcademicProgramInformationNeeded = me.model.get('personEducationGoal').get('additionalAcademicProgramInformationNeeded');
		
		if ( personEducationGoal != null && personEducationGoal != undefined )
		{
			me.getCareerDecidedCheckOn().setValue( careerDecided );
			me.getCareerDecidedCheckOff().setValue( !careerDecided );
			
			me.getConfidentInAbilitiesCheckOn().setValue( confidentInAbilities );
			me.getConfidentInAbilitiesCheckOff().setValue( !confidentInAbilities );
			
			me.getAdditionalAcademicProgramInformationNeededCheckOn().setValue( additionalAcademicProgramInformationNeeded );
			me.getAdditionalAcademicProgramInformationNeededCheckOff().setValue( !additionalAcademicProgramInformationNeeded );
		}		

		return me.callParent(arguments);
    }
});
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
Ext.define("Ssp.view.tools.studentintake.EducationGoals", {
	extend: "Ext.form.Panel",
	alias: 'widget.studentintakeeducationgoals',
	id : "StudentIntakeEducationGoals",   
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.studentintake.EducationGoalsViewController',
	width: "100%",
    height: "100%", 
    initComponent: function() {
    	var me=this;
		Ext.apply(me, 
				{
					autoScroll: true,
				    bodyPadding: 5,
				    border: 0,
				    layout: 'anchor',
				    defaults: {
				        anchor: '100%'
				    },
				    fieldDefaults: {
				        msgTarget: 'side',
				        labelAlign: 'right',
				        labelWidth: 200
				    },
				    defaultType: "radiogroup",
				    items: [{
				            xtype: "fieldset",
				            border: 0,
				            title: "",
				            id: 'StudentIntakeEducationGoalsFieldSet',
				            defaultType: "textfield",
				            defaults: {
				                anchor: "95%"
				            },
				       items: [{
				            xtype: "radiogroup",
				            id: 'StudentIntakeEducationGoalsRadioGroup',
				            fieldLabel: "Education/Career Goal",
				            allowBlank: true,
				            columns: 1
				        }]
				    },{
			            xtype: "fieldset",
			            border: 0,
			            title: '',
			            defaultType: "textfield",
			            defaults: {
			                anchor: "95%"
			            },
			       items: [{
				        fieldLabel: 'What is your planned major?',
				        name: 'plannedMajor'
				    },{
			            xtype: "radiogroup",
			            fieldLabel: "How sure are you about your major?",
			            columns: 1,
			            items: [
			                {boxLabel: "Very Unsure", name: "howSureAboutMajor", inputValue: "1"},
			                {boxLabel: "", name: "howSureAboutMajor", inputValue: "2"},
			                {boxLabel: "", name: "howSureAboutMajor", inputValue: "3"},
			                {boxLabel: "", name: "howSureAboutMajor", inputValue: "4"},
			                {boxLabel: "Very Sure", name: "howSureAboutMajor", inputValue: "5"}
			        		]
			        },{
				        xtype: "radiogroup",
				        fieldLabel: 'Have you decided on a career/occupation?',
				        columns: 1,
				        itemId: 'careerDecided',
				        items: [
				            {boxLabel: "Yes", itemId: "careerDecidedCheckOn", name: "careerDecided", inputValue:"true"},
				            {boxLabel: "No", itemId: "careerDecidedCheckOff", name: "careerDecided", inputValue:"false"}]
					},{
				        fieldLabel: 'What is your planned occupation?',
				        name: 'plannedOccupation'
				    },{
			            xtype: "radiogroup",
			            fieldLabel: "How sure are you about your occupation?",
			            columns: 1,
			            items: [
			                {boxLabel: "Very Unsure", name: "howSureAboutOccupation", inputValue: "1"},
			                {boxLabel: "", name: "howSureAboutOccupation", inputValue: "2"},
			                {boxLabel: "", name: "howSureAboutOccupation", inputValue: "3"},
			                {boxLabel: "", name: "howSureAboutOccupation", inputValue: "4"},
			                {boxLabel: "Very Sure", name: "howSureAboutOccupation", inputValue: "5"}
			        		]
			        },{
				        xtype: 'radiogroup',
				        fieldLabel: 'Are you confident your abilities are compatible with the career field?',
				        columns: 1,
				        itemId: 'confidentInAbilities',
				        items: [
				            {boxLabel: "Yes", itemId: "confidentInAbilitiesCheckOn", name: "confidentInAbilities", inputValue:"true"},
				            {boxLabel: "No", itemId: "confidentInAbilitiesCheckOff", name: "confidentInAbilities", inputValue:"false"}]
					},{
				        xtype: "radiogroup",
				        fieldLabel: 'Do you need additional information about which academic programs may lead to a future career?',
				        columns: 1,
				        itemId: 'additionalAcademicProgramInformationNeeded',
				        items: [
				            {boxLabel: "Yes", itemId: "additionalAcademicProgramInformationNeededCheckOn", name: "additionalAcademicProgramInformationNeeded", inputValue:"true"},
				            {boxLabel: "No", itemId: "additionalAcademicProgramInformationNeededCheckOff", name: "additionalAcademicProgramInformationNeeded", inputValue:"false"}]
					}]
				    
				    }]
				});
		
		return me.callParent(arguments);
	}	
});
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
Ext.define('Ssp.controller.admin.shg.EditSelfHelpGuideAvailableChallengesAdminViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	store: 'challengesStore',
    	formUtils: 'formRendererUtils',
    	model: 'currentChallenge'
    },
    config: {
    	containerToLoadInto: 'adminforms',
    	formToDisplay: 'editchallenge'
    },      
	init: function() {
		var me=this;
		
		me.formUtils.reconfigureGridPanel( me.getView(), me.store);
		me.store.load();
		
		return me.callParent(arguments);
    }  ,
	
	displayEditor: function(){
		var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {});
	}
});
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
Ext.define('Ssp.controller.admin.shg.EditSelfHelpGuideChallengesViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	formUtils: 'formRendererUtils',
    	parent: 'currentSelfHelpGuide',
    	store: 'selfHelpGuideQuestionsStore',
    	model: 'currentSelfHelpGuideQuestions'
    },
    config: {
    	formToDisplay: 'selfhelpguideadmin',
    	containerToLoadInto: 'adminforms'
    },
    control: {
    	'deleteChallengeButton': {
			click: 'deleteConfirmation'
		},
       'gridView': {
    	   refresh: 'onRefresh',
    	   drop: 'onDrop'
        },
    },
    
	init: function() {
		this.formUtils.reconfigureGridPanel( this.getView(), this.store);
		this.store.load({
			params: {selfReferenceGuideId:this.parent.data.id}
		});
		return this.callParent(arguments);
    },
    onRefresh: function(node, data, dropRec, dropPosition)
    {

    },
    onDrop:function(node, data, overModel, dropPosition, options)
	{   
    	var me=this;
		//Since the panels use two different models we need to remove the record from 
		//the store and recreate it so it conforms to the appropriate model
		var badRecord = me.store.findRecord('id', data.records[0].data.id);
        var newQuestionNumber = me.store.indexOf(badRecord) + 1;
        var newRecord = new Ssp.model.tool.shg.SelfHelpGuideQuestions();
        newRecord.data.critical = false;
        newRecord.data.mandatory = false;
        newRecord.data.name = badRecord.data.name;
        newRecord.data.challengeId = badRecord.data.id;
        newRecord.data.questionNumber = newQuestionNumber;
        newRecord.data.selfHelpGuideId = this.parent.data.id;
        if(badRecord)
        {
        	me.store.data.replace(this.store.data.getKey(badRecord),newRecord);
        	
        	for(var i = 0; i<this.store.data.items.length; i++)
        	{
        		me.store.data.items[i].data.questionNumber = i+1;
        	}
        }
        else
        {
        	me.store.add(newRecord);
        }
        me.getView().getStore().loadRecords(me.store.getRange());
        this.formUtils.reconfigureGridPanel( this.getView(), me.store);
     },
     deleteConfirmation: function( button ) {
    	 var me=this;
    	 var grid = button.up('grid');
		 var store = grid.getStore();
		 var selection = grid.getView().getSelectionModel().getSelection()[0];
		 var message;
		 if(!selection)
		 {
		 	Ext.Msg.alert('SSP Error', 'Please select an item.'); 
		 }
		 else
		 if ( selection.get('id') ) 
		 {
			   if ( !Ssp.util.Constants.isRestrictedAdminItemId( selection.get('id')  ) )
			   {
				    message = 'You are about to delete ' + selection.get('name') + '. Would you like to continue?';
			     	      	   
		         Ext.Msg.confirm({
		 		     title:'Delete?',
		 		     msg: message,
		 		     buttons: Ext.Msg.YESNO,
		 		     fn: me.deleteRecord,
		 		     scope: me
		 		   });
			   }else{
				   Ext.Msg.alert('WARNING', 'This item is related to core SSP functionality. Please see a developer to delete this item.'); 
			   }
		  }else{
		       store.remove(selection);
		       me.getView().getStore().loadRecords(me.store.getRange());
		       me.formUtils.reconfigureGridPanel( me.getView(), me.store);
		  }
     },	

	deleteRecord: function( btnId ){
		var me=this;
		var grid=me.getView();
		var store = grid.getStore();
		var selection = grid.getView().getSelectionModel().getSelection()[0];
		var id = selection.get('id');
		if (btnId=="yes")
		{
			me.apiProperties.makeRequest({
	 		url: store.getProxy().url+"/"+id,
	 		method: 'DELETE',
			successFunc: function(response,responseText){
	 		store.remove( store.getById( id ) );
	 		   }
	 	    });
		}
	},
	
	onCancelClick: function(button){
		this.displayMain();
	},
	
	displayMain: function(){
		var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {});
	}
});
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
Ext.define('Ssp.controller.admin.shg.EditSelfHelpGuideViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	formUtils: 'formRendererUtils',
    	model: 'currentSelfHelpGuide',
    	store: 'selfHelpGuidesStore',
    	questionStore: 'selfHelpGuideQuestionsStore'
    },
    config: {
    	containerToLoadInto: 'adminforms',
    	formToDisplay: 'selfhelpguideadmin'
    },
    control: {
    	'saveButton': {
			click: 'onSaveClick'
		},
		
		'cancelButton': {
			click: 'onCancelClick'
		}   	
    },
    
	init: function() {
		this.getView().getForm().loadRecord(this.model);
		return this.callParent(arguments);
    },
	onSaveClick: function(button) {
		var me = this;
		var record, id, jsonData, url,parentId;
		url = this.store.getProxy().url;
		this.getView().getForm().updateRecord();
		record = this.model;
		id = record.get('id');
		jsonData = record.data;
		successFuncGetId = function(response, view) {
			var r = Ext.decode(response.responseText);
			parentId = r.id;
	 		url = me.questionStore.getProxy().url;
	 		for(var i=0; i<me.questionStore.data.items.length;i++)
	 		{
	 			jsonData = me.questionStore.data.items[i].data;
	 			jsonData.selfHelpGuideId = parentId;
	 			id = jsonData.id;
	 			if (id.length > 0)
	 			{
	 				// editing
	 				me.apiProperties.makeRequest({
	 					url: url+"/"+id,
	 					method: 'PUT',
	 					jsonData: jsonData,
	 					successFunc: successFunc 
	 				});
	 				
	 			}else{
	 				// adding
	 				me.apiProperties.makeRequest({
	 					url: url,
	 					method: 'POST',
	 					jsonData: jsonData,
	 					successFunc: successFunc 
	 				});		
	 			}
	 		}
			
		};
		successFunc = function(response, view) {
			me.displayMain();
		};		
		if (id.length > 0)
		{
			// editing
			this.apiProperties.makeRequest({
				url: url+"/"+id,
				method: 'PUT',
				jsonData: jsonData,
				successFunc: successFuncGetId 
			});
		}else{
			// adding
			this.apiProperties.makeRequest({
				url: url,
				method: 'POST',
				jsonData: jsonData,
				successFunc: successFuncGetId 
			});		
		}
	 	},		
		
	
	onCancelClick: function(button){
		this.displayMain();
	},
	
	displayMain: function(){
		var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {});
	}
});
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
Ext.define('Ssp.controller.admin.shg.SelfHelpGuideAdminViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	selfHelpGuidesStore: 'selfHelpGuidesStore'
    },
	init: function() {
		this.selfHelpGuidesStore.load();	
		return this.callParent(arguments);
    }
});
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
Ext.define('Ssp.controller.admin.shg.SelfHelpGuideEditAdminController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
	init: function() {
		return this.callParent(arguments);
    }
});
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
Ext.define('Ssp.controller.admin.shg.SelfHelpGuideEditChallengesAdminController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
	init: function() {
		return this.callParent(arguments);
    }
});
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
Ext.define('Ssp.controller.admin.shg.SelfHelpGuidesDisplayViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	store: 'selfHelpGuidesStore',
    	selfHelpGuideQuestionsStore: 'selfHelpGuideQuestionsStore',
    	formUtils: 'formRendererUtils',
    	model: 'currentSelfHelpGuide',
    },
    config: {
    	containerToLoadInto: 'adminforms',
    	formToDisplay: 'editselfhelpguide'
    },
    control: {
    	'editButton': {
			click: 'onEditClick'
		},
		
		'addButton': {
			click: 'onAddClick'
		}   	
    },       
	init: function() {
		var me=this;
		
		me.formUtils.reconfigureGridPanel( me.getView(), me.store);
		me.store.load();
		
		return me.callParent(arguments);
    }, 
    
	onEditClick: function(button) {
		var grid, record;
		grid = button.up('grid');
		record = grid.getView().getSelectionModel().getSelection()[0];
        if (record) 
        {		
        	this.model.data=record.data;
        	this.displayEditor();
        }else{
     	   Ext.Msg.alert('SSP Error', 'Please select an item to edit.'); 
        }
	},
	
	onAddClick: function(button){
		var model = new Ssp.model.tool.shg.SelfHelpGuides();
		this.model.data = model.data;
		this.displayEditor();
	},
	displayEditor: function(){
		var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {});
	}
});
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
Ext.define('Ssp.model.tool.shg.SelfHelpGuideQuestions', {
    extend: 'Ssp.model.AbstractBase',
    fields: [{name:'id',type:'string'},
             {name:'name',type:'string'},
             {name:'questionNumber',type:'integer'},
             {name:'critical',type:'boolean'},
             {name:'mandatory',type:'boolean'},             
             {name:'selfHelpGuideId',type:'string'},
             {name:'challengeId',type:'string'},
             ]           
});

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
Ext.define('Ssp.store.SelfHelpGuideQuestions', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.tool.shg.SelfHelpGuideQuestions',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties'
    },    
	constructor: function(){
		Ext.apply(this, { proxy: this.apiProperties.getProxy( this.apiProperties.getItemUrl('selfHelpGuideQuestions') ),
						  autoLoad: false });
		return this.callParent(arguments);
	},
	
	groupField: 'group'
});
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
Ext.define('Ssp.model.tool.shg.SelfHelpGuides', {
    extend: 'Ssp.model.AbstractBase',
    fields: [{name:'id',type:'string'},
             {name:'name',type:'string'},
             {name:'description',type:'string'},
             {name:'active',type:'boolean'},
             {name:'authenticationRequired',type:'boolean'},             
             {name:'threshold',type:'integer'},
             {name:'introductoryText',type:'string'},
             {name:'summaryText',type:'string'},
             {name:'summaryTextEarlyAlert',type:'string'},    
             {name:'summaryTextThreshold',type:'string'},
             {name:'summaryTextEarlyAlert',type:'string'}
             ]
});
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
Ext.define('Ssp.store.SelfHelpGuides', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.tool.shg.SelfHelpGuides',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties'
    },    
	constructor: function(){
		Ext.apply(this, { proxy: this.apiProperties.getProxy( this.apiProperties.getItemUrl('selfHelpGuides') ),
						  autoLoad: false });
		return this.callParent(arguments);
	},
	
	groupField: 'group'
});
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
Ext.define('Ssp.view.admin.forms.shg.EditSelfHelpGuide',{
	extend: 'Ext.form.Panel',
	alias : 'widget.editselfhelpguide',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.shg.SelfHelpGuideEditAdminController',
	height: '100%',
	width: '100%',
	layout: {
        type: 'vbox',
        align: 'stretch'
    },
    autoScroll:true,
    initComponent: function(){
		Ext.apply(this,{
	          items: [
	                  {
	                  	xtype: 'editselfhelpguidedetails', 
	                  	flex: 1
	                  },{
	                  	xtype: 'editselfhelpguideeditchallenges', 
	                  	flex: 1
	                  }
	                 ]});
    	return this.callParent(arguments);
    }
});

 
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
Ext.define('Ssp.view.admin.forms.shg.EditSelfHelpGuideAvailableChallengesAdmin', {
	extend: 'Ext.grid.Panel',
	alias : 'widget.editselfhelpguideavailablechallengesadmin',
	title: 'Available Challenges',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.shg.EditSelfHelpGuideAvailableChallengesAdminViewController',
    inject: {
        apiProperties: 'apiProperties',
        authenticatedPerson: 'authenticatedPerson',
        columnRendererUtils: 'columnRendererUtils'
    },
    height: '100%',
	width: '100%',

    initComponent: function(){
    	var me=this;
    	Ext.apply(me,
    			{
		          viewConfig: {
		        	  plugins: {
		                  ptype: 'gridviewdragdrop',
		                  dragGroup: 'gridtogrid',
		                  enableDrag: true
		        	  },
		          },
    		      autoScroll: true,
    		      selType: 'rowmodel',
    		      enableDragDrop: false,
    		      columns: [
    		                { header: 'Name',  
    		                  dataIndex: 'name',
    		                  flex: 1 
    		                }
    		           ],
    		        
    		           dockedItems: [
     		       		{
     		       			xtype: 'pagingtoolbar',
     		       		    dock: 'bottom',
     		       		    displayInfo: true,
     		       		    pageSize: me.apiProperties.getPagingSize()
     		       		}]
     		               	
    	});
    	
    	return me.callParent(arguments);
    }
});
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
Ext.define('Ssp.view.admin.forms.shg.EditSelfHelpGuideChallenges',{
	extend: 'Ext.grid.Panel',
	alias : 'widget.editselfhelpguidechallenges',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.shg.EditSelfHelpGuideChallengesViewController',
	title: 'Assigned Challenges',
    inject: {
    	selfHelpGuideQuestionsStore: 'selfHelpGuideQuestionsStore',
        apiProperties: 'apiProperties',
        authenticatedPerson: 'authenticatedPerson',
        columnRendererUtils: 'columnRendererUtils',
    	parent: 'currentSelfHelpGuide'
    },	
	initComponent: function() {
    	var me=this;
        Ext.apply(me, {
	          viewConfig: {
	        	  itemId: 'gridView',
	        	  plugins: {
	                  ptype: 'gridviewdragdrop',
	                  dropGroup: 'gridtogrid',
	                  dragGroup: 'gridtogrid',
			          enableDrop: true,
			          enableDrag: true,
	        	  }        
	                },	        	  
		      enableDragDrop: true,
		      selType: 'rowmodel',
		      columns: [
		                { 
		                  header: 'Question Number',  
			              dataIndex: 'questionNumber',
			              flex: 1 
			            },		                
		                { 
			              header: 'Challenge Name',  
		                  dataIndex: 'name',
		                  flex: 3 
		                },
		                { 
		                  header: 'Critical',  
    		              dataIndex: 'critical',
		                  xtype: 'checkcolumn',
    		              flex: 1 
    		             },
		                { 
    		              header: 'Mandatory',  
  		                  dataIndex: 'mandatory',
		                  xtype: 'checkcolumn',
		                  flex: 1 
  		                } 		                
		           ],
            dockedItems: [{
	               xtype: 'toolbar',
	               items: [{
    		                   text: 'Delete Challenge',
    		                   xtype: 'button',
    		                   action: 'delete',
     		                   hidden: !me.authenticatedPerson.hasAccess('SELF_HELP_GUIDE_DELETE_BUTTON'),
    		                   itemId: 'deleteChallengeButton'
    		               }]
	           },
	       		{
	       			xtype: 'pagingtoolbar',
	       		    dock: 'bottom',
	       		    displayInfo: true,
	       		    pageSize: this.apiProperties.getPagingSize()
	       		}]            
        });

        return this.callParent(arguments);
    }	
});
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
Ext.define('Ssp.view.admin.forms.shg.EditSelfHelpGuideDetails',{
	extend: 'Ext.form.Panel',
	alias : 'widget.editselfhelpguidedetails',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.shg.EditSelfHelpGuideViewController',
	title: 'Edit Self Help Guide',
    inject: {
    	selfHelpGuidesStore: 'selfHelpGuidesStore',
        authenticatedPerson: 'authenticatedPerson'
    },
    autoScroll: true,
    collapsible: true,
    scroll: 'vertical',
	initComponent: function() {
    	var me=this;
        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'textfield',
                    fieldLabel: 'Self Help Guide Name',
                    anchor: '100%',
                    name: 'name'
                },
                {
                    xtype: 'textareafield',
                    fieldLabel: 'Description',
                    anchor: '100%',
                    name: 'description'
                },               
                {
                    xtype: 'numberfield',
                    fieldLabel: 'Threshold',
                    anchor: '30%',
                    name: 'threshold'
                },{
                    xtype: 'textareafield',
                    fieldLabel: 'Introduction',
                    anchor: '100%',
                    name: 'introductoryText'
                },
                {
                    xtype: 'textareafield',
                    fieldLabel: 'Summary',
                    anchor: '100%',
                    name: 'summaryText'
                },
                {
                    xtype: 'textareafield',
                    fieldLabel: 'Threshold Summary',
                    anchor: '100%',
                    name: 'summaryTextThreshold'
                },
                {
                    xtype: 'textareafield',
                    fieldLabel: 'Early Alert Summary',
                    anchor: '100%',
                    name: 'summaryTextEarlyAlert'
                },
                {
                    xtype: 'checkboxfield',
                    fieldLabel: 'Published',
                    anchor: '100%',
                    name: 'active'
                },
                {
                    xtype: 'checkboxfield',
                    fieldLabel: 'Authentication Required',
                    anchor: '100%',
                    name: 'authenticationRequired'
                }                
            ],
            dockedItems: [{
	               xtype: 'toolbar',
	               items: [{
 		                   text: 'Save',
 		                   xtype: 'button',
 		                   action: 'save',
 		                   hidden: !me.authenticatedPerson.hasAccess('SELF_HELP_GUIDE_SAVE_BUTTON'),
 		                   itemId: 'saveButton'
 		               }, '-', {
 		                   text: 'Cancel',
 		                   xtype: 'button',
 		                   action: 'cancel',
 		                   itemId: 'cancelButton'
 		               }]
	           }]    
        });

        return this.callParent(arguments);
    }	
});
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
Ext.define('Ssp.view.admin.forms.shg.EditSelfHelpGuideEditChallenges',{
	extend: 'Ext.form.Panel',
	alias : 'widget.editselfhelpguideeditchallenges',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.shg.SelfHelpGuideEditChallengesAdminController',
	title: 'Manage Challenges',
	height: '100%',
	width: '100%',
	layout: {
        type: 'hbox',
        align: 'stretch'
    },
    collapsible: true,
    initComponent: function(){
		Ext.apply(this,{
	          items: [
	                  {
	                  	xtype: 'editselfhelpguidechallenges', 
	                  	flex: 1
	                  },{
	                  	xtype: 'editselfhelpguideavailablechallengesadmin', 
	                  	flex: 1
	                  }
	                 ]});
    	return this.callParent(arguments);
    }
});

 
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
Ext.define('Ssp.view.admin.forms.shg.SelfHelpGuideAdmin', {
	extend: 'Ext.container.Container',
	alias : 'widget.selfhelpguideadmin',
	title: 'Self Help Guide Admin',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.shg.SelfHelpGuideAdminViewController',
	height: '100%',
	width: '100%',
	layout: {
        type: 'vbox',
        align: 'stretch'
    },
    initComponent: function(){
		Ext.apply(this,{
	          items: [
	                  {
	                  	xtype: 'selfhelpguidesdisplayadmin', 
	                    anchor: '100%',
	                  	flex: 1
	                  }
	                 ]});
    	return this.callParent(arguments);
    }
});
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
Ext.define('Ssp.view.admin.forms.shg.SelfHelpGuidesDisplayAdmin', {
	extend: 'Ext.grid.Panel',
	alias : 'widget.selfhelpguidesdisplayadmin',
	title: 'Self Help Guides Admin',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.shg.SelfHelpGuidesDisplayViewController',
    inject: {
        apiProperties: 'apiProperties',
        authenticatedPerson: 'authenticatedPerson',
        columnRendererUtils: 'columnRendererUtils'
    },
    height: '100%',
	width: '100%',

    initComponent: function(){
    	var me=this;
    	Ext.apply(me,
    			{
		          viewConfig: {
		        	
		          },
    		      autoScroll: true,
    		      selType: 'rowmodel',
    		      enableDragDrop: false,
    		      columns: [
    		                { header: 'Self Help Guide',  
    		                  dataIndex: 'name',
    		                  flex: 1 
    		                },
    		                { header: 'Description',  
        		                  dataIndex: 'description',
        		                  flex: 1 
        		             },
    		                { header: 'Published',  
      		                  dataIndex: 'active',
      		                  renderer: me.columnRendererUtils.renderFriendlyBoolean,
      		                  flex: 1 
      		                }
    		           ],
    		        
    		           dockedItems: [
     		       		{
     		       			xtype: 'pagingtoolbar',
     		       		    dock: 'bottom',
     		       		    displayInfo: true,
     		       		    pageSize: me.apiProperties.getPagingSize()
     		       		},
     		              {
     		               xtype: 'toolbar',
     		               items: [{
     		                   text: 'Add',
     		                   iconCls: 'icon-add',
     		                   xtype: 'button',
     		                   hidden: !me.authenticatedPerson.hasAccess('SELF_HELP_GUIDE_DELETE_BUTTON'),
     		                   action: 'add',
     		                   itemId: 'addButton'
     		               }, '-', {
     		                   text: 'Edit',
     		                   iconCls: 'icon-edit',
     		                   xtype: 'button',
     		                   hidden: !me.authenticatedPerson.hasAccess('SELF_HELP_GUIDE_EDIT_BUTTON'),
     		                   action: 'edit',
     		                   itemId: 'editButton'
     		               }]
     		           },{
     		               xtype: 'toolbar',
     		               dock: 'top',
     		               items: [{
     	                      xtype: 'label',
     	                       text: 'Click on an existing Guide to Edit or Delete.  Click on "add" to create a new Guide.'
     	                     }]
     		           }]    	
    	});
    	
    	return me.callParent(arguments);
    }
});

