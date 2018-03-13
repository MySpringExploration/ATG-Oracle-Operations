/**
 * 
 */
package org.iqra.operationsapp.dynadmin.service;


import org.iqra.operationsapp.entity.DashboardInfo;
import org.iqra.operationsapp.entity.DynAdminInfo;

/**
 * @author Abdul
 * 06-Mar-2018
 * 
 */

public interface DynAdminOperationsService {
	
	public  DynAdminInfo setComponentValues(DynAdminInfo dynAdminInfo);
	public DynAdminInfo invalidateCache(DynAdminInfo dynAdminInfo);
	public DynAdminInfo invokeMethod(DynAdminInfo dynAdminInfo);
	public DynAdminInfo getComponentValue(DynAdminInfo dynAdminInfo);
	public DynAdminInfo getComponentValueWithConcurrentThreads(DynAdminInfo dynAdminInfo);
	public DashboardInfo getServersStatus(DashboardInfo dashboardInfo);
	

}
