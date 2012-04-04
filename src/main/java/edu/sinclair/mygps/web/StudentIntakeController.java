package edu.sinclair.mygps.web;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
	
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.sinclair.mygps.form.StudentIntakeFormManager;
import edu.sinclair.mygps.model.transferobject.FormTO;

@Controller
@RequestMapping("/intake")
public class StudentIntakeController {
	
	@Autowired
	private StudentIntakeFormManager studentIntakeFormManager;

	private Logger logger = LoggerFactory.getLogger(StudentIntakeController.class);

	@RequestMapping(value = "/getForm", method = RequestMethod.GET)
	public @ResponseBody FormTO getForm() throws Exception {
		
		try {
			return studentIntakeFormManager.populate();
		} catch (Exception e) {
			logger.error("ERROR : getForm() : {}", e.getMessage(), e);
			throw e;
		}
		
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody Boolean saveForm(@RequestBody FormTO formTO, HttpServletResponse response) throws Exception {
		
		try {
			studentIntakeFormManager.save(formTO);
			return true;
		} catch (Exception e) {
			logger.error("ERROR : saveForm() : {}", e.getMessage(), e);
			throw e;
		}
	
	}
	
	@ExceptionHandler(Exception.class)
	public @ResponseBody String handleException(Exception e, HttpServletResponse response) {
		logger.error("ERROR : handleException()", e);
	    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	    return e.getMessage();
	}
	
}
