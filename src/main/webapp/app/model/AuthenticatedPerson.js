Ext.define('Ssp.model.AuthenticatedPerson', {
    extend: 'Ssp.model.AbstractBase',
    fields: [{name:'permissions', type:'auto', defaultValue: null},
    		 {name:'confidentialityLevels', type:'auto', defaultValue: null},
    		 {name:'objectPermissionsCollection', type:'auto'}],
    statics: {  	
    	REQUIRED_PERMISSIONS_PRINT_HISTORY_BUTTON: ['ROLE_PERSON_READ',
    	                                            'ROLE_PERSON_JOURNAL_ENTRY_READ',
    	                                            'ROLE_PERSON_TASK_READ',
    	                                            'ROLE_PERSON_EARLY_ALERT_READ',
    	                                            'ROLE_PERSON_EARLY_ALERT_RESPONSE_READ'],   	
    	REQUIRED_PERMISSIONS_PROFILE_TOOL:['ROLE_PERSON_READ'],
    	REQUIRED_PERMISSIONS_STUDENTINTAKE_TOOL:['ROLE_PERSON_HELLO'],
    	REQUIRED_PERMISSIONS_ACTIONPLAN_TOOL:['ROLE_PERSON_READ'],
    	REQUIRED_PERMISSIONS_JOURNAL_TOOL:['ROLE_PERSON_READ'],
    	REQUIRED_PERMISSIONS_EARLYALERT_TOOL:['ROLE_PERSON_READ'],
     	REQUIRED_PERMISSIONS_STUDENTINFORMATIONSYSTEM_TOOL:['ROLE_PERSON_READ']
    },
    
    /**
     * Returns true if the user has access permissions 
     * for a specified object type.
     */
    getObjectPermissions: function( objectName ){
    	/*
    	return col.findBy(function(item,key){
    		if (objectName==item.get('name'))
    		{
    			return true;
    		}
    	},scope:this);
    	*/
    },
 
    /**
     * Set object permissions. Sets the permission property for
     * any object in the system to true/false depending on whether
     * or not the required permissions exist in the users assigned permissions.
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
    			hasAccess = me.hasPermissions(requiredPermissions);
    			objectPermission = new Ssp.model.ObjectPermission;
    			objectPermission.set('name',prop.replace('REQUIRED_PERMISSIONS',""));
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
     *  arrPermissions - an array of permissions to test against the granted permissions for this user.
     *  
     *  return true if all of the permissionsToTest exist in the user's record
     */
    hasPermissions: function( arrPermissions ){
   	   return Ext.Array.every(arrPermissions,function(permission){
   		   return this.hasPermission( permission );
   	   },this);
    },
    
    /**
     * Determines if a user has access to the provided permission.
     * @arguments
     *  - permission - a permission
     *  to test against the granted permissions for this user.
     *  
     *  return true if the permission exists in the user's record
     */
    hasPermission: function( permission ){
   	 return Ext.Array.contains( this.get('permissions'), permission );
    }
});