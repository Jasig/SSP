package edu.sinclair.ssp.factory;

import java.util.List;

import com.google.common.collect.Lists;

public abstract class AbstractTransferObjectFactory <T,U> implements TransferObjectFactory<T,U>{

	public abstract T toTO(U from);
	
	public abstract U toModel(T from);
	
	public List<T> toTOList(List<U> from){
		List<T> toList = Lists.newArrayList();
		for(U c : from){
			toList.add(toTO(c));
		}
		return toList;
	}
	
	public List<U> toModelList(List<T> from){
		List<U> toList = Lists.newArrayList();
		for(T c : from){
			toList.add(toModel(c));
		}
		return toList;
	}
}
