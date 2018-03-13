/**
 * 
 */
package org.iqra.operationsapp.dao;


import org.iqra.operationsapp.entity.Customer;
import org.iqra.operationsapp.entity.DBOperations;

/**
 * @author Abdul
 * 27-Feb-2018
 * 
 */
public interface DBOperationsDAO {
	public DBOperations executeQuery(String dbQuery);
	public boolean executeDDL(String dbQuery);
	public int executeDML(String dbQuery);
	public void insert(Customer customer);
	
}
