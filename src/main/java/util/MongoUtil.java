package util;

import java.net.UnknownHostException;
import java.util.Map;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;

import config.reader.DBConfigReader;

public class MongoUtil {
	private static MongoClient client;
	private static DB db;
	
	private MongoUtil() {}
	
	static {
		if(null == db) {
			try {
				client = new MongoClient(DBConfigReader.getHost(), DBConfigReader.getPort());
				db = client.getDB(DBConfigReader.getDbname());
			} catch (UnknownHostException e) {
				e.printStackTrace();
				System.err.println("数据库初始化错误");
			}
		}
	}
	
	public static DB getDB() {
		db.cleanCursors(true);
		return db;
	}
	
	public static WriteResult insert(String collectionName, Map<String, Object> params) {
		DB db = getDB();
		DBCollection collection = db.getCollection(collectionName);
		
		BasicDBObject doc = new BasicDBObject(params);
		
		return collection.insert(doc);
	}
}
