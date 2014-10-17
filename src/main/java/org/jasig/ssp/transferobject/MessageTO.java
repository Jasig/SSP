package org.jasig.ssp.transferobject;

import org.jasig.ssp.model.Message;

public class MessageTO extends AbstractAuditableTO<Message>
	implements TransferObject<Message> {

	private String carbonCopy;
	private String recipientEmailAddress;
	
	public MessageTO() {
		super();
	}
	
	public MessageTO(final Message message) {
		super.from(message);
		this.recipientEmailAddress = message.getRecipientEmailAddress();
		this.carbonCopy = message.getCarbonCopy();
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
	

}
