Ext.define('Ssp.model.SearchPerson', {
    extend: 'Ext.data.Model',
    fields: [{name:'id', type: 'string'},
             {name: 'schoolId', type: 'string'},
             {name: 'firstName', type: 'string'},
             {name: 'middleInitial', type: 'string'},
             {name: 'lastName', type: 'string'},
             {name: 'photoUrl', type: 'string'},
             {name: 'currentProgramStatusName', type: 'string'},
             {name: 'coach', type: 'auto'}],

     getFullName: function(){ 
     	var firstName = this.get('firstName') || "";
     	var middleInitial = this.get('middleInitial') || "";
     	var lastName = this.get('lastName') || "";
     	return firstName + " " + middleInitial + " " + lastName;
     }      
});