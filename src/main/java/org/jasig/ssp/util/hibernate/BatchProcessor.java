/**
 * Licensed to Apereo under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Apereo licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp.util.hibernate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author jamesstanley
 * Example Usage:
 * BatchProcessor processor =  new BatchProcessor(ids, sAndP);
 *		do{
 *			final Criteria criteria = createCriteria();
 *			processor.process(criteria, "id");
 *		}while(processor.moreToProcess());
 *
 *	return processor.getPagedResults();
 * @param <I> The ids
 * @param <O> The sorting and paging object
 */
public class BatchProcessor<I, O> {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(BatchProcessor.class);
	
	@Value("#{configProperties.db_batchsize}")
	private int batchsize = 300;
	SortingAndPaging sortAndPage =  null;
	private Long count = 0L;
	
	Iterator<List<I>> batches;
	List<O> results = new ArrayList<O>();
	
	public BatchProcessor(List<I> in, SortingAndPaging sortAndPage) {
		batches = prepareBatches(in);
		this.sortAndPage = sortAndPage;
	}
	
	public BatchProcessor(Collection<I> in, SortingAndPaging sortAndPage) {
		batches = prepareBatches(in);
		this.sortAndPage = sortAndPage;
	}
	
	public BatchProcessor(List<I> in) {
		batches = prepareBatches(in);
	}
	
	public BatchProcessor(Collection<I> in) {
		batches = prepareBatches(in);
	}
	
	public void process(Query query, String propertyName){
		if(batches.hasNext()){
			List<I> batch = batches.next();
			if(batch != null && !batch.isEmpty()){
				query.setParameterList(propertyName, batch);
				results.addAll(query.list());
			}
		}
	}
	
	public List<I> updateProcess(Query query, String propertyName){
		if(batches.hasNext()){
			List<I> batch = batches.next();
			if(batch != null && !batch.isEmpty()){
				query.setParameterList(propertyName, batch);
				count += query.executeUpdate();
			}
			return batch;
		}
		return null;
	}
	
	public void countDistinct(Query query, String propertyName){
		if(batches.hasNext()){
			List<I> batch = batches.next();
			if(batch != null && !batch.isEmpty()){
				query.setParameterList(propertyName, batch);
				count += (Long)(query.uniqueResult());
			}
		}
	}
	
	public void countDistinct(Criteria query, String propertyName){
		if(batches.hasNext()){
			List<I> batch = batches.next();
			if(batch != null && !batch.isEmpty()){
				query.add(Restrictions.in(propertyName, batch));
				count += (Long)(query.uniqueResult());
			}
		}
	}
	
	public void process(Criteria criteria, String propertyName){
		if(batches.hasNext()){
			List<I> batch = batches.next();
			if(batch != null && !batch.isEmpty()){
				criteria.add(Restrictions.in(propertyName, batch));
				results.addAll(criteria.list());
			}
		}
	}
	
	public Boolean moreToProcess(){
		return batches.hasNext();
	}
	
	public List<O> getSortedAndPagedResultsAsList() {
		
		if(sortAndPage != null){
			try{
				return (List<O>)sortAndPage.sortAndPageList((List<Object>)results);
			}catch(Exception exp){
				LOGGER.error("Error returning results from batch processor.",exp);
			}
		}
		return results;
	}
	
	public List<O> getUnsortedUnpagedResultsAsList() {
		return results;
	}
	
	public PagingWrapper<O> getUnSortedAndUnPagedResults(Integer count){
		if(count == null)
		  count = results.size();
		return new PagingWrapper<O>(count, results);
	}
	
	public PagingWrapper<O> getSortedAndPagedResults(){
		Integer count = results.size();
		if(sortAndPage != null){
			try{
				return new PagingWrapper<O>(count, (List<O>)sortAndPage.sortAndPageList((List<Object>)results));
			}catch(Exception exp){
				LOGGER.error("Error returning results from batch processor.",exp);
		    }
		}

		return new PagingWrapper<O>(count, results);
	}
	
	private Iterator<List<I>> prepareBatches(Collection<I> in) 
	{
		List<I> currentBatch = new ArrayList<I>(); 
		List<List<I>> batches = new ArrayList<List<I>>();
		int batchCounter = 0;
		for (I i : in) 
		{
			if(batchCounter == batchsize)
			{
				currentBatch.add(i);
				batches.add(currentBatch);
				currentBatch = new ArrayList<I>();
				batchCounter = 0;
			}
			else
			{
				currentBatch.add(i);
				batchCounter++;
			}
		}
		if(currentBatch.size() > 0)
			batches.add(currentBatch);
		return batches.iterator();
	}
	
	public Long getCount(){
		return count;
	}
}
