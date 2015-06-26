/**
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

package org.jasig.ssp.util.hibernate;


import org.hibernate.engine.spi.CollectionEntry;
import org.hibernate.envers.configuration.AuditConfiguration;
import org.hibernate.envers.event.BaseEnversCollectionEventListener;
import org.hibernate.event.spi.PreCollectionRemoveEvent;
import org.hibernate.event.spi.PreCollectionRemoveEventListener;

public class EnversPreCollectionRemoveEventListenerImpl extends BaseEnversCollectionEventListener
        implements PreCollectionRemoveEventListener {

    protected EnversPreCollectionRemoveEventListenerImpl(AuditConfiguration enversConfiguration) {
        super( enversConfiguration );
    }

    @Override
    public void onPreRemoveCollection(PreCollectionRemoveEvent event) {
        CollectionEntry collectionEntry = getCollectionEntry( event );
        if ( collectionEntry != null && collectionEntry.getLoadedPersister() != null && !collectionEntry.getLoadedPersister().isInverse() ) {
            onCollectionAction( event, null, collectionEntry.getSnapshot(), collectionEntry );
        }
    }
}
