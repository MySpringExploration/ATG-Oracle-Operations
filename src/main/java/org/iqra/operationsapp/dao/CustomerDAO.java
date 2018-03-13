/**
 * 
 */
package org.iqra.operationsapp.dao;

import org.iqra.operationsapp.entity.Customer;

/**
 * @author Abdul
 * 28-Feb-2018
 * 
 */
public interface CustomerDAO {
	public void insert(Customer customer);
	public Customer findByCustomerId(int custId);
}
