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
package org.jasig.ssp.model.reference;

import org.jasig.ssp.model.Auditable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;


/**
 * External View reference object used by External View Tool to display outside webpages.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class ExternalView extends AbstractReference implements Auditable {

	private static final long serialVersionUID = -7806397445389124704L;


    private boolean isEmbedded;

    @Column(nullable = true, length = 2084)
    @Size(max = 2084)
	private String url;

    @Column(nullable = true, length = 35)
    @Size(max = 35)
    private String variableStudentIdentifier;

    @Column(nullable = true, length = 35)
    @Size(max = 35)
    private String variableUserIdentifier;


	/**
	 * Constructor
	 */
	public ExternalView() {
		super();
	}

	/**
	 * Constructor
	 *
	 * @param id
	 *            Identifier; required
	 */

	public ExternalView(@NotNull final UUID id) {
		super(id);
	}


	/**
	 * Constructor
	 *
	 * @param id
	 *            Identifier; required
	 * @param name
	 *            Name; required; max 80 characters
	 * @param description
	 *            Description; max 64000 characters
	 */
	public ExternalView(@NotNull final UUID id, @NotNull final String name,
                        final String description) {
		super(id, name, description);
	}
	
	@Override
	protected int hashPrime() {
		return 373;
	}
	
	@Override
	public int hashCode() { 
		int result = hashPrime() * super.hashCode();

        result *= hashField("variableUserIdentifier", getVariableUserIdentifier());
        result *= hashField("variableStudentIdentifier", getVariableStudentIdentifier());
        result *= hashField("externalURL", getUrl());
        result *= isEmbedded() ? 3 : 5;

        return result;
	}

    public boolean isEmbedded() {
        return isEmbedded;
    }

    public void setEmbedded(boolean embedded) {
        isEmbedded = embedded;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String externalURL) {
        this.url = externalURL;
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
}