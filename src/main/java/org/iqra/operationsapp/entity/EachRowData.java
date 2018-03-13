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
public class EachRowData {
	
	private List<String> eachRow;

	public EachRowData() {
		super();
		
	}

	public List<String> getEachRow() {
		return eachRow;
	}

	public void setEachRow(List<String> eachRow) {
		this.eachRow = eachRow;
	}

	@Override
	public String toString() {
		return "EachRowData [eachRow=" + eachRow + "]";
	}

}
