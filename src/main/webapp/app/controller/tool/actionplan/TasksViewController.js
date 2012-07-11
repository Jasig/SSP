Ext.define('Ssp.controller.tool.actionplan.TasksViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	appEventsController: 'appEventsController',
    	formUtils: 'formRendererUtils',
    	model: 'currentTask',
    	person: 'currentPerson',
    	store: 'tasksStore' 
    },
    
    config: {
    	appEventsController: 'appEventsController',
    	containerToLoadInto: 'tools',
    	formToDisplay: 'addtask',
    	url: ''
    },
    
    control: {
    	view: {
    		viewready: 'onViewReady'
    	}
	},
	
	init: function() {
		this.url = this.apiProperties.createUrl( this.apiProperties.getItemUrl('personTask') );
		this.url = this.url.replace('{id}',this.person.get('id'));

		return this.callParent(arguments);
    },

    onViewReady: function(comp, obj){
    	this.appEventsController.assignEvent({eventName: 'addTask', callBackFunc: this.onAddTask, scope: this});
    	this.appEventsController.assignEvent({eventName: 'editTask', callBackFunc: this.onEditTask, scope: this});
    	this.appEventsController.assignEvent({eventName: 'closeTask', callBackFunc: this.onCloseTask, scope: this});
    	this.appEventsController.assignEvent({eventName: 'deleteTask', callBackFunc: this.deleteConfirmation, scope: this});
    },    
 
    destroy: function() {
    	this.appEventsController.removeEvent({eventName: 'addTask', callBackFunc: this.onAddTask, scope: this});
    	this.appEventsController.removeEvent({eventName: 'editTask', callBackFunc: this.onEditTask, scope: this});
    	this.appEventsController.removeEvent({eventName: 'closeTask', callBackFunc: this.onCloseTask, scope: this});
    	this.appEventsController.removeEvent({eventName: 'deleteTask', callBackFunc: this.deleteConfirmation, scope: this});

        return this.callParent( arguments );
    },
    
    onAddTask: function() {
    	var task = new Ssp.model.tool.actionplan.Task();
    	this.model.data = task.data;
    	this.loadEditor();
    },    
    
    onEditTask: function(){
 	   console.log('TaskViewController->editTask');
 	   //Ext.Msg.alert("NOTIFICATION","This functionality is disabled until I can figure out why the tree component renders it's init method twice on edit from the grid.")
 	   this.loadEditor();
    },
    
    onCloseTask: function() {
	   console.log('TaskViewController->closeTask');
       var me=this;
	   var store, id, model, groupName;
       model = me.model;
       id = model.get('id');
	   store = me.store;
       if (id != "") 
       {
           model.set('completed',true);
           model.set('completedDate', new Date() );
           // remove group property before save, since
           // the group property was added dynamically for
           // sorting and will invalidate the model on the
           // server side.
           groupName = model.data.group;
           delete model.data.group;
		   this.apiProperties.makeRequest({
			   url: this.url+"/"+id,
			   method: 'PUT',
			   jsonData: model.data,
			   successFunc: function(response,responseText){
				   var r = Ext.decode(response.responseText);
				   // ensure proper save
				   if ( r.id != "" )
				   {
					   model.set( "completedDate", r.completedDate );
					   model.set( "completed", r.completed );
					   // reset the group for sorting purposes
					   model.set('group',groupName);
					   model.commit();
					   store.sync();
					   // filter the tasks, so the completed task is no longer
					   // listed
					   me.appEventsController.getApplication().fireEvent('filterTasks');					   
				   }
			   },
			   scope: me
		   });
       }else{
    	   Ext.Msg.alert('SSP Error', 'Unable to delete. No id was specified to delete.'); 
       }
    },    
    
    deleteConfirmation: function() {
        var message = 'You are about to delete the task: "'+ this.model.get('name') + '". Would you like to continue?';
    	var model = this.model;
        if (model.get('id') != "") 
        {
    		// test if this is a student task
     	   if ( model.get('createdBy').id == this.person.get('id') )
     	   {
     		   message = "WARNING: You are about to delete a task created by this student. Would you like to continue?"; 
     	   }
     	   
           Ext.Msg.confirm({
   		     title:'Delete Task?',
   		     msg: message,
   		     buttons: Ext.Msg.YESNO,
   		     fn: this.deleteTask,
   		     scope: this
   		   });
        }else{
     	   Ext.Msg.alert('SSP Error', 'Unable to delete task.'); 
        }
     },
     
     deleteTask: function( btnId ){
     	var store = this.store;
     	var id = this.model.get('id');
     	if (btnId=="yes")
     	{
         	this.apiProperties.makeRequest({
      		   url: this.url+"/"+id,
      		   method: 'DELETE',
      		   successFunc: function(response,responseText){
      			   store.remove( store.getById( id ) );
      		   }
      	    });    		
     	}
     },    
     
     loadEditor: function(){
     	var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {});    	
     }
});