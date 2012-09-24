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
             {name: 'removeParentWhenNoChildrenExist', type: 'boolean', defaultValue: false}]
});