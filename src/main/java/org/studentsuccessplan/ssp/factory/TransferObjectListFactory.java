package org.studentsuccessplan.ssp.factory;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import org.studentsuccessplan.ssp.transferobject.TransferObject;

/**
 * Turn a list of Transfer Objects into Model Objects, and back again.
 * 
 * @param <T>
 *            The Transfer Object Class
 * @param <M>
 *            The Model Object Class
 */
public class TransferObjectListFactory<T extends TransferObject<M>, M> {

	private static final Logger logger = LoggerFactory
			.getLogger(TransferObjectListFactory.class);

	private Class<T> transferObjectClass;

	/**
	 * The Constructor requires the Class to be provided again in order to be
	 * able to reflectively call the constructor. The factory requires that the
	 * class have a no parameter constructor.
	 * 
	 * @param transferObjectClass
	 *            Transfer object class
	 */
	public TransferObjectListFactory(Class<T> transferObjectClass) {
		this.transferObjectClass = transferObjectClass;
	}

	/**
	 * Create a list of Transfer Objects from a Collection of Model Objects
	 * 
	 * @param from
	 *            Original collection from which to transfer data.
	 * @return Copied transfer objects
	 */
	public List<T> toTOList(Collection<M> from) {
		List<T> toList = Lists.newArrayList();

		for (M m : from) {
			T tObject;
			try {
				tObject = transferObjectClass.newInstance();
				tObject.fromModel(m);
				toList.add(tObject);
			} catch (InstantiationException e) {
				logger.error("unable to instantiate Transfer object in factory.");
			} catch (IllegalAccessException e) {
				logger.error("unable to instantiate Transfer object in factory.");
			}
		}

		return toList;
	}

	/**
	 * Create a list of Model Objects from a Collection of Transfer Objects
	 * 
	 * @param from
	 *            Original collection from which to transfer data.
	 * @return Copied transfer objects
	 */
	public List<M> toModelList(Collection<T> from) {
		List<M> mList = Lists.newArrayList();
		toModelCollection(from, mList);
		return mList;
	}

	/**
	 * Create a Set of Model Objects from a Collection of Transfer Objects
	 * 
	 * @param from
	 *            Original collection from which to transfer data.
	 * @return Copied transfer objects
	 */
	public Set<M> toModelSet(Collection<T> from) {
		Set<M> mSet = Sets.newHashSet();
		toModelCollection(from, mSet);
		return mSet;
	}

	private void toModelCollection(Collection<T> from, Collection<M> to) {
		for (TransferObject<M> c : from) {
			to.add(c.asModel());
		}
	}
}
