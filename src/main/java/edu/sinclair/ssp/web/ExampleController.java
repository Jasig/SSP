package edu.sinclair.ssp.web;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/example")
public class ExampleController {

	private static final Logger logger = LoggerFactory
			.getLogger(ExampleController.class);

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody
	Map<String, String> defaultMethod() throws Exception {
		return get();
	}

	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public @ResponseBody
	Map<String, String> get() throws Exception {
		logger.info("get called");

		Map<String, String> val = new HashMap<String, String>();
		val.put("firstName", "Shawn");
		val.put("middleInitial", "E");
		val.put("lastName", "Gormley");
		val.put("uniqueSchoolUserID", "0383504");
		val.put("birthDate", "08/22/1973");
		val.put("emailSchool", "shawn.gormley@sinclair.edu");
		val.put("emailHome", "shawn.gormley@sinclair.edu");
		val.put("homePhone", "937-767-9494");
		val.put("workPhone", "937-512-2293");
		val.put("cellPhone", "555-555-5555");
		val.put("address", "2360 Hazelnut Dr.");
		val.put("city", "Fairborn");
		val.put("state", "OH");
		val.put("zipCode", "45324-2183");
		val.put("studentId", "12345678");

		return val;
	}

}
