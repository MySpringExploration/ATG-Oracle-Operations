/**
 * 
 */
package org.iqra.operationsapp.export.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.iqra.operationsapp.entity.AllRowsData;
import org.iqra.operationsapp.entity.DBOperations;
import org.iqra.operationsapp.entity.EachRowData;
import org.iqra.operationsapp.entity.TableMetaData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.document.AbstractPdfView;


import com.lowagie.text.Document;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;


/**
 * @author Abdul 10-Mar-2018
 * 
 */
public class PdfView extends AbstractPdfView {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		logger.debug("Begin : PdfView --> /buildPdfDocument() --> ");

		response.setHeader("Content-Disposition", "attachment; filename=\"Results.pdf\"");

		@SuppressWarnings("unchecked")
		DBOperations dBOperations = (DBOperations) model.get("dBOperations");

		TableMetaData tableMetadata = dBOperations.getTableMetadata();

		Table table = new Table(tableMetadata.getTableMetadata().size());
		
		
		for (String columnName : tableMetadata.getTableMetadata()) {
			table.addCell(columnName);
		}

		AllRowsData allRowsData = dBOperations.getAllRowsData();
		for (EachRowData eachRowData : allRowsData.getAllRows()) {
			for (String columnData : eachRowData.getEachRow()) {
				table.addCell(columnData);
			}
		}

		document.add(table);
		logger.debug("End : PdfView --> /buildExcelDocument() --> ");
	}

	
	
}
