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
package org.jasig.ssp.transferobject.reference;

import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.reference.ExternalView;
import org.jasig.ssp.transferobject.TransferObject;
import java.util.Collection;
import java.util.List;
import java.util.UUID;


public class ExternalViewTO extends AbstractReferenceTO<ExternalView> implements TransferObject<ExternalView> {

    private String url;
    private String variableStudentIdentifier;
    private String variableUserIdentifier;
    private boolean isEmbedded;


    //SSP UserId Fields
    private transient static String USERNAME = "username";
    private transient static String SCHOOL_ID = "schoolId";
    private transient static String PRIMARY_EMAIL_ADDRESS = "primaryEmailAddress";
    //URL Variables
    private transient static String USER_ID_URL_VAR = "USERID";
    private transient static String STUDENT_ID_URL_VAR = "STUDENTID";


    public ExternalViewTO() {
		super();
	}

    public ExternalViewTO(final ExternalView model) {
        super();
        from(model);
    }

    public ExternalViewTO(final UUID id, final String name, final String description) {
        super(id, name, description);
    }

    public ExternalViewTO(final ExternalView model, final Person currentUser, final Person selectedStudent) {
        this.from(model);
        setUrlCurrentUser(currentUser);
        setUrlSelectedStudent(selectedStudent);
    }

    @Override
    public final void from(final ExternalView model) {
        super.from(model);
        setEmbedded(model.isEmbedded());
        setUrl(model.getUrl());
        setVariableStudentIdentifier(model.getVariableStudentIdentifier());
        setVariableUserIdentifier(model.getVariableUserIdentifier());
    }

    public static List<ExternalViewTO> toTOList(final Collection<ExternalView> models) {
        final List<ExternalViewTO> tObjects = Lists.newArrayList();

        for (final ExternalView model : models) {
            tObjects.add(new ExternalViewTO(model));
        }

        return tObjects;
    }

    public boolean isEmbedded() {
        return isEmbedded;
    }

    public void setEmbedded(boolean isEmbedded) {
        this.isEmbedded = isEmbedded;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVariableStudentIdentifier() {
        return variableStudentIdentifier;
    }

    public void setVariableStudentIdentifier(String variableStudentIdentifier) {
        this.variableStudentIdentifier = variableStudentIdentifier;
    }

    public String getVariableUserIdentifier() {
        return variableUserIdentifier;
    }

    public void setVariableUserIdentifier(String variableUserIdentifier) {
        this.variableUserIdentifier = variableUserIdentifier;
    }

    public void setUrlCurrentUser(final Person currentUser) {
        if (currentUser != null && StringUtils.isNotBlank(variableUserIdentifier) && StringUtils.isNotBlank(url)) {

            if (StringUtils.equals(variableUserIdentifier, USERNAME)) {
                setUrl(url.replace(USER_ID_URL_VAR, currentUser.getUsername()));

            } else if (StringUtils.equals(variableUserIdentifier, SCHOOL_ID)) {
                setUrl(url.replace(USER_ID_URL_VAR, currentUser.getSchoolId()));

            } else if (StringUtils.equals(variableUserIdentifier, PRIMARY_EMAIL_ADDRESS)) {
                setUrl(url.replace(USER_ID_URL_VAR, currentUser.getPrimaryEmailAddress()));

            } else {
                //do nothing
            }
        }
    }

    public void setUrlSelectedStudent(final Person selectedStudent) {
        if (selectedStudent != null && StringUtils.isNotBlank(variableStudentIdentifier) && StringUtils.isNotBlank(url)) {

            if (StringUtils.equals(variableStudentIdentifier, USERNAME)) {
                setUrl(url.replace(STUDENT_ID_URL_VAR, selectedStudent.getUsername()));

            } else if (StringUtils.equals(variableStudentIdentifier, SCHOOL_ID)) {
                setUrl(url.replace(STUDENT_ID_URL_VAR, selectedStudent.getSchoolId()));

            } else if (StringUtils.equals(variableStudentIdentifier, PRIMARY_EMAIL_ADDRESS)) {
                setUrl(url.replace(STUDENT_ID_URL_VAR, selectedStudent.getPrimaryEmailAddress()));

            } else {
                //do nothing
            }
        }
    }
}