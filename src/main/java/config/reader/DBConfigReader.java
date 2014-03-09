package config.reader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DBConfigReader {
	private static String host;
	private static Integer port;
	private static String dbname;
	private static String username;
	private static String password;
	
	static {
		try {
			Properties prop = new Properties();
			InputStream is = DBConfigReader.class.getClassLoader().getResourceAsStream("db.properties");
			prop.load(is);
			
			host = prop.getProperty("db.host").trim();
			port = Integer.valueOf(prop.getProperty("db.port").trim());
			dbname = prop.getProperty("db.dbname").trim();
			username = prop.getProperty("db.username").trim();
			password = prop.getProperty("db.password").trim();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getHost() {
		return host;
	}

	public static Integer getPort() {
		return port;
	}

	public static String getDbname() {
		return dbname;
	}

	public static String getUsername() {
		return username;
	}

	public static String getPassword() {
		return password;
	}
}
