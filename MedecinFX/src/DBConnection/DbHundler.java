package DBConnection;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

public class DbHundler extends Configs {
	Connection dbConnection;
	public Connection getConnection(){

		String connectionString = "jdbc:mysql://"+Configs.dbHost+":"+Configs.dbPort+"/"+Configs.dbName+"?autoReconnect=true&useSSL=false";
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			dbConnection = (Connection) DriverManager.getConnection(connectionString,Configs.dbUser,Configs.dbPass);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dbConnection;

	}

}
