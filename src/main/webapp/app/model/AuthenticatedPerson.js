Ext.define('Ssp.model.AuthenticatedPerson', {
    extend: 'Ssp.model.AbstractBase',
    config: {
    	unauthorizedAccessAlertTitle: 'Unauthorized Access',
    	unauthorizedAccessAlertMessage: 'You do not have permission to access this item. Please see your system administrator if you require access to this information!'    	
    },
    fields: [{name:'permissions', type:'auto', defaultValue: null},
    		 {name:'confidentialityLevels', type:'auto', defaultValue: null},
    		 {name:'objectPermissionsCollection', type:'auto'}],  
    statics: {
    	/*
    	 * To implement a required permission against an object in the interface:
    	 * 
    	 * 1) Add an static property to this class with a name of
    	 * 'REQUIRED_PERMISSIONS_' + the object name you would like to secure. (See props below)
    	 * 
    	 * 2) Set the permissions for that object in an array as the value for the
    	 * assigned property.
    	 * 
    	 * 3) In the interface classes, determine the object access by calling the hasAccess 
    	 * method of this class and passing the object name as the sole argument, without
    	 * the appended 'REQUIRED_PERMISSIONS_' on the name.
    	 * 
    	 * For example: In a view you could assign the hidden property of a button like so:
    	 *  
    	 *  {
    	 *    xtype: 'button',
    	 *    hidden: !authenticatedPerson.hasAccess('PRINT_HISTORY_BUTTON'),
    	 *    text: 'Print History'
    	 *  }
    	 * 
    	 * The hasAccess method will return a boolean for use in securing the interface based
    	 * upon the permissions array you've defined by object name when you defined your required
    	 * permissions for the associated object.
    	 */
    	
    	/* MAIN NAVIGATION */
    	REQUIRED_PERMISSIONS_STUDENTS_NAVIGATION_BUTTON: ['ROLE_PERSON_READ'],
    	REQUIRED_PERMISSIONS_ADMIN_NAVIGATION_BUTTON: ['ROLE_REFERENCE_WRITE'],
    	
    	/* SEARCH */  	
    	REQUIRED_PERMISSIONS_STUDENT_SEARCH: ['ROLE_PERSON_SEARCH_READ'],   	
    	
    	/* STUDENTS */  	
    	REQUIRED_PERMISSIONS_ADD_STUDENT_BUTTON: ['ROLE_PERSON_WRITE'],
    	REQUIRED_PERMISSIONS_EDIT_STUDENT_BUTTON: ['ROLE_PERSON_WRITE'],
    	REQUIRED_PERMISSIONS_DELETE_STUDENT_BUTTON: ['ROLE_PERSON_DELETE'],
    	
    	/* ADMIN TOOLS */
    	REQUIRED_PERMISSIONS_ABSTRACT_REFERENCE_ADMIN_ADD_BUTTON: ['ROLE_REFERENCE_WRITE'],
    	REQUIRED_PERMISSIONS_ABSTRACT_REFERENCE_ADMIN_EDIT: ['ROLE_REFERENCE_WRITE'],
    	REQUIRED_PERMISSIONS_ABSTRACT_REFERENCE_ADMIN_DELETE_BUTTON: ['ROLE_REFERENCE_WRITE'],

    	REQUIRED_PERMISSIONS_CAMPUS_ADMIN_ADD_BUTTON: ['ROLE_REFERENCE_WRITE'],
    	REQUIRED_PERMISSIONS_CAMPUS_ADMIN_EDIT_BUTTON: ['ROLE_REFERENCE_WRITE'],
    	REQUIRED_PERMISSIONS_CAMPUS_ADMIN_DELETE_BUTTON: ['ROLE_REFERENCE_WRITE'],    	

    	REQUIRED_PERMISSIONS_CONFIDENTIALITY_AGREEMENT_ADMIN_FIELDS: ['ROLE_REFERENCE_WRITE'],
    	REQUIRED_PERMISSIONS_CONFIDENTIALITY_AGREEMENT_ADMIN_SAVE_BUTTON: ['ROLE_REFERENCE_WRITE'],    	
    	
    	/* counseling reference guide admin */ 
    	
    	// delete associations
    	REQUIRED_PERMISSIONS_CHALLENGE_CATEGORIES_ASSOCIATION_ADMIN_DELETE_BUTTON: ['ROLE_REFERENCE_WRITE'],   	
    	REQUIRED_PERMISSIONS_CHALLENGE_REFERRALS_ASSOCIATION_ADMIN_DELETE_BUTTON: ['ROLE_REFERENCE_WRITE'],
    	
    	// enable drag and drop associations
    	REQUIRED_PERMISSIONS_CHALLENGE_CATEGORIES_ADMIN_ASSOCIATIONS: ['ROLE_REFERENCE_WRITE'], 
    	REQUIRED_PERMISSIONS_CHALLENGE_REFERRALS_ADMIN_ASSOCIATIONS: ['ROLE_REFERENCE_WRITE'],
    	
    	REQUIRED_PERMISSIONS_CHALLENGES_ADMIN_ADD_BUTTON: ['ROLE_REFERENCE_WRITE'],
    	REQUIRED_PERMISSIONS_CHALLENGES_ADMIN_EDIT_BUTTON: ['ROLE_REFERENCE_WRITE'],
    	REQUIRED_PERMISSIONS_CHALLENGES_ADMIN_DELETE_BUTTON: ['ROLE_REFERENCE_WRITE'],  

    	REQUIRED_PERMISSIONS_CHALLENGE_REFERRALS_ADMIN_ADD_BUTTON: ['ROLE_REFERENCE_WRITE'],
    	REQUIRED_PERMISSIONS_CHALLENGE_REFERRALS_ADMIN_EDIT_BUTTON: ['ROLE_REFERENCE_WRITE'],
    	REQUIRED_PERMISSIONS_CHALLENGE_REFERRALS_ADMIN_DELETE_BUTTON: ['ROLE_REFERENCE_WRITE'],     	

    	/* journal admin */ 
    	
    	// delete associations
    	REQUIRED_PERMISSIONS_TRACK_STEP_ASSOCIATION_ADMIN_DELETE_BUTTON: ['ROLE_REFERENCE_WRITE'],   	
    	REQUIRED_PERMISSIONS_STEP_DETAIL_ASSOCIATION_ADMIN_DELETE_BUTTON: ['ROLE_REFERENCE_WRITE'],
    	
    	// enable drag and drop associations
    	REQUIRED_PERMISSIONS_TRACKS_STEPS_ADMIN_ASSOCIATIONS: ['ROLE_REFERENCE_WRITE'], 
    	REQUIRED_PERMISSIONS_STEP_DETAILS_ADMIN_ASSOCIATIONS: ['ROLE_REFERENCE_WRITE'],
    	
    	REQUIRED_PERMISSIONS_JOURNAL_STEP_ADMIN_ADD_BUTTON: ['ROLE_REFERENCE_WRITE'],
    	REQUIRED_PERMISSIONS_JOURNAL_STEP_ADMIN_EDIT_BUTTON: ['ROLE_REFERENCE_WRITE'],
    	REQUIRED_PERMISSIONS_JOURNAL_STEP_ADMIN_DELETE_BUTTON: ['ROLE_REFERENCE_WRITE'],  

    	REQUIRED_PERMISSIONS_JOURNAL_DETAIL_ADMIN_ADD_BUTTON: ['ROLE_REFERENCE_WRITE'],
    	REQUIRED_PERMISSIONS_JOURNAL_DETAIL_ADMIN_EDIT_BUTTON: ['ROLE_REFERENCE_WRITE'],
    	REQUIRED_PERMISSIONS_JOURNAL_DETAIL_ADMIN_DELETE_BUTTON: ['ROLE_REFERENCE_WRITE'],      	
    	
    	
    	/* PROFILE TOOL */
    	REQUIRED_PERMISSIONS_PROFILE_TOOL: ['ROLE_PERSON_READ'],
    	REQUIRED_PERMISSIONS_TRANSITION_BUTTON: ['ROLE_PERSON_PROGRAM_STATUS_WRITE'],
    	REQUIRED_PERMISSIONS_PRINT_HISTORY_BUTTON: ['ROLE_PERSON_READ',
    	                                            'ROLE_PERSON_JOURNAL_ENTRY_READ',
    	                                            'ROLE_PERSON_TASK_READ',
    	                                            'ROLE_PERSON_EARLY_ALERT_READ',
    	                                            'ROLE_PERSON_EARLY_ALERT_RESPONSE_READ'],
        
    	REQUIRED_PERMISSIONS_PROFILE_PRINT_CONFIDENTIALITY_AGREEMENT_BUTTON: ['ROLE_REFERENCE_READ'],

    	                                        	
        /* STUDENT INTAKE TOOL */
    	REQUIRED_PERMISSIONS_STUDENTINTAKE_TOOL: ['ROLE_STUDENT_INTAKE_READ'],
    	REQUIRED_PERMISSIONS_STUDENT_INTAKE_SAVE_BUTTON: ['ROLE_STUDENT_INTAKE_WRITE'],
    	REQUIRED_PERMISSIONS_STUDENT_INTAKE_CANCEL_BUTTON: ['ROLE_STUDENT_INTAKE_WRITE'],
    	REQUIRED_PERMISSIONS_STUDENT_INTAKE_CHALLENGE_TAB: ['ROLE_PERSON_CHALLENGE_READ'],
    	 	
    	/* ACTION PLAN TOOL */
    	REQUIRED_PERMISSIONS_ACTIONPLAN_TOOL: ['ROLE_PERSON_READ'],
    	REQUIRED_PERMISSIONS_EMAIL_ACTION_PLAN_BUTTON: ['ROLE_PERSON_TASK_READ','ROLE_PERSON_GOAL_READ'],
    	REQUIRED_PERMISSIONS_PRINT_ACTION_PLAN_BUTTON: ['ROLE_PERSON_TASK_READ','ROLE_PERSON_GOAL_READ'],
    	REQUIRED_PERMISSIONS_ACTION_PLAN_TASKS_PANEL: ['ROLE_PERSON_TASK_READ'],
    	REQUIRED_PERMISSIONS_FILTER_TASKS_BY_AUTHENTICATED_USER_CHECKBOX: ['ROLE_PERSON_TASK_READ'],
    	REQUIRED_PERMISSIONS_ADD_TASK_BUTTON: ['ROLE_PERSON_TASK_WRITE'],
    	REQUIRED_PERMISSIONS_EDIT_TASK_BUTTON: ['ROLE_PERSON_TASK_WRITE'],
    	REQUIRED_PERMISSIONS_CLOSE_TASK_BUTTON: ['ROLE_PERSON_TASK_WRITE'],
    	REQUIRED_PERMISSIONS_DELETE_TASK_BUTTON: ['ROLE_PERSON_TASK_DELETE'],
    	REQUIRED_PERMISSIONS_ACTION_PLAN_GOALS_PANEL: ['ROLE_PERSON_TASK_READ'],
    	REQUIRED_PERMISSIONS_ADD_GOAL_BUTTON: ['ROLE_PERSON_GOAL_WRITE'],
    	REQUIRED_PERMISSIONS_EDIT_GOAL_BUTTON: ['ROLE_PERSON_GOAL_WRITE'],
    	REQUIRED_PERMISSIONS_DELETE_GOAL_BUTTON: ['ROLE_PERSON_GOAL_DELETE'],
    	REQUIRED_PERMISSIONS_ACTION_PLAN_STRENGTHS_PANEL: ['ROLE_PERSON_READ'],
    	REQUIRED_PERMISSIONS_ACTION_PLAN_STRENGTHS_FIELD: ['ROLE_PERSON_WRITE'],
    	REQUIRED_PERMISSIONS_SAVE_STRENGTHS_BUTTON: ['ROLE_PERSON_WRITE'],
    	                                            
    	/* JOURNAL TOOL */
    	REQUIRED_PERMISSIONS_JOURNAL_TOOL:['ROLE_PERSON_JOURNAL_ENTRY_READ'],
    	REQUIRED_PERMISSIONS_ADD_JOURNAL_ENTRY_BUTTON: ['ROLE_PERSON_JOURNAL_ENTRY_WRITE'],
    	REQUIRED_PERMISSIONS_EDIT_JOURNAL_ENTRY_BUTTON: ['ROLE_PERSON_JOURNAL_ENTRY_WRITE'],
    	REQUIRED_PERMISSIONS_DELETE_JOURNAL_ENTRY_BUTTON: ['ROLE_PERSON_JOURNAL_ENTRY_DELETE'],
    	
    	/* EARLY ALERT TOOL */
    	REQUIRED_PERMISSIONS_EARLYALERT_TOOL:['ROLE_PERSON_EARLY_ALERT_READ','ROLE_PERSON_EARLY_ALERT_RESPONSE_READ',],
    	
    	/* STUDENT INFORMATION SYSTEM TOOL */
     	REQUIRED_PERMISSIONS_STUDENTINFORMATIONSYSTEM_TOOL:['ROLE_PERSON_READ']
    },
    
    /**
     * Returns true if the user has access permissions 
     * for a specified object type.
     * Example use case:
     * 
     *    if ( authenticatedPerson.hasAccess('PRINT_HISTORY_BUTTON') )
     *    {
     *       // User can access the print history button
     *    }
     * 
     */
    hasAccess: function( objectName ){
    	var col = this.get('objectPermissionsCollection');
    	var permission = col.findBy(function(item,key){
    		if(objectName==item.get('name'))
    		{
    			return true;
    		}
    	});
    	return ((permission != null)? permission.get('hasAccess') : false);
    },
 
    /**
     * Creates a MixedCollection of ObjectPermission records containing the names
     * of view items to secure in the system. Each object contains a hasAccess property
     * set to true/false depending on whether or not the required permissions exist in the users
     * granted permissions. The objects reference the names of the static properties in 
     * this class and can be used to determine if view items are available in the system.
     * See the hasAccess method of this class for additional details regarding use of 
     * the ObjectPermissions MixedCollection. 
     */
    setObjectPermissions: function(){
    	var me=this;
    	var col = new Ext.util.MixedCollection();
    	var re = new RegExp(/REQUIRED_PERMISSIONS/);
    	var objectPermission, hasAccess, requiredPermissions;
    	for (prop in Ssp.model.AuthenticatedPerson)
    	{
    		if( prop.search(re) != -1)
    		{
    			requiredPermissions = Ssp.model.AuthenticatedPerson[prop];
    			hasAccess = me.hasRequiredPermissions(requiredPermissions);
    			objectPermission = new Ssp.model.ObjectPermission;
    			objectPermission.set('name',prop.replace('REQUIRED_PERMISSIONS_',""));
    			objectPermission.set('hasAccess',hasAccess);
    			col.add( objectPermission );
    		}	
    	}
    	me.set('objectPermissionsCollection',col);
    },    
    
    /**
     * Determines if a user has access to a provided array of permissions.
     * 
     * @arguments
     *  arrRequiredPermissions - an array of permissions to test against the granted permissions for this user.
     *  
     *  return true if all of the permissions exist in the user's record
     */
    hasRequiredPermissions: function( arrRequiredPermissions ){
        var access = new Array();
    	Ext.Array.every(arrRequiredPermissions,function(permission){
   		   if ( this.hasPermission( permission ) == false )
   		   {
   			 access.push( false ); 
   		   }
   	    },this);
    	
        return ((access.length==0)? true : false); 
    },
    
    /**
     * Determines if a user has access to the provided permission.
     * Tests against the granted permissions for this user.
     * 
     * @arguments
     *  - permission - a permission
     *  
     *  return true if the permission exists in the user's record
     */
    hasPermission: function( permission ){
   	 return Ext.Array.contains( this.get('permissions'), permission );
    },

    /**
     * Determines if a user has a supplied confidentiality level by id.
     * Tests against the granted confidentiality levels for this user.
     *      
     * @arguments
     *  id - confidentialityLevelId
     *  
     *  return true if the confidentiality level exists in the user's record
     */
    hasConfidentialityLevel: function( id ){
    	var me=this;
    	var levels = new Array();
    	var confidentialityLevels = me.get('confidentialityLevels');
    	Ext.Array.each(confidentialityLevels ,function( item ){
    	   if ( item.id == id )
		   {
			 levels.push(id); 
		   }
	    },me);
   	 	return ((levels.length==0)? false : true);
    },    
    
    /**
     * Apply a filter function to a supplied store
     * to limit the available items to the confidentiality levels within
     * a users authenticated level of confidentiality.
     */
    applyConfidentialityLevelsFilter: function( store ){
		var me=this;
    	var filtersArr = [];
		var filterAuthenticatedFunc;
		filterAuthenticatedFunc = new Ext.util.Filter({
		    filterFn: function(item) {
				return this.hasConfidentialityLevel( item.get('id') ); 
			},
			scope: me
		});
		filtersArr.push( filterAuthenticatedFunc );
		store.filter( filtersArr );
    },
    
    /**
     * Provides an unauthorized access alert message.
     */
    showUnauthorizedAccessAlert: function(){
    	Ext.Msg.alert(this.getUnauthorizedAccessAlertTitle(), this.getUnauthorizedAccessAlertMessage() );
    }
});