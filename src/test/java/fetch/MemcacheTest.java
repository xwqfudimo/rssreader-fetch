package fetch;

import java.util.List;

import org.junit.Test;

import util.MemcacheUtil;
import constant.Constants;

public class MemcacheTest {

	@Test
	public void testSet() {
		MemcacheUtil.set("test", "this is a test");
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGet() {
		LoadFetchUrl.loadUrlFromDB();
		
		List<String> rssSources = (List<String>) MemcacheUtil.get(Constants.RSS_SOURCES);
		System.out.println("rssSources : " + rssSources);
		for(String url : rssSources) {
			System.out.println(url);
		}
	}
	
	@Test 
	public void testFlushAll() {
		MemcacheUtil.flushAll();
	}
}
