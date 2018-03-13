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
public class AllRowsData {
	
	private List<EachRowData> allRows;

	public AllRowsData() {
		super();
		
	}

	public List<EachRowData> getAllRows() {
		return allRows;
	}

	public void setAllRows(List<EachRowData> allRows) {
		this.allRows = allRows;
	}

	@Override
	public String toString() {
		return "AllRowsData [allRows=" + allRows + "]";
	}
	
}
