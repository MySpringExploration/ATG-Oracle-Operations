/**
 * 
 */
package org.iqra.operationsapp.service.impl;

import org.iqra.operationsapp.dao.DBOperationsDAO;
import org.iqra.operationsapp.entity.DBOperations;
import org.iqra.operationsapp.service.DBOperationsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

/**
 * @author Abdul
 * 28-Feb-2018
 * 
 */
@Service
public class DBOperationsServiceImpl implements DBOperationsService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public DBOperations executeQuery(String dbQuery) {
		
		logger.debug("Begin : DBOperationsServiceImpl --> executeQuery() -->");
		
		ApplicationContext context =
				new ClassPathXmlApplicationContext("Spring-Module.xml");
		DBOperationsDAO dbOperationsDAO = (DBOperationsDAO)context.getBean("dbOperationsDAO");
		((ConfigurableApplicationContext)context).close();
		
		logger.debug("End : DBOperationsServiceImpl --> executeQuery() -->");
		return dbOperationsDAO.executeQuery(dbQuery);
	}

}
