Ext.define('Ssp.model.Tool', {
    extend: 'Ext.data.Model',
    fields: [{name:'name',type:'string'},
             {name:'toolType',type:'string'},
             {name:'active',type:'boolean'},
             {name:'group',type:'string'}]
});