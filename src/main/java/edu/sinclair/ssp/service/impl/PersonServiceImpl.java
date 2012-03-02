package edu.sinclair.ssp.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.sinclair.ssp.dao.PersonDao;
import edu.sinclair.ssp.model.Person;
import edu.sinclair.ssp.service.PersonService;

@Service
@Transactional
public class PersonServiceImpl implements PersonService {

	//private static final Logger logger = LoggerFactory.getLogger(PersonServiceImpl.class);
	
	@Autowired
	private PersonDao dao;

	@Override
	public Person personFromId(UUID id) {
		return dao.get(id);
	}
	
	
}
