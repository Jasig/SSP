Ext.define('Ssp.model.AuthenticatedPerson', {
    extend: 'Ssp.model.AbstractBase',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties'
    }, 
    fields: [{name: 'schoolId', type: 'string'},
    		 {name: 'firstName', type: 'string'},
             {name: 'middleInitial', type: 'string'},
    		 {name: 'lastName', type: 'string'},
             {name: 'primaryEmailAddress', type: 'string'},
    		 {name: 'username', type: 'string'},
             {name: 'userId', type: 'string'},
             {name:'permissions',type:'auto'}],

     getFullName: function(){ 
     	var firstName = this.get('firstName') || "";
     	var middleInitial = this.get('middleInitial') || "";
     	var lastName = this.get('lastName') || "";
     	return firstName + " " + middleInitial + " " + lastName;
     }
});