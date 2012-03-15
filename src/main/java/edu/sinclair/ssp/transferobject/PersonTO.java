package edu.sinclair.ssp.transferobject;

import java.util.Date;

import edu.sinclair.ssp.model.Person;

public class PersonTO extends AuditableTO implements TransferObject<Person>{

	private String firstName, middleInitial, lastName;
	private Date birthDate;
	private String primaryEmailAddress, secondaryEmailAddress;
	private String username;
	private String homePhone, workPhone, cellPhone;
	private String addressLine1, addressLine2, city, state, zipCode;
	private String photoUrl;
	private String schoolId;
	private boolean enabled;


	public PersonTO(){
		super();
	}

	public PersonTO(Person model){
		super();
		pullAttributesFromModel(model);
	}


	public void pullAttributesFromModel(Person model){
		super.fromModel(model);
		
		if(model.getFirstName()!=null){
			setFirstName(model.getFirstName());
		}
		
		if(model.getMiddleInitial()!=null){
			setMiddleInitial(model.getMiddleInitial());
		}
		
		if(model.getLastName()!=null){
			setLastName(model.getLastName());
		}
		
		if(model.getBirthDate()!=null){
			setBirthDate(model.getBirthDate());
		}
		
		if(model.getPrimaryEmailAddress()!=null){
			setPrimaryEmailAddress(model.getPrimaryEmailAddress());
		}
		
		if(model.getSecondaryEmailAddress()!=null){
			setSecondaryEmailAddress(model.getSecondaryEmailAddress());
		}
		
		if(model.getUsername()!=null){
			setUsername(model.getUsername());
		}
		
		if(model.getHomePhone()!=null){
			setHomePhone(model.getHomePhone());
		}
		
		if(model.getWorkPhone()!=null){
			setWorkPhone(model.getWorkPhone());
		}
		
		if(model.getCellPhone()!=null){
			setCellPhone(model.getCellPhone());
		}
		
		if(model.getAddressLine1()!=null){
			setAddressLine1(model.getAddressLine1());
		}
		
		if(model.getAddressLine2()!=null){
			setAddressLine2(model.getAddressLine2());
		}
		
		if(model.getCity()!=null){
			setCity(model.getCity());
		}
		
		if(model.getState()!=null){
			setState(model.getState());
		}
		
		if(model.getZipCode()!=null){
			setZipCode(model.getZipCode());
		}
		
		if(model.getPhotoUrl()!=null){
			setPhotoUrl(model.getPhotoUrl());
		}
		
		if(model.getSchoolId()!=null){
			setSchoolId(model.getSchoolId());
		}
		
		setEnabled(model.isEnabled());
	}

	public Person pushAttributesToModel(Person model){
		super.addToModel(model);
		
		if(getFirstName()!=null){
			model.setFirstName(getFirstName());
		}
		
		if(getMiddleInitial()!=null){
			model.setMiddleInitial(getMiddleInitial());
		}
		
		if(getLastName()!=null){
			model.setLastName(getLastName());
		}
		
		if(getBirthDate()!=null){
			model.setBirthDate(getBirthDate());
		}
		
		if(getPrimaryEmailAddress()!=null){
			model.setPrimaryEmailAddress(getPrimaryEmailAddress());
		}
		
		if(getSecondaryEmailAddress()!=null){
			model.setSecondaryEmailAddress(getSecondaryEmailAddress());
		}
		
		if(getUsername()!=null){
			model.setUsername(getUsername());
		}
		
		if(getHomePhone()!=null){
			model.setHomePhone(getHomePhone());
		}
		
		if(getWorkPhone()!=null){
			model.setWorkPhone(getWorkPhone());
		}
		
		if(getCellPhone()!=null){
			model.setCellPhone(getCellPhone());
		}
		
		if(getAddressLine1()!=null){
			model.setAddressLine1(getAddressLine1());
		}
		
		if(getAddressLine2()!=null){
			model.setAddressLine2(getAddressLine2());
		}
		
		if(getCity()!=null){
			model.setCity(getCity());
		}
		
		if(getState()!=null){
			model.setState(getState());
		}
		
		if(getZipCode()!=null){
			model.setZipCode(getZipCode());
		}
		
		if(getPhotoUrl()!=null){
			model.setPhotoUrl(getPhotoUrl());
		}
		
		if(getSchoolId()!=null){
			model.setSchoolId(getSchoolId());
		}
		
		model.setEnabled(isEnabled());
		
		return model;
	}

	public Person asModel(){
		return pushAttributesToModel(new Person());
	}


	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleInitial() {
		return middleInitial;
	}

	public void setMiddleInitial(String middleInitial) {
		this.middleInitial = middleInitial;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getPrimaryEmailAddress() {
		return primaryEmailAddress;
	}

	public void setPrimaryEmailAddress(String primaryEmailAddress) {
		this.primaryEmailAddress = primaryEmailAddress;
	}

	public String getSecondaryEmailAddress() {
		return secondaryEmailAddress;
	}

	public void setSecondaryEmailAddress(String secondaryEmailAddress) {
		this.secondaryEmailAddress = secondaryEmailAddress;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getHomePhone() {
		return homePhone;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	public String getWorkPhone() {
		return workPhone;
	}

	public void setWorkPhone(String workPhone) {
		this.workPhone = workPhone;
	}

	public String getCellPhone() {
		return cellPhone;
	}

	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
}
