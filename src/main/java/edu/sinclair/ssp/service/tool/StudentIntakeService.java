package edu.sinclair.ssp.service.tool;

import java.util.UUID;

import org.springframework.stereotype.Service;

import edu.sinclair.ssp.model.tool.StudentIntakeForm;

@Service
public class StudentIntakeService {

	//private static final Logger logger = LoggerFactory.getLogger(StudentIntakeService.class);

	public boolean save(StudentIntakeForm form){
		return false;
	}
	
	public StudentIntakeForm loadForStudent(UUID studentId){
		return new StudentIntakeForm();
	}
}
