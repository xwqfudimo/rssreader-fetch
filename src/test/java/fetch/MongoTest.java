package fetch;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import util.MongoUtil;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class MongoTest {

	@Test
	public void testQuery() {
		DB db = MongoUtil.getDB();
		DBCollection coll = db.getCollection("article");
		DBCursor cursor = coll.find();
		while (cursor.hasNext()) {
			DBObject record = cursor.next();
			System.out.println(record.get("title") + "----" + record.get("link") + "----" + record.get("published_date"));
			System.out.println("-------------------------------------");
			System.out.println(record.get("content"));
			System.out.println("******************************************\n\n");
		}
		cursor.close();
	}
	
	@Test
	public void testDrop() {
		DB db = MongoUtil.getDB();
		DBCollection coll = db.getCollection("article");
		coll.drop();
	}
	
	@Test 
	public void testInsert() {
		DB db = MongoUtil.getDB();
		DBCollection coll = db.getCollection("fetch_url");
		Map<String, String> map = new HashMap<String, String>();
		map.put("fetch_url", "http://feeds.geekpark.net/");
		map.put("source_name", "极客公园");
		BasicDBObject doc = new BasicDBObject(map);
		coll.insert(doc);
	}
}
