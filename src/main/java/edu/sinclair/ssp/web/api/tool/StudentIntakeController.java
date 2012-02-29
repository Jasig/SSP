package edu.sinclair.ssp.web.api.tool;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.sinclair.ssp.service.tool.StudentIntakeService;

@Controller
@RequestMapping("/tool/studentIntake")
public class StudentIntakeController {

	//private static final Logger logger = LoggerFactory.getLogger(StudentIntakeController.class);

	@Autowired
	private StudentIntakeService service;

	@RequestMapping(value = "/referenceData", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> referenceData() {
		return service.referenceData();
	}
}
