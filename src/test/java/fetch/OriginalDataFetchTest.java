package fetch;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import util.MemcacheUtil;

public class OriginalDataFetchTest {
	
	@Before
	public void init() {
		List<String> addrList = new ArrayList<String>();
		addrList.add("http://feeds.geekpark.net/");
		MemcacheUtil.set("rssSources", addrList);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void test() {
		List<String> rssAddrs = (List<String>) MemcacheUtil.get("rssSources");
		for(String addr : rssAddrs) {
			FetchRunnable.fetch(addr);
		}
	}

}
