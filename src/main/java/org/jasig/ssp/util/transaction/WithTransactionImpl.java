package org.jasig.ssp.util.transaction;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.Callable;

@Service
public class WithTransactionImpl implements WithTransaction {

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public <T> T withNewTransaction(Callable<T> work) throws Exception {
		return doWork(work);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public <T> T withNewTransactionAndUncheckedExceptions(Callable<T> work) {
		try {
			return doWork(work);
		} catch ( RuntimeException e ) {
			throw e;
		} catch ( Exception e ) {
			throw new RuntimeException(e);
		}
	}

	@Override
	@Transactional
	public <T> T withTransaction(Callable<T> work) throws Exception {
		return doWork(work);
	}

	private <T> T doWork(Callable<T> work) throws Exception  {
		return work.call();
	}
}
