package config.reader;

import java.io.InputStream;
import java.util.Properties;

public class MemcacheConfigReader {
	private static String host;
	private static String port;
	
	static {
		try {
			Properties prop = new Properties();
			InputStream is = MemcacheConfigReader.class.getClassLoader().getResourceAsStream("memcache.properties");
			prop.load(is);
			
			host = prop.getProperty("memcache.host").trim();
			port = prop.getProperty("memcache.port").trim();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public static String getHost() {
		return host;
	}
	
	public static String getPort() {
		return port;
	}
}
