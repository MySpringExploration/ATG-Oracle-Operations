/**
 * 
 */
package org.iqra.operationsapp.dao.impl;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.iqra.operationsapp.dao.DBOperationsDAO;
import org.iqra.operationsapp.entity.AllRowsData;
import org.iqra.operationsapp.entity.Customer;
import org.iqra.operationsapp.entity.DBOperations;
import org.iqra.operationsapp.entity.EachRowData;
import org.iqra.operationsapp.entity.TableMetaData;
import org.springframework.stereotype.Component;

/**
 * @author Abdul 27-Feb-2018
 * 
 */

@Component
public class DBOperationsDAOImpl implements DBOperationsDAO {

	private DBOperations dBOperations;

	public void setdBOperations(DBOperations dBOperations) {
		this.dBOperations = dBOperations;
	}

	private TableMetaData tableMetaData;

	public void setTableMetaData(TableMetaData tableMetaData) {
		this.tableMetaData = tableMetaData;
	}

	private AllRowsData allRowsData;

	public void setAllRowsData(AllRowsData allRowsData) {
		this.allRowsData = allRowsData;
	}

	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public DBOperations executeQuery(String dbQuery) {

		Connection conn = null;
		Statement statement = null;
		ResultSet resultSet = null;
		ResultSetMetaData metaData = null;

		int updateCount = -1;
		boolean isDesc = false;
		String queryPrefix = dbQuery.substring(0, dbQuery.indexOf(" ")).toUpperCase();
		
		List<String> metaDataList = new ArrayList<String>();
		List<EachRowData> rowsDataList = new ArrayList<EachRowData>();
		

		try {
			conn = dataSource.getConnection();
			statement = conn.createStatement();
			if (queryPrefix.equals("CREATE")) {
				statement.executeUpdate(dbQuery);
				dBOperations.setSuccessMsg("Table Created successfully");
			} else if (queryPrefix.equals("ALTER")) {
				statement.executeUpdate(dbQuery);
				dBOperations.setSuccessMsg("Table Altered successfully");
			} else if (queryPrefix.equals("DROP")) {
				statement.executeUpdate(dbQuery);
				dBOperations.setSuccessMsg("Table Dropped successfully");
			} else if (queryPrefix.equals("RENAME")) {
				statement.executeUpdate(dbQuery);
				dBOperations.setSuccessMsg("Table Renamed successfully");
			} else {
				if (statement.execute(dbQuery)) {

					try {
						resultSet = statement.getResultSet();
						metaData = resultSet.getMetaData();
						if (queryPrefix.equals("DESC") || queryPrefix.equals("DESCRIBE")) {
							isDesc = true;
						}

					} catch (SQLException e) {
						dBOperations.setExceptionMsg(e.getMessage());
						return dBOperations;
					}
					int columnCount = 0;
					try {
						columnCount = metaData.getColumnCount();
					} catch (SQLException e) {
						dBOperations.setExceptionMsg(e.getMessage());
						return dBOperations;
					}

					try {

						if (!isDesc) {

							execSelectQuery(resultSet, metaData, metaDataList, rowsDataList, columnCount);
						} else {
							execDescribeQuery(dbQuery, conn, rowsDataList);
						}

					} catch (SQLException e) {
						dBOperations.setExceptionMsg(e.getMessage());
						return dBOperations;
					}
					allRowsData.setAllRows(rowsDataList);

					resultSet.close();
					statement.close();

				} else {
					try {
						updateCount = statement.getUpdateCount();
						if (updateCount > 0) {
							execModifyTableDataQuery(updateCount, queryPrefix);

						} else if (queryPrefix.equals("TRUNC") || queryPrefix.equals("TRUNCATE")) {
							dBOperations.setSuccessMsg("Table truncated successfully");
						}

						statement.close();
					} catch (SQLException e) {
						dBOperations.setExceptionMsg(e.getMessage());
						return dBOperations;
					}

				}

			}

		} catch (SQLException e) {
			dBOperations.setExceptionMsg(e.getMessage());
			return dBOperations;
			// throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					dBOperations.setExceptionMsg("Exception while closing db connection :<br/>" + e.getMessage());
					return dBOperations;
				}
			}
		}
		dBOperations.setTableMetadata(tableMetaData);
		dBOperations.setAllRowsData(allRowsData);

		return dBOperations;
	}

	private void execSelectQuery(ResultSet resultSet, ResultSetMetaData metaData, List<String> metaDataList,
			List<EachRowData> rowsDataList, int columnCount) throws SQLException {
		for (int i = 0; i < columnCount; i++) {
			String colname = null;
			try {
				colname = metaData.getColumnName(i + 1).toUpperCase();

			} catch (Exception e) {
				colname = "ERROR";
			}
			metaDataList.add(colname);
		}
		tableMetaData.setTableMetadata(metaDataList);
		while (resultSet.next()) {
			EachRowData eachRowData = new EachRowData();
			List<String> columnsDataList = new ArrayList<String>();
			for (int i = 0; i < columnCount; i++) {

				String data = null;

				data = resultSet.getString(i + 1);
				if (data == null) {
					data = "null";
				}
				columnsDataList.add(data);
			}
			eachRowData.setEachRow(columnsDataList);
			rowsDataList.add(eachRowData);
		}
	}

	private void execModifyTableDataQuery(int updateCount, String queryPrefix) {
		if (queryPrefix.equals("UPDATE")) {

			dBOperations.setSuccessMsg("Successfully updated " + updateCount + " records");

		} else if (queryPrefix.equals("DELETE")) {

			dBOperations.setSuccessMsg("Successfully deleted " + updateCount + " records");

		} else if (queryPrefix.equals("INSERT")) {

			dBOperations.setSuccessMsg("Successfully Inserted " + updateCount + " records");

		} else if (queryPrefix.equals("TRUNC") || queryPrefix.equals("TRUNCATE")) {

			dBOperations.setSuccessMsg("Successfully truncated " + updateCount + " records");

		}
	}

	private void execDescribeQuery(String dbQuery, Connection conn, List<EachRowData> rowsDataList)
			throws SQLException {
		DatabaseMetaData dataBaseMetaDta;
		List<String> metaDataList;
		
		String[] columnNamesArray = { "COLUMN_NAME", "TYPE_NAME", "COLUMN_SIZE", "IS_NULLABLE", "IS_GENERATEDCOLUMN",
				"IS_PRIMARY_KEY" };
		metaDataList = new ArrayList<String>(Arrays.asList(columnNamesArray));
		tableMetaData.setTableMetadata(metaDataList);

		String tableName = "";
		if (dbQuery.contains(";")) {
			tableName = dbQuery.substring(dbQuery.indexOf(" "), dbQuery.length() - 1).toUpperCase().trim();
		} else {
			tableName = dbQuery.substring(dbQuery.lastIndexOf(" "), dbQuery.length()).toUpperCase();
		}

		dataBaseMetaDta = conn.getMetaData();
		
		ResultSet resultSetForDesc = dataBaseMetaDta.getColumns(null, "%", tableName, "%");
		ResultSet resultSetForPrimaryKey = dataBaseMetaDta.getPrimaryKeys(null, "%", tableName);
		List<String> primaryKeysList = new ArrayList<String>();
		while (resultSetForPrimaryKey.next()) {
			primaryKeysList.add(resultSetForPrimaryKey.getString("COLUMN_NAME"));
		}
		while (resultSetForDesc.next()) {
			EachRowData eachRowData = new EachRowData();
			List<String> columnsDataList = new ArrayList<String>();
			for (int i = 0; i < 6; i++) {
				String columnName = resultSetForDesc.getString("COLUMN_NAME");
				String data = null;
				if (i == 5) {
					if (primaryKeysList.contains(columnName)) {
						data = "YES";
					} else {
						data = "NO";
					}
				} else {
					data = resultSetForDesc.getString(columnNamesArray[i]);
					if (data == null) {
						data = "null";
					}
				}
				columnsDataList.add(data.toUpperCase());
			}
			eachRowData.setEachRow(columnsDataList);
			rowsDataList.add(eachRowData);

		}
		
		resultSetForDesc.close();
	}

	public void insert(Customer customer) {

		String sql = "INSERT INTO CUSTOMER1 " + "(CUST_ID, NAME, AGE) VALUES (?, ?, ?)";
		Connection conn = null;

		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, customer.getCustId());
			ps.setString(2, customer.getName());
			ps.setInt(3, customer.getAge());
			ps.executeUpdate();
			ps.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);

		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
	}

	public boolean executeDDL(String dbQuery) {
		return false;
	}

	public int executeDML(String dbQuery) {

		return 0;
	}

}
