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
import org.jasig.ssp.model.NotificationRecipient;

import java.util.Collection;
import java.util.Set;


public class NotificationRecipientTO extends AbstractAuditableTO<NotificationRecipient>
	implements TransferObject<NotificationRecipient> {

	private PersonLiteTO recipient;
    private String sspRole;

	public NotificationRecipientTO() {
		super();
	}

	public NotificationRecipientTO(final NotificationRecipient model) {
		super();
		from(model);
	}

	@Override
	public void from(NotificationRecipient notificationRecipient) {
        super.from(notificationRecipient);

        if (notificationRecipient != null) {
            if (notificationRecipient.getPerson() != null) {
                this.setRecipient(new PersonLiteTO(notificationRecipient.getPerson().getId(), notificationRecipient.getPerson().getFirstName(), notificationRecipient.getPerson().getLastName()));
            }

            if (notificationRecipient.getSspRole() != null) {
                this.setSspRole(notificationRecipient.getSspRole());
            }
        }
    }

    public static Set<NotificationRecipientTO> toTOSet(
            final Collection<NotificationRecipient> models) {
        final Set<NotificationRecipientTO> tObjects = Sets.newHashSet();
        for (NotificationRecipient model : models) {
            tObjects.add(new NotificationRecipientTO(model));
        }
        return tObjects;
	}

    public PersonLiteTO getRecipient() {
        return recipient;
    }

    public void setRecipient(PersonLiteTO recipient) {
        this.recipient = recipient;
    }

    public String getSspRole() {
        return sspRole;
    }

    public void setSspRole(String sspRole) {
        this.sspRole = sspRole;
    }
}
