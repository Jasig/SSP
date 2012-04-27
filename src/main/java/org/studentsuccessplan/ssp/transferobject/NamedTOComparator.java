package org.studentsuccessplan.ssp.transferobject;

import java.io.Serializable;
import java.util.Comparator;

public class NamedTOComparator implements Comparator<NamedTO>, Serializable {

	private static final long serialVersionUID = 1267207639241417281L;

	@Override
	public int compare(final NamedTO a, final NamedTO b) {
		return a.getName().toUpperCase()
				.compareTo(b.getName().toUpperCase());
	}
}
