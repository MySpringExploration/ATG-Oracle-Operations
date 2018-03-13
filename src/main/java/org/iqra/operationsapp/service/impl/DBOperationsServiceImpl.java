/**
 * 
 */
package org.iqra.operationsapp.service.impl;

import org.iqra.operationsapp.dao.DBOperationsDAO;
import org.iqra.operationsapp.entity.DBOperations;
import org.iqra.operationsapp.service.DBOperationsService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

/**
 * @author Abdul
 * 28-Feb-2018
 * 
 */
@Service
public class DBOperationsServiceImpl implements DBOperationsService {
	
	//@Autowired
	//private DBOperationsDAO  dbOperationsDAO;
	
	@Override
	public DBOperations executeQuery(String dbQuery) {
		ApplicationContext context =
				new ClassPathXmlApplicationContext("Spring-Module.xml");
		DBOperationsDAO dbOperationsDAO = (DBOperationsDAO)context.getBean("dbOperationsDAO");
		return dbOperationsDAO.executeQuery(dbQuery);
	}

}
