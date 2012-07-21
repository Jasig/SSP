Ext.define('Ssp.view.admin.forms.campus.DefineCampus',{
	extend: 'Ext.panel.Panel',
	alias : 'widget.definecampus',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.campus.DefineCampusViewController',
	title: 'Define a Campus',
	height: '100%',
	width: '100%',
	layout:'card',
	initComponent: function() {
		var me=this;
        Ext.applyIf(me, {
        	activeItem: 0,
        	
        	dockedItems: [{
	               xtype: 'toolbar',
	               dock: 'top',
	               items: [{
	                   text: 'Cancel',
	                   xtype: 'button',
	                   itemId: 'cancelCampusEditorButton'
	               },{
	                   text: 'Prev',
	                   xtype: 'button',
	                   action: 'prev',
	                   itemId: 'prevButton'
	               }, '-', {
	                   text: 'Next',
	                   xtype: 'button',
	                   action: 'next',
	                   itemId: 'nextButton'
	               }, '-', {
	                   text: 'Finish',
	                   xtype: 'button',
	                   action: 'finish',
	                   itemId: 'finishButton'
	               }]
	           }],
        	
        	items: [{
        	    xtype:'editcampus',
        	    flex: 1
        	},{
        		xtype:'campusearlyalertroutingsadmin',
        	    flex: 1
        	}]
        });
        return me.callParent(arguments);
	}
});