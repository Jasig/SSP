Ext.define('Ssp.model.CaseloadPerson', {
    extend: 'Ext.data.Model',
    fields: [{name:'personId', type: 'string'},
             {name: 'schoolId', type: 'string'},
             {name: 'firstName', type: 'string'},
             {name: 'middleInitial', type: 'string'},
             {name: 'lastName', type: 'string'},
             {name: 'studentTypeName', type: 'string'},
             {name: 'currentAppointmentStartDate', type: 'date', dateFormat: 'time'},
             {name: 'numberOfEarlyAlerts', type: 'string'},
             {name: 'studentIntakeComplete', type: 'boolean'}],

     getFullName: function(){ 
     	var firstName = this.get('firstName') || "";
     	var middleInitial = this.get('middleInitial') || "";
     	var lastName = this.get('lastName') || "";
     	return firstName + " " + middleInitial + " " + lastName;
     },
     
     getStudentTypeName: function(){
     	return ((this.get('studentTypeName') != null)? this.get('studentTypeName') : "");   	
     }       
});