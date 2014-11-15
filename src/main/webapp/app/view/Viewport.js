/*
 * Licensed to Apereo under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Apereo licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
Ext.define('Ssp.view.Viewport',{
	extend: 'Ext.container.Container',
	requires: ['Ext.EventManager'],
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.ViewportViewController',
    inject: {
    	parentDivId: 'sspParentDivId',
    	renderFullScreen: 'renderSSPFullScreen'
    },
    layout: 'fit',
    id: 'sspView',
    alias: 'widget.sspview',

    preserveElOnDestroy: true,

    initComponent : function() {
        var me = this,
            html = document.body.parentNode,
            el;

        // Init the Main Shell for the application
        Ext.apply(this, {items: [{xtype:'mainview'}]}); 
        
        // Get the DOM disruption over with before the Viewport renders and begins a layout
        Ext.getScrollbarSize();
        
        // Clear any dimensions, we will size later on
        me.width = me.height = undefined;

        me.callParent(arguments);
        if (me.renderFullScreen==true)
        {
           Ext.fly(html).addCls(Ext.baseCSSPrefix + 'viewport');
        }
        if (me.autoScroll) {
            //delete me.autoScroll;
            //Ext.fly(html).setStyle('overflow', 'auto');
        }
        if (me.renderFullScreen==true)
        {
            // me.el = el = Ext.getBody();
            me.el = el = Ext.getElementById( this.parentDivId );
        }else{
        	me.el = el = Ext.getElementById( this.parentDivId );
        }
        el.setHeight = Ext.emptyFn;
        el.setWidth = Ext.emptyFn;
        el.setSize = Ext.emptyFn;
        //el.dom.scroll = 'no';
        me.allowDomMove = false;
        me.renderTo = me.el;
     },
    
    onRender: function() {
        var me = this;
        me.callParent(arguments);

        // Important to start life as the proper size (to avoid extra layouts)
        // But after render so that the size is not stamped into the body

        if (this.renderFullScreen==true)
        {
            me.width = Ext.Element.getViewportWidth()-12;
            me.height = Ext.Element.getViewportHeight()-145;
        }else{
        	me.width = Ext.Element.getViewportWidth()-35; // me.el.getViewSize().width;
            me.height = me.el.getViewSize().height; //Ext.Element.getViewportHeight()-160;  
        }
    },

    afterFirstLayout: function() {
        var me = this;

        me.callParent(arguments);
        setTimeout(function() {
            Ext.EventManager.onWindowResize(me.fireResize, me);
        }, 1);
    },

    fireResize : function(width, height){
        // In IE we can get resize events that have our current size, so we ignore them
        // to avoid the useless layout...
    	var me = this;
    	// if renderFullScreen is configured then size to the viewport
    	// otherwise
    	// resize the container width and leave the height alone
    	// this will maintain the container size to the height as
    	// originally drawn on the page, but the width will be flexible
    	if (this.renderFullScreen==true)
    	{
    		newWidth = Ext.Element.getViewportWidth()-12;
    		newHeight = Ext.Element.getViewportHeight()-145;
    	}else{
    		newWidth = Ext.Element.getViewportWidth()-35; // me.width+(width-me.el.getWidth());
    		newHeight = me.el.getViewSize().height; //Ext.Element.getViewportHeight()-160; // me.height;
    	}
    		
    	if (width != me.width || height != me.height) {
	        me.setSize( newWidth, newHeight);
    	}
    }
});