/*
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
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
    mixins: ['Deft.mixin.Injectable'],
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
            itemexpand: 'onItemExpand'
        }
    },
    
    init: function(){
        var rootNode = null;
        this.journalTrackUrl = this.apiProperties.getItemUrl('journalTrack');
        this.journalStepUrl = this.apiProperties.getItemUrl('journalStep');
        this.journalStepDetailUrl = this.apiProperties.getItemUrl('journalStep');
        if (this.journalEntry.get('journalTrack') != null) {
            this.loadSteps();
        }
        
        
        return this.callParent(arguments);
    },
    
    destroy: function(){
        // clear the categories
        this.treeUtils.clearRootCategories();
        
        return this.callParent(arguments);
    },
    
    loadSteps: function(){
    
        // clear the categories
        this.treeUtils.clearRootCategories();
        
        var journalTrackId = this.journalEntry.get('journalTrack').id;
        
        // load the steps
        if (journalTrackId != null && journalTrackId != "") {
            var treeRequest = new Ssp.model.util.TreeRequest();
            treeRequest.set('url', this.journalTrackUrl + '/' + journalTrackId + '/journalTrackJournalStep?limit=-1&status=ALL');
            treeRequest.set('nodeType', 'journalStep');
            treeRequest.set('isLeaf', false);
            treeRequest.set('enableCheckedItems', false);
            treeRequest.set('responseFilter', this.transformJournalTrackJournalStepAssociations);
            treeRequest.set('expanded', false);
            treeRequest.set('callbackFunc', this.afterJournalStepsLoaded);
            treeRequest.set('callbackScope', this);
            this.treeUtils.getItems(treeRequest);
        }
    },

    transformJournalTrackJournalStepAssociations: function(assocs) {
        // Lots of copy/paste code from transformJournalStepJournalDetailAssociations()
        // that we're just living with for now
        var me = this;
        var transformed = [];
        Ext.Array.each(assocs, function(assoc, index) {
            if ( assoc.objectStatus === "ACTIVE" || me.isSelectedJournalTrackJournalStepAssociation(assoc) ) {
                transformed.push(me.journalStepNodeItemFromTrackAssociation(assoc));
            }
        });
        return me.sortBy(transformed, "name");
    },

    isSelectedJournalTrackJournalStepAssociation: function(assoc) {
        // Lots of copy/paste code from isSelectedJournalStepJournalDetailAssociation()
        // that we're just living with for now

        var me = this;
        var journalEntryDetails = me.journalEntry.get("journalEntryDetails");
        if ( !(journalEntryDetails) ) {
            return false;
        }

        var assocJournalStepId = assoc.journalStep && assoc.journalStep.id;
        if ( !(assocJournalStepId) ) {
            return false;
        }
        return journalEntryDetails.some(function(journalEntryDetail, index){
            if ( journalEntryDetail.objectStatus !== 'ACTIVE' ) {
                return false;
            }
            var journalStep = journalEntryDetail.journalStep;
            return journalStep && journalStep.id === assocJournalStepId;
        });
    },

    journalStepNodeItemFromTrackAssociation: function(assoc) {
        return assoc.journalStep;
    },

    afterJournalStepsLoaded: function(scope){
        // after the journal steps load expand them to
        // display the details under each step
        var me = this;
        
        scope.getView().getView().getTreeStore().getRootNode().expandChildren();
    },
    
    onItemExpand: function(nodeInt, obj){
        var me = this;
        var node = nodeInt;
        var url = me.journalStepDetailUrl;
        var id = me.treeUtils.getIdFromNodeId(node.data.id);
        if (url != "") {
            var treeRequest = new Ssp.model.util.TreeRequest();
            // can't sort on name here... these objects have no name
            treeRequest.set('url', url + '/' + id + '/journalStepJournalStepDetail?limit=-1&status=ALL');
            treeRequest.set('nodeType', 'journalDetail');
            treeRequest.set('isLeaf', true);
            treeRequest.set('nodeToAppendTo', node);
            treeRequest.set('enableCheckedItems', true);
            treeRequest.set('responseFilter', me.transformJournalStepJournalDetailAssociations);
            treeRequest.set('callbackFunc', me.afterJournalDetailsLoaded);
            treeRequest.set('callbackScope', me);
            treeRequest.set('removeParentWhenNoChildrenExist', true);
            
            treeRequest.set('node', node);
            me.treeUtils.getItems(treeRequest);
        }
    },

    transformJournalStepJournalDetailAssociations: function(assocs) {
        var me = this;
        var transformed = [];
        Ext.Array.each(assocs, function(assoc, index) {
            if ( assoc.objectStatus === "ACTIVE" || me.isSelectedJournalStepJournalDetailAssociation(assoc) ) {
                transformed.push(me.journalDetailNodeItemFromStepAssociation(assoc));
            }
        });
        return me.sortBy(transformed, "name");
    },

    isSelectedJournalStepJournalDetailAssociation: function(assoc) {
        var me = this;
        var journalEntryDetails = me.journalEntry.get("journalEntryDetails");
        if ( !(journalEntryDetails) ) {
            return false;
        }

        // Journal entry API doesn't actually track step/detail assoc IDs, even
        // though that's what's happening on the back end.
        var assocJournalStepId = assoc.journalStep && assoc.journalStep.id;
        var assocJournalStepDetailId = assoc.journalStepDetail && assoc.journalStepDetail.id;
        if ( !(assocJournalStepId) || !(assocJournalStepDetailId) ) {
            return false;
        }
        return journalEntryDetails.some(function(journalEntryDetail, index){
            if ( journalEntryDetail.objectStatus !== 'ACTIVE' ) {
                return false;
            }
            var journalStep = journalEntryDetail.journalStep;
            if ( !(journalStep && journalStep.id === assocJournalStepId) ) {
                return false;
            }
            var journalStepDetails = journalEntryDetail.journalStepDetails;
            if ( !(journalStepDetails) ) {
                return false;
            }
            return journalStepDetails.some(function(journalStepDetail, index){
                return journalStepDetail.id === assocJournalStepDetailId;
            });
        });
    },

    journalDetailNodeItemFromStepAssociation: function(assoc) {
        return assoc.journalStepDetail;
    },

    afterJournalDetailsLoaded: function(scope, node){

        var me = this;
        // after the journal details load select each detail
        // that is selected in the journal
        var journalEntryDetails = scope.journalEntry.get("journalEntryDetails");
        
        if (journalEntryDetails != "" && journalEntryDetails != null) {
        
            Ext.Array.each(journalEntryDetails, function(item, index){

                if ( item.objectStatus === 'ACTIVE' ) {

                    var journalStepDetails = item.journalStepDetails;
                    var journalStep = item.journalStep;
                    var counter = 0;

                    Ext.Array.each(journalStepDetails, function(innerItem, innerIndex){

                        var id = innerItem.id;

                        var detailNode = scope.getView().getView().getTreeStore().getNodeById(id + '_journalDetail');

                        if (detailNode != null) {

                            stepNode = detailNode.parentNode;



                            var parentId = journalStep.id;


                            var parentNode = scope.getView().getView().getTreeStore().getNodeById(parentId + '_journalStep');

                            if ((stepNode && parentNode) && (stepNode.id == parentNode.id)) {
                                detailNode.set('checked', true);

                            }
                        }

                    });

                }
                
            });
        }
        
        var children = node.childNodes;
        if (children) {
            if (node.get('qtitle') == 'INACTIVE' && !children.length) {
                node.remove();
            }
        }
    },
    
    onSaveClick: function(button){
        var me = this;
        
        me.save();
    },
    
    save: function(){
        var me = this;
        var journalEntry = me.journalEntry;
        var tree = me.getView();
        var treeUtils = me.treeUtils;
        var records = tree.getView().getChecked();
        journalEntry.removeAllJournalEntryDetails();
        var je = journalEntry;
        // add/remove the detail from the Journal Entry
        Ext.Array.each(records, function(record, index){
            var id = me.treeUtils.getIdFromNodeId(record.data.id);
            var childText = record.data.text;
            var parentId = me.treeUtils.getIdFromNodeId(record.data.parentId);
            var parentText = record.parentNode.data.text;
            var step = null;
            var detail = null;
            
            step = {
                "id": parentId,
                "name": parentText
            };
            detail = {
                "id": id,
                "name": childText
            };
            // add journal detail
            journalEntry.addJournalDetail(step, detail);
        }, this);
        
    },
    
    
    displayJournalEditor: function(){
        var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {});
    },

    sortBy: function(array, field) {
        array.sort(function(a,b){
            var fieldA = a[field];
            var fieldB = b[field];
            if ( fieldA < fieldB ) {
                return -1;
            }
            return (fieldA > fieldB) ? 1 : 0;
        });
        return array; // just to make chaining easier
    },
    
});
