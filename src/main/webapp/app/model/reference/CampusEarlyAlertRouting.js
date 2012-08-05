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