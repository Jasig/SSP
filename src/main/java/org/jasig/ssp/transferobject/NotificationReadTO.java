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
package org.jasig.ssp.transferobject;

import com.google.common.collect.Sets;
import org.jasig.ssp.model.NotificationRead;

import java.util.Collection;
import java.util.Set;


public class NotificationReadTO extends AbstractAuditableTO<NotificationRead>
	implements TransferObject<NotificationRead> {

	private PersonLiteTO person;

	public NotificationReadTO() {
		super();
	}

	public NotificationReadTO(final NotificationRead model) {
		super();
		from(model);
	}

	@Override
	public void from(NotificationRead notificationRead) {
        super.from(notificationRead);
        if (notificationRead != null) {
            if (notificationRead.getPerson() != null) {
                this.setPerson(new PersonLiteTO(notificationRead.getPerson().getId(), notificationRead.getPerson().getFirstName(), notificationRead.getPerson().getLastName()));
            }
        }
    }
    public static Set<NotificationReadTO> toTOSet(
            final Collection<NotificationRead> models) {
        final Set<NotificationReadTO> tObjects = Sets.newHashSet();
        for (NotificationRead model : models) {
            tObjects.add(new NotificationReadTO(model));
        }
        return tObjects;
    }

    public PersonLiteTO getPerson() {
        return person;
    }

    public void setPerson(PersonLiteTO person) {
        this.person = person;
    }
}
