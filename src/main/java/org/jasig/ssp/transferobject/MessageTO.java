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

import org.jasig.ssp.model.Message;
import java.util.Date;

public class MessageTO extends AbstractAuditableTO<Message>
	implements TransferObject<Message> {

	private String subject;
	private String carbonCopy;
	private String recipientEmailAddress;
	private String body;
	private PersonLiteTO sender;
    private String sentToAddresses;
    private String sentCcAddresses;
    private String sentBccAddresses;
    private String sentFromAddress;
    private String sentReplyToAddress;
    private Date sentDate;
    private Integer retryCount;

	
	public MessageTO() {
		super();
	}
	
	public MessageTO(final Message model) {
		super();
		from(model);
	}

	@Override
	public void from(Message model) {
		super.from(model);
		this.subject = model.getSubject();
		this.recipientEmailAddress = model.getRecipientEmailAddress();
		this.carbonCopy = model.getCarbonCopy();
		this.body = model.getBody();
		if (model.getSender() != null) {
			this.sender = new PersonLiteTO(model.getSender().getId(),model.getSender().getFirstName(),model.getSender().getLastName());
		}
        this.sentToAddresses = model.getSentToAddresses();
        this.sentCcAddresses = model.getSentCcAddresses();
        this.sentBccAddresses = model.getSentBccAddresses();
        this.sentFromAddress = model.getSentFromAddress();
        this.sentReplyToAddress = model.getSentReplyToAddress();
        this.sentDate = model.getSentDate();
        this.retryCount = model.getRetryCount();
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setRecipientEmailAddress(String recipientEmailAddress) {
		this.recipientEmailAddress = recipientEmailAddress;
	}
	
	public String getRecipientEmailAddress() {
		return this.recipientEmailAddress;
	}
	
	public void setCarbonCopy(String carbonCopy) {
		this.carbonCopy = carbonCopy;
	}
	
	public String getCarbonCopy() {
		return this.carbonCopy;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public PersonLiteTO getSender() {
		return sender;
	}

	public void setSender(PersonLiteTO sender) {
		this.sender = sender;
	}

    public String getSentToAddresses () {
        return sentToAddresses;
    }

    public void setSentToAddresses (String sentToAddresses) {
        this.sentToAddresses = sentToAddresses;
    }

    public String getSentCcAddresses () {
        return sentCcAddresses;
    }

    public void setSentCcAddresses (String sentCcAddresses) {
        this.sentCcAddresses = sentCcAddresses;
    }

    public String getSentBccAddresses () {
        return sentBccAddresses;
    }

    public void setSentBccAddresses (String sentBccAddresses) {
        this.sentBccAddresses = sentBccAddresses;
    }

    public String getSentFromAddress () {
        return sentFromAddress;
    }

    public void setSentFromAddress (String sentFromAddress) {
        this.sentFromAddress = sentFromAddress;
    }

    public String getSentReplyToAddress () {
        return sentReplyToAddress;
    }

    public void setSentReplyToAddress (String sentReplyToAddress) {
        this.sentReplyToAddress = sentReplyToAddress;
    }

    public Date getSentDate () {
        return sentDate;
    }

    public void setSentDate (Date sentDate) {
        this.sentDate = sentDate;
    }

    public Integer getRetryCount () {
        return retryCount;
    }

    public void setRetryCount (Integer retryCount) {
        this.retryCount = retryCount;
    }
}
