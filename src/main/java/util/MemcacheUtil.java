package util;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;

import config.reader.MemcacheConfigReader;

public class MemcacheUtil {
	private static MemCachedClient client = new MemCachedClient();
	
	private MemcacheUtil() {}
	
	static {
		SockIOPool pool = SockIOPool.getInstance();
		String server = MemcacheConfigReader.getHost() + ":" + MemcacheConfigReader.getPort();
		pool.setServers(new String[]{server});
		pool.setWeights(new Integer[]{1});
		
		 pool.setInitConn( 5 );
         pool.setMinConn( 5 );
         pool.setMaxConn( 250 );
         pool.setMaxIdle( 1000 * 60 * 60 * 6 );

         // set the sleep for the maint thread
         // it will wake up every x seconds and
         // maintain the pool size
         pool.setMaintSleep( 30 );

         // set some TCP settings
         // disable nagle
         // set the read timeout to 3 secs
         // and don't set a connect timeout
         pool.setNagle( false );
         pool.setSocketTO( 3000 );
         pool.setSocketConnectTO( 0 );
         
         pool.initialize();
         
         pool.setHashingAlg( SockIOPool.NEW_COMPAT_HASH );
	}
	
	public static Object get(String key) {
		return client.get(key);
	}
	
	public static void set(String key, Object value) {
		client.set(key, value);
	}
	
	public static void flushAll() {
		client.flushAll();
	}
}
