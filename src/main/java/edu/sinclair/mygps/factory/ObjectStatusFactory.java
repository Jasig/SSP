package edu.sinclair.mygps.factory;

import edu.sinclair.ssp.model.ObjectStatus;

public class ObjectStatusFactory {

	public static ObjectStatus activeToObjectStatus (Boolean active) {
		ObjectStatus objectStatus;

		if (active) {
			objectStatus = ObjectStatus.ACTIVE;
		} else {
			objectStatus = ObjectStatus.INACTIVE;
		}

		return objectStatus;
	}

	public static Boolean objectStatusToActive (ObjectStatus objectStatus) {
		return objectStatus == ObjectStatus.ACTIVE;
	}
}
