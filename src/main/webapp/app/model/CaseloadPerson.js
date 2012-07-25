Ext.define('Ssp.model.CaseloadPerson', {
    extend: 'Ext.data.Model',
    fields: [{name:'personId', type: 'string'},
             {name: 'schoolId', type: 'string'},
             {name:'firstName', type:'string'},
             {name:'lastName', type: 'string'},
             {name:'middleName',type:'string'},
             {name: 'studentTypeName', type: 'string'},
             {name: 'currentAppointmentStartDate', type: 'date', dateFormat: 'time'},
             {name: 'numberOfEarlyAlerts', type: 'string'},
             {name: 'studentIntakeComplete', type: 'boolean'},
             {name: 'currentAppointmentStartTime', type: 'date', dateFormat: 'time'},
             {name: 'currentProgramStatusName', type: 'string'}],            
             
     getFullName: function(){ 
      	var firstName = this.get('firstName') || "";
      	var middleName = this.get('middleName') || "";
      	var lastName = this.get('lastName') || "";
      	return firstName + " " + middleName + " " + lastName;
     },
     
     getStudentTypeName: function(){
     	return ((this.get('studentTypeName') != null)? this.get('studentTypeName') : "");   	
     }       
});