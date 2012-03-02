package edu.sinclair.ssp.model.transferobject;

import java.util.Map;

import com.google.common.collect.Maps;

public class Form <T>{

	private T model;
	
	private Map<String, Object> referenceData = Maps.newHashMap();

	public T getModel() {
		return model;
	}

	public void setModel(T model) {
		this.model = model;
	}

	public Map<String, Object> getReferenceData() {
		return referenceData;
	}

	public void setReferenceData(Map<String, Object> referenceData) {
		this.referenceData = referenceData;
	}
	
	
}
