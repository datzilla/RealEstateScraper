package utils;

import java.beans.PropertyVetoException;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class MySQLHandler {


	private static MySQLHandler mMySQLHandler;
	private static ComboPooledDataSource mConnectionPool;
	
	private String mJDBCClass;
	private String mJDBCUrl;
	private String mDatabase;
	private String mUser;
	private String mPassword;
	short mMaxPoolSize;

	private MySQLHandler (String pJDBCClass, String pJDBCUrl, String pUser, String pPassword, String pDatabase, short pMaxPoolSize) throws SQLException, PropertyVetoException {
		mJDBCClass = pJDBCClass;
		mJDBCUrl = pJDBCUrl;
		mUser = pUser;
		mDatabase = pDatabase;
		mUser = pUser;
		mPassword = pPassword;
		mMaxPoolSize = pMaxPoolSize;
		
		//  Set up 
		mConnectionPool = new ComboPooledDataSource();
		
		//Connect to SQLite
		mConnectionPool.setDriverClass(mJDBCClass);
		mConnectionPool.setJdbcUrl(mJDBCUrl);
		mConnectionPool.setDataSourceName(mDatabase);
		mConnectionPool.setUser(mUser);
		mConnectionPool.setPassword(mPassword);
		
		mConnectionPool.setAutoCommitOnClose(true);
		mConnectionPool.setMaxPoolSize(mMaxPoolSize);
	}
	
	private MySQLHandler (String pJDBCClass, String pJDBCUrl,short pMaxPoolSize) throws SQLException, PropertyVetoException {
		mJDBCClass = pJDBCClass;
		mJDBCUrl = pJDBCUrl;
		mMaxPoolSize = pMaxPoolSize;
		
		//  Set up 
		mConnectionPool = new ComboPooledDataSource();
		
		//Connect to SQLite
		mConnectionPool.setDriverClass(mJDBCClass);
		mConnectionPool.setJdbcUrl(mJDBCUrl);
		mConnectionPool.setUser(mUser);
		mConnectionPool.setPassword(mPassword);
		
		mConnectionPool.setAutoCommitOnClose(true);
		mConnectionPool.setMaxPoolSize(mMaxPoolSize);
	}
	

	public static MySQLHandler getHandler (String pJDBCClass, String pJDBCUrl, String pUser, String pPassword, String pDatabase, short pMaxPoolSize) throws SQLException, PropertyVetoException {
		if (mMySQLHandler == null) {
			mMySQLHandler = new MySQLHandler (pJDBCClass, pJDBCUrl, pUser, pPassword, pDatabase, pMaxPoolSize);
			return mMySQLHandler;
		} else {
			return mMySQLHandler;
		}
	}
	
	
	public static MySQLHandler getHandler (String pJDBCClass, String pJDBCUrl, short pMaxPoolSize) throws SQLException, PropertyVetoException {
		if (mMySQLHandler == null) {
			mMySQLHandler = new MySQLHandler (pJDBCClass, pJDBCUrl, pMaxPoolSize);
			return mMySQLHandler;
		} else {
			return mMySQLHandler;
		}
	}
	
	public Connection getConnection () throws SQLException {
		if(mConnectionPool != null) {
			return mConnectionPool.getConnection();
		} else {
			return null;
		}
	}
	
	public void closeConnection (Connection pConnection) throws Exception {
		pConnection.close();
	}
	
	public ResultSet selectAllRows (Connection pConnection, String pTableName) throws Exception {
		Statement stmt = pConnection.createStatement();
		ResultSet result = stmt.executeQuery ("SELECT * FROM " + pTableName);
		return result;
	}
	
	public void insertStringValue (Connection pConnection, String pTableName, String pColumnName, String pValue, String pIDColumnNanme, String pRowID)  throws Exception{
		String sql = "UPDATE " + pTableName
				+ " SET " + pColumnName 
				+ "= ? "
				+ "WHERE " + pIDColumnNanme + "=" + pRowID;
		PreparedStatement stmt = (PreparedStatement) pConnection.prepareStatement(sql);
		stmt.setString(1, pValue);
		stmt.execute();
		stmt.close();
		stmt = null;
		pConnection.commit();
	}
	
	public void insertByteValue (Connection pConnection, String pTableName, String pColumnName, byte pValue, String pIDColumnNanme, String pRowID)  throws Exception{
		String sql = "UPDATE " + pTableName
				+ " SET " + pColumnName 
				+ "= ? "
				+ "WHERE " + pIDColumnNanme + "=" + pRowID;
		PreparedStatement stmt = (PreparedStatement) pConnection.prepareStatement(sql);
		stmt.setInt (1, pValue);
		stmt.execute();
		stmt.close();
		stmt = null;
		pConnection.commit();
	}
	
	public void insertTextValue (Connection pConnection, String pTableName, String pColumnName, String pValue, String pIDColumnNanme, String pRowID)  throws Exception{
		String sql = "UPDATE " + pTableName
				+ " SET " + pColumnName 
				+ "= ? "
				+ "WHERE " + pIDColumnNanme + "=" + pRowID;
		PreparedStatement stmt = (PreparedStatement) pConnection.prepareStatement(sql);
		stmt.setBinaryStream(1, StringToInputStream (pValue));
		stmt.execute();
		stmt.close();
		stmt = null;
		pConnection.commit();
	}
	
	public void insertIntValue (Connection pConnection, String pTableName, String pColumnName, int pValue, String pIDColumnNanme, String pRowID)  throws Exception{
		String sql = "UPDATE " + pTableName
				+ " SET " + pColumnName 
				+ "= ? "
				+ "WHERE " + pIDColumnNanme + "=" + pRowID;
		PreparedStatement stmt = (PreparedStatement) pConnection.prepareStatement(sql);
		stmt.setInt (1, pValue);
		stmt.execute();
		stmt.close();
		stmt = null;
		pConnection.commit();
	}
	
	private InputStream StringToInputStream (String pString) {
		InputStream is;
		try {
			is = new ByteArrayInputStream(pString.getBytes("utf-8"));
		}catch (Exception e){
			is = new ByteArrayInputStream(pString.getBytes());
		}
		return is;
	}
	
	public String autoIDGenerateNewRow (Connection pConnection, String pTableName, String pPrimaryColumn) throws Exception {
		String rowID = null;
		Statement stmt = pConnection.createStatement();
		String sqlStatement = "INSERT INTO " + pTableName + "(" + pPrimaryColumn + " ) VALUES (null)";

		stmt.execute(sqlStatement, Statement.RETURN_GENERATED_KEYS);
		ResultSet rs = stmt.getGeneratedKeys();
		rs.next();
		rowID = rs.getString (1);
		pConnection.commit();
		stmt.close();
		stmt = null;
		rs.close();
		rs = null;
		return rowID;
	}
	
	public String getDatabase() {
		return mDatabase;
	}

	public void setDatabase(String pDatabase) {
		mDatabase = pDatabase;
	}

	public String getUser() {
		return mUser;
	}

	public void setUser(String pUser) {
		mUser = pUser;
	}

	public String getPassword() {
		return mPassword;
	}

	public void setPassword(String pPassword) {
		mPassword = pPassword;
	}
}
