/**
 * 
 */
package org.iqra.operationsapp.service;

import org.iqra.operationsapp.entity.DBOperations;
import org.springframework.security.access.annotation.Secured;

/**
 * @author Abdul
 * 28-Feb-2018
 * 
 */
public interface DBOperationsService {
	@Secured ({"ROLE_USER","ROLE_EDITUSER","ROLE_ADMIN"})
	DBOperations executeQuery(String dbQuery);
}
