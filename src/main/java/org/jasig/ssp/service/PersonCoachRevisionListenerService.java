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

package org.jasig.ssp.service;


import org.hibernate.envers.RevisionListener;
import org.jasig.ssp.model.AuditPerson;
import org.jasig.ssp.model.PersonCoachRevisionEntity;
import org.jasig.ssp.security.SspUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.Date;


/**
 * For auditing Coach changes, this adds to the default fields logged when audit field changes in Person model
 */
@Service
public class PersonCoachRevisionListenerService implements RevisionListener, ApplicationContextAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonCoachRevisionListenerService.class);

    private transient ApplicationContext applicationContext;

    /**
     * Set the Spring container context
     */
    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void newRevision(Object revisionEntity) {
        try {
            final PersonCoachRevisionEntity revEntity = (PersonCoachRevisionEntity) revisionEntity;
            final SspUser sspUser = (SspUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            revEntity.setModifiedDate(new Date());
            revEntity.setModifiedBy(new AuditPerson(sspUser.getPerson().getId()));

        } catch (ClassCastException | NullPointerException e) {
            LOGGER.warn("Warning couldn't find personCoachRevisionEntity or currentUser during coach change! " + e);
        }
    }
}
