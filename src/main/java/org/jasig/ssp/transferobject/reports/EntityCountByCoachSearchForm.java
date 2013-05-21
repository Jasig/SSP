package org.jasig.ssp.transferobject.reports;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.jasig.ssp.model.Person;
import org.jasig.ssp.util.sort.SortingAndPaging;

public class EntityCountByCoachSearchForm {
	
	private List<Person> coaches;
	private Date createDateFrom;
	private Date createDateTo;
	private List<UUID> studentTypeIds;
	private List<UUID> serviceReasonIds;
	private List<UUID> specialServiceGroupIds;
	private SortingAndPaging sAndP;
	
	
	public EntityCountByCoachSearchForm(){
		
	}
	
	/**
	 * @param coaches
	 * @param createDateFrom
	 * @param createDateTo
	 * @param studentTypeIds
	 * @param serviceReasonIds
	 * @param specialServiceGroupIds
	 * @param sAndP
	 */
	public EntityCountByCoachSearchForm(List<Person> coaches,
			Date createDateFrom, Date createDateTo, List<UUID> studentTypeIds,
			List<UUID> serviceReasonIds, List<UUID> specialServiceGroupIds,
			SortingAndPaging sAndP) {
		super();
		this.coaches = coaches;
		this.createDateFrom = createDateFrom;
		this.createDateTo = createDateTo;
		this.studentTypeIds = studentTypeIds;
		this.serviceReasonIds = serviceReasonIds;
		this.specialServiceGroupIds = specialServiceGroupIds;
		this.sAndP = sAndP;
	}
	/**
	 * @return the coaches
	 */
	public List<Person> getCoaches() {
		return coaches;
	}
	/**
	 * @param coaches the coaches to set
	 */
	public void setCoaches(List<Person> coaches) {
		this.coaches = coaches;
	}
	/**
	 * @return the createDateFrom
	 */
	public Date getCreateDateFrom() {
		return createDateFrom;
	}
	/**
	 * @param createDateFrom the createDateFrom to set
	 */
	public void setCreateDateFrom(Date createDateFrom) {
		this.createDateFrom = createDateFrom;
	}
	/**
	 * @return the createDateTo
	 */
	public Date getCreateDateTo() {
		return createDateTo;
	}
	/**
	 * @param createDateTo the createDateTo to set
	 */
	public void setCreateDateTo(Date createDateTo) {
		this.createDateTo = createDateTo;
	}
	/**
	 * @return the studentTypeIds
	 */
	public List<UUID> getStudentTypeIds() {
		return studentTypeIds;
	}
	/**
	 * @param studentTypeIds the studentTypeIds to set
	 */
	public void setStudentTypeIds(List<UUID> studentTypeIds) {
		this.studentTypeIds = studentTypeIds;
	}
	/**
	 * @return the serviceReasonIds
	 */
	public List<UUID> getServiceReasonIds() {
		return serviceReasonIds;
	}
	/**
	 * @param serviceReasonIds the serviceReasonIds to set
	 */
	public void setServiceReasonIds(List<UUID> serviceReasonIds) {
		this.serviceReasonIds = serviceReasonIds;
	}
	/**
	 * @return the specialServiceGroupIds
	 */
	public List<UUID> getSpecialServiceGroupIds() {
		return specialServiceGroupIds;
	}
	/**
	 * @param specialServiceGroupIds the specialServiceGroupIds to set
	 */
	public void setSpecialServiceGroupIds(List<UUID> specialServiceGroupIds) {
		this.specialServiceGroupIds = specialServiceGroupIds;
	}
	/**
	 * @return the sAndP
	 */
	public SortingAndPaging getSAndP() {
		return sAndP;
	}
	/**
	 * @param sAndP the sAndP to set
	 */
	public void setSAndP(SortingAndPaging sAndP) {
		this.sAndP = sAndP;
	}

}
