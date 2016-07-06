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

import org.apache.commons.lang.StringUtils;
import org.jasig.ssp.model.Message;
import java.util.Date;

public class MessageTO extends AbstractAuditableTO<Message>
	implements TransferObject<Message> {

	private String subject;
	private String body;
	private PersonLiteTO sender;
    private String recipientEmailAddress;
    private String carbonCopy;
    private String sentToAddresses;
    private String sentCcAddresses;
    private String sentBccAddresses;
    private String sentFromAddress;
    private String sentReplyToAddress;
    private Date sentDate;
    private Integer retryCount;
    private boolean sent;

	
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
		this.body = model.getBody();
		if (model.getSender() != null) {
			this.sender = new PersonLiteTO(model.getSender().getId(),model.getSender().getFirstName(),model.getSender().getLastName());
		}
		this.recipientEmailAddress = cleanEmailAddress(model.getRecipientEmailAddress());
		if (model.getRecipient() != null && StringUtils.isNotBlank(model.getRecipient().getPrimaryEmailAddress())) {
            if (StringUtils.isNotBlank(this.recipientEmailAddress)) {
                this.recipientEmailAddress = this.recipientEmailAddress + ", ";
            }
            this.recipientEmailAddress = this.recipientEmailAddress +
                    cleanEmailAddress(model.getRecipient().getPrimaryEmailAddress());
        }
        this.carbonCopy = cleanEmailAddress(model.getCarbonCopy());
        this.sentToAddresses = cleanEmailAddress(model.getSentToAddresses());
        this.sentCcAddresses = cleanEmailAddress(model.getSentCcAddresses());
        this.sentBccAddresses = cleanEmailAddress(model.getSentBccAddresses());
        this.sentFromAddress = cleanEmailAddress(model.getSentFromAddress());
        this.sentReplyToAddress = cleanEmailAddress(model.getSentReplyToAddress());
        this.sentDate = model.getSentDate();
        this.retryCount = model.getRetryCount();
        if (sentDate == null) {
            this.setSent(false);
        } else {
            this.setSent(true);
        }
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setRecipientEmailAddress(String recipientEmailAddresses) {
		this.recipientEmailAddress = recipientEmailAddresses;
	}
	
	public String getRecipientEmailAddress() {
		return this.recipientEmailAddress;
	}
	
	public void setCarbonCopy(String carbonCopyEmailAddresses) {
		this.carbonCopy = carbonCopyEmailAddresses;
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

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    private String cleanEmailAddress (String emailAddress) {
        if (StringUtils.isNotBlank(emailAddress)) {
            return emailAddress.replace("\"", "").replace("'", "").trim();
        }
        return emailAddress;
    }
}
