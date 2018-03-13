/**
 * 
 */
package org.iqra.operationsapp.export.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.iqra.operationsapp.entity.AllRowsData;
import org.iqra.operationsapp.entity.DBOperations;
import org.iqra.operationsapp.entity.EachRowData;
import org.iqra.operationsapp.entity.TableMetaData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.document.AbstractXlsView;


/**
 * @author Abdul
 * 09-Mar-2018
 * 
 */
public class ExcelView extends AbstractXlsView{

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
    @Override
    protected void buildExcelDocument(Map<String, Object> model,
                                      Workbook workbook,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
    	
    	logger.debug("Begin : ExcelView --> /buildExcelDocument() --> ");

        // change the file name
        response.setHeader("Content-Disposition", "attachment; filename=\"Results.xls\"");
        
        @SuppressWarnings("unchecked")
        DBOperations dBOperations = (DBOperations) model.get("dBOperations");
        
        TableMetaData tableMetadata = dBOperations.getTableMetadata();
        // create excel xls sheet
        Sheet sheet = workbook.createSheet("Results");
        sheet.setDefaultColumnWidth(30);

        // create style for header cells
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("Arial");
        style.setFillForegroundColor(HSSFColor.BLUE.index);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        font.setBold(true);
        font.setColor(HSSFColor.WHITE.index);
        style.setFont(font);

        // create header row
        Row header = sheet.createRow(0);
        int columnCount = 0;
        for(String columnName : tableMetadata.getTableMetadata()) {
        		header.createCell(columnCount).setCellValue(columnName);
            header.getCell(columnCount).setCellStyle(style);
            columnCount++;
        }
        
        
        int rowCount = 1;
        
        AllRowsData allRowsData = dBOperations.getAllRowsData();
        for(EachRowData eachRowData : allRowsData.getAllRows()) {
        		Row userRow =  sheet.createRow(rowCount);
        		int dataColumnCount = 0;
        		for(String columnData : eachRowData.getEachRow()) {
        			userRow.createCell(dataColumnCount).setCellValue(columnData);
        			dataColumnCount++;
        		}
        		rowCount++;
        }
        
        logger.debug("End : ExcelView --> /buildExcelDocument() --> ");
        	}
	
}
