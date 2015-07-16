package org.jasig.ssp.transferobject.reference;

import java.io.Serializable;

public class ReferenceCounterTO  implements Serializable {

	private static final long serialVersionUID = 4629721410362384086L;
	
	String name;
	Long count;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}

}
