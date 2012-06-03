Ext.define('Ssp.model.reference.CampusEarlyAlertRouting', {
    extend: 'Ssp.model.AbstractBase',
    fields: [{name:'earlyAlertReasonId',type:'string'},
             {name:'person',type:'auto'},
             {name:'groupName',type:'string'},
             {name:'groupEmail',type:'string'}]
});