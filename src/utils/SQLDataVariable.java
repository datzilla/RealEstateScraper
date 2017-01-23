package utils;

import java.sql.*;
import java.util.List;


public class SQLDataVariable <T> implements Variable <T> {
	private Connection mConnection;
	private String mDatabaseName;
	private String pTable;
	private short pColumnIndex;

	
	SQLDataVariable (Connection pConnection, String pDatabase, String pTable, short pColumnIndex) {
		
	}


	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public T getNext() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<T> getNext(int inNoOfElements) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setPosition(int pPosition) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public int getTotal() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public int getPosition() {
		// TODO Auto-generated method stub
		return 0;
	}

}
