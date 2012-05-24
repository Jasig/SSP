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
        Ext.applyIf(this, {
        	activeItem: 0,
        	
        	dockedItems: [{
	               xtype: 'toolbar',
	               dock: 'bottom',
	               items: [{
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
        	    id: 'card-0',
        	    html: 'Step 1'
        	},{
        	    id: 'card-1',
        	    html: 'Step 2'
        	},{
        	    id: 'card-2',
        	    html: 'Step 3'
        	}]
        });
        return this.callParent(arguments);
	}
});