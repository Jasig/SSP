Ext.define('Ssp.view.security.Login', {
	extend: 'Ext.form.Panel',
	alias : 'widget.Login',
	id: 'Login',
	width: '100%',
	height: '100%',
    labelWidth:80, 
    frame:true, 
    defaultType:'textfield',
	monitorValid:true,

	// Specific attributes for the text fields for username / password. 
	// The "name" attribute defines the name of variables sent to the server.
    items:[{ 
            fieldLabel:'Username', 
            name:'loginUsername',
            value: 'demo',
            allowBlank:false 
        },{ 
            fieldLabel:'Password', 
            name:'loginPassword', 
            inputType:'password', 
            value: 'demo',
            allowBlank:false 
        }],
 
    buttons:[{ 
            text:'Login',
            id: 'securityLoginButton',
            formBind: true	  
            /*
            handler:function(){ 
                login.getForm().submit({ 
                    method:'POST', 
                    waitTitle:'Connecting', 
                    waitMsg:'Sending data...',
 
						// Functions that fire (success or failure) when the server responds. 
						// The one that executes is determined by the 
						// response that comes from login.asp as seen below. The server would 
						// actually respond with valid JSON, 
						// something like: response.write "{ success: true}" or 
						// response.write "{ success: false, errors: { reason: 'Login failed. Try again.' }}" 
						// depending on the logic contained within your server script.
						// If a success occurs, the user is notified with an alert messagebox, 
						// and when they click "OK", they are redirected to whatever page
						// you define as redirect. 
 
                        success:function(){ 
                        	Ext.Msg.alert('Status', 'Login Successful!', function(btn, text){
			   if (btn == 'ok'){
	                        var redirect = 'index.html'; 
		                        window.location = redirect;
                                   }
			        });
                        },
 
						// Failure function, see comment above re: success and failure. 
					// You can see here, if login fails, it throws a messagebox
					// at the user telling him / her as much.  
		 
                    failure:function(form, action){ 
                        if(action.failureType == 'server'){ 
                            obj = Ext.util.JSON.decode(action.response.responseText); 
                            Ext.Msg.alert('Login Failed!', obj.errors.reason); 
                        }else{ 
                            Ext.Msg.alert('Warning!', 'Authentication server is unreachable : ' + action.response.responseText); 
                        } 
                        login.getForm().reset(); 
                    } 
                }); 
                
            } 
            */
        }],
	
	initComponent: function() {	
		this.superclass.initComponent.call(this, arguments);
	}	
});