Ext.define('Ssp.model.AuthenticatedPerson', {
    extend: 'Ssp.model.AbstractBase',
    fields: [{name: 'permissions', type:'auto', defaultValue: null},
    		 {name: 'confidentialityLevels', type:'auto', defaultValue: null}],
    		 
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