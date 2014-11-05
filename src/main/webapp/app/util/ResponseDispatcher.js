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
// Use this to trigger additional callbacks as side-effects of completion of
// an arbitrary collection of named success/failure callbacks. The latter are
// usually async AJAX callbacks. E.g. a controller might need to issue several
// AJAX calls, then do something when all that data has been loaded (maybe just
// clear a spinner). Currently can register side-effect callbacks that either
// fire after every named callback or after at least one of each named callbacks
// has fired. Does not cache the results of the named callbacks. That could
// be added in future revisions, but the assumption is that the client is
// capable of caching those results since they are passed to client-registered
// callbacks.
//
// There is no sophistication in the callback mechanism, e.g. all "failures"
// go to the same callback... there is no statuscode-based routing.
Ext.define('Ssp.util.ResponseDispatcher',{
    extend: 'Ext.Component',
    config: {
        successCallbacks: {},
        failureCallbacks: {},
        remainingOpNames: [],
        afterAnyOp: null,
        afterLastOp: null
    },
    initComponent: function() {
        return this.callParent( arguments );
    },

    // Returns a function that is typically useful for registering with
    // of our "service" classes as a success callback.
    setSuccessCallback: function(opName, callback, callbackScope) {
        var me = this;
        me.getSuccessCallbacks()[opName] = me.newScopedCallback(callback, callbackScope);
        return function(response) {
            me.onSuccess(opName, response);
        };
    },

    // Returns a function that is typically useful for registering with
    // of our "service" classes as a failure callback.
    setFailureCallback: function(opName, callback, callbackScope) {
        var me = this;
        me.getFailureCallbacks()[opName] = me.newScopedCallback(callback, callbackScope);
    },

    setAfterAnyCallback: function(callback, callbackScope) {
        var me = this;
        me.setAfterAnyOp(me.newScopedCallback(callback, callbackScope));
    },
    setAfterLastCallback: function(callback, callbackScope) {
        var me = this;
        me.setAfterLastOp(me.newScopedCallback(callback, callbackScope));
    },
    newScopedCallback: function(callback, callbackScope) {
        if (!(callback)) {
            return null;
        }
        return { callback: callback, callbackScope: callbackScope };
    },
    invokeScopedCallback: function(callback, response, opName) {
        if ( callback ) {
            callback.callback.apply(callback.callbackScope, [response, callback.callbackScope, opName]);
        }
    },
    onSuccess: function(opName, response) {
        var me = this;
        var callback = me.getSuccessCallbacks()[opName];
        me.onResponse(opName, response, callback);
    },
    onFailure: function(opName, response) {
        var me = this;
        var callback = me.getFailureCallbacks()[opName];
        me.onResponse(opName, response, callback);
    },
    onResponse: function(opName, response, callback) {
        var me = this;
        var remainingOps = me.getRemainingOpNames();
        if ( remainingOps ) {
            me.setRemainingOpNames(Ext.Array.filter(remainingOps, function(element) {
                return element !== opName;
            }, me));
            remainingOps = me.getRemainingOpNames();
        }

        me.invokeScopedCallback(callback, response, opName);
        me.invokeScopedCallback(me.getAfterAnyOp(), response, opName);
        if (!(remainingOps) || remainingOps.length === 0 ) {
            me.invokeScopedCallback(me.getAfterLastOp(), response, opName);
        }
    }
});