/**
 * 
 */
package org.iqra.operationsapp.ds;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * @author Abdul 14-Mar-2018
 * 
 */

public class CustomDataSource {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private String jDBCUrl;
	private String userName;
	private String password;

	private static HikariConfig config = new HikariConfig();
	private static HikariDataSource ds = new HikariDataSource();

	// static {
	// //config = new HikariConfig("datasource.properties");
	//
	// //Properties props = new Properties();
	// //props.setProperty("dataSourceClassName", "org.h2.Driver");
	// //props.setProperty("dataSource.user", "spring.datasource.enusername");
	// //props.setProperty("dataSource.password", "");
	// //props.put("dataSource.logWriter", new PrintWriter(System.out));
	// //config = new HikariConfig(props);
	//
	// //config.setJdbcUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;INIT=runscript from
	// 'classpath:/db.sql'");
	// //config.setUsername("");
	// //config.setPassword("");
	// //config.addDataSourceProperty("cachePrepStmts", "true");
	// //config.addDataSourceProperty("prepStmtCacheSize", "250");
	// //config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
	// //ds = new HikariDataSource(config);
	//
	// ds.setJdbcUrl(jDBCUrl);
	// ds.setUsername(userName);
	// ds.setPassword(password);
	// }

	public CustomDataSource() {

		super();
		logger.debug("Begin : CustomDataSource --> CustomDataSource() -->  jDBCUrl : " + jDBCUrl + " userName "
				+ userName + " password " + password);

		
		logger.debug("End : CustomDataSource --> CustomDataSource() -->  jDBCUrl : " + jDBCUrl + " userName " + userName
				+ " password " + password);
	}

	public CustomDataSource(String jDBCUrl, String userName, String password) {
		super();
		this.jDBCUrl = jDBCUrl;
		this.userName = userName;
		this.password = password;
		ds.setJdbcUrl(jDBCUrl);
		ds.setUsername(userName);
		ds.setPassword(password);
	}

	public Connection getConnection() throws SQLException {
		logger.debug("Begin : CustomDataSource --> getConnection() ");

		return ds.getConnection();
	}

}
