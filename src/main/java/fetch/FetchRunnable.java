package fetch;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import util.DateUtil;
import util.MemcacheUtil;
import vo.FetchUrl;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

import config.reader.FetchConfigReader;
import constant.Constants;

/**
 * 原始数据抓取线程
 */
public class FetchRunnable implements Runnable {
	private static SyndFeedInput input = new SyndFeedInput();
	private static int time = 0;
	
	//从memcache中取出rss源地址
	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		List<String> rssSources = (List<String>) MemcacheUtil.get(Constants.RSS_SOURCES);
		for(String rssSrc : rssSources) {
			fetch(rssSrc);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void fetch(String rssSource) {
		//从数据库更新fetch_url列表
		if(time++ >= FetchConfigReader.getUpdateTime()) {
			LoadFetchUrl.loadUrlFromDB();
		}
		
		XmlReader reader = null;
		try {
			String[] fetchUrl = rssSource.split(FetchUrl.SEPARATE);
			String sourceName = fetchUrl[0];
			String addr = fetchUrl[1];
			
			URL url = new URL(addr);
			reader = new XmlReader(url);
			SyndFeed feed = input.build(reader);
			
			List<SyndEntry> list = feed.getEntries();
			for(SyndEntry entry : list) {
				//如果memcache中已存在link则不再入库
				if(null == MemcacheUtil.get(entry.getLink().hashCode() + "")) {
					//每次新建一个文章内容抓取线程去抓取内容
					String publishedDate = DateUtil.getCurrent();
					if(entry.getPublishedDate() != null) {
						publishedDate = DateUtil.convertDate(entry.getPublishedDate());
					}
					new Thread(new ContentRunnable(entry.getTitle(), entry.getLink(), publishedDate, sourceName)).start();
				} 
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(reader != null) reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
