Ext.define('Ssp.controller.tool.actionplan.TasksViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	appEventsController: 'appEventsController',
    	formUtils: 'formRendererUtils',
    	person: 'currentPerson',
    	tasksStore: 'tasksStore' 
    },
    
    config: {
    	personTaskUrl: ''
    },
    
    control: {
    	view: {
    		viewready: 'onViewReady'
    	},
    	
		'addTaskButton': {
			click: 'onAddTaskClick'
		}
	},
	
	init: function() {
		this.personTaskUrl = this.apiProperties.getItemUrl('personTask');
		this.personTaskUrl=this.personTaskUrl.replace('{id}',this.person.get('id'));

		return this.callParent(arguments);
    },

    onViewReady: function(comp, obj){
    	this.appEventsController.getApplication().addListener('editTask', function(args){
 			this.editTask();
		},this);

    	this.appEventsController.getApplication().addListener('closeTask', function(args){
 			this.closeTask(args);
		},this);
    	
    	this.appEventsController.getApplication().addListener('deleteTask', function(args){
 			this.deleteTask(args);
		},this);
    },    
 
    onAddTaskClick: function(button) {
		var comp = this.formUtils.loadDisplay('tools','addtask', true, {});
    },
    
    editTask: function(){
 	   console.log('TaskViewController->editTask');
    },
    
    closeTask: function(args) {
	   console.log('TaskViewController->closeTask');
       var url, store, id;
       var record = args.record;
       var id = record.get('id');
	   store = this.tasksStore;
       url = this.apiProperties.createUrl( this.personTaskUrl );
       if (id != "") 
       {
           record.set('completed',true);
		   this.apiProperties.makeRequest({
			   url: url+id,
			   method: 'PUT',
			   jsonData: record.data,
			   successFunc: function(response,responseText){
				   var r = Ext.decode(response.responseText);
				   // record.commit();
				   //store.sync();
			   },
			   scope: this
		   });
       }else{
    	   Ext.Msg.alert('SSP Error', 'Unable to delete. No id was specified to delete.'); 
       }
    },    
    
    /*
     * @args - an object containing the record to delete
     * 
     */
    deleteTask: function(args) {
       var url, store, id;
       var record = args.record;
       var id = record.get('id');
       var type = record.get('type');
	   store = this.tasksStore;
       url = this.apiProperties.createUrl( this.personTaskUrl );
       if (id != "" && type.toLowerCase() == 'ssp') 
       {
           this.apiProperties.makeRequest({
			   url: url+id,
			   method: 'DELETE',
			   successFunc: function(response,responseText){
				   var r = Ext.decode(response.responseText);
				   store.remove( record );
			   },
			   scope: this
		   });
       }else{
    	   Ext.Msg.alert('SSP Error', 'Unable to delete. No id was specified or this is not a deletable task.'); 
       }
    }
});