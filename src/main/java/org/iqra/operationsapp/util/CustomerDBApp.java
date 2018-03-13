/**
 * 
 */
package org.iqra.operationsapp.util;


import org.iqra.operationsapp.dao.DBOperationsDAO;
import org.iqra.operationsapp.entity.Customer;
import org.iqra.operationsapp.entity.DBOperations;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Abdul
 * 28-Feb-2018
 * 
 */
public class CustomerDBApp {
	
	public static void main( String[] args )
    {
		ApplicationContext context =
				new ClassPathXmlApplicationContext("Spring-Module.xml");
		DBOperationsDAO dbOperationsDAO = (DBOperationsDAO) context.getBean("dbOperationsDAO");
        /*CustomerDAO customerDAO = (CustomerDAO) context.getBean("customerDAO");
        Customer customer = new Customer(1, "mkyong",28);
        customerDAO.insert(customer);
        */
        
        Customer customer = new Customer(8, "Rafi",45);
        dbOperationsDAO.insert(customer);
        //DBOperations dbOperatoins = dbOperationsDAO.executeQuery("RENAME TABLE Customer TO Customer1");
        DBOperations dbOperatoins = dbOperationsDAO.executeQuery("select * from Customer1;");
        //Customer customer1 = customerDAO.findByCustomerId(1);
        System.out.println("Fetching Success Message : "+dbOperatoins.getSuccessMsg());
        System.out.println("Fetching Error Message : "+dbOperatoins.getExceptionMsg());
        System.out.println("Fetching Table MetaData : "+dbOperatoins.getTableMetadata());
        //System.out.println("System.out :: "+dbOperatoins.getSysOutput());
        System.out.println("Fetching Table Data : "+dbOperatoins.getAllRowsData());

    }
	
}
