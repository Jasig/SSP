Ext.define('Ssp.model.tool.earlyalert.PersonEarlyAlert', {
    extend: 'Ssp.model.AbstractBase',
    fields: [{name:'name',type:'string'},
             {name:'comment',type:'string'},
             {name:'confidentialityLevelId',type:'string'}]
});