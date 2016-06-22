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
Ext.define('Ssp.controller.admin.map.AssociateElectiveCoursesAdminViewController', {
	extend: 'Ssp.controller.admin.AdminItemAssociationViewController',
    inject: {
        apiProperties: 'apiProperties',
        formUtils: 'formRendererUtils',
        treeUtils: 'treeRendererUtils',
        textStore: 'sspTextStore',
        currentTemplateGetId: 'currentMapPlan'
    },
    config: {
        associatedItemType: 'electiveCourseDetail',
        parentItemType: 'electiveCourses',
        parentIdAttribute: 'electiveCourseId',
        associatedItemIdAttribute: 'electiveCourseDetailId'
    },
	constructor: function(){
		var me=this;
		me.callParent(arguments);
		me.clear();

		var params = {status: "ACTIVE", limit: "-1"};
		if (me.currentTemplateGetId && me.currentTemplateGetId.get('id')) {
            me.getParentItemsWithIdAndParams(me.currentTemplateGetId.get('id'), params);
        } else {
            Ext.Msg.alert('SSP Error', 'Please select a Template.');
        }
		
		return me;
	},
    onBeforeDrop: function(node, data, overModel, dropPosition, dropHandler, eOpts) {
        var me=this;
        var url, parentId, associatedItemId, node;

        // ensure the drop handler waits for the drop
        dropHandler.wait=true;
        if(data.records[0].get('objectStatus') ==='INACTIVE')
        {
            Ext.Msg.alert(
                me.textStore.getValueByCode('ssp.message.elective-course.error-title','SSP Error'),
                me.textStore.getValueByCode('ssp.message.elective-course.inactive-reference-item','You cannot assign inactive reference items'));
            dropHandler.cancelDrop;
            return 1;
        }
        // handle drop on a folder
        if ((!overModel.isLeaf() && dropPosition == 'append')||((dropPosition=='before' || dropPosition=='after') && overModel.isLeaf()))
        {
            if (!overModel.isLeaf() && dropPosition == 'append') {
                node = overModel;
            } else {
                node = overModel.parentNode;
            }

            //Check to see if duplicate course
            for (i=0; i < node.childNodes.length; i++) {
                if (node.childNodes[i].data.text==data.records[0].get('formattedCourse')) {
                    Ext.Msg.alert(
                        me.textStore.getValueByCode('ssp.message.elective-course.error-title','SSP Error'),
                        me.textStore.getValueByCode('ssp.message.elective-course.duplicate-course','You cannot associated the same course more than once.'));
                    dropHandler.cancelDrop;
                    return 1;
                }
            }

            parentId = me.treeUtils.getIdFromNodeId(node.data.id);

            parentUrl = me.apiProperties.getItemUrl( me.getParentItemType() ) + '/' + parentId + '/' + me.getAssociatedItemType();
            url = me.apiProperties.createUrl( parentUrl );

            var requestData = {formattedCourse:data.records[0].get('formattedCourse'),
                               courseCode:data.records[0].get('code'),
                               courseTitle:data.records[0].get('title'),
                               courseDescription:data.records[0].get('description'),
                               creditHours:data.records[0].get('maxCreditHours') };

            me.apiProperties.makeRequest({
                url: url,
                method: 'POST',
                jsonData: requestData,
                successFunc: function(response, view){
                    me.getAssociatedItems(node, parentId);
                }
            });
        }

        dropHandler.cancelDrop;
        return 1;
    }
});