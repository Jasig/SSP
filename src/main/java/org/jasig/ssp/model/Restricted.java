package org.jasig.ssp.model;

import org.jasig.ssp.model.reference.ConfidentialityLevel;

/**
 * Marks a class as having a Restriction via a ConfidentialityLevel
 * 
 */
public interface Restricted {

	ConfidentialityLevel getConfidentialityLevel();
}
