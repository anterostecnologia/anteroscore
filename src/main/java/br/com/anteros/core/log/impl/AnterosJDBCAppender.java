package br.com.anteros.core.log.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.log4j.jdbc.JDBCAppender;

public class AnterosJDBCAppender extends JDBCAppender{

	private int maxTimeOnMinutesToKeepLog = 0;

	private String deleteStatement;

	@Override
	public void flushBuffer() {
		if (maxTimeOnMinutesToKeepLog > 0 && deleteStatement != null) {
			Date date = new Date();
			long millisecond =  maxTimeOnMinutesToKeepLog * 60 * 1000;
			Date newDate = new Date(date.getTime() - millisecond);

			Connection con = null;
			PreparedStatement stmt = null;

			try {
				con = getConnection();
				stmt = (PreparedStatement) con.prepareStatement(deleteStatement);
				stmt.setTimestamp(1, new Timestamp(newDate.getTime()));
				stmt.executeUpdate();
				stmt.close();
			} catch (SQLException e) {
				if (stmt != null)
					try {
						stmt.close();
						throw e;
					} catch (SQLException e1) {
					}
			}
			closeConnection(con);
		}

		super.flushBuffer();
	}

	public int getMaxTimeOnMinutesToKeepLog() {
		return maxTimeOnMinutesToKeepLog;
	}

	public void setMaxTimeOnMinutesToKeepLog(int maxTimeOnMinutesToKeepLog) {
		this.maxTimeOnMinutesToKeepLog = maxTimeOnMinutesToKeepLog;
	}

	public String getDeleteStatement() {
		return deleteStatement;
	}

	public void setDeleteStatement(String deleteStatement) {
		this.deleteStatement = deleteStatement;
	}

}
