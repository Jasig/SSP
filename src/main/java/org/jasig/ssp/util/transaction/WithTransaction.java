package org.jasig.ssp.util.transaction;


import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.Callable;

public interface WithTransaction {
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	<T> T withNewTransaction(Callable<T> work) throws Exception;

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	<T> T withNewTransactionAndUncheckedExceptions(Callable<T> work);

	@Transactional
	<T> T withTransaction(Callable<T> work) throws Exception;

	@Transactional
	<T> T withTransactionAndUncheckedExceptions(Callable<T> work);
}
