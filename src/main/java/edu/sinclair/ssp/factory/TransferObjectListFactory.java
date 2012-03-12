package edu.sinclair.ssp.factory;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

import edu.sinclair.ssp.transferobject.TransferObject;

public class TransferObjectListFactory <T extends TransferObject<M>,M>{

	private static final Logger logger = LoggerFactory.getLogger(TransferObjectListFactory.class);

	private Class<T> transferObjectClass;

	public TransferObjectListFactory(Class<T> transferObjectClass){
		this.transferObjectClass = transferObjectClass;
	}

	public List<T> toTOList(List<M> from){
		List<T> toList = Lists.newArrayList();
		for(M m : from){
			T tObject;
			try {
				tObject = (T) transferObjectClass.newInstance();
				tObject.pullAttributesFromModel(m);
				toList.add(tObject);
			} catch (InstantiationException e) {
				logger.error("unable to instantiate Transfer object in factory.");
			} catch (IllegalAccessException e) {
				logger.error("unable to instantiate Transfer object in factory.");
			}
		}
		return toList;
	}

	public List<M> toModelList(List<T> from){
		List<M> mList = Lists.newArrayList();
		for(TransferObject<M> c : from){
			mList.add(c.asModel());
		}
		return mList;
	}
}
