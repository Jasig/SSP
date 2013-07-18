/*
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
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
            treeRequest.set('url', this.journalTrackUrl + '/' + journalTrackId + '/journalStep?sort=name&status=ALL');
            treeRequest.set('nodeType', 'journalStep');
            treeRequest.set('isLeaf', false);
            treeRequest.set('enableCheckedItems', false);
            treeRequest.set('expanded', false);
            treeRequest.set('callbackFunc', this.afterJournalStepsLoaded);
            treeRequest.set('callbackScope', this);
            this.treeUtils.getItems(treeRequest);
        }
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
            treeRequest.set('url', url + '/' + id + '/journalStepDetail?sort=name&status=' + 'ALL');
            treeRequest.set('nodeType', 'journalDetail');
            treeRequest.set('isLeaf', true);
            treeRequest.set('nodeToAppendTo', node);
            treeRequest.set('enableCheckedItems', true);
            treeRequest.set('callbackFunc', me.afterJournalDetailsLoaded);
            treeRequest.set('callbackScope', me);
            treeRequest.set('removeParentWhenNoChildrenExist', true);
            
            treeRequest.set('node', node);
            me.treeUtils.getItems(treeRequest);
        }
    },
    
    
    
    
    afterJournalDetailsLoaded: function(scope, node){
        var me = this;
        // after the journal details load select each detail
        // that is selected in the journal
        var journalEntryDetails = scope.journalEntry.get("journalEntryDetails");
        
        if (journalEntryDetails != "" && journalEntryDetails != null) {
        
            Ext.Array.each(journalEntryDetails, function(item, index){
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
                        
                        if (stepNode.id == parentNode.id) {
                            detailNode.set('checked', true);
                            
                        }
                    }
                });
                
            });
        }
        
        var children = node.childNodes;
        if (children) {
           
            var kidsLen = (children.length - 1);
            for (var i = kidsLen; i >= 0; i--) {
                var child = node.childNodes[i];
               
                var isChecked = child.get('checked');
                var status = child.get('qtitle');
                if (!isChecked && status == 'INACTIVE') {
                    child.remove();
                }
            }
            children = node.childNodes;
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
    }
    
});
