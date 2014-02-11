/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp.service.external.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.jasig.ssp.dao.external.ExternalDataDao;
import org.jasig.ssp.dao.external.ExternalStudentTestDao;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.external.ExternalStudentTest;
import org.jasig.ssp.model.reference.Config;
import org.jasig.ssp.security.BasicAuthenticationRestTemplate;
import org.jasig.ssp.service.external.ExternalStudentTestService;
import org.jasig.ssp.service.reference.ConfigService;
import org.jasig.ssp.transferobject.external.ExternalStudentTestTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ExternalStudentTestServiceImpl extends
		AbstractExternalDataService<ExternalStudentTest> implements ExternalStudentTestService {

	@Autowired
	private transient ExternalStudentTestDao dao;
	
	private static final String SMARTER_MEASURE_TEST_CODES_CONFIG_NAME ="smarter_measure_reserved_test_code_pairs";
	
	private static final String SMARTER_MEASURE_USERNAME_CONFIG_NAME = "smarter_measure_username";
	
	private static final String SMARTER_MEASURE_PASSWORD_CONFIG_NAME = "smarter_measure_password";
	
	private static final String SMARTER_MEASURE_BASE_URL_CONFIG_NAME = "smarter_measure_base_url";
	
	private static final String SMARTER_MEASURE_GROUP_KEY_CONFIG_NAME = "smarter_measure_group_key";
	
	@Autowired
	private transient ConfigService configService;

	@Override
	protected ExternalDataDao<ExternalStudentTest> getDao() {
		// TODO Auto-generated method stub
		return dao;
	}
	
	@Override
	public List<ExternalStudentTest> getStudentTestResults(String schoolId) {		
		return  dao.getStudentTestResults(schoolId);
	}


	@Override
	public List<ExternalStudentTest> getStudentTestResults(String schoolId, UUID id) {
		List<ExternalStudentTest> studentTests = dao.getStudentTestResults(schoolId);
		if(studentTests.size() > 0){
			Config tcConfig = configService.getByName(SMARTER_MEASURE_TEST_CODES_CONFIG_NAME);
			if(tcConfig != null && StringUtils.isNotBlank(tcConfig.getValue())){
				Map<String,String> codes = getTestCodes(tcConfig.getValue());
				for(ExternalStudentTest studentTest:studentTests){
					if(hasDetails(studentTest, codes)){
						studentTest.setHasDetails(true);
						studentTest.setTestProviderLink(getPersonalTestLink(id, studentTest));
					}
				}
			}
		}
		return studentTests;
	}
	
	@Override
	public Object getTestDetails(String testCode, String subTestcode, Person person){
		String username = null;
		String password = null;
		String baseUrl = null;
		String groupKey = null;
		Config unConfig = configService.getByName(SMARTER_MEASURE_USERNAME_CONFIG_NAME);
		Config pConfig = configService.getByName(SMARTER_MEASURE_PASSWORD_CONFIG_NAME);
		Config buConfig = configService.getByName(SMARTER_MEASURE_BASE_URL_CONFIG_NAME);
		Config gkConfig = configService.getByName(SMARTER_MEASURE_GROUP_KEY_CONFIG_NAME);
		if(unConfig != null && StringUtils.isNotBlank(unConfig.getValue())){
			username = unConfig.getValue();
		}else{
			return null;
		}
		
		if(pConfig != null && StringUtils.isNotBlank(pConfig.getValue())){
			password = pConfig.getValue();
		}else{
			return null;
		}
		
		if(buConfig != null && StringUtils.isNotBlank(buConfig.getValue())){
			baseUrl = buConfig.getValue();
		}else{
			return null;
		}
		
		if(gkConfig != null && StringUtils.isNotBlank(gkConfig.getValue())){
			groupKey = gkConfig.getValue();
		}
		
		String userNameUrl = baseUrl+ "/users";
		Map<String,String> params = new HashMap<String,String>();
		params.put("FirstName", person.getFirstName());
		params.put("LastName", person.getLastName());
		params.put("Email", person.getPrimaryEmailAddress());
		params.put("InternalID", person.getSchoolId());
		if(StringUtils.isNotBlank(groupKey))
			params.put("GroupKey", groupKey);
		
		BasicAuthenticationRestTemplate client = new BasicAuthenticationRestTemplate(username, password);
		String smUserId = (String)client.get(userNameUrl, params, String.class);
		
		String urlRedirect = baseUrl+ "/users/" + smUserId + "/reportlink";

		return  (String)client.get(urlRedirect, null, String.class);
	}


	private Boolean hasDetails(ExternalStudentTest studentTest, Map<String,String> codeMap){
		if(codeMap.containsKey(studentTest.getTestCode())){
			String subTestCode = codeMap.get(studentTest.getTestCode());
			if((StringUtils.isBlank(studentTest.getSubTestCode()) && StringUtils.isBlank(subTestCode)) ||
					subTestCode.equals(studentTest.getSubTestCode()))
				return true;
			else
				return false;
		}
		return false;
	}
	
	private  Map<String,String> getTestCodes(String testCodes){
		Map<String,String> codeMap = new HashMap<String,String>();
		String[] splitCodes = testCodes.split(",");
		for(String code:splitCodes){
			String[] splitCode = code.split(":");
			if(splitCode.length == 2){
				codeMap.put(splitCode[0], splitCode[1]);
			}else{
				codeMap.put(splitCode[0], null);
			}
		}
		
		return codeMap;
	}
	
	
	
	private String getPersonalTestLink(UUID studentId, ExternalStudentTest studentTest){
		StringBuilder url=  new StringBuilder("/ssp/api/1/person/" +
				studentId.toString() + "/test/details");
		if(StringUtils.isNotBlank(studentTest.getTestCode()))
				url.append("?testCode=").append(studentTest.getTestCode());
		if(StringUtils.isNotBlank(studentTest.getSubTestCode()))
			url.append("&subTestCode=").append(studentTest.getSubTestCode());
		
		return url.toString();
	}

}
