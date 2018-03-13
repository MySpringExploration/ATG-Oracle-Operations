/**
 * 
 */
package org.iqra.operationsapp.entity;

import java.util.List;

import org.springframework.stereotype.Component;

/**
 * @author Abdul
 * 27-Feb-2018
 * 
 */
@Component
public class TableMetaData {
	
	private List<String> tableMetadata;
	
	public TableMetaData() {
		super();
	}
	
	public List<String> getTableMetadata() {
		return tableMetadata;
	}

	public void setTableMetadata(List<String> tableMetadata) {
		this.tableMetadata = tableMetadata;
	}

	@Override
	public String toString() {
		return "TableMetaData [tableMetadata=" + tableMetadata + "]";
	}

	
	
}
