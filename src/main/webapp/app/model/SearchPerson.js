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