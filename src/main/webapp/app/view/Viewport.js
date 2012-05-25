Ext.define('Ssp.view.Viewport',{
	extend: 'Ext.container.Container',
	requires: ['Ext.EventManager'],
    renderTo: 'sspApp',
    layout: 'fit',
    id: 'sspView',
    alias: 'widget.sspview',

    // Privatize config options which, if used, would interfere with the
    // correct operation of the Viewport as the sole manager of the
    // layout of the document body.

    /**
     * @cfg {String/HTMLElement/Ext.Element} applyTo
     * @private
     */

    /**
     * @cfg {Boolean} allowDomMove
     * @private
     */

    /**
     * @cfg {Boolean} hideParent
     * @private
     */

    /**
     * @cfg {String/HTMLElement/Ext.Element} renderTo
     * Always renders to document body.
     * @private
     */

    /**
     * @cfg {Number} height
     * Sets itself to viewport width.
     * @private
     */

    /**
     * @cfg {Number} width
     * Sets itself to viewport height.
     * @private
     */

    /**
     * @cfg {Boolean} deferHeight
     * @private
     */

    /**
     * @cfg {Boolean} monitorResize
     * @private
     */

    preserveElOnDestroy: true,

    initComponent : function() {
        var me = this,
            html = document.body.parentNode,
            el;

        Ext.applyIf(this, {items: [{xtype:'Main'}]});
        
        // Get the DOM disruption over with before the Viewport renders and begins a layout
        Ext.getScrollbarSize();
        
        // Clear any dimensions, we will size later on
        me.width = me.height = undefined;

        me.callParent(arguments);
        Ext.fly(html).addCls(Ext.baseCSSPrefix + 'viewport');
        if (me.autoScroll) {
            delete me.autoScroll;
            Ext.fly(html).setStyle('overflow', 'auto');
        }
        me.el = el = Ext.getElementById('sspApp');
        //el.setHeight = Ext.emptyFn;
        //el.setWidth = Ext.emptyFn;
        //el.setSize = Ext.emptyFn;
        //el.dom.scroll = 'no';
        me.allowDomMove = false;
        me.renderTo = me.el;
     },
    
    onRender: function() {
        var me = this;
        me.callParent(arguments);

        // Important to start life as the proper size (to avoid extra layouts)
        // But after render so that the size is not stamped into the body
        me.width = Ext.Element.getViewportWidth();
        me.height = Ext.Element.getViewportHeight();
        //me.width = me.el.width;
        //me.height = me.el.height;
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
        if (width != this.width || height != this.height) {
            this.setSize(width, height);
        }
    }
});