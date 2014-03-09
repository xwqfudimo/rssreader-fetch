package config.reader;

import java.io.InputStream;
import java.util.Properties;

public class FetchConfigReader {
	private static int interval = 5000;
	private static int updateTime = 5;
	
	static {
		try {
			Properties prop = new Properties();
			InputStream is = FetchConfigReader.class.getClassLoader().getResourceAsStream("fetch.properties");
			prop.load(is);
			
			interval = Integer.valueOf(prop.getProperty("fetch.interval").trim());
			updateTime = Integer.valueOf(prop.getProperty("fetch_url.update.time").trim());
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public static int getInterval() {
		return interval;
	}
	
	public static int getUpdateTime() {
		return updateTime;
	}
}
