package util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	
	public static String getCurrent() {
		return sdf.format(new Date());
	}
	
	public static String convertDate(Date date) {
		return sdf.format(date);
	}
}
