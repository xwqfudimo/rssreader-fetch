package fetch;

import java.util.ArrayList;
import java.util.List;

import util.MemcacheUtil;
import util.MongoUtil;
import vo.FetchUrl;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import constant.Constants;

public class LoadFetchUrl {
	private static List<String> rssSources = new ArrayList<String>(); 
	
	//从数据库中读取rss源地址存入memcache中
	public static void loadUrlFromDB() {
		rssSources.clear();
		
		DB db = MongoUtil.getDB();
		DBCollection fetchUrlColl = db.getCollection("fetch_url");
		DBCursor cursor = fetchUrlColl.find();
		while(cursor.hasNext()) {
			DBObject record = cursor.next();
			FetchUrl fetchUrl = new FetchUrl();
			fetchUrl.setFetchUrl(record.get("fetch_url").toString());
			fetchUrl.setSourceName(record.get("source_name").toString());
			
			rssSources.add(fetchUrl.toString());
		}
		cursor.close();
		
		MemcacheUtil.set(Constants.RSS_SOURCES, rssSources);
	}
}
