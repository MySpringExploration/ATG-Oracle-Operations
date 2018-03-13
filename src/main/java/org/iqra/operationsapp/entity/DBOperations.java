/**
 * 
 */
package org.iqra.operationsapp.entity;


import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;

/**
 * @author Abdul
 * 27-Feb-2018
 * 
 */
@Component
public class DBOperations implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String dbQuery;
	
	private String exceptionMsg;
	
	private String successMsg;
	
	private TableMetaData tableMetadata;
	
	private AllRowsData allRowsData;
	
	public String getDbQuery() {
		return dbQuery;
	}

	public void setDbQuery(String dbQuery) {
		this.dbQuery = dbQuery;
	}

	public String getExceptionMsg() {
		return exceptionMsg;
	}

	public void setExceptionMsg(String exceptionMsg) {
		this.exceptionMsg = exceptionMsg;
	}

	public String getSuccessMsg() {
		return successMsg;
	}

	public void setSuccessMsg(String successMsg) {
		this.successMsg = successMsg;
	}
	
	public TableMetaData getTableMetadata() {
		return tableMetadata;
	}

	public void setTableMetadata(TableMetaData tableMetadata) {
		this.tableMetadata = tableMetadata;
	}

	public AllRowsData getAllRowsData() {
		return allRowsData;
	}

	public void setAllRowsData(AllRowsData allRowsData) {
		this.allRowsData = allRowsData;
	}
	
}
