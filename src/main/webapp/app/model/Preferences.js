Ext.define('Ssp.model.Preferences', {
    extend: 'Ext.data.Model',
    fields: [{name:'SEARCH_GRID_VIEW_TYPE',type:'int', defaultValue:1}, // 0 display photo, 1 display grid
    		 {name:'ACTION_PLAN_ACTIVE_VIEW',type:'int',defaultValue:0} // 0 for Tasks, 1 for Goals
    		]
});